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

import java.math.BigDecimal
import java.util.List
import java.util.Set
import org.eclipse.set.basis.graph.TopPoint
import org.eclipse.set.core.services.Services
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.core.services.graph.TopologicalGraphService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Anlage
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Einschaltung
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Kante
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.DP_Typ_GETCS_AttributeGroup
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.Datenpunkt
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ZUB_Streckeneigenschaft
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Fahrstrasse.Markanter_Punkt
import org.eclipse.set.model.planpro.Geodaten.Strecke
import org.eclipse.set.model.planpro.Gleis.Gleis_Bezeichnung
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
import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.DatenpunktExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StellBereichExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*
import static extension org.eclipse.set.utils.math.BigDecimalExtensions.*

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
				val counts = container.balise.filter [ bal |
					bal.IDDatenpunkt?.value === it
				].map [
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
					val bos = container.bereichObjekt
					val relevantBereichs = bos.filter[bo|bo.contains(it)].
						filter(Gleis_Bezeichnung).toList
					val tracksDesignation = relevantBereichs.filterNull.map [
						bezeichnung?.bezGleisBezeichnung?.wert
					]
					'''BÜ-K «IDBUEAnlage?.value?.bezeichnung?.bezeichnungTabelle?.wert», Gl. «tracksDesignation.join(", ")»'''
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
		var List<Pair<Strecke, String>> dpBezugStreckeAndKm = newLinkedList
		try {
			dpBezugStreckeAndKm.addAll(datenpunkt.getStreckeAndKm(dpBezug))
		} catch (Exception e) {
			handleFillingException(e, it,
				cols.getColumn(Bezugspunkt_Standort_Strecke))
			handleFillingException(e, it,
				cols.getColumn(Bezugspunkt_Standort_km))
			handleFillingException(e, it, cols.getColumn(Bemerkung))
		}
		if (!dpBezugStreckeAndKm.nullOrEmpty) {
			// G: Ssza.Bezugspunkt.Standort.Strecke
			fillIterable(
				cols.getColumn(Bezugspunkt_Standort_Strecke),
				dpBezugStreckeAndKm,
				[map[key?.bezeichnung?.bezeichnungStrecke?.wert].filterNull],
				MIXED_STRING_COMPARATOR
			)

			// H: Ssza.Bezugspunkt.Standort.km
			fillIterable(
				cols.getColumn(Bezugspunkt_Standort_km),
				dpBezugStreckeAndKm,
				[map[value]],
				MIXED_STRING_COMPARATOR
			)

			val firstStreckekm = dpBezugStreckeAndKm.firstOrNull?.value
			// O: Ssza.Bemerkung
			fillConditional(
				cols.getColumn(Bemerkung),
				datenpunkt,
				[
					ZUB_Streckeneigenschaft.isInstance(it) &&
						(it as ZUB_Streckeneigenschaft).metallteil !== null
				],
				[
					firstStreckekm ?: ""
				]
			)
		}

		// J: Ssza.DP-Standort.Stellbereich
		fillSwitch(
			cols.getColumn(DP_Standort_Stellbereich),
			dpBezug,
			bezugspunktCaseIterable(
				Markanter_Punkt,
				[
					fillDPStandortStellbereich(IDMarkanteStelle?.value)
				]
			),
			bezugspunktCaseIterable(#[BUE_Kante, PZB_Element, BUE_Anlage], [
				fillDPStandortStellbereich
			]),
			bezugspunktCaseIterable(BUE_Einschaltung, [
				val sue = schaltmittelZuordnung.map[IDSchalter?.value]?.
					filterNull.toList
				return sue.flatMap [
					fillDPStandortStellbereich
				]
			]),
			bezugspunktCase(ZUB_Streckeneigenschaft, [
				mostOverlapControlArea?.stellbereichBezeichnung ?: ""
			])
		)

		// K: Ssza.DP-Standort.ETCS-Gleiskante
		fill(
			cols.getColumn(DP_Standort_ETCS_Gleiskante),
			datenpunkt,
			[
				ETCSKanten.map [ edge |
					edge?.bezeichnung.bezeichnungETCSKante?.wert
				].join(ITERABLE_FILLING_SEPARATOR)
			]
		)

		// L: Ssza.DP-Standort.Strecke
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

		// M: Ssza.DP-Standort.Km
		fillIterable(
			cols.getColumn(DP_Standort_Km),
			datenpunkt,
			[
				punktObjektStrecke.map[streckeKm?.wert]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// I: Ssza.DP-Standort.rel_Lage_zu_BP
		val topologicalDistance = [ Punkt_Objekt po |
			return Services.topGraphService.findShortestDistance(
				new TopPoint(datenpunkt), new TopPoint(po)).orElse(null)
		]
		fillSwitch(
			cols.getColumn(DP_Standort_rel_Lage_zu_BP),
			dpBezug,
			bezugspunktCase(
				#[BUE_Anlage, BUE_Kante, PZB_Element],
				[
					topologicalDistance.apply(it).toTableDecimal
				]
			),
			bezugspunktCase(
				ZUB_Streckeneigenschaft,
				[
					val distanceRange = datenpunkt.distanceToBereichObjekt(it).
						orElse(null)
					if (distanceRange !== null) {
						return distanceRange.lowerEndpoint.toString
					}
				]
			),
			bezugspunktCase(
				BUE_Einschaltung,
				[
					val distances = schaltmittelZuordnung.map[IDSchalter.value].
						flatMap [ schalter |
							switch (schalter) {
								FMA_Komponente,
								Zugeinwirkung:
									return #[
										topologicalDistance.apply(schalter)]
								FMA_Anlage:
									return schalter.fmaKomponenten.map [
										topologicalDistance.apply(it)
									]
							}
						].filterNull.toList

					if (!distances.nullOrEmpty) {
						return distances.min.toTableDecimal
					}
					return ""
				]
			),
			bezugspunktCase(
				Markanter_Punkt,
				[
					val ms = IDMarkanteStelle?.value
					if (ms !== null && ms instanceof Punkt_Objekt) {
						return topologicalDistance.apply((ms as Punkt_Objekt)).
							toTableDecimal
					}

				]
			)
		)

		// N: Ssza.rel_Lage_b_zu_a
		fill(
			cols.getColumn(rel_Lage_b_zu_a),
			datenpunkt,
			[datenpunktAllg?.datenpunktLaenge?.wert?.toString]
		)

		fillFootnotes(datenpunkt)

		return
	}

	private dispatch def getSwitchName(Void objekt) {
		return ""
	}

	private dispatch def getSwitchName(Zugeinwirkung objekt) {
		return objekt.bezeichnung?.bezeichnungTabelle?.wert
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
		val result = newHashSet
		switch (b) {
			FMA_Anlage:
				result.add(b.relevantAreaControl)
			ZUB_Streckeneigenschaft:
				result.add(b.mostOverlapControlArea)
			default:
				result.addAll(b.container.stellBereich.filter [
					isInControlArea(b)
				])
		}
		return result.map[stellbereichBezeichnung].filterNull
	}

	private def String getStellbereichBezeichnung(Stell_Bereich controlArea) {
		val externalControl = controlArea?.IDAussenelementansteuerung?.value
		if (externalControl === null) {
			return null
		}
		return externalControl.IDOertlichkeitNamensgebend?.value.bezeichnung?.
			oertlichkeitAbkuerzung?.wert ?:
			externalControl.bezeichnung?.bezeichnungAEA?.wert
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

	private def dispatch List<Pair<Strecke, String>> getStreckeAndKm(
		Datenpunkt dp, Basis_Objekt object) {
		return #[]
	}

	private def dispatch List<Pair<Strecke, String>> getStreckeAndKm(
		Datenpunkt dp, Markanter_Punkt markantePunkt) {
		val ms = markantePunkt.IDMarkanteStelle?.value
		if (ms instanceof Punkt_Objekt) {
			return getStreckeAndKm(dp, ms)
		}
		return #[]
	}

	private def dispatch List<Pair<Strecke, String>> getStreckeAndKm(
		Datenpunkt dp, Punkt_Objekt po) {
		return po.punktObjektStrecke.map [
			IDStrecke?.value -> streckeKm?.wert
		].toList
	}

	private def dispatch List<Pair<Strecke, String>> getStreckeAndKm(
		Datenpunkt dp, BUE_Einschaltung bueEinschaltung) {
		val sue = bueEinschaltung.schaltmittelZuordnung.map[IDSchalter?.value]?.
			filterNull.toList
		return sue.flatMap [
			switch (it) {
				Zugeinwirkung,
				FMA_Komponente:
					return getStreckeAndKm(dp, it)
				FMA_Anlage: {
					val dpTopPoint = new TopPoint(dp)
					val topGraphService = Services.topGraphService
					val distancesToDP = fmaKomponenten.map [ fma |
						fma ->
							topGraphService.findShortestDistance(dpTopPoint,
								new TopPoint(fma))
					].filter[value.present]
					if (!distancesToDP.nullOrEmpty) {
						return dp.getStreckeAndKm(
							distancesToDP.minBy[value.get].key)
					}
					return #[]
				}
			}
		].toList
	}

	private def dispatch List<Pair<Strecke, String>> getStreckeAndKm(
		Datenpunkt dp, ZUB_Streckeneigenschaft zub) {
		val topGraphService = Services.topGraphService
		val dpPoint = new TopPoint(dp)
		// Find nearest area limit of ZUB_Streckeigenschaft
		val distancesToDp = zub.bereichObjektTeilbereich.flatMap[toTopPoints].
			map [
				it ->
					topGraphService.findShortestDistance(dpPoint, it).
						orElse(null)
			].filter[value !== null]
		if (distancesToDp.isNullOrEmpty) {
			return null
		}
		val nearestPoint = distancesToDp.minBy[value].key
		// Find relevant route, which contain a area with same limit like nearest point 
		val relevantStrecke = dp.container.strecke.findFirst [
			bereichObjektTeilbereich.exists [ botb |
				botb.topKante === nearestPoint.edge &&
					(botb.begrenzungA.wert === nearestPoint.distance ||
						botb.begrenzungB.wert === nearestPoint.distance
					)
			]
		]

		return #[relevantStrecke ->
			relevantStrecke.getNearestPointKm(nearestPoint)]
	}

	private def String getNearestPointKm(Strecke strecke,
		TopPoint nearestPoint) {
		val topKante = nearestPoint.edge
		val geoKantemetadata = Services.geometryService.getGeoKanteAt(topKante,
			topKante.TOPKnotenA, nearestPoint.distance);
		val pointCoordinate = Services.geometryService.getCoordinate(
			geoKantemetadata, nearestPoint.distance, BigDecimal.ZERO,
			ENUMWirkrichtung.ENUM_WIRKRICHTUNG_BEIDE)
		return pointCoordinate.coordinate.
			getStreckeKmThroughtProjection(strecke).toTableDecimal	
	}
}
