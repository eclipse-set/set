/** 
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.excel

import java.util.Optional
import java.util.Set
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.util.Units
import java.util.List
import java.util.Collections

/**
 * Extension for Excel Workbook
 * @autor Truong
 */
class HSSFWorkbookExtension {

	static float FONT_SCALE_FACTOR = 1.0725f;
	static final float CM_PER_INCH = 2.54f;

	static def Optional<Cell> getCellAt(Sheet sheet, int rowIndex,
		int columnIndex) {
		return Optional.ofNullable(sheet.getRow(rowIndex)?.getCell(columnIndex))
	}

	static def Optional<String> getCellStringValue(Optional<Cell> cell) {
		if (cell.isPresent) {
			return cell.get.cellStringValue
		}
		return Optional.empty
	}

	static def Optional<String> getCellStringValue(Cell cell) {
		return Optional.ofNullable(
			cell?.stringCellValue.isNullOrEmpty ? null : cell.stringCellValue
		)
	}

	static def Optional<CellRangeAddress> getColumnSpanRangeAt(Cell cell) {
		if (cell === null) {
			return Optional.empty
		}
		return getColumnSpanRangeAt(cell.sheet, cell.columnIndex, cell.rowIndex)
	}

	static def Optional<CellRangeAddress> getColumnSpanRangeAt(Sheet sheet,
		int columnIndex, int rowIndex) {
		return Optional.ofNullable(
			sheet.mergedRegions.filter[firstRow === lastRow].filter [
				firstRow === rowIndex
			].findFirst[firstColumn <= columnIndex && lastColumn >= columnIndex]
		)
	}

	static def float getColumnWidthInCm(Sheet sheet, int columnIndex) {
		val widthPX = Math.round(sheet.getColumnWidthInPixels(columnIndex) *
			FONT_SCALE_FACTOR)
		return Math.round(
			(widthPX.floatValue / Units.PIXEL_DPI).doubleValue * CM_PER_INCH *
				100) / 100f
	}

	static def int getHeaderLastRowIndex(Sheet sheet) {
		return sheet.repeatingRows.lastRow
	}

	static def int getHeaderLastColumnIndex(Sheet sheet) {
		val firstRow = sheet.getRow(0)
		// Skip first column, that is count number column and
		// can maybe null or empty
		var lastColumnIndex = 1
		for (var i = lastColumnIndex; i < firstRow.lastCellNum; i++) {
			val cell = firstRow.getCell(i)
			if (cell.cellStringValue.isEmpty) {
				return lastColumnIndex
			}
			lastColumnIndex = i
		}

		return lastColumnIndex
	}

	static def CellRangeAddress[] getParentGroupSpan(Sheet sheet) {
		return sheet.mergedRegions.filter[firstRow === 1]
	}

	static def Set<Integer> getRepeatingColumns(Sheet sheet) {
		val result = <Integer>newHashSet
		if (sheet === null || sheet.repeatingColumns === null) {
			return result
		}
		val repeatingRange = sheet.repeatingColumns
		
		for (var i = repeatingRange.firstColumn; i <=
			repeatingRange.lastColumn; i++) {
			result.add(i)
		}
		return result
	}

	static def Optional<CellRangeAddress> getRowSpanRangeAt(Cell cell) {
		return getRowSpanRangeAt(cell.sheet, cell.rowIndex, cell.columnIndex)
	}

	static def Optional<CellRangeAddress> getRowSpanRangeAt(Sheet sheet,
		int rowIndex, int columnIndex) {
		return Optional.ofNullable(
			sheet.mergedRegions.filter[firstColumn === lastColumn].filter [
				firstColumn === columnIndex
			].findFirst[firstRow <= rowIndex && lastRow >= rowIndex]
		)
	}

	static def float getRowHeightInCm(Sheet sheet, int rowIndex) {
		val height = Units.pointsToPixel(
			sheet.getRow(rowIndex).getHeightInPoints());
		val heightPx = Math.round(height * FONT_SCALE_FACTOR);
		return Math.round(heightPx.floatValue / Units.PIXEL_DPI * CM_PER_INCH *
			100) / 100f;
	}
	
	static def List<Cell> getFirstDataRow(Sheet sheet) {
		val hearLastRowIndex = sheet.headerLastRowIndex
		val firstDataRow = sheet.getRow(hearLastRowIndex + 1)
		if (firstDataRow === null || firstDataRow.empty) {
			return Collections.emptyList
		}
		return firstDataRow.iterator.toList.filter[
			val firstRowCellValue = sheet.getCellAt(0, columnIndex).cellStringValue
			return firstRowCellValue.present
		].toList
	}
}
