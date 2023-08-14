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
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.MultiColorContent
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.TablemodelFactory
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.toolboxmodel.BasisTypen.ENUMLinksRechts
import org.eclipse.set.toolboxmodel.Gleis.Gleis_Abschnitt
import org.eclipse.set.toolboxmodel.Regelzeichnung.Regelzeichnung
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.ENUMElektrischerAntriebLage
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.ENUMElementLage
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.Kreuzung_AttributeGroup
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.Zungenpaar_AttributeGroup
import org.eclipse.set.utils.table.TMFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static java.lang.Boolean.*
import static org.eclipse.set.feature.table.pt1.sskw.SskwColumns.*
import static org.eclipse.set.toolboxmodel.BasisTypen.ENUMLinksRechts.*
import static org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.ENUMWKrArt.*
import static org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.ENUMWKrGspStellart.*

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GleisAbschnittExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalbegriffExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspKomponenteExtensions.*
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.toolboxmodel.Signalbegriffe_Ril_301.Zs3

/**
 * Table transformation for a Weichentabelle (SSKW).
 * 
 * @author Schaefer
 */
class SskwTransformator extends AbstractPlanPro2TableModelTransformator {

	static val Logger logger = LoggerFactory.getLogger(
		typeof(SskwTransformator))

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService) {
		super(cols, enumTranslationService)
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
		TMFactory factory) {
		val weichen = container.WKrGspElement

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

			// B: Sskw.Weiche_Kreuzung_Gleissperre_Sonderanlage.Form
			fillConditional(
				instance,
				cols.getColumn(Form),
				element,
				[IDWKrAnlage !== null],
				[
					val wKrAnlageAllg = WKrAnlage.WKrAnlageAllg
					val art = wKrAnlageAllg.WKrArt.wert
					// remove redundant art in grundform
					val grundform = wKrAnlageAllg.WKrGrundform.wert.
						replaceFirst("^" + art + " *", "")
					'''«art» «grundform»'''
				]
			)

			// C: Sskw.Freimeldung.Fma
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

			// D: Sskw.Freimeldung.nicht_grenzzeichenfrei.Links
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

			// E: Sskw.Freimeldung.nicht_grenzzeichenfrei.Rechts
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

			// F: Sskw.Freimeldung.Isolierfall
			fillConditional(
				instance,
				cols.getColumn(Isolierfall),
				element,
				[IDWKrAnlage !== null],
				[WKrAnlage.WKrAnlageAllg?.isolierfall?.wert ?: ""]
			)

			// G: Sskw.Vorzugslage.Lage
			fillSwitch(
				instance,
				cols.getColumn(Vorzugslage_Lage),
				element,
				new Case<W_Kr_Gsp_Element>(
					[weicheElement?.weicheVorzugslage?.wert !== null],
					[weicheElement.weicheVorzugslage.wert.translate]
				),
				new Case<W_Kr_Gsp_Element>(
					[gleissperreElement?.gleissperreVorzugslage?.wert !== null],
					[gleissperreElement.gleissperreVorzugslage.wert.translate]
				)
			)

			// H: Sskw.Vorzugslage.Automatik
			fillConditional(
				instance,
				cols.getColumn(Vorzugslage_Automatik),
				element,
				[WKrGspElementAllg?.vorzugslageAutomatik?.wert !== null],
				[WKrGspElementAllg.vorzugslageAutomatik.wert ? "x" : "o"],
				[""]
			)

			// I: Sskw.Weiche.Auffahrortung
			fill(
				instance,
				cols.getColumn(Weiche_Auffahrortung),
				element,
				[weicheElement?.auffahrortung?.wert?.translate ?: ""]
			)

			// J: Sskw.Weiche.Antriebe
			val elementKomponenten = element.container.WKrGspKomponente.filter [
				IDWKrGspElement?.identitaet?.wert == element.identitaet.wert &&
					zungenpaar !== null
			].toList

			fillMultiColorIterable(
				instance,
				cols.getColumn(Weiche_Antriebe),
				element,
				[
					transformMultiColorContent(
						elementKomponenten,
						[zungenpaar?.elektrischerAntriebAnzahl?.wert],
						[zungenpaar?.elektrischerAntriebLage?.wert]
					)
				],
				"+"
			)

			// K: Sskw.Weiche.Weichensignal
			val weichensignal = elementKomponenten.map [
				zungenpaar?.weichensignal?.wert
			].filterNull
			fillIterable(
				instance,
				cols.getColumn(Weiche_Weichensignal),
				element,
				[weichensignal.map[translate].toSet],
				null
			)

			// L: Sskw.Weiche.Pruefkontakte
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

			val isPMaxL = element.isGeschwindigkeitPMax(element.topKanteL)
			val isPMaxR = element.isGeschwindigkeitPMax(element.topKanteR)

			// M: Sskw.Weiche.v_zul_W.Links
			val wKrGspKomponenten = element.WKrGspKomponenten
			fillSwitch(
				instance,
				cols.getColumn(Weiche_v_zul_W_Links),
				element,
				fillingIterableCase(
					[art_ew_abw_ibw_kloth_dw_sonstige_mit_zungenpaar],
					[
						wKrGspKomponenten.map[zungenpaar].
							printGeschwindingkeitL(isPMaxL)
					]
				),
				fillingIterableCase(
					[art_ekw],
					[
						wKrGspKomponenten.filter [
							zungenpaar?.kreuzungsgleis?.wert ==
								ENUM_LINKS_RECHTS_RECHTS
						].map[zungenpaar].toList.printGeschwindingkeitL(isPMaxL)
					]
				),
				fillingIterableCase(
					[art_dkw],
					[
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

			// N: Sskw.Weiche.v_zul_W.Rechts
			fillSwitch(
				instance,
				cols.getColumn(Weiche_v_zul_W_Rechts),
				element,
				fillingIterableCase(
					[art_ew_abw_ibw_kloth_dw_sonstige_mit_zungenpaar],
					[
						wKrGspKomponenten.map [
							zungenpaar
						].printGeschwindingkeitR(isPMaxR)
					]
				),
				fillingIterableCase(
					[art_ekw],
					[
						wKrGspKomponenten.filter [
							zungenpaar?.kreuzungsgleis?.wert ==
								ENUM_LINKS_RECHTS_LINKS
						].map[zungenpaar].toList.printGeschwindingkeitR(isPMaxR)
					]
				),
				fillingIterableCase(
					[art_dkw],
					[
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

			// O: Sskw.Kreuzung.v_zul_K.Links
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
						getKreuzungEKWGroup(wKrGspKomponenten,
							ENUM_LINKS_RECHTS_LINKS).
							printGeschwindingkeitL(isPMaxL)
					]
				),
				fillingIterableCase(
					[art_dkw && exKrLinksKomponenten],
					[
						krLinksKomponenten.map [
							zungenpaar
						].toList.printGeschwindingkeitL(isPMaxL)
					]
				),
				fillingIterableCase(
					[art_kr_flachkreuzung_sonstige_mit_kreuzung],
					[
						wKrGspKomponenten.map[kreuzung].
							printGeschwindingkeitL(isPMaxL)
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[keine_anlage],
					[""]
				)
			)

			// P: Sskw.Kreuzung.v_zul_K.Rechts
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
						getKreuzungEKWGroup(wKrGspKomponenten,
							ENUM_LINKS_RECHTS_RECHTS).
							printGeschwindingkeitR(isPMaxR)
					]
				),
				fillingIterableCase(
					[art_dkw && exKrRechtsKomponenten],
					[
						krRechtsKomponenten.map [
							zungenpaar
						].toList.printGeschwindingkeitR(isPMaxR)
					]
				),
				fillingIterableCase(
					[art_kr_flachkreuzung_sonstige_mit_kreuzung],
					[
						wKrGspKomponenten.map[kreuzung].
							printGeschwindingkeitR(isPMaxR)
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[keine_anlage],
					[""]
				)
			)

			// Q: Sskw.Herzstueck.Antriebe
			instance.fillHerzstueckAntriebe(element)

			val entgleisungsschuhe = element.WKrGspKomponenten.filter [
				entgleisungsschuh !== null
			]
			val exEntgleisungsschuh = !entgleisungsschuhe.empty
			// R: Sskw.Gleissperre.Antriebe
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
					val gleissperreAntrieb = gleissperreAntrieb
					if (entgleisungsschuhe.exists [
						austauschAntriebe?.wert === true
					]) {
						multiColorContent.multiColorValue = gleissperreAntrieb
						multiColorContent.stringFormat = "%s"
					} else {
						multiColorContent.multiColorValue = null
						multiColorContent.stringFormat = gleissperreAntrieb
					}
					return multiColorContent
				]
			)

			// S: Sskw.Gleissperre.Gsp_signal
			fillIterable(
				instance,
				cols.getColumn(Gleissperre_Gsp_signal),
				element,
				[
					WKrGspKomponenten.map[entgleisungsschuh].filterNull.map [
						gleissperrensignal?.wert?.translate ?: "o"
					].toSet
				],
				null
			)

			// T: Sskw.Gleissperre.Auswurfrichtung
			fillIterableWithConditional(
				instance,
				cols.getColumn(Gleissperre_Auswurfrichtung),
				element,
				[exEntgleisungsschuh],
				[
					WKrGspKomponenten.map [
						entgleisungsschuh.auswurfrichtung.wert.literal
					].toSet
				],
				null,
				", "
			)

			// U: Sskw.Gleissperre.Schutzschiene
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

			// V: Sskw.Sonstiges.Regelzeichnung_Nr
			fillIterable(
				instance,
				cols.getColumn(Sonstiges_Regelzeichnung_Nr),
				element,
				[element.regelzeichnungen.map[fillRegelzeichnung]],
				null
			)

			// W: Sskw.Sonstiges.DWs
			fillSwitch(
				instance,
				cols.getColumn(Sonstiges_DWs),
				element,
				new Case<W_Kr_Gsp_Element>(
					[IDWKrAnlage === null],
					[""]
				),
				new Case<W_Kr_Gsp_Element>(
					[WKrAnlage.IDAnhangDWS !== null],
					["x"]
				),
				new Case<W_Kr_Gsp_Element>(
					[WKrAnlage.IDAnhangDWS === null],
					["o"]
				)
			)

			// X: Sskw.Sonderanlage.Art
			fillIterable(
				instance,
				cols.getColumn(Sonderanlage_Art),
				element,
				[
					wKrGspKomponenten.map [
						besonderesFahrwegelement?.wert?.translate
					].filterNull
				],
				null
			)

			// Y: Sskw.Bemerkung
			fill(
				instance,
				cols.getColumn(Bemerkung),
				element,
				[footnoteTransformation.transform(it, instance)]
			)
		}

		return factory.table
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
			fill(
				row,
				cols.getColumn(Herzstueck_Antriebe),
				element,
				[herzstueckAntriebe.fold(0, [s, a|s + a]).toString]
			)
		} else if (elektrischerAntriebAnzahl.exists[it > 0]) {
			fillMultiColorIterable(
				row,
				cols.getColumn(Herzstueck_Antriebe),
				element,
				[
					transformMultiColorContent(
						WKrGspKomponenten,
						[kreuzung?.elektrischerAntriebAnzahl?.wert],
						[kreuzung?.elektrischerAntriebLage?.wert]
					)
				],
				"+"
			)
		} else {
			fill(
				row,
				cols.getColumn(Herzstueck_Antriebe),
				element,
				[]
			)
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
				IDSignalSignalbegriff.hasSignalbegriffID(Zs3)
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
		(W_Kr_Gsp_Komponente)=>ENUMElektrischerAntriebLage actuatorPositionSelector
	) {
		return components.map [
			val multiColorContent = TablemodelFactory.eINSTANCE.
				createMultiColorContent
			val actuator = actuatorNumberSelector.apply(it)
			val noOfActuators = actuator !== null ? actuator.intValue : 0
			val position = it.getPosition(actuatorNumberSelector,
				actuatorPositionSelector)
			if (austauschAntriebe?.wert === true) {
				multiColorContent.multiColorValue = noOfActuators.toString
				multiColorContent.stringFormat = '''%s«IF noOfActuators >0» («position»)«ENDIF»'''
			} else {
				multiColorContent.multiColorValue = null
				multiColorContent.stringFormat = '''«noOfActuators»«IF noOfActuators > 0» («position»)«ENDIF»'''
			}

			return multiColorContent
		].filterNull.toList
	}

	private def String getPosition(
		W_Kr_Gsp_Komponente component,
		(W_Kr_Gsp_Komponente)=>BigInteger actuatorNumberSelector,
		(W_Kr_Gsp_Komponente)=>ENUMElektrischerAntriebLage actuatorPositionSelector
	) {
		if (actuatorNumberSelector.apply(component) != BigInteger.ZERO) {
			return actuatorPositionSelector.apply(component).translate ?:
				"keine Lage"
		} else {
			return null
		}
	}

	private def String getGleissperreAntrieb(W_Kr_Gsp_Element element) {
		return element.WKrGspElementAllg.WKrGspStellart.wert ===
			ENUMW_KR_GSP_STELLART_ELEKTRISCH_FERNGESTELLT ? "1" : "0"
	}
}
