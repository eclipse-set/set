/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.LinkedList
import java.util.List
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Aneinander
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Aneinander_Zuordnung
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.model.planpro.Signale.Signal

import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StellBereichExtensions.*

/**
 * This class extends {@link Fstr_Aneinander}.
 */
class FstrAneinanderExtensions extends BasisObjektExtensions {

	/**
	 * @param fstrAneinanderZuordnung this Fstr_Aneinander_Zuordnung
	 * 
	 * @returns the Fstr_Aneinander
	 */
	def static Fstr_Aneinander getFstrAneinander(
		Fstr_Aneinander_Zuordnung fstrAneinanderZuordnung
	) {
		return fstrAneinanderZuordnung.IDFstrAneinander?.value
	}

	/**
	 * @param fstrAneinanderZuordnung this Fstr_Aneinander_Zuordnung
	 * 
	 * @returns the Fstr_Zug_Rangier
	 */
	def static Fstr_Zug_Rangier getFstrZugRangier(
		Fstr_Aneinander_Zuordnung fstrAneinanderZuordnung
	) {
		return fstrAneinanderZuordnung.IDFstrZugRangier?.value
	}

	/**
	 * @param fstrAneinander this Fstr_Aneinander
	 * 
	 * @returns the list of Fstr_Aneinander_Zuordnung
	 */
	def static List<Fstr_Aneinander_Zuordnung> getZuordnungen(
		Fstr_Aneinander fstrAneinander) {
		val result = new LinkedList<Fstr_Aneinander_Zuordnung>
		for (zuordnung : fstrAneinander.container.fstrAneinanderZuordnung) {
			if (zuordnung.IDFstrAneinander?.wert ==
				fstrAneinander.identitaet.wert) {
				result.add(zuordnung)
			}
		}
		return result
	}

	/**
	 * @param fstrAneinander this Fstr_Aneinander
	 * 
	 * @returns the Startsignal of the first Fahrstraße of this Fstr Aneinander
	 */
	def static Signal getStartSignal(Fstr_Aneinander fstrAneinander) {
		return fstrAneinander?.zuordnungen?.fstrStartZuordnung?.
			getFstrZugRangier?.fstrFahrweg?.start
	}

	/**
	 * @param fstrAneinander this Fstr_Aneinander
	 * 
	 * @returns the Zielsignal of the last Fahrstraße of this Fstr Aneinander
	 */
	def static Signal getZielSignal(Fstr_Aneinander fstrAneinander) {
		return fstrAneinander?.fstrZielZuordnung?.getFstrZugRangier?.
			fstrFahrweg?.zielSignal
	}

	/**
	 * @param fstrAneinander this Fstr_Aneinander
	 * 
	 * @returns the Zieldurchrutschweg of the last Fahrstraße of this Fstr Aneinander
	 */
	def static Fstr_DWeg getZielDweg(Fstr_Aneinander fstrAneinander) {
		return fstrAneinander?.fstrZielZuordnung?.getFstrZugRangier?.fstrDWeg
	}

	/**
	 * @param fstrAneinander this Fstr_Aneinander
	 * 
	 * @returns the Zielzuordnung of this Fstr Aneinander
	 */
	static def Fstr_Aneinander_Zuordnung getFstrZielZuordnung(
		Fstr_Aneinander fstrAneinander) {
		val List<Fstr_Aneinander_Zuordnung> zuordnungen = fstrAneinander.
			zuordnungen
		for (zuordnung : zuordnungen) {
			if (!zuordnungen?.map [
				fstrZugRangier?.fstrFahrweg?.IDStart?.value?.identitaet?.wert
			].contains(
				zuordnung?.fstrZugRangier?.fstrFahrweg?.IDZiel?.value?.
					identitaet?.wert)) {
				return zuordnung
			}
		}
		return null;
	}

	static def String getTableDescription(Fstr_Aneinander fstrAneinander) {
		val zielDweg = fstrAneinander.zielDweg
		return '''«fstrAneinander.startSignal?.bezeichnung?.bezeichnungTabelle?.wert ?: ""»/« //
		fstrAneinander.zielSignal?.bezeichnung?.bezeichnungTabelle?.wert ?: ""»«IF zielDweg !== null» (« //
			fstrAneinander.zielDweg?.bezeichnung?.bezeichnungFstrDWeg?.wert ?: ""»)«ENDIF»'''
	}

	static def Fstr_Aneinander_Zuordnung getFstrStartZuordnung(
		List<Fstr_Aneinander_Zuordnung> zuordnungen) {
		for (zuordnung : zuordnungen) {
			if (!zuordnungen.map [
				fstrZugRangier?.fstrFahrweg?.IDZiel?.value?.identitaet?.wert
			].contains(
				zuordnung?.fstrZugRangier?.fstrFahrweg?.IDStart?.value?.
					identitaet?.wert)) {
				return zuordnung
			}
		}
		return null;
	}

	def static boolean isBelongToControlArea(Fstr_Aneinander fstrAneinander,
		Stell_Bereich controlArea) {
		val areaStellelements = controlArea.aussenElementAnsteuerung.
			stellelements.toList

		val fstrFahrwegs = fstrAneinander.zuordnungen.map[fstrZugRangier].map[fstrFahrweg].map[
			IDStart.value -> IDZiel.value
		].toList
		
		if (fstrFahrwegs.isNullOrEmpty || areaStellelements.isNullOrEmpty) {
			return false
		}
		val startSignal = fstrFahrwegs.reduce[p1, p2| p1.key !== p2.value ? p1 : p2]?.key
		return startSignal !== null && areaStellelements.contains(startSignal.stellelement)
	}
}
