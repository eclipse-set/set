/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sskw

import java.math.BigInteger
import java.util.LinkedList
import java.util.List
import java.util.Set
import org.eclipse.set.basis.IModelSession
import org.eclipse.set.basis.constants.ContainerType
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.core.services.session.SessionService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Gleis.Gleis_Abschnitt
import org.eclipse.set.model.planpro.Regelzeichnung.Regelzeichnung
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs3
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.ENUMElementLage
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.Elektrischer_Antrieb_Lage_TypeClass
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.Kreuzung_AttributeGroup
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.Zungenpaar_AttributeGroup
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.MultiColorContent
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.TablemodelFactory
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static java.lang.Boolean.*
import static org.eclipse.set.feature.table.pt1.sskw.SskwColumns.*
import static org.eclipse.set.model.planpro.BasisTypen.ENUMLinksRechts.*
import static org.eclipse.set.model.planpro.Weichen_und_Gleissperren.ENUMWKrArt.*
import static org.eclipse.set.model.planpro.Weichen_und_Gleissperren.ENUMWKrGspStellart.*

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GleisAbschnittExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.MultiContainer_AttributeGroupExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalbegriffExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspKomponenteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*
import org.eclipse.set.utils.xml.EObjectXMLFinder
import org.eclipse.set.utils.xml.EObjectXMLFinder.XmlParseException

/**
 * Table transformation for a Weichentabelle (SSKW).
 * 
 * @author Schaefer
 */
class SskwTransformator extends AbstractPlanPro2TableModelTransformator {

	SessionService sessionService
	static val Logger logger = LoggerFactory.getLogger(
		typeof(SskwTransformator))

