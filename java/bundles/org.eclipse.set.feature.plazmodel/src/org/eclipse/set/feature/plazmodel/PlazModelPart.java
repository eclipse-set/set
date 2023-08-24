/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecp.ui.view.ECPRendererException;
import org.eclipse.set.basis.ProblemMessage;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.constants.ToolboxViewState;
import org.eclipse.set.basis.extensions.MApplicationElementExtensions;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.plazmodel.service.PlazModelService;
import org.eclipse.set.feature.plazmodel.table.PlazModelTableView;
import org.eclipse.set.model.plazmodel.PlazReport;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup;
import org.eclipse.set.utils.SaveAndRefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.emfforms.AbstractEmfFormsPart;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Stuecker
 */
public class PlazModelPart extends AbstractEmfFormsPart {
	private Exception createException;

	@Inject
	@Translation
	Messages messages;
	@Inject
	UISynchronize sync;

	@Inject
	PlazModelService plazModelService;

	@Inject
	EnumTranslationService enumTranslationService;

	@Inject
	TableMenuService tableMenuService;

	private PlazModelTableView tableView;
	private PlazReport plazReport;

	/**
	 * Create the part.
	 */
	@Inject
	public PlazModelPart() {
		super();
	}

	@Override
	protected void createFormsView(final Composite parent)
			throws ECPRendererException {
		tableView = new PlazModelTableView(this, messages, tableMenuService,
				enumTranslationService);
		final Shell shell = getToolboxShell();
		final MPart part = getToolboxPart();
		final DialogService dialogService = getDialogService();
		dialogService.showProgressUISync(shell,
				messages.PlaZService_MonitorDisplay_GeneratePlazModel,
				() -> create(parent));
		if (createException != null) {
			dialogService.error(shell, createException);
			MApplicationElementExtensions.setViewState(part,
					ToolboxViewState.ERROR);
		}
		if (getModelSession().isDirty()) {
			setOutdated(true);
		}

	}

	private void create(final Composite parent) {
		try {
			updatePlazModel();
			tableView.create(parent, plazReport);
			createExportButton(parent);
			createException = null;
		} catch (final Exception e) {
			createException = e;
		}
	}

	private void updatePlazModel() {
		plazReport = plazModelService.runPlazModel(getModelSession());
		tableView.updateView(plazReport);
		setOutdated(false);

		// record in cache service
		final Cache cache = Services.getCacheService()
				.getCache(ToolboxConstants.CacheId.PROBLEM_MESSAGE);
		final List<ProblemMessage> problems = cache.get("plazReport", //$NON-NLS-1$
				ArrayList::new);
		problems.clear();
		plazReport.getEntries().stream().filter(
				entry -> entry.getSeverity() != ValidationSeverity.SUCCESS)
				.forEach(entry -> problems
						.add(new ProblemMessage(entry.getMessage(),
								entry.getType(), entry.getLineNumber(), 2,
								entry.getObjectState().getLiteral())));
		getBroker().post(Events.PROBLEMS_CHANGED, null);
	}

	private void createExportButton(final Composite parent) {
		final Button exportPlazModelButton = new Button(parent, SWT.PUSH);
		exportPlazModelButton.setText(messages.PlazModellPart_ExportTitleMsg);
		exportPlazModelButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(final SelectionEvent event) {
				exportPlazModel();
			}

			@Override
			public void widgetSelected(final SelectionEvent event) {
				exportPlazModel();
			}
		});
	}

	static final String HEADER_PATTERN = """
			PlaZ-Modell-Prüfung
			Datei: %s
			Prüfungszeit: %s
			Werkzeugkofferversion: %s


			"Lfd. Nr.";"Schweregrad";"Problemart";"Zeilennummer";"Objektart";"Attribut/-gruppe";"Bereich";"Meldung"
			""";

	private void exportPlazModel() {
		final Shell shell = getToolboxShell();
		final Path location = getModelSession().getToolboxFile().getPath();
		final Path parent = location.getParent();
		final String defaultPath = parent == null ? "" : parent.toString(); //$NON-NLS-1$
		final String defaultFileName = String.format(
				messages.PlazModellPart_ExportCsvFilePattern,
				PathExtensions.getBaseFileName(location));

		final Optional<Path> optionalPath = getDialogService().saveFileDialog(
				shell, getDialogService().getCsvFileFilters(),
				Paths.get(defaultPath, defaultFileName),
				messages.PlazModellPart_ExportTitleMsg);

		if (optionalPath.isPresent()) {
			final String fileName = optionalPath.get().toString();
			try (final OutputStreamWriter writer = new OutputStreamWriter(
					new FileOutputStream(fileName),
					StandardCharsets.ISO_8859_1)) {
				writer.write(String.format(HEADER_PATTERN,
						location.getFileName().toString(),
						LocalDateTime.now()
								.format(DateTimeFormatter
										.ofPattern("uuuu-MM-dd HH:mm:ss")), //$NON-NLS-1$
						ToolboxConfiguration.getToolboxVersion()
								.getLongVersion()));
				plazReport.getEntries().stream().map(EObjectExtensions::toCSV)
						.forEach(t -> {
							try {
								writer.write(t);
							} catch (final IOException e) {
								throw new RuntimeException(e);
							}
						});

			} catch (final Exception e) {
				getDialogService().error(shell, e);
			}
		}

	}

	@Override
	protected SelectableAction getOutdatedAction() {
		return new SaveAndRefreshAction(this);
	}

	@Override
	protected void handleContainerDataChanged(final ContainerDataChanged e) {
		setOutdated(true);
	}

	@Override
	protected void handleProjectDataChanged(final ProjectDataChanged e) {
		setOutdated(true);
	}

	@Override
	protected void updateViewContainerDataChanged(
			final List<Container_AttributeGroup> container) {
		updatePlazModel();
	}

	@Override
	protected void updateViewProjectDataChanged(
			final List<Notification> notifications) {
		updatePlazModel();
	}

}
