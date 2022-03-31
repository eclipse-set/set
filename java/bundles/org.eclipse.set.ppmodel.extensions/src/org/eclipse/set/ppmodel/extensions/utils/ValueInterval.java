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
 * [left ... right]
 * <p>
 * [..............] length
 * <p>
 * [.....] distance
 * 
 * @param <S>
 *            the type of the interval values
 * @param <T>
 *            the type of the interval distance
 * 
 * @author Schaefer
 */
public class ValueInterval<S, T> {

	private final T distance;

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
	 * @param distance
	 *            the distance
	 */
	public ValueInterval(final S left, final S right, final T length,
			final T distance) {
		this.left = left;
		this.right = right;
		this.length = length;
		this.distance = distance;
	}

	/**
	 * @return the distance
	 */
	public T getDistance() {
		return distance;
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
