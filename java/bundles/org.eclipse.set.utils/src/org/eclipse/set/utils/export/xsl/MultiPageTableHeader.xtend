/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.export.xsl

import java.io.IOException
import java.util.LinkedHashSet
import java.util.Optional
import java.util.Set
import javax.xml.parsers.ParserConfigurationException
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.BorderDirection
import org.w3c.dom.Element
import org.xml.sax.SAXException

import static org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.*
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLFoAttributeName.*
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLStyleSets.WIDE_BORDER_STYLE
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLTag.*

import static extension org.eclipse.set.utils.excel.ExcelWorkbookExtension.*
import static extension org.eclipse.set.utils.export.xsl.TransformStyle.setBorderStyle

class MultiPageTableHeader extends AbstractTransformTableHeader {
	static val MULTIPAGE_LAYOUT_TEMPLATE_PATH = "data/export/pdf/multipage_layout.xsl"
	static val XSL_MAINPAGE_TEMPLATE_NAME = "MainPage"
	val pageBreakAts = <Integer>newLinkedHashSet

	new(XSSFSheet sheet, float maxPaperWidth) {
		super(sheet, maxPaperWidth)
		pageBreakAts.addAll(sheet.columnBreaks)
	}

	override protected addTableToTemplate(Element tableTemplate,
		Element table) {
		val block = doc.createElement(FO_BLOCK)
		block.appendChild(table)
		tableTemplate.appendChild(block)
	}

	override protected getFopPageTemplate() throws ParserConfigurationException, SAXException, IOException {
		val doc = XSL_TEMPLATE_PATH.parseTemplate
		val rootNode = doc.getElementsByTagName(XSL_STYLESHEET).item(0)
		val multipageTemplate = MULTIPAGE_LAYOUT_TEMPLATE_PATH.parseTemplate
		val newPageBlock = multipageTemplate.findNodebyTagName(FO_BLOCK,
			ATTR_ID, START_INDENT)
		if (newPageBlock.isPresent) {
			newPageBlock.get.setAttribute(START_INDENT,
				String.format("-%scm", maxPaperWidth.toString))
		}

		val clone = doc.importNode(multipageTemplate.firstChild, true)
		val singlePageLayout = doc.findNodebyTagName(XSL_TEMPLATE, ATTR_NAME,
			XSL_MAINPAGE_TEMPLATE_NAME)
		if (singlePageLayout.present) {
			rootNode.replaceChild(clone, singlePageLayout.get)
		} else {
			rootNode.appendChild(clone)
		}
		return doc
	}

	override protected getTableStyle() {
		return new TransformTableBody(doc, sheet).
			pageBreakColumnCellStyle(pageBreakAts)
	}

	override protected transformColumn(LinkedHashSet<Element> cols,
		float sumWidth, int colsIndex, int columNumber) {
		var columnWidth = sheet.getColumnWidthInCm(colsIndex).floatValue

		if (pageBreakAts.contains(colsIndex) ||
			isBigerThanPaperWidth(sumWidth + columnWidth, colsIndex + 1)) {
			// set reamining width
			pageBreakAts.add(colsIndex)
			columnWidth = maxPaperWidth - sumWidth

			// Add last column in page
			cols.add(createTableColumn(columNumber, columnWidth))
			// Add repeating column to new page
			val repeatColumns = cols.transformBreakColumns(columNumber)
			return Pair.of(repeatColumns.key, repeatColumns.value)
		} else {
			cols.add(createTableColumn(columNumber, columnWidth))
			return Pair.of(columNumber, sumWidth + columnWidth)
		}

	}

