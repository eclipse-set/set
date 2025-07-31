/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.export.xsl;

import static org.eclipse.set.utils.excel.ExcelWorkbookExtension.*;
import static org.eclipse.set.utils.export.xsl.TransformStyle.setBorderStyle;
import static org.eclipse.set.utils.export.xsl.TransformStyle.transformCellStyle;
import static org.eclipse.set.utils.export.xsl.XSLConstant.TableAttrValue.*;
import static org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.*;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLFoAttributeName.ATTR_MATCH;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLFoAttributeName.ATTR_SELECT;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLStyleSets.TABLE_HEADER_STYLE;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLTag.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.BorderDirection;
import org.eclipse.xtext.xbase.lib.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Transform excel table header to xsl
 * 
 * @author Truong
 *
 */
public abstract class AbstractTransformTableHeader {
	protected static final String XSL_TEMPLATE_PATH = "data/export/pdf/table_template.xsl"; //$NON-NLS-1$
	protected static final float OFFSET = 0.01f;

	protected Document doc;
	protected XSSFSheet sheet;
	protected float maxPaperWidth;

	/**
	 * Constructor
	 * 
	 * @param sheet
	 *            {@link XSSFSheet}
	 * @param maxPaperWidth
	 *            the page width avaible for content
	 */
	protected AbstractTransformTableHeader(final XSSFSheet sheet,
			final float maxPaperWidth) {
		this.sheet = sheet;
		this.maxPaperWidth = maxPaperWidth;
	}

	/**
	 * Get document template
	 * 
	 * @param path
	 *            the path of template
	 * @return the document
	 * @throws ParserConfigurationException
	 *             {@link ParserConfigurationException}
	 * @throws SAXException
	 *             {@link SAXException}
	 * @throws IOException
	 *             {@link IOException}
	 */
	public static Document parseTemplate(final String path)
			throws ParserConfigurationException, SAXException, IOException {
		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		documentBuilderFactory.setFeature(
				"http://xml.org/sax/features/external-general-entities", false); //$NON-NLS-1$
		documentBuilderFactory.setFeature(
				"http://xml.org/sax/features/external-parameter-entities", //$NON-NLS-1$
				false);
		final DocumentBuilder builder = documentBuilderFactory
				.newDocumentBuilder();
		return builder.parse(Paths.get(path).toAbsolutePath().toFile());
	}

	/**
	 * Transform excel table header to xsl document
	 * 
	 * @return the xsl document
	 * @throws ParserConfigurationException
	 *             {@link ParserConfigurationException}
	 * @throws SAXException
	 *             {@link SAXException}
	 * @throws IOException
	 *             {@link IOException}
	 * @throws TransformerException
	 *             {@link TransformerException}
	 */
	public Document transform() throws ParserConfigurationException,
			SAXException, IOException, TransformerException {
		doc = getFopPageTemplate();
		final Node rootNode = doc.getElementsByTagName(XSL_STYLESHEET).item(0);
		final Element table = transformTable();

		rootNode.appendChild(table);
		rootNode.appendChild(emptyTableTemplate(doc));
		final Set<Element> tableStyles = getTableStyle();
		tableStyles.forEach(rootNode::appendChild);
		return doc;
	}

	protected abstract Document getFopPageTemplate()
			throws ParserConfigurationException, SAXException, IOException,
			TransformerException;

	protected Element transformTable() {
		final Element tableTemplate = doc.createElement(XSL_TEMPLATE);
		tableTemplate.setAttribute(ATTR_MATCH, TABLE_ROWS_ROW);
		final Element table = doc.createElement(FO_TABLE);
		table.setAttribute(TABLE_LAYOUT, LAYOUT_FIXED);
		table.setAttribute(TABLE_WIDTH, "100%"); //$NON-NLS-1$
		final Set<Element> tableColumns = transformColumns();
		tableColumns.forEach(table::appendChild);

		final Element tableHeader = doc.createElement(FO_TABLE_HEADER);
		tableHeader.setAttribute(XSL_USE_ATTRIBUTE_SETS, TABLE_HEADER_STYLE);

		final Set<Element> tableRows = transformRows();
		tableRows.forEach(tableHeader::appendChild);
		table.appendChild(tableHeader);

		final Element tableBody = doc.createElement(FO_TABLE_BODY);
		tableBody.setAttribute(START_INDENT, "0mm"); //$NON-NLS-1$
		final Element tableBodyChild = doc.createElement(XSL_APPLY_TEMPLATE);
		tableBodyChild.setAttribute(ATTR_SELECT, ROWS_ROW);
		tableBody.appendChild(tableBodyChild);
		table.appendChild(tableBody);
		addTableToTemplate(tableTemplate, table);
		return tableTemplate;
	}

