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
import java.util.function.Consumer;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.export.CheckBoxTreeElement;
import org.eclipse.set.basis.export.CheckboxModelElement;
import org.eclipse.set.basis.extensions.Exceptions;
import org.eclipse.set.basis.threads.Threads;
import org.eclipse.set.core.services.configurationservice.UserConfigurationService;
import org.eclipse.set.feature.export.Messages;
import org.eclipse.set.feature.export.checkboxmodel.CheckBoxTreeModelProvider;
import org.eclipse.set.feature.export.checkboxmodel.CheckboxTreeModel;
import org.eclipse.set.services.export.ExportService;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.SaveAndRefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.events.SessionDirtyChanged;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
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
	ExportService exportService;

	@Inject
	@Translation
	Messages messages;

	@Inject
	@Translation
	org.eclipse.set.utils.Messages utilMessages;

	CheckboxTreeViewer viewer;

	private CheckboxTreeModel treeDataModel;
	private Composite buttonBar;

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

	protected CheckboxTreeViewer getViewer() {
		return viewer;
	}

	protected Composite getButtonBar() {
		return buttonBar;
	}

	protected ExportService getExportService() {
		return exportService;
	}

	/**
	 * @return {@link CheckboxTreeModel}
	 */
	public CheckboxTreeModel getTreeDataModel() {
		return treeDataModel;
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
		viewer = new CheckboxTreeViewer(section,
				SWT.V_SCROLL | SWT.H_SCROLL | SWT.BORDER);
		viewer.setComparator(new ViewerComparator());
		viewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(final Object element) {
				final CheckboxModelElement ele = (CheckboxModelElement) element;
				return ele.toString();
			}
		});
		viewer.setContentProvider(new CheckBoxTreeModelProvider());
		treeDataModel = createTreeModelData();
		viewer.setInput(treeDataModel);
		treeDataModel.selectAll();
		viewer.setCheckedElements(treeDataModel.getChecked());
		viewer.expandAll();
		createSelectButtonGroup(section);
		viewer.setCheckStateProvider(checkStateProvider());
		viewer.addCheckStateListener(checkStateListerner());

		GridDataFactory.fillDefaults()
				.align(SWT.FILL, SWT.FILL)
				.grab(true, true)
				.applyTo(viewer.getTree());
	}

	protected ICheckStateListener checkStateListerner() {
		return event -> {
			if (event
					.getElement() instanceof final CheckBoxTreeElement treeElement) {
				if (event.getChecked()) {
					treeElement.select();
				} else {
					treeElement.deselect();
				}

				viewer.update(treeElement, null);
				validateExportButton();
			}
		};
	}

	protected ICheckStateProvider checkStateProvider() {
		return new ICheckStateProvider() {

			@Override
			public boolean isGrayed(final Object element) {
				return false;
			}

			@Override
			public boolean isChecked(final Object element) {
				if (element instanceof final CheckBoxTreeElement treeElement) {
					if (treeElement.isParent()) {
						viewer.setSubtreeChecked(treeElement,
								treeElement.isChecked());
					} else {
						final boolean isAllChildsSameStatus = treeElement
								.getParent()
								.getChildElements()
								.stream()
								.allMatch(ele -> ele.isChecked() == treeElement
										.isChecked());
						final boolean isParentChecked = viewer
								.getChecked(treeElement.getParent());
						if (isAllChildsSameStatus
								&& isParentChecked != treeElement.isChecked()
								|| !isAllChildsSameStatus && isParentChecked) {
							viewer.setChecked(treeElement.getParent(),
									treeElement.getParent().isChecked());
						}

					}
					return treeElement.isChecked();
				}
				return false;
			}
		};
	}

	protected void createSelectButtonGroup(final Composite parent) {
		buttonBar = new Composite(parent, SWT.BOTTOM);
		final FillLayout fillLayout = new FillLayout(SWT.VERTICAL);
		fillLayout.spacing = 2;

		buttonBar.setLayout(fillLayout);
		// Create select all button
		createSelectButton(buttonBar, messages.selectAll, treeDataModel,
				CheckboxTreeModel::selectAll);

		// Create deselected all button
		createSelectButton(buttonBar, messages.selectNone, treeDataModel,
				CheckboxTreeModel::deselectAll);
		GridDataFactory.fillDefaults()
				.align(SWT.FILL, SWT.TOP)
				.applyTo(buttonBar);
	}

	protected Button createSelectButton(final Composite parent,
			final String buttonText, final CheckboxTreeModel treeModel,
			final Consumer<CheckboxTreeModel> modelAction) {
		final Button button = new Button(parent, SWT.PUSH);
		button.setText(buttonText);
		button.addListener(SWT.Selection, event -> {
			modelAction.accept(treeModel);
			viewer.setCheckedElements(treeModel.getChecked());
			validateExportButton();
		});
		return button;
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
		buttonSelectDir.addListener(SWT.Selection,
				event -> getDialogService()
						.selectDirectory(parent.getShell(),
								getSelectedDirectory().toString())
						.ifPresent(selectedName -> {
							final Path selectedPath = Paths.get(selectedName);

							final Path dir = selectedPath;

							selectedDir = dir;
							directoryPath
									.setText(getSelectedDirectory().toString());
							userConfigService.setLastExportPath(selectedDir);
						}));

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

	protected abstract CheckboxTreeModel createTreeModelData();

	@Override
	protected void createView(final Composite parent) {
		selectedDir = userConfigService.getLastExportPath();
		parent.setLayout(new FillLayout());
		final ScrolledComposite scrolledComposite = new ScrolledComposite(
				parent, SWT.V_SCROLL);
		scrolledComposite.setLayout(new GridLayout());
		GridDataFactory.fillDefaults()
				.grab(true, true)
				.applyTo(scrolledComposite);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setAlwaysShowScrollBars(false);

		isSessionDirty = getModelSession().isDirty();
		final Composite content = new Composite(scrolledComposite, SWT.NONE);
		content.setLayout(new GridLayout(1, false));
		GridDataFactory.fillDefaults().grab(true, true).applyTo(content);
		scrolledComposite.setContent(content);
		createExportListSection(content);
		createExportSection(content);
		exportButton = new Button(content, SWT.PUSH);
		exportButton.setText(getExportButtonText());
		exportButton.addListener(SWT.Selection,
				event -> startExport(parent.getShell(), getModelSession()));

		scrolledComposite
				.setMinSize(content.computeSize(SWT.DEFAULT, SWT.DEFAULT));
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
