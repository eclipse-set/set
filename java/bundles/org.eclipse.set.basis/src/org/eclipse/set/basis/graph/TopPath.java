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
package org.eclipse.set.basis.graph;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante;

/**
 * Helper class to represent a path of edges on the topological graph Not a Java
 * record due to Xtend limitations
 */
public class TopPath {
	private final List<TOP_Kante> edges;
	private final BigDecimal length;

	/**
	 * @param edges
	 *            ordered list of edges for this path
	 * @param length
	 *            the total length of the path. may be less than the total
	 *            length of the edges if the path does not cover the full extent
	 *            of the edges
	 */
	public TopPath(final List<TOP_Kante> edges, final BigDecimal length) {
		this.edges = edges;
		this.length = length;
	}

	/**
	 * @return the edges of the path
	 */
	public List<TOP_Kante> edges() {
		return edges;
	}

	/**
	 * @return the total length of the path. may be less than the total length
	 *         of the edges if the path does not cover the full extent of the
	 *         edges
	 */
	public BigDecimal length() {
		return length;
	}

	/**
	 * @param point
	 *            a point to find the distance for
	 * @return distance from the start of the path, or empty if the point is not
	 *         on the path
	 */
	public Optional<BigDecimal> getDistance(final TopPoint point) {
		BigDecimal offset = BigDecimal.ZERO;

		TOP_Kante previousEdge = null;
		for (final TOP_Kante edge : edges()) {
			// If the point is on the current edge, check which direction to add
			if (point.edge().equals(edge)) {
				// in top direction?
				if (previousEdge == null
						|| previousEdge.getIDTOPKnotenB() == edge
								.getIDTOPKnotenA()
						|| previousEdge.getIDTOPKnotenA() == edge
								.getIDTOPKnotenA()) {
					return Optional.of(offset.add(point.distance()));
				}

				// against top direction
				return Optional.of(offset
						.add(edge.getTOPKanteAllg().getTOPLaenge().getWert())
						.subtract(point.distance()));

			}

			// Point not on this edge, check next edge
			previousEdge = edge;
			offset = offset
					.add(edge.getTOPKanteAllg().getTOPLaenge().getWert());
		}
		return Optional.empty();

	}

}
