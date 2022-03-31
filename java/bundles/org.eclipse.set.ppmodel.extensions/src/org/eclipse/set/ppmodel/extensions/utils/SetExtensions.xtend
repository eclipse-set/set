/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import java.util.Collection
import java.util.Set

/**
 * This class extends {@link Set}.
 * 
 * @author Schaefer
 */
class SetExtensions {
	
	/**
	 * @param set this set
	 * @param collection a collection
	 * 
	 * @returns a set containing all elements of <b>set</b> which are
	 * also contained in <b>collection</b>
	 */
	def static <T> Set<T> intersection(Set<T> set, Collection<T> collection) {
		return set.filter[collection.contains(it)].toSet
	}
	
	/**
	 * @param iterable a non-empty iterable of sets
	 * 
	 * @returns the intersection of the sets of the iterable
	 * 
	 * @throws NoSuchElementException if the iterable is empty
	 */
	def static <T> Set<T> intersection(Iterable<Set<T>> iterable) {
		val iter = iterable.iterator
		val first = iter.next
		return iter.fold(first, [u,s|u.intersection(s)])
	}
}
