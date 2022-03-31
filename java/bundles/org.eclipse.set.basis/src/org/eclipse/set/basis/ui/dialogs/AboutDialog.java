/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.resource.FontDescriptor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.set.basis.ui.VersionInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * This dialog displays an about info.
 * 
 * @author Schaefer
 */
public class AboutDialog extends Dialog {

	private static final String ABOUT_FONT_NAME = "Segoe UI Semibold"; //$NON-NLS-1$

	private final static FontDescriptor INFO_FONT_DESCRIPTOR = FontDescriptor
			.createFrom(ABOUT_FONT_NAME, 12, SWT.BOLD);

	private static Point computeControl(final Control control) {
		return control.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}

	private static Point computeControls(final Control... controls) {
		final Point result = new Point(0, 0);
		for (final Control control : controls) {
			result.x = result.x + computeControl(control).x;
			result.y = result.y + computeControl(control).y;
		}
		return result;
	}

	private static Image createImage(
			final LocalResourceManager localResourceManager,
			final ImageDescriptor imageDescriptor) {
		if (imageDescriptor != null) {
			return localResourceManager.createImage(imageDescriptor);
		}
		return null;
	}

	private static void createVersionInfo(final VersionInfo vi,
			final Composite composite) {
		final Label versionLabel = new Label(composite, SWT.NONE);
		versionLabel.setText(vi.label);

		final Label versionInfoLabel = new Label(composite, SWT.NONE);
		versionInfoLabel.setText(vi.version);
	}

	@SuppressWarnings("unused")
	// new Label()... used by SWT Designer for empty lines
	private Label empty;
	private final String info;
	private final ImageDescriptor leftImageDescriptor;
	private final String license;
	private final ImageDescriptor rightImageDescriptor;
	private final String title;

	private final VersionInfo[] versionInfo;

	/**
	 * Creates the about dialog.
	 * 
	 * @param shell
	 *            the shell
	 * @param title
	 *            the dialog title
	 * @param info
	 *            the about info
	 * @param versionInfo
	 *            the version info
	 * @param license
	 *            the license text
	 * @param leftImage
	 *            the left image
	 * @param rightImage
	 *            the right image
	 */
	public AboutDialog(final Shell shell, final String title, final String info,
			final VersionInfo[] versionInfo, final String license,
			final ImageDescriptor leftImage, final ImageDescriptor rightImage) {
		super(shell);
		this.title = title;
		this.info = info;
		this.versionInfo = versionInfo;
		this.license = license;
		this.leftImageDescriptor = leftImage;
		this.rightImageDescriptor = rightImage;
	}

	private Composite createVersionInfoComposite(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		for (final VersionInfo vi : versionInfo) {
			createVersionInfo(vi, composite);
		}

		return composite;
	}

	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL,
				true);
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite container = (Composite) super.createDialogArea(parent);
		parent.getShell().setText(title);
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

		final Composite versionInfoComposite = createVersionInfoComposite(
				composite);

		final Label rightImageLabel = new Label(container, SWT.NONE);
		rightImageLabel.setLayoutData(
				new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		rightImageLabel.setImage(rightImage);

		empty = new Label(container, SWT.NONE);
		empty = new Label(container, SWT.NONE);
		empty = new Label(container, SWT.NONE);
		empty = new Label(container, SWT.NONE);
		empty = new Label(container, SWT.NONE);

		final Text licenseText = new Text(container,
				SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		final GridData gd_licenseText = new GridData(SWT.FILL, SWT.FILL, true,
				true, 5, 1);
		licenseText.setLayoutData(gd_licenseText);
		licenseText.setText(license);
		licenseText.setEditable(false);

		final Point size = computeControls(infoLabel, versionInfoComposite);
		parent.getShell().setSize((int) Math.round(size.x * 1.5), size.y * 7);

		return container;
	}
}
