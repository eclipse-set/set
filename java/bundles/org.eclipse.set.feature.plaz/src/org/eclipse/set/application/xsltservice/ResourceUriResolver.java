/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.xsltservice;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.set.basis.extensions.SourceExtensions;

/**
 * An {@link URIResolver} using a given class context and resource base
 * directory.
 * 
 * @author Schaefer
 *
 * @param <T>
 *            type for the class context
 */
public class ResourceUriResolver<T> implements URIResolver {

	private final Class<T> classContext;

	private final String resourceBase;

	/**
	 * @param classContext
	 *            the class context
	 * @param resourceBase
	 *            the resource base directory
	 */
	public ResourceUriResolver(final Class<T> classContext,
			final String resourceBase) {
		this.classContext = classContext;
		this.resourceBase = resourceBase;
	}

	@Override
	public Source resolve(final String href, final String base)
			throws TransformerException {
		try {
			if (href.startsWith("file:")) { //$NON-NLS-1$
				return new StreamSource(new File(new URI(href)));
			}
			final String location = resourceBase + href;
			return SourceExtensions.getResourceAsSource(classContext, location);
		} catch (final URISyntaxException e) {
			throw new TransformerException(e);
		}
	}
}