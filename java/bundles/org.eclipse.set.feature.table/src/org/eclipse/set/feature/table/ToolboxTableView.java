/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table;

import static org.eclipse.set.feature.table.abstracttableview.ToolboxTableModelThemeConfiguration.toPixel;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ThreadUtils;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultRowHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupGroupHeaderLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupHeaderLayer;
import org.eclipse.nebula.widgets.nattable.group.ColumnGroupModel;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.SpanningDataLayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.resize.command.RowHeightResetCommand;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.viewport.command.ShowRowInViewportCommand;
import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.constants.ToolboxViewState;
import org.eclipse.set.basis.extensions.MApplicationElementExtensions;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.configurationservice.UserConfigurationService;
import org.eclipse.set.feature.table.abstracttableview.ColumnGroup4HeaderLayer;
import org.eclipse.set.feature.table.abstracttableview.ColumnGroupGroupGroupHeaderLayer;
import org.eclipse.set.feature.table.abstracttableview.NatTableColumnGroupHelper;
import org.eclipse.set.feature.table.abstracttableview.ToolboxTableModelThemeConfiguration;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.feature.table.messages.MessagesWrapper;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions;
import org.eclipse.set.model.tablemodel.extensions.Headings;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.model.titlebox.extensions.TitleboxExtensions;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToFreeFieldTransformation;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToTitleboxTransformation;
import org.eclipse.set.services.export.ExportService;
import org.eclipse.set.services.export.TableCompileService;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.RefreshAction;
import org.eclipse.set.utils.SelectableAction;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.DefaultToolboxEventHandler;
import org.eclipse.set.utils.events.JumpToSiteplanEvent;
import org.eclipse.set.utils.events.JumpToSourceLineEvent;
import org.eclipse.set.utils.events.JumpToTableEvent;
import org.eclipse.set.utils.events.SelectedControlAreaChangedEvent;
import org.eclipse.set.utils.events.TableDataChangeEvent;
import org.eclipse.set.utils.events.ToolboxEventHandler;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.exception.ExceptionHandler;
import org.eclipse.set.utils.table.BodyLayerStack;
import org.eclipse.set.utils.table.Pt1TableChangeProperties;
import org.eclipse.set.utils.table.TableModelInstanceBodyDataProvider;
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;

/**
 * View class for all toolbox table views. This class is responsible for
 * creating the actual nattable with all its layers.
 * 
 * @author rumpf
 */
public final class ToolboxTableView extends BasePart {

	protected static final int DEBUG_WIDTH_CORRECTION = 0;

	static final Logger logger = LoggerFactory
			.getLogger(ToolboxTableView.class);

	private BodyLayerStack bodyLayerStack;

	@Inject
	private TableCompileService compileService;

	@Inject
	private ExportService exportService;
	private NatTable natTable;

	private final List<TableRow> tableInstances = Lists.newLinkedList();

	private ToolboxEventHandler<JumpToTableEvent> tableSelectRowHandler;
	private ToolboxEventHandler<TableDataChangeEvent> tableDataChangeHandler;

	private ToolboxEventHandler<SelectedControlAreaChangedEvent> selectionControlAreaHandler;

	private int scrollToPositionRequested = -1;

	private Text tableFooting;

	@Inject
	@Translation
	Messages messages;

	@Inject
	UISynchronize sync;

	Table table;

	@Inject
	TableService tableService;

	@Inject
	TableMenuService tableMenuService;

	@Inject
	UserConfigurationService userConfigService;

	TableType tableType;

	Set<String> controlAreaIds;

	/**
	 * this injection is only needed to invoke the call of the respective
	 * context function which will lead to registration of the messages as an
	 * osgi service. DON'T DELETE UNLESS YOU KNOW WHAT YOU ARE DOING ;-)
	 */
	@Inject
	MessagesWrapper wrapper;

	private TableModelInstanceBodyDataProvider bodyDataProvider;

	/**
	 * constructor
	 */
	public ToolboxTableView() {
		super();
	}

	@Override
	public TableType getTableType() {
		return tableType;
	}

	private ExportType getExportType() {
		switch (tableType) {
		case FINAL:
			return ExportType.INVENTORY_RECORDS;
		case DIFF:
			return ExportType.PLANNING_RECORDS;
		case SINGLE:
			return ExportType.PLANNING_RECORDS;
		default:
			throw new IllegalArgumentException(tableType.toString());
		}
	}

	private FreeFieldInfo getFreeFieldInfo() {
		final PlanProToFreeFieldTransformation planProToFreeField = PlanProToFreeFieldTransformation
				.create();
		return planProToFreeField.transform(getModelSession());
	}

