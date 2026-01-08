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
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.jgrapht.Graph;
import org.jgrapht.GraphType;
import org.jgrapht.graph.AbstractGraph;
import org.jgrapht.graph.AsGraphUnion;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.WeightedPseudograph;

/**
 * Helper class to convert an undirected graph of top edges to a directed graph
 */
public class AsSplitTopGraph
		extends AbstractGraph<AsSplitTopGraph.Node, AsSplitTopGraph.Edge> {

	/**
	 * Helper type to represent an Edge in the graph. Each edge is a subset of
	 * exactly one TOP_Kante, however multiple edges can ccover a single
	 * TOP_Kante
	 * 
	 * @param edge
	 *            the TOP_Kante
	 * @param offset
	 *            start offset on the TOP_Kante (null if directly covering the
	 *            TOP_Kante)
	 * @param length
	 *            total length of the edge (null if directly covering the
	 *            TOP_Kante)
	 */
	public record Edge(TOP_Kante edge, BigDecimal offset, BigDecimal length) {
		/**
		 * @param baseEdge
		 *            the TOP_Kante
		 */
		public Edge(final TOP_Kante baseEdge) {
			this(baseEdge, null, null);
		}

		private Edge(final TopPoint point, final Edge oldEdge,
				final boolean inTopDirection) {
			this(point.edge(), //
					calculateOffset(point, oldEdge, inTopDirection), //
					calculateLength(point, oldEdge, inTopDirection));
		}

		private static BigDecimal calculateOffset(final TopPoint point,
				final Edge oldEdge, final boolean inTopDirection) {
			if (inTopDirection) {
				if (oldEdge.offset == null) {
					return BigDecimal.ZERO;
				}
				return oldEdge.offset;
			}
			return point.distance();
		}

		private static BigDecimal calculateLength(final TopPoint point,
				final Edge oldEdge, final boolean inTopDirection) {
			if (inTopDirection) {
				if (oldEdge.offset == null) {
					return point.distance();
				}
				return point.distance().subtract(oldEdge.offset);
			}

			if (oldEdge.length == null) {
				return point.edge()
						.getTOPKanteAllg()
						.getTOPLaenge()
						.getWert()
						.subtract(point.distance());
			}
			return oldEdge.length.subtract(point.distance())
					.add(oldEdge.offset);
		}

		/**
		 * @return edge weight
		 */
		public BigDecimal getWeight() {
			if (length != null) {
				return length;
			}
			return edge.getTOPKanteAllg().getTOPLaenge().getWert();
		}
	}

	/**
	 * A wrapper type for graph node representing either a TOP_Knoten or a
	 * TopPoint.
	 * 
	 * @param node
	 *            the TOP_Knoten
	 * @param point
	 *            the TopPoint
	 */
	public record Node(TOP_Knoten node, TopPoint point) {
		/**
		 * @param baseNode
		 *            the TOP_Knoten for this node
		 */
		public Node(final TOP_Knoten baseNode) {
			this(baseNode, null);
		}

		/**
		 * This constructor should be private, as creating a TopPoint node
		 * should only happen by splitting the graph
		 */
		private Node(final TopPoint point) {
			this(null, point);
		}
	}

	/**
	 * The base graph, representing the TOP_Knoten/TOP_Kante model
	 */
	private final Graph<Node, Edge> baseGraph;

	/**
	 * The point edge graph, a small supplementary graph representing removed
	 * TOP_Kanten that are replaced with TopPoint-based Nodes and Edges
	 */
	private final Graph<Node, Edge> pointEdgeGraph;

	/**
	 * The "split graph" union of baseGraph and pointEdgeGraph
	 */
	private final Graph<Node, Edge> unionGraph;

	/**
	 * Creates a new splittable topological graph
	 * 
	 * @param base
	 *            the base topological graph.
	 */
	public AsSplitTopGraph(final Graph<Node, Edge> base) {
		baseGraph = new AsSubgraph<>(base);
		pointEdgeGraph = new WeightedPseudograph<>(Edge.class);
		unionGraph = new AsGraphUnion<>(baseGraph, pointEdgeGraph);
	}

	/**
	 * @param at
	 *            the point where to split at
	 * @return a node at the point where the graph was split
	 */
	public Node splitGraphAt(final TopPoint at) {
		return splitGraphAt(at, null);
	}

	/**
	 * @param at
	 *            the point where to split at
	 * @param inTopDirection
	 *            whether to split in a single top direction or not. If TRUE,
	 *            then split in top direction, if FALSE split against it, if
	 *            null do not split in either top directions
	 * @return a node at the point where the graph was split
	 */
	public Node splitGraphAt(final TopPoint at, final Boolean inTopDirection) {
		// When already split at the point
		final Optional<Node> existedNode = existedSplitNodeAt(at);
		if (existedNode.isPresent()) {
			return existedNode.get();
		}
		final TOP_Kante edge = at.edge();
		final List<Edge> pointEdgeList = pointEdgeGraph.edgeSet()
				.stream()
				.filter(c -> c.edge == edge)
				.filter(c -> c.offset.compareTo(at.distance()) <= 0)
				.filter(c -> c.offset.add(c.length)
						.compareTo(at.distance()) >= 0)
				.toList();
		final Optional<Edge> pointGraphEdge = pointEdgeList.stream()
				.max((e1, e2) -> e1.offset.compareTo(e2.offset));
		if (pointGraphEdge.isPresent()) {
			if (isTopKnoten(at)) {
				return at.distance().compareTo(BigDecimal.ZERO) == 0
						? pointEdgeGraph.getEdgeSource(pointGraphEdge.get())
						: pointEdgeGraph.getEdgeTarget(pointGraphEdge.get());
			}
			return splitGraphAt(pointEdgeGraph, at, pointGraphEdge.get(),
					inTopDirection);
		}

		final Optional<Edge> graphEdge = baseGraph.edgeSet()
				.stream()
				.filter(c -> c.edge == edge)
				.findFirst();
		if (graphEdge.isPresent()) {
			return splitGraphAt(baseGraph, at, graphEdge.get(), inTopDirection);
		}

		throw new IllegalArgumentException(
				"Cannot split graph on a point outside the graph"); //$NON-NLS-1$
	}

	private Optional<Node> existedSplitNodeAt(final TopPoint at) {
		if (isTopKnoten(at)) {
			final TOP_Knoten atKnote = at.distance()
					.compareTo(BigDecimal.ZERO) == 0
							? at.edge().getIDTOPKnotenA().getValue()
							: at.edge().getIDTOPKnotenB().getValue();
			return pointEdgeGraph.vertexSet()
					.stream()
					.filter(n -> n.node != null)
					.filter(n -> n.node == atKnote)
					.findFirst();
		}
		return pointEdgeGraph.vertexSet()
				.stream()
				.filter(n -> n.point != null)
				.filter(n -> n.point.edge() == at.edge()
						&& n.point.distance().compareTo(at.distance()) == 0)
				.findFirst();
	}

	private static boolean isTopKnoten(final TopPoint point) {
		return point.distance().compareTo(BigDecimal.ZERO) == 0
				|| point.distance()
						.compareTo(point.edge()
								.getTOPKanteAllg()
								.getTOPLaenge()
								.getWert()) == 0;
	}

	private Node addPointGraphNode(final TopPoint point) {
		final Node node = new Node(point);
		pointEdgeGraph.addVertex(node);
		return node;
	}

	private void addPointGraphEdge(final Node from, final Node to,
			final Edge edge) {
		pointEdgeGraph.addEdge(from, to, edge);
		pointEdgeGraph.setEdgeWeight(edge, edge.getWeight().doubleValue());
	}

	private Node splitGraphAt(final Graph<Node, Edge> sourceGraph,
			final TopPoint point, final Edge existingEdge,
			final Boolean inTopDirection) {
		final Node topDirNode = addPointGraphNode(point);
		final Node againstTopDirNode = addPointGraphNode(point);
		final Node sourceVertex = sourceGraph.getEdgeSource(existingEdge);
		final Node targetVertex = sourceGraph.getEdgeTarget(existingEdge);
		pointEdgeGraph.addVertex(sourceVertex);
		pointEdgeGraph.addVertex(targetVertex);
		final Edge edge = new Edge(point, existingEdge, true);
		addPointGraphEdge(sourceVertex, topDirNode, edge);
		addPointGraphEdge(againstTopDirNode, targetVertex,
				new Edge(point, existingEdge, false));

		// If the graph is connected, add a connection between the two nodes
		if (inTopDirection == null) {
			addPointGraphEdge(topDirNode, againstTopDirNode,
					new Edge(point.edge(), edge.offset.add(edge.length),
							BigDecimal.ZERO));
		}

		sourceGraph.removeEdge(existingEdge);

		if (inTopDirection == null || inTopDirection.equals(Boolean.TRUE)) {
			return topDirNode;
		}
		return againstTopDirNode;
	}

	// Implement abstract methods by deferring to splitGraph
	@Override
	public Set<Edge> getAllEdges(final Node sourceVertex,
			final Node targetVertex) {
		return unionGraph.getAllEdges(sourceVertex, targetVertex);
	}

	@Override
	public Edge getEdge(final Node sourceVertex, final Node targetVertex) {
		return unionGraph.getEdge(sourceVertex, targetVertex);
	}

	@Override
	public Supplier<Node> getVertexSupplier() {
		return unionGraph.getVertexSupplier();
	}

	@Override
	public Supplier<Edge> getEdgeSupplier() {
		return unionGraph.getEdgeSupplier();
	}

	@Override
	public Edge addEdge(final Node sourceVertex, final Node targetVertex) {
		return unionGraph.addEdge(sourceVertex, targetVertex);
	}

	@Override
	public boolean addEdge(final Node sourceVertex, final Node targetVertex,
			final Edge e) {
		return unionGraph.addEdge(sourceVertex, targetVertex, e);
	}

	@Override
	public Node addVertex() {
		return unionGraph.addVertex();
	}

	@Override
	public boolean addVertex(final Node v) {
		return unionGraph.addVertex(v);
	}

	@Override
	public boolean containsEdge(final Edge e) {
		return unionGraph.containsEdge(e);
	}

	@Override
	public boolean containsVertex(final Node v) {
		return unionGraph.containsVertex(v);
	}

	@Override
	public Set<Edge> edgeSet() {
		return unionGraph.edgeSet();
	}

	@Override
	public int degreeOf(final Node vertex) {
		return unionGraph.degreeOf(vertex);
	}

	@Override
	public Set<Edge> edgesOf(final Node vertex) {
		return unionGraph.edgesOf(vertex);
	}

	@Override
	public int inDegreeOf(final Node vertex) {
		return unionGraph.inDegreeOf(vertex);
	}

	@Override
	public Set<Edge> incomingEdgesOf(final Node vertex) {
		return unionGraph.incomingEdgesOf(vertex);
	}

	@Override
	public int outDegreeOf(final Node vertex) {
		return unionGraph.outDegreeOf(vertex);
	}

	@Override
	public Set<Edge> outgoingEdgesOf(final Node vertex) {
		return unionGraph.outgoingEdgesOf(vertex);
	}

	@Override
	public Edge removeEdge(final Node sourceVertex, final Node targetVertex) {
		return unionGraph.removeEdge(sourceVertex, targetVertex);
	}

	@Override
	public boolean removeEdge(final Edge e) {
		return unionGraph.removeEdge(e);
	}

	@Override
	public boolean removeVertex(final Node v) {
		return unionGraph.removeVertex(v);
	}

	@Override
	public Set<Node> vertexSet() {
		return unionGraph.vertexSet();
	}

	@Override
	public Node getEdgeSource(final Edge e) {
		return unionGraph.getEdgeSource(e);
	}

	@Override
	public Node getEdgeTarget(final Edge e) {
		return unionGraph.getEdgeTarget(e);
	}

	@Override
	public GraphType getType() {
		return unionGraph.getType();
	}

	@Override
	public double getEdgeWeight(final Edge e) {
		final double edgeWeight = unionGraph.getEdgeWeight(e);
		if (edgeWeight < 0.0 && Math.abs(edgeWeight) <= ToolboxConfiguration
				.getPathFindingTolerance()) {
			return 0.0;
		}
		return edgeWeight;
	}

	@Override
	public void setEdgeWeight(final Edge e, final double weight) {
		unionGraph.setEdgeWeight(e, weight);
	}

}