/**
 * Copyright (c) 2024 DB InfraGO AG and others
 * 
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.pt1.ssza

import java.util.Set
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.core.services.graph.TopologicalGraphService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Anlage
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Einschaltung
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Kante
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.DP_Typ_GETCS_AttributeGroup
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.Datenpunkt
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ZUB_Streckeneigenschaft
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Fahrstrasse.Markanter_Punkt
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente
import org.eclipse.set.model.planpro.Ortung.Zugeinwirkung
import org.eclipse.set.model.planpro.PZB.PZB_Element
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.utils.table.RowFactory
import org.eclipse.set.utils.table.TMFactory

import static org.eclipse.set.feature.table.pt1.sskp.SskpTransformator.*
import static org.eclipse.set.feature.table.pt1.ssza.SszaColumns.*

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich

/**
 * Table transformation for Datenpunkttabelle (Ssza).
 */
class SszaTransformator extends AbstractPlanPro2TableModelTransformator {

	var TMFactory factory = null

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService,
		TopologicalGraphService topGraphService) {
		super(cols, enumTranslationService)
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory, Stell_Bereich controlArea) {
		this.factory = factory
		return container.transform
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup container) {

		container.datenpunkt.filter[isPlanningObject].forEach [ it |
			if (Thread.currentThread.interrupted) {
				return
			}
			it.transform
		]
		return
	}

	private def transform(Datenpunkt datenpunkt) {
		val rowGroup = factory.newRowGroup(datenpunkt)

		datenpunkt.DPTyp.forEach [
			DPTypGETCS.forEach [
				transform(rowGroup, datenpunkt, it)
			]
		]
	}

	private def TableRow create rowGroup.newTableRow transform(
		RowFactory rowGroup, Datenpunkt datenpunkt,
		DP_Typ_GETCS_AttributeGroup dpType) {

		val dpBezug = dpType?.IDDPBezugFunktional?.value

		// A: Ssza.Datenpunkt.NID_C
		fill(
			cols.getColumn(Datenpunkt_NID_C),
			datenpunkt,
			[DPETCSAdresse?.NIDC?.wert?.toString]
		)

		// B: Ssza.Datenpunkt.NID_BG
		fill(
			cols.getColumn(Datenpunkt_NID_BG),
			datenpunkt,
			[DPETCSAdresse?.NIDBG?.wert?.toString]
		)

		// C: Ssza.Datenpunkt.Typ
		fill(
			cols.getColumn(Datenpunkt_Typ),
			datenpunkt,
			[dpType.DPTypETCS?.wert?.toString]
		)

		// D: Ssza.Datenpunkt.Gesteuert
		fill(
			cols.getColumn(Datenpunkt_Gesteuert),
			datenpunkt,
			[(LEUSteuernde?.IDLEUAnlage !== null).translate]
		)

		// E: Ssza.Datenpunkt.Anzahl_Balisen
		fill(
			cols.getColumn(Datenpunkt_Anzahl_Balisen),
			datenpunkt,
			[
				val counts = container.balise.
					filter[IDDatenpunkt?.value === it].map [
						baliseAllg?.anordnungImDP?.wert
					].filterNull
				if (counts.empty)
					return ""

				return counts.max.toString
			]
		)

		// F: Ssza.Bezugspunkt.Bezeichnung
		fillSwitch(
			cols.getColumn(Bezugspunkt_Bezeichnung),
			dpBezug,
			bezugspunktCase(
				BUE_Anlage,
				[bezeichnung?.bezeichnungTabelle?.wert]
			),
			bezugspunktCaseIterable(
				BUE_Einschaltung,
				[
					schaltmittelZuordnung.map [
						'''«schaltmittelFunktion?.wert.translate» «getSwitchName(IDSchalter?.value)»'''
					]
				]
			),
			bezugspunktCase(
				BUE_Kante,
				[
					'''BÜ-K «IDBUEAnlage?.value?.bezeichnung?.bezeichnungTabelle?.wert», Gl. TODO''' // TODO
				]
			),
			bezugspunktCase(
				PZB_Element,
				[
					'''GM «PZBArt?.wert?.translate» «IDPZBElementZuordnung?.value?.PZBElementZuordnungBP?.map[fillBezugsElement(IDPZBElementBezugspunkt?.value)].join»'''
				]
			),
			bezugspunktCase(
				ZUB_Streckeneigenschaft,
				[bezeichnung?.bezeichnungZUBSE?.wert]
			),
			bezugspunktCase(
				Markanter_Punkt,
				[bezeichnung?.bezeichnungMarkanterPunkt?.wert]
			)
		)

		// Ssza.Bezugspunkt.Standort.Strecke
		val getBezeichnungStrecke = [ Punkt_Objekt it |
			punktObjektStrecke?.map [
				IDStrecke?.value?.bezeichnung?.bezeichnungStrecke?.wert
			]
		]

		fillSwitch(
			cols.getColumn(Bezugspunkt_Standort_Strecke),
			dpBezug,
			bezugspunktCaseIterable(
				#[BUE_Kante, PZB_Element, BUE_Anlage],
				[
					getBezeichnungStrecke.apply(it)
				]
			),
			bezugspunktCaseIterable(
				Markanter_Punkt,
				[
					val ms = IDMarkanteStelle?.value
					if (ms instanceof Punkt_Objekt)
						return getBezeichnungStrecke.apply(ms)
					return #[]
				]
			),
			bezugspunktCaseIterable(
				BUE_Einschaltung,
				[
					val sue = schaltmittelZuordnung.map[IDSchalter?.value]?.
						filterNull.toList

					return sue.map [
						switch (it) {
							Zugeinwirkung,
							FMA_Komponente:
								return #[it]
							FMA_Anlage: {
								val fma = it
								return container.FMAKomponente.filter [
									IDFMAgrenze.contains(fma)
								]
							}
						}
					].flatten.flatMap[getBezeichnungStrecke.apply(it)]
				]
			),
			bezugspunktCase(
				ZUB_Streckeneigenschaft,
				[
					"TODO" // TODO
				]
			)
		)
		val getKMStrecke = [ Punkt_Objekt it |
			punktObjektStrecke?.map [
				streckeKm?.wert
			]
		]
		fillSwitch(
			cols.getColumn(Bezugspunkt_Standort_km),
			dpBezug,
			bezugspunktCaseIterable(
				#[BUE_Kante, PZB_Element, BUE_Anlage],
				[getKMStrecke.apply(it)]
			),
			bezugspunktCaseIterable(
				Markanter_Punkt,
				[
					val ms = IDMarkanteStelle?.value
					if (ms instanceof Punkt_Objekt) {
						return getKMStrecke.apply(ms)
					}
					return #[]
				]
			),
			bezugspunktCaseIterable(
				BUE_Einschaltung,
				[
					val sue = schaltmittelZuordnung.map[IDSchalter?.value]?.
						filterNull.toList

					return sue.flatMap [
						switch (it) {
							Zugeinwirkung,
							FMA_Komponente:
								return getKMStrecke.apply(it)
							FMA_Anlage: {
								// TODO
								return null
							}
						}
					].filterNull
				]
			),
			bezugspunktCase(
				ZUB_Streckeneigenschaft,
				[
					"TODO" // TODO
				]
			)
		)

		fillSwitch(
			cols.getColumn(DP_Standort_Stellbereich),
			dpBezug,
			bezugspunktCase(
				Markanter_Punkt,
				[val ms = IDMarkanteStelle?.value fillDPStandortStellbereich(ms)]
			),
			bezugspunktCase(
				#[BUE_Kante, PZB_Element, BUE_Anlage, PZB_Element], [
					fillDPStandortStellbereich
				]),
			bezugspunktCaseIterable(BUE_Einschaltung, [
				val sue = schaltmittelZuordnung.map[IDSchalter?.value]?.
					filterNull.toList

				val pos = (sue.filter(Zugeinwirkung) +
					sue.filter(FMA_Komponente)).map [
					fillDPStandortStellbereich
				]

				// TODO: FMA_Anlage
				return pos
			]),
			bezugspunktCase(
				ZUB_Streckeneigenschaft,
				["TODO"] // TODO
			)
		)

		// Ssza.DP-Standort.ETCS-Gleiskante
		fill(
			cols.getColumn(DP_Standort_ETCS_Gleiskante),
			datenpunkt,
			[
				// TODO
				return "TODO"
			]
		)

		// Ssza.DP-Standort.Strecke
		fillIterable(
			cols.getColumn(DP_Standort_Strecke),
			datenpunkt,
			[
				punktObjektStrecke.map [
					IDStrecke?.value?.bezeichnung?.bezeichnungStrecke?.wert
				]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// Ssza.DP-Standort.Km
		fillIterable(
			cols.getColumn(DP_Standort_Km),
			datenpunkt,
			[
				punktObjektStrecke.map[streckeKm?.wert]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		fillSwitch(
			cols.getColumn(DP_Standort_rel_Lage_zu_BP),
			datenpunkt,
			bezugspunktCase(
				#[BUE_Anlage, BUE_Kante, PZB_Element, ZUB_Streckeneigenschaft],
				[
					"TODO" // TODO
				]
			),
			bezugspunktCase(
				BUE_Einschaltung,
				[
					"TODO" // TODO
				]
			),
			bezugspunktCase(
				Markanter_Punkt,
				[
					"TODO" // TODO
				]
			)
		)

		// Ssza.rel_Lage_b_zu_a
		fill(
			cols.getColumn(rel_Lage_b_zu_a),
			datenpunkt,
			[datenpunktAllg?.datenpunktLaenge?.wert?.toString]
		// TODO: Note
		)

		// Ssza.Bemerkung
		fill(
			cols.getColumn(Bemerkung),
			datenpunkt,
			[
				"TODO" // TODO
			]
		)

		fillFootnotes(datenpunkt)

		return
	}

	private dispatch def getSwitchName(Void objekt) {
		return ""
	}

	private dispatch def getSwitchName(Zugeinwirkung objekt) {
		return objekt.bezeichnung?.bezeichnungTabelle
	}

	private dispatch def getSwitchName(FMA_Anlage objekt) {
		return objekt.bzBezeichner
	}

	private dispatch def getSwitchName(FMA_Komponente objekt) {
		return objekt.bezeichnung?.bezeichnungTabelle?.wert
	}

	private static def <T> Case<Basis_Objekt> bezugspunktCase(
		Iterable<Class<? extends T>> clazz,
		(T)=>String filling
	) {
		return bezugspunktCaseIterable(clazz, [#[filling.apply(it)]])
	}

	private static def <T> Case<Basis_Objekt> bezugspunktCase(
		Class<? extends T> clazz,
		(T)=>String filling
	) {
		return bezugspunktCaseIterable(clazz, [#[filling.apply(it)]])
	}

	private static def <T> Case<Basis_Objekt> bezugspunktCaseIterable(
		Class<? extends T> clazz,
		(T)=>Iterable<String> filling
	) {
		val action = [ Basis_Objekt it |
			if (it instanceof Markanter_Punkt)
				return it.IDMarkanteStelle?.value as T
			return it as T
		]

		return new Case<Basis_Objekt>(
			[clazz.isInstance(it)],
			action.andThen(filling),
			ITERABLE_FILLING_SEPARATOR,
			null
		)
	}

	private static def <T> Case<Basis_Objekt> bezugspunktCaseIterable(
		Iterable<Class<? extends T>> clazz,
		(T)=>Iterable<String> filling
	) {
		val action = [ Basis_Objekt it |
			return it as T
		]

		return new Case<Basis_Objekt>(
			[clazz.exists[isInstance(it)]],
			action.andThen(filling),
			ITERABLE_FILLING_SEPARATOR,
			null
		)
	}

	private def fillDPStandortStellbereich(Basis_Objekt b) {
		return "TODO"
	}

	private def getSchaltmittelZuordnung(BUE_Einschaltung bue) {
		bue.container.schaltmittelZuordnung.filter [
			bue == IDAnforderung?.value
		].filter [
			IDSchalter?.value instanceof Zugeinwirkung ||
				IDSchalter?.value instanceof FMA_Anlage ||
				IDSchalter?.value instanceof FMA_Komponente
		]
	}

}
