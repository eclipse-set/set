/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.transform

import java.io.IOException
import java.nio.file.Paths
import java.util.Optional
import java.util.Set
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException
import javax.xml.transform.TransformerException
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.SAXException

import static org.eclipse.set.utils.table.transform.XSLConstant.TableAttribute.*
import static org.eclipse.set.utils.table.transform.XSLConstant.XSLTag.*

import static extension org.eclipse.set.utils.excel.HSSFWorkbookExtension.*
import static extension org.eclipse.set.utils.table.TableBuilderFromExcel.*
import static extension org.eclipse.set.utils.table.transform.TransformStyle.*
import org.eclipse.set.utils.table.transform.XSLConstant.TableAttribute.BorderDirection

/**
 * Transform Excel table header to XSL
 * 
 * @author Truong 
 */
class TransformTableHeader {
	static val XSL_TEMPLATE_PATH = "data/export/pdf/table_template.xsl"
	static val MULTIPAGE_LAYOUT_TEMPLATE_PATH = "data/export/pdf/multipage_layout.xsl"
	static val XSL_MAINPAGE_TEMPLATE_NAME = "MainPage"

	Document doc
	Sheet sheet
	float maxPaperWidth
	var pageBreakAts = <Integer>newLinkedHashSet

	new(Sheet sheet, float maxPaperWidth) {
		this.sheet = sheet
		this.maxPaperWidth = maxPaperWidth
	}

	static def Document parseTemplate(
		String path) throws ParserConfigurationException , SAXException, IOException {
		val documentBuilderFactory = DocumentBuilderFactory.newInstance
		val builder = documentBuilderFactory.newDocumentBuilder
		val doc = builder.parse(Paths.get(path).toAbsolutePath.toFile)
		return doc
	}

	def Document transform() throws ParserConfigurationException, SAXException, IOException , TransformerException {
		doc = XSL_TEMPLATE_PATH.parseTemplate
		val rootNode = doc.getElementsByTagName(XSL_STYLESHEET).item(0)

		val table = transformTable
		if (!pageBreakAts.empty) {
			val multipageTemplate = MULTIPAGE_LAYOUT_TEMPLATE_PATH.parseTemplate
			val clone = doc.importNode(multipageTemplate.firstChild, true)
			val singlePageLayout = doc.findNodebyTagName(XSL_TEMPLATE,
				XSL_MAINPAGE_TEMPLATE_NAME)
			if (singlePageLayout.present) {
				rootNode.replaceChild(clone, singlePageLayout.get)
			} else {
				rootNode.appendChild(clone)
			}

		}
		rootNode.appendChild(table)
		rootNode.appendChild(doc.emptyTableTemplate)

		val transformTableBody = new TransformTableBody(doc, sheet)
		val tableStyle = pageBreakAts.size > 0
				? transformTableBody.pageBreakColumnCellStyle(pageBreakAts)
				: transformTableBody.defaultStyles
		tableStyle.forEach[rootNode.appendChild(it)]
		return doc
	}

	private def Element transformTable() {
		val tableTemplate = doc.createElement(XSL_TEMPLATE)
		tableTemplate.setAttribute("match", "Table[Rows/Row]")

		val table = doc.createElement(FO_TABLE)
		table.setAttribute(TABLE_LAYOUT, "fixed")
		table.setAttribute(TABLE_WIDTH, "100%")

		val tableColumn = transformColumn(sheet.getRow(0))
		tableColumn.forEach[table.appendChild(it)]

		val tableHeader = doc.createElement(FO_TABLE_HEADER)
		tableHeader.setAttribute(XSL_USE_ATTRIBUTE_SETS, "table-header-style")

		val tableRows = transformRow
		tableRows.forEach[tableHeader.appendChild(it)]
		table.appendChild(tableHeader)

		val tableBody = doc.createElement(FO_TABLE_BODY)
		tableBody.setAttribute(START_INDENT, "0mm")
		val tableBodyChild = doc.createElement(XSL_APPLY_TEMPLATE)
		tableBodyChild.setAttribute("select", "Rows/Row")
		tableBody.appendChild(tableBodyChild)
		table.appendChild(tableBody)

		if (pageBreakAts.empty) {
			tableTemplate.appendChild(table)
			val footNote = doc.createElement(XSL_APPLY_TEMPLATE)
			footNote.setAttribute("select", "Footnotes")

			tableTemplate.appendChild(footNote)

		} else {
			val block = doc.createElement(FO_BLOCK)
			block.appendChild(table)
			tableTemplate.appendChild(block)
		}

		return tableTemplate
	}

