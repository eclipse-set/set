/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils;

/**
 * Indicates a problem with the initialization of a part.
 * 
 * @author Schaefer
 */
public class PartInitializationException extends RuntimeException {

	/**
	 * @param message
	 *            the detail message
	 */
	public PartInitializationException(final String message) {
		super(message);
	}

	/**
	 * @param message
	 *            the detail message
	 * @param cause
	 *            the cause
	 */
	public PartInitializationException(final String message,
			final Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param cause
	 *            the cause
	 */
	public PartInitializationException(final Throwable cause) {
		super(cause);
	}
}