	@SuppressWarnings("nls")
	private Set<Element> transformColumns() {
		final LinkedHashSet<Element> cols = new LinkedHashSet<>();
		float sumWidth = 0f;
		for (int i = 0, columNumber = 1; i <= getHeaderLastColumnIndex(
				sheet); i++, columNumber++) {
			final Pair<Integer, Float> pair = transformColumn(cols, sumWidth, i,
					columNumber);
			columNumber = pair.getKey().intValue();
			sumWidth = pair.getValue().floatValue();
		}

		// Fill remaining page width
		if (sumWidth < maxPaperWidth - OFFSET) {
			final float remainingWidth = maxPaperWidth - sumWidth - OFFSET;
			final float lastColumnWith = Float.parseFloat(cols.getLast()
					.getAttribute(COLUMN_WIDTH)
					.replace("cm", ""));
			cols.getLast()
					.setAttribute(COLUMN_WIDTH,
							Double.toString(lastColumnWith + remainingWidth)
									+ "cm");
		}
		return cols;
	}

	protected abstract Pair<Integer, Float> transformColumn(
			LinkedHashSet<Element> cols, float sumWidth, int colsIndex,
			int columNumber);

	protected Element createTableColumn(final int columnNumber,
			final float columnWidth) {
		final Element tableColumn = doc.createElement(FO_TABLE_COLUMN);
		tableColumn.setAttribute(COLUMN_NUMBER, Integer.toString(columnNumber));
		tableColumn.setAttribute(COLUMN_WIDTH,
				Float.toString(columnWidth) + "cm"); //$NON-NLS-1$
		return tableColumn;
	}

	protected Set<Element> transformRows() {
		final LinkedHashSet<Element> rows = new LinkedHashSet<>();
		final int headerLastRowIndex = getHeaderLastRowIndex(sheet);
		for (int i = 0; i <= headerLastRowIndex; i++) {
			final Element row = doc.createElement(FO_TABLE_ROW);
			final Set<Element> cells = transformRowCells(sheet.getRow(i));
			if (!cells.isEmpty()) {
				cells.forEach(row::appendChild);
				rows.add(row);
			}
		}
		return rows;
	}

	protected Set<Element> transformRowCells(final XSSFRow row) {
		final Set<Element> cells = new LinkedHashSet<>();
		final int headerLastColumnIndex = getHeaderLastColumnIndex(sheet);
		final int headerLastRowIndex = getHeaderLastRowIndex(sheet);
		final int rowNum = row.getRowNum();
		cells.add(transformFirstColumnCell(row));
		for (int i = 1; i <= headerLastColumnIndex; i++) {
			final Optional<Cell> excelCell = getCellAt(sheet, rowNum, i);
			if (rowNum == headerLastRowIndex || excelCell.isEmpty()) {
				addCell(cells, rowNum, createTableCell(excelCell), i);
				continue;
			}
			final Optional<CellRangeAddress> rowSpanRangeAt = getRowSpanRangeAt(
					excelCell.get());
			// Skip cell in row span
			if (!(rowSpanRangeAt.isPresent()
					&& rowSpanRangeAt.get().getFirstRow() != rowNum)) {
				final Element cell = createTableCell(excelCell);
				// Spring to last column of span
				i += transformSpanCell(cell, excelCell);
				addCell(cells, rowNum, cell, i);
			}
		}

		// Avoid hide empty row
		final boolean isEmptyRow = cells.stream()
				.allMatch(ele -> ele.getTextContent().isEmpty());
		// Only show empty row, when it is last row (unit row)
		if (isEmptyRow && rowNum == headerLastRowIndex) {
			handleEmptyRow(cells);
		}
		return cells;
	}

