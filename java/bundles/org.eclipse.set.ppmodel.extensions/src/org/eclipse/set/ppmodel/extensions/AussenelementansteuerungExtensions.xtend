/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Aussenelementansteuerung
import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Unterbringung
import org.eclipse.set.toolboxmodel.Basisobjekte.Basis_Objekt
import org.eclipse.set.toolboxmodel.Geodaten.Oertlichkeit
import java.util.List
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

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
		return aussenelementansteuerung.IDUnterbringung.resolve(Unterbringung)
	}

	/**
	 * @param aussenelementansteuerung this Aussenelementansteuerung
	 * 
	 * @return the Aussenelementansteuerung information sekundaer
	 */
	def static List<Aussenelementansteuerung> getAussenelementansteuerungInformationSekundaer(
		Aussenelementansteuerung aussenelementansteuerung
	) {
		return aussenelementansteuerung.IDInformationSekundaer.map [
			resolve(Aussenelementansteuerung)
		]
	}

	/**
	 * @param aussenelementansteuerung this Aussenelementansteuerung
	 * 
	 * @return the Aussenelementansteuerung information primaer
	 */
	def static Basis_Objekt getAussenelementansteuerungEnergiePrimaer(
		Aussenelementansteuerung aussenelementansteuerung) {
		return aussenelementansteuerung.AEAEnergieversorgung.IDEnergiePrimaer.
			resolve(Basis_Objekt)
	}

	/**
	 * @param aussenelementansteuerung this Aussenelementansteuerung
	 * 
	 * @return the Aussenelementansteuerung information sekundaer
	 */
	def static Aussenelementansteuerung getAussenelementansteuerungEnergieSekundaer(
		Aussenelementansteuerung aussenelementansteuerung) {
		return aussenelementansteuerung.AEAEnergieversorgung.IDEnergieSekundaer.
			resolve(Aussenelementansteuerung)
	}

	/**
	 * @param aussenelementansteuerung this Aussenelementansteuerung
	 * 
	 * @return the Oertlichkeit
	 */
	def static Oertlichkeit getOertlichkeitNamensgebend(
		Aussenelementansteuerung aussenelementansteuerung) {
		return aussenelementansteuerung.IDOertlichkeitNamensgebend.resolve(
			Oertlichkeit)
	}
}
