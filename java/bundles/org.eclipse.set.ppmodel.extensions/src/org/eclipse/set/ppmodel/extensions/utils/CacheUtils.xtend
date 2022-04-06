/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*

/**
 * Utility functions for caching model objects
 */
class CacheUtils {

	/**
	 * Determines a caching key for an EObject
	 * 
	 * EObjects are identified by building a string according to the following rules:
	 * - References are resolved and mapped to their respective cache keys
	 * - Attribtues are converted to strings and added to the key
	 * 
	 * @param object the Basis_Objekt
	 * @return the cache key uniquely identifying the object
	 */
	static dispatch def String getCacheKey(EObject object) {
		return String.join("/", object.eClass.EAttributes.map [
			val attr = object.eGet(it)
			if (attr instanceof String)
				return attr
			return attr.toString
		] + object.eClass.EReferences.map [
			val value = object.eGet(it, true)
			if (value instanceof EObject)
				return value.cacheKey
			return ""
		])
	}

	/**
	 * Determines a caching key for an EList
	 * 
	 * @param list the list
	 * @return the cache key uniquely identifying the list contents
	 */
	static dispatch def String getCacheKey(EList<? extends EObject> list) {
		return list.map[cacheKey].sort.join("/")
	}

	/**
	 * Determines a caching key for an Ur Objekt.
	 * 
	 * An Ur Objekt can be uniquely identified by its container key and identity.
	 * 
	 * @param object the Ur Objekt
	 * 
	 * @return the cache key uniquely identifying the object
	 */
	static dispatch def String getCacheKey(Ur_Objekt object) {
		return '''«object.container.cacheString»/«object.identitaet.wert»'''
	}

	/**
	 * @param object this Ur Objekt
	 * @param other another object (with a sensible string representation)
	 * describing further dependencies of the cache value
	 * 
	 * @return the cache key (including the container's cacheString)
	 */
	def static String getCacheKey(Ur_Objekt object, Object other) {
		return '''«object.cacheKey»/other=«other»'''
	}
}
