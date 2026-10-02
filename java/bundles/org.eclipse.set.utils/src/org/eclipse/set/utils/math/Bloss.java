/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Determines points on a Bloss curve with a zero target curvature
 * 
 * The implementation follows the formulas outlined in Appendix 6 of
 * Übergangsbogenberechnung nach Dr.-Ing. Schuhr
 * 
 * @author Stuecker
 *
 */
public class Bloss {
	private final double startCurvature;
	private final double endCurvature;

	private final double firstLength;
	private final int maxIterations;

	private final boolean isInflectionCurve;

	private final double totalLength;

	/**
	 * 7 point closed Newton-Cotes rule
	 */
	private static final double[] NC_WEIGHTS = { 41, 216, 27, 272, 27, 216,
			41 };

	/**
	 * Simple bloss curve from RadiusA to a straight line.
	 * 
	 * @param radius
	 *            radius of the curve
	 * @param arcLength
	 *            total length of the curve
	 * @param iterations
	 *            number of iterations to perform during calculation
	 */
	public Bloss(final double radius, final double arcLength,
			final int iterations) {
		this(radius, 0, arcLength, iterations);
	}

	public Bloss(final double radiusA, final double radiusB,
			final double arcLength, final int iterations) {
		this.totalLength = arcLength;
		this.maxIterations = iterations;
		startCurvature = radiusA == 0 ? 0 : 1 / radiusA;
		endCurvature = radiusB == 0 ? 0 : 1 / radiusB;
		isInflectionCurve = startCurvature * endCurvature < 0 && arcLength > 0;
		firstLength = isInflectionCurve
				? arcLength * startCurvature / (startCurvature - endCurvature)
				: arcLength;
	}

	/**
	 * Calculates a number of points (defined by segmentCount) on a bloss curve
	 * 
	 * @param segmentCount
	 *            number of segments to divide the curve into
	 * 
	 * @return a list of xy-positions on the bloss curve
	 */
	public List<double[]> calculate(final int segmentCount) {
		final double segmentLength = this.totalLength / (segmentCount - 1);
		final List<double[]> positions = new ArrayList<>();
		for (var i = 0; i < segmentCount; i++) {
			positions.add(calculatePoint(segmentLength * i));
		}
		return positions;
	}

	/**
	 * Calculates a single points on a bloss curve
	 * 
	 * @param length
	 *            the length of the curve for the given point
	 * @return a xy-position on the curve
	 */
	public double[] calculatePoint(final double length) {
		if (isInflectionCurve && length > firstLength) {
			final double[] firstPart = integrate(0, firstLength);
			final double[] secondPart = integrate(firstLength, totalLength);
			return new double[] { firstPart[0] + secondPart[0],
					firstPart[1] + secondPart[1] };
		}
		return integrate(0, length);
	}

	/**
	 * 
	 * @param from
	 * @param to
	 * @return
	 */
	private double[] integrate(final double from, final double to) {
		final double[] coor = new double[2];
		if (from == to) {
			return coor;
		}
		final double factor = (to - from) / (840 * maxIterations);
		final double delta = (to - from) / maxIterations;
		final double h = delta / 6;
		for (int i = 0; i < maxIterations; i++) {
			final double x = from + i * delta;
			for (int k = 0; k < NC_WEIGHTS.length; k++) {
				final double angle = directionAngle(x + k * h);
				coor[0] += NC_WEIGHTS[k] * Math.cos(angle);
				coor[1] += NC_WEIGHTS[k] * Math.sin(angle);
			}
		}
		coor[0] *= factor;
		coor[1] *= factor;
		return coor;
	}

	/**
	 * Calculate direction angle of the tangent, measured from the +N axis,
	 * clockwise, in radians.
	 * 
	 * @param length
	 *            Arc length along the curve, measured from the element start
	 *            point. 0 ≤ l ≤ L.
	 * @return
	 */
	private double directionAngle(final double length) {
		if (!isInflectionCurve) {
			return startCurvature * length + (endCurvature - startCurvature)
					* totalLength * hermitIntegral(length / totalLength);
		}

		if (length < firstLength) {
			return startCurvature * firstLength
					* rampIntegral(length / firstLength);
		}

		final double secondLength = totalLength - firstLength;
		final double t = (totalLength - length) / secondLength;
		return 5 / 8 * startCurvature * firstLength
				+ endCurvature * secondLength * (5 / 8 * rampIntegral(t));

	}

	/**
	 * Hermit Integral with s = segmentLength / totalLength
	 * 
	 * @param s
	 *            segmentLength / totalLength
	 * @return
	 */
	private static double hermitIntegral(final double s) {
		return Math.pow(s, 3) - Math.pow(s, 4) / 2;
	}

	/**
	 * Bloss ramp Integral with t = segmentLength / firstLength
	 * 
	 * @param t
	 *            segmentLength / firstLength
	 * @return
	 */

	private static double rampIntegral(final double t) {
		return t - Math.pow(t, 3) / 2 + Math.pow(t, 4) / 8;
	}
}
