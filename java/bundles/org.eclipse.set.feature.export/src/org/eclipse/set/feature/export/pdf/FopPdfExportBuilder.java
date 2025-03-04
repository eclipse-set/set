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
import static org.eclipse.set.utils.export.xsl.TransformTable.toStreamSource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
import org.apache.pdfbox.text.PDFTextStripper;
import org.eclipse.core.runtime.Assert;
import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.ToolboxPaths;
import org.eclipse.set.basis.ToolboxPaths.ExportPathExtension;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.exceptions.FileExportException;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.services.export.TableExport;
import org.eclipse.set.services.fop.FopService;
import org.eclipse.set.services.fop.FopService.OutputFormat;
import org.eclipse.set.services.fop.FopService.PdfAMode;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.export.xsl.TransformTable;
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

	@Reference
	EnumTranslationService enumTranslationService;
	protected static final String FOOTNOTE_MARK = "Footnote"; //$NON-NLS-1$
	protected static final Logger logger = LoggerFactory
			.getLogger(FopPdfExportBuilder.class);

	private static final String TITLEBOX_SHORTCUT = "schriftfeld"; //$NON-NLS-1$

	protected static TransformerFactory newTransformerFactory() {
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

	protected static String createTableDocumentText(final Table table,
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

	protected static void exportTableDocument(final Path filename,
			final String content) throws IOException {
		try (PrintWriter out = new PrintWriter(filename.toString())) {
			out.println(content);
		}
	}

	protected static String getFilename(final String shortcut,
			final String extension) {
		return shortcut + "-fop." + extension; //$NON-NLS-1$
	}

	protected static Table getTableToBeExported(
			final Map<TableType, Table> tables, final ExportType exportType) {
		switch (exportType) {
			case INVENTORY_RECORDS:
				final Table invTable = tables.get(TableType.FINAL);
				if (invTable != null) {
					return invTable;
				}
				// if we do not have a final table we export the table of the
				// single
				// container of a state
				return tables.get(TableType.SINGLE);
			case PLANNING_RECORDS: {
				final Table planTable = tables.get(TableType.DIFF);
				if (planTable != null) {
					return planTable;
				}
				// if we do not have a diff table we export the table of the
				// single
				// container of a state
				return tables.get(TableType.SINGLE);
			}
			default:
				throw new IllegalArgumentException(exportType.toString());
		}
	}

	protected FopService fopService;

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
		final List<String> pageBreakRowsIndex = getPageBreakRowsIndex(table);
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
			createTablePdf(tableDocumentText, outputPath, shortcut, tableType,
					PdfAMode.PDF_A_3a, overwriteHandling, pageBreakRowsIndex);
		} catch (final ParserConfigurationException | TransformerException
				| IOException | SAXException e) {
			throw new FileExportException(outputPath, e);
		} catch (final UserAbortion e) {
			// do nothing
		}
	}

	protected void createTablePdf(final String tableDocumentText,
			final Path outputPath, final String shortcut,
			final TableType tableType, final PdfAMode pdfAMode,
			final OverwriteHandling overwriteHandling,
			final List<String> pageBreakRowsIndex)
			throws IOException, SAXException, TransformerException,
			ParserConfigurationException, UserAbortion {
		final TransformTable transformTable = new TransformTable(shortcut,
				translationTableType(tableType));
		final Document xslDoc = pageBreakRowsIndex.isEmpty()
				? transformTable.transform()
				: transformTable.transform(pageBreakRowsIndex);
		createTablePdf(xslDoc, tableDocumentText, outputPath, shortcut,
				pdfAMode, overwriteHandling);
		// Resort pages of PDF
		if (transformTable.isMultiPageLayout()) {
			reSortPdfPage(outputPath);
		}
	}

	/**
	 * Sort multipage layout PDF again, because the page sequence by FOP export
	 * isn't rational.
	 * 
	 * @param outputPath
	 *            the pdf file, which result of FOP export
	 * @throws IOException
	 *             the {@link IOException}
	 */
	private static void reSortPdfPage(final Path outputPath)
			throws IOException {
		try (PDDocument pdf = PDDocument.load(outputPath.toFile());
				PDDocument newPdf = new PDDocument()) {

			final int pageCount = pdf.getPages().getCount();
			final List<PDPage> tablePages = new LinkedList<>();
			final List<PDPage> footnotePages = new LinkedList<>();
			final IntFunction<String> getPageTextFunc = pageIndex -> {
				// When table page isn't empty, that mean all footnote page are
				// already add to list
				if (!tablePages.isEmpty()) {
					return ""; //$NON-NLS-1$
				}
				try {
					final PDFTextStripper pdfTextStripper = new PDFTextStripper();
					pdfTextStripper.setStartPage(pageIndex);
					pdfTextStripper.setEndPage(pageIndex);

					return pdfTextStripper.getText(pdf);
				} catch (final IOException e) {
					return ""; //$NON-NLS-1$
				}
			};
			for (int i = pageCount,
					j = 0; i >= 0; i--, j = footnotePages.size()) {
				// Find Footnote page
				final PDPage page = pdf.getPage(i - 1);
				final String pageText = getPageTextFunc.apply(i);
				if (pageText.contains(FOOTNOTE_MARK)) {
					footnotePages.addFirst(page);
				} else if ((pageCount - j) / 2 < i) {
					// Determine A page of table
					final int aPageIndex = i - (pageCount - j) / 2 - 1;
					final PDPage aPage = pdf.getPage(aPageIndex);
					tablePages.addFirst(page);
					tablePages.addFirst(aPage);
				} else {
					break;
				}
			}
			footnotePages.forEach(pdf::removePage);

			tablePages.forEach(newPdf::addPage);
			footnotePages.forEach(newPdf::addPage);
			// Copy metadata
			final PDMetadata metadata = pdf.getDocumentCatalog().getMetadata();
			if (metadata != null) {
				newPdf.getDocumentCatalog().setMetadata(metadata);
			}
			final List<PDOutputIntent> outputIntents = pdf.getDocumentCatalog()
					.getOutputIntents();
			if (outputIntents != null && !outputIntents.isEmpty()) {
				newPdf.getDocumentCatalog().setOutputIntents(outputIntents);
			}

			newPdf.save(outputPath.toFile());
		}
	}

	private void createTablePdf(final Document xslDoc,
			final String tableDocumentText, final Path outputPath,
			final String shortcut, final PdfAMode pdfAMode,
			final OverwriteHandling overwriteHandling) throws IOException,
			SAXException, TransformerException, UserAbortion {
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
			fopService.fop(OutputFormat.PDF, toStreamSource(xslDoc),
					tableDocumentSource, outputPath, pdfAMode,
					overwriteHandling, null);
		} else {
			logger.error("Cant export table: " + shortcut); //$NON-NLS-1$
		}
	}

	/**
	 * @param table
	 *            the export table
	 * @return the index of row, which the export should break page
	 */
	@SuppressWarnings("static-method")
	protected List<String> getPageBreakRowsIndex(final Table table) {
		return Collections.emptyList();
	}

	@Override
	public void exportTitleboxImage(final Titlebox titlebox,
			final Path imagePath, final OverwriteHandling overwriteHandling)
			throws Exception {
		try {
			final String tableDocumentText = createTitleboxDocumentText(
					titlebox);
			if (ToolboxConfiguration.isDevelopmentMode()) {
				exportTableDocument(
						Paths.get(imagePath.getParent().toString(),
								TITLEBOX_SHORTCUT, "xml"), //$NON-NLS-1$
						tableDocumentText);
			}
			createImageFile(tableDocumentText, imagePath, overwriteHandling);
		} catch (final UserAbortion e) {
			// do nothing
		}

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
		} catch (final UserAbortion e) {
			// do nothing
		}
	}

	@Override
	public void exportSiteplanPdf(final List<BufferedImage> imagesData,
			final Titlebox titleBox, final FreeFieldInfo freeFieldInfo,
			final double ppm, final String outputDir,
			final ToolboxPaths toolboxPaths, final TableType tableType,
			final OverwriteHandling overwriteHandling) {
		// do nothing
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
			throws SAXException, IOException, TransformerException,
			UserAbortion {
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
			throws IOException, SAXException, TransformerException,
			UserAbortion {
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

	protected String translationTableType(final TableType tableType) {
		return EObjectExtensions
				.getNullableObject(tableType,
						type -> enumTranslationService.translate(type)
								.getPresentation())
				.orElse(null);
	}

	@Override
	public String getTableShortcut() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ExportFormat getExportFormat() {
		return ExportFormat.PDF;
	}

}
