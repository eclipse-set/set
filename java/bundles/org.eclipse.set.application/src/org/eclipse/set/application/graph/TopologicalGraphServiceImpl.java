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
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.utils.graph.AsDirectedTopGraph;
import org.eclipse.set.utils.graph.AsSplitTopGraph;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Edge;
import org.eclipse.set.utils.graph.AsSplitTopGraph.Node;
import org.jgrapht.GraphPath;
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

	/**
	 * The default constructor
	 */
	public TopologicalGraphServiceImpl() {
		Services.setTopGraphService(this);
	}

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
		return AsDirectedTopGraph.getAllPaths(new AsSplitTopGraph(topGraphBase),
				from, to, limit);
	}

	@Override
	public TopPath findPathBetween(final TopPoint from, final TopPoint to,
			final int limit, final Predicate<TopPath> condition) {
		return AsDirectedTopGraph.getPath(new AsSplitTopGraph(topGraphBase),
				from, to, limit, condition);
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
				.map(p -> new TopPath(p.getEdgeList()
						.stream()
						.map(Edge::edge)
						.distinct()
						.toList(), getPathWeight(p), from));
	}

	private static BigDecimal getPathWeight(final GraphPath<Node, Edge> path) {
		return path.getEdgeList()
				.stream()
				.map(Edge::getWeight)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);
	}

	@Override
	public Optional<BigDecimal> findShortestDistance(final TopPoint from,
			final TopPoint to) {
		return findShortestPath(from, to).map(TopPath::length);
	}

	@Override
	public Optional<TopPoint> findClosestPoint(final TopPoint from,
			final List<TopPoint> points, final boolean searchInTopDirection) {
		final AsSplitTopGraph graphView = new AsSplitTopGraph(topGraphBase);
		final Node fromNode = graphView.splitGraphAt(from,
				Boolean.valueOf(searchInTopDirection));

		BigDecimal minWeight = BigDecimal.valueOf(1000000);
		Optional<TopPoint> minPoint = Optional.empty();
		for (final TopPoint point : points) {
			Node toNode = null;
			try {
				toNode = graphView.splitGraphAt(point);
			} catch (final IllegalArgumentException e) {
				continue;
			}
			final GraphPath<Node, Edge> path = DijkstraShortestPath
					.findPathBetween(graphView, fromNode, toNode);
			if (path == null) {
				continue;
			}
			final BigDecimal weight = getPathWeight(path);
			if (minPoint.isEmpty() || weight.compareTo(minWeight) < 0) {
				minWeight = weight;
				minPoint = Optional.of(point);
			}
		}

		return minPoint;
	}
}
