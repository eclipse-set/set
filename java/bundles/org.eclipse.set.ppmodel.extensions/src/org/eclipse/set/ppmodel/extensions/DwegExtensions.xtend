/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Fstr_DWeg
import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Fstr_DWeg_W_Kr
import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Fstr_Fahrweg
import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Fstr_Zug_Rangier
import de.scheidtbachmann.planpro.model.model1902.Fahrstrasse.Markanter_Punkt
import de.scheidtbachmann.planpro.model.model1902.Ortung.FMA_Anlage
import java.util.LinkedList
import java.util.List

import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import java.util.Set
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

/**
 * Extensions for {@link Fstr_DWeg}.
 */
class DwegExtensions extends BasisObjektExtensions {

	/**
	 * @param dweg this Durchrutschweg
	 * 
	 * @returns the Fahrweg
	 */
	def static Fstr_Fahrweg getFstrFahrweg(
		Fstr_DWeg dweg
	) {
		return dweg.IDFstrFahrweg.resolve(Fstr_Fahrweg)
	}

	/**
	 * @param dweg this Durchrutschweg
	 * 
	 * @returns the Weichen/Kreuzungen-Zuordnungen
	 */
	def static List<Fstr_DWeg_W_Kr> zuordnungen(Fstr_DWeg dweg) {
		val result = new LinkedList<Fstr_DWeg_W_Kr>
		val zuordnungen = dweg.container.fstrDWegWKr
		for (zuordnung : zuordnungen) {
			if (zuordnung.IDFstrDWeg.wert == dweg.identitaet.wert) {
				result.add(zuordnung)
			}
		}
		return result
	}

	/**
	 * @param dweg this Durchrutschweg
	 * 
	 * @returns the Zug-/Rangierstraßen for this Durchrutschweg
	 */
	def static List<Fstr_Zug_Rangier> fstrZugRangier(Fstr_DWeg dweg) {
		val result = new LinkedList<Fstr_Zug_Rangier>
		val fstrList = dweg.container.fstrZugRangier
		for (fstr : fstrList) {
			if (fstr.fstrZug?.fstrZugDWeg !== null) {
				if (fstr.fstrZug.fstrZugDWeg.IDFstrDWeg.wert ==
					dweg.identitaet.wert) {
					result.add(fstr)
				}
			}
		}
		return result
	}

	/**
	 * @param dweg this Durchrutschweg
	 * 
	 * @returns the FMA Anlage Freimeldung set of this Durchrutschweg
	 */
	def static Set<FMA_Anlage> getFmaAnlageFreimeldung(Fstr_DWeg dweg) {
		return dweg.IDFMAAnlageFreimeldung.map [
			resolve(FMA_Anlage)
		].toSet
	}

	/**
	 * @param dweg this Durchrutschweg
	 * 
	 * @returns the PZB Gefahrpunkt
	 */
	def static Markanter_Punkt getPZBGefahrpunkt(Fstr_DWeg dweg) {
		return dweg.IDPZBGefahrpunkt.resolve(Markanter_Punkt)
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
}
