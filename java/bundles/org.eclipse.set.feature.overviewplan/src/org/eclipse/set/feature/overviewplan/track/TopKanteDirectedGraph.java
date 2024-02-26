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
package org.eclipse.set.feature.overviewplan.track;

import static org.eclipse.set.utils.math.IntegerExtensions.findSumCombination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.set.basis.Pair;
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Knoten;
import org.eclipse.set.utils.graph.AsDirectedTopGraph.DirectedTOPEdge;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DirectedPseudograph;
import org.jgrapht.graph.builder.GraphBuilder;

/**
 * 
 */
public class TopKanteDirectedGraph {
	DirectedPseudograph<TOP_Knoten, DirectedTOPEdge<TOPKanteMetaData>> graph;

	record MachtTopEdgeLength(boolean isMatch,
			List<TOPKanteMetaData> notMatchEdges, int differentLength) {
	}

	/**
	 */
	public TopKanteDirectedGraph() {
		graph = new DirectedPseudograph<>(DirectedTOPEdge.class);
	}

	/**
	 * @param md
	 *            {@link TOPKanteMetaData}
	 * @param inPathDirection
	 *            whether the direction follows the path direction
	 */
	public void addEdge(final TOPKanteMetaData md,
			final boolean inPathDirection) {
		graph.addVertex(md.getTopNodeA());
		graph.addVertex(md.getTopNodeB());
		graph.addEdge(md.getTopNodeA(), md.getTopNodeB(),
				new DirectedTOPEdge<>(md, inPathDirection));
		graph.addEdge(md.getTopNodeA(), md.getTopNodeB(),
				new DirectedTOPEdge<>(md, !inPathDirection));
	}

	/**
	 * @param md
	 *            {@link TOPKanteMetaData}
	 */
	public void addEdge(final TOPKanteMetaData md) {
		graph.addVertex(md.getTopNodeA());
		graph.addVertex(md.getTopNodeB());
		graph.addEdge(md.getTopNodeA(), md.getTopNodeB(),
				new DirectedTOPEdge<>(md, true));
		graph.addEdge(md.getTopNodeB(), md.getTopNodeA(),
				new DirectedTOPEdge<>(md, false));
	}

	/**
	 * Find all {@link CyclePath} of a edge
	 * 
	 * @param edge
	 *            the {@link TOPKanteMetaData}
	 * @return list of {@link CyclePath}
	 */
	public List<CyclePath> findAllCyclePaths(final TOPKanteMetaData edge) {
		if (!edge.getCyclePaths().isEmpty()) {
			return edge.getCyclePaths();
		}

		final TOP_Knoten from = edge.getTopNodeA();
		final TOP_Knoten to = edge.getTopNodeB();
		final AllDirectedPaths<TOP_Knoten, DirectedTOPEdge<TOPKanteMetaData>> allDirectedPaths = new AllDirectedPaths<>(
				cloneGraph(edge));
		final List<CyclePath> result = new ArrayList<>();
		final List<GraphPath<TOP_Knoten, DirectedTOPEdge<TOPKanteMetaData>>> paths = allDirectedPaths
				.getAllPaths(from, to, true, null);
		paths.forEach(path -> {
			final List<DirectedTOPEdge<TOPKanteMetaData>> edgesList = new LinkedList<>();
			DirectedTOPEdge<TOPKanteMetaData> firstEdge = graph.getEdge(from,
					to);
			edgesList.add(firstEdge);
			boolean direction = firstEdge.inTopDirection();
			for (int i = 0; i < path.getEdgeList().size(); i++) {
				final DirectedTOPEdge<TOPKanteMetaData> nextEdge = path
						.getEdgeList().get(i);
				if (firstEdge.edge().isSameDirection(nextEdge.edge())) {
					edgesList.add(
							new DirectedTOPEdge<>(nextEdge.edge(), direction));
				} else {
					edgesList.add(
							new DirectedTOPEdge<>(nextEdge.edge(), !direction));
					direction = !direction;
				}
				firstEdge = nextEdge;
			}
			result.add(new CyclePath(edgesList));
		});
		return result;
	}

	/**
	 * Find relevant length for the edge
	 * 
	 * @param md
	 *            the edge
	 */
	public void findTopKanteLength(final TOPKanteMetaData md) {
		final List<CyclePath> allPathsBetween = findAllCyclePaths(md);
		if (allPathsBetween.isEmpty()) {
			md.setLength(1);
			return;
		}

		// TO increment perfomance and give edge minimun length.
		// Sort path list by count of oposite direction edge.
		allPathsBetween.sort(
				(p1, p2) -> Integer.compare(p1.getOpositeDirectionEdge().size(),
						p2.getOpositeDirectionEdge().size()));
		allPathsBetween.forEach(path -> {
			MachtTopEdgeLength matchLength = isMatchLength(path);
			while (!matchLength.isMatch) {
				setTopKanteLength(matchLength.notMatchEdges(),
						matchLength.differentLength);
				matchLength = isMatchLength(path);
			}

			path.getEdges().forEach(edge -> {
				if (!edge.edge().alreadyRegistedLength()) {
					edge.edge().setLength(1);
				}
			});
		});
	}

