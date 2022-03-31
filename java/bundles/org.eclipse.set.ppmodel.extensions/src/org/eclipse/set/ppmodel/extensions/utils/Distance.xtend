/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import java.util.Comparator

/**
 * This class can compare Abstand values.
 * 
 * @author Schaefer
 */
class Distance implements Comparator<Double> {
	
	static val double TOLERANCE = 0.1
	
	/**
	 * Compares Abstand values with a given tolerance.
	 * 
	 * @param a first Abstand
	 * @param b second Abstand
	 * 
     * @return a negative integer, zero, or a positive integer as the
     * first Abstand is less than, equal to, or greater than the
     * second.
	 */
	def static int compare(double a, double b) {
		if (Math.abs(a - b) <= TOLERANCE) {
			return 0
		}
		if (a < b) {
			return -1
		}
		return 1
	}
	
	override compare(Double o1, Double o2) {
		return compare(o1.doubleValue, o2.doubleValue)
	}
}
