/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.math;

/**
 * Provides implementations for Agate rounding specifications.
 * 
 * @author Schaefer
 */
public class AgateRounding {

	/**
	 * @param a
	 *            the double
	 * 
	 * @return the closest long to the argument - 0.4, with ties rounding to
	 *         positive infinity
	 */
	public static long roundDown(final double a) {
		return Math.round(a - 0.4);
	}

	/**
	 * @param a
	 *            the double
	 * 
	 * @return the closest long to the argument + 0.4, with ties rounding to
	 *         positive infinity
	 */
	public static long roundUp(final double a) {
		final long roundedDown = Math.round(a - 0.5);
		final long roundedUp = roundedDown + 1;
		final double rest = a - roundedDown;
		if (rest > 0.1) {
			return roundedUp;
		}
		return roundedDown;
	}
}
