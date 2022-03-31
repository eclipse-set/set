/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.exceptions;

import java.io.File;
import java.io.IOException;

/**
 * Indicates, that a file is not readable.
 * 
 * @author Schaefer
 */
public class NotReadable extends IOException {

	/**
	 * @param file
	 *            the not readable file
	 */
	public NotReadable(final File file) {
		super(file.toString());
	}
}
