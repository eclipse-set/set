/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.fileservice;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

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

	private Document domDocument = null;

	// IMPROVE: this Resource and Resource of EditingDomain have same content.
	// Should we this Resouce here remove ?
	private XMLResource resource;

	@Override
	public XMLResource getResource() {
		return resource;
	}

	@Override
	public void setXMLDocument(final Document doc) {
		domDocument = doc;
	}

	@Override
	public Document getXMLDocument() {
		return domDocument;
	}

	private void modifyXmlDeclaration() {
		if (resource instanceof final PlanProResourceImpl planproResource) {
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

		setResource(newResource);
	}

	protected void setResource(final XMLResource resource) {
		this.resource = resource;
		modifyXmlDeclaration();
	}

	protected void setResourcePath(final Path path) {
		if (!resource.getURI().isFile()) {
			// Use the file extension as content type
			// This may not be the same as the extension of path if path is a
			// temporary file (e.g. for zipped planpro files)
			final String contentType = PathExtensions.getExtension(getPath());

			final XMLResource newResource = (XMLResource) getEditingDomain()
					.getResourceSet().createResource(
							URI.createFileURI(path.toString()), contentType);
			newResource.setEncoding(ENCODING);
			if (!resource.getContents().isEmpty()) {
				newResource.getContents().addAll(resource.getContents());
			}
			resource = newResource;
			modifyXmlDeclaration();
		} else {
			resource.setURI(URI.createFileURI(path.toString()));
		}
	}

	@Override
	public void save() throws IOException {
		saveResource();
	}

	@Override
	public DocumentRoot getSourceModel() {
		if (getResource() instanceof final org.eclipse.set.toolboxmodel.PlanPro.util.PlanProResourceImpl ppresource) {
			return ppresource.getSourceModel();
		}
		return null;
	}

	/**
	 * Saves the resource
	 * 
	 * @throws IOException
	 */
	protected abstract void saveResource() throws IOException;
}
