/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.transform

import java.util.HashMap
import java.util.Map
import java.util.Optional
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.eclipse.set.utils.table.transform.XSLConstant.TableAttribute.BorderDirection
import org.w3c.dom.Element

import static org.eclipse.set.utils.table.transform.XSLConstant.TableAttribute.*
import static org.eclipse.set.utils.table.transform.XSLConstant.XSLStyleSets.*

/**
 * Transform excel cell style to xsl style
 * @author Truong
 */
class TransformStyle {

	/**
	 * transform excel cell style to xsl style
	 */
	static def void transformCellStyle(Element xslCell,
		Optional<Cell> excelCell) {
		if (excelCell.empty) {
			return
		}
		val excelStyle = excelCell.get.cellStyle
		xslCell.borderStyle = excelStyle.transformBorderStyle

		xslCell.transformTextAlign(excelStyle.alignment,
			excelStyle.verticalAlignment)
		xslCell.transformTextFont(excelCell.get)
	}

	/**
	 * Set border style for xsl element
	 */
	private static def void setBorderStyle(Element cell,
		HashMap<BorderDirection, String> borderStyles) {
		borderStyles.filter[directionm, style|!style.empty].forEach [ direction, style |
			val directionString = direction.directionString
			cell.setAttribute(directionString, '''{$«style»}''')
		]
	}

	/**
	 * Set border style for xsl element
	 */
	static def void setBorderStyle(Optional<Cell> cell, Element xslCell,
		BorderDirection direction) {
		if (cell.empty) {
			return
		}
		val borderStyle = cell.get.cellStyle.transformBorderStyle.get(direction)
		xslCell.setBorderStyle(direction, borderStyle)
	}

	/**
	 * Set border style for xsl element
	 */
	static def void setBorderStyle(Element xslCell, BorderDirection direction,
		String borderStyle) {
		if (!borderStyle.empty) {
			xslCell.setAttribute(
				direction.directionString, '''{$«borderStyle»}''')
		} else if (xslCell.getAttribute(direction.directionString) !== null) {
			xslCell.removeAttribute(direction.directionString)
		}
	}

	/**
	 * Transform borderstyle of excel cell to xsl element
	 */
	def static HashMap<BorderDirection, String> transformBorderStyle(
		CellStyle style) {
		return newHashMap(
			Pair.of(BorderDirection.TOP, style.borderTop.transformBorderStyle),
			Pair.of(BorderDirection.BOTTOM,
				style.borderBottom.transformBorderStyle),
			Pair.of(BorderDirection.LEFT,
				style.borderLeft.transformBorderStyle),
			Pair.of(BorderDirection.RIGHT,
				style.borderRight.transformBorderStyle)
		)
	}

	private static def String transformBorderStyle(BorderStyle borderStyle) {
		switch (borderStyle) {
			case THIN:
				return SMALL_BORDER_STYLE
			case MEDIUM:
				return WIDE_BORDER_STYLE
			// Do not render other styles
			default:
				return ""
		}
	}

	/**
	 * Set border style for excel cell
	 */
	static def void setExcelCellBorderStyle(Optional<Cell> cell,
		Map<BorderDirection, BorderStyle> borderStyles) {
		borderStyles.forEach [ direction, style |
			cell.setExcelCellBorderStyle(direction, style)
		]
	}

	/**
	 * Set border style for excel cell
	 */
	static def void setExcelCellBorderStyle(Optional<Cell> cell,
		BorderDirection direction, BorderStyle style) {
		if (cell.empty) {
			return
		}
		val wb = cell.get.sheet.workbook
		val newStyle = wb.createCellStyle
		newStyle.cloneStyleFrom(cell.get.cellStyle)
		switch (direction) {
			case LEFT:
				newStyle.borderLeft = style
		
			case RIGHT:
				newStyle.borderRight = style
			case TOP:
				newStyle.borderTop = style
			case BOTTOM:
				newStyle.borderBottom = style
		}
		cell.get.cellStyle = newStyle
	}

	/**
	 * Set text algin for xsl element
	 */
	private static def void transformTextAlign(Element xslCell,
		HorizontalAlignment horizonAlign, VerticalAlignment verticalAlign) {
		var horizon = ""
		switch (horizonAlign) {
			case LEFT:
				horizon = HorizontalAlignment.LEFT.name
			case RIGHT:
				horizon = HorizontalAlignment.RIGHT.name
			default:
				horizon = HorizontalAlignment.CENTER.name
		}
		xslCell.setAttribute(TEXT_ALIGN, horizon.toLowerCase)
	}

	/**
	 * Set text font for xsl element
	 */
	private static def void transformTextFont(Element xslCell, Cell excelCell) {
		val workbook = excelCell.row.sheet.workbook
		val cellFontIndex = excelCell.cellStyle.fontIndex
		val fontStyle = workbook.getFontAt(cellFontIndex)
		if (fontStyle.bold) {
			xslCell.setAttribute(FONT_WEIGHT, "bold")
		}

		if (fontStyle.italic) {
			xslCell.setAttribute(FONT_STYLE, "italic")
		}
	}

}
