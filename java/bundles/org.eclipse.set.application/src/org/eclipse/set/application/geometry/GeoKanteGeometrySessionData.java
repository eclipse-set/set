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
import org.locationtech.jts.geom.LineString;

/**
 * Store GeoKanteGeometry information for each session
 */
public class GeoKanteGeometrySessionData {

	private final Map<GEO_Kante, LineString> edgeGeometry;
	private final Map<String, List<GEOKanteMetadata>> geoKanteMetadas;

	/**
	 * constructor
	 */
	public GeoKanteGeometrySessionData() {
		edgeGeometry = new ConcurrentHashMap<>();
		geoKanteMetadas = new ConcurrentHashMap<>();
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
}
