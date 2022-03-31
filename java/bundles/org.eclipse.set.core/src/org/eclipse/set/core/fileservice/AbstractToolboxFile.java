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
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.ppmodel.extensions.PlanProResourceImplExtensions;
import org.w3c.dom.Document;

import de.scheidtbachmann.planpro.model.model1902.PlanPro.util.PlanProResourceImpl;

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
		if (resource instanceof PlanProResourceImpl) {
			PlanProResourceImplExtensions
					.setStandalone((PlanProResourceImpl) resource, NO);
		}
	}

	protected void loadResource(final Path path,
			final EditingDomain editingDomain) throws IOException {
		final ResourceSet resourceSet = editingDomain.getResourceSet();
		final URI resourceUri = URI.createFileURI(path.toString());
		XMLResource newResource = (XMLResource) resourceSet
				.getResource(resourceUri, false);
		if (newResource == null) {
			// Load the resource
			newResource = (XMLResource) resourceSet.createResource(resourceUri);
			// Allow ppxml files with unknown features to be loaded
			// by ignoring wrapped FeatureNotFoundExceptions
			try {
				newResource.load(resourceSet.getLoadOptions());
			} catch (final Resource.IOWrappedException e) {
				if (!(e.getCause() instanceof FeatureNotFoundException)) {
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
			final XMLResource newResource = (XMLResource) getEditingDomain()
					.getResourceSet()
					.createResource(URI.createFileURI(path.toString()));
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

}
