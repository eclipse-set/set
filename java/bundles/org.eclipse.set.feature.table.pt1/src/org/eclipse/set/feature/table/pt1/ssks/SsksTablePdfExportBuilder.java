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
package org.eclipse.set.feature.table.pt1.ssks;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.Assert;
import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.ToolboxPaths;
import org.eclipse.set.basis.ToolboxPaths.ExportPathExtension;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.exceptions.FileExportException;
import org.eclipse.set.feature.export.pdf.FopPdfExportBuilder;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableRowExtensions;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.services.export.TableExport;
import org.eclipse.set.services.fop.FopService;
import org.eclipse.set.services.fop.FopService.OutputFormat;
import org.eclipse.set.services.fop.FopService.PdfAMode;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.table.transform.TransformTable;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Specifier PDF export for table Ssks via FOP
 * 
 * @author Truong
 * 
 *         IMPROVE: duplicate code with {@link FopPdfExportBuilder} should be
 *         move to a Abstractclass
 */
@Component(immediate = true, service = TableExport.class)
public class SsksTablePdfExportBuilder extends FopPdfExportBuilder {

	@Reference
	private Messages messages;

	private FopService fopService;

	/**
	 * @param fopService
	 *            the FOP Service
	 */
	@Override
	@Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, unbind = "-")
	public void setFopService(final FopService fopService) {
		this.fopService = fopService;
	}

	@Override
	public void exportTitleboxImage(final Titlebox titlebox,
			final Path imagePath, final OverwriteHandling overwriteHandling)
			throws Exception {
		// do nothing
	}

	@Override
	public void export(final Map<TableType, Table> tables,
			final ExportType exportType, final Titlebox titlebox,
			final FreeFieldInfo freeFieldInfo, final String shortcut,
			final String outputDir, final ToolboxPaths toolboxPaths,
			final TableType tableType,
			final OverwriteHandling overwriteHandling)
			throws FileExportException {
		final Table table = getTableToBeExported(tables, exportType);
		Assert.isNotNull(table);
		final Path outputPath = toolboxPaths.getTableExportPath(shortcut,
				Paths.get(outputDir), exportType,
				ExportPathExtension.TABLE_PDF_EXPORT_EXTENSION);
		try {
			final String tableDocumentText = createTableDocumentText(table,
					titlebox, freeFieldInfo);
			if (ToolboxConfiguration.isDevelopmentMode()) {
				exportTableDocument(
						Paths.get(outputDir, getFilename(shortcut, "xml")), //$NON-NLS-1$
						tableDocumentText);
			}
			createSsksTablePdf(table, tableDocumentText, outputPath, shortcut,
					tableType, PdfAMode.PDF_A_3a, overwriteHandling);
		} catch (final ParserConfigurationException | TransformerException
				| IOException | SAXException e) {
			throw new FileExportException(outputPath, e);
		}
	}

	protected void createSsksTablePdf(final Table table,
			final String tableDocumentText, final Path outputPath,
			final String shortcut, final TableType tableType,
			final PdfAMode pdfAMode, final OverwriteHandling overwriteHandling)
			throws IOException, SAXException, TransformerException,
			ParserConfigurationException {
		final List<String> pageBreakRowIndexes = getPageBreakRowsIndex(table);

		final TransformTable transformTable = new TransformTable(shortcut,
				translationTableType(tableType));
		final Document xslDoc = pageBreakRowIndexes.isEmpty()
				? transformTable.transform()
				: transformTable.transform(pageBreakRowIndexes);
		if (xslDoc != null) {
			if (ToolboxConfiguration.isDevelopmentMode()) {
				final Transformer documentToString = newTransformerFactory()
						.newTransformer();
				final DOMSource source = new DOMSource(xslDoc);
				final StringWriter writer = new StringWriter();
				final StreamResult result = new StreamResult(writer);
				documentToString.transform(source, result);
				exportTableDocument(
						Paths.get(outputPath.getParent().toString(),
								getFilename(shortcut, "xsl")), //$NON-NLS-1$
						writer.toString());
			}
			final ByteArrayInputStream tableDocumentStream = new ByteArrayInputStream(
					tableDocumentText.getBytes(UTF_8));
			final StreamSource tableDocumentSource = new StreamSource(
					tableDocumentStream);
			fopService.fop(OutputFormat.PDF,
					TransformTable.toStreamSource(xslDoc), tableDocumentSource,
					outputPath, pdfAMode, overwriteHandling, null);
		} else {
			logger.error("Cant export table: " + shortcut); //$NON-NLS-1$
		}
	}

	private static List<String> getPageBreakRowsIndex(final Table table) {
		final List<String> result = new ArrayList<>();
		final List<TableRow> rows = TableExtensions.getTableRows(table);
		final ColumnDescriptor fiktiveSignalColumn = TableExtensions
				.getColumns(table).stream()
				.filter(column -> column.getColumnPosition()
						.equals(SsksColumns.Fiktives_Signal))
				.findFirst().orElse(null);
		if (fiktiveSignalColumn == null) {
			return result;
		}

		final Map<Integer, TableRow> rowsIndexed = rows.stream()
				.collect(Collectors.toMap(
						e -> Integer.valueOf(rows.indexOf(e) + 1),
						Function.identity()));
		final List<Entry<Integer, TableRow>> fiktiveSignalRows = rowsIndexed
				.entrySet().stream().filter(entry -> {
					final TableRow row = entry.getValue();
					final String cellValue = TableRowExtensions
							.getPlainStringValue(row, fiktiveSignalColumn);
					return !cellValue.isEmpty() && !cellValue.isBlank();
				}).toList();
		final Optional<Entry<Integer, TableRow>> firstFiktiveSignalEntry = fiktiveSignalRows
				.stream().min((first, second) -> Integer.compare(
						first.getKey().intValue(), second.getKey().intValue()));
		final Optional<Entry<Integer, TableRow>> lastFiktiveSignalEntry = fiktiveSignalRows
				.stream().max((first, second) -> Integer.compare(
						first.getKey().intValue(), second.getKey().intValue()));
		if (firstFiktiveSignalEntry.isPresent()) {
			result.add(firstFiktiveSignalEntry.get().getKey().toString());
		}

		if (lastFiktiveSignalEntry.isPresent() && lastFiktiveSignalEntry.get()
				.getKey().intValue() < rows.size()) {
			// To break a Pdf page with XSL, the attribute "break-bfore" was
			// used
			// Therefore, the last row index should increased by +1
			result.add(String.valueOf(
					lastFiktiveSignalEntry.get().getKey().intValue() + 1));
		}
		return result;
	}

	@Override
	public void exportTitleboxPdf(final Titlebox titlebox, final Path pdfPath,
			final OverwriteHandling overwriteHandling) throws Exception {
		// do nothing
	}

	@Override
	public String getTableShortcut() {
		return messages.ToolboxTableNameSsksShort.toLowerCase();
	}

	@Override
	public ExportFormat getExportFormat() {
		return ExportFormat.PDF;
	}

}
