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

import static org.eclipse.set.utils.excel.ExcelWorkbookExtension.*;
import static org.eclipse.set.utils.export.xsl.TransformStyle.setBorderStyle;
import static org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.NUMBER_COLUMNS_SPANNED;
import static org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.START_INDENT;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLFoAttributeName.ATTR_ID;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLStyleSets.WIDE_BORDER_STYLE;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLTag.FO_BLOCK;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLTag.XSL_STYLESHEET;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.lastOrNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.eclipse.set.utils.excel.ExcelWorkbookExtension;
import org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.BorderDirection;
import org.eclipse.set.utils.math.BigDecimalExtensions;
import org.eclipse.xtext.xbase.lib.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 * 
 */
public class MultiPageTableHeader extends AbstractTransformTableHeader {
	private static final String MULTIPAGE_LAYOUT_TEMPLATE_PATH = "data/export/pdf/multipage_layout.xsl"; //$NON-NLS-1$
	private static final String XSL_MAINPAGE_TEMPLATE_NAME = "MainPage"; //$NON-NLS-1$
	Set<Integer> pageBreakAts = new LinkedHashSet<>();

	protected MultiPageTableHeader(final XSSFSheet sheet,
			final float maxPaperWidth) {
		super(sheet, maxPaperWidth);
		for (final int columnIndex : sheet.getColumnBreaks()) {
			pageBreakAts.add(Integer.valueOf(columnIndex));
		}
	}

	@Override
	protected void addTableToTemplate(final Element tableTemplate,
			final Element table) {
		final Element block = doc.createElement(FO_BLOCK);
		block.appendChild(table);
		tableTemplate.appendChild(block);
	}

	@Override
	protected Document getFopPageTemplate() throws ParserConfigurationException,
			SAXException, IOException, TransformerException {
		final Document tableTemplate = parseTemplate(XSL_TEMPLATE_PATH);
		final Node rootNode = tableTemplate.getElementsByTagName(XSL_STYLESHEET)
				.item(0);
		final Document multipageTemplate = parseTemplate(
				MULTIPAGE_LAYOUT_TEMPLATE_PATH);
		final Optional<Element> newPageBlock = findNodebyTagName(
				multipageTemplate, FO_BLOCK, ATTR_ID, START_INDENT);
		if (newPageBlock.isPresent()) {
			newPageBlock.get()
					.setAttribute(START_INDENT,
							BigDecimalExtensions.toTableDecimal(
									BigDecimal.valueOf(maxPaperWidth).negate(),
									2) + "cm"); //$NON-NLS-1$
		}

		final Node clone = tableTemplate
				.importNode(multipageTemplate.getFirstChild(), true);
		final Optional<Element> singlePageLayout = findNodebyTagName(
				tableTemplate, XSLConstant.XSLTag.XSL_TEMPLATE,
				XSLConstant.XSLFoAttributeName.ATTR_NAME,
				XSL_MAINPAGE_TEMPLATE_NAME);
		if (singlePageLayout.isPresent()) {
			rootNode.replaceChild(clone, singlePageLayout.get());
		} else {
			rootNode.appendChild(clone);
		}
		return tableTemplate;
	}

	@Override
	protected Set<Element> getTableStyle() {
		return new TransformTableBody(doc, sheet)
				.pageBreakColumnCellStyle(pageBreakAts);
	}

	@SuppressWarnings("boxing")
	@Override
	protected Pair<Integer, Float> transformColumn(
			final LinkedHashSet<Element> cols, final float sumWidth,
			final int colsIndex, final int columNumber) {
		float columnWidth = ExcelWorkbookExtension.getColumnWidthInCm(sheet,
				colsIndex);
		if (pageBreakAts.contains(colsIndex) || isBigerThanPaperWidth(
				sumWidth + columnWidth, colsIndex + 1)) {
			// set reamining width
			pageBreakAts.add(colsIndex);
			columnWidth = maxPaperWidth - sumWidth;

			// Add last column in page
			cols.add(createTableColumn(columNumber, columnWidth));
			// Add repeating column to new page
			final Pair<Integer, Float> repeatColumns = transformBreakColumns(
					cols, columNumber);
			return Pair.of(repeatColumns.getKey(), repeatColumns.getValue());
		}
		cols.add(createTableColumn(columNumber, columnWidth));
		return Pair.of(columNumber, sumWidth + columnWidth);
	}

	@SuppressWarnings("boxing")
	private Pair<Integer, Float> transformBreakColumns(
			final LinkedHashSet<Element> columns, final int columnNumber) {
		final LinkedHashSet<Integer> repeatingColumns = getRepeatingColumns(
				sheet);
		if (repeatingColumns.isEmpty()) {
			// When repeating columns is empty,
			// then add only numerical order column
			final float columnWidth = getColumnWidthInCm(sheet, 0);
			columns.add(createTableColumn(columnNumber, columnWidth));
			return Pair.of(columnNumber + 1, columnWidth);
		}

		float repeatColumnsWidth = 0f;
		for (int index = 0; index < repeatingColumns.size(); index++) {
			final int colIndex = columnNumber + index + 1;
			final float columnWidth = getColumnWidthInCm(sheet,
					new ArrayList<>(repeatingColumns).get(index));
			repeatColumnsWidth += columnWidth;
			columns.add(createTableColumn(colIndex, columnWidth));
		}
		return Pair.of(columnNumber + repeatingColumns.size(),
				repeatColumnsWidth);
	}

