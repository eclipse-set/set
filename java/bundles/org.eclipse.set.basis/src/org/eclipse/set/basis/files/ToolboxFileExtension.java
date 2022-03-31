/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.files;

/**
 * A file extension with a priority. A high priority defines the default
 * extension for a category.
 * 
 * @author Schaefer
 */
public class ToolboxFileExtension {

	private final String extension;
	private final int priority;

	/**
	 * @param extension
	 *            the extension string
	 * @param priority
	 *            the priority
	 */
	public ToolboxFileExtension(final String extension, final int priority) {
		this.extension = extension;
		this.priority = priority;
	}

	/**
	 * @return the extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
}
