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

import org.eclipse.set.basis.graph.DirectedElement;
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante;
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
	 * @return true, if find process is done
	 */
	boolean isFindGeometryComplete();
}
