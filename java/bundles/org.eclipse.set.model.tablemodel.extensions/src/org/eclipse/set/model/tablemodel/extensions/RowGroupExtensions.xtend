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
import static extension org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.*
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
	 * @param cells the cells of new row
	 * 
	 * @return the new row with the given values
	 */
	static def TableRow addRow(RowGroup group, List<TableCell> cells) {
		val newRow = TablemodelFactory.eINSTANCE.createTableRow
		group.rows.add(newRow)
		val table = group.table
		table.columns.forEach[newRow.cells.add(createTableCell)]
		cells.forEach [ cell |
			val content = cell.content
			val columnPosition = cell.columndescriptor.columnPosition
			val targetCell = newRow.cells.findFirst [
				columndescriptor.columnPosition == columnPosition
			]
			if (targetCell !== null) {
				targetCell.content = content
				if (!cell.cellannotation.nullOrEmpty) {
					targetCell.textAlignment = cell.format.textAlignment
					targetCell.topologicalCalcultation = cell.format.topologicalCalculation
				}	
				
			}
		]
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
	 * Find group for the row. 
	 * 
	 * @params row the table row
	 * @params excludeCols the column, that don't need same value
	 */
	static def RowGroup findRowGroup(Table table, TableRow row,
		List<ColumnDescriptor> excludeCols) {
		val groups = table.tablecontent.rowgroups
		val sameValueCols = table.columns.filter[!excludeCols.contains(it)]
		return groups.findFirst [ group |
			val firstRowInGroup = group.rows.get(0)
			firstRowInGroup.cells.filter [
				sameValueCols.contains(columndescriptor)
			].forall [
				val groupValue = content.plainStringValue
				val rowValue = row.getPlainStringValue(columndescriptor)
				return groupValue.equals(rowValue)
			]
		]
	}
}
