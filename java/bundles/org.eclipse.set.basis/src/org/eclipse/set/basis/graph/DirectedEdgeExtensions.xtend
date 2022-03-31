/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.graph;

/**
 * Extensions for {@link DirectedEdge}.
 */
class DirectedEdgeExtensions {

	/**
	 * @param edge this edge
	 * @param node a node
	 * 
	 * @return whether the given node is the head or tail of this edge
	 */
	static def <E, N, P> boolean contains(DirectedEdge<E, N, P> edge, N node) {
		return node == edge.tail || node == edge.head
	}
}
