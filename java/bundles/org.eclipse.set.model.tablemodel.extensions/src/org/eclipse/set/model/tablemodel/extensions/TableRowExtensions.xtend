/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.extensions

import java.util.HashMap
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt
import org.eclipse.set.model.tablemodel.CellContent
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.MultiColorContent
import org.eclipse.set.model.tablemodel.RowGroup
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableCell
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.TablemodelFactory
import org.eclipse.set.model.tablemodel.format.TableformatFactory

import static extension org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.RowGroupExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableCellExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableContentExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.utils.StringExtensions.*

/**
 * Extensions for {@link TableRow}.
 * 
 * @author Rumpf
 */
class TableRowExtensions {

	/** 
	 * Returns a formatted string representation intended for rendering as
	 * rich text. This method should only be called in the context of rendering
	 * the content, e.g. in NatTable content providers.
	 * 
	 * @param row the table row.
	 * @param columnIndex the column index
	 * 
	 * @return a formatted string representation of the cell content
	 */
	def static String getRichTextValue(TableRow row, int columnIndex) {
		val cell = row.cells.get(columnIndex)
		if (cell === null || cell.content === null) {
			return null
		}
		return cell.richTextValue
	}

	/**
	 * Returns a formatted string representation intended for rendering as
	 * rich text. This method should only be called in the context of rendering
	 * the content, e.g. in NatTable content providers.
	 * 
	 * @param row this table row
	 * @param label the label of the column
	 * 
	 * @return a formatted string representation of the cell content or
	 * <code>null</code> if no such column exists
	 */
	def static String getRichTextValue(TableRow row, String label) {
		return row.cells.findFirst[columndescriptor.label == label]?.
			richTextValue
	}

	/**
	 * @param row this table row
	 * @param label the label of the column
	 * 
	 * @return the plain string value at the first column with the given label or
	 * <code>null</code> if no such column exists
	 */
	def static String getPlainStringValue(TableRow row, String label) {
		return row.cells.findFirst[columndescriptor.label == label]?.
			plainStringValue
	}

	/** 
	 * @param row the table row.
	 * @param columnIndex the column index
	 * 
	 * @return the plain string column content
	 */
	def static String getPlainStringValue(TableRow row, int columnIndex) {
		val tableCell = row.cells.get(columnIndex)
		if (tableCell === null || tableCell.content === null) {
			return null
		}
		return tableCell.plainStringValue
	}

	/**
	 * @param row this table row
	 * @param column the descriptor for the column
	 * 
	 * @return the plain string value at the given column
	 */
	def static String getPlainStringValue(TableRow row,
		ColumnDescriptor column) {
		return row.getCell(column).plainStringValue
	}

	/** 
	 * sets the column content.
	 * @param row the table row
	 * @param columnIndex the column index
	 * @param value the new content
	 */
	def static void set(TableRow row, int columnIndex, String value) {
		val newContent = TablemodelFactory.eINSTANCE.createStringCellContent
		newContent.value?.add(value)
		row.cells.get(columnIndex).content = newContent
	}

	/** 
	 * sets the column by column descriptor.
	 * @param tableRow the table row
	 * @param column the column
	 * @param value the new content
	 */
	def static void set(TableRow row, ColumnDescriptor column, String value) {
		val newContent = TablemodelFactory.eINSTANCE.createStringCellContent
		newContent.value?.add(value)
		row.getCell(column).content = newContent
	}

	/** 
	 * sets the column by column descriptor.
	 * @param tableRow the table row
	 * @param column the column
	 * @param value the new content
	 */
	def static void set(TableRow row, ColumnDescriptor column,
		Iterable<String> value, String separator) {
		val newContent = TablemodelFactory.eINSTANCE.createStringCellContent
		newContent.value?.addAll(value)
		newContent.separator = separator
		row.getCell(column).content = newContent
	}

	/**
	 * sets the column content, that should be in red and yellow display,
	 * by column descriptor
	 * @param tableRow the table row
	 * @param column the column
	 * @param multiColorValue the multi color content
	 * @param stringFormat the string format of content,
	 * 					   which contain reference for multi color content
	 */
	def static void set(TableRow row, ColumnDescriptor column,
		List<MultiColorContent> contents, String seperator) {
		val newContent = TablemodelFactory.eINSTANCE.createMultiColorCellContent
		newContent.value.addAll(contents)
		newContent.separator = seperator
		row.getCell(column).content = newContent
	}

