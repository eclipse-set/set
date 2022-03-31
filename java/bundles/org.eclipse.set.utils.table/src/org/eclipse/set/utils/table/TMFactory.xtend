/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Ur_Objekt
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.TablemodelFactory

import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*

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

	/**
	 * @param leadingObjectGuid
	 *            the guid of the leading object
	 */
	def TableRow newTableRow(Ur_Objekt leadingObject, int leadingObjectIndex) {
		val row = newTableRow
		row.group.leadingObject = leadingObject
		row.group.leadingObjectIndex = leadingObjectIndex
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
