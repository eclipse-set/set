/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table

import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.RowGroup
import org.eclipse.set.model.tablemodel.TableCell
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.TablemodelFactory

import static extension org.eclipse.set.model.tablemodel.extensions.RowGroupExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import org.eclipse.set.utils.table.RowFactory

/** 
 * Abstract implementation of {@link RowFactory}.
 * 
 * @author Schaefer
 */
abstract class AbstractRowFactory implements RowFactory {

	def RowGroup getRowGroup()

	override TableRow newTableRow() {
		val rowGroup = getRowGroup()
		val newTableRow = TablemodelFactory.eINSTANCE.createTableRow
		rowGroup.rows.add(newTableRow)
		rowGroup.table.columns.forEach [
			newTableRow.cells.add(newTableCell(it))
		]
		return newTableRow
	}

	private def TableCell newTableCell(ColumnDescriptor descriptor) {
		val newTableColumn = TablemodelFactory.eINSTANCE.createTableCell
		newTableColumn.columndescriptor = descriptor
		return newTableColumn
	}
}
