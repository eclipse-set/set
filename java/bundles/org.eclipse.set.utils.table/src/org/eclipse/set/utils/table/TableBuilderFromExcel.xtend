/** 
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table

import java.util.Optional
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.util.CellRangeAddress
import org.eclipse.set.model.tablemodel.TablemodelFactory

import static extension org.eclipse.set.utils.excel.ExcelWorkbookExtension.*

/**
 * Extension for create table from excel template
 * @autor Truong
 */
class TableBuilderFromExcel {

	def static void headerBuilder(Sheet sheet, GroupBuilder root,
		int columnIndex) {
		val cell = sheet.getCellAt(1, columnIndex)

		if (cell.empty) {
			return
		}
		val lastCellofGroup = root.nestedGroupBuilder(cell).get
		headerBuilder(sheet, root, lastCellofGroup.columnIndex + 1)
	}

	private def static Optional<Cell> nestedGroupBuilder(GroupBuilder parent,
		Optional<Cell> cell) {
		if (cell.cellStringValue.empty) {
			return cell
		}
		val sheet = cell.get.sheet
		val columnSpan = cell.get.getColumnSpanRangeAt
		if (columnSpan.present) {
			return sheet.nestedColumnGroupBuilder(parent, columnSpan.get)
		}
		return parent.singleGroupBuilder(cell.get)
	}

	private def static Optional<Cell> nestedColumnGroupBuilder(Sheet sheet,
		GroupBuilder parent, CellRangeAddress columnSpanRange) {
		val firstColumnIndex = columnSpanRange.firstColumn
		val lastColumnIndex = columnSpanRange.lastColumn
		val currentRowIndex = columnSpanRange.firstRow
		var currentCell = sheet.getCellAt(currentRowIndex, firstColumnIndex)
		var currentCellValue = currentCell.cellStringValue
		if (firstColumnIndex === lastColumnIndex || currentCellValue.empty) {
			return currentCell
		}

		val currentGroup = parent.addGroup(currentCellValue.get)
		if (currentCell.isPresent) {
			currentGroup.groupRoot.height = sheet.getCellHeight(currentCell.get)
		}

		for (var i = firstColumnIndex; i <= lastColumnIndex; i++) {
			val nextRowIndex = currentRowIndex + 1
			val childCell = sheet.getCellAt(nextRowIndex, i)
			currentCell = currentGroup.nestedGroupBuilder(childCell)
		}
		return currentCell
	}

	private def static Optional<Cell> singleGroupBuilder(GroupBuilder parent,
		Cell cell) {
		if (Optional.of(cell).isSingleColumnGroup) {
			val singleGroup = parent.addGroup(cell.stringCellValue)
			val cellAtNextRow = cell.sheet.getCellAt(cell.rowIndex + 1,
				cell.columnIndex)
			if (cellAtNextRow.present) {
				return singleGroup.singleGroupBuilder(cellAtNextRow.get)
			}
		}
		return parent.cellBuild(cell)
	}

	def static boolean isSingleColumnGroup(Optional<Cell> cell) {
		if (cell.empty && cell.get.stringCellValue.empty) {
			return false
		}

		val lastRowOfSheet = cell.get.sheet.headerLastRowIndex
		val nextRow = cell.get.rowIndex + 1
		if (lastRowOfSheet !== cell.get.rowIndex &&
			lastRowOfSheet !== nextRow) {
			val nextRowCell = cell.get.sheet.getCellAt(nextRow,
				cell.get.columnIndex)
			return nextRowCell.cellStringValue.present
		}

		return false
	}

	private def static Optional<Cell> cellBuild(GroupBuilder parent,
		Cell cell) {
		val sheet = cell.sheet
		val columnIndex = cell.columnIndex
		val columnName = sheet.getCellAt(0, columnIndex).cellStringValue
		val columnDescriptor = TablemodelFactory.eINSTANCE.
			createColumnDescriptor
		columnDescriptor.label = cell.stringCellValue
		columnDescriptor.width = sheet.getColumnWidthInCm(columnIndex).
			floatValue
		columnDescriptor.height = sheet.getCellHeight(cell)
		if (columnName.present) {
			columnDescriptor.columnPosition = columnName.get
		}

		val unitValue = sheet.getCellAt(sheet.headerLastRowIndex, columnIndex).
			cellStringValue
		if (unitValue.present) {
			parent.add(columnDescriptor, unitValue.get)
		} else {
			parent.add(columnDescriptor)
		}
		return Optional.of(cell)
	}

	private def static float getCellHeight(Sheet sheet, Cell cell) {
		var cellHeight = sheet.getRowHeightInCm(cell.rowIndex)
		val rowSpan = sheet.getRowSpanRangeAt(cell.rowIndex, cell.columnIndex)
		if (rowSpan.present) {
			val firstRow = rowSpan.get.firstRow
			val lastRow = rowSpan.get.lastRow
			for (var i = firstRow + 1; i <= lastRow; i++) {
				cellHeight += sheet.getRowHeightInCm(i)
			}
		}
		return cellHeight.floatValue
	}
}