	private def Set<Element> transformColumn(Row row) {
		val columns = <Element>newLinkedHashSet
		var sumWidth = 0f
		// Row count column
		for (var i = 0, var columnNumber = 1; i <=
			sheet.headerLastColumnIndex; i++, columnNumber++) {
			var columndWidth = sheet.getColumnWidthInCm(i).floatValue
			sumWidth += columndWidth
			if (sumWidth.needToBreakPage(i + 1)) {
				pageBreakAts.add(i)
				columndWidth += this.maxPaperWidth - sumWidth
				// reset sum of width
				sumWidth = 0f
				// Add last page column with fill page size width
				columns.add(createTableColumn(columnNumber, columndWidth))
				// Add row count column to new page
				columnNumber += 1
				columns.add(
					createTableColumn(columnNumber,
						sheet.getColumnWidthInCm(0).floatValue))
				// Add element description column to new page
				columnNumber += 1
				columns.add(
					createTableColumn(columnNumber,
						sheet.getColumnWidthInCm(1).floatValue))

			} else {
				columns.add(createTableColumn(columnNumber, columndWidth))
			}
		}
		return columns
	}

	private def Element createTableColumn(int columnNumber, float columnWidth) {
		val column = doc.createElement(FO_TABLE_COLUMN)
		column.setAttribute(COLUMN_NUMBER, Integer.toString(columnNumber))
		column.setAttribute(COLUMN_WIDTH, Float.toString(columnWidth) + "cm")
		return column
	}

	private def Set<Element> transformRow() {
		val rows = <Element>newLinkedHashSet
		val lastRowIndex = sheet.headerLastRowIndex
		for (var i = 0; i <= lastRowIndex; i++) {
			val row = doc.createElement(FO_TABLE_ROW)
			val cells = sheet.getRow(i).transformCells
			if (!cells.empty) {
				cells.forEach[row.appendChild(it)]
				rows.add(row)
			}
		}
		return rows
	}

	private def Set<Element> transformCells(Row row) {
		val cells = <Element>newLinkedHashSet
		val sheet = row.sheet
		val lastCellIndex = sheet.headerLastColumnIndex
		val lastRowIndex = sheet.headerLastRowIndex
		// First column is count number column
		cells.add(row.transfromFirstColumnCell)
		for (var i = 1; i <= lastCellIndex; i++) {
			val excelCell = sheet.getCellAt(row.rowNum, i)
			val cellContent = excelCell.cellStringValue
			if (row.rowNum === lastRowIndex) {
				cells.add(excelCell.transformCell)

			} else if (excelCell.present && cellContent.present) {
				var cell = excelCell.transformCell
				val cellSpanRange = excelCell.get.columnSpanRangeAt
				if (cellSpanRange.present) {
					val spanCount = cellSpanRange.get.numberOfCells.toString
					i = cellSpanRange.get.lastColumn
					cell.setAttribute(NUMBER_COLUMNS_SPANNED, spanCount)

					// Set border style for last cell in column span
					sheet.getCellAt(row.rowNum, i).setBorderStyle(cell,
						BorderDirection.RIGHT)
				} else if (!excelCell.get.isSingleColumnGroup &&
					row.rowNum !== 0) {
					val rowSpanCount = lastRowIndex - row.rowNum
					cell.setAttribute(NUMBER_ROWS_SPANNED,
						rowSpanCount.toString)

					// set border style for last cell in row span
					sheet.getCellAt(lastRowIndex - 1, i).setBorderStyle(cell,
						BorderDirection.BOTTOM)

				}

				cells.add(cell)
			}
			// Add repeating column after break page
			if (pageBreakAts.contains(i)) {
				cells.whenBreakPage(row.rowNum)
			}
		}

		val isEmptyRow = !cells.exists[!firstChild.textContent.empty]
		if (isEmptyRow) {
			// Only visible empty row, when it is last row
			if (row.rowNum === lastRowIndex) {
				val secondCellBlock = cells.size > 1 ? cells.get(1).firstChild as Element : doc.createElement(FO_TABLE_CELL)
				if (cells.size === 1) {
					cells.add(secondCellBlock)
				} 
				secondCellBlock.setAttribute("color", "white")
				secondCellBlock.textContent = "."
			} else {
				return emptySet
			}

		}
		return cells
	}

