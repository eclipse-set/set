/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.export;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.export.CheckBoxTreeElement;
import org.eclipse.set.basis.export.CheckboxModelElement;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.core.services.export.AdditionalExportService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.feature.export.checkboxmodel.CheckboxTreeModel;
import org.eclipse.set.feature.export.parts.DocumentExportPart;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToFreeFieldTransformation;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToTitleboxTransformation;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.services.export.TableCompileService;
import org.eclipse.set.services.table.TableService;
import org.eclipse.set.utils.SaveAndRefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.DefaultToolboxEventHandler;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.events.SelectedControlAreaChangedEvent;
import org.eclipse.set.utils.events.ToolboxEventHandler;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.exception.ExceptionHandler;
import org.eclipse.set.utils.table.TableInfo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;

/**
 * Viewpart for the export of documents.
 * 
 * @author rumpf
 */
public abstract class PlanProExportPart extends DocumentExportPart {

	protected static final Logger logger = LoggerFactory
			.getLogger(PlanProExportPart.class);
	protected static final String EMPTY_TABLE = "leer"; //$NON-NLS-1$
	@Inject
	@Translation
	protected Messages messages;

	@Inject
	@Optional
	private AdditionalExportService<CheckBoxTreeElement> additionalExportService;

	@Inject
	ToolboxPartService toolboxPartService;

	@Inject
	TableService tableService;

	@Inject
	TableCompileService compileService;

	private ToolboxEventHandler<SelectedControlAreaChangedEvent> selectionControlAreaHandler;

	/**
	 * creates the part.
	 */
	@Inject
	public PlanProExportPart() {
		super();
	}

	@PostConstruct
	private void postConstruct() {
		final Set<String> currentAreaIds = getModelSession()
				.getSelectedControlAreas()
				.stream()
				.map(Pair::getSecond)
				.collect(Collectors.toSet());
		updateTreeElements(currentAreaIds);
		selectionControlAreaHandler = new DefaultToolboxEventHandler<>() {
			@Override
			public void accept(final SelectedControlAreaChangedEvent t) {
				final Set<String> areaIds = t.getControlAreas()
						.stream()
						.map(area -> area.areaId())
						.collect(Collectors.toSet());
				updateTreeElements(areaIds);
			}
		};

		ToolboxEvents.subscribe(getBroker(),
				SelectedControlAreaChangedEvent.class,
				selectionControlAreaHandler);
	}

	@Override
	public CheckboxTreeModel getTreeDataModel() {
		if (super.getTreeDataModel() instanceof final TableCheckboxTreeModel tableTreeModel) {
			return tableTreeModel;
		}
		throw new IllegalArgumentException();
	}

	protected void updateTreeElements(final Set<String> areaIds) {
		final Set<TableInfo> avaibleTables = new HashSet<>(
				tableService.getAvailableTables());
		final TableType tableType = getModelSession().getTableType();
		if (!(getTreeDataModel() instanceof TableCheckboxTreeModel)) {
			throw new IllegalArgumentException();
		}
		final TableCheckboxTreeModel treeDataModel = (TableCheckboxTreeModel) getTreeDataModel();
		try {
			getDialogService().showProgress(getToolboxShell(), monitor -> {
				logger.debug("Start update tree elements"); //$NON-NLS-1$
				final Map<TableInfo, Table> pt1Tables = tableService
						.transformTables(monitor, getModelSession(),
								avaibleTables, tableType, areaIds);
				Display.getDefault().asyncExec(() -> {
					pt1Tables.forEach((tableInfo, table) -> {
						CheckBoxTreeElement element = treeDataModel
								.getElement(tableInfo)
								.orElse(null);
						if (element == null) {
							element = treeDataModel.addElement(tableInfo);
						}
						if (TableExtensions.isTableEmpty(table)) {
							element.setStatus(EMPTY_TABLE);
							// By default not select the empty table
							element.deselect();
						} else {
							element.setStatus(null);
							element.select();
						}
						getViewer().update(element, null);
					});
					validateExportButton();
				});

			});
		} catch (final Exception e) {
			getDialogService().error(getToolboxShell(), e);
		}
	}

	@PreDestroy
	private void preDestroy() {
		logger.trace("preDestroy"); //$NON-NLS-1$ LOG
		ToolboxEvents.unsubscribe(getBroker(), selectionControlAreaHandler);
	}

