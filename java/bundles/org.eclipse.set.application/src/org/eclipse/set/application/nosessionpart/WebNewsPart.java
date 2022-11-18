/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.nosessionpart;

import java.nio.file.Path;

import org.eclipse.set.utils.PartInitializationException;
import org.eclipse.set.utils.WebNoSessionBasePart;
import org.eclipse.swt.widgets.Composite;

/**
 * News Web Part
 * 
 * @author Truong
 */
public class WebNewsPart extends WebNoSessionBasePart {

	private static final String NEWS_DIRECTORY = "./web/news"; //$NON-NLS-1$
	private static final String NEWS_FILENAME = "index.html"; //$NON-NLS-1$

	private void setupRouteHandler() {
		try {
			browser.serveRootDirectory(Path.of(NEWS_DIRECTORY));
		} catch (final Exception e) {
			throw new PartInitializationException(
					"Failed to setup about route handler"); //$NON-NLS-1$
		}
	}

	@Override
	protected void createView(final Composite parent) {
		super.createView(parent);
		setupRouteHandler();
		browser.setToolboxUrl(NEWS_FILENAME);
	}
}
