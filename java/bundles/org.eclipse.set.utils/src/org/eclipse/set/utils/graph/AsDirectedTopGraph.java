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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.set.basis.graph.TopPath;
import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.model.planpro.Geodaten.ENUMTOPAnschluss;
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Edge;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Node;
import org.eclipse.set.utils.math.BigDecimalExtensions;
import org.jgrapht.Graph;
import org.jgrapht.graph.AsWeightedGraph;
import org.jgrapht.graph.DirectedPseudograph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class to convert an undirected graph of top edges to a directed graph
 */
public class AsDirectedTopGraph {
	private static final Logger logger = LoggerFactory
			.getLogger(AsDirectedTopGraph.class);

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
	 * Find relevant path from two {@link TopPoint}. The find process will
	 * cancel, when the path length greate than max path weigth
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
	 * Find relevant path from two {@link Node}. The find process will cancel,
	 * when the path length greate than max path weigth
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
			final DirectedPathSearch directedPath = new DirectedPathSearch(
					graph, startNode, endNode, maxPathWeight);

			final TopPath topPath = directedPath.getTopPath(relevantCondition,
					false);
			if (topPath != null) {
				return topPath;
			}
		}

		final List<DirectedTOPEdge<Edge>> relevantEdgesFromTarget = findRelevantEdgesFromTarget(
				graph, endNode, maxPathWeight);
		for (final DirectedTOPEdge<Edge> edge : graph
				.outgoingEdgesOf(startNode)) {
			final DirectedPathSearch path = new DirectedPathSearch(graph,
					startNode, endNode, maxPathWeight);
			path.addEdge(edge);
			final List<TopPath> topPaths = getPath(path,
					relevantEdgesFromTarget, relevantCondition, false);
			if (!topPaths.isEmpty()) {
				return topPaths.getFirst();
			}
		}
		return null;
	}

	/**
	 * Find all paths from two {@link Node}, include incomplete paths. When the
	 * path length greater than the max path weight, then break find process and
	 * give the incomplete path back.
	 * 
	 * @param graph
	 *            the graph
	 * @param from
	 *            the start point
	 * @param to
	 *            the end point
	 * @param maxPathWeight
	 *            max weight of path
	 * @return the list of path
	 */
	public static List<TopPath> getAllPaths(final AsSplitTopGraph graph,
			final TopPoint from, final TopPoint to, final int maxPathWeight) {
		final Node startNode = graph.splitGraphAt(from);
		final Node endNode = graph.splitGraphAt(to);
		final Graph<Node, DirectedTOPEdge<Edge>> directedTopGraph = AsDirectedTopGraph
				.asDirectedTopGraph(graph);

		// When start and end node are same
		if (startNode == endNode) {
			final DirectedPathSearch directedPath = new DirectedPathSearch(
					directedTopGraph, startNode, endNode, maxPathWeight);
			final TopPath topPath = directedPath.getTopPath(null, true);
			if (topPath != null) {
				return List.of(topPath);
			}
		}
		final List<TopPath> result = new ArrayList<>();
		for (final DirectedTOPEdge<Edge> edge : directedTopGraph
				.outgoingEdgesOf(startNode)) {
			final DirectedPathSearch path = new DirectedPathSearch(
					directedTopGraph, startNode, endNode, maxPathWeight);
			path.addEdge(edge);
			final List<TopPath> topPath = getPath(path, Collections.emptyList(),
					null, true);
			result.addAll(topPath);
		}

		return result;
	}

	private static List<TopPath> getPath(
			final DirectedPathSearch incompletePath,
			final List<DirectedTOPEdge<Edge>> relevantEdgesFromTarget,
			final Predicate<TopPath> relevantCondition,
			final boolean includeIncompletePath) {
		final List<TopPath> result = new ArrayList<>();
		final Deque<DirectedPathSearch> incompletePaths = new LinkedList<>();
		incompletePaths.add(incompletePath);
		final Graph<Node, DirectedTOPEdge<Edge>> graph = incompletePath.graph();
		// Walkthrough graph to find relevant path
		for (DirectedPathSearch path; (path = incompletePaths
				.poll()) != null;) {
			final DirectedTOPEdge<Edge> lastEdge = path.path().getLast();
			final Node lastNode = graph.getEdgeTarget(lastEdge);
			for (final DirectedTOPEdge<Edge> edge : graph
					.outgoingEdgesOf(lastNode)) {
				if (!isRelevantNextEdge(lastEdge, edge, lastNode)) {
					continue;
				}
				if (!includeIncompletePath) {
					final TopPath shortesPath = getShortesPath(incompletePaths,
							path, edge, relevantEdgesFromTarget,
							relevantCondition);
					if (shortesPath != null) {
						return List.of(shortesPath);
					}
				} else {
					getAllPaths(result, incompletePaths, path, edge);
				}

			}
		}
		return result;
	}

	private static TopPath getShortesPath(
			final Deque<DirectedPathSearch> incompletePaths,
			final DirectedPathSearch path, final DirectedTOPEdge<Edge> edge,
			final List<DirectedTOPEdge<Edge>> relevantEdgesFromTarget,
			final Predicate<TopPath> relevantCondition) {
		if (relevantEdgesFromTarget.contains(edge)
				&& path.isRelevantPathWeight(edge.edge().getWeight())) {
			final DirectedPathSearch newPath = path.clonePath();
			if (!newPath.addEdge(edge)) {
				return null;
			}

			final TopPath topPath = newPath.getTopPath(relevantCondition,
					false);
			if (topPath != null) {
				return topPath;
			}

			if (newPath.isRelevantPathLength()) {
				incompletePaths.addFirst(newPath);
			}
		}
		return null;
	}

	private static void getAllPaths(final List<TopPath> result,
			final Deque<DirectedPathSearch> incompletePaths,
			final DirectedPathSearch path, final DirectedTOPEdge<Edge> edge) {
		final DirectedPathSearch newPath = path.clonePath();
		if (!newPath.addEdge(edge)) {
			return;
		}
		final TopPath topPath = newPath.getTopPath(null, false);
		if (topPath != null) {
			result.add(topPath);
			return;
		}
		if (newPath.isRelevantPathLength()) {
			incompletePaths.addFirst(newPath);
		} else {
			result.add(newPath.getTopPath(null, true));
		}
	}

	/**
	 * Find all edge around end node, which total weight smaller than max path
	 * weight
	 * 
	 * @param maxPathWeight
	 * @return relevant edges around end node
	 */
	private static List<DirectedTOPEdge<Edge>> findRelevantEdgesFromTarget(
			final Graph<Node, DirectedTOPEdge<Edge>> graph, final Node target,
			final int maxPathWeight) {
		final List<DirectedTOPEdge<Edge>> edgesDistance = new ArrayList<>();
		final Map<Node, BigDecimal> remainingWeigthFromEnd = new HashMap<>();
		final Queue<Node> nodesToProcess = new ArrayDeque<>();
		nodesToProcess.add(target);
		final BigDecimal tmpMaxWeight = BigDecimalExtensions
				.toBigDecimal(Integer.valueOf(maxPathWeight));
		remainingWeigthFromEnd.put(target, tmpMaxWeight);
		for (Node vertex; (vertex = nodesToProcess.poll()) != null;) {
			assert remainingWeigthFromEnd.containsKey(vertex);
			final BigDecimal currentRemaining = remainingWeigthFromEnd
					.get(vertex);

			// Check whether the incoming edges of this node are correctly
			// decorated
			final Set<DirectedTOPEdge<Edge>> incomingEdgesOf = graph
					.incomingEdgesOf(vertex);
			for (final DirectedTOPEdge<Edge> edge : incomingEdgesOf) {
				// Mark the edge if needed
				final BigDecimal weight = edge.edge().getWeight();
				final BigDecimal remainingWeight = currentRemaining
						.subtract(weight);

				if (remainingWeight.compareTo(BigDecimal.ZERO) < 1) {
					continue;
				}
				edgesDistance.add(edge);
				// Mark the edge's source vertex if needed
				final Node edgeSource = graph.getEdgeSource(edge);
				remainingWeigthFromEnd.computeIfAbsent(edgeSource, v -> {
					nodesToProcess.add(edgeSource);
					return remainingWeight;
				});

				// When another edge exists, which is shorter than found edge,
				// replace the weight
				remainingWeigthFromEnd.computeIfPresent(edgeSource,
						(k, v) -> remainingWeight.compareTo(v) > 0
								? remainingWeight
								: v);
			}
		}
		assert nodesToProcess.isEmpty();
		return edgesDistance;
	}

	/**
	 * The connect type between to edge must plausible
	 * 
	 * @param current
	 *            the current edge
	 * @param next
	 *            the next edge
	 * @param connectNode
	 *            the connect node
	 * @return whether connect relevant
	 */
	private static boolean isRelevantNextEdge(
			final DirectedTOPEdge<Edge> current,
			final DirectedTOPEdge<Edge> next, final Node connectNode) {
		if (current.edge() == next.edge()) {
			return true;
		}
		if (connectNode.node() == null) {
			return false;
		}
		final Function<Edge, Optional<ENUMTOPAnschluss>> getTopConnectType = edge -> {
			try {
				final TOP_Knoten topNodeA = edge.edge()
						.getIDTOPKnotenA()
						.getValue();
				final TOP_Knoten topNodeB = edge.edge()
						.getIDTOPKnotenB()
						.getValue();
				if (topNodeA == connectNode.node()) {
					return Optional.ofNullable(edge.edge()
							.getTOPKanteAllg()
							.getTOPAnschlussA()
							.getWert());
				}

				if (topNodeB == connectNode.node()) {
					return Optional.ofNullable(edge.edge()
							.getTOPKanteAllg()
							.getTOPAnschlussB()
							.getWert());
				}
				throw new IllegalArgumentException();
			} catch (final Exception e) {
				logger.error(
						"Can't find TOP_Anschlus of TOP_Kanten: {} at TOP_Knoten: {}", //$NON-NLS-1$
						edge.edge().getIdentitaet().getWert(),
						connectNode.node().getIdentitaet().getWert());
				return Optional.empty();
			}
		};
		final ENUMTOPAnschluss currentConnectType = getTopConnectType
				.apply(current.edge())
				.orElse(null);
		final ENUMTOPAnschluss nextConnectType = getTopConnectType
				.apply(next.edge())
				.orElse(null);
		if (currentConnectType == null || nextConnectType == null) {
			return false;
		}

		return switch (currentConnectType) {
			case ENUMTOP_ANSCHLUSS_LINKS, ENUMTOP_ANSCHLUSS_RECHTS -> nextConnectType == ENUMTOPAnschluss.ENUMTOP_ANSCHLUSS_SPITZE;
			case ENUMTOP_ANSCHLUSS_SPITZE -> nextConnectType == ENUMTOPAnschluss.ENUMTOP_ANSCHLUSS_LINKS
					|| nextConnectType == ENUMTOPAnschluss.ENUMTOP_ANSCHLUSS_RECHTS;
			case ENUMTOP_ANSCHLUSS_MERIDIANSPRUNG -> nextConnectType == ENUMTOPAnschluss.ENUMTOP_ANSCHLUSS_MERIDIANSPRUNG;
			case ENUMTOP_ANSCHLUSS_SCHNITT -> nextConnectType == ENUMTOPAnschluss.ENUMTOP_ANSCHLUSS_SCHNITT;
			default -> false;
		};
	}
}