/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.core.runtime.Assert;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.extensions.SourceExtensions;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

/**
 * A directory with PlanPro schemas.
 * 
 * @author Schaefer
 */
public class PlanProSchemaDir {

	private static class LSInputImpl implements LSInput {

		private final String baseURI;
		private final String publicId;
		private final String systemId;
		private final String version;
		private final Bundle bundle = FrameworkUtil
				.getBundle(PlanPro_Schnittstelle.class);

		LSInputImpl(final String publicId, final String systemId,
				final String baseURI, final String version) {
			this.publicId = publicId;
			this.systemId = systemId;
			this.baseURI = baseURI;
			this.version = version;
		}

		@Override
		public String getBaseURI() {
			return baseURI;
		}

		@Override
		public InputStream getByteStream() {
			return PlanPro_Schnittstelle.class
					.getResourceAsStream(getSchemaPath());
		}

		@Override
		public boolean getCertifiedText() {
			return false;
		}

		@Override
		public Reader getCharacterStream() {
			return null;
		}

		@Override
		public String getEncoding() {
			return null;
		}

		@Override
		public String getPublicId() {
			return publicId;
		}

		@Override
		public String getStringData() {
			return null;
		}

		@Override
		public String getSystemId() {
			return systemId;
		}

		@Override
		public void setBaseURI(final String baseURI) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setByteStream(final InputStream byteStream) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setCertifiedText(final boolean certifiedText) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setCharacterStream(final Reader characterStream) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setEncoding(final String encoding) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setPublicId(final String publicId) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setStringData(final String stringData) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setSystemId(final String systemId) {
			throw new UnsupportedOperationException();
		}

		private String getSchemaDir() {
			return String.format("%s/%s", SCHEMA_DIR, version); //$NON-NLS-1$
		}

		private String getSchemaPath() {
			final Enumeration<URL> entries = bundle.findEntries(getSchemaDir(),
					getSystemId(), true);
			final URL element = entries.nextElement();
			Assert.isTrue(!entries.hasMoreElements());
			return element.getFile();
		}

	}

	private static class ResourceResolver implements LSResourceResolver {

		public ResourceResolver() {
			// avoid synthetic access
		}

		@Override
		public LSInput resolveResource(final String type,
				final String namespaceURI, final String publicId,
				final String systemId, final String baseURI) {
			final String version = namespaceURI
					.substring(namespaceURI.lastIndexOf("/") + 1); //$NON-NLS-1$
			return new LSInputImpl(publicId, systemId, baseURI, version);
		}
	}

	private static final String PLANPRO_SCHEMA_NAME = "PlanPro.xsd"; //$NON-NLS-1$

	private static final String SCHEMA_DIR = "schema"; //$NON-NLS-1$

	private static final String SCHEMA_PATTERN = "*.xsd"; //$NON-NLS-1$

	private static final String SEPARATOR = "/"; //$NON-NLS-1$

	private static boolean isPlanProPath(final Path path) {
		return path.getFileName()
				.toString()
				.equalsIgnoreCase(PLANPRO_SCHEMA_NAME);
	}

	/**
	 * @return the optional PlanPro schema path
	 */
	public static Optional<Path> getPlanProSchemaPath() {
		return getSchemaPaths().stream()
				.filter(PlanProSchemaDir::isPlanProPath)
				.findFirst();
	}

	/**
	 * @return resource resolver for schemas of this directory
	 */
	public static LSResourceResolver getResourceResolver() {
		return new ResourceResolver();
	}

	/**
	 * @return the schema paths
	 */
	public static List<Path> getSchemaPaths() {
		final List<Path> result = new LinkedList<>();
		final Bundle bundle = FrameworkUtil
				.getBundle(PlanPro_Schnittstelle.class);
		final Enumeration<URL> entries = bundle.findEntries(SCHEMA_DIR,
				SCHEMA_PATTERN, true);
		while (entries.hasMoreElements()) {
			final URL url = entries.nextElement();
			result.add(Paths.get(url.getFile()));
		}
		return result;
	}

	/**
	 * @return the schemas of this directory
	 */
	public static Source[] getSchemas() {
		final List<Source> result = new LinkedList<>();
		for (final Path schemaPath : getSchemaPaths()) {
			final String pathString = PathExtensions.toString(schemaPath,
					SEPARATOR);
			final StreamSource source = SourceExtensions.getResourceAsSource(
					PlanPro_Schnittstelle.class, pathString);
			result.add(source);
		}
		return result.toArray(new Source[0]);
	}
}