	private def Element transfromFirstColumnCell(Row row) {
		if (row.getCell(0) === null) {
			row.createCell(0, CellType.BLANK)
		}
		val firstCell = row.getCell(0)
		val cellStyle = sheet.workbook.createCellStyle
		cellStyle.cloneStyleFrom(firstCell.cellStyle)
		cellStyle.borderLeft = BorderStyle.THIN
		firstCell.cellStyle = cellStyle
		return transformCell(Optional.of(firstCell))
	}

	private def Element transformCell(Optional<Cell> excelCell) {
		val cell = doc.createElement(FO_TABLE_CELL)
		cell.transformCellStyle(excelCell)
		val cellContent = excelCell.cellStringValue
		val block = doc.createElement(FO_BLOCK)
		block.setAttribute(START_INDENT, "0mm")
		if (cellContent.present) {
			block.textContent = cellContent.get
		}
		cell.appendChild(block)
		return cell
	}

	private def Element emptyTableTemplate(Document doc) {
		val template = doc.createElement(XSL_TEMPLATE)
		template.setAttribute("match", "Table[not(Rows/Row)]")
		val block = doc.createElement(FO_BLOCK)
		block.textContent = "Die Tabelle ist leer."
		template.appendChild(block)
		return template
	}

	private def boolean needToBreakPage(float currentSumWidth,
		int nextColumnIndex) {
		val nextColumnGroup = sheet.parentGroupSpan.findFirst [
			firstColumn === nextColumnIndex
		]
		if (nextColumnGroup !== null) {
			var groupSumWidth = 0f
			for (var i = nextColumnIndex; i <=
				nextColumnGroup.lastColumn; i++) {
				groupSumWidth += sheet.getColumnWidthInCm(i).floatValue
			}
			return currentSumWidth + groupSumWidth > this.maxPaperWidth
		}
		return false
	}

	private def void whenBreakPage(Set<Element> cells, int rowIndex) {
		val repeatingColumns = sheet.repeatingColumns
		for (var i = repeatingColumns.firstColumn; i <=
			repeatingColumns.lastColumn; i++) {
			if (i === 0) {
				cells.add(transfromFirstColumnCell(sheet.getRow(rowIndex)))
			} else if (sheet.getCellAt(rowIndex, i).cellStringValue.present) {
				cells.add(cells.get(i).cloneNode(true) as Element)
			}
		}
	}

	static def Optional<Element> findNodebyTagName(Document doc, String tag,
		String name) {
		val nodeList = doc.getElementsByTagName(tag)
		for (var i = 0; i < nodeList.length; i++) {
			val item = nodeList.item(i) as Element
			if (item.getAttribute("name").equals(name)) {
				return Optional.ofNullable(item)
			}
		}
		return Optional.empty
	}
}
