/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.geometry;

import java.math.BigDecimal;

import org.eclipse.set.basis.graph.DirectedElement;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineSegment;

/**
 * Position on a segment.
 * 
 * @author Schaefer
 */
public class SegmentPosition {

	private final DirectedElement<LineSegment> directedlineSegment;
	private final double distance;

	/**
	 * @param directedLineSegment
	 *            the line segment of the position
	 * @param distance
	 *            die Entfernung in der Richtung des Geradensegments
	 */
	public SegmentPosition(
			final DirectedElement<LineSegment> directedLineSegment,
			final BigDecimal distance) {
		this.directedlineSegment = directedLineSegment;
		this.distance = distance.doubleValue();
	}

	/**
	 * @return the coordinate of the position
	 */
	public Coordinate getCoordinate() {
		final LineSegment lineSegment = directedlineSegment.getElement();
		final double length = lineSegment.getLength();
		if (length == 0) {
			return lineSegment.pointAlong(0);
		}
		return lineSegment.pointAlong(distance / length);
	}

	/**
	 * @return the line segment of the position
	 */
	public DirectedElement<LineSegment> getDirectedLineSegment() {
		return directedlineSegment;
	}
}
