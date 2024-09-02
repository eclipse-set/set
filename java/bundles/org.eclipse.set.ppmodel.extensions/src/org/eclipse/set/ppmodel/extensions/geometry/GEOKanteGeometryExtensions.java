/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.ppmodel.extensions.geometry;

import static org.eclipse.set.ppmodel.extensions.GeoKanteExtensions.isCRSConsistent;
import static org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.getCRS;
import static org.eclipse.set.ppmodel.extensions.GeoKnotenExtensions.getCoordinate;
import static org.eclipse.set.ppmodel.extensions.geometry.CoordinateExtensions.getAngleBetweenPoints;
import static org.eclipse.set.ppmodel.extensions.geometry.CoordinateExtensions.mirrorY;
import static org.eclipse.set.ppmodel.extensions.geometry.CoordinateExtensions.offsetBy;
import static org.eclipse.set.ppmodel.extensions.geometry.CoordinateExtensions.rotateAroundOrigin;
import static org.eclipse.set.ppmodel.extensions.geometry.CoordinateExtensions.rotateAroundPoint;
import static org.eclipse.xtext.xbase.lib.IterableExtensions.lastOrNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.set.basis.geometry.Chord;
import org.eclipse.set.basis.geometry.Geometries;
import org.eclipse.set.basis.graph.DirectedEdge;
import org.eclipse.set.basis.graph.DirectedElement;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.geometry.GeoKanteGeometryService;
import org.eclipse.set.model.planpro.Geodaten.ENUMGEOForm;
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante;
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten;
import org.eclipse.set.utils.math.Bloss;
import org.eclipse.set.utils.math.Clothoid;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find geometry for the GEO_Kante
 */
public class GEOKanteGeometryExtensions {
	static final Logger logger = LoggerFactory
			.getLogger(GEOKanteGeometryExtensions.class);
	// Configuration options for Bloss curves
	private static final int BLOSS_SEGMENTS_MIN = 10;
	private static final double BLOSS_SEGMENTS_PER_LENGTH = 0.5;
	private static final int BLOSS_PRECISION = 20;

	// Configuration options for Clothoids
	private static final int CLOTHOID_SEGMENTS_MIN = 10;
	private static final double CLOTHOID_SEGMENTS_PER_LENGTH = 0.5;
	private static final int CLOTHOID_PRECISION = 5;

	private static GeometryFactory geomeryFactory;

	private static GeometryFactory getGeometryFactory() {
		if (geomeryFactory == null) {
			geomeryFactory = new GeometryFactory();
		}
		return geomeryFactory;
	}

	/**
	 * findout geometry of the {@link GEO_Kante}
	 * 
	 * @param edge
	 *            the {@link GEO_Kante}
	 * @return line string geometry
	 */
	public static LineString defineEdgeGeometry(final GEO_Kante edge) {
		final ENUMGEOForm geoForm = edge.getGEOKanteAllg().getGEOForm()
				.getWert();
		switch (geoForm) {
		case ENUMGEO_FORM_GERADE, ENUMGEO_FORM_SONSTIGE, ENUMGEO_FORM_RICHTGERADE_KNICK_AM_ENDE_200_GON, ENUMGEO_FORM_KM_SPRUNG:
			return getGeometryFactory().createLineString(getCoordinates(edge));
		case ENUMGEO_FORM_BOGEN:
			return arc(edge);
		case ENUMGEO_FORM_KLOTHOIDE:
			return clothoid(edge);
		case ENUMGEO_FORM_BLOSSKURVE, ENUMGEO_FORM_BLOSS_EINFACH_GESCHWUNGEN:
			return blosscurve(edge);
		default: {
			logger.warn("Form {} not supported.", geoForm.getName()); //$NON-NLS-1$
			return getGeometryFactory().createLineString(getCoordinates(edge));
		}
		}
	}

	private static LineString arc(final GEO_Kante edge) {
		final Coordinate[] coordinates = getCoordinates(edge);
		final double radius = edge.getGEOKanteAllg().getGEORadiusA().getWert()
				.doubleValue();
		return Geometries.createArc(getGeometryFactory(),
				new Chord(coordinates[0], coordinates[1], Math.abs(radius),
						radius < 0 ? Chord.Orientation.ARC_RIGHT
								: Chord.Orientation.ARC_LEFT));
	}

