/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.dialogs;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.swt.widgets.Shell;

/**
 * Dialog for display of help contents.
 * 
 * @author rumpf
 *
 */
public final class HelpDialog extends BrowserDialog {

	private static String HELP_DIRECTORY = "./web/help"; //$NON-NLS-1$
	private static String HELP_FILENAME = "index.html"; //$NON-NLS-1$

	/**
	 * creates the dialog
	 * 
	 * @param parentShell
	 *            the parent shell
	 * @param title
	 *            the dialog title
	 */
	public HelpDialog(final Shell parentShell, final String title) {
		super(parentShell, title);
	}

	@Override
	protected Path getHtmlLocation() {
		return Paths.get(HELP_DIRECTORY, HELP_FILENAME);
	}
}
