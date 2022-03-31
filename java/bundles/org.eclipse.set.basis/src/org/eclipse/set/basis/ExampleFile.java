/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.nio.file.Path;

/**
 * A file in the example folder.
 * 
 * @author Schaefer
 */
public class ExampleFile {

	private final String label;
	private final Path path;

	/**
	 * @param label
	 *            the label for the menu item
	 * @param path
	 *            the path of the example file
	 */
	public ExampleFile(final String label, final Path path) {
		this.label = label;
		this.path = path;
	}

	/**
	 * @return the label for the menu item
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the path of the example file
	 */
	public Path getPath() {
		return path;
	}
}
