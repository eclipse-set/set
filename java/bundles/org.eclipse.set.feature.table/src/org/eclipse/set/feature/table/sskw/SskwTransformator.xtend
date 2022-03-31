/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sskw

import de.scheidtbachmann.planpro.model.model1902.BasisTypen.ENUMLinksRechts
import de.scheidtbachmann.planpro.model.model1902.Gleis.Gleis_Abschnitt
import de.scheidtbachmann.planpro.model.model1902.Regelzeichnung.Regelzeichnung
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.ENUMElementLage
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.Kreuzung_AttributeGroup
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.Zungenpaar_AttributeGroup
import java.math.BigInteger
import java.util.LinkedList
import java.util.List
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.utils.table.TMFactory
import org.eclipse.set.feature.table.messages.MessagesWrapper
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.format.TextAlignment
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static de.scheidtbachmann.planpro.model.model1902.BasisTypen.ENUMLinksRechts.*
import static de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.ENUMWKrArt.*
import static de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.ENUMWKrGspStellart.*
import static java.lang.Boolean.*

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GleisAbschnittExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspKomponenteExtensions.*

/**
 * Table transformation for a Weichentabelle (SSKW).
 * 
 * @author Schaefer
 */
class SskwTransformator extends AbstractPlanPro2TableModelTransformator {

	SskwColumns cols

	new(SskwColumns columns, MessagesWrapper messagesWrapper) {
		super(messagesWrapper)
		this.cols = columns;
	}

	static val Logger logger = LoggerFactory.getLogger(
		typeof(SskwTransformator))

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
			fill(instance, cols.basis_bezeichnung, element, [
				bezeichnung.bezeichnungTabelle.wert
			])
			if (logger.debugEnabled) {
				logger.debug(element.bezeichnung.bezeichnungTabelle.wert)
			}

			// B: Sskw.Weiche_Kreuzung_Gleissperre_Sonderanlage.Bezeichnung
			fillConditional(instance, cols.form,
				element, [IDWKrAnlage !== null], [
					val wKrAnlageAllg = WKrAnlage.WKrAnlageAllg
					val art = wKrAnlageAllg.WKrArt.wert
					// remove redundant art in grundform
					val grundform = wKrAnlageAllg.WKrGrundform.wert.
						replaceFirst("^" + art + " *", "")
					'''«art» «grundform»'''
				])

			// C: Freimeldung.Fma
			fillIterable(
				instance,
				cols.fma,
				element,
				[
					WKrGspKomponenten.map[fmaAnlage].flatten.toSet.map [
						bzBezeichner
					]
				],
				MIXED_STRING_COMPARATOR
			)

