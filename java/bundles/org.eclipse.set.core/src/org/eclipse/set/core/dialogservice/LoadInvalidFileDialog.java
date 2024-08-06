/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.dialogservice;

import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.set.core.Messages;
import org.eclipse.swt.widgets.Shell;

/**
 * Warning before loading invalid model files.
 * 
 * @author Schaefer
 */
public class LoadInvalidFileDialog extends AbstractDialogWithIcon {
	protected final String INVALID_ICON_URI = "platform:/plugin/org.eclipse.set.utils/icons/warning_red_transparent_48px.png"; //$NON-NLS-1$

	/**
	 * @param parentShell
	 *            the parent shell
	 * @param messages
	 *            the messages
	 * @param filename
	 *            the filename to be displayed
	 */
	public LoadInvalidFileDialog(final Shell parentShell,
			final Messages messages, final String filename) {
		super(parentShell, messages.LoadInvalidFileDialog_Title,
				String.format(messages.LoadInvalidFileDialog_MessagePattern,
						filename),
				messages.LoadIncompleteFileDialog_OpenLabel,
				messages.LoadInvalidFileDialog_CloseLabel);
	}

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
	public LoadInvalidFileDialog(final Shell parentShell,
			final String dialogTitle, final String message,
			final String... buttonsLabel) {
		super(parentShell, dialogTitle, message, buttonsLabel);
	}

	@Override
	protected URI getIconURI() throws URISyntaxException {
		return new URI(INVALID_ICON_URI);
	}

}
