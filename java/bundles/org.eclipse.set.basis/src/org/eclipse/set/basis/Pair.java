/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

/**
 * A pair of two objects.
 * 
 * @param <S>
 *            type of the first element
 * @param <T>
 *            type of the second element
 * 
 * @author Schaefer
 */
public class Pair<S, T> {

	private static boolean nullSaveEquals(final Object left,
			final Object right) {
		if (left == null) {
			return right == null;
		}
		return left.equals(right);
	}

	private final S first;

	private final T second;

	/**
	 * @param first
	 *            the first element
	 * @param second
	 *            the second element
	 */
	public Pair(final S first, final T second) {
		this.first = first;
		this.second = second;
	}

	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		if (other == null) {
			return false;
		}
		if (other.getClass() != getClass()) {
			return false;
		}
		final Pair<?, ?> otherPair = (Pair<?, ?>) other;
		if (!nullSaveEquals(first, otherPair.first)) {
			return false;
		}
		if (!nullSaveEquals(second, otherPair.second)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the first element
	 */
	public S getFirst() {
		return first;
	}

	/**
	 * @return the second element
	 */
	public T getSecond() {
		return second;
	}

	@Override
	public int hashCode() {
		int result = 17;
		final int hashMultiplier = 59;
		final int firstHash = first == null ? 0 : first.hashCode();
		result = result * hashMultiplier + firstHash;
		final int secondHash = second == null ? 0 : second.hashCode();
		result = result * hashMultiplier + secondHash;
		return result;
	}

	@SuppressWarnings("nls")
	@Override
	public String toString() {
		return "Pair [first=" + first + ", second=" + second + "]";
	}
}
