/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Anlage
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schloss
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schlosskombination
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schluessel
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schluesselsperre
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich

import static extension org.eclipse.set.ppmodel.extensions.SchluesselExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SchlosskombinationExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StellBereichExtensions.*

/**
 * Extensions for {@link Schloss}.
 */
class SchlossExtensions extends BasisObjektExtensions {

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the Schlosskombination
	 */
	def static Schlosskombination getSchlossKombination(Schloss schloss) {
		return schloss.schlossSk.IDSchlosskombination?.value
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the Schluesselsperre
	 */
	def static Schluesselsperre getSchluesselsperre(Schloss schloss) {
		return schloss?.schlossSsp?.IDSchluesselsperre?.value
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the Schluessel
	 */
	def static Schluessel getSchluesel(Schloss schloss) {
		return schloss.IDSchluessel?.value
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the BUE_Anlage
	 */
	def static BUE_Anlage getBueAnlage(Schloss schloss) {
		return schloss.schlossBUE.IDBUEAnlage?.value
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the W_Kr_Gsp_Element (W)
	 */
	def static W_Kr_Gsp_Element getGspElement(Schloss schloss) {
		return schloss.schlossGsp.IDGspElement?.value
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the W_Kr_Gsp_Element (W-Kr)
	 */
	def static W_Kr_Gsp_Element getWKrElement(Schloss schloss) {
		return schloss?.schlossW?.IDWKrElement?.value
	}

	/**
	 * @param schloss this schloss
	 * 
	 * @returns the Sonderanlage
	 */
	def static W_Kr_Gsp_Element getSonderanlage(Schloss schloss) {
		return schloss?.schlossSonderanlage?.IDSonderanlage?.value
	}
	
	def static Iterable<Schloss> filterObjectsIsBelongToControlArea(Iterable<Schloss> schlosses, Stell_Bereich controlArea) {
		if (controlArea === null || schlosses.nullOrEmpty) {
			return schlosses;
		}
		
		val result = newHashSet
		val container = schlosses?.head?.container
		// 1. Condition
		// IMPROVE: Not completely, because the requirements for this case aren't clear
		val stellelements = container.stellelement.map[IDInformation?.value].
			filterNull.filter[AussenelementansteuerungExtensions.isBelongToControlArea(it, controlArea)]
		val ssp = container.schluesselsperre.filter [ ssp |
			stellelements.exists[it === ssp.IDStellelement.value]
		]
		val schluessels = schlosses.filter [ schloss |
			ssp.exists[it === schloss.schlossSsp.IDSchluesselsperre.value]
		].map[schluesel].filterNull
		result.addAll(schluessels.flatMap[schloesser])

		// 2.Condition
		result.filter[schlossSk?.hauptschloss.wert].flatMap [ schloss |
			schloss.schlossSk.IDSchlosskombination?.value.schloesser.filter [
				it.schlossSk !== null && !it.schlossSk.hauptschloss.wert
			]
		].filterNull.map[schluesel].flatMap[schloesser].forEach[result.add(it)]

		// 3. Condition
		schlosses.filter [ schloss |
			controlArea.wkrGspElement.exists [ gspElement |
				schloss.schlossW?.IDWKrElement?.value === gspElement ||
					schloss.schlossGsp?.IDGspElement?.value === gspElement ||
					schloss.schlossSonderanlage?.IDSonderanlage?.value ==
						gspElement
			]
		].map[schluesel].flatMap[schloesser].toSet.filter [
			technischBerechtigter?.wert
		].forEach[result.add(it)]
		return result
	}
}
