/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.geometry;

import org.locationtech.jts.geom.LineString;

/**
 * Indicates that a given distance determines no coordinate on a line string.
 * 
 * @author Schaefer
 */
public class IllegalLineStringDistance extends GeometryException {

	private final double distance;
	private final LineString lineString;

	/**
	 * Erzeugt die Ausnahme.
	 *
	 * @param lineString
	 *            die Geradenkette
	 * @param distance
	 *            der Abstand
	 */
	public IllegalLineStringDistance(final LineString lineString,
			final double distance) {
		super(String.format("No point found on %s at distance %s", lineString, //$NON-NLS-1$
				Double.valueOf(distance)));
		this.distance = distance;
		this.lineString = lineString;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distance;
	}

	/**
	 * @return the length of the line string
	 */
	public double getLength() {
		return lineString.getLength();
	}
}
