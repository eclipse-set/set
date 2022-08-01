/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table;

import java.util.List;

import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;

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

		// And contain the same value
		final String valueA = TableRowExtensions.getPlainStringValue(rowA,
				column);
		final String valueB = TableRowExtensions.getPlainStringValue(rowA,
				column);
		return valueA.equals(valueB);
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
			if (cd.isMergeCommonValues()) {
				return true;
			}
			cd = cd.getParent();
		}
	}
}
