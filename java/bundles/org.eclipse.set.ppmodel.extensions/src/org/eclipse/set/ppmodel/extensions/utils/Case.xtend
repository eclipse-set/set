/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import java.util.List
import java.util.Comparator

/**
 * Describes a switch case.
 * 
 * @author Schaefer
 */
class Case<T> {

	/**
	 * the condition
	 */
	public (T)=>Boolean condition
	
	/**
	 * the filling
	 */
	public (T)=>Iterable<String> filling
	
	public String seperator
	
	public Comparator<String> comparator
	
	/**
	 * @param condition the condition
	 * @param condition the filling
	 */
	new(
		(T)=>Boolean condition,
		(T)=>Iterable<String> filling,
		String seperator,
		Comparator<String> comparator
	) {
		this.condition = condition
		this.filling = filling
		this.seperator = seperator
		this.comparator = comparator
	}	
	
	new((T) => Boolean condition,
		(T) => String filling
	) {
		this.condition = condition
		this.filling = filling.andThen[it !== null ? List.of(it) : emptyList]
		this.seperator = null
	}	
}
