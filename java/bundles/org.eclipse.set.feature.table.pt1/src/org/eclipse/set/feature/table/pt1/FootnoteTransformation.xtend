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
import org.eclipse.set.model.planpro.Ortung.Schaltmittel_Zuordnung
import org.eclipse.set.model.planpro.Signale.Signal_Signalbegriff
import org.eclipse.set.model.tablemodel.Footnote
import org.eclipse.emf.ecore.EObject

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
		object?.IDBearbeitungsvermerk?.filterNull?.map[value]?.toSet?.map [
			createFootnote(object)
		]?.forEach[addFootnote]
		object?.transformObjectStateEnum?.createFootnote(object)?.addFootnote
		object?.referenceFootnotes?.forEach[addFootnote]
	}

	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		Basis_Objekt obj) {
		return #[]
	}

	// Determine Footnotes for Ssks Table
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
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
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		Signal_Befestigung signalBefestigung) {
		if (signalBefestigung === null) {
			return #[]
		}
		return signalBefestigung?.signalBefestigungen?.flatMap [ sb |
			val notes = sb?.IDBearbeitungsvermerk?.map [
				createFootnote(sb)
			] ?: #[]
			val objectStateNote = #[
				sb?.transformObjectStateEnum?.createFootnote(sb)
			].filterNull
			val footnotes = #[notes, objectStateNote].flatten

			return footnotes.toList.withPrefix(sb.prefix)
		] ?: #[]
	}

	// Determine Footnotes for Ssks Table
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		Signal_Rahmen signalRahmen) {
		val rahmenFootnotes = signalRahmen?.IDBearbeitungsvermerk?.map [
			createFootnote(signalRahmen)
		]?.filterNull
		val objectStateNote = #[
			signalRahmen?.transformObjectStateEnum?.
				createFootnote(signalRahmen)]?.filterNull
		val signalRahmenFootnotes = #[rahmenFootnotes, objectStateNote].
			filterNull.flatten.toList.withPrefix(signalRahmen.prefix)

		val signalBegriffFootnotes = signalRahmen?.signalbegriffe?.flatMap [
			referenceFootnotes
		]
		return #[signalRahmenFootnotes, signalBegriffFootnotes].filterNull.
			flatten
	}

	// Determine Footnotes for Ssks Table
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		Signal_Signalbegriff signalBegriff) {
		val signalBegriffFootnotes = signalBegriff?.IDBearbeitungsvermerk?.map [
			createFootnote(signalBegriff)
		]?.filterNull
		val objectStateNote = #[
			signalBegriff?.transformObjectStateEnum?.
				createFootnote(signalBegriff)].filterNull
		return #[signalBegriffFootnotes, objectStateNote].filterNull.flatten.
			toList.withPrefix(signalBegriff.prefix)
	}

	// Determine Footnotes for Sskw Table
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		W_Kr_Gsp_Element gspElement) {
		val gspKomonenten = gspElement?.WKrGspKomponenten
		val gspKomponentFootNotes = gspKomonenten?.flatMap [ gspKomponente |
			gspKomponente.IDBearbeitungsvermerk.map [
				createFootnote(gspKomponente)
			]
		]
		val gspKomponentObjStates = gspKomonenten?.map [
			transformObjectStateEnum?.createFootnote(it)
		].filterNull

		val gspAnlage = gspElement?.WKrAnlage
		val gspAnlageFootNotes = gspAnlage?.IDBearbeitungsvermerk?.map [
			createFootnote(gspAnlage)
		]
		val gspAnlageObjStates = #[
			gspAnlage?.transformObjectStateEnum?.createFootnote(gspAnlage)
		].filterNull

		return #[gspKomponentFootNotes, gspKomponentObjStates,
			gspAnlageFootNotes, gspAnlageObjStates].filterNull.flatten
	}

	// Determine Footnotes for Ssbb & Ssit Table
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		Bedien_Einrichtung_Oertlich einrichtung) {
		return einrichtung?.unterbringung?.referenceFootnotes ?: #[]
	}

	// Determine Footnotes for Sska Table
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		Aussenelementansteuerung element) {
		return element?.unterbringung?.referenceFootnotes ?: #[]
	}

	// Determine Footnotes for Sska Table
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		ESTW_Zentraleinheit element) {
		return element?.unterbringung?.referenceFootnotes ?: #[]
	}

	// Determine Footnotes for Ssko Table
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		Schloss schloss
	) {
		if (schloss?.schlossSk !== null) {
			val schlossKombination = schloss?.schlossKombination
			val objStateNote = #[
				schlossKombination?.transformObjectStateEnum?.createFootnote(
					schlossKombination)].filterNull
			return #[objStateNote, schlossKombination?.unterbringung?.
				referenceFootnotes].filterNull.flatten
		}

		if (schloss?.schlossSsp !== null) {
			val schluesselSperre = schloss?.schluesselsperre
			val objStateNote = #[
				schluesselSperre?.transformObjectStateEnum?.createFootnote(
					schluesselSperre)].filterNull
			return #[objStateNote, schluesselSperre?.unterbringung?.
				referenceFootnotes].filterNull.flatten
		}
		return #[]
	}

	// Determine Footnotes for Sskt table
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		Technik_Standort element) {
		return element?.unterbringung?.referenceFootnotes ?: #[]
	}

	// Determine Footnotes for Sskt table
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		Bedien_Standort element) {
		return element?.unterbringung?.referenceFootnotes ?: #[]
	}

	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		Unterbringung obj) {
		val objStateNote = #[obj?.transformObjectStateEnum?.
			createFootnote(obj)].filterNull
		return #[objStateNote, obj?.punktObjektStrecke?.flatMap [
			referenceFootnotes
		]].filterNull.flatten
	}

	// Determine Footnotes for Sszs table
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		ETCS_Signal element) {
		return element?.IDSignal?.value?.punktObjektStrecke?.flatMap [
			referenceFootnotes
		] ?: #[]
	}

	// Determine Footnotes for Sszw table
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		ETCS_W_Kr element) {
		return switch (element?.IDWKrAnlage?.value?.WKrAnlageArt) {
			case ENUMW_KR_ART_EW,
			case ENUMW_KR_ART_IBW,
			case ENUMW_KR_ART_ABW,
			case ENUMW_KR_ART_DW,
			case ENUMW_KR_ART_KLOTHOIDENWEICHE,
			case ENUMW_KR_ART_KORBBOGENWEICHE: {
				val gspKomponent = element?.WKrGspKomponents?.firstOrNull
				val objStateNote = #[
					gspKomponent?.transformObjectStateEnum?.
						createFootnote(gspKomponent)]
				return #[objStateNote, gspKomponent?.referenceFootnotes].
					filterNull.flatten
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
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		FMA_Anlage fmaAnlage) {
		return fmaAnlage?.schaltmittelZuordnungen?.flatMap [
			referenceFootnotes
		] ?: #[]
	}

	// Determine Footnotes for Sslz
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		Fstr_Zug_Rangier fstrZugRangier) {
		if (!isZ(fstrZugRangier)) {
			return #[]
		}
		return fstrZugRangier?.fstrFahrweg?.start?.zweitesHaltfallkriterium?.
			referenceFootnotes ?: #[]
	}

	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		Schaltmittel_Zuordnung obj) {
		val objStateNote = #[obj?.transformObjectStateEnum].filterNull
		return #[objStateNote, obj?.IDBearbeitungsvermerk].filterNull.flatten.
			map[createFootnote(obj)]
	}

	// Determine Footnotes for Sskg, Ssza table
	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		Punkt_Objekt po) {
		return po?.punktObjektStrecke?.flatMap [
			referenceFootnotes
		] ?: #[]
	}

	private def dispatch Iterable<Footnote> getReferenceFootnotes(
		Punkt_Objekt_Strecke_AttributeGroup pos) {
		val routeNotes = pos?.IDStrecke?.IDBearbeitungsvermerk?.map [
			createFootnote(pos.IDStrecke)
		] ?: #[]
		val kmNotes = pos?.streckeKm?.IDBearbeitungsvermerk?.map [
			createFootnote(pos.streckeKm)
		] ?: #[]
		return #[routeNotes, kmNotes].flatten.filterNull
	}

	private def void addFootnote(Footnote footnote) {
		if (footnote === null) {
			return
		}
		if (row.footnotes === null) {
			row.footnotes = TablemodelFactory.eINSTANCE.
				createSimpleFootnoteContainer()
		}
		(row.footnotes as SimpleFootnoteContainer).footnotes.add(footnote)
	}

	private def createFootnote(ID_Bearbeitungsvermerk_TypeClass bv,
		EObject obj) {
		return bv?.value?.createFootnote(obj)
	}

	private def createFootnote(Bearbeitungsvermerk bv, EObject obj) {
		if (bv === null) {
			return null
		}
		val footnote = TablemodelFactory.eINSTANCE.createFootnote()
		footnote.ownerObject = obj
		footnote.footnote = (bv)

		return footnote
	}

}
