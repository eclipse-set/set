/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.xls;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.eclipse.core.runtime.Assert;
import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.ToolboxPaths;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.exceptions.FileExportException;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.services.export.TableExport;
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
		int index = 0;
		while (sheet.getRow(index) != null) {
			final Row row = sheet.getRow(index);
			// The first content row can be found by finding the first row
			// without borders
			// to the left, right and bottom

			// Use the second cell in the row, which is the first content
			// column for PT1 tables,
			// since column 0 is always blank
			final Cell cell = row.getCell(1);
			// If the cell is the (default) empty cell, it has no borders
			if (cell == null) {
				// Empty cells have no border
				return index;
			}

			// Otherwise check the borders
			final BorderStyle borderRight = cell.getCellStyle()
					.getBorderRight();
			final BorderStyle borderBottom = cell.getCellStyle()
					.getBorderBottom();
			final BorderStyle borderLeft = cell.getCellStyle().getBorderLeft();
			if (borderRight == BorderStyle.NONE
					&& borderBottom == BorderStyle.NONE
					&& borderLeft == BorderStyle.NONE) {
				return index;
			}
			index++;
		}
		throw new IllegalArgumentException(
				"No content row found for the given sheet"); //$NON-NLS-1$
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
		final short maxColIx = row.getLastCellNum();

		final List<String> headers = new ArrayList<>();

		for (int colIx = 1; colIx < maxColIx; colIx++) {
			final Cell cell = row.getCell(colIx);
			if (cell != null) {
				final String cellValue = cell.getStringCellValue();

				if (cellValue != null && cellValue.length() > 0) {
					headers.add(cell.getStringCellValue());
				}
			}
		}

		return headers.toArray(new String[0]);
	}

	@Override
	public void export(final Map<TableType, Table> tables,
			final ExportType exportType, final Titlebox titlebox,
			final FreeFieldInfo freeFieldInfo, final String shortcut,
			final String outputDir, final ToolboxPaths toolboxPaths,
			final OverwriteHandling overwriteHandling)
			throws FileExportException {
		final Table table = getTableToBeExported(tables);

		final Path templatePath = Paths.get(TEMPLATE_DIR,
				shortcut + "_vorlage.xlt"); //$NON-NLS-1$
		final Path outputPath = toolboxPaths.getTableXlsExport(shortcut,
				Paths.get(outputDir), exportType);

		try (final FileInputStream inputStream = new FileInputStream(
				templatePath.toFile());
				final Workbook workbook = new HSSFWorkbook(inputStream)) {

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

			// excel-Tabelle erstellen:
			int sheetRowIndex = rowIndex;
			for (final TableRow row : rows) {
				for (int i = 0; i < columnCount; i++) {
					final String content = TableRowExtensions
							.getPlainStringValue(row, i);
					Row sheetRow = sheet.getRow(sheetRowIndex);
					if (sheetRow == null) {
						// wenn nicht vorhanden, dann neu machen
						sheetRow = sheet.createRow(sheetRowIndex);
					}
					Cell cell = sheetRow.getCell(i + 1);
					if (cell == null) {
						cell = sheetRow.createCell(i + 1);
					}
					cell.setCellValue(content);
				}
				sheetRowIndex++;
			}

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
}