			// D: Freimeldung.nicht_grenzzeichenfrei.Links
			fillSwitch(
				instance,
				cols.links,
				element,
				new Case<W_Kr_Gsp_Element>([
					weicheElement?.GZFreimeldungL?.elementLage?.wert ===
						ENUMElementLage.ENUM_ELEMENT_LAGE_ABSCHNITT
				], [
					(weicheElement?.GZFreimeldungL?.
						element as Gleis_Abschnitt)?.fmaAnlagen.map [
						bzBezeichner
					].getIterableFilling(MIXED_STRING_COMPARATOR)
				]),
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

			// E: Freimeldung.nicht_grenzzeichenfrei.Rechts
			fillSwitch(
				instance,
				cols.rechts,
				element,
				new Case<W_Kr_Gsp_Element>([
					weicheElement?.GZFreimeldungR?.elementLage?.wert ===
						ENUMElementLage.ENUM_ELEMENT_LAGE_ABSCHNITT
				], [
					(weicheElement?.GZFreimeldungR?.
						element as Gleis_Abschnitt)?.fmaAnlagen?.map [
						bzBezeichner
					].getIterableFilling(MIXED_STRING_COMPARATOR)
				]),
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

			// F: Freimeldung.Isolierfall
			fillConditional(
				instance,
				cols.isolierfall,
				element,
				[IDWKrAnlage !== null],
				[WKrAnlage.WKrAnlageAllg?.isolierfall?.wert ?: ""]
			)

			// G: Vorzugslage.Lage
			fillSwitch(
				instance,
				cols.lage,
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

			// H: Vorzugslage.Automatik
			fillSwitch(
				instance,
				cols.automatik,
				element,
				new Case<W_Kr_Gsp_Element>([
					WKrGspElementAllg?.vorzugslageAutomatik?.wert
				], ["x"]),
				new Case<W_Kr_Gsp_Element>([
					weicheElement?.weicheVorzugslage !== null &&
						(		WKrGspElementAllg.vorzugslageAutomatik ===
							null || !WKrGspElementAllg.vorzugslageAutomatik.wert
				 	)
				], ["o"]),
				new Case<W_Kr_Gsp_Element>([
					gleissperreElement?.gleissperreVorzugslage !== null &&
						(		WKrGspElementAllg.vorzugslageAutomatik ===
							null || !WKrGspElementAllg.vorzugslageAutomatik.wert
				 	)
				], ["o"])
			)

			// I: Weiche.Auffahrortung
			fill(
				instance,
				cols.auffahrortung,
				element,
				[weicheElement?.auffahrortung?.wert?.translate ?: ""]
			)

			// J: Weiche.Antriebe
			val elementKomponenten = element.container.WKrGspKomponente.filter [
				IDWKrGspElement.wert == element.identitaet.wert &&
					zungenpaar !== null
			]
			fill(
				instance,
				cols.weiche_antriebe,
				element,
				[
					fillNoOfActuators( //
						elementKomponenten, //
						[zungenpaar?.elektrischerAntriebAnzahl?.wert], //
						[zungenpaar?.elektrischerAntriebLage?.wert]
					)
				]
			)

			// K: Weiche.Weichensignal
			val weichensignal = elementKomponenten.map [
				zungenpaar?.weichensignal?.wert
			].filterNull
			fill(
				instance,
				cols.weichensignal,
				element,
				[weichensignal.map[translate].toSet.getIterableFilling(null)]
			)

			// L: Weiche.Pruefkontakte
			val pruefkontakte = elementKomponenten.map [
				zungenpaar?.zungenpruefkontaktAnzahl?.wert
			].filterNull.map[intValue]
			fillConditional(
				instance,
				cols.pruefkontakte,
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
			// M: Weiche.v_zul_W.Links
			val wKrGspKomponenten = element.WKrGspKomponenten

			fillSwitch(
				instance,
				cols.v_zul_w_links,
				element,
				new Case<W_Kr_Gsp_Element>(
					[art_ew_abw_ibw_kloth_dw_sonstige_mit_zungenpaar],
					[
						wKrGspKomponenten.map[zungenpaar].printGeschwindingkeitL
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[art_ekw],
					[
						wKrGspKomponenten.filter [
							zungenpaar?.kreuzungsgleis?.wert ==
								ENUM_LINKS_RECHTS_RECHTS
						].map[zungenpaar].toList.printGeschwindingkeitL
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[art_dkw],
					[
						wKrGspKomponenten.filter [
							zungenpaar?.kreuzungsgleis?.wert ==
								ENUM_LINKS_RECHTS_RECHTS
						].map[zungenpaar].toList.printGeschwindingkeitL
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

			// N: Weiche.v_zul_W.Rechts
			fillSwitch(
				instance,
				cols.v_zul_w_rechts,
				element,
				new Case<W_Kr_Gsp_Element>(
					[art_ew_abw_ibw_kloth_dw_sonstige_mit_zungenpaar],
					[
						wKrGspKomponenten.map[zungenpaar].printGeschwindingkeitR
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[art_ekw],
					[
						wKrGspKomponenten.filter [
							zungenpaar?.kreuzungsgleis?.wert ==
								ENUM_LINKS_RECHTS_LINKS
						].map[zungenpaar].toList.printGeschwindingkeitR
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[art_dkw],
					[
						wKrGspKomponenten.filter [
							zungenpaar?.kreuzungsgleis?.wert ==
								ENUM_LINKS_RECHTS_LINKS
						].map[zungenpaar].toList.printGeschwindingkeitR
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

			// O: Kreuzung.v_zul_K.Links
			val krLinksKomponenten = wKrGspKomponenten.filter [
				zungenpaar?.kreuzungsgleis?.wert == ENUM_LINKS_RECHTS_LINKS
			]
			val exKrLinksKomponenten = !krLinksKomponenten.empty
			fillSwitch(
				instance,
				cols.v_zul_k_links,
				element,
				new Case<W_Kr_Gsp_Element>(
					[art_ew_abw_ibw_kloth_dw_sonstige_mit_zungenpaar],
					[""]
				),
				new Case<W_Kr_Gsp_Element>(
					[art_ekw && exKrLinksKomponenten],
					[
						krLinksKomponenten.map[zungenpaar].toList.
							printGeschwindingkeitL
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[art_ekw && !exKrLinksKomponenten],
					[
						WKrAnlage.WKrGspElemente.filter [
							identitaet.wert != element.identitaet.wert
						].map[WKrGspKomponenten].flatten.map[zungenpaar].toList.
							printGeschwindingkeitL
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[art_dkw && exKrLinksKomponenten],
					[
						krLinksKomponenten.map[zungenpaar].toList.
							printGeschwindingkeitL
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[art_kr_flachkreuzung_sonstige_mit_kreuzung],
					[
						wKrGspKomponenten.map[kreuzung].toList.
							printGeschwindingkeitL
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[keine_anlage],
					[""]
				)
			)

			// P: Kreuzung.v_zul_K.Rechts
			val krRechtsKomponenten = wKrGspKomponenten.filter [
				zungenpaar?.kreuzungsgleis?.wert == ENUM_LINKS_RECHTS_RECHTS
			]
			val exKrRechtsKomponenten = !krRechtsKomponenten.empty
			fillSwitch(
				instance,
				cols.v_zul_k_rechts,
				element,
				new Case<W_Kr_Gsp_Element>(
					[art_ew_abw_ibw_kloth_dw_sonstige_mit_zungenpaar],
					[""]
				),
				new Case<W_Kr_Gsp_Element>(
					[art_ekw && exKrRechtsKomponenten],
					[
						WKrAnlage.WKrGspElemente.filter [
							identitaet.wert != element.identitaet.wert
						].map[WKrGspKomponenten].flatten.map[zungenpaar].toList.
							printGeschwindingkeitR
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[art_ekw && !exKrRechtsKomponenten],
					[
						krRechtsKomponenten.map[zungenpaar].toList.
							printGeschwindingkeitR
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[art_dkw && exKrRechtsKomponenten],
					[
						krRechtsKomponenten.map[zungenpaar].toList.
							printGeschwindingkeitR
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[art_kr_flachkreuzung_sonstige_mit_kreuzung],
					[
						wKrGspKomponenten.map[kreuzung].toList.
							printGeschwindingkeitR
					]
				),
				new Case<W_Kr_Gsp_Element>(
					[keine_anlage],
					[""]
				)
			)

			// Q: Herzstueck.Antriebe
			val herzstueckAntriebe = wKrGspKomponenten.map [
				zungenpaar?.herzstueckAntriebe?.wert
			].filterNull.map[intValue]
			val elektrischerAntriebAnzahl = wKrGspKomponenten.map [
				kreuzung?.elektrischerAntriebAnzahl?.wert
			].filterNull.map[intValue]
			fillSwitch(
				instance,
				cols.herzstuecke_antriebe,
				element,
				new Case<W_Kr_Gsp_Element>(
					[herzstueckAntriebe.exists[it > 0]],
					[herzstueckAntriebe.fold(0, [s, a|s + a]).toString]
				),
				new Case<W_Kr_Gsp_Element>(
					[elektrischerAntriebAnzahl.exists[it > 0]],
					[
						fillNoOfActuators( //
							wKrGspKomponenten, //
							[kreuzung?.elektrischerAntriebAnzahl?.wert], //
							[kreuzung?.elektrischerAntriebLage?.wert]
						)
					]
				)
			)

			val entgleisungsschuhe = element.WKrGspKomponenten.filter [
				entgleisungsschuh !== null
			]
			val exEntgleisungsschuh = !entgleisungsschuhe.empty

			// R: Gleissperre.Antriebe
			fillSwitch(
				instance,
				cols.gleissperre_antriebe,
				element,
				new Case<W_Kr_Gsp_Element>(
					[
						exEntgleisungsschuh &&
							WKrGspElementAllg?.WKrGspStellart?.wert ==
								ENUMW_KR_GSP_STELLART_ELEKTRISCH_FERNGESTELLT
					],
					["1"]
				),
				new Case<W_Kr_Gsp_Element>(
					[exEntgleisungsschuh],
					["0"]
				)
			)

			// S: Gleissperre.Gsp_signal
			fillIterable(
				instance,
				cols.gsp_signal,
				element,
				[
					WKrGspKomponenten.map[entgleisungsschuh].filterNull.map [
						gleissperrensignal?.wert?.translate ?: "o"
					].toSet
				],
				null
			)

			// T: Gleissperre.Auswurfrichtung
			fillConditional(
				instance,
				cols.auswurfrichtung,
				element,
				[exEntgleisungsschuh],
				[
					'''«FOR r : WKrGspKomponenten.map[entgleisungsschuh.auswurfrichtung.wert].toSet SEPARATOR ", "»«r»«ENDFOR»'''
				]
			)

			// U: Gleissperre.Schutzschiene
			fillSwitch(
				instance,
				cols.schutzschiene,
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

			// V: Sonstiges.Regelzeichnung_Nr
			fillIterable(
				instance,
				cols.regelzeichnung_nr,
				element,
				[element.regelzeichnungen.map[fillRegelzeichnung]],
				null
			)

			// W: Sonstiges.DWs
			fillSwitch(
				instance,
				cols.dws,
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
				cols.Sonderanlage_Art,
				element,
				[
					wKrGspKomponenten.map [
						besonderesFahrwegelement?.wert?.translate
					].filterNull
				],
				null
			)

			// Y: Bemerkung
			fill(
				instance,
				cols.basis_bemerkung,
				element,
				[footnoteTransformation.transform(it, instance)]
			)
		}

		return factory.table
	}

	private def dispatch String printGeschwindingkeitL(Object group) {
		throw new IllegalArgumentException(group.toString)
	}

	private def dispatch String printGeschwindingkeitL(List<?> group) {
		return group.map[printGeschwindingkeitL].getIterableFilling(
			ToolboxConstants.NUMERIC_COMPARATOR,
			", "
		)
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

	private def dispatch String printGeschwindingkeitR(List<?> group) {
		return group.map[printGeschwindingkeitR].getIterableFilling(
			ToolboxConstants.NUMERIC_COMPARATOR,
			", "
		)
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

	private def String fillNoOfActuators(
		Iterable<W_Kr_Gsp_Komponente> components,
		(W_Kr_Gsp_Komponente)=>BigInteger actuatorNumberSelector,
		(W_Kr_Gsp_Komponente)=>ENUMLinksRechts actuatorPositionSelector
	) {
		val noOfActuators = components.getNoOfActuators(actuatorNumberSelector)
		val position = components.getPosition(actuatorNumberSelector,
			actuatorPositionSelector)

		return '''«noOfActuators»«IF noOfActuators > 0» («position»)«ENDIF»'''
	}

	private def int getNoOfActuators(
		Iterable<W_Kr_Gsp_Komponente> components,
		(W_Kr_Gsp_Komponente)=>BigInteger actuatorNumberSelector
	) {
		return components.map[actuatorNumberSelector.apply(it)].filterNull.map [
			intValue
		].fold(0, [s, n|s + n])
	}

	private def String getPosition(
		Iterable<W_Kr_Gsp_Komponente> components,
		(W_Kr_Gsp_Komponente)=>BigInteger actuatorNumberSelector,
		(W_Kr_Gsp_Komponente)=>ENUMLinksRechts actuatorPositionSelector
	) {
		return components.map [
			getPosition(actuatorNumberSelector, actuatorPositionSelector)
		].filterNull.iterableFilling
	}

	private def String getPosition(
		W_Kr_Gsp_Komponente component,
		(W_Kr_Gsp_Komponente)=>BigInteger actuatorNumberSelector,
		(W_Kr_Gsp_Komponente)=>ENUMLinksRechts actuatorPositionSelector
	) {
		if (actuatorNumberSelector.apply(component) != BigInteger.ZERO) {
			return actuatorPositionSelector.apply(component).translate ?:
				"keine Lage"
		} else {
			return null
		}
	}

	override void formatTableContent(Table table) {
		// A: Weiche_Kreuzung_Gleissperre.Bezeichnung
		table.setTextAlignment(0, TextAlignment.LEFT);

		// X: Bemerkung
		table.setTextAlignment(23, TextAlignment.LEFT);
	}
}
