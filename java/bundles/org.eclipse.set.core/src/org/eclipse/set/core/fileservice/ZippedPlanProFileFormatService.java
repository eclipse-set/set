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
import org.eclipse.set.model.temporaryintegration.util.TemporaryintegrationResourceFactoryImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import de.scheidtbachmann.planpro.model.model1902.PlanPro.util.PlanProResourceFactoryImpl;

/**
 * Toolbox file support for *.planpro files.
 * 
 * @author Schaefer
 */
@Component(immediate = true)
public class ZippedPlanProFileFormatService
		implements ToolboxFileFormatService {

	@Reference
	protected SessionService sessionService;

	@Override
	public ToolboxFile create(final Format format, final ToolboxFileRole role) {
		Assert.isTrue(format.isZippedPlanPro());
		return new ZippedPlanProToolboxFile(format, createEditingDomain(),
				role);
	}

	@Override
	public ToolboxFile create(final ToolboxFile toolboxFile) {
		return new ZippedPlanProToolboxFile(
				(ZippedPlanProToolboxFile) toolboxFile);
	}

	@Override
	public Collection<ToolboxFileExtension> extensionsForCategory(
			final String category) {
		return sessionService.getZippedSupportMap().getOrDefault(category,
				Collections.emptySet());
	}

	@Override
	public boolean isSupported(final Format format) {
		return format.isZippedPlanPro();
	}

	@Override
	public boolean isSupported(final Path path) {
		return SupportMapExtensions
				.getSupportedExtensions(sessionService.getZippedSupportMap())
				.contains(PathExtensions.getExtension(path));
	}

	@Override
	public boolean isSupported(final ToolboxFile toolboxFile) {
		return toolboxFile instanceof ZippedPlanProToolboxFile;
	}

	@Override
	public ToolboxFile load(final Path path, final ToolboxFileRole role) {
		final Format format = sessionService.getFormat(path);
		Assert.isTrue(format.isZippedPlanPro());
		return new ZippedPlanProToolboxFile(path, format, createEditingDomain(),
				true, role);
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

		// register merge model extensions
		for (final ToolboxFileExtension toolboxExtension : extensionsForCategory(
				ToolboxConstants.EXTENSION_CATEGORY_PPMERGE)) {
			final String extension = toolboxExtension.getExtension();
			if (!registeredExtensions.containsKey(extension)) {
				Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap()
						.put(extension,
								new TemporaryintegrationResourceFactoryImpl());
			}
		}
	}

	private EditingDomain createEditingDomain() {
		return sessionService.createEditingDomain();
	}

}
