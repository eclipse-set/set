/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.BasisTypen.Zeiger_TypeClass
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Ur_Objekt

import static extension org.eclipse.set.ppmodel.extensions.MultiContainer_AttributeGroupExtensions.*

/**
 * Extensions for {@link Zeiger_TypeClass}.
 */
class ZeigerExtensions extends BasisObjektExtensions {

	/**
	 * @param pointer the Zeiger_TypeClass
	 * @param type the type to resolve as
	 * 
	 * @returns the object pointed to by the pointer
	 */
	static def <T extends Ur_Objekt> T resolve(Zeiger_TypeClass pointer,
		Class<T> type) {
		if (pointer === null)
			return null
		return pointer.container.getObject(type, pointer)
	}
}