	private Path getAttachmentPath(final String guid) {
		try {
			return getModelSession().getToolboxFile()
					.getMediaPath(Guid.create(guid));
		} catch (final UnsupportedOperationException e) {
			return null; // .ppxml-Files do not support attachments
		}
	}

	private Titlebox getTitlebox(final String shortcut) {
		final PlanProToTitleboxTransformation planProToTitlebox = PlanProToTitleboxTransformation
				.create();
		final Titlebox titlebox = planProToTitlebox.transform(
				getModelSession().getPlanProSchnittstelle(),
				tableService.getTableNameInfo(shortcut),
				this::getAttachmentPath);
		updateTitlebox(titlebox);
		return titlebox;
	}

	private boolean isDisplayedDataAffected(
			final Container_AttributeGroup container) {
		if (tableType == TableType.DIFF) {
			return true;
		}
		return getModelSession()
				.getContainer(tableType.getContainerForTable()) == container;
	}

	private void outdatedUpdate() {
		if (isOutdated()) {
			updateTableView();
			setOutdated(false);
		}
	}

	@PostConstruct
	private void postConstruct() {
		tableSelectRowHandler = new DefaultToolboxEventHandler<>() {
			@Override
			public void accept(final JumpToTableEvent t) {
				tableSelectRowHandler(t);
			}
		};

		ToolboxEvents.subscribe(getBroker(), JumpToTableEvent.class,
				tableSelectRowHandler);

		tableDataChangeHandler = new DefaultToolboxEventHandler<>() {
			@Override
			public void accept(final TableDataChangeEvent t) {
				if (!t.getProperties().isEmpty() && t.getProperties()
						.getFirst() instanceof Pt1TableChangeProperties) {

					t.getProperties().forEach(ele -> {
						bodyDataProvider.updateContent(tableType,
								(Pt1TableChangeProperties) ele);
					});
					natTable.refresh();
				}
			}
		};
		ToolboxEvents.subscribe(getBroker(), TableDataChangeEvent.class,
				tableDataChangeHandler, TableDataChangeEvent
						.getTopic(getTableShortcut()).toLowerCase());

		selectionControlAreaHandler = new DefaultToolboxEventHandler<>() {
			@Override
			public void accept(final SelectedControlAreaChangedEvent t) {
				controlAreaIds.clear();
				t.getControlAreas()
						.forEach(area -> controlAreaIds.add(area.areaId()));
				tableType = t.getTableType();
				updateTableView();
			}
		};

		ToolboxEvents.subscribe(getBroker(),
				SelectedControlAreaChangedEvent.class,
				selectionControlAreaHandler);
	}

	@PreDestroy
	private void preDestroy() {
		logger.trace("preDestroy"); //$NON-NLS-1$ LOG
		ToolboxEvents.unsubscribe(getBroker(), tableSelectRowHandler);
		ToolboxEvents.unsubscribe(getBroker(), tableDataChangeHandler);
		ToolboxEvents.unsubscribe(getBroker(), selectionControlAreaHandler);
		getBroker().send(Events.CLOSE_PART, getTableShortcut().toLowerCase());
	}

	private String getTableShortcut() {
		return tableService.extractShortcut(getToolboxPart().getElementId());
	}

	private Void showExportEndDialog(final Shell shell) {
		getDialogService().reportExported(shell);
		return null;
	}

	private void tableSelectRowHandler(final JumpToTableEvent event) {
		// IMPROVE: here should be check, if the table is target of jump event.
		// Currently can't check this, because the jump event from siteplan no
		// contain target table.
		// Determine the row by searching for the leading object
		final int rowIndex = TableExtensions
				.getLeadingObjectRowIndexByGUID(table, event.getSearchKey());
		if (rowIndex == -1) {
			return;
		}

		// Select the row
		bodyLayerStack.getSelectionLayer().selectRow(0, rowIndex, false, false);
		scrollToPositionRequested = rowIndex;
	}

	/**
	 * transform the current planpro model to the specific view table model.
	 * 
	 * @param elementId
	 *            the element id of the part
	 * 
	 * @return the table view model
	 */
	private Table transformToTableModel(final String elementId,
			final IModelSession modelSession) {
		return tableService.transformToTable(elementId, tableType, modelSession,
				controlAreaIds);
	}

	private void updateTableView() {
		tableService.updateTable(this, () -> {
			updateModel(getToolboxPart(), getModelSession());
			natTable.doCommand(new RowHeightResetCommand());
			natTable.refresh();
			updateButtons();

			// Update footnotes
			tableFooting.setText(TableExtensions.getFootnotesText(table));
		}, tableInstances::clear);

	}

