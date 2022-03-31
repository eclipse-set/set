/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.dialogservice;

import org.eclipse.jface.dialogs.IDialogLabelKeys;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.widgets.Shell;

/**
 * A confirmation dialog.
 * 
 * @author schaefer
 */
public class ConfirmDialog extends MessageDialog {

	/**
	 * @param shell
	 *            the parent shell, or <code>null</code> to create a top-level
	 *            shell
	 * @param title
	 *            the dialog title, or <code>null</code> if none
	 * @param message
	 *            the confirmation message
	 * @param confirmLabel
	 *            the button label for the confirmation
	 */
	public ConfirmDialog(final Shell shell, final String title,
			final String message, final String confirmLabel) {
		super(shell, title, null, message, MessageDialog.CONFIRM,
				new String[] { confirmLabel,
						JFaceResources
								.getString(IDialogLabelKeys.CANCEL_LABEL_KEY) },
				1);
	}

	/**
	 * @return whether the user confirmed the dialog
	 */
	public boolean confirmed() {
		return open() == 0;
	}
}
