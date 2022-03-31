/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.handler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.ui.VersionInfo;
import org.eclipse.set.core.services.dialog.DialogService;
import org.eclipse.set.core.services.version.AdditionalVersionService;
import org.eclipse.set.ppmodel.extensions.PlanProPackageExtensions;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.ToolboxVersion;
import org.eclipse.swt.widgets.Shell;

import com.google.common.collect.Lists;

/**
 * This handler controls an about dialog.
 * 
 * @author Schaefer
 */
public class AboutHandler {
	private static final String LICENSE_FILE = "about.txt"; //$NON-NLS-1$

	@SuppressWarnings("static-method")
	protected String readLicenseText() throws IOException {
		return String.join(System.lineSeparator(), Files
				.readAllLines(Paths.get(LICENSE_FILE), StandardCharsets.UTF_8));
	}

	@Inject
	@Optional
	private AdditionalVersionService additionalVersionService;

	@Inject
	private DialogService dialogService;

	@Execute
	private void execute(final Shell shell,
			@Translation final Messages messages) throws IOException {
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

		final String licenseText = readLicenseText();
		final VersionInfo[] versionInfoArray = versionInfo
				.toArray(new VersionInfo[0]);
		dialogService.openAbout(shell, versionInfoArray, licenseText);
	}
}
