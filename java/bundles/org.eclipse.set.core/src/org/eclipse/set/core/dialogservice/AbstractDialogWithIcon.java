/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.core.dialogservice;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Dialog with icon in messages area
 * 
 * @author truong
 */
public abstract class AbstractDialogWithIcon extends MessageDialog {

	/**
	 * @param parentShell
	 *            the parent shell
	 * @param dialogTitle
	 *            the dialog title
	 * @param message
	 *            the dialog message
	 * @param buttonsLabel
	 *            the buttons label
	 */
	protected AbstractDialogWithIcon(final Shell parentShell,
			final String dialogTitle, final String message,
			final String... buttonsLabel) {
		super(parentShell, dialogTitle, null, message, MessageDialog.QUESTION,
				buttonsLabel, 1);
	}

	protected abstract URI getIconURI() throws URISyntaxException;

	@Override
	public Image getImage() {
		try {
			return ImageDescriptor.createFromURL(getIconURI().toURL())
					.createImage();
		} catch (final MalformedURLException | URISyntaxException e) {
			return null;
		}
	}

	@Override
	protected Control createMessageArea(final Composite composite) {
		// create composite
		// create image
		final Image image = getImage();
		final GridLayout gridLayout = new GridLayout(2, false);
		composite.setLayout(gridLayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		if (image != null) {
			imageLabel = new Label(composite, SWT.NULL);
			image.setBackground(imageLabel.getBackground());
			imageLabel.setImage(image);
		}
		// create message
		if (message != null) {
			messageLabel = new Label(composite, getMessageLabelStyle());
			messageLabel.setText(message);
			GridDataFactory.fillDefaults()
					.align(SWT.FILL, SWT.BEGINNING)
					.grab(true, false)
					.hint(convertHorizontalDLUsToPixels(
							IDialogConstants.MINIMUM_MESSAGE_AREA_WIDTH),
							SWT.DEFAULT)
					.applyTo(messageLabel);
		}
		return composite;
	}
}