	/**
	 * Set the cell value at the first appropriate position.
	 * 
	 * @param row this table row
	 * @param columnLabel the column label
	 * @param value the cell value
	 * 
	 * @throws IllegalArgumentException if the given position is not found
	 */
	def static void set(TableRow row, String columnLabel, String value) {
		val column = row.cells.findFirst [
			columndescriptor.label == columnLabel
		]
		val newContent = TablemodelFactory.eINSTANCE.createStringCellContent
		newContent.value?.add(value)
		column.content = newContent
	}

	/**
	 * @param row the table row
	 * @param column the column
	 * @return the table cell for a given row and column.
	 */
	def static TableCell getCell(TableRow row, ColumnDescriptor column) {
		for (TableCell cell : row.cells)
			if (cell.columndescriptor == column ||
				cell.columndescriptor.isDescendantOf(column) ||
				!cell.columndescriptor.columnPosition.nullOrEmpty &&
					cell.columndescriptor.columnPosition.equals(
						column.columnPosition))
				return cell
		throw new IllegalArgumentException("no column found: " + column.label);
	}

	/**
	 * @param row this table row
	 * 
	 * @return the descriptors of the table columns
	 */
	def static List<ColumnDescriptor> getColumnDescriptors(TableRow row) {
		return row.cells.map[columndescriptor]
	}

	/**
	 * @param row this table row
	 * 
	 * @return the row group, this row belongs to
	 */
	def static RowGroup getGroup(TableRow row) {
		return row.eContainer as RowGroup
	}

	/**
	 * @param row this table row
	 * 
	 * @return the table, this row belongs to
	 */
	def static Table getTable(TableRow row) {
		return row.group.table
	}

	/**
	 * @param table the table
	 * @param row the row to get the row index for
	 * 
	 * @return the index of the row or -1 if row not found in the table
	 */
	def static int getTrueRowIndex(TableRow row) {
		var rowIndex = 0;
		for (RowGroup rowGroup : row.table.tablecontent.rowgroups) {
			for (TableRow r : rowGroup.rows) {
				if (r.equals(row)) {
					return rowIndex;
				}
				rowIndex++;
			}
		}
		return -1;
	}

	/**
	 * @param row this table row
	 * @param other another table row
	 * 
	 * @return whether the rows contain equal table columns
	 */
	def static boolean isEqual(TableRow row, TableRow other) {
		if (row.cells.size != other.cells.size) {
			return false
		}
		return row.cells.indexed.forall [
			value.isEqual(other.cells.get(key))
		]
	}

	/**
	 * @param tableRow this table row
	 * 
	 * @return the content of this row in the order of the table {@link TableExtensions#getColumns columns}
	 */
	def static List<CellContent> getContent(TableRow row) {
		val contentMap = row.createContentMap
		return row.group.content.table.columns.map[contentMap.get(it)]
	}

	/**
	 * @param tableRow this table row
	 * 
	 * @return the type of the leading guid of this row
	 */
	static def String getLeadingObjectGuid(TableRow tableRow) {
		return tableRow.group.leadingObject?.identitaet?.wert
	}

	static def Ur_Objekt getLeadingObject(TableRow tableRow) {
		return tableRow.group.leadingObject
	}

	def static String toDebugString(TableRow row, int groupNumber,
		int columnWidth) {
		if (row.eContainer !== null) {
			return '''«groupNumber.toString.toPaddedString(3)» «row.leadingObjectGuid.toPaddedString(columnWidth)» «FOR cell : row.cells SEPARATOR " "»«
			cell.plainStringValue.toPaddedString(columnWidth)»«ENDFOR»'''
		}
		return "detached row"
	}

	private static def Map<ColumnDescriptor, CellContent> createContentMap(
		TableRow row) {
		val result = new HashMap
		row.cells.forEach [
			result.put(columndescriptor, content)
		]
		return result
	}

	def static void setTopologicalCell(TableRow row,
		Set<ColumnDescriptor> cols) {
		cols.forEach [ column |
			val cell = row.getCell(column)
			if (cell.plainStringValue.nullOrEmpty) {
				return
			}
			
			if (cell.cellannotation.nullOrEmpty) {
				cell.cellannotation.add(
					TableformatFactory.eINSTANCE.createCellFormat)
			}
			cell.format.topologicalCalculation = true
		]

	}
}
