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

import org.eclipse.set.utils.graph.AsSplitTopGraph.Edge;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Node;
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
	public record DirectedTOPEdge(Edge edge, boolean inTopDirection) {
	}

	/**
	 * Returns the top graph as a directed graph
	 * 
	 * @param base
	 *            undirected top graph to base off
	 * @return a graph with all edges directed
	 */
	public static Graph<Node, DirectedTOPEdge> asDirectedTopGraph(
			final Graph<Node, Edge> base) {
		final Graph<Node, DirectedTOPEdge> graph = new AsWeightedGraph<>(
				new DirectedPseudograph<>(DirectedTOPEdge.class),
				new HashMap<>());

		base.vertexSet().forEach(graph::addVertex);
		base.edgeSet().forEach(edge -> {
			final DirectedTOPEdge e1 = new DirectedTOPEdge(edge, true);
			graph.addEdge(base.getEdgeSource(edge), base.getEdgeTarget(edge),
					e1);
			graph.setEdgeWeight(e1, base.getEdgeWeight(edge));
			final DirectedTOPEdge e2 = new DirectedTOPEdge(edge, false);
			graph.addEdge(base.getEdgeTarget(edge), base.getEdgeSource(edge),
					e2);
			graph.setEdgeWeight(e2, base.getEdgeWeight(edge));
		});

		return graph;
	}

}