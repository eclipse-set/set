/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table

import org.eclipse.nebula.widgets.nattable.data.IDataProvider
import org.eclipse.set.model.tablemodel.Table
import static extension org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions.*
import java.util.List

/**
 * IDataProvider implementation for Table
 * 
 * @author Stuecker
 */
class TableDataProvider implements IDataProvider {
	int rowCount
	int columnCount
	List<List<String>> tableContents

	new(Table table) {
		refresh(table)
	}

	def void refresh(Table table) {
		// The number of actual columns is the number of leaf column descriptors
		// as each defines a single column in the table (rather than a heading)
		this.columnCount = table.columndescriptors.flatMap[leaves].toSet.size
		this.rowCount = table.tablecontent.rowgroups.flatMap[rows].size
		this.tableContents = table.tablecontent.rowgroups.flatMap[rows].map [
			it.cells.map[content.richTextValue].toList
		].toList
	}

	override int getColumnCount() {
		return columnCount
	}

	override Object getDataValue(int columnIndex, int rowIndex) {
		return tableContents.get(rowIndex).get(columnIndex)
	}

	override int getRowCount() {
		return rowCount
	}

	override void setDataValue(int columnIndex, int rowIndex, Object newValue) {
		// Tables are read-only
		throw new UnsupportedOperationException("Cannot set values in a table")
	}

}
