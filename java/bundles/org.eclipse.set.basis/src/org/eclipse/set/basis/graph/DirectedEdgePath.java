/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.graph;

import java.util.Iterator;
import java.util.List;

/**
 * A path of directed edges.
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
public interface DirectedEdgePath<E, N, P> extends Digraph<E, N, P> {

	/**
	 * Appends an edge to the end of this path.
	 * 
	 * @param e
	 *            the edge
	 */
	void append(DirectedEdge<E, N, P> e);

	/**
	 * @return a copy of this path
	 */
	DirectedEdgePath<E, N, P> copy();

	/**
	 * Determines the distance between two points on this path. If one of the
	 * points is <code>null</code>, then the distance between the beginning of
	 * the path (<b>p1</b>=<code>null</code>) or the distance between the end of
	 * the path ( <b>p2</b>=<code>null</code>) and the remaining point is
	 * determined. If both points are <code>null</code> then the
	 * {@link #getLength() length} of the path is returned.
	 * 
	 * @param p1
	 *            a point on this path
	 * @param p2
	 *            another point on this path
	 * 
	 * @return the distance between the two points
	 */
	double distance(P p1, P p2);

	/**
	 * @param i
	 *            the edge index
	 * 
	 * @return the edge at the given index
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of range
	 */
	DirectedEdge<E, N, P> get(int i);

	/**
	 * @param p
	 *            a point object
	 * 
	 * @return the first edge with the given point object
	 * 
	 * @throws IllegalArgumentException
	 *             if the point object is on no edge of this path
	 */
	DirectedEdge<E, N, P> get(P p);

	/**
	 * @param head
	 *            the head node
	 * 
	 * @return the edge on this path with the given head node or
	 *         <code>null</code> if no such edge exists
	 */
	DirectedEdge<E, N, P> getEdgeForHead(final N head);

	/**
	 * @param tail
	 *            the tail node
	 * 
	 * @return the edge on this path with the given tail node or
	 *         <code>null</code> if no such edge exists
	 */
	DirectedEdge<E, N, P> getEdgeForTail(final N tail);

	/**
	 * @return an iterator for the directed edges of this path
	 */
	Iterator<DirectedEdge<E, N, P>> getEdgeIterator();

	/**
	 * @return the list of edges according to the order in this path
	 */
	List<DirectedEdge<E, N, P>> getEdgeList();

	/**
	 * Returns an iterator for the edge point objects of this path.
	 * 
	 * @return an iterator for the point objects of this path
	 */
	Iterator<DirectedEdgePoint<E, N, P>> getEdgePointIterator();

	/**
	 * @return the end point object of this path
	 */
	P getEnd();

	/**
	 * @param e
	 *            the directed edge
	 * 
	 * @return the index of the directed edge
	 */
	int getIndex(DirectedEdge<E, N, P> e);

	/**
	 * @return the total length of this path
	 */
	double getLength();

	/**
	 * Returns an iterator for the point objects of this path. For points
	 * connected to the edge at the tail or head implementations may provide the
	 * same point on adjacent edges. That is to say the iterator may return the
	 * same point twice.
	 * 
	 * @return an iterator for the point objects of this path
	 */
	Iterator<P> getPointIterator();

	/**
	 * @return the start point object of this path
	 */
	P getStart();

	/**
	 * Prepends a
	 * 
	 * @param e
	 */
	void prepend(DirectedEdge<E, N, P> e);

	/**
	 * @param end
	 *            the end point
	 */
	void setEnd(P end);

	/**
	 * @param start
	 *            the start point
	 */
	void setStart(P start);

	/**
	 * @param subStart
	 *            the start point
	 * @param subEnd
	 *            the end point
	 * 
	 * @return the subpath from <b>subStart</b> to <b>subEnd</b> or
	 *         <code>null</code> if no such subpath exists
	 */
	DirectedEdgePath<E, N, P> subPath(P subStart, P subEnd);
}
