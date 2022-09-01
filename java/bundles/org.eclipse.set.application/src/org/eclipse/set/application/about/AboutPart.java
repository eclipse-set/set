/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.about;

import java.nio.file.Paths;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.ui.VersionInfo;
import org.eclipse.set.core.services.version.AdditionalVersionService;
import org.eclipse.set.ppmodel.extensions.PlanProPackageExtensions;
import org.eclipse.set.utils.PartInitializationException;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.ToolboxVersion;
import org.eclipse.set.utils.WebNoSessionBasePart;
import org.eclipse.swt.widgets.Composite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * This part displays about info
 * 
 * @author Truong
 */
public class AboutPart extends WebNoSessionBasePart {

	private static final String ABOUT_DIRECTORY = "./web/about"; //$NON-NLS-1$
	private static final String ABOUT_FILENAME = "index.html"; //$NON-NLS-1$

	@Inject
	@Translation
	Messages messages;

	@Inject
	@Optional
	private AdditionalVersionService additionalVersionService;

	static final Logger LOGGER = LoggerFactory.getLogger(AboutPart.class);

	AboutServer server;

	@Override
	protected String getURL() {
		return server.getRootUrl() + ABOUT_FILENAME;
	}

	private void startServer() {
		try {
			server = new AboutServer();
			server.configure(Paths.get(ABOUT_DIRECTORY),
					getVersionInformation());
			server.start();
		} catch (final Exception e) {
			throw new PartInitializationException(
					"Faild to start About Web Server"); //$NON-NLS-1$
		}
		LOGGER.info("Web server started at: " + server.getRootUrl()); //$NON-NLS-1$
	}

	@Override
	protected void createView(final Composite parent) {
		startServer();
		super.createView(parent);

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
