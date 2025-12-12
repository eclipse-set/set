/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.graph;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.basis.cache.NoCache;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Algorithms for {@link Digraph}s.
 * 
 * @author Schaefer
 */
public class Digraphs {

	private Digraphs() {
	}

	private static final Logger logger = LoggerFactory
			.getLogger(Digraphs.class);

	private static Function<PlanPro_Schnittstelle, Cache> edgeToSubPathCacheSupplier;

	/**
	 * Set a cache supplier, to provide a cache used to store edge to subpath
	 * results.
	 * 
	 * @param edgeToSubPathCacheSupplier
	 *            the edge to subpath cache supplier
	 */
	public static void setEdgeToSubPathCacheSupplier(
			final Function<PlanPro_Schnittstelle, Cache> edgeToSubPathCacheSupplier) {
		Digraphs.edgeToSubPathCacheSupplier = edgeToSubPathCacheSupplier;
	}

	/**
	 * @param <E>
	 *            the edge type
	 * @param <N>
	 *            the node type
	 * @param <P>
	 *            the point object type
	 * 
	 * @param digraph
	 *            this digraph
	 * @param start
	 *            the starting points
	 * @param end
	 *            the ending points
	 * 
	 * @return all paths from all starting points to all ending points
	 */
	public static <E, N, P> Set<DirectedEdgePath<E, N, P>> getPaths(
			final Digraph<E, N, P> digraph, final Collection<P> start,
			final Collection<P> end) {
		final Set<DirectedEdgePath<E, N, P>> result = new HashSet<>();
		start.forEach(
				s -> end.forEach(e -> result.addAll(getPaths(digraph, s, e))));
		return result;
	}

	/**
	 * @param <E>
	 *            the edge type
	 * @param <N>
	 *            the node type
	 * @param <P>
	 *            the point object type
	 * 
	 * @param digraph
	 *            the digraph
	 * @param startPoint
	 *            the start point
	 * @param endPoint
	 *            the end point
	 * 
	 * @return the set of all paths from the start point to the end point
	 */
	public static <E, N, P> Set<DirectedEdgePath<E, N, P>> getPaths(
			final Digraph<E, N, P> digraph, final P startPoint,
			final P endPoint) {
		return digraph.getEdges()
				.stream()
				.filter(edge -> edge.contains(startPoint))
				.flatMap(startEdge -> getPaths(startEdge, digraph, startPoint,
						endPoint).stream())
				.collect(Collectors.toSet());

	}

	/**
	 * @param <E>
	 *            the edge type
	 * @param <N>
	 *            the node type
	 * @param <P>
	 *            the point object type
	 * 
	 * @param start
	 *            the starting edge
	 * @param routing
	 *            the routing
	 * 
	 * @return the set of all paths from the starting edge to the end of the
	 *         given routing
	 */
	public static <E, N, P> Set<DirectedEdgePath<E, N, P>> getPaths(
			final DirectedEdge<E, N, P> start, final Routing<E, N, P> routing) {
		return getPaths(start, routing, BigDecimal.ONE.negate());
	}

	/**
	 * @param <E>
	 *            the edge type
	 * @param <N>
	 *            the node type
	 * @param <P>
	 *            the point object type
	 * 
	 * @param start
	 *            the starting edge
	 * @param routing
	 *            the routing
	 * @param minDistance
	 *            the minimum distance (distance is ignored if value < 0)
	 * 
	 * @return the set of all paths from the starting edge to a distance at
	 *         least <b>minDistance</b> or until the end of the given routing
	 */
	public static <E, N, P> Set<DirectedEdgePath<E, N, P>> getPaths(
			final DirectedEdge<E, N, P> start, final Routing<E, N, P> routing,
			final BigDecimal minDistance) {
		return getCache(start).get(getPathCacheKey(start, routing, minDistance),
				() -> calculateSubPaths(start, routing, minDistance));
	}

	private static <E, N, P> String getPathCacheKey(
			final DirectedEdge<E, N, P> start, final Routing<E, N, P> routing,
			final BigDecimal minDistance) {
		final List<String> components = List.of(start.getCacheKey(),
				minDistance.toString(), routing.getCacheKey());
		return String.join("/", components); //$NON-NLS-1$
	}