	private static LineString clothoid(final GEO_Kante edge) {
		final double radiusA = Optional
				.ofNullable(edge.getGEOKanteAllg().getGEORadiusA().getWert())
				.orElse(BigDecimal.ZERO).doubleValue();
		final double radiusB = Optional
				.ofNullable(edge.getGEOKanteAllg().getGEORadiusB().getWert())
				.orElse(BigDecimal.ZERO).doubleValue();
		final Coordinate coordinateA = getCoordinate(
				edge.getIDGEOKnotenA().getValue());
		final Coordinate coordinateB = getCoordinateNodeB(edge);
		final double angle = edge.getGEOKanteAllg().getGEORichtungswinkel()
				.getWert().doubleValue();
		final double length = edge.getGEOKanteAllg().getGEOLaenge().getWert()
				.doubleValue();
		if (radiusA != 0 && radiusB != 0) {
			// Clothoid connecting two curved tracks
			// Split the clothoid into two connected clothoids, each of half
			// length
			// Determine the center point
			final Clothoid clothoid = new Clothoid(Math.abs(radiusB), length,
					CLOTHOID_PRECISION);
			final double angleRad = Math.PI / 200 * (100 - angle);
			final double[] point = clothoid.getPoint(length / 2);
			Coordinate centerCoordinate = new Coordinate(point[0], point[1]);
			if (radiusB > 0) {
				centerCoordinate = mirrorY(centerCoordinate);
			}

			final Coordinate center = offsetBy(
					rotateAroundOrigin(centerCoordinate, angleRad),
					coordinateA.x, coordinateA.y);

			// Draw the segments
			final Coordinate[] segmentA = clothoid(coordinateA, center, radiusB,
					length / 2).getCoordinates();
			final Coordinate[] segmentB = clothoid(center, coordinateB, radiusA,
					length / 2).getCoordinates();
			final List<Coordinate> segment = new ArrayList<>();
			segment.addAll(Arrays.asList(segmentA));
			segment.addAll(Arrays.asList(segmentB));
			return getGeometryFactory()
					.createLineString(segment.toArray(new Coordinate[0]));

		} else if (radiusA == 0) {
			// Curve from node A to node B
			return clothoid(coordinateA, coordinateB, radiusB, length);
		}
		// Curve from node B to node A
		// Invert the radius, as left-right is also inverted due to the
		// drawing direction
		return clothoid(coordinateB, coordinateA, -radiusA, length);
	}

	private static LineString clothoid(final Coordinate fromCoordinate,
			final Coordinate toCoordinate, final double radius,
			final double length) {
		// Calculate the base clothoid
		final int segmentCount = (int) Math.max(
				length * CLOTHOID_SEGMENTS_PER_LENGTH, CLOTHOID_SEGMENTS_MIN);
		final Clothoid clothoid = new Clothoid(Math.abs(radius), length,
				CLOTHOID_PRECISION);
		final List<double[]> clothoidCoordinates = clothoid
				.getPoints(segmentCount);
		final List<Coordinate> coords = clothoidCoordinates.stream()
				.map(coor -> {
					final Coordinate coordinate = new Coordinate(coor[0],
							coor[1]);
					// Mirror along y-axis for left curves
					return radius > 0 ? mirrorY(coordinate) : coordinate;
				}).toList();

		// Move the curve to the right position
		final List<Coordinate> coordinates = coords.stream()
				.map(coor -> offsetBy(coor, fromCoordinate.x, fromCoordinate.y))
				.toList();
		// Rotate the curve so that the last coordinates from the GEO_Kante and
		// curve line up
		final double angleA = getAngleBetweenPoints(fromCoordinate,
				toCoordinate);
		final double angleB = getAngleBetweenPoints(fromCoordinate,
				lastOrNull(coordinates));
		final double angleOffset = angleA - angleB;
		return getGeometryFactory().createLineString(coordinates.stream().map(
				coor -> rotateAroundPoint(coor, angleOffset, fromCoordinate))
				.toList().toArray(new Coordinate[0]));
	}