	private def Pair<Integer, Float> transformBreakColumns(LinkedHashSet<Element> columns,
		int columnNumber) {
		val repeatingColumns = getRepeatingColumns(sheet)
		if (repeatingColumns.empty) {
			// When repeating columns is empty,
			// then add only numerical order column
			val columnWidth = sheet.getColumnWidthInCm(0).floatValue
			columns.add(
				createTableColumn(columnNumber,
					columnWidth))
			return columnNumber + 1 -> columnWidth
		}
		var repeatColumnsWidth = 0f;
		for (var index = 0; index < repeatingColumns.size; index++) {
			val colIndex = columnNumber + index + 1
			val columnWidth = sheet.getColumnWidthInCm(repeatingColumns.toList.get(index)).floatValue
			repeatColumnsWidth += columnWidth
			columns.add(
				createTableColumn(colIndex,columnWidth))
		}
		return columnNumber + repeatingColumns.size -> repeatColumnsWidth
	}

	override protected getCellSpanColumn(Optional<Cell> excelCell) {
		val cellSpanRange = excelCell.get.columnSpanRangeAt
		if (cellSpanRange.present) {
			val spanCount = cellSpanRange.get.numberOfCells
			// When page break at a span column, then spilt this span 
			val breakInSpan = pageBreakAts.findFirst [
				it < cellSpanRange.get.lastColumn &&
					it > cellSpanRange.get.firstColumn
			]
			if (breakInSpan !== null) {
				val colIndex = excelCell.get.columnIndex
				if (excelCell.cellStringValue.empty) {
					excelCell.get.cellValue = excelCell.get.sheet.getCellAt(
						cellSpanRange.get.firstRow,
						cellSpanRange.get.firstColumn)?.get.cellStringValue?.get
				}
				// add 1 for counting the break column itself
				return colIndex <= breakInSpan
					? breakInSpan - cellSpanRange.get.firstColumn + 1
					: cellSpanRange.get.lastColumn - breakInSpan
			}
			return spanCount
		}
		return 0
	}

	override protected addCell(Set<Element> cells, int rowIndex, Element cell,
		int cellStartCol) {
		cells.add(cell)
		if (!pageBreakAts.contains(cellStartCol)) {
			return
		}
		// Last column of page should have wide right border
		cells.lastOrNull?.setBorderStyle(BorderDirection.RIGHT, WIDE_BORDER_STYLE)
		val repeatingColumns = getRepeatingColumns(sheet)
		if (repeatingColumns.empty) {
			cells.add(transformFirstColumnCell(sheet.getRow(rowIndex)))
			return
		}
		repeatingColumns.forEach [ colIndex |
			if (colIndex === 0) {
				cells.add(transformFirstColumnCell(sheet.getRow(rowIndex)))
			} else if (sheet.getRowSpanRangeAt(rowIndex, colIndex).empty &&
				(sheet.getCellAt(rowIndex, colIndex).present ||
					rowIndex === sheet.headerLastRowIndex)) {
				val cloneNode = cells.get(colIndex).cloneNode(true) as Element
				val columnsSpanned = cloneNode.getAttribute(
					NUMBER_COLUMNS_SPANNED)

				// Last repeated column should have wide right border
				if (colIndex === repeatingColumns.size() - 1) {
					cloneNode.setBorderStyle(BorderDirection.RIGHT,
						WIDE_BORDER_STYLE)
				}
				if (columnsSpanned.empty || columnsSpanned.equals("1")) {
					cells.add(cloneNode)
				} else {
					try {
						val spanCount = Integer.parseInt(columnsSpanned)
						val repeatingSpan = repeatingColumns.filter [
							colIndex <= it && (colIndex + spanCount >= it)
						]
						cloneNode.setAttribute(NUMBER_COLUMNS_SPANNED,
							repeatingSpan.size.toString)
						cells.add(cloneNode)
					} catch (NumberFormatException e) {
						throw new NumberFormatException
					}
				}
			}
		]
	}

	private def boolean isBigerThanPaperWidth(float currentSumWidth,
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
			return currentSumWidth + groupSumWidth > maxPaperWidth
		}
		return currentSumWidth + sheet.getColumnWidthInCm(nextColumnIndex) >
			maxPaperWidth
	}
}
