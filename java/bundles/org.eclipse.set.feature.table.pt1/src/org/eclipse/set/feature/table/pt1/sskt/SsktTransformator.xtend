/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sskt

import java.util.Set
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Ansteuerung_Element.TSO_IP_AB_Teilsystem_AttributeGroup
import org.eclipse.set.model.planpro.Ansteuerung_Element.Technik_Standort
import org.eclipse.set.model.planpro.Ansteuerung_Element.Unterbringung
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Bedienung.BSO_IP_AB_Teilsystem_AttributeGroup
import org.eclipse.set.model.planpro.Bedienung.Bedien_Standort
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.utils.table.RowFactory
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin

import static org.eclipse.set.feature.table.pt1.sskt.SsktColumns.*

import static extension org.eclipse.set.ppmodel.extensions.BedienStandortExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TechnikStandortExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UnterbringungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*

/**
 * Table transformation for Sskt.
 * 
 * @author Schaefer
 */
class SsktTransformator extends AbstractPlanPro2TableModelTransformator {

	var TMFactory factory = null

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory, Stell_Bereich controlArea) {
		this.factory = factory
		return container.transformToTable(controlArea)
	}

	private def Table create factory.table transformToTable(
		MultiContainer_AttributeGroup container, Stell_Bereich controlArea) {
		(container.technikStandort + container.bedienStandort).filter [
			isPlanningObject
		].filterObjectsInControlArea(controlArea).filter [
			generalbedingung
		].forEach [
			if (Thread.currentThread.interrupted) {
				return
			}
			transformToRow
		]
		return
	}

	private def dispatch void transformToRow(Basis_Objekt standort) {
		throw new IllegalArgumentException(standort.toString)
	}

	private def dispatch void transformToRow(Technik_Standort standort) {
		val rg = factory.newRowGroup(standort)
		val teilsysteme = standort.TSOIPAdressblock?.TSOIPABTeilsystem
		if (teilsysteme !== null && !teilsysteme.empty) {
			teilsysteme.forEach[transformToRow(rg, standort, it)]
		} else {
			transformToRow(rg, standort, null)
		}
	}

	private def dispatch void transformToRow(Bedien_Standort standort) {

		val rg = factory.newRowGroup(standort)
		val teilsysteme = standort.BSOIPAdressblock?.BSOIPABTeilsystem
		if (teilsysteme !== null && !teilsysteme.empty) {
			teilsysteme.forEach[transformToRow(rg, standort, it)]
		} else {
			transformToRow(rg, standort, null)
		}
	}

	private def TableRow create rg.newTableRow
		transformToRow(RowFactory rg, Technik_Standort standort,
		TSO_IP_AB_Teilsystem_AttributeGroup ts) {

		// A: Sskt.Grundsatzangaben.Bezeichnung
		fill(
			cols.getColumn(Bezeichnung),
			standort,
			[bezeichnung?.bezeichnungTSO?.wert]
		)

		// B: Sskt.Grundsatzangaben.Art
		fill(
			cols.getColumn(Art),
			standort,
			["TSO"]
		)

		// C: Sskt.Grundsatzangaben.Bedien_Standort
		fillIterable(
			cols.getColumn(Bedien_Standort),
			standort,
			[bedienStandort.map[bezeichnung?.bezeichnungBSO?.wert]],
			MIXED_STRING_COMPARATOR
		)

		fillUnterbringung(
			standort,
			standort.unterbringung
		)

		// H: Sskt.IP_Adressangaben.Regionalbereich
		fill(
			cols.getColumn(IP_Regionalbereich),
			standort,
			[TSOIPAdressblock?.regionalbereich?.translate]
		)

		// I: Sskt.IP_Adressangaben.Adressblock_Blau.IPv4_Blau
		fill(
			cols.getColumn(IPv4_Blau),
			standort,
			[TSOIPAdressblock?.IPAdressblockBlauV4?.wert]
		)

		// J: Sskt.IP_Adressangaben.Adressblock_Blau.IPv6_Blau
		fill(
			cols.getColumn(IPv6_Blau),
			standort,
			[TSOIPAdressblock?.IPAdressblockBlauV6?.wert]
		)

		// K: Sskt.IP_Adressangaben.Adressblock_Grau.IPv4_Grau
		fill(
			cols.getColumn(IPv4_Grau),
			standort,
			[TSOIPAdressblock?.IPAdressblockGrauV4?.wert]
		)

		// L: Sskt.IP_Adressangaben.Adressblock_Grau.IPv6_Grau
		fill(
			cols.getColumn(IPv6_Grau),
			standort,
			[TSOIPAdressblock?.IPAdressblockGrauV6?.wert]
		)

		// M: Sskt.IP_Adressangaben.Teilsystem.Art
		fill(
			cols.getColumn(IP_Teilsystem_Art),
			standort,
			[ts?.TSOTeilsystemArt?.translate]
		)

		// N: Sskt.IP_Adressangaben.Teilsystem.TS_Blau
		fill(
			cols.getColumn(Teilsystem_TS_Blau),
			standort,
			[ts?.IPAdressblockBlau?.wert]
		)

		// O: Sskt.IP_Adressangaben.Teilsystem.TS_Grau
		fill(
			cols.getColumn(Teilsystem_TS_Grau),
			standort,
			[
				ts?.IPAdressblockGrau?.wert
			]
		)

		// P: Sskt.Bemerkung
		fillFootnotes(standort)
	}

	private def TableRow create rg.newTableRow transformToRow(RowFactory rg,
		Bedien_Standort standort, BSO_IP_AB_Teilsystem_AttributeGroup bs) {

		// A: Sskt.Grundsatzangaben.Bezeichnung
		fill(
			cols.getColumn(Bezeichnung),
			standort,
			[bezeichnung?.bezeichnungBSO?.wert]
		)

		// B: Sskt.Grundsatzangaben.Art
		fill(
			cols.getColumn(Art),
			standort,
			["BSO"]
		)

		// C: Sskt.Grundsatzangaben.Bedien_Standort
		fill(
			cols.getColumn(Bedien_Standort),
			standort,
			[""]
		)

		fillUnterbringung(
			standort,
			standort.unterbringung
		)

		// H: Sskt.IP_Adressangaben.Regionalbereich
		fill(
			cols.getColumn(IP_Regionalbereich),
			standort,
			[BSOIPAdressblock?.regionalbereich?.translate]
		)

		// I: Sskt.IP_Adressangaben.Adressblock_Blau.IPv4_Blau
		fill(
			cols.getColumn(IPv4_Blau),
			standort,
			[BSOIPAdressblock?.IPAdressblockBlauV4?.wert]
		)

		// J: Sskt.IP_Adressangaben.Adressblock_Blau.IPv6_Blau
		fill(
			cols.getColumn(IPv6_Blau),
			standort,
			[BSOIPAdressblock?.IPAdressblockBlauV6?.wert]
		)

		// K: Sskt.IP_Adressangaben.Adressblock_Grau.IPv4_Grau
		fill(
			cols.getColumn(IPv4_Grau),
			standort,
			[BSOIPAdressblock?.IPAdressblockGrauV4?.wert]
		)

		// L: Sskt.IP_Adressangaben.Adressblock_Grau.IPv6_Grau
		fill(
			cols.getColumn(IPv6_Grau),
			standort,
			[BSOIPAdressblock?.IPAdressblockGrauV6?.wert]
		)

		// M: Sskt.IP_Adressangaben.Teilsystem.Art
		fill(
			cols.getColumn(IP_Teilsystem_Art),
			standort,
			[bs?.BSOTeilsystemArt?.translate]
		)

		// N: Sskt.IP_Adressangaben.Teilsystem.TS_Blau
		fill(
			cols.getColumn(Teilsystem_TS_Blau),
			standort,
			[bs?.IPAdressblockBlau?.wert]
		)

		// O: Sskt.IP_Adressangaben.Teilsystem.TS_Grau
		fill(
			cols.getColumn(Teilsystem_TS_Grau),
			standort,
			[bs?.IPAdressblockGrau?.wert]
		)

		// P: Sskt.Bemerkung
		fillFootnotes(standort)

		return
	}

	private def void fillUnterbringung(
		TableRow row,
		Basis_Objekt standort,
		Unterbringung unterbringung
	) {
		// D: Sskt.Grundsatzangaben.Unterbringung.Art
		fill(
			row,
			cols.getColumn(Unterbringung_Art),
			standort,
			[
				unterbringung?.unterbringungAllg?.unterbringungArt?.
					translate
			]
		)

		// E: Sskt.Grundsatzangaben.Unterbringung.Ort
		fill(
			row,
			cols.getColumn(Unterbringung_Ort),
			standort,
			[unterbringung?.standortBeschreibung?.wert]
		)

		// F: Sskt.Grundsatzangaben.Unterbringung.Strecke
		fillIterable(
			row,
			cols.getColumn(Unterbringung_Strecke),
			standort,
			[unterbringung?.strecken?.map[bezeichnung?.bezeichnungStrecke?.wert]],
			MIXED_STRING_COMPARATOR
		)

		// G: Sskt.Grundsatzangaben.Unterbringung.km
		fillIterable(
			row,
			cols.getColumn(Unterbringung_km),
			standort,
			[unterbringung?.punktObjektStrecke?.map[streckeKm?.wert]],
			MIXED_STRING_COMPARATOR
		)
	}

	private def boolean getGeneralbedingung(Basis_Objekt standort) {
		return true
	}
}
