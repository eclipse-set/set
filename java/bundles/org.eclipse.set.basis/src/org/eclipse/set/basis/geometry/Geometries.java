/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.geometry;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.set.basis.Lists;
import org.eclipse.set.basis.graph.DirectedElementImpl;
import org.locationtech.jts.algorithm.Angle;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateSequence;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineSegment;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.util.AffineTransformation;
import org.locationtech.jts.geom.util.AffineTransformationFactory;

/**
 * Geometry utility functions.
 * 
 * @author Schaefer
 */
public class Geometries {

	private static final double ACCURACY = 0.1;

	/**
	 * @param original
	 *            the original
	 * 
	 * @return a copy of the original
	 */
	public static LineSegment clone(final LineSegment original) {
		return new LineSegment(original.p0.x, original.p0.y, original.p1.x,
				original.p1.y);
	}

	/**
	 * Creates an arc.
	 * 
	 * @param geometryFactory
	 *            the geometry factory
	 * @param chord
	 *            the chord
	 * 
	 * @return the arc
	 */
	public static LineString createArc(final GeometryFactory geometryFactory,
			final Chord chord) {

		final double[] data = chord.linearize();

		final CoordinateSequence cs = geometryFactory
				.getCoordinateSequenceFactory().create(data.length / 2, 2);
		for (int i = 0; i < cs.size(); i++) {
			cs.setOrdinate(i, 0, data[i * 2]);
			cs.setOrdinate(i, 1, data[i * 2 + 1]);
		}
		return new LineString(cs, geometryFactory);
	}

	/**
	 * Returns an angle with the following characteristics:
	 * <ul>
	 * <li>the segment rotated with the angle at p0 is vertical</li>
	 * <li>the rotated segments end is above the start (p1.y >= p0.y)</li>
	 * </ul>
	 * 
	 * @param segment
	 *            the segment
	 * 
	 * @return the angle (counterclockwise in degrees)
	 */
	public static double getRotationToVertical(final LineSegment segment) {
		final double angle = Angle.angleBetweenOriented(segment.p1, segment.p0,
				new Coordinate(segment.p0.x, segment.p0.y + 1));
		return Angle.toDegrees(angle);
	}

	/**
	 * @param lineString
	 *            the line string
	 * @param start
	 *            the start coordinate
	 * @param distance
	 *            the distance
	 * 
	 * @return the position on the line string at the given distance from the
	 *         start coordinate
	 * 
	 * @throws IllegalLineStringDistance
	 *             if the line string is too short for the given distance
	 */
	public static SegmentPosition getSegmentPosition(
			final LineString lineString, final Coordinate start,
			final BigDecimal distance) throws IllegalLineStringDistance {
		final List<DirectedElementImpl<LineSegment>> segments = getSegments(
				DirectedElementImpl.forwards(lineString), start);
		final SegmentPosition position = getSegmentPosition(segments, distance);
		if (position == null) {
			throw new IllegalLineStringDistance(lineString,
					distance.doubleValue());
		}
		return position;
	}

	/**
	 * Scales the given segment to the given length.
	 * 
	 * @param segment
	 *            the segment
	 * @param length
	 *            the length
	 */
	public static void scale(final LineSegment segment, final double length) {
		final Coordinate start = segment.p0;
		final AffineTransformation moveStartToOrigin = AffineTransformation
				.translationInstance(-start.x, -start.y);
		final double segmentAngle = segment.angle();
		final AffineTransformation rotateToXAxis = AffineTransformation
				.rotationInstance(-segmentAngle);
		double xScale = 0.0;
		if (segment.getLength() != 0) {
			xScale = length / segment.getLength();
		}
		final AffineTransformation scaleAlongXAxis = AffineTransformation
				.scaleInstance(xScale, 1);
		final AffineTransformation rotateToOriginalAngle = AffineTransformation
				.rotationInstance(segmentAngle);
		final AffineTransformation moveOriginToStart = AffineTransformation
				.translationInstance(start.x, start.y);
		final AffineTransformation transformation = moveStartToOrigin
				.compose(rotateToXAxis).compose(scaleAlongXAxis)
				.compose(rotateToOriginalAngle).compose(moveOriginToStart);
		transformation.transform(segment.p0, segment.p0);
		transformation.transform(segment.p1, segment.p1);
	}

