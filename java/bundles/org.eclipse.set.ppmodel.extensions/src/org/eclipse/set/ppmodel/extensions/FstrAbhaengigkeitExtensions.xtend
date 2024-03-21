/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Bedienung.Bedien_Anzeige_Element
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Abhaengigkeit
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schluesselsperre
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Fahrweg

/**
 * This class extends {@link Fstr_Abhaengigkeit}.
 */
class FstrAbhaengigkeitExtensions extends BasisObjektExtensions {

	/**
	 * @param fstr this Fstr_Abhaengigkeit
	 * 
	 * @return Schlüsselsperre, die überwacht sein muss, damit die Fstr gesichert ist
	 */
	def static Schluesselsperre schluesselsperre(
		Fstr_Abhaengigkeit abhaengigkeit
	) {
		return abhaengigkeit?.fstrAbhaengigkeitSsp?.IDSchluesselsperre?.value
	}

	/**
	 * @param fstr this Fstr_Abhaengigkeit
	 * 
	 * @return the associated Fstr_Fahrweg
	 */
	def static Fstr_Fahrweg getFstrFahrweg(
		Fstr_Abhaengigkeit abhaengigkeit
	) {
		return abhaengigkeit?.IDFstrFahrweg?.value
	}

	/**
	 * @param fstr this Fstr_Abhaengigkeit
	 * 
	 * @return Bedienanzeigeelement, das wirksam sein muss, damit die Fstr gesichert ist
	 */
	def static Bedien_Anzeige_Element getBedienAnzeigeElement(
		Fstr_Abhaengigkeit abhaengigkeit
	) {
		return abhaengigkeit.IDBedienAnzeigeElement?.value
	}
}
