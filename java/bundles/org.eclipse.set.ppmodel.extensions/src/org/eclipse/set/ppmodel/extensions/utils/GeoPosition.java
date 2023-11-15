/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils;

import org.eclipse.set.toolboxmodel.BasisTypen.ENUMWirkrichtung;
import org.locationtech.jts.geom.Coordinate;

/**
 * Geometric Position for an object
 * 
 * IMPROVE: This could be a Java record (Pending xtend support
 * https://github.com/eclipse/xtext/issues/2823)
 */
public class GeoPosition {
	private final Coordinate coordinate;
	private final double actionRotation;
	private final double topRotation;

	/**
	 * @param coordinate
	 *            the coordinate
	 * @param topRotation
	 *            the rotation (counterclockwise in degrees) of the TOP_Kante at
	 *            the coordinate
	 * @param wirkrichtung
	 *            the effective rotation for the position relative to the
	 *            TOP_Kante based on ENUMWirkrichtung
	 */
	public GeoPosition(final Coordinate coordinate, final double topRotation,
			final ENUMWirkrichtung wirkrichtung) {
		this(coordinate, topRotation,
				wirkrichtung == ENUMWirkrichtung.ENUM_WIRKRICHTUNG_GEGEN
						? topRotation + 180
						: topRotation);
	}

	/**
	 * @param coordinate
	 *            the coordinate
	 * @param topRotation
	 *            the rotation (counterclockwise in degrees) of the TOP_Kante at
	 *            the coordinate
	 * @param actionRotation
	 *            the effective rotation for the position (e.g. a signal
	 *            direction)
	 */
	public GeoPosition(final Coordinate coordinate, final double topRotation,
			final double actionRotation) {
		this.coordinate = coordinate;
		this.topRotation = topRotation;
		this.actionRotation = actionRotation;
	}

	/**
	 * @return the coordinate
	 */
	public Coordinate getCoordinate() {
		return coordinate;
	}

	/**
	 * @return the effective action rotation (counterclockwise in degrees). This
	 *         points towards the direction of the track this object applies
	 *         it's effect to. This should be used to rotate the object.
	 */
	public double getEffectiveRotation() {
		return actionRotation;
	}

	/**
	 * @return the topological rotation (counterclockwise in degrees). This
	 *         points towards the topological direction of the track this object
	 *         is positioned on. This should be used to position objects
	 *         relative to the track.
	 */
	public double getTopologicalRotation() {
		return topRotation;
	}

}
