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
package org.eclipse.set.core.graph;

import java.util.HashMap;
import java.util.OptionalDouble;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.core.services.graph.TopologicalGraphService;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante;
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.AsSubgraph;
import org.jgrapht.graph.AsWeightedGraph;
import org.jgrapht.graph.WeightedPseudograph;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
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
	private static double getDistance(final TOP_Knoten from,
			final TopPoint to) {
		if (to.edge().getIDTOPKnotenA().equals(from)) {
			return to.distance();
		} else if (to.edge().getIDTOPKnotenB().equals(from)) {
			return to.edge().getTOPKanteAllg().getTOPLaenge().getWert()
					.doubleValue() - to.distance();
		}
		throw new IllegalArgumentException("TOP_Knoten is not on to.edge()"); //$NON-NLS-1$
	}

	@Override
	public OptionalDouble findShortestPathInDirection(final TopPoint from,
			final TopPoint to, final boolean searchInTopDirection) {

		// If both points are on the same edge, check if following the edge
		// in
		// the desired direction immediately provides a result
		if (from.edge().equals(to.edge())) {
			final double fromDistance = from.distance();
			final double toDistance = to.distance();

			if (searchInTopDirection && toDistance >= fromDistance
					|| !searchInTopDirection && toDistance < fromDistance) {
				return OptionalDouble.of(Math.abs(toDistance - fromDistance));
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
		TOP_Knoten startNode = null;
		if (searchInTopDirection) {
			startNode = from.edge().getIDTOPKnotenB();
		} else {
			startNode = from.edge().getIDTOPKnotenA();
		}

		// Set ending edge weight to zero, as we do not know which end
		// TOP_Knoten is closer (only if edges are not identical)
		if (!from.edge().equals(to.edge())) {
			graphView.setEdgeWeight(to.edge(), 0);
		}

		final GraphPath<TOP_Knoten, TOP_Kante> path = DijkstraShortestPath
				.findPathBetween(graph, from.edge().getIDTOPKnotenA(),
						to.edge().getIDTOPKnotenB());

		if (path == null) {
			return OptionalDouble.empty();
		}

		double pathWeight = path.getWeight();
		// Determine whether the path crosses the end edge. If so, consider
		// the path to end at knotenB
		TOP_Knoten endNode = null;
		if (!path.getEdgeList().isEmpty() && path.getEdgeList()
				.get(path.getEdgeList().size() - 1).equals(to.edge())) {
			endNode = to.edge().getIDTOPKnotenA();
		} else {
			endNode = to.edge().getIDTOPKnotenB();
			pathWeight -= to.edge().getTOPKanteAllg().getTOPLaenge().getWert()
					.doubleValue();
		}

		final double startDistance = getDistance(startNode, from);
		final double endDistance = getDistance(endNode, to);

		return OptionalDouble.of(startDistance + pathWeight + endDistance);

	}

	@Override
	public OptionalDouble findShortestPath(final TopPoint from,
			final TopPoint to) {
		// If both points are on the same edge, the calculation is trivial
		if (from.edge().equals(to.edge())) {
			return OptionalDouble.of(Math.abs(from.distance() - to.distance()));
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
			return OptionalDouble.empty();
		}

		double pathWeight = path.getWeight();

		// Determine whether the path crosses the start edge. If so,
		// consider
		// the path to start at knotenB and remove the edge weight
		TOP_Knoten startNode = from.edge().getIDTOPKnotenA();
		if (!path.getEdgeList().isEmpty()
				&& path.getEdgeList().get(0).equals(from.edge())) {
			startNode = from.edge().getIDTOPKnotenB();
			pathWeight -= from.edge().getTOPKanteAllg().getTOPLaenge().getWert()
					.doubleValue();
		}

		// Determine whether the path crosses the end edge. If so, consider
		// the path to end at knotenB
		TOP_Knoten endNode = to.edge().getIDTOPKnotenA();
		if (!path.getEdgeList().isEmpty() && path.getEdgeList()
				.get(path.getEdgeList().size() - 1).equals(to.edge())) {
			endNode = to.edge().getIDTOPKnotenB();
			pathWeight -= to.edge().getTOPKanteAllg().getTOPLaenge().getWert()
					.doubleValue();
		}

		// Add the initial distance of the TopPoint to the first TOP_Knoten
		final double startDistance = getDistance(startNode, from);
		final double endDistance = getDistance(endNode, to);

		return OptionalDouble.of(startDistance + pathWeight + endDistance);

	}

}
