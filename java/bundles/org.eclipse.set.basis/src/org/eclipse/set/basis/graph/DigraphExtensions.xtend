/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.graph;

import java.util.Set

/**
 * Extensions for {@link Digraph}.
 */
class DigraphExtensions {

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
	static def <E, N, P> Set<DirectedEdgePath<E, N, P>> getPaths(
		Digraph<E, N, P> digraph, P startPoint, P endPoint) {
		return digraph.edges.filter[contains(startPoint)].map [
			Digraphs.getPaths(it, digraph, startPoint, endPoint)
		].flatten.toSet
	}
}
