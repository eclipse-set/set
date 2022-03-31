/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Fstr_DWeg_Spezifisch_AttributeGroup
import de.scheidtbachmann.planpro.model.model1902.Ortung.FMA_Anlage
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

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
		return dwegSp.IDFMAAnlageZielgleis.resolve(FMA_Anlage)
	}
}
