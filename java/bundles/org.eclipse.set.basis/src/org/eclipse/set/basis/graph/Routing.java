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
import java.util.Set;

/**
 * A routing describes a succession of edges.
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
public interface Routing<E, N, P> {

	/**
	 * @param directedEdge
	 *            a directed edge
	 * 
	 * @return the direct predecessors of the edge
	 */
	Set<DirectedEdge<E, N, P>> getDirectPredecessors(
			DirectedEdge<E, N, P> directedEdge);

	/**
	 * @param directedEdge
	 *            a directed edge
	 * 
	 * @return the direct successors of the edge
	 */
	Set<DirectedEdge<E, N, P>> getDirectSuccessors(
			DirectedEdge<E, N, P> directedEdge);

	/**
	 * @return a comparator to compare distances
	 */
	Comparator<BigDecimal> getDistanceComparator();

	/**
	 * @param tail
	 *            the tail
	 * @param head
	 *            the head
	 * 
	 * @return the unique edge from tail to head or <code>null</code> if such an
	 *         edge does not exists
	 */
	DirectedEdge<E, N, P> getEdge(N tail, N head);

	/**
	 * @param tail
	 *            the tail
	 * @param head
	 *            the head
	 * 
	 * @return all edges from tail to head
	 */
	Set<DirectedEdge<E, N, P>> getEdges(N tail, N head);

	/**
	 * @return an empty path
	 */
	DirectedEdgePath<E, N, P> getEmptyPath();

	/**
	 * @return a string to use for caching
	 */
	String getCacheKey();
}
