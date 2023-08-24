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

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.resource.Resource.Factory.Registry;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.extensions.PathExtensions;
import org.eclipse.set.basis.files.ToolboxFile;
import org.eclipse.set.basis.files.ToolboxFile.Format;
import org.eclipse.set.basis.files.ToolboxFileExtension;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.files.ToolboxFileFormatService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.toolboxmodel.PlanPro.util.PlanProResourceFactoryImpl;
import org.eclipse.set.toolboxmodel.PlanPro.util.ToolboxModelService;
import org.eclipse.set.toolboxmodel.transform.ToolboxModelServiceImpl;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

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

	private EditingDomain createEditingDomain() {
		final EditingDomain ed = sessionService.createEditingDomain();
		final Registry registry = ed.getResourceSet()
				.getResourceFactoryRegistry();

		// register PlanPro model extensions by content type
		for (final ToolboxFileExtension toolboxExtension : extensionsForCategory(
				ToolboxConstants.EXTENSION_CATEGORY_PPFILE)) {
			final String extension = toolboxExtension.getExtension();
			if (extension.length() > 0) {
				final PlanProResourceFactoryImpl resourceFactory = new PlanProResourceFactoryImpl();
				registry.getContentTypeToFactoryMap().put(extension,
						resourceFactory);
				final ToolboxModelService toolboxmodelService = new ToolboxModelServiceImpl();
				resourceFactory.setToolboxModelServiceProvider(
						() -> toolboxmodelService);
			}
		}

		// Register merge model extensions by content type
		/* TODO(1.10.0.1): Readd once temporary integrations are reenabled
		for (final ToolboxFileExtension toolboxExtension : extensionsForCategory(
				ToolboxConstants.EXTENSION_CATEGORY_PPMERGE)) {
			final String extension = toolboxExtension.getExtension();
			if (extension.length() > 0) {
				registry.getContentTypeToFactoryMap().put(extension,
						new TemporaryintegrationResourceFactoryImpl());
			}
		}
		*/
		return ed;
	}

}
