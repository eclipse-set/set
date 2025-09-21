/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.basis.geometry;

/**
 * 
 */
public class GeometryOptionsBuilder {

	/**
	 * @param stepSize
	 *            the step size
	 * @param precision
	 *            the accuracy
	 * @param minSegmentCount
	 *            the segment count
	 */
	public static record GeometryOptions(double stepSize, int precision,
			int minSegmentCount) {

		/**
		 * @return the step size
		 */
		public double stepSize() {
			return stepSize;
		}

		/**
		 * @return the accuracy
		 */
		public int precision() {
			return precision;
		}

		/**
		 * @return the segment count
		 */
		public int minSegmentCount() {
			return minSegmentCount;
		}

	}

	private static final int DEFAULT_MIN_COUNT = 20;
	private static final double DEFAULT_STEP_SIZE = Math.PI / 100;

	private double stepSize = DEFAULT_STEP_SIZE;
	private int count = DEFAULT_MIN_COUNT;
	private int percision = 0;

	/**
	 * Default constructor
	 */
	public GeometryOptionsBuilder() {
		// do nothing
	}

	/**
	 * @return the {@link GeometryCalculationOptions}
	 */
	public GeometryOptions build() {
		return new GeometryOptions(stepSize, percision, count);
	}

	/**
	 * @param stepSize
	 *            the chord step size
	 * @return the builders
	 */
	public GeometryOptionsBuilder setStepSize(final double stepSize) {
		this.stepSize = stepSize;
		return this;
	}

	/**
	 * @param percision
	 *            the percision
	 * @return the builder
	 */
	public GeometryOptionsBuilder setAccuracy(final int percision) {
		this.percision = percision;
		return this;
	}

	/**
	 * @param count
	 *            the segment count
	 * @return the builders
	 */
	public GeometryOptionsBuilder setSegmentCount(final int count) {
		this.count = count;
		return this;
	}
}
