/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table

import java.util.Comparator
import java.util.List
import java.util.Map
import java.util.function.UnaryOperator
import org.eclipse.nebula.widgets.nattable.data.IDataProvider
import org.eclipse.set.model.tablemodel.CellContent
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow

import static extension org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions.*

/**
 * IDataProvider implementation for Table
 * 
 * @author Stuecker
 */
class TableDataProvider implements IDataProvider {
	int columnCount
	protected List<TableRowData> tableContents

	UnaryOperator<Integer> getSourceLine
	protected Map<Integer, Object> filters = newHashMap
	protected Table table

	static val EXCULDE_FILTER_SIGN = "-"

	protected Pair<Integer, Comparator<? super String>> currentComparator

	new(Table table, UnaryOperator<Integer> getSourceLine) {
		this.getSourceLine = getSourceLine;
		this.table = table
		refresh()
	}

	/**
	 * Update the table model and refresh the table content
	 */
	def void refresh(Table table) {
		this.table = table
		refresh()
	}

	/**
	 * Update the table content 
	 */
	def void refresh() {
		// The number of actual columns is the number of leaf column descriptors
		// as each defines a single column in the table (rather than a heading)
		this.columnCount = table.columndescriptors.flatMap[leaves].toSet.size
		this.tableContents = table.tablecontent.rowgroups.flatMap[rows].indexed.
			map[new TableRowData(key, value)].filter[filterMatch].toList
	}

	/**
	 * Checks whether a row fulfils the applied filters
	 * 
	 * @param row the row to check
	 */
	protected def boolean filterMatch(TableRowData row) {
		for (var i = 0; i < columnCount; i++) {
			if (filters.containsKey(i)) {
				val content = row.contents.get(i).plainStringValue.toLowerCase
				var filterValue = filters.get(i).toString.toLowerCase
				val isExcludeFilter = filterValue.substring(0, 1).equals(
					EXCULDE_FILTER_SIGN)
				filterValue = isExcludeFilter
					? filterValue.substring(1)
					: filterValue

				// Equivalence logic
				if (isExcludeFilter === content.contains(filterValue)) {
					return false
				}
			}
		}
		return true
	}

	/** 
	 * @param row the current table row (after filters are applied)
	 * @return the original row index(before filters were applied)
	 */
	def int getOriginalRowIndex(int row) {
		return tableContents.get(row).row.rowIndex
	}

	def int getObjectSourceLine(int row) {
		return getSourceLine === null ? -1 : getSourceLine.apply(row)
	}

	def TableRowData getRowData(int rowIndex) {
		if (rowIndex >= 0 && rowIndex < tableContents.size) {
			return tableContents.get(rowIndex)
		}
		return null
	}

	def int getCurrentRowIndex(TableRowData row) {
		return tableContents.indexOf(row)
	}

	/**
	 * 
	 * @param rowToFind the row
	 * @return {@link TableRowData}
	 */
	def TableRowData findRow(TableRow rowtoFind) {
		return tableContents.findFirst [
			row === rowtoFind
		]
	}

	def String getObjectState(int row) {
		val zustandColumn = table.columndescriptors.findFirst [
			label !== null && label.equals('Zustand')
		]
		if (zustandColumn === null) {
			return null
		}
		val zustandCell = tableContents?.get(row)?.contents?.get(
			table.columndescriptors.indexOf(zustandColumn) - 1)
		if (zustandCell === null) {
			return null
		}
		return zustandCell.plainStringValue

	}

	override int getColumnCount() {
		return columnCount
	}

	override Object getDataValue(int columnIndex, int rowIndex) {
		return tableContents.get(rowIndex).contents.get(columnIndex).
			richTextValue
	}

	override int getRowCount() {
		return this.tableContents.size
	}

	override void setDataValue(int columnIndex, int rowIndex, Object newValue) {
		// Tables are read-only
		throw new UnsupportedOperationException("Cannot set values in a table")
	}

	/**
	 * Applies a set of filters to the table
	 * 
	 * @param filterIndexToObjectMap A map<columnIndex, filterValue> of filters to apply
	 * @param table the raw table data
	 */
	def void applyFilter(Map<Integer, Object> filterIndexToObjectMap) {
		this.filters = filterIndexToObjectMap
		refresh()
		// sort contents again after filter
		sort()
	}

	def void sort() {
		if (currentComparator !== null) {
			sort(currentComparator.key, currentComparator.value)
		}
	}

	def void sort(int column, Comparator<? super String> comparator) {
		tableContents = tableContents.sortWith(
			tableRowDataComparator(column, comparator))
		currentComparator = new Pair(column, comparator)
	}

	protected def Comparator<TableRowData> tableRowDataComparator(int column,
		Comparator<? super String> comparator) {
		return [ row1, row2 |
			comparator.rowComparator(column).compare(row1.contents,
				row2.contents)
		]
	}

	protected def Comparator<CellContent> cellComparator(
		Comparator<? super String> comparator) {
		return [ cell1, cell2 |
			comparator.compare(cell1.plainStringValue, cell2.plainStringValue)
		]
	}

	protected def Comparator<List<CellContent>> rowComparator(
		Comparator<? super String> comparator, int column) {
		return [ cells1, cells2 |
			comparator.cellComparator.compare(cells1.get(column),
				cells2.get(column))
		]
	}
}
