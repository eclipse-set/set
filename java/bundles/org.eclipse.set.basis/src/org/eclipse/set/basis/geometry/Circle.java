/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.geometry;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineSegment;

/**
 * Arithmetic description of a circle.
 * 
 * @author Schaefer
 */
public class Circle {

	private final Coordinate midpoint;
	private final double radius;

	/**
	 * @param chord
	 *            the chord
	 */
	public Circle(final Chord chord) {
		this(chord.getMidpoint(), chord.getRadius());
	}

	/**
	 * @param midpoint
	 *            the midpoint
	 * @param radius
	 *            the radius
	 */
	public Circle(final Coordinate midpoint, final double radius) {
		this.midpoint = midpoint;
		this.radius = radius;
	}

	/**
	 * @return the midpoint
	 */
	public Coordinate getMidpoint() {
		return midpoint;
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Creates a tangential line segment at the given coordinate. The tangent
	 * has a counter-clockwise orientation (is turned counter-clockwise relative
	 * to the radius)
	 * 
	 * @param coordinate
	 *            the coordinate
	 * 
	 * @return the tangent
	 */
	public LineSegment getTangent(final Coordinate coordinate) {
		final LineSegment tangent = Geometries
				.clone(new LineSegment(getMidpoint(), coordinate));
		Geometries.translate(tangent, getMidpoint(), coordinate);
		Geometries.turn(tangent, 90);
		return tangent;
	}
}
