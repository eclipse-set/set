/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.internal;

import static org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.HOURGLASS_ICON;
import static org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.getStringValueIterable;
import static org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.getLeadingObjectGuid;
import static org.eclipse.set.ppmodel.extensions.StellBereichExtensions.getStellBereich;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.CompareFootnoteContainer;
import org.eclipse.set.model.tablemodel.CompareStateCellContent;
import org.eclipse.set.model.tablemodel.CompareTableCellContent;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.MultiContainer_AttributeGroupExtensions;
import org.eclipse.set.ppmodel.extensions.UrObjectExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.services.table.TableService;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.events.TableDataChangeEvent;
import org.eclipse.set.utils.table.Pt1TableChangeProperties;
import org.eclipse.set.utils.table.TableError;
import org.eclipse.set.utils.table.TableInfo;
import org.eclipse.set.utils.table.TableInfo.Pt1TableCategory;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.collect.Streams;

/**
 * 
 */
public class TableServiceUtils {

	private static record UrObjektEachContainer(Ur_Objekt initalObj,
			Ur_Objekt finalObj, Ur_Objekt singleObj) {

		protected static UrObjektEachContainer createInstance(
				final Ur_Objekt obj, final IModelSession modelSession) {
			if (modelSession.getTableType() == TableType.SINGLE) {
				return new UrObjektEachContainer(null, null, obj);
			}

			final Function<ContainerType, Ur_Objekt> getObjInContainer = containerType -> {
				final ContainerType currentType = UrObjectExtensions
						.getContainerType(obj);
				final MultiContainer_AttributeGroup targetContainer = modelSession
						.getContainer(containerType);
				return currentType == containerType ? obj
						: MultiContainer_AttributeGroupExtensions.getObject(
								targetContainer, obj.getClass(),
								obj.getIdentitaet().getWert());
			};
			return new UrObjektEachContainer(
					getObjInContainer.apply(ContainerType.INITIAL),
					getObjInContainer.apply(ContainerType.FINAL), null);
		}

		public Ur_Objekt getObject(final TableType tableType) {
			return switch (tableType) {
				case INITIAL -> initalObj;
				case FINAL -> finalObj;
				case SINGLE -> singleObj;
				default -> null;
			};
		}

	}

	protected static List<TableError> getCachedTableError(
			final CacheService cachService, final TableInfo tableInfo,
			final IModelSession modelSession,
			final PlanPro2TableTransformationService transformationService,
			final Set<String> controlAreaIds) {
		final Cache cache = cachService.getCache(
				modelSession.getPlanProSchnittstelle(),
				ToolboxConstants.CacheId.TABLE_ERRORS);
		if (!cache.contains(tableInfo.shortcut())) {
			return null;
		}
		final List<TableError> cachedErrors = cache.get(tableInfo.shortcut(),
				Collections::emptyList);
		if (cachedErrors.isEmpty()) {
			return cachedErrors;
		}
		final TableType tableType = modelSession.getTableType();
		final List<TableError> errorsByCurrentTableState = cachedErrors.stream()
				.filter(error -> tableType == TableType.DIFF
						|| tableType == TableType.SINGLE
						|| tableType == error.getContainerType()
								.getDefaultTableType())
				.toList();
		if (controlAreaIds.isEmpty()) {
			return errorsByCurrentTableState.stream()
					.filter(error -> UrObjectExtensions
							.isPlanningObject(error.getLeadingObject()))
					.toList();
		}
		final Function<TableError, UrObjektEachContainer> getObj = error -> switch (error
				.getContainerType()) {
			case INITIAL -> new UrObjektEachContainer(error.getLeadingObject(),
					null, null);
			case FINAL -> new UrObjektEachContainer(null,
					error.getLeadingObject(), null);
			case SINGLE -> new UrObjektEachContainer(null, null,
					error.getLeadingObject());
			default -> throw new IllegalArgumentException();
		};
		return filterElementBelongToControlArea(errorsByCurrentTableState,
				getObj, controlAreaIds, modelSession, transformationService,
				null);
	}

