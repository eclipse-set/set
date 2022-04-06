/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.util.EcoreUtil

/**
 * Extensions for {@link EObject}.
 * 
 * @author Stuecker
 */
class EObjectExtensions {
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
				val value = EcoreUtil.create(it.EReferenceType)
				value.fillDefaults
				object.eSet(it, value)
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
	 * Serializes all attributes of an EObject into a CSV string
	 */
	static def String toCSV(EObject object) {
		return String.join(";", object.eClass.EAttributes.map[
			val attr = object.eGet(it)
			if(attr instanceof String)
				return attr.replace("\"", "\"\"")
			return attr.toString
		]) + System.lineSeparator
	}
}
