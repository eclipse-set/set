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
	public (T)=>String filling
	
	/**
	 * @param condition the condition
	 * @param condition the filling
	 */
	new(
		(T)=>Boolean condition,
		(T)=>String filling
	) {
		this.condition = condition
		this.filling = filling
	}	
}
