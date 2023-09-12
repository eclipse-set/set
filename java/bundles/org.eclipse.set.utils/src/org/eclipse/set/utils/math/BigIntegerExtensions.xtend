/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.math

import java.math.BigInteger

/** 
 * Extensions for {@link BigInteger}.
 * 
 * @author Schaefer
 */
class BigIntegerExtensions extends org.eclipse.xtext.xbase.lib.BigIntegerExtensions {

	/**
	 * @param integer this integer
	 * @param otherInteger the other integer
	 * 
	 * @return whether this integer is not <code>null</code> and greater than
	 * the given (not <code>null</code>) other integer
	 */
	static def boolean isNotNullAndGreater(
		BigInteger integer,
		BigInteger otherInteger
	) {
		if (integer === null) {
			return false
		}
		return integer.compareTo(otherInteger) > 0
	}
	
	static def int toInteger(BigInteger integer) {
		if (integer === null) {
			return 0
		}
		return integer.intValue
	}
}
