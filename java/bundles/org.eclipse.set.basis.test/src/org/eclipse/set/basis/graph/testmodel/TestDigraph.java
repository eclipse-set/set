/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.basis.graph.testmodel;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Set;

import org.eclipse.set.basis.graph.AbstractDigraph;
import org.eclipse.set.basis.graph.DirectedEdge;
import org.eclipse.set.basis.graph.DirectedEdgePath;

import com.google.common.collect.Sets;

/**
 * A digraph implementation with a string edge type, a character node type and
 * an integer point type.
 * 
 * @author Schaefer
 */
public class TestDigraph extends AbstractDigraph<String, Character, Integer> {

	private final Set<DirectedEdge<String, Character, Integer>> edges = Sets
			.newHashSet();

	@Override
	public Set<DirectedEdge<String, Character, Integer>> getEdges() {
		return edges;
	}

	@Override
	public Comparator<BigDecimal> getDistanceComparator() {
		return BigDecimal::compareTo;
	}

	@Override
	public DirectedEdgePath<String, Character, Integer> getEmptyPath() {
		return new TestPath();
	}

	/**
	 * Add the given edge to this digraph.
	 * 
	 * @param edge
	 *            the edge to be added
	 */
	public void addEdge(final TestEdge edge) {
		edges.add(edge);
	}

	@Override
	public String getCacheKey() {
		return String.join("/", //$NON-NLS-1$
				edges.stream().map(DirectedEdge::getCacheKey).toList());
	}
}