	private static <E, N, P> Set<DirectedEdgePath<E, N, P>> calculateSubPaths(
			final DirectedEdge<E, N, P> start, final Routing<E, N, P> routing,
			final BigDecimal minDistance) {
		final Set<DirectedEdgePath<E, N, P>> subpaths = getSubPaths(start,
				minDistance, routing, routing.getEmptyPath());
		for (final DirectedEdgePath<E, N, P> path : subpaths) {
			path.prepend(start);
		}
		if (subpaths.isEmpty()) {
			final DirectedEdgePath<E, N, P> pathWithStart = routing
					.getEmptyPath();
			pathWithStart.append(start);
			subpaths.add(pathWithStart);
		}
		return subpaths;
	}

	private static <E, N, P> Cache getCache(final DirectedEdge<E, N, P> edge) {
		if (edgeToSubPathCacheSupplier != null) {
			return edgeToSubPathCacheSupplier
					.apply(edge.getPlanProSchnittstelle());
		}
		return new NoCache();
	}

	/**
	 * @param <E>
	 *            the edge type
	 * @param <N>
	 *            the node type
	 * @param <P>
	 *            the point object type
	 * 
	 * @param startEdge
	 *            the start edge
	 * @param routing
	 *            the routing
	 * @param startPoint
	 *            the start point
	 * @param endPoint
	 *            the end point
	 * 
	 * @return the set of all paths from the start point to the end point in the
	 *         direction given by the start edge
	 */
	public static <E, N, P> Set<DirectedEdgePath<E, N, P>> getPaths(
			final DirectedEdge<E, N, P> startEdge,
			final Routing<E, N, P> routing, final P startPoint,
			final P endPoint) {
		final Set<DirectedEdgePath<E, N, P>> paths = getPaths(startEdge,
				routing);
		final Set<DirectedEdgePath<E, N, P>> routes = new HashSet<>();
		for (final DirectedEdgePath<E, N, P> path : paths) {
			final DirectedEdgePath<E, N, P> route = path.subPath(startPoint,
					endPoint);
			if (route != null) {
				routes.add(route);
			}
		}
		return routes;
	}

	/**
	 * @param <E>
	 *            the edge type
	 * @param <N>
	 *            the node type
	 * @param <P>
	 *            the point object type
	 * 
	 * @param directedEdge
	 *            a directed edge
	 * 
	 * @return a pretty formated string representation for the directed edge
	 */
	public static <E, N, P> String prettyString(
			final DirectedEdge<E, N, P> directedEdge) {
		final StringBuilder builder = new StringBuilder();
		builder.append("("); //$NON-NLS-1$
		final Iterator<P> pointIterator = directedEdge.getIterator();
		boolean isFirst = true;
		while (pointIterator.hasNext()) {
			if (!isFirst) {
				builder.append(" "); //$NON-NLS-1$
			}
			final P point = pointIterator.next();
			builder.append(prettyString(point));
			isFirst = false;
		}
		builder.append(")"); //$NON-NLS-1$
		return builder.toString();
	}

	/**
	 * @param <E>
	 *            the edge type
	 * @param <N>
	 *            the node type
	 * @param <P>
	 *            the point object type
	 * 
	 * @param path
	 *            a path
	 * 
	 * @return a pretty formated string representation for the path
	 */
	public static <E, N, P> String prettyString(
			final DirectedEdgePath<E, N, P> path) {
		return String.format("{start=%s end=%s edges=%s}", path.getStart(), //$NON-NLS-1$
				path.getEnd(), getEdgesString(path));
	}

	/**
	 * @param <P>
	 *            the point object type
	 * 
	 * @param point
	 *            a point
	 * 
	 * @return a pretty formated string representation for the point
	 */
	public static <P> String prettyString(final P point) {
		return point.toString();
	}

	/**
	 * @param <E>
	 *            the edge type
	 * @param <N>
	 *            the node type
	 * @param <P>
	 *            the point object type
	 * 
	 * @param paths
	 *            a path list
	 * 
	 * @return a pretty formated string representation for the path list
	 */
	public static <E, N, P> String prettyString(
			final Set<DirectedEdgePath<E, N, P>> paths) {
		final StringBuilder builder = new StringBuilder();
		for (final DirectedEdgePath<E, N, P> path : paths) {
			builder.append(prettyString(path) + "\n"); //$NON-NLS-1$
		}
		return builder.toString();
	}

