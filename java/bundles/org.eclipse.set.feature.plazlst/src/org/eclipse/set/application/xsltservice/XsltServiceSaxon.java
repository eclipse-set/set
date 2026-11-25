/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.xsltservice;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Map;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.set.basis.exceptions.XsltServiceException;
import org.eclipse.set.basis.exceptions.XsltTransformationAbortedException;
import org.eclipse.set.services.xslt.XsltService;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.saxon.TransformerFactoryImpl;
import net.sf.saxon.jaxp.SaxonTransformerFactory;
import net.sf.saxon.lib.StandardErrorListener;
import net.sf.saxon.trace.TraceCodeInjector;

/**
 * Dies ist eine Saxon-Implementation des {@link XsltService XSLT-Dienstes}.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(immediate = true)
public class XsltServiceSaxon implements XsltService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(XsltServiceSaxon.class);

	@Override
	public <T> String transform(final Class<T> classContext,
			final String resourceBase, final String transformation,
			final Path source) {
		try {
			final URIResolver uriResolver = new ResourceUriResolver<>(
					classContext, resourceBase);
			final Source xsltTransformation = uriResolver
					.resolve(transformation, null);
			final Source xmlSource = new StreamSource(source.toFile());
			return transform(xsltTransformation, xmlSource, null, uriResolver);
		} catch (final TransformerException e) {
			throw new XsltServiceException(e, transformation, source.toString(),
					null, XsltServiceSaxon::getExpandedMessage);
		}
	}

	@Override
	public <T> String transform(final Class<T> classContext,
			final String resourceBase, final String transformation,
			final Path source, final Map<String, String> parameters) {
		try {
			final URIResolver uriResolver = new ResourceUriResolver<>(
					classContext, resourceBase);
			final Source xsltTransformation = uriResolver
					.resolve(transformation, null);
			final Source xmlSource = new StreamSource(source.toFile());
			return transform(xsltTransformation, xmlSource, parameters,
					uriResolver);
		} catch (final TransformerException e) {
			throw new XsltServiceException(e, transformation, source.toString(),
					parameters, XsltServiceSaxon::getExpandedMessage);
		}
	}

	@Override
	public String transform(final InputStream transformation,
			final InputStream source) {
		try {
			final StreamSource transformationStreamSource = new StreamSource(
					transformation);
			final StreamSource sourceStreamSource = new StreamSource(source);
			return transform(transformationStreamSource, sourceStreamSource,
					null, null);
		} catch (final TransformerException e) {
			throw new XsltServiceException(e, null, null, null,
					XsltServiceSaxon::getExpandedMessage);
		}
	}

	@Override
	public String transform(final InputStream transformation,
			final Path source) {
		try {
			final StreamSource transformationStreamSource = new StreamSource(
					transformation);
			final Source xmlSource = new StreamSource(source.toFile());
			return transform(transformationStreamSource, xmlSource, null, null);
		} catch (final TransformerException e) {
			throw new XsltServiceException(e, null, source.toString(), null,
					XsltServiceSaxon::getExpandedMessage);
		}
	}

	@Override
	public String transform(final InputStream transformation, final Path source,
			final Map<String, String> parameters) {
		try {
			final StreamSource transformationStreamSource = new StreamSource(
					transformation);
			final Source xmlSource = new StreamSource(source.toFile());
			return transform(transformationStreamSource, xmlSource, parameters,
					null);
		} catch (final TransformerException e) {
			throw new XsltServiceException(e, null, source.toString(),
					parameters, XsltServiceSaxon::getExpandedMessage);
		}
	}

	@Override
	public String transform(final Path transformation, final Path source) {
		try {
			final Source xsltTransformation = new StreamSource(
					transformation.toFile());
			final Source xmlSource = new StreamSource(source.toFile());
			return transform(xsltTransformation, xmlSource, null, null);
		} catch (final TransformerException e) {
			throw new XsltServiceException(e, transformation.toString(),
					source.toString(), null,
					XsltServiceSaxon::getExpandedMessage);
		}
	}

	private static String transform(final Source transformation,
			final Source source, final Map<String, String> parameters,
			final URIResolver uriResolver) throws TransformerException {
		final SaxonTransformerFactory factory = createFactory(uriResolver);
		final Transformer transformer = createTransformer(factory,
				transformation, parameters);
		return transform(transformer, source);
	}

	private static String transform(final Transformer transformer,
			final Source source) throws TransformerException {
		final Writer writer = new StringWriter();
		final Result outputTarget = new StreamResult(writer);
		try {
			transformer.transform(source, outputTarget);
		} catch (final RuntimeException e) {
			handle(e);
		}
		return writer.toString();
	}

	private static void handle(final RuntimeException e) {
		// In some cases the abortion-by-exception via the
		// ThreadInterruptionTraceListener above causes an
		// inconsistent state within the Saxon template
		// evaluator, which then throws a separate RuntimeException.
		//
		// As a workaround, throw a new XsltTransformationAbortedException
		// if a RuntimeException was thrown after this thread was
		// interrupted
		if (Thread.currentThread().isInterrupted()) {
			throw new XsltTransformationAbortedException();
		}
		// Otherwise re-throw the RuntimeException
		throw e;
	}

	private static SaxonTransformerFactory createFactory(
			final URIResolver uriResolver) {
		final SaxonTransformerFactory factory = new TransformerFactoryImpl();

		// log messages are not subject to internationalization
		LOGGER.info("Using XSLT-Factory {}", //$NON-NLS-1$
				factory.getClass().getName());

		setupTraceListener(factory);
		setupUriResolver(uriResolver, factory);

		return factory;
	}

	private static void setupTraceListener(
			final SaxonTransformerFactory factory) {
		factory.getConfiguration().getDefaultXsltCompilerInfo()
				.setCodeInjector(new TraceCodeInjector());
		factory.getConfiguration()
				.setTraceListener(new ThreadInterruptionTraceListener());
	}

	private static void setupUriResolver(final URIResolver uriResolver,
			final SaxonTransformerFactory factory) {
		if (uriResolver != null) {
			factory.setURIResolver(uriResolver);
		}
	}

	private static Transformer createTransformer(
			final SaxonTransformerFactory factory, final Source transformation,
			final Map<String, String> parameters)
			throws TransformerConfigurationException {
		final Transformer transformer = factory.newTransformer(transformation);
		setParameters(parameters, transformer);
		return transformer;
	}

	private static void setParameters(final Map<String, String> parameters,
			final Transformer transformer) {
		if (parameters != null) {
			for (final Map.Entry<String, String> entry : parameters
					.entrySet()) {
				transformer.setParameter(entry.getKey(), entry.getValue());
			}
		}
	}

	private static String getExpandedMessage(final XsltServiceException e) {
		final StandardErrorListener l = new StandardErrorListener();
		final TransformerException te = (TransformerException) e.getCause();
		return String.format("%s%n%s%n%s", e.getMessage(), //$NON-NLS-1$
				l.getLocationMessage(te), l.getExpandedMessage(te));
	}
}
