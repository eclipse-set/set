/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils;

/**
 * An interval consists of two (possibly identical) values a length and a
 * distance.
 * <p>
 * 
 * Idea:
 * <p>
 * [left ....... right]
 * <p>
 * [..................] length
 * <p>
 * [.....] distanceFromLeft
 * 
 * @param <S>
 *            the type of the interval values
 * @param <T>
 *            the type of the interval distance
 * 
 * @author Schaefer
 */
public class ValueInterval<S, T> {

	private final T distanceFromLeft;

	private final T distanceFromRight;

	private final S left;

	private final T length;

	private final S right;

	/**
	 * @param left
	 *            the left value
	 * @param right
	 *            the right value
	 * @param length
	 *            the length
	 * @param distanceFromLeft
	 *            the distance from left
	 * @param distanceFromRight
	 *            the distance from right
	 */
	public ValueInterval(final S left, final S right, final T length,
			final T distanceFromLeft, final T distanceFromRight) {
		this.left = left;
		this.right = right;
		this.length = length;
		this.distanceFromLeft = distanceFromLeft;
		this.distanceFromRight = distanceFromRight;
	}

	/**
	 * @return the distance
	 */
	public T getDistanceFromLeft() {
		return distanceFromLeft;
	}

	/**
	 * @return the distance
	 */
	public T getDistanceFromRight() {
		return distanceFromRight;
	}

	/**
	 * @return the left value
	 */
	public S getLeft() {
		return left;
	}

	/**
	 * @return the right value
	 */
	public S getRight() {
		return right;
	}

	/**
	 * @return the length
	 */
	public T getLength() {
		return length;
	}
}
