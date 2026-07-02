/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.xlsx;

import static org.eclipse.set.utils.excel.ExcelWorkbookExtension.getCellAt;
import static org.eclipse.set.utils.excel.ExcelWorkbookExtension.getCellStringValue;
import static org.eclipse.set.utils.excel.ExcelWorkbookExtension.getHeaderLastColumnIndex;
import static org.eclipse.set.utils.excel.ExcelWorkbookExtension.getHeaderLastRowIndex;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.core.runtime.Assert;
import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.ToolboxPaths;
import org.eclipse.set.basis.ToolboxPaths.ExportPathExtension;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.exceptions.FileExportException;
import org.eclipse.set.feature.export.pdf.TableToTableDocument;
import org.eclipse.set.model.tablemodel.CompareFootnoteContainer;
import org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer;
import org.eclipse.set.model.tablemodel.Footnote;
import org.eclipse.set.model.tablemodel.FootnoteContainer;
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions.FootnoteInfo;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.services.export.TableExport;
import org.eclipse.set.utils.table.TableSpanUtils;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Streams;

/**
 * {@link TableExport} implementation for Excel with template files.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(immediate = true)
public class ExcelExportBuilder implements TableExport {

	private static final Logger logger = LoggerFactory
			.getLogger(ExcelExportBuilder.class);

	private static final String TEMPLATE_DIR = "./data/export/excel"; //$NON-NLS-1$
	private static final String FOOTNOTE_SHEET_NAME = "Bemerkungen"; //$NON-NLS-1$

	private static int getFirstRowForContent(final Sheet sheet) {
		return getHeaderLastRowIndex(sheet) + 1;
	}

	private static Table getTableToBeExported(
			final Map<TableType, Table> tables) {
		final Table invTable = tables.get(TableType.FINAL);
		if (invTable != null) {
			return invTable;
		}
		// if we do not have a final table we export the table of the single
		// container of a state
		return tables.get(TableType.SINGLE);
	}

	static String[] getColumnHeaders(final Sheet sheet) {
		final Row row = sheet.getRow(0);
		final int maxColIx = getHeaderLastColumnIndex(sheet);
		final List<String> headers = new ArrayList<>();
		for (int colIx = 1; colIx <= maxColIx; colIx++) {
			final Optional<Cell> cell = getCellAt(sheet, row.getRowNum(),
					colIx);
			final Optional<String> cellStringValue = getCellStringValue(cell);
			if (cellStringValue.isPresent()) {
				headers.add(cellStringValue.get());
			}
		}

		return headers.toArray(new String[0]);
	}

	@Override
	public void export(final Map<TableType, Table> tables,
			final ExportType exportType, final Titlebox titlebox,
			final FreeFieldInfo freeFieldInfo, final String shortcut,
			final String outputDir, final ToolboxPaths toolboxPaths,
			final TableType tableType,
			final OverwriteHandling overwriteHandling)
			throws FileExportException {
		final Table table = getTableToBeExported(tables);
		final boolean isInlineFootnote = TableExtensions
				.isInlineFootnote(table);
		// IMPROVE: this is only a temporary situation for the table
		// Sskp_dm
		final String tableShortcut = shortcut.equals("sskp_dm") ? "sskp" //$NON-NLS-1$//$NON-NLS-2$
				: shortcut;
		final Path templatePath = Paths.get(TEMPLATE_DIR,
				tableShortcut + "_vorlage.xlsx"); //$NON-NLS-1$
		final Path outputPath = toolboxPaths.getTableExportPath(shortcut,
				Paths.get(outputDir), exportType,
				ExportPathExtension.TABLE_XLSX_EXPORT_EXTENSION);

		try (final FileInputStream inputStream = new FileInputStream(
				templatePath.toFile());
				final Workbook workbook = new XSSFWorkbook(inputStream)) {

			// check overwrite
			if (!overwriteHandling.test(outputPath)) {
				return;
			}

			// es gibt nur einen
			final Sheet sheet = workbook.getSheetAt(0);
			workbook.setSheetName(0, shortcut.substring(0, 1).toUpperCase()
					+ shortcut.substring(1));
			// dummy-Header erzeugen für die Transformation
			final String[] headers = getColumnHeaders(sheet);
			final int columnCount = headers.length;
			// Zeilenindex der ersten leeren Zeile bestimmen
			final int rowIndex = getFirstRowForContent(sheet);

			logger.info("exporting table = {}", templatePath); //$NON-NLS-1$
			final List<TableRow> rows = TableExtensions.getTableRows(table);

			// Fill sheet
			fillSheet(workbook, sheet, rows, rowIndex, columnCount,
					isInlineFootnote);

			if (!isInlineFootnote) {
				final Sheet footnoteSheet = workbook
						.createSheet(FOOTNOTE_SHEET_NAME);
				fillFootnoteSheet(footnoteSheet, table);
			}

			// Create spans
			addTableSpans(sheet, rows, rowIndex, columnCount);

			// Improve: Kompletter Wechsel auf Apache FileUtils,
			// vllt bei Erweiterung des Feature-Umfangs
			final Path outputDirPath = outputPath.getParent();

			// we used outputDir above to compile the path, so we don't expect
			// outputDirPath to be null
			Assert.isNotNull(outputDirPath);

			FileUtils.forceMkdir(outputDirPath.toFile());

			// ...und im Zielverzeichnis schreiben
			try (final FileOutputStream fileOut = new FileOutputStream(
					outputPath.toString())) {
				workbook.write(fileOut);
			}
		} catch (final IOException e) {
			throw new FileExportException(outputPath, e);
		}
	}

	@SuppressWarnings("boxing")
	private static void fillFootnoteSheet(final Sheet footnoteSheet,
			final Table table) {
		final List<FootnoteInfo> allFootnotes = new ArrayList<>(
				Streams.stream(TableExtensions.getAllFootnotes(table))
						.toList());
		if (allFootnotes.isEmpty()) {
			return;
		}
		footnoteSheet.autoSizeColumn(1);
		allFootnotes.sort(
				(first, second) -> Integer.compare(first.index, second.index));
		for (int i = 0; i < allFootnotes.size(); i++) {
			final Row row = footnoteSheet.createRow(i + 1);
			final Cell fnIndexCell = row.createCell(0);
			final FootnoteInfo footnoteInfo = allFootnotes.get(i);
			// Currently export only the FINAL-State table to excel, therefore
			// no need to handle cell style
			fnIndexCell.setCellValue(String.format("*%d", footnoteInfo.index) //$NON-NLS-1$
			);
			final Cell fnContentCell = row.createCell(1);
			fnContentCell.setCellValue(footnoteInfo.toText());

		}
	}

	private static void fillSheet(final Workbook workbook, final Sheet sheet,
			final List<TableRow> rows, final int rowIndex,
			final int columnCount, final boolean inlineFootnote) {
		if (rows.isEmpty()) {
			return;
		}
		final Table table = TableRowExtensions.getTable(rows.getFirst());
		final List<FootnoteInfo> allFootnotes = Streams
				.stream(TableExtensions.getAllFootnotes(table))
				.toList();
		int contentRowIndex = rowIndex;

		for (final TableRow row : rows) {
			final Row sheetRow = contentRowIndex == rowIndex
					? sheet.getRow(contentRowIndex)
					: createNewRow(sheet, contentRowIndex, columnCount);
			final FootnoteContainer footnotes = row.getFootnotes();
			for (int i = 0; i < columnCount; i++) {
				final String content = TableRowExtensions
						.getPlainStringValue(row, i);
				Cell cell = sheetRow.getCell(i + 1);

				if (cell == null) {
					cell = sheetRow.createCell(i + 1);
					cell.getCellStyle()
							.setFont(workbook.getFontAt(sheet.getRow(0)
									.getCell(1)
									.getCellStyle()
									.getFontIndex()));
				}
				if (TableToTableDocument
						.isRemarkColumn(row.getCells().get(i))) {
					fillFootnoteCell(cell, content, allFootnotes, footnotes,
							inlineFootnote);
					continue;
				}
				cell.setCellValue(content);
			}
			// Auto adjust row height
			sheetRow.setHeight((short) -1);
			contentRowIndex++;
		}
	}

	private static List<Footnote> getFootnotes(
			final FootnoteContainer fnContainer) {
		if (fnContainer == null) {
			return Collections.emptyList();
		}
		return switch (fnContainer) {
			case final SimpleFootnoteContainer simpleContainer -> simpleContainer
					.getFootnotes();
			case final CompareFootnoteContainer compareContainer -> compareContainer
					.getUnchangedFootnotes()
					.getFootnotes();
			case final CompareTableFootnoteContainer compareContainer -> getFootnotes(
					compareContainer.getMainPlanFootnoteContainer());
			default -> Collections.emptyList();
		};
	}

	private static void fillFootnoteCell(final Cell cell,
			final String cellContent, final List<FootnoteInfo> allFootnotes,
			final FootnoteContainer fnContainer, final boolean inlineFootnote) {
		final List<Footnote> footnotes = getFootnotes(fnContainer);
		final List<FootnoteInfo> fnInfo = TableToTableDocument
				.processFootnotes(footnotes.stream()
						.map(fn -> TableExtensions.getFootnoteInfo(allFootnotes,
								fn))
						.toList());
		final StringBuilder builder = new StringBuilder();
		if (!cellContent.isEmpty() && !cellContent.isBlank()) {
			builder.append(cellContent);
			builder.append(TableToTableDocument.FOOTNOTE_INLINE_TEXT_SEPARATOR);
		}
		final String footnoteValue = fnInfo.stream()
				.map(inlineFootnote ? FootnoteInfo::toText
						: FootnoteInfo::toShorthand)
				.collect(Collectors.joining(inlineFootnote
						? TableToTableDocument.FOOTNOTE_INLINE_TEXT_SEPARATOR
						: TableToTableDocument.FOOTNOTE_MARK_SEPRATOR));
		builder.append(footnoteValue);
		cell.setCellValue(builder.toString());
	}

	@SuppressWarnings("resource")
	private static Row createNewRow(final Sheet sheet, final int rowIndex,
			final int maxColIndex) {
		final Row cloneRow = sheet.createRow(rowIndex);
		final Workbook workbook = sheet.getWorkbook();
		for (int i = 0; i <= maxColIndex; i++) {
			final Optional<Cell> cellAt = getCellAt(sheet, rowIndex - 1, i);
			final Cell newCell = cloneRow.createCell(i);
			if (cellAt.isPresent()) {
				final CellStyle cloneStyle = workbook.createCellStyle();
				cloneStyle.cloneStyleFrom(cellAt.get().getCellStyle());
				cloneStyle.setBorderTop(BorderStyle.NONE);
				cloneStyle.setBorderBottom(BorderStyle.NONE);
				newCell.setCellStyle(cloneStyle);
			}
		}
		return cloneRow;
	}

	private static void addTableSpans(final Sheet sheet,
			final List<TableRow> rows, final int rowIndex,
			final int columnCount) {
		int sheetRowIndex = rowIndex;
		final TableSpanUtils spanUtils = new TableSpanUtils(rows);
		for (int row = 0; row < rows.size(); row++) {
			for (int column = 0; column < columnCount; column++) {
				if (!spanUtils.isMergeAllowed(column, row)) {
					continue;
				}
				final int sheetColumn = column + 1;

				final int spanUp = spanUtils.getRowSpanUp(column, row);
				final int spanDown = spanUtils.getRowSpanDown(column, row);

				// If spanUp > 0, we have already merged this span
				// in a previous iteration
				if (spanUp > 0) {
					continue;
				}

				// Nothing to merge?
				if (spanDown == 0) {
					continue;
				}

				sheet.addMergedRegion(new CellRangeAddress(sheetRowIndex,
						sheetRowIndex + spanDown, sheetColumn, sheetColumn));
			}

			sheetRowIndex++;
		}

	}

	@Override
	public void exportTitleboxImage(final Titlebox titlebox, final Path path,
			final OverwriteHandling overwriteHandling) {
		// do nothing
	}

	@Override
	public void exportTitleboxPdf(final Titlebox titlebox, final Path pdfPath,
			final OverwriteHandling overwriteHandling) throws Exception {
		// do nothing
	}

	@Override
	public ExportFormat getExportFormat() {
		return ExportFormat.EXCEL;
	}

	@Override
	public String getTableShortcut() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void exportSiteplanPdf(final List<BufferedImage> imagesData,
			final Titlebox titleBox, final FreeFieldInfo freeFieldInfo,
			final double ppm, final String outputDir,
			final ToolboxPaths toolboxPaths, final TableType tableType,
			final OverwriteHandling overwriteHandling) {
		// do nothing

	}
}
