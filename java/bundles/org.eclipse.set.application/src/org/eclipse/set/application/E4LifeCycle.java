/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.lifecycle.PreSave;
import org.eclipse.e4.ui.workbench.lifecycle.ProcessRemovals;
import org.eclipse.set.core.services.branding.BrandingService;
import org.eclipse.set.core.services.splash.SplashScreenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Beispielimplementierung mit annotierten e4 LifeCycle Methoden. Wird im
 * Erweiterungspunkt <em>org.eclipse.core.runtime.products</em> verwendet.
 * 
 * @author Schaefer
 **/
public class E4LifeCycle {

	private static final Logger logger = LoggerFactory
			.getLogger(E4LifeCycle.class);

	@PostContextCreate
	static void postContextCreate(final IEclipseContext workbenchContext,
			final SplashScreenService splashScreenService,
			final BrandingService brandingService) {
		logger.debug(workbenchContext.toString());
		splashScreenService.show(brandingService.getSplashImage());
	}

	@PreSave
	static void preSave(final IEclipseContext workbenchContext) {
		logger.debug(workbenchContext.toString());
	}

	@ProcessRemovals
	static void processRemovals(final IEclipseContext workbenchContext) {
		logger.debug(workbenchContext.toString());
	}
}
