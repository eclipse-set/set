/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.extensions

import org.eclipse.set.model.tablemodel.format.TextAlignment
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.RowGroup
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableCell
import org.eclipse.set.model.tablemodel.TableContent
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.TablemodelFactory
import java.util.List

import static extension org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableCellExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableContentExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*

/**
 * Extensions for {@link RowGroup}.
 * 
 * @author Schaefer
 */
class RowGroupExtensions {

	/**
	 * @param group this row group
	 * @param values the cell values
	 * 
	 * @return the new row with the given values
	 */
	static def TableRow addRow(RowGroup group, String... values) {
		val newRow = TablemodelFactory.eINSTANCE.createTableRow
		group.rows.add(newRow)
		val table = group.table
		table.columns.forEach[newRow.cells.add(createTableCell)]
		values.indexed.forEach[newRow.set(key, value)]
		return newRow
	}

	/**
	 * @param group this row group
	 * 
	 * @return the table this group belongs to
	 */
	static def Table getTable(RowGroup group) {
		return group.content.table
	}

	/**
	 * @param group this row group
	 * 
	 * @return the table content this group belongs to
	 */
	static def TableContent getContent(RowGroup group) {
		return group.eContainer as TableContent
	}

	/**
	 * @param group this row group
	 * @param other another row group
	 * 
	 * @return whether the row groups contain equal rows
	 */
	def static boolean isEqual(RowGroup group, RowGroup other) {
		if (group.rows.size != other.rows.size) {
			return false
		}
		return group.rows.indexed.forall [
			value.isEqual(other.rows.get(key))
		]
	}

	/**
	 * @param group this row group
	 * @param descriptor the column descriptor
	 * 
	 * @return the cells of the given column descriptor within this row group
	 */
	def static List<TableCell> getColumnCells(RowGroup group,
		ColumnDescriptor descriptor) {
		return group.rows.map[getCell(descriptor)]
	}

	/**
	 * Set the given text alignment to the given column of all rows of this row group.
	 * 
	 * @param table this row group
	 * @param columnIdx the column index
	 */
	static def void setTextAlignment(RowGroup group, int columnIdx,
		TextAlignment textAlignment) {
		group.rows.forEach[cells.get(columnIdx).textAlignment = textAlignment]
	}

	/**
	 * @param table this row group
	 * 
	 * @return the maximum footnote number for this row group
	 */
	static def int getMaxFootnoteNumber(RowGroup group) {
		return group.rows.fold(0, [n, r|Math.max(n, r.maxFootnoteNumber)])
	}
}
