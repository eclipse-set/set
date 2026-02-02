/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table;

import static org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.getStringValueIterable;

import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.CompareStateCellContent;
import org.eclipse.set.model.tablemodel.RowMergeMode;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;

import com.google.common.collect.Streams;

/**
 * Helper class to calculate table spans
 * 
 * @author Stuecker
 *
 */
public class TableSpanUtils {
	/**
	 * @param rows
	 *            the table rows
	 */
	public TableSpanUtils(final List<TableRow> rows) {
		this.instances = rows;
	}

	private final List<TableRow> instances;

	/**
	 * @param row
	 *            column
	 * @param column
	 *            column
	 * @return the number of mergable cells above the row in the given column
	 */
	public int getRowSpanUp(final int column, final int row) {
		if (row == 0) {
			return 0;
		}
		if (getCellsMergable(column, row, row - 1)) {
			return 1 + getRowSpanUp(column, row - 1);
		}
		return 0;
	}

	/**
	 * @param column
	 *            column
	 * @param row
	 *            row
	 * @return the number of mergable cells below the row in the given column
	 */
	public int getRowSpanDown(final int column, final int row) {
		if (row + 1 >= instances.size()) {
			return 0;
		}
		if (getCellsMergable(column, row, row + 1)) {
			return 1 + getRowSpanDown(column, row + 1);
		}
		return 0;
	}

	/**
	 * Decides whether two cells in a column are mergable
	 */
	private boolean getCellsMergable(final int column, final int rowIndexA,
			final int rowIndexB) {
		final TableRow rowA = instances.get(rowIndexA);
		final TableRow rowB = instances.get(rowIndexB);

		// For cells to be mergable they must:
		// Be contained within the same row
		if (TableRowExtensions.getGroup(rowA) != TableRowExtensions
				.getGroup(rowB)) {
			return false;
		}
		final CellContent cellContentA = TableRowExtensions.getContent(rowA)
				.get(column);
		final CellContent cellContentB = TableRowExtensions.getContent(rowB)
				.get(column);
		if (cellContentA == null) {
			return cellContentB == null;
		}
		if (isEqual(cellContentA, cellContentB)) {
			return true;
		}

		// The cell value must not be same to be merge. When the RowMergeMode ==
		// ENABLE, then should be cells anyway merge.
		// Here replace the cell content by priority : StringCellContent ->
		// CompareCellContent (both value not empty or new value not empty) will
		// set to the second cell
		final boolean shouldReplaceValue = shouldReplaceValue(cellContentA,
				cellContentB);
		if (shouldReplaceValue) {
			rowB.getCells()
					.get(column)
					.setContent(EcoreUtil.copy(cellContentA));
		}
		return shouldReplaceValue;
	}

	private static boolean shouldReplaceValue(final CellContent cellContentA,
			final CellContent cellContentB) {
		return switch (cellContentA) {
			case final StringCellContent stringCellContentA -> !isEmptyCellContentValue(
					stringCellContentA);
			case final CompareStateCellContent compareCellContentA -> {
				if (!isEmptyCellContentValue(compareCellContentA.getNewValue())
						&& !isEmptyCellContentValue(
								compareCellContentA.getOldValue())) {
					yield true;
				}
				if (cellContentB instanceof final StringCellContent stringCellContentB) {
					yield isEmptyCellContentValue(stringCellContentB);
				}

				if (cellContentB instanceof final CompareStateCellContent compareCellContentB) {
					yield !isEmptyCellContentValue(
							compareCellContentA.getNewValue())
							&& (isEmptyCellContentValue(
									compareCellContentB.getNewValue())
									|| isEmptyCellContentValue(
											compareCellContentB.getOldValue()));
				}
				yield true;
			}
			default -> false;
		};
	}

	private static boolean isEqual(final CellContent cellContentA,
			final CellContent cellContentB) {
		return switch (cellContentA) {
			case final StringCellContent stringCellContentA -> isEqual(
					stringCellContentA, cellContentB);
			case final CompareStateCellContent compareCellContentA -> isEqual(
					compareCellContentA, cellContentB);
			default -> CellContentExtensions.isEqual(cellContentA,
					cellContentB);
		};
	}

	private static boolean isEqual(final StringCellContent stringCellContentA,
			final CellContent cellContentB) {
		return switch (cellContentB) {
			// In the case, that a cell content is StringCellContent and the
			// another is CompareCellContent, then compare old/new values of the
			// compare cell content with the another cell. Because when the row
			// in Final was removed/added will be COmpareCellContent and by
			// normal compare the row can't be merged
			case final CompareStateCellContent compareCellContentB -> {
				final List<String> oldValuesB = Streams
						.stream(getStringValueIterable(
								compareCellContentB.getOldValue()))
						.map(String::trim)
						.filter(v -> !v.isEmpty() && !v.isBlank())
						.toList();
				final List<String> newValuesB = Streams
						.stream(getStringValueIterable(
								compareCellContentB.getNewValue()))
						.map(String::trim)
						.filter(v -> !v.isEmpty() && !v.isBlank())
						.toList();
				if (oldValuesB.isEmpty() == newValuesB.isEmpty()) {
					yield false;
				}
				yield stringCellContentA.getValue()
						.equals(oldValuesB.isEmpty() ? newValuesB : oldValuesB);
			}
			default -> CellContentExtensions.isEqual(stringCellContentA,
					cellContentB);
		};
	}

	private static boolean isEqual(
			final CompareStateCellContent compareCellContentA,
			final CellContent cellContentB) {
		return switch (cellContentB) {
			case final StringCellContent stringCellContentB -> isEqual(
					stringCellContentB, compareCellContentA);
			default -> CellContentExtensions.isEqual(compareCellContentA,
					cellContentB);
		};
	}

	/**
	 * @param column
	 *            the column
	 * @param row
	 *            the row
	 * @return whether merging is allowed for a given column
	 */
	public boolean isMergeAllowed(final int column, final int row) {
		final TableRow tableRow = instances.get(row);
		ColumnDescriptor cd = TableRowExtensions.getColumnDescriptors(tableRow)
				.get(column);

		while (true) {
			if (cd == null) {
				return false;
			}
			if (cd.getMergeCommonValues() == RowMergeMode.DEFAULT) {
				cd = cd.getParent();
				continue;
			}
			return cd.getMergeCommonValues() == RowMergeMode.ENABLED;
		}
	}

	private static boolean isEmptyCellContentValue(
			final CellContent cellContent) {
		final List<String> values = Streams
				.stream(getStringValueIterable(cellContent))
				.toList();
		return values.isEmpty() || values.stream()
				.map(String::trim)
				.filter(v -> !v.isEmpty() && !v.isBlank())
				.count() == 0;
	}
}
