/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

/**
 * Can count something relative to an expected total.
 * 
 * @author Schaefer
 */
class Counter {
	
	val int total
	var int count
	
	/**
	 * @param total the expected total
	 */
	new(int total) {
		this.total = total
		this.count = 0;
	}

	/**
	 * Count.
	 */
	def Counter count() {
		count++
		return this
	}
	
	override toString() {
		return '''«count»/«total»'''
	}
}
