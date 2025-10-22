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
package org.eclipse.set.feature.table.pt1.sszs

import java.math.BigDecimal
import java.util.List
import java.util.Optional
import java.util.Set
import org.apache.commons.lang3.Range
import org.eclipse.set.basis.constants.ToolboxConstants
import org.eclipse.set.basis.graph.TopPoint
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.core.services.graph.TopologicalGraphService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_Signal
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Nichthaltfall
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Kl
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Ne14
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.OzBk
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs1
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs13
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs2
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs2v
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs3
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs6
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs7
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Zs8
import org.eclipse.set.model.planpro.Signalbegriffe_Struktur.Signalbegriff_ID_TypeClass
import org.eclipse.set.model.planpro.Signale.ENUMAnschaltdauer
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.ppmodel.extensions.utils.TopGraph
import org.eclipse.set.utils.math.AgateRounding
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin

import static org.eclipse.set.feature.table.pt1.sszs.SszsColumns.*
import static org.eclipse.set.model.planpro.Signale.ENUMAutoEinstellung.*
import static org.eclipse.set.model.planpro.Signale.ENUMFiktivesSignalFunktion.*
import static org.eclipse.set.model.planpro.Signale.ENUMSignalArt.*
import static org.eclipse.set.ppmodel.extensions.geometry.GEOKanteGeometryExtensions.*

import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrNichthaltfallExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StellBereichExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*
import static extension org.eclipse.set.utils.math.BigDecimalExtensions.*
import static extension org.eclipse.set.utils.math.DoubleExtensions.*

/**
 * Table transformation for ETCS Melde- und Kommandoanschaltung Muka Signale (Sszs).
 */
class SszsTransformator extends AbstractPlanPro2TableModelTransformator {
	static BigDecimal MAX_TOP_DISTANCE_IN_METER = BigDecimal.ZERO
	static Range<Double> FMA_KOMPONENT_DISTANCE_RANGE = Range.of(-3.0, 350.0);
	TopologicalGraphService topGraphService

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService,
		TopologicalGraphService topGraphService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
		this.topGraphService = topGraphService
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory, Stell_Bereich controlArea) {
		val topGraph = new TopGraph(container.TOPKante)
		for (etcsSignal : container.ETCSSignal.filter[isPlanningObject]) {
			val refSignal = etcsSignal.IDSignal?.value
			val row = factory.newTableRow(etcsSignal)

			// A: Sszs.Signal.Bezeichnung
			fill(
				row,
				cols.getColumn(Bezeichnung),
				refSignal,
				[bezeichnung?.bezeichnungTabelle?.wert]
			)

			// B: Sszs.Signal.Art
			val signalRealAktivShirmArt = refSignal?.signalReal?.
				signalRealAktivSchirm?.signalArt?.wert

			fillSwitch(
				row,
				cols.getColumn(Art),
				etcsSignal,
				new Case<ETCS_Signal>(
					[
						isRelevantSignal(
							IDSignal?.value,
							[
								signalReal?.signalRealAktiv?.autoEinstellung?.
									wert === ENUM_AUTO_EINSTELLUNG_SB
							],
							[
								List.of(ENUM_SIGNAL_ART_HAUPTSIGNAL,
									ENUM_SIGNAL_ART_HAUPTSPERRSIGNAL,
									ENUM_SIGNAL_ART_MEHRABSCHNITTSSIGNAL,
									ENUM_SIGNAL_ART_MEHRABSCHNITTSSPERRSIGNAL,
									ENUM_SIGNAL_ART_HAUPTSPERRSIGNAL_NE_14_LS,
									ENUM_SIGNAL_ART_SPERRSIGNAL).contains(
									signalReal?.signalRealAktivSchirm?.
										signalArt?.wert)
							]
						) || !findSignalInDistance(
							topGraph,
							row,
							cols.getColumn(Art),
							[signalReal !== null],
							[!existSignalRealAktiv],
							[isSignalNe14OrOzBk],
							[isSignalFiktivZielBkOrNe14],
							[
								signalFiktiv?.autoEinstellung?.wert ===
									ENUM_AUTO_EINSTELLUNG_SB
							]
						).nullOrEmpty
					],
					["SB"]
				),
				new Case<ETCS_Signal>(
					[
						!findSignalInDistance(
							topGraph,
							row,
							cols.getColumn(Art),
							[signalReal !== null],
							[!existSignalRealAktiv],
							[isSignalNe14OrOzBk],
							[isSignalFiktivZielBkOrNe14]
						).nullOrEmpty
					],
					["VB"]
				),
				new Case(
					[
						signalRealAktivShirmArt ===
							ENUM_SIGNAL_ART_HAUPTSPERRSIGNAL_NE_14_LS
					],
					["Ls"]
				),
				new Case<ETCS_Signal>(
					[

						signalRealAktivShirmArt ===
							ENUM_SIGNAL_ART_SPERRSIGNAL && !
								IDSignal?.value.
							isStartOrDestinationOfAnyTrainRoute
					],
					["sLs"]
				),
				new Case(
					[

						signalRealAktivShirmArt ===
							ENUM_SIGNAL_ART_ZUGDECKUNGSSIGNAL
					],
					["ZdS"]
				),
				new Case(
					[
						#[ENUM_SIGNAL_ART_HAUPTSIGNAL,
							ENUM_SIGNAL_ART_HAUPTSPERRSIGNAL,
							ENUM_SIGNAL_ART_MEHRABSCHNITTSSIGNAL,
							ENUM_SIGNAL_ART_MEHRABSCHNITTSSPERRSIGNAL].exists [
							it === signalRealAktivShirmArt
						]
					],
					["L"]
				)
			)

			val streckeAndKm = getStreckeAndKm(refSignal)
			if (refSignal.punktObjektStrecke.size > 1 &&
				!refSignal.punktObjektStrecke.exists [
					kmMassgebend?.wert !== null
				]) {
				row.addTopologicalCell(cols.getColumn(Standort_Km))
				row.addTopologicalCell(cols.getColumn(Strecke))
			}
			// C: Sszs.Signal.Standort.Strecke
			fillIterable(
				row,
				cols.getColumn(Strecke),
				refSignal,
				[
					streckeAndKm.map[key]
				],
				MIXED_STRING_COMPARATOR
			)

			// D: Sszs.Signal.Standort.km
			fillIterableMultiCellWhenAllow(
				row,
				cols.getColumn(Standort_Km),
				refSignal,
				[
					isFindGeometryComplete ||
						!streckeAndKm.flatMap[value].filter[!nullOrEmpty].
							nullOrEmpty
				],
				[
					val kmValues = streckeAndKm.flatMap[value].filter [
						!nullOrEmpty
					]
					if (!kmValues.nullOrEmpty) {
						return kmValues.toList
					}
					val routeThroughBereichObjekt = singlePoint.
						streckenThroughBereichObjekt
					return getStreckeKm(routeThroughBereichObjekt).toList

				],
				null,
				ITERABLE_FILLING_SEPARATOR
			)

			// E: Sszs.Signalisierung.Zs_1
			fillConditional(
				row,
				cols.getColumn(Zs_1),
				refSignal,
				[hasSignalbegriffID(Zs1)],
				["x"]
			)

			// F: Sszs.Signalisierung.Zs_2
			fillConditional(
				row,
				cols.getColumn(Zs_2),
				refSignal,
				[hasSignalbegriffID(Zs2) || hasSignalbegriffID(Zs2v)],
				["x"]
			)

			// G: Sszs.Signalisierung.Zs_3
			val zs3Signals = refSignal.getSignalbegriffe(Zs3)
			if (!zs3Signals.empty) {
				fillIterable(
					row,
					cols.getColumn(Zs_3),
					refSignal,
					[
						val isZs3SignalGeschaltet = isSignalBegriffeGeschaltet(
							Zs3)
						if (isZs3SignalGeschaltet.empty) {
							return #[]
						}
						val symbols = zs3Signals.filterNull.map [
							signalbegriffID?.symbol
						]?.filterNull ?: []
						return isZs3SignalGeschaltet.get
							? symbols
							: symbols.map [
							it + "F"
						]

					],
					MIXED_STRING_COMPARATOR
				)
			}

			// H: Sszs.Signalisierung.Zs_6
			fillConditional(
				row,
				cols.getColumn(Zs_6),
				refSignal.isSignalBegriffeGeschaltet(Zs6),
				[
					present
				],
				[get ? "x" : "F"]
			)

			// I: Sszs.Signalisierung.Zs_7
			fillConditional(
				row,
				cols.getColumn(Zs_7),
				refSignal,
				[
					hasSignalbegriffID(Zs7)
				],
				["x"]
			)

			// J: Sszs.Signalisierung.Zs_8
			fillConditional(
				row,
				cols.getColumn(Zs_8),
				refSignal,
				[
					hasSignalbegriffID(Zs8)
				],
				["x"]
			)

			// K: Sszs.Signalisierung.Loeschung_Zs_1_7_8
			fillConditional(
				row,
				cols.getColumn(Loeschung_Zs),
				refSignal.signalbegriffe,
				[
					exists[ begriff |
						begriff?.signalSignalbegriffAllg?.anschaltdauer?.
							wert === ENUMAnschaltdauer.ENUM_ANSCHALTDAUER_Z
					]
				],
				["x"]
			)

			// L: Sszs.Signalisierung.Zs_13
			fillConditional(
				row,
				cols.getColumn(Zs_13),
				refSignal.isSignalBegriffeGeschaltet(Zs13),
				[
					present
				],
				[get ? "x" : "F"]
			)

			// M: Sszs.Signalisierung.Kennlicht
			fillConditional(
				row,
				cols.getColumn(Kennlicht),
				refSignal,
				[hasSignalbegriffID(Kl)],
				["x"]
			)

			// N: Sszs.Signalisierung.Ne_14_Rz
			fillIterableWithConditional(
				row,
				cols.getColumn(Ne_14_Rz),
				refSignal,
				[hasSignalbegriffID(Ne14)],
				[
					return signalRahmen?.map [ rahmen |
						val regelZeichnung = rahmen?.IDRegelzeichnung?.value
						val regelZeichnungBild = regelZeichnung?.
							regelzeichnungAllg?.bild?.wert
						if (regelZeichnung === null) {
							return null
						}
						return '''«regelZeichnung?.regelzeichnungAllg?.RZNummer?.wert» «IF regelZeichnungBild !== null»(«regelZeichnungBild»)«ENDIF»'''
					]?.filterNull ?: #[]
				],
				MIXED_STRING_COMPARATOR,
				ITERABLE_FILLING_SEPARATOR
			)

			// O: Sszs.ETCS_Gefahrpunkt.Bezeichnung:
			fillConditional(
				row,
				cols.getColumn(Gefahrpunkt_Bezeichnung),
				etcsSignal,
				[IDETCSGefahrpunkt2?.value !== null],
				[
					val gefahrPunkt = IDETCSGefahrpunkt?.value?.bezeichnung?.
						bezeichnungMarkanterPunkt?.wert
					val gefahrPunkt2 = IDETCSGefahrpunkt2?.value?.bezeichnung?.
						bezeichnungMarkanterPunkt?.wert
					'''«gefahrPunkt» («gefahrPunkt2»)'''
				],
				[
					IDETCSGefahrpunkt?.value?.bezeichnung?.
						bezeichnungMarkanterPunkt?.wert ?: ''
				]
			)

			// P: Sszs.ETCS_Gefahrpunkt.Abstand_vom_Signal
			fillSwitch(
				row,
				cols.getColumn(Abstand_vom_Signal),
				etcsSignal,
				new Case<ETCS_Signal>(
					[ETCSGefahrpunktabstandAbweichend !== null],
					[ETCSGefahrpunktabstandAbweichend?.wert?.toTableDecimal]
				),
				new Case<ETCS_Signal>(
					[IDETCSGefahrpunkt2?.value !== null],
					[
						row.addTopologicalCell(
							cols.getColumn(Abstand_vom_Signal))
						val distanceToETCSGefahrpunkt = distanceToSignal(
							IDETCSGefahrpunkt?.value?.IDMarkanteStelle?.value).
							toTableDecimal
						val distanceToETCSGefahrpunkt2 = distanceToSignal(
							IDETCSGefahrpunkt2?.value?.IDMarkanteStelle?.value).
							toTableDecimal
						return '''«distanceToETCSGefahrpunkt»(«distanceToETCSGefahrpunkt2»)'''
					]
				),
				new Case<ETCS_Signal>(
					[IDETCSGefahrpunkt2?.value === null],
					[
						row.addTopologicalCell(
							cols.getColumn(Abstand_vom_Signal))
						distanceToSignal(
							IDETCSGefahrpunkt?.value?.IDMarkanteStelle?.value).
							toTableDecimal
					]
				)
			)

			// Q: Sszs.Kuerzester_DWeg.bis49m
			fillConditional(
				row,
				cols.getColumn(Dweg_49),
				etcsSignal,
				[ETCSSignalDWeg?.DWegIntervall50?.wert !== null],
				[ETCSSignalDWeg?.DWegIntervall50?.wert.toTableDecimal]
			)

			// R: Sszs.Kuerzester_DWeg.ab50bis199m
			fillConditional(
				row,
				cols.getColumn(Dweg_50_199),
				etcsSignal,
				[ETCSSignalDWeg?.DWegIntervall50200?.wert !== null],
				[ETCSSignalDWeg?.DWegIntervall50200?.wert.toTableDecimal]
			)

			// S: Sszs.Kuerzester_DWeg.ab200m
			fillConditional(
				row,
				cols.getColumn(Dweg_200),
				etcsSignal,
				[ETCSSignalDWeg?.DWegIntervall200?.wert !== null],
				[ETCSSignalDWeg?.DWegIntervall200?.wert.toTableDecimal]
			)

			// T: Sszs.Sonstige_Funktionen.Dunkelschaltanstoss
			fill(
				row,
				cols.getColumn(Dunkelschaltanstoss),
				etcsSignal,
				[ETCSSignalAllg?.dunkelschaltanstoss?.wert.translate]
			)

			// U: Sszs.Sonstige_Funktionen.Autom_Betrieb
			fill(
				row,
				cols.getColumn(Autom_Betrieb),
				etcsSignal,
				[
					if (it === null || IDSignal?.value === null ||
						IDSignal?.value?.signalReal === null) {
						return Boolean.FALSE.translate
					}
					val signal = IDSignal.value
					val signalRealActiveAutoConfig = signal.signalReal?.
						signalRealAktiv?.autoEinstellung?.wert
					return (signalRealActiveAutoConfig ===
						ENUM_AUTO_EINSTELLUNG_ZL ||
						signalRealActiveAutoConfig ===
							ENUM_AUTO_EINSTELLUNG_SB || !findSignalInDistance(
							topGraph,
							row,
							cols.getColumn(Autom_Betrieb),
							[signalReal !== null],
							[!existSignalRealAktiv],
							[isSignalNe14OrOzBk],
							[isSignalFiktivZielBkOrNe14],
							[
								signalFiktiv.autoEinstellung.wert ===
									ENUM_AUTO_EINSTELLUNG_SB ||
									signalFiktiv.autoEinstellung.wert ===
										ENUM_AUTO_EINSTELLUNG_ZL
							]
						).nullOrEmpty
						
					).translate
				]
			)

			// V: Sszs.Sonstige_Funktionen.D_End
			fill(
				row,
				cols.getColumn(D_End),
				refSignal,
				[
					val distance = getNearstFMAKomponent(topGraph)
					if (distance.empty) {
						return ""
					}
					val distanceValue = distance.get
					return distanceValue <= 5 ||
						distanceValue >= -3 ? "0" : AgateRounding.roundUp(
						distanceValue).toString
				]
			)

			// W: Sszs.Sonstige_Funktionen.Einstieg_erlaubt
			fillConditional(
				row,
				cols.getColumn(Einstieg_Erlaubt),
				etcsSignal,
				[ETCSSignalAllg?.einstiegErlaubt?.wert !== null],
				[
					ETCSSignalAllg?.einstiegErlaubt?.wert.translate
				],
				[
					// TODO
				]
			)

			// X: Sszs.Sonstige_Funktionen.Ausstieg_ETCS_Sperre
			fill(
				row,
				cols.getColumn(Ausstieg_ETCS_Sperre),
				etcsSignal,
				[ETCSSignalAllg?.ausstiegETCSSperre?.translate]
			)

			// Y: Sszs.Sonstige_Funktionen.d_Haltfall
			fillIterableWithSeparatorConditional(
				row,
				cols.getColumn(d_Haltfall),
				etcsSignal,
				[
					!etcsSignal?.IDSignal?.value?.fstrNichtHaltfall.nullOrEmpty
				],
				[
					val signal = etcsSignal?.IDSignal?.value
					if (signal === null) {
						return #[]
					}
					val fstrNichtHaltfall = signal.fstrNichtHaltfall
					return fstrNichtHaltfall.map [ fstr |
						fstr.FMAKomponentOnFstr.map[fma|distanceToSignal(fma)].
							max
					].filterNull.toSet.map[toString]
				],
				ToolboxConstants.NUMERIC_COMPARATOR,
				[
					val distance = IDSignal?.value?.
						getNearstFMAKomponent(topGraph)
					if (distance.isPresent) {
						return distance.get.toTableDecimal
					}

				],
				ITERABLE_FILLING_SEPARATOR
			)

			// Z: Sszs.Sonstige_Funktionen.Haltfallkriterium_2
			fillConditional(
				row,
				cols.getColumn(Haltfallkriterium_2),
				refSignal,
				[
					signalFstrS?.IDZweitesHaltfallkriterium?.value !== null
				],
				["x"]
			)

			// AA: Sszs.SOnstige_FUnktionen.ZSS
			fillConditional(
				row,
				cols.getColumn(ZSS),
				refSignal,
				[
					container.ZUBBereichsgrenze.exists [ zubBereich |
						zubBereich.ZUBBereichsgrenzeNachL2.exists [ zubBereichL2 |
							zubBereichL2.IDSignalZufahrtsicherungL2oS.exists [ idSignal |
								idSignal.value === it
							]
						]
					]
				],
				["x"]
			)

			// AB: Sszs.TBV.Meldepunkt
			fill(
				row,
				cols.getColumn(Meldepunkt),
				etcsSignal,
				[ETCSSignalTBV?.TBVMeldepunkt?.wert.translate]
			)

			// AC: Sszs.TBV.Laenge_Tunnelbereich
			fill(
				row,
				cols.getColumn(Laenge_Tunnelbereich),
				etcsSignal,
				[
					ETCSSignalTBV?.TBVTunnelbereichLaenge?.wert?.
						toTableIntegerAgateUp ?: ""
				]
			)

			// AD: Sszs.TBV.Tunnelsignal
			fill(
				row,
				cols.getColumn(Tunnelsignal),
				etcsSignal,
				[ETCSSignalTBV?.TBVTunnelsignal?.wert.translate]
			)

			// AE: Sszs.Ansteuerung.ESTW-Zentraleinheit
			fillIterable(
				row,
				cols.getColumn(ESTW_Zentraleinheit),
				refSignal,
				[
					stellelement?.IDInformation?.value?.ESTWZentraleinheits?.map [
						IDOertlichkeitNamensgebend?.value?.bezeichnung?.
							oertlichkeitAbkuerzung?.wert
					]?.filterNull ?: #[]
				],
				MIXED_STRING_COMPARATOR
			)

			// AF: Sszs.Ansteuerung.Stellbereich
			fillIterable(
				row,
				cols.getColumn(Stellbereich),
				refSignal,
				[
					container.stellBereich.filter [ area |
						area.isInControlArea(stellelement)
					].map [
						aussenElementAnsteuerung.oertlichkeitNamensgebend.
							bezeichnung?.oertlichkeitAbkuerzung?.wert
					]

				],
				MIXED_STRING_COMPARATOR
			)

			// AG: Sszs.Ansteuerung.RBC-Anschaltung
			fillConditional(
				row,
				cols.getColumn(RBC_Anschaltung),
				etcsSignal,
				[IDRBC.nullOrEmpty],
				["x"]
			)

			// AF: Sszs.Bemerkung
			fillSwitchGrouped(
				row,
				cols.getColumn(Bemerkung),
				refSignal,
				[
					filterNull.flatMap[filling.apply(refSignal)]
				],
				#[
					new Case<Signal>(
						[ signal |
							signal.container.ZUBBereichsgrenze.flatMap [
								ZUBBereichsgrenzeNachL2
							].filterNull.flatMap[IDSignalZufahrtsicherungL2oS].
								filterNull.map[value].exists[signal === it]
						],
						["Zufahrtsicherungssignal L2oS"]
					),
					new Case<Signal>(
						[
							hasSignalbegriffID(Ne14) &&
								signalReal?.signalRealAktivSchirm?.signalArt?.
									wert === ENUM_SIGNAL_ART_SPERRSIGNAL &&
								!isStartOrDestinationOfAnyTrainRoute
						],
						["keine Übertragung vom Stellwerk an die ETCS-Zentrale"]
					)
				]
			)

			fillFootnotes(row, etcsSignal)

		}

		return factory.table
	}

	/**
	 * Check is the signal is meet the requirements
	 * @param signal the signal to check
	 * @param conditions the requirements
	 */
	private def boolean isRelevantSignal(Signal signal,
		(Signal)=>Boolean... conditions) {
		return conditions.forall[it.apply(signal)]
	}

	private def boolean isExistSignalRealAktiv(Signal signal) {
		return signal?.signalReal?.signalRealAktiv !== null ||
			signal?.signalReal?.signalRealAktivSchirm !== null
	}

	private def boolean isSignalNe14OrOzBk(Signal signal) {
		return signal.hasSignalbegriffID(Ne14) ||
			signal.hasSignalbegriffID(OzBk)
	}

	private def boolean isSignalFiktivZielBkOrNe14(Signal signal) {
		val fiktiveSignalFunktion = signal.signalFiktiv?.fiktivesSignalFunktion
		if (fiktiveSignalFunktion.nullOrEmpty) {
			return false
		}
		return fiktiveSignalFunktion.exists [
			wert === ENUM_FIKTIVES_SIGNAL_FUNKTION_ZUG_START_ZIEL_BK || wert ===
				ENUM_FIKTIVES_SIGNAL_FUNKTION_ZUG_START_ZIEL_NE_14
		]
	}

	/**
	 * Find the signal of ETCS_Signal,
	 * which have 1m distance with source signal, same direction and meet the requirement
	 * @param sourceSignal the source etcs signal,
	 * @param topGraph the topologie graph
	 * @paran predicates the list of predicate
	 */
	private def List<Signal> findSignalInDistance(ETCS_Signal sourceSignal,
		TopGraph topGraph, TableRow row, ColumnDescriptor col,
		(Signal)=>Boolean... predicates) {
		val refSignal = sourceSignal.IDSignal?.value
		if (refSignal === null) {
			throw new IllegalArgumentException('''ETCS_Signal: «sourceSignal.identitaet.wert» missing Signal''')
		}
		val relevantSignal = sourceSignal.container.ETCSSignal.map [
			IDSignal?.value
		].filterNull.filter [ signal |
			predicates.forall[apply(signal)] &&
				topGraphService.isInWirkrichtungOfSignal(refSignal, signal)
		].filter [
			sourceSignal.distanceToSignal(it) < MAX_TOP_DISTANCE_IN_METER
		].toList
		if (!relevantSignal.nullOrEmpty) {
			row.addTopologicalCell(col)
		}
		return relevantSignal
	}

	private def List<Fstr_Nichthaltfall> getFstrNichtHaltfall(Signal signal) {
		return signal.container.fstrNichthaltfall.filter [
			IDFstrFahrweg?.value !== null
		].filter[IDFstrFahrweg.value.IDStart?.value === signal].toList
	}

	private def Optional<Double> getNearstFMAKomponent(Signal signal,
		TopGraph topGraph) {
		if (signal === null) {
			return Optional.empty
		}
		val fmaKomponenten = signal.fmaKomponenten
		val signalTopPoint = new TopPoint(signal)
		val distanceToSignal = fmaKomponenten.map [ fma |
			val distances = topGraphService.findShortestDistance(signalTopPoint,
				new TopPoint(fma)).orElse(null)
			if (distances === null) {
				return null
			}
			if (distances.compareTo(BigDecimal.ZERO) == 0) {
				return fma -> 0.0
			}
			return topGraphService.isInWirkrichtungOfSignal(signal, fma)
				? fma -> distances.doubleValue
				: fma -> -distances.doubleValue
		].filterNull
		if (distanceToSignal.empty) {
			return Optional.empty
		}
		return Optional.of(distanceToSignal.filter [
			FMA_KOMPONENT_DISTANCE_RANGE.contains(value)
		].minBy[value].value)
	}

	private def dispatch BigDecimal distanceToSignal(ETCS_Signal etcsSignal,
		Basis_Objekt object) {
		throw new IllegalArgumentException()
	}

	private def dispatch BigDecimal distanceToSignal(ETCS_Signal etcsSignal,
		Punkt_Objekt po) {
		val signal = etcsSignal.IDSignal?.value
		if (signal !== null && po !== null) {
			val signalTopPoint = new TopPoint(signal)
			val topPoint = new TopPoint(po)
			val distance = topGraphService.findShortestDistance(signalTopPoint,
				topPoint)
			if (distance.present) {
				return distance.get
			}
		}

		throw new IllegalArgumentException()
	}

	private def Optional<Boolean> isSignalBegriffeGeschaltet(Signal signal,
		Class<? extends Signalbegriff_ID_TypeClass> begriffType) {
		val signalBegriffe = signal.getSignalbegriffe(begriffType)
		val begriffeGeschalteValues = signalBegriffe.map [
			signalSignalbegriffAllg?.geschaltet?.wert
		].filterNull
		if (begriffeGeschalteValues.nullOrEmpty) {
			return Optional.empty
		}
		return Optional.ofNullable(
			begriffeGeschalteValues.contains(Boolean.TRUE))
	}
}
