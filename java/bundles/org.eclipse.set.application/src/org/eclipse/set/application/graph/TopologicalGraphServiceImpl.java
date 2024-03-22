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
package org.eclipse.set.application.graph;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.graph.TopPath;
import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.graph.AsDirectedTopGraph;
import org.eclipse.set.utils.graph.AsDirectedTopGraph.DirectedTOPEdge;
import org.eclipse.set.utils.graph.AsSplitTopGraph;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Edge;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Node;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.WeightedPseudograph;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

/**
 * TopologicalGraph service for finding distances in the topological model
 */
@Component(property = EventConstants.EVENT_TOPIC + "="
		+ Events.MODEL_CHANGED, service = { EventHandler.class,
				TopologicalGraphService.class })
public class TopologicalGraphServiceImpl
		implements TopologicalGraphService, EventHandler {
	private WeightedPseudograph<AsSplitTopGraph.Node, AsSplitTopGraph.Edge> topGraphBase;

	@Reference
	EventAdmin eventAdmin;

	@Override
	public void handleEvent(final Event event) {
		// Create a new graph for the new model
		topGraphBase = new WeightedPseudograph<>(AsSplitTopGraph.Edge.class);

		final PlanPro_Schnittstelle planProSchnittstelle = (PlanPro_Schnittstelle) event
				.getProperty(IEventBroker.DATA);
		addContainerToGraph(PlanProSchnittstelleExtensions
				.getContainer(planProSchnittstelle, ContainerType.INITIAL));
		addContainerToGraph(PlanProSchnittstelleExtensions
				.getContainer(planProSchnittstelle, ContainerType.FINAL));
		addContainerToGraph(PlanProSchnittstelleExtensions
				.getContainer(planProSchnittstelle, ContainerType.SINGLE));

		// Notify that the top model changed
		final Map<String, Object> properties = new HashMap<>();
		properties.put("org.eclipse.e4.data", planProSchnittstelle); //$NON-NLS-1$
		eventAdmin.sendEvent(new Event(Events.TOPMODEL_CHANGED, properties));
	}

	private void addContainerToGraph(
			final MultiContainer_AttributeGroup container) {
		if (container == null) {
			return;
		}
		container.getTOPKante().forEach(this::addEdge);
	}

	private void addEdge(final TOP_Kante edge) {
		final Node a = new Node(edge.getIDTOPKnotenA().getValue());
		final Node b = new Node(edge.getIDTOPKnotenB().getValue());
		topGraphBase.addVertex(a);
		topGraphBase.addVertex(b);
		final Edge graphEdge = new Edge(edge);
		topGraphBase.addEdge(a, b, graphEdge);
		topGraphBase.setEdgeWeight(graphEdge,
				edge.getTOPKanteAllg().getTOPLaenge().getWert().doubleValue());
	}

	@Override
	public List<TopPath> findAllPathsBetween(final TopPoint from,
			final TopPoint to, final int limit) {
		final AsSplitTopGraph graphView = new AsSplitTopGraph(topGraphBase);

		final Node fromNode = graphView.splitGraphAt(from);
		final Node toNode = graphView.splitGraphAt(to);

		final Graph<Node, DirectedTOPEdge<Edge>> directedGraph = AsDirectedTopGraph
				.asDirectedTopGraph(graphView);
		final List<GraphPath<Node, DirectedTOPEdge<Edge>>> paths = new AllDirectedPaths<>(
				directedGraph).getAllPaths(fromNode, toNode, true,
						Integer.valueOf(limit));

		return paths.stream()
				.map(c -> new TopPath(
						c.getEdgeList().stream().map(e -> e.edge()).distinct()
								.map(Edge::edge).toList(),
						getDirectedPathWeight(c), fromNode.point().distance()))
				.toList();
	}

	@Override
	public TopPath findPathBetween(final TopPoint from, final TopPoint to,
			final int limit, final Predicate<TopPath> condition) {
		return AsDirectedTopGraph.getPath(new AsSplitTopGraph(topGraphBase),
				from, to, limit, condition);
	}

	private static BigDecimal getDirectedPathWeight(
			final GraphPath<Node, DirectedTOPEdge<Edge>> graphPath) {
		return graphPath.getEdgeList().stream()
				.map(edge -> edge.edge().getWeight())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public Optional<BigDecimal> findShortestDistanceInDirection(
			final TopPoint from, final TopPoint to,
			final boolean searchInTopDirection) {
		final AsSplitTopGraph graphView = new AsSplitTopGraph(topGraphBase);
		final Node fromNode = graphView.splitGraphAt(from,
				Boolean.valueOf(searchInTopDirection));
		final Node toNode = graphView.splitGraphAt(to);

		return Optional.ofNullable( //
				DijkstraShortestPath.findPathBetween(graphView, fromNode,
						toNode))
				.map(p -> getPathWeight(p));
	}

	@Override
	public Optional<TopPath> findShortestPath(final TopPoint from,
			final TopPoint to) {
		final AsSplitTopGraph graphView = new AsSplitTopGraph(topGraphBase);

		final Node fromNode = graphView.splitGraphAt(from);
		final Node toNode = graphView.splitGraphAt(to);

		return Optional.ofNullable( //
				DijkstraShortestPath.findPathBetween(graphView, fromNode,
						toNode))
				.map(p -> new TopPath(
						p.getEdgeList().stream().map(Edge::edge).distinct()
								.toList(),
						getPathWeight(p), fromNode.point().distance()));
	}

	private static BigDecimal getPathWeight(final GraphPath<Node, Edge> path) {
		return path.getEdgeList().stream().map(Edge::getWeight)
				.reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}

	@Override
	public Optional<BigDecimal> findShortestDistance(final TopPoint from,
			final TopPoint to) {
		return findShortestPath(from, to).map(TopPath::length);
	}
}
