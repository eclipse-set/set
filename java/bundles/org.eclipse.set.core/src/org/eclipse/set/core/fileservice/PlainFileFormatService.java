/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.fileservice;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFile.Format;
import org.eclipse.set.basis.files.ToolboxFileExtension;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.files.ToolboxFileFormatService;
import org.eclipse.set.core.services.session.SessionService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import de.scheidtbachmann.planpro.model.model1902.PlanPro.util.PlanProResourceFactoryImpl;

/**
 * Toolbox file support for plain files.
 * 
 * @author Schaefer
 */
@Component(immediate = true)
public class PlainFileFormatService implements ToolboxFileFormatService {

	@Reference
	protected SessionService sessionService;

	@Override
	public ToolboxFile create(final Format format, final ToolboxFileRole role) {
		return new PlainToolboxFile(sessionService, format,
				createEditingDomain());
	}

	@Override
	public ToolboxFile create(final ToolboxFile toolboxFile) {
		return new PlainToolboxFile((PlainToolboxFile) toolboxFile);
	}

	@Override
	public Collection<ToolboxFileExtension> extensionsForCategory(
			final String category) {
		return sessionService.getPlainSupportMap().getOrDefault(category,
				Collections.emptySet());

	}

	@Override
	public boolean isSupported(final Format format) {
		return format.isPlain();
	}

	@Override
	public boolean isSupported(final Path path) {
		// IMPROVE it's a bit strange that only the session service knows if
		// this format service support a given path (extension). And it is even
		// error-prone that we registerResourceFactories (see below)
		// independently from the session service.
		// Do isSupported(Path) and registerResourceFactories really belong to
		// this service?
		return SupportMapExtensions
				.getSupportedExtensions(sessionService.getPlainSupportMap())
				.contains(PathExtensions.getExtension(path));
	}

	@Override
	public boolean isSupported(final ToolboxFile toolboxFile) {
		return toolboxFile instanceof PlainToolboxFile;
	}

	@Override
	public ToolboxFile load(final Path path, final ToolboxFileRole role) {
		final Format format = sessionService.getFormat(path);
		Assert.isTrue(format.isPlain());
		return new PlainToolboxFile(sessionService, path, format,
				createEditingDomain(), true);
	}

	@Override
	public void registerResourceFactories() {
		// get registry with all supported file types
		final Map<String, Object> registeredExtensions = Resource.Factory.Registry.INSTANCE
				.getExtensionToFactoryMap();

		{ // default extension
			final String extension = Resource.Factory.Registry.DEFAULT_EXTENSION;
			if (!registeredExtensions.containsKey(extension)) {
				Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
						.put(extension, new XMIResourceFactoryImpl());
			}
		}

		// register PlanPro model extensions
		for (final ToolboxFileExtension toolboxExtension : extensionsForCategory(
				ToolboxConstants.EXTENSION_CATEGORY_PPFILE)) {
			final String extension = toolboxExtension.getExtension();
			if (extension.length() > 0
					&& !registeredExtensions.containsKey(extension)) {
				Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
						.put(extension, new PlanProResourceFactoryImpl());
			}
		}
	}

	private EditingDomain createEditingDomain() {
		return sessionService.createEditingDomain();
	}

}
