/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.graph

import org.eclipse.set.basis.graph.Digraphs
import org.eclipse.set.basis.graph.DirectedEdge
import org.eclipse.set.basis.graph.DirectedEdgePath
import org.eclipse.set.basis.graph.Routing
import java.util.Collection
import java.util.Set

/**
 * Extensions for {@link DirectedEdge}.
 */
class DirectedEdgeExtensions extends org.eclipse.set.basis.graph.DirectedEdgeExtensions {

	/**
	 * @param edge this starting edge
	 * @param routing the routing
	 * @param start the starting points
	 * @param end the ending points
	 * 
	 * @return the set of all paths from the starting points to the ending points in the
	 * direction given by this starting edge
	 */
	static def <E, N, P> Set<DirectedEdgePath<E, N, P>> getPaths(
		DirectedEdge<E, N, P> edge, Routing<E, N, P> routing,
		Collection<P> start, Collection<P> end) {
		return start.map[s|end.map[edge.getPaths(routing,s,it)]].flatten.flatten.toSet
	}

	/**
	 * @param edge this starting edge
	 * @param routing the routing
	 * @param start the starting point
	 * @param end the ending point
	 * 
	 * @return the set of all paths from the starting point to the ending point in the
	 * direction given by this starting edge
	 */
	static def <E, N, P> Set<DirectedEdgePath<E, N, P>> getPaths(
		DirectedEdge<E, N, P> startEdge, Routing<E, N, P> routing, P start, P end) {
		return Digraphs.getPaths(startEdge, routing, start, end)
	}
}
