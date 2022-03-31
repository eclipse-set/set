/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Punkt_Objekt
import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Markanter_Punkt
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

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
		return markanterPunkt.IDMarkanteStelle.resolve(Punkt_Objekt)
	}
}
