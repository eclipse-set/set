/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.graph

import org.eclipse.set.basis.graph.Digraph
import org.eclipse.set.basis.graph.Digraphs
import org.eclipse.set.basis.graph.DirectedEdgePath
import java.util.Collection
import java.util.Set

/**
 * Extensions for {@link Digraph}.
 * 
 * @author Schaefer
 */
class DigraphExtensions extends org.eclipse.set.basis.graph.DigraphExtensions {

	/**
	 * @param digraph this digraph
	 * @param start the starting points
	 * @param end the ending points
	 * 
	 * @return all paths from all starting points to all ending points
	 */
	static def <E, N, P> Set<DirectedEdgePath<E, N, P>> getPaths(
		Digraph<E, N, P> digraph, Collection<P> start, Collection<P> end) {
		return start.map [ s |
			end.map [ e |
				Digraphs.getPaths(digraph, s, e)
			]
		].flatten.flatten.toSet
	}
}
