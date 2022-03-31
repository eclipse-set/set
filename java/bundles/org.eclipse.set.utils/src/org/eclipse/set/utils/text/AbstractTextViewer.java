/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.text;

import java.util.List;

/**
 * Provides a text for given lines.
 * 
 * @author Schaefer
 */
public abstract class AbstractTextViewer implements LineTextViewer {

	private static final String NEW_LINE = "\n"; //$NON-NLS-1$

	/**
	 * @param lines
	 *            the lines
	 * 
	 * @return the text for the lines
	 */
	public static String getText(final List<String> lines) {
		final StringBuilder builder = new StringBuilder();
		for (final String line : lines) {
			builder.append(line);
			builder.append(NEW_LINE);
		}
		return builder.toString();
	}
}
