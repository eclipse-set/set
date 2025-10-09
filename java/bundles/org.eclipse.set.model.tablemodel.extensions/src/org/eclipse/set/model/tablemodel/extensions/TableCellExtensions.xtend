/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.tablemodel.extensions

import java.util.Set
import org.eclipse.set.model.tablemodel.TableCell
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.format.CellFormat
import org.eclipse.set.model.tablemodel.format.TableformatFactory
import org.eclipse.set.model.tablemodel.format.TextAlignment

import static extension org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions.*

/**
 * Extensions for {@link TableCell}.
 * 
 * @author Schaefer
 */
class TableCellExtensions {

	/**
	 * Returns a formatted string representation intended for rendering as
	 * rich text. This method should only be called in the context of rendering
	 * the content, e.g. in NatTable content providers.
	 * 
	 * @param cell this cell
	 *  
	 * @return a formatted string representation of the cell content
	 */
	static def String getRichTextValue(TableCell cell) {
		if (cell.columndescriptor.isFootnoteReferenceColumn) {
			return cell.content.getRichTextValueWithFootnotes(
				(cell.eContainer as TableRow).footnotes)
		}
		return cell.content.richTextValue
	}

	/**
	 * @param cell this cell
	 * 
	 * @return the content as a string
	 */
	static def String getPlainStringValue(TableCell cell) {
		val plainString = cell.content.plainStringValue
		if (plainString === null || plainString.trim.empty) {
			return ""
		}
		return plainString
	}

	static def Set<String> getIterableStringValue(TableCell cell) {
		return cell.content.stringValueIterable.filter [
			it !== null && !trim.empty
		].toSet
	}

	/**
	 * @param cell this cell
	 * @param other another cell
	 * 
	 * @return whether the cells have equal descriptors and contents
	 */
	def static boolean isEqual(TableCell cell, TableCell other) {
		return cell.columndescriptor.isEqual(other.columndescriptor) &&
			cell.content.isEqual(other.content)
	}

	/**
	 * @param cell this cell
	 * 
	 * @return the table row of this cell
	 */
	def static TableRow getTableRow(TableCell cell) {
		return cell.eContainer as TableRow
	}

	/**
	 * @param cell this cell
	 * 
	 * @return the number of this cell within the cell's table row (starting with 1)
	 */
	def static int getColumnNumber(TableCell cell) {
		return cell.tableRow.cells.indexOf(cell) + 1
	}

	/**
	 * @param cell this cell
	 * @param textAlignment the text alignment
	 */
	def static void setTextAlignment(TableCell cell,
		TextAlignment textAlignment) {
		cell.format.textAlignment = textAlignment
	}

	def static CellFormat getFormat(TableCell cell) {
		val results = cell.cellannotation.filter(CellFormat).toList
		if (results.size == 0) {
			val newCellFormat = TableformatFactory.eINSTANCE.createCellFormat
			cell.cellannotation.add(newCellFormat)
			results.add(newCellFormat)
		}
		if (results.size != 1) {
			throw new IllegalArgumentException('''Ambiguous cell formats in «cell».''')
		}
		return results.get(0)
	}
}
