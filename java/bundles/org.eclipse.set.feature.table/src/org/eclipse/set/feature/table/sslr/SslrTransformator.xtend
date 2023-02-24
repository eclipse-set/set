/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sslr

import java.math.BigInteger
import java.util.Collections
import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.feature.table.messages.MessagesWrapper
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.format.TextAlignment
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.toolboxmodel.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.utils.table.TMFactory

import static org.eclipse.set.toolboxmodel.Fahrstrasse.ENUMRangierGegenfahrtausschluss.*

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
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

	val SslrColumns columns
	var TMFactory factory = null

	new(SslrColumns columns, MessagesWrapper messagesWrapper) {
		super(messagesWrapper)
		this.columns = columns;
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		this.factory = factory
		return container.transform
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup container) {
		container.fstrZugRangier.filter[generalbedingung].forEach [ it |
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
		fillSwitch(
			columns.Bezeichnung,
			fstrZugRangier,
			new Case<Fstr_Zug_Rangier>(
				[
					fstrZugRangierAllg?.fstrReihenfolge?.wert.compareTo(
						BigInteger.ZERO) == 0
				],
				[
					'''«fstrFahrwegStartSignalBezeichnung»/«fstrFahrwegZielSignalBezeichnung»'''
				]
			),
			new Case<Fstr_Zug_Rangier>(
				[
					fstrZugRangierAllg?.fstrReihenfolge?.wert.compareTo(
						BigInteger.ZERO) > 0
				],
				[
					'''«fstrFahrwegStartSignalBezeichnung»/«fstrFahrwegZielSignalBezeichnung» [U«fstrZugRangierAllg.fstrReihenfolge.wert.toString»]'''
				]
			)
		)

		// B: Sslr.Grundsatzangaben.Fahrweg.Start
		fill(
			columns.Start,
			fstrZugRangier,
			[fstrFahrwegStartSignalBezeichnung]
		)

		// C: Sslr.Grundsatzangaben.Fahrweg.Ziel
		fill(
			columns.Ziel,
			fstrZugRangier,
			[fstrFahrwegZielSignalBezeichnung]
		)

		// D: Sslr.Grundsatzangaben.Fahrweg.Nummer
		fillConditional(
			columns.Nummer,
			fstrZugRangier,
			[
				fstrZugRangierAllg?.fstrReihenfolge?.wert.compareTo(
					BigInteger.ONE) > 0
			],
			[(fstrZugRangierAllg.fstrReihenfolge.wert - BigInteger.ONE).toString]
		)

		// E: Sslr.Grundsatzangaben.Fahrweg.Entscheidungsweiche
		fillIterable(
			columns.Entscheidungsweiche,
			fstrZugRangier,
			[getEntscheidungsweichen(newLinkedList()).map[bezeichnung]],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// F: Sslr.Grundsatzangaben.Art
		fill(
			columns.Art,
			fstrZugRangier,
			[
				fstrZugRangierAllg?.fstrArt?.wert?.translate?.substring(0, 1)
			]
		)

		// G: Sslr.Einstellung.Autom_Einstellung
		fill(
			columns.Autom_Einstellung,
			fstrZugRangier,
			[fstrZug?.automatischeEinstellung?.wert?.translate]
		)

		// H: Sslr.Einstellung.F_Bedienung
		fill(
			columns.F_Bedienung,
			fstrZugRangier,
			[fstrZugRangierAllg?.FBedienung?.wert?.translate]
		)

		// I: Sslr.Abhaengigkeiten.Inselgleis.Bezeichnung
		val raFahrtGleichzeitigVerbot = fstrZugRangier?.fstrFahrweg?.
			zielSignal?.raFahrtGleichzeitigVerbot ?: Collections.emptySet
		fillSwitch(
			columns.InselgleisBezeichnung,
			fstrZugRangier,
			new Case<Fstr_Zug_Rangier>(
				[!raFahrtGleichzeitigVerbot.empty],
				[
					raFahrtGleichzeitigVerbot.map [
						bezeichnung?.bezGleisBezeichnung?.wert
					].getIterableFilling(MIXED_STRING_COMPARATOR)
				]
			),
			new Case<Fstr_Zug_Rangier>(
				[
					#{
						ENUM_RANGIER_GEGENFAHRTAUSSCHLUSS_JA,
						ENUM_RANGIER_GEGENFAHRTAUSSCHLUSS_INSELGLEIS_FREI
					}.contains(fstrRangier?.rangierGegenfahrtausschluss?.wert)
				],
				[
					fstrZugRangier.container.gleisBezeichnung.filter [
						intersects(fstrZugRangier?.fstrFahrweg?.zielSignal)
					].map[bezeichnung?.bezGleisBezeichnung?.wert].
						getIterableFilling(MIXED_STRING_COMPARATOR)
				]
			)
		)

		// J: Sslr.Abhaengigkeiten.Inselgleis.Gegenfahrtausschluss
		fill(
			columns.Gegenfahrtausschluss,
			fstrZugRangier,
			[
				val gegenfahrtausschluss = fstrRangier?.
					rangierGegenfahrtausschluss?.wert
				if (gegenfahrtausschluss === null)
					return null
				switch (gegenfahrtausschluss) {
					case ENUM_RANGIER_GEGENFAHRTAUSSCHLUSS_INSELGLEIS_FREI: return "ZG frei"
					case ENUM_RANGIER_GEGENFAHRTAUSSCHLUSS_JA: return "x"
					case ENUM_RANGIER_GEGENFAHRTAUSSCHLUSS_NEIN: return "o"
				}
			]
		)

		// K: Sslr.Abhaengigkeiten.Gleisfreimeldung
		fillIterable(
			columns.Gleisfreimeldung,
			fstrZugRangier,
			[
				fmaAnlageRangierFrei?.map [
					IDGleisAbschnitt?.bezeichnung?.bezeichnungTabelle?.wert
				].toSet
			],
			MIXED_STRING_COMPARATOR
		)

		// L: Sslr.Abhaengigkeiten.FwWeichen_mit_Fla
		fillIterable(
			columns.FwWeichen_mit_Fla,
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
			columns.Ueberwachte_Ssp,
			fstrZugRangier,
			[
				fstrFahrweg?.abhaengigkeiten.map [
					val ssp = schluesselsperre?.bezeichnung?.
						bezeichnungTabelle?.wert
					val aufloesungZielgleis = fstrAbhaengigkeitSsp?.
						aufloesungSspZielgeis?.wert?.translate
					'''«ssp» («aufloesungZielgleis»)'''
				]
			],
			MIXED_STRING_COMPARATOR
		)

		// N: Sslr.Abhaengigkeiten.Abhaengiger_BUe
		fillIterable(
			columns.Abhaengiger_BUe,
			fstrZugRangier,
			[fstrFahrweg.BUes.map[bezeichnung.bezeichnungTabelle.wert]],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// O: Sslr.Abhaengigkeiten.Ziel_erlaubnisabh
		fill(
			columns.Ziel_erlaubnisabh,
			fstrZugRangier,
			[
				(fstrFahrweg?.zielSignal?.signalFstr?.
					IDRaZielErlaubnisabhaengig?.identitaet?.wert !== null).
					translate
			]
		)

		// P: Sslr.Abhaengigkeiten.Aufloes_Fstr
		fill(
			columns.Aufloes_Fstr,
			fstrZugRangier,
			[
				fstrFahrweg?.start?.signalFstr?.rangierstrasseRestaufloesung?.
					wert?.translate
			]
		)

		// Q: Sslr.Bemerkung
		fill(
			columns.basis_bemerkung,
			fstrZugRangier,
			[
				val bedAnzeigeElemente = fstrFahrweg?.abhaengigkeiten?.map [
					bedienAnzeigeElement
				]?.filterNull ?: Collections.emptyList
				val footnotes = footnoteTransformation.transform(it, row)
				'''«FOR bae : bedAnzeigeElemente.map[comment[translate]].filterNull SEPARATOR ", "»«bae»«ENDFOR» «footnotes»'''.
					toString.trim
			]
		)
		return
	}

	private def boolean getGeneralbedingung(Fstr_Zug_Rangier fstrZugRangier) {
		return fstrZugRangier?.fstrZugRangierAllg?.fstrArt?.wert?.literal?.
			substring(0, 1) == "R"
	}

	override void formatTableContent(Table table) {
		// A: Sslr.Grundsatzangaben.Bezeichnung
		table.setTextAlignment(0, TextAlignment.LEFT);

		// B: Sslr.Grundsatzangaben.Fahrweg.Start
		table.setTextAlignment(1, TextAlignment.LEFT);

		// C: Sslr.Grundsatzangaben.Fahrweg.Ziel
		table.setTextAlignment(2, TextAlignment.LEFT);

		// Q: Sslr.Bemerkung
		table.setTextAlignment(15, TextAlignment.LEFT);
	}
}
