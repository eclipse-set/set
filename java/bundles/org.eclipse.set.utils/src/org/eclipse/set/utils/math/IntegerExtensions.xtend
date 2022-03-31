/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.math

/**
 * Extensions for {@link Integer}.
 * 
 * @author Schaefer
 */
class IntegerExtensions {

	/**
	 * @param i this integer
	 * 
	 * @return array with the integers 0, ..., i - 1
	 */
	def static int[] getPredecessors(int i) {
		val result = newLinkedList()

		for (var j = 0; j < i; j++) {
			result.add(j)
		}

		return result
	}
}
