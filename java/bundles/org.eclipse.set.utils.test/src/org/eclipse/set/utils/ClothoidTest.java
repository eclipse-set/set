/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.set.utils.math.Clothoid;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Clothoid}.
 * 
 * @author Stuecker
 */
@SuppressWarnings("nls")
public class ClothoidTest {
	List<double[]> result;

	Clothoid testee;
	int segmentCount;
	List<double[]> expected;

	// Acceptable tolerance for calculated results
	static final double TOLERANCE = 0.002;

	/**
	 * Tests {@link Clothoid} using reference values for the unit clothoid
	 * (clothoid with A^2 = 1)
	 */
	@Test
	public void testReferenceCalculation() {
		givenUnitClothoid();
		givenGeometryOptions(11);
		givenExpectedResult(0.0, 0.0, // l=0.0
				0.100000, 0.000167, // l=0.1
				0.199992, 0.001333, // l=0.2
				0.299939, 0.004499, // l=0.3
				0.399744, 0.010662, // l=0.4
				0.499219, 0.020810, // l=0.5
				0.598059, 0.035917, // l=0.6
				0.695810, 0.056922, // l=0.7
				0.791847, 0.084711, // l=0.8
				0.885349, 0.120084, // l=0.9
				0.975288, 0.163714 // l=1.0
		);
		whenCalculatingUnitClothoid();
		thenResultMatchesReference();
	}

	/**
	 * Tests {@link Clothoid} using egg clothoid with very high start radius so
	 * that it should be equal to reference values for the unit clothoid
	 * {@link #testReferenceCalculation()}
	 */
	@Test
	public void testReferenceEggCalculation() {
		givenEggClothoid();
		givenGeometryOptions(11);
		givenExpectedResult(0.0, 0.0, // l=0.0
				0.100000, 0.000167, // l=0.1
				0.199992, 0.001333, // l=0.2
				0.299939, 0.004499, // l=0.3
				0.399744, 0.010662, // l=0.4
				0.499219, 0.020810, // l=0.5
				0.598059, 0.035917, // l=0.6
				0.695810, 0.056922, // l=0.7
				0.791847, 0.084711, // l=0.8
				0.885349, 0.120084, // l=0.9
				0.975288, 0.163714 // l=1.0
		);
		whenCalculatingUnitClothoid();
		thenResultMatchesReference();
	}

	private static void assertPositionEquality(final double[] expected,
			final double[] actual, final int index) {
		assertEquals(expected[0], actual[0], TOLERANCE,
				"X coordinate of point " + index + " is invalid");
		assertEquals(expected[1], actual[1], TOLERANCE,
				"Y coordinate of point " + index + " is invalid");
	}

	private void givenUnitClothoid() {
		testee = new Clothoid(1, 1, 10);
	}

	private void givenEggClothoid() {
		testee = new Clothoid(99999999999999999999999999999999999999f, 1, 1, 5);
	}

	private void givenGeometryOptions(final int segmentCount) {
		this.segmentCount = segmentCount;
	}

	private void givenExpectedResult(final double... expected) {
		assertEquals(0, expected.length % 2,
				"Requires even amount of coordinates");
		assertEquals(this.segmentCount, expected.length / 2,
				"Needs to have length of given segment count");
		final List<double[]> positions = new ArrayList<>();
		for (var i = 0; i < expected.length; i += 2) {
			positions.add(new double[] { expected[i], expected[i + 1] });
		}
		this.expected = positions;
	}

	private void thenResultMatchesReference() {
		assertEquals(segmentCount, result.size());

		if (this.expected != null) {
			for (int i = 0; i < expected.size(); i++) {
				assertPositionEquality(expected.get(i), result.get(i), i);
			}
		}
	}

	private void whenCalculatingUnitClothoid() {
		result = testee.getPoints(segmentCount);
	}
}
