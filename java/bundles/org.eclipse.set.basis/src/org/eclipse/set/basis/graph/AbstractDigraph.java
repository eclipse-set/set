/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Common implementations for {@link Digraph}.
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
public abstract class AbstractDigraph<E, N, P> implements Digraph<E, N, P> {

	@Override
	public Set<DirectedEdge<E, N, P>> getDirectPredecessors(
			final DirectedEdge<E, N, P> edge) {
		final Set<DirectedEdge<E, N, P>> result = new HashSet<>();
		for (final DirectedEdge<E, N, P> e : getEdges()) {
			if (e.getHead() == edge.getTail()) {
				result.add(e);
			}
		}
		return result;
	}

	@Override
	public Set<N> getDirectPredecessors(final N node) {
		final Set<N> result = new HashSet<>();
		for (final DirectedEdge<E, N, P> e : getEdges()) {
			if (e.getHead() == node) {
				result.add(e.getTail());
			}
		}
		return result;
	}

	@Override
	public Set<DirectedEdge<E, N, P>> getDirectSuccessors(
			final DirectedEdge<E, N, P> edge) {
		final Set<DirectedEdge<E, N, P>> result = new HashSet<>();
		for (final DirectedEdge<E, N, P> e : getEdges()) {
			if (e.getTail() == edge.getHead()) {
				result.add(e);
			}
		}
		return result;
	}

	@Override
	public Set<N> getDirectSuccessors(final N node) {
		final Set<N> result = new HashSet<>();
		for (final DirectedEdge<E, N, P> e : getEdges()) {
			if (e.getTail() == node) {
				result.add(e.getHead());
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

	@Override
	public Set<DirectedEdge<E, N, P>> getEdges(final N tail, final N head) {
		final Set<DirectedEdge<E, N, P>> result = new HashSet<>();
		for (final DirectedEdge<E, N, P> edge : getEdges()) {
			if (edge.getTail().equals(tail) && edge.getHead().equals(head)) {
				result.add(edge);
			}
		}
		return result;
	}

	@Override
	public Set<N> getNodes() {
		final Set<N> result = new HashSet<>();
		for (final DirectedEdge<E, N, P> e : getEdges()) {
			result.add(e.getTail());
			result.add(e.getHead());
		}
		return result;
	}
}
