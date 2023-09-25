/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table

import java.util.List
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.TablemodelFactory
import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt

import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*

/**
 * Factory for the table model.
 * 
 * @author Schaefer
 */
class TMFactory extends AbstractRowFactory {

	val Table table

	/**
	 * @param table the table
	 */
	new(Table table) {
		this.table = table
		if (table.tablecontent === null) {
			table.tablecontent = TablemodelFactory.eINSTANCE.createTableContent
		}
	}

	/**
	 * @param leadingObjectGuid
	 *            the guid of the leading object
	 */
	def RowFactory newRowGroup(Ur_Objekt leadingObject) {
		val group = TablemodelFactory.eINSTANCE.createRowGroup
		table.tablecontent.rowgroups.add(group)
		group.leadingObject = leadingObject
		return new RowGroupRowFactory(group)
	}

	/** 
	 * @param leadingObjectGuid
	 *            the guid of the leading object
	 */
	def TableRow newTableRow(Ur_Objekt leadingObject) {
		return newTableRow(leadingObject, 0)
	}

	def TableRow newTableRow(int rowIndex, List<ColumnDescriptor> columns) {
		val newTableRow = TablemodelFactory.eINSTANCE.createTableRow
		newTableRow.rowIndex = rowIndex
		columns.forEach [
			newTableRow.cells.add(newTableCell(it))
		]
		return newTableRow
	}

	/**
	 * Create and fill general row for row group with abstract value of
	 * row in group
	 * 
	 * @param rowInGroup any row in group
	 * @param excludeColumns the columns, that shouldn't fill
	 */
	def TableRow createGeneralGroupRow(TableRow rowInGroup,
		List<ColumnDescriptor> excludeColumns) {
		val generalRow = newTableRow(0, table.columns)
		table.columns.filter[!excludeColumns.contains(it)].forEach [
			generalRow.set(it, rowInGroup.getPlainStringValue(it))
		]
		return generalRow
	}

	/**
	 * @param leadingObjectGuid
	 *            the guid of the leading object
	 */
	def TableRow newTableRow(Ur_Objekt leadingObject, int leadingObjectIndex) {
		val row = newTableRow
		row.group.leadingObject = leadingObject
		row.rowIndex = leadingObjectIndex
		return row
	}

	/**
	 * @return returns the table
	 */
	def Table getTable() {
		return table;
	}

	override getRowGroup() {
		val group = TablemodelFactory.eINSTANCE.createRowGroup
		table.tablecontent.rowgroups.add(group)
		return group
	}
}
