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

/**
 * IDataProvider implementation for Table
 * 
 * @author Stuecker
 */
class TableDataProvider implements IDataProvider {
	int columnCount
	List<List<String>> tableContents
	Map<Integer, Object> filters = newHashMap

	new(Table table) {
		refresh(table)
	}

	def void refresh(Table table) {
		// The number of actual columns is the number of leaf column descriptors
		// as each defines a single column in the table (rather than a heading)
		this.columnCount = table.columndescriptors.flatMap[leaves].toSet.size
		this.tableContents = table.tablecontent.rowgroups.flatMap[rows].filter [
			filterMatch
		].map [
			it.cells.map[content.richTextValue].toList
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
				val content = row.cells.get(i).content.plainStringValue
				if (!content.contains(filters.get(i).toString)) {
					return false
				}
			}
		}
		return true
	}

	override int getColumnCount() {
		return columnCount
	}

	override Object getDataValue(int columnIndex, int rowIndex) {
		return tableContents.get(rowIndex).get(columnIndex)
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
	def void applyFilter(Map<Integer, Object> filterIndexToObjectMap,
		Table table) {
		this.filters = filterIndexToObjectMap;
		refresh(table)
	}

}
