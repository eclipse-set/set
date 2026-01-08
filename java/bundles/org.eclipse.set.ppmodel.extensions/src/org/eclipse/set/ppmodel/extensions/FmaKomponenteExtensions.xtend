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
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Fahrstrasse.Markanter_Punkt
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente
import org.eclipse.set.model.planpro.Ortung.Schaltmittel_Zuordnung

import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.MarkanterPunktExtensions.*

/**
 * This class extends {@link FMA_Komponente}.
 * 
 * @author Schaefer
 */
class FmaKomponenteExtensions extends BasisObjektExtensions {

	/**
	 * @param komp the FMA Komponente
	 * @param anlage the FMA Anlage
	 * 
	 * @return whether the FMA Komponente belongs to the given FMA Anlage
	 */
	def static boolean belongsTo(FMA_Komponente komp, FMA_Anlage anlage) {
		return !komp?.IDFMAgrenze?.filter [
			it?.value?.identitaet?.wert == anlage?.identitaet?.wert
		].nullOrEmpty
	}

	/**
	 * @param komp the FMA Komponente
	 * 
	 * @return all FMA-Anlagen the FMA-Komponente is a FMA-Grenze for
	 */
	def static List<FMA_Anlage> getAngrenzendeFMA(
		FMA_Komponente komp
	) {
		return komp.IDFMAgrenze.map[value].filterNull.toList
	}
	
	def static dispatch Punkt_Objekt getBezugsPunkt(FMA_Komponente fma) {
		return fma?.IDBezugspunkt?.value?.bezugsPunkt
	}
	
	def static dispatch Punkt_Objekt getBezugsPunkt(Basis_Objekt object) {
		return null
	}
	
	def static dispatch Punkt_Objekt getBezugsPunkt(Punkt_Objekt po) {
		return po
	}
	
	def static dispatch Punkt_Objekt getBezugsPunkt(Markanter_Punkt markanter) {
		return markanter.markanteStelle
	}
	
	def static Schaltmittel_Zuordnung getSchaltmittelZuordnung(
		FMA_Komponente komp) {
		for (Schaltmittel_Zuordnung zuord : komp.container.
			schaltmittelZuordnung) {
			if (zuord?.IDSchalter?.value?.identitaet?.wert !== null &&
				zuord?.IDSchalter?.value?.identitaet?.wert.equals(
					komp.identitaet.wert)) {
				return zuord;
			}
		}
		return null;
	}

	def static boolean isBelongToControlArea(FMA_Komponente fma,
		Stell_Bereich area) {
		val aussenElementAnsteuerungen = fma?.FMAKomponenteAchszaehlpunkt?.
			IDInformation?.map[value]

		return !aussenElementAnsteuerungen.nullOrEmpty &&
			aussenElementAnsteuerungen.filterNull.exists [
				isBelongToControlArea(area)
			]
	}
}
