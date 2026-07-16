/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.overview;

import static org.eclipse.set.basis.constants.ToolboxConstants.*;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.extensions.Exceptions;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.basis.threads.Threads;
import org.eclipse.set.core.services.configurationservice.UserConfigurationService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.feature.table.internal.TableServiceUtils;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.feature.table.overview.TableStatusGroup.TableSectionControl;
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToFreeFieldTransformation;
import org.eclipse.set.ppmodel.extensions.utils.PlanProToTitleboxTransformation;
import org.eclipse.set.services.export.ExportService;
import org.eclipse.set.services.export.TableCompileService;
import org.eclipse.set.services.table.TableService;
import org.eclipse.set.services.table.TableStatus;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.events.ContainerDataChanged;
import org.eclipse.set.utils.events.DefaultToolboxEventHandler;
import org.eclipse.set.utils.events.ProjectDataChanged;
import org.eclipse.set.utils.events.SelectedControlAreaChangedEvent;
import org.eclipse.set.utils.events.SelectedControlAreaChangedEvent.ControlAreaValue;
import org.eclipse.set.utils.events.ToolboxEventHandler;
import org.eclipse.set.utils.events.ToolboxEvents;
import org.eclipse.set.utils.exception.ExceptionHandler;
import org.eclipse.set.utils.table.TableError;
import org.eclipse.set.utils.table.TableInfo;
import org.eclipse.set.utils.table.TableInfo.Pt1TableCategory;
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.eclipse.swt.widgets.Composite;
import org.osgi.service.event.EventHandler;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Inject;

/**
 * Overview of all tables and their errors
 * 
 * @author Peters
 *
 */
public class TableOverviewPart extends BasePart {

	@Inject
	@Translation
	protected Messages messages;

	@Inject
	private EnumTranslationService enumTranslationService;

	@Inject
	private ToolboxPartService toolboxPartService;

	@Inject
	private IEventBroker broker;

	@Inject
	private TableService tableService;

	@Inject
	private TableMenuService tableMenuService;

	@Inject
	UserConfigurationService userConfigService;

	@Inject
	private TableCompileService compileService;

	@Inject
	private ExportService exportService;

	private TableErrorTableView tableErrorTableView;

	private final EventHandler tableErrorsChangeEventHandler = event -> onTableErrorsChange();
	private ToolboxEventHandler<SelectedControlAreaChangedEvent> selectionControlAreaHandler;
	private EventHandler comparePlaningLoadedHandler;
	private boolean ignoreChangeEvent = false;
	private Set<String> controlAreaIds = new HashSet<>();
	private TableType tableType = null;

	private TableStatusGroup tableStatusGroup;

	@Override
	public TableType getTableType() {
		return tableType;
	}

	@PostConstruct
	void postConstruct() {
		selectionControlAreaHandler = new DefaultToolboxEventHandler<>() {
			@Override
			public void accept(final SelectedControlAreaChangedEvent t) {
				tableType = t.getTableType();
				controlAreaIds = t.getControlAreas()
						.stream()
						.map(ControlAreaValue::areaId)
						.collect(Collectors.toSet());
				// When all table already transformation, that mean the
				// calculate all table button is disable, therefore need to
				// reload the
				// tables here for update table status
				if (getMissingTables().isEmpty()) {
					final Set<TableInfo> needToTransformTable = getTableToReTransform();
					calculateAllMissingTablesEvent(
							monitor -> tableService.transformTables(monitor,
									needToTransformTable, t.getTableType(),
									controlAreaIds));
				}
				update();
			}
		};

		ToolboxEvents.subscribe(getBroker(),
				SelectedControlAreaChangedEvent.class,
				selectionControlAreaHandler);

		comparePlaningLoadedHandler = event -> {
			// When all table already transformation, that mean the calculate
			// all table button is disable, therefore need to reload the tables
			// here for update table status
			if (getMissingTables().isEmpty()) {
				final Set<TableInfo> needToTransformTable = getTableToReTransform();
				calculateAllMissingTablesEvent(
						monitor -> tableService.transformTables(monitor,
								needToTransformTable, getTableType(),
								controlAreaIds));
			}
			update();
		};
		getBroker().subscribe(Events.COMPARE_MODEL_LOADED,
				comparePlaningLoadedHandler);
	}

	@Override
	protected void createView(final Composite parent) {
		// initialize table type
		tableType = getModelSession().getTableType();

		controlAreaIds = getModelSession().getSelectedControlAreas()
				.stream()
				.map(Pair::getSecond)
				.collect(Collectors.toSet());

		createTableStatusGroup(parent);

		// Create table problem table view
		tableErrorTableView = new TableErrorTableView(messages, this,
				enumTranslationService, tableMenuService);
		tableErrorTableView.create(parent);

		getBroker().subscribe(Events.TABLEERROR_CHANGED,
				tableErrorsChangeEventHandler);

		update();
	}