	/**
	 * Provides a list of all not yet generated tables.
	 * 
	 * @param tableService
	 *            the table service to fetch available tables
	 * @param modelSession
	 *            the current model session
	 * @param controlAreaIds
	 *            the set of control area id's
	 * @return list of tables that are not yet generated
	 */
	public static Collection<TableInfo> getMissingTables(
			final TableService tableService, final IModelSession modelSession,
			final Set<String> controlAreaIds) {
		return getMissingTables(tableService, modelSession, controlAreaIds,
				null);
	}

	/**
	 * Provides a list of all not yet generated tables.
	 * 
	 * @param tableService
	 *            the table service to fetch available tables
	 * @param modelSession
	 *            the current model session
	 * @param controlAreaIds
	 *            the set of control area id's
	 * @param tableCategory
	 *            an optional table category to only calculate tables of this
	 *            category. Provide null if this filter shall not be applied
	 * @return list of tables that are not yet generated
	 */
	public static Collection<TableInfo> getMissingTables(
			final TableService tableService, final IModelSession modelSession,
			final Set<String> controlAreaIds,
			final Pt1TableCategory tableCategory) {
		final Map<TableInfo, Collection<TableError>> computedErrors = tableService
				.getTableErrors(modelSession, controlAreaIds, tableCategory);
		final Collection<TableInfo> allTableInfos = tableService
				.getAvailableTables()
				.stream()
				.filter(table -> tableCategory == null
						|| table.category().equals(tableCategory))
				.toList();

		final ArrayList<TableInfo> missingTables = new ArrayList<>();
		missingTables.addAll(allTableInfos);
		if (!ToolboxConfiguration.isDebugMode()) {
			// in debug mode we want to be able to recompute the errors
			// that's why we mark all as missing
			missingTables
					.removeIf(info -> computedErrors.keySet().contains(info));
		}
		return missingTables;
	}

	/**
	 * Generates all not yet generated tables.
	 * 
	 * @param tableService
	 *            the table service to fetch not yet generated tables tables
	 * @param modelSession
	 *            the current model session
	 * @param controlAreaIds
	 *            the set of control area id's
	 * @param monitor
	 *            the monitor to display progress
	 * @param messages
	 *            the translated messages for displaying in the progress monitor
	 */
	public static void calculateAllMissingTables(
			final TableService tableService, final IModelSession modelSession,
			final Set<String> controlAreaIds, final IProgressMonitor monitor,
			final Messages messages) {
		calculateAllMissingTables(tableService, modelSession, controlAreaIds,
				null, monitor, messages);
	}

	/**
	 * Generates all not yet generated tables.
	 * 
	 * @param tableService
	 *            the table service to fetch not yet generated tables tables
	 * @param modelSession
	 *            the current model session
	 * @param controlAreaIds
	 *            the set of control area id's
	 * @param tableCategory
	 *            an optional table category to only calculate tables of this
	 *            category. Provide null if this filter shall not be applied
	 * @param monitor
	 *            the monitor to display progress
	 * @param messages
	 *            the translated messages for displaying in the progress monitor
	 */
	public static void calculateAllMissingTables(
			final TableService tableService, final IModelSession modelSession,
			final Set<String> controlAreaIds,
			final Pt1TableCategory tableCategory,
			final IProgressMonitor monitor, final Messages messages) {
		final Collection<TableInfo> missingTables = getMissingTables(
				tableService, modelSession, controlAreaIds, tableCategory);
		monitor.beginTask(messages.TableOverviewPart_CalculateMissingTask,
				missingTables.size());
		if (!modelSession.isSingleState()) {
			tableService.transformTables(monitor, new HashSet<>(missingTables),
					TableType.DIFF, controlAreaIds);
		} else {
			tableService.transformTables(monitor, new HashSet<>(missingTables),
					TableType.SINGLE, controlAreaIds);
		}
	}

