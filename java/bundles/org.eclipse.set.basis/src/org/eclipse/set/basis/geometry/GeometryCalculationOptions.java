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

import org.eclipse.set.basis.geometry.GeometryOptionsBuilder.GeometryOptions;

/**
 * Options for calculation geometry
 * 
 * @author truong
 */
public class GeometryCalculationOptions {
	// Configuration options for Bloss curves
	private static final int DEFAULT_BLOSS_SEGMENTS_MIN = 10;
	private static final double DEFAULT_BLOSS_SEGMENTS_PER_LENGTH = 0.5;
	private static final int DEFAULT_BLOSS_PRECISION = 20;

	// Configuration options for Clothoids
	private static final int DEFAULT_CLOTHOID_SEGMENTS_MIN = 10;
	private static final double DEFAULT_CLOTHOID_SEGMENTS_PER_LENGTH = 0.5;
	private static final int DEFAULT_CLOTHOID_PRECISION = 5;

	private final GeometryOptions chordOptions;
	private final GeometryOptions blossOptions;
	private final GeometryOptions clothoidOptions;

	/**
	 * @param chordOptions
	 *            the chord options
	 * @param blossOptions
	 *            the bloss options
	 * @param clothoidOptions
	 *            the clothoid options
	 */
	public GeometryCalculationOptions(final GeometryOptions chordOptions,
			final GeometryOptions blossOptions,
			final GeometryOptions clothoidOptions) {
		this.chordOptions = chordOptions;
		this.blossOptions = blossOptions;
		this.clothoidOptions = clothoidOptions;
	}

	/**
	 * @return the chord options
	 */
	public GeometryOptions getChordOptions() {
		return chordOptions;
	}

	/**
	 * @return the bloss options
	 */
	public GeometryOptions getBlossOptions() {
		return blossOptions;
	}

	/**
	 * @return the clothoid options
	 */
	public GeometryOptions getClothoidOptions() {
		return clothoidOptions;
	}

	/**
	 * @return the default options
	 */
	public static GeometryCalculationOptions getDefaultOptions() {
		return new GeometryCalculationOptionsBuilder().build();
	}

	/**
	 * The builder for {@link GeometryCalculationOptions}
	 */
	public static class GeometryCalculationOptionsBuilder {
		private GeometryOptions chordOptionsBuilder;
		private GeometryOptions blossOptionsBuilder;
		private GeometryOptions clothoidOptionsBuilder;

		/**
		 * 
		 */
		public GeometryCalculationOptionsBuilder() {
			chordOptionsBuilder = new GeometryOptionsBuilder().build();
			blossOptionsBuilder = new GeometryOptionsBuilder()
					.setStepSize(DEFAULT_BLOSS_SEGMENTS_PER_LENGTH)
					.setAccuracy(DEFAULT_BLOSS_PRECISION)
					.setSegmentCount(DEFAULT_BLOSS_SEGMENTS_MIN)
					.build();
			clothoidOptionsBuilder = new GeometryOptionsBuilder()
					.setSegmentCount(DEFAULT_CLOTHOID_SEGMENTS_MIN)
					.setAccuracy(DEFAULT_CLOTHOID_PRECISION)
					.setStepSize(DEFAULT_CLOTHOID_SEGMENTS_PER_LENGTH)
					.build();
		}

		/**
		 * @param chordOptions
		 *            the chord options
		 * @return the builder
		 */
		public GeometryCalculationOptionsBuilder setChordOptions(
				final GeometryOptions chordOptions) {
			this.chordOptionsBuilder = chordOptions;
			return this;
		}

		/**
		 * @param blossOptions
		 *            the bloss options
		 * @return the builder
		 */
		public GeometryCalculationOptionsBuilder setBlossOptions(
				final GeometryOptions blossOptions) {
			this.blossOptionsBuilder = blossOptions;
			return this;
		}

		/**
		 * @param clothoidOptions
		 *            the clothoid options
		 * @return the builder
		 */
		public GeometryCalculationOptionsBuilder setClothoidOptions(
				final GeometryOptions clothoidOptions) {
			this.clothoidOptionsBuilder = clothoidOptions;
			return this;
		}

		/**
		 * @return the {@link GeometryCalculationOptions}
		 */
		public GeometryCalculationOptions build() {
			return new GeometryCalculationOptions(chordOptionsBuilder,
					blossOptionsBuilder, clothoidOptionsBuilder);
		}
	}
}
