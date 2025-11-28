/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.xlsx;

import static org.eclipse.set.utils.excel.ExcelWorkbookExtension.*;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.services.export.TableExport;
import org.eclipse.set.utils.table.TableSpanUtils;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
			// dummy-Header erzeugen für die Transformation
			final String[] headers = getColumnHeaders(sheet);
			final int columnCount = headers.length;
			// Zeilenindex der ersten leeren Zeile bestimmen
			final int rowIndex = getFirstRowForContent(sheet);

			logger.info("exporting table = {}", templatePath); //$NON-NLS-1$
			final List<TableRow> rows = TableExtensions.getTableRows(table);

			// Fill sheet
			fillSheet(sheet, rows, rowIndex, columnCount);

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

	private static void fillSheet(final Sheet sheet, final List<TableRow> rows,
			final int rowIndex, final int columnCount) {
		int contentRowIndex = rowIndex;
		for (final TableRow row : rows) {
			final Row sheetRow = contentRowIndex == rowIndex
					? sheet.getRow(contentRowIndex)
					: createNewRow(sheet, contentRowIndex, columnCount);
			for (int i = 0; i < columnCount; i++) {
				final String content = TableRowExtensions
						.getPlainStringValue(row, i);
				Cell cell = sheetRow.getCell(i + 1);

				if (cell == null) {
					cell = sheetRow.createCell(i + 1);
				}

				cell.setCellValue(content);
			}
			// Auto adjust row height
			sheetRow.setHeight((short) -1);
			contentRowIndex++;
		}
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

				sheet.addMergedRegion(new CellRangeAddress(sheetRowIndex + row,
						sheetRowIndex + row + spanDown, column, column));
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
