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
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.basis.guid.Guid;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.toolboxmodel.PlanPro.util.PlanProResourceFactoryImpl;

/**
 * Toolbox file support for plain files.
 * 
 * @author Schaefer
 */
public class PlainToolboxFile extends AbstractToolboxFile {

	private Path commonPath;
	private final EditingDomain editingDomain;
	private final Format format;
	private boolean loadable;
	private final SessionService sessionService;

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

	}

	// Initialize toolbox file and create resource
	PlainToolboxFile(final SessionService sessionService, final Format format,
			final EditingDomain editingDomain) {
		this.sessionService = sessionService;
		this.commonPath = null;
		this.format = format;
		this.editingDomain = editingDomain;
		this.loadable = false;
	}

	// Initialize toolbox file and create resource. The content is not loaded
	// here. Use {@link #load()} to explicitly load the resource.
	PlainToolboxFile(final SessionService sessionService, final Path path,
			final Format format, final EditingDomain editingDomain,
			final boolean loadable) {
		this.sessionService = sessionService;
		this.commonPath = path;
		this.format = format;
		this.editingDomain = editingDomain;
		this.loadable = loadable;
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
	public void open() throws IOException {
		if (isLoadable()) {
			generateMD5CheckSum();
			loadResource(getModelPath(), editingDomain);
		} else {
			throw new IllegalStateException("Toolbox file not loadable."); //$NON-NLS-1$
		}
	}

	@Override
	public void saveResource(final XMLResource resource) throws IOException {
		if (resource instanceof PlanProResourceFactoryImpl
				&& resource.getURI().isFile()) {
			resource.save(null);
			editingDomain.getCommandStack().flush();
			loadable = true;
		} else {
			throw new IllegalStateException(Optional
					.ofNullable(resource.getURI())
					.map(u -> "Illegal uri " + u.toString()).orElse("no uri")); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	@Override
	public void setPath(final Path path) {
		commonPath = path;
		setResourcePath(path);
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
		return PathExtensions.getExtension(modelPath);
	}

	@Override
	public XMLResource getPlanProResource() {
		return getResource(getContentType(getModelPath()));
	}

	@Override
	public XMLResource getLayoutResource() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Path getLayoutPath() {
		throw new UnsupportedOperationException();
	}
}
