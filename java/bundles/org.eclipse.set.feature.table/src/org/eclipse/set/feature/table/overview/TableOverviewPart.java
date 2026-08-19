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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.extensions.Exceptions;
import org.eclipse.set.basis.threads.Threads;
import org.eclipse.set.core.services.configurationservice.UserConfigurationService;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.feature.table.internal.TableServiceUtils;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.feature.table.overview.TableStatusGroupView.TableSectionControl;
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup;
import org.eclipse.set.services.export.ExportService;
import org.eclipse.set.services.export.ExportService.TableToExportPath;
import org.eclipse.set.services.export.TableCompileService;
import org.eclipse.set.services.export.TableExport.ExportFormat;
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
import org.eclipse.swt.widgets.Shell;
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

	private TableStatusGroupView tableStatusGroup;

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
		tableStatusGroup = new TableStatusGroupView(parent, tableService,
				getTableCategory(), messages);
		tableStatusGroup.createView();
		// Not transformed tables control
		TableStatusGroupView.addButtonAction(
				tableStatusGroup.getMissingTablesControl(),
				TableSectionControl::getCalculateMissingTableButton,
				() -> calculateAllMissingTablesEvent(
						this::calculateAllMissingTables));

		// Table with errors control
		TableStatusGroupView.addButtonAction(
				tableStatusGroup.getContainErrorTablesControl(),
				TableSectionControl::getOpenAllButton,
				() -> openAllRelevantTable(TableStatus::isContainsErrors));
		TableStatusGroupView.addButtonAction(
				tableStatusGroup.getContainErrorTablesControl(),
				TableSectionControl::getExportAllButton,
				() -> exportAllRelevantTable(TableStatus::isContainsErrors));

		// Not empty table control
		TableStatusGroupView.addButtonAction(
				tableStatusGroup.getTableWithDatasControl(),
				TableSectionControl::getOpenAllButton,
				() -> openAllRelevantTable(s -> !s.isEmpty()));
		TableStatusGroupView.addButtonAction(
				tableStatusGroup.getTableWithDatasControl(),
				TableSectionControl::getExportAllButton,
				() -> exportAllRelevantTable(s -> !s.isEmpty()));

		// Table with states changed data control
		TableStatusGroupView.addButtonAction(
				tableStatusGroup.getContainsStatesChangeTablesControl(),
				TableSectionControl::getOpenAllButton,
				() -> openAllRelevantTable(
						TableStatus::isContainsStateChanged));
		TableStatusGroupView.addButtonAction(
				tableStatusGroup.getContainsStatesChangeTablesControl(),
				TableSectionControl::getExportAllButton,
				() -> exportAllRelevantTable(
						TableStatus::isContainsStateChanged));

		// Table with plan changed control
		TableStatusGroupView.addButtonAction(
				tableStatusGroup.getContainsPlanChangeTablesControl(),
				TableSectionControl::getOpenAllButton,
				() -> openAllRelevantTable(TableStatus::isContainsPlanChanged));
		TableStatusGroupView.addButtonAction(
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
		final String outputDir = optionalOutputDir.get();
		final List<TableToExportPath> tablesToExport = tablesStatus.entrySet()
				.stream()
				.filter(entry -> tableWithStatus.test(entry.getValue()))
				.map(Entry::getKey)
				.map(tableInfo -> TableToExportPath.createInstance(tableInfo,
						getModelSession(), ExportType.PLANNING_RECORDS,
						Paths.get(outputDir),
						List.of(ExportFormat.EXCEL, ExportFormat.PDF)))
				.toList();
		try {
			final List<TableToExportPath> filterConfirmOverwriteTable = filterConfirmOverwriteTable(
					tablesToExport, getToolboxShell(), getDialogService(),
					outputDir);
			final IRunnableWithProgress exportThread = new IRunnableWithProgress() {
				@Override
				public void run(final IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {
					monitor.beginTask(messages.TableExportPart_TaskMsg,
							filterConfirmOverwriteTable.size());
					Threads.stopCurrentOnCancel(monitor);
					exportService.exportMultiTable(ExportType.INVENTORY_RECORDS,
							filterConfirmOverwriteTable, getModelSession(),
							compileService, getDialogService(), tableType,
							controlAreaIds, monitor,
							OverwriteHandling.forCheckbox(true),
							new ExceptionHandler(getToolboxShell(),
									getDialogService()));
					monitor.done();
				}
			};

			final ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(
					getToolboxShell());
			progressMonitorDialog.run(true, true, exportThread);

			if (!progressMonitorDialog.getProgressMonitor().isCanceled()) {
				// export finished
				getDialogService().openDirectoryAfterExport(getToolboxShell(),
						Path.of(optionalOutputDir.get()));
				userConfigService
						.setLastExportPath(Path.of(optionalOutputDir.get()));
			}
		} catch (final Exception e) {
			if (!Exceptions.isCausedByThreadDeath(e)) {
				getDialogService().error(getToolboxShell(), e);
			} else {
				Thread.currentThread().interrupt();
			}
		}
	}

	private static List<TableToExportPath> filterConfirmOverwriteTable(
			final List<TableToExportPath> tablesToExport, final Shell shell,
			final DialogService dialogService, final String outputDir)
			throws IOException {
		final Set<TableToExportPath> alreadyExistExport = getAlreadyExistExport(
				tablesToExport, outputDir);
		if (alreadyExistExport.isEmpty()) {
			return tablesToExport;
		}
		final List<TableToExportPath> result = new ArrayList<>(tablesToExport);
		result.removeIf(t -> alreadyExistExport.stream()
				.anyMatch(exported -> exported.tableInfo()
						.shortcut()
						.equals(t.tableInfo().shortcut())));
		final List<String> selectItems = alreadyExistExport.stream()

				.map(t -> t.tableInfo().nameInfo().getFullDisplayName())
				.sorted()
				.toList();
		final List<String> confirmedOverwirteFiles = dialogService
				.confirmOverwriteMultiFile(shell, selectItems,
						IDialogConstants.OK_LABEL, null);
		final List<TableToExportPath> confirmOverwrite = alreadyExistExport
				.stream()
				.filter(table -> confirmedOverwirteFiles.contains(
						table.tableInfo().nameInfo().getFullDisplayName()))
				.toList();
		result.addAll(confirmOverwrite);
		return result;
	}

	private static Set<TableToExportPath> getAlreadyExistExport(
			final List<TableToExportPath> tablesToExport,
			final String outputDir) throws IOException {
		final Path outDir = Path.of(outputDir);
		if (!Files.exists(outDir) || !Files.isDirectory(outDir)) {
			throw new IllegalArgumentException("outputDir should be Directory"); //$NON-NLS-1$
		}
		final Set<TableToExportPath> alreadyExistExportTable = new HashSet<>();
		try (Stream<Path> filesPath = Files.walk(Path.of(outputDir))) {
			filesPath.forEach(p -> {
				final String exportedFileName = p.getFileName().toString();
				final Optional<TableToExportPath> exportedTable = tablesToExport
						.stream()
						.filter(t -> t.getExportFilesName()
								.contains(exportedFileName))
						.findFirst();
				if (exportedTable.isPresent()) {
					alreadyExistExportTable.add(exportedTable.get());
				}
			});
		}
		return alreadyExistExportTable;
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
		getTableErrors().values()
				.stream()
				.filter(Objects::nonNull)
				.forEach(allErrors::addAll);
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
