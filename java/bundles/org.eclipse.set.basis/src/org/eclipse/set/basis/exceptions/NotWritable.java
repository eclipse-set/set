/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.exceptions;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Indicates, that a file is not writable.
 * 
 * @author Schaefer
 */
public class NotWritable extends IOException {

	private final Path filePath;

	/**
	 * @param filePath
	 *            the not writable file path
	 */
	public NotWritable(final Path filePath) {
		super(filePath.toString());
		this.filePath = filePath;
	}

	/**
	 * @return the not writable file path
	 */
	public Path getFilePath() {
		return filePath;
	}
}
