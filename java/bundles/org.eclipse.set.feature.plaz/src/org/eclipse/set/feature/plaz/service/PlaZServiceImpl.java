/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plaz.service;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.fop.apps.io.ResourceResolverFactory;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.PlanProXMLNode;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.basis.exceptions.NotWritable;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.feature.plaz.Messages;
import org.eclipse.set.feature.plaz.PlazConstants;
import org.eclipse.set.feature.plaz.schema.PlaZSchema;
import org.eclipse.set.feature.plaz.utils.PlaZConfiguration;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.services.fop.FopService;
import org.eclipse.set.services.fop.FopService.OutputFormat;
import org.eclipse.set.services.fop.FopService.PdfAMode;
import org.eclipse.set.services.xslt.XsltService;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.math.AgateRounding;
import org.eclipse.set.utils.math.DoubleExtensions;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import jakarta.inject.Inject;

/**
 * Implementation of {@link PlazService}.
 * 
 * @author Schaefer
 */
public class PlaZServiceImpl implements PlazService {

	private static final String DEFAULT_RESOURCE_PATH;

	// FE: file extensions are not translated
	private static final String EXTENSION_SVRL = ".svrl"; //$NON-NLS-1$ FE

	private static final String FALSE = "false"; //$NON-NLS-1$

	private static final String HIERARCHICAL_SVRL = "HierarchicalSVRL.xsl"; //$NON-NLS-1$

	private static final String MAX_OBJECT_COUNT_IN_PDF = "MAX_OBJECT_COUNT_IN_PDF";//$NON-NLS-1$ FN

	private static final String PARAM_DETAILED_REPORT = "detailliertes-dokument"; //$NON-NLS-1$

	private static final String PLANPRO_CHECKSUM = "md5"; //$NON-NLS-1$

	private static final String PLANPRO_FILENAME = "planpro-dateiname"; //$NON-NLS-1$

	private static final String PLAZ_HTML_RESOURCES = "./web/plaz"; //$NON-NLS-1$

	private static final String RESOURCE_BASE = "/res/"; //$NON-NLS-1$

	private static final String SCHEMA_VALID = "schema-valid"; //$NON-NLS-1$

	private static final String TOOL_STAMP = "tool-stamp"; //$NON-NLS-1$

	private static final String TRANSFORM_TO_FOP = "svrl2xslfo.xsl"; //$NON-NLS-1$ FN

	private static final String TRANSFORM_TO_HTML = "svrl2html.xsl"; //$NON-NLS-1$

	private static final String TRANSFORM_TO_SVRL = "Regelbasis_compiled.sch"; //$NON-NLS-1$ FN

	static final Resource DEFAULT_RESOURCE;

	static final Logger logger = LoggerFactory.getLogger(PlaZServiceImpl.class);

	static {
		DEFAULT_RESOURCE_PATH = RESOURCE_BASE
				+ "Pictures/049_Achtung_v1-1-orange-rahmen_gross.svg"; //$NON-NLS-1$
		DEFAULT_RESOURCE = new Resource(
				PlaZSchema.class.getResourceAsStream(DEFAULT_RESOURCE_PATH));
	}

	/**
	 * Determine estimated time for transform PlaZ-LST
	 * 
	 * @param svrlPath
	 *            the SVRL file path
	 * @param svrlAvailable
	 * @param toolboxFile
	 *            the {@link ToolboxFile}
	 * @return the estimated time
	 */
	@SuppressWarnings("boxing")
	private static double estimateTimeCalculate(final Path svrlPath,
			final boolean svrlAvailable, final ToolboxFile toolboxFile) {
		final PlanProXMLNode rootNode = toolboxFile.getXMLDocument(
				PathExtensions.getBaseFileName(toolboxFile.getModelPath()));
		final Set<PlanProXMLNode> containerNodes = PlanProXMLNode
				.evaluateXPath(rootNode, "//Container")
				.stream()
				.filter(container -> container.getParent()
						.getNodeName()
						.equals("LST_Zustand_Ziel")
						|| container.getParent()
								.getNodeName()
								.equals("LST_Zustand"))
				.collect(Collectors.toSet());
		final int totalLSTElement = containerNodes.stream()
				.map(container -> container.getChildren().size())
				.reduce(0, (a, b) -> a + b)
				.intValue();
		logger.info("Total Final Element: {}", totalLSTElement);
		final double estimateTime = 0.005 * Math.pow(totalLSTElement, 1.5)
				+ 25700;
		if (Files.exists(svrlPath) || svrlAvailable) {
			// Average time by transform with exist SVRL file
			return 10 * 1000;
		}
		return estimateTime;
	}

