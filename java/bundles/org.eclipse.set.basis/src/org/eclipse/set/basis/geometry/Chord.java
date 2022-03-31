/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.geometry;

import org.eclipse.core.runtime.Assert;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineSegment;

/**
 * Arithmetic description of a chord. A chord is a line segment, whose endpoints
 * lie on a circle.
 * 
 * @author Schaefer
 */
public class Chord {

	/**
	 * Describes the orientation of the chord.
	 */
	public enum Orientation {
		/**
		 * the arc between the endpoints of the chord lies left of the chord (in
		 * the direction start -> end)
		 */
		ARC_LEFT,

		/**
		 * the arc between the endpoints of the chord lies right of the chord
		 * (in the direction start -> end)
		 */
		ARC_RIGHT
	}

	private final LineSegment lineSegment;
	private final Orientation orientation;
	private final double radius;

	/**
	 * @param start
	 *            start coordinate of the chord
	 * @param end
	 *            end coordinate of the chord
	 * @param radius
	 *            the radius of the circle
	 * @param orientation
	 *            the orientation of the chord
	 */
	public Chord(final Coordinate start, final Coordinate end,
			final double radius, final Orientation orientation) {
		this(new LineSegment(start, end), radius, orientation);
	}

	/**
	 * @param lineSegment
	 *            the chord's line segment
	 * @param radius
	 *            the radius of the circle
	 * @param orientation
	 *            the orientation of the chord
	 */
	public Chord(final LineSegment lineSegment, final double radius,
			final Orientation orientation) {
		Assert.isTrue(radius > 0);

		this.lineSegment = lineSegment;
		this.radius = radius;
		this.orientation = orientation;
	}

	/**
	 * @return the end of the chord
	 */
	public Coordinate getEnd() {
		return lineSegment.p1;
	}

	/**
	 * @return the midpoint of the chord's circle
	 */
	public Coordinate getMidpoint() {
		final double c = lineSegment.getLength();
		final double r2 = radius * radius;
		final double c2 = c * c;
		final double h = Math.sqrt(r2 - c2 / 4);
		final Coordinate middleOfC = lineSegment.midPoint();
		final LineSegment hSegment = Geometries.clone(lineSegment);
		Geometries.translate(hSegment, getStart(), middleOfC);
		if (orientation == Orientation.ARC_LEFT) {
			Geometries.turn(hSegment, -90);
		} else {
			Geometries.turn(hSegment, 90);
		}
		Geometries.scale(hSegment, h);
		return hSegment.p1;
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Returns the sagitta. The sagitta is a perpendicular line segment on the
	 * chord between the midpoint of the chord and the arc of the circle. The
	 * direction is "midpoint of the chord -> point on the arc".
	 * 
	 * @return the sagitta
	 */
	public LineSegment getSagitta() {
		final double c = lineSegment.getLength();
		final double r2 = radius * radius;
		final double c2 = c * c;
		final double h = Math.sqrt(r2 - c2 / 4);
		final double s = radius - h;
		final Coordinate middleOfC = lineSegment.midPoint();
		final LineSegment sagitta = Geometries.clone(lineSegment);
		Geometries.translate(sagitta, getStart(), middleOfC);
		if (orientation == Orientation.ARC_LEFT) {
			Geometries.turn(sagitta, 90);
		} else {
			Geometries.turn(sagitta, -90);
		}
		Geometries.scale(sagitta, s);
		return sagitta;
	}

	/**
	 * @return the start of the chord
	 */
	public Coordinate getStart() {
		return lineSegment.p0;
	}
}
