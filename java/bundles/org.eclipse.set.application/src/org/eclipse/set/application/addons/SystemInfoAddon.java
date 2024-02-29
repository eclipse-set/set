/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.addons;

import jakarta.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.set.basis.info.SystemInfo;

/**
 * Log system info.
 * 
 * @author Schaefer
 */
public class SystemInfoAddon {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SystemInfoAddon.class);

	@Inject
	@Optional
	private static void startUpComplete(
			@SuppressWarnings("unused") @UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) final MApplication application) {
		LOGGER.info("java version={}", SystemInfo.getJavaInfo()); //$NON-NLS-1$
		LOGGER.info("resolution={}", SystemInfo.getResolution()); //$NON-NLS-1$
	}
}
