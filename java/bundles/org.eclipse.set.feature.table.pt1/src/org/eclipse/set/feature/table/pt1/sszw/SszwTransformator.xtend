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

import java.util.Set
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_W_Kr
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.utils.table.TMFactory

import static org.eclipse.set.feature.table.pt1.sszw.SszwColumns.*
import static org.eclipse.set.model.planpro.Weichen_und_Gleissperren.ENUMWKrArt.*

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ETCSWKrExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StellBereichExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Anlage
import org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts
import org.eclipse.set.basis.graph.TopPoint
import org.eclipse.set.core.services.graph.TopologicalGraphService
import org.eclipse.set.core.services.Services

/**
 * Table transformation for ETCS Melde- und Kommandoschaltung Muka Weichen (Sszw)
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
				container.WKrGspElement.filter [ gspElement |
					gspElement.IDWKrAnlage?.value !== null &&
						gspElement.IDWKrAnlage.value === it
				].map[bezeichnung?.bezeichnungTabelle?.wert].toSet.
					firstOrNull ?: ""
			]
		)

		// B: Sszw.W_Kr.Art
		fill(
			row,
			cols.getColumn(Art),
			refWKrAnlage,
			[WKrAnlageArt.translate ?: ""]
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

		// F: Sszw.W_Kr.Laenge.li
		fillSwitch(
			row,
			cols.getColumn(Laenge_links),
			refWKrAnlage,
			new Case<W_Kr_Anlage>(
				[
					WKrAnlageArt === ENUMW_KR_ART_EW
				],
				[
					val gspElemente = WKrGspElemente
					val gspKomponentTopPoints = gspElemente.flatMap[WKrGspKomponenten].filterNull.map[new TopPoint(it)]
					val signalTopPoints = WKrGspElemente.map[weicheElement?.IDGrenzzeichen?.value].filterNull.map[new TopPoint(it)]
					return signalTopPoints.flatMap[signalTopPoint |
						gspKomponentTopPoints.map[topGraphService.findShortestDistance(signalTopPoint, it)]
					].map[orElse(null)].min.doubleValue.toString
					
				]
			),
			new Case<W_Kr_Anlage>(
				[
					val anlageArt = WKrAnlageArt
					return (anlageArt === ENUMW_KR_ART_EKW ||
						anlageArt === ENUMW_KR_ART_DKW) &&
						WKrGspElemente.flatMap[WKrGspKomponenten].map [
							zungenpaar?.kreuzungsgleis?.wert
						].filterNull.exists [
							it === ENUMLinksRechts.ENUM_LINKS_RECHTS_LINKS
						]
				],
				[
					// TODO
				]
			)
		)

		// G: Sszw.Ansteuerung.ESTW_Zentraleinheit
		// TODO
		// H: Sszw.Ansteuerung.Stellbereich
		fill(
			row,
			cols.getColumn(Stellbereich),
			etcsWkr,
			[
				val constrolArea = stellbereich
				val designation = constrolArea?.oertlichkeitBezeichnung ?:
					constrolArea?.aussenelementansteuerungBezeichnungAEA
				return designation ?: ""
			]
		)
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
					""
			}
			default:
				return null
		}
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
