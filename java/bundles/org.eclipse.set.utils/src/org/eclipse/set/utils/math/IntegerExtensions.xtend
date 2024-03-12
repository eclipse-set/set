/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.math

import java.util.List

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

	def static List<int[]> findSumCombination(int targetSum, int arraySize) {
		var result = newArrayList
		var combination = newArrayList
		for (var i = 0; i < arraySize; i++) {
			combination.add(0)
		}
		result.generateCombinations(targetSum, arraySize, combination, 0, 1)
		return result
	}

	private def static void generateCombinations(List<int[]> result,
		int targetSum, int arraySize, List<Integer> currentCombi,
		int currentIndex, int startDigi) {
		val sum = currentCombi.reduce[p1, p2|p1 + p2]
		if (currentIndex === arraySize || sum === targetSum) {
			if (sum === targetSum && !result.exists [
				currentCombi.containsAll(it)
			]) {
				val clone = newArrayList
				clone.addAll(currentCombi)
				result.add(clone)
			}
			return
		}

		for (var i = startDigi; i <= targetSum - arraySize + 1; i++) {
			currentCombi.set(currentIndex, i)
			result.generateCombinations(targetSum, arraySize, currentCombi,
				currentIndex + 1, startDigi)

		}
	}
}
