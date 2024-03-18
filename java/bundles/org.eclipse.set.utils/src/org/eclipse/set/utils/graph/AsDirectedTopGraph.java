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

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.Predicate;

import org.eclipse.set.basis.graph.TopPath;
import org.eclipse.set.basis.graph.TopPoint;
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
	 * @param <T>
	 *            edge type
	 * 
	 * @param edge
	 *            the edge
	 * @param inTopDirection
	 *            whether the direction follows the top direction
	 * 
	 */
	public record DirectedTOPEdge<T>(T edge, boolean inTopDirection) {
	}

	/**
	 * Returns the top graph as a directed graph
	 * 
	 * @param base
	 *            undirected top graph to base off
	 * @return a graph with all edges directed
	 */
	public static Graph<Node, DirectedTOPEdge<Edge>> asDirectedTopGraph(
			final Graph<Node, Edge> base) {
		final Graph<Node, DirectedTOPEdge<Edge>> graph = new AsWeightedGraph<>(
				new DirectedPseudograph<>(DirectedTOPEdge.class),
				new HashMap<>());

		base.vertexSet().forEach(graph::addVertex);
		base.edgeSet().forEach(edge -> {
			final DirectedTOPEdge<Edge> e1 = new DirectedTOPEdge<>(edge, true);
			graph.addEdge(base.getEdgeSource(edge), base.getEdgeTarget(edge),
					e1);
			graph.setEdgeWeight(e1, base.getEdgeWeight(edge));
			final DirectedTOPEdge<Edge> e2 = new DirectedTOPEdge<>(edge, false);
			graph.addEdge(base.getEdgeTarget(edge), base.getEdgeSource(edge),
					e2);
			graph.setEdgeWeight(e2, base.getEdgeWeight(edge));
		});

		return graph;
	}

	/**
	 * Find relevant path from two {@link TopPoint}
	 * 
	 * @param graph
	 *            the graph
	 * @param from
	 *            the start point
	 * @param to
	 *            the end point
	 * @param maxPathWeight
	 *            max weight of path
	 * @param relevantCondition
	 *            condition for the relevant path
	 * @return the path
	 */
	public static TopPath getPath(final AsSplitTopGraph graph,
			final TopPoint from, final TopPoint to, final int maxPathWeight,
			final Predicate<TopPath> relevantCondition) {
		final Node startNode = graph.splitGraphAt(from);
		final Node endNode = graph.splitGraphAt(to);
		final Graph<Node, DirectedTOPEdge<Edge>> directedTopGraph = AsDirectedTopGraph
				.asDirectedTopGraph(graph);
		return getPath(directedTopGraph, startNode, endNode, maxPathWeight,
				relevantCondition);
	}

	/**
	 * Find relevant path from two {@link Node}
	 * 
	 * @param graph
	 *            the graph
	 * @param startNode
	 *            the start Node
	 * @param endNode
	 *            the end Node
	 * @param maxPathWeight
	 *            max weight of path
	 * @param relevantCondition
	 *            condition for the relevant path
	 * @return the path
	 */
	public static TopPath getPath(
			final Graph<Node, DirectedTOPEdge<Edge>> graph,
			final Node startNode, final Node endNode, final int maxPathWeight,
			final Predicate<TopPath> relevantCondition) {
		if (maxPathWeight <= 0) {
			throw new IllegalArgumentException(
					"maxPathWeight must be greater als 0"); //$NON-NLS-1$
		}
		// When start and end node are same
		if (startNode == endNode) {
			final DirectedPath directedPath = new DirectedPath(graph, startNode,
					endNode, maxPathWeight);

			final TopPath topPath = directedPath.getTopPath(relevantCondition);
			if (topPath != null) {
				return topPath;
			}
		}

		final Map<DirectedTOPEdge<Edge>, Integer> edgeWeightBackwards = edgeMinDistancesBackwards(
				graph, endNode, maxPathWeight);
		for (final DirectedTOPEdge<Edge> edge : graph
				.outgoingEdgesOf(startNode)) {
			final DirectedPath path = new DirectedPath(graph, startNode,
					endNode, maxPathWeight);
			path.addEdge(edge);
			final TopPath topPath = getPath(path, edgeWeightBackwards,
					relevantCondition);
			if (topPath != null) {
				return topPath;
			}
		}
		return null;
	}

	private static TopPath getPath(final DirectedPath incomplePath,
			final Map<DirectedTOPEdge<Edge>, Integer> edgeWeightBackwards,
			final Predicate<TopPath> relevantCondition) {
		final Deque<DirectedPath> incompletePaths = new LinkedList<>();
		incompletePaths.add(incomplePath);
		final Graph<Node, DirectedTOPEdge<Edge>> graph = incomplePath.graph();
		// Walkthrough graph to find relevant path
		for (DirectedPath path; (path = incompletePaths.poll()) != null;) {
			final DirectedTOPEdge<Edge> lastEdge = path.path().getLast();
			final Node lastNode = graph.getEdgeTarget(lastEdge);
			for (final DirectedTOPEdge<Edge> edge : graph
					.outgoingEdgesOf(lastNode)) {
				if (edgeWeightBackwards.containsKey(edge)
						&& path.isRelevantPathWeight(edgeWeightBackwards
								.get(edge).intValue())) {
					final DirectedPath newPath = path.clonePath();
					if (!newPath.addEdge(edge)) {
						continue;
					}

					final TopPath topPath = newPath
							.getTopPath(relevantCondition);
					if (topPath != null) {
						return topPath;
					}

					if (newPath.isRelevantPathLength()) {
						incompletePaths.addFirst(newPath);
					}
				}
			}
		}
		return null;
	}

	/**
	 * Find all edge around end node, which total weight smaller than max path
	 * weight
	 * 
	 * @param maxPathWeight
	 * @return relevant edges around end node
	 */
	private static Map<DirectedTOPEdge<Edge>, Integer> edgeMinDistancesBackwards(
			final Graph<Node, DirectedTOPEdge<Edge>> graph, final Node target,
			final int maxPathWeight) {
		final Map<DirectedTOPEdge<Edge>, Integer> edgesDistance = new HashMap<>();
		final Map<Node, Integer> nodeWithDistanceFromEnd = new HashMap<>();
		final Queue<Node> nodesToProcess = new ArrayDeque<>();

		nodesToProcess.add(target);
		nodeWithDistanceFromEnd.put(target, Integer.valueOf(0));
		for (Node vertex; (vertex = nodesToProcess.poll()) != null;) {
			assert nodeWithDistanceFromEnd.containsKey(vertex);
			final int currentDistance = nodeWithDistanceFromEnd.get(vertex)
					.intValue();

			// Check whether the incoming edges of this node are correctly
			// decorated
			for (final DirectedTOPEdge<Edge> edge : graph
					.incomingEdgesOf(vertex)) {
				// Mark the edge if needed
				final BigDecimal weight = edge.edge().getWeight();
				final int childDistance = weight
						.add(BigDecimal.valueOf(currentDistance)).toBigInteger()
						.intValue();
				if (childDistance > maxPathWeight) {
					continue;
				}
				if (edgesDistance
						.computeIfAbsent(edge,
								t -> Integer.valueOf(childDistance))
						.intValue() > childDistance) {
					edgesDistance.put(edge, Integer.valueOf(childDistance));
				}

				// Mark the edge's source vertex if needed
				final Node edgeSource = graph.getEdgeSource(edge);
				if (!nodeWithDistanceFromEnd.containsKey(edgeSource)
						|| nodeWithDistanceFromEnd.get(edgeSource)
								.intValue() > childDistance) {
					nodeWithDistanceFromEnd.put(edgeSource,
							Integer.valueOf(childDistance));
					nodesToProcess.add(edgeSource);
				}
			}
		}
		assert nodesToProcess.isEmpty();
		return edgesDistance;
	}
}