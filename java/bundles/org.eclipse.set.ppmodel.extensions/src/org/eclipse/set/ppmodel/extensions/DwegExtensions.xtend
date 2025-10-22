/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.List
import java.util.Set
import org.eclipse.set.basis.graph.TopPath
import org.eclipse.set.basis.graph.TopPoint
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg_W_Kr
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Fahrweg
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage

import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*

/**
 * Extensions for {@link Fstr_DWeg}.
 */
class DwegExtensions extends BasisObjektExtensions {

	/**
	 * @param dweg this Durchrutschweg
	 * 
	 * @returns the Zug-/Rangierstraßen for this Durchrutschweg
	 */
	def static List<Fstr_Zug_Rangier> fstrZugRangier(Fstr_DWeg dweg) {
		return dweg?.container?.fstrZugRangier?.filter [
			fstrZug?.fstrZugDWeg?.IDFstrDWeg?.value === dweg
		]?.toList
	}

	/**
	 * @param dweg this Durchrutschweg
	 * 
	 * @returns the Fahrweg
	 */
	def static Fstr_Fahrweg getFstrFahrweg(
		Fstr_DWeg dweg
	) {
		return dweg.IDFstrFahrweg?.value
	}

	/**
	 * @param dweg this Durchrutschweg
	 * 
	 * @returns the FMA Anlage Freimeldung set of this Durchrutschweg
	 */
	def static Set<FMA_Anlage> getFmaAnlageFreimeldung(Fstr_DWeg dweg) {
		return dweg?.IDFMAAnlageFreimeldung?.map[value].filterNull.toSet
	}

	/**
	 * @param dweg this Durchrutschweg
	 * 
	 * @returns the name of the Startsignal plus the name of the Durchrutschweg
	 */
	def static String getFullName(Fstr_DWeg dweg) {
		val nameStartsignal = dweg.fstrFahrweg.start.bezeichnung.
			bezeichnungTabelle.wert
		val nameDweg = dweg.bezeichnung.bezeichnungFstrDWeg.wert
		return '''«nameStartsignal» «nameDweg»'''
	}

	/**
	 * @param dweg this Durchrutschweg
	 * 
	 * @returns the Weichen/Kreuzungen-Zuordnungen
	 */
	def static List<Fstr_DWeg_W_Kr> zuordnungen(Fstr_DWeg dweg) {
		return dweg?.container?.fstrDWegWKr?.
			filter[IDFstrDWeg?.value === dweg]?.toList
	}

	// Check if the FMA on the Fstr_Fahrweg of the Dweg lie.
	// When the Dweg ending before the free reporting section end,
	// then the FMA on direction of DWeg is relevant
	def static boolean isRelevantFma(Fstr_DWeg dweg, Punkt_Objekt fma,
		TopPath pathToFma) {
		val fahrweg = dweg?.fstrFahrweg
		val endFarhwegPotk = fahrweg?.zielPunktObjekt.punktObjektTOPKante
		val topEndFahrweg = fahrweg?.zielPunktObjekt?.topKanten
		val test1 = fma.topKanten.exists[topEndFahrweg.contains(it)]
		val test2 = endFarhwegPotk.exists [
				pathToFma.getDistance(new TopPoint(it)).isPresent
			]
		return fma.topKanten.exists[topEndFahrweg.contains(it)] ||
			endFarhwegPotk.exists [
				pathToFma.getDistance(new TopPoint(it)).isPresent
			]
	}

}
