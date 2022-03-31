/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import java.util.Collection

/**
 * Extensions for {@link Collection}.
 */
class CollectionExtensions {

	/**
	 * @param collection this collection
	 * 
	 * @return the unique element in this collection
	 * 
	 * @throws IllegalArgumentException if there is no unique element
	 */
	static def <T> T getUnique(Collection<T> collection) {
		if (collection.size == 1) {
			return collection.get(0)
		}
		throw new IllegalArgumentException(
			'''Argument contains «collection.size» elements.'''
		)
	}
	
	/**
	 * @param collection this collection
	 * 
	 * @return the unique element in this collection or null if there is no unique element
	 */
	static def <T> T getUniqueOrNull(Collection<T> collection) {
		if (collection.size == 1) {
			return collection.get(0)
		}
		return null
	}
}
