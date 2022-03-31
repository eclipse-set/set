/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.geometry;

/**
 * Indicates a problem with determining the geometry of an object.
 * 
 * @author Schaefer
 */
public class GeometryException extends RuntimeException {

	/**
	 * @param msg
	 *            detail message
	 */
	public GeometryException(final String msg) {
		super(msg);
	}
}
