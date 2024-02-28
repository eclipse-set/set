/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.addon;

import jakarta.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.set.core.modelservice.PlanningAccessServiceImpl;
import org.eclipse.set.core.modelservice.SimpleToolboxViewModelService;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.planningaccess.PlanningAccessService;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Add on for EMF Forms Plugin.
 * 
 * @author Schaefer
 */
public class EmfFormsAddon {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EmfFormsAddon.class);

	@Inject
	@Optional
	private static void startUpComplete(
			@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) final IEclipseContext context) {
		if (context.get(ToolboxViewModelService.class) == null) {
			LOGGER.info("Started EMF Forms Addon"); //$NON-NLS-1$

			// create toolbox viewmodel service
			final ToolboxViewModelService toolboxViewModelService = ContextInjectionFactory
					.make(SimpleToolboxViewModelService.class, context);

			// registering as a true view model service fails
			Services.setToolboxViewModelService(toolboxViewModelService);
			context.set(ToolboxViewModelService.class, toolboxViewModelService);

			// create model element provider service
			final PlanningAccessService planningAccessService = ContextInjectionFactory
					.make(PlanningAccessServiceImpl.class, context);
			Services.setPlanningAccessService(planningAccessService);
			context.set(PlanningAccessService.class, planningAccessService);
		}
	}
}
