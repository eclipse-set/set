/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.export.xsl;

import static org.eclipse.set.utils.excel.ExcelWorkbookExtension.getColumnWidthInCm;
import static org.eclipse.set.utils.excel.ExcelWorkbookExtension.getHeaderLastColumnIndex;
import static org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.XSL_USE_ATTRIBUTE_SETS;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLFoAttributeName.*;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLNodeName.WATER_MARK_TEMPLATE_NAME;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLStyleSets.BODY_ROW_CELL_STYLE;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLTag.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
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
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * Transform excel table to xsl document
 * 
 * @author Truong
 */
public class TransformTable {
	private static final String EXCEL_TEMPLATE_PATH = "data/export/excel"; //$NON-NLS-1$
	private static final float A3_PAPER_WIDTH = 42f;
	ExportType exportType;
	String shortcut;
	TableType tableType;
	private AbstractTransformTableHeader transformHeader;
	private final EnumTranslationService enumTranslation;

	private static final Logger logger = LoggerFactory
			.getLogger(TransformTable.class);

	/**
	 * @param exportType
	 *            the type of export
	 * @param tableShortcut
	 *            the shortcut of table
	 * @param tableType
	 *            the type of table
	 * @param enumTranslation
	 *            the {@link EnumTranslationService}
	 */
	public TransformTable(final ExportType exportType,
			final String tableShortcut, final TableType tableType,
			final EnumTranslationService enumTranslation) {
		this.exportType = exportType;
		this.shortcut = tableShortcut;
		this.tableType = tableType;
		this.enumTranslation = enumTranslation;
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

		transformHeader = needToBreakPage(tableSheet, contentWidth)
				? new MultiPageTableHeader(tableSheet, contentWidth)
				: new SinglePageTableHeader(tableSheet, contentWidth);
		final Document xslDoc = transformHeader.transform();
		if (tableType != null && tableType != TableType.DIFF
				&& exportType != ExportType.INVENTORY_RECORDS) {
			setWaterMarkContent(xslDoc);
		}

		return xslDoc;
	}

	/**
	 * Transform Excel table to xsl document with break page point
	 * 
	 * @param breakAtRows
	 *            row index should be break
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
	@SuppressWarnings("nls")
	public Document transform(final List<String> breakAtRows)
			throws IOException, ParserConfigurationException, SAXException,
			TransformerException {
		final Document doc = transform();
		final Node rootNode = doc.getElementsByTagName(XSL_STYLESHEET).item(0);
		breakAtRows.forEach(rowNumber -> {
			final Element template = doc.createElement(XSL_TEMPLATE);
			template.setAttribute(ATTR_MATCH,
					String.format("Row[@group-number = '%s']", rowNumber));
			final Element tableRow = doc.createElement(FO_TABLE_ROW);
			tableRow.setAttribute("break-after", "page");
			final Element tableCell = doc.createElement(FO_TABLE_CELL);
			tableCell.setAttribute(XSL_USE_ATTRIBUTE_SETS, BODY_ROW_CELL_STYLE);
			final Element block = doc.createElement(FO_BLOCK);
			final Element valueof = doc.createElement(XSL_VALUE_OF);
			valueof.setAttribute(ATTR_SELECT, "@group-number");
			block.appendChild(valueof);
			tableCell.appendChild(block);

			final Element applytemplate = doc.createElement(XSL_APPLY_TEMPLATE);
			tableRow.appendChild(tableCell);
			tableRow.appendChild(applytemplate);
			template.appendChild(tableRow);
			rootNode.appendChild(template);
		});

		return doc;

	}

	private void setWaterMarkContent(final Document xslDoc) {
		final Optional<Element> waterMarkVariable = AbstractTransformTableHeader
				.findNodebyTagName(xslDoc, XSL_VARIABLE, ATTR_NAME,
						WATER_MARK_TEMPLATE_NAME);
		if (waterMarkVariable.isPresent()) {
			try {
				waterMarkVariable.get()
						.setTextContent(enumTranslation.translate(tableType)
								.getPresentation());
			} catch (final NullPointerException e) {
				waterMarkVariable.get().setTextContent(""); //$NON-NLS-1$
			}

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
		// IMPROVE: this is only a temporary situation for the table
		// Sskp_dm
		String tableShortcut = shortcut;
		if (shortcut.equals("sskp_dm")) { //$NON-NLS-1$
			tableShortcut = "sskp"; //$NON-NLS-1$
		}
		return Paths
				.get(String.format("%s/%s_vorlage.xlsx", EXCEL_TEMPLATE_PATH, //$NON-NLS-1$
						tableShortcut))
				.toAbsolutePath()
				.toFile();
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

	/**
	 * @return true, if it is the multi page layotu
	 */
	public boolean isMultiPageLayout() {
		return transformHeader instanceof MultiPageTableHeader;
	}
}
