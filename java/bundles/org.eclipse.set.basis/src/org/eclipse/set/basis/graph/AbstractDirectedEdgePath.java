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
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.eclipse.core.runtime.Assert;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.basis.cache.NoCache;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;

import com.google.common.collect.Iterators;

/**
 * Common implementations for {@link DirectedEdgePath}.
 * 
 * @param <E>
 *            the edge type
 * @param <N>
 *            the node type
 * @param <P>
 *            the point object type
 * 
 * @author Schaefer
 */
public abstract class AbstractDirectedEdgePath<E, N, P>
		extends AbstractDigraph<E, N, P> implements DirectedEdgePath<E, N, P> {

	private static boolean equals(final Object o1, final Object o2) {
		if (o1 == null || o2 == null) {
			return o1 == null && o2 == null;
		}
		return o1.equals(o2);
	}

	private final LinkedList<DirectedEdge<E, N, P>> edges = new LinkedList<>();

	private P end;

	private P start;

	protected static Function<PlanPro_Schnittstelle, Cache> edgeToPointsCacheSupplier;

	/**
	 * Set a cache supplier, to provide a cache used to store edge to point
	 * results.
	 * 
	 * @param edgeToPointsCacheSupplier
	 *            the edge to points cache supplier
	 */
	public static void setEdgeToPointsCacheSupplier(
			final Function<PlanPro_Schnittstelle, Cache> edgeToPointsCacheSupplier) {
		AbstractDirectedEdgePath.edgeToPointsCacheSupplier = edgeToPointsCacheSupplier;
	}

	/**
	 * @param edges
	 *            the edges in the correct order and direction
	 * @param start
	 *            the start point
	 * @param end
	 *            the end point
	 */
	protected AbstractDirectedEdgePath(final List<DirectedEdge<E, N, P>> edges,
			final P start, final P end) {
		if (edges != null) {
			this.edges.addAll(edges);
		}
		this.start = start;
		this.end = end;
	}

	@Override
	public void append(final DirectedEdge<E, N, P> e) {
		edges.add(e);
	}

	@Override
	public BigDecimal distance(final P p1, final P p2) {
		if (p1 == null) {
			return distanceFromStart(p2);
		}
		if (p2 == null) {
			return distanceToEnd(p1);
		}

		final int idx1 = getIndex(p1);
		final int idx2 = getIndex(p2);

		if (idx1 < idx2) {
			return distance(p1, idx1, p2, idx2);
		}

		if (idx2 < idx1) {
			return distance(p2, idx2, p1, idx1);
		}

		final DirectedEdge<E, N, P> e = get(idx1);
		return e.distance(p1, p2);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof final DirectedEdgePath<?, ?, ?> path) {
			return equals(path.getStart(), getStart())
					&& equals(path.getEnd(), getEnd())
					&& equals(path.getEdgeList(), getEdgeList());
		}
		return false;
	}

	@Override
	public DirectedEdge<E, N, P> get(final int i) {
		return edges.get(i);
	}

	@Override
	public DirectedEdge<E, N, P> get(final P p) {
		for (final DirectedEdge<E, N, P> edge : edges) {
			if (edge.contains(p)) {
				return edge;
			}
		}
		return null;
	}

	@Override
	public DirectedEdge<E, N, P> getEdgeForHead(final N head) {
		final Set<DirectedEdge<E, N, P>> result = new HashSet<>();
		for (final DirectedEdge<E, N, P> edge : getEdges()) {
			if (edge.getHead() == head) {
				result.add(edge);
			}
		}
		Assert.isTrue(result.size() <= 1);
		if (result.size() == 1) {
			return result.iterator().next();
		}
		return null;
	}

	@Override
	public DirectedEdge<E, N, P> getEdgeForTail(final N tail) {
		final Set<DirectedEdge<E, N, P>> result = new HashSet<>();
		for (final DirectedEdge<E, N, P> edge : getEdges()) {
			if (edge.getTail() == tail) {
				result.add(edge);
			}
		}
		Assert.isTrue(result.size() <= 1);
		if (result.size() == 1) {
			return result.iterator().next();
		}
		return null;
	}

	@Override
	public Iterator<DirectedEdge<E, N, P>> getEdgeIterator() {
		return edges.iterator();
	}

	@Override
	public List<DirectedEdge<E, N, P>> getEdgeList() {
		return edges;
	}

	@Override
	public Iterator<DirectedEdgePoint<E, N, P>> getEdgePointIterator() {
		// all edge points on all edges
		final List<DirectedEdgePoint<E, N, P>> directedEdgePoints = new LinkedList<>();
		final Iterator<DirectedEdge<E, N, P>> edgeIter = getEdgeIterator();

		while (edgeIter.hasNext()) {
			final DirectedEdge<E, N, P> edge = edgeIter.next();

			// Its possible but unlikely that this class will be instantiated
			// with different point types during runtime and that cache values
			// from different point contexts match. In this case a class cast
			// exception will be thrown when accessing concrete point instances
			// at a later time.
			// Actually the edgeToPointsCache is doing some runtime type
			// checking to fail fast but this is out of scope here.
			final List<P> pointList = getCache(edge).get(edge.getCacheKey(),
					() -> getPointList(edge));
			pointList.stream()
					.map(point -> new DirectedEdgePoint<>(edge, point))
					.forEach(directedEdgePoints::add);
		}

		// only the points
		final List<P> points = directedEdgePoints.stream()
				.map(DirectedEdgePoint::getPoint)
				.toList();

		// points between start and end of path
		final List<DirectedEdgePoint<E, N, P>> pathDirectedEdgePoints;
		if (start != null && end != null) {
			final int fromIndex = points.indexOf(start);
			checkIndex(fromIndex, start);
			final int toIndex = points.indexOf(end);
			checkIndex(toIndex, end);
			pathDirectedEdgePoints = directedEdgePoints.subList(fromIndex,
					toIndex + 1);
		} else if (start != null) {
			final int fromIndex = points.indexOf(start);
			checkIndex(fromIndex, start);
			pathDirectedEdgePoints = directedEdgePoints.subList(fromIndex,
					directedEdgePoints.size());
		} else if (end != null) {
			final int toIndex = points.indexOf(end);
			checkIndex(toIndex, end);
			pathDirectedEdgePoints = directedEdgePoints.subList(0, toIndex + 1);
		} else {
			pathDirectedEdgePoints = directedEdgePoints;
		}

		return pathDirectedEdgePoints.iterator();
	}

	private List<P> getPointList(final DirectedEdge<E, N, P> edge) {
		return IteratorExtensions.toList(edge.getIterator());
	}

	protected Cache getCache(final DirectedEdge<E, N, P> edge) {
		if (edgeToPointsCacheSupplier != null) {
			return edgeToPointsCacheSupplier
					.apply(edge.getPlanProSchnittstelle());
		}
		return new NoCache();
	}

	@Override
	public Set<DirectedEdge<E, N, P>> getEdges() {
		return new HashSet<>(edges);
	}

	@Override
	public P getEnd() {
		return end;
	}

	@Override
	public int getIndex(final DirectedEdge<E, N, P> e) {
		return edges.indexOf(e);
	}

	@Override
	public BigDecimal getLength() {
		return distance(getStart(), getEnd());
	}

	@Override
	public Iterator<P> getPointIterator() {
		return Iterators.transform(getEdgePointIterator(),
				DirectedEdgePoint::getPoint);
	}

	@Override
	public P getStart() {
		return start;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17 * hash + (start != null ? start.hashCode() : 0);
		hash = 17 * hash + (end != null ? end.hashCode() : 0);
		hash = 17 * hash + edges.hashCode();
		return hash;
	}

	@Override
	public void prepend(final DirectedEdge<E, N, P> e) {
		edges.addFirst(e);
	}

	@Override
	public void setEnd(final P end) {
		this.end = end;
	}

	@Override
	public void setStart(final P start) {
		this.start = start;
	}

	@Override
	public DirectedEdgePath<E, N, P> subPath(final P subStart, final P subEnd) {
		final DirectedEdge<E, N, P> startEdge = get(subStart);
		final DirectedEdge<E, N, P> endEdge = get(subEnd);
		if (startEdge != null && endEdge != null) {
			final int startIdx = getIndex(startEdge);
			final int endIdx = getIndex(endEdge);
			if (startIdx < endIdx) {
				return createSubPath(startIdx, endIdx, subStart, subEnd);
			}
			if (startIdx == endIdx) {
				final BigDecimal subStartDistance = startEdge
						.distanceFromTail(subStart);
				final BigDecimal subEndDistance = endEdge
						.distanceFromTail(subEnd);
				final Comparator<BigDecimal> distanceComparator = getDistanceComparator();
				if (distanceComparator.compare(subStartDistance,
						subEndDistance) <= 0) {
					return createSubPath(startIdx, endIdx, subStart, subEnd);
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return String.format("%s{start=%s end=%s edges=%s}", super.toString(), //$NON-NLS-1$
				getStart(), getEnd(), getEdgesString());
	}

	private void checkIndex(final int index, final P point) {
		if (index < 0) {
			throw new IllegalArgumentException(String
					.format("Point %s not on this path.", point.toString())); //$NON-NLS-1$
		}
	}

	private DirectedEdgePath<E, N, P> createSubPath(final int startIdx,
			final int endIdx, final P subStart, final P subEnd) {
		final DirectedEdgePath<E, N, P> result = getEmptyPath();
		for (int i = startIdx; i <= endIdx; i++) {
			// get(i) may be expensive for linked list
			result.append(edges.get(i));
		}
		result.setStart(subStart);
		result.setEnd(subEnd);

		return result;
	}

	private BigDecimal distance(final P pStart, final int idxStart,
			final P pEnd, final int idxEnd) {
		if (idxStart > idxEnd) {
			return BigDecimal.ZERO;
		}

		if (idxStart != idxEnd) {
			BigDecimal startDistance;
			if (pStart != null) {
				startDistance = get(idxStart).distanceToHead(pStart);
			} else {
				startDistance = get(idxStart).getLength();
			}

			BigDecimal endDistance;
			if (pEnd != null) {
				endDistance = get(idxEnd).distanceFromTail(pEnd);
			} else {
				endDistance = get(idxEnd).getLength();
			}

			BigDecimal inBetweenDistance = BigDecimal.ZERO;
			for (int i = idxStart + 1; i < idxEnd; i++) {
				inBetweenDistance = inBetweenDistance.add(get(i).getLength());
			}

			return startDistance.add(inBetweenDistance).add(endDistance);
		}

		// idxStart == idxEnd
		if (pStart == null && pEnd == null) {
			return get(idxStart).getLength();
		}
		if (pStart == null) {
			return get(idxStart).distanceFromTail(pEnd);
		}
		return get(idxStart).distanceToHead(pStart);
	}

	private BigDecimal distanceFromStart(final P p) {
		if (getStart() != null) {
			return distance(getStart(), p);
		}
		if (p != null) {
			return distance(null, 0, p, getIndex(p));
		}
		return distance(null, 0, null, edges.size() - 1);
	}

	private BigDecimal distanceToEnd(final P p) {
		if (getEnd() != null) {
			return distance(getEnd(), p);
		}
		return distance(p, getIndex(p), null, edges.size() - 1);
	}

	private String getEdgesString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("["); //$NON-NLS-1$
		boolean isFirst = true;
		for (final DirectedEdge<E, N, P> edge : edges) {
			if (!isFirst) {
				builder.append(", "); //$NON-NLS-1$
			}
			builder.append(edge.toString());
			isFirst = false;
		}
		builder.append("]"); //$NON-NLS-1$
		return builder.toString();
	}

	private int getIndex(final P p) {
		final DirectedEdge<E, N, P> e = get(p);
		if (e == null) {
			throw new IllegalArgumentException(p.toString());
		}
		return getIndex(e);
	}
}
