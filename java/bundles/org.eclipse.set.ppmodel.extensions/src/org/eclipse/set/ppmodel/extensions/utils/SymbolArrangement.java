/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils;

/**
 * Along with geometric information for an object, the arrangement provides
 * additional data for an symbolic representation of an object on a map.
 * 
 * @param <T>
 *            type for the geometric information
 * 
 * @author Schaefer
 */
public class SymbolArrangement<T> {

	private final T geometricInformation;
	private final double rotation;

	/**
	 * @param geometricInformation
	 *            the geometry
	 * @param rotation
	 *            the rotation (counterclockwise in degrees)
	 */
	public SymbolArrangement(final T geometricInformation,
			final double rotation) {
		this.geometricInformation = geometricInformation;
		this.rotation = rotation;
	}

	/**
	 * @return the geometry
	 */
	public T getGeometricInformation() {
		return geometricInformation;
	}

	/**
	 * @return the rotation (counterclockwise in degrees)
	 */
	public double getRotation() {
		return rotation;
	}
}