	private void updateTitlebox(final Titlebox titlebox) {
		if (getExportType() == ExportType.INVENTORY_RECORDS) {
			TitleboxExtensions.set(titlebox,
					TitleboxExtensions.DOC_TYPE_ADDRESS,
					TitleboxExtensions.INVENTORY_RECORDS_DOC_TYPE_SHOTCUT);
		}
	}

	@Override
	protected void createView(final Composite parent) {

		// initialize table type
		tableType = getModelSession().getTableType();
		if (tableType == null) {
			tableType = getModelSession().getNature().getDefaultContainer()
					.getTableTypeForTables();
		}
		controlAreaIds = getModelSession().getSelectedControlAreas().stream()
				.map(Pair::getSecond).collect(Collectors.toSet());

		tableService.updateTable(this,
				() -> updateModel(getToolboxPart(), getModelSession()),
				tableInstances::clear);

		// if the table was not created (possibly the creation was canceled by
		// the user), we stop here with creating the view
		if (table == null) {
			return;
		}

		final ColumnDescriptor rootColumnDescriptor = table
				.getColumndescriptors().get(0);

		if (logger.isDebugEnabled()) {
			// PLANPRO-1916: create time-expensive debug text only in this case
			logger.info(Headings.getTreeString(rootColumnDescriptor));
			logger.info(
					Headings.getWidthTestCsv(rootColumnDescriptor, input -> {
						if (input == null) {
							return Integer.valueOf(0);
						}
						return Integer.valueOf(toPixel(input.floatValue())
								+ DEBUG_WIDTH_CORRECTION);
					}));
		}

		// Body layer stack
		// create the bodydataProvider. this cannot happen in the constructor as
		// the abstract constructor is called before the subclass constructor
		// is called
		Assert.isNotNull(tableInstances);
		bodyDataProvider = new TableModelInstanceBodyDataProvider(
				TableExtensions.getPropertyCount(table), tableInstances);
		final SpanningDataLayer bodyDataLayer = new SpanningDataLayer(
				bodyDataProvider);

		bodyLayerStack = new BodyLayerStack(bodyDataLayer);

		bodyLayerStack.freezeColumns(
				tableService.getFixedColumns(getToolboxPart().getElementId()));

		final SelectionLayer selectionLayer = bodyLayerStack
				.getSelectionLayer();
		// selectionLayer.addConfiguration(
		// new RowSelectionListener(getToolboxPart().getElementId(),
		// selectionLayer, tableInstances, getBroker()));

		// column header stack
		final IDataProvider columnHeaderDataProvider = new DefaultColumnHeaderDataProvider(
				ColumnDescriptorExtensions
						.getColumnLabels(rootColumnDescriptor));
		final DataLayer columnHeaderDataLayer = new DataLayer(
				columnHeaderDataProvider);
		final ColumnHeaderLayer columnHeaderLayer = new ColumnHeaderLayer(
				columnHeaderDataLayer, bodyLayerStack,
				bodyLayerStack.getSelectionLayer());

		// column groups
		final ColumnGroupModel columnGroupModel = new ColumnGroupModel();
		final ColumnGroupHeaderLayer columnGroupHeaderLayer = new ColumnGroupHeaderLayer(
				columnHeaderLayer, bodyLayerStack.getSelectionLayer(),
				columnGroupModel);
		NatTableColumnGroupHelper.addGroups(rootColumnDescriptor,
				columnGroupHeaderLayer);
		columnGroupHeaderLayer
				.setRowHeight(toPixel((float) ColumnDescriptorExtensions
						.getGroupRowHeight(rootColumnDescriptor)));

		// column group groups
		final ColumnGroupModel columnGroupGroupModel = new ColumnGroupModel();
		final ColumnGroupGroupHeaderLayer columnGroupGroupHeaderLayer = new ColumnGroupGroupHeaderLayer(
				columnGroupHeaderLayer, columnGroupGroupModel);
		NatTableColumnGroupHelper.addGroupGroups(rootColumnDescriptor,
				columnGroupGroupHeaderLayer);
		columnGroupGroupHeaderLayer
				.setRowHeight(toPixel((float) ColumnDescriptorExtensions
						.getGroupGroupRowHeight(rootColumnDescriptor)));

		// column group group groups
		final ColumnGroupModel columnGroupGroupGroupModel = new ColumnGroupModel();
		final ColumnGroupGroupGroupHeaderLayer columnGroupGroupGroupHeaderLayer = new ColumnGroupGroupGroupHeaderLayer(
				columnGroupGroupHeaderLayer, columnGroupHeaderLayer,
				bodyLayerStack.getSelectionLayer(), columnGroupGroupGroupModel);
		NatTableColumnGroupHelper.addGroupGroupGroups(rootColumnDescriptor,
				columnGroupGroupGroupHeaderLayer);
		columnGroupGroupGroupHeaderLayer
				.setRowHeight(toPixel((float) ColumnDescriptorExtensions
						.getGroupGroupGroupRowHeight(rootColumnDescriptor)));

		// column group4
		final ColumnGroupModel columnGroup4Model = new ColumnGroupModel();
		final ColumnGroup4HeaderLayer columnGroup4HeaderLayer = new ColumnGroup4HeaderLayer(
				columnGroupGroupGroupHeaderLayer, columnGroupGroupHeaderLayer,
				columnGroupHeaderLayer, bodyLayerStack.getSelectionLayer(),
				columnGroup4Model);
		NatTableColumnGroupHelper.addColumnNumbers(rootColumnDescriptor,
				columnGroup4HeaderLayer);
		columnGroup4HeaderLayer
				.setRowHeight(toPixel((float) ColumnDescriptorExtensions
						.getGroup4RowHeight(rootColumnDescriptor)));

		// row header stack
		final IDataProvider rowHeaderDataProvider = new DefaultRowHeaderDataProvider(
				bodyDataProvider);
		final DataLayer rowHeaderDataLayer = new DataLayer(
				rowHeaderDataProvider, 40, 20);
		final RowHeaderLayer rowHeaderLayer = new RowHeaderLayer(
				rowHeaderDataLayer, bodyLayerStack,
				bodyLayerStack.getSelectionLayer());

		// Corner Layer stack
		final DefaultCornerDataProvider cornerDataProvider = new DefaultCornerDataProvider(
				columnHeaderDataProvider, rowHeaderDataProvider);
		final DataLayer cornerDataLayer = new DataLayer(cornerDataProvider);
		final CornerLayer cornerLayer = new CornerLayer(cornerDataLayer,
				rowHeaderLayer, columnGroup4HeaderLayer);

		// gridlayer
		final GridLayer gridLayer = new GridLayer(bodyLayerStack,
				columnGroup4HeaderLayer, rowHeaderLayer, cornerLayer);
		natTable = new NatTable(parent, gridLayer, false);
		GridDataFactory.fillDefaults().grab(true, true).minSize(-1, 500)
				.applyTo(natTable);

		addMenuItems();
		natTable.addConfiguration(tableMenuService
				.createMenuConfiguration(natTable, selectionLayer));

		natTable.configure();
		// set style
		natTable.setTheme(new ToolboxTableModelThemeConfiguration(natTable,
				columnHeaderDataLayer, bodyDataLayer, gridLayer,
				rootColumnDescriptor, bodyLayerStack, bodyDataProvider,
				getDialogService()));

		bodyLayerStack.getSelectionLayer().clear();

		// display footnotes
		tableFooting = new Text(parent, SWT.MULTI);
		tableFooting.setText(TableExtensions.getFootnotesText(table));
		tableFooting.setEditable(false);
		GridDataFactory.fillDefaults().grab(true, false).minSize(-1, 500)
				.applyTo(tableFooting);

		// export action
		getBanderole().setExportAction(new SelectableAction() {
			@Override
			public String getText() {
				return messages.ToolboxTableView_Export;
			}

			@Override
			public void selected(final SelectionEvent e) {
				export();
			}
		});

		// update buttons
		final CommandStackListener commandStackListener = event -> sync
				.syncExec(this::updateButtons);
		getModelSession().getEditingDomain().getCommandStack()
				.addCommandStackListener(commandStackListener);

		natTable.addDisposeListener(
				event -> getModelSession().getEditingDomain().getCommandStack()
						.removeCommandStackListener(commandStackListener));

		natTable.addPaintListener(e -> {
			if (scrollToPositionRequested != -1) {
				natTable.doCommand(new ShowRowInViewportCommand(
						scrollToPositionRequested));
				scrollToPositionRequested = -1;
			}
		});

		updateButtons();
	}

