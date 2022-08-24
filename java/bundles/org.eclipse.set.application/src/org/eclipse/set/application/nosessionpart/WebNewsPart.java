/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.nosessionpart;

import java.nio.file.Paths;

import org.eclipse.set.utils.WebNoSessionBasePart;

/**
 * News Web Part
 * 
 * @author Truong
 */
public class WebNewsPart extends WebNoSessionBasePart {

	private static final String NEWS_DIRECTORY = "./web/news"; //$NON-NLS-1$
	private static final String NEWS_FILENAME = "index.html"; //$NON-NLS-1$

	@Override
	protected String getURL() {
		return Paths.get(NEWS_DIRECTORY, NEWS_FILENAME).toUri().toString();
	}
}
