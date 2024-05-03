/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.pdf;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.Assert;
import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.ToolboxPaths;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.exceptions.FileExportException;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.model.tablemodel.Table;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * {@link TableExport} implementation for PDF export via FOP.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(immediate = true)
public class FopPdfExportBuilder implements TableExport {

	private static final Logger logger = LoggerFactory
			.getLogger(FopPdfExportBuilder.class);

	private static final String TITLEBOX_SHORTCUT = "schriftfeld"; //$NON-NLS-1$

	private static TransformerFactory newTransformerFactory() {
		final TransformerFactory transformerFactory = TransformerFactory
				.newInstance();
		// Disable external access
		try {
			transformerFactory
					.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		} catch (final TransformerConfigurationException e) {
			// Ignore failure
		}
		return transformerFactory;
	}

	private static String createTableDocumentText(final Table table,
			final Titlebox titlebox, final FreeFieldInfo freeFieldInfo)
			throws ParserConfigurationException, TransformerException {
		final TableToTableDocument tableToXmlFo = TableToTableDocument
				.createTransformation();
		final Document document = tableToXmlFo.transformToDocument(table,
				titlebox, freeFieldInfo);
		final Transformer documentToString = newTransformerFactory()
				.newTransformer();
		final DOMSource source = new DOMSource(document);
		final StringWriter writer = new StringWriter();
		final StreamResult result = new StreamResult(writer);
		documentToString.transform(source, result);
		return writer.toString();
	}

	private static String createTitleboxDocumentText(final Titlebox titlebox)
			throws Exception {
		final TableToTableDocument tableToXmlFo = TableToTableDocument
				.createTransformation();
		final Document document = tableToXmlFo.transformToDocument(titlebox);

		final Transformer documentToString = newTransformerFactory()
				.newTransformer();
		final DOMSource source = new DOMSource(document);
		final StringWriter writer = new StringWriter();
		final StreamResult result = new StreamResult(writer);
		documentToString.transform(source, result);
		return writer.toString();
	}

	private static void exportTableDocument(final Path filename,
			final String content) throws IOException {
		try (PrintWriter out = new PrintWriter(filename.toString())) {
			out.println(content);
		}
	}

	private static String getFilename(final String shortcut,
			final String extension) {
		return shortcut + "-fop." + extension; //$NON-NLS-1$
	}

	private static Table getTableToBeExported(
			final Map<TableType, Table> tables, final ExportType exportType) {
		switch (exportType) {
		case INVENTORY_RECORDS:
			final Table invTable = tables.get(TableType.FINAL);
			if (invTable != null) {
				return invTable;
			}
			// if we do not have a final table we export the table of the single
			// container of a state
			return tables.get(TableType.SINGLE);
		case PLANNING_RECORDS: {
			final Table planTable = tables.get(TableType.DIFF);
			if (planTable != null) {
				return planTable;
			}
			// if we do not have a diff table we export the table of the single
			// container of a state
			return tables.get(TableType.SINGLE);
		}
		default:
			throw new IllegalArgumentException(exportType.toString());
		}
	}

	private FopService fopService;

	private String templateDir = "./data/export/pdf/"; //$NON-NLS-1$

	@Override
	public void export(final Map<TableType, Table> tables,
			final ExportType exportType, final Titlebox titlebox,
			final FreeFieldInfo freeFieldInfo, final String shortcut,
			final String outputDir, final ToolboxPaths toolboxPaths,
			final TableType tableType,
			final OverwriteHandling overwriteHandling)
			throws FileExportException {
		logger.info("Exporting {}", shortcut); //$NON-NLS-1$
		final Table table = getTableToBeExported(tables, exportType);
		Assert.isNotNull(table);
		final Path outputPath = toolboxPaths.getTablePdfExport(shortcut,
				Paths.get(outputDir), exportType);
		try {
			final String tableDocumentText = createTableDocumentText(table,
					titlebox, freeFieldInfo);
			if (ToolboxConfiguration.isDevelopmentMode()) {
				exportTableDocument(
						Paths.get(outputDir, getFilename(shortcut, "xml")), //$NON-NLS-1$
						tableDocumentText);
			}
			createTablePdf(tableDocumentText, outputPath, shortcut, tableType,
					PdfAMode.PDF_A_3a, overwriteHandling);
		} catch (final ParserConfigurationException | TransformerException
				| IOException | SAXException e) {
			throw new FileExportException(outputPath, e);
		}
	}

