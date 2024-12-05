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
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg_W_Kr
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Fahrweg
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Gleis.Gleis_Abschnitt
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element

import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrDWegWKrExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*

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

	/**
	 * Find FMA of this durchrutschweg
	 * @param dweg this Durchrutschweg
	 * @return the fma on this durchrutschweg, without fma on start signal
	 */
	def static Iterable<Punkt_Objekt> getFMAs(Fstr_DWeg dweg) {
		val fmaAnlagen = dweg?.fmaAnlageFreimeldung.toList
		if (fmaAnlagen.empty || fmaAnlagen === null) {
			return emptySet
		}

		if (fmaAnlagen.contains(null)) {
			throw new IllegalArgumentException('''«dweg?.bezeichnung?.bezeichnungFstrDWeg?.wert» contains non-FMA-Anlagen within ID_FMA_Anlage''')
		}

		val topFahrWeg = dweg?.fstrFahrweg?.topKanten
		val fmaGrenzens = fmaAnlagen.map[fmaGrenzen].flatten.toSet
		val startSignal = dweg.fstrFahrweg?.start

		return fmaGrenzens.filter [ fma |
			val isFmaAtStartSignal = fma.singlePoints.exists [ fmaPotk |
				startSignal.singlePoints.map[abstand.wert].exists [ signalDistance |
					signalDistance.compareTo(fmaPotk.abstand.wert) == 0
				]
			]

			return fma.topKanten.exists[topFahrWeg.contains(it)] &&
				!isFmaAtStartSignal || dweg.isRelevantFma(fma)
		].toSet
	}

	// Fall start signal and the target fma stay on two different TOP_Kante
	def private static boolean isRelevantFma(Fstr_DWeg dweg, Punkt_Objekt fma) {
		val fahrweg = dweg?.fstrFahrweg
		val topFahrWeg = fahrweg?.topKanten
		val startSignal = fahrweg?.start
		val topEndFahrweg = fahrweg?.zielPunktObjekt?.topKanten
		// When slip way run over a track switch
		val dwegGspElement = dweg?.zuordnungen?.map[WKrGspElement]
		if (!dwegGspElement.empty) {
			// 1. Fall: start from leg of track switch
			if (startSignal.topKanten.exists [
				dwegGspElement.map[#[topKanteL, topKanteR]].flatten.contains(it)
			]) {
				return fma.topKanten.exists [
					topEndFahrweg.contains(it)
				]
			}

			// 2. Fall: start from top of track switch and this switch is a combined switch
			val connectionGsp = dwegGspElement.map [
				val gzL = weicheElement?.GZFreimeldungL?.element
				val gzR = weicheElement?.GZFreimeldungR?.element
				if (gzL !== null && topFahrWeg.contains(topKanteL)) {
					return gzL
				} else if (gzR !== null && topFahrWeg.contains(topKanteR)) {
					return gzR
				}
				return null
			].filterNull
			if (!connectionGsp.empty) {
				return fma.topKanten.exists [ topGrenze |
					connectionGsp.exists [
						gzFreimeldungTOPKante.contains(topGrenze)
					]
				]
			}
		}

		val fmaAnlageOnZiel = fahrweg?.container?.FMAAnlage?.filter [
			IDGleisAbschnitt?.value?.topKanten.exists [
				topEndFahrweg.contains(it)
			] && !dweg.fmaAnlageFreimeldung.contains(it)
		].toSet
		return fmaAnlageOnZiel.map[fmaGrenzen].flatten.exists [
			it === fma
		]
	}

	private static def dispatch Set<TOP_Kante> gzFreimeldungTOPKante(
		Basis_Objekt basicObject) {
		throw new IllegalArgumentException(basicObject.class.simpleName)
	}

	private static def dispatch Set<TOP_Kante> gzFreimeldungTOPKante(
		W_Kr_Gsp_Element gspElement) {
		return #[gspElement.topKanteL, gspElement.topKanteR].toSet
	}

	private static def dispatch Set<TOP_Kante> gzFreimeldungTOPKante(
		Gleis_Abschnitt gleisAbschnitt) {
		return gleisAbschnitt.topKanten
	}
}
