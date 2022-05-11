/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.dialogservice;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Warning dialog, when file is corrupt
 * 
 * @author Truong
 *
 */
public abstract class AbstractFileDialog extends Dialog {

	/**
	 * @param parentShell
	 *            the parentShell
	 */
	protected AbstractFileDialog(final Shell parentShell) {
		super(parentShell);
	}

	@Override
	public int open() {
		create();

		final Shell shell = getShell();
		final Point size = getSize();
		final int width = size.x;
		final int height = size.y;
		shell.setSize(width, height);

		final Shell parentShell = getParentShell();
		final Point parentSize = parentShell.getSize();
		final Point parentLocation = parentShell.getLocation();
		shell.setLocation(parentLocation.x + (parentSize.x - width) / 2,
				parentLocation.y + (parentSize.y - height) / 2);

		return super.open();
	}

	protected Point getSize() {
		final Shell shell = getShell();
		final Point size = shell.getSize();
		return new Point(size.x / 3, size.y * 2);
	}

	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		createButton(parent, IDialogConstants.CANCEL_ID, getCloseLabel(), true);
		createButton(parent, IDialogConstants.OK_ID, getOpenLabel(), false);
	}

	/**
	 * Get open button Label
	 * 
	 * @return open label
	 */
	protected abstract String getOpenLabel();

	/**
	 * Get close button label
	 * 
	 * @return close label
	 */
	protected abstract String getCloseLabel();

	@Override
	protected Control createDialogArea(final Composite parent) {
		parent.getShell().setText(getDialogTitle());
		final Composite container = (Composite) super.createDialogArea(parent);
		final Text text = new Text(container,
				SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
		text.setLayoutData(new GridData(GridData.FILL_BOTH));
		text.setText(getDialogMessage());
		return container;
	}

	/**
	 * Dialog Title
	 * 
	 * @return dialog title
	 */
	protected abstract String getDialogTitle();

	/**
	 * Dialog Messages
	 * 
	 * @return dialog messages
	 */
	protected abstract String getDialogMessage();

}
