/**
 * Copyright (c) 2019 DB Netz AG and others.
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
 * Dialog for news and release notes.
 * 
 * @author Schaefer
 */
public class NewsDialog extends BrowserDialog {

	private static String NEWS_DIRECTORY = "./web/news"; //$NON-NLS-1$
	private static String NEWS_FILENAME = "index.html"; //$NON-NLS-1$

	/**
	 * @param parentShell
	 *            the parent shell
	 * @param title
	 *            the title
	 */
	public NewsDialog(final Shell parentShell, final String title) {
		super(parentShell);
		setTitle(title);
	}

	@Override
	protected Path getHtmlLocation() {
		return Paths.get(NEWS_DIRECTORY, NEWS_FILENAME);
	}
}