	protected static Table filterRequestValue(final Table table,
			final TableType tableType, final TableInfo tableInfo,
			final IModelSession modelsession,
			final PlanPro2TableTransformationService transformationService,
			final Set<String> controlAreaIds) {
		final Table result = filterTableByState(table, tableType);
		// Worknotes table need only regard on table state
		if (tableInfo.shortcut()
				.equalsIgnoreCase(ToolboxConstants.WORKNOTES_TABLE_SHORTCUT)) {
			return result;
		}
		if (tableType == TableType.DIFF) {
			filterRowGroupBelongToControlAreaByDiffState(result, modelsession,
					transformationService, controlAreaIds);
			result.getTablecontent()
					.getRowgroups()
					.removeIf(group -> !UrObjectExtensions
							.isPlanningObject(group.getLeadingObject()));
			return result;
		}
		result.getTablecontent().getRowgroups().removeIf(group -> {
			final UrObjektEachContainer objectEachContanier = UrObjektEachContainer
					.createInstance(group.getLeadingObject(), modelsession);
			final Ur_Objekt leadingObj = objectEachContanier
					.getObject(tableType);
			final MultiContainer_AttributeGroup container = modelsession
					.getContainer(tableType.getContainerForTable());
			final List<Stell_Bereich> areas = controlAreaIds.stream()
					.map(areaId -> getStellBereich(container, areaId))
					.toList();
			return !transformationService
					.isObjectBelongToRendereArea(leadingObj)
					|| !transformationService
							.isObjectBelongToRendereArea(leadingObj, areas);

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
		compareStateRows
				.forEach(row -> transformCompareCellContent(row, tableType));

		TableExtensions.getTableRows(table)
				.stream()
				.filter(row -> row
						.getFootnotes() instanceof CompareFootnoteContainer)
				.forEach(row -> transformCompareFootnote(row, tableType));
		return table;
	}

	private static void transformCompareCellContent(final TableRow compareRow,
			final TableType tableType) {
		compareRow.getCells()
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
				});
	}

	private static void transformCompareFootnote(final TableRow compareRow,
			final TableType tableType) {
		if (compareRow
				.getFootnotes() instanceof final CompareFootnoteContainer compareFnContainer) {
			final SimpleFootnoteContainer simpleFootnoteContainer = TablemodelFactory.eINSTANCE
					.createSimpleFootnoteContainer();
			simpleFootnoteContainer.getFootnotes()
					.addAll(compareFnContainer.getUnchangedFootnotes()
							.getFootnotes());
			switch (tableType) {
				case INITIAL:
					simpleFootnoteContainer.getFootnotes()
							.addAll(compareFnContainer.getOldFootnotes()
									.getFootnotes());
					break;
				case FINAL:
					simpleFootnoteContainer.getFootnotes()
							.addAll(compareFnContainer.getNewFootnotes()
									.getFootnotes());
					break;
				default:
					return;
			}
			compareRow.setFootnotes(simpleFootnoteContainer);
		}
	}

	private static void filterRowGroupBelongToControlAreaByDiffState(
			final Table result, final IModelSession modelsession,
			final PlanPro2TableTransformationService transformationService,
			final Set<String> controlAreaIds) {
		if (controlAreaIds.isEmpty()) {
			return;
		}
		final List<RowGroup> relevantRowGroup = filterElementBelongToControlArea(
				result.getTablecontent().getRowgroups(),
				rowGroup -> UrObjektEachContainer.createInstance(
						rowGroup.getLeadingObject(), modelsession),
				controlAreaIds, modelsession, transformationService,
				(rowGroup, notBelongToAreaState) -> rowGroup.getRows()
						.forEach(row -> handleTableRowNotBelongToArea()
								.accept(row, notBelongToAreaState)));
		result.getTablecontent()
				.getRowgroups()
				.removeIf(group -> !relevantRowGroup.contains(group));
	}

