/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.internal;

import static org.eclipse.set.basis.extensions.MApplicationElementExtensions.isOpenPart;
import static org.eclipse.set.ppmodel.extensions.StellBereichExtensions.getStellBereich;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.StreamSupport;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.MissingSupplier;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.constants.ToolboxViewState;
import org.eclipse.set.basis.extensions.MApplicationElementExtensions;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.basis.threads.Threads;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.extensions.TableCellExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.ppmodel.extensions.ContainerExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.services.table.TableDiffService;
import org.eclipse.set.services.table.TableDiffService.TableCompareType;
import org.eclipse.set.services.table.TableService;
import org.eclipse.set.utils.BasePart;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.table.TableError;
import org.eclipse.set.utils.table.TableInfo;
import org.eclipse.set.utils.table.TableInfo.Pt1TableCategory;
import org.eclipse.set.utils.table.TableTransformationService;
import org.eclipse.set.utils.table.sorting.TableRowGroupComparator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.xbase.lib.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import jakarta.inject.Inject;

/**
 * TableService implementation with the task of transforming the planpro model
 * into table model instances for selected tables.
 * <p>
 * The table service collects {@link TableTransformationService} instances from
 * the osgi service registry for each toolbox table. These instances need to
 * fill in the osgi service property named <code>table.shortcut</code> with the
 * shortcut table name (e.g. "ssld").
 * <p>
 * The table service caches transformed tables and listens to to basic events in
 * the toolbox via the osgi event admin service on the topic
 * <code>modelsession/container/*</code> which will lead to invalidate all cache
 * entries in any case an event is received.
 * 
 * @author rumpf
 *
 */
public final class TableServiceImpl implements TableService {

	static final Logger logger = LoggerFactory
			.getLogger(TableServiceImpl.class);

	@Inject
	IEventBroker broker;

	@Inject
	CacheService cacheService;

	@Inject
	@Translation
	Messages messages;

	@Inject
	ToolboxPartService partService;

	@Inject
	DialogService dialogService;

	@Inject
	SessionService sessionService;

	private final Map<TableInfo, PlanPro2TableTransformationService> modelServiceMap = new ConcurrentHashMap<>();

	private final Map<TableCompareType, TableDiffService> diffServiceMap = new ConcurrentHashMap<>();
	private static final Queue<Pair<BasePart, Runnable>> transformTableThreads = new LinkedList<>();

	private static final String EMPTY = "empty"; //$NON-NLS-1$
	private static final String IGNORED_PLANNING_AREA_CACHE_KEY = "ignoredPlanningArea";//$NON-NLS-1$

	private CacheService getCacheService() {
		return ToolboxConfiguration.isDebugMode() ? Services.getNoCacheService()
				: cacheService;
	}

	/**
	 * adds a model service. For a model service to be properly added it has to
	 * set the <code>table.shortcut</code property.
	 * 
	 * @param service
	 *            the service
	 * @param properties
	 *            the service properties
	 * @throws IllegalAccessException
	 *             if the table.shortcut property is not set
	 */
	void addModelService(final PlanPro2TableTransformationService service,
			final Map<String, Object> properties)
			throws IllegalAccessException {
		final TableInfo tableInfo = TableServiceContextFunction
				.getTableInfo(properties);
		modelServiceMap.put(tableInfo, service);
	}

	void addModelServiceByInfo(final TableInfo tableInfo,
			final PlanPro2TableTransformationService service) {
		modelServiceMap.put(tableInfo, service);
	}

	void addDiffService(final TableDiffService diffService) {
		diffServiceMap.put(diffService.getCompareType(), diffService);
	}

	void addDiffSerivce(final TableCompareType compareType,
			final TableDiffService diffService) {
		diffServiceMap.put(compareType, diffService);
	}

	void removeDiffService(final TableCompareType compareType) {
		if (diffServiceMap.containsKey(compareType)) {
			diffServiceMap.remove(compareType);
		}
	}

