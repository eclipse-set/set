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

import java.util.List;

import org.eclipse.set.utils.math.Clothoid;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link Clothoid}. IMPROVE: actualy given't reliable test data (also
 * Clothoid coordinate). Therefore this test should be for the first disable
 * 
 * @author Stuecker
 */
@Disabled
public class ClothoidTest {
	List<double[]> result;

	Clothoid testee;

	// Acceptable tolerance for calculated results
	static final double TOLERANCE = 0.002;

	/**
	 * Tests {@link Clothoid} using reference values for the unit clothoid
	 * (clothoid with A^2 = 1)
	 */
	@Test
	public void testReferenceCalculation() {
		givenUnitClothoid();
		whenCalculatingUnitClothoid();
		thenResultMatchesReference();
	}

	private static void assertPositionEquality(final double expectedX,
			final double expectedY, final double[] actual) {
		assertEquals(expectedX, actual[0], TOLERANCE);
		assertEquals(expectedY, actual[1], TOLERANCE);
	}

	private void givenUnitClothoid() {
		testee = new Clothoid(1, 1, 10);
	}

	private void thenResultMatchesReference() {
		assertPositionEquality(0.0, 0.0, result.get(0));
		assertPositionEquality(0.099999, 0.000166, result.get(1));
		assertPositionEquality(0.199800, 0.006661, result.get(2));
		assertPositionEquality(0.299932, 0.014975, result.get(3));
		assertPositionEquality(0.398404, 0.026590, result.get(4));
		assertPositionEquality(0.496853, 0.039811, result.get(5));
		assertPositionEquality(0.594624, 0.059616, result.get(6));
		assertPositionEquality(0.691469, 0.080954, result.get(7));
		assertPositionEquality(0.787291, 0.105451, result.get(8));
		assertPositionEquality(0.881901, 0.133057, result.get(9));
		assertPositionEquality(0.970298, 0.197019, result.get(10));
	}

	private void whenCalculatingUnitClothoid() {
		result = testee.getPoints(11);
	}
}
