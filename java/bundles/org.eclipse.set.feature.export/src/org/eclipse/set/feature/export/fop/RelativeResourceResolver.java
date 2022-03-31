/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.fop;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Paths;

import org.apache.fop.apps.io.ResourceResolverFactory;
import org.apache.xmlgraphics.io.Resource;
import org.apache.xmlgraphics.io.ResourceResolver;

/**
 * Implementation of a {@link ResourceResolver} which handles relative URIs
 * 
 * @author Stuecker
 *
 */
public class RelativeResourceResolver implements ResourceResolver {
	private final ResourceResolver absoluteResolver;
	private final String basePath;

	/**
	 * @param absoluteResolver
	 *            the resolver to use for absolute URIs
	 * @param relativeBaseDir
	 *            the base directory for relative URIs
	 */
	public RelativeResourceResolver(final ResourceResolver absoluteResolver,
			final String relativeBaseDir) {
		this.absoluteResolver = absoluteResolver;
		this.basePath = relativeBaseDir;
	}

	/**
	 * @param relativeBaseDir
	 *            the base directory for relative URIs
	 */
	public RelativeResourceResolver(final String relativeBaseDir) {
		this(ResourceResolverFactory.createDefaultResourceResolver(),
				relativeBaseDir);
	}

	@Override
	public Resource getResource(final URI uri) throws IOException {
		if (!uri.isAbsolute()) {
			final URI absoluteURI = Paths.get(basePath, uri.toString())
					.toAbsolutePath().toUri();
			return absoluteResolver.getResource(absoluteURI);
		}
		return absoluteResolver.getResource(uri);
	}

	@Override
	public OutputStream getOutputStream(final URI uri) throws IOException {
		if (!uri.isAbsolute()) {
			final URI absoluteURI = Paths.get(basePath, uri.toString())
					.toAbsolutePath().toUri();
			return absoluteResolver.getOutputStream(absoluteURI);
		}
		return absoluteResolver.getOutputStream(uri);
	}
}
