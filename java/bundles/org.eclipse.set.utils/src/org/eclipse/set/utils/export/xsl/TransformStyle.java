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

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute;
import org.eclipse.set.utils.export.xsl.XSLConstant.TableAttribute.BorderDirection;
import org.eclipse.set.utils.export.xsl.XSLConstant.XSLStyleSets;
import org.w3c.dom.Element;

/**
 * Transform excel cell style to xsl style
 * 
 * @author Truong
 */
public class TransformStyle {

	private TransformStyle() {

	}

	/**
	 * Transform style of a excel cell to xml attribute
	 * 
	 * @param xslCell
	 *            xsl cell element
	 * @param excelCell
	 *            excel cell
	 */
	public static void transformCellStyle(final Element xslCell,
			final Optional<Cell> excelCell) {
		if (excelCell.isEmpty()) {
			return;
		}

		final CellStyle excelStyle = excelCell.get().getCellStyle();
		final Map<BorderDirection, String> transformBorderStyle = transformBorderStyle(
				excelStyle);
		setBorderStyle(xslCell, transformBorderStyle);
		setTextAlgin(xslCell, excelStyle.getAlignment());
		setTextFont(xslCell, excelCell.get());
	}

	private static void setBorderStyle(final Element cell,
			final Map<BorderDirection, String> borderStyles) {
		borderStyles.forEach((borderDirection, style) -> {
			if (style.isBlank() || style.isEmpty()) {
				return;
			}
			cell.setAttribute(borderDirection.getDirectionString(),
					String.format("{$%s}", style)); //$NON-NLS-1$
		});
	}

	/**
	 * Get excel cell border style to set attribute for XSL element
	 * 
	 * @param cell
	 *            the excel cell
	 * @param xslCell
	 *            the XSL element
	 * @param direction
	 *            the border to set style
	 */
	public static void setBorderStyle(final Optional<Cell> cell,
			final Element xslCell, final BorderDirection direction) {
		if (cell.isEmpty()) {
			return;
		}
		final String style = transformBorderStyle(cell.get().getCellStyle())
				.get(direction);
		setBorderStyle(xslCell, direction, style);
	}

	/**
	 * Set border style attribute for xsl element
	 * 
	 * @param xslCell
	 *            the xsl element
	 * @param direction
	 *            the border to set style
	 * @param borderStyle
	 *            the style
	 */
	public static void setBorderStyle(final Element xslCell,
			final BorderDirection direction, final String borderStyle) {
		if (borderStyle.isEmpty() || borderStyle.isBlank()) {
			xslCell.removeAttribute(direction.getDirectionString());
		} else {
			xslCell.setAttribute(direction.getDirectionString(),
					String.format("{$%s}", borderStyle)); //$NON-NLS-1$
		}
	}

	/**
	 * Transform borderstyle of excel cell to xsl element
	 * 
	 * @param style
	 *            excel cell style
	 * @return the border style of cell
	 */
	public static Map<BorderDirection, String> transformBorderStyle(
			final CellStyle style) {
		return Map.of(BorderDirection.TOP,
				transformBorderStyle(style.getBorderTop()),
				BorderDirection.BOTTOM,
				transformBorderStyle(style.getBorderBottom()),
				BorderDirection.LEFT,
				transformBorderStyle(style.getBorderLeft()),
				BorderDirection.RIGHT,
				transformBorderStyle(style.getBorderRight()));
	}

	private static String transformBorderStyle(final BorderStyle borderStyle) {
		return switch (borderStyle) {
			case THIN -> XSLStyleSets.SMALL_BORDER_STYLE;
			case MEDIUM -> XSLStyleSets.WIDE_BORDER_STYLE;
			// Do not render other styles
			default -> ""; //$NON-NLS-1$
		};
	}

	private static void setTextAlgin(final Element xslCell,
			final HorizontalAlignment horizonAlign) {
		final String horizon = switch (horizonAlign) {
			case LEFT -> HorizontalAlignment.LEFT.name();
			case RIGHT -> HorizontalAlignment.RIGHT.name();
			default -> HorizontalAlignment.CENTER.name();
		};
		xslCell.setAttribute(TableAttribute.TEXT_ALIGN, horizon.toLowerCase());
	}

	private static void setTextFont(final Element xslCell,
			final Cell excelCell) {
		try (final Workbook workBook = excelCell.getRow()
				.getSheet()
				.getWorkbook()) {
			final int fontIndex = excelCell.getCellStyle().getFontIndex();
			final Font textFont = workBook.getFontAt(fontIndex);
			if (textFont.getBold()) {
				xslCell.setAttribute(TableAttribute.FONT_WEIGHT, "blod"); //$NON-NLS-1$
			}
			if (textFont.getItalic()) {
				xslCell.setAttribute(TableAttribute.FONT_STYLE, "italic"); //$NON-NLS-1$
			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Set border style for excel cell
	 * 
	 * @param cell
	 *            the excel cell
	 * @param borderStyles
	 *            the border styles
	 */
	public static void setExcelCellBorderStyle(final Optional<Cell> cell,
			final Map<BorderDirection, BorderStyle> borderStyles) {
		borderStyles.forEach((direction, style) -> setExcelCellBorderStyle(cell,
				direction, style));
	}

	/**
	 * Set border style for excel cell
	 * 
	 * @param cell
	 *            the excel cell
	 * @param direction
	 *            the border to set style
	 * @param style
	 *            the style
	 */
	public static void setExcelCellBorderStyle(final Optional<Cell> cell,
			final BorderDirection direction, final BorderStyle style) {
		if (cell.isEmpty()) {
			return;
		}

		try (final Workbook workbook = cell.get().getSheet().getWorkbook()) {
			final CellStyle newStyle = workbook.createCellStyle();
			newStyle.cloneStyleFrom(cell.get().getCellStyle());
			switch (direction) {
				case LEFT:
					newStyle.setBorderLeft(style);
					break;
				case RIGHT:
					newStyle.setBorderRight(style);
					break;
				case TOP:
					newStyle.setBorderTop(style);
					break;
				case BOTTOM:
					newStyle.setBorderBottom(style);
					break;
				default:
					break;
			}

			cell.get().setCellStyle(newStyle);

		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}
}
