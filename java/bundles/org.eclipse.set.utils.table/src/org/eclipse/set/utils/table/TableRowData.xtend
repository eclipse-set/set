/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.table

import org.eclipse.set.model.tablemodel.TableRow
import java.util.List
import org.eclipse.set.model.tablemodel.CellContent

class TableRowData {
	
	Integer rowIndex
	
	TableRow row
	
	List<CellContent> contents
	
	new(Integer rowIndex, TableRow row) {
		this.rowIndex = rowIndex
		this.row = row
		this.contents = row.cells.map[content].toList
	}
	
	def int getRowIndex() {
		return rowIndex
	}
	
	def void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex 
	}
	
	def TableRow getRow() {
		return row
	}
	
	def List<CellContent> getContents() {
		return contents
	}
}