/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.fileservice;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;

import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.FeatureNotFoundException;
import org.eclipse.emf.ecore.xmi.IllegalValueException;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.model.model11001.PlanPro.DocumentRoot;
import org.eclipse.set.model.model11001.PlanPro.util.PlanProResourceImpl;
import org.eclipse.set.ppmodel.extensions.PlanProResourceImplExtensions;
import org.w3c.dom.Document;

/**
 * Common functionality for toolbox files.
 * 
 * @author Schaefer
 */
public abstract class AbstractToolboxFile implements ToolboxFile {
	private static final String NO = "no"; //$NON-NLS-1$

	protected static final String ENCODING = StandardCharsets.UTF_8.name();

	private final HashMap<String, Document> domDocument = new HashMap<>();

	private String md5checksum = null;

	private HashMap<String, XMLResource> resources = new HashMap<>();

	@Override
	public void setXMLDocument(final String docName, final Document doc) {
		domDocument.put(docName, doc);
	}

	@Override
	public Document getXMLDocument(final String docName) {
		return domDocument.get(docName);
	}

	@Override
	public String getChecksum() {
		return md5checksum;
	}

	protected void generateMD5CheckSum() throws IOException {
		try (final FileInputStream input = new FileInputStream(
				this.getPath().toFile())) {
			this.md5checksum = DigestUtils.md5Hex(input).toUpperCase();
		}

	}

	private void modifyXmlDeclaration() {
		if (getPlanProResource() instanceof final PlanProResourceImpl planproResource) {
			PlanProResourceImplExtensions.setStandalone(planproResource, NO);
		}
	}

	protected void loadResource(final Path path,
			final EditingDomain editingDomain) throws IOException {
		final ResourceSet resourceSet = editingDomain.getResourceSet();

		// Allow file extesion with Uppercase
		final URI resourceUri = URI.createFileURI(
				PathExtensions.toLowerCaseExtension(path).toString());
		XMLResource newResource = (XMLResource) resourceSet
				.getResource(resourceUri, false);
		if (newResource == null) {
			// Use the file extension as content type
			// This may not be the same as the extension of path if path is a
			// temporary file (e.g. for zipped planpro files)
			final String contentType = PathExtensions.getExtension(getPath());
			// Load the resource
			newResource = (XMLResource) resourceSet.createResource(resourceUri,
					contentType);
			// Allow ppxml files with unknown features to be loaded
			// by ignoring wrapped FeatureNotFoundExceptions
			try {
				newResource.load(resourceSet.getLoadOptions());
			} catch (final Resource.IOWrappedException e) {
				if (!(e.getCause() instanceof FeatureNotFoundException
						|| e.getCause() instanceof IllegalValueException)) {
					throw e;
				}
			}
		}
		addResource(getContentType(path), newResource);

	}

	protected void addResource(final String contentName,
			final XMLResource resource) {
		if (contentName == null || resource == null) {
			return;
		}
		this.resources.put(contentName, resource);
		modifyXmlDeclaration();
	}

	protected XMLResource getResource(final String contentName) {
		return resources.get(contentName);
	}

	protected void setResourcePath(final Path path) {
		final HashMap<String, XMLResource> newResources = new HashMap<>();
		resources.forEach((contentName, resource) -> {
			if (!resource.getURI().isFile()) {
				// Use the file extension as content type
				// This may not be the same as the extension of path if path is
				// a
				// temporary file (e.g. for zipped planpro files)
				final String contentType = PathExtensions
						.getExtension(getPath());

				final XMLResource newResource = (XMLResource) getEditingDomain()
						.getResourceSet()
						.createResource(URI.createFileURI(path.toString()),
								contentType);
				newResource.setEncoding(ENCODING);
				if (!resource.getContents().isEmpty()) {
					newResource.getContents().addAll(resource.getContents());
				}
				newResources.put(contentName, newResource);
				modifyXmlDeclaration();
			} else {
				resource.setURI(URI.createFileURI(path.toString()));
			}
		});
		resources = newResources;
	}

	@Override
	public void save() throws IOException {
		this.saveResource(getPlanProResource());
	}

	@Override
	public void close() throws IOException {
		getEditingDomain().getResourceSet().getResources().clear();
		resources.values().forEach(resource -> resource.getContents().clear());
	}

	@Override
	public DocumentRoot getPlanProSourceModel() {
		final XMLResource planProResource = getPlanProResource();
		if (planProResource == null) {
			return null;
		}
		return ((org.eclipse.set.toolboxmodel.PlanPro.util.PlanProResourceImpl) planProResource)
				.getSourceModel();
	}

	@Override
	public org.eclipse.set.model.model11001.Layoutinformationen.DocumentRoot getLayoutSourceModel() {
		try {
			final XMLResource layoutResource = getLayoutResource();
			if (layoutResource == null) {
				return null;
			}
			return ((org.eclipse.set.toolboxmodel.PlanPro.util.PlanProResourceImpl) layoutResource)
					.getLayoutModel();
		} catch (final UnsupportedOperationException e) {
			return null;
		}

	}

	/**
	 * Saves the resource
	 * 
	 * @throws IOException
	 */
	protected abstract void saveResource(XMLResource resource)
			throws IOException;

	protected abstract String getContentType(Path modelPath);
}