	/**
	 * @param <E>
	 *            the edge type
	 * @param <N>
	 *            the node type
	 * @param <P>
	 *            the point object type
	 * 
	 * @param paths
	 *            a collection of paths
	 * 
	 * @return a new digraph consisting of the edges of the given paths
	 */
	public static <E, N, P> Digraph<E, N, P> toDigraph(
			final Collection<DirectedEdgePath<E, N, P>> paths) {
		final DirectedEdgePath<E, N, P> aPath;
		if (paths.isEmpty()) {
			aPath = null;
		} else {
			aPath = paths.iterator().next();
		}
		return new AbstractDigraph<>() {

			private Set<DirectedEdge<E, N, P>> edges;

			{
				edges = new HashSet<>();
				for (final DirectedEdgePath<E, N, P> path : paths) {
					final List<DirectedEdge<E, N, P>> pathEdges = path
							.getEdgeList();
					for (final DirectedEdge<E, N, P> pathEdge : pathEdges) {
						edges.add(pathEdge);
					}
				}
			}

			@Override
			public Comparator<BigDecimal> getDistanceComparator() {
				if (aPath != null) {
					return aPath.getDistanceComparator();
				}
				return null;
			}

			@Override
			public Set<DirectedEdge<E, N, P>> getEdges() {
				return edges;
			}

			@Override
			public DirectedEdgePath<E, N, P> getEmptyPath() {
				if (aPath != null) {
					return aPath.getEmptyPath();
				}
				return null;
			}

			@Override
			public String getCacheKey() {
				return String.join("/", //$NON-NLS-1$
						edges.stream().map(DirectedEdge::getCacheKey).toList());
			}
		};
	}

	private static <E, N, P> String getEdgesString(
			final DirectedEdgePath<E, N, P> path) {
		final StringBuilder builder = new StringBuilder();
		builder.append("["); //$NON-NLS-1$
		final Iterator<DirectedEdge<E, N, P>> edgeIterator = path
				.getEdgeIterator();
		boolean isFirst = true;
		while (edgeIterator.hasNext()) {
			if (!isFirst) {
				builder.append(" "); //$NON-NLS-1$
			}
			final DirectedEdge<E, N, P> directedEdge = edgeIterator.next();
			builder.append(prettyString(directedEdge));
			isFirst = false;
		}
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

	private static <E, N, P> Set<DirectedEdgePath<E, N, P>> getSubPaths(
			final DirectedEdge<E, N, P> start, final BigDecimal minDistance,
			final Routing<E, N, P> routing,
			final DirectedEdgePath<E, N, P> path) {
		final Set<DirectedEdgePath<E, N, P>> result = new HashSet<>();
		final Deque<Pair<DirectedEdge<E, N, P>, DirectedEdgePath<E, N, P>>> directedEdges = new LinkedList<>();
		directedEdges.add(new Pair<>(start, path.copy()));
		for (Pair<DirectedEdge<E, N, P>, DirectedEdgePath<E, N, P>> edgeWithPath; (edgeWithPath = directedEdges
				.poll()) != null;) {
			final DirectedEdge<E, N, P> edge = edgeWithPath.getFirst();
			final Set<DirectedEdge<E, N, P>> successors = routing
					.getDirectSuccessors(edge);
			logger.debug("start={} path={}", edge, edgeWithPath.getSecond()); //$NON-NLS-1$
			if (successors.isEmpty()) {
				result.add(edgeWithPath.getSecond());
			}
			for (final DirectedEdge<E, N, P> successor : successors) {
				final DirectedEdgePath<E, N, P> successorPath = edgeWithPath
						.getSecond()
						.copy();
				if (DirectedEdgePathExtension.contains(path, successor)) {
					result.add(successorPath);
				} else {
					successorPath.append(successor);
					final Comparator<BigDecimal> comparator = routing
							.getDistanceComparator();
					if (comparator.compare(successorPath.getLength(),
							minDistance) < 0
							|| minDistance.compareTo(BigDecimal.ZERO) < 0) {
						directedEdges.add(new Pair<>(successor, successorPath));
					} else {
						result.add(successorPath);
					}
				}
			}
		}

		return result;
	}
}
