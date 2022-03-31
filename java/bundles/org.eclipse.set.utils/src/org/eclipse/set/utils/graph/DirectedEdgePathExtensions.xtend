/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.graph

import org.eclipse.set.basis.graph.DirectedEdgePath
import org.eclipse.set.basis.graph.DirectedEdgePathExtension
import java.util.Collection
import java.util.Set

/**
 * Extensions for {@link DirectedEdgePath}.
 */
class DirectedEdgePathExtensions extends DirectedEdgePathExtension {

	/**
	 * @param path this path
	 * @param start the starting points
	 * @param end the ending points
	 * 
	 * @return the unique distance from all start
	 */
	static def <E, N, P> Set<P> intersection(DirectedEdgePath<E, N, P> path,
		Collection<P> points) {
		return points.filter[path.contains(it)].toSet
	}

	/**
	 * @param path this path
	 * @param point the point
	 * 
	 * @return whether this path contains the given point
	 */
	static def <E, N, P> boolean contains(DirectedEdgePath<E, N, P> path,
		P point) {
		return path.edgeList.exists[contains(point)]
	}
}
