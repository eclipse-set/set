/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1

import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit
import org.eclipse.set.model.planpro.Ansteuerung_Element.Technik_Standort
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_Signal
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_W_Kr
import org.eclipse.set.model.planpro.BasisTypen.ID_Bearbeitungsvermerk_TypeClass
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_Strecke_AttributeGroup
import org.eclipse.set.model.planpro.Bedienung.Bedien_Einrichtung_Oertlich
import org.eclipse.set.model.planpro.Bedienung.Bedien_Standort
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schloss
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung
import org.eclipse.set.model.planpro.Signale.Signal_Rahmen
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.TablemodelFactory

import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienEinrichtungOertlichExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienStandortExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ESTW_ZentraleinheitExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ETCSWKrExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SchlossExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SchlosskombinationExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SchluesselsperreExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalBefestigungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalRahmenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TechnikStandortExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*
import static extension org.eclipse.set.model.tablemodel.extensions.FootnoteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.model.planpro.Ansteuerung_Element.Unterbringung

/**
 * Transform basis objects to footnotes.
 * 
 * @author Schaefer
 */
class FootnoteTransformation {

	var TableRow row

	/**
	 * @param row the table row
	 */
	def void transform(Basis_Objekt object, TableRow row) {
		this.row = row
		// Direct attachment notes
		object?.IDBearbeitungsvermerk?.map[value]?.toSet?.forEach[addFootnote]
		object?.referenceFootnotes?.map[value]?.toSet?.forEach[addFootnote]

		object.transformObjectStateEnum(
			object?.basisObjektAllg?.objektzustandBesonders?.wert)?.value?.
			addFootnote
	}

	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		Basis_Objekt obj) {
		return #[]
	}

	// Determine Footnotes for Ssks Table
	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		Signal signal) {
		val signalRahmenFootNotes = signal?.signalRahmen?.flatMap [
			referenceFootnotes
		]
		val signalBefestigungFootNotes = signal?.signalRahmen?.map [
			signalBefestigung
		]?.filterNull?.flatMap [
			referenceFootnotes
		]

		// Strecke & Km footnotes
		val posFootNotes = signal?.punktObjektStrecke?.flatMap [
			referenceFootnotes
		]

		return #[signalRahmenFootNotes, signalBefestigungFootNotes,
			posFootNotes].filterNull.flatten
	}

	// Determine Footnotes for Ssks Table
	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		Signal_Befestigung signalBefestigung) {
		if (signalBefestigung === null) {
			return #[]
		}
		return signalBefestigung?.signalBefestigungen?.filter [
			IDBearbeitungsvermerk !== null
		]?.flatMap [
			val notes = IDBearbeitungsvermerk
			val objectStateNote = #[
				signalBefestigung?.transformObjectStateEnum(
					basisObjektAllg?.objektzustandBesonders?.wert)
			]
			return #[notes, objectStateNote].flatten
		] ?: #[]
	}

	// Determine Footnotes for Ssks Table
	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		Signal_Rahmen signalRahmen) {
		val rahmenFootnotes = signalRahmen?.IDBearbeitungsvermerk?.filterNull
		val objectStateNote = #[signalRahmen?.transformObjectStateEnum(
			signalRahmen?.basisObjektAllg?.objektzustandBesonders?.wert)]
		val signalBegriffFootntoes = signalRahmen?.signalbegriffe?.flatMap [
			IDBearbeitungsvermerk
		]?.filterNull
		return #[rahmenFootnotes, objectStateNote, signalBegriffFootntoes].
			filterNull.flatten
	}

	// Determine Footnotes for Sskw Table
	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		W_Kr_Gsp_Element gspElement) {
		val gspKomonenten = gspElement?.WKrGspKomponenten
		val gspKomponentFootNotes = gspKomonenten?.flatMap [
			IDBearbeitungsvermerk
		]
		val gspKomponentObjStates = gspKomonenten?.map [
			transformObjectStateEnum(
				basisObjektAllg?.objektzustandBesonders?.wert)
		]

		val gspAnlage = gspElement?.WKrAnlage
		val gspAnlageFootNotes = gspAnlage?.IDBearbeitungsvermerk
		val gspAnlageObjStates = #[
			gspAnlage.transformObjectStateEnum(
				gspAnlage?.basisObjektAllg?.objektzustandBesonders?.wert)
		]

		return #[gspKomponentFootNotes, gspKomponentObjStates,
			gspAnlageFootNotes, gspAnlageObjStates].filterNull.flatten
	}

	// Determine Footnotes for Ssbb & Ssit Table
	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		Bedien_Einrichtung_Oertlich einrichtung) {
		return einrichtung?.unterbringung?.referenceFootnotes ?: #[]
	}

	// Determine Footnotes for Sska Table
	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		Aussenelementansteuerung element) {
		return element?.unterbringung?.referenceFootnotes ?: #[]
	}

	// Determine Footnotes for Sska Table
	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		ESTW_Zentraleinheit element) {
		return element?.unterbringung?.referenceFootnotes ?: #[]
	}

	// Determine Footnotes for Ssko Table
	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		Schloss schloss
	) {
		if (schloss?.schlossSk !== null) {
			return schloss?.schlossKombination?.unterbringung?.
				referenceFootnotes ?: #[]
		}

		if (schloss?.schlossSsp !== null) {
			return schloss?.schluesselsperre?.unterbringung?.
				referenceFootnotes ?: #[]
		}
		return #[]
	}

	// Determine Footnotes for Sskt table
	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		Technik_Standort element) {
		return element?.unterbringung?.referenceFootnotes ?: #[]
	}

	// Determine Footnotes for Sskt table
	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		Bedien_Standort element) {
		return element?.unterbringung?.referenceFootnotes ?: #[]
	}

	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		Unterbringung obj) {
		return obj?.punktObjektStrecke?.flatMap[referenceFootnotes] ?: #[]
	}

	// Determine Footnotes for Sszs table
	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		ETCS_Signal element) {
		return element?.IDSignal?.value?.punktObjektStrecke?.flatMap [
			referenceFootnotes
		] ?: #[]
	}

	// Determine Footnotes for Sszw table
	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		ETCS_W_Kr element) {
		return switch (element?.IDWKrAnlage?.value?.WKrAnlageArt) {
			case ENUMW_KR_ART_EW,
			case ENUMW_KR_ART_IBW,
			case ENUMW_KR_ART_ABW,
			case ENUMW_KR_ART_DW,
			case ENUMW_KR_ART_KLOTHOIDENWEICHE,
			case ENUMW_KR_ART_KORBBOGENWEICHE: {
				val gspKomponent = element.WKrGspKomponents.firstOrNull
				return gspKomponent.referenceFootnotes ?: #[]
			}
			case ENUMW_KR_ART_DKW,
			case ENUMW_KR_ART_EKW,
			case ENUMW_KR_ART_FLACHKREUZUNG,
			case ENUMW_KR_ART_KR: {
				// TODO: not clear how to get Km footnotes for this
				return #[]
			}
			default:
				#[]
		}
	}

	// Determine Footnotes for Sskf
	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		FMA_Anlage fmaAnlage) {
		return fmaAnlage?.schaltmittelZuordnungen?.flatMap [
			IDBearbeitungsvermerk
		] ?: #[]
	}

	// Determine Footnotes for Sslz
	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		Fstr_Zug_Rangier fstrZugRangier) {
		if (!isZ(fstrZugRangier)) {
			return #[]
		}
		return fstrZugRangier.fstrFahrweg?.start?.zweitesHaltfallkriterium?.
			IDBearbeitungsvermerk ?: #[]
	}

	// Determine Footnotes for Sskg, Ssza table
	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		Punkt_Objekt po) {
		return po?.punktObjektStrecke?.flatMap [
			referenceFootnotes
		] ?: #[]
	}

	private def dispatch Iterable<ID_Bearbeitungsvermerk_TypeClass> getReferenceFootnotes(
		Punkt_Objekt_Strecke_AttributeGroup pos) {
		val routeNotes = pos?.IDStrecke?.IDBearbeitungsvermerk
		val kmNotes = pos?.streckeKm?.IDBearbeitungsvermerk
		return #[routeNotes, kmNotes].filterNull.flatten
	}

	private def void addFootnote(Bearbeitungsvermerk comment) {
		if (comment === null) {
			return
		}
		if (row.footnotes === null)
			row.footnotes = TablemodelFactory.eINSTANCE.
				createSimpleFootnoteContainer()
		(row.footnotes as SimpleFootnoteContainer).footnotes.add(comment)
	}
}
