/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.addons;

import java.io.IOException;
import java.util.Set;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.core.services.configurationservice.UserConfigurationService;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.ToolboxVersion;

/**
 * Addon for opening toolbox news part on the first time
 * 
 * @author Truong
 */
public class ShowNewsAddon {
	@Inject
	UserConfigurationService userConfigService;
	@Inject
	ToolboxPartService partService;

	@Inject
	@Optional
	@SuppressWarnings("unused")
	private void startUpComplete(
			@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) final MApplication application)
			throws IOException {
		final ToolboxVersion toolboxVersion = ToolboxConfiguration
				.getToolboxVersion();
		if (toolboxVersion.isDevelopmentVersion()) {
			return;
		}
		final String versionNumber = toolboxVersion.getExtraShortVersion();
		final Set<String> knownVersions = userConfigService.getKnownVersions();
		if (!knownVersions.contains(versionNumber)) {
			userConfigService.addKnownVersions(versionNumber);
			partService.showPart(ToolboxConstants.WEB_NEWS_PART_ID);
		}

	}

}