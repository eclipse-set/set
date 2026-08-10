/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sslr

import java.math.BigInteger
import java.util.Collections
import java.util.Set
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin

import static org.eclipse.set.feature.table.pt1.sslr.SslrColumns.*

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienAnzeigeElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FlaSchutzExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrAbhaengigkeitExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrRangierFlaZuordnungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*

/**
 * Table transformation for a Rangierstraßentabelle (Sslr).
 * 
 * @author Schneider/Schaefer
 */
class SslrTransformator extends AbstractPlanPro2TableModelTransformator {

	var TMFactory factory = null

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		this.factory = factory
		return container.transform
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup container) {
		container.fstrZugRangier.filter[isR].forEach [ it |
				if (Thread.currentThread.interrupted) {
					return
				}
				it.transform
			]
		return
	}

	private def TableRow create factory.newTableRow(fstrZugRangier) transform(
		Fstr_Zug_Rangier fstrZugRangier) {
		val fstrFahrwegStartSignalBezeichnung = fstrZugRangier.fstrFahrweg?.
			start?.bezeichnung?.bezeichnungTabelle?.wert ?: ""
		val fstrFahrwegZielSignalBezeichnung = fstrZugRangier.fstrFahrweg?.
			zielSignal?.bezeichnung?.bezeichnungTabelle?.wert ?: ""
		val row = it

		// A: Sslr.Grundsatzangaben.Bezeichnung
		fill(
			cols.getColumn(Bezeichnung),
			fstrZugRangier,
			[getRangierFstrBezeichnung[isR]]
		)

		// B: Sslr.Grundsatzangaben.Fahrweg.Start
		fill(
			cols.getColumn(Fahrweg_Start),
			fstrZugRangier,
			[fstrFahrwegStartSignalBezeichnung]
		)

		// C: Sslr.Grundsatzangaben.Fahrweg.Ziel
		fill(
			cols.getColumn(Fahrweg_Ziel),
			fstrZugRangier,
			[fstrFahrwegZielSignalBezeichnung]
		)

		// D: Sslr.Grundsatzangaben.Fahrweg.Nummer
		fillConditional(
			cols.getColumn(Fahrweg_Nummer),
			fstrZugRangier,
			[
				fstrZugRangierAllg?.fstrReihenfolge?.wert.compareTo(
					BigInteger.ZERO) > 0
			],
			[fstrZugRangierAllg.fstrReihenfolge.wert.toString]
		)

		// E: Sslr.Grundsatzangaben.Fahrweg.Entscheidungsweiche
		fillIterable(
			cols.getColumn(Fahrweg_Entscheidungsweiche),
			fstrZugRangier,
			[getEntscheidungsweichen(newLinkedList).map[bezeichnung]],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// F: Sslr.Grundsatzangaben.Art
		fill(
			cols.getColumn(Art),
			fstrZugRangier,
			[
				fstrRangier?.fstrRangierArt?.translate?.substring(0, 1)
			]
		)

		// G: Sslr.Einstellung.Autom_Einstellung
		fill(
			cols.getColumn(Autom_Einstellung),
			fstrZugRangier,
			[fstrZug?.automatischeEinstellung?.translate]
		)

		// H: Sslr.Einstellung.F_Bedienung
		fill(
			cols.getColumn(F_Bedienung),
			fstrZugRangier,
			[fstrZugRangierAllg?.FBedienung?.wert?.translate]
		)

		// I: Sslr.Abhaengigkeiten.Inselgleis.Bezeichnung
		val raFahrtGleichzeitigVerbot = fstrZugRangier?.fstrFahrweg?.
			zielSignal?.raFahrtGleichzeitigVerbot ?: Collections.emptySet
		fillIterable(
			cols.getColumn(Inselgleis_Bezeichnung),
			fstrZugRangier,
			[ fstr |
				val result = newHashSet
				if (!raFahrtGleichzeitigVerbot.nullOrEmpty) {
					result.addAll(raFahrtGleichzeitigVerbot.map [
						bezeichnung?.bezGleisBezeichnung?.wert
					].filterNull)
				}
				
				if (fstr.fstrRangier?.rangierGegenfahrtausschluss?.wert !==
					null) {
						val bezeichnung = fstr.container.gleisBezeichnung.filter [
						intersects(fstr?.fstrFahrweg?.zielSignal)
					].map[bezeichnung?.bezGleisBezeichnung?.wert].filterNull
					if (!bezeichnung.empty && result.addAll(bezeichnung)) {
						addTopologicalCell(cols.getColumn(Inselgleis_Bezeichnung))	
					}
				}
				return result
			],
			MIXED_STRING_COMPARATOR
		)

		// J: Sslr.Abhaengigkeiten.Inselgleis.Gegenfahrtausschluss
		fill(
			cols.getColumn(Inselgleis_Gegenfahrtausschluss),
			fstrZugRangier,
			[
				val gegenfahrtausschluss = fstrRangier?.
					rangierGegenfahrtausschluss?.wert
				if (gegenfahrtausschluss === null)
					return null
				switch (gegenfahrtausschluss) {
					case ENUM_RANGIER_GEGENFAHRTAUSSCHLUSS_INSELGLEIS_FREI:
						return "Inselgleis frei"
					case ENUM_RANGIER_GEGENFAHRTAUSSCHLUSS_JA:
						return true.translate
					case ENUM_RANGIER_GEGENFAHRTAUSSCHLUSS_NEIN:
						return false.translate
				}
			]
		)

		// K: Sslr.Abhaengigkeiten.Gleisfreimeldung
		fillIterable(
			cols.getColumn(Gleisfreimeldung),
			fstrZugRangier,
			[
				fmaAnlageRangierFrei?.map [
					IDGleisAbschnitt?.value?.bezeichnung?.bezeichnungTabelle?.
						wert
				].toSet
			],
			MIXED_STRING_COMPARATOR
		)

		// L: Sslr.Abhaengigkeiten.FwWeichen_mit_Fla
		fillIterable(
			cols.getColumn(FwWeichen_mit_Fla),
			fstrZugRangier,
			[
				fstrRangierFlaZuordnung?.map [
					(flaSchutz?.anforderer as W_Kr_Gsp_Element)?.bezeichnung?.
						bezeichnungTabelle?.wert
				]

			],
			MIXED_STRING_COMPARATOR
		)

		// M: Sslr.Abhaengigkeiten.Ueberwachte_Ssp
		fillIterable(
			cols.getColumn(Ueberwachte_Ssp),
			fstrZugRangier,
			[
				fstrFahrweg?.abhaengigkeiten.map [
					val ssp = schluesselsperre?.bezeichnung?.
						bezeichnungTabelle?.wert
					val aufloesungZielgleis = fstrAbhaengigkeitSsp?.
						aufloesungSspZielgleis?.translate

					aufloesungZielgleis !== null &&
						!aufloesungZielgleis.isEmpty()
						? '''«ssp» («aufloesungZielgleis»)''' : ssp
				]
			],
			MIXED_STRING_COMPARATOR
		)

		// N: Sslr.Abhaengigkeiten.Abhaengiger_BUe
		fillIterable(
			cols.getColumn(Abhaengiger_BUe),
			fstrZugRangier,
			[fstrFahrweg.BUes.map[bezeichnung.bezeichnungTabelle.wert]],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// O: Sslr.Abhaengigkeiten.Ziel_erlaubnisabh
		fill(
			cols.getColumn(Ziel_erlaubnisabh),
			fstrZugRangier,
			[
				(fstrFahrweg?.zielSignal?.signalFstr?.
					IDRaZielErlaubnisabhaengig?.value?.identitaet?.wert !==
					null).translate
			]
		)

		// P: Sslr.Abhaengigkeiten.Aufloes_Fstr
		fill(
			cols.getColumn(Aufloes_Fstr),
			fstrZugRangier,
			[
				fstrFahrweg?.zielSignal?.signalFstr?.
					rangierstrasseRestaufloesung?.wert?.translate
			]
		)

		// Q: Sslr.Bemerkung
		fill(
			cols.getColumn(Bemerkung),
			fstrZugRangier,
			[
				val bedAnzeigeElemente = fstrFahrweg?.abhaengigkeiten?.map [
					bedienAnzeigeElement
				]?.filterNull ?: Collections.emptyList
				'''«FOR bae : bedAnzeigeElemente.map[comment[translate]].filterNull SEPARATOR ", "»«bae»«ENDFOR»'''.
					toString.trim
			]
		)

		val bedAnzeigeElemente = fstrZugRangier.fstrFahrweg?.abhaengigkeiten?.
			map [
				bedienAnzeigeElement
			]?.filterNull ?: Collections.emptyList

		val besondersRangierFstrs = fstrZugRangier.IDFstrAusschlussBesonders?.
			filter [
				value.isR
			]?.filterNull ?: Collections.emptyList

		val zugFstrs = fstrZugRangier.IDFstrAusschlussBesonders?.filter [
			fstrZugRangier?.fstrRangier.fstrRangierArt.wert?.literal?.
				substring(0, 1) == "Z"
		]?.filterNull ?: Collections.emptyList
		fillSwitch(
			cols.getColumn(Bemerkung),
			fstrZugRangier,
			new Case<Fstr_Zug_Rangier>(
				[
					!bedAnzeigeElemente.empty && !bedAnzeigeElemente.map [
						comment[translate]
					].filterNull.empty
				],
				[
					'''«FOR bae : bedAnzeigeElemente.map[comment[translate]].filterNull SEPARATOR ", "»«bae»«ENDFOR»'''.
						toString.trim
				]
			),
			new Case<Fstr_Zug_Rangier>(
				[
					!besondersRangierFstrs.empty
				],
				[
					val fahrWegStartZiel = IDFstrAusschlussBesonders.map [
						value.fstrFahrweg.transformFahrwegStartZiel
					]

					'''«FOR fwsz : fahrWegStartZiel.filterNull SEPARATOR ", "»«fwsz»«ENDFOR»'''.
						toString.trim
				]
			),
			new Case<Fstr_Zug_Rangier>(
				[
					!zugFstrs.empty
				],
				[ zugRangier |
					'''«FOR fstr : zugFstrs SEPARATOR ", "»«fstr?.value?.fstrZugRangierBezeichnung»«ENDFOR»'''
				]
			)
		)

		fillFootnotes(row, fstrZugRangier)
		return
	}
}
