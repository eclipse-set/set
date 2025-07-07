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
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.set.basis.graph.DirectedEdge;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;

import com.google.common.collect.Lists;

/**
 * An edge implementation with a string edge element type, a character node type
 * and an integer point type. Only edge elements with two characters are valid
 * and these characters, together with the direction of the edge, will imply the
 * head and tail of the edge. The points are evenly spread on the edge and have
 * a distance of 1 to successors and predecessors.
 * 
 * @author Schaefer
 */
@SuppressWarnings({ "boxing", "nls" })
public class TestEdge implements DirectedEdge<String, Character, Integer> {

	private String element;
	private boolean isForwards;
	private final List<Integer> points = Lists.newArrayList();

	/**
	 * Create the edge with the given edge element and points. The points are
	 * assumed to be in the implied order of the given edge element and the
	 * initial direction is "forwards".
	 * 
	 * @param element
	 *            the edge element
	 * @param points
	 *            the points on the edge
	 */
	public TestEdge(final String element, final Integer... points) {
		this.element = element;
		isForwards = true;
		this.points.addAll(Arrays.asList(points));
	}

	@Override
	public String getElement() {
		return element;
	}

	@Override
	public boolean isForwards() {
		return isForwards;
	}

	@Override
	public void setElement(final String element) {
		checkEdgeElement(element);
		this.element = element;
	}

	@Override
	public void setForwards(final boolean isForwards) {
		this.isForwards = isForwards;
	}

	@Override
	public boolean contains(final Integer point) {
		return points.contains(point);
	}

	@Override
	public BigDecimal distance(final Integer p1, final Integer p2) {
		final int i1 = points.indexOf(p1);
		checkIndex(i1, p1);
		final int i2 = points.indexOf(p2);
		checkIndex(i2, p2);

		// all points have a distance of 1 to each other
		return BigDecimal.valueOf(Math.abs(i2 - i1));
	}

	@Override
	public BigDecimal distanceFromTail(final Integer point) {
		checkPoint(point);

		// we have a first point, because "point" is a point on this edge
		final Integer start = points.get(0);

		return distance(start, point);
	}

	@Override
	public BigDecimal distanceToHead(final Integer point) {
		checkPoint(point);

		// we have a last point, because "point" is a point on this edge
		final Integer start = points.get(points.size() - 1);

		return distance(start, point);
	}

	@Override
	public Character getTail() {
		if (isForwards) {
			return element.charAt(0);
		}
		return element.charAt(1);
	}

	@Override
	public Character getHead() {
		if (isForwards) {
			return element.charAt(1);
		}
		return element.charAt(0);
	}

	@Override
	public Iterator<Integer> getIterator() {
		if (isForwards) {
			return points.iterator();
		}
		return Lists.reverse(points).iterator();
	}

	@Override
	public BigDecimal getLength() {
		return BigDecimal.valueOf(points.size());
	}

	@Override
	public String getCacheKey() {
		return toString();
	}

	@Override
	public String toString() {
		return element + (isForwards ? "/F" : "/B");
	}

	/**
	 * Tests whether this edge has the head and tail implied by the given edge
	 * element string (first character = tail, last character = head). We do not
	 * test against this edge's element. This may or may not be equals to the
	 * given edge element.
	 * 
	 * @param edgeElement
	 *            the edge element
	 * 
	 * @return whether this edge has the head and tail implied by the given edge
	 *         element string
	 */
	public boolean isEdge(final String edgeElement) {
		checkEdgeElement(edgeElement);
		return getTail().equals(edgeElement.charAt(0))
				&& getHead().equals(edgeElement.charAt(1));
	}

	private static void checkEdgeElement(final String element) {
		if (element.length() != 2) {
			throw new IllegalArgumentException(element);
		}
	}

	private static void checkIndex(final int index, final Integer point) {
		if (index < 0) {
			throw new NoSuchElementException(point.toString());
		}
	}

	private void checkPoint(final Integer point) {
		checkIndex(points.indexOf(point), point);
	}

	@Override
	public PlanPro_Schnittstelle getPlanProSchnittstelle() {
		throw new UnsupportedOperationException(
				"not implemented for test model"); //$NON-NLS-1$
	}
}
