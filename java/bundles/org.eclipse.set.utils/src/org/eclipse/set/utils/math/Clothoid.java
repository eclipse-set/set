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

import com.google.common.math.BigIntegerMath;

/**
 * Calculates a clothoid transition curve. The formulation see:
 * https://de.wikipedia.org/wiki/Klothoide#Moderne_Berechnungsverfahren
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
				.divide(divisor, RoundingMode.HALF_UP)
				.doubleValue();
	}

	private final int clothoidIter;

	private final double totalLength;

	private final double clothoidParameter;

	/**
	 * @param radius
	 *            the radius of the curve
	 * @param totalLength
	 *            total length of the curve
	 * @param iterations
	 *            the number of series iterations to use for calculating
	 *            xy-positions
	 */
	public Clothoid(final double radius, final double totalLength,
			final int iterations) {
		this.clothoidIter = iterations;
		this.totalLength = totalLength;
		clothoidParameter = radius * totalLength;
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
		return clothoidSegment(length, clothoidParameter);
	}

	/**
	 * Calculates a single point on the clothoid from a given length and
	 * clothoid size
	 * 
	 * @param L
	 *            The length of the clothoid segment (L)
	 * @param Asq
	 *            The size of the clothoid squared (A^2)
	 * @return the xy-position of the point at the end of the segment
	 */
	private double[] clothoidSegment(final double L, final double Asq) {
		final double T = L * L / (2 * Asq);
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
