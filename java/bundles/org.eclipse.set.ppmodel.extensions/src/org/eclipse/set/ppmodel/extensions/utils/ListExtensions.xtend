/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt
import java.util.Collection
import java.util.List
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Extensions for {@link List}.
 */
class ListExtensions extends CollectionExtensions {
	
	static final Logger logger = LoggerFactory
		.getLogger(typeof(ListExtensions));
	
	/**
	 * @param list this list
	 * @param object the Ur Objekt
	 * 
	 * @return the unique element in this list
	 * 
	 * @throws IllegalArgumentException if there is no unique element
	 */
	static def <T> T getUniqueWithWarning(List<T> list, Ur_Objekt object) {
		val msg = '''Argument contains «list.size» elements for «
			object.identitaet.wert».'''
		if (list.size > 0) {
			if (list.size > 1) {
				logger.warn(msg)
			}
			return list.get(0)
		}
		throw new IllegalArgumentException(msg)
	}
	
	/**
	 * Adds the item to the list if it does not already contain the item.
	 * 
	 * @param list this list
	 * @param item an object
	 */
	static def <T> void addNew(List<T> list, T item) {
		if (!list.contains(item)) {
			list.add(item)
		}
	}
	
	/**
	 * Adds all items to the list which are not already contained by the list.
	 * 
	 * @param list this list
	 * @param items the items
	 */
	static def <T> void addAllNew(List<T> list, Collection<T> items) {
		items.forEach[list.addNew(it)]
	}
	
	static def <T> List<T> cloneLinkedList(List<T> list) {
		val clone = newLinkedList
		clone.addAll(list)
		return clone
	}
}