	@Override
	protected int getCellSpanColumn(final Optional<Cell> excelCell) {
		if (excelCell.isEmpty()) {
			return 0;
		}

		final Optional<CellRangeAddress> cellSpanRange = getColumnSpanRangeAt(
				excelCell.get());
		if (cellSpanRange.isPresent()) {
			final int spanCount = cellSpanRange.get().getNumberOfCells();
			// When page break at a span column, then spilt this span
			final Integer breakInSpan = pageBreakAts.stream()
					.filter(colIndex -> colIndex
							.intValue() < cellSpanRange.get().getLastColumn()
							&& colIndex.intValue() > cellSpanRange.get()
									.getFirstColumn())
					.findFirst()
					.orElse(null);

			if (breakInSpan != null) {
				final int colIndex = excelCell.get().getColumnIndex();
				if (getCellStringValue(excelCell).isEmpty()) {
					final Optional<Cell> cellAt = getCellAt(
							excelCell.get().getSheet(),
							cellSpanRange.get().getFirstRow(),
							cellSpanRange.get().getFirstColumn());
					excelCell.get()
							.setCellValue(
									getCellStringValue(cellAt).orElse("")); //$NON-NLS-1$
				}
				// add 1 for counting the break column itself
				return colIndex <= breakInSpan.intValue()
						? breakInSpan.intValue()
								- cellSpanRange.get().getFirstColumn() + 1
						: cellSpanRange.get().getLastColumn()
								- breakInSpan.intValue();
			}
			return spanCount;
		}
		return 0;
	}

	@SuppressWarnings("boxing")
	@Override
	protected void addCell(final Set<Element> cells, final int rowIndex,
			final Element cell, final int cellStartCol) {
		cells.add(cell);
		if (!pageBreakAts.contains(cellStartCol)) {
			return;
		}
		// Last column of page should have wide right border
		setBorderStyle(lastOrNull(cells), BorderDirection.RIGHT,
				WIDE_BORDER_STYLE);
		final LinkedHashSet<Integer> repeatingColumns = getRepeatingColumns(
				sheet);
		if (repeatingColumns.isEmpty()) {
			cells.add(transformFirstColumnCell(sheet.getRow(rowIndex)));
			return;
		}

		repeatingColumns.forEach(colIndex -> {
			if (colIndex.intValue() == 0) {
				cells.add(transformFirstColumnCell(sheet.getRow(rowIndex)));
			} else if (getRowSpanRangeAt(sheet, rowIndex, colIndex.intValue())
					.isEmpty()
					&& (getCellAt(sheet, rowIndex, rowIndex).isPresent()
							|| rowIndex == getHeaderLastRowIndex(sheet))) {
				final Element cloneNode = (Element) cells
						.toArray(new Element[0])[colIndex.intValue()]
								.cloneNode(true);
				final String columnsSpanned = cloneNode
						.getAttribute(NUMBER_COLUMNS_SPANNED);

				// Last repeated column should have wide right border
				if (colIndex.intValue() == repeatingColumns.size() - 1) {
					setBorderStyle(cloneNode, BorderDirection.RIGHT,
							WIDE_BORDER_STYLE);
				}
				if (columnsSpanned.isEmpty() || columnsSpanned.equals("1")) { //$NON-NLS-1$
					cells.add(cloneNode);
				} else {
					try {
						final Integer spanCount = Integer
								.parseInt(columnsSpanned);
						final long repeatingSpanCount = repeatingColumns
								.stream()
								.filter(col -> colIndex <= col
										&& colIndex + spanCount >= col)
								.count();
						cloneNode.setAttribute(NUMBER_COLUMNS_SPANNED,
								String.valueOf(repeatingSpanCount));
						cells.add(cloneNode);
					} catch (final NumberFormatException e) {
						throw new NumberFormatException();
					}
				}
			}
		});

	}

	private boolean isBigerThanPaperWidth(final float currentSumWidth,
			final int nextColumnIndex) {
		final CellRangeAddress nextColumnGroup = Arrays
				.stream(getParentGroupSpan(sheet))
				.filter(group -> group.getFirstColumn() == nextColumnIndex)
				.findFirst()
				.orElse(null);
		if (nextColumnGroup != null) {
			float groupSumWidth = 0f;
			for (var i = nextColumnIndex; i <= nextColumnGroup
					.getLastColumn(); i++) {
				groupSumWidth += getColumnWidthInCm(sheet, i);
			}
			return currentSumWidth + groupSumWidth > maxPaperWidth;
		}
		return currentSumWidth
				+ getColumnWidthInCm(sheet, nextColumnIndex) > maxPaperWidth;
	}
}
