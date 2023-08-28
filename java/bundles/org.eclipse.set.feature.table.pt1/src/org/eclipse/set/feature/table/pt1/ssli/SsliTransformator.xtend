/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssli

import java.util.ArrayList
import java.util.HashMap
import java.util.HashSet
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.ppmodel.extensions.utils.TopGraph
import org.eclipse.set.toolboxmodel.Fahrstrasse.ENUMRangierGegenfahrtausschluss
import org.eclipse.set.toolboxmodel.Gleis.Gleis_Bezeichnung
import org.eclipse.set.toolboxmodel.Signale.ENUMSignalArt
import org.eclipse.set.toolboxmodel.Signale.Signal
import org.eclipse.set.utils.table.TMFactory

import static org.eclipse.set.feature.table.pt1.ssli.SsliColumns.*
import static org.eclipse.set.toolboxmodel.Fahrstrasse.ENUMRangierGegenfahrtausschluss.*

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.utils.graph.DigraphExtensions.*

/**
 * Table transformation for a Inselgleistabelle (Ssli).
 * 
 * @author Schaefer
 */
class SsliTransformator extends AbstractPlanPro2TableModelTransformator {

	var TMFactory factory = null
	var MultiContainer_AttributeGroup container = null

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService) {
		super(cols, enumTranslationService)
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		this.factory = factory
		this.container = container
		return container.transform
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup container) {
		transformToBegrenzungen(container.gleisBezeichnung, container.signal).
			filter[generalbedingung].forEach [ it |
				if (Thread.currentThread.interrupted) {
					return
				}
				it.transform
			]
		return
	}

	private static class Gleis_BezeichnungBegrenzung {
		Gleis_Bezeichnung gleisBezeichnung
		List<Signal> zgFahrtVerbot = new ArrayList<Signal>;
		List<Signal> raFahrtVerbot = new ArrayList<Signal>;
		Iterable<Signal> raGegenfahrtausschluss = new ArrayList<Signal>;

		new(Gleis_Bezeichnung bezeichnung) {
			gleisBezeichnung = bezeichnung
		}
	}

	private def TableRow create factory.newTableRow(gleisBezeichnungBegrenzung.gleisBezeichnung) transform(
		Gleis_BezeichnungBegrenzung gleisBezeichnungBegrenzung
	) {
		val row = it
		val gleisBezeichnung = gleisBezeichnungBegrenzung.gleisBezeichnung
		val begrenzungen = gleisBezeichnungBegrenzung.zgFahrtVerbot +
			gleisBezeichnungBegrenzung.raFahrtVerbot +
			gleisBezeichnungBegrenzung.raGegenfahrtausschluss

		// A: Ssli.Grundsatzangaben.Bezeichnung_Inselgleis
		fill(
			cols.getColumn(Bezeichnung_Inselgleis),
			gleisBezeichnung,
			[transformToBezeichnung]
		)

		val laengenBegrenzung = gleisBezeichnungBegrenzung.laengenBegrenzung
		// B: Ssli.Grundsatzangaben.Laenge
		fillIterable(
			cols.getColumn(Laenge),
			gleisBezeichnung,
			[
				laengenBegrenzung.map [
					String.format("%.0f", Math.floor(it))
				]
			],
			null,
			[it]
		)

		// C: Ssli.Grundsatzangaben.Begrenzende_Signale.PY_Richtung
		fillIterable(
			cols.getColumn(PY_Richtung),
			gleisBezeichnung,
			[
				begrenzungen.filter[lageplanKurzContains("POUVWY", true)].map [
					bezeichnung?.bezeichnungTabelle?.wert
				]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// D: Ssli.Grundsatzangaben.Begrenzende_Signale.NX_Richtung
		fillIterable(
			cols.getColumn(NX_Richtung),
			gleisBezeichnung,
			[
				begrenzungen.filter[lageplanKurzContains("NQRSTX", false)].map [
					bezeichnung?.bezeichnungTabelle?.wert
				]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// E: Ssli.Ausschluss_Fahrten.Zugausfahrt
		fillSwitch(
			cols.getColumn(Zugausfahrt),
			gleisBezeichnung,
			new Case<Gleis_Bezeichnung>([
				begrenzungen.exists [
					signalFstrAusInselgleis?.IDZgFahrtGleichzeitigVerbot !==
						null &&
						!signalFstrAusInselgleis.IDZgFahrtGleichzeitigVerbot.
							empty
				]
			], [true.translate]),
			new Case<Gleis_Bezeichnung>([
				begrenzungen.forall[startOfAnyTrainRoute]
			], [false.translate])
		)

		// F: Ssli.Ausschluss_Fahrten.Rangierfahrt.Einfahrt
		fillSwitch(
			cols.getColumn(Rangierfahrt_Einfahrt),
			gleisBezeichnung,
			new Case<Gleis_Bezeichnung>([
				begrenzungen.exists [
					transformToGegenfahrtausschluesse.contains(
						ENUM_RANGIER_GEGENFAHRTAUSSCHLUSS_JA) ||
						transformToGegenfahrtausschluesse.contains(
							ENUM_RANGIER_GEGENFAHRTAUSSCHLUSS_INSELGLEIS_FREI)
				]
			], [true.translate]),
			new Case<Gleis_Bezeichnung>([
				begrenzungen.forall [
					isStartOrDestinationOfAnyShuntingRoute(false)
				]
			], [false.translate])
		)

		// G: Ssli.Ausschluss_Fahrten.Rangierfahrt.Ausfahrt
		fillSwitch(
			cols.getColumn(Rangierfahrt_Ausfahrt),
			gleisBezeichnung,
			new Case<Gleis_Bezeichnung>([
				begrenzungen.exists [
					signalFstrAusInselgleis?.IDRaFahrtGleichzeitigVerbot !==
						null &&
						!signalFstrAusInselgleis.IDRaFahrtGleichzeitigVerbot.
							empty
				]
			], [true.translate]),
			new Case<Gleis_Bezeichnung>([
				begrenzungen.forall [
					isStartOrDestinationOfAnyShuntingRoute(true)
				]
			], [false.translate])
		)

		// H: Ssli.Bemerkung
		fill(
			cols.getColumn(Bemerkung),
			gleisBezeichnung,
			[footnoteTransformation.transform(it, row)]
		)

		return
	}

	private def List<Double> getLaengenBegrenzung(
		Gleis_BezeichnungBegrenzung gleis) {
		val begrenzungen = gleis.zgFahrtVerbot + gleis.raFahrtVerbot +
			gleis.raGegenfahrtausschluss

		// Try main signals first
		val shortest = getShortestSignalDistance(begrenzungen.filter [
			#[ENUMSignalArt.ENUM_SIGNAL_ART_HAUPTSIGNAL,
				ENUMSignalArt.ENUM_SIGNAL_ART_HAUPTSPERRSIGNAL,
				ENUMSignalArt.ENUM_SIGNAL_ART_MEHRABSCHNITTSSIGNAL,
				ENUMSignalArt.ENUM_SIGNAL_ART_MEHRABSCHNITTSSPERRSIGNAL].
				contains(signalReal?.signalRealAktivSchirm?.signalArt)
		])
		if (shortest !== null)
			return shortest

		// Otherwise get the shortest overall distance
		return getShortestSignalDistance(begrenzungen)
	}

	private def List<Double> getShortestSignalDistance(
		Iterable<Signal> begrenzungen) {
		val minLens = begrenzungen.map [ signalA |
			// Calculate the distance to all other signals 
			// and find the minimum distance
			val minLenFromA = begrenzungen.map [ signalB |
				if (signalA === signalB)
					return null
				createDistances(signalA, signalB)
			].filterNull.sortBy[stream.mapToDouble[d|d].sum]

			if (minLenFromA.empty)
				return null
			return minLenFromA.get(0)
		].filterNull
		if (minLens.empty)
			return null
		// Return the minimum length between any two signals
		return minLens.sortBy[stream.mapToDouble[d|d].sum].get(0)
	}

	private def String create createBezeichnung(gleisBezeichnung) transformToBezeichnung(
		Gleis_Bezeichnung gleisBezeichnung
	) {
	}

	private def Set<ENUMRangierGegenfahrtausschluss> create new HashSet transformToGegenfahrtausschluesse(
		Signal signal
	) {
		addAll(container.fstrFahrweg.filter [
			IDZiel?.identitaet?.wert == signal?.identitaet?.wert
		].map [ fw |
			container.fstrZugRangier.filter [
				IDFstrFahrweg?.identitaet?.wert == fw?.identitaet?.wert
			]
		].flatten.toSet.map[fstrRangier?.rangierGegenfahrtausschluss?.wert])
		return
	}

	private def Iterable<Gleis_BezeichnungBegrenzung> transformToBegrenzungen(
		Iterable<Gleis_Bezeichnung> gleis,
		Iterable<Signal> signals
	) {
		val Map<String, Gleis_BezeichnungBegrenzung> result = new HashMap<String, Gleis_BezeichnungBegrenzung>
		gleis.forEach [ g |
			val gleisBegrenzung = new Gleis_BezeichnungBegrenzung(g)
			result.put(g.bezeichnung.bezGleisBezeichnung.wert, gleisBegrenzung)

			val signaleAufGleis = g.filterContained(container.signal)
			gleisBegrenzung.raGegenfahrtausschluss = signaleAufGleis.filter [
				val ausschluesse = transformToGegenfahrtausschluesse
				ausschluesse.contains(ENUM_RANGIER_GEGENFAHRTAUSSCHLUSS_JA) ||
					ausschluesse.contains(
						ENUM_RANGIER_GEGENFAHRTAUSSCHLUSS_INSELGLEIS_FREI)
			]

		]
		signals.forEach [ signal |
			val rafahrt = signal?.signalFstrAusInselgleis?.
				IDRaFahrtGleichzeitigVerbot
			rafahrt?.filter[gl|result.containsKey(gl.identitaet?.wert)]?.forEach [ gl |
				result.get(gl.identitaet?.wert).raFahrtVerbot.add(signal)
			]
			val zgfahrt = signal?.signalFstrAusInselgleis?.
				IDZgFahrtGleichzeitigVerbot
			zgfahrt?.filter[gl|result.containsKey(gl.identitaet?.wert)]?.forEach [ gl |
				result.get(gl.identitaet?.wert).zgFahrtVerbot.add(signal)
			]
		]

		return result.values
	}

	private def String createBezeichnung(Gleis_Bezeichnung gleisBezeichnung) {
		return gleisBezeichnung?.bezeichnung?.bezGleisBezeichnung?.wert
	}

	private def List<Double> createDistances(Signal a, Signal b) {
		val topGraph = new TopGraph(a.container.TOPKante)
		val paths = topGraph.getPaths(a.singlePoints, b.singlePoints)

		return paths.map[length].toList
	}

	private def boolean getGeneralbedingung(
		Gleis_BezeichnungBegrenzung gleisBezeichnung
	) {
		return gleisBezeichnung.zgFahrtVerbot.size >= 2 ||
			gleisBezeichnung.raFahrtVerbot.size >= 2 ||
			gleisBezeichnung.raGegenfahrtausschluss.size == 2
	}

	private static def boolean lageplanKurzContains(Signal signal,
		String letters, boolean isEven) {
		val bezeichnung = signal?.bezeichnung?.bezeichnungLageplanKurz?.wert
		if (bezeichnung === null) {
			return false
		}
		if (letters.toCharArray.exists[bezeichnung.contains(toString)]) {
			return true
		}
		try {
			val bezeichnungsNummer = Integer.parseInt(bezeichnung)
			if (isEven) {
				return bezeichnungsNummer % 2 == 0
			} else {
				return bezeichnungsNummer % 2 != 0
			}
		} catch (NumberFormatException ex) {
		}
		return false
	}
}
