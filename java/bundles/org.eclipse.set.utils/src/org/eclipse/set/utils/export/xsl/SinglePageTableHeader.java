/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.utils.export.xsl;

import static org.eclipse.set.utils.excel.ExcelWorkbookExtension.getColumnSpanRangeAt;
import static org.eclipse.set.utils.excel.ExcelWorkbookExtension.getColumnWidthInCm;
import static org.eclipse.set.utils.export.xsl.XSLConstant.TableAttrValue.FOOTNOTES;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLFoAttributeName.ATTR_SELECT;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLTag.XSL_APPLY_TEMPLATE;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.eclipse.xtext.xbase.lib.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Transform Excel table header to XSL
 * 
 * @author Truong
 */
public class SinglePageTableHeader extends AbstractTransformTableHeader {

	protected SinglePageTableHeader(final XSSFSheet sheet,
			final float maxPaperWidth) {
		super(sheet, maxPaperWidth);
	}

	@Override
	protected Document getFopPageTemplate() throws ParserConfigurationException,
			SAXException, IOException, TransformerException {
		return parseTemplate(XSL_TEMPLATE_PATH);
	}

	@SuppressWarnings("boxing")
	@Override
	protected Pair<Integer, Float> transformColumn(
			final LinkedHashSet<Element> cols, final float sumWidth,
			final int colsIndex, final int columNumber) {
		final float columnWidth = getColumnWidthInCm(sheet, colsIndex);
		cols.add(createTableColumn(columNumber, columnWidth));
		return Pair.of(columNumber, sumWidth + columnWidth);
	}

	@Override
	protected int getCellSpanColumn(final Optional<Cell> excelCell) {
		if (excelCell.isPresent()) {
			final Optional<CellRangeAddress> cellSpanRange = getColumnSpanRangeAt(
					excelCell.get());
			return cellSpanRange.isPresent()
					? cellSpanRange.get().getNumberOfCells()
					: 0;
		}
		return 0;
	}

	@Override
	protected void addCell(final Set<Element> cells, final int rowIndex,
			final Element cell, final int cellLastCol) {
		cells.add(cell);
	}

	@Override
	protected void addTableToTemplate(final Element tableTemplate,
			final Element table) {
		tableTemplate.appendChild(table);
		final Element footNote = doc.createElement(XSL_APPLY_TEMPLATE);
		footNote.setAttribute(ATTR_SELECT, FOOTNOTES);
		tableTemplate.appendChild(footNote);

	}

	@Override
	protected Set<Element> getTableStyle() {
		return new TransformTableBody(doc, sheet).getDefaultStyles();
	}

}
