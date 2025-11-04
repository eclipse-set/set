/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sslz

import java.util.Collection
import java.util.Collections
import java.util.List
import java.util.Set
import org.eclipse.set.basis.MixedStringComparator
import org.eclipse.set.basis.Wrapper
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Fahrstrasse.ENUMFstrMittelArt
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Abhaengigkeit
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Signalisierung
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.model.planpro.Gleis.ENUMGleisart
import org.eclipse.set.model.planpro.Gleis.Gleis_Abschnitt
import org.eclipse.set.model.planpro.Gleis.Gleis_Bezeichnung
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage
import org.eclipse.set.model.planpro.Ortung.Zugeinwirkung
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Hp0
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Kl
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.ZlO
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.ZlU
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs13
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs2
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs2v
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs3
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs3v
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs6
import org.eclipse.set.model.planpro.Signalbegriffe_Struktur.Signalbegriff_ID_TypeClass
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Signale.Signal_Signalbegriff
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.extensions.Utils
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import static org.eclipse.set.feature.table.pt1.sslz.SslzColumns.*
import static org.eclipse.set.model.planpro.Bahnuebergang.ENUMBUESicherungsart.*
import static org.eclipse.set.model.planpro.Fahrstrasse.ENUMFstrZugArt.*
import static org.eclipse.set.model.planpro.Gleis.ENUMGleisart.*

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienAnzeigeElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrAbhaengigkeitExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrNichthaltfallExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrSignalisierungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SchaltmittelZuordnungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalRahmenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalbegriffExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.Debug.*

/**
 * Table transformation for a Zugstraßentabelle (SSLZ).
 * 
 * @author Rumpf
 */
class SslzTransformator extends AbstractPlanPro2TableModelTransformator {

	static val Logger logger = LoggerFactory.getLogger(
		typeof(SslzTransformator))

	static val SIGNALBEGRIFF_COMPARATOR = new MixedStringComparator(
		"(?<letters1>[A-Za-z]*)(?<number1>[0-9]*)(?<letters2>[A-Za-z]*)(\\((?<number2>[0-9]*)(?<letters3>[A-Za-z]*)\\))?")