	private int transformSpanCell(final Element cell,
			final Optional<Cell> excelCell) {
		if (excelCell.isEmpty()) {
			return 0;
		}
		int spanCount = 0;
		final int columnIndex = excelCell.get().getColumnIndex();
		final int headerLastRowIndex = getHeaderLastRowIndex(sheet);
		final int spanColumnRange = getCellSpanColumn(excelCell);
		if (spanColumnRange > 0) {
			cell.setAttribute(NUMBER_COLUMNS_SPANNED,
					String.valueOf(spanColumnRange));
			// Set border style for last cell in column span
			setBorderStyle(excelCell, cell, BorderDirection.RIGHT);
			spanCount = spanColumnRange - 1;
		}

		final Optional<CellRangeAddress> rowSpanRangeAt = getRowSpanRangeAt(
				excelCell.get());
		if (rowSpanRangeAt.isPresent()) {
			final int rowSpanRange = rowSpanRangeAt.get().getNumberOfCells();
			cell.setAttribute(NUMBER_ROWS_SPANNED,
					String.valueOf(rowSpanRange));
			// Set border style for last cell in column span
			setBorderStyle(
					getCellAt(sheet, headerLastRowIndex - 1, columnIndex), cell,
					BorderDirection.BOTTOM);
		}
		return spanCount;
	}

	private void handleEmptyRow(final Set<Element> cells) {
		final Iterator<Element> iterator = cells.iterator();
		iterator.next();
		final Optional<Element> firstCell = Optional.of(iterator.next());
		Element firstCellBlock = (Element) firstCell.get().getFirstChild();
		if (firstCellBlock == null) {
			firstCellBlock = doc.createElement(FO_BLOCK);
			firstCell.get().appendChild(firstCellBlock);
		}
		firstCellBlock.setAttribute("color", "white"); //$NON-NLS-1$ //$NON-NLS-2$
		firstCellBlock.setTextContent("."); //$NON-NLS-1$
	}

	protected abstract int getCellSpanColumn(Optional<Cell> excelCell);

	protected abstract void addCell(Set<Element> cells, int rowIndex,
			Element cell, int cellLastCol);

	@SuppressWarnings("resource")
	protected Element transformFirstColumnCell(final Row row) {
		if (row.getCell(0) == null) {
			row.createCell(0, CellType.BLANK);
		}
		final Cell firstCell = row.getCell(0);
		final XSSFWorkbook workbook = sheet.getWorkbook();
		final XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.cloneStyleFrom(firstCell.getCellStyle());
		cellStyle.setBorderLeft(BorderStyle.THIN);
		firstCell.setCellStyle(cellStyle);
		return createTableCell(Optional.of(firstCell));
	}

	private Element createTableCell(final Optional<Cell> excelCell) {
		final Element cell = doc.createElement(FO_TABLE_CELL);
		transformCellStyle(cell, excelCell);
		final Element block = doc.createElement(FO_BLOCK);
		block.setAttribute(START_INDENT, "0mm"); //$NON-NLS-1$
		if (excelCell.isPresent()) {
			TransformStyle.setCellContent(block, excelCell.get());
		}

		cell.appendChild(block);
		return cell;
	}

	protected abstract void addTableToTemplate(Element tableTemplate,
			Element table);

	protected abstract Set<Element> getTableStyle();

	protected static Element emptyTableTemplate(final Document doc) {
		final Element template = doc.createElement(XSL_TEMPLATE);
		template.setAttribute(ATTR_MATCH, TABLE_NOT_ROWS_ROW);
		final Element block = doc.createElement(FO_BLOCK);
		block.setTextContent("Die Tabelle ist leer"); //$NON-NLS-1$
		template.appendChild(block);
		return template;
	}

	/**
	 * Find document element
	 * 
	 * @param doc
	 *            the document
	 * @param tag
	 *            the element tag
	 * @param attribute
	 *            the element attribute
	 * @param value
	 *            the value of this attribute
	 * @return the element
	 * 
	 */
	public static Optional<Element> findNodebyTagName(final Document doc,
			final String tag, final String attribute, final String value) {
		final NodeList nodeList = doc.getElementsByTagName(tag);
		for (var i = 0; i < nodeList.getLength(); i++) {
			final Element item = (Element) nodeList.item(i);
			if (item.getAttribute(attribute).equals(value)) {
				return Optional.ofNullable(item);
			}
		}
		return Optional.empty();
	}

}
