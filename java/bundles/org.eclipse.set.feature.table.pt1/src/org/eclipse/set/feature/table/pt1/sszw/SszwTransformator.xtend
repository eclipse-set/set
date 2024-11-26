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
package org.eclipse.set.feature.table.pt1.sszw

import java.util.Collections
import java.util.List
import java.util.Set
import org.eclipse.set.basis.graph.TopPoint
import org.eclipse.set.core.services.Services
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.core.services.graph.TopologicalGraphService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_W_Kr
import org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.ENUMWKrArt
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Anlage
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.utils.table.TMFactory

import static org.eclipse.set.feature.table.pt1.sszw.SszwColumns.*
import static org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts.*
import static org.eclipse.set.model.planpro.Weichen_und_Gleissperren.ENUMWKrArt.*

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ETCSWKrExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StellBereichExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*
import static extension org.eclipse.set.utils.math.BigDecimalExtensions.*

/**
 * Table transformation for ETCS Melde- und Kommandoanschaltung Weichen (Sszw)
 * 
 * @author truong
 */
class SszwTransformator extends AbstractPlanPro2TableModelTransformator {
	var TMFactory factory = null
	TopologicalGraphService topGraphService

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService) {
		super(cols, enumTranslationService)
		this.topGraphService = Services.topGraphService
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory, Stell_Bereich controlArea) {
		this.factory = factory
		return container.transform
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup contanier) {
		contanier.ETCSWKr.filter[isPlanningObject].forEach [
			if (Thread.currentThread.interrupted) {
				return
			}
			transform
		]
		return
	}

	private def transform(ETCS_W_Kr etcsWkr) {
		val row = factory.newTableRow(etcsWkr)
		val refWKrAnlage = etcsWkr.IDWKrAnlage?.value
		// A: Sszw.W_Kr.Bezeichnung
		fillConditional(
			row,
			cols.getColumn(Bezeichnung),
			refWKrAnlage,
			[
				WKrAnlageArt === ENUMW_KR_ART_EW
			],
			[
				WKrGspElemente.map[bezeichnung?.bezeichnungTabelle?.wert].toSet.
					firstOrNull ?: ""
			]
		)

		// B: Sszw.W_Kr.Art
		fill(
			row,
			cols.getColumn(Art),
			refWKrAnlage,
			["Test"]// [WKrAnlageArt.translate ?: ""]
		)

		// C: Sszw.W_Kr.Form
		fill(
			row,
			cols.getColumn(Bauform),
			refWKrAnlage,
			[WKrAnlageAllg?.WKrGrundform?.wert ?: ""]
		)

		// D: Sszw.W_Kr.Standort.Strecke
		val streckInfo = etcsWkr.streckeInfo
		fill(
			row,
			cols.getColumn(Strecke),
			etcsWkr,
			[streckInfo?.key ?: ""]
		)

		// E: Sszw.W_Kr.Standort.Km
		fill(
			row,
			cols.getColumn(km),
			etcsWkr,
			[streckInfo?.value ?: ""]
		)

		val wKomponentEW = refWKrAnlage.getGspKomponente(
			[it === ENUMW_KR_ART_EW],
			[true]
		)

		val wKomponentDKW_EKW_L = refWKrAnlage.getGspKomponente(
			[it === ENUMW_KR_ART_DKW || it === ENUMW_KR_ART_EKW],
			[it === ENUM_LINKS_RECHTS_LINKS]
		)

		val wKomponentDKW_EKW_R = refWKrAnlage.getGspKomponente(
			[it === ENUMW_KR_ART_DKW || it === ENUMW_KR_ART_EKW],
			[it === ENUM_LINKS_RECHTS_RECHTS]
		)

		// F: Sszw.W_Kr.Laenge.li
		fillSwitch(
			row,
			cols.getColumn(Laenge_links),
			refWKrAnlage,
			new Case<W_Kr_Anlage>(
				[
					!wKomponentEW.nullOrEmpty
				],
				[
					getWKrLaenge(wKomponentEW)

				]
			),
			new Case<W_Kr_Anlage>(
				[
					!wKomponentDKW_EKW_L.nullOrEmpty
				],
				[
					// TODO
				]
			)
		)

		// G: Sszw.W_Kr.Laenge.re
		fillSwitch(
			row,
			cols.getColumn(Laaenge_rechts),
			refWKrAnlage,
			new Case<W_Kr_Anlage>(
				[
					!wKomponentEW.nullOrEmpty
				],
				[
					getWKrLaenge(wKomponentEW).toString

				]
			),
			new Case<W_Kr_Anlage>(
				[
					!wKomponentDKW_EKW_R.nullOrEmpty
				],
				[
					// TODO
				]
			)
		)

		// H
		fill(
			row,
			cols.getColumn(Geschwindigkeit_W_L),
			refWKrAnlage,
			[
				// TODO
				"TODO"
			]
		)

		// I
		fill(
			row,
			cols.getColumn(Geschwindigkeit_W_R),
			refWKrAnlage,
			[
				// TODO
				"TODO"
			]
		)
		// J 
		fill(
			row,
			cols.getColumn(Geschwindigkeit_Kr_L),
			refWKrAnlage,
			[
				// TODO
				"TODO"
			]
		)

		// K 
		fill(
			row,
			cols.getColumn(Geschwindiket_Kr_R),
			refWKrAnlage,
			[
				// TODO
				"TODO"
			]
		)

		// L: Sszw.Ansteuerung.ESTW_Zentraleinheit
		fill(
			row,
			cols.getColumn(ESTW_Zentraleinheit),
			etcsWkr,
			[
				// TODO
				"TODO"
			]
		)
		// M: Sszw.Ansteuerung.Stellbereich
		fill(
			row,
			cols.getColumn(Stellbereich),
			etcsWkr,
			[
				stellbereich.oertlichkeitBezeichnung ?: ""
			]
		)

		// N: Sszw.Bemerkung
		fill(
			row,
			cols.getColumn(Bemerkung),
			etcsWkr,
			[]
		)
		fillFootnotes(row, etcsWkr)
	}

	private def Pair<String, String> getStreckeInfo(ETCS_W_Kr etcsWKr) {
		switch (etcsWKr.IDWKrAnlage?.value?.WKrAnlageArt) {
			case ENUMW_KR_ART_EW,
			case ENUMW_KR_ART_IBW,
			case ENUMW_KR_ART_ABW,
			case ENUMW_KR_ART_DW,
			case ENUMW_KR_ART_KLOTHOIDENWEICHE,
			case ENUMW_KR_ART_KORBBOGENWEICHE: {
				val gspKomponent = etcsWKr.WKrGspKomponents.firstOrNull
				val strecke = gspKomponent?.punktObjektStrecke?.firstOrNull?.
					IDStrecke?.value
				val bezeichnung = strecke?.bezeichnung?.bezeichnungStrecke?.
					wert ?: ""
				val km = gspKomponent?.punktObjektStrecke?.firstOrNull.
					streckeKm?.wert
				return bezeichnung -> km
			}
			case ENUMW_KR_ART_DKW,
			case ENUMW_KR_ART_EKW,
			case ENUMW_KR_ART_FLACHKREUZUNG,
			case ENUMW_KR_ART_KR: {
				val potks = etcsWKr.punktsObjektTopKante
				val strecke = etcsWKr.container.strecke.filter [ route |
					potks.exists[potk|potk.isBelongToBereichObjekt(route)]
				].filterNull.firstOrNull
				// TODO: km
				return strecke?.bezeichnung?.bezeichnungStrecke?.wert ?: "" ->
					"TODO"
			}
			default:
				return null
		}
	}

	private def List<W_Kr_Gsp_Komponente> getGspKomponente(
		W_Kr_Anlage wkrAnlage, (ENUMWKrArt)=>boolean wkrArtCondition,
		(ENUMLinksRechts)=>boolean kreuzungsgleisCondition) {
		if (!wkrArtCondition.apply(wkrAnlage.WKrAnlageArt)) {
			return Collections.emptyList
		}
		val gspKomponenten = wkrAnlage.WKrGspElemente.flatMap[WKrGspKomponenten]
		return gspKomponenten.filter [
			kreuzungsgleisCondition.apply(zungenpaar?.kreuzungsgleis?.wert)
		].toList
	}

	private def String getWKrLaenge(W_Kr_Anlage anlage,
		List<W_Kr_Gsp_Komponente> gspKomponente) {
		val signalTopPoints = anlage.WKrGspElemente.map [
			weicheElement?.IDGrenzzeichen?.value
		].filterNull.map[new TopPoint(it)]
		val distance = gspKomponente.map[new TopPoint(it)].flatMap [ gspPoint |
			signalTopPoints.map [
				topGraphService.findShortestDistance(it, gspPoint)
			]
		].map[orElse(null)].filterNull
		return distance.nullOrEmpty ? "" : distance.min.toTableDecimal
	}

	private def Stell_Bereich getStellbereich(ETCS_W_Kr etcsWKr) {
		switch (etcsWKr.IDWKrAnlage?.value?.WKrAnlageArt) {
			case ENUMW_KR_ART_EW,
			case ENUMW_KR_ART_IBW,
			case ENUMW_KR_ART_ABW,
			case ENUMW_KR_ART_DW,
			case ENUMW_KR_ART_KLOTHOIDENWEICHE,
			case ENUMW_KR_ART_KORBBOGENWEICHE: {
				val gspKomponenten = etcsWKr.WKrGspKomponents
				return etcsWKr.container.stellBereich.filter [ area |
					gspKomponenten.exists[area.isInControlArea(it)]
				].filterNull.firstOrNull
			}
			case ENUMW_KR_ART_DKW,
			case ENUMW_KR_ART_EKW,
			case ENUMW_KR_ART_FLACHKREUZUNG,
			case ENUMW_KR_ART_KR: {
				val potks = etcsWKr.punktsObjektTopKante
				return etcsWKr.container.stellBereich.filter [ area |
					potks.exists[isBelongToBereichObjekt(area)]
				].filterNull.firstOrNull
			}
			default:
				return null
		}
	}
}
