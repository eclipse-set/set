/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssld

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Set
import org.eclipse.set.basis.graph.TopPath
import org.eclipse.set.basis.graph.TopPoint
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.core.services.graph.TopologicalGraphService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg
import org.eclipse.set.model.planpro.Geodaten.ENUMTOPAnschluss
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.ppmodel.extensions.utils.TopGraph
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin

import static org.eclipse.set.feature.table.pt1.ssld.SsldColumns.*
import static org.eclipse.set.model.planpro.Signale.ENUMSignalFunktion.*

import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.DwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrDWegSpezifischExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrDWegWKrExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKnotenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import static extension org.eclipse.set.utils.math.BigDecimalExtensions.*

/**
 * Table transformation for a Durchrutschwegtabelle (SSLD).
 * 
 * @author Dittmer
 */
class SsldTransformator extends AbstractPlanPro2TableModelTransformator {

	val TopologicalGraphService topGraphService;

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService,
		TopologicalGraphService topGraphService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
		this.topGraphService = topGraphService
	}

	def TopPath getShortestPath(Signal signal, Punkt_Objekt p) {
		val points1 = signal.singlePoints.map[new TopPoint(it)]
		val points2 = p.singlePoints.map[new TopPoint(it)]

		return points1.flatMap [ pa |
			points2.map [ pb |
				topGraphService.findShortestPath(pa, pb)
			]
		].filter[present].map[get].minBy[length]
	}

	def String getFreigemeldetLaenge(Fstr_DWeg dweg, TopGraph topGraph,
		BigDecimal maxLength) {
		val startSignal = dweg?.fstrFahrweg?.start
		val fmas = dweg?.fmaAnlageFreimeldung?.map[fmaGrenzen]?.flatten.toSet.
			filter[topGraph.isInWirkrichtungOfSignal(startSignal, it)].toList
		val pathFromSignalToFMA = fmas?.map [
			it -> getShortestPath(dweg?.fstrFahrweg?.start, it)
		]
		if (pathFromSignalToFMA.isEmpty) {
			return ""
		}

		val relevantFmas = pathFromSignalToFMA.filter [
			dweg.isRelevantFma(key, value)
		].toList

		// When the free reporting section ending before the Dweg end,
		// then take the FMA am nearst of the end of Dweg
		if (relevantFmas.isEmpty) {
			val fstrTOPKante = dweg.fstrFahrweg.bereichObjektTeilbereich.map [
				topKante
			]
			val relevantPaths = pathFromSignalToFMA.filter [
				value.edges.forall[fstrTOPKante.contains(it)]
			]
			if (relevantPaths.empty) {
				throw new IllegalArgumentException("no path found")
			}
			return relevantPaths.maxBy[value.length].value.length.
				toTableIntegerAgateDown
		}

		val distance = relevantFmas.map[value.length].max
		return distance > maxLength.add(BigDecimal.ONE)
			? '''> «maxLength.toTableIntegerAgateDown»''' : distance.
			toTableIntegerAgateDown
	}

	override transformTableContent(
		MultiContainer_AttributeGroup container,
		TMFactory factory,
		Stell_Bereich controlArea
	) {
		val topGraph = new TopGraph(container.TOPKante)
		val fstDwegList = container.fstrDWeg.filter[isPlanningObject].
			filterObjectsInControlArea(controlArea)

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
				[
					IDPZBGefahrpunkt?.value?.bezeichnung?.
						bezeichnungMarkanterPunkt?.wert
				]
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
				[fstrDWegSpezifisch?.DWegV?.wert?.toString]
			)

			// G: Ssld.Eigenschaften.Laenge.Soll
			fill(
				instance,
				cols.getColumn(Laenge_Soll),
				dweg,
				[fstrDWegAllg?.laengeSoll?.wert?.toString]
			)

			// H: Ssld.Eigenschaften.Laenge.Ist
			val fstrFahrWegLength = dweg.fstrFahrweg?.length
			fill(
				instance,
				cols.getColumn(Laenge_Ist),
				dweg,
				[fstrFahrWegLength?.toTableIntegerAgateDown]
			)

			// I: Ssld.Eigenschaften.Laenge.Freigemeldet
			fill(
				instance,
				cols.getColumn(Freigemeldet),
				dweg,
				[
					getFreigemeldetLaenge(topGraph,
						fstrFahrWegLength ?:
							BigDecimal.valueOf(Integer.MAX_VALUE))
				]
			)

			// J: Ssld.Eigenschaften.massgebende_Neigung
			fill(
				instance,
				cols.getColumn(massgebende_Neigung),
				dweg,
				[
					fstrDWegAllg.massgebendeNeigung?.wert?.setScale(1,
						RoundingMode.FLOOR)?.toString
				]
			)

			// K: Ssld.Abhaengigkeiten.Weichen_Kreuzungen.mit_Verschluss
			fillIterable(
				instance,
				cols.getColumn(Weichen_Kreuzungen_mit_Verschluss),
				dweg,
				[fillWeichenKreuzungen(Boolean.TRUE)],
				MIXED_STRING_COMPARATOR
			)

			// L: Ssld.Abhaengigkeiten.Weichen_Kreuzungen.ohne_Verschluss
			fillIterable(
				instance,
				cols.getColumn(Weichen_Kreuzungen_ohne_Verschluss),
				dweg,
				[fillWeichenKreuzungen(Boolean.FALSE)],
				MIXED_STRING_COMPARATOR
			)

			// M: Ssld.Abhaengigkeiten.relevante_FmA
			fillIterable(
				instance,
				cols.getColumn(relevante_FmA),
				dweg,
				[
					val fmaAnlagen = dweg?.fmaAnlageFreimeldung
					if (fmaAnlagen.contains(null)) {
						throw new IllegalArgumentException('''«dweg?.bezeichnung?.bezeichnungFstrDWeg?.wert» contains non-FMA-Anlagen within ID_FMA_Anlage''')
					}
					fmaAnlagen.map[tableName]
				],
				MIXED_STRING_COMPARATOR
			)

			// N: Ssld.Abhaengigkeiten.v_Aufwertung_Verzicht
			fillConditional(
				instance,
				cols.getColumn(v_Aufwertung_Verzicht),
				dweg,
				[fstrFahrweg?.start.isStartOfAnyTrainRoute],
				[
					fstrDWegSpezifisch?.DWegVAufwertungVerzicht?.wert?.translate
				]
			)

			// O: Ssld.Abhaengigkeiten.Erlaubnisabhaengig
			fillConditional(
				instance,
				cols.getColumn(Erlaubnisabhaengig),
				dweg,
				[fstrFahrweg?.zielPunkt?.IDDWegErlaubnisabhaengig !== null],
				["x"],
				[
					#{
						ENUM_SIGNAL_FUNKTION_AUSFAHR_SIGNAL,
						ENUM_SIGNAL_FUNKTION_AUSFAHR_ZWISCHEN_SIGNAL,
						ENUM_SIGNAL_FUNKTION_EINFAHR_AUSFAHR_SIGNAL,
						ENUM_SIGNAL_FUNKTION_GRUPPENAUSFAHR_GRUPPENZWISCHEN_SIGNAL,
						ENUM_SIGNAL_FUNKTION_GRUPPENAUSFAHR_SIGNAL
					}.contains(
						fstrFahrweg?.start?.signalReal?.signalFunktion?.wert
					) ? "□" : ""
				]
			)

			// P: Ssld.Aufloesung.Manuell
			fill(
				instance,
				cols.getColumn(Manuell),
				dweg,
				[
					fstrFahrweg?.start?.signalFstr?.DAManuell?.wert?.
						translate ?: ""
				]
			)

			// Q: Ssld.Aufloesung.Aufloeseabschnitt.Bezeichnung
			fillConditional(
				instance,
				cols.getColumn(Aufloeseabschnitt_Bezeichnung),
				dweg,
				[fstrDWegSpezifisch !== null],
				[
					fstrDWegSpezifisch.fmaAnlageZielgleis?.IDGleisAbschnitt?.
						value?.bezeichnung?.bezeichnungTabelle?.wert ?: ""
				]
			)

			// R: Ssld.Aufloesung.Aufloeseabschnitt.Laenge
			fillConditional(
				instance,
				cols.getColumn(Aufloeseabschnitt_Laenge),
				dweg,
				[fstrDWegSpezifisch !== null],
				[
					getZielGleisAbschnittLength(topGraph)
				]
			)

			// S: Ssld.Aufloesung.Verzoegerung
			fillConditional(
				instance,
				cols.getColumn(Verzoegerung),
				dweg,
				[fstrDWegSpezifisch !== null],
				[
					fstrDWegSpezifisch?.aufloesungVerzoegerung?.wert?.toString
				]
			)

			// T: Ssld.Bemerkung
			fillFootnotes(instance, dweg)

		}

		return factory.table
	}

	private def String getZielGleisAbschnittLength(Fstr_DWeg dweg,
		TopGraph topGraph) {
		val startSignal = dweg?.fstrFahrweg?.start
		val fmaAnlage = dweg.fstrDWegSpezifisch?.IDFMAAnlageZielgleis?.value
		// The relevant FMA shouldn't lie on direction of start signal
		val fmaKomponenten = fmaAnlage.fmaGrenzen.filter [
			!topGraph.isInWirkrichtungOfSignal(startSignal, it)
		].toList
		val pathsFromSignalToFMA = fmaKomponenten.map [
			startSignal.getShortestPath(it)
		]

		val fstrs = dweg.fstrZugRangier
		val relevantPaths = fstrs.empty
				// if no fstrs we take all paths to any of the fmaKomponenten 
				? pathsFromSignalToFMA
				// otherwise we determine which path is overlapping with Fstr
				: fstrs.map [ fstr |
				val fstrTOPKanten = fstr.IDFstrFahrweg?.value.
					bereichObjektTeilbereich.map[IDTOPKante.value]
				val overlappingtPaths = pathsFromSignalToFMA.filter [ path |
					path.edges.forall[fstrTOPKanten.contains(it)]
				]
				return overlappingtPaths
			].flatten
		// of all the relevant paths between fmaGrenzen and startSignal we take the longest path
		val maxDistance = relevantPaths.map[it -> length].maxBy[value]
		return maxDistance.value.toTableIntegerAgateDown
	}

	private def Iterable<String> fillWeichenKreuzungen(Fstr_DWeg dweg,
		Boolean closure) {
		val dwegTopKante = dweg.fstrFahrweg.topKanten
		return dweg.zuordnungen.filter[elementVerschluss?.wert == closure].map [
			val gspElement = WKrGspElement
			val weichenKnoten = gspElement.WKrGspKomponenten.map[topKnoten].
				filterNull
			val relevantKnoten = weichenKnoten.filter [ knoten |
				return knoten.topKanten.exists [ knotenKante |
					dwegTopKante.exists[knotenKante === it]
				]
			]
			val anschluss = relevantKnoten.flatMap [ knoten |
				knoten.topKanten.filter[dwegTopKante.contains(it)].map [
					getTOPAnschluss(knoten)
				].filter[it !== ENUMTOPAnschluss.ENUMTOP_ANSCHLUSS_SPITZE]
			]
			return gspElement -> anschluss
		].map [ pair |
			val lage = pair.value.map [
				switch (it) {
					case ENUMTOP_ANSCHLUSS_LINKS:
						"L"
					case ENUMTOP_ANSCHLUSS_RECHTS:
						"R"
					default:
						throw new IllegalArgumentException('''Ambiguous connection at Gsp_Element: «pair.key.identitaet.wert»''')
				}
			]
			return '''«pair.key.bezeichnung?.bezeichnungTabelle?.wert»«IF !lage.nullOrEmpty» («lage.join(", ")»)«ENDIF»'''
		]
	}
}
