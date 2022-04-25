/*
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils

import org.eclipse.emf.ecore.EObject

/**
 * Extensions for {@link EObject}.
 * 
 * @author Stuecker
 */
class EObjectExtensions {
	/**
	 * Returns the closest parent of a specified class or null
	 * 
	 * @param object the object
	 * @param clazz the class
	 * @return the parent of type clazz (or null)
	 */
	static def <T> T getParentByType(EObject object, Class<T> clazz) {
		if (object === null)
			return null
		if (clazz.isInstance(object))
			return object as T
		else
			return object.eContainer.getParentByType(clazz)
	}
}
