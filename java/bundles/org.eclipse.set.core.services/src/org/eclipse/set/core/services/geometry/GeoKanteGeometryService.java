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
package org.eclipse.set.core.services.geometry;

import java.math.BigDecimal;
import java.util.List;

import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.geometry.GEOKanteCoordinate;
import org.eclipse.set.basis.geometry.GEOKanteMetadata;
import org.eclipse.set.basis.graph.DirectedElement;
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante;
import org.eclipse.set.model.planpro.Geodaten.Strecke;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;

/**
 * 
 */
public interface GeoKanteGeometryService {
	/**
	 * @param edge
	 *            the geo kante
	 * @return the line string of this GEO Kante
	 */
	LineString getGeometry(final GEO_Kante edge);

	/**
	 * @param directedEdge
	 *            the GEO_Kante with direction
	 * @return the line string of this GEO Kante (with the implied direction of
	 *         the GEO Kante)
	 */
	LineString getGeometry(final DirectedElement<GEO_Kante> directedEdge);

	/**
	 * @param punktObjekt
	 *            the punkt objekt
	 * @param distance
	 *            the distance from the punktObjekt
	 * @return the coordinate at punkt objekt with the given distance as offset
	 */
	GEOKanteCoordinate getCoordinateAt(Punkt_Objekt punktObjekt,
			BigDecimal distance);

	/**
	 * @param topKante
	 *            the TOP_Kante to consider
	 * @param start
	 *            the start of the TOP_Kante from which to measure the distance
	 * @param distance
	 *            the distance from the start of the TOP_Kante
	 * @param lateralDistance
	 *            the lateral distance from the track
	 * @param wirkrichtung
	 *            the direction on the track
	 * @return the coordinate
	 */
	GEOKanteCoordinate getCoordinate(TOP_Kante topKante, TOP_Knoten start,
			BigDecimal distance, BigDecimal lateralDistance,
			ENUMWirkrichtung wirkrichtung);

	/**
	 * Returns the GEOKanteCoordinate for a given distance on this GEO_Kante
	 * 
	 * @param geoKante
	 *            the geokante metadata
	 * @param distance
	 *            the distance on the GEO_Kante as defined by the distance from
	 *            the start of the original TOP_Kante
	 * @param lateralDistance
	 *            the lateral distance to consider
	 * @param wirkrichtung
	 *            the direction of the object
	 * @return a GEOKanteCoordinate specifying the geographical position of a
	 *         point with the given distance and lateralDistance
	 * @if the geometry cannot be determined
	 */
	GEOKanteCoordinate getCoordinate(GEOKanteMetadata geoKante,
			BigDecimal distance, BigDecimal lateralDistance,
			ENUMWirkrichtung wirkrichtung);

	/**
	 * 
	 * @param geoKante
	 *            the GEO_Kante
	 * @return the reference GEOKanteMetadata of this GEO_Kante
	 */
	GEOKanteMetadata getGeoKanteMetaData(GEO_Kante geoKante);

	/**
	 * @param topKante
	 *            the TOP_Kante to consider
	 * @param topKnoten
	 *            the start of the TOP_Kante from which to measure the distance
	 * @param distance
	 *            the distance from the start of the TOP_Kante
	 * @return the GEO_Kante with metadata at the specified distance
	 */
	GEOKanteMetadata getGeoKanteAt(TOP_Kante topKante, TOP_Knoten topKnoten,
			BigDecimal distance);

	/**
	 * @param topKante
	 *            the TOP_Kante to consider
	 * @return a list of GEO_Kanten with metadata for the given TOP_Kante
	 */
	List<GEOKanteMetadata> getTopKantenMetaData(TOP_Kante topKante);

	/**
	 * @param strecke
	 *            the Strecke
	 * @return a list of GEO_Kanten with metadata for the given Strecke
	 */
	List<GEOKanteMetadata> getStreckeMetaData(Strecke strecke);

	/**
	 * 
	 * @return true, if find process is done
	 */
	boolean isFindGeometryComplete();

	/**
	 * Determine the projection coordinate of a Point on the {@link TOP_Kante}
	 * and the topological distance of this coordinate to start node of the
	 * {@link TOP_Kante}
	 * 
	 * @param coordinate
	 *            the target coordinate
	 * @param topKante
	 *            the {@link TOP_Kante}
	 * @return Pair<GEOKanteCoordinate, BigDecimal>
	 */
	Pair<GEOKanteCoordinate, BigDecimal> getProjectionCoordinateOnTopKante(
			Coordinate coordinate, TOP_Kante topKante);

	/**
	 * Determine the projection coordinate of a Point on the {@link Strecke} and
	 * the topological distance of this coordinate to start node of the
	 * {@link Strecke}
	 * 
	 * @param coordinate
	 *            the target coordinate
	 * @param strecke
	 *            the {@link Strecke}
	 * @return Pair<GEOKanteCoordinate, BigDecimal>
	 */
	Pair<GEOKanteCoordinate, BigDecimal> getProjectionCoordinateOnStrecke(
			Coordinate coordinate, Strecke strecke);
}
