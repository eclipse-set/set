/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Nichthaltfall
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage

/**
 * Diese Klasse erweitert {@link Fstr_Nichthaltfall}.
 */
class FstrNichthaltfallExtensions extends BasisObjektExtensions {

	/**
	 * @param fstrNichthaltfall this Fstr_Nichthaltfall
	 * 
	 * @returns the FMA Anlage
	 */
	def static FMA_Anlage getFmaAnlage(
		Fstr_Nichthaltfall fstrNichthaltfall
	) {
		return fstrNichthaltfall.IDFMAAnlage?.value
	}
}
