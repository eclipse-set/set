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
import static org.eclipse.set.utils.export.xsl.TransformStyle.setExcelCellBorderStyle;
import static org.eclipse.set.utils.export.xsl.TransformStyle.transformBorderStyle;
import static org.eclipse.set.utils.export.xsl.XMLDocumentExtensions.createXMLElementWithAttr;
import static org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.NUMBER_ROWS_SPANNED;
import static org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.XSL_USE_ATTRIBUTE_SETS;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLFoAttributeName.ATTR_NAME;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLFoAttributeName.ATTR_SELECT;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLStyleSets.*;
import static org.eclipse.set.utils.export.xsl.XSLConstant.XSLTag.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.eclipse.set.utils.export.xsl.XMLDocumentExtensions.XMLAttribute;
import org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.BorderDirection;
import org.eclipse.set.utils.export.xsl.XSLConstant.XSLStyleSets;
import org.eclipse.set.utils.export.xsl.XSLConstant.XSLTag;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Transform excel table body style
 * 
 * @author Truong
 */
public class TransformTableBody {
	final Document doc;
	final Sheet sheet;

	/**
	 * Constructor
	 * 
	 * @param doc
	 *            the XSL document
	 * @param sheet
	 *            the table excel sheet
	 */
	public TransformTableBody(final Document doc, final Sheet sheet) {
		this.doc = doc;
		this.sheet = sheet;
	}

	/**
	 * @return the XSL style template for the cells
	 */
	public Set<Element> getDefaultStyles() {
		return getDefaultStyles(groupCellByStyle(Collections.emptySet()));
	}

	/**
	 * @param styleGroup
	 *            the group of cells, which have same style template
	 * @return the XSL style template for the cells
	 */
	public Set<Element> getDefaultStyles(final Set<Set<Cell>> styleGroup) {
		return styleGroup.stream()
				.flatMap(
						cells -> Set
								.of(rowStyleTemplate(cells),
										lastRowStyleTemplate(cells))
								.stream())
				.collect(Collectors.toSet());
	}

	@SuppressWarnings("boxing")
	private Set<Set<Cell>> groupCellByStyle(final Set<Integer> pageBreakAts) {
		final Set<Set<Cell>> result = new LinkedHashSet<>();
		final int lastHeaderRowIndex = getHeaderLastRowIndex(sheet);
		final Set<Integer> parentGroupLastIndex = getColumnWithWideBorderRight();
		// Get next 2 row, because first row after table header row will
		// contains
		// border style of header row
		final Row firstDataRow = sheet.getRow(lastHeaderRowIndex + 2);
		if (firstDataRow == null) {
			throw new RuntimeException(
					"Missing first data row. Is the printing area configured correctly?"); //$NON-NLS-1$
		}

		for (int i = 0; i <= getHeaderLastColumnIndex(sheet); i++) {
			final Optional<Cell> cellAt = getCellAt(sheet,
					firstDataRow.getRowNum(), i);

			if (cellAt.isEmpty()) {
				continue;
			}

			if (parentGroupLastIndex.contains(i) || pageBreakAts.contains(i)) {
				setExcelCellBorderStyle(cellAt, BorderDirection.RIGHT,
						BorderStyle.MEDIUM);
				// Set border style for The break column and the after
			} else if (pageBreakAts.contains(i - 1)) {
				setExcelCellBorderStyle(cellAt, BorderDirection.LEFT,
						BorderStyle.MEDIUM);
			}

			if (!isDefaultStyle(cellAt.get().getCellStyle())) {
				Set<Cell> sameStyleGroup = result.stream()
						.filter(cells -> cells.stream()
								.filter(cell -> isEquals(
										cellAt.get().getCellStyle(),
										cell.getCellStyle()))
								.findFirst()
								.orElse(null) != null)
						.findFirst()
						.orElse(null);
				if (sameStyleGroup == null) {
					sameStyleGroup = new LinkedHashSet<>();
					result.add(sameStyleGroup);
				}
				sameStyleGroup.add(cellAt.get());
			}
		}

		return result;
	}

	@SuppressWarnings("boxing")
	private Set<Integer> getColumnWithWideBorderRight() {
		final Row headerRow = sheet.getRow(1);
		final Set<Integer> result = new HashSet<>();
		// Start at 1 to skip empty column 0
		for (var i = 1; i <= getHeaderLastColumnIndex(sheet); i++) {
			final Cell cell = headerRow.getCell(i);
			if (cell.getCellStyle().getBorderRight() == BorderStyle.MEDIUM) {
				result.add(i);
			}
			if (i != 1 && cell.getCellStyle()
					.getBorderLeft() == BorderStyle.MEDIUM) {
				result.add(i - 1);
			}
		}

		return result;
	}

	private static boolean isDefaultStyle(final CellStyle cellStyle) {
		final Map<BorderDirection, String> borderStyle = TransformStyle
				.transformBorderStyle(cellStyle);
		final boolean isDefaultBorder = borderStyle.values()
				.stream()
				.filter(style -> !style.isEmpty())
				.count() == 0;
		return isDefaultBorder
				&& cellStyle.getAlignment() == HorizontalAlignment.CENTER;
	}