	@Override
	protected SelectableAction getOutdatedAction() {
		return new RefreshAction(this, e -> outdatedUpdate());
	}

	@Override
	protected void handleContainerDataChanged(final ContainerDataChanged e) {
		if (isDisplayedDataAffected(e.getContainer())) {
			setOutdated(true);
		}
	}

	@Override
	protected void updateViewContainerDataChanged(
			final List<Container_AttributeGroup> container) {
		outdatedUpdate();
	}

	void export() {
		final String shortcut = getTableShortcut();
		final List<Thread> transformatorThreads = ThreadUtils.getAllThreads()
				.stream()
				.filter(t -> t != null
						&& t.getName().startsWith(shortcut.toLowerCase())
						&& t.isAlive())
				.toList();
		if (!transformatorThreads.isEmpty() && !getDialogService()
				.confirmExportNotCompleteTable(getToolboxShell())) {
			return;
		}
		final Map<TableType, Table> tables = compileService.compile(shortcut,
				getModelSession(), controlAreaIds);
		final Optional<String> optionalOutputDir = getDialogService()
				.selectDirectory(getToolboxShell(),
						userConfigService.getLastExportPath().toString());
		try {
			getDialogService().showProgress(getToolboxShell(),
					monitor -> optionalOutputDir.ifPresent(outputDir -> {
						monitor.beginTask(messages.ToolboxTableView_ExportTable,
								IProgressMonitor.UNKNOWN);
						exportService.export(tables, getExportType(),
								getTitlebox(shortcut), getFreeFieldInfo(),
								shortcut, outputDir,
								getModelSession().getToolboxPaths(),
								getModelSession().getTableType(),
								OverwriteHandling
										.forUserConfirmation(path -> Boolean
												.valueOf(getDialogService()
														.confirmOverwrite(
																getToolboxShell(),
																path))),
								new ExceptionHandler(getToolboxShell(),
										getDialogService()));
					}));
			optionalOutputDir.ifPresent(outputDir -> {
				getDialogService().openDirectoryAfterExport(getToolboxShell(),
						Path.of(outputDir));
				userConfigService.setLastExportPath(Path.of(outputDir));
			});
		} catch (InvocationTargetException | InterruptedException e) {
			Thread.currentThread().interrupt();
			getDialogService().error(getToolboxShell(), e);
		}
	}

