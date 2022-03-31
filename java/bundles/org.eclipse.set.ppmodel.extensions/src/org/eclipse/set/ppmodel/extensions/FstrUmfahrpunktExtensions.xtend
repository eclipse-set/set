/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Basis_Objekt
import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Fstr_Umfahrpunkt
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*
/**
 * This class extends {@link Fstr_Umfahrpunkt}.
 * 
 * @author Schaefer
 */
class FstrUmfahrpunktExtensions extends BasisObjektExtensions {

	/**
	 * @param umfahrpunkt this Umfahrpunkt
	 * 
	 * @return the element this Umfahrpunkt is derived from 
	 */
	static def Basis_Objekt getUmfahrpunkt(Fstr_Umfahrpunkt umfahrpunkt) {
		return umfahrpunkt.IDUmfahrpunkt.resolve(Basis_Objekt)
	}
}
