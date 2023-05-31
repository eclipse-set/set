/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssld

import java.math.RoundingMode
import java.util.Set
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.ppmodel.extensions.utils.TopGraph
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt
import org.eclipse.set.toolboxmodel.Fahrstrasse.Fstr_DWeg
import org.eclipse.set.toolboxmodel.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.toolboxmodel.Signale.Signal
import org.eclipse.set.utils.math.AgateRounding
import org.eclipse.set.utils.table.TMFactory

import static org.eclipse.set.feature.table.pt1.ssld.SsldColumns.*
import static org.eclipse.set.toolboxmodel.Signale.ENUMSignalFunktion.*

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.DwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrDWegSpezifischExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrDWegWKrExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.utils.graph.DigraphExtensions.*
import static extension org.eclipse.set.utils.math.BigDecimalExtensions.*

/**
 * Table transformation for a Durchrutschwegtabelle (SSLD).
 * 
 * @author Dittmer
 */
class SsldTransformator extends AbstractPlanPro2TableModelTransformator {

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService) {
		super(cols, enumTranslationService)
	}

	def static double getShortestPathLength(TopGraph topGraph, Signal signal,
		Punkt_Objekt p) {
		val paths = topGraph.getPaths(signal.singlePoints, p.singlePoints)
		if (paths.isNullOrEmpty) {
			return 0.0
		} else {
			return paths.map[it.length].min
		}
	}

	def static String getFreigemeldetLaenge(TopGraph topGraph, Fstr_DWeg dweg) {
		if (dweg.IDFMAAnlageFreimeldung === null) {
			return ""
		}

		val fmaAnlagen = dweg?.fmaAnlageFreimeldung
		if (fmaAnlagen.empty) {
			return ""
		}
		if (fmaAnlagen.contains(null)) {
			throw new IllegalArgumentException('''«dweg?.bezeichnung?.bezeichnungFstrDWeg?.wert» contains non-FMA-Anlagen within ID_FMA_Anlage''')
		}

		val fstrFahrwegsZumSignal = dweg.container.fstrFahrweg.filter [
			dweg.fstrFahrweg.start === start
		]
		val topDWeg = fstrFahrwegsZumSignal.map[bereichObjektTeilbereich].
			flatten.map[IDTOPKante].toList
		val punktObjects = fmaAnlagen?.map[fmaGrenzen]?.flatten?.filter [
			// Only consider FMA borders which are located on the DWeg
			punktObjektTOPKante.map[IDTOPKante].exists[topDWeg.contains(it)]
		]
		val distance = punktObjects?.fold(
			Double.valueOf(0.0), [ Double current, Punkt_Objekt grenze |
				Math.max(current,
					getShortestPathLength(topGraph, dweg?.fstrFahrweg?.start,
						grenze))
			])
		val roundedDistance = AgateRounding.roundDown(distance)
		if (roundedDistance == 0.0)
			throw new IllegalArgumentException("no path found")
		else
			return roundedDistance.toString
	}

	override transformTableContent(
		MultiContainer_AttributeGroup container,
		TMFactory factory
	) {

		val topGraph = new TopGraph(container.TOPKante)

		val fstDwegList = container.fstrDWeg

		// var footnoteNumber = 1;
		for (dweg : fstDwegList) {
			if (Thread.currentThread.interrupted) {
				return null
			}
			val instance = factory.newTableRow(dweg)
			// A: Ssld.Grundsatzangaben.von
			fill(
				instance,
				cols.getColumn(von),
				dweg,
				[fstrFahrweg.start.bezeichnung?.bezeichnungTabelle?.wert]
			)

			// B: Ssld.Grundsatzangaben.bis
			fillConditional(
				instance,
				cols.getColumn(bis),
				dweg,
				[fstrDWegSpezifisch !== null],
				[
					fstrFahrweg.zielPunkt.bezeichnung.bezeichnungMarkanterPunkt.
						wert
				]
			)

			// C: Ssld.Grundsatzangaben.Gefahrpunkt
			fillSwitch(
				instance,
				cols.getColumn(Gefahrpunkt),
				dweg,
				new Case<Fstr_DWeg>(
					[fstrDWegSpezifisch === null],
					[
						fstrFahrweg.zielPunkt.bezeichnung.
							bezeichnungMarkanterPunkt.wert
					]
				)
			)

			// D: Ssld.Grundsatzangaben.PZB_Gefahrpunkt
			fill(
				instance,
				cols.getColumn(PZB_Gefahrpunkt),
				dweg,
				[PZBGefahrpunkt?.bezeichnung?.bezeichnungMarkanterPunkt?.wert]
			)

			// E: Ssld.Grundsatzangaben.Bezeichnung
			fillConditional(
				instance,
				cols.getColumn(Bezeichnung),
				dweg,
				[fstrDWegSpezifisch !== null],
				[bezeichnung.bezeichnungFstrDWeg.wert]
			)

			// F: Ssld.Eigenschaften.Zielgeschwindigkeit_moeglich
			fillConditional(
				instance,
				cols.getColumn(Zielgeschwindigkeit_moeglich),
				dweg,
				[fstrDWegSpezifisch !== null],
				[fstrDWegSpezifisch.DWegV.wert.toString]
			)

			// G: Ssld.Eigenschaften.Laenge.Soll
			fill(
				instance,
				cols.getColumn(Laenge_Soll),
				dweg,
				[fstrDWegAllg.laengeSoll.wert.toString]
			)

			// H: Ssld.Eigenschaften.Laenge.Ist
			fill(
				instance,
				cols.getColumn(Laenge_Ist),
				dweg,
				[fstrFahrweg.length.toTableIntegerAgateDown]
			)

			// I: Ssld.Eigenschaften.Laenge.Freigemeldet
			fill(
				instance,
				cols.getColumn(Freigemeldet),
				dweg,
				[getFreigemeldetLaenge(topGraph, dweg)]
			)

			// J: Ssld.Eigenschaften.massgebende_Neigung
			fill(
				instance,
				cols.getColumn(massgebende_Neigung),
				dweg,
				[
					fstrDWegAllg.massgebendeNeigung.wert.setScale(1,
						RoundingMode.DOWN).toString
				]
			)

			// K: Ssld.Abhaengigkeiten.Weichen_Kreuzungen.mit_Verschluss
			fillIterableWithSeparatorConditional(
				instance,
				cols.getColumn(Weichen_Kreuzungen_mit_Verschluss),
				dweg,
				[fstrDWegSpezifisch !== null],
				[
					zuordnungen.filter[elementVerschluss?.wert].map [
						WKrGspElement.bezeichnung.bezeichnungTabelle.wert
					]
				],
				MIXED_STRING_COMPARATOR,
				[""],
				"\r\n"
			)

			// L: Ssld.Abhaengigkeiten.Weichen_Kreuzungen.ohne_Verschluss
			fillIterableWithSeparatorConditional(
				instance,
				cols.getColumn(Weichen_Kreuzungen_ohne_Verschluss),
				dweg,
				[fstrDWegSpezifisch !== null],
				[
					zuordnungen.filter[!elementVerschluss?.wert].map [
						WKrGspElement.bezeichnung.bezeichnungTabelle.wert
					]
				],
				MIXED_STRING_COMPARATOR,
				[""],
				"\r\n"
			)

			// M: Ssld.Abhaengigkeiten.relevante_FmA
			fillIterableWithSeparatorConditional(
				instance,
				cols.getColumn(relevante_FmA),
				dweg,
				[fstrDWegSpezifisch !== null],
				[
					val fmaAnlagen = dweg?.fmaAnlageFreimeldung
					if (fmaAnlagen.contains(null)) {
						throw new IllegalArgumentException('''«dweg?.bezeichnung?.bezeichnungFstrDWeg?.wert» contains non-FMA-Anlagen within ID_FMA_Anlage''')
					}
					fmaAnlagen.map[tableName]
				],
				MIXED_STRING_COMPARATOR,
				[""],
				"\r\n"
			)

			// N: Ssld.Abhaengigkeiten.v_Aufwertung_Verzicht
			fillConditional(
				instance,
				cols.getColumn(v_Aufwertung_Verzicht),
				dweg,
				[dweg?.fstrFahrweg?.start.isStartOfAnyTrainRoute],
				[
					dweg?.fstrDWegSpezifisch?.DWegVAufwertungVerzicht?.wert?.
						translate
				]
			)

			// O: Ssld.Abhaengigkeiten.Erlaubnisabhaengig
			fillConditional(
				instance,
				cols.getColumn(Erlaubnisabhaengig),
				dweg,
				[fstrDWegSpezifisch !== null],
				[
					if (fstrFahrweg.zielPunkt.IDDWegErlaubnisabhaengig !== null)
						"x"
					else if (#{
						ENUM_SIGNAL_FUNKTION_AUSFAHR_SIGNAL,
						ENUM_SIGNAL_FUNKTION_AUSFAHR_ZWISCHEN_SIGNAL,
						ENUM_SIGNAL_FUNKTION_EINFAHR_AUSFAHR_SIGNAL,
						ENUM_SIGNAL_FUNKTION_GRUPPENAUSFAHR_GRUPPENZWISCHEN_SIGNAL,
						ENUM_SIGNAL_FUNKTION_GRUPPENAUSFAHR_SIGNAL
					}.contains(
						fstrFahrweg?.start?.signalReal?.signalFunktion?.wert
					))
						"o"
				]
			)

			// P: Ssld.Aufloesung.Manuell
			fillConditional(
				instance,
				cols.getColumn(Manuell),
				dweg,
				[fstrDWegSpezifisch !== null],
				[
					fstrFahrweg?.start?.signalFstr?.DAManuell?.wert?.translate
				]
			)

			// Q: Ssld.Aufloesung.Zielgleisabschnitt.Bezeichnung
			fillConditional(
				instance,
				cols.getColumn(Zielgleisabschnitt_Bezeichnung),
				dweg,
				[dweg.fstrDWegSpezifisch !== null],
				[
					fstrDWegSpezifisch.fmaAnlageZielgleis.IDGleisAbschnitt.
						bezeichnung.bezeichnungTabelle.wert
				]
			)

			// R: Ssld.Aufloesung.Zielgleisabschnitt.Laenge
			fillConditional(
				instance,
				cols.getColumn(Zielgleisabschnitt_Laenge),
				dweg,
				[dweg.fstrDWegSpezifisch !== null],
				[
					val fstrs = fstrZugRangier
					if (fstrs.empty) {
						return ""
					}
					
					val distance = fstrs?.fold(
						Double.valueOf(0.0),
						[ Double current, Fstr_Zug_Rangier fstr |
							Math.max(current,
								fstrDWegSpezifisch?.IDFMAAnlageZielgleis?.
									IDGleisAbschnitt?.
									getOverlappingLength(fstr.IDFstrFahrweg).
									doubleValue)
						]
					)
					return AgateRounding.roundDown(distance).toString
				]
			)

			// S: Ssld.Aufloesung.Verzoegerung
			fillConditional(
				instance,
				cols.getColumn(Verzoegerung),
				dweg,
				[dweg.fstrDWegSpezifisch !== null],
				[
					fstrDWegSpezifisch.aufloesungVerzoegerung.wert.toString
				]
			)

			// T: Ssld.Bemerkung
			fill(
				instance,
				cols.getColumn("T"),
				dweg,
				[footnoteTransformation.transform(it, instance)]
			)

		}

		return factory.table
	}
}
