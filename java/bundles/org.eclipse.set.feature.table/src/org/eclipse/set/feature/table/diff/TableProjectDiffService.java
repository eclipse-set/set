/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.diff;

import static org.eclipse.set.ppmodel.extensions.EObjectExtensions.getNullableObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.CompareCellContent;
import org.eclipse.set.model.tablemodel.CompareTableCellContent;
import org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer;
import org.eclipse.set.model.tablemodel.FootnoteContainer;
import org.eclipse.set.model.tablemodel.MultiColorCellContent;
import org.eclipse.set.model.tablemodel.PlanCompareRow;
import org.eclipse.set.model.tablemodel.PlanCompareRowType;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions;
import org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions;
import org.eclipse.set.model.tablemodel.extensions.FootnoteContainerExtensions;
import org.eclipse.set.model.tablemodel.extensions.RowGroupExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableCellExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.services.table.TableDiffService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.common.collect.Streams;

/**
 * Create diff table between two loaded project
 * 
 * @author truong
 */
@Component(immediate = true, service = { TableDiffService.class })
public class TableProjectDiffService extends AbstractTableDiff {
	@Reference
	SessionService sessionService;

	@Override
	protected void compareRow(final TableRow mergedTableRow,
			final Table compareTable) {
		// The missing row is the row of compare table, which the main table
		// doesn't contains. Or the leading guid was changed
		// The missing row can be skip, because it was already with
		// CompareTableCellContent defined
		if (mergedTableRow instanceof PlanCompareRow) {
			return;
		}

		super.compareRow(mergedTableRow, compareTable);

		// When the row after compare contain only CompareTableCellContent, then
		// this row isn't exist in CompareTable, also complete new row
		if (isNewRow(mergedTableRow)) {
			final PlanCompareRow planCompareRow = TablemodelFactory.eINSTANCE
					.createPlanCompareRow();
			planCompareRow.setRowType(PlanCompareRowType.NEW_ROW);
			planCompareRow.getCells().addAll(mergedTableRow.getCells());
			final RowGroup group = TableRowExtensions.getGroup(mergedTableRow);
			final int indexOf = group.getRows().indexOf(mergedTableRow);
			group.getRows().remove(mergedTableRow);
			group.getRows().add(indexOf, planCompareRow);

		}
	}

	// The row, which give only in current plan, also main plan
	private static boolean isNewRow(final TableRow row) {
		return row.getCells()
				.stream()
				.map(TableCell::getContent)
				.allMatch(content -> {
					if (content instanceof final CompareTableCellContent compareCellContent) {
						return compareCellContent
								.getComparePlanCellContent() == null;
					}
					return false;
				});
	}

	@Override
	protected void addMissingRowGroup(final RowGroup compareTableRowGroup,
			final Table mergedTable) {
		final RowGroup match = findMatchRow(compareTableRowGroup, mergedTable);
		if (match == null) {
			super.addMissingRowGroup(compareTableRowGroup, mergedTable);
		}
	}

	private static RowGroup findMatchRow(final RowGroup compareTableRowGroup,
			final Table mergedTable) {
		final String groupGuid = EObjectExtensions
				.getNullableObject(compareTableRowGroup,
						g -> g.getLeadingObject().getIdentitaet().getWert())
				.orElse(null);
		final int groupId = compareTableRowGroup.getLeadingObjectIndex();
		final RowGroup match = TableExtensions.getGroupById(mergedTable,
				groupGuid, groupId);
		if (match != null) {
			return match;
		}

		// Determine the row group, which the leading guid was changed, but the
		// content aren't
		final RowGroup changeGUIDGroup = mergedTable.getTablecontent()
				.getRowgroups()
				.stream()
				.filter(group -> RowGroupExtensions
						.isEqual(compareTableRowGroup, group))
				.findFirst()
				.orElse(null);
		if (changeGUIDGroup != null) {
			createChangeGuidRowGroup(changeGUIDGroup);
			return changeGUIDGroup;
		}
		return null;
	}

	/**
	 * Create row group, which the content is same, but the leading guid was
	 * changed
	 * 
	 * @param mainTableRowGroup
	 * @param columns
	 *            the table {@link ColumnDescriptor}
	 */
	private static void createChangeGuidRowGroup(
			final RowGroup mainTableRowGroup) {
		final List<PlanCompareRow> compareRows = new ArrayList<>();
		for (final TableRow row : mainTableRowGroup.getRows()) {
			final PlanCompareRow planCompareRow = TablemodelFactory.eINSTANCE
					.createPlanCompareRow();
			planCompareRow.setRowType(PlanCompareRowType.CHANGED_GUID_ROW);
			planCompareRow.getCells().addAll(row.getCells());
			planCompareRow.getCells().forEach(cell -> {
				final CompareTableCellContent tableCompareCell = TablemodelFactory.eINSTANCE
						.createCompareTableCellContent();
				final CellContent content = cell.getContent();
				tableCompareCell
						.setComparePlanCellContent(EcoreUtil.copy(content));
				tableCompareCell
						.setMainPlanCellContent(EcoreUtil.copy(content));
				tableCompareCell.setSeparator(content.getSeparator());
				cell.setContent(tableCompareCell);
				compareRows.add(planCompareRow);
			});
		}

		// Replace to compare row
		mainTableRowGroup.getRows().clear();
		mainTableRowGroup.getRows().addAll(compareRows);
	}

