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

import java.math.BigDecimal
import java.math.BigInteger
import java.util.Collections
import java.util.List
import java.util.Optional
import java.util.Set
import org.eclipse.set.basis.graph.TopPoint
import org.eclipse.set.core.services.Services
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.core.services.graph.TopologicalGraphService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_W_Kr
import org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts
import org.eclipse.set.model.planpro.Geodaten.ENUMTOPAnschluss
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.ENUMWKrArt
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.ENUMWKrGspStellart
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Anlage
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.utils.math.AgateRounding
import org.eclipse.set.utils.table.TMFactory
import org.eclipse.set.utils.table.TableError
import org.osgi.service.event.EventAdmin

import static org.eclipse.set.feature.table.pt1.sszw.SszwColumns.*
import static org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts.*
import static org.eclipse.set.model.planpro.Weichen_und_Gleissperren.ENUMWKrArt.*
import static org.eclipse.set.ppmodel.extensions.geometry.GEOKanteGeometryExtensions.*

import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ESTW_ZentraleinheitExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ETCSWKrExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StellBereichExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
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
	String tableShortcut

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService, EventAdmin eventAdmin,
		String tableShortcut) {
		super(cols, enumTranslationService, eventAdmin)
		this.topGraphService = Services.topGraphService
		this.tableShortcut = tableShortcut
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		this.factory = factory
		return container.transform
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup contanier) {
		contanier.ETCSWKr.forEach [
			if (Thread.currentThread.interrupted) {
				return
			}
			IDWKrAnlage?.value.WKrGspElemente.forEach [ gspElement |
				transform(gspElement)
			]
		]
		return
	}

	private def transform(ETCS_W_Kr etcsWkr, W_Kr_Gsp_Element wKrGspElement) {
		val row = factory.newTableRow(etcsWkr)
		val refWKrAnlage = etcsWkr.IDWKrAnlage?.value
		// A: Sszw.W_Kr.Bezeichnung
		fill(
			row,
			cols.getColumn(Bezeichnung),
			wKrGspElement,
			[
				bezeichnung?.bezeichnungTabelle?.wert
			]
		)

		// B: Sszw.W_Kr.Grundform.Art
		fill(
			row,
			cols.getColumn(Art),
			refWKrAnlage,
			[WKrAnlageAllg?.WKrArt?.translate ?: ""]
		)

		// C: Sszw.W_Kr.Grundform.Form
		fill(
			row,
			cols.getColumn(Bauform),
			refWKrAnlage,
			[WKrAnlageAllg?.WKrGrundform?.wert ?: ""]
		)

		// D: Sszw.W_Kr.Standort.Strecke
		try {

			val streckeInfos = etcsWkr.streckeInfo
			fillIterable(
				row,
				cols.getColumn(Strecke),
				etcsWkr,
				[streckeInfos.map[key]],
				null
			)

			// E: Sszw.W_Kr.Standort.Km
			fillIterableSingleCellWhenAllowed(
				row,
				cols.getColumn(km),
				etcsWkr,
				[
					isFindGeometryComplete || streckeInfos.map[value].exists [
						isPresent
					]
				],
				[
					val kmValues = streckeInfos.map[value].filter[isPresent].map [
						get
					].toList
					return kmValues
				],
				null,
				ITERABLE_FILLING_SEPARATOR,
				tableShortcut
			)
		} catch (Exception e) {
			handleFillingException(e, row, cols.getColumn(Strecke))
			handleFillingException(e, row, cols.getColumn(km))
		}

		val wKomponentEW_L = refWKrAnlage.getGspKomponente(
			wKrGspElement,
			[isSimpleTrackSwitch],
			[
				val potk = punktObjektTOPKante.firstOrNull
				return (potk.abstand.wert === BigDecimal.ZERO &&
					potk.topKante.TOPAnschlussA ===
						ENUMTOPAnschluss.ENUMTOP_ANSCHLUSS_LINKS) ||
					(potk.abstand.wert !== BigDecimal.ZERO &&
						potk.topKante.TOPAnschlussB ===
							ENUMTOPAnschluss.ENUMTOP_ANSCHLUSS_RECHTS)
			]
		)

		val wKomponentEW_R = refWKrAnlage.getGspKomponente(
			wKrGspElement,
			[isSimpleTrackSwitch],
			[
				val potk = punktObjektTOPKante.firstOrNull
				return (potk.abstand.wert === BigDecimal.ZERO &&
					potk.topKante.TOPAnschlussA ===
						ENUMTOPAnschluss.ENUMTOP_ANSCHLUSS_RECHTS) ||
					(potk.abstand.wert !== BigDecimal.ZERO &&
						potk.topKante.TOPAnschlussB ===
							ENUMTOPAnschluss.ENUMTOP_ANSCHLUSS_LINKS)
			]
		)

		val wKomponentDKW_EKW_L = refWKrAnlage.getGspKomponente(
			wKrGspElement,
			[isCrossSwitch],
			[zungenpaar?.kreuzungsgleis?.wert === ENUM_LINKS_RECHTS_LINKS]
		)

		val wKomponentDKW_EKW_R = refWKrAnlage.getGspKomponente(
			wKrGspElement,
			[isCrossSwitch],
			[zungenpaar?.kreuzungsgleis?.wert === ENUM_LINKS_RECHTS_RECHTS]
		)

		// F: Sszw.W_Kr.Laenge.li
		fillSwitch(
			row,
			cols.getColumn(Laenge_links),
			wKrGspElement,
			new Case<W_Kr_Gsp_Element>(
				[!wKomponentEW_L.nullOrEmpty],
				[getWKrLaenge(wKomponentEW_L)]
			),
			new Case<W_Kr_Gsp_Element>(
				[!wKomponentDKW_EKW_L.nullOrEmpty],
				[getWKrLaenge(wKomponentDKW_EKW_L)]
			)
		)

		// G: Sszw.W_Kr.Laenge.re
		fillSwitch(
			row,
			cols.getColumn(Laaenge_rechts),
			wKrGspElement,
			new Case<W_Kr_Gsp_Element>(
				[!wKomponentEW_R.nullOrEmpty],
				[getWKrLaenge(wKomponentEW_R)]
			),
			new Case<W_Kr_Gsp_Element>(
				[!wKomponentDKW_EKW_R.nullOrEmpty],
				[getWKrLaenge(wKomponentDKW_EKW_R)]
			)
		)

		val trackSwitchTypes = #[ENUMW_KR_ART_EW, ENUMW_KR_ART_IBW,
			ENUMW_KR_ART_ABW, ENUMW_KR_ART_DW, ENUMW_KR_ART_KLOTHOIDENWEICHE,
			ENUMW_KR_ART_KORBBOGENWEICHE, ENUMW_KR_ART_SONSTIGE,
			ENUMW_KR_ART_EKW, ENUMW_KR_ART_DKW]
		// H: Sszw.Zulaessige_Geschwindigkeit.Weiche.li
		fill(
			row,
			cols.getColumn(Geschwindigkeit_W_L),
			refWKrAnlage,
			[
				getWKrGeschwindigkeit(
					wKrGspElement,
					[zungenpaar?.geschwindigkeitL?.wert],
					[zungenpaar?.geschwindigkeitL?.wert],
					[trackSwitchTypes.contains(it)],
					ENUM_LINKS_RECHTS_RECHTS
				)
			]
		)

		// I: Sszw.Zulaessige_Geschwindigkeit.Weiche.re
		fill(
			row,
			cols.getColumn(Geschwindigkeit_W_R),
			refWKrAnlage,
			[
				getWKrGeschwindigkeit(
					wKrGspElement,
					[zungenpaar?.geschwindigkeitR?.wert],
					[zungenpaar?.geschwindigkeitR?.wert],
					[trackSwitchTypes.contains(it)],
					ENUM_LINKS_RECHTS_LINKS
				)
			]
		)

		val crossTypes = #[ENUMW_KR_ART_FLACHKREUZUNG, ENUMW_KR_ART_KR,
			ENUMW_KR_ART_SONSTIGE, ENUMW_KR_ART_EKW, ENUMW_KR_ART_DKW]

		// J: Sszw.Zulaessige_Geschwindigkeit.Kreuzung.li
		fill(
			row,
			cols.getColumn(Geschwindigkeit_W_L),
			refWKrAnlage,
			[
				getWKrGeschwindigkeit(
					wKrGspElement,
					[kreuzung?.geschwindigkeitL?.wert],
					[zungenpaar?.geschwindigkeitL?.wert],
					[crossTypes.contains(it)],
					ENUM_LINKS_RECHTS_LINKS
				)
			]
		)

		// K: Sszw.Zulaessige_Geschwindigkeit.Kreuzung.re
		fill(
			row,
			cols.getColumn(Geschwindiket_Kr_R),
			refWKrAnlage,
			[
				getWKrGeschwindigkeit(
					wKrGspElement,
					[kreuzung?.geschwindigkeitR?.wert],
					[zungenpaar?.geschwindigkeitR?.wert],
					[crossTypes.contains(it)],
					ENUM_LINKS_RECHTS_RECHTS
				)
			]
		)

		// L: Sszw.Ansteuerung.ESTW_Zentraleinheit
		fillIterableWithConditional(
			row,
			cols.getColumn(ESTWZentraleinheit),
			wKrGspElement,
			[aussenelementansteuerung !== null],
			[
				aussenelementansteuerung.IDInformationPrimaer.filter(
					ESTW_Zentraleinheit).map [
					oertlichkeitAbkuerzung
				].filterNull
			],
			null,
			ITERABLE_FILLING_SEPARATOR
		)

		// M: Sszw.Ansteuerung.Stellbereich
		fillIterable(
			row,
			cols.getColumn(Stellbereich),
			etcsWkr,
			[
				val outsideControl = wKrGspElement.aussenelementansteuerung
				if (container.stellBereich.exists [
					IDAussenelementansteuerung?.value === outsideControl
				]) {
					return #[
						outsideControl.oertlichkeitNamensgebend.bezeichnung?.
							oertlichkeitAbkuerzung?.wert ?:
							outsideControl.bezeichnung?.bezeichnungAEA?.wert]
				}
				return #[stellbereich?.oertlichkeitBezeichnung].filterNull
			],
			null
		)

		// N: Sszw.RBC-Anschaltung
		fillConditional(
			row,
			cols.getColumn(RBA_Anschaltung),
			wKrGspElement,
			[
				#[ENUMWKrGspStellart.
					ENUMW_KR_GSP_STELLART_ELEKTRISCH_ORTSGESTELLT,
					ENUMWKrGspStellart.
						ENUMW_KR_GSP_STELLART_MECHANISCH_ORTSGESTELLT].contains(
					WKrGspElementAllg?.WKrGspStellart.wert)
			],
			["x"]
		)

		// O: Sszw.Bemerkung
		fill(
			row,
			cols.getColumn(Bemerkung),
			etcsWkr,
			[]
		)
		fillFootnotes(row, etcsWkr)
	}

	private def List<Pair<String, Optional<String>>> getStreckeInfo(
		ETCS_W_Kr etcsWKr) {
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
				return #[bezeichnung -> Optional.ofNullable(km)]
			}
			case ENUMW_KR_ART_DKW,
			case ENUMW_KR_ART_EKW,
			case ENUMW_KR_ART_FLACHKREUZUNG,
			case ENUMW_KR_ART_KR: {
				return etcsWKr.getStreckeInfoOfCrossSwitch
			}
			default:
				return null
		}
	}

	private def List<Pair<String, Optional<String>>> getStreckeInfoOfCrossSwitch(
		ETCS_W_Kr etcsWKr) {
		if (etcsWKr?.IDWKrAnlage?.value === null ||
			!etcsWKr.IDWKrAnlage?.value.isCrossSwitch) {
			return #[]
		}
		return etcsWKr.punktsObjektTopKante.flatMap [ potk |
			potk.streckenThroughBereichObjekt.map [ route |
				var Optional<String> routeKm = Optional.empty
				if (isFindGeometryComplete) {
					routeKm = Optional.ofNullable(
						potk.getStreckeKmThroughProjection(route)?.
							toTableDecimal)
				}
				return route.bezeichnung?.bezeichnungStrecke?.wert -> routeKm
			]
		].toList
	}

	private def List<W_Kr_Gsp_Komponente> getGspKomponente(
		W_Kr_Anlage wkrAnlage, W_Kr_Gsp_Element gspElement,
		(W_Kr_Anlage)=>boolean wkrArtCondition,
		(W_Kr_Gsp_Komponente)=>boolean leftRightCondition) {
		if (!wkrArtCondition.apply(wkrAnlage)) {
			return Collections.emptyList
		}
		val gspKomponenten = gspElement.WKrGspKomponenten

		return gspKomponenten.filter [
			leftRightCondition.apply(it)
		].toList
	}

	private def String getWKrLaenge(W_Kr_Gsp_Element gspElement,
		List<W_Kr_Gsp_Komponente> gspKomponente) {
		val signal = gspElement.weicheElement?.IDGrenzzeichen?.value
		if (signal === null) {
			return ""
		}
		val signalTopPoint = new TopPoint(signal)
		val distance = gspKomponente.map[new TopPoint(it)].map [ gspPoint |
			topGraphService.findShortestDistance(signalTopPoint, gspPoint)
		].map[orElse(null)].filterNull
		return distance.nullOrEmpty
			? ""
			: AgateRounding.roundDown(distance.min.doubleValue).toString
	}

	private def String getWKrGeschwindigkeit(
		W_Kr_Anlage wKrAnlage,
		W_Kr_Gsp_Element gspElement,
		(W_Kr_Gsp_Komponente)=>BigInteger allowSpeed,
		(W_Kr_Gsp_Komponente)=>BigInteger allowSpeedEKW_DKW,
		(ENUMWKrArt)=>Boolean typeCondition,
		ENUMLinksRechts leftRightCross
	) {
		val type = wKrAnlage.WKrAnlageArt
		if (!typeCondition.apply(wKrAnlage.WKrAnlageArt)) {
			return ""
		}

		switch (type) {
			case ENUMW_KR_ART_DKW,
			case ENUMW_KR_ART_EKW: {
				val gspKomponent = gspElement.WKrGspKomponenten.findFirst [
					zungenpaar?.kreuzungsgleis?.wert === leftRightCross
				]
				return allowSpeedEKW_DKW.apply(gspKomponent)?.toString ?: ""
			}
			case ENUMW_KR_ART_ABW,
			case ENUMW_KR_ART_DW,
			case ENUMW_KR_ART_EW,
			case ENUMW_KR_ART_IBW,
			case ENUMW_KR_ART_KLOTHOIDENWEICHE,
			case ENUMW_KR_ART_KORBBOGENWEICHE,
			case ENUMW_KR_ART_FLACHKREUZUNG,
			case ENUMW_KR_ART_KR,
			case ENUMW_KR_ART_SONSTIGE: {
				val gspKomponent = gspElement.WKrGspKomponenten.firstOrNull
				return allowSpeed.apply(gspKomponent)?.toString ?: ""
			}
			default:
				""
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
