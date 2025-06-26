/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.export.xsl

import java.util.Optional
import java.util.Set
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.Sheet
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.BorderDirection
import org.eclipse.set.utils.export.xsl.XSLConstant.XSLFoAttributeName
import org.w3c.dom.Document
import org.w3c.dom.Element

import static org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.*
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLStyleSets.*
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLTag.*

import static extension org.eclipse.set.utils.excel.ExcelWorkbookExtension.*
import static extension org.eclipse.set.utils.export.xsl.TransformStyle.*

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
		return groupCellByStyle(#[]).flatMap [
			#[it.rowStyle, it.lastRowStyle]
		].toSet
	}

	def Set<Element> getDefaultStyles(Set<Set<Cell>> styleGroup) {
		return styleGroup.flatMap [
			#[it.rowStyle, it.lastRowStyle]
		].toSet
	}

	private def Set<Set<Cell>> groupCellByStyle(int[] pageBreakAt) {
		val result = <Set<Cell>>newLinkedHashSet
		val lastHeaderRowIndex = sheet.headerLastRowIndex
		val parentGroupLastIndex = columnWithWideBorderRight
		// Get next 2 row, because first row after table header row will contains
		// border style of header row
		var firstDataRow = sheet.getRow(lastHeaderRowIndex + 2)
		if (firstDataRow === null) {
			throw new RuntimeException(
				"Missing first data row. Is the printing area configured correctly?");
		}

		for (var i = 0; i <= sheet.headerLastColumnIndex; i++) {
			val cell = sheet.getCellAt(firstDataRow.rowNum, i)
			if (cell.present) {
				if (parentGroupLastIndex.contains(i)) {
					setExcelCellBorderStyle(cell, BorderDirection.RIGHT,
						BorderStyle.MEDIUM)
				// Set border style for The break column and the after
				} else if (pageBreakAt.contains(i - 1)) {
					setExcelCellBorderStyle(cell, BorderDirection.LEFT,
						BorderStyle.MEDIUM)
				} else if (pageBreakAt.contains(i)) {
					setExcelCellBorderStyle(cell, BorderDirection.RIGHT,
						BorderStyle.MEDIUM)
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
		val headerRow = sheet.getRow(1)
		val result = newArrayList
		// Start at 1 to skip empty column 0
		for (var i = 1; i <= sheet.headerLastColumnIndex; i++) {
			val cell = headerRow.getCell(i)
			if (cell.cellStyle.borderRight == BorderStyle.MEDIUM) {
				result.add(i)
			}
			if (i !== 1 && cell.cellStyle.borderLeft == BorderStyle.MEDIUM) {
				result.add(i - 1)
			}
		}

		return result.toSet
	}

	def Set<Element> pageBreakColumnCellStyle(int[] pageBreakAt) {
		val groupStyles = groupCellByStyle(pageBreakAt)
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
		].filterNull.toSet
		val pageBreakColumnsStyle = #[pageBreakColumns.rowStyle,
			pageBreakColumns.lastRowStyle].filterNull.toSet

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
		if (exclusionColumns.empty) {
			return null
		}

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
		if (exclusionColumns.empty) {
			return null
		}

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

	private static def Element createChooseElement(Document doc,
		String expression, Element ifTrue, Element whenFalse) {
		val choose = doc.createElement(XSL_CHOOSE)
		val when = doc.createElement(XSL_WHEN)

		choose.appendChild(when)
		when.setAttribute("test", expression)
		when.appendChild(ifTrue)

		val otherwise = doc.createElement(XSL_OTHERWISE)
		otherwise.appendChild(whenFalse)
		choose.appendChild(otherwise)
		return choose
	}

	private static def Element createCellStyleElement(Document doc,
		String styleSets, Cell cell) {
		val tableCell = doc.createElement(FO_TABLE_CELL)
		tableCell.transformCellStyle(Optional.of(cell))
		tableCell.setAttribute(XSL_USE_ATTRIBUTE_SETS, styleSets)
		tableCell.setAttribute(NUMBER_ROWS_SPANNED,
			String.format("{@%s}", NUMBER_ROWS_SPANNED))
		tableCell.addChooseForCompareProjectCell(doc)
		tableCell.appendChild(doc.createElement(XSL_APPLY_TEMPLATE))
		return tableCell
	}
	
	private static def void addChooseForCompareProjectCell(Element tableCell, Document doc) {
		val compareCellBorderAttribute = doc.createElement(XSL_ATTRIBUTE)
		compareCellBorderAttribute.setAttribute(XSLFoAttributeName.ATTR_NAME,
			"border")
		compareCellBorderAttribute.textContent = '''0.3mm solid «ToolboxConstants.TABLE_COMPARE_TABLE_CELL_BORDER_COLOR»'''

		val borderColorAttribute = doc.createElement(XSL_ATTRIBUTE)
		borderColorAttribute.setAttribute(XSLFoAttributeName.ATTR_NAME,
			"border-color")
		borderColorAttribute.textContent = "black"
		val chooseElement = createChooseElement(doc, "CompareProjectContent",
			compareCellBorderAttribute, borderColorAttribute)
		tableCell.appendChild(chooseElement)
	}

}