	@SuppressWarnings("boxing")
	private static String estimateTimeToString(final double milisecond) {
		final double second = milisecond / 1000;
		final double minute = AgateRounding.roundUp(second / 60, 1);
		if (minute < 1) {

			return String.format("~%s Sekunden",
					DoubleExtensions.toTableDecimal(second, 0));
		}
		return String.format("~%s Minuten",
				DoubleExtensions.toTableDecimal(minute, 0));
	}

	private static Map<String, String> getParameters(final boolean schemaValid,
			final ToolboxFile toolboxFile) {
		final Map<String, String> parameters = new HashMap<>();
		parameters.put(PLANPRO_FILENAME,
				toolboxFile.getPath().getFileName().toString());
		parameters.put(PLANPRO_CHECKSUM, toolboxFile.getChecksum());
		parameters.put(TOOL_STAMP, getToolStamp());
		parameters.put(SCHEMA_VALID, Boolean.toString(schemaValid));
		parameters.put(MAX_OBJECT_COUNT_IN_PDF,
				PlaZConfiguration.getMaxObjectCount());
		return parameters;
	}

	private static Map<String, String> getSummaryParameters(
			final boolean schemaValid, final ToolboxFile toolboxFile) {
		final Map<String, String> parameters = getParameters(schemaValid,
				toolboxFile);
		parameters.put(PARAM_DETAILED_REPORT, FALSE);
		return parameters;
	}

	private static String getToolStamp() {
		final String version = ToolboxConfiguration.getToolboxVersion()
				.getShortVersion();
		return "PlanPro-Werkzeugkoffer, Version " + version; //$NON-NLS-1$
	}

	@Inject
	private CacheService cacheService;

	@Inject
	private FopService fopService;

	@Inject
	private XsltService xsltService;

	@Inject
	@Translation
	protected Messages messages;

	final ResourceResolver resourceResolver = new ResourceResolver() {
		private final ResourceResolver defaultResourceResolver = ResourceResolverFactory
				.createDefaultResourceResolver();

		@Override
		public OutputStream getOutputStream(final URI arg0) throws IOException {
			throw new UnsupportedOperationException();
		}

		@Override
		public Resource getResource(final URI uri) throws IOException {
			if (uri.isAbsolute() && new File(uri).exists()) {
				return defaultResourceResolver.getResource(uri);
			}

			final File resFile = new File(uri);

			logger.info("Resolve: {}", resFile.getName()); //$NON-NLS-1$ LOG
			final Resource resource = getResource(resFile.getParentFile(),
					Paths.get(resFile.getName()));

			logger.info("resource={}", resource); //$NON-NLS-1$ LOG

			if (resource == null) {
				logger.error("Resource {} not found.", resFile.getName()); //$NON-NLS-1$
				return DEFAULT_RESOURCE;
			}
			return resource;
		}

		private Resource getResource(final File parentFile, final Path path)
				throws IOException {
			final String resourcePath = (RESOURCE_BASE + path).replace('\\',
					'/');
			final InputStream resourceAsStream = PlaZSchema.class
					.getResourceAsStream(resourcePath);
			if (resourceAsStream != null) {
				return new Resource(resourceAsStream);
			}
			if (!parentFile.getParentFile().exists()) {
				return null;
			}
			return getResource(parentFile.getParentFile(),
					Paths.get(parentFile.getName(), path.toString()));
		}
	};

	@Override
	public void createCompletePdfReport(final IProgressMonitor monitor,
			final Path tmpDir, final ToolboxFile toolboxFile,
			final Path pdfLocation, final boolean schemaValid,
			final boolean svrlAvailable,
			final OverwriteHandling overwriteHandling) {
		createPdfReport(monitor, tmpDir, toolboxFile.getModelPath(),
				getParameters(schemaValid, toolboxFile), pdfLocation,
				svrlAvailable, toolboxFile, overwriteHandling);
	}