	static val List<ENUMGleisart> NOT_USABLE = #[ENUM_GLEISART_ANSCHLUSSGLEIS,
		ENUM_GLEISART_NEBENGLEIS, ENUM_GLEISART_SONSTIGE]

	static val String EMPTY_FILLING = ""

	static val String WARNING_SYMBOL = "\u26A0"

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory, Stell_Bereich controlArea) {
		val fstrZugRangierList = container.fstrZugRangier.filter [
			isPlanningObject
		].filterObjectsInControlArea(controlArea)
		val fstrZugRangierListSorted = fstrZugRangierList
		var current = 0

		for (fstrZugRangier : fstrZugRangierListSorted) {
			current++

			if (Thread.currentThread.interrupted) {
				return null
			}
			// Generalbedingung
			val isZ = fstrZugRangier.isZ

			if (isZ) {
				val instance = factory.newTableRow(fstrZugRangier)

				// A: Sslz.Grundsatzangaben.Bezeichnung
				fill(
					instance,
					cols.getColumn(Grundsatzangaben_Bezeichnung),
					fstrZugRangier,
					[getZugFstrBezeichnung([isZ(it)])]
				)

				// B: Sslz.Grundsatzangaben.Fahrweg.Start
				fill(instance, cols.getColumn(Start), fstrZugRangier, [
					fahrwegStart
				])

				// C: Sslz.Grundsatzangaben.Fahrweg.Ziel
				fillConditional(instance, cols.getColumn(Ziel),
					fstrZugRangier, [
						val zielSignal = IDFstrFahrweg?.value.IDZiel?.value
						return zielSignal.container.contents.filter(
							Fstr_Zug_Rangier).findFirst [
							fstrZug?.fstrZugArt?.wert === ENUM_FSTR_ZUG_ART_B &&
								IDFstrFahrweg?.value?.IDStart?.value ==
									zielSignal
						] === null
					], [fahrwegZiel], [fahrwegZielBlock])

				// D: Sslz.Grundsatzangaben.Fahrweg.Nummer
				fill(
					instance,
					cols.getColumn(Nummer),
					fstrZugRangier,
					[fahrwegNummer]
				)

				// E: Sslz.Grundsatzangaben.Fahrweg.Entscheidungsweiche
				fillIterable(
					instance,
					cols.getColumn(Entscheidungsweiche),
					fstrZugRangier,
					[getEntscheidungsweichen(NOT_USABLE).map[bezeichnung]],
					MIXED_STRING_COMPARATOR,
					[it]
				)

				// F: Sslz.Grundsatzangaben.Durchrutschweg.Bezeichnung
				fill(instance, cols.getColumn(Durchrutschweg_Bezeichnung),
					fstrZugRangier, [
						val bezeichnung = fstrZugRangier?.fstrDWeg?.
							bezeichnung?.bezeichnungFstrDWeg?.wert
						if (bezeichnung === null)
							return null

						val vorzug = fstrZugRangier?.fstrZug?.fstrZugDWeg?.
							DWegVorzug?.wert
						if (vorzug === null)
							return '''«bezeichnung» «WARNING_SYMBOL»'''
						if (vorzug)
							return '''«bezeichnung»*'''
						else
							return bezeichnung
					])

				// G: Sslz.Grundsatzangaben.Art
				fill(instance,
					cols.getColumn(Art),
					fstrZugRangier,
					[fstrZugArt]
				)

				// H: Sslz.Einstellung.Autom_Einstellung
				fill(instance, cols.getColumn(Autom_Einstellung),
					fstrZugRangier, [
						fstrZugRangier?.fstrZug?.automatischeEinstellung?.
							translate ?: ""
					])

				// I: Sslz.Einstellung.F_Bedienung
				fill(
					instance,
					cols.getColumn(F_Bedienung),
					fstrZugRangier,
					[
						fstrZugRangier?.fstrZugRangierAllg?.FBedienung?.wert?.
							translate ?: ""
					]
				)

				// J: Sslz.Abhaengigkeiten.Inselgleis
				fillIterable(
					instance,
					cols.getColumn(Inselgleis),
					fstrZugRangier?.fstrFahrweg?.zielSignal?.
						zgFahrtGleichzeitigVerbot ?: Collections.emptySet,
					[fillInselgleis],
					MIXED_STRING_COMPARATOR
				)

				// K: Sslz.Abhaengigkeiten.Ueberwachte_Ssp
				fillIterable(
					instance,
					cols.getColumn(Ueberwachte_Ssp),
					fstrZugRangier,
					[
						fstrFahrweg?.abhaengigkeiten?.map [
							schluesselsperre?.bezeichnung?.bezeichnungTabelle?.
								wert
						]
					],
					MIXED_STRING_COMPARATOR
				)

				if (logger.debugEnabled) {
					logger.
						debug('''IDBUEEinschaltung=«fstrZugRangier.fstrZug?.IDBUEEinschaltung»''')
				}

				// L: Sslz.Abhaengigkeiten.Abhaengiger_BUe
				fillIterable(
					instance,
					cols.getColumn(Abhaengiger_BUe),
					fstrZugRangier,
					[
						(fstrFahrweg.BUes + fstrZugRangier.BUesImGefahrraum).
							filter [
								!#{
									ENUMBUE_SICHERUNGSART_P,
									ENUMBUE_SICHERUNGSART_PUND_LF,
									ENUMBUE_SICHERUNGSART_UE,
									ENUMBUE_SICHERUNGSART_UE_UND_P
								}.contains(
									BUEAnlageAllg?.BUESicherungsart?.wert)
							].toSet.map[bezeichnung?.bezeichnungTabelle?.wert]
					],
					MIXED_STRING_COMPARATOR
				)

				// M: Sslz.Abhaengigkeiten.Nichthaltfallabschnitt
				fillIterable(
					instance,
					cols.getColumn(Nichthaltfallabschnitt),
					fstrZugRangier,
					[
						fstrNichthaltfall.map [
							fmaAnlage?.IDGleisAbschnitt?.value?.bezeichnung?.
								bezeichnungTabelle?.wert
						]
					],
					MIXED_STRING_COMPARATOR
				)

				// N: Sslz.Abhaengigkeiten.Zweites_Haltfallkrit
				fill(
					instance,
					cols.getColumn(Zweites_Haltfallkrit),
					fstrZugRangier,
					[
						(fstrFahrweg?.start?.zweitesHaltfallkriterium?.
							schalter as Zugeinwirkung)?.bezeichnung?.
							bezeichnungTabelle?.wert
					]
				)

				// O: Sslz.Abhaengigkeiten.Anrueckverschluss
				val schaltmittel = new Wrapper<Set<Basis_Objekt>>
				val gleisabschnitte = new Wrapper<Set<Gleis_Abschnitt>>
				val zugeinwirkungen = new Wrapper<Set<String>>
				fillSwitch(
					instance,
					cols.getColumn(Anrueckverschluss),
					fstrZugRangier,
					new Case<Fstr_Zug_Rangier>(
						[
							schaltmittel.value = fstrFahrweg?.start?.
								anrueckverschluss?.map[schalter]?.toSet ?:
								Collections.emptySet
							gleisabschnitte.value = schaltmittel.value.filter(
								FMA_Anlage).map[IDGleisAbschnitt?.value].
								filterNull.toSet
							!gleisabschnitte.value.empty
						],
						[
							gleisabschnitte.value.map [
								bezeichnung?.bezeichnungTabelle?.wert
							].filterNull
						],
						ITERABLE_FILLING_SEPARATOR,
						MIXED_STRING_COMPARATOR
					),
					new Case<Fstr_Zug_Rangier>(
						[
							zugeinwirkungen.value = schaltmittel.value.filter(
								Zugeinwirkung).map [
								bezeichnung?.bezeichnungTabelle?.wert
							].toSet
							!zugeinwirkungen.value.empty
						],
						[
							zugeinwirkungen.value
						],
						ITERABLE_FILLING_SEPARATOR,
						MIXED_STRING_COMPARATOR
					)
				)

				// P: Sslz.Signalisierung.Geschwindigkeit_Startsignal.Hg
				fill(instance, cols.getColumn(Hg), fstrZugRangier, [
					fstrZugRangier?.fstrFahrweg?.fstrVHg?.wert?.toString
				])

				// Q: Sslz.Signalisierung.Geschwindigkeit_Startsignal.Fahrweg
				fill(
					instance,
					cols.getColumn(Fahrweg),
					fstrZugRangier,
					[
						fstrZugRangier.geschwindigkeit.orElse(null)?.toString ?:
							""
					]
				)

				// R: Sslz.Signalisierung.Geschwindigkeit_Startsignal.DWeg
				fill(instance, cols.getColumn(DWeg), fstrZugRangier, [
					fstrZugRangier?.fstrDWeg?.fstrDWegSpezifisch?.DWegV?.wert?.
						toString
				])

				// S: Sslz.Signalisierung.Geschwindigkeit_Startsignal.Besonders
				fill(instance, cols.getColumn(Besonders), fstrZugRangier, [
					fstrZugRangier?.fstrZugRangierAllg?.fstrV?.wert?.toString
				])

				// T: Sslz.Signalisierung.Geschwindigkeit_Startsignal.Zs3
				fillIterable(
					instance,
					cols.getColumn(Geschwindigkeit_Startsignal_Zs3),
					fstrZugRangier,
					[signalBegriffZs3ByStartSignal],
					SIGNALBEGRIFF_COMPARATOR
				)

				// U: Sslz.Signalisierung.Geschwindigkeit_Startsignal.Aufwertung_Mwtfstr
				fill(
					instance,
					cols.getColumn(Aufwertung_Mwtfstr),
					fstrZugRangier,
					[
						fstrZugRangier?.fstrMittel?.fstrMittelVAufwertung?.
							wert?.translate ?: ""
					]
				)

				// V: Sslz.Signalisierung.Sonstiges_Startsignal.Zusatzlicht
				fillConditional(
					instance,
					cols.getColumn(Zusatzlicht),
					fstrZugRangier,
					[ r |
						r.fstrFahrweg.start.signalbegriffe.exists [
							hasSignalbegriffID(typeof(ZlO)) &&
								r.fstrSignalisierung.map [
									signalSignalbegriff
								].toList.contains(it)
						]
					],
					["x"]
				)

				// W: Sslz.Signalisierung.Sonstiges_Startsignal.Zs3v
				fillIterable(
					instance,
					cols.getColumn(Zs3v),
					fstrZugRangier,
					[

						getRelevantSignalBegriffAtSignal(fstrFahrweg.start,
							typeof(Zs3v)).map[signalBegriffSymbol]
					],
					SIGNALBEGRIFF_COMPARATOR
				)

				// X: Sslz.Signalisierung.Sonstiges_Startsignal.Zs2
				fillIterable(
					instance,
					cols.getColumn(Zs2),
					fstrZugRangier,
					[
						fstrSignalisierung.
							getFstrSignalisierungSymbol(typeof(Zs2))
					],
					SIGNALBEGRIFF_COMPARATOR
				)

				// Y: Sslz.Signalisierung.Sonstiges_Startsignal.Zs2v*
				fillIterable(
					instance,
					cols.getColumn(Zs2v),
					fstrZugRangier,
					[
						getRelevantSignalBegriffAtSignal(fstrFahrweg.start,
							typeof(Zs2v)).map [
							signalbegriffID.symbol
						]
					],
					SIGNALBEGRIFF_COMPARATOR
				)

				// Z: Sslz.Signalisierung.Sonstiges_Startsignal.Zs6
				fillConditional(
					instance,
					cols.getColumn(Sonstiges_Startsignal_Zs6),
					fstrZugRangier,
					[
						fstrFahrweg.start.signalbegriffe.exists [
							hasSignalbegriffID(typeof(Zs6))
						]
					],
					[
						val zs6Begriffes = fstrFahrweg.start.signalbegriffe.
							filter[hasSignalbegriffID(typeof(Zs6))]
						fstrSignalisierung.map[signalSignalbegriff].exists [
							zs6Begriffes.contains(it)
						].translate
					]
				)

				// AA: Sslz.Signalisierung.Sonstiges_Startsignal.Zs13
				fillConditional(
					instance,
					cols.getColumn(Zs13),
					fstrZugRangier,
					[
						fstrSignalisierung.map [
							signalSignalbegriff
						].exists [
							signalbegriffID instanceof Zs13
						]
					],
					["x"]
				)

				// AB: Sslz.Signalisierung.Im_Fahrweg.Zs3
				fillIterable(
					instance,
					cols.getColumn(Im_Fahrweg_Zs3),
					fstrZugRangier,
					[
						val zs3NichtStartZiel = fstrZugRangier.fstrSignalisierung.
							filter [
								signalSignalbegriff !== null &&
									signalSignalbegriff.
										hasSignalbegriffID(typeof(Zs3)) &&
									signalSignalbegriff.signalRahmen.signal !==
										fstrZugRangier.fstrFahrweg.start &&
									signalSignalbegriff.signalRahmen.signal !==
										fstrZugRangier.fstrFahrweg.zielSignal
							].sortBy [
								signalSignalbegriff?.signalbegriffID?.symbol
							]
						zs3NichtStartZiel.map [
							'''«signalSignalbegriff?.signalRahmen?.signal?.bezeichnung?.bezeichnungTabelle?.wert»«
						»(«signalSignalbegriff?.signalBegriffSymbol»)'''
						]
					],
					null,
					[it],
					ITERABLE_FILLING_SEPARATOR
				)

				// Analysis: Sslz.Signalisierung.Im_Fahrweg.Zs6
				if (logger.debugEnabled) {
					try {
						logger.
							debug('''signalbegriffeImFahrweg=«fstrZugRangier.fstrFahrweg.signalbegriffeImFahrweg.debugString»''')
						logger.
							debug('''fstrSignalisierung=«fstrZugRangier.fstrSignalisierung.debugString»''')
					} catch (Exception e) {
						logger.error(e.message)
					}
				}
				// AC: Sslz.Signalisierung.Im_Fahrweg.Zs6
				fill(
					instance,
					cols.getColumn(Im_Fahrweg_Zs6),
					fstrZugRangier,
					[
						fstrFahrweg.signalbegriffeImFahrweg.findFirst [ b1 |
							b1.hasSignalbegriffID(typeof(Zs6)) &&
								fstrZugRangier.fstrSignalisierung.exists [ b2 |
									b2.signalSignalbegriff === b1
								]
						]?.signalRahmen?.signal?.bezeichnung?.
							bezeichnungTabelle?.wert
					]
				)

				// AD: Sslz.Signalisierung.Im_Fahrweg.Kennlicht
				fillIterable(
					instance,
					cols.getColumn(Kennlicht),
					fstrZugRangier,
					[
						fstrSignalisierung.getSignalberiffsWithType(typeof(Kl)).
							map [
								signalRahmen.signal?.bezeichnung?.
									bezeichnungTabelle?.wert
							]
					],
					MIXED_STRING_COMPARATOR
				)

				// AE: Sslz.Signalisierung.Im_Fahrweg.Vorsignalisierung
				fillIterable(
					instance,
					cols.getColumn(Vorsignalisierung),
					fstrZugRangier,
					[
						vorsignalisierung.map [ vorsignal |
							getVorsignalBezeichnung(vorsignal)
						]
					],
					MIXED_STRING_COMPARATOR
				)

				// AF: Sslz.Bemerkung
				fillSwitch(
					instance,
					cols.getColumn(Bemerkung),
					fstrZugRangier,
					new Case<Fstr_Zug_Rangier>([
						fstrZugRangier.container.contents.filter(
							Fstr_Abhaengigkeit).map [
							IDBedienAnzeigeElement?.value?.
								bedienAnzeigeElementAllg
						].findFirst [
							it?.taste !== null || it?.schalter !== null
						] !== null
					], [
						val bedAnzeigeElemente = fstrFahrweg?.abhaengigkeiten?.
							map [
								bedienAnzeigeElement
							]?.filterNull ?: Collections.emptyList

						'''«FOR bae : bedAnzeigeElemente»«bae.comment[translate]»«bae» «ENDFOR»'''.
							toString.trim
					]),
					new Case<Fstr_Zug_Rangier>([
						!IDFstrAusschlussBesonders.empty
					], [
						val fstrAusschlussBesonders = IDFstrAusschlussBesonders.
							map [
								value?.getZugFstrBezeichnung([art|isZ(art)])
							]

						'''«FOR fstr : fstrAusschlussBesonders»«fstr» «ENDFOR»'''.
							toString.trim
					])
				)

				fillFootnotes(instance, fstrZugRangier)

				if (logger.debugEnabled) {
					logger.debug(Utils.debugString(
						instance
					))
					logger.
						debug('''rowGroups=«factory.table.tablecontent.rowgroups.size»''')
					logger.debug('''rows=«factory.table.tablecontent.rowgroups.fold(
						0, [sum,rg|sum+rg.rows.size])»''')
				}
			// Representation-GUIDs füllen
			}
		}

		return factory.table
	}

	def List<String> fillInselgleis(
		Collection<Gleis_Bezeichnung> tracks
	) {
		return tracks.map[bezeichnung.bezGleisBezeichnung.wert].filterNull.
			toSet.toList
	}

	private def String fahrwegStart(
		Fstr_Zug_Rangier fstrZugRangier
	) {
		return fstrZugRangier?.fstrFahrweg?.start?.bezeichnung?.
			bezeichnungTabelle?.wert
	}

	private def String fahrwegZiel(
		Fstr_Zug_Rangier fstrZugRangier
	) {
		return fstrZugRangier?.fstrFahrweg?.zielSignal?.bezeichnung?.
			bezeichnungTabelle?.wert
	}

	private def String fahrwegZielBlock(
		Fstr_Zug_Rangier fstrZugRangier
	) {
		val ziel = fstrZugRangier?.IDFstrFahrweg?.value?.IDZiel?.value as Signal

		val fahrwegeAbZiel = ziel.container.contents.filter(Fstr_Zug_Rangier).
			filter [
				fstrZug?.fstrZugArt?.wert === ENUM_FSTR_ZUG_ART_B &&
					IDFstrFahrweg?.value?.IDStart?.value == ziel
			]

		val blockSignale = fahrwegeAbZiel.map [
			(IDFstrFahrweg?.value?.IDZiel?.value as Signal)?.bezeichnung?.
				bezeichnungTabelle?.wert
		].filterNull.join(" ")

		return '''«ziel?.bezeichnung?.bezeichnungTabelle?.wert» [«blockSignale»]'''
	}

	private def String fahrwegNummer(
		Fstr_Zug_Rangier fstrZugRangier
	) {
		if (fstrZugRangier?.fstrZugRangierAllg?.fstrReihenfolge?.wert.
			intValue != 0) {
			return fstrZugRangier?.fstrZugRangierAllg?.fstrReihenfolge?.wert.
				toString
		}
		return EMPTY_FILLING
	}

	private def static Fstr_Zug_Rangier getZielFstrZugRangier(Fstr_Zug_Rangier it) {
		val zielSignal = IDFstrFahrweg?.value.IDZiel?.value
		return zielSignal.container.contents.filter(Fstr_Zug_Rangier).findFirst [
			IDFstrFahrweg?.value?.IDStart?.value == zielSignal &&
				fstrZug?.fstrZugArt?.wert === ENUM_FSTR_ZUG_ART_B
		]
	}

	private def List<String> getSignalBegriffZs3ByStartSignal(
		Fstr_Zug_Rangier fstrZugRangier) {
		val fstrMittelArt = fstrZugRangier?.fstrMittel?.fstrMittelArt?.wert
		val signalBegriffAtZielSignal = fstrZugRangier?.
			getRelevantSignalBegriffAtSignal(
				fstrZugRangier?.fstrFahrweg?.zielSignal, typeof(Zs3))
		if ((fstrMittelArt === ENUMFstrMittelArt.ENUM_FSTR_MITTEL_ART_ZM ||
			fstrMittelArt === ENUMFstrMittelArt.ENUM_FSTR_MITTEL_ART_ZUM) &&
			!signalBegriffAtZielSignal.isNullOrEmpty) {
			return signalBegriffAtZielSignal.map[signalBegriffSymbol]
		}

		val startSignal = fstrZugRangier?.fstrFahrweg?.start
		if (startSignal === null) {
			return #[]
		}
		val signalBegriffOfSignal = startSignal.signalRahmen?.flatMap [
			signalbegriffe
		].filter[it !== null && hasSignalbegriffID(typeof(Zs3))].toList
		return fstrZugRangier.fstrSignalisierung.filter [ fstrSignal |
			signalBegriffOfSignal.exists [
				it === fstrSignal.IDSignalSignalbegriff?.value
			]
		].map [
			val signalSignalbegriffSymbol = IDSignalSignalbegriff?.value?.
				signalBegriffSymbol
			if (IDSignalSignalbegriffZiel === null) {
				return signalSignalbegriffSymbol
			}
			val signalSignalbegriffziel = IDSignalSignalbegriffZiel.value
			if (signalSignalbegriffziel !== null &&
				(signalSignalbegriffziel.hasSignalbegriffID(typeof(Zs3)) ||
					signalSignalbegriffziel.hasSignalbegriffID(typeof(Hp0)))) {
				return '''«signalSignalbegriffSymbol»(«
					»«IF signalSignalbegriffziel.hasSignalbegriffID(typeof(Hp0))»0«
					»«ELSE»«signalSignalbegriffziel.signalBegriffSymbol»«ENDIF»)'''
			}
			return null
		].filterNull.toList
	}

	private def List<Signal_Signalbegriff> getRelevantSignalBegriffAtSignal(
		Fstr_Zug_Rangier fstrZugRangier, Signal signal,
		Class<? extends Signalbegriff_ID_TypeClass> signalBegriff) {
		if (signal === null) {
			return #[]
		}
		val relevantSignalBegriff = signal.signalRahmen.flatMap [
			signalbegriffe
		].filter[hasSignalbegriffID(signalBegriff)].toList
		val fstrSignalisierung = fstrZugRangier.fstrSignalisierung.toList

		return fstrSignalisierung.map[IDSignalSignalbegriff.value].filter [ s |
			relevantSignalBegriff.exists[it === s]
		].toList
	}

	private def String getVorsignalBezeichnung(Fstr_Zug_Rangier fstrZugRangier,
		Signal vorsignal) {
		if (vorsignal === null) {
			return ""
		}

		val fstrSignalisierung = fstrZugRangier.fstrSignalisierung.toList
		val existsZl = fstrSignalisierung.map [
			IDSignalSignalbegriff.value
		].exists [
			(hasSignalbegriffID(typeof(ZlO)) ||
				hasSignalbegriffID(typeof(ZlU))) &&
				signalRahmen.signal === vorsignal
		]
		val zielSignal = fstrZugRangier.fstrFahrweg.zielSignal
		if (!(zielSignal.hasSignalbegriffID(typeof(Zs2)) ||
			zielSignal.hasSignalbegriffID(typeof(Zs3)))) {
			return '''«vorsignal.bezeichnung.bezeichnungTabelle.wert»«IF existsZl» (Zl)«ENDIF»'''
		}

		val relevantFstrSignalisierung = fstrSignalisierung.filter [
			signalSignalbegriff !== null &&
				signalSignalbegriff.signalRahmen?.signal === vorsignal
		].filter [
			(signalSignalbegriff.hasSignalbegriffID(typeof(Zs2v)) ||
				signalSignalbegriff.hasSignalbegriffID(typeof(Zs3v)))
		].toList

		val zs2vSymbols = relevantFstrSignalisierung.
			getFstrSignalisierungSymbol(typeof(Zs2v))
		val zs3vSymbols = relevantFstrSignalisierung.
			getFstrSignalisierungSymbol(typeof(Zs3v))

		val additionInfo = #[zs2vSymbols.join(","), zs3vSymbols.join(","),
			existsZl ? "Zl" : ""].filter[!nullOrEmpty].join("/")
		return '''«vorsignal.bezeichnung.bezeichnungTabelle.wert»«IF !additionInfo.nullOrEmpty» («additionInfo»)«ENDIF»'''
	}

	private def <T extends Signalbegriff_ID_TypeClass> List<String> getFstrSignalisierungSymbol(
		List<Fstr_Signalisierung> fstrSignalisierung, Class<T> type) {
		return fstrSignalisierung.getSignalberiffsWithType(type).map [
			signalBegriffSymbol
		]
	}

	private def String getSignalBegriffSymbol(
		Signal_Signalbegriff signalBegriff) {
		if (signalBegriff === null) {
			return ""
		}
		return '''«signalBegriff.signalbegriffID?.symbol»«
		»«IF (signalBegriff.hasSignalbegriffID(typeof(Zs3)) || signalBegriff.hasSignalbegriffID(typeof(Zs3v))) && !signalBegriff.signalSignalbegriffAllg?.geschaltet?.wert»F«ENDIF»'''
	}
	
	def static String getFstrZugArt(Fstr_Zug_Rangier fstrZugRangier) {
		val fstrZug = fstrZugRangier.fstrZug
		val fstrZugArt = fstrZug?.fstrZugArt?.wert.literal
		if (fstrZugRangier.zielFstrZugRangier !== null) {
			return '''«fstrZugArt.substring(1) ?: ""»B'''
		}
		
		if (fstrZug?.IDSignalGruppenausfahrt !== null) {
			return '''G«fstrZugArt.substring(1) ?: ""»'''
		}
		
		if (fstrZugArt !== null) {
			return fstrZugArt.substring(1)
		}
		
		if (fstrZugRangier.fstrMittel?.fstrMittelArt?.wert !== null) {
			return fstrZugRangier.fstrMittel?.fstrMittelArt?.wert.literal.substring(1);
		}
		return ""
	}
}
