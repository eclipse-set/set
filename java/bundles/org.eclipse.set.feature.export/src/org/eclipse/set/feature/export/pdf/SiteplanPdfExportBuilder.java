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
package org.eclipse.set.feature.export.pdf;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.set.basis.ToolboxPaths.ExportPathExtension.TABLE_PDF_EXPORT_EXTENSION;
import static org.eclipse.set.utils.export.xsl.TransformTable.toStreamSource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.poi.UnsupportedFileFormatException;
import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.ToolboxPaths;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.exceptions.FileExportException;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.titlebox.Titlebox;
import org.eclipse.set.services.export.TableExport;
import org.eclipse.set.services.fop.FopService;
import org.eclipse.set.services.fop.FopService.OutputFormat;
import org.eclipse.set.services.fop.FopService.PdfAMode;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.export.xsl.siteplan.SiteplanXSL;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Export site plan to PDF via FOP.
 * 
 * @author Truong
 */
@Component(immediate = true, service = TableExport.class)
public class SiteplanPdfExportBuilder extends FopPdfExportBuilder {

	private static final String SITEPLAN_EXPORT_NAME = "SI"; //$NON-NLS-1$
	private static final String SITEPLAN_TMP_DIR = "siteplanTmp"; //$NON-NLS-1$

	/**
	 * @param enumTranslationService
	 *            the ENUM Translation Service
	 */
	@Override
	@Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, unbind = "-")
	public void setEnumTranslationService(
			final EnumTranslationService enumTranslationService) {
		this.enumTranslationService = enumTranslationService;
	}

	/**
	 * @param fopService
	 *            the FOP Service
	 */
	@Override
	@Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, unbind = "-")
	public void setFopService(final FopService fopService) {
		this.fopService = fopService;
	}

	private static String createImageDocumentText(final BufferedImage imageData,
			final Titlebox titleBox, final FreeFieldInfo freeFieldInfo,
			final double ppm)
			throws ParserConfigurationException, TransformerException {
		final TableToTableDocument tableToXmlFo = TableToTableDocument
				.createTransformation();
		final Document document = tableToXmlFo.transformToDocument(imageData,
				titleBox, freeFieldInfo, ppm);
		final Transformer documentToString = newTransformerFactory()
				.newTransformer();
		final DOMSource source = new DOMSource(document);
		final StringWriter writer = new StringWriter();
		final StreamResult result = new StreamResult(writer);
		documentToString.transform(source, result);
		return writer.toString();
	}

	@Override
	public void exportTitleboxImage(final Titlebox titlebox,
			final Path imagePath, final OverwriteHandling overwriteHandling)
			throws Exception {
		// do nothing
	}

	@Override
	public void exportTitleboxPdf(final Titlebox titlebox, final Path pdfPath,
			final OverwriteHandling overwriteHandling) throws Exception {
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
		// do nothing
	}

	@Override
	public void exportSiteplanPdf(final List<BufferedImage> imagesData,
			final Titlebox titleBox, final FreeFieldInfo freeFieldInfo,
			final double ppm, final String outputDir,
			final ToolboxPaths toolboxPaths, final TableType tableType,
			final OverwriteHandling overwriteHandling) {
		final Path exportTmpDir = Path.of(outputDir, SITEPLAN_TMP_DIR);
		if (!exportTmpDir.toFile().exists()) {
			exportTmpDir.toFile().mkdirs();
		} else if (exportTmpDir.toFile().isDirectory()) {
			final File[] listFiles = exportTmpDir.toFile().listFiles();
			if (listFiles != null) {
				for (final File file : listFiles) {
					file.delete();
				}
			}
		}
		final PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
		pdfMergerUtility.setDestinationFileName(toolboxPaths
				.getTableExportPath(SITEPLAN_EXPORT_NAME, Paths.get(outputDir),
						ExportType.PLANNING_RECORDS, TABLE_PDF_EXPORT_EXTENSION)
				.toString());
		try {
			for (int i = 0; i < imagesData.size(); i++) {
				final String imageDocumentText = createImageDocumentText(
						imagesData.get(i), titleBox, freeFieldInfo, ppm);
				final String siteplanExportName = SITEPLAN_EXPORT_NAME + "_" //$NON-NLS-1$
						+ i;
				if (ToolboxConfiguration.isDebugMode()) {
					exportTableDocument(
							Paths.get(exportTmpDir.toString(),
									getFilename(siteplanExportName, "xml")), //$NON-NLS-1$
							imageDocumentText);
				}
				final ByteArrayInputStream tableDocumentStream = new ByteArrayInputStream(
						imageDocumentText.getBytes(UTF_8));
				final StreamSource imageDocumentSource = new StreamSource(
						tableDocumentStream);
				final String exportFileName = getFilename(siteplanExportName,
						"xsl"); //$NON-NLS-1$
				final String pagePostFix = i == imagesData.size() - 1 ? "-" //$NON-NLS-1$
						: "+"; //$NON-NLS-1$
				final Pair<String, StreamSource> xslStreamSource = getSiteplanXSLTemplate(
						imagesData.get(i), ppm, tableType,
						exportTmpDir.toString(), exportFileName, i + 1,
						pagePostFix);
				final Path outputPath = toolboxPaths.getTableExportPath(
						String.format("%s_%s_%d", SITEPLAN_EXPORT_NAME, //$NON-NLS-1$
								xslStreamSource.getFirst(), Integer.valueOf(i)),
						exportTmpDir, ExportType.PLANNING_RECORDS,
						TABLE_PDF_EXPORT_EXTENSION);
				fopService.fop(OutputFormat.PDF, xslStreamSource.getSecond(),
						imageDocumentSource, outputPath, PdfAMode.PDF_A_3a,
						overwriteHandling, null);
				pdfMergerUtility.addSource(outputPath.toFile());
			}
			pdfMergerUtility
					.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
		} catch (final Exception e) {
			throw new FileExportException(
					Path.of(outputDir, SITEPLAN_EXPORT_NAME), e);
		}

		if (!ToolboxConfiguration.isDevelopmentMode()
				&& exportTmpDir.toFile().exists()) {
			try (Stream<Path> paths = Files.walk(exportTmpDir,
					FileVisitOption.values())) {
				paths.sorted(Comparator.reverseOrder())
						.map(Path::toFile)
						.forEach(File::delete);
			} catch (final IOException e) {
				throw new FileExportException(
						Path.of(outputDir, SITEPLAN_EXPORT_NAME), e);
			}
		}

	}

	private Pair<String, StreamSource> getSiteplanXSLTemplate(
			final BufferedImage imageData, final double ppm,
			final TableType tableType, final String outputDir,
			final String exportFileName, final int pagePosition,
			final String pagePostfix) throws ParserConfigurationException,
			SAXException, IOException, NullPointerException,
			TransformerException, UnsupportedFileFormatException {
		final SiteplanXSL siteplanXSL = new SiteplanXSL(imageData, ppm,
				translationTableType(tableType), pagePosition, pagePostfix);
		final Document xslDoc = siteplanXSL.getXSLDocument();
		final String pageDIN = siteplanXSL.getPageStyle()
				.getPageDIN()
				.toString();
		if (ToolboxConfiguration.isDebugMode()) {
			final Transformer documentToString = newTransformerFactory()
					.newTransformer();
			final DOMSource source = new DOMSource(xslDoc);
			final StringWriter writer = new StringWriter();
			final StreamResult result = new StreamResult(writer);
			documentToString.transform(source, result);
			exportTableDocument(Path.of(outputDir, exportFileName),
					writer.toString());
		}
		return new Pair<>(pageDIN, toStreamSource(xslDoc));
	}
}
