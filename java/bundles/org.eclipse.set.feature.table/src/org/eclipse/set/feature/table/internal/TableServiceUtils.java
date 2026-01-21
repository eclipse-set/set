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

import static org.eclipse.set.ppmodel.extensions.StellBereichExtensions.getStellBereich;
import static org.eclipse.set.ppmodel.extensions.StellBereichExtensions.isInControlArea;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.planpro.Block.Block_Anlage;
import org.eclipse.set.model.planpro.Block.Block_Element;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.CompareStateCellContent;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.ppmodel.extensions.BasisAttributExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.MultiContainer_AttributeGroupExtensions;
import org.eclipse.set.ppmodel.extensions.StellBereichExtensions;
import org.eclipse.set.ppmodel.extensions.UrObjectExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.table.TableError;
import org.eclipse.set.utils.table.TableInfo;
import org.eclipse.set.utils.table.TableInfo.Pt1TableCategory;
import org.eclipse.xtext.xbase.lib.Pair;

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

	}

	protected static List<TableError> getCachedTableError(
			final CacheService cachService, final TableInfo tableInfo,
			final IModelSession modelSession,
			final Set<String> controlAreaIds) {
		final Cache cache = cachService.getCache(
				modelSession.getPlanProSchnittstelle(),
				ToolboxConstants.CacheId.TABLE_ERRORS);
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
				getObj, controlAreaIds, modelSession, null);
	}

	protected static Table filterRequestValue(final Table table,
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
		// final List<Stell_Bereich> initalControlAreas =
		// controlAreaIds.stream()
		// .map(areaId -> getStellBereich(
		// modelsession.getContainer(ContainerType.INITIAL),
		// areaId))
		// .filter(Objects::nonNull)
		// .toList();
		// final List<Stell_Bereich> finalControlAreas = controlAreaIds.stream()
		// .map(areaId -> getStellBereich(
		// modelsession.getContainer(ContainerType.FINAL), areaId))
		// .filter(Objects::nonNull)
		// .toList();
		final List<RowGroup> relevantRowGroup = filterElementBelongToControlArea(
				result.getTablecontent().getRowgroups(),
				rowGroup -> UrObjektEachContainer.createInstance(
						rowGroup.getLeadingObject(), modelsession),
				controlAreaIds, modelsession,
				(rowGroup, notBelongToAreaState) -> rowGroup.getRows()
						.forEach(row -> handleTableRowNotBelongToArea()
								.accept(row, notBelongToAreaState)));
		result.getTablecontent().getRowgroups().forEach(group -> {
			if (!relevantRowGroup.contains(group)) {
				group.getRows().clear();
			}
		});
		// result.getTablecontent().getRowgroups().forEach(group -> {
		// final Pair<Ur_Objekt, Ur_Objekt> initalFinalObj = getInitalFinalObj(
		// group.getLeadingObject(), modelsession);
		// final Ur_Objekt initalObj = initalFinalObj.getKey();
		// final Ur_Objekt finalObj = initalFinalObj.getValue();
		//
		// final boolean isFinalObjBelongToAreas = isElementBelongToAreas(
		// finalObj, finalControlAreas);
		// final boolean isInitialObjBelongToAreas = isElementBelongToAreas(
		// initalObj, initalControlAreas);
		// if (isFinalObjBelongToAreas && isInitialObjBelongToAreas) {
		// return;
		// }
		//
		// if (!isFinalObjBelongToAreas && !isInitialObjBelongToAreas) {
		// group.getRows().clear();
		// }
		//
		// if (isFinalObjBelongToAreas) {
		// group.getRows()
		// .forEach(row -> handleTableRowNotBelongToArea()
		// .accept(row, TableType.INITIAL));
		// }
		//
		// if (isInitialObjBelongToAreas) {
		// group.getRows()
		// .forEach(row -> handleTableRowNotBelongToArea()
		// .accept(row, TableType.FINAL));
		// }
		// });
	}

	private static <T> List<T> filterElementBelongToControlArea(
			final List<T> listElement,
			final Function<T, UrObjektEachContainer> getUrObj,
			final Set<String> controlAreas, final IModelSession modelSession,
			final BiConsumer<T, TableType> handleByInitialOrFinalElementNotBelongToArea) {
		if (modelSession.getTableType() == TableType.SINGLE) {
			final List<Stell_Bereich> areas = controlAreas.stream()
					.map(area -> getStellBereich(
							modelSession.getContainer(ContainerType.SINGLE),
							area))
					.toList();
			return listElement.stream().filter(ele -> {
				final Ur_Objekt obj = getUrObj.apply(ele).singleObj();
				return isElementBelongToAreas(obj, areas);
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
			final boolean isInitialObjBelongToAreas = isElementBelongToAreas(
					objEachContainer.initalObj(), inititalControlAreas);
			final boolean isFinalObjBelongToAreas = isElementBelongToAreas(
					objEachContainer.finalObj(), finalControlAreas);
			if (isInitialObjBelongToAreas != isFinalObjBelongToAreas
					&& handleByInitialOrFinalElementNotBelongToArea != null) {
				handleByInitialOrFinalElementNotBelongToArea.accept(ele,
						isInitialObjBelongToAreas ? TableType.FINAL
								: TableType.INITIAL);
			}
			return isInitialObjBelongToAreas || isFinalObjBelongToAreas;
		}).toList();
	}

	private static Pair<Ur_Objekt, Ur_Objekt> getInitalFinalObj(
			final Ur_Objekt leadingObj, final IModelSession modelSession) {
		if (modelSession.getTableType() == TableType.SINGLE) {
			return new Pair<>(leadingObj, null);
		}
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

	private static boolean isElementBelongToAreas(final Ur_Objekt element,
			final List<Stell_Bereich> areas) {
		// Specially case for block element. When this block element not belong
		// to area, but this is relevant block element of another block element,
		// which belong to control area, then return true
		// See: ppmtab - General condition and
		// SslbTransformator#findRelevantBlockElements for more information
		if (element instanceof final Block_Element blockElement) {
			if (isInControlArea(areas, blockElement)) {
				return true;
			}
			final Optional<Block_Anlage> targetAnlage = Streams
					.stream(BasisAttributExtensions.getContainer(blockElement)
							.getBlockAnlage())
					.filter(blockAnlage -> blockAnlage.getIDBlockElementA()
							.getValue() == blockElement
							|| blockAnlage.getIDBlockElementB()
									.getValue() == blockElement)
					.findFirst();
			if (targetAnlage.isEmpty()) {
				return false;
			}
			final Block_Element anotherBlockElement = targetAnlage.get()
					.getIDBlockElementA()
					.getValue() == blockElement
							? targetAnlage.get().getIDBlockElementB().getValue()
							: targetAnlage.get()
									.getIDBlockElementA()
									.getValue();
			return isInControlArea(areas, anotherBlockElement);
		}
		return isInControlArea(areas, element);
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

}
