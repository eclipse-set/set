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
	private BigDecimal firstEdgeLength;
	private TopPoint startNode;

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
		this.startNode = null;
	}

	/**
	 * @param edges
	 *            ordered list of edges for this path
	 * @param length
	 *            the total length of the path. may be less than the total
	 *            length of the edges if the path does not cover the full extent
	 *            of the edges
	 * @param startNode
	 *            the start point of this path
	 */
	public TopPath(final List<TOP_Kante> edges, final BigDecimal length,
			final TopPoint startNode) {
		this.edges = edges;
		this.length = length;
		this.startNode = startNode;
		this.firstEdgeLength = null;
	}

	private TopPoint determineStartNode() {
		try {
			final TOP_Kante firstEdge = edges.getFirst();
			startNode = new TopPoint(firstEdge, firstEdge.getTOPKanteAllg()
					.getTOPLaenge().getWert().subtract(firstEdgeLength));
			return startNode;
		} catch (final Exception e) {
			throw new IllegalArgumentException(
					"Can\'t find start node of TopPath");
		}
	}

	private BigDecimal determintFirstEdgeLength() {
		try {
			firstEdgeLength = edges.getFirst().getTOPKanteAllg().getTOPLaenge()
					.getWert().subtract(startNode.distance());
			return firstEdgeLength;
		} catch (final Exception e) {
			throw new IllegalArgumentException(
					"Can\'t find first edge length of TopPath");
		}
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
		BigDecimal pathOffset = firstEdgeLength != null ? firstEdgeLength
				: determintFirstEdgeLength();
		final TopPoint startOfPath = startNode != null ? startNode
				: determineStartNode();
		TOP_Kante previousEdge = null;
		for (final TOP_Kante edge : edges()) {
			final BigDecimal edgeLength = edge.getTOPKanteAllg().getTOPLaenge()
					.getWert();

			// If the point is on the current edge, check which direction to add
			if (point.edge().equals(edge)) {
				// Point in first Edge
				if (previousEdge == null && point.edge() == edge) {
					// Point lie before path start
					if (point.distance()
							.compareTo(startOfPath.distance()) < 0) {
						return Optional.empty();
					}
					return Optional.of(
							point.distance().subtract(startOfPath.distance()));
				}
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
