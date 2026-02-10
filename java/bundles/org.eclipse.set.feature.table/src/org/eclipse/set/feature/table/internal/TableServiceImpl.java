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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
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
import org.eclipse.set.basis.constants.ContainerType;
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
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.CompareStateCellContent;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions;
import org.eclipse.set.model.tablemodel.extensions.FootnoteExtensions;
import org.eclipse.set.model.tablemodel.extensions.FootnoteExtensions.WorkNotesUsage;
import org.eclipse.set.model.tablemodel.extensions.TableCellExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.MultiContainer_AttributeGroupExtensions;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.StellBereichExtensions;
import org.eclipse.set.ppmodel.extensions.UrObjectExtensions;
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
	private final Map<TableInfo, Set<FootnoteExtensions.WorkNotesUsage>> workNotesPerTable = new ConcurrentHashMap<>();
	private static final Queue<Pair<BasePart, Runnable>> transformTableThreads = new LinkedList<>();
	private static final Set<TableInfo> nonTransformableTables = new HashSet<>();

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

	void cleanWorkNotesProTable() {
		workNotesPerTable.clear();
	}
	
	private Table createDiffStateTable(final TableInfo tableInfo,
			final IModelSession modelSession) {
		final PlanPro2TableTransformationService modelService = getModelService(
				tableInfo);

		final Table startTable = modelService
				.transform(PlanProSchnittstelleExtensions.getContainer(
						modelSession.getPlanProSchnittstelle(),
						ContainerType.INITIAL));
		final Table zielTable = modelService
				.transform(PlanProSchnittstelleExtensions.getContainer(
						modelSession.getPlanProSchnittstelle(),
						ContainerType.FINAL));
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

	@Override
	public TableInfo getTableInfo(final BasePart part) {
		final String shortcut = extractShortcut(
				part.getToolboxPart().getElementId());
		return getTableInfo(shortcut);
	}

	@Override
	public TableInfo getTableInfo(final String shortcut) {
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
		return modelServiceMap.keySet()
				.stream()
				.filter(tableInfo -> !nonTransformableTables
						.contains(tableInfo))
				.toList();
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
			final IModelSession modelSession,
			final Collection<TableError> errors) {
		final String shortName = getTableNameInfo(tableInfo).getShortName();
		final String shortCut = tableInfo.shortcut();
		errors.forEach(error -> error.setSource(shortName));
		if (modelSession.getTableType() == TableType.SINGLE) {
			getCacheService()
					.getCache(modelSession.getPlanProSchnittstelle(),
							ToolboxConstants.CacheId.TABLE_ERRORS_SINGLE)
					.set(shortCut, errors);
			broker.post(Events.TABLEERROR_CHANGED, null);
			return;
		}

		final Collection<TableError> initialErros = new ArrayList<>();
		final Collection<TableError> finalErrors = new ArrayList<>();
		errors.forEach(error -> {
			switch (error.getTableType()) {
				case INITIAL:
					initialErros.add(error);
					break;
				case FINAL:
					finalErrors.add(error);
					break;
				default:
					return;
			}
		});
		getCacheService()
				.getCache(modelSession.getPlanProSchnittstelle(),
						ToolboxConstants.CacheId.TABLE_ERRORS_INITIAL)
				.set(shortCut, initialErros);
		getCacheService()
				.getCache(modelSession.getPlanProSchnittstelle(),
						ToolboxConstants.CacheId.TABLE_ERRORS_INITIAL)
				.set(shortCut, finalErrors);
		combineTableErrors(modelSession, shortCut);
	}

	private Object loadTransform(final TableInfo tableInfo,
			final IModelSession modelSession) {
		final PlanPro2TableTransformationService modelService = getModelService(
				tableInfo);
		Table transformedTable = null;
		transformedTable = createDiffStateTable(tableInfo, modelSession);
		modelService.format(transformedTable);
		if (Thread.currentThread().isInterrupted()
				|| transformedTable == null) {
			return MissingSupplier.MISSING_VALUE;
		}

		// sorting
		sortTable(transformedTable, tableInfo);
		saveTableToCache(transformedTable, modelSession, tableInfo);
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
		final Cache cache = getCacheService().getCache(
				modelSession.getPlanProSchnittstelle(),
				ToolboxConstants.SHORTCUT_TO_TABLE_CACHE_ID);
		final Table table = cache.get(tableInfo.shortcut(),
				() -> (Table) loadTransform(tableInfo, modelSession));
		if (tableType != TableType.DIFF && !controlAreaIds.isEmpty()
				&& controlAreaIds.stream()
						.noneMatch(area -> isContainerContainArea(
								modelSession.getContainer(
										tableType.getContainerForTable()),
								area))) {
			// Create empty table
			final Table emptyTable = TablemodelFactory.eINSTANCE.createTable();
			emptyTable.setTablecontent(
					TablemodelFactory.eINSTANCE.createTableContent());
			getModelService(tableInfo).buildHeading(emptyTable);
			return emptyTable;
		}

		final Table resultTable = filterRequestValue(EcoreUtil.copy(table),
				tableInfo, tableType, modelSession, controlAreaIds);
		clearEmptyRow(resultTable);
		sortTable(resultTable, tableInfo);
		return resultTable;

	}

	private static Table filterRequestValue(final Table table,
			final TableInfo tableInfo, final TableType tableType,
			final IModelSession modelsession,
			final Set<String> controlAreaIds) {
		final Table result = filterTableByState(table, tableType);
		if (tableInfo.category() == Pt1TableCategory.ETCS) {
			return result;
		}
		if (tableType == TableType.DIFF) {
			filterRowGroupBelongToControlAreaByDiffState(result, modelsession,
					controlAreaIds);
			result.getTablecontent()
					.getRowgroups()
					.removeIf(group -> !UrObjectExtensions
							.isPlanningObject(group.getLeadingObject()));
			return result;
		}

		result.getTablecontent().getRowgroups().removeIf(group -> {
			final Pair<Ur_Objekt, Ur_Objekt> initalFinalObj = getInitalFinalObj(
					group.getLeadingObject(), modelsession);
			final Ur_Objekt leadingObj = tableType == TableType.FINAL
					? initalFinalObj.getValue()
					: initalFinalObj.getKey();
			final MultiContainer_AttributeGroup container = modelsession
					.getContainer(tableType.getContainerForTable());
			final List<Stell_Bereich> areas = controlAreaIds.stream()
					.map(areaId -> getStellBereich(container, areaId))
					.toList();
			return !UrObjectExtensions.isPlanningObject(leadingObj)
					|| !areas.isEmpty() && areas.stream()
							.noneMatch(area -> StellBereichExtensions
									.isInControlArea(area, leadingObj));

		});
		return result;
	}

	private static Table filterTableByState(final Table table,
			final TableType tableType) {
		if (tableType == TableType.DIFF || tableType == TableType.SINGLE) {
			return table;
		}

		final List<TableRow> compareStateRows = TableExtensions
				.getTableRows(table)
				.stream()
				.filter(row -> row.getCells()
						.stream()
						.map(TableCell::getContent)
						.anyMatch(CompareStateCellContent.class::isInstance))
				.toList();
		if (compareStateRows.isEmpty()) {
			return table;
		}
		compareStateRows.forEach(row -> row.getCells()
				.stream()
				.filter(cell -> cell
						.getContent() instanceof CompareStateCellContent)
				.forEach(cell -> {
					final CompareStateCellContent compareCellContent = (CompareStateCellContent) cell
							.getContent();
					if (tableType == TableType.INITIAL) {
						cell.setContent(compareCellContent.getOldValue());
					} else if (tableType == TableType.FINAL) {
						cell.setContent(compareCellContent.getNewValue());
					}
				}));
		return table;
	}

	private static void filterRowGroupBelongToControlAreaByDiffState(
			final Table result, final IModelSession modelsession,
			final Set<String> controlAreaIds) {
		if (controlAreaIds.isEmpty()) {
			return;
		}
		final List<Stell_Bereich> initalControlAreas = controlAreaIds.stream()
				.map(areaId -> getStellBereich(
						modelsession.getContainer(ContainerType.INITIAL),
						areaId))
				.filter(Objects::nonNull)
				.toList();
		final List<Stell_Bereich> finalControlAreas = controlAreaIds.stream()
				.map(areaId -> getStellBereich(
						modelsession.getContainer(ContainerType.FINAL), areaId))
				.filter(Objects::nonNull)
				.toList();
		result.getTablecontent().getRowgroups().forEach(group -> {
			final Pair<Ur_Objekt, Ur_Objekt> initalFinalObj = getInitalFinalObj(
					group.getLeadingObject(), modelsession);
			final Ur_Objekt initalObj = initalFinalObj.getKey();
			final Ur_Objekt finalObj = initalFinalObj.getValue();

			final boolean isFinalObjBelongToAreas = finalObj != null
					&& finalControlAreas.stream()
							.anyMatch(area -> StellBereichExtensions
									.isInControlArea(area, finalObj));
			final boolean isInitialObjBelongToAreas = initalObj != null
					&& initalControlAreas.stream()
							.anyMatch(area -> StellBereichExtensions
									.isInControlArea(area, initalObj));
			if (isFinalObjBelongToAreas && isInitialObjBelongToAreas) {
				return;
			}

			if (!isFinalObjBelongToAreas && !isInitialObjBelongToAreas) {
				group.getRows().clear();
			}

			if (isFinalObjBelongToAreas) {
				group.getRows()
						.forEach(row -> handleTableRowNotBelongToArea()
								.accept(row, TableType.INITIAL));
			}

			if (isInitialObjBelongToAreas) {
				group.getRows()
						.forEach(row -> handleTableRowNotBelongToArea()
								.accept(row, TableType.FINAL));
			}
		});
	}

	private static Pair<Ur_Objekt, Ur_Objekt> getInitalFinalObj(
			final Ur_Objekt leadingObj, final IModelSession modelSession) {
		final Function<ContainerType, Ur_Objekt> getObjInContainer = containerType -> {
			final ContainerType currentType = UrObjectExtensions
					.getContainerType(leadingObj);
			final MultiContainer_AttributeGroup targetContainer = modelSession
					.getContainer(containerType);
			return currentType == containerType ? leadingObj
					: MultiContainer_AttributeGroupExtensions.getObject(
							targetContainer, leadingObj.getClass(),
							leadingObj.getIdentitaet().getWert());
		};
		return new Pair<>(getObjInContainer.apply(ContainerType.INITIAL),
				getObjInContainer.apply(ContainerType.FINAL));
	}

	private static BiConsumer<TableRow, TableType> handleTableRowNotBelongToArea() {
		return (final TableRow row, final TableType missingObjTableType) -> row
				.getCells()
				.forEach(cell -> {
					if (cell.getContent() instanceof final CompareStateCellContent compareCellContent) {
						if (missingObjTableType == TableType.INITIAL) {
							compareCellContent.setOldValue(null);
						} else {
							compareCellContent.setNewValue(null);
						}
						return;
					}
					final CompareStateCellContent compareContent = TablemodelFactory.eINSTANCE
							.createCompareStateCellContent();
					switch (missingObjTableType) {
						case INITIAL: {
							compareContent.setNewValue(cell.getContent());
							break;
						}
						case FINAL:
							compareContent.setOldValue(cell.getContent());
							break;
						default:
							throw new IllegalArgumentException();
					}
					final Optional<String> separator = EObjectExtensions
							.getNullableObject(cell.getContent(),
									CellContent::getSeparator);
					if (separator.isPresent()) {
						compareContent.setSeparator(separator.get());
					}
					cell.setContent(compareContent);
				});
	}

	private static void clearEmptyRow(final Table table) {
		table.getTablecontent()
				.getRowgroups()
				.forEach(group -> group.getRows()
						.removeIf(row -> row.getCells().isEmpty() || row
								.getCells()
								.stream()
								.allMatch(cell -> cell.getContent() == null
										|| CellContentExtensions
												.getPlainStringValue(
														cell.getContent())
												.isEmpty())));
		table.getTablecontent()
				.getRowgroups()
				.removeIf(group -> group.getRows().isEmpty());
	}

	private void storageWorknotes(final TableInfo tableInfo,
			final Table resultTable) {
		if (resultTable == null || resultTable.getTablecontent() == null) {
			return;
		}
		// Filter worknotes, which already in another tables visualation
		if (tableInfo.shortcut()
				.equalsIgnoreCase(ToolboxConstants.WORKNOTES_TABLE_SHORTCUT)) {
			// Special handle for fill Column C of Sxxx table
			workNotesPerTable.forEach((table, notes) -> {
				if (notes.isEmpty()) {
					return;
				}
				final TableNameInfo tableNameInfo = getTableNameInfo(table);
				FootnoteExtensions.fillSxxxTableColumnC(resultTable, notes,
						tableNameInfo.getShortName());
			});
			return;
		}
		final Set<FootnoteExtensions.WorkNotesUsage> tableNotes = FootnoteExtensions
				.getNotesInTable(resultTable);

		workNotesPerTable.compute(tableInfo, (k, tablNotes) -> {
			if (tablNotes == null) {
				return tableNotes;
			}

			tableNotes.forEach(workNote -> {
				final Optional<WorkNotesUsage> wn = tablNotes.stream()
						.filter(n -> n.ownerObj().equals(workNote.ownerObj()))
						.findFirst();
				if (wn.isEmpty()) {
					tablNotes.add(workNote);
					return;
				}
				wn.get().notes().addAll(workNote.notes());
			});

			return tablNotes;
		});
		// Reload Sxxx table only when all tables was transformed
		if (transformTableThreads.isEmpty()) {
			broker.send(Events.RELOAD_WORKNOTES_TABLE, null);
		}
	}

	private void saveTableToCache(final Table table,
			final IModelSession modelSession, final TableInfo tableInfo) {
		final String threadName = String.format("%s/saveCache", //$NON-NLS-1$
				tableInfo.shortcut());
		final PlanPro2TableTransformationService modelService = getModelService(
				tableInfo);
		// It will create a separate transformation for each table state, which
		// means each table state will have its own list of table errors.
		final Collection<TableError> errors = modelService.getTableErrors();
		final Thread storageCacheThread = new Thread(() -> {
			final Runnable storageFunc = () -> {
				final Cache cache = getCacheService().getCache(
						modelSession.getPlanProSchnittstelle(),
						ToolboxConstants.SHORTCUT_TO_TABLE_CACHE_ID);
				if (table != null) {
					cache.set(tableInfo.shortcut(), table);
				}
				saveTableError(tableInfo, modelSession, errors);
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
			final Set<TableInfo> tablesToTransfrom, final TableType tableType,
			final Set<String> controlAreaIds) {
		final Map<TableInfo, Table> result = new HashMap<>();
		monitor.beginTask(messages.TableOverviewPart_CalculateMissingTask,
				tablesToTransfrom.size());

		for (final TableInfo tableInfo : tablesToTransfrom) {
			try {
				final TableNameInfo nameInfo = getTableNameInfo(tableInfo);
				monitor.subTask(nameInfo.getFullDisplayName());
				final Table table = createDiffTable(tableInfo, tableType,
						controlAreaIds);
				while (!TableService.isTransformComplete(tableInfo, null)) {
					Thread.sleep(2000);
				}
				result.put(tableInfo, table);
				monitor.worked(1);
			} catch (final Exception e) {
				Thread.interrupted();
			}

		}
		monitor.done();
		return result;
	}

	@Override
	public Table createDiffTable(final TableInfo tableInfo,
			final TableType tableType, final Set<String> controlAreaIds) {
		Table mainSessionTable = null;
		try {
			mainSessionTable = transformToTable(tableInfo, tableType,
					sessionService.getLoadedSession(ToolboxFileRole.SESSION),
					controlAreaIds);
			if (sessionService.getLoadedSession(
					ToolboxFileRole.COMPARE_PLANNING) == null) {
				storageWorknotes(tableInfo, mainSessionTable);
				return mainSessionTable;
			}
			// Waiting table compare transform, then create compare table
			// between to plan
			while (!TableService.isTransformComplete(tableInfo, null)) {
				Thread.sleep(2000);
			}
		} catch (final Exception e) {
			logger.error("Transformation Error: {} : {}", //$NON-NLS-1$
					tableInfo.shortcut(), e.getMessage());
			nonTransformableTables.add(tableInfo);
			broker.post(Events.TABLEERROR_CHANGED, null);
			throw new RuntimeException(e);
		}

		// When it give Exception by transform second plan, then return the
		// first plan table
		try {
			final IModelSession compareSession = sessionService
					.getLoadedSession(ToolboxFileRole.COMPARE_PLANNING);
			final Table compareSessionTable = transformToTable(tableInfo,
					tableType, compareSession, controlAreaIds);
			final Table compareTable = diffServiceMap
					.get(TableCompareType.PROJECT)
					.createDiffTable(mainSessionTable, compareSessionTable);
			sortTable(compareTable, tableInfo);
			storageWorknotes(tableInfo, compareSessionTable);
			return compareTable;
		} catch (final Exception e) {
			dialogService.error(Display.getCurrent().getActiveShell(),
					messages.TableTransform_Error,
					messages.TableTransform_ComparePlanError_Msg, e);
			return mainSessionTable;
		}

	}

	@Override
	public void sortTable(final Table table, final TableInfo tableInfo) {
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

	@Override
	public Set<TableInfo> getNonTransformableTables(
			final Pt1TableCategory tableCategory) {
		return nonTransformableTables.stream()
				.filter(info -> info.category().equals(tableCategory))
				.collect(Collectors.toSet());
	}

	@SuppressWarnings("static-method")
	void clearInstance() {
		transformTableThreads.clear();
		nonTransformableTables.clear();
	}
}