	private static LineString blosscurve(final GEO_Kante edge) {
		final double radiusA = Optional
				.ofNullable(edge.getGEOKanteAllg().getGEORadiusA().getWert())
				.orElse(BigDecimal.ZERO).doubleValue();
		final double radiusB = Optional
				.ofNullable(edge.getGEOKanteAllg().getGEORadiusB().getWert())
				.orElse(BigDecimal.ZERO).doubleValue();
		final Coordinate coordinateA = getCoordinate(
				edge.getIDGEOKnotenA().getValue());
		final Coordinate coordinateB = getCoordinateNodeB(edge);
		final double length = edge.getGEOKanteAllg().getGEOLaenge().getWert()
				.doubleValue();
		if (radiusA != 0 && radiusB != 0) {
			// Bloss curve connecting two straight tracks
			logger.warn("Form Bloss between straight tracks not supported."); //$NON-NLS-1$
			return getGeometryFactory().createLineString(getCoordinates(edge));
		} else if (radiusA == 0) {
			// Curve from node B to node A
			return blosscurve(coordinateB, coordinateA, radiusB, length);
		}
		// Curve from node A to node B
		// Invert the radius, as left-right is also inverted due to the
		// drawing direction
		return blosscurve(coordinateA, coordinateB, -radiusA, length);

	}

	private static LineString blosscurve(final Coordinate fromCoordinate,
			final Coordinate toCoordinate, final double radius,
			final double length) {
		final Bloss bloss = new Bloss(Math.abs(radius), length,
				BLOSS_PRECISION);
		final int segmentCount = (int) Math
				.max(length * BLOSS_SEGMENTS_PER_LENGTH, BLOSS_SEGMENTS_MIN);
		final List<Coordinate> coords = bloss.calculate(segmentCount).stream()
				.map(coor -> {
					final Coordinate coordinate = new Coordinate(coor[0],
							coor[1], 0);
					return radius < 0 ? mirrorY(coordinate) : coordinate;
				}).toList();

		// Move the curve to the right position
		final List<Coordinate> coordinates = coords.stream()
				.map(coor -> offsetBy(coor, fromCoordinate.x, fromCoordinate.y))
				.toList();
		// Rotate the curve so that the last coordinates from the GEO_Kante and
		// curve line up
		final double angleA = getAngleBetweenPoints(fromCoordinate,
				toCoordinate);
		final double angleB = getAngleBetweenPoints(fromCoordinate,
				lastOrNull(coordinates));
		final double angleOffset = angleA - angleB;
		return getGeometryFactory().createLineString(coordinates.stream().map(
				coor -> rotateAroundPoint(coor, angleOffset, fromCoordinate))
				.toList().toArray(new Coordinate[0]));
	}

	/**
	 * Returns GEO_Knoten_B coordinates in the same CRS as GEO_Knoten_A
	 * 
	 * @param edge
	 *            the Geo_Kante
	 */
	private static Coordinate getCoordinateNodeB(final GEO_Kante edge) {
		final GEO_Knoten nodeA = edge.getIDGEOKnotenA().getValue();
		final GEO_Knoten nodeB = edge.getIDGEOKnotenB().getValue();
		Coordinate coordinateB = getCoordinate(nodeB);
		if (!isCRSConsistent(edge)) {
			coordinateB = CoordinateExtensions.transformCRS(coordinateB,
					getCRS(nodeB), getCRS(nodeA));
		}
		return coordinateB;
	}

	private static Coordinate[] getCoordinates(final GEO_Kante edge) {
		return Stream
				.of(getCoordinate(edge.getIDGEOKnotenA().getValue()),
						getCoordinateNodeB(edge))
				.toList().toArray(new Coordinate[0]);
	}

	/**
	 * See {@link GeoKanteGeometryService#getGeometry(GEO_Kante)}
	 * 
	 * @param edge
	 *            the geo kante
	 * @return the line string of this GEO Kante
	 */
	public static LineString getGeometry(final GEO_Kante edge) {
		return Services.getGeometryService().getGeometry(edge);
	}

	/**
	 * See {@link GeoKanteGeometryService#getGeometry(DirectedElement)}
	 * 
	 * @param directedEdge
	 *            the {@link DirectedEdge}
	 * @return the line string geometry
	 */
	public static LineString getGeometry(
			final DirectedElement<GEO_Kante> directedEdge) {
		return Services.getGeometryService().getGeometry(directedEdge);
	}

	/**
	 * @return {@link GeoKanteGeometryService#isFindGeometryComplete()}
	 */
	public static boolean isFindGeometryComplete() {
		return Services.getGeometryService().isFindGeometryComplete();
	}
}
