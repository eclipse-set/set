/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.parts;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.extensions.Exceptions;
import org.eclipse.set.basis.threads.Threads;
import org.eclipse.set.core.services.configurationservice.UserConfigurationService;
import org.eclipse.set.core.services.export.CheckboxModelElement;
import org.eclipse.set.feature.export.Messages;
import org.eclipse.set.feature.export.checkboxmodel.CheckboxModel;
import org.eclipse.set.feature.export.checkboxmodel.CheckboxModelContentProvider;
import org.eclipse.set.feature.table.TableService;
import org.eclipse.set.feature.table.messages.MessagesWrapper;
import org.eclipse.set.services.export.ExportService;
import org.eclipse.set.services.export.TableCompileService;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.SaveAndRefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.events.SessionDirtyChanged;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;

/**
 * Viewpart for the export of documents.
 * 
 * @author rumpf
 */
public abstract class DocumentExportPart extends BasePart {

	private static final int SECTION_FONT_HEIGHT = 16;

	static final Logger logger = LoggerFactory
			.getLogger(DocumentExportPart.class);

	private Text directoryPath;

	private Button exportButton;
	private boolean isSessionDirty;
	private Path selectedDir;

	@Inject
	UserConfigurationService userConfigService;

	Button checkOverrideButton;

	@Inject
	TableCompileService compileService;

	@Inject
	ExportService exportService;

	@Inject
	@Translation
	Messages messages;

	@Inject
	TableService tableService;

	@Inject
	@Translation
	org.eclipse.set.utils.Messages utilMessages;

	CheckboxTableViewer viewer;

	@Inject
	MessagesWrapper wrapper;

	/**
	 * Constructor
	 */
	protected DocumentExportPart() {
		super();
	}

	/**
	 * @return the export button
	 */
	public Button getExportButton() {
		return exportButton;
	}

