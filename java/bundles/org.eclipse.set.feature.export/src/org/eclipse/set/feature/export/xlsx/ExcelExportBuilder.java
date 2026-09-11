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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.eclipse.core.runtime.Assert;
import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.ToolboxPaths;
import org.eclipse.set.basis.ToolboxPaths.ExportPathExtension;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.exceptions.FileExportException;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.export.pdf.TableToTableDocument;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.CompareFootnoteContainer;
import org.eclipse.set.model.tablemodel.CompareStateCellContent;
import org.eclipse.set.model.tablemodel.CompareTableCellContent;
import org.eclipse.set.model.tablemodel.CompareTableFootnoteContainer;
import org.eclipse.set.model.tablemodel.FootnoteContainer;
import org.eclipse.set.model.tablemodel.PlanCompareRow;
import org.eclipse.set.model.tablemodel.PlanCompareRowType;
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableCellExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions.FootnoteInfo;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.services.export.TableExport;
import org.eclipse.set.utils.table.TableSpanUtils;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTBorder;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTBorderPr;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTColor;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTXf;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.STBorderStyle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
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
	private static List<XSSFCellStyle> defaultCellStyleByColumn;
	private static XSSFFont cellNewValueFont;
	private static XSSFFont cellOldValueFont;

	@Reference
	EnumTranslationService enumTranslationService;

	private static int getFirstRowForContent(final Sheet sheet) {
		return getHeaderLastRowIndex(sheet) + 1;
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

		// IMPROVE: this is only a temporary situation for the table
		// Sskp_dm
		final String tableShortcut = shortcut.equals("sskp_dm") ? "sskp" //$NON-NLS-1$//$NON-NLS-2$
				: shortcut;
		final Path outputPath = toolboxPaths.getTableExportPath(shortcut,
				Paths.get(outputDir), exportType,
				ExportPathExtension.TABLE_XLSX_EXPORT_EXTENSION);
		export(tables, exportType, titlebox, freeFieldInfo, tableShortcut,
				tableType, outputPath, overwriteHandling);

	}

	@Override
	public void export(final Map<TableType, Table> tables,
			final ExportType exportType, final Titlebox titleBox,
			final FreeFieldInfo freeFieldInfo, final String shortcut,
			final TableType tableType, final Path outputPath,
			final OverwriteHandling overwriteHandling)
			throws FileExportException {
		final Table table = tables.get(tableType);
		final boolean isInlineFootnote = TableExtensions
				.isInlineFootnote(table);
		// IMPROVE: this is only a temporary situation for the table
		// Sskp_dm
		final String tableShortcut = shortcut.equals("sskp_dm") ? "sskp" //$NON-NLS-1$//$NON-NLS-2$
				: shortcut;
		final Path templatePath = Paths.get(TEMPLATE_DIR,
				tableShortcut + "_vorlage.xlsx"); //$NON-NLS-1$
		try (final FileInputStream inputStream = new FileInputStream(
				templatePath.toFile());
				final XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
			// check overwrite
			if (!overwriteHandling.test(outputPath)) {
				return;
			}
			createCellNewValueFont(workbook);
			createCellOldValueFont(workbook);
			// es gibt nur einen
			final XSSFSheet sheet = workbook.getSheetAt(0);
			workbook.setSheetName(0, shortcut.substring(0, 1).toUpperCase()
					+ shortcut.substring(1));
			defaultCellStyleByColumn = getDefaultCellStyles(workbook, sheet);
			if (tableType != TableType.DIFF
					&& exportType != ExportType.INVENTORY_RECORDS) {
				ExcelWaterMark.createWaterMark(sheet,
						enumTranslationService.translate(tableType)
								.getPresentation());
			}
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
				final XSSFSheet footnoteSheet = workbook
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

	private static List<XSSFCellStyle> getDefaultCellStyles(
			final XSSFWorkbook workbook, final XSSFSheet sheet) {
		final int headerLastRowIndex = getHeaderLastRowIndex(sheet);
		final XSSFRow dataRow = sheet.getRow(headerLastRowIndex + 1);
		final List<XSSFCellStyle> result = new LinkedList<>();
		for (int i = 0; i <= getHeaderLastColumnIndex(sheet); i++) {
			if (dataRow.getCell(i) == null) {
				final XSSFCell cell = dataRow.createCell(i);
				cell.getCellStyle().setFont(getDefaultFont(workbook));
				cell.getCellStyle().setBorderBottom(BorderStyle.NONE);
				cell.getCellStyle().setBorderTop(BorderStyle.NONE);
				cell.getCellStyle().setBorderRight(BorderStyle.NONE);
				cell.getCellStyle().setBorderLeft(BorderStyle.NONE);
			}
			result.add(dataRow.getCell(i).getCellStyle());

		}
		return result;
	}

	@SuppressWarnings("boxing")
	private static void fillFootnoteSheet(final XSSFSheet footnoteSheet,
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

	private static void fillSheet(final XSSFWorkbook workbook,
			final XSSFSheet sheet, final List<TableRow> rows,
			final int rowIndex, final int columnCount,
			final boolean inlineFootnote) {
		int contentRowIndex = rowIndex;
		for (final TableRow row : rows) {
			if (isRowEmpty(row) && !(row instanceof PlanCompareRow)) {
				continue;
			}
			final XSSFRow sheetRow = contentRowIndex == rowIndex
					? sheet.getRow(contentRowIndex)
					: createNewRow(sheet, contentRowIndex, columnCount);
			final FootnoteContainer footnoteContainer = row.getFootnotes();
			for (int i = 0; i < columnCount; i++) {
				final TableCell tableCell = row.getCells().get(i);
				final XSSFRichTextString richTextCell = createRichTextCell(
						tableCell.getContent());
				final XSSFCell cell = sheetRow.getCell(i + 1);
				if (tableCell.getContent() instanceof CompareTableCellContent) {
					setCompareTableCellStyle(workbook, columnCount, row, i,
							cell);
				}
				if (TableToTableDocument.isRemarkColumn(row.getCells().get(i))
						&& footnoteContainer != null) {
					fillFootnoteCell(richTextCell, footnoteContainer,
							inlineFootnote);
				}

				cell.setCellValue(richTextCell);
			}
			// Auto adjust row height
			sheetRow.setHeight((short) -1);
			contentRowIndex++;
		}
	}

	private static void setCompareTableCellStyle(final XSSFWorkbook workbook,
			final int columnCount, final TableRow row, final int i,
			final XSSFCell cell) {
		final CTBorder cellBorder = switch (row) {
			case final PlanCompareRow compareRow -> createCompareTableCellBorderStyle(
					workbook, true, true, i == columnCount - 1, i == 0,
					compareRow
							.getRowType() == PlanCompareRowType.CHANGED_GUID_ROW
									? STBorderStyle.MEDIUM_DASHED
									: STBorderStyle.MEDIUM);
			default -> createCompareTableCellBorderStyle(workbook, true, true,
					true, true, STBorderStyle.MEDIUM);
		};
		final XSSFCellStyle compareTableCellStyle = getCompareTableCellStyle(
				workbook, cell, cellBorder);
		cell.setCellStyle(compareTableCellStyle);
	}

	private static boolean isRowEmpty(final TableRow row) {
		return getCellContents(row).stream()
				.allMatch(s -> s == null || s.trim().isEmpty());
	}

	private static List<String> getCellContents(final TableRow row) {
		return row.getCells().stream().map(cell -> {
			String content = TableCellExtensions.getPlainStringValue(cell);
			if (cell.getContent() instanceof final CompareTableCellContent compareCell) {
				content = CellContentExtensions.getPlainStringValue(
						compareCell.getMainPlanCellContent());
			}
			return content;
		}).toList();
	}

	@SuppressWarnings("nls")
	private static XSSFRichTextString createRichTextCell(
			final CellContent content) {
		if (content == null) {
			return new XSSFRichTextString("");
		}
		return switch (content) {
			case final StringCellContent stringContent -> new XSSFRichTextString(
					CellContentExtensions.getPlainStringValue(stringContent));
			case final CompareTableCellContent compareTableContent -> createRichTextCell(
					compareTableContent.getMainPlanCellContent());
			case final CompareStateCellContent compareStateContent -> createRichTextCell(
					compareStateContent);
			default -> throw new IllegalArgumentException(
					"Unexpected value: " + content);
		};
	}

	private static XSSFRichTextString createRichTextCell(
			final CompareStateCellContent compareStateContent) {
		final Iterable<String> newValues = CellContentExtensions
				.getStringValueIterable(compareStateContent.getNewValue());
		final Iterable<String> oldValues = CellContentExtensions
				.getStringValueIterable(compareStateContent.getOldValue());
		final String newValuesStr = Streams.stream(newValues)
				.collect(Collectors.joining(System.lineSeparator()));
		final String oldValuesStr = Streams.stream(oldValues)
				.collect(Collectors.joining(System.lineSeparator()));

		final XSSFRichTextString richtText = new XSSFRichTextString();
		if (newValuesStr.isEmpty()) {
			richtText.setString(oldValuesStr);
			richtText.applyFont(0, oldValuesStr.length(), cellOldValueFont);
		} else if (oldValuesStr.isEmpty()) {
			richtText.setString(newValuesStr);
			richtText.applyFont(0, newValuesStr.length(), cellNewValueFont);
		} else {
			final String textValue = Stream.of(newValuesStr, oldValuesStr)
					.collect(Collectors.joining(System.lineSeparator()));
			richtText.setString(textValue);
			richtText.applyFont(0, newValuesStr.length(), cellNewValueFont);
			richtText.applyFont(newValuesStr.length() + 1, textValue.length(),
					cellOldValueFont);
		}

		return richtText;
	}

	private static XSSFCellStyle getCompareTableCellStyle(
			final XSSFWorkbook workbook, final XSSFCell currentCell,
			final CTBorder ctBorder) {
		try {
			final StylesTable stylesSource = workbook.getStylesSource();
			final CTXf clone = cloneCellCTXf(workbook, currentCell);
			// Create blue border for compare cell
			// When the border already added to workbook, then get the index in
			// styles source, else added to styles source
			final int compareTableCellBorderIdx = getStyleSourceObjectIndex(
					ctBorder, //
					index -> stylesSource.getBorderAt(index).getCTBorder(),
					newObj -> stylesSource.putBorder(
							new XSSFCellBorder(newObj, workbook.getTheme(),
									stylesSource.getIndexedColors())));
			clone.setBorderId(compareTableCellBorderIdx);
			clone.setApplyBorder(true);

			// When the styles source exist the cell style like this, then give
			// the index of back, else added
			final int compareTableCellStyleIdx = getStyleSourceObjectIndex(
					clone, //
					stylesSource::getCellXfAt, //
					newObj -> stylesSource.putCellXf(newObj) - 1);
			return new XSSFCellStyle(compareTableCellStyleIdx, -1, stylesSource,
					workbook.getTheme());
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static CTXf cloneCellCTXf(final XSSFWorkbook workbook,
			final XSSFCell currentCell) throws XmlException {
		final StylesTable stylesSource = workbook.getStylesSource();
		// Get storage style index
		final int currentStyleIdx = stylesSource
				.putStyle(currentCell.getCellStyle());
		// Get raw source of the style
		final CTXf currentCellCTXf = stylesSource.getCellXfAt(currentStyleIdx);

		return CTXf.Factory.parse(currentCellCTXf.xmlText());
	}

	private static CTBorder createCompareTableCellBorderStyle(
			final XSSFWorkbook workbook, final boolean setTop,
			final boolean setBottom, final boolean setRight,
			final boolean setLeft, final STBorderStyle.Enum borderStyle) {
		final CTBorder ctborder = CTBorder.Factory.newInstance();
		final XSSFColor clr = XSSFColor.from(CTColor.Factory.newInstance(),
				workbook.getStylesSource().getIndexedColors());
		clr.setIndexed(IndexedColors.BLUE.getIndex());
		if (setBottom) {
			final CTBorderPr bottom = ctborder.isSetBottom()
					? ctborder.getBottom()
					: ctborder.addNewBottom();
			bottom.setStyle(borderStyle);
			bottom.setColor(clr.getCTColor());
		}

		if (setTop) {
			final CTBorderPr top = ctborder.isSetTop() ? ctborder.getTop()
					: ctborder.addNewTop();
			top.setStyle(borderStyle);
			top.setColor(clr.getCTColor());
		}

		if (setRight) {
			final CTBorderPr right = ctborder.isSetRight() ? ctborder.getRight()
					: ctborder.addNewRight();
			right.setStyle(STBorderStyle.MEDIUM);
			right.setColor(clr.getCTColor());

		}

		if (setLeft) {
			final CTBorderPr left = ctborder.isSetLeft() ? ctborder.getLeft()
					: ctborder.addNewLeft();
			left.setStyle(STBorderStyle.MEDIUM);
			left.setColor(clr.getCTColor());
		}

		return ctborder;
	}

	private static <T extends XmlObject> int getStyleSourceObjectIndex(
			final T obj, final IntFunction<T> getAddedObjFunc,
			final ToIntFunction<T> addNewObjFunc) {
		try {
			int i = 0;
			while (true) {
				final T addedObj = getAddedObjFunc.apply(i);
				if (addedObj.xmlText().equals(obj.xmlText())) {
					return i;
				}
				i++;
			}
		} catch (final IndexOutOfBoundsException e) {
			return addNewObjFunc.applyAsInt(obj);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static void fillFootnoteCell(final XSSFRichTextString richText,
			final FootnoteContainer fnContainer, final boolean inlineFootnote) {
		switch (fnContainer) {
			case final SimpleFootnoteContainer simpleContainer -> {
				final Iterable<FootnoteInfo> fnsInfo = TableExtensions
						.getFootnoteInfos(simpleContainer);
				final String fnStr = getFootnoteString(fnsInfo, inlineFootnote);
				richText.append(System.lineSeparator() + fnStr);

			}
			case final CompareFootnoteContainer compareContainer -> {
				final Iterable<FootnoteInfo> commonFnInfos = TableExtensions
						.getFootnoteInfos(
								compareContainer.getUnchangedFootnotes());
				final String commonFnStr = getFootnoteString(commonFnInfos,
						inlineFootnote);
				richText.append(System.lineSeparator() + commonFnStr);

				final Iterable<FootnoteInfo> newFnInfos = TableExtensions
						.getFootnoteInfos(compareContainer.getNewFootnotes());
				final String newFnStr = getFootnoteString(newFnInfos,
						inlineFootnote);
				richText.append(System.lineSeparator() + newFnStr,
						cellNewValueFont);

				final Iterable<FootnoteInfo> oldFnInfos = TableExtensions
						.getFootnoteInfos(compareContainer.getOldFootnotes());
				final String oldFnStr = getFootnoteString(oldFnInfos,
						inlineFootnote);
				richText.append(System.lineSeparator() + oldFnStr,
						cellNewValueFont);

			}
			case final CompareTableFootnoteContainer compareTableContainer -> fillFootnoteCell(
					richText,
					compareTableContainer.getMainPlanFootnoteContainer(),
					inlineFootnote);
			default -> throw new IllegalArgumentException(
					fnContainer.getClass().getName());
		}
	}

	private static String getFootnoteString(
			final Iterable<FootnoteInfo> footnotesInfo,
			final boolean inlineFootnote) {
		return Streams.stream(footnotesInfo)
				.map(inlineFootnote ? FootnoteInfo::toText
						: FootnoteInfo::toShorthand)
				.collect(Collectors
						.joining(inlineFootnote ? System.lineSeparator()
								: TableToTableDocument.FOOTNOTE_MARK_SEPRATOR));

	}

	private static XSSFRow createNewRow(final XSSFSheet sheet,
			final int rowIndex, final int maxColIndex) {
		final XSSFRow cloneRow = sheet.createRow(rowIndex);
		for (int i = 0; i <= maxColIndex; i++) {
			final Cell newCell = cloneRow.createCell(i);
			// Default style is style from first data cell of this column
			newCell.setCellStyle(defaultCellStyleByColumn.get(i));
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
				final CellRangeAddress mergeRegion = new CellRangeAddress(
						sheetRowIndex, sheetRowIndex + spanDown, sheetColumn,
						sheetColumn);
				sheet.addMergedRegion(mergeRegion);
				setStyleForMergeRegion(sheet, mergeRegion);
			}

			sheetRowIndex++;
		}
	}

	private static void setStyleForMergeRegion(final Sheet sheet,
			final CellRangeAddress mergeRegion) {
		if (mergeRegion.getFirstColumn() != mergeRegion.getLastColumn()) {
			throw new IllegalArgumentException("Shoundn't merge vertical cell"); //$NON-NLS-1$
		}
		final Cell firstCell = sheet.getRow(mergeRegion.getFirstRow())
				.getCell(mergeRegion.getFirstColumn());
		final CellStyle firstCellStyle = firstCell.getCellStyle();
		for (int i = mergeRegion.getFirstRow(); i <= mergeRegion
				.getLastRow(); i++) {
			final Cell cell = sheet.getRow(i)
					.getCell(mergeRegion.getFirstColumn());
			cell.setCellStyle(firstCellStyle);
		}

	}

	private static void createCellNewValueFont(final XSSFWorkbook workbook) {
		final Font defaultFont = getDefaultFont(workbook);
		cellNewValueFont = workbook.createFont();
		cellNewValueFont.setFontName(defaultFont.getFontName());
		cellNewValueFont
				.setFontHeightInPoints(defaultFont.getFontHeightInPoints());
		cellNewValueFont.setColor(IndexedColors.RED.getIndex());
	}

	private static void createCellOldValueFont(final XSSFWorkbook workbook) {
		final Font defaultFont = getDefaultFont(workbook);
		cellOldValueFont = workbook.createFont();
		cellOldValueFont.setFontName(defaultFont.getFontName());
		cellOldValueFont
				.setFontHeightInPoints(defaultFont.getFontHeightInPoints());
		cellOldValueFont.setColor(IndexedColors.YELLOW.getIndex());
		cellOldValueFont.setStrikeout(true);
	}

	private static Font getDefaultFont(final Workbook workbook) {
		return workbook.getFontAt(workbook.getSheetAt(0)
				.getRow(0)
				.getCell(1)
				.getCellStyle()
				.getFontIndex());
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
