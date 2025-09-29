/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.export.addons;

import org.apache.fop.image.loader.batik.ImageLoaderFactorySVG;
import org.apache.fop.image.loader.batik.PreloaderSVG;
import org.apache.xmlgraphics.image.loader.spi.ImageImplRegistry;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;

/**
 * FOP Initialization.
 * 
 * @author Schaefer
 */
public class FopAddon {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(FopAddon.class);

	@Inject
	@Optional
	private static void startUpComplete(
			@SuppressWarnings("unused") @UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) final MApplication application) {

		// register SVG pre loader
		final ImageImplRegistry newInstance = ImageImplRegistry.newInstance();
		newInstance.registerPreloader(new PreloaderSVG());
		newInstance.registerLoaderFactory(new ImageLoaderFactorySVG());

		LOGGER.info("FOP initialized."); //$NON-NLS-1$
		// IMPROVE this shouldn't be necessary
	}
}
