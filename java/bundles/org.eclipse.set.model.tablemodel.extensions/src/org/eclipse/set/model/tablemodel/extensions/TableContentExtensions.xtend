/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.extensions

import org.eclipse.set.model.tablemodel.RowGroup
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableContent
import org.eclipse.set.model.tablemodel.TablemodelFactory

import static extension org.eclipse.set.model.tablemodel.extensions.RowGroupExtensions.*

/**
 * Extensions for {@link TableContent}.
 * 
 * @author Schaefer
 */
class TableContentExtensions {

	/**
	 * @param content this table content
	 * 
	 * @return the table this content belongs to
	 */
	def static Table getTable(TableContent content) {
		return content.eContainer as Table
	}

	/**
	 * @param content this table content
	 * @param other another table content
	 * 
	 * @return whether the tables content contain equal row groups 
	 */
	def static boolean isEqual(TableContent content, TableContent other) {
		if (content.rowgroups.size != other.rowgroups.size) {
			return false
		}
		return content.rowgroups.indexed.forall [
			value.isEqual(other.rowgroups.get(key))
		]
	}
	
	def static void addRowGroup(TableContent content, RowGroup groupToAdd) {
		val newRowGroup = TablemodelFactory.eINSTANCE.createRowGroup
		content.rowgroups.add(newRowGroup)
		groupToAdd.rows.forEach[row |
			newRowGroup.addRow(row)
		]
		newRowGroup.leadingObject = groupToAdd.leadingObject
		newRowGroup.leadingObjectIndex = groupToAdd.leadingObjectIndex
		
	}
}
