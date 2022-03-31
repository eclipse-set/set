/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * Can load a resource.
 * 
 * @author Schaefer
 */
@FunctionalInterface
public interface ResourceLoader {

	/**
	 * @param location
	 *            the model location
	 * 
	 * @return the resource
	 * 
	 * @throws IOException
	 *             if an I/O exception occurs while loading the resource
	 */
	Resource load(Path location) throws IOException;
}
