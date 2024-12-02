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

import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;

/**
 * Helper class to represent a path of edges on the topological graph Not a Java
 * record due to Xtend limitations
 */
public class TopPath {
	private final List<TOP_Kante> edges;
	private final BigDecimal length;
	private final BigDecimal firstEdgeLength;

	/**
	 * @param edges
	 *            ordered list of edges for this path
	 * @param length
	 *            the total length of the path. may be less than the total
	 *            length of the edges if the path does not cover the full extent
	 *            of the edges
	 * @param firstEdgeLength
	 *            the length of the first edge
	 */
	public TopPath(final List<TOP_Kante> edges, final BigDecimal length,
			final BigDecimal firstEdgeLength) {
		this.edges = edges;
		this.length = length;
		this.firstEdgeLength = firstEdgeLength;
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
	 * @return the length off the first Edges
	 */
	public BigDecimal firstEdgeLength() {
		return firstEdgeLength;
	}

	/**
	 * @param point
	 *            a point to find the distance for
	 * @return distance from the start of the path, or empty if the point is not
	 *         on the path
	 */
	public Optional<BigDecimal> getDistance(final TopPoint point) {
		BigDecimal pathOffset = this.firstEdgeLength;

		TOP_Kante previousEdge = null;
		for (final TOP_Kante edge : edges()) {
			final BigDecimal edgeLength = edge.getTOPKanteAllg().getTOPLaenge()
					.getWert();

			// If the point is on the current edge, check which direction to add
			if (point.edge().equals(edge)) {
				// In top direction
				if (previousEdge == null
						|| previousEdge.getIDTOPKnotenB().getValue() == edge
								.getIDTOPKnotenA().getValue()
						|| previousEdge.getIDTOPKnotenA().getValue() == edge
								.getIDTOPKnotenA().getValue()) {
					final BigDecimal pointDistance = pathOffset
							.add(point.distance());
					if (pointDistance.compareTo(BigDecimal.ZERO) < 0
							|| pointDistance.compareTo(length) > 0) {
						return Optional.empty();
					}

					return Optional.of(pointDistance);
				}

				// against top direction
				final BigDecimal pointDistance = pathOffset.add(edgeLength)
						.subtract(point.distance());
				if (pointDistance.compareTo(BigDecimal.ZERO) < 0
						|| pointDistance.compareTo(length) > 0) {
					return Optional.empty();
				}

				return Optional.of(pointDistance);
			}

			// Point not on this edge, check next edge
			if (previousEdge != null) {
				pathOffset = pathOffset.add(edgeLength);
			}
			previousEdge = edge;

		}
		return Optional.empty();
	}
}