	/**
	 * Set length for one or more edge in backward/forward edges list
	 * 
	 * @param edges
	 *            all edges of this list should be same direction
	 * @param different
	 *            the different between length of backward- and forward length
	 */
	@SuppressWarnings("boxing")
	private void setTopKanteLength(final List<TOPKanteMetaData> edges,
			final int different) {
		final List<TOPKanteMetaData> notRegisterLengthEdges = edges.stream()
				.filter(edge -> !edge.alreadyRegistedLength()).toList();
		final List<TOPKanteMetaData> shouldUpdateLengthEdges = notRegisterLengthEdges
				.isEmpty() ? edges : notRegisterLengthEdges;
		final TOPKanteMetaData matchEdge = getRelevantEdge(
				shouldUpdateLengthEdges, different);
		if (matchEdge != null) {
			return;
		}

		// Fall no match edge found, then try increment length of more edges
		final Map<TOPKanteMetaData, Integer> tmpMap = new HashMap<>();
		edges.forEach(edge -> tmpMap.put(edge, edge.getLength()));
		int splitCount = 2;
		while (splitCount > different) {
			final List<int[]> sumCombinations = findSumCombination(different,
					splitCount);
			for (final int[] combination : sumCombinations) {
				final Map<TOPKanteMetaData, Integer> matchEdges = new HashMap<>();
				for (int j = 0; j < splitCount; j++) {
					final int value = combination[j];
					final TOPKanteMetaData machtEdge = getRelevantEdge(tmpMap
							.keySet().stream()
							.filter(edge -> !matchEdges.containsKey(edge))
							.toList(), value);
					if (machtEdge != null) {
						matchEdges.put(machtEdge, value);
					}
				}
				if (matchEdges.size() == splitCount) {
					break;
				}

				// Undo change length, when not match
				matchEdges.keySet()
						.forEach(edge -> edge.setLength(tmpMap.get(edge)));
			}
			splitCount++;
		}
	}

	private TOPKanteMetaData getRelevantEdge(final List<TOPKanteMetaData> edges,
			final int different) {
		// A path contain a edge, whtich not registed length will don't
		// considered
		final List<Pair<TOPKanteMetaData, List<CyclePath>>> pathWithAlreadyRegistedLength = edges
				.stream().map(edge -> {
					final List<CyclePath> allCyclePaths = findAllCyclePaths(
							edge);
					final List<CyclePath> pathsWithAlreadRegistedLengthEdge = allCyclePaths
							.stream().filter(
									path -> path.getEdges().stream()
											.filter(e -> e.edge() != edge)
											.allMatch(e -> e.edge()
													.alreadyRegistedLength()))
							.toList();
					return new Pair<>(edge, pathsWithAlreadRegistedLengthEdge);
				}).toList();

		final List<Pair<TOPKanteMetaData, List<CyclePath>>> pathWithoutAlreaydRegistedLength = pathWithAlreadyRegistedLength
				.stream().filter(e -> e.getSecond().isEmpty()).toList();

		// When exsist path, which cotain a edge with not registered length,
		// then the edge, which start and end ist, ist relevant
		if (!pathWithoutAlreaydRegistedLength.isEmpty()) {
			final TOPKanteMetaData matchEdge = pathWithoutAlreaydRegistedLength
					.get(0).getFirst();
			matchEdge.setLength(matchEdge.getLength() + different);
			return matchEdge;
		}

		TOPKanteMetaData matchEdge = null;
		for (final Pair<TOPKanteMetaData, List<CyclePath>> pair : pathWithAlreadyRegistedLength) {
			final TOPKanteMetaData edge = pair.getFirst();
			final int tmpLength = edge.getLength();
			edge.setLength(different + tmpLength);

			final boolean isRelevantLength = pair.getSecond().stream()
					.map(TopKanteDirectedGraph::isMatchLength)
					.anyMatch(MachtTopEdgeLength::isMatch);
			if (isRelevantLength) {
				matchEdge = edge;
				break;
			}
			edge.setLength(tmpLength);
		}
		return matchEdge;
	}

	// The path is match length, when sum of forward edges
	// and sum of backwards edges are same
	private static MachtTopEdgeLength isMatchLength(final CyclePath path) {
		final List<TOPKanteMetaData> forwardEdges = path.getSameDirectionEdge();
		final List<TOPKanteMetaData> backwardEdges = path
				.getOpositeDirectionEdge();

		final int forwardLength = CyclePath.getPathLength(forwardEdges);
		final int backwardLength = CyclePath.getPathLength(backwardEdges);
		if (forwardLength != backwardLength) {
			return new MachtTopEdgeLength(false,
					forwardLength < backwardLength ? forwardEdges
							: backwardEdges,
					Math.abs(backwardLength - forwardLength));
		}

		return new MachtTopEdgeLength(true, Collections.emptyList(), 0);
	}

	private DirectedPseudograph<TOP_Knoten, DirectedTOPEdge<TOPKanteMetaData>> cloneGraph(
			final TOPKanteMetaData md) {
		final GraphBuilder<TOP_Knoten, DirectedTOPEdge<TOPKanteMetaData>, ? extends DirectedPseudograph<TOP_Knoten, DirectedTOPEdge<TOPKanteMetaData>>> builder = DirectedPseudograph
				.createBuilder(DirectedTOPEdge.class);
		builder.addGraph(graph);

		final DirectedPseudograph<TOP_Knoten, DirectedTOPEdge<TOPKanteMetaData>> clone = builder
				.build();
		if (md != null) {
			clone.removeEdge(md.getTopNodeA(), md.getTopNodeB());
			clone.removeEdge(md.getTopNodeB(), md.getTopNodeA());
		}

		return clone;
	}
}
