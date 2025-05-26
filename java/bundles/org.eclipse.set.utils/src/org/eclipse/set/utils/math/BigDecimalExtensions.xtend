/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.math

import java.math.BigDecimal
import java.math.RoundingMode
import org.eclipse.emf.common.util.Enumerator
import java.text.DecimalFormat

/**
 * Extensions for {@link Enumerator}.
 * 
 * @author Schaefer
 */
class BigDecimalExtensions {

	/**
	 * @param length the length
	 * 
	 * @return the formatted length
	 */
	static def String toTableDecimal(BigDecimal length) {
		if (length !== null) {
			return String.format("%.3f", length)
		}
		return null
	}


	static def String toTableDecimal(BigDecimal length, int decimalPlace) {
		if (length !== null) {
			val decimal = length.setScale(decimalPlace, RoundingMode.FLOOR)
			return decimal.toPlainString
		}
	}
	/**
	 * @param length the length
	 * @param multiplier the multiplier
	 * 
	 * @return the formatted length
	 */
	static def String toTableInteger(BigDecimal length, int multiplier) {
		if (length !== null) {
			return String.format("%.0f",
				length * BigDecimal.valueOf(multiplier))
		}
		return null
	}

	/**
	 * @param length the length
	 * 
	 * @return the formatted length
	 */
	static def String toTableInteger(BigDecimal length) {
		if (length !== null) {
			return length.toTableInteger(1)
		}
		return null
	}

	/**
	 * Convert number value to bigdecimal
	 */
	static def <T extends Number> BigDecimal toBigDecimal(T value) {
		if (value instanceof BigDecimal) {
			return value
		}
		try {
			return BigDecimal.valueOf(value.doubleValue)
		} catch (Exception e) {
			throw new RuntimeException(e)
		}

	}

	/**
	 * @param length the length
	 * 
	 * @return the rounded and formatted length
	 */
	static def String toTableIntegerAgateUp(BigDecimal length) {
		if (length !== null) {
			return Long.toString(AgateRounding.roundUp(length.doubleValue))
		}
		return null
	}

	/**
	 * @param length the length
	 * 
	 * @return the rounded and formatted length
	 */
	static def String toTableIntegerAgateDown(BigDecimal length) {
		if (length !== null) {
			return Long.toString(AgateRounding.roundDown(length.doubleValue))
		}
		return null
	}

	/**
	 * @param value the big decimal to multiply
	 * @param multiplyValue the multiply value
	 * @return the result
	 */
	static def <T extends Number> BigDecimal multiplyValue(BigDecimal value,
		T multiplyValue) {
		if (value !== null) {
			return value.multiply(multiplyValue.toBigDecimal)
		}
		return null
	}

	/**
	 * @param value the big decimal to multiply
	 * @param multiplyValue the multiply value
	 * @return the result
	 */
	static def <T extends Number> BigDecimal divideValue(BigDecimal value,
		T divideValue) {
		if (value !== null) {
			return value.divide(divideValue.toBigDecimal,
				RoundingMode.DOWN)
		}
		return null
	}

	static def double toDouble(BigDecimal value) {
		if (value === null) {
			return 0.0
		}
		return value.doubleValue
	}
}
