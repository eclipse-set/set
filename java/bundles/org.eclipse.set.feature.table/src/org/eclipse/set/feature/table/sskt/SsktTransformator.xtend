/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sskt

import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Technik_Standort
import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Unterbringung
import org.eclipse.set.toolboxmodel.Basisobjekte.Basis_Objekt
import org.eclipse.set.toolboxmodel.Bedienung.Bedien_Standort
import java.util.Collections
import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.utils.table.TMFactory
import org.eclipse.set.feature.table.messages.MessagesWrapper
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.format.TextAlignment
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienStandortExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TechnikStandortExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UnterbringungExtensions.*

/**
 * Table transformation for Sskt.
 * 
 * @author Schaefer
 */
class SsktTransformator extends AbstractPlanPro2TableModelTransformator {

	val SsktColumns columns
	var TMFactory factory = null

	new(SsktColumns columns, MessagesWrapper messagesWrapper) {
		super(messagesWrapper)
		this.columns = columns;
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		this.factory = factory
		return container.transformToTable
	}

	private def Table create factory.table transformToTable(
		MultiContainer_AttributeGroup container) {
		(container.technikStandort + container.bedienStandort).filter [
			generalbedingung
		].forEach[it|
			if (Thread.currentThread.interrupted) {
				return
			}
			it.transformToRow
		]
		return
	}

	private def dispatch TableRow create factory.newTableRow(standort) transformToRow(
		Basis_Objekt standort) {
		throw new IllegalArgumentException(standort.toString)
	}

	private def dispatch TableRow create factory.newTableRow(standort) transformToRow(
		Technik_Standort standort) {

		fill(
			columns.Bezeichnung,
			standort,
			[bezeichnung?.bezeichnungTSO?.wert]
		)

		fill(
			columns.Grundsatzangaben_Art,
			standort,
			["TSO"]
		)

		fillIterable(
			columns.Bedien_Standort,
			standort,
			[bedienStandort.map[bezeichnung?.bezeichnungBSO?.wert]],
			MIXED_STRING_COMPARATOR
		)

		fillUnterbringung(
			standort,
			standort.unterbringung
		)

		fill(
			columns.Unterbringung_Art,
			standort,
			[unterbringung?.unterbringungAllg?.unterbringungArt?.wert?.translate]
		)

		fill(
			columns.Regionalbereich,
			standort,
			[TSOIPAdressblock?.regionalbereich?.wert?.translate]
		)

		fill(
			columns.IPv4_Blau,
			standort,
			[TSOIPAdressblock?.IPAdressblockBlauV4?.wert]
		)

		fill(
			columns.IPv6_Blau,
			standort,
			[TSOIPAdressblock?.IPAdressblockBlauV6?.wert]
		)

		fill(
			columns.IPv4_Grau,
			standort,
			[TSOIPAdressblock?.IPAdressblockGrauV4?.wert]
		)

		fill(
			columns.IPv6_Grau,
			standort,
			[TSOIPAdressblock?.IPAdressblockGrauV6?.wert]
		)

		fillIterable(
			columns.Teilsystem_Art,
			standort,
			[
				TSOIPAdressblock?.TSOIPABTeilsystem?.map [
					TSOTeilsystemArt?.wert?.translate
				] ?: Collections.emptyList
			],
			MIXED_STRING_COMPARATOR
		)

		fillIterable(
			columns.TS_Blau,
			standort,
			[
				TSOIPAdressblock?.TSOIPABTeilsystem?.map [
					IPAdressblockBlau?.wert
				] ?: Collections.emptyList
			],
			MIXED_STRING_COMPARATOR
		)

		fillIterable(
			columns.TS_Grau,
			standort,
			[
				TSOIPAdressblock?.TSOIPABTeilsystem?.map [
					IPAdressblockGrau?.wert
				] ?: Collections.emptyList
			],
			MIXED_STRING_COMPARATOR
		)

		val row = it
		fill(
			columns.basis_bemerkung,
			standort,
			[footnoteTransformation.transform(it, row)]
		)

		return
	}

	private def dispatch TableRow create factory.newTableRow(standort) transformToRow(
		Bedien_Standort standort) {

		fill(
			columns.Bezeichnung,
			standort,
			[bezeichnung?.bezeichnungBSO?.wert]
		)

		fill(
			columns.Grundsatzangaben_Art,
			standort,
			["BSO"]
		)

		fill(
			columns.Bedien_Standort,
			standort,
			[""]
		)

		fillUnterbringung(
			standort,
			standort.unterbringung
		)

		fill(
			columns.Regionalbereich,
			standort,
			[BSOIPAdressblock?.regionalbereich?.wert?.translate]
		)

		fill(
			columns.IPv4_Blau,
			standort,
			[BSOIPAdressblock?.IPAdressblockBlauV4?.wert]
		)

		fill(
			columns.IPv6_Blau,
			standort,
			[BSOIPAdressblock?.IPAdressblockBlauV6?.wert]
		)

		fill(
			columns.IPv4_Grau,
			standort,
			[BSOIPAdressblock?.IPAdressblockGrauV4?.wert]
		)

		fill(
			columns.IPv6_Grau,
			standort,
			[BSOIPAdressblock?.IPAdressblockGrauV6?.wert]
		)

		fillIterable(
			columns.Teilsystem_Art,
			standort,
			[
				BSOIPAdressblock?.BSOIPABTeilsystem?.map [
					BSOTeilsystemArt?.wert?.translate
				] ?: Collections.emptyList
			],
			MIXED_STRING_COMPARATOR
		)

		fillIterable(
			columns.TS_Blau,
			standort,
			[
				BSOIPAdressblock?.BSOIPABTeilsystem?.map [
					IPAdressblockBlau?.wert
				] ?: Collections.emptyList
			],
			MIXED_STRING_COMPARATOR
		)

		fillIterable(
			columns.TS_Grau,
			standort,
			[
				BSOIPAdressblock?.BSOIPABTeilsystem?.map [
					IPAdressblockGrau?.wert
				] ?: Collections.emptyList
			],
			MIXED_STRING_COMPARATOR
		)

		val row = it
		fill(
			columns.basis_bemerkung,
			standort,
			[footnoteTransformation.transform(it, row)]
		)

		return
	}

	private def void fillUnterbringung(
		TableRow row,
		Basis_Objekt standort,
		Unterbringung unterbringung
	) {
		fill(
			row,
			columns.Unterbringung_Art,
			standort,
			[unterbringung?.unterbringungAllg?.unterbringungArt?.wert?.translate]
		)

		fill(
			row,
			columns.Ort,
			standort,
			[unterbringung?.standortBeschreibung?.wert]
		)

		fillIterable(
			row,
			columns.Strecke,
			standort,
			[unterbringung?.strecken?.map[bezeichnung?.bezeichnungStrecke?.wert]],
			MIXED_STRING_COMPARATOR
		)

		fillIterable(
			row,
			columns.km,
			standort,
			[unterbringung?.punktObjektStrecke?.map[streckeKm?.wert]],
			MIXED_STRING_COMPARATOR
		)
	}

	private def boolean getGeneralbedingung(Basis_Objekt standort) {
		return true
	}

	override void formatTableContent(Table table) {
		// IMPROVE: Use column descriptor instead of index
		
		// A: Sskt.Grundsatzangaben.Bezeichnung
		table.setTextAlignment(0, TextAlignment.LEFT);

		// G: Sskt.Grundsatzangaben.Unterbringung.km
		table.setTextAlignment(6, TextAlignment.RIGHT);

		// U: Sskt.Bemerkung
		table.setTextAlignment(15, TextAlignment.LEFT);
	}
}
