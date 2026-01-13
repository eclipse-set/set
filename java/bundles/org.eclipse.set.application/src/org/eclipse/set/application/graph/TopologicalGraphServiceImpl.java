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

import static org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.getContainer;
import static org.eclipse.set.ppmodel.extensions.MultiContainer_AttributeGroupExtensions.getPlanProSchnittstelle;

import java.math.BigDecimal;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.basis.graph.TopPath;
import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.TopKanteExtensions;
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
@Component(property = { EventConstants.EVENT_TOPIC + "=" + Events.MODEL_CHANGED,
		EventConstants.EVENT_TOPIC + "=" + Events.COMPARE_MODEL_LOADED,
		EventConstants.EVENT_TOPIC + "=" + Events.CLOSE_SESSION }, service = {
				EventHandler.class, TopologicalGraphService.class })
public class TopologicalGraphServiceImpl
		implements TopologicalGraphService, EventHandler {
	private final Map<PlanPro_Schnittstelle, WeightedPseudograph<AsSplitTopGraph.Node, AsSplitTopGraph.Edge>> topGraphBaseMap;
	// Tolerance value by find path with topological direction
	private final int TOLERANT_DISTANCE_TO_FIND_DIRECTION_PATH = 500;

	@Reference
	EventAdmin eventAdmin;
	@Reference
	SessionService sessionService;

	/**
	 * The default constructor
	 */
	public TopologicalGraphServiceImpl() {
		this.topGraphBaseMap = new HashMap<>();
		Services.setTopGraphService(this);
	}

	@Override
	public void handleEvent(final Event event) {
		if (event.getTopic().equals(Events.CLOSE_SESSION)) {
			final ToolboxFileRole role = (ToolboxFileRole) event
					.getProperty(IEventBroker.DATA);
			if (role == ToolboxFileRole.SESSION) {
				topGraphBaseMap.clear();
			} else {
				topGraphBaseMap.remove(sessionService.getLoadedSession(role)
						.getPlanProSchnittstelle());
			}

			return;
		}
		// Create a new graph for the new model
		final IModelSession modelsession = (IModelSession) event
				.getProperty(IEventBroker.DATA);
		final PlanPro_Schnittstelle planProSchnittstelle = modelsession
				.getPlanProSchnittstelle();
		final WeightedPseudograph<Node, Edge> topGraphBase = getTopGraphBase(
				planProSchnittstelle);
		addContainerToGraph(topGraphBase, PlanProSchnittstelleExtensions
				.getContainer(planProSchnittstelle, ContainerType.INITIAL));
		addContainerToGraph(topGraphBase, PlanProSchnittstelleExtensions
				.getContainer(planProSchnittstelle, ContainerType.FINAL));
		addContainerToGraph(topGraphBase, PlanProSchnittstelleExtensions
				.getContainer(planProSchnittstelle, ContainerType.SINGLE));

		// Notify that the top model changed
		final Map<String, Object> properties = new HashMap<>();
		properties.put("org.eclipse.e4.data", planProSchnittstelle); //$NON-NLS-1$
		eventAdmin.sendEvent(new Event(Events.TOPMODEL_CHANGED, properties));
	}

	private static void addContainerToGraph(
			final WeightedPseudograph<Node, Edge> topGraphBase,
			final MultiContainer_AttributeGroup container) {
		if (container == null) {
			return;
		}
		container.getTOPKante().forEach(edge -> addEdge(topGraphBase, edge));
	}

	private WeightedPseudograph<AsSplitTopGraph.Node, AsSplitTopGraph.Edge> getTopGraphBase(
			final PlanPro_Schnittstelle schnittstelle) {
		return topGraphBaseMap.computeIfAbsent(schnittstelle,
				k -> new WeightedPseudograph<>(AsSplitTopGraph.Edge.class));
	}

	private static void addEdge(
			final WeightedPseudograph<AsSplitTopGraph.Node, AsSplitTopGraph.Edge> topGraphBase,
			final TOP_Kante edge) {
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
		final MultiContainer_AttributeGroup container = getContainer(
				from.edge());
		final PlanPro_Schnittstelle planProSchnittstelle = getPlanProSchnittstelle(
				container);
		return AsDirectedTopGraph.getAllPaths(
				new AsSplitTopGraph(getTopGraphBase(planProSchnittstelle)),
				from, to, limit);
	}

	@Override
	public TopPath findPathBetween(final TopPoint from, final TopPoint to,
			final int limit, final Predicate<TopPath> condition) {
		final MultiContainer_AttributeGroup container = getContainer(
				from.edge());
		final PlanPro_Schnittstelle planProSchnittstelle = getPlanProSchnittstelle(
				container);
		return AsDirectedTopGraph.getPath(
				new AsSplitTopGraph(getTopGraphBase(planProSchnittstelle)),
				from, to, limit, condition);
	}

	@Override
	public Optional<BigDecimal> findShortestDistanceInDirection(
			final TopPoint from, final TopPoint to,
			final boolean searchInTopDirection) {
		return findShortestPathInDirection(from, to, searchInTopDirection)
				.map(TopPath::length);
	}

	@Override
	public Optional<TopPath> findShortestPath(final TopPoint from,
			final TopPoint to) {
		if (from.equalLocation(to)) {
			return Optional.of(
					new TopPath(List.of(from.edge()), BigDecimal.ZERO, from));
		}
		final MultiContainer_AttributeGroup container = getContainer(
				from.edge());
		final PlanPro_Schnittstelle planProSchnittstelle = getPlanProSchnittstelle(
				container);
		final AsSplitTopGraph graphView = new AsSplitTopGraph(
				getTopGraphBase(planProSchnittstelle));

		final Node fromNode = graphView.splitGraphAt(from);
		final Node toNode = graphView.splitGraphAt(to);

		return Optional.ofNullable( //
				findPathBetween(graphView, fromNode, toNode))
				.map(p -> new TopPath(p.getEdgeList()
						.stream()
						.map(Edge::edge)
						.distinct()
						.toList(), getPathWeight(p), from));
	}

	@Override
	public Optional<TopPath> findShortestPathInDirection(final TopPoint from,
			final TopPoint to, final boolean inTopDirection) {
		// Fall the both of point lie on same TOP_Kante
		if (from.edge() == to.edge()) {
			final BigDecimal distance = from.distance().subtract(to.distance());
			return distance.doubleValue() < 0 == inTopDirection
					? Optional.of(new TopPath(List.of(from.edge()),
							distance.abs(), distance.abs()))
					: Optional.empty();
		}
		final MultiContainer_AttributeGroup container = getContainer(
				from.edge());
		final PlanPro_Schnittstelle planProSchnittstelle = getPlanProSchnittstelle(
				container);
		final AsSplitTopGraph graphView = new AsSplitTopGraph(
				getTopGraphBase(planProSchnittstelle));

		final Node fromNode = graphView.splitGraphAt(from,
				Boolean.valueOf(inTopDirection));
		final Node toNode = graphView.splitGraphAt(to);
		final Optional<BigDecimal> shortestDistance = findShortestDistance(from,
				to);

		if (shortestDistance.isEmpty()) {
			return Optional.empty();
		}

		final TopPath path = AsDirectedTopGraph
				.getPath(AsDirectedTopGraph.asDirectedTopGraph(graphView),
						fromNode, toNode,
						shortestDistance.get().intValue()
								+ TOLERANT_DISTANCE_TO_FIND_DIRECTION_PATH,
						topPath -> {
							final Deque<TOP_Kante> edges = new LinkedList<>(
									topPath.edges());
							TOP_Kante current = edges.poll();
							while (!edges.isEmpty()) {
								final TOP_Kante next = edges.poll();

								if (!TopKanteExtensions.isRoute(next,
										current)) {
									return false;
								}
								current = next;
							}
							return true;
						});
		return Optional.ofNullable(path);
	}

	private static GraphPath<AsSplitTopGraph.Node, AsSplitTopGraph.Edge> findPathBetween(
			final AsSplitTopGraph graphView, final Node fromNode,
			final Node toNode) {
		try {
			return DijkstraShortestPath.findPathBetween(graphView, fromNode,
					toNode);

		} catch (final IllegalArgumentException ex) {
			if (ex.getMessage().equals("Negative edge weight not allowed")) { //$NON-NLS-1$
				throw new IllegalArgumentException("Invalid spot location", ex); //$NON-NLS-1$
			}
			throw ex;
		}
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
		final MultiContainer_AttributeGroup container = getContainer(
				from.edge());
		final PlanPro_Schnittstelle planProSchnittstelle = getPlanProSchnittstelle(
				container);
		final AsSplitTopGraph graphView = new AsSplitTopGraph(
				getTopGraphBase(planProSchnittstelle));
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
			final GraphPath<Node, Edge> path = findPathBetween(graphView,
					fromNode, toNode);
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
