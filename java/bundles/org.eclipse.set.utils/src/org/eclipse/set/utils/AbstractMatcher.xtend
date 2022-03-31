/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils

import org.eclipse.set.basis.integration.DiffLabelProvider
import org.eclipse.set.basis.integration.Matcher
import java.util.Arrays
import java.util.List
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil

import static com.google.common.base.Strings.*

import static extension org.eclipse.set.utils.EClassExtensions.*

/**
 * Abstract implementation for {@link Matcher}.
 * 
 * @author Schaefer
 */
abstract class AbstractMatcher implements Matcher {

	val DiffLabelProvider labelProvider

	new(DiffLabelProvider labelProvider) {
		this.labelProvider = labelProvider
	}

	override isDifferent(EObject first, EObject second) {
		return !getDifferences(first, second).empty
	}

	override getNonEmptyAttributes(EObject first, EObject second) {
		val types = #{first, second}.filterNull.map[eClass].toSet
		if (types.size !== 1) {
			throw new IllegalArgumentException('''types=«types»''')
		}
		val type = types.head
		return type.getAttributePaths(false).filter [
			hasNonEmptyValues(first, second, it)
		].toList
	}

	override getDifferences(EObject first, EObject second) {
		val types = #{first, second}.filterNull.map[eClass].toSet
		if (types.size !== 1) {
			throw new IllegalArgumentException('''types=«types»''')
		}
		val type = types.head
		return type.getAttributePaths(false).filter [
			!hasEqualValues(first, second, it)
		].toList
	}

	private def boolean hasEqualValues(EObject first, EObject second,
		List<String> path) {
		val firstValue = labelProvider.getAttributeValue(first, path)
		val secondValue = labelProvider.getAttributeValue(second, path)
		if (firstValue === null || secondValue === null) {
			return firstValue === secondValue
		}
		val types = #{firstValue, secondValue}.map[class].toSet
		if (types.size > 1) {
			return false
		}
		val type = types.head
		if (typeof(List).isAssignableFrom(type)) {
			return equalLists(firstValue as List<?>, secondValue as List<?>)
		}
		if (type.array) {
			return equalArrays(firstValue, secondValue)
		}
		return firstValue.equals(secondValue)
	}

	private def boolean hasNonEmptyValues(EObject first, EObject second,
		List<String> path) {
		val firstValue = labelProvider.getAttributeValue(first, path)
		val secondValue = labelProvider.getAttributeValue(second, path)
		if (firstValue === null && secondValue === null) {
			return false
		}
		val aNonNullValue = #{firstValue, secondValue}.filterNull.head
		val types = #{firstValue, secondValue}.filterNull.map[class].toSet
		if (types.size > 1) {
			throw new IllegalArgumentException('''types=«types»''')
		}
		val type = types.head
		if (aNonNullValue instanceof List<?> || type.array) {
			return size(firstValue) > 0 || size(secondValue) > 0
		}
		if (aNonNullValue instanceof String) {
			return !isNullOrEmpty(firstValue as String) ||
				!isNullOrEmpty(firstValue as String)
		}
		return true
	}

	def int size(Object object) {
		if (object === null) {
			return 0
		}
		if (object instanceof List<?>) {
			return object.size
		}
		if (object instanceof byte[]) {
			return object.length
		}
		throw new IllegalArgumentException('''Unsupported array type «object.class»''')
	}

	private def boolean equalArrays(Object first, Object second) {
		if (first instanceof byte[]) {
			if (second instanceof byte[]) {
				return Arrays.equals(first, second)
			} else {
				return false
			}
		} else {
			throw new IllegalArgumentException('''Unsupported array type «first.class»''')
		}
	}

	private def boolean equalLists(List<?> firstValue, List<?> secondValue) {
		if (firstValue.size !== secondValue.size) {
			return false
		}
		val size = firstValue.size
		for (var int i = 0; i < size; i++) {
			val firstObject = firstValue.get(i)
			val secondObject = secondValue.get(i)

			if (firstObject instanceof EObject &&
				secondObject instanceof EObject) {
				// we compare EObject's structurally
				if (!EcoreUtil.equals(
					firstObject as EObject,
					secondObject as EObject
				)) {
					return false
				}
			} else {
				// and other objects per equal
				if (!firstObject.equals(secondObject)) {
					return false
				}
			}
		}
		return true
	}
}
