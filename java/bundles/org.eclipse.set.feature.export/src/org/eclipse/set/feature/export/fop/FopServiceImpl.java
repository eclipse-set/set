/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.fop;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.apps.io.ResourceResolverFactory;
import org.apache.fop.configuration.ConfigurationException;
import org.apache.fop.image.loader.batik.ImageLoaderFactorySVG;
import org.apache.fop.image.loader.batik.PreloaderSVG;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;
import org.eclipse.set.basis.OverwriteHandling;
import org.eclipse.set.basis.exceptions.UserAbortion;
import org.eclipse.set.core.services.font.FontService;
import org.eclipse.set.services.fop.FopService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.xml.sax.SAXException;

/**
 * Implementation for {@link FopService}.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component
public class FopServiceImpl implements FopService {
	private static final String DATA_DIR = "data"; //$NON-NLS-1$
	private FopFactory fopFactory;
	private TransformerFactory transformerFactory;

	@Reference
	FontService fontService;

	/**
	 * Resource resolvers follow the following order:
	 * 
	 * 1) If a custom resource resolver is passed to fop(), that resolver is
	 * used
	 *
	 * 2) If the URI is absolute, the default resource resolver is used
	 * 
	 * 3) If the URI is relative, it is transformed into an absolute URI based
	 * on DATA_DIR
	 */

	/**
	 * Default resolver to use if no resolver is set by the caller
	 */
	private final ResourceResolver defaultResourceResolver = new RelativeResourceResolver(
			DATA_DIR);

	/**
	 * A resource resolver proxy, which defers the actual resource resolver to
	 * the resourceResolver member
	 */
	private ResourceResolver resourceResolver = defaultResourceResolver;
	private final ResourceResolver proxyResourceResolver = new ResourceResolver() {
		@Override
		public Resource getResource(final URI uri) throws IOException {
			return resourceResolver.getResource(uri);
		}

		@Override
		public OutputStream getOutputStream(final URI uri) throws IOException {
			return resourceResolver.getOutputStream(uri);
		}
	};

	/**
	 * Activate the service.
	 * 
	 * @throws IOException
	 *             an IO exception occurred
	 * @throws ConfigurationException
	 *             a configuration exception occurred
	 * @throws SAXException
	 *             a SAX exception occurred
	 * @throws URISyntaxException
	 *             the {@link URISyntaxException}
	 */
	@Activate
	public void activate() throws IOException, SAXException,
			ConfigurationException, URISyntaxException {
		final FopFactoryBuilder fopFactoryBuilder = new FopFactoryBuilder(
				new File(".").toURI(), proxyResourceResolver); //$NON-NLS-1$
		fopFactoryBuilder.setHyphenBaseResourceResolver(
				ResourceResolverFactory.createDefaultInternalResourceResolver(
						FopServiceImpl.class.getClassLoader()
								.getResource("hyph") //$NON-NLS-1$
								.toURI()));
		fopFactoryBuilder.getImageManager()
				.getRegistry()
				.registerPreloader(new PreloaderSVG());
		fopFactoryBuilder.getImageManager()
				.getRegistry()
				.registerLoaderFactory(new ImageLoaderFactorySVG());
		fopFactory = fopFactoryBuilder.build();
		fopFactory.getRendererFactory()
				.addDocumentHandlerMaker(
						new FopPDFDocumentHandlerMaker(fontService));
		transformerFactory = TransformerFactory.newInstance();
	}

	@Override
	public void fop(final OutputFormat outputFormat, final Source stylesheet,
			final Source xmlData, final Path outputPath,
			final PdfAMode pdfAMode, final OverwriteHandling overwriteHandling,
			final ResourceResolver userResourceResolver) throws FOPException,
			IOException, TransformerException, UserAbortion {
		// check overwrite
		if (!overwriteHandling.test(outputPath)) {
			throw new UserAbortion();
		}

		try (final FileOutputStream outputStream = new FileOutputStream(
				outputPath.toFile());
				final OutputStream bufferedOut = new BufferedOutputStream(
						outputStream)) {
			final FOUserAgent userAgent = fopFactory.newFOUserAgent();
			if (pdfAMode != PdfAMode.NONE) {
				@SuppressWarnings("unchecked")
				final Map<String, String> rendererOptions = userAgent
						.getRendererOptions();
				rendererOptions.put("pdf-a-mode", //$NON-NLS-1$
						pdfAMode.getModeString());
			}
			if (outputFormat == OutputFormat.IMAGE) {
				@SuppressWarnings("unchecked")
				final Map<String, String> rendererOptions = userAgent
						.getRendererOptions();
				rendererOptions.put("dpi", "600"); //$NON-NLS-1$ //$NON-NLS-2$
			}
			final Fop fop = fopFactory.newFop(outputFormat.getFormatString(),
					userAgent, bufferedOut);
			final SAXResult saxResult = new SAXResult(fop.getDefaultHandler());

			// Set resource resolver if present
			if (userResourceResolver != null) {
				this.resourceResolver = userResourceResolver;
			}
			if (stylesheet == null) {
				transformerFactory.newTransformer()
						.transform(xmlData, saxResult);
			} else {
				transformerFactory.newTransformer(stylesheet)
						.transform(xmlData, saxResult);
			}
			// Restore resource resolver to default
			this.resourceResolver = defaultResourceResolver;
		}
	}

	/**
	 * @return the base dir
	 */
	@Override
	public String getBaseDir() {
		return DATA_DIR;
	}

}
