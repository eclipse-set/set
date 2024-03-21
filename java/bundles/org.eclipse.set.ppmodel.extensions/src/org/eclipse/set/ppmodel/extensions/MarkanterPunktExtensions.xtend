/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Fahrstrasse.Markanter_Punkt

/**
 * Extensions for {@link Markanter_Punkt}.
 */
class MarkanterPunktExtensions extends BasisObjektExtensions {

	/**
	 * @param markanterPunkt this markanter Punkt
	 * 
	 * @return the markante Stelle of this markanter Punkt
	 */
	def static Punkt_Objekt getMarkanteStelle(Markanter_Punkt markanterPunkt) {
		return markanterPunkt?.IDMarkanteStelle?.value as Punkt_Objekt
	}
}
