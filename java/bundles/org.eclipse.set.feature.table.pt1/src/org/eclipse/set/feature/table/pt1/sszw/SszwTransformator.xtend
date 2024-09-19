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
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Anlage

class SszwTransformator extends AbstractPlanPro2TableModelTransformator {
	var TMFactory factory = null

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService) {
		super(cols, enumTranslationService)
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
			[WKrAnlageAllg?.WKrArt?.wert.translate ?: ""]
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
				[WKrAnlageArt === ENUMW_KR_ART_EW
					
				],
				[]
			),
			new Case<W_Kr_Anlage>(
				[],
				[]
			)
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
				val bezeichnung = strecke?.bezeichnung?.bezeichnungStrecke?.wert ?: ""
				val km = gspKomponent?.punktObjektStrecke?.firstOrNull.streckeKm?.wert
				return bezeichnung -> km
			}
			case ENUMW_KR_ART_DKW,
			case ENUMW_KR_ART_EKW,
			case ENUMW_KR_ART_FLACHKREUZUNG,
			case ENUMW_KR_ART_KR: {
				// TODO
			}
			default:
				return null
		}
	}
}