	private static <T> List<T> filterElementBelongToControlArea(
			final List<T> listElement,
			final Function<T, UrObjektEachContainer> getUrObj,
			final Set<String> controlAreas, final IModelSession modelSession,
			final PlanPro2TableTransformationService transformationService,
			final BiConsumer<T, TableType> handleByInitialOrFinalElementNotBelongToArea) {
		if (modelSession.getTableType() == TableType.SINGLE) {
			final List<Stell_Bereich> areas = controlAreas.stream()
					.map(area -> getStellBereich(
							modelSession.getContainer(ContainerType.SINGLE),
							area))
					.toList();
			return listElement.stream().filter(ele -> {
				final Ur_Objekt obj = getUrObj.apply(ele).singleObj();
				return transformationService.isObjectBelongToRendereArea(obj,
						areas);
			}).toList();
		}

		final List<Stell_Bereich> inititalControlAreas = controlAreas.stream()
				.map(area -> getStellBereich(
						modelSession.getContainer(ContainerType.INITIAL), area))
				.filter(Objects::nonNull)
				.toList();
		final List<Stell_Bereich> finalControlAreas = controlAreas.stream()
				.map(area -> getStellBereich(
						modelSession.getContainer(ContainerType.FINAL), area))
				.filter(Objects::nonNull)
				.toList();

		return listElement.stream().filter(ele -> {
			final UrObjektEachContainer objEachContainer = getUrObj.apply(ele);
			final boolean isInitialObjBelongToAreas = !inititalControlAreas
					.isEmpty()
					&& transformationService.isObjectBelongToRendereArea(
							objEachContainer.initalObj, inititalControlAreas);
			final boolean isFinalObjBelongToAreas = !finalControlAreas.isEmpty()
					&& transformationService.isObjectBelongToRendereArea(
							objEachContainer.finalObj, finalControlAreas);
			if (isInitialObjBelongToAreas != isFinalObjBelongToAreas
					&& handleByInitialOrFinalElementNotBelongToArea != null) {
				handleByInitialOrFinalElementNotBelongToArea.accept(ele,
						isInitialObjBelongToAreas ? TableType.FINAL
								: TableType.INITIAL);
			}
			return isInitialObjBelongToAreas || isFinalObjBelongToAreas;
		}).toList();
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

	protected static void clearEmptyRow(final Table table) {
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

	/**
	 * Update table content with data from {@link TableDataChangeEvent}
	 * 
	 * @param tableRows
	 *            the rows to update
	 * @param changedDatas
	 *            the new data
	 * @param tableType
	 *            the table type
	 * @param sessionService
	 *            the {@link SessionService}
	 */
	public static void updateTableContent(final List<TableRow> tableRows,
			final List<Pt1TableChangeProperties> changedDatas,
			final TableType tableType, final SessionService sessionService) {
		if (tableType != TableType.DIFF) {
			changedDatas.forEach(data -> {
				if (tableType.getContainerForTable() == data
						.getContainerType()) {
					final Optional<TableRow> first = tableRows.stream()
							.filter(e -> e.equals(data.getRow())
									|| getLeadingObjectGuid(e)
											.equals(getLeadingObjectGuid(
													data.getRow())))
							.findFirst();
					if (first.isEmpty()) {
						return;
					}
					TableRowExtensions.set(first.get(),
							data.getChangeDataColumn(), data.getNewValues(),
							data.getSeparator());
				}
			});
			return;
		}

		final List<Pair<TableRow, Pt1TableChangeProperties>> changedDataRow = changedDatas
				.stream()
				.map(data -> {
					final TableRow changedRow = tableRows.stream()
							.filter(row -> row == data.getRow()
									|| getLeadingObjectGuid(row)
											.equals(getLeadingObjectGuid(
													data.getRow())))
							.findFirst()
							.orElse(null);
					if (changedRow == null) {
						return null;
					}
					return new Pair<>(changedRow, data);
				})
				.filter(Objects::nonNull)
				.toList();
		if (changedDataRow.isEmpty()) {
			return;
		}
		changedDataRow.forEach(row -> {
			final TableCell cell = TableRowExtensions.getCell(row.getFirst(),
					row.getSecond().getChangeDataColumn());
			final CellContent newContent = getNewContent(cell.getContent(),
					row.getSecond(), sessionService);
			cell.setContent(newContent);
		});
	}

	private static CellContent getNewContent(final CellContent oldContent,
			final Pt1TableChangeProperties properties,
			final SessionService sessionService) {
		return switch (oldContent) {
			case final StringCellContent stringContent -> getNewContent(
					stringContent, properties);
			case final CompareStateCellContent compareContent -> getNewContent(
					compareContent, properties);
			case final CompareTableCellContent compareTableContent -> getNewContent(
					compareTableContent, properties, sessionService);
			default -> throw new UnsupportedOperationException();
		};
	}

	private static CellContent getNewContent(final StringCellContent oldContent,
			final Pt1TableChangeProperties properties) {
		final List<String> currentValues = StreamSupport
				.stream(getStringValueIterable(oldContent).spliterator(), false)
				.toList();
		if (currentValues.size() == 1
				&& currentValues.getFirst().equals(HOURGLASS_ICON)) {
			final StringCellContent newContent = TablemodelFactory.eINSTANCE
					.createStringCellContent();
			newContent.getValue().addAll(properties.getNewValues());
			newContent.setSeparator(properties.getSeparator());
			return newContent;
		}

		if (!equalsValues(currentValues, properties.getNewValues())) {
			if (properties.getContainerType() == ContainerType.INITIAL) {
				return createCompareCellContent(properties.getNewValues(),
						currentValues, oldContent.getSeparator());
			}
			return createCompareCellContent(currentValues,
					properties.getNewValues(), oldContent.getSeparator());
		}

		return oldContent;
	}

	private static CellContent getNewContent(
			final CompareStateCellContent oldContent,
			final Pt1TableChangeProperties properties) {
		final ContainerType containerType = properties.getContainerType();
		final List<String> oldValues = IterableExtensions
				.toList(CellContentExtensions
						.getStringValueIterable(oldContent.getOldValue()));
		final List<String> newValues = IterableExtensions
				.toList(CellContentExtensions
						.getStringValueIterable(oldContent.getNewValue()));
		switch (containerType) {
			case FINAL:
				if (!equalsValues(newValues, properties.getNewValues())) {
					return createCompareCellContent(oldValues,
							properties.getNewValues(),
							oldContent.getSeparator());
				}
				break;
			case INITIAL:
				if (!equalsValues(oldValues, properties.getNewValues())) {
					return createCompareCellContent(properties.getNewValues(),
							newValues, oldContent.getSeparator());
				}
				break;
			default:
				throw new IllegalArgumentException(
						"SingelState can't have compare cell content"); //$NON-NLS-1$
		}
		return null;
	}

	private static CellContent getNewContent(
			final CompareTableCellContent oldContent,
			final Pt1TableChangeProperties properties,
			final SessionService sessionService) {
		final PlanPro_Schnittstelle planProSchnittstelle = properties
				.getPlanProSchnittstelle();

		final Optional<Entry<ToolboxFileRole, IModelSession>> targetSession = sessionService
				.getLoadedSessions()
				.entrySet()
				.stream()
				.filter(entry -> entry.getValue()
						.getPlanProSchnittstelle()
						.equals(planProSchnittstelle))
				.findFirst();
		if (targetSession.isEmpty()) {
			return null;
		}
		final CompareTableCellContent clone = EcoreUtil.copy(oldContent);
		switch (targetSession.get().getKey()) {
			case SESSION: {
				clone.setMainPlanCellContent(
						getNewContent(oldContent.getMainPlanCellContent(),
								properties, sessionService));
				break;
			}
			case COMPARE_PLANNING: {
				clone.setComparePlanCellContent(
						getNewContent(oldContent.getComparePlanCellContent(),
								properties, sessionService));
				break;
			}
			default:
				return null;
		}

		final Set<String> mainPlanCellValues = Streams
				.stream(CellContentExtensions
						.getStringValueIterable(clone.getMainPlanCellContent()))
				.filter(value -> value != null && !value.trim().isEmpty())
				.collect(Collectors.toSet());
		final Set<String> comparePlanCellValues = Streams
				.stream(CellContentExtensions.getStringValueIterable(
						clone.getComparePlanCellContent()))
				.filter(value -> value != null && !value.trim().isEmpty())
				.collect(Collectors.toSet());
		return mainPlanCellValues.equals(comparePlanCellValues)
				? clone.getMainPlanCellContent()
				: clone;
	}

	private static CompareStateCellContent createCompareCellContent(
			final List<String> oldValues, final List<String> newValues,
			final String separator) {
		final CompareStateCellContent compareContent = TablemodelFactory.eINSTANCE
				.createCompareStateCellContent();
		compareContent.setOldValue(
				CellContentExtensions.createStringCellContent(oldValues));
		compareContent.setNewValue(
				CellContentExtensions.createStringCellContent(newValues));
		compareContent.setSeparator(separator);
		return compareContent;
	}

	private static boolean equalsValues(final List<String> oldValues,
			final List<String> newValues) {
		return oldValues.size() == newValues.size()
				&& oldValues.stream().allMatch(newValues::contains);
	}

}
