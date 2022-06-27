/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.parts;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.ProblemMessage;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.constants.ToolboxViewState;
import org.eclipse.set.basis.extensions.MApplicationElementExtensions;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.core.services.validation.ValidationAnnotationService;
import org.eclipse.set.core.services.version.PlanProVersionService;
import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.feature.validation.report.SessionToValidationReportTransformation;
import org.eclipse.set.feature.validation.report.ValidationProblemTableViewerConsumer;
import org.eclipse.set.feature.validation.table.ValidationTableView;
import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.set.model.validationreport.extensions.ValidationReportExtension;
import org.eclipse.set.toolboxmodel.PlanPro.Container_AttributeGroup;
import org.eclipse.set.utils.SaveAndRefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.StatefulButtonAction;
import org.eclipse.set.utils.emfforms.AbstractEmfFormsPart;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.DefaultToolboxEventHandler;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * This part can display status and validation details about the current model
 * session.
 * 
 * @author Bleidiessel / Schaefer
 */
public class ValidationPart extends AbstractEmfFormsPart<IModelSession> {

	private static final int BUTTON_WIDTH_EXPORT_VALIDATION = 30;

	private static final String EXPORT_VALIDATION_ACTION = "exportValidationAction"; //$NON-NLS-1$

	private static final String SHOW_VALIDATION_TABLE_ACTION = "showValidationTableAction"; //$NON-NLS-1$

	private static final String VALIDATION_PROBLEM_TABLE_EVENT_LINK = "validationProblemTableEventLink"; //$NON-NLS-1$

	private static final String VIEW_VALIDATION_REPORT = "validationReport"; //$NON-NLS-1$

	private static final String INJECT_VIEW_VALIDATION_NATTABLE = "validationTableNattable"; //$NON-NLS-1$

	private Exception createException;

	private StatefulButtonAction exportValidationAction;

	@Inject
	private ToolboxPartService toolboxPartService;

	private SessionToValidationReportTransformation transformation;

	private DefaultToolboxEventHandler<JumpToSourceLineEvent> validationProblemSelectedHandler;

	private ValidationReport validationReport;

	private ValidationTableView tableView;

	@Inject
	private PlanProVersionService versionService;

	@Inject
	@Translation
	Messages messages;

	@Inject
	UISynchronize sync;

	@Inject
	private ValidationAnnotationService annotationModelService;

	@Inject
	private IEventBroker broker;

	/**
	 * Create the part.
	 */
	@Inject
	public ValidationPart() {
		super(IModelSession.class);
	}

