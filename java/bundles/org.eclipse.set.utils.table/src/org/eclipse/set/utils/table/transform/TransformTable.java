/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.transform;

import static org.eclipse.set.utils.excel.ExcelWorkbookExtension.getColumnWidthInCm;
import static org.eclipse.set.utils.excel.ExcelWorkbookExtension.getHeaderLastColumnIndex;
import static org.eclipse.set.utils.table.transform.XSLConstant.XSLFoAttributeName.ATTR_NAME;
import static org.eclipse.set.utils.table.transform.XSLConstant.XSLTag.XSL_VARIABLE;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Optional;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Transform excel table to xsl document
 * 
 * @author Truong
 */
public class TransformTable {
	private static final String EXCEL_TEMPLATE_PATH = "data/export/excel"; //$NON-NLS-1$
	private static final String WATER_MARK_TEMPLATE_NAME = "water-mark-content"; //$NON-NLS-1$
	private static final float A3_PAPER_WIDTH = 42f;
	String shortcut;
	String tableTyle;

	private static final Logger logger = LoggerFactory
			.getLogger(TransformTable.class);

	/**
	 * @param tableShortcut
	 *            the shortcut of table
	 * @param tableType
	 *            the type of table
	 */
	public TransformTable(final String tableShortcut, final String tableType) {
		this.shortcut = tableShortcut;
		this.tableTyle = tableType;
	}

	/**
	 * Transform Excel table to xsl document
	 * 
	 * @return xsl document
	 * @throws IOException
	 *             {@link IOException}
	 * @throws ParserConfigurationException
	 *             {@link ParserConfigurationException}
	 * @throws SAXException
	 *             {@link SAXException}
	 * @throws TransformerException
	 *             {@link TransformerException}
	 */
	public Document transform() throws IOException,
			ParserConfigurationException, SAXException, TransformerException {
		final File excelFile = getTemplatePath();
		final XSSFSheet tableSheet = getTableSheet(excelFile);
		if (tableSheet == null) {
			return null;
		}
		final float contentWidth = getContentWidth(tableSheet);

		final AbstractTransformTableHeader transformHeader = needToBreakPage(
				tableSheet, contentWidth)
						? new MultiPageTableHeader(tableSheet, contentWidth)
						: new SinglePageTableHeader(tableSheet, contentWidth);
		final Document xslDoc = transformHeader.transform();
		if (tableTyle != null) {
			setWaterMarkContent(xslDoc);
		}

		return xslDoc;
	}

	private void setWaterMarkContent(final Document xslDoc) {
		final Optional<Element> waterMarkVariable = AbstractTransformTableHeader
				.findNodebyTagName(xslDoc, XSL_VARIABLE, ATTR_NAME,
						WATER_MARK_TEMPLATE_NAME);
		if (waterMarkVariable.isPresent()) {
			waterMarkVariable.get().setTextContent(tableTyle);
		}
	}

	private static float getContentWidth(final XSSFSheet sheet) {
		final XSSFPrintSetup printSetup = sheet.getPrintSetup();
		final double leftMargin = printSetup.getLeftMargin() * 2.54;
		final double rightMargin = printSetup.getRightMargin() * 2.54;
		return Math.round(
				(A3_PAPER_WIDTH - (float) (leftMargin + rightMargin)) * 100)
				/ 100f;
	}

	private static XSSFSheet getTableSheet(final File templateFile)
			throws IOException {
		if (templateFile.canRead()) {
			try (final InputStream inputStream = new FileInputStream(
					templateFile);
					final XSSFWorkbook workbook = new XSSFWorkbook(
							inputStream)) {
				return workbook.getSheetAt(0);
			}
		}
		logger.error("Missing pdf export template: " + templateFile.toString()); //$NON-NLS-1$
		return null;
	}

	private File getTemplatePath() {
		return Paths
				.get(String.format("%s/%s_vorlage.xlsx", EXCEL_TEMPLATE_PATH, //$NON-NLS-1$
						shortcut))
				.toAbsolutePath().toFile();
	}

	/**
	 * @param doc
	 *            xsl document
	 * @return streamsource of xsl document
	 * @throws TransformerException
	 *             {@link TransformerException}
	 */
	public static StreamSource toStreamSource(final Document doc)
			throws TransformerException {
		final DOMSource domSource = new DOMSource(doc);
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final StreamResult result = new StreamResult(baos);
		final Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.transform(domSource, result);

		return new StreamSource(new ByteArrayInputStream(baos.toByteArray()),
				domSource.getSystemId());
	}

	private static boolean needToBreakPage(final XSSFSheet sheet,
			final float maxContentWidth) {
		final int[] columnBreaks = sheet.getColumnBreaks();
		if (columnBreaks.length > 0) {
			return true;
		}
		float sumWidth = 0f;
		for (int i = 0; i <= getHeaderLastColumnIndex(sheet); i++) {
			final float columnWidthInCm = getColumnWidthInCm(sheet, i);
			sumWidth += columnWidthInCm;
		}

		return sumWidth > maxContentWidth;
	}
}
