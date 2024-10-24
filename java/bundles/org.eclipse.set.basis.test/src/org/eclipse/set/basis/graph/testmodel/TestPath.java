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
import java.util.List;

import org.eclipse.set.basis.graph.AbstractDirectedEdgePath;
import org.eclipse.set.basis.graph.DirectedEdge;
import org.eclipse.set.basis.graph.DirectedEdgePath;

/**
 * A directed edge path implementation with a string edge type, a character node
 * type and an integer point type.
 * 
 * @author Schaefer
 */
public class TestPath
		extends AbstractDirectedEdgePath<String, Character, Integer> {

	/**
	 * Create an empty path.
	 */
	public TestPath() {
		super(null, null, null);
	}

	/**
	 * @param edges
	 *            the edges in the correct order and direction
	 * @param start
	 *            the start point
	 * @param end
	 *            the end point
	 */
	public TestPath(final List<DirectedEdge<String, Character, Integer>> edges,
			final Integer start, final Integer end) {
		super(edges, start, end);
	}

	@Override
	public DirectedEdgePath<String, Character, Integer> copy() {
		return new TestPath(getEdgeList(), getStart(), getEnd());
	}

	@Override
	public Comparator<BigDecimal> getDistanceComparator() {
		return BigDecimal::compareTo;
	}

	@Override
	public DirectedEdgePath<String, Character, Integer> getEmptyPath() {
		return new TestPath();
	}

	@Override
	public String getCacheKey() {
		throw new UnsupportedOperationException(
				"not implemented for test model"); //$NON-NLS-1$
	}
}
