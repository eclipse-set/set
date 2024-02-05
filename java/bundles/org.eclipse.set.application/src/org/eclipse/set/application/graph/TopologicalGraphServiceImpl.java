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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.graph.TopPath;
import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante;
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.utils.graph.AsDirectedTopGraph;
import org.eclipse.set.utils.graph.AsDirectedTopGraph.DirectedTOPEdge;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.AsWeightedGraph;
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
	private WeightedPseudograph<TOP_Knoten, TOP_Kante> graph;

	@Reference
	EventAdmin eventAdmin;

	@Override
	public void handleEvent(final Event event) {
		// Create a new graph for the new model
		graph = new WeightedPseudograph<>(TOP_Kante.class);

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
		graph.addVertex(edge.getIDTOPKnotenA());
		graph.addVertex(edge.getIDTOPKnotenB());
		graph.addEdge(edge.getIDTOPKnotenA(), edge.getIDTOPKnotenB(), edge);
		graph.setEdgeWeight(edge,
				edge.getTOPKanteAllg().getTOPLaenge().getWert().doubleValue());
	}

	/**
	 * Returns the distance from a TOP_Knoten to a TopPoint. Only works if the
	 * TopPoint is located on a TOP_Kante indicdent to the TOP_Knoten
	 * 
	 * @param from
	 *            the TOP_Knoten
	 * @param to
	 *            the TopPoint
	 * @return the distance between the two points
	 */
	private static BigDecimal getDistance(final TOP_Knoten from,
			final TopPoint to) {
		if (to.edge().getIDTOPKnotenA().equals(from)) {
			return to.distance();
		} else if (to.edge().getIDTOPKnotenB().equals(from)) {
			return to.edge().getTOPKanteAllg().getTOPLaenge().getWert()
					.subtract(to.distance());
		}
		throw new IllegalArgumentException("TOP_Knoten is not on to.edge()"); //$NON-NLS-1$
	}

	@Override
	public List<TopPath> findAllPathsBetween(final TopPoint from,
			final TopPoint to) {
		// Create a graph view to edit the graph used for this path finding
		final Graph<TOP_Knoten, TOP_Kante> graphView = new AsWeightedGraph<>(
				new AsSubgraph<>(graph), new HashMap<>(), false);

		final List<TopPath> resultPaths = new ArrayList<>();

		// If both points are on the same edge, only consider the path on the
		// edge
		if (from.edge().equals(to.edge())) {
			return List.of(new TopPath(List.of(from.edge()),
					from.distance().subtract(to.distance()).abs()));
		}

		// Set source and end edge lengths to zero
		graphView.setEdgeWeight(from.edge(), 0);
		graphView.setEdgeWeight(to.edge(), 0);

		final TOP_Knoten fromNode = from.edge().getIDTOPKnotenA();
		final TOP_Knoten toNode = to.edge().getIDTOPKnotenA();

		final Graph<TOP_Knoten, DirectedTOPEdge> directedGraph = AsDirectedTopGraph
				.asDirectedTopGraph(graphView);
		final List<GraphPath<TOP_Knoten, DirectedTOPEdge>> paths = new AllDirectedPaths<>(
				directedGraph).getAllPaths(fromNode, toNode, true, null);

		paths.stream().forEach(path -> {
			BigDecimal pathWeight = getDirectedPathWeight(path,
					List.of(from.edge(), to.edge()));

			// Determine whether the path crosses the start edge. If so,
			// consider
			// the path to start at knotenB and remove the edge weight
			TOP_Knoten startNode = from.edge().getIDTOPKnotenA();
			if (!path.getEdgeList().isEmpty()
					&& path.getEdgeList().get(0).edge().equals(from.edge())) {
				startNode = from.edge().getIDTOPKnotenB();
			}

			// Determine whether the path crosses the end edge. If so, consider
			// the path to end at knotenB
			TOP_Knoten endNode = to.edge().getIDTOPKnotenA();
			if (!path.getEdgeList().isEmpty()
					&& path.getEdgeList().get(path.getEdgeList().size() - 1)
							.edge().equals(to.edge())) {
				endNode = to.edge().getIDTOPKnotenB();
			}

			// Add the initial distance of the TopPoint to the first TOP_Knoten
			pathWeight = pathWeight.add(getDistance(startNode, from))
					.add(getDistance(endNode, to));

			// Add start/ending edges if not already part of the path
			final List<TOP_Kante> fullPath = new ArrayList<>();
			if (path.getEdgeList().stream()
					.noneMatch(c -> c.edge().equals(from.edge()))) {
				fullPath.add(from.edge());
			}

			path.getEdgeList().stream().map(c -> c.edge())
					.forEach(fullPath::add);
			if (path.getEdgeList().stream()
					.noneMatch(c -> c.edge().equals(to.edge()))) {
				fullPath.add(to.edge());
			}

			resultPaths.add(new TopPath(fullPath, pathWeight));
		});

		return resultPaths;
	}

	private static BigDecimal getPathWeight(
			final GraphPath<TOP_Knoten, TOP_Kante> graphPath,
			final List<TOP_Kante> ignoreList) {
		return graphPath.getEdgeList().stream()
				.filter(c -> !ignoreList.contains(c))
				.map(edge -> edge.getTOPKanteAllg().getTOPLaenge().getWert())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static BigDecimal getDirectedPathWeight(
			final GraphPath<TOP_Knoten, DirectedTOPEdge> graphPath,
			final List<TOP_Kante> ignoreList) {
		return graphPath.getEdgeList().stream().map(edge -> edge.edge())
				.filter(c -> !ignoreList.contains(c))
				.map(edge -> edge.getTOPKanteAllg().getTOPLaenge().getWert())
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	@Override
	public Optional<BigDecimal> findShortestPathInDirection(final TopPoint from,
			final TopPoint to, final boolean searchInTopDirection) {

		// If both points are on the same edge, check if following the edge
		// in
		// the desired direction immediately provides a result
		if (from.edge().equals(to.edge())) {
			final BigDecimal fromDistance = from.distance();
			final BigDecimal toDistance = to.distance();

			if (searchInTopDirection && toDistance.compareTo(fromDistance) >= 0
					|| !searchInTopDirection
							&& toDistance.compareTo(fromDistance) < 0) {
				return Optional.of(toDistance.subtract(fromDistance).abs());
			}
		}

		// Graph algorithms usually find paths between vertices.
		// As the starting search direction is determined by the direction
		// Remove the start edge and start the search from the corresponding
		// end of the edge

		// Create a graph view to edit the graph used for this path finding
		final Graph<TOP_Knoten, TOP_Kante> graphView = new AsWeightedGraph<>(
				new AsSubgraph<>(graph), new HashMap<>(), false);

		graphView.removeEdge(from.edge());
		final TOP_Knoten startNode = searchInTopDirection
				? from.edge().getIDTOPKnotenB()
				: from.edge().getIDTOPKnotenA();

		// Set ending edge weight to zero, as we do not know which end
		// TOP_Knoten is closer (only if edges are not identical)
		if (!from.edge().equals(to.edge())) {
			graphView.setEdgeWeight(to.edge(), 0);
		}

		final GraphPath<TOP_Knoten, TOP_Kante> path = DijkstraShortestPath
				.findPathBetween(graph, from.edge().getIDTOPKnotenA(),
						to.edge().getIDTOPKnotenB());

		if (path == null) {
			return Optional.empty();
		}

		BigDecimal pathWeight = getPathWeight(path,
				List.of(from.edge(), to.edge()));
		// Determine whether the path crosses the end edge. If so, consider
		// the path to end at knotenB
		TOP_Knoten endNode = null;
		if (!path.getEdgeList().isEmpty() && path.getEdgeList()
				.get(path.getEdgeList().size() - 1).equals(to.edge())) {
			endNode = to.edge().getIDTOPKnotenA();
		} else {
			endNode = to.edge().getIDTOPKnotenB();
			pathWeight = pathWeight.subtract(
					to.edge().getTOPKanteAllg().getTOPLaenge().getWert());
		}

		final BigDecimal startDistance = getDistance(startNode, from);
		final BigDecimal endDistance = getDistance(endNode, to);

		return Optional.of(startDistance.add(pathWeight).add(endDistance));

	}

	@Override
	public Optional<BigDecimal> findShortestPath(final TopPoint from,
			final TopPoint to) {
		// If both points are on the same edge, the calculation is trivial
		if (from.edge().equals(to.edge())) {
			return Optional.of(from.distance().subtract(to.distance()).abs());
		}

		// Graph algorithms usually find paths between vertices. Therefore
		// set
		// the starting and ending edges to zero
		// This results in the shortest path to either TOPKnotenA or
		// TOPKnotenB
		// being found (when searching for TOPKnotenA).
		// In a secondary step, we then decide which TOP_Knoten to start off
		// from (and measure the distance to the object)

		// Create a graph view to edit the graph weights used for this path
		// finding
		final Graph<TOP_Knoten, TOP_Kante> graphView = new AsWeightedGraph<>(
				graph, new HashMap<>(), false);

		// Set source and end edge lengths to zero
		graphView.setEdgeWeight(from.edge(), 0);
		graphView.setEdgeWeight(to.edge(), 0);

		final GraphPath<TOP_Knoten, TOP_Kante> path = DijkstraShortestPath
				.findPathBetween(graph, from.edge().getIDTOPKnotenA(),
						to.edge().getIDTOPKnotenA());

		if (path == null) {
			return Optional.empty();
		}

		BigDecimal pathWeight = getPathWeight(path,
				List.of(from.edge(), to.edge()));

		// Determine whether the path crosses the start edge. If so,
		// consider
		// the path to start at knotenB and remove the edge weight
		TOP_Knoten startNode = from.edge().getIDTOPKnotenA();
		if (!path.getEdgeList().isEmpty()
				&& path.getEdgeList().get(0).equals(from.edge())) {
			startNode = from.edge().getIDTOPKnotenB();
			pathWeight = pathWeight.subtract(
					from.edge().getTOPKanteAllg().getTOPLaenge().getWert());
		}

		// Determine whether the path crosses the end edge. If so, consider
		// the path to end at knotenB
		TOP_Knoten endNode = to.edge().getIDTOPKnotenA();
		if (!path.getEdgeList().isEmpty() && path.getEdgeList()
				.get(path.getEdgeList().size() - 1).equals(to.edge())) {
			endNode = to.edge().getIDTOPKnotenB();
			pathWeight = pathWeight.subtract(
					to.edge().getTOPKanteAllg().getTOPLaenge().getWert());
		}

		// Add the initial distance of the TopPoint to the first TOP_Knoten
		final BigDecimal startDistance = getDistance(startNode, from);
		final BigDecimal endDistance = getDistance(endNode, to);

		return Optional.of(startDistance.add(pathWeight).add(endDistance));
	}

}
