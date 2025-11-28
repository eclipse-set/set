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
import org.eclipse.emf.common.util.Enumerator

import static extension org.eclipse.set.utils.math.BigDecimalExtensions.*

/**
 * Extensions for {@link Enumerator}.
 * 
 * @author Schaefer
 */
class DoubleExtensions {

	/**
	 * @param length the length
	 * 
	 * @result the formatted length
	 */
	static def String toTableDecimal(Double length) {
		return BigDecimal.valueOf(length).toTableDecimal
	}
	
		/**
	 * @param length the length
	 * 
	 * @result the formatted length
	 */
	static def String toTableDecimal(Double length, int decimalPlace) {
		return BigDecimal.valueOf(length).toTableDecimal(decimalPlace)
	}
	

	/**
	 * @param length the length
	 * 
	 * @result the formatted length
	 */
	static def String toTableInteger(Double length) {
		return BigDecimal.valueOf(length).toTableInteger
	}
}
