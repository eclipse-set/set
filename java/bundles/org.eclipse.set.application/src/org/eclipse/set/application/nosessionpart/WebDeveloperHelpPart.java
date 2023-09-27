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
 * The Develper Help part
 * 
 * @author Truong
 */
public class WebDeveloperHelpPart extends WebNoSessionBasePart {

	private static final String HELP_DIRECTORY = "./web/developerhelp"; //$NON-NLS-1$
	private static final String HELP_FILENAME = "index.html"; //$NON-NLS-1$

	private void setupRouteHandler() {
		try {
			browser.serveRootDirectory(Path.of(HELP_DIRECTORY));
		} catch (final Exception e) {
			throw new PartInitializationException(
					"Failed to setup route handler"); //$NON-NLS-1$
		}
	}

	@Override
	protected void createView(final Composite parent) {
		super.createView(parent);
		setupRouteHandler();
		browser.setToolboxUrl(HELP_FILENAME);
	}
}