	private void createExportListSection(final Composite parent) {
		final Label label = new Label(parent, SWT.NONE);
		label.setText(getDescription());
		final GridData layoutData = new GridData();
		layoutData.horizontalSpan = 2;
		label.setLayoutData(layoutData);

		final Composite section = createSection(parent, messages.chose_export,
				2);

		final Label sectionLabel = new Label(section, SWT.NONE);
		sectionLabel.setText(messages.chose_export_detail);
		final GridData layoutDataSectionLabel = new GridData();
		layoutDataSectionLabel.horizontalSpan = 2;
		sectionLabel.setLayoutData(layoutDataSectionLabel);

		viewer = CheckboxTableViewer.newCheckList(section,
				SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				final CheckboxModelElement ele = (CheckboxModelElement) element;
				return ele.getName();
			}
		});
		viewer.setContentProvider(new CheckboxModelContentProvider());

		final CheckboxModelElement[] elements = createCheckboxModelElements();
		final CheckboxModel input = new CheckboxModel(elements);
		viewer.setInput(input);
		input.selectAll();
		viewer.setCheckedElements(input.getChecked());

		final Composite buttonBar = new Composite(section, SWT.BOTTOM);
		final FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 2;

		buttonBar.setLayout(fillLayout);

		final Button selectAllButton = new Button(buttonBar, SWT.PUSH);
		selectAllButton.setText(messages.selectAll);
		selectAllButton.addListener(SWT.Selection, event -> {
			input.selectAll();
			viewer.setCheckedElements(input.getChecked());
			validateExportButton();
		});

		final Button selectNoneButton = new Button(buttonBar, SWT.PUSH);
		selectNoneButton.setText(messages.selectNone);
		selectNoneButton.addListener(SWT.Selection, event -> {
			input.deselectAll();
			viewer.setCheckedElements(input.getChecked());
			validateExportButton();
		});
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP)
				.applyTo(buttonBar);
		viewer.addCheckStateListener(event -> {
			final CheckboxModelElement element = (CheckboxModelElement) event
					.getElement();
			if (event.getChecked()) {
				element.select();
			} else {
				element.deselect();
			}
			viewer.update(element, null);
			validateExportButton();
		});

		viewer.addCheckStateListener(event -> {
			final CheckboxModelElement element = (CheckboxModelElement) event
					.getElement();
			if (event.getChecked()) {
				element.select();
			} else {
				element.deselect();
			}
			viewer.update(element, null);
			validateExportButton();
		});

		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL)
				.grab(true, true).applyTo(viewer.getTable());
	}

	private void createExportSection(final Composite parent) {
		final Composite section = createSection(parent,
				messages.export_settings, 3);

		final Label label = new Label(section, SWT.NONE);
		label.setText(messages.output_directory);
		final GridData gridData = new GridData();
		gridData.horizontalSpan = 3;
		label.setLayoutData(gridData);

		directoryPath = new Text(section, SWT.NONE);
		final GridData gridData2 = new GridData(SWT.FILL, SWT.CENTER, true,
				false);
		gridData2.horizontalSpan = 2;
		directoryPath.setLayoutData(gridData2);
		directoryPath.setText(getSelectedDirectory().toString());
		directoryPath.setEditable(false);
		final Button buttonSelectDir = new Button(section, SWT.PUSH);
		buttonSelectDir.setText(messages.chose_directory_button);
		buttonSelectDir.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(final Event event) {
				getDialogService()
						.selectDirectory(parent.getShell(),
								getSelectedDirectory().toString())
						.ifPresent(selectedName -> {
							final Path selectedPath = Paths.get(selectedName);

							final Path dir = selectedPath;

							selectedDir = dir;
							directoryPath
									.setText(getSelectedDirectory().toString());
							userConfigService.setLastExportPath(selectedDir);
						});
			}
		});

		// check override
		checkOverrideButton = new Button(section, SWT.CHECK);
		checkOverrideButton.setSelection(true);
		final Label checkOverrideLabel = new Label(section, SWT.NONE);
		checkOverrideLabel
				.setText(messages.DocumentExportPart_checkOverrideLabel);
	}

	private Composite createSection(final Composite parent,
			final String headingText, final int columns) {
		// ensure a not-negative value not greater than quarter of font height
		final int step = Math.min(0, SECTION_FONT_HEIGHT / 4);
		final Group section = new Group(parent, SWT.NONE);
		section.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		section.setLayout(new GridLayout(columns, false));
		final FontDescriptor descriptor = FontDescriptor
				.createFrom(section.getFont())
				.setHeight(SECTION_FONT_HEIGHT - 2 * step);
		final Font font = localResourceManager.create(descriptor);
		section.setFont(font);
		section.setText(headingText);
		return section;
	}

	private void startExport(final Shell shell,
			final IModelSession modelSession) {
		final OverwriteHandling overwriteHandling = OverwriteHandling
				.forCheckbox(checkOverrideButton.getSelection());
		final Object[] checkedElements = viewer.getCheckedElements();
		// runnable for the transformation
		final IRunnableWithProgress exportThread = new IRunnableWithProgress() {
			@Override
			public void run(final IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				// start a single task with unknown timeframe
				monitor.beginTask(getTaskMessage(), IProgressMonitor.UNKNOWN);

				// listen to cancel
				Threads.stopCurrentOnCancel(monitor);

				for (final Object entry : checkedElements) {
					final CheckboxModelElement ele = (CheckboxModelElement) entry;
					export(ele, modelSession, overwriteHandling, monitor);
				}

				// stop progress
				monitor.done();
			}
		};
		final ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(
				shell);
		try {
			progressMonitorDialog.run(true, true, exportThread);
		} catch (final Exception e) {
			if (!Exceptions.isCausedByThreadDeath(e)) {
				getDialogService().error(shell, e);
			}
		}

		if (!progressMonitorDialog.getProgressMonitor().isCanceled()) {
			// export finished
			getDialogService().openDirectoryAfterExport(getToolboxShell(),
					getSelectedDirectory());
			userConfigService.setLastExportPath(getSelectedDirectory());
		}
	}

	protected abstract CheckboxModelElement[] createCheckboxModelElements();

	@Override
	protected void createView(final Composite parent) {
		selectedDir = userConfigService.getLastExportPath();
		parent.setLayout(new FillLayout());
		final Composite content = new Composite(parent, SWT.NONE);
		content.setLayout(new GridLayout(1, false));

		isSessionDirty = getModelSession().isDirty();

		createExportListSection(content);
		createExportSection(content);
		exportButton = new Button(content, SWT.PUSH);
		exportButton.setText(getExportButtonText());
		exportButton.addListener(SWT.Selection,
				event -> startExport(parent.getShell(), getModelSession()));
		validateExportButton();
		setOutdated(getModelSession().isDirty());
	}

	protected abstract void export(CheckboxModelElement element,
			IModelSession modelSession, OverwriteHandling overwriteHandling,
			IProgressMonitor monitor);

	protected abstract String getDescription();

	protected abstract String getExportButtonText();

	@Override
	protected SelectableAction getOutdatedAction() {
		return new SaveAndRefreshAction(this);
	}

	protected Path getSelectedDirectory() {
		return selectedDir;
	}

	protected abstract String getTaskMessage();

	@Override
	protected void handleSessionDirtyEvent(final SessionDirtyChanged e) {
		isSessionDirty = e.isDirty();
		validateExportButton();
		setOutdated(isSessionDirty);
	}

	protected void validateExportButton() {
		exportButton.setEnabled(
				viewer.getCheckedElements().length > 0 && !isSessionDirty);
	}
}
