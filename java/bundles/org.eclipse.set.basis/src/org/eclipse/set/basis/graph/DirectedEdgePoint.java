/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.graph;

/**
 * A point on a directed edge.
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
public class DirectedEdgePoint<E, N, P> {
	private final DirectedEdge<E, N, P> edge;
	private final P point;

	/**
	 * @param edge
	 *            the edge
	 * @param point
	 *            the point
	 */
	public DirectedEdgePoint(final DirectedEdge<E, N, P> edge, final P point) {
		this.edge = edge;
		this.point = point;
	}

	/**
	 * @return the edge
	 */
	public DirectedEdge<E, N, P> getEdge() {
		return edge;
	}

	/**
	 * @return the point
	 */
	public P getPoint() {
		return point;
	}
}
