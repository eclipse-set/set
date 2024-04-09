/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg_W_Kr
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element

/**
 * Diese Klasse erweitert {@link Fstr_DWeg_W_Kr}.
 */
class FstrDWegWKrExtensions extends BasisObjektExtensions {

	/**
	 * @param z diese Zuordnung
	 * 
	 * @returns das stellbare Element
	 */
	def static W_Kr_Gsp_Element getWKrGspElement(Fstr_DWeg_W_Kr z) {
		return z.IDWKrGspElement?.value
	}
}
