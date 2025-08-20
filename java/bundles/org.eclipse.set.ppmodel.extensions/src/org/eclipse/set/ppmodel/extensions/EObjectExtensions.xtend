/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.List
import java.util.Optional
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.EcoreUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Extensions for {@link EObject}.
 * 
 * @author Stuecker
 */
class EObjectExtensions {
	static Logger logger = LoggerFactory.getLogger(EObjectExtensions)

	/**
	 * Fills an EObject with default values if not present
	 * 
	 * For required references (lowerBound == upperBound == 1) the reference is created
	 * For unsettable attributes, expliticly set them to null
	 * 
	 * Recurses into subobjects 
	 * 
	 * @param EObject the EObject
	 */
	static def void fillDefaults(EObject object) {
		// For all required but not present references, create the referenced type
		object.eClass.EReferences.filter[lowerBound == 1 && upperBound == 1].
			filter [
				!object.eIsSet(it)
			].forEach [
				try {
					val value = EcoreUtil.create(it.EReferenceType)
					value.fillDefaults
					object.eSet(it, value)
				} catch (Exception e) {
					logger.error(e.message)
				}

			]

		// For all unsettable attributes, prefer unset to null values 
		object.eClass.EAttributes.filter[isUnsettable].filter [
			!object.eIsSet(it)
		].forEach [
			object.eSet(it, null)
		]

		// Recurse into contained objects
		object.eContents.forEach[fillDefaults]
	}

	/**
	 * Checks whether an EObject does not contain some required values 
	 * 
	 * Recurses into subobjects 
	 * 
	 * @param EObject the EObject
	 */
	static def boolean containsUnfilledValues(EObject object) {
		// Does the object contain an unset reference?
		if (!object.eClass.EReferences.filter [
			lowerBound == 1 && upperBound == 1
		].filter[!object.eIsSet(it)].empty)
			return true

		// Does the object contain an unset attribute? 
		if (!object.eClass.EAttributes.filter[isUnsettable].filter [
			!object.eIsSet(it)
		].empty)
			return true

		// Recurse into contained objects
		return object.eContents.map[containsUnfilledValues].fold(false, [ a, b |
			a || b
		])
	}

	/**
	 * Returns all missing required values in an EObject
	 * 
	 * Recurses into subobjects, but does not fill ELists
	 * 
	 * @param EObject the EObject
	 */
	static def Iterable<Pair<EStructuralFeature, EObject>> getUnfilledValues(
		EObject object) {
		// Does the object contain an unset reference?
		val unsetReferences = object.eClass.EReferences.filter [
			lowerBound == 1 && !isMany
		].filter[!object.eIsSet(it)]

		// Does the object contain an unset attribute? 
		val unsetAttributes = object.eClass.EAttributes.filter[isUnsettable].
			filter [
				!object.eIsSet(it)
			]

		// Recurse into contained objects
		return (unsetReferences + unsetAttributes).map[it -> object] +
			object.eContents.flatMap[getUnfilledValues]
	}

	/**
	 * Serializes all attributes of an EObject into a CSV string
	 */
	static def String toCSV(EObject object) {
		return String.join(";", object.eClass.EAttributes.map [
			val attr = object.eGet(it)
			if (attr === null)
				return "";
			if (attr instanceof String)
				return attr.replace("\"", "\"\"")
			return attr.toString
		]) + System.lineSeparator
	}
	
	static def <T, U> Optional<U> getNullableObject(T t, (T) => U func) {
		try {
			return Optional.ofNullable(func.apply(t))
		} catch(NullPointerException e) {
			logger.debug(e.message)
			return Optional.empty
		}
	}
	
	static def <T> Optional<T> getIndexOutBoundableObject(List<T> t, int index) {
		try {
			if (t.isNullOrEmpty) {
				return Optional.empty
			}
			return Optional.ofNullable(t.get(index))
		} catch (IndexOutOfBoundsException e) {
			logger.debug(e.message)
			return Optional.empty
		}
		
	}
}
