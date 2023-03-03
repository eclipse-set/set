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
import java.util.Optional
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.w3c.dom.Element

import static org.eclipse.set.utils.table.transform.XSLConstant.XSLStyleSets.*
import static org.eclipse.set.utils.table.transform.XSLConstant.TableAttribute.*
import org.eclipse.set.utils.table.transform.XSLConstant.TableAttribute.BorderDirection

/**
 * Transform excel cell style to xsl style
 * @author Truong
 */
class TransformStyle {
	/**
	 * transform excel cell style to xsl style
	 */
	static def void transformCellStyle(Element xslCell, Optional<Cell> excelCell) {
		if (excelCell.empty) {
			return
		}
		val excelStyle = excelCell.get.cellStyle
		xslCell.borderStyle = excelStyle.transformBorderStyle
		
		xslCell.transformTextAlign(excelStyle.alignment, excelStyle.verticalAlignment)
		xslCell.transformTextFont(excelCell.get)
	}
	
	private static def void setBorderStyle(Element cell,
		HashMap<BorderDirection, String> borderStyles) {
		borderStyles.filter[directionm, style| !style.empty].forEach [ direction, style |
			val directionString = direction.directionString
			cell.setAttribute(directionString, String.format("{$%s}", style))
		]
	}
	
	def static HashMap<BorderDirection, String> transformBorderStyle(CellStyle style) {
		return newHashMap(
			Pair.of(BorderDirection.TOP, style.borderTop.transformBorderStyle),
			Pair.of(BorderDirection.BOTTOM, style.borderBottom.transformBorderStyle),
			Pair.of(BorderDirection.LEFT, style.borderLeft.transformBorderStyle),
			Pair.of(BorderDirection.RIGHT, style.borderRight.transformBorderStyle)
		)
	}

	private static def String transformBorderStyle(BorderStyle borderStyle) {
		switch(borderStyle) {
			case THIN:
				return SMALL_BORDER_STYLE
			case MEDIUM:
				return WIDE_BORDER_STYLE
			// Do not render other styles
			default:
				return ""
		}
	}
	
	private static def void transformTextAlign(Element xslCell, HorizontalAlignment horizonAlign, VerticalAlignment verticalAlign) {
		var horizon = ""
		switch(horizonAlign) {
			case LEFT:
				horizon = HorizontalAlignment.LEFT.name
			case RIGHT:
				horizon = HorizontalAlignment.RIGHT.name
			default:
				horizon = HorizontalAlignment.CENTER.name
		}
		xslCell.setAttribute(TEXT_ALIGN, horizon.toLowerCase)
	}
	
	private static def void transformTextFont(Element xslCell, Cell excelCell) {
		val workbook = excelCell.row.sheet.workbook
		val cellFontIndex = excelCell.cellStyle.fontIndexAsInt
		val fontStyle = workbook.getFontAt(cellFontIndex)
		if (fontStyle.bold) {
			xslCell.setAttribute(FONT_WEIGHT, "bold")
		}
		
		if (fontStyle.italic) {
			xslCell.setAttribute(FONT_STYLE, "italic")
		}
	}

}