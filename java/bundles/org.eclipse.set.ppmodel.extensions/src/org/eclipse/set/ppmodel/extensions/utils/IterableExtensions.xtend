/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import java.util.LinkedList
import java.util.List

import static extension org.eclipse.set.basis.Lists.*
import java.util.function.Function
import java.util.ArrayList

/**
 * This class extends {@link Iterable}.
 * 
 * @author Schaefer
 */
class IterableExtensions {

	/**
	 * @param iterator this iterator
	 * 
	 * @return a list that contains all the entries of the given iterator in
	 * the same order with no multiple elements
	 */
	static def <T> List<T> toUniqueList(Iterable<T> iterator) {
		val result = new LinkedList<T>
		result.addAllNew(iterator.toList)
		return result
	}


	/**
	 * Groups an iterable into consecutive groups which have a common value defined by a condition
	 * 
	 * For example when grouping by string length with the following arguments
	 * 	iterable = #["A", "B", "CD", "EF", "G", "HI", "JKL"]
	 * 	condition = [str | str.length]
	 * then the result is 
	 * 	[["A", "B"], ["CD", "EF"], ["G"], ["HI"], ["JKL"]] 
	 * 
	 * @param iterable the iterable <T>
	 * @param condition a condition which transforms an instance of T to a comparable entity U
	 * @return consecutive groups according to condition
	 */
	static def <T, U> Iterable<Iterable<T>> consecutiveGroups(Iterable<T> iterable, Function<T, U> condition)
	{
		var U currentValue = null;
		var ArrayList<T> currentList = null;
		val result = new ArrayList<Iterable<T>>
		for(T value : iterable)
		{
			val groupValue = condition.apply(value)
			if(groupValue.equals(currentValue))
			{
				currentList.add(value)
			}
			else 
			{
				currentList = new ArrayList<T>
				currentList.add(value)
				result.add(currentList)
				currentValue = groupValue
			}
		}
		return result
	}
	
	/**
	 * Filters an iterable so that it only contains values which are distinct by a given predicate. 
	 * If two values are not distinct, the first value found is returned 
	 * 
	 * For example 
	 * 	distinctBy(#["one", "two", "three", "four"], [str | str.size]
	 * returns
	 *  #["one", "three", "four"]
	 * since "two" is the same size as "one".
	 * 
	 * @param iterable the iterable <T>
	 * @param predicate a predicate to apply before matching the values
	 * @return an iterable distinct by the predicate   
	 */
	static def <T, U> Iterable<T> distinctBy(Iterable<T> iterable, (T)=>U predicate) {
		val knownSet = newHashSet
		return iterable.filter [
			return knownSet.add(predicate.apply(it))
		]
	}
	
		
	/**
	 * Filters an iterable so that it only contains values which are not distinct by a given predicate. 
	 * If two values are distinct, the second value found is returned 
	 * 
	 * For example 
	 * 	notDistinctBy(#["one", "two", "three", "four"], [str | str.size]
	 * returns
	 *  #["two"]
	 * since "two" is the same size as "one".
	 * 
	 * @param iterable the iterable <T>
	 * @param predicate a predicate to apply before matching the values
	 * @return an iterable not distinct by the predicate   
	 */
	static def <T, U> Iterable<T> notDistinctBy(Iterable<T> iterable, (T)=>U predicate) {
		val knownSet = newHashSet
		return iterable.filter [
			return !knownSet.add(predicate.apply(it))
		]
	}

	/**
	 * Maps an iterable with the iteration index as a second parameter to the mapping function 
	 * 
	 * @param iterable the iterable <T>
	 * @param funciton a function to apply to each element of the iterable
	 * @return a mapped iterable    
	 */
	static def <T, U> Iterable<U> mapIndex(Iterable<T> iterable,
		(T, int)=>U function) {
		// IMPROVE: This constructs a new list which is eagerly filled
		val result = newArrayList
		iterable.forEach[it, index|result.add(function.apply(it, index))]
		return result
	}	
}