	private void createTableStatusGroup(final Composite parent) {
		tableStatusGroup = new TableStatusGroup(parent, tableService,
				getTableCategory(), messages);
		tableStatusGroup.createView();
		TableStatusGroup.addButtonAction(
				tableStatusGroup.getMissingTablesControl(),
				TableSectionControl::getCalculateMissingTableButton,
				() -> calculateAllMissingTablesEvent(
						this::calculateAllMissingTables));

		TableStatusGroup.addButtonAction(
				tableStatusGroup.getContainErrorTablesControl(),
				TableSectionControl::getOpenAllButton,
				() -> openAllRelevantTable(TableStatus::isContainsErrors));
		TableStatusGroup.addButtonAction(
				tableStatusGroup.getContainErrorTablesControl(),
				TableSectionControl::getExportAllButton,
				() -> exportAllRelevantTable(TableStatus::isContainsErrors));

		TableStatusGroup.addButtonAction(
				tableStatusGroup.getContainsStatesChangeTablesControl(),
				TableSectionControl::getOpenAllButton,
				() -> openAllRelevantTable(
						TableStatus::isContainsStateChanged));
		TableStatusGroup.addButtonAction(
				tableStatusGroup.getContainsStatesChangeTablesControl(),
				TableSectionControl::getExportAllButton,
				() -> exportAllRelevantTable(
						TableStatus::isContainsStateChanged));

		TableStatusGroup.addButtonAction(
				tableStatusGroup.getContainsPlanChangeTablesControl(),
				TableSectionControl::getOpenAllButton,
				() -> openAllRelevantTable(TableStatus::isContainsPlanChanged));
		TableStatusGroup.addButtonAction(
				tableStatusGroup.getContainsPlanChangeTablesControl(),
				TableSectionControl::getExportAllButton,
				() -> exportAllRelevantTable(
						TableStatus::isContainsPlanChanged));
	}

	private void onTableErrorsChange() {
		if (!ignoreChangeEvent) {
			update();
		}
	}

	private void calculateAllMissingTablesEvent(
			final Consumer<IProgressMonitor> calculateEvent) {
		ignoreChangeEvent = true;
		try {
			getDialogService().showProgress(getToolboxShell(),
					calculateEvent::accept);
		} catch (InvocationTargetException | InterruptedException e) {
			getDialogService().error(getToolboxShell(), e);
		}
		ignoreChangeEvent = false;
		update();
	}

	private void calculateAllMissingTables(final IProgressMonitor monitor) {
		TableServiceUtils.calculateAllMissingTables(tableService,
				getModelSession(), controlAreaIds, getTableCategory(), monitor,
				messages);
	}

	private Map<TableInfo, Collection<TableError>> getTableErrors() {
		return tableService.getTableErrors(getModelSession(), controlAreaIds,
				getTableCategory());
	}

	private void openAllRelevantTable(
			final Predicate<TableStatus> tableWithStatus) {
		final Map<TableInfo, TableStatus> tablesStatus = tableService
				.getTablesStatus(getTableCategory());
		tablesStatus.forEach((k, v) -> {
			final String tablePartIdPrefix = switch (getTableCategory()) {
				case ESTW -> ESTW_TABLE_PART_ID_PREFIX;
				case ETCS -> ETCS_TABLE_PART_ID_PREFIX;
				case ESTW_SUPPLEMENT -> ESTW_SUPPLEMENT_PART_ID_PREFIX;
				default -> throw new IllegalArgumentException(
						"Unexpected value: " + getTableCategory()); //$NON-NLS-1$
			};

			if (tableWithStatus.test(v)) {
				toolboxPartService.showPart(String.format("%s.%s", //$NON-NLS-1$
						tablePartIdPrefix, k.shortcut()));
			}
		});
	}

