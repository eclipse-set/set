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

import com.google.common.math.BigIntegerMath;

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
	private final double[][] amnCache;
	private final double curvature;
	private final int maxIterations;

	private final double totalLength;

	/**
	 * @param radius
	 *            radius of the curve
	 * @param totalLength
	 *            total length of the curve
	 * @param iterations
	 *            number of iterations to perform during calculation
	 */
	public Bloss(final double radius, final double totalLength,
			final int iterations) {
		this.totalLength = totalLength;
		this.maxIterations = iterations;
		this.curvature = radius != 0 ? 1 / radius : 0;

		// Prepare the cache for a_m,n
		// n is at most 4m
		final int nMax = 4 * iterations;
		amnCache = new double[iterations][nMax];
		for (int i = 0; i < iterations; ++i) {
			amnCache[i] = new double[nMax];
			for (int j = 0; j < nMax; ++j) {
				// Set all values to NaN
				amnCache[i][j] = Double.NaN;
			}
		}

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
		return new double[] { x(length), y(length) };
	}

	/**
	 * Calculates the term a_m,n using a cache
	 * 
	 * @param m
	 *            value for m
	 * @param n
	 *            value for n
	 * @return a_m,n
	 */
	private double amn(final int m, final int n) {
		// Check if the value is not yet present
		// For the empty state we use Double.NaN and NaN != NaN
		if (amnCache[m - 1][n - 1] != amnCache[m - 1][n - 1]) {
			amnCache[m - 1][n - 1] = amnUncached(m, n);
		}
		return amnCache[m - 1][n - 1];
	}

	/**
	 * Calculates the term a_m,n
	 * 
	 * @param m
	 *            value for m
	 * @param n
	 *            value for n
	 * @return a_m,n
	 */
	private double amnUncached(final int m, final int n) {
		if (m == 1) {
			return an(n);
		}

		double result = 0.0;
		// First sum term
		if (n == m || n == m + 1 || n == m + 2) {

			for (int j = 1; j <= n - m + 1; ++j) {
				result += amn(m - 1, n - j) * an(j);
			}
		}
		// Second sum term
		else if (n >= m + 3 && n <= 4 * m - 3) {
			for (int j = 1; j <= 4; ++j) {
				result += amn(m - 1, n - j) * an(j);
			}
		}
		// Third sum term
		else if (n == 4 * m || n == 4 * m - 1 || n == 4 * m - 2) {
			for (int j = n - 4 * m + 4; j <= 4; ++j) {
				result += amn(m - 1, n - j) * an(j);
			}
		} else {
			// This should be unreachable
			throw new RuntimeException("Invalid value for n in amn"); //$NON-NLS-1$
		}
		return result;
	}

	/**
	 * Definition of a_n
	 * 
	 * @param n
	 *            index
	 * @return a_n
	 */
	private double an(final int n) {
		if (n == 1) {
			return curvature;
		}
		if (n == 2) {
			return 0;
		}
		if (n == 3) {
			return -curvature / Math.pow(totalLength, 2);
		}
		if (n == 4) {
			return curvature / (2 * Math.pow(totalLength, 3));
		}

		// This should be unreachable
		throw new RuntimeException("Invalid value for n in an"); //$NON-NLS-1$
	}

	private double x(final double l) {
		double result = l;
		for (int m = 2; m <= maxIterations; m += 2) {
			final double sign = Math.pow(-1, m / 2.0);
			final double fact = BigIntegerMath.factorial(m).doubleValue();

			double innerSum = 0.0;
			for (int n = m; n <= 4 * m; ++n) {
				innerSum += amn(m, n) * Math.pow(l, n + 1.0) / (n + 1);
			}

			result += sign / fact * innerSum;
		}
		return result;
	}

	private double y(final double l) {
		double result = 0.0;
		for (int m = 1; m <= maxIterations; m += 2) {
			final double sign = Math.pow(-1, (m - 1) / 2.0);
			final double fact = BigIntegerMath.factorial(m).doubleValue();

			double innerSum = 0.0;
			for (int n = m; n <= 4 * m; ++n) {
				innerSum += amn(m, n) * Math.pow(l, n + 1.0) / (n + 1);
			}

			result += sign / fact * innerSum;
		}
		return result;
	}
}
