/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg_Spezifisch_AttributeGroup
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage

/**
 * Diese Klasse erweitert {@link Fstr_DWeg_Spezifisch_AttributeGroup}.
 */
class FstrDWegSpezifischExtensions extends BasisObjektExtensions {

	/**
	 * @param dwegSp this DWeg-Spezifisch
	 * 
	 * @returns the FMA Anlage Zielgleis
	 */
	def static FMA_Anlage fmaAnlageZielgleis(
		Fstr_DWeg_Spezifisch_AttributeGroup dwegSp
	) {
		return dwegSp.IDFMAAnlageZielgleis?.value
	}
}