	private Table createDiffTable(final TableInfo tableInfo,
			final IModelSession modelSession, final String controlAreaId) {

		final Table startTable = transformToTable(tableInfo, TableType.INITIAL,
				modelSession, controlAreaId == null ? Collections.emptySet()
						: Set.of(controlAreaId));
		final Table zielTable = transformToTable(tableInfo, TableType.FINAL,
				modelSession, controlAreaId == null ? Collections.emptySet()
						: Set.of(controlAreaId));
		if (zielTable == null || startTable == null) {
			return null;
		}

		return diffServiceMap.get(TableCompareType.STATE)
				.createDiffTable(startTable, zielTable);
	}

	@Override
	public String extractShortcut(final PartDescription element) {
		return element.getToolboxViewName().substring(0, 4);
	}

	@Override
	public String extractShortcut(final String elementId) {
		final String[] parts = elementId.split("\\."); //$NON-NLS-1$
		return parts[parts.length - 1];
	}

	private String getContainerCacheId(final IModelSession modelSession,
			final TableType tableType) {
		// For table diffs, combine initial and final cache ids
		if (tableType == TableType.DIFF) {
			return getContainerCacheId(modelSession, TableType.INITIAL) + "#" //$NON-NLS-1$
					+ getContainerCacheId(modelSession, TableType.FINAL);
		}

		// For other tables, use the container's cache id
		return tableType.toString() + "$" //$NON-NLS-1$
				+ ContainerExtensions.getCacheId(modelSession
						.getContainer(tableType.getContainerForTable()));
	}

	private TableInfo getTableInfo(final BasePart part) {
		final String shortcut = extractShortcut(
				part.getToolboxPart().getElementId());
		return getAvailableTables().stream()
				.filter(table -> table.shortcut().equalsIgnoreCase(shortcut))
				.findFirst()
				.orElse(null);
	}