	@Override
	protected void createFormsView(final Composite parent) {
		final Shell shell = getToolboxShell();
		final MPart part = getToolboxPart();
		final DialogService dialogService = getDialogService();
		dialogService.showProgressUISync(shell, messages.ProgressMsg,
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
			// create validation report
			transformation = new SessionToValidationReportTransformation(
					messages, versionService);
			validationReport = transformation.transform(getModelSession());

			// create annotation model
			annotationModelService.createModel(
					getModelSession().getToolboxFile().getModelPath(),
					validationReport);

			// link table to source viewer
			final ValidationProblemTableViewerConsumer<IModelSession> validationProblemTableEventLink = new ValidationProblemTableViewerConsumer<>(
					getBroker(), this, toolboxPartService);
			modelService.put(VALIDATION_PROBLEM_TABLE_EVENT_LINK,
					validationProblemTableEventLink);

			// record in cache service
			final Cache cache = Services.getCacheService()
					.getCache(ToolboxConstants.CacheId.PROBLEM_MESSAGE);
			final List<ProblemMessage> problems = cache.get("validationReport", //$NON-NLS-1$
					ArrayList::new);
			problems.clear();
			// IMPROVE: Record full line and column indices
			validationReport.getProblems()
					.forEach(problem -> problems
							.add(new ProblemMessage(problem.getMessage(),
									problem.getType(), problem.getLineNumber(),
									problem.getLineNumber(), 0, 99999, 3)));

			// export control
			exportValidationAction = new StatefulButtonAction(
					messages.ExportValidationMsg,
					BUTTON_WIDTH_EXPORT_VALIDATION) {

				@Override
				public void selected(final SelectionEvent e) {
					exportValidation();
				}
			};
			modelService.put(EXPORT_VALIDATION_ACTION, exportValidationAction);

			// show validation table control
			final StatefulButtonAction showValidationTableAction = new StatefulButtonAction(
					messages.ShowValidationTableMsg,
					BUTTON_WIDTH_EXPORT_VALIDATION) {

				@Override
				public void selected(final SelectionEvent e) {
					showValidationTable();
				}
			};
			modelService.put(SHOW_VALIDATION_TABLE_ACTION,
					showValidationTableAction);

			// Register nattable injector
			tableView = new ValidationTableView(toolboxPartService, this,
					messages, broker);
			final Function<Composite, Control> func = innerParent -> {
				final Control natTable = tableView.create(innerParent,
						validationReport);
				// Set height
				GridDataFactory.fillDefaults().grab(true, false)
						.hint(SWT.DEFAULT, 200).applyTo(natTable);
				return natTable;
			};
			modelService.put(INJECT_VIEW_VALIDATION_NATTABLE, func);

			// create form content
			createEmfFormsPart(parent, validationReport,
					VIEW_VALIDATION_REPORT);
			final Control formsControl = getView().getSWTControl();
			GridDataFactory.createFrom((GridData) formsControl.getLayoutData())
					.grab(true, false).align(SWT.FILL, SWT.TOP)
					.applyTo(formsControl);

			// initial update of button states
			updateButtonStates();

			createException = null;
		} catch (final Exception e) {
			createException = e;
		}
	}

	void exportValidation() {
		final Shell shell = getToolboxShell();
		final Path location = getModelSession().getToolboxFile().getPath();
		final Path parent = location.getParent();
		final String defaultPath = parent == null ? "" : parent.toString(); //$NON-NLS-1$
		final String defaultFileName = String.format(messages.ExportFilePattern,
				PathExtensions.getBaseFileName(location));

		final Optional<Path> optionalPath = getDialogService().saveFileDialog(
				shell, getDialogService().getCsvFileFilters(),
				Paths.get(defaultPath, defaultFileName),
				messages.ExportValidationTitleMsg);

		// export
		if (optionalPath.isPresent()) {
			final String fileName = optionalPath.get().toString();
			try (final OutputStreamWriter writer = new OutputStreamWriter(
					new FileOutputStream(fileName),
					StandardCharsets.ISO_8859_1)) {
				ValidationReportExtension.problemCsvExport(validationReport,
						writer);
			} catch (final Exception e) {
				getDialogService().error(shell, e);
			}
		}
	}

	void showValidationTable() {
		toolboxPartService.showPart(ToolboxConstants.VALIDATION_TABLE_PART_ID);
	}

	@Override
	protected void updateViewContainerDataChanged(
			final List<Container_AttributeGroup> container) {
		updateValidationView();
	}

	@Override
	protected void updateViewProjectDataChanged(
			final List<Notification> notifications) {
		updateValidationView();
	}

	private void updateValidationView() {
		if (isOutdated()) {
			// refresh validation
			getModelSession().refreshValidation();

			// update validation report
			validationReport = transformation.transform(getModelSession());

			// reset outdated mark
			setOutdated(false);

			// update buttons
			updateButtonStates();

			// update table
			tableView.updateView(validationReport);
		}
	}

	private void updateButtonStates() {
		exportValidationAction
				.setEnabled(!validationReport.getProblems().isEmpty());
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

	@PreDestroy
	private void preDestroy() {
		ToolboxEvents.unsubscribe(getBroker(),
				validationProblemSelectedHandler);
	}
}
