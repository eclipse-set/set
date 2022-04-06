/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.BasisTypen.Zeiger_TypeClass
import org.eclipse.set.toolboxmodel.Basisobjekte.Ur_Objekt

/**
 * Extensions for {@link Zeiger_TypeClass}.
 */
class ZeigerExtensions extends BasisObjektExtensions {	
	/**
	 * // TODO: Remove this
	 * 
	 * @param pointer the Zeiger_TypeClass
	 * @param type the type to resolve as
	 * 
	 * @returns the object pointed to by the pointer
	 */
	@Deprecated
	static def <T extends Ur_Objekt> T resolve(Ur_Objekt pointer,
		Class<T> type) {
		if (pointer === null)
			return null
		return pointer as T
	}
	
}
