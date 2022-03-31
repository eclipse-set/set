/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.graph;

import java.util.LinkedList
import java.util.List

/**
 * Extensions for {@link DirectedEdgePath}.
 */
class DirectedEdgePathExtension {

	/**
	 * @param path this path
	 * @param edge an edge
	 * 
	 * @return whether the given edge is an edge of this path
	 */
	static def <E, N, P> boolean contains(DirectedEdgePath<E, N, P> path,
		DirectedEdge<E, N, P> edge) {
		return path.edgeIterator.toList.contains(edge)
	}

	/**
	 * @param path this path
	 * @param node a node
	 * 
	 * @return all edges of the path connected to the given node
	 */
	def static <E, N, P> List<DirectedEdge<E, N, P>> getEdges(
		DirectedEdgePath<E, N, P> path, N node) {
		val result = new LinkedList
		val successor = path.getEdgeForTail(node)
		val predecessor = path.getEdgeForHead(node)
		if (successor !== null) {
			result.add(successor)
		}
		if (predecessor !== null) {
			result.add(predecessor)
		}
		return result
	}

	static def <E, N, P> List<N> getNodeList(DirectedEdgePath<E, N, P> path) {
		val edges = path.edgeList
		if (edges.empty) {
			return emptyList
		}
		val result = newLinkedList(edges.head.tail)
		edges.forEach[result.add(head)]
		return result
	}
}