	private void createTablePdf(final String tableDocumentText,
			final Path outputPath, final String shortcut,
			final TableType tableType, final PdfAMode pdfAMode,
			final OverwriteHandling overwriteHandling) throws IOException,
			SAXException, TransformerException, ParserConfigurationException {
		final TransformTable transformTable = new TransformTable(shortcut,
				translationTableType(tableType));
		final Document xslDoc = transformTable.transform();
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

	@Override
	public void exportTitleboxImage(final Titlebox titlebox,
			final Path imagePath, final OverwriteHandling overwriteHandling)
			throws Exception {
		final String tableDocumentText = createTitleboxDocumentText(titlebox);
		if (ToolboxConfiguration.isDevelopmentMode()) {
			exportTableDocument(
					Paths.get(imagePath.getParent().toString(),
							TITLEBOX_SHORTCUT, "xml"), //$NON-NLS-1$
					tableDocumentText);
		}
		createImageFile(tableDocumentText, imagePath, overwriteHandling);
	}

	@Override
	public void exportTitleboxPdf(final Titlebox titlebox, final Path pdfPath,
			final OverwriteHandling overwriteHandling) throws Exception {
		try {
			final String tableDocumentText = createTitleboxDocumentText(
					titlebox);
			if (ToolboxConfiguration.isDevelopmentMode()) {
				exportTableDocument(
						Paths.get(pdfPath.getParent().toString(),
								getFilename(TITLEBOX_SHORTCUT, "xml")), //$NON-NLS-1$
						tableDocumentText);
			}
			createPdf(tableDocumentText, pdfPath, TITLEBOX_SHORTCUT,
					PdfAMode.NONE, overwriteHandling);
		} catch (final ParserConfigurationException | TransformerException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return the template directory
	 */
	public String getTemplateDir() {
		return templateDir;
	}

	/**
	 * @param fopService
	 *            the FOP Service
	 */
	@Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, unbind = "-")
	public void setFopService(final FopService fopService) {
		this.fopService = fopService;
	}

	/**
	 * @param templateDir
	 *            the template dir
	 */
	public void setTemplateDir(final String templateDir) {
		this.templateDir = templateDir;
	}

	private void createImageFile(final String tableDocumentText,
			final Path imagePath, final OverwriteHandling overwriteHandling)
			throws SAXException, IOException, TransformerException {
		final File xsltFile = getTemplateFilename(TITLEBOX_SHORTCUT).toFile();
		if (xsltFile.canRead()) {
			final StreamSource xslt = new StreamSource(xsltFile);
			final ByteArrayInputStream tableDocumentStream = new ByteArrayInputStream(
					tableDocumentText.getBytes(UTF_8));
			final StreamSource tableDocumentSource = new StreamSource(
					tableDocumentStream);
			fopService.fop(OutputFormat.IMAGE, xslt, tableDocumentSource,
					imagePath, PdfAMode.NONE, overwriteHandling, null);
		} else {
			logger.error("Missing pdf export template: " + xsltFile.toString()); //$NON-NLS-1$
		}
	}

	private void createPdf(final String tableDocumentText,
			final Path outputPath, final String shortcut,
			final PdfAMode pdfAMode, final OverwriteHandling overwriteHandling)
			throws IOException, SAXException, TransformerException {
		final File xsltFile = getTemplateFilename(shortcut).toFile();
		PathExtensions.checkCanRead(xsltFile.toPath());
		if (xsltFile.canRead()) {
			final StreamSource xslt = new StreamSource(xsltFile);
			final ByteArrayInputStream tableDocumentStream = new ByteArrayInputStream(
					tableDocumentText.getBytes(UTF_8));
			final StreamSource tableDocumentSource = new StreamSource(
					tableDocumentStream);
			fopService.fop(OutputFormat.PDF, xslt, tableDocumentSource,
					outputPath, pdfAMode, overwriteHandling, null);
		} else {
			logger.error("Missing pdf export template: " + xsltFile.toString()); //$NON-NLS-1$
		}
	}

	private Path getTemplateFilename(final String shortcut) {
		return Paths.get(getTemplateDir(), shortcut + "_vorlage.xsl"); //$NON-NLS-1$
	}

	// IMPROVE: This translation should replace by EnumTranslationService in 2.0
	// version.
	private static String translationTableType(final TableType tableType) {
		if (tableType == null) {
			return null;
		}
		switch (tableType) {
		case INITIAL:
			return "Startzustand"; //$NON-NLS-1$
		case FINAL:
			return "Zielzustand"; //$NON-NLS-1$
		default:
			return null;
		}
	}
}