	void updateButtons() {
		getBanderole().setEnableExport(getTableType() != TableType.INITIAL
				&& !getModelSession().isDirty());
	}

	void updateModel(final MPart part, final IModelSession modelSession) {
		// update banderole
		getBanderole().setTableType(tableType);
		table = transformToTableModel(part.getElementId(), modelSession);
		// flag creation
		MApplicationElementExtensions.setViewState(part,
				ToolboxViewState.CREATED);

		tableInstances.clear();
		tableInstances.addAll(TableExtensions.getTableRows(table));

	}

	private void addMenuItems() {
		tableMenuService.addMenuItem(tableMenuService.createShowInTextViewItem(
				createJumpToSourceLineEvent(),
				bodyLayerStack.getSelectionLayer(),
				rowPosition -> getRowReferenceObjectGuid(rowPosition) != null));
		tableMenuService.addMenuItem(tableMenuService.createShowInSitePlanItem(
				createJumpToSiteplanEvent(), bodyLayerStack.getSelectionLayer(),
				rowPosition -> {
					final List<TableRow> tableRows = TableExtensions
							.getTableRows(table);
					final Ur_Objekt object = TableRowExtensions
							.getLeadingObject(tableRows.get(rowPosition));
					return object != null && Services.getSiteplanService()
							.isSiteplanElement(object);
				}));
	}

	protected JumpToSourceLineEvent createJumpToSourceLineEvent() {
		return new JumpToSourceLineEvent(this) {
			@Override
			public String getObjectGuid() {
				final Collection<ILayerCell> selectedCells = bodyLayerStack
						.getSelectionLayer().getSelectedCells();
				if (selectedCells.isEmpty()) {
					return null;
				}
				final int rowPosition = selectedCells.iterator().next()
						.getRowPosition();
				final List<TableRow> tableRows = TableExtensions
						.getTableRows(table);
				return TableRowExtensions
						.getLeadingObjectGuid(tableRows.get(rowPosition));
			}
		};
	}

	protected JumpToSiteplanEvent createJumpToSiteplanEvent() {
		return new JumpToSiteplanEvent(getToolboxPart().getElementId()) {
			@Override
			public TableRow getRow() {
				final Collection<ILayerCell> selectedCells = bodyLayerStack
						.getSelectionLayer().getSelectedCells();
				if (selectedCells.isEmpty()) {
					return null;
				}
				final int rowPosition = selectedCells.iterator().next()
						.getRowPosition();
				final List<TableRow> tableRows = TableExtensions
						.getTableRows(table);
				return tableRows.get(rowPosition);
			}
		};
	}

	private String getRowReferenceObjectGuid(final int rowPosition) {
		final List<TableRow> tableRows = TableExtensions.getTableRows(table);
		return TableRowExtensions
				.getLeadingObjectGuid(tableRows.get(rowPosition));
	}

}