	/**
	 * Translates the segment by a vector given by the source and destination
	 * coordinates.
	 * 
	 * @param segment
	 *            the segment
	 * @param source
	 *            the source
	 * @param destination
	 *            the destination
	 */
	public static void translate(final LineSegment segment,
			final Coordinate source, final Coordinate destination) {
		final AffineTransformation transformation = AffineTransformationFactory
				.createFromControlVectors(source, destination);
		transformation.transform(segment.p0, segment.p0);
		transformation.transform(segment.p1, segment.p1);
	}

	/**
	 * Turns the segment at the start coordinates by the given angle
	 * counter-clockwise.
	 * 
	 * @param segment
	 *            the segment
	 * @param angle
	 *            the angle in degrees
	 */
	public static void turn(final LineSegment segment, final double angle) {
		final Coordinate start = segment.p0;
		final AffineTransformation moveStartToOrigin = AffineTransformation
				.translationInstance(-start.x, -start.y);
		final AffineTransformation rotate = AffineTransformation
				.rotationInstance(Angle.toRadians(angle));
		final AffineTransformation moveOriginToStart = AffineTransformation
				.translationInstance(start.x, start.y);
		final AffineTransformation transformation = moveStartToOrigin
				.compose(rotate).compose(moveOriginToStart);
		transformation.transform(segment.p0, segment.p0);
		transformation.transform(segment.p1, segment.p1);
	}

	private static DirectedElementImpl<LineString> getLineStringAtStart(
			final DirectedElementImpl<LineString> directedLineString,
			final Coordinate start) {
		final LineString lineString = directedLineString.getElement();
		final Coordinate lineStringStart = lineString.getCoordinateN(0);
		if (lineStringStart.distance(start) < ACCURACY) {
			return directedLineString;
		}
		final LineString reverseLineString = lineString.reverse();
		// reverseLineStringStart = lineStringEnd
		final Coordinate lineStringEnd = reverseLineString.getCoordinateN(0);
		if (lineStringEnd.distance(start) < ACCURACY) {
			return DirectedElementImpl.backwards(reverseLineString);
		}
		throw new IllegalArgumentException(String.format(
				"Coordinate %s is not start or end of line string %s", start, //$NON-NLS-1$
				directedLineString));
	}

	private static SegmentPosition getSegmentPosition(
			final List<DirectedElementImpl<LineSegment>> segments,
			final BigDecimal distance) {
		if (segments.isEmpty()) {
			return null;
		}
		final DirectedElementImpl<LineSegment> head = Lists.head(segments);
		final double length = head.getElement().getLength();
		if (distance.doubleValue() <= length + ACCURACY) {
			return new SegmentPosition(head, distance);
		}
		return getSegmentPosition(Lists.tail(segments),
				distance.subtract(BigDecimal.valueOf(length)));
	}

	private static List<DirectedElementImpl<LineSegment>> getSegments(
			final DirectedElementImpl<LineString> directedLineString,
			final Coordinate start) {
		final LinkedList<DirectedElementImpl<LineSegment>> result = new LinkedList<>();
		final DirectedElementImpl<LineString> directedLineStringAtStart = getLineStringAtStart(
				directedLineString, start);
		final CoordinateSequence sequence = directedLineStringAtStart
				.getElement().getCoordinateSequence();
		final int size = sequence.size();
		if (size < 2) {
			return result;
		}
		Coordinate segmentStart = sequence.getCoordinate(0);
		for (int i = 1; i < size; i++) {
			final Coordinate segmentEnd = sequence.getCoordinate(i);
			result.add(new DirectedElementImpl<>(
					new LineSegment(segmentStart, segmentEnd),
					directedLineStringAtStart.isForwards()));
			segmentStart = segmentEnd;
		}

		return result;
	}
}
