/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;

/**
 * Abstract implementation of a {@link Routing}.
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
public abstract class AbstractRouting<E, N, P> implements Routing<E, N, P> {

	protected Map<N, Set<E>> edgesOfNode;

	protected AbstractRouting() {
		edgesOfNode = new HashMap<>();
	}

	@Override
	public Set<DirectedEdge<E, N, P>> getDirectPredecessors(
			final DirectedEdge<E, N, P> directedEdge) {
		final Set<DirectedEdge<E, N, P>> result = new HashSet<>();
		final N tail = directedEdge.getTail();
		final Set<E> edges = getEdges(tail);
		for (final E e : edges) {
			if (isRoute(e, directedEdge.getElement(), tail)) {
				final DirectedEdge<E, N, P> de = getHeadEdge(tail, e);
				Assert.isNotNull(de);
				result.add(de);
			}
		}
		return result;
	}

	@Override
	public Set<DirectedEdge<E, N, P>> getDirectSuccessors(
			final DirectedEdge<E, N, P> directedEdge) {
		final Set<DirectedEdge<E, N, P>> result = new HashSet<>();
		final N head = directedEdge.getHead();
		final Set<E> edges = getEdges(head);
		for (final E e : edges) {
			if (isRoute(directedEdge.getElement(), e, head)) {
				final DirectedEdge<E, N, P> de = getTailEdge(head, e);
				Assert.isNotNull(de);
				result.add(de);
			}
		}
		return result;
	}

	@Override
	public DirectedEdge<E, N, P> getEdge(final N tail, final N head) {
		final Set<DirectedEdge<E, N, P>> result = getEdges(tail, head);
		if (result.size() == 1) {
			return result.iterator().next();
		}
		return null;
	}

	/**
	 * @param node
	 *            the node
	 * 
	 * @return all edges with the given node
	 */
	public abstract Set<E> getEdges(final N node);

	@Override
	public Set<DirectedEdge<E, N, P>> getEdges(final N tail, final N head) {
		final Set<DirectedEdge<E, N, P>> result = new HashSet<>();
		final Set<E> edges = getEdges(tail);
		for (final E e : edges) {
			if (hasNode(e, head)) {
				result.add(getTailEdge(tail, e));
			}
		}
		return result;
	}

	/**
	 * @param head
	 *            the head of the directed edge
	 * @param edge
	 *            the edge
	 * 
	 * @return the directed edge, or <code>null</code> if no such unique edge
	 *         exists
	 */
	public abstract DirectedEdge<E, N, P> getHeadEdge(final N head,
			final E edge);

	/**
	 * @param tail
	 *            the tail of the directed edge
	 * @param edge
	 *            the edge
	 * 
	 * @return the directed edge, or <code>null</code> if no such unique edge
	 *         exists
	 */
	public abstract DirectedEdge<E, N, P> getTailEdge(final N tail,
			final E edge);

	/**
	 * @param origin
	 *            the origin edge
	 * @param destination
	 *            the destination edge
	 * @param transition
	 *            the transition node
	 * 
	 * @return whether a routing from the origin to the destination via the
	 *         transition is possible
	 */
	public abstract boolean isRoute(final E origin, final E destination,
			final N transition);

	private boolean hasNode(final E e, final N head) {
		return getEdges(head).contains(e);
	}
}