	@Override
	protected CheckboxTreeModel createTreeModelData() {
		final List<CheckBoxTreeElement> elements = new ArrayList<>();
		final Collection<TableInfo> availableTables = tableService
				.getAvailableTables();

		availableTables.forEach(tableInfo -> {
			final TableNameInfo nameInfo = tableService
					.getTableNameInfo(tableInfo.shortcut());
			CheckBoxTreeElement parentElement = elements.stream()
					.filter(ele -> ele.getId()
							.equals(tableInfo.category().getId()))
					.findFirst()
					.orElse(null);
			if (parentElement == null) {
				parentElement = new CheckBoxTreeElement(
						tableInfo.category().getId(),
						tableInfo.category().toString());
				elements.add(parentElement);
			}
			parentElement.addChild(new CheckBoxTreeElement(
					nameInfo.getShortName().toLowerCase(),
					nameInfo.getFullDisplayName()));
		});

		if (additionalExportService != null) {
			additionalExportService
					.createAdditionalCheckboxModelElements(elements);
		}

		return new TableCheckboxTreeModel(elements, tableService);
	}

	@Override
	protected void createSelectButtonGroup(final Composite parent) {
		super.createSelectButtonGroup(parent);
		// Create select all without empty table button
		createSelectButton(getButtonBar(),
				messages.TableExportPart_FilterEmptyButton, getTreeDataModel(),
				model -> Arrays.stream(model.getAllElements())
						.filter(ele -> ele.getStatus() != null
								&& ele.getStatus().equals(EMPTY_TABLE))
						.forEach(ele -> ele.deselect()));
	}

	private Path getAttachmentPath(final String guid) {
		try {
			return getModelSession().getToolboxFile()
					.getMediaPath(Guid.create(guid));
		} catch (final UnsupportedOperationException e) {
			return null; // .ppxml-Files do not support attachments
		}
	}

	@Override
	protected void export(final CheckboxModelElement element,
			final IModelSession modelSession,
			final OverwriteHandling overwriteHandling,
			final IProgressMonitor monitor) {
		final String id = element.getId();
		// Skip table category element
		if (TableInfo.Pt1TableCategory.getCategoryEnum(id) != null) {
			return;
		}

		if (additionalExportService != null
				&& additionalExportService.isAdditionalExportId(id)) {
			additionalExportService.createAdditionalExport(id, modelSession,
					monitor, getSelectedDirectory(), getExportType(),
					overwriteHandling);
		} else {
			final Map<TableType, Table> tables = compileService.compile(id,
					modelSession,
					modelSession.getSelectedControlAreas()
							.stream()
							.map(Pair::getSecond)
							.collect(Collectors.toSet()));
			final PlanProToTitleboxTransformation planProToTitlebox = new PlanProToTitleboxTransformation(
					getSessionService());
			final Titlebox titlebox = planProToTitlebox.transform(
					tableService.getTableNameInfo(id), this::getAttachmentPath);
			updateTitlebox(titlebox);
			final PlanProToFreeFieldTransformation planProToFreeField = PlanProToFreeFieldTransformation
					.create();
			final FreeFieldInfo freeFieldInfo = planProToFreeField
					.transform(modelSession);
			getExportService().exportPdf(tables, getExportType(), titlebox,
					freeFieldInfo, id, getSelectedDirectory().toString(),
					modelSession.getToolboxPaths(), getTableType(),
					overwriteHandling, new ExceptionHandler(getToolboxShell(),
							getDialogService()));
		}
	}

	protected abstract ExportType getExportType();

	@Override
	protected SelectableAction getOutdatedAction() {
		return new SaveAndRefreshAction(this);
	}

	@Override
	protected String getTaskMessage() {
		return messages.TableExportPart_TaskMsg;
	}

	@Override
	protected void handleContainerDataChanged(final ContainerDataChanged e) {
		setOutdated(true);
	}

	@Override
	protected void handleProjectDataChanged(final ProjectDataChanged e) {
		setOutdated(true);
	}

	protected abstract void updateTitlebox(Titlebox titlebox);

	@Override
	protected void updateViewContainerDataChanged(
			final List<Container_AttributeGroup> container) {
		setOutdated(false);
	}

	@Override
	protected void updateViewProjectDataChanged(
			final List<Notification> notifications) {
		setOutdated(false);
	}
}
