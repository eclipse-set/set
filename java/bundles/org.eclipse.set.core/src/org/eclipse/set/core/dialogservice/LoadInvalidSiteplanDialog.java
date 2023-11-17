/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.dialogservice;

import org.eclipse.set.core.Messages;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Truong
 *
 */
public class LoadInvalidSiteplanDialog extends AbstractFileDialog {

	private final String filename;
	private final Messages messages;

	@Override
	protected Point getSize() {
		final Shell shell = getShell();
		final Point size = shell.getSize();
		return new Point(size.x / 3 * 2, size.y * 2);
	}

	public LoadInvalidSiteplanDialog(final Shell parentShell,
			final String filename, final Messages messages) {
		super(parentShell);
		this.filename = filename;
		this.messages = messages;
	}

	@Override
	protected String getOpenLabel() {
		return messages.LoadInvalidCRSDialog_OpenLabel;
	}

	@Override
	protected String getCloseLabel() {
		return messages.LoadInvalidCRSDialog_CloseLabel;
	}

	@Override
	protected String getDialogTitle() {
		return messages.LoadInvalidCRSDialog_Title;
	}

	@Override
	protected String getDialogMessage() {
		return String.format(messages.LoadInvalidCRSDialog_MessagePattern,
				filename);
	}
}
