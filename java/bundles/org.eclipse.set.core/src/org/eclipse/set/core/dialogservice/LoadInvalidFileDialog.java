/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.dialogservice;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.set.core.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Warning before loading invalid model files.
 * 
 * @author Schaefer
 */
class LoadInvalidFileDialog extends Dialog {

	protected final String filename;

	protected final Messages messages;

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
		super(parentShell);
		this.messages = messages;
		this.filename = filename;
	}

	@Override
	public int open() {
		create();

		final Shell shell = getShell();
		final Point size = shell.getSize();
		final int width = size.x / 3;
		final int height = size.y * 2;
		shell.setSize(width, height);

		final Shell parentShell = getParentShell();
		final Point parentSize = parentShell.getSize();
		final Point parentLocation = parentShell.getLocation();
		shell.setLocation(parentLocation.x + (parentSize.x - width) / 2,
				parentLocation.y + (parentSize.y - height) / 2);

		return super.open();
	}

	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.CANCEL_ID,
				messages.LoadInvalidFileDialog_CloseLabel, true);
		createButton(parent, IDialogConstants.OK_ID,
				messages.LoadInvalidFileDialog_OpenLabel, false);
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		parent.getShell().setText(messages.LoadInvalidFileDialog_Title);
		final Composite container = (Composite) super.createDialogArea(parent);
		final Text text = new Text(container,
				SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		text.setLayoutData(new GridData(GridData.FILL_BOTH));
		text.setText(String.format(
				messages.LoadInvalidFileDialog_MessagePattern, filename));
		return container;
	}
}
