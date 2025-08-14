/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.fileservice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.PlanProFileResource;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.model.planpro.PlanPro.DocumentRoot;
import org.eclipse.set.model.planpro.PlanPro.util.PlanProResourceImpl;

/**
 * Toolbox file support for plain files.
 * 
 * @author Schaefer
 */
public class PlainToolboxFile extends AbstractToolboxFile {

	private Path commonPath;
	private final EditingDomain editingDomain;
	private static final String TECHNICAL_RESOURCE_TYPE_NAME = "content"; //$NON-NLS-1$
	private final Format format;
	private boolean loadable;
	private final SessionService sessionService;

	private final ToolboxFileRole role;

	/**
	 * @param toolboxFile
	 *            the original toolbox file
	 */
	public PlainToolboxFile(final PlainToolboxFile toolboxFile) {
		this.sessionService = toolboxFile.sessionService;
		this.commonPath = toolboxFile.commonPath;
		this.format = toolboxFile.format;
		this.editingDomain = toolboxFile.editingDomain;
		addResource(PathExtensions.getBaseFileName(toolboxFile.getPath()),
				toolboxFile.getPlanProResource());
		this.loadable = false;
		this.role = toolboxFile.getRole();

	}

	// Initialize toolbox file and create resource. The content is not loaded
	// here. Use {@link #load()} to explicitly load the resource.
	PlainToolboxFile(final SessionService sessionService, final Path path,
			final Format format, final EditingDomain editingDomain,
			final boolean loadable, final ToolboxFileRole role) {
		this.sessionService = sessionService;
		this.commonPath = path;
		this.format = format;
		this.editingDomain = editingDomain;
		this.loadable = loadable;
		this.role = role;
	}

	@Override
	public void copyAllMedia(final ToolboxFile toolboxfile) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void copyMedia(final ToolboxFile toolboxfile, final String id)
			throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void createMedia(final Guid guid, final byte[] data) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(final boolean close) throws IOException {
		Files.delete(commonPath);
	}

	@Override
	public void deleteMedia(final Guid guid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Path getMediaPath(final Guid guid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<String> getAllMedia() throws IOException {
		return new ArrayList<>();
	}

	@Override
	public EditingDomain getEditingDomain() {
		return editingDomain;
	}

	@Override
	public Format getFormat() {
		return format;
	}

	@Override
	public byte[] getMedia(final Guid guid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Path getModelPath() {
		return commonPath;
	}

	@Override
	public Path getPath() {
		return commonPath;
	}

	@Override
	public boolean hasDetachedAttachments() {
		return false;
	}

	@Override
	public boolean hasMedia(final String id) {
		return false;
	}

	@Override
	public boolean isLoadable() {
		return loadable;
	}

	@Override
	public void openModel() throws IOException {
		if (isLoadable()) {
			generateMD5CheckSum();
			loadResource(getModelPath(), editingDomain,
					Services.getPlanProVersionService());
			final DocumentRoot doc = (org.eclipse.set.model.planpro.PlanPro.DocumentRoot) getPlanProResource()
					.getContents()
					.getFirst();
			ToolboxIDResolver
					.resolveIDReferences(doc.getPlanProSchnittstelle());
		} else {
			throw new IllegalStateException("Toolbox file not loadable."); //$NON-NLS-1$
		}
	}

	@Override
	public void saveResource(final XMLResource resource) throws IOException {
		if (resource instanceof PlanProResourceImpl
				&& resource.getURI().isFile()) {
			resource.save(null);
			editingDomain.getCommandStack().flush();
			loadable = true;
		} else {
			throw new IllegalStateException(
					Optional.ofNullable(resource.getURI())
							.map(u -> "Illegal uri " + u.toString()) //$NON-NLS-1$
							.orElse("no uri")); //$NON-NLS-1$
		}
	}

	@Override
	public void setPath(final Path path) {
		commonPath = path;
		setResourcePath(getPlanProResource(), path);
	}

	@Override
	public void setRole(final ToolboxFileRole role) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setTemporaryDirectory(final Path path) {
		// plain toolbox files do not need a temporary directory
	}

	@Override
	protected String getContentType(final Path modelPath) {
		return modelPath == null ? TECHNICAL_RESOURCE_TYPE_NAME
				: PathExtensions.getBaseFileName(modelPath);
	}

	@Override
	public PlanProFileResource getPlanProResource() {
		// Plain file contains only technical resource
		final Iterator<PlanProFileResource> resourcesIte = resources.values()
				.iterator();
		return resourcesIte.hasNext() ? resourcesIte.next() : null;
	}

	@Override
	public PlanProFileResource getLayoutResource() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Path getLayoutPath() {
		return null;
	}

	@Override
	public void openLayout() throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ToolboxFileRole getRole() {
		return role;
	}
}