	private PlanPro2TableTransformationService getModelService(
			final TableInfo tableInfo) {
		final PlanPro2TableTransformationService transformService = modelServiceMap
				.get(tableInfo);
		if (transformService == null) {
			throw new IllegalArgumentException(
					"no model service for " + tableInfo.shortcut() + " found!"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return transformService;
	}

	@Override
	public TableNameInfo getTableNameInfo(final TableInfo tableInfo) {
		return getModelService(tableInfo).getTableNameInfo();
	}

	@Override
	public Collection<TableInfo> getAvailableTables() {
		return new ArrayList<>(modelServiceMap.keySet());
	}

	@Override
	public Set<Integer> getFixedColumns(final TableInfo tableInfo) {
		return getModelService(tableInfo).getFixedColumnsPos();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<TableInfo, Collection<TableError>> getTableErrors(
			final IModelSession modelSession, final Set<String> controlAreaIds,
			final Pt1TableCategory tableCategory) {
		final HashMap<TableInfo, Collection<TableError>> map = new HashMap<>();
		final String tableErrorsCacheGroup = switch (modelSession
				.getTableType()) {
			case FINAL -> ToolboxConstants.CacheId.TABLE_ERRORS_FINAL;
			case INITIAL -> ToolboxConstants.CacheId.TABLE_ERRORS_INITIAL;
			case SINGLE -> ToolboxConstants.CacheId.TABLE_ERRORS_SINGLE;
			case DIFF -> ToolboxConstants.CacheId.TABLE_ERRORS;
		};
		final Cache cache = getCacheService().getCache(
				modelSession.getPlanProSchnittstelle(), tableErrorsCacheGroup);
		getAvailableTables().forEach(tableInfo -> {
			if (tableInfo.category().equals(tableCategory)) {
				final List<Pair<String, String>> cacheKeys = getCacheKeys(
						tableInfo.shortcut(), modelSession, controlAreaIds);
				final List<List<TableError>> tableErrors = cacheKeys.stream()
						.map(cacheKey -> (List<TableError>) cache
								.getIfPresent(cacheKey.getValue()))
						.filter(Objects::nonNull)
						.toList();
				if (!tableErrors.isEmpty()
						|| !TableService.isTransformComplete(tableInfo, null)) {
					map.put(tableInfo,
							tableErrors.stream()
									.flatMap(List::stream)
									.toList());
				}
			}
		});
		return map;

	}

	@SuppressWarnings("unchecked")
	private void combineTableErrors(final IModelSession modelSession,
			final String cacheKey) {
		final Collection<TableError> initialErrors = (Collection<TableError>) getCacheService()
				.getCache(modelSession.getPlanProSchnittstelle(),
						ToolboxConstants.CacheId.TABLE_ERRORS_INITIAL)
				.getIfPresent(cacheKey);
		final Collection<TableError> finalErrors = (Collection<TableError>) getCacheService()
				.getCache(modelSession.getPlanProSchnittstelle(),
						ToolboxConstants.CacheId.TABLE_ERRORS_FINAL)
				.getIfPresent(cacheKey);
		if (initialErrors == null || finalErrors == null) {
			return;
		}
		final Collection<TableError> combined = new ArrayList<>();
		combined.addAll(initialErrors);
		combined.addAll(finalErrors);
		getCacheService()
				.getCache(modelSession.getPlanProSchnittstelle(),
						ToolboxConstants.CacheId.TABLE_ERRORS)
				.set(cacheKey, combined);
		broker.post(Events.TABLEERROR_CHANGED, null);
	}

	private void saveTableError(final TableInfo tableInfo,
			final IModelSession modelSession, final TableType tableType,
			final Collection<TableError> errors, final String cacheKey) {
		final String shortName = getTableNameInfo(tableInfo).getShortName();
		errors.forEach(error -> {
			error.setSource(shortName);
			error.setTableType(tableType);
		});
		switch (tableType) {
			case INITIAL:
				getCacheService()
						.getCache(modelSession.getPlanProSchnittstelle(),
								ToolboxConstants.CacheId.TABLE_ERRORS_INITIAL)
						.set(cacheKey, errors);
				break;
			case FINAL:
				getCacheService()
						.getCache(modelSession.getPlanProSchnittstelle(),
								ToolboxConstants.CacheId.TABLE_ERRORS_FINAL)
						.set(cacheKey, errors);
				break;
			case SINGLE:
				getCacheService()
						.getCache(modelSession.getPlanProSchnittstelle(),
								ToolboxConstants.CacheId.TABLE_ERRORS_SINGLE)
						.set(cacheKey, errors);
				// The plan with single state don't need combine cache errrors
				broker.post(Events.TABLEERROR_CHANGED, null);
				return;
			default:
				return;
		}
		combineTableErrors(modelSession, cacheKey);
	}

	private Object loadTransform(final TableInfo tableInfo,
			final TableType tableType, final IModelSession modelSession,
			final String controlAreaId) {
		final PlanPro2TableTransformationService modelService = getModelService(
				tableInfo);
		Table transformedTable = null;
		if (tableType == TableType.DIFF) {
			transformedTable = createDiffTable(tableInfo, modelSession,
					controlAreaId);
			modelService.format(transformedTable);
		} else {
			final MultiContainer_AttributeGroup container = modelSession
					.getContainer(tableType.getContainerForTable());
			final Stell_Bereich area = getStellBereich(container,
					controlAreaId);
			if (controlAreaId == null
					|| isContainerContainArea(container, controlAreaId)) {
				transformedTable = modelService.transform(container, area);
			} else {
				// Create empty table
				transformedTable = TablemodelFactory.eINSTANCE.createTable();
				transformedTable.setTablecontent(
						TablemodelFactory.eINSTANCE.createTableContent());
				modelService.buildHeading(transformedTable);
			}
		}
		if (Thread.currentThread().isInterrupted()
				|| transformedTable == null) {
			return MissingSupplier.MISSING_VALUE;
		}

		// sorting
		sortTable(transformedTable, tableType, tableInfo);
		return transformedTable;
	}

	private static boolean isContainerContainArea(
			final MultiContainer_AttributeGroup container,
			final String areaId) {
		return StreamSupport
				.stream(container.getStellBereich().spliterator(), false)
				.anyMatch(sb -> sb.getIdentitaet().getWert().equals(areaId));
	}

	/**
	 * removes a model service.
	 * 
	 * @param properties
	 *            the service properties
	 * @throws IllegalAccessException
	 *             if the table.shortcut property is not set
	 */
	public void removeModelService(final Map<String, Object> properties)
			throws IllegalAccessException {
		final TableInfo tableInfo = TableServiceContextFunction
				.getTableInfo(properties);
		modelServiceMap.remove(tableInfo);
	}

	@Override
	public String transformToCsv(final TableInfo tableInfo,
			final TableType tableType, final IModelSession modelSession,
			final Set<String> controlAreas) {
		final Table table = transformToTable(tableInfo, tableType, modelSession,
				controlAreas);
		return transformToCsv(table);
	}

	/**
	 * 
	 * @param table
	 * @return table as csv string
	 */
	@SuppressWarnings("static-method")
	private String transformToCsv(final Table table) {
		if (table == null) {
			return ""; //$NON-NLS-1$
		}
		final List<TableRow> rows = TableExtensions.getTableRows(table);
		final List<ColumnDescriptor> colNames = table.getColumndescriptors()
				.stream() //
				.filter(descriptor -> !Strings
						.isNullOrEmpty(descriptor.getLabel())) //
				.toList();
		final String delimeter = ";"; //$NON-NLS-1$
		final StringBuilder builder = new StringBuilder();
		for (final ColumnDescriptor colName : colNames) {
			builder.append(colName.getLabel() + delimeter);
		}
		builder.append("\n"); //$NON-NLS-1$

		for (final TableRow row : rows) {
			for (final ColumnDescriptor colName : colNames) {
				try {
					builder.append(TableCellExtensions.getPlainStringValue(
							TableRowExtensions.getCell(row, colName))
							+ delimeter);
				} catch (final Exception e) {
					builder.append(e.toString());
				}
			}
			builder.append("\n"); //$NON-NLS-1$
		}
		return builder.toString();
	}

	private List<Pair<String, String>> getCacheKeys(final String shortCut,
			final IModelSession modelSession,
			final Set<String> controlAreaIds) {
		if (controlAreaIds.isEmpty()) {
			final String cachedKey = modelSession.isPlanningAreaIgnored()
					? cacheService.cacheKeyBuilder(shortCut,
							IGNORED_PLANNING_AREA_CACHE_KEY, EMPTY)
					: cacheService.cacheKeyBuilder(shortCut, EMPTY);
			return List.of(Pair.of(null, cachedKey));
		}
		return controlAreaIds.stream().map(areaId -> {
			// Planning area is always ignored when any control area is
			// selected.
			final String areaCacheKey = cacheService.cacheKeyBuilder(shortCut,
					IGNORED_PLANNING_AREA_CACHE_KEY, areaId);
			return Pair.of(areaId, areaCacheKey);
		}).toList();
	}

	@Override
	public Table transformToTable(final TableInfo tableInfo,
			final TableType tableType, final IModelSession modelSession,
			final Set<String> controlAreaIds) {
		final String containerId = getContainerCacheId(modelSession, tableType);
		final Cache cache = getCacheService().getCache(
				modelSession.getPlanProSchnittstelle(),
				ToolboxConstants.SHORTCUT_TO_TABLE_CACHE_ID, containerId);

		Table resultTable = null;

		final List<Pair<String, String>> cacheKeys = getCacheKeys(
				tableInfo.shortcut(), modelSession, controlAreaIds);
		for (final Pair<String, String> cacheKey : cacheKeys) {
			final String areaId = cacheKey.getKey();
			final String areaCacheKey = cacheKey.getValue();
			Table table = (Table) cache.getIfPresent(areaCacheKey);

			if (table == null) {
				table = (Table) loadTransform(tableInfo, tableType,
						modelSession, areaId);
				saveTableToCache(table, modelSession, containerId, tableInfo,
						tableType, areaCacheKey);
			}
			if (resultTable == null) {
				resultTable = EcoreUtil.copy(table);
			} else {
				for (final RowGroup rowGroup : table.getTablecontent()
						.getRowgroups()) {
					TableExtensions.addRowGroup(resultTable, rowGroup);
				}
			}
		}

		// sorting
		if (resultTable != null && resultTable.getTablecontent() != null) {
			sortTable(resultTable, tableType, tableInfo);
		}

		return resultTable;
	}

	private void saveTableToCache(final Table table,
			final IModelSession modelSession, final String containerId,
			final TableInfo tableInfo, final TableType tableType,
			final String areaCacheKey) {
		final String threadName = String.format("%s/saveCache/%s", //$NON-NLS-1$
				tableInfo.shortcut(), areaCacheKey);
		final PlanPro2TableTransformationService modelService = getModelService(
				tableInfo);
		// It will create a separate transformation for each table state, which
		// means each table state will have its own list of table errors.
		final Collection<TableError> errors = modelService.getTableErrors();
		final Thread storageCacheThread = new Thread(() -> {
			final Runnable storageFunc = () -> {
				final Cache cache = getCacheService().getCache(
						modelSession.getPlanProSchnittstelle(),
						ToolboxConstants.SHORTCUT_TO_TABLE_CACHE_ID,
						containerId);
				if (table != null) {
					cache.set(areaCacheKey, table);
				}
				saveTableError(tableInfo, modelSession, tableType, errors,
						areaCacheKey);
			};

			if (TableService.isTransformComplete(tableInfo,
					s -> !s.equalsIgnoreCase(threadName))) {
				storageFunc.run();
				return;
			}
			while (!TableService.isTransformComplete(tableInfo,
					s -> !s.equalsIgnoreCase(threadName))) {
				try {
					Thread.sleep(2000);
				} catch (final InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}
			}
			storageFunc.run();
		}, threadName);
		storageCacheThread.start();

	}

	@Override
	public void updateTable(final BasePart tablePart,
			final List<Pt1TableCategory> tableCategories,
			final Runnable updateTableHandler, final Runnable clearInstance) {
		// Find which table categories should be update
		final List<String> tablePrefixes = List
				.of(ToolboxConstants.ESTW_TABLE_PART_ID_PREFIX,
						ToolboxConstants.ETCS_TABLE_PART_ID_PREFIX,
						ToolboxConstants.ESTW_SUPPLEMENT_PART_ID_PREFIX)
				.stream()
				.filter(prefix -> tableCategories.isEmpty()
						|| tableCategories.stream()
								.map(Pt1TableCategory::getId)
								.anyMatch(prefix::contains))
				.toList();
		// Get already open table parts
		final List<MPart> openTableParts = partService.getOpenParts()
				.stream()
				.filter(part -> tablePrefixes.stream()
						.anyMatch(prefix -> part.getElementId()
								.startsWith(prefix))
						// IMPROVE: currently table overview isn't regard on
						// control area
						&& !part.getElementId()
								.endsWith(ToolboxConstants.TABLE_OVERVIEW_ID))
				.map(MPart.class::cast)
				.toList();

		transformTableThreads.add(new Pair<>(tablePart, updateTableHandler));
		final List<MPart> parts = transformTableThreads.stream()
				.map(pair -> pair.getKey().getToolboxPart())
				.toList();

		// Create a loading monitor only when the current table part isn't open
		// or already collect all transform handler of the open table parts
		final IRunnableWithProgress updateTableProgress = !isOpenPart(
				tablePart.getToolboxPart()) || parts.containsAll(openTableParts)
						? createProgressMonitor()
						: null;

		if (updateTableProgress != null) {
			final ProgressMonitorDialog monitor = new ProgressMonitorDialog(
					tablePart.getToolboxShell());
			try {
				logger.info("Start ProgressMonitorDialog..."); //$NON-NLS-1$
				monitor.run(true, true, updateTableProgress);
			} catch (final InvocationTargetException e) {
				logger.error(e.toString(), e);
				throw new RuntimeException(e);
			} catch (final InterruptedException e) {
				clearInstance.run();
				transformTableThreads
						.forEach(pair -> MApplicationElementExtensions
								.setViewState(pair.getKey().getToolboxPart(),
										ToolboxViewState.CANCELED));

				Thread.currentThread().interrupt();
			}
		}
	}

	private IRunnableWithProgress createProgressMonitor() {
		// runnable for the transformation
		return monitor -> {
			// start a single task with unknown time frame
			monitor.beginTask(
					messages.Abstracttableview_transformation_progress,
					transformTableThreads.size() > 1
							? transformTableThreads.size()
							: IProgressMonitor.UNKNOWN);
			// listen to cancel
			Threads.stopCurrentOnCancel(monitor);

			// Wait for table transform
			for (Pair<BasePart, Runnable> transformThread; (transformThread = transformTableThreads
					.poll()) != null;) {
				final TableInfo tableInfo = getTableInfo(
						transformThread.getKey());
				final TableNameInfo tableNameInfo = getTableNameInfo(tableInfo);
				monitor.subTask(tableNameInfo.getFullDisplayName());
				Display.getDefault().syncExec(transformThread.getValue());
				monitor.worked(1);
			}
			if (monitor.isCanceled()) {
				throw new InterruptedException();
			}
			// stop progress
			monitor.done();
			logger.info("ProgressMonitorDialog done."); //$NON-NLS-1$

		};
	}

	@Override
	public Map<TableInfo, Table> transformTables(final IProgressMonitor monitor,
			final IModelSession modelSession,
			final Set<TableInfo> tablesToTransfrom, final TableType tableType,
			final Set<String> controlAreaIds) {
		final Map<TableInfo, Table> result = new HashMap<>();
		monitor.beginTask(messages.TableOverviewPart_CalculateMissingTask,
				tablesToTransfrom.size());

		for (final TableInfo tableInfo : tablesToTransfrom) {
			final TableNameInfo nameInfo = getTableNameInfo(tableInfo);
			monitor.subTask(nameInfo.getFullDisplayName());
			final Table table = transformToTable(tableInfo, tableType,
					modelSession, controlAreaIds);
			while (!TableService.isTransformComplete(tableInfo, null)) {
				try {
					Thread.sleep(2000);
				} catch (final InterruptedException e) {
					Thread.interrupted();
				}
			}

			result.put(tableInfo, table);
			monitor.worked(1);
		}
		monitor.done();
		return result;
	}

	@Override
	public Table createDiffTable(final TableInfo tableInfo,
			final TableType tableType, final Set<String> controlAreaIds) {
		final Table mainSessionTable = transformToTable(tableInfo, tableType,
				sessionService.getLoadedSession(ToolboxFileRole.SESSION),
				controlAreaIds);
		final IModelSession compareSession = sessionService
				.getLoadedSession(ToolboxFileRole.COMPARE_PLANNING);
		if (compareSession == null) {
			return mainSessionTable;
		}

		final Table compareSessionTable = transformToTable(tableInfo, tableType,
				compareSession, controlAreaIds);

		// Waiting table compare transform, then create compare table between to
		// plan
		while (!TableService.isTransformComplete(tableInfo, null)) {
			try {
				Thread.sleep(2000);
			} catch (final InterruptedException e) {
				Thread.interrupted();
			}
		}
		final Table compareTable = diffServiceMap.get(TableCompareType.PROJECT)
				.createDiffTable(mainSessionTable, compareSessionTable);
		sortTable(compareTable, TableType.DIFF, tableInfo);
		return compareTable;
	}

	@Override
	public void sortTable(final Table table, final TableType tableType,
			final TableInfo tableInfo) {
		final Comparator<RowGroup> comparator = getModelService(tableInfo)
				.getRowGroupComparator();
		ECollections.sort(table.getTablecontent().getRowgroups(), comparator);
	}

	@Override
	public TableRowGroupComparator getRowGroupComparator(
			final TableInfo tableInfo) {
		final Comparator<RowGroup> comparator = getModelService(tableInfo)
				.getRowGroupComparator();
		if (comparator instanceof final TableRowGroupComparator rowGroupComparator) {
			return rowGroupComparator;
		}
		return null;
	}
}
