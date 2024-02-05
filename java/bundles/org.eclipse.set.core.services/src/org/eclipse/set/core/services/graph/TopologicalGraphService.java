/********************************************************************************
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0 
 * 
 ********************************************************************************/
package org.eclipse.set.core.services.graph;

import java.util.OptionalDouble;

import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt;
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante;

/**
 * @author Stuecker
 *
 */
public interface TopologicalGraphService {

	/**
	 * Helper record to indicate a point on a TOP_Kante
	 * 
	 * @param edge
	 *            the TOP_Kante
	 * @param distance
	 *            the distance from TOP_Kante.IDTOPKnotenA
	 */
	record TopPoint(TOP_Kante edge, double distance) {
		public TopPoint(final Punkt_Objekt po) {
			this(po.getPunktObjektTOPKante().get(0));
		}

		public TopPoint(final Punkt_Objekt_TOP_Kante_AttributeGroup potk) {
			this(potk.getIDTOPKante(),
					potk.getAbstand().getWert().doubleValue());
		}
	}

	/**
	 * @param from
	 *            starting point to search from
	 * @param to
	 *            end point to search towards
	 * @param searchInTopDirection
	 *            whether to start the search in the top direction of 'from' or
	 *            against it
	 * @return the shortest distance between from and to, or empty if no path is
	 *         found
	 */
	OptionalDouble findShortestPathInDirection(final TopPoint from,
			final TopPoint to, final boolean searchInTopDirection);

	/**
	 * @param from
	 *            starting point to search from
	 * @param to
	 *            end point to search towards
	 * @return the shortest distance between from and to, or empty if no path is
	 *         found
	 */
	OptionalDouble findShortestPath(final TopPoint from, final TopPoint to);

}
