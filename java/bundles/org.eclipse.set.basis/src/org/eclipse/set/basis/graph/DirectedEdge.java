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

/**
 * A directed edge with point objects on it.
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
public interface DirectedEdge<E, N, P> extends DirectedElement<E> {

	/**
	 * @param p
	 *            a point object
	 * 
	 * @return whether this edge contains the given point object
	 */
	boolean contains(P p);

	/**
	 * @param p1
	 *            a point object on this edge
	 * @param p2
	 *            another point object on this edge
	 * 
	 * @return the distance of the point objects
	 */
	double distance(P p1, P p2);

	/**
	 * @param p
	 *            a point object on this edge
	 * 
	 * @return the distance from the tail (start) of this edge to the given
	 *         point object
	 */
	double distanceFromTail(P p);

	/**
	 * @param p
	 *            a point object on this edge
	 * 
	 * @return the distance from the given point object to the head (end) of
	 *         this edge
	 */
	double distanceToHead(P p);

	/**
	 * @return the head (end) node of this directed edge
	 */
	N getHead();

	/**
	 * @return an iterator for the point objects of this directed edge
	 */
	Iterator<P> getIterator();

	/**
	 * @return the length of this edge
	 */
	double getLength();

	/**
	 * @return the tail (start) node of this directed edge
	 */
	N getTail();

	/**
	 * @return a key usable for caching the directed edge
	 */
	String getCacheKey();
}
