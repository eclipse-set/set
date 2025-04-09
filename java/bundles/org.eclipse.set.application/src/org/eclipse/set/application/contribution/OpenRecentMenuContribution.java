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
import java.util.List;

import org.eclipse.e4.ui.di.AboutToShow;
import org.eclipse.e4.ui.model.application.ui.menu.MMenuElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.set.basis.ExampleFile;
import org.eclipse.set.core.services.configurationservice.UserConfigurationService;

import jakarta.inject.Inject;

/**
 * 
 */
public class OpenRecentMenuContribution extends LoadExampleContribution {
	@Inject
	UserConfigurationService userConfigService;

	@Override
	@AboutToShow
	protected void aboutToShow2(final List<MMenuElement> items,
			final EModelService modelService) {
		final List<Path> lastOpenFiles = userConfigService.getLastOpenFiles();
		lastOpenFiles.stream()
				.map(file -> new ExampleFile(file.getFileName().toString(),
						file))
				.forEach(file -> {
					addExample(file, modelService, items);
				});
	}
}
