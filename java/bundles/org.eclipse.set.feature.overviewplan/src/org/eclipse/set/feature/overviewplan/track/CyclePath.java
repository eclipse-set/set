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
package org.eclipse.set.feature.overviewplan.track;

import java.util.List;

import org.eclipse.set.utils.graph.AsDirectedTopGraph.DirectedTOPEdge;

/**
 * Helper class to represent a path of edge, which start and end at this edge
 */
public class CyclePath {
	private final List<DirectedTOPEdge<TOPKanteMetaData>> edges;

	/**
	 * @param edges
	 *            ordered list of edges for this path
	 */
	public CyclePath(final List<DirectedTOPEdge<TOPKanteMetaData>> edges) {
		this.edges = edges;
	}

	/**
	 * @return the edges of this path
	 */
	public List<DirectedTOPEdge<TOPKanteMetaData>> getEdges() {
		return edges;
	}

	/**
	 * @return the edges, which same direction with first edges
	 */
	public List<TOPKanteMetaData> getSameDirectionEdge() {
		return edges.stream()
				.filter(edge -> edge.inTopDirection())
				.map(edge -> edge.edge())
				.toList();
	}

	/**
	 * @return the edges, which oposite direction with first edges
	 */
	public List<TOPKanteMetaData> getOpositeDirectionEdge() {
		return edges.stream()
				.filter(edge -> !edge.inTopDirection())
				.map(edge -> edge.edge())
				.toList();
	}

	/**
	 * @param edges
	 *            path
	 * @return sum of edge length
	 */
	@SuppressWarnings("boxing")
	public static int getPathLength(final List<TOPKanteMetaData> edges) {
		return edges.stream()
				.map(edge -> edge.getLength())
				.reduce(0, Integer::sum);
	}
}
