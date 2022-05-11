/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.dialogservice;

import org.eclipse.set.core.Messages;
import org.eclipse.swt.widgets.Shell;

/**
 * Warning before loading incomplete model files.
 * 
 * @author Stuecker
 */
class LoadIncompleteFileDialog extends AbstractFileDialog {

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
	public LoadIncompleteFileDialog(final Shell parentShell,
			final Messages messages, final String filename) {
		super(parentShell);
		this.messages = messages;
		this.filename = filename;
	}

	@Override
	protected String getDialogTitle() {
		return messages.LoadIncompleteFileDialog_Title;
	}

	@Override
	protected String getDialogMessage() {
		return String.format(messages.LoadIncompleteFileDialog_MessagePattern,
				filename);
	}

	@Override
	protected String getOpenLabel() {
		return messages.LoadIncompleteFileDialog_OpenLabel;
	}

	@Override
	protected String getCloseLabel() {
		return messages.LoadInvalidFileDialog_CloseLabel;
	}
}
