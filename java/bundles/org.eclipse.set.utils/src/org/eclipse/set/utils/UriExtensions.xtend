/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils

import java.nio.file.Path
import java.nio.file.Paths
import org.eclipse.emf.common.util.URI

/**
 * Extensions for {@link URI}.
 * 
 * @author Schaefer
 */
class UriExtensions {

	/**
	 * @param uri this URI
	 * 
	 * @return the directory part of the URI
	 */
	static def Path getDirectory(URI uri) {
		return Paths.get(uri.toFileString).parent
	}

	/**
	 * @param uri this URI
	 * 
	 * @return the filename part of the URI
	 */
	static def Path getFilename(URI uri) {
		return Paths.get(uri.toFileString).fileName
	}
}
