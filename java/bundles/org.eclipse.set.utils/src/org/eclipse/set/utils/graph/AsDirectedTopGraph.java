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
package org.eclipse.set.utils.graph;

import java.util.HashMap;

import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante;
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten;
import org.jgrapht.Graph;
import org.jgrapht.graph.AsWeightedGraph;
import org.jgrapht.graph.DirectedPseudograph;

/**
 * Helper class to convert an undirected graph of top edges to a directed graph
 */
public class AsDirectedTopGraph {

	/**
	 * Helper record for providing a TOP_Kante with a direction
	 * 
	 * @param edge
	 *            the edge
	 * @param inTopDirection
	 *            whether the direction follows the top direction
	 * 
	 */
	public record DirectedTOPEdge(TOP_Kante edge, boolean inTopDirection) {
	}

	/**
	 * Returns the top graph as a directed graph
	 * 
	 * @param base
	 *            undirected top graph to base off
	 * @return a graph with all edges directed
	 */
	public static Graph<TOP_Knoten, DirectedTOPEdge> asDirectedTopGraph(
			final Graph<TOP_Knoten, TOP_Kante> base) {
		final Graph<TOP_Knoten, DirectedTOPEdge> graph = new AsWeightedGraph<>(
				new DirectedPseudograph<>(DirectedTOPEdge.class),
				new HashMap<>());

		base.vertexSet().forEach(graph::addVertex);
		base.edgeSet().forEach(edge -> {
			final DirectedTOPEdge e1 = new DirectedTOPEdge(edge, true);
			graph.addEdge(edge.getIDTOPKnotenA(), edge.getIDTOPKnotenB(), e1);
			graph.setEdgeWeight(e1, base.getEdgeWeight(edge));
			final DirectedTOPEdge e2 = new DirectedTOPEdge(edge, false);
			graph.addEdge(edge.getIDTOPKnotenB(), edge.getIDTOPKnotenA(), e2);
			graph.setEdgeWeight(e2, base.getEdgeWeight(edge));
		});

		return graph;
	}

}