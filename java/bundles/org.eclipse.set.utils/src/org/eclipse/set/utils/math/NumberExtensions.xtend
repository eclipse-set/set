/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.math

/**
 * Extensions for {@link Number}.
 * 
 * @author Schaefer
 */
class NumberExtensions {

	/**
	 * @param number this number
	 * 
	 * @return the int value of the number, if the number is not
	 * <code>null</code>; 0 otherwise
	 */
	static def int intValueOrZero(Number number) {
		if (number === null) {
			return 0
		}
		return number.intValue
	}

	/**
	 * @param number this number
	 * 
	 * @return the int value of the number, if the number is not
	 * <code>null</code>; 0 otherwise
	 */
	static def double doubleValueOrZero(Number number) {
		if (number === null) {
			return 0
		}
		return number.doubleValue
	}

	/**
	 * @param number this number
	 * 
	 * @return the int value of the number, if the number is not
	 * <code>null</code>; 0 otherwise
	 */
	static def double doubleValueOrMax(Number number) {
		if (number === null) {
			return Double.MAX_VALUE
		}
		return number.doubleValue
	}
}
