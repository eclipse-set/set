/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Fstr_DWeg_W_Kr
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

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
		return z.IDWKrGspElement.resolve(W_Kr_Gsp_Element)
	}
}
