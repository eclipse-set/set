/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.application.geometry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.set.basis.geometry.GEOKanteMetadata;
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante;
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;

/**
 * Store GeoKanteGeometry information for each session
 */
public class GeoKanteGeometrySessionData {

	private final Map<GEO_Kante, LineString> edgeGeometry;
	private final Map<String, List<GEOKanteMetadata>> geoKanteMetadas;

	private final Map<GEO_Knoten, Coordinate> geoNodeCoordinates;

	/**
	 * constructor
	 */
	public GeoKanteGeometrySessionData() {
		edgeGeometry = new ConcurrentHashMap<>();
		geoKanteMetadas = new ConcurrentHashMap<>();
		geoNodeCoordinates = new ConcurrentHashMap<>();
	}

	/**
	 * @return the already found geometry
	 */
	public Map<GEO_Kante, LineString> getEdgeGeometry() {
		return edgeGeometry;
	}

	/**
	 * @return the already found {@link GEOKanteMetadata}k
	 */
	public Map<String, List<GEOKanteMetadata>> getGeoKanteMetadas() {
		return geoKanteMetadas;
	}

	/**
	 * @return the geometry coordinate of {@link GEO_Knoten}
	 */
	public Map<GEO_Knoten, Coordinate> getGeoNodeCoordinates() {
		return geoNodeCoordinates;
	}

	/**
	 * Clear data
	 */
	public void clear() {
		edgeGeometry.clear();
		geoKanteMetadas.clear();
		geoNodeCoordinates.clear();
	}
}
