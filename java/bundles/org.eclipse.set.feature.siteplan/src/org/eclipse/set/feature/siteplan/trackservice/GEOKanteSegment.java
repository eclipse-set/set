/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.trackservice;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.set.toolboxmodel.Basisobjekte.Bereich_Objekt;
import org.locationtech.jts.geom.Coordinate;

/**
 * A segment on a GEO_Kante with a list of relevant Bereich_Objekte for the
 * GEO_Kante
 * 
 * @author Stuecker
 *
 */
public class GEOKanteSegment {
	private final Set<Bereich_Objekt> bereichObjekte;
	private double length;
	private double start;

	/**
	 * @param start
	 *            the start of the segment relevant to the top level TOP_Kante
	 * @param length
	 *            the length of the segment
	 */
	GEOKanteSegment(final double start, final double length) {
		this.start = start;
		this.length = length;
		this.bereichObjekte = new HashSet<>();
	}

	/**
	 * Copy constructor
	 * 
	 * @param other
	 *            the object to copy from
	 */
	GEOKanteSegment(final GEOKanteSegment other) {
		this.length = other.length;
		this.start = other.start;
		this.bereichObjekte = new HashSet<>(other.bereichObjekte);
	}

	/**
	 * @return all Bereich_Objekte covering this segment
	 */
	public Set<Bereich_Objekt> getBereichObjekte() {
		return bereichObjekte;
	}

	/**
	 * @return the length of the segment
	 */
	public double getLength() {
		return length;
	}

	/**
	 * @return the start of the segment as defined by the distance from the
	 *         start of the original TOP_Kante that was used to determine this
	 *         segment object
	 */
	public double getStart() {
		return start;
	}

	/**
	 * @return the end of the segment as defined per {@link #getStart()} +
	 *         {@link #getLength()}
	 */
	public double getEnd() {
		return start + length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public void setLength(final double length) {
		this.length = length;
	}

	/**
	 * @param start
	 *            the start distance to set
	 */
	public void setStart(final double start) {
		this.start = start;
	}

	/**
	 * @param geoKante
	 *            the metadata for the GEO_Kante
	 * @return a list of coordinates representing this segment @ if the
	 *         coordinates could not be determined
	 */
	public List<GEOKanteCoordinate> getCoordinates(
			final GEOKanteMetadata geoKante) {
		final List<GEOKanteCoordinate> result = new ArrayList<>();

		// Determine the coordinates in the geometry which are strictly within
		// this segment
		Coordinate lastCoordinate = null;
		double distance = geoKante.getStart();
		for (final Coordinate coordinate : geoKante.getGeometry()
				.getCoordinates()) {
			// Initialize the previous coordinate
			if (lastCoordinate == null) {
				lastCoordinate = coordinate;
				// The first (and last) coordinate is never strictly within a
				// segment
				// so it does not get added to the resulting list
				continue;
			}

			// Skip coordinates before the segment
			if (distance < getStart()) {
				distance += lastCoordinate.distance(coordinate);
				lastCoordinate = coordinate;
				continue;
			}

			// Abort if we have processed all coordinates within the segment
			if (distance > getEnd()) {
				break;
			}

			if (result.isEmpty()) {
				// Add the first coordinate
				result.add(new GEOKanteCoordinate(lastCoordinate,
						bereichObjekte, geoKante.getCRS()));
			}

			// Add the coordinate
			result.add(new GEOKanteCoordinate(coordinate, bereichObjekte,
					geoKante.getCRS()));
			distance += lastCoordinate.distance(coordinate);
			lastCoordinate = coordinate;
		}

		return result;
	}
}
