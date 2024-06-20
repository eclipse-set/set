/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.List
import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit
import org.eclipse.set.model.planpro.Ansteuerung_Element.Unterbringung
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Geodaten.Oertlichkeit
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stellelement

/**
 * Extensions for {@link Aussenelementansteuerung}.
 */
class AussenelementansteuerungExtensions extends BasisObjektExtensions {

	/**
	 * @param aussenelementansteuerung this Aussenelementansteuerung
	 * 
	 * @return the Unterbringung
	 */
	def static Unterbringung getUnterbringung(
		Aussenelementansteuerung aussenelementansteuerung) {
		return aussenelementansteuerung.IDUnterbringung?.value
	}

	/**
	 * @param aussenelementansteuerung this Aussenelementansteuerung
	 * 
	 * @return the Aussenelementansteuerung information sekundaer
	 */
	def static List<Aussenelementansteuerung> getAussenelementansteuerungInformationSekundaer(
		Aussenelementansteuerung aussenelementansteuerung
	) {
		return aussenelementansteuerung.IDInformationSekundaer?.map[value]?.
			filterNull?.toList
	}

	/**
	 * @param aussenelementansteuerung this Aussenelementansteuerung
	 * 
	 * @return the Aussenelementansteuerung information primaer
	 */
	def static Basis_Objekt getAussenelementansteuerungEnergiePrimaer(
		Aussenelementansteuerung aussenelementansteuerung) {
		return aussenelementansteuerung.AEAEnergieversorgung.IDEnergiePrimaer?.
			value
	}

	/**
	 * @param aussenelementansteuerung this Aussenelementansteuerung
	 * 
	 * @return the Oertlichkeit
	 */
	def static Oertlichkeit getOertlichkeitNamensgebend(
		Aussenelementansteuerung aussenelementansteuerung) {
		return aussenelementansteuerung.IDOertlichkeitNamensgebend?.value
	}

	def static dispatch String getElementBezeichnung(Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	def static dispatch String getElementBezeichnung(
		Aussenelementansteuerung element) {
		return element?.bezeichnung?.bezeichnungAEA?.wert ?: "";
	}

	def static dispatch String getElementBezeichnung(
		ESTW_Zentraleinheit element) {
		return element?.bezeichnung?.bezeichnungESTWZE?.wert ?: "";
	}

	def static Iterable<Stellelement> getStellelements(
		Aussenelementansteuerung aussenelementansteuerung) {
		return aussenelementansteuerung.container.stellelement.filter [
			IDInformation.value === aussenelementansteuerung ||
				IDEnergie.value === aussenelementansteuerung
		]
	}
}