	EObjectXMLFinder xmlFinder

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService, EventAdmin eventAdmin,
		SessionService sessionService) {
		super(cols, enumTranslationService, eventAdmin)
		this.sessionService = sessionService
	}

	private static def String angrenzendesElementL(W_Kr_Gsp_Element element,
		String lage) {
		val angElement = element?.weicheElement?.GZFreimeldungL?.element
		if (angElement !== null) {
			return '''«(angElement as W_Kr_Gsp_Element)?.bezeichnung?.bezeichnungTabelle?.wert ?: "-"» «lage»'''
		} else {
			throw new IllegalArgumentException('''«element?.bezeichnung?.bezeichnungTabelle?.wert» has no GZFreimeldungL''')
		}
	}

	private static def String angrenzendesElementR(W_Kr_Gsp_Element element,
		String lage) {
		val angElement = element?.weicheElement?.GZFreimeldungR?.element
		if (angElement !== null) {
			return '''«(angElement as W_Kr_Gsp_Element)?.bezeichnung?.bezeichnungTabelle?.wert ?: "-"» «lage»'''
		} else {
			throw new IllegalArgumentException('''«element?.bezeichnung?.bezeichnungTabelle?.wert» has no GZFreimeldungR''')
		}
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory, Stell_Bereich controlArea) {
		xmlFinder = createEObjetXMLFinder(container)
		val weichen = container.WKrGspElement.filter[isPlanningObject].
			filterObjectsInControlArea(controlArea)

		for (element : weichen) {
			if (Thread.currentThread.interrupted) {
				return null
			}
			val instance = factory.newTableRow(element)

			// A: Sskw.Weiche_Kreuzung_Gleissperre_Sonderanlage.Bezeichnung
			fill(
				instance,
				cols.getColumn(Bezeichnung),
				element,
				[bezeichnung.bezeichnungTabelle.wert]
			)
			if (logger.debugEnabled) {
				logger.debug(element.bezeichnung.bezeichnungTabelle.wert)
			}

			// B: Sskw.Weiche_Kreuzung_Gelissperre_Sonderanlage.Art
			fillConditional(
				instance,
				cols.getColumn(Art),
				element,
				[IDWKrAnlage !== null],
				[WKrAnlage?.WKrAnlageAllg?.WKrArt?.translate]
			)

			// C: Sskw.Weiche_Kreuzung_Gleissperre_Sonderanlage.Form
			fillConditional(
				instance,
				cols.getColumn(Form),
				element,
				[IDWKrAnlage !== null],
				[WKrAnlage?.WKrAnlageAllg?.WKrGrundform?.wert]
			)

			// D: Sskw.Freimeldung.Fma
			fillIterable(
				instance,
				cols.getColumn(Freimeldung_Fma),
				element,
				[
					WKrGspKomponenten.map[fmaAnlage].flatten.toSet.map [
						bzBezeichner
					]
				],
				MIXED_STRING_COMPARATOR
			)

			// E: Sskw.Freimeldung.nicht_grenzzeichenfrei.Links
			fillSwitch(
				instance,
				cols.getColumn(nicht_grenzzeichenfrei_Links),
				element,
				new Case<W_Kr_Gsp_Element>(
					[
						weicheElement?.GZFreimeldungL?.elementLage?.wert ===
							ENUMElementLage.ENUM_ELEMENT_LAGE_ABSCHNITT
					],
					[
						(weicheElement?.GZFreimeldungL?.
							element as Gleis_Abschnitt)?.fmaAnlagen.map [
							bzBezeichner
						]
					],
					ITERABLE_FILLING_SEPARATOR,
					MIXED_STRING_COMPARATOR
				),
				new Case<W_Kr_Gsp_Element>([
					weicheElement?.GZFreimeldungL?.elementLage?.wert ===
						ENUMElementLage.ENUM_ELEMENT_LAGE_WEICHE
				], [
					(weicheElement?.GZFreimeldungL?.
						element as W_Kr_Gsp_Element)?.bezeichnung?.
						bezeichnungTabelle?.wert
				]),
				new Case<W_Kr_Gsp_Element>([
					weicheElement?.GZFreimeldungL?.elementLage?.wert ===
						ENUMElementLage.ENUM_ELEMENT_LAGE_WEICHE_L
				], [angrenzendesElementL("L")]),
				new Case<W_Kr_Gsp_Element>([
					weicheElement?.GZFreimeldungL?.elementLage?.wert ===
						ENUMElementLage.ENUM_ELEMENT_LAGE_WEICHE_R
				], [angrenzendesElementL("R")])
			)

			// F: Sskw.Freimeldung.nicht_grenzzeichenfrei.Rechts
			fillSwitch(
				instance,
				cols.getColumn(nicht_grenzzeichenfrei_Rechts),
				element,
				new Case<W_Kr_Gsp_Element>(
					[
						weicheElement?.GZFreimeldungR?.elementLage?.wert ===
							ENUMElementLage.ENUM_ELEMENT_LAGE_ABSCHNITT
					],
					[
						(weicheElement?.GZFreimeldungR?.
							element as Gleis_Abschnitt)?.fmaAnlagen?.map [
							bzBezeichner
						]
					],
					ITERABLE_FILLING_SEPARATOR,
					MIXED_STRING_COMPARATOR
				),
				new Case<W_Kr_Gsp_Element>([
					weicheElement?.GZFreimeldungR?.elementLage?.wert ===
						ENUMElementLage.ENUM_ELEMENT_LAGE_WEICHE
				], [
					(weicheElement?.GZFreimeldungR?.
						element as W_Kr_Gsp_Element)?.bezeichnung?.
						bezeichnungTabelle?.wert
				]),
				new Case<W_Kr_Gsp_Element>([
					weicheElement?.GZFreimeldungR?.elementLage?.wert ===
						ENUMElementLage.ENUM_ELEMENT_LAGE_WEICHE_L
				], [angrenzendesElementR("L")]),
				new Case<W_Kr_Gsp_Element>([
					weicheElement?.GZFreimeldungR?.elementLage?.wert ===
						ENUMElementLage.ENUM_ELEMENT_LAGE_WEICHE_R
				], [angrenzendesElementR("R")])
			)

			// G: Sskw.Freimeldung.Isolierfall
			fillConditional(
				instance,
				cols.getColumn(Isolierfall),
				element,
				[IDWKrAnlage !== null],
				[WKrAnlage.WKrAnlageAllg?.isolierfall?.wert ?: ""]
			)

			// H: Sskw.Vorzugslage.Lage
			fillSwitch(
				instance,
				cols.getColumn(Vorzugslage_Lage),
				element,
				new Case<W_Kr_Gsp_Element>(
					[weicheElement?.weicheVorzugslage?.wert !== null],
					[weicheElement?.weicheVorzugslage?.translate ?: ""]
				),
				new Case<W_Kr_Gsp_Element>(
					[gleissperreElement?.gleissperreVorzugslage?.wert !== null],
					[
						gleissperreElement?.gleissperreVorzugslage?.
							translate ?: ""
					]
				)
			)

			// I: Sskw.Vorzugslage.Automatik
			fill(
				instance,
				cols.getColumn(Vorzugslage_Automatik),
				element,
				[WKrGspElementAllg?.vorzugslageAutomatik?.wert?.translate ?: ""]
			)

			// J: Sskw.Weiche.Auffahrortung
			fill(
				instance,
				cols.getColumn(Weiche_Auffahrortung),
				element,
				[weicheElement?.auffahrortung?.wert?.translate ?: ""]
			)

			// K: Sskw.Weiche.Antriebe
			instance.fillAntrieb(
				cols.getColumn(Weiche_Antriebe),
				element,
				[zungenpaar?.elektrischerAntriebAnzahl?.wert],
				[zungenpaar?.elektrischerAntriebLage],
				[true]
			)

			val elementKomponenten = element.container.WKrGspKomponente.filter [
				IDWKrGspElement?.value?.identitaet?.wert ==
					element.identitaet.wert && zungenpaar !== null
			].toList

			// L: Sskw.Weiche.Weichensignal
			val weichensignal = elementKomponenten.map [
				zungenpaar?.weichensignal
			].filterNull
			fillIterable(
				instance,
				cols.getColumn(Weiche_Weichensignal),
				element,
				[
					weichensignal.map[translate].toSet
				],
				null
			)

			// M: Sskw.Weiche.Pruefkontakte
			val pruefkontakte = elementKomponenten.map [
				zungenpaar?.zungenpruefkontaktAnzahl?.wert
			].filterNull.map[intValue]
			fillConditional(
				instance,
				cols.getColumn(Weiche_Pruefkontakte),
				element,
				[!pruefkontakte.empty],
				[pruefkontakte.fold(0, [s, a|s + a]).toString]
			)

			val art = element.WKrAnlage?.WKrAnlageAllg?.WKrArt?.wert
			val art_ew_abw_ibw_kloth_dw = #{
				ENUMW_KR_ART_EW,
				ENUMW_KR_ART_ABW,
				ENUMW_KR_ART_IBW,
				ENUMW_KR_ART_KLOTHOIDENWEICHE,
				ENUMW_KR_ART_DW
			}.contains(art)
			val art_ekw = #{
				ENUMW_KR_ART_EKW
			}.contains(art)

			val art_dkw = #{
				ENUMW_KR_ART_DKW
			}.contains(art)

			val art_kr_flachkreuzung = #{
				ENUMW_KR_ART_KR,
				ENUMW_KR_ART_FLACHKREUZUNG
			}.contains(art)

			val art_sonstige = #{
				ENUMW_KR_ART_SONSTIGE
			}.contains(art)

			val art_sonstige_mit_zungenpaar = art_sonstige &&
				element.WKrGspKomponenten.exists[zungenpaar !== null]

			val art_sonstige_mit_kreuzung = art_sonstige &&
				element.WKrGspKomponenten.exists[kreuzung !== null]

			val art_ew_abw_ibw_kloth_dw_sonstige_mit_zungenpaar = art_ew_abw_ibw_kloth_dw ||
				art_sonstige_mit_zungenpaar

			val art_kr_flachkreuzung_sonstige_mit_kreuzung = art_kr_flachkreuzung ||
				art_sonstige_mit_kreuzung

			val keine_anlage = element.IDWKrAnlage === null

			// N: Sskw.Weiche.v_zul_W.Links
			val wKrGspKomponenten = element.WKrGspKomponenten
			fillSwitch(
				instance,
				cols.getColumn(Weiche_v_zul_W_Links),
				element,
				fillingIterableCase(
					[art_ew_abw_ibw_kloth_dw_sonstige_mit_zungenpaar],
					[
						val isPMaxL = element.
							isGeschwindigkeitPMax(element.topKanteL)
						if (isPMaxL) {
							instance.addTopologicalCell(
								cols.getColumn(Weiche_v_zul_W_Links))
						}
						wKrGspKomponenten.map[zungenpaar].
							printGeschwindingkeitL(isPMaxL)
					]
				),
				fillingIterableCase(
					[art_ekw || art_dkw],
					[
						val isPMaxL = element.
							isGeschwindigkeitPMax(element.topKanteL)
						if (isPMaxL) {
							instance.addTopologicalCell(
								cols.getColumn(Weiche_v_zul_W_Links))
						}
						wKrGspKomponenten.filter [
							zungenpaar?.kreuzungsgleis?.wert ==
								ENUM_LINKS_RECHTS_RECHTS
						].map[zungenpaar].toList.printGeschwindingkeitL(isPMaxL)
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[art_kr_flachkreuzung_sonstige_mit_kreuzung],
					[""]
				),
				new Case<W_Kr_Gsp_Element>(
					[keine_anlage],
					[""]
				)
			)

			// O: Sskw.Weiche.v_zul_W.Rechts
			fillSwitch(
				instance,
				cols.getColumn(Weiche_v_zul_W_Rechts),
				element,
				fillingIterableCase(
					[art_ew_abw_ibw_kloth_dw_sonstige_mit_zungenpaar],
					[
						val isPMaxR = element.
							isGeschwindigkeitPMax(element.topKanteR)
						if (isPMaxR) {
							instance.addTopologicalCell(
								cols.getColumn(Weiche_v_zul_W_Rechts))
						}
						wKrGspKomponenten.map [
							zungenpaar
						].printGeschwindingkeitR(isPMaxR)
					]
				),
				fillingIterableCase(
					[art_ekw || art_dkw],
					[
						val isPMaxR = element.
							isGeschwindigkeitPMax(element.topKanteR)
						if (isPMaxR) {
							instance.addTopologicalCell(
								cols.getColumn(Weiche_v_zul_W_Rechts))
						}
						wKrGspKomponenten.filter [
							zungenpaar?.kreuzungsgleis?.wert ==
								ENUM_LINKS_RECHTS_LINKS
						].map[zungenpaar].toList.printGeschwindingkeitR(isPMaxR)
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[art_kr_flachkreuzung_sonstige_mit_kreuzung],
					[""]
				),
				new Case<W_Kr_Gsp_Element>(
					[keine_anlage],
					[""]
				)
			)

			// P: Sskw.Kreuzung.v_zul_K.Links
			val krLinksKomponenten = wKrGspKomponenten.filter [
				zungenpaar?.kreuzungsgleis?.wert == ENUM_LINKS_RECHTS_LINKS
			]
			val exKrLinksKomponenten = !krLinksKomponenten.empty
			fillSwitch(
				instance,
				cols.getColumn(Kreuzung_v_zul_K_Links),
				element,
				new Case<W_Kr_Gsp_Element>(
					[art_ew_abw_ibw_kloth_dw_sonstige_mit_zungenpaar],
					[""]
				),
				fillingIterableCase(
					[art_ekw],
					[
						instance.addTopologicalCell(
							cols.getColumn(Kreuzung_v_zul_K_Links))
						val isPMaxL = element.
							isGeschwindigkeitPMax(element.topKanteL)
						getKreuzungEKWGroup(wKrGspKomponenten,
							ENUM_LINKS_RECHTS_LINKS).
							printGeschwindingkeitL(isPMaxL)
					]
				),
				fillingIterableCase(
					[art_dkw && exKrLinksKomponenten],
					[
						instance.addTopologicalCell(
							cols.getColumn(Kreuzung_v_zul_K_Links))
						val isPMaxL = element.
							isGeschwindigkeitPMax(element.topKanteL)
						krLinksKomponenten.map [
							zungenpaar
						].toList.printGeschwindingkeitL(isPMaxL)
					]
				),
				fillingIterableCase(
					[art_kr_flachkreuzung_sonstige_mit_kreuzung],
					[
						val isPMaxL = element.
							isGeschwindigkeitPMax(element.topKanteL)
						if (isPMaxL) {
							instance.addTopologicalCell(
								cols.getColumn(Kreuzung_v_zul_K_Links))
						}
						wKrGspKomponenten.map[kreuzung].
							printGeschwindingkeitL(isPMaxL)
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[keine_anlage],
					[""]
				)
			)

			// Q: Sskw.Kreuzung.v_zul_K.Rechts
			val krRechtsKomponenten = wKrGspKomponenten.filter [
				zungenpaar?.kreuzungsgleis?.wert == ENUM_LINKS_RECHTS_RECHTS
			]
			val exKrRechtsKomponenten = !krRechtsKomponenten.empty
			fillSwitch(
				instance,
				cols.getColumn(Kreuzung_v_zul_K_Rechts),
				element,
				new Case<W_Kr_Gsp_Element>(
					[art_ew_abw_ibw_kloth_dw_sonstige_mit_zungenpaar],
					[""]
				),
				fillingIterableCase(
					[art_ekw],
					[
						instance.addTopologicalCell(
							cols.getColumn(Kreuzung_v_zul_K_Rechts))
						val isPMaxR = element.
							isGeschwindigkeitPMax(element.topKanteR)
						getKreuzungEKWGroup(wKrGspKomponenten,
							ENUM_LINKS_RECHTS_RECHTS).
							printGeschwindingkeitR(isPMaxR)
					]
				),
				fillingIterableCase(
					[art_dkw && exKrRechtsKomponenten],
					[
						instance.addTopologicalCell(
							cols.getColumn(Kreuzung_v_zul_K_Rechts))
						val isPMaxR = element.
							isGeschwindigkeitPMax(element.topKanteR)
						krRechtsKomponenten.map [
							zungenpaar
						].toList.printGeschwindingkeitR(isPMaxR)
					]
				),
				fillingIterableCase(
					[art_kr_flachkreuzung_sonstige_mit_kreuzung],
					[
						val isPMaxR = element.
							isGeschwindigkeitPMax(element.topKanteR)
						if (isPMaxR) {
							instance.addTopologicalCell(
								cols.getColumn(Kreuzung_v_zul_K_Rechts))
						}
						wKrGspKomponenten.map[kreuzung].
							printGeschwindingkeitR(isPMaxR)
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[keine_anlage],
					[""]
				)
			)

			// R: Sskw.Herzstueck.Antriebe
			instance.fillHerzstueckAntriebe(element)

			val entgleisungsschuhe = element.WKrGspKomponenten.filter [
				entgleisungsschuh !== null
			]
			val exEntgleisungsschuh = !entgleisungsschuhe.empty

			// S: Sskw.Gleissperre.Antriebe
			fillMultiColor(
				instance,
				cols.getColumn(Gleissperre_Antriebe),
				element,
				[
					if (!exEntgleisungsschuh) {
						return null
					}
					val multiColorContent = TablemodelFactory.eINSTANCE.
						createMultiColorContent
					multiColorContent.disableMultiColor = true
					val gleissperreAntrieb = gleissperreAntrieb
					if (entgleisungsschuhe.exists [
						austauschAntriebe?.wert === true
					] && it.container.containerType == ContainerType.FINAL) {
						multiColorContent.multiColorValue = gleissperreAntrieb
						multiColorContent.stringFormat = "%s"
					} else {
						multiColorContent.multiColorValue = null
						multiColorContent.stringFormat = gleissperreAntrieb
					}
					return multiColorContent
				]
			)

			// T: Sskw.Gleissperre.Gsp_signal
			fillIterable(
				instance,
				cols.getColumn(Gleissperre_Gsp_signal),
				element,
				[
					WKrGspKomponenten.map[entgleisungsschuh].filterNull.map [
						gleissperrensignal?.translate ?: "□"
					].toSet
				],
				null
			)

			// U: Sskw.Gleissperre.Auswurfrichtung
			fillConditional(
				instance,
				cols.getColumn(Gleissperre_Auswurfrichtung),
				element,
				[
					exEntgleisungsschuh
				],
				[
					val auswurfrichtung = WKrGspKomponenten.map [
						entgleisungsschuh?.auswurfrichtung
					].filterNull.toList
					if (!auswurfrichtung.nullOrEmpty) {
						return auswurfrichtung.first.translate
					}
					val potk = WKrGspKomponenten.flatMap[singlePoints].filter [
						it !== null && seitlicheLage?.wert !== null &&
							wirkrichtung?.wert !== null
					].firstOrNull

					if (potk === null || potk.wirkrichtung.wert ===
						ENUMWirkrichtung.ENUM_WIRKRICHTUNG_BEIDE) {
						throw new IllegalArgumentException('''The W_Kr_Gsp_Element: «identitaet.wert» hat keine gültige Auswurfrichtung''')
					}
					// Equivalent compare:
					// Lateral position RECHTS + direction IN -> R
					// Lateral position RECHTS + direction GEGEN -> L
					// Lateral position LINKS + direction IN -> L
					// Lateral position LINKS + direction GEGEN -> R
					return (potk.seitlicheLage.wert ===
						ENUM_LINKS_RECHTS_LINKS) ===
						(potk.wirkrichtung.wert === ENUMWirkrichtung.
							ENUM_WIRKRICHTUNG_IN) ? "L" : "R"
				]
			)

			// V: Sskw.Gleissperre.Schutzschiene
			fillSwitch(
				instance,
				cols.getColumn(Gleissperre_Schutzschiene),
				element,
				new Case<W_Kr_Gsp_Element>(
					[exEntgleisungsschuh],
					[
						Boolean.valueOf(WKrGspKomponenten.exists [
							entgleisungsschuh?.schutzschiene?.wert == TRUE
						]).translate
					]
				)
			)

			// W: Sskw.Sonstiges.Regelzeichnung_Nr
			fillIterable(
				instance,
				cols.getColumn(Sonstiges_Regelzeichnung_Nr),
				element,
				[element.regelzeichnungen.map[fillRegelzeichnung]],
				null
			)

			// X: Sskw.Sonstiges.DWs
			fillConditional(
				instance,
				cols.getColumn(Sonstiges_DWs),
				element,
				[IDWKrAnlage === null],
				[""],
				[(WKrAnlage.IDAnhangDWS !== null).translate]
			)

			// Y: Sskw.Sonderanlage.Art
			fillIterable(
				instance,
				cols.getColumn(Sonderanlage_Art),
				element,
				[
					wKrGspKomponenten.map [
						besonderesFahrwegelement?.translate
					].filterNull
				],
				null
			)

			// Z: Sskw.Bemerkung
			fillFootnotes(instance, element)
		}

		return factory.table
	}

	def EObjectXMLFinder createEObjetXMLFinder(
		MultiContainer_AttributeGroup container) {
		try {
			val loadedSession = sessionService.loadedSessions
			var IModelSession modelSession = null
			if (loadedSession.size === 1) {
				modelSession = loadedSession.values.firstOrNull
			} else {
				val planproSchnittstelle = container.planProSchnittstelle
				modelSession = loadedSession.values.filter [
					it.planProSchnittstelle == planproSchnittstelle
				].firstOrNull
			}
			return new EObjectXMLFinder(modelSession.toolboxFile,
				modelSession.toolboxFile.modelPath)
		} catch (Exception e) {
			logger.error("Can't create EObjectXMLFinder: {}", e.message)
			return null
		}

	}

	/**
	 * Create filling Iterable case with compartor as ToolboxConstants.NUMERIC_COMPARATOR
	 * and separator as ","
	 */
	private def static Case<W_Kr_Gsp_Element> fillingIterableCase(
		(W_Kr_Gsp_Element)=>Boolean condition,
		(W_Kr_Gsp_Element)=>Iterable<String> filling) {
		return new Case<W_Kr_Gsp_Element>(condition, filling, ", ",
			ToolboxConstants.NUMERIC_COMPARATOR)
	}

	def void fillHerzstueckAntriebe(TableRow row, W_Kr_Gsp_Element element) {
		val herzstueckAntriebe = element.WKrGspKomponenten.map [
			zungenpaar?.herzstueckAntriebe?.wert
		].filterNull.map[intValue]
		val elektrischerAntriebAnzahl = element.WKrGspKomponenten.map [
			kreuzung?.elektrischerAntriebAnzahl?.wert
		].filterNull.map[intValue]
		if (herzstueckAntriebe.exists[it > 0]) {
			row.fillAntrieb(cols.getColumn(Herzstueck_Antriebe), element, [
				zungenpaar?.herzstueckAntriebe?.wert
			], [null], [kreuzung !== null])
		} else if (elektrischerAntriebAnzahl.exists[it > 0]) {
			row.fillAntrieb(cols.getColumn(Herzstueck_Antriebe), element, [
				kreuzung?.elektrischerAntriebAnzahl?.wert
			], [kreuzung?.elektrischerAntriebLage], [kreuzung !== null])
		} else {
			fill(
				row,
				cols.getColumn(Herzstueck_Antriebe),
				element,
				[]
			)
		}
	}

	def void fillAntrieb(TableRow row, ColumnDescriptor column,
		W_Kr_Gsp_Element element,
		(W_Kr_Gsp_Komponente)=>BigInteger actuatorNumberSelector,
		(W_Kr_Gsp_Komponente)=>Elektrischer_Antrieb_Lage_TypeClass actuatorPositionSelector,
		(W_Kr_Gsp_Komponente)=>Boolean fillPositionSupplementCondition) {
		val components = element.WKrGspKomponenten
		if (components.map[actuatorNumberSelector.apply(it)].filterNull.
			nullOrEmpty) {
			return
		}
		if (element.container?.containerType === ContainerType.FINAL &&
			components.exists [
				austauschAntriebe?.wert !== null && austauschAntriebe?.wert
			]) {
			fillMultiColorIterable(
				row,
				column,
				element,
				[
					transformMultiColorContent(
						WKrGspKomponenten,
						actuatorNumberSelector,
						actuatorPositionSelector,
						[kreuzung !== null]
					)
				],
				"+"
			)
		} else {
			val actuatorCount = components.
				map[actuatorNumberSelector.apply(it)].filterNull.map[intValue].
				reduce[p1, p2|p1.intValue + p2.intValue]
			val position = components.filter [
				fillPositionSupplementCondition.apply(it)
			].map [
				it -> actuatorNumberSelector.apply(it)
			].map[key.getPosition(value, actuatorPositionSelector)].filter [
				!nullOrEmpty && !blank
			].toSet.join(", ")
			if (actuatorCount === null || actuatorCount === 0) {
				return
			}
			fill(row, column, element, [
				'''«actuatorCount» «IF !position.nullOrEmpty && !position.blank »(«position»)«ENDIF»'''
			])
		}
	}

	private def List<Zungenpaar_AttributeGroup> getKreuzungEKWGroup(
		W_Kr_Gsp_Element element, List<W_Kr_Gsp_Komponente> komponentens,
		ENUMLinksRechts enumLinksRechts) {
		val group = newLinkedList
		komponentens.forEach [
			if (zungenpaar?.kreuzungsgleis?.wert == enumLinksRechts) {
				group.add(zungenpaar)
			} else {
				val gspElement = element.container.WKrGspElement.filter [
					element.WKrAnlage === WKrAnlage && it !== element
				]
				group.addAll(gspElement.map[WKrGspKomponenten].flatten.map [
					zungenpaar
				])
			}

		]
		return group
	}

	private def boolean isGeschwindigkeitPMax(W_Kr_Gsp_Element element,
		TOP_Kante topKante) {
		val fstrZugs = element.getFstrZugCrossingLeg(topKante)
		if (fstrZugs.empty) {
			return false
		}
		return !fstrZugs.exists [
			fstrSignalisierung.exists [
				IDSignalSignalbegriff?.value.hasSignalbegriffID(Zs3)
			]
		]
	}

	private def dispatch String printGeschwindingkeitL(Object group) {
		throw new IllegalArgumentException(group.toString)
	}

	private def Iterable<String> printGeschwindingkeitL(List<?> group,
		boolean isPMaxL) {
		return group.map [
			'''«printGeschwindingkeitL» «IF isPMaxL»(pmax)«ENDIF»'''
		]
	}

	private def dispatch String printGeschwindingkeitL(
		Zungenpaar_AttributeGroup group) {
		return '''«group?.geschwindigkeitL?.wert»'''
	}

	private def dispatch String printGeschwindingkeitL(
		Kreuzung_AttributeGroup group) {
		return '''«group?.geschwindigkeitL?.wert»'''
	}

	private def dispatch String printGeschwindingkeitR(Object group) {
		throw new IllegalArgumentException(group.toString)
	}

	private def Iterable<String> printGeschwindingkeitR(List<?> group,
		boolean isPMaxR) {
		return group.map [
			'''«printGeschwindingkeitR» «IF isPMaxR»(pmax)«ENDIF»'''
		]
	}

	private def dispatch String printGeschwindingkeitR(
		Zungenpaar_AttributeGroup group) {
		return '''«group?.geschwindigkeitR?.wert»'''
	}

	private def dispatch String printGeschwindingkeitR(
		Kreuzung_AttributeGroup group) {
		return '''«group?.geschwindigkeitR?.wert»'''
	}

	private def List<Regelzeichnung> getRegelzeichnungen(
		W_Kr_Gsp_Element element) {
		val result = new LinkedList<Regelzeichnung>

		if (element.IDRegelzeichnung !== null) {
			result.add(element.regelzeichnung)
		}
		result.addAll(
			element.WKrGspKomponenten.map[regelzeichnungen].flatten.filterNull
		)

		return result
	}

	private def List<MultiColorContent> transformMultiColorContent(
		Iterable<W_Kr_Gsp_Komponente> components,
		(W_Kr_Gsp_Komponente)=>BigInteger actuatorNumberSelector,
		(W_Kr_Gsp_Komponente)=>Elektrischer_Antrieb_Lage_TypeClass actuatorPositionSelector,
		(W_Kr_Gsp_Komponente)=>Boolean fillPositionSupplementCondition
	) {
		return components.map [
			val multiColorContent = TablemodelFactory.eINSTANCE.
				createMultiColorContent
			// Only rendered multicolor by DIFF state
			multiColorContent.disableMultiColor = true
			val actuator = actuatorNumberSelector.apply(it)
			if (actuator === null) {
				multiColorContent.multiColorValue = ""
				multiColorContent.stringFormat = ""
				return multiColorContent
			}
			val noOfActuators = actuator.intValue
			val position = it.getPosition(actuator, actuatorPositionSelector)
			val fillPositionCondition = noOfActuators > 0 &&
				position !== null && fillPositionSupplementCondition.apply(it)
			if (austauschAntriebe?.wert !== null && austauschAntriebe?.wert) {
				multiColorContent.multiColorValue = noOfActuators.toString
				multiColorContent.stringFormat = '''%s«IF fillPositionCondition» («position»)«ENDIF»'''
			} else {
				multiColorContent.multiColorValue = null
				multiColorContent.stringFormat = '''«noOfActuators»«IF fillPositionCondition» («position»)«ENDIF»'''
			}

			return multiColorContent
		].filterNull.toList
	}

	private def String getPosition(
		W_Kr_Gsp_Komponente component,
		BigInteger actuator,
		(W_Kr_Gsp_Komponente)=>Elektrischer_Antrieb_Lage_TypeClass actuatorPositionSelector
	) {

		if (actuator !== null && actuator != BigInteger.ZERO) {
			val lage = actuatorPositionSelector.apply(component)
			val enumTranslateValue = lage?.translate
			if (enumTranslateValue === null) {
				if (xmlFinder !== null && xmlFinder.isNilValue(lage)) {
					return "keine Lage"
				}
				return ""
			}
			return enumTranslateValue
		}
		return null
	}

	private def String getGleissperreAntrieb(W_Kr_Gsp_Element element) {
		return element.WKrGspElementAllg.WKrGspStellart.wert ===
			ENUMW_KR_GSP_STELLART_ELEKTRISCH_FERNGESTELLT ? "1" : "0"
	}
}
