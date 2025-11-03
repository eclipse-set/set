/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.validation.parts;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.set.basis.ProblemMessage;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.constants.ToolboxViewState;
import org.eclipse.set.basis.extensions.MApplicationElementExtensions;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.configurationservice.UserConfigurationService;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.core.services.version.PlanProVersionService;
import org.eclipse.set.feature.validation.Messages;
import org.eclipse.set.feature.validation.report.SessionToValidationReportTransformation;
import org.eclipse.set.feature.validation.table.ValidationTableView;
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup;
import org.eclipse.set.model.validationreport.ValidationReport;
import org.eclipse.set.model.validationreport.ValidationSeverity;
import org.eclipse.set.utils.SaveAndRefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.emfforms.AbstractEmfFormsPart;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;

/**
 * This part can display status and validation details about the current model
 * session.
 * 
 * @author Bleidiessel / Schaefer
 */
public class ValidationPart extends AbstractEmfFormsPart {

	private static final int BUTTON_WIDTH_EXPORT_VALIDATION = 30;

	private static final String VIEW_VALIDATION_REPORT = "validationReport"; //$NON-NLS-1$

	private static final String INJECT_VIEW_VALIDATION_NATTABLE = "validationTableNattable"; //$NON-NLS-1$

	private Exception createException;

	@Inject
	private ToolboxPartService toolboxPartService;

	@Inject
	private TableMenuService tableMenuService;

	private SessionToValidationReportTransformation transformation;

	private ValidationReport validationReport;

	private ValidationTableView tableView;

	private Button exportValidationButton;

	@Inject
	private PlanProVersionService versionService;

	@Inject
	EnumTranslationService enumTranslationService;

	@Inject
	@Translation
	Messages messages;

	@Inject
	UISynchronize sync;

	@Inject
	CacheService cacheService;

	@Inject
	UserConfigurationService userConfigService;

	private Listener resizeListener;
	private Composite resizeListenerObject;

	@PostConstruct
	void postConstruct() {
		getBroker().subscribe(Events.CLOSE_SESSION, event -> {
			modelService.put(INJECT_VIEW_VALIDATION_NATTABLE, null);
		});
	}

	/**
	 * Create the part.
	 */
	@Inject
	public ValidationPart() {
		super();
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
					messages, versionService, enumTranslationService);
			validationReport = transformation.transform(getModelSession());

			storageReport();

			// Register nattable injector
			tableView = new ValidationTableView(this, messages,
					tableMenuService);
			final Function<Composite, Control> func = innerParent -> {

				final Composite composite = new Composite(innerParent,
						SWT.NONE);

				GridLayoutFactory.swtDefaults()
						.numColumns(3)
						.applyTo(composite);
				GridDataFactory.swtDefaults()
						.align(SWT.FILL, SWT.FILL)
						.grab(true, false)
						.applyTo(composite);

				final Control natTable = tableView.create(innerParent,
						validationReport);
				// create the open view button
				final Button showTableButton = new Button(composite, SWT.PUSH);
				GridDataFactory.swtDefaults()
						.align(SWT.LEFT, SWT.FILL)
						.grab(false, false)
						.applyTo(showTableButton);

				showTableButton.setText(messages.ShowValidationTableMsg);
				showTableButton.addListener(SWT.Selection,
						event -> showValidationTable());
				showTableButton.setSize(BUTTON_WIDTH_EXPORT_VALIDATION, 0);

				// create the toggle collapsed button
				final Button collapseAllButton = tableView
						.createExpandCollapseAllButton(composite,
								messages.ValidationTable_ExpandAllGroup,
								messages.ValidationTable_CollapseAllGroup);

				exportValidationButton = new Button(composite, SWT.PUSH);
				GridDataFactory.swtDefaults()
						.align(SWT.RIGHT, SWT.FILL)
						.grab(true, false)
						.applyTo(exportValidationButton);
				exportValidationButton.setText(messages.ExportValidationMsg);
				exportValidationButton.addListener(SWT.Selection,
						event -> tableView.exportCsv());
				exportValidationButton.setSize(BUTTON_WIDTH_EXPORT_VALIDATION,
						0);

				// Setup layout
				showTableButton.setLayoutData(
						new GridData(SWT.LEFT, SWT.TOP, false, true));
				collapseAllButton.setLayoutData(
						new GridData(SWT.LEFT, SWT.TOP, false, true));
				exportValidationButton.setLayoutData(
						new GridData(SWT.RIGHT, SWT.TOP, true, true));

				GridDataFactory.swtDefaults()
						.align(SWT.FILL, SWT.FILL)
						.grab(true, true)
						.hint(SWT.DEFAULT, 600)
						.applyTo(natTable);
				return natTable;
			};
			modelService.put(INJECT_VIEW_VALIDATION_NATTABLE, func);

			// create form content
			final ScrolledComposite viewComposite = new ScrolledComposite(
					parent, SWT.V_SCROLL);
			viewComposite.setLayout(new GridLayout());
			GridDataFactory.fillDefaults()
					.grab(true, true)
					.applyTo(viewComposite);

			createEmfFormsPart(viewComposite, validationReport,
					VIEW_VALIDATION_REPORT);
			viewComposite.setContent(getView().getSWTControl());

			// initial update of button states
			updateButtonStates();

			// Resize the EMF Forms view according to the outside area to update
			// the scroll view size when the window is resized
			resizeListenerObject = parent;
			resizeListener = event -> {
				final Point size = getView().getSWTControl()
						.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				final Rectangle bounds = parent.getBounds();
				// No offset from parent
				bounds.x = 0;
				bounds.y = 0;
				// Keep some width at the right side for the scroll bar
				bounds.width = (int) (bounds.width * 0.98 - 25);
				// Use internal height or the parent height
				bounds.height = (int) Math.max(bounds.height - 25.0,
						size.y * 0.9);

				getView().getSWTControl().setBounds(bounds);
			};
			parent.addListener(SWT.Resize, resizeListener);

			createException = null;
		} catch (final Exception e) {
			createException = e;
		}
	}

	private void storageReport() {
		final Cache cache = cacheService.getCache(ToolboxFileRole.SESSION,
				ToolboxConstants.CacheId.PROBLEM_MESSAGE);
		final List<ProblemMessage> problems = cache.get(VIEW_VALIDATION_REPORT,
				ArrayList::new);
		problems.clear();

		// Add problems
		validationReport.getProblems()
				.stream()
				.filter(problem -> problem
						.getSeverity() != ValidationSeverity.SUCCESS)
				.forEach(problem -> problems
						.add(new ProblemMessage(problem.getMessage(),
								problem.getType(), problem.getLineNumber(), 3,
								problem.getObjectState().getLiteral())));
		getBroker().post(Events.PROBLEMS_CHANGED, null);
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
			storageReport();
			// reset outdated mark
			setOutdated(false);

			// update buttons
			updateButtonStates();

			// update table
			tableView.updateView(validationReport);
		}
	}

	private void updateButtonStates() {
		exportValidationButton
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
		if (resizeListenerObject != null
				&& !resizeListenerObject.isDisposed()) {
			resizeListenerObject.removeListener(SWT.RESIZE, resizeListener);
		}
		this.dispose();
	}
}
