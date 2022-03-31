/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.graph;

import java.util.Set;

/**
 * Description of a digraph.
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
// IMPROVE: minimize interface
public interface Digraph<E, N, P> extends Routing<E, N, P> {

	/**
	 * @param node
	 *            a node
	 * 
	 * @return the direct predecessors of the node
	 */
	Set<N> getDirectPredecessors(N node);

	/**
	 * @param node
	 *            a node
	 * 
	 * @return the direct successors of the node
	 */
	Set<N> getDirectSuccessors(N node);

	/**
	 * @return the edges
	 */
	Set<DirectedEdge<E, N, P>> getEdges();

	/**
	 * @return the nodes
	 */
	Set<N> getNodes();
}
