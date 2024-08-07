/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.List
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Ansteuerung_Element.Technik_Standort
import org.eclipse.set.model.planpro.Ansteuerung_Element.Unterbringung
import org.eclipse.set.model.planpro.Bedienung.Bedien_Standort

import static extension org.eclipse.set.ppmodel.extensions.StellBereichExtensions.*

/**
 * Extensions for {@link Technik_Standort}.
 *  
 * @author Schaefer
 */
class TechnikStandortExtensions extends BasisObjektExtensions {

	/**
	 * @param standort this Technikstandort
	 * 
	 * @return the Bedienstandorte this Technikstandort is operated from
	 */
	static def List<Bedien_Standort> getBedienStandort(
		Technik_Standort standort
	) {
		return standort.IDBedienStandort?.map[value]?.filterNull?.toList
	}

	/**
	 * @param standort this Technikstandort
	 * 
	 * @return the Unterbringung this Technikstandort is installed in
	 */
	static def Unterbringung getUnterbringung(Technik_Standort standort) {
		return standort?.IDUnterbringung?.value
	}

	def static boolean isRelevantControlArea(Technik_Standort standort,
		Stell_Bereich controlArea) {
		return controlArea.technikStandorts.exists[it === standort]
	}
}
