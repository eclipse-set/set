/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Ansteuerung_Element.Unterbringung
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit;
import java.util.List
import org.eclipse.set.model.planpro.Bedienung.Bedien_Platz
import org.eclipse.set.model.planpro.Bedienung.Bedien_Bezirk
import org.eclipse.set.model.planpro.Ansteuerung_Element.Technik_Standort
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich

import static extension org.eclipse.set.ppmodel.extensions.StellBereichExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import org.eclipse.set.model.planpro.Geodaten.Oertlichkeit

/**
 * Extensions for {@link ESTW_Zentraleinheit}.
 */
class ESTW_ZentraleinheitExtensions extends BasisObjektExtensions {

	/**
	 * @param estw_zentraleinheit this ESTW_Zentraleinheit
	 * 
	 * @return the Unterbringung
	 */
	def static Unterbringung getUnterbringung(
		ESTW_Zentraleinheit estw_zentraleinheit) {
		return estw_zentraleinheit?.IDUnterbringung?.value as Unterbringung
	}

	/**
	 * @param estw_zentraleinheit this ESTW_Zentraleinheit
	 * 
	 * @return the List of Bedien_Plaetze
	 */
	def static List<Bedien_Platz> getBedienPlaetze(
		ESTW_Zentraleinheit estw_zentraleinheit) {

		return estw_zentraleinheit.container.bedienPlatz.filter [ b |
			b.IDESTWZentraleinheit?.wert == estw_zentraleinheit.identitaet.wert
		].toList;
	}

	/**
	 * @param estw_zentraleinheit this ESTW_Zentraleinheit
	 * 
	 * @return the Bedien_Bezirk as defined by ID_Bedien_Bezirk_Zentral
	 */
	def static Bedien_Bezirk getBedienBezirkZentral(
		ESTW_Zentraleinheit estw_zentraleinheit) {
		return estw_zentraleinheit.IDBedienBezirkZentral?.value
	}

	/**
	 * @param estw_zentraleinheit this ESTW_Zentraleinheit
	 * 
	 * @return the Bedien_Bezirk as defined by ID_Bedien_Bezirk_Virtuell
	 */
	def static Bedien_Bezirk getBedienBezirkVirtuell(
		ESTW_Zentraleinheit estw_zentraleinheit) {
		return estw_zentraleinheit.IDBedienBezirkVirtuell?.value
	}

	def static Oertlichkeit getOertlichkeitNamensgebend(
		ESTW_Zentraleinheit estw_zentraleinheit) {
		return estw_zentraleinheit?.IDOertlichkeitNamensgebend?.value
	}

	def static String getOertlichkeitAbkuerzung(
		ESTW_Zentraleinheit estw_zentraleinheit) {
		return estw_zentraleinheit.oertlichkeitNamensgebend?.bezeichnung?.
			oertlichkeitAbkuerzung?.wert
	}

	/**
	 * @param estw_zentraleinheit this ESTW_Zentraleinheit
	 * 
	 * @return the Bedien_Bezirk as defined by ID_Bedien_Bezirk_Virtuell
	 */
	def static List<Technik_Standort> getTechnikStandort(
		ESTW_Zentraleinheit estw_zentraleinheit) {
		return estw_zentraleinheit.container.technikStandort.filter [
			IDUnterbringung?.value ===
				estw_zentraleinheit.IDUnterbringung?.value
		].toList
	}

	def static boolean isBelongToControlArea(
		ESTW_Zentraleinheit estw_zentraleinheit, Stell_Bereich area) {
		val estwZentrals = area?.aussenElementAnsteuerung?.ESTWZentraleinheits
		return !estwZentrals.nullOrEmpty && estwZentrals.exists [
			it === estw_zentraleinheit
		];
	}

}
