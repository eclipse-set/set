/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.transform

import java.util.Optional
import java.util.Set
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.Sheet
import org.w3c.dom.Document
import org.w3c.dom.Element

import static org.eclipse.set.utils.table.transform.XSLConstant.XSLStyleSets.*

import static extension org.eclipse.set.utils.excel.HSSFWorkbookExtension.*
import static extension org.eclipse.set.utils.table.transform.TransformStyle.*
import static org.eclipse.set.utils.table.transform.XSLConstant.XSLTag.*
import static org.eclipse.set.utils.table.transform.XSLConstant.TableAttribute.*

/**
 * Transform excel table body style
 * @author Truong
 */
class TransformTableBody {
	Document doc
	Sheet sheet

	new(Document doc, Sheet sheet) {
		this.doc = doc
		this.sheet = sheet
	}

	def Set<Element> getDefaultStyles() {
		return groupCellByStyle.flatMap [
			#[it.rowStyle, it.lastRowStyle]
		].toSet
	}

	def Set<Element> getDefaultStyles(Set<Set<Cell>> styleGroup) {
		return styleGroup.flatMap [
			#[it.rowStyle, it.lastRowStyle]
		].toSet
	}

	private def Set<Set<Cell>> groupCellByStyle() {
		val result = <Set<Cell>>newLinkedHashSet
		val lastHeaderRowIndex = sheet.headerLastRowIndex
		val parentGroupLastIndex = columnWithWideBorderRight
		val firstDataRow = sheet.getRow(lastHeaderRowIndex + 2)
		if (firstDataRow !== null) {
			for (var i = 0; i <= sheet.headerLastColumnIndex; i++) {
				val cell = sheet.getCellAt(firstDataRow.rowNum, i)
				if (cell.present) {
					if (parentGroupLastIndex.contains(i)) {
						val cellStyle = sheet.workbook.createCellStyle
						cellStyle.cloneStyleFrom(cell.get.cellStyle)
						cellStyle.borderRight = BorderStyle.MEDIUM
						cell.get.cellStyle = cellStyle
					}
					if (!cell.get.cellStyle.defaultStyle) {
						val sameStyleGroup = result.findFirst [
							it.findFirst [ lastColumnCell |
								cell.get.cellStyle.isEquals(
									lastColumnCell.cellStyle)
							] !== null
						]
						sameStyleGroup !== null ? sameStyleGroup.add(
							cell.get) : result.add(newLinkedHashSet(cell.get))
					}
				}
			}
		}
		return result
	}

	private def boolean isDefaultStyle(CellStyle cellStyle) {
		val borderStyle = cellStyle.transformBorderStyle
		val isDefaultBorder = borderStyle.filter[direction, style|!style.empty].
			size === 0
		return isDefaultBorder &&
			cellStyle.alignment === HorizontalAlignment.CENTER
	}

	private def boolean isEquals(CellStyle first, CellStyle second) {
		if (first.alignment !== second.alignment) {
			return false
		}
		val firstBorderStyle = first.transformBorderStyle
		val secondBorderStyle = second.transformBorderStyle
		return firstBorderStyle.equals(secondBorderStyle)
	}

	private def int[] getColumnWithWideBorderRight() {
		val parentGroupSpan = sheet.parentGroupSpan
		val result = parentGroupSpan.filter [
			lastColumn !== sheet.headerLastColumnIndex
		].map[lastColumn].toSet
		if (!parentGroupSpan.exists[it.firstColumn === 1]) {
			result.add(1)
		}
		return result
	}

	def Set<Element> pageBreakColumnCellStyle(int[] pageBreakAt) {
		val groupStyles = groupCellByStyle
		val pageBreakColumns = groupStyles.flatMap [
			it.map [ cell |
				if (pageBreakAt.contains(cell.columnIndex)) {
					return cell
				}
			]
		].filterNull.toList

		val defaultStyle = groupStyles.map [
			it.removeIf[cell|pageBreakAt.contains(cell.columnIndex)]
			return it
		].flatMap [
			#[it.rowStyle, it.lastRowStyle]
		].toSet
		val pageBreakColumnsStyle = #[pageBreakColumns.rowStyle,
			pageBreakColumns.lastRowStyle].toSet

		pageBreakColumnsStyle.forEach [
			val numberCountColStyle = doc.createElement(FO_TABLE_CELL)
			numberCountColStyle.setAttribute(XSL_USE_ATTRIBUTE_SETS,
				BODY_ROW_CELL_STYLE)
			numberCountColStyle.setAttribute(NUMBER_ROWS_SPANNED,
				String.format("{@%s}", NUMBER_ROWS_SPANNED))

			val block = doc.createElement(FO_BLOCK)
			val valueof = doc.createElement(XSL_VALUE_OF)
			valueof.setAttribute("select", "../@group-number")
			block.appendChild(valueof)
			numberCountColStyle.appendChild(block)

			val applyTemplates = doc.createElement(XSL_APPLY_TEMPLATE)
			applyTemplates.setAttribute("select", "../*[@column-number = '1']")
			appendChild(numberCountColStyle)
			appendChild(applyTemplates)
		]

		pageBreakColumnsStyle.addAll(defaultStyle)
		return pageBreakColumnsStyle
	}
	
	private def Element rowStyle(Cell[] exclusionColumns) {

		val expression = String.format(
			"Cell[contains(' %s ', concat(' ', @column-number, ' '))]",
			String.join(" ", exclusionColumns.map [
				Integer.toString(it.columnIndex)
			]))
		val template = doc.createStyleTemplate(expression)
		val tableCell = doc.createCellStyleElement(DEFAULT_CELL_STYLE,
			exclusionColumns.get(0))

		template.appendChild(tableCell)
		return template
	}

	private def Element lastRowStyle(Cell[] exclusionColumns) {
		val expression = String.format(
			"Cell[contains(' %s ', concat(' ', @column-number, ' '))" +
				" and ../@group-number = count(/Table/Rows/Row)]",
			String.join(" ", exclusionColumns.map [
				Integer.toString(it.columnIndex)
			]))
		val template = doc.createStyleTemplate(expression)
		val tableCell = doc.createCellStyleElement(
			LAST_ROW_CELL_STYLE,
			exclusionColumns.get(0)
		)

		template.appendChild(tableCell)
		return template
	}

	private static def Element createStyleTemplate(Document doc,
		String expression) {
		val template = doc.createElement(XSL_TEMPLATE)
		template.setAttribute("match", expression)
		return template
	}

	private static def Element createCellStyleElement(Document doc,
		String styleSets, Cell cell) {
		val tableCell = doc.createElement(FO_TABLE_CELL)
		tableCell.transformCellStyle(Optional.of(cell))
		tableCell.setAttribute(XSL_USE_ATTRIBUTE_SETS, styleSets)
		tableCell.setAttribute(NUMBER_ROWS_SPANNED,
			String.format("{@%s}", NUMBER_ROWS_SPANNED))
		tableCell.appendChild(doc.createElement(XSL_APPLY_TEMPLATE))
		return tableCell
	}

}
