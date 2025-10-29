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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.FeatureNotFoundException;
import org.eclipse.emf.ecore.xmi.IllegalValueException;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.PlanProXMLNode;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ExtendedPlanProValidator;
import org.eclipse.set.basis.files.PlanProFileResource;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.core.services.version.PlanProVersionService;
import org.eclipse.set.model.planpro.PlanPro.DocumentRoot;

/**
 * Common functionality for toolbox files.
 * 
 * @author Schaefer
 */
public abstract class AbstractToolboxFile implements ToolboxFile {
	private static final String NO = "no"; //$NON-NLS-1$

	protected static final String ENCODING = StandardCharsets.UTF_8.name();

	private HashMap<String, PlanProXMLNode> domDocument = new HashMap<>();

	private String md5checksum = null;

	protected final HashMap<String, PlanProFileResource> resources = new HashMap<>();

	@Override
	public void setXMLDocument(final String docName, final PlanProXMLNode doc) {
		domDocument.put(docName, doc);
	}

	@Override
	public PlanProXMLNode getXMLDocument(final String docName) {
		return domDocument.get(docName);
	}

	@Override
	public void clearXMLDocument() {
		domDocument = new HashMap<>();
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
		getPlanProResource().setStandalone(NO);
	}

	protected void loadResource(final Path path,
			final EditingDomain editingDomain,
			final PlanProVersionService versionService) throws IOException {
		ExtendedPlanProValidator.registerValidator();
		final ResourceSet resourceSet = editingDomain.getResourceSet();
		// Allow file extesion with Uppercase
		final URI resourceUri = URI.createFileURI(
				PathExtensions.toLowerCaseExtension(path).toString());
		PlanProFileResource newResource = (PlanProFileResource) resourceSet
				.getResource(resourceUri, false);
		if (newResource == null) {
			// Load the resource
			newResource = new PlanProFileResource(resourceUri,
					new PlanProXMLHelper(newResource, versionService));
			resourceSet.getResources().add(newResource);
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
			final PlanProFileResource resource) {
		if (contentName == null || resource == null) {
			return;
		}
		this.resources.put(contentName, resource);
		modifyXmlDeclaration();
	}

	protected PlanProFileResource getResource(final String contentName) {
		return resources.get(contentName);
	}

	protected void setResource(final String contentName,
			final PlanProFileResource resource) {
		resources.put(contentName, resource);
		modifyXmlDeclaration();
	}

	@Override
	public void setResourcePath(final Resource resource, final Path path) {
		if (!resource.getURI().isFile()) {
			final PlanProFileResource newResource = new PlanProFileResource(
					URI.createFileURI(path.toString()));
			getEditingDomain().getResourceSet().getResources().add(newResource);
			newResource.setEncoding(ENCODING);
			if (!resource.getContents().isEmpty()) {
				newResource.getContents().addAll(resource.getContents());
			}
			resources.put(getContentType(path), newResource);
			modifyXmlDeclaration();
		} else {
			resource.setURI(URI.createFileURI(path.toString()));
		}
	}

	@Override
	public void save() throws IOException {
		this.saveResource(getPlanProResource());
	}

	@Override
	public void close() throws IOException {
		getEditingDomain().getCommandStack().flush();
		getEditingDomain().getResourceSet().getResources().clear();
		resources.values().forEach(resource -> resource.getContents().clear());
		resources.clear();
		clearXMLDocument();
	}

	@Override
	public DocumentRoot getPlanProDocumentRoot() {
		final PlanProFileResource planProResource = getPlanProResource();
		if (planProResource == null) {
			return null;
		}
		final EList<EObject> contents = getPlanProResource().getContents();
		if (contents.isEmpty()) {
			return null;
		}
		return (org.eclipse.set.model.planpro.PlanPro.DocumentRoot) contents
				.getFirst();
	}

	@Override
	public org.eclipse.set.model.planpro.Layoutinformationen.DocumentRoot getLayoutDocumentRoot() {
		try {
			final PlanProFileResource layoutResource = getLayoutResource();
			if (layoutResource == null) {
				return null;
			}
			final EList<EObject> contents = getLayoutResource().getContents();
			return contents.isEmpty() ? null
					: (org.eclipse.set.model.planpro.Layoutinformationen.DocumentRoot) contents
							.getFirst();
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
