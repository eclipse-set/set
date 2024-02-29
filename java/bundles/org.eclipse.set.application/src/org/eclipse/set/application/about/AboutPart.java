/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.about;

import java.nio.file.Path;
import java.util.List;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.ui.VersionInfo;
import org.eclipse.set.core.services.part.ToolboxPartService;
import org.eclipse.set.core.services.version.AdditionalVersionService;
import org.eclipse.set.ppmodel.extensions.PlanProPackageExtensions;
import org.eclipse.set.utils.PartInitializationException;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.ToolboxVersion;
import org.eclipse.set.utils.WebNoSessionBasePart;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

import jakarta.inject.Inject;

/**
 * This part displays about info
 * 
 * @author Truong
 */
public class AboutPart extends WebNoSessionBasePart {

	private static final String ABOUT_DIRECTORY = "web/about"; //$NON-NLS-1$
	private static final String ABOUT_FILENAME = "index.html"; //$NON-NLS-1$

	@Inject
	@Translation
	Messages messages;

	@Inject
	@Optional
	private AdditionalVersionService additionalVersionService;

	@Inject
	private ToolboxPartService partService;

	static final Logger LOGGER = LoggerFactory.getLogger(AboutPart.class);

	private void setupRouteHandler() {
		try {
			browser.serveUri("version.json", (request, response) -> { //$NON-NLS-1$
				response.setMimeType("application/json"); //$NON-NLS-1$
				response.setStatus(200);
				response.setResponseData(
						new ObjectMapper().writerWithDefaultPrettyPrinter()
								.writeValueAsString(getVersionInformation()));
			});

			browser.serveUri("chrome-credits", (request, response) -> { //$NON-NLS-1$
				partService.showPart(ToolboxConstants.CHROMIUM_CREDITS_PART_ID);
				response.setStatus(200);
			});

			browser.serveRootDirectory(Path.of(ABOUT_DIRECTORY));
		} catch (final Exception e) {
			throw new PartInitializationException(
					"Failed to setup about route handler"); //$NON-NLS-1$
		}
	}

	@Override
	protected void createView(final Composite parent) {
		super.createView(parent);
		setupRouteHandler();
		browser.setToolboxUrl(ABOUT_FILENAME);
	}

	private VersionInfo[] getVersionInformation() {
		final List<VersionInfo> versionInfo = Lists.newArrayList();
		final VersionInfo versionInfo0 = new VersionInfo();
		final VersionInfo versionInfo1 = new VersionInfo();
		versionInfo0.label = messages.AboutHandler_ToolboxVersionLabel;
		versionInfo1.label = messages.AboutHandler_ModelVersionLabel;
		final ToolboxVersion toolboxVersion = ToolboxConfiguration
				.getToolboxVersion();
		versionInfo0.version = toolboxVersion.isAvailable()
				? toolboxVersion.getLongVersion()
				: messages.AboutHandler_UnknownVersion;
		versionInfo1.version = PlanProPackageExtensions.getModelVersion();
		versionInfo.add(versionInfo0);
		versionInfo.add(versionInfo1);

		if (additionalVersionService != null) {
			additionalVersionService.addInfo(versionInfo);
		}
		return versionInfo.toArray(new VersionInfo[0]);
	}

}
