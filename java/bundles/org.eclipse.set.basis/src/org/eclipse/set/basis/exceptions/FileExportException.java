/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.exceptions;

import java.nio.file.Path;

/**
 * An exception occurred while exporting to a file.
 * 
 * @author schaefer
 */
public class FileExportException extends RuntimeException {

	private final Throwable exception;
	private final Path exportTarget;

	/**
	 * @param exportTarget
	 *            the export target
	 * @param exception
	 *            the occurred exception
	 */
	public FileExportException(final Path exportTarget,
			final Throwable exception) {
		this.exportTarget = exportTarget;
		this.exception = exception;
	}

	/**
	 * @return the occurred exception
	 */
	public Throwable getException() {
		return exception;
	}

	/**
	 * @return the export target
	 */
	public Path getExportTarget() {
		return exportTarget;
	}
}