	private static boolean isEquals(final CellStyle first,
			final CellStyle second) {
		if (first.getAlignment() != second.getAlignment()) {
			return false;
		}
		final Map<BorderDirection, String> firstBorderStyle = transformBorderStyle(
				first);
		final Map<BorderDirection, String> secondBorderStyle = transformBorderStyle(
				second);
		return firstBorderStyle.equals(secondBorderStyle);
	}

	/**
	 * @param pageBreakAt
	 *            the columns index, where page break;
	 * @return XSL style templates at page break columns
	 */
	@SuppressWarnings("boxing")
	public Set<Element> pageBreakColumnCellStyle(
			final Set<Integer> pageBreakAt) {
		final Set<Set<Cell>> groupStyles = groupCellByStyle(pageBreakAt);

		final Set<Cell> pageBreakColumns = groupStyles.stream()
				.flatMap(group -> group.stream().map(cell -> {
					if (pageBreakAt.contains(cell.getColumnIndex())) {
						return cell;
					}
					return null;
				}))
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());

		final Set<Element> pageBreakColumnsStyle = Stream
				.of(rowStyleTemplate(pageBreakColumns),
						lastRowStyleTemplate(pageBreakColumns))
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());

		pageBreakColumnsStyle.forEach(style -> {
			final Element numberCountColStyle = XMLDocumentExtensions
					.createXMLElementWithAttr(doc, FO_TABLE_CELL,
							new XMLAttribute(XSL_USE_ATTRIBUTE_SETS,
									XSLStyleSets.BODY_ROW_CELL_STYLE),
							new XMLAttribute(NUMBER_ROWS_SPANNED, String
									.format("{@%s}", NUMBER_ROWS_SPANNED))); //$NON-NLS-1$
			final Element block = doc.createElement(XSLTag.FO_BLOCK);
			final Element valueOf = createXMLElementWithAttr(doc, XSL_VALUE_OF,
					ATTR_SELECT, "../@group-number"); //$NON-NLS-1$
			block.appendChild(valueOf);
			numberCountColStyle.appendChild(block);

			final Element applyTemplates = createXMLElementWithAttr(doc,
					XSL_APPLY_TEMPLATE, ATTR_SELECT,
					"../*[@column-number = '1']"); //$NON-NLS-1$
			style.appendChild(numberCountColStyle);
			style.appendChild(applyTemplates);
		});

		final Set<Element> defeaultStyle = groupStyles.stream()
				.flatMap(group -> {
					group.removeIf(cell -> pageBreakAt
							.contains(cell.getColumnIndex()));
					return Stream.of(rowStyleTemplate(group),
							lastRowStyleTemplate(group));
				})
				.filter(Objects::nonNull)
				.collect(Collectors.toSet());
		pageBreakColumnsStyle.addAll(defeaultStyle);
		return pageBreakColumnsStyle;
	}

	private Element rowStyleTemplate(final Set<Cell> exclusionColumns) {
		if (exclusionColumns.isEmpty()) {
			return null;
		}

		final String expression = String.format(
				"Cell[contains(' %s ', concat(' ', @column-number, ' '))" //$NON-NLS-1$
						+ " and ../@group-number != count(/Table/Rows/Row)]", //$NON-NLS-1$
				String.join(" ", exclusionColumns.stream() //$NON-NLS-1$
						.map(col -> String.valueOf(col.getColumnIndex()))
						.toList()));
		final Element template = createXMLElementWithAttr(doc, XSL_TEMPLATE,
				XSLConstant.XSLFoAttributeName.ATTR_MATCH, expression);
		final Element tableCell = createCellStyleElement(DEFAULT_CELL_STYLE,
				(Cell) exclusionColumns.toArray()[0]);
		template.appendChild(tableCell);
		return template;
	}

	private Element lastRowStyleTemplate(final Set<Cell> exclusionColumns) {
		if (exclusionColumns.isEmpty()) {
			return null;
		}
		final String expression = String.format(
				"Cell[contains(' %s ', concat(' ', @column-number, ' '))" //$NON-NLS-1$
						+ " and ../@group-number = count(/Table/Rows/Row)]", //$NON-NLS-1$
				String.join(" ", exclusionColumns.stream() //$NON-NLS-1$
						.map(col -> String.valueOf(col.getColumnIndex()))
						.toList()));
		final Element template = createXMLElementWithAttr(doc, XSL_TEMPLATE,
				XSLConstant.XSLFoAttributeName.ATTR_MATCH, expression);

		final Element tableCell = createCellStyleElement(LAST_ROW_CELL_STYLE,
				(Cell) exclusionColumns.toArray()[0]);
		template.appendChild(tableCell);
		return template;
	}

	private Element createCellStyleElement(final String styleSets,
			final Cell cell) {
		final Element tableCell = createXMLElementWithAttr(doc, FO_TABLE_CELL,
				new XMLAttribute(XSL_USE_ATTRIBUTE_SETS, styleSets),
				new XMLAttribute(NUMBER_ROWS_SPANNED,
						String.format("{@%s}", NUMBER_ROWS_SPANNED))); //$NON-NLS-1$
		TransformStyle.transformCellStyle(tableCell, Optional.of(cell));
		tableCell.appendChild(createXMLElementWithAttr(doc, XSL_CALL_TEMPLATE,
				ATTR_NAME, PLAN_COMPARE_CONTENT_TEMPLATE));
		tableCell.appendChild(doc.createElement(XSL_APPLY_TEMPLATE));
		return tableCell;
	}
}
