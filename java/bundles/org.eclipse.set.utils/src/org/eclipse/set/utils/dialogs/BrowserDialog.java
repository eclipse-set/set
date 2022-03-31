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

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.set.utils.WebBrowser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Common base for browser dialogs.
 * 
 * @author Schaefer
 */
public abstract class BrowserDialog extends Dialog {

	private WebBrowser browser;
	private String title;
	private String url;

	protected BrowserDialog(final Shell parentShell) {
		this(parentShell, ""); //$NON-NLS-1$
	}

	protected BrowserDialog(final Shell parentShell, final String title) {
		super(parentShell);
		this.title = title;
		setShellStyle(
				SWT.CLOSE | SWT.MODELESS | SWT.BORDER | SWT.TITLE | SWT.RESIZE);
		setBlockOnOpen(false);
	}

	/**
	 * Set a new anchor, using the current URL.
	 * 
	 * @param anchor
	 *            the new anchor
	 */
	public void setAnchor(final String anchor) {
		browser.setUrl(url + "#" + anchor); //$NON-NLS-1$
	}

	@Override
	protected void createButtonsForButtonBar(final Composite parent) {
		final GridLayout layout = (GridLayout) parent.getLayout();
		layout.marginHeight = 0;
	}

	@Override
	protected Control createDialogArea(final Composite parent) {
		parent.getShell().setText(title);
		try {
			parent.setLayout(new GridLayout(1, false));
			final Path htmlLocation = getHtmlLocation();
			browser = new WebBrowser(parent);
			browser.setLayoutData(
					new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			url = htmlLocation.toUri().toString();
			browser.setUrl(url);
		} catch (final Exception e) {
			parent.setLayout(new FillLayout());
			final Text text = new Text(parent, SWT.READ_ONLY | SWT.WRAP);
			text.setText(e.getMessage());
		}

		return browser.getControl();
	}

	protected abstract Path getHtmlLocation();

	@Override
	protected Point getInitialSize() {
		return new Point(768, 976);
	}

	protected void setTitle(final String title) {
		this.title = title;
	}
}
