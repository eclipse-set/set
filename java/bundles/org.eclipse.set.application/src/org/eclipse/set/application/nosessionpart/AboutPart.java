/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.nosessionpart;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.ui.VersionInfo;
import org.eclipse.set.core.services.branding.BrandingService;
import org.eclipse.set.core.services.version.AdditionalVersionService;
import org.eclipse.set.ppmodel.extensions.PlanProPackageExtensions;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.eclipse.set.utils.ToolboxVersion;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.google.common.collect.Lists;

/**
 * This part displays about info
 * 
 * @author Truong
 */
public class AboutPart {

	private static final String ABOUT_FONT_NAME = "Segoe UI Semibold"; //$NON-NLS-1$

	private final static FontDescriptor INFO_FONT_DESCRIPTOR = FontDescriptor
			.createFrom(ABOUT_FONT_NAME, 12, SWT.BOLD);

	@Inject
	private BrandingService brandingService;
	@Inject
	@Optional
	private AdditionalVersionService additionalVersionService;
	@SuppressWarnings("unused")
	private Label empty;

	@PostConstruct
	private void postContruct(final Composite parent,
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
		final VersionInfo[] versionInfoArray = versionInfo
				.toArray(new VersionInfo[0]);
		createAboutPart(parent,
				brandingService.getNames().getApplicationTitle(),
				versionInfoArray, brandingService.getLicense(),
				brandingService.getActionLogo().orElse(null),
				brandingService.getVendorLogo().orElse(null));
	}

	private void createAboutPart(final Composite parent, final String info,
			final VersionInfo[] versionInfo, final String license,
			final ImageDescriptor leftImageDescriptor,
			final ImageDescriptor rightImageDescriptor) {
		final Composite container = new Composite(parent, SWT.None);
		container.setLayout(new GridLayout(5, false));
		empty = new Label(container, SWT.NONE);

		final LocalResourceManager localResourceManager = new LocalResourceManager(
				JFaceResources.getResources(), container);
		final Image leftImage = createImage(localResourceManager,
				leftImageDescriptor);
		final Image rightImage = createImage(localResourceManager,
				rightImageDescriptor);
		final Font infoFont = localResourceManager
				.createFont(INFO_FONT_DESCRIPTOR);

		empty = new Label(container, SWT.NONE);
		empty = new Label(container, SWT.NONE);
		empty = new Label(container, SWT.NONE);
		empty = new Label(container, SWT.NONE);
		empty = new Label(container, SWT.NONE);
		empty = new Label(container, SWT.NONE);

		final Label infoLabel = new Label(container, SWT.NONE);
		infoLabel.setFont(infoFont);
		infoLabel.setLayoutData(
				new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1));
		infoLabel.setText(info);
		empty = new Label(container, SWT.NONE);

		final Label leftImageLabel = new Label(container, SWT.NONE);
		leftImageLabel.setImage(leftImage);
		empty = new Label(container, SWT.NONE);

		final Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(
				new GridData(SWT.CENTER, SWT.CENTER, true, false, 2, 1));
		composite.setLayout(new GridLayout(2, false));

		createVersionInfoComposite(composite, versionInfo);

		final Label rightImageLabel = new Label(container, SWT.NONE);
		rightImageLabel.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		rightImageLabel.setImage(rightImage);

		empty = new Label(container, SWT.NONE);
		empty = new Label(container, SWT.NONE);
		empty = new Label(container, SWT.NONE);
		empty = new Label(container, SWT.NONE);
		empty = new Label(container, SWT.NONE);

		final org.eclipse.swt.widgets.Text licenseText = new org.eclipse.swt.widgets.Text(
				container, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		final GridData gd_licenseText = new GridData(SWT.FILL, SWT.FILL, true,
				true, 5, 1);
		licenseText.setLayoutData(gd_licenseText);
		licenseText.setText(license);
		licenseText.setEditable(false);
	}

	private static Composite createVersionInfoComposite(final Composite parent,
			final VersionInfo[] versionInfo) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		for (final VersionInfo vi : versionInfo) {
			createVersionInfo(vi, composite);
		}

		return composite;
	}

	private static void createVersionInfo(final VersionInfo vi,
			final Composite composite) {
		final Label versionLabel = new Label(composite, SWT.NONE);
		versionLabel.setText(vi.label);

		final Label versionInfoLabel = new Label(composite, SWT.NONE);
		versionInfoLabel.setText(vi.version);
	}

	private static Image createImage(
			final LocalResourceManager localResourceManager,
			final ImageDescriptor imageDescriptor) {
		if (imageDescriptor != null) {
			return localResourceManager.createImage(imageDescriptor);
		}
		return null;
	}
}
