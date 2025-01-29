/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.geometry;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt;
import org.eclipse.set.model.planpro.Geodaten.ENUMGEOKoordinatensystem;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;

/**
 * A coordinate on a GEO_Kante including information of this GEO_Kante
 * 
 * @author Stuecker
 *
 */
public class GEOKanteCoordinate {
	private static final double GEOMETRY_TOLERANCE = 1e-9;
	private final GEOKanteMetadata geoKante;
	private final GeoPosition position;
	private final ENUMGEOKoordinatensystem crs;

	private final Optional<BigDecimal> topDistance;

	private Set<Bereich_Objekt> bereichObjekt = null;

	/**
	 * @param position
	 *            the {@link GeoPosition}
	 * @param geoKante
	 *            the {@link GEOKanteMetadata}
	 * @param crs
	 *            the coordinate system
	 */
	public GEOKanteCoordinate(final GeoPosition position,
			final GEOKanteMetadata geoKante,
			final ENUMGEOKoordinatensystem crs) {
		this.position = position;
		this.geoKante = geoKante;
		this.crs = crs;
		this.topDistance = determineTopDistance(position.getCoordinate(),
				geoKante);
	}

	/**
	 * @param coordinate
	 *            the coordinate
	 * @param geoKante
	 *            the {@link Bereich_Objekt}
	 * @param crs
	 *            the coordinate system
	 */
	public GEOKanteCoordinate(final Coordinate coordinate,
			final GEOKanteMetadata geoKante,
			final ENUMGEOKoordinatensystem crs) {
		this.position = new GeoPosition(coordinate, 0, 0);
		this.geoKante = geoKante;
		this.crs = crs;
		this.topDistance = determineTopDistance(coordinate, geoKante);
	}

	/**
	 * @param coordinate
	 *            the coordinate
	 * @param geoKante
	 *            the {@link Bereich_Objekt}
	 * @param topDistance
	 *            the topological distance
	 * @param crs
	 *            the coordinate system
	 */
	public GEOKanteCoordinate(final Coordinate coordinate,
			final GEOKanteMetadata geoKante, final BigDecimal topDistance,
			final ENUMGEOKoordinatensystem crs) {
		this.position = new GeoPosition(coordinate, 0, 0);
		this.geoKante = geoKante;
		this.crs = crs;
		this.topDistance = Optional.of(topDistance);
	}

	private static Optional<BigDecimal> determineTopDistance(
			final Coordinate coordinate, final GEOKanteMetadata metadata) {
		final GeometryFactory geometryFactory = new GeometryFactory();
		final LineString geometry = metadata.getGeometry();
		// Determine the distance only when the coordinate lie on the GEO_Kante
		if (!geometry.isWithinDistance(geometryFactory.createPoint(coordinate),
				GEOMETRY_TOLERANCE)) {
			return Optional.empty();
		}
		BigDecimal distance = metadata.getStart();
		Coordinate currentCoordinate = null;
		for (final Coordinate nextCoordinate : geometry.getCoordinates()) {
			if (currentCoordinate == null) {
				currentCoordinate = nextCoordinate;
				continue;
			}

			final double distanceToNextCoord = currentCoordinate
					.distance(currentCoordinate);
			final double distanceToTargetCoord = currentCoordinate
					.distance(coordinate);
			if (isOnLine(coordinate, currentCoordinate, nextCoordinate)
					&& distanceToNextCoord >= distanceToTargetCoord) {
				return Optional.of(distance
						.add(BigDecimal.valueOf(distanceToTargetCoord)));
			}
			distance = distance.add(BigDecimal.valueOf(distanceToTargetCoord));
			currentCoordinate = nextCoordinate;
		}
		return Optional.empty();
	}

	private static boolean isOnLine(final Coordinate coordinate,
			final Coordinate coordinateA, final Coordinate coordianteB) {
		final GeometryFactory geometryFactory = new GeometryFactory();
		final LineString line = geometryFactory.createLineString(
				new Coordinate[] { coordinateA, coordianteB });
		final Point point = geometryFactory.createPoint(coordinate);
		return line.isWithinDistance(point, GEOMETRY_TOLERANCE);
	}

	/**
	 * @return a list of Bereich_Objekte which affect the coordinate
	 */
	public Set<Bereich_Objekt> getBereichObjekte() {
		if (bereichObjekt != null) {
			return bereichObjekt;
		}

		if (topDistance.isEmpty()) {
			return Collections.emptySet();
		}
		bereichObjekt = geoKante.segments.stream().filter(
				segment -> topDistance.get().compareTo(segment.getStart()) >= 0
						&& topDistance.get().compareTo(segment.getEnd()) <= 0)
				.flatMap(segment -> segment.getBereichObjekte().stream())
				.collect(Collectors.toSet());
		return bereichObjekt;
	}

	/**
	 * @return the {@link GeoPosition}
	 */
	public GeoPosition getGeoPosition() {
		return position;
	}

	/**
	 * @return the coordinate of the point in the coordinate reference system as
	 *         defined by {@link #getCRS()}
	 */
	public Coordinate getCoordinate() {
		return position.getCoordinate();
	}

	/**
	 * @return coordinate reference system for this point
	 */
	public ENUMGEOKoordinatensystem getCRS() {
		return crs;
	}

	/**
	 * @return rotation of the point
	 */
	public double getEffectiveRotation() {
		return position.getEffectiveRotation();
	}

	/**
	 * @return rotation of the point
	 */
	public double getTopologicalRotation() {
		return position.getTopologicalRotation();
	}
}