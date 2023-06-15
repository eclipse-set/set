/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssld

import java.math.RoundingMode
import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.feature.table.messages.MessagesWrapper
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.format.TextAlignment
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.ppmodel.extensions.utils.TopGraph
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt
import org.eclipse.set.toolboxmodel.Fahrstrasse.Fstr_DWeg
import org.eclipse.set.toolboxmodel.Signale.Signal
import org.eclipse.set.utils.math.AgateRounding
import org.eclipse.set.utils.table.TMFactory

import static org.eclipse.set.toolboxmodel.Signale.ENUMSignalFunktion.*

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
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

	SsldColumns cols

	new(SsldColumns columns, MessagesWrapper messagesWrapper) {
		super(messagesWrapper)
		this.cols = columns;
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
		val endFMA = dweg?.FMAs
		if (endFMA.empty) {
			return ""
		}
		val distance = endFMA?.fold(
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
			// von (Signal)
			fill(
				instance,
				cols.von,
				dweg,
				[fstrFahrweg.start.bezeichnung?.bezeichnungTabelle?.wert]
			)

			// bis (Markanter Punkt)
			fillConditional(
				instance,
				cols.bis,
				dweg,
				[fstrDWegSpezifisch !== null],
				[
					fstrFahrweg.zielPunkt.bezeichnung.bezeichnungMarkanterPunkt.
						wert
				]
			)

			// Gefahrpunkt
			fillSwitch(
				instance,
				cols.gefahrpunkt,
				dweg,
				new Case<Fstr_DWeg>(
					[fstrDWegSpezifisch === null],
					[
						fstrFahrweg.zielPunkt.bezeichnung.
							bezeichnungMarkanterPunkt.wert
					]
				)
			)

			// PZB Gefahrpunkt
			fill(
				instance,
				cols.pzb_gefahrpunkt,
				dweg,
				[IDPZBGefahrpunkt?.bezeichnung?.bezeichnungMarkanterPunkt?.wert]
			)

			// Bezeichnung
			fillConditional(
				instance,
				cols.basis_bezeichnung,
				dweg,
				[fstrDWegSpezifisch !== null],
				[bezeichnung.bezeichnungFstrDWeg.wert]
			)

			// Zielgeschwindigkeit möglich
			fillConditional(
				instance,
				cols.zielgeschwindigkeit_moeglich,
				dweg,
				[fstrDWegSpezifisch !== null],
				[fstrDWegSpezifisch.DWegV.wert.toString]
			)

			// Soll
			fill(
				instance,
				cols.soll,
				dweg,
				[fstrDWegAllg.laengeSoll.wert.toString]
			)

			// Ist
			fill(
				instance,
				cols.ist,
				dweg,
				[fstrFahrweg.length.toTableIntegerAgateDown]
			)

			// I: Ssld.Eigenschaften.Laenge.Freigemeldet
			fill(instance, cols.freigemeldet, dweg, [
				getFreigemeldetLaenge(topGraph, dweg)
			])

			// maßgebende Neigung
			fill(
				instance,
				cols.massgebende_neigung,
				dweg,
				[
					fstrDWegAllg.massgebendeNeigung.wert.setScale(1,
						RoundingMode.DOWN).toString
				]
			)

			// mit Verschluss
			fillIterableWithSeparatorConditional(
				instance,
				cols.mit_verschluss,
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

			// ohne Verschluss
			fillIterableWithSeparatorConditional(
				instance,
				cols.ohne_verschluss,
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

			// relevante FmA
			fillIterableWithSeparatorConditional(
				instance,
				cols.relevante_fma,
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

			// v-Aufwertung Verzicht
			fillConditional(
				instance,
				cols.v_aufwertung_verzicht,
				dweg,
				[dweg?.fstrFahrweg?.start.isStartOfAnyTrainRoute],
				[
					dweg?.fstrDWegSpezifisch?.DWegVAufwertungVerzicht?.wert?.
						translate
				]
			)

			// Erlaubnisabhängig
			fillConditional(
				instance,
				cols.erlaubnisabhaengig,
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
						fstrFahrweg?.start?.signalReal?.signalRealAktiv?.
							signalFunktion?.wert
					))
						"o"
				]
			)

			// manuell
			fillConditional(
				instance,
				cols.manuell,
				dweg,
				[fstrDWegSpezifisch !== null],
				[
					fstrFahrweg?.start?.signalFstr?.DAManuell?.wert?.translate
				]
			)

			// Zielgleisabschnitt_Bezeichnung
			fillConditional(
				instance,
				cols.bezeichnung,
				dweg,
				[dweg.fstrDWegSpezifisch !== null],
				[
					fstrDWegSpezifisch.fmaAnlageZielgleis.IDGleisAbschnitt.
						bezeichnung.bezeichnungTabelle.wert
				]
			)

			// Länge
			fillConditional(
				instance,
				cols.laenge,
				dweg,
				[dweg.fstrDWegSpezifisch !== null],
				[
					val fstrs = container.fstrZugRangier.filter [
						fstrZug?.fstrZugDWeg?.IDFstrDWeg === dweg
					]
					if (fstrs.empty) {
						return ""
					}

					fstrDWegSpezifisch.IDFMAAnlageZielgleis.IDGleisAbschnitt.
						getOverlappingLength(fstrs.get(0).IDFstrFahrweg).
						toTableIntegerAgateDown
				]
			)

			// Verzögerung
			fillConditional(
				instance,
				cols.verzoegerung,
				dweg,
				[dweg.fstrDWegSpezifisch !== null],
				[
					fstrDWegSpezifisch.aufloesungVerzoegerung.wert.toString
				]
			)

			// PLANPRO-2377
			// Verschieben nach BasisObjektExtensions.comment(Basis_Objekt)
			// Bemerkung ==> Fussnoten erstellen
			// fillIterable verwenden
//							var str = ""
//							var first = true
//							val comments = dweg.comments
//							for (Bearbeitungsvermerk vermerk : comments) {
//								if (!first) {
//									str = str + ", "
//								}
//								first = false
//								str = str + "*" + footnoteNumber + " "
//								instance.addFootnote(footnoteNumber,
//									vermerk?.bearbeitungsvermerkAllg?.
//										kommentar?.wert)
//								footnoteNumber++
//							}
//
//							val commentText = str.trim;
			fill(
				instance,
				cols.basis_bemerkung,
				dweg,
				[footnoteTransformation.transform(it, instance)]
			)

		}

		return factory.table
	}

	override void formatTableContent(Table table) {
		// A: Ssld.Grundsatzangaben.von
		table.setTextAlignment(0, TextAlignment.LEFT);

		// B: Ssld.Grundsatzangaben.bis
		table.setTextAlignment(1, TextAlignment.LEFT);

		// C: Ssld.Grundsatzangaben.Gefahrpunkt
		table.setTextAlignment(2, TextAlignment.LEFT);

		// G: Bemerkung
		table.setTextAlignment(19, TextAlignment.LEFT);
	}
}
