/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Ur_Objekt
import org.eclipse.set.basis.MissingSupplier

/**
 * Object loader for a cache.
 * 
 * @author Schaefer
 */
class UrObjectLoader<T extends Ur_Objekt> implements MissingSupplier<Object> {

	val Iterable<T> objects
	val String guid

	new(Iterable<T> objects, String guid) {
		this.objects = objects
		this.guid = guid
	}

	override get() {
		val result = objects.findFirst[identitaet?.wert == guid]
		if (result !== null) {
			return result
		}
		return MISSING_VALUE
	}
}
