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
import javax.xml.transform.TransformerException
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.w3c.dom.Element
import org.xml.sax.SAXException

import static org.eclipse.set.utils.export.xsl.XSLConstant.TableAttrValue.FOOTNOTES
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLFoAttributeName.ATTR_SELECT
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLTag.*

import static extension org.eclipse.set.utils.excel.ExcelWorkbookExtension.*

/**
 * Transform Excel table header to XSL
 * 
 * @author Truong 
 */
class SinglePageTableHeader extends AbstractTransformTableHeader {

	new(XSSFSheet sheet, float maxPaperWidth) {
		super(sheet, maxPaperWidth)
	}

	override protected getFopPageTemplate() throws ParserConfigurationException, SAXException, IOException, TransformerException {
		return XSL_TEMPLATE_PATH.parseTemplate

	}

	override protected transformColumn(LinkedHashSet<Element> cols,
		float sumWidth, int colsIndex, int columNumber) {
		val columnWidth = sheet.getColumnWidthInCm(colsIndex).floatValue
		cols.add(createTableColumn(columNumber, columnWidth))
		return Pair.of(columNumber, sumWidth + columnWidth)
	}
	
	
	override protected getCellSpanColumn(Optional<Cell> excelCell) {
		val cellSpanRange = excelCell.get.columnSpanRangeAt
		if (cellSpanRange.present) {
			return cellSpanRange.get.numberOfCells

		}
		return 0
	}
	
	
	override protected addCell(Set<Element> cells, int rowIndex, Element cell, int cellStartCol) {
		cells.add(cell)
	}


	override protected addTableToTemplate(Element tableTemplate,
		Element table) {
		tableTemplate.appendChild(table)
		val footNote = doc.createElement(XSL_APPLY_TEMPLATE)
		footNote.setAttribute(ATTR_SELECT, FOOTNOTES)
		tableTemplate.appendChild(footNote)
	}

	override protected getTableStyle() {
		return new TransformTableBody(doc, sheet).defaultStyles
	}
}
