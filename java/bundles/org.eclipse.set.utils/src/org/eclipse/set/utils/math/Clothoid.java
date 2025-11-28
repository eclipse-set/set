/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.set.basis.constants.ToolboxConstants;

import com.google.common.math.BigIntegerMath;

/**
 * Calculates a clothoid transition curve. The formulation see:
 * https://de.wikipedia.org/wiki/Klothoide#Moderne_Berechnungsverfahren.
 * 
 * The calculation has been generalized to also support egg clothoids which is
 * clothoid that is already starting with a certain radius. See:
 * https://de.wikipedia.org/wiki/Eiklothoide
 * 
 * @author Stuecker
 *
 */
public class Clothoid {

	/**
	 * Calculates the index'th term for the clothoid formula
	 * 
	 * Equivalent to: sign * T^i/(i*2*i+1) where sign is 1 if index % 4 <= 1 and
	 * -1 otherwise
	 * 
	 * @param index
	 *            The index of the term
	 * @param T
	 * @return the index'th term
	 */
	static double clothoidTerm(final int index, final double T) {
		if (!Double.isFinite(T)) {
			return 0;
		}
		// Sign: positive if index is 0 or 1, negative otherwise
		final BigDecimal factor = BigDecimal.valueOf(index % 4 <= 1 ? 1 : -1);
		// Divisor: index! * (2 * index + 1)
		final BigDecimal divisor = new BigDecimal(
				BigIntegerMath.factorial(index)
						.multiply(BigInteger.valueOf(2 * index + 1l)));
		// Power: T^index
		final BigDecimal power = BigDecimal.valueOf(T).pow(index);
		return factor.multiply(power)
				.divide(divisor, ToolboxConstants.ROUNDING_TO_PLACE,
						RoundingMode.HALF_UP)
				.doubleValue();
	}

	private final int clothoidIter;

	private final double totalLength;

	/**
	 * factor on how much the radius increases per distance on the clothoid.
	 * Comparable with the factor "m" of a normal linear function f(x) = m*x + n
	 */
	private final double curvatureIncrease;

	/**
	 * the distance to the requested starting point on the clothoid. This is
	 * supposed to be 0 when we want to start from a straight but when we plan
	 * to start from another curve we typically have starting radius and this
	 * distance is the length until we are at that point on the clothoid where
	 * the starting radius is active.
	 */
	private final double distanceToStartingPoint;

	private final double[] startingPoint;

	/**
	 * @param radius
	 *            the radius at the end of the curve
	 * @param totalLength
	 *            total length of the curve
	 * @param iterations
	 *            the number of series iterations to use for calculating
	 *            xy-positions
	 */
	public Clothoid(final double radius, final double totalLength,
			final int iterations) {
		this(0, radius, totalLength, iterations);
	}

	/**
	 * @param radiusA
	 *            the radius at the start of the curve
	 * @param radiusB
	 *            the radius at the end of the curve
	 * @param totalLength
	 *            total length of the curve
	 * @param iterations
	 *            the number of series iterations to use for calculating
	 *            xy-positions
	 */
	public Clothoid(final double radiusA, final double radiusB,
			final double totalLength, final int iterations) {
		this.clothoidIter = iterations;
		this.totalLength = totalLength;
		final double startCurvature = radiusA == 0 ? 0 : 1 / radiusA;
		final double endCurvature = radiusB == 0 ? 0 : 1 / radiusB;
		this.curvatureIncrease = (endCurvature - startCurvature) / totalLength;
		this.distanceToStartingPoint = startCurvature / this.curvatureIncrease;
		this.startingPoint = clothoidSegment(this.distanceToStartingPoint);
	}

	/**
	 * Calculates a number of points (defined by segmentCount) on a clothoid
	 * 
	 * @param segmentCount
	 *            the number of segments to divide the length into
	 * 
	 * @return a list of xy-positions on the clothoid
	 */
	public List<double[]> getPoints(final int segmentCount) {
		final double segmentLength = this.totalLength / (segmentCount - 1);
		final List<double[]> positions = new ArrayList<>();

		for (var i = 0; i < segmentCount; i++) {
			positions.add(getPoint(segmentLength * i));
		}
		return positions;
	}

	/**
	 * Calculates a single point on a clothoid
	 * 
	 * @param length
	 *            the length of the clothoid for the given point
	 * @return a xy-position on the clothoid
	 */
	public double[] getPoint(final double length) {
		// for the case of an egg clothoid we need to add the distance to the
		// starting point and remove that again to get the correct coordinate on
		// the clothoid
		final double[] point = clothoidSegment(
				this.distanceToStartingPoint + length);
		return new double[] { point[0] - startingPoint[0],
				point[1] - startingPoint[1] };
	}

	/**
	 * Calculates a single point on the clothoid from a given length and
	 * clothoid size
	 * 
	 * @param L
	 *            The length of the clothoid segment (L)
	 * @return the xy-position of the point at the end of the segment
	 */
	private double[] clothoidSegment(final double L) {
		final double T = this.curvatureIncrease * L * L / 2;
		return new double[] { clothoidX(L, T), clothoidY(L, T) };
	}

	/**
	 * Calculates the x position for a clothoid defined by T of a given length
	 * 
	 * @param L
	 *            length of the clothoid segment
	 * @param T
	 *            tangent angle between initial and final points
	 * @return the x coordinate
	 */
	private double clothoidX(final double L, final double T) {
		double result = 0;
		for (var i = 0; i < clothoidIter; i++) {
			result += clothoidTerm(2 * i, T);
		}
		return L * result;
	}

	/**
	 * Calculates the y position for a clothoid defined by T of a given length
	 * 
	 * @param L
	 *            length of the clothoid segment
	 * @param T
	 *            tangent angle between initial and final points
	 * @return the y coordinate
	 */
	private double clothoidY(final double L, final double T) {
		double result = 0;
		for (var i = 0; i < clothoidIter; i++) {
			result += clothoidTerm(2 * i + 1, T);
		}
		return L * result;
	}
}