	@Override
	protected TableRow createMissingRow(final List<ColumnDescriptor> columns) {
		final PlanCompareRow missingRow = TablemodelFactory.eINSTANCE
				.createPlanCompareRow();
		columns.forEach(col -> {
			final TableCell cell = TablemodelFactory.eINSTANCE
					.createTableCell();
			cell.setColumndescriptor(col);
			final CompareTableCellContent compareContent = TablemodelFactory.eINSTANCE
					.createCompareTableCellContent();
			final StringCellContent strContent = TablemodelFactory.eINSTANCE
					.createStringCellContent();
			strContent.getValue().add(""); //$NON-NLS-1$
			compareContent.setComparePlanCellContent(strContent);
			cell.setContent(compareContent);
			missingRow.getCells().add(cell);
		});
		missingRow.setRowType(PlanCompareRowType.REMOVED_ROW);
		return missingRow;
	}

	@Override
	CellContent createDiffContent(final TableCell mainTableCell,
			final TableCell compareTableCell) {
		final CellContent mainTableCellContent = getNullableObject(
				mainTableCell, TableCell::getContent).orElse(null);
		final CellContent compareTableCellContent = getNullableObject(
				compareTableCell, TableCell::getContent).orElse(null);

		if (isSameValue(mainTableCellContent, compareTableCellContent)) {
			return null;
		}

		// Replace FootnoteContainer in main table row
		if (isFootnoteCellContent(mainTableCellContent)) {
			final TableRow mainRow = TableCellExtensions
					.getTableRow(mainTableCell);
			final FootnoteContainer mainFootnoteContainer = mainRow
					.getFootnotes();
			final TableRow compareRow = EObjectExtensions
					.getNullableObject(compareTableCell,
							TableCellExtensions::getTableRow)
					.orElse(null);
			final FootnoteContainer compareFootnoteContainer = compareRow == null
					? TablemodelFactory.eINSTANCE
							.createSimpleFootnoteContainer()
					: compareRow.getFootnotes();
			mainRow.setFootnotes(createCompareTableFootnoteContainer(
					mainFootnoteContainer, compareFootnoteContainer));

		}
		final CompareTableCellContent cellContent = TablemodelFactory.eINSTANCE
				.createCompareTableCellContent();
		cellContent.setMainPlanCellContent(mainTableCellContent);
		cellContent.setComparePlanCellContent(
				compareTableCellContent == null ? null
						: compareTableCellContent);

		return cellContent;
	}

	@Override
	SessionService getSessionService() {
		return sessionService;
	}

	/**
	 * @param first
	 *            the first cell
	 * @param second
	 *            the second cell
	 * @return true, if the table cell haven same value
	 */
	protected boolean isSameValue(final CellContent first,
			final CellContent second) {
		if (first == null || second == null) {
			return first == second;
		}

		if (isFootnoteCellContent(first) && isFootnoteCellContent(second)) {
			return isSameFootnoteContent(first, second);
		}

		return switch (first) {
			case final StringCellContent stringContent -> isSameValue(
					stringContent, second);
			case final CompareCellContent compareContent -> isSameValue(
					compareContent, second);
			case final MultiColorCellContent multiColorContent -> isSameValue(
					multiColorContent, second);
			default -> true;
		};
	}

	protected static boolean isSameFootnoteContent(final CellContent first,
			final CellContent second) {
		final TableCell mainTableCell = CellContentExtensions
				.getTableCell(first);
		final TableCell compareTableCell = CellContentExtensions
				.getTableCell(second);
		final TableRow mainRow = TableCellExtensions.getTableRow(mainTableCell);
		final TableRow compareRow = TableCellExtensions
				.getTableRow(compareTableCell);
		// The mainRow & comapreRow can't be null here, was changed before
		return FootnoteContainerExtensions.isSameFootnotesComment(
				mainRow.getFootnotes(), compareRow.getFootnotes());
	}

