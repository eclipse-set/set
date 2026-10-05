/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.utils.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * The test follow the Bloss data from ProVI document
 */
public class BlossTest {
	private static double[] START_POINT = new double[] { 1000, 2000 };
	private static double START_ANGLE = Math.toRadians(30);
	private static final double TOLERANCE = 1.0e-4;
	boolean leftCurve;

	@SuppressWarnings("boxing")
	private static Stream<Arguments> straightToCurveReferenceValues() {
		return Stream.of(Arguments.of(0, 1000.0, 2000.0), //
				Arguments.of(10, 1005.0011, 2008.6596), //
				Arguments.of(20, 1010.0162, 2017.3111), //
				Arguments.of(30, 1015.0775, 2025.9357), //
				Arguments.of(40, 1020.2300, 2034.5060), //
				Arguments.of(50, 1025.5245, 2042.9892), //
				Arguments.of(60, 1031.0104, 2051.3498), //
				Arguments.of(70, 1036.7280, 2059.5535), //
				Arguments.of(80, 1042.7028, 2067.5718) //
		);
	}

	@SuppressWarnings("boxing")
	private static Stream<Arguments> InflectionCurveReferenceValues() {
		return Stream.of(Arguments.of(0, 1000.0, 2000.0), //
				Arguments.of(10, 1005.1424, 2008.5759), //
				Arguments.of(20, 1010.5552, 2016.9839), //
				Arguments.of(30, 1016.2033, 2025.2357), //
				Arguments.of(40, 1022.0382, 2033.3568), //
				Arguments.of(50, 1028.0020, 2041.3837), //
				Arguments.of(60, 1034.0317, 2049.3614), //
				Arguments.of(70, 1040.0680, 2057.3340), //
				Arguments.of(80, 1046.0778, 2065.3266), //
				Arguments.of(90, 1052.0352, 2073.3584),
				Arguments.of(100, 1057.9151, 2081.4470),
				Arguments.of(110, 1063.6941, 2089.6080),
				Arguments.of(120, 1069.3506, 2097.8543),
				Arguments.of(130, 1074.8662, 2106.1955),
				Arguments.of(140, 1080.2258, 2114.6377),
				Arguments.of(150, 1085.4190, 2123.1833));
	}

	Bloss testee;

	@ParameterizedTest
	@MethodSource("straightToCurveReferenceValues")
	void straightToCurveTest(final double length, final double expectX,
			final double expectY) {
		givenStraightToCurveBloss();
		expectCoordinatenCorrect(length, expectX, expectY);
	}

	private void expectCoordinatenCorrect(final double length,
			final double expectX, final double expectY) {
		final double[] localPoint = testee.calculatePoint(length);

		final double[] transformedCoord = transformCoord(localPoint);
		assertEquals(expectX, transformedCoord[0], TOLERANCE, "X at " + length); //$NON-NLS-1$
		assertEquals(expectY, transformedCoord[1], TOLERANCE, "Y at " + length); //$NON-NLS-1$
	}

	private static double[] transformCoord(final double[] local) {
		final double sin = Math.sin(START_ANGLE);
		final double cos = Math.cos(START_ANGLE);
		return new double[] { //
				START_POINT[0] + cos * local[0] - sin * local[1], //
				START_POINT[1] + sin * local[0] + cos * local[1] //
		};
	}

	private void givenStraightToCurveBloss() {
		testee = new Bloss(0, -300, 80, 10);
		leftCurve = false;
	}
}
