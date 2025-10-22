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
package org.eclipse.set.utils.graph;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.eclipse.set.basis.graph.TopPath;
import org.eclipse.set.utils.graph.AsDirectedTopGraph.DirectedTOPEdge;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Edge;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Node;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphWalk;

/**
 * Helper class to find directed path between two Node
 * 
 */
public class DirectedPathSearch {
	private final Graph<Node, DirectedTOPEdge<Edge>> graph;

	/**
	 * @return the graph
	 */
	public Graph<Node, DirectedTOPEdge<Edge>> graph() {
		return graph;
	}

	private final Node startNode;

	/**
	 * @return the start node
	 */
	public Node startNode() {
		return startNode;
	}

	private final Node endNode;

	/**
	 * @return the end node
	 */
	public Node endNode() {
		return endNode;
	}

	private final List<DirectedTOPEdge<Edge>> path;

	/**
	 * @return the path
	 */
	public List<DirectedTOPEdge<Edge>> path() {
		return path;
	}

	private final int maxPathWeight;

	/**
	 * @return the maxPathLength
	 */
	public int maxPathWeight() {
		return maxPathWeight;
	}

	private final Set<Node> pathNodes;

	/**
	 * @param graph
	 *            the graph
	 * @param startNode
	 *            the start node
	 * @param endNode
	 *            the end node
	 * @param path
	 *            the path
	 * @param maxPathLength
	 *            max weight of the path
	 */
	public DirectedPathSearch(final Graph<Node, DirectedTOPEdge<Edge>> graph,
			final Node startNode, final Node endNode,
			final List<DirectedTOPEdge<Edge>> path, final int maxPathLength) {
		this.graph = graph;
		this.startNode = startNode;
		this.endNode = endNode;
		this.path = path;
		this.maxPathWeight = maxPathLength;
		pathNodes = new HashSet<>();
		getPathNodes();

	}

	/**
	 * @param graph
	 *            the graph
	 * @param startNode
	 *            the start node
	 * @param endNode
	 *            the end node
	 * @param maxPathLength
	 *            max length of the path
	 */
	public DirectedPathSearch(final Graph<Node, DirectedTOPEdge<Edge>> graph,
			final Node startNode, final Node endNode, final int maxPathLength) {
		this(graph, startNode, endNode, new LinkedList<>(), maxPathLength);
	}

	/**
	 * @return new path
	 */
	public DirectedPathSearch clonePath() {
		return new DirectedPathSearch(graph, startNode, endNode,
				new LinkedList<>(path), maxPathWeight);
	}

	/**
	 * The path is complete, when the target node of last path edge equal end
	 * Node
	 * 
	 * @return true if path complete
	 */
	public boolean isCompletePath() {
		return startNode.equals(endNode) || !path.isEmpty()
				&& graph.getEdgeTarget(path.getLast()).equals(endNode);
	}

	/**
	 * Add new edge to the path
	 * 
	 * @param edge
	 *            the edge
	 * @return false if the path already contains the new edge target node
	 */
	public boolean addEdge(final DirectedTOPEdge<Edge> edge) {
		// The path shouldn't self intersecting
		if (getPathNodes().contains(graph.getEdgeTarget(edge))) {
			return false;
		}
		path.add(edge);
		pathNodes.add(graph.getEdgeSource(edge));
		pathNodes.add(graph.getEdgeTarget(edge));
		return true;
	}

	/**
	 * @return all nodes of the path
	 */
	public Set<Node> getPathNodes() {
		if (pathNodes.isEmpty()) {
			path.forEach(edge -> {
				pathNodes.add(graph.getEdgeSource(edge));
				pathNodes.add(graph.getEdgeTarget(edge));
			});
		}
		return pathNodes;
	}

	/**
	 * @return true, if maxPathLength not register or the path length <
	 *         maxPathLength
	 */
	public boolean isRelevantPathLength() {
		return isRelevantPathWeight(0);
	}

