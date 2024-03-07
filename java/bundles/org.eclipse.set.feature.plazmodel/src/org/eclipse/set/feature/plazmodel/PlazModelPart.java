/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plazmodel;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.eclipse.e4.core.services.events.IEventBroker;
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
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.plazmodel.check.PlazCheck;
import org.eclipse.set.feature.plazmodel.service.PlazModelService;
import org.eclipse.set.feature.plazmodel.table.PlazModelTableView;
import org.eclipse.set.model.plazmodel.PlazReport;
import org.eclipse.set.model.validationreport.ValidationProblem;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup;
import org.eclipse.set.utils.SaveAndRefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.emfforms.AbstractEmfFormsPart;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.table.export.ExportToCSV;
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

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

	@Inject
	CacheService cacheService;

	private PlazModelTableView tableView;
	private PlazReport plazReport;

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void postConstruct() {
		getBroker().subscribe(Events.DO_PLAZ_CHECK, new EventHandler() {

			@Override
			public void handleEvent(final Event event) {
				final Object property = event.getProperty(IEventBroker.DATA);
				if (property instanceof final Class<?> clazz
						&& PlazCheck.class.isAssignableFrom(clazz)) {
					final PlazReport newReport = plazModelService.runPlazModel(
							getModelSession(),
							(Class<? extends PlazCheck>) clazz);
					if (plazReport == null
							|| plazReport.getEntries().isEmpty()) {
						tableView.updateView(newReport);
						return;
					}

					final List<String> objectArts = newReport.getEntries()
							.stream().map(ValidationProblem::getObjectArt)
							.distinct().toList();
					final List<ValidationProblem> oldReports = plazReport
							.getEntries().stream().filter(entry -> objectArts
									.contains(entry.getObjectArt()))
							.toList();
					plazReport.getEntries().removeAll(oldReports);
					plazReport.getEntries().addAll(newReport.getEntries());
					PlazModelService
							.sortAndIndexedProblems(plazReport.getEntries());
					tableView.updateView(plazReport);
				}

			}
		});
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

		// export action
		getBanderole().setEnableExport(true);
		getBanderole().setExportAction(new SelectableAction() {

			@Override
			public void selected(final SelectionEvent e) {
				exportPlazModel();
			}

			@Override
			public String getText() {
				return messages.PlazModellPart_ExportTitleMsg;
			}
		});

		tableView.createExpandCollapseAllButton(
				(Composite) getBanderole().getControl(),
				messages.PlazModellPart_ExpandAllGroup,
				messages.PlazModellPart_CollapseAllGroup);

	}

	private void create(final Composite parent) {
		try {
			updatePlazModel();
			tableView.create(parent, plazReport);
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
		final Cache cache = cacheService
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

	@SuppressWarnings("nls")
	static final String HEADER_PATTERN = """
			PlaZ-Modell-Prüfung
			Datei: %s
			Prüfungszeit: %s
			Werkzeugkofferversion: %s


			"Lfd. Nr.";"Schweregrad";"Problemart";"Zeilennummer";"Objektart";"Attribut/-gruppe";"Bereich";"Zustand";"Meldung"
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

		// export
		final ExportToCSV<String> problemExport = new ExportToCSV<>(
				HEADER_PATTERN);
		problemExport.exportToCSV(optionalPath, tableView.transformToCSV());
		optionalPath.ifPresent(
				outputDir -> getDialogService().openDirectoryAfterExport(
						getToolboxShell(), outputDir.getParent()));
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
