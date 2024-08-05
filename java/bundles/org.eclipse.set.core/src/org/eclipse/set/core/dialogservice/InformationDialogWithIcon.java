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

import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 */
public class InformationDialogWithIcon extends MessageDialog {

	Image dialogIcon;

	/**
	 * @param parentShell
	 * @param dialogTitle
	 * @param dialogMessage
	 * @param dialogIcon
	 */
	public InformationDialogWithIcon(final Shell parentShell,
			final String dialogTitle, final String dialogMessage,
			final Image dialogIcon) {
		super(parentShell, dialogTitle, null, dialogMessage,
				MessageDialog.CONFIRM, new String[] { JFaceResources
						.getString(IDialogLabelKeys.OK_LABEL_KEY) },
				1);
		this.dialogIcon = dialogIcon;
	}

	@Override
	public Image getImage() {
		return this.dialogIcon;
	}

}
