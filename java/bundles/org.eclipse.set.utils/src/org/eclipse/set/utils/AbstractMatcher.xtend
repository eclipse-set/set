/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils

import java.util.Arrays
import java.util.List
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.set.basis.integration.DiffLabelProvider
import org.eclipse.set.basis.integration.Matcher
import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt

import static com.google.common.base.Strings.*

import static extension org.eclipse.set.utils.EClassExtensions.*
import static extension org.eclipse.set.utils.EObjectExtensions.*
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle
import org.eclipse.set.toolboxmodel.PlanPro.DocumentRoot
import org.eclipse.set.model.temporaryintegration.ToolboxTemporaryIntegration

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

		if (firstValue instanceof Ur_Objekt && secondValue === null) {
			return (firstValue as Ur_Objekt).identitaet?.wert ==
				getEObjectGuidFromPath(second, path)
		}
		if (secondValue instanceof Ur_Objekt && firstValue === null) {
			return (secondValue as Ur_Objekt).identitaet?.wert ==
				getEObjectGuidFromPath(first, path)
		}

		if (firstValue === null || secondValue === null) {
			return firstValue === secondValue
		}
		val types = #{firstValue, secondValue}.map[class].toSet
		if (types.size > 1) {
			return false
		}
		val type = types.head
		if (typeof(List).isAssignableFrom(type)) {
			val result = equal(firstValue as List<?>, secondValue as List<?>)
			return result
		}
		if (type.array) {
			return equalArrays(firstValue, secondValue)
		}

		if (firstValue instanceof Ur_Objekt &&
			secondValue instanceof Ur_Objekt) {
			return (firstValue as Ur_Objekt).identitaet?.wert ==
				(secondValue as Ur_Objekt).identitaet?.wert
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

	private dispatch def boolean equal(List<?> firstValue,
		List<?> secondValue) {
		return equalList(firstValue, secondValue, null, null, null)
	}

	private def boolean equalList(List<?> firstValue, List<?> secondValue,
		EObject firstContainer, EObject secondContainer, EReference reference) {
		// Compare sizes
		if (firstValue.size !== secondValue.size) {
			return false
		}

		// Compare elements
		val size = firstValue.size
		for (var int i = 0; i < size; i++) {
			val firstObject = firstValue.get(i)
			val secondObject = secondValue.get(i)

			if (reference !== null && !reference.isContainment &&
				(firstObject === null || firstObject instanceof Ur_Objekt) &&
				(secondObject === null || secondObject instanceof Ur_Objekt)) {
				// Compare guids
				if (firstObject.getEObjectGuid(firstContainer, reference) !=
					secondObject.getEObjectGuid(secondContainer, reference)) {
					return false
				}
			} else if (firstObject instanceof EObject &&
				secondObject instanceof EObject) {
				// we compare EObject's structurally
				if (!equal(
					firstObject as EObject,
					secondObject as EObject
				)) {
					return false
				}
			} else {
				// If either value is null, it may be an invalid ID reference
				// and other objects per equal
				if (!firstObject.equals(secondObject)) {
					return false
				}
			}
		}
		return true
	}

	private dispatch def boolean equal(EObject firstObject,
		EObject secondObject) {
		// Reference-equality?
		if (firstObject === secondObject)
			return true
		// If the objects are not of the same class, they cannot be equal
		if (firstObject.eClass !== secondObject.eClass)
			return false

		// Compare features
		for (EStructuralFeature feature : firstObject.eClass.
			EStructuralFeatures.filter[!isDerived]) {
			if (firstObject.eIsSet(feature) != secondObject.eIsSet(feature))
				return false
			if (feature instanceof EReference) {
				if (!equalReference(firstObject, secondObject, feature)) {
					return false
				}
			} else if (feature instanceof EAttribute) {
				if (!equalAttribute(firstObject, secondObject, feature)) {
					return false
				}
			}
		}

		// No features
		return true
	}

	private def boolean equalReference(EObject firstObject,
		EObject secondObject, EReference reference) {
		val firstValue = firstObject.eGet(reference)
		val secondValue = secondObject.eGet(reference)

		// If this is a non-containment reference of Ur_Objekt, it is an ID reference
		// hence only compare the target guids
		if (!reference.isContainment &&
			(firstObject === null || firstValue instanceof Ur_Objekt) &&
			(secondObject === null || secondValue instanceof Ur_Objekt)) {
			// Compare guids
			return firstValue.getEObjectGuid(firstObject, reference) ==
				secondValue.getEObjectGuid(secondObject, reference)

		} else if (reference.isMany && firstValue !== null &&
			secondValue !== null) {
			return equalList(firstValue as EList<?>, secondValue as EList<?>,
				firstObject, secondObject, reference)
		}

		// Avoid recursing with null
		if (firstValue === null) {
			return secondValue === null
		} else if (secondValue === null) {
			return firstValue === null
		}
		return equal(firstValue, secondValue)
	}

	private def String getEObjectGuid(Object value, EObject container,
		EReference reference) {
		// If the Ur_Objekt is present use it directly
		if (value instanceof Ur_Objekt)
			return value.identitaet?.wert

		//
		if (value !== null)
			return null

		// Check unresolved ID References
		val schnittstelle = container.getParentByType(PlanPro_Schnittstelle)
		val parent = schnittstelle.eContainer
		if (parent instanceof ToolboxTemporaryIntegration) {
			val idref = parent.getIDReferences(schnittstelle).filter [
				target === container && targetRef === reference
			].head

			if (idref !== null) {
				return idref.guid

			}
		}
		return null;
	}

	private def getIDReferences(ToolboxTemporaryIntegration tmpInt,
		PlanPro_Schnittstelle schnittstelle) {
		if (tmpInt.primaryPlanning === schnittstelle)
			return tmpInt.primaryPlanningIDReferences
		if (tmpInt.secondaryPlanning === schnittstelle)
			return tmpInt.secondaryPlanningIDReferences
		if (tmpInt.compositePlanning === schnittstelle)
			return tmpInt.compositePlanningIDReferences
		return null
	}

	private def String getEObjectGuidFromPath(EObject container,
		List<String> path) {
		val feature = getFeatureFromPath(container, path)
		return getEObjectGuid(null, container, feature as EReference)
	}

	private def EStructuralFeature getFeatureFromPath(EObject container,
		Iterable<String> path) {
		val tail = path.tail
		val feature = container.eClass.getEStructuralFeature(path.head)
		if (tail.empty) {
			return feature
		}
		val value = container.eGet(feature)
		return getFeatureFromPath((value as EObject), tail)
	}

	private def boolean equalAttribute(EObject firstObject,
		EObject secondObject, EAttribute attribute) {
		return firstObject.eGet(attribute) == secondObject.eGet(attribute)
	}

}
