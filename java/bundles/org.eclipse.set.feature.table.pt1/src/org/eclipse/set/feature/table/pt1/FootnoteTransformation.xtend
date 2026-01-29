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
import org.eclipse.set.model.planpro.Basisobjekte.ENUMObjektzustandBesonders

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
		object?.IDBearbeitungsvermerk?.map[value].forEach[addFootnote]
		val refFootnote = object?.refObjectFootnotes
		val directState = object.basisObjektAllg?.objektzustandBesonders?.wert
		
		object?.refObjectFootnotes?.filter(ID_Bearbeitungsvermerk_TypeClass).map [
			value
		]?.toSet?.forEach[addFootnote]
	}

	private def dispatch Iterable<?> getRefObjectFootnotes(Basis_Objekt object) {
		return #[]
	}

	// Determine Footnotes for Ssks Table
	private def dispatch Iterable<?> getRefObjectFootnotes(Signal signal) {
		val signalRahmenFootNotes = signal?.signalRahmen?.map [
			refObjectFootnotes
		].flatten
		val signalBefestigungFootNotes = signal?.signalRahmen?.map [
			signalBefestigung
		].filterNull.map [
			refObjectFootnotes
		].flatten

		// Strecke & Km footnotes
		val posFootNotes = signal?.punktObjektStrecke.map[refObjectFootnotes].
			flatten

		return #[signalRahmenFootNotes, signalBefestigungFootNotes,
			posFootNotes].filterNull.flatten
	}

	// Determine Footnotes for Ssks Table
	private def dispatch Iterable<?> getRefObjectFootnotes(
		Signal_Befestigung signalBefestigung) {
		if (signalBefestigung === null) {
			return #[]
		}
		return signalBefestigung.signalBefestigungen.filter [
			IDBearbeitungsvermerk !== null
		].flatMap[IDBearbeitungsvermerk]
	}

	// Determine Footnotes for Ssks Table
	private def dispatch Iterable<?> getRefObjectFootnotes(
		Signal_Rahmen signalRahmen) {
		val rahmenFootnotes = signalRahmen.IDBearbeitungsvermerk.filterNull
		val signalBegriffFootntoes = signalRahmen.signalbegriffe.flatMap [
			IDBearbeitungsvermerk
		].filterNull
		return #[rahmenFootnotes, signalBegriffFootntoes].flatten
	}

	// Determine Footnotes for Sskw Table
	private def dispatch Iterable<?> getRefObjectFootnotes(
		W_Kr_Gsp_Element gspElement) {
		val gspKomponentFootNotes = gspElement?.WKrGspKomponenten?.flatMap [
			IDBearbeitungsvermerk
		]
		val gspAnlageFootNotes = gspElement?.WKrAnlage?.IDBearbeitungsvermerk
		return #[gspKomponentFootNotes, gspAnlageFootNotes].filterNull.flatten
	}

	// Determine Footnotes for Ssbb & Ssit Table
	private def dispatch Iterable<?> getRefObjectFootnotes(
		Bedien_Einrichtung_Oertlich einrichtung) {
		val routeKmFootnotes = einrichtung.unterbringung?.punktObjektStrecke?.
			map [
				refObjectFootnotes
			].flatten ?: #[]
		return #[routeKmFootnotes].flatten
	}

	// Determine Footnotes for Sska Table
	private def dispatch Iterable<?> getRefObjectFootnotes(
		Aussenelementansteuerung element) {
		val routeKmFootnotes = element.unterbringung?.punktObjektStrecke?.map [
			refObjectFootnotes
		].flatten ?: #[]
		return #[routeKmFootnotes].flatten
	}

	// Determine Footnotes for Sska Table
	private def dispatch Iterable<?> getRefObjectFootnotes(
		ESTW_Zentraleinheit element) {
		val routeKmFootnotes = element.unterbringung?.punktObjektStrecke?.map [
			refObjectFootnotes
		].flatten ?: #[]
		return #[routeKmFootnotes].flatten
	}

	// Determine Footnotes for Ssko Table
	private def dispatch Iterable<?> getRefObjectFootnotes(
		Schloss schloss
	) {
		val result = newArrayList()
		if (schloss.schlossSk !== null) {
			result.addAll(
				schloss.schlossKombination?.unterbringung?.punktObjektStrecke?.
					map[refObjectFootnotes].flatten ?: [])
		}

		if (schloss.schlossSsp !== null) {
			result.addAll(
				schloss.schluesselsperre?.unterbringung?.punktObjektStrecke?.map [
					refObjectFootnotes
				].flatten ?: [])
		}
		return result
	}

	// Determine Footnotes for Sskt table
	private def dispatch Iterable<?> getRefObjectFootnotes(
		Technik_Standort element) {
		val routeKmFootnotes = element.unterbringung?.punktObjektStrecke?.map [
			refObjectFootnotes
		].flatten ?: #[]
		return #[routeKmFootnotes].flatten
	}

	// Determine Footnotes for Sskt table
	private def dispatch Iterable<?> getRefObjectFootnotes(
		Bedien_Standort element) {
		val routeKmFootnotes = element.unterbringung?.punktObjektStrecke?.map [
			refObjectFootnotes
		].flatten ?: #[]
		return #[routeKmFootnotes].flatten
	}

	// Determine Footnotes for Sszs table
	private def dispatch Iterable<?> getRefObjectFootnotes(ETCS_Signal element) {
		val routeKmFootnotes = element?.IDSignal?.value?.punktObjektStrecke?.map [
			refObjectFootnotes
		].flatten ?: #[]
		return #[routeKmFootnotes].flatten
	}

	// Determine Footnotes for Sszw table
	private def dispatch Iterable<?> getRefObjectFootnotes(ETCS_W_Kr element) {
		val routeKmFootnotes = switch (element.IDWKrAnlage?.value?.WKrAnlageArt) {
			case ENUMW_KR_ART_EW,
			case ENUMW_KR_ART_IBW,
			case ENUMW_KR_ART_ABW,
			case ENUMW_KR_ART_DW,
			case ENUMW_KR_ART_KLOTHOIDENWEICHE,
			case ENUMW_KR_ART_KORBBOGENWEICHE: {
				val gspKomponent = element.WKrGspKomponents.firstOrNull
				return gspKomponent.objectFootnotes ?: #[]
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
		return #[routeKmFootnotes].flatten
	}

	// Determine Footnotes for Sskg, Ssza table
	private def dispatch Iterable<?> getRefObjectFootnotes(Punkt_Objekt po) {
		val routeKmFootnotes = po?.punktObjektStrecke?.map[refObjectFootnotes].
			flatten ?: #[]
		return #[routeKmFootnotes].flatten
	}

	private def dispatch Iterable<?> getRefObjectFootnotes(
		Punkt_Objekt_Strecke_AttributeGroup pos) {
		return pos?.streckeKm?.IDBearbeitungsvermerk ?: #[]
	}

	private def dispatch void addFootnote(Bearbeitungsvermerk comment) {
		if (row.footnotes === null)
			row.footnotes = TablemodelFactory.eINSTANCE.
				createSimpleFootnoteContainer()
		(row.footnotes as SimpleFootnoteContainer).footnotes.add(comment)
	}
	
	private def dispatch void addFootnote(ENUMObjektzustandBesonders objState) {
		if (row.footnotes === null)
			row.footnotes = TablemodelFactory.eINSTANCE.
				createSimpleFootnoteContainer()
	}
}