	private static CompareTableFootnoteContainer createCompareTableFootnoteContainer(
			final FootnoteContainer mainTableFootnotes,
			final FootnoteContainer compareTableFootnotes) {
		final CompareTableFootnoteContainer compareTableFootnoteContainer = TablemodelFactory.eINSTANCE
				.createCompareTableFootnoteContainer();
		compareTableFootnoteContainer
				.setComparePlanFootnoteContainer(compareTableFootnotes);
		compareTableFootnoteContainer
				.setMainPlanFootnoteContainer(mainTableFootnotes);
		return compareTableFootnoteContainer;
	}

	/**
	 * @param firstCellContent
	 *            the {@link CompareCellContent}
	 * @param secondCellContent
	 *            the {@link CellContent}
	 * @return true, if the table cell haven same value
	 */
	protected boolean isSameValue(final CompareCellContent firstCellContent,
			final CellContent secondCellContent) {
		// Compare between CompareCellContent with another cell content is only
		// in compare table between two Project available.
		// That mean by table DIFF state will compare FINAL state
		final TableType tableType = getSessionService()
				.getLoadedSession(ToolboxFileRole.SESSION)
				.getTableType();
		final StringCellContent compareCellStringContent = TablemodelFactory.eINSTANCE
				.createStringCellContent();
		if (tableType == TableType.INITIAL) {
			compareCellStringContent.getValue()
					.addAll(firstCellContent.getOldValue());
		} else {
			compareCellStringContent.getValue()
					.addAll(firstCellContent.getNewValue());
		}
		return isSameValue(compareCellStringContent, secondCellContent);
	}

	/**
	 * @param firstCellContent
	 *            the {@link MultiColorCellContent}
	 * @param secondCellContent
	 *            the {@link CellContent}
	 * @return true, if the table cell haven same value
	 */
	protected boolean isSameValue(final MultiColorCellContent firstCellContent,
			final CellContent secondCellContent) {
		return firstCellContent.getValue().stream().allMatch(value -> {
			final StringCellContent stringCellContent = TablemodelFactory.eINSTANCE
					.createStringCellContent();
			if (value.getMultiColorValue() == null
					|| value.getMultiColorValue().isEmpty()) {
				stringCellContent.getValue().add(value.getStringFormat());

			} else {
				stringCellContent.getValue()
						.add(String.format(value.getStringFormat(),
								value.getMultiColorValue()));
			}

			return isSameValue(stringCellContent, secondCellContent);
		});
	}

	/**
	 * @param firstCellContent
	 *            the {@link StringCellContent}
	 * @param secondCellContent
	 *            the {@link CellContent}
	 * @return true, if the table cell haven same value
	 */
	protected boolean isSameValue(final StringCellContent firstCellContent,
			final CellContent secondCellContent) {
		final Set<String> firstValues = Streams
				.stream(CellContentExtensions
						.getStringValueIterable(firstCellContent))
				.filter(value -> value != null && !value.trim().isEmpty())
				.collect(Collectors.toSet());
		if (firstValues.contains(CellContentExtensions.HOURGLASS_ICON)) {
			return false;
		}
		return switch (secondCellContent) {
			case final StringCellContent stringContent -> {
				final Set<String> secondValues = Streams
						.stream(CellContentExtensions
								.getStringValueIterable(stringContent))
						.filter(value -> value != null
								&& !value.trim().isEmpty())
						.collect(Collectors.toSet());
				yield firstValues.equals(secondValues);
			}
			case final CompareCellContent compareContent -> {
				final TableType tableType = getSessionService()
						.getLoadedSession(ToolboxFileRole.SESSION)
						.getTableType();
				final Set<String> values = switch (tableType) {
					case TableType.INITIAL -> compareContent.getOldValue()
							.stream()
							.filter(value -> value != null
									&& !value.trim().isEmpty())
							.collect(Collectors.toSet());
					default -> compareContent.getNewValue()
							.stream()
							.filter(value -> value != null
									&& !value.trim().isEmpty())
							.collect(Collectors.toSet());

				};
				yield firstValues.equals(values);
			}
			case final MultiColorCellContent multiColorContent -> {
				final Set<String> values = multiColorContent.getValue()
						.stream()
						.map(content -> {
							if (content.getMultiColorValue() == null
									|| content.getMultiColorValue().isEmpty()) {
								return content.getStringFormat();
							}
							return String.format(content.getStringFormat(),
									content.getMultiColorValue());
						})
						.collect(Collectors.toSet());
				yield firstValues.equals(values);
			}
			default -> false;
		};
	}

	private static boolean isFootnoteCellContent(
			final CellContent cellContent) {
		return cellContent != null
				&& ColumnDescriptorExtensions.isFootnoteReferenceColumn(
						CellContentExtensions.getTableCell(cellContent)
								.getColumndescriptor());
	}

	@Override
	public TableCompareType getCompareType() {
		return TableCompareType.PROJECT;
	}
}
