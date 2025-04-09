/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.application.contribution;

import java.nio.file.Path;

import org.eclipse.set.application.handler.OpenPlanProHandler;
import org.eclipse.set.basis.ExampleFile;
import org.eclipse.set.core.services.configurationservice.UserConfigurationService;
import org.eclipse.set.core.services.example.ExampleService;
import org.eclipse.swt.widgets.Shell;

import jakarta.inject.Inject;

/**
 * 
 */
public class OpenRecentMenuItem extends OpenPlanProHandler {
	@Inject
	UserConfigurationService userConfigService;

	@Override
	protected Path chooseFile(final Shell shell) {
		final ExampleFile recentItem = (ExampleFile) getMenuItem()
				.getTransientData()
				.get(ExampleService.EXAMPLE_FILE_KEY);
		if (recentItem.getPath() != null) {
			userConfigService.addPathToOpenRecent(recentItem.getPath());
		}
		return recentItem.getPath();
	}
}