	@Override
	public void createHtmlReport(final IProgressMonitor monitor,
			final Path tmpDir, final ToolboxFile toolboxFile,
			final Path htmlLocation, final boolean schemaValid,
			final boolean svrlAvailable) {
		final Path modelLocation = toolboxFile.getModelPath();
		logger.info("modelLocation={}", modelLocation.toString()); //$NON-NLS-1$
		logger.info("htmlLocation={}", htmlLocation.toString()); //$NON-NLS-1$

		final Path svrlPath = Paths.get(tmpDir.toString(),
				modelLocation.getFileName().toString() + EXTENSION_SVRL);
		final long startTime = System.currentTimeMillis();
		final double estimateTimeCalculate = estimateTimeCalculate(svrlPath,
				svrlAvailable, toolboxFile);
		notifyNextTask(monitor, messages.PlaZService_MonitorDisplay_Prep, 1, 3,
				estimateTimeCalculate);
		try (final InputStream transformToSvrl = PlaZSchema.class
				.getResourceAsStream(TRANSFORM_TO_SVRL);
				final PrintWriter htmlWriter = new PrintWriter(
						htmlLocation.toFile(), UTF_8.name())) {

			// Copy resources
			FileUtils.copyDirectory(new File(PLAZ_HTML_RESOURCES),
					new File(tmpDir.toString()));
			notifyNextTask(monitor,
					messages.PlaZService_MonitorDisplay_PerformPlaZCheck, 2, 3,
					estimateTimeCalculate
							- (System.currentTimeMillis() - startTime));
			if (Files.notExists(svrlPath) || !svrlAvailable) {
				// PP -> SVRL
				logger.info("transform PP -> SVRL"); //$NON-NLS-1$ LOG
				final String svrl = xsltService.transform(PlaZSchema.class,
						RESOURCE_BASE, TRANSFORM_TO_SVRL, modelLocation);

				try (final PrintWriter svrlOut = new PrintWriter(
						svrlPath.toFile(), UTF_8.name())) {
					svrlOut.print(svrl);
				}
			}

			notifyNextTask(monitor,
					messages.PlaZService_MonitorDisplay_GenerateHtml, 3, 3,
					estimateTimeCalculate
							- (System.currentTimeMillis() - startTime));
			// SVRL -> HTML
			logger.info("transform SVRL -> HTML"); //$NON-NLS-1$ LOG
			final String htmlResult = xsltService.transform(PlaZSchema.class,
					RESOURCE_BASE, TRANSFORM_TO_HTML, svrlPath,
					getParameters(schemaValid, toolboxFile));
			htmlWriter.print(htmlResult);
			if (monitor != null) {
				monitor.worked(1);
			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void createPdfSummary(final IProgressMonitor monitor,
			final Path tmpDir, final ToolboxFile toolboxFile,
			final Path pdfLocation, final boolean schemaValid,
			final boolean svrlAvailable,
			final OverwriteHandling overwriteHandling) {
		createPdfReport(monitor, tmpDir, toolboxFile.getModelPath(),
				getSummaryParameters(schemaValid, toolboxFile), pdfLocation,
				svrlAvailable, toolboxFile, overwriteHandling);
	}

	@Override
	public String getVersion() {
		final InputStream input = PlaZSchema.class
				.getResourceAsStream(RESOURCE_BASE + HIERARCHICAL_SVRL);
		try (final Scanner scanner = new Scanner(input)) {
			final String result = scanner
					.findWithinHorizon("name=\"dateOfDelivery\">[^<]+<", 0); //$NON-NLS-1$
			final String version = result.replaceFirst(".*>", "") //$NON-NLS-1$ //$NON-NLS-2$
					.replaceFirst("<", "");
			return version;
		}
	}

	@Override
	public boolean isHtmlReportAvailable(
			final PlanPro_Schnittstelle planProSchnittstelle) {
		final Cache cache = cacheService.getCache(ToolboxFileRole.SESSION,
				PlazConstants.PLAZ_CACHE_ID);
		if (planProSchnittstelle == null) {
			return false;
		}
		final Boolean available = cache.get(
				PlazConstants.HTML_REPORT_AVAILABLE_ID, () -> Boolean.FALSE);
		return available.booleanValue();
	}

	@Override
	public boolean isPdfReportAvailable(
			final PlanPro_Schnittstelle planProSchnittstelle) {
		final Cache cache = cacheService.getCache(ToolboxFileRole.SESSION,
				PlazConstants.PLAZ_CACHE_ID);
		if (planProSchnittstelle == null) {
			return false;
		}
		final Boolean available = cache.get(
				PlazConstants.PDF_REPORT_AVAILABLE_ID, () -> Boolean.FALSE);
		return available.booleanValue();
	}

	@Override
	public boolean isPdfSummaryAvailable(
			final PlanPro_Schnittstelle planProSchnittstelle) {
		final Cache cache = cacheService.getCache(ToolboxFileRole.SESSION,
				PlazConstants.PLAZ_CACHE_ID);
		if (planProSchnittstelle == null) {
			return false;
		}
		final Boolean available = cache.get(
				PlazConstants.PDF_SUMMARY_AVAILABLE_ID, () -> Boolean.FALSE);
		return available.booleanValue();
	}

	@Override
	public boolean isSvrlAvailable(
			final PlanPro_Schnittstelle planProSchnittstelle) {
		final Cache cache = cacheService.getCache(ToolboxFileRole.SESSION,
				PlazConstants.PLAZ_CACHE_ID);
		if (planProSchnittstelle == null) {
			return false;
		}
		final Boolean available = cache.get(PlazConstants.SVRL_AVAILABLE_ID,
				() -> Boolean.FALSE);
		return available.booleanValue();
	}

	@Override
	public void setHtmlReportAvailable(
			final PlanPro_Schnittstelle planProSchnittstelle,
			final boolean value) {
		final Cache cache = cacheService.getCache(ToolboxFileRole.SESSION,
				PlazConstants.PLAZ_CACHE_ID);
		if (planProSchnittstelle == null) {
			// caching not supported for invalid files
			return;
		}
		cache.set(PlazConstants.HTML_REPORT_AVAILABLE_ID,
				Boolean.valueOf(value));
	}

	@Override
	public void setPdfReportAvailable(
			final PlanPro_Schnittstelle planProSchnittstelle,
			final boolean value) {
		final Cache cache = cacheService.getCache(ToolboxFileRole.SESSION,
				PlazConstants.PLAZ_CACHE_ID);
		if (planProSchnittstelle == null) {
			// caching not supported for invalid files
			return;
		}
		cache.set(PlazConstants.PDF_REPORT_AVAILABLE_ID,
				Boolean.valueOf(value));
	}

	@Override
	public void setPdfSummaryAvailable(
			final PlanPro_Schnittstelle planProSchnittstelle,
			final boolean value) {
		final Cache cache = cacheService.getCache(ToolboxFileRole.SESSION,
				PlazConstants.PLAZ_CACHE_ID);
		if (planProSchnittstelle == null) {
			// caching not supported for invalid files
			return;
		}
		cache.set(PlazConstants.PDF_SUMMARY_AVAILABLE_ID,
				Boolean.valueOf(value));
	}

	@Override
	public void setSvrlFileAvailable(
			final PlanPro_Schnittstelle planProSchnittstelle,
			final boolean value) {
		final Cache cache = cacheService.getCache(ToolboxFileRole.SESSION,
				PlazConstants.PLAZ_CACHE_ID);
		if (planProSchnittstelle == null) {
			// caching not supported for invalid files
			return;
		}
		cache.set(PlazConstants.SVRL_AVAILABLE_ID, Boolean.valueOf(value));
	}

	@SuppressWarnings("boxing")
	private void createPdfReport(final IProgressMonitor monitor,
			final Path tmpDir, final Path modelLocation,
			final Map<String, String> parameters, final Path pdfLocation,
			final boolean svrlAvailable, final ToolboxFile toolboxFile,
			final OverwriteHandling overwriteHandling) {
		// check override
		try {
			if (!overwriteHandling.test(pdfLocation)) {
				return;
			}
		} catch (final NotWritable e) {
			throw new RuntimeException(e);
		}

		final Path svrlPath = Paths.get(tmpDir.toString(),
				modelLocation.getFileName().toString() + EXTENSION_SVRL);
		final long startTime = System.currentTimeMillis();
		final double estimateTimeCalculate = estimateTimeCalculate(svrlPath,
				svrlAvailable, toolboxFile);
		logger.info("Estimated Time: {}s",
				Math.round(estimateTimeCalculate / 1000));
		try (final FileOutputStream pdfOutputStream = new FileOutputStream(
				pdfLocation.toFile());
				final OutputStream pdfOut = new BufferedOutputStream(
						pdfOutputStream)) {

			if (Files.notExists(svrlPath) || !svrlAvailable) {

				notifyNextTask(monitor,
						messages.PlaZService_MonitorDisplay_PerformPlaZCheck, 1,
						3, estimateTimeCalculate);

				// PP -> SVRL
				logger.info("transform PP -> SVRL: {}", //$NON-NLS-1$
						svrlPath.toFile().getAbsolutePath());
				final String svrl = xsltService.transform(PlaZSchema.class,
						RESOURCE_BASE, TRANSFORM_TO_SVRL, modelLocation);

				try (final PrintWriter svrlOut = new PrintWriter(
						svrlPath.toFile(), UTF_8.name())) {
					svrlOut.print(svrl);
				}
			}
			final long current1 = System.currentTimeMillis();
			logger.info("Is Time PP -> SVRL: {}s",
					(current1 - startTime) / 1000);
			notifyNextTask(monitor,
					messages.PlaZService_MonitorDisplay_PreparePdf, 2, 3,
					estimateTimeCalculate - (current1 - startTime));

			// SVRL -> FOP
			logger.info("transform SVRL -> FOP"); //$NON-NLS-1$ LOG
			final String fopResult = xsltService.transform(PlaZSchema.class,
					RESOURCE_BASE, TRANSFORM_TO_FOP, svrlPath, parameters);

			// FOP -> PDF
			final long current2 = System.currentTimeMillis();
			notifyNextTask(monitor,
					messages.PlaZService_MonitorDisplay_GeneratePdf, 3, 3,
					estimateTimeCalculate - (current2 - startTime));
			final ByteArrayInputStream fopStream = new ByteArrayInputStream(
					fopResult.getBytes(UTF_8));
			final StreamSource fopSource = new StreamSource(fopStream);
			logger.info("Is Time SVRL -> FOP: {}s",
					(current2 - current1) / 1000);
			logger.info("transform FOP -> PDF"); //$NON-NLS-1$ LOG
			fopService.fop(OutputFormat.PDF, null, fopSource, pdfLocation,
					PdfAMode.NONE, overwriteHandling, resourceResolver);
			if (monitor != null) {
				monitor.worked(1);
			}
			logger.info("Is Time FOP -> PDF: {}s",
					(System.currentTimeMillis() - current2) / 1000);
		} catch (final SAXException | TransformerException | IOException e) {
			throw new RuntimeException(e);
		} catch (final UserAbortion e) {
			// do nothing
		}
	}

	private void notifyNextTask(final IProgressMonitor monitor,
			final String task, final int step, final int maxStep,
			final double estimateTime) {
		if (monitor != null) {
			final StringBuilder sb = new StringBuilder(task);
			sb.append(" ("); //$NON-NLS-1$
			sb.append(messages.PlaZService_MonitorDisplay_Step);
			sb.append(" "); //$NON-NLS-1$
			sb.append(Integer.toString(step));
			sb.append("/"); //$NON-NLS-1$
			sb.append(Integer.toString(maxStep));
			sb.append(")"); //$NON-NLS-1$
			sb.append(" - ");
			sb.append(messages.PlaZService_MonitorDisplay_Step_EstimateTime);
			sb.append(" ");
			sb.append(estimateTimeToString(estimateTime));
			monitor.worked(1);
			monitor.setTaskName(sb.toString());
		}
	}

	@Reference(cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.STATIC, unbind = "-")
	private void setXsltService(final XsltService xsltService) {
		this.xsltService = xsltService;
	}
}
