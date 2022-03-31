/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.partservice;

import java.util.List;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.core.services.part.PartDescriptionService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import com.google.common.collect.Lists;

/**
 * Create and publish {@link ToolboxPartService}.
 * 
 * @author Schaefer
 */
@Component(service = IContextFunction.class, property = "service.context.key:String=org.eclipse.set.core.services.part.ToolboxPartService")
public class ToolboxPartServiceContextFunction extends ContextFunction {

	private final List<PartDescriptionService> descriptions = Lists
			.newArrayList();
	private ToolboxMultiPartServiceImpl partService = null;

	/**
	 * @param descriptionService
	 *            the description service
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addDescriptionService(
			final PartDescriptionService descriptionService) {
		descriptions.add(descriptionService);
		if (partService != null) {
			partService.put(descriptionService);
		}
	}

	@Override
	public Object compute(final IEclipseContext context,
			final String contextKey) {
		partService = ContextInjectionFactory
				.make(ToolboxMultiPartServiceImpl.class, context);
		descriptions.forEach(d -> partService.put(d));
		final MApplication application = context.get(MApplication.class);
		application.getContext().set(ToolboxPartService.class, partService);
		return partService;
	}

	/**
	 * @param descriptionService
	 *            the description service
	 */
	public void removeDescriptionService(
			final PartDescriptionService descriptionService) {
		// we do not support the removal of a description service
	}
}
