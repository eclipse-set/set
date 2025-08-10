/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.geometry;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

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
	private final double stepSize;

	/**
	 * @param start
	 *            start coordinate of the chord
	 * @param end
	 *            end coordinate of the chord
	 * @param radius
	 *            the radius of the circle
	 * @param orientation
	 *            the orientation of the chord
	 * @param stepSize
	 *            the step size for linearization
	 */
	public Chord(final Coordinate start, final Coordinate end,
			final double radius, final Orientation orientation,
			final double stepSize) {
		this(new LineSegment(start, end), radius, orientation, stepSize);
	}

	/**
	 * @param lineSegment
	 *            the chord's line segment
	 * @param radius
	 *            the radius of the circle
	 * @param orientation
	 *            the orientation of the chord
	 * @param stepSize
	 *            the step size for linearization
	 */
	public Chord(final LineSegment lineSegment, final double radius,
			final Orientation orientation, final double stepSize) {
		Assert.isTrue(radius > 0);
		this.stepSize = stepSize;
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

	/**
	 * Linearizes this chord into linear segments
	 * 
	 * @return an array of xy-coordinates representing this chord
	 */
	public double[] linearize() {
		// Shift all coordinates to the origin (= circle center)
		final Coordinate center = getMidpoint();
		final double startX = lineSegment.p0.x - center.x;
		final double startY = lineSegment.p0.y - center.y;
		final double endX = lineSegment.p1.x - center.x;
		final double endY = lineSegment.p1.y - center.y;

		// Always consider a right arc. If this is a left arc, flip start
		// and end to get a right arc.
		final CoordinateArray array;
		if (orientation == Orientation.ARC_LEFT) {
			array = linearizeArc(endX, endY, startX, startY);
			array.reverse();
		} else {
			array = linearizeArc(startX, startY, endX, endY);
		}

		// Move back to original location
		array.offsetBy(center.x, center.y);
		return array.getData();
	}

	/**
	 * Linearizes an arc
	 * 
	 * @param startX
	 *            start of the arc
	 * @param startY
	 *            end of the arc
	 * @param endX
	 *            end of the arc (x)
	 * @param endY
	 *            end of the arc (y)
	 * @return a list of coordinates approximating the arc
	 */
	private CoordinateArray linearizeArc(final double startX,
			final double startY, final double endX, final double endY) {
		// Find angles
		final double startAngle = Math.atan2(startY, startX);
		final double endAngle = Math.atan2(endY, endX);

		// Ensure end > startAngle. Also subtract epsilon to avoid overshoots
		final double epsilon = 1.0e-10;
		double end = endAngle - epsilon;
		if (end < startAngle) {
			end += 2 * Math.PI;
		}

		// Find length of output array (+2 for start/end)
		double angle = (Math.floor(startAngle / stepSize) + 1) * stepSize;
		final int count = 2 + (int) Math.ceil((end - angle) / stepSize);

		final CoordinateArray array = new CoordinateArray(count);
		// Move across the arc recording points
		array.add(startX, startY);
		while (angle < end) {
			final double x = radius * cos(angle);
			final double y = radius * sin(angle);
			array.add(x, y);
			angle += stepSize;
		}
		array.add(endX, endY);
		return array;
	}
}