	private void exportAllRelevantTable(
			final Predicate<TableStatus> tableWithStatus) {
		final Map<TableInfo, TableStatus> tablesStatus = tableService
				.getTablesStatus(getTableCategory());
		final Optional<String> optionalOutputDir = getDialogService()
				.selectDirectory(getToolboxShell(),
						userConfigService.getLastExportPath().toString());
		if (optionalOutputDir.isEmpty()) {
			return;
		}
		final IRunnableWithProgress exportThread = new IRunnableWithProgress() {

			@Override
			public void run(final IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				monitor.beginTask(messages.TableExportPart_TaskMsg,
						tablesStatus.size());
				Threads.stopCurrentOnCancel(monitor);
				tablesStatus.forEach((k, v) -> {
					if (tableWithStatus.test(v)) {
						export(k, optionalOutputDir.get());
					}
				});
				monitor.done();
			}
		};

		final ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(
				getToolboxShell());
		try {
			progressMonitorDialog.run(true, true, exportThread);
		} catch (final Exception e) {
			if (!Exceptions.isCausedByThreadDeath(e)) {
				getDialogService().error(getToolboxShell(), e);
			}
		}
		if (!progressMonitorDialog.getProgressMonitor().isCanceled()) {
			// export finished
			getDialogService().openDirectoryAfterExport(getToolboxShell(),
					Path.of(optionalOutputDir.get()));
			userConfigService
					.setLastExportPath(Path.of(optionalOutputDir.get()));
		}
	}

	private void export(final TableInfo tableInfo, final String outDir) {
		final Map<TableType, Table> tables = compileService.compile(tableInfo,
				getModelSession(), controlAreaIds);
		final PlanProToTitleboxTransformation planProToTitleBox = new PlanProToTitleboxTransformation(
				getSessionService());
		final Titlebox titleBox = planProToTitleBox.transform(
				tableService.getTableNameInfo(tableInfo),
				this::getAttachmentPath);

		final PlanProToFreeFieldTransformation planProToFreeField = PlanProToFreeFieldTransformation
				.create(getSessionService());
		final FreeFieldInfo freeFeild = planProToFreeField.transform();
		exportService.exportPdf(tables, ExportType.PLANNING_RECORDS, titleBox,
				freeFeild, tableInfo.shortcut(), outDir,
				getModelSession().getToolboxPaths(), tableType,
				OverwriteHandling.forUserConfirmation(
						path -> Boolean.valueOf(getDialogService()
								.confirmOverwrite(getToolboxShell(), path))),
				new ExceptionHandler(getToolboxShell(), getDialogService()));
	}

	private Path getAttachmentPath(final String guid) {
		try {
			return getModelSession().getToolboxFile()
					.getMediaPath(Guid.create(guid));
		} catch (final UnsupportedOperationException e) {
			return null; // .ppxml-Files do not support attachments
		}
	}

	private Pt1TableCategory getTableCategory() {
		final String elementId = getToolboxPart().getElementId();
		if (elementId.startsWith(ESTW_TABLE_PART_ID_PREFIX)) {
			return Pt1TableCategory.ESTW;
		} else if (elementId.startsWith(ETCS_TABLE_PART_ID_PREFIX)) {
			return Pt1TableCategory.ETCS;
		} else if (elementId.startsWith(ESTW_SUPPLEMENT_PART_ID_PREFIX)) {
			return Pt1TableCategory.ESTW_SUPPLEMENT;
		}
		throw new IllegalArgumentException();
	}

	private void update() {
		tableStatusGroup.updateControlText(getMissingTables());
		final ArrayList<TableError> allErrors = new ArrayList<>();
		getTableErrors().values().forEach(allErrors::addAll);
		tableErrorTableView.updateView(allErrors);
	}

	private Collection<TableInfo> getMissingTables() {
		return TableServiceUtils.getMissingTables(tableService,
				getModelSession(), controlAreaIds, getTableCategory());
	}

	@PreDestroy
	private void unsubscribe() {
		broker.unsubscribe(tableErrorsChangeEventHandler);
		broker.unsubscribe(comparePlaningLoadedHandler);
		ToolboxEvents.unsubscribe(broker, selectionControlAreaHandler);
	}

	private Set<TableInfo> getTableToReTransform() {
		final List<String> activeTablePart = getActiveTablePart();
		return tableService.getAvailableTables()
				.stream()
				.filter(info -> info.category() == getTableCategory())
				.filter(info -> activeTablePart.stream()
						.noneMatch(
								partId -> partId.equals(info.getTablePartId())))
				.collect(Collectors.toSet());
	}

	private List<String> getActiveTablePart() {
		final String tablePartPrefix = getTableCategory().getTablePartPrefix();
		return toolboxPartService.getOpenParts()
				.stream()
				.filter(part -> part.getElementId().startsWith(tablePartPrefix)
						&& !part.getElementId()
								.equals(getToolboxPart().getElementId()))
				.map(MPart::getElementId)
				.toList();
	}

	/**
	 * Create the part.
	 */
	@Inject
	public TableOverviewPart() {
		super();
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
		update();
	}

	@Override
	protected void updateViewProjectDataChanged(
			final List<Notification> notifications) {
		update();
	}
}