	/**
	 * @param weight
	 *            incoming weight
	 * @return true, when new path weight is relevant
	 */
	public boolean isRelevantPathWeight(final double weight) {
		return maxPathWeight != 0
				&& getDirectedPathWeight(path).add(BigDecimal.valueOf(weight))
						.compareTo(BigDecimal.valueOf(maxPathWeight)) < 1;
	}

	/**
	 * @param weight
	 *            incoming weight
	 * @return true, when new path weight is relevant
	 */
	public boolean isRelevantPathWeight(final BigDecimal weight) {
		return maxPathWeight != 0 && BigDecimal.valueOf(maxPathWeight)
				.subtract(getDirectedPathWeight(path).add(weight))
				.compareTo(BigDecimal.ZERO) >= 0;

	}

	/**
	 * Transform the path to {@link TopPath}
	 * 
	 * @param condition
	 *            condition for relevant TopPath
	 * @param acceptIncompletePath
	 *            should return incomplete path or not
	 * @return the TopPath or null, when not satisfy the condition or the path
	 *         isn't complete
	 */
	public TopPath getTopPath(final Predicate<TopPath> condition,
			final boolean acceptIncompletePath) {
		if (!isCompletePath() && !acceptIncompletePath) {
			return null;
		}
		final GraphPath<Node, DirectedTOPEdge<Edge>> completePath = makePath(
				graph, path, startNode);
		if (!acceptIncompletePath
				&& (!completePath.getStartVertex().equals(startNode)
						|| !completePath.getEndVertex().equals(endNode)
						|| completePath.getWeight() > maxPathWeight)) {
			return null;
		}

		final TopPath topPath = pathTransform(completePath, startNode);
		return condition == null || condition.test(topPath) ? topPath : null;
	}

	private static TopPath pathTransform(
			final GraphPath<Node, DirectedTOPEdge<Edge>> path,
			final Node startNode) {
		final DirectedTOPEdge<Edge> firstEdge = path.getEdgeList().getFirst();
		final Node edgeSource = path.getGraph().getEdgeSource(firstEdge);
		if (!path.getStartVertex().equals(startNode)
				|| !edgeSource.equals(startNode)) {
			throw new IllegalArgumentException(
					"Path not start from source node"); //$NON-NLS-1$
		}

		return new TopPath(
				path.getEdgeList()
						.stream()
						.map(e -> e.edge())
						.distinct()
						.map(Edge::edge)
						.toList(),
				getDirectedPathWeight(path), startNode.point());
	}

	private static BigDecimal getDirectedPathWeight(
			final GraphPath<Node, DirectedTOPEdge<Edge>> graphPath) {
		return getDirectedPathWeight(graphPath.getEdgeList());
	}

	private static BigDecimal getDirectedPathWeight(
			final List<DirectedTOPEdge<Edge>> path) {
		return path.stream()
				.map(edge -> edge.edge().getWeight())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	/**
	 * Transform an ordered list of edges into a GraphPath.
	 *
	 * The weight of the generated GraphPath is set to the sum of the weights of
	 * the edges.
	 *
	 * @param edges
	 *            the edges
	 *
	 * @return the corresponding GraphPath
	 */
	private static GraphPath<Node, DirectedTOPEdge<Edge>> makePath(
			final Graph<Node, DirectedTOPEdge<Edge>> directedGraph,
			final List<DirectedTOPEdge<Edge>> edges, final Node startNode) {
		if (edges.isEmpty()) {
			return GraphWalk.singletonWalk(directedGraph, startNode, 0);
		}
		final Node source = directedGraph.getEdgeSource(edges.getFirst());
		final Node target = directedGraph.getEdgeTarget(edges.getLast());
		final double weight = edges.stream()
				.mapToDouble(directedGraph::getEdgeWeight)
				.sum();
		return new GraphWalk<>(directedGraph, source, target, edges, weight);
	}
}
