/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table

import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.TablemodelFactory

import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.*
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import java.util.List

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
	 * Add new table row to same group, when alread exist,
	 * otherwise create new group and add into this
	 *   
	 * @params row the table row
	 * @params columns list of columns have same value
	 */
	def void addRowToGroup(TableRow row, List<ColumnDescriptor> columns) {
		val rowsGroups = table.tablecontent.rowgroups
		val group = rowsGroups.findFirst[group |
			val firstRowInGroup = group.rows.get(0)
			firstRowInGroup.cells.filter[columns.contains(columndescriptor)].forall[
				val groupValue = getPlainStringValue(content)
				val rowValue = getPlainStringValue(row, columndescriptor)
				return groupValue.equals(rowValue)				
			]
		]
		if (group !== null) {
			group.rows.add(row)
		} else {
			rowGroup.rows.add(row)
		}
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
		columns.forEach[
			newTableRow.cells.add(newTableCell(it))
		]
		return newTableRow
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
