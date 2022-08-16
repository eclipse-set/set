/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.geometry;

/**
 * An array of XY-coordinates
 */
class CoordinateArray {
	private final double[] data;
	private int insertIndex;

	public CoordinateArray(final int size) {
		this.data = new double[size * 2];
	}

	/**
	 * Adds an XY-Coordinate
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 */
	public void add(final double x, final double y) {
		data[insertIndex++] = x;
		data[insertIndex++] = y;
	}

	/**
	 * @return raw array
	 */
	public double[] getData() {
		return data;
	}

	/**
	 * Moves all coordinates by the given values
	 * 
	 * @param x
	 *            x offset
	 * @param y
	 *            y offset
	 */
	public void offsetBy(final double x, final double y) {
		for (int i = 0; i < data.length / 2; i++) {
			data[i * 2] += x;
			data[i * 2 + 1] += y;
		}
	}

	private void swap(final int a, final int b) {
		final double tmp = data[a];
		data[a] = data[b];
		data[b] = tmp;
	}

	/**
	 * Reverses the coordinate array in place.
	 */
	public void reverse() {
		final int limit = (insertIndex - 1) / 2;
		for (int i = 0; i < limit; i += 2) {
			swap(i, insertIndex - i - 2);
			swap(i + 1, insertIndex - i - 1);
		}
	}
}