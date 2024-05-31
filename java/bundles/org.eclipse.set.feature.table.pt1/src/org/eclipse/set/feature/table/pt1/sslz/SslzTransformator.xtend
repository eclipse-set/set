/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sslz

import java.math.BigInteger
import java.util.Collection
import java.util.Collections
import java.util.List
import java.util.Set
import org.eclipse.set.basis.MixedStringComparator
import org.eclipse.set.basis.Wrapper
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.extensions.Utils
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Abhaengigkeit
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Fahrweg
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.model.planpro.Gleis.ENUMGleisart
import org.eclipse.set.model.planpro.Gleis.Gleis_Abschnitt
import org.eclipse.set.model.planpro.Gleis.Gleis_Bezeichnung
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage
import org.eclipse.set.model.planpro.Ortung.Zugeinwirkung
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Hp0
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Kl
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.ZlO
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs13
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs2
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs2v
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs3
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs3v
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs6
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.utils.table.TMFactory
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
		"(?<letters1>[A-Za-z]*)(?<number>[0-9]*)(?<letters2>[A-Za-z]*)")

	static val List<ENUMGleisart> NOT_USABLE = #[ENUM_GLEISART_ANSCHLUSSGLEIS,
		ENUM_GLEISART_NEBENGLEIS, ENUM_GLEISART_SONSTIGE]

	static val String EMPTY_FILLING = ""

	static val String WARNING_SYMBOL = "\u26A0"

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService) {
		super(cols, enumTranslationService)
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		val fstrZugRangierList = container.fstrZugRangier.filter [
			isPlanningObject
		]
		val fstrZugRangierListSorted = fstrZugRangierList
		var current = 0

		for (fstrZugRangier : fstrZugRangierListSorted) {
			current++

			if (Thread.currentThread.interrupted) {
				return null
			}
			// Generalbedingung
			val isZ = isZ(fstrZugRangier.fstrZug?.fstrZugArt)

			if (isZ) {
				val instance = factory.newTableRow(fstrZugRangier)

				fill(
					instance,
					cols.getColumn(Grundsatzangaben_Bezeichnung),
					fstrZugRangier,
					[getZugFstrBezeichnung([isZ(it)])]
				)

				fill(instance, cols.getColumn(Start), fstrZugRangier, [
					fahrwegStart
				])

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

				fill(
					instance,
					cols.getColumn(Nummer),
					fstrZugRangier,
					[fahrwegNummer]
				)

				fillIterable(
					instance,
					cols.getColumn(Entscheidungsweiche),
					fstrZugRangier,
					[getEntscheidungsweichen(NOT_USABLE).map[bezeichnung]],
					MIXED_STRING_COMPARATOR,
					[it]
				)

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

				fillSwitch(
					instance,
					cols.getColumn(Art),
					fstrZugRangier,
					new Case<Fstr_Zug_Rangier>([zielFstrZugRangier !== null], [
						'''«fstrZug?.fstrZugArt?.wert?.literal?.substring(1)»B'''
					]),
					new Case<Fstr_Zug_Rangier>(
						[fstrZug?.IDSignalGruppenausfahrt !== null],
						[
							'''G«fstrZug?.fstrZugArt?.wert.literal.substring(1)»'''
						]
					),
					new Case<Fstr_Zug_Rangier>(
						[true],
						[fstrZug?.fstrZugArt?.wert.literal.substring(1)]
					)
				)

				fill(instance, cols.getColumn(Autom_Einstellung),
					fstrZugRangier, [
						fstrZugRangier?.fstrZug?.automatischeEinstellung?.wert?.
							translate ?: ""
					])

				fill(
					instance,
					cols.getColumn(F_Bedienung),
					fstrZugRangier,
					[
						fstrZugRangier?.fstrZugRangierAllg?.FBedienung?.wert?.
							translate ?: ""
					]
				)

				fillIterable(
					instance,
					cols.getColumn(Inselgleis),
					fstrZugRangier?.fstrFahrweg?.zielSignal?.
						zgFahrtGleichzeitigVerbot ?: Collections.emptySet,
					[fillInselgleis],
					MIXED_STRING_COMPARATOR
				)

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

				fill(instance, cols.getColumn(Hg), fstrZugRangier, [
					fstrZugRangier?.fstrFahrweg?.fstrVHg?.wert?.toString
				])

				fillConditional(instance, cols.getColumn(Fahrweg),
					fstrZugRangier, [
						fstrZugRangier.geschwindigkeit == Integer.MAX_VALUE
					], [""], [Integer.toString(fstrZugRangier.geschwindigkeit)])

				fill(instance, cols.getColumn(DWeg), fstrZugRangier, [
					fstrZugRangier?.fstrDWeg?.fstrDWegSpezifisch?.DWegV?.wert?.
						toString
				])

				fill(instance, cols.getColumn(Besonders), fstrZugRangier, [
					fstrZugRangier?.fstrZugRangierAllg?.fstrV?.wert?.toString
				])

				fillSwitch(
					instance,
					cols.getColumn(Geschwindigkeit_Startsignal_Zs3),
					fstrZugRangier,
					new Case<Fstr_Zug_Rangier>(
						[
							(fstrZug.fstrZugArt?.wert?.literal == "ZM" ||
								fstrZug.fstrZugArt?.wert?.literal == "ZUM") &&
								!fstrSignalisierung.filter [
									signalSignalbegriff.
										hasSignalbegriffID(typeof(Zs3)) &&
										signalSignalbegriff.signalRahmen.
											signal ==
											fstrZugRangier.fstrFahrweg.IDZiel
								].empty
						],
						[
							val signals = fstrSignalisierung.map [
								signalSignalbegriff
							].filter [
								hasSignalbegriffID(typeof(Zs3)) &&
									signalRahmen.signal ==
										fstrZugRangier.fstrFahrweg.IDZiel
							].sortBy[signalbegriffID?.symbol]
							return signals.map[signalbegriffID?.symbol].
								filterNull
						],
						ITERABLE_FILLING_SEPARATOR,
						null
					),
					new Case<Fstr_Zug_Rangier>(
						[true /* condition handled within filling */ ],
						[
							val zs3Start = fstrZugRangier.fstrSignalisierung.
								filter [
									signalSignalbegriff !== null &&
										signalSignalbegriffZiel === null &&
										signalSignalbegriff.
											hasSignalbegriffID(typeof(Zs3)) &&
										signalSignalbegriff.signalRahmen.signal.
											identitaet.wert ==
											fstrZugRangier.fstrFahrweg.start.
												identitaet.wert
								].sortBy [
									signalSignalbegriff?.signalbegriffID?.symbol
								]

							val zs3StartZiel = fstrZugRangier.
								fstrSignalisierung.filter [
									signalSignalbegriff !== null &&
										signalSignalbegriffZiel !== null &&
										signalSignalbegriff.
											hasSignalbegriffID(typeof(Zs3)) &&
										(signalSignalbegriffZiel.
											hasSignalbegriffID(typeof(Zs3)) ||
											signalSignalbegriffZiel.
												hasSignalbegriffID(
													typeof(Hp0))) &&
										signalSignalbegriff.signalRahmen.signal.
											identitaet.wert ==
											fstrZugRangier.fstrFahrweg.start.
												identitaet.wert
								].sortBy [
									signalSignalbegriff?.signalbegriffID?.symbol
								]
							val result = zs3Start.map [
								'''«signalSignalbegriff?.signalbegriffID?.symbol»'''
							].toSet
							if (!zs3Start.empty && !zs3StartZiel.empty) {
								result.addAll(zs3StartZiel.map [
									'''«signalSignalbegriff?.signalbegriffID?.symbol»(«
									»«IF signalSignalbegriffZiel.hasSignalbegriffID(typeof(Hp0))»0«ELSE»«
									»«signalSignalbegriffZiel?.signalbegriffID?.symbol»«ENDIF»)'''
								])
							}
							return result
						],
						ITERABLE_FILLING_SEPARATOR,
						null
					)
				)

				fill(
					instance,
					cols.getColumn(Aufwertung_Mwtfstr),
					fstrZugRangier,
					[
						fstrZugRangier?.fstrMittel?.fstrMittelVAufwertung?.
							wert?.translate ?: ""
					]
				)

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

				fillIterable(
					instance,
					cols.getColumn(Zs3v),
					fstrZugRangier,
					[
						fstrSignalisierung.map [
							signalSignalbegriff
						].filter [
							hasSignalbegriffID(typeof(Zs3v))
						].map[signalbegriffID.symbol]
					],
					SIGNALBEGRIFF_COMPARATOR
				)

				fillIterable(
					instance,
					cols.getColumn(Zs2),
					fstrZugRangier,
					[
						fstrSignalisierung.map [
							signalSignalbegriff
						].filter [
							hasSignalbegriffID(typeof(Zs2))
						].map[signalbegriffID.symbol]
					],
					SIGNALBEGRIFF_COMPARATOR
				)

				fillIterable(
					instance,
					cols.getColumn(Zs2v),
					fstrZugRangier,
					[
						fstrSignalisierung.map [
							signalSignalbegriff
						].filter [
							hasSignalbegriffID(typeof(Zs2v))
						].map[signalbegriffID.symbol]
					],
					SIGNALBEGRIFF_COMPARATOR
				)

				fillConditional(
					instance,
					cols.getColumn(Sonstiges_Startsignal_Zs6),
					fstrZugRangier,
					[ r |
						r.fstrFahrweg.start.signalbegriffe.exists [ b |
							b.hasSignalbegriffID(typeof(Zs6)) &&
								r.fstrSignalisierung.map[signalSignalbegriff].
									contains(b)
						]
					],
					["x"]
				)

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

				fillIterable(
					instance,
					cols.getColumn(Im_Fahrweg_Zs3),
					fstrZugRangier,
					[
						val zs3NichtStart = fstrZugRangier.fstrSignalisierung.
							filter [
								signalSignalbegriff !== null &&
									signalSignalbegriff.
										hasSignalbegriffID(typeof(Zs3)) &&
									signalSignalbegriff.signalRahmen.signal.
										identitaet.wert !=
										fstrZugRangier.fstrFahrweg.start.
											identitaet.wert
							].sortBy [
								signalSignalbegriff?.signalbegriffID?.symbol
							]
						zs3NichtStart.map [
							'''«signalSignalbegriff?.signalRahmen?.signal?.bezeichnung?.bezeichnungTabelle?.wert»«
						»(«signalSignalbegriff?.signalbegriffID?.symbol»'''
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

				fillIterable(
					instance,
					cols.getColumn(Kennlicht),
					fstrZugRangier,
					[
						fstrSignalisierung.map [
							signalSignalbegriff
						].filter [
							hasSignalbegriffID(
								typeof(Kl)
							)
						].map [
							signalRahmen.signal?.bezeichnung?.
								bezeichnungTabelle?.wert
						]
					],
					MIXED_STRING_COMPARATOR
				)

				fillIterable(
					instance,
					cols.getColumn(Vorsignalisierung),
					fstrZugRangier,
					[
						vorsignalisierung.map [
							bezeichnung?.bezeichnungTabelle?.wert
						]
					],
					MIXED_STRING_COMPARATOR
				)

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

		val startFahrweg = ziel.container.contents.filter(Fstr_Fahrweg).filter [
			ziel == IDStart?.value
		]
		val start = ziel.container.contents.filter(Fstr_Zug_Rangier).filter [
			fstrZug?.fstrZugArt?.wert === ENUM_FSTR_ZUG_ART_B &&
				startFahrweg.contains(IDFstrFahrweg)
		].map [
			(IDFstrFahrweg?.value?.IDZiel?.value as Signal)?.bezeichnung?.
				bezeichnungTabelle?.wert
		].filterNull.join(" ")

		return '''«ziel?.bezeichnung?.bezeichnungTabelle?.wert» [«start»]'''
	}

	private def String fahrwegNummer(
		Fstr_Zug_Rangier fstrZugRangier
	) {
		if (fstrZugRangier?.fstrZugRangierAllg?.fstrReihenfolge?.wert.intValue >
			1) {
			return (fstrZugRangier?.fstrZugRangierAllg?.fstrReihenfolge?.wert -
				BigInteger.ONE).toString
		}
		return EMPTY_FILLING
	}

	private def Fstr_Zug_Rangier getZielFstrZugRangier(Fstr_Zug_Rangier it) {
		val zielSignal = IDFstrFahrweg?.value.IDZiel?.value
		return zielSignal.container.contents.filter(Fstr_Zug_Rangier).findFirst [
			IDFstrFahrweg?.value?.IDStart?.value == zielSignal &&
				fstrZug?.fstrZugArt?.wert === ENUM_FSTR_ZUG_ART_B
		]
	}
}
