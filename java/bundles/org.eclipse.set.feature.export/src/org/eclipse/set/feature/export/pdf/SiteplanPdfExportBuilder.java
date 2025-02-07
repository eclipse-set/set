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
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.poi.UnsupportedFileFormatException;
import org.eclipse.set.basis.FreeFieldInfo;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.ToolboxPaths;
import org.eclipse.set.basis.constants.ExportType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.exceptions.FileExportException;
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

	/**
	 * @param fopService
	 *            the FOP Service
	 */
	@Override
	@Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, unbind = "-")
	public void setFopService(final FopService fopService) {
		this.fopService = fopService;
	}

	private static String createImageDocumentText(
			final List<BufferedImage> imagesData, final Titlebox titleBox,
			final FreeFieldInfo freeFieldInfo, final double ppm)
			throws ParserConfigurationException, TransformerException {
		final TableToTableDocument tableToXmlFo = TableToTableDocument
				.createTransformation();
		final Document document = tableToXmlFo.transformToDocument(imagesData,
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

		try {
			final String imageDocumentText = createImageDocumentText(imagesData,
					titleBox, freeFieldInfo, ppm);

			if (ToolboxConfiguration.isDevelopmentMode()) {
				exportTableDocument(
						Paths.get(outputDir,
								getFilename(SITEPLAN_EXPORT_NAME, "xml")), //$NON-NLS-1$
						imageDocumentText);
			}
			final ByteArrayInputStream tableDocumentStream = new ByteArrayInputStream(
					imageDocumentText.getBytes(UTF_8));
			final StreamSource imageDocumentSource = new StreamSource(
					tableDocumentStream);
			final Pair<String, StreamSource> xslStreamSource = getSiteplanXSLTemplate(
					imagesData, ppm, tableType, outputDir);
			final Path outputPath = toolboxPaths.getTableExportPath(
					SITEPLAN_EXPORT_NAME + "_" + xslStreamSource.getFirst(), //$NON-NLS-1$
					Paths.get(outputDir), ExportType.PLANNING_RECORDS,
					TABLE_PDF_EXPORT_EXTENSION);
			fopService.fop(OutputFormat.PDF, xslStreamSource.getSecond(),
					imageDocumentSource, outputPath, PdfAMode.PDF_A_3a,
					overwriteHandling, null);

		} catch (final Exception e) {
			throw new FileExportException(
					Path.of(outputDir, SITEPLAN_EXPORT_NAME), e);
		}
	}

	private Pair<String, StreamSource> getSiteplanXSLTemplate(
			final List<BufferedImage> imagesData, final double ppm,
			final TableType tableType, final String outputDir)
			throws ParserConfigurationException, SAXException, IOException,
			NullPointerException, TransformerException,
			UnsupportedFileFormatException {
		final SiteplanXSL siteplanXSL = new SiteplanXSL(imagesData, ppm,
				translationTableType(tableType));
		final Document xslDoc = siteplanXSL.getXSLDocument();
		final String pageDIN = siteplanXSL.getPageStyle()
				.getPageDIN()
				.toString();
		if (ToolboxConfiguration.isDevelopmentMode()) {
			final Transformer documentToString = newTransformerFactory()
					.newTransformer();
			final DOMSource source = new DOMSource(xslDoc);
			final StringWriter writer = new StringWriter();
			final StreamResult result = new StreamResult(writer);
			documentToString.transform(source, result);
			exportTableDocument(
					Path.of(outputDir,
							getFilename(SITEPLAN_EXPORT_NAME + "_" + pageDIN, //$NON-NLS-1$
									"xsl")), //$NON-NLS-1$
					writer.toString());
		}
		return new Pair<>(pageDIN, toStreamSource(xslDoc));
	}

}
