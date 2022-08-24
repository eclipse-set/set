/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class for web part not accessary in session
 * 
 * @author Truong
 *
 */
public abstract class WebNoSessionBasePart {
	protected WebBrowser browser;
	static final Logger LOGGER = LoggerFactory
			.getLogger(WebNoSessionBasePart.class);

	@PostConstruct
	private void postConstruct(final Composite parent) {
		try {
			// parent layout
			parent.setLayout(new GridLayout(1, false));

			// the view composite
			final Composite viewComposite = new Composite(parent, SWT.NONE);
			viewComposite.setLayout(new GridLayout());
			GridDataFactory.fillDefaults().grab(true, true)
					.applyTo(viewComposite);

			createView(viewComposite);
		} catch (final Exception e) {
			parent.setLayout(new FillLayout());
			final Text text = new Text(parent, SWT.READ_ONLY | SWT.WRAP);
			text.setText(e.getMessage());
		}
	}

	@PreDestroy
	protected void preDestroy() {
		browser.stop();
	}

	protected void createView(final Composite parent) {
		browser = new WebBrowser(parent);
		try {
			browser.setLayoutData(
					new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			browser.setUrl(getURL());
		} catch (final Exception e) {
			parent.setLayout(new FillLayout());
			final Text text = new Text(parent, SWT.READ_ONLY | SWT.WRAP);
			text.setText(e.getMessage());
		}
	}

	protected abstract String getURL();
}
