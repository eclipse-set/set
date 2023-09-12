/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table

import java.util.List
import java.util.Map
import org.eclipse.nebula.widgets.nattable.data.IDataProvider
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow

import static extension org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions.*
import java.util.Comparator
import org.eclipse.set.model.tablemodel.CellContent
import java.util.function.Function

/**
 * IDataProvider implementation for Table
 * 
 * @author Stuecker
 */
class TableDataProvider implements IDataProvider {
	int columnCount
	List<Pair<Integer, List<CellContent>>> tableContents
	Function<Integer, Integer> getSourceLine
	Map<Integer, Object> filters = newHashMap
	Table table

	new(Table table, Function<Integer, Integer> getSourceLine) {
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
			filter[value.filterMatch].map [
				key -> value.cells.map[content].toList
			].toList
	}

	/**
	 * Checks whether a row fulfils the applied filters
	 * 
	 * @param row the row to check
	 */
	private def boolean filterMatch(TableRow row) {
		for (var i = 0; i < columnCount; i++) {
			if (filters.containsKey(i)) {
				val content = row.cells.get(i).content.plainStringValue.
					toLowerCase
				if (!content.contains(filters.get(i).toString.toLowerCase)) {
					return false
				}
			}
		}
		return true
	}

	/** 
	 * @param row the current table row (after filters are applied)
	 * @return the original row (before filters were applied)
	 */
	def int getOriginalRow(int row) {
		return tableContents.get(row).key
	}

	def int getObjectSourceLine(int row) {
		return getSourceLine === null ? -1 : getSourceLine.apply(row)
	}

	def String getObjectState(int row) {
		val zustandColumn = table.columndescriptors.findFirst [
			label !== null && label.equals('Zustand')
		]
		if (zustandColumn === null) {
			return null
		}
		val zustandCell = tableContents?.get(row)?.value?.get(
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
		return tableContents.get(rowIndex).value.get(columnIndex).richTextValue
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
	}

	def void sort(int column, Comparator<? super String> comparator) {
		val Comparator<CellContent> cellComparator = [ cell1, cell2 |
			comparator.compare(cell1.plainStringValue, cell2.plainStringValue)
		]
		val Comparator<List<CellContent>> rowComparator = [ cells1, cells2 |
			cellComparator.compare(cells1.get(column), cells2.get(column))
		]
		tableContents = tableContents.sortWith [ p1, p2 |
			rowComparator.compare(p1.value, p2.value)
		]
	}
}
