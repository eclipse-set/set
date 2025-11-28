/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sskp

import java.math.RoundingMode
import java.util.Set
import org.eclipse.set.basis.graph.TopPoint
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.core.services.graph.TopologicalGraphService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg
import org.eclipse.set.model.planpro.PZB.ENUMPZBArt
import org.eclipse.set.model.planpro.PZB.ENUMWirksamkeitFstr
import org.eclipse.set.model.planpro.PZB.PZB_Element
import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Ne5
import org.eclipse.set.model.planpro.Signale.ENUMSignalArt
import org.eclipse.set.model.planpro.Signale.ENUMSignalFunktion
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.ppmodel.extensions.utils.TopGraph
import org.eclipse.set.utils.math.AgateRounding
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin

import static org.eclipse.set.basis.constants.ToolboxConstants.NUMERIC_COMPARATOR
import static org.eclipse.set.feature.table.pt1.sskp.SskpColumns.*

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PZBElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalRahmenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalbegriffExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.IterableExtensions.*
import static extension org.eclipse.set.utils.math.BigDecimalExtensions.*
import static extension org.eclipse.set.utils.math.BigIntegerExtensions.*
import static extension org.eclipse.set.utils.math.DoubleExtensions.*
import org.eclipse.set.model.planpro.PZB.util.PZBValidator
import java.math.BigDecimal

/**
 * Table transformation for a PZB-Tabelle (Sskp)
 * 
 * @author Truong
 */
class SskpTransformator extends AbstractPlanPro2TableModelTransformator {
	static final double ADDITION_SCHUTZSTRECKE_SOLL_60 = 450
	static final double ADDITION_SCHUTZSTRECKE_SOLL_40_60 = 350
	static final double ADDITION_SCHUTZSTRECKE_SOLL_40 = 210
	TopologicalGraphService topGraphService;

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService,
		TopologicalGraphService topGraphService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
		this.topGraphService = topGraphService
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory, Stell_Bereich controlArea) {

		val topGraph = new TopGraph(container.TOPKante)
		for (PZB_Element pzb : container.PZBElement.filter[isPlanningObject].
			filterObjectsInControlArea(controlArea).filter [
				PZBElementGUE?.IDPZBElementMitnutzung?.value === null
			]) {

			if (Thread.currentThread.interrupted) {
				return null
			}

			val rg = factory.newRowGroup(pzb)
			val isPZB2000 = pzb.PZBArt?.wert ===
				ENUMPZBArt.ENUMPZB_ART_2000_HZ ||
				pzb.PZBArt?.wert === ENUMPZBArt.ENUMPZB_ART_1000_2000_HZ
			val fstrDwegs = pzb?.fstrDWegs

			if (!isPZB2000 || fstrDwegs.nullOrEmpty ||
				pzb.PZBElementGM === null) {
				val instance = rg.newTableRow()
				fillRowGroupContent(instance, pzb, null, topGraph)
			} else {
				pzb?.fstrDWegs?.forEach [
					val instance = rg.newTableRow()
					fillRowGroupContent(instance, pzb, it, topGraph)
				]
			}
		}

		return factory.table
	}

	private def fillRowGroupContent(TableRow instance, PZB_Element pzb,
		Fstr_DWeg dweg, TopGraph topGraph) {
		// A: Sskp.Bezug.BezugsElement
		fillIterable(
			instance,
			cols.getColumn(Bezugselement),
			pzb,
			[PZBElementBezugspunkt.filterNull.map[fillBezugsElement]],
			MIXED_STRING_COMPARATOR
		)

		// B: Sskp.Bezug.Wirkfrequenz
		fill(
			instance,
			cols.getColumn(Wirkfrequenz),
			pzb,
			[PZBArt?.translate]
		)

		val isPZB2000 = pzb.PZBArt?.wert === ENUMPZBArt.ENUMPZB_ART_2000_HZ ||
			pzb.PZBArt?.wert === ENUMPZBArt.ENUMPZB_ART_1000_2000_HZ

		if (isPZB2000 && dweg !== null && pzb.PZBElementGM !== null) {
			// C: Sskp.PZB_Schutzstrecke.PZB_Schutzpunkt
			fill(
				instance,
				cols.getColumn(PZB_Schutzpunkt),
				dweg,
				[
					IDPZBGefahrpunkt?.value?.bezeichnung?.
						bezeichnungMarkanterPunkt?.wert
				]
			)

			// D: Sskp.PZB_Schutzstrecke.GeschwindigkeitsKlasse
			fillConditional(
				instance,
				cols.getColumn(GeschwindigkeitsKlasse),
				dweg,
				[IDPZBGefahrpunkt !== null],
				[
					val dwegV = fstrDWegSpezifisch?.DWegV?.wert.toInteger
					if (dwegV === 0) {
						return ""
					}
					if (dwegV > 60) {
						return "v > 60"
					} else if (dwegV <= 60 && dwegV > 40) {
						return "40 < v ≤ 60"
					} else if (dwegV <= 40) {
						return "v ≤ 40"
					}
					return ""

				]
			)

			// E: Sskp.PZB_Schutzstrecke.PZB_Schutzstr.PZB_Schutzstrecke_Soll
			fillConditional(
				instance,
				cols.getColumn(PZB_Schutzstrecke_Soll),
				dweg,
				[IDPZBGefahrpunkt !== null],
				[
					if (fstrDWegSpezifisch === null) {
						return IDFstrFahrweg?.value?.IDStart?.value?.
							PZBSchutzstreckeSoll?.wert?.toString ?: ""
					}

					val dwegV = fstrDWegSpezifisch.DWegV?.wert.toInteger
					val inclination = fstrDWegAllg?.massgebendeNeigung?.wert.
						toDouble
					val multipleValue = inclination >= 0 ? 0.05 : 0.1

					if (dwegV === 0) {
						return ""
					}

					if (dwegV > 40 || (dwegV <= 40 && inclination <= 0)) {
						addTopologicalCell(instance,
							cols.getColumn(PZB_Schutzstrecke_Soll))
					}
					val fillFunc = [ long value |
						if (inclination >= 0) {
							return value < 210 ? 210 : value
						}
						return value > 550 ? 550 : value
					]

					if (dwegV > 60) {
						return '''«fillFunc.apply(AgateRounding.roundUp(ADDITION_SCHUTZSTRECKE_SOLL_60 - inclination * multipleValue * 200))»'''
					} else if (dwegV <= 60 && dwegV > 40) {
						return '''«fillFunc.apply(AgateRounding.roundUp(ADDITION_SCHUTZSTRECKE_SOLL_40_60 - inclination * multipleValue * 100))»'''
					} else if (dwegV <= 40) {
						return '''«inclination > 0 ? 210 : AgateRounding.roundUp(ADDITION_SCHUTZSTRECKE_SOLL_40 - inclination * multipleValue * 50)»'''
					}
					return ""
				]
			)

			// F: Sskp.PZB_Schutzstrecke.PZB_Schutzstr.PZB_Schutzstrecke_Ist
			fillConditional(
				instance,
				cols.getColumn(PZB_Schutzstrecke_Ist),
				dweg,
				[IDPZBGefahrpunkt !== null],
				[
					val markanteStelle = dweg?.IDPZBGefahrpunkt?.value?.
						IDMarkanteStelle?.value
					if (markanteStelle instanceof Punkt_Objekt)
						return AgateRounding.roundDown(
							getPointsDistance(markanteStelle,
								dweg.IDFstrFahrweg?.value?.IDStart?.value).min).
							toString
					else
						return ""
				]
			)
		}

		// G: Sskp.Gleismagnete.Wirksamkeit
		fillIterable(
			instance,
			cols.getColumn(Wirksamkeit),
			pzb,
			[
				PZBElementZuordnungBP.map [ pzbZuordnungBp |
					switch (pzbZuordnungBp.wirksamkeit?.wert) {
						case ENUM_WIRKSAMKEIT_SCHALTBAR_VON_SIGNAL,
						case ENUM_WIRKSAMKEIT_SONSTIGE: {
							pzbZuordnungBp.wirksamkeit?.translate
						}
						case ENUM_WIRKSAMKEIT_STAENDIG_WIRKSAM: {
							// IMPROVE: Special case due to model limitatations. A future model should introduce 
							// separate values for STAENDING_WIRKSAM and STAENDING_AKTIV
							if (pzb.PZBElementGUE !== null &&
								pzb.PZBElementGUE.IDPZBElementMitnutzung ===
									null) {
								"stä. akt."
							} else {
								"stä. wirk."
							}
						}
					}
				]
			],
			null
		)

		// H: Sskp.Gleismagnete.Wirksamkeit_Bedingung
		val bueSpezifischeSignals = pzb.container.BUESpezifischesSignal.filter [
			pzb.PZBElementBezugspunkt.filter(Signal).filter [
				signalReal.signalFunktion.wert === ENUMSignalFunktion.
					ENUM_SIGNAL_FUNKTION_BUE_UEBERWACHUNGSSIGNAL
			].exists[signal|signal === IDSignal.value]
		]
		fillSwitch(
			instance,
			cols.getColumn(Wirksamkeit_Bedingung),
			pzb,
			new Case<PZB_Element>(
				[
					!PZBElementZuordnungFstr.map[IDFstrZugRangier?.value].
						empty || (PZBElementGUE !== null &&
						PZBElementZuordnungFstr.exists [
							wirksamkeitFstr?.wert === ENUMWirksamkeitFstr.
								ENUM_WIRKSAMKEIT_FSTR_STAENDIG_WIRKSAM_WENN_FAHRSTRASSE_EINGESTELLT
						])
				],
				[
					PZBElementZuordnungFstr.map [ pzbZuordnung |
						val wirksamKeit = pzbZuordnung.wirksamkeitFstr?.
							translate
						val fstrZugRangier = pzbZuordnung.IDFstrZugRangier?.
							value?.fstrZugRangierBezeichnung
						return '''«wirksamKeit» «fstrZugRangier»'''
					]
				],
				ITERABLE_FILLING_SEPARATOR,
				null
			),
			new Case<PZB_Element>(
				[
					PZBElementZuordnungFstr.exists [
						wirksamkeitFstr?.wert == ENUMWirksamkeitFstr.
							ENUM_WIRKSAMKEIT_FSTR_SONSTIGE
					]
				],
				[
					IDPZBElementZuordnung?.value?.PZBElementZuordnungFstr.
						flatMap [
							wirksamkeitFstr?.IDBearbeitungsvermerk
						].map [
							value?.bearbeitungsvermerkAllg?.kurztext?.wert
						].filterNull
				],
				ITERABLE_FILLING_SEPARATOR,
				MIXED_STRING_COMPARATOR
			),
			new Case<PZB_Element>(
				[!bueSpezifischeSignals.empty],
				[
					bueSpezifischeSignals.map [
						IDBUEAnlage?.value?.bezeichnung?.bezeichnungTabelle?.
							wert
					]
				],
				ITERABLE_FILLING_SEPARATOR,
				MIXED_STRING_COMPARATOR
			)
		)

		// I: Sskp.Gleismagnete.Abstand_Signal_Weiche
		fillIterable(
			instance,
			cols.getColumn(Abstand_Signal_Weiche),
			pzb,
			[
				PZBElementBezugspunkt.filterNull.map [
					getDistanceSignalTrackSwitch(topGraph, pzb, it)
				]
			],
			null
		)

		// J: Sskp.Gleismagnete.Abstand_GM_2000
		fillIterable(
			instance,
			cols.getColumn(Abstand_GM_2000),
			pzb,
			[
				if (PZBArt?.wert === ENUMPZBArt.ENUMPZB_ART_2000_HZ) {
					return #[]
				}
				val pzbGM2000 = container.PZBElement.filter [ pzbEle |
					pzbEle !== it &&
						(pzbEle.PZBArt?.wert ===
							ENUMPZBArt.ENUMPZB_ART_2000_HZ ||
							pzbEle.PZBArt?.wert ===
								ENUMPZBArt.ENUMPZB_ART_1000_2000_HZ) &&
						pzbEle?.PZBElementGM !== null
				].toList
				val bezugspunktSignals = PZBElementBezugspunkt.filter(Signal)

				pzbGM2000.filter [ pzbEle |
					if (PZBArt?.wert === ENUMPZBArt.ENUMPZB_ART_500_HZ) {
						return pzbEle.PZBElementBezugspunkt.filter(Signal).
							exists[signal|bezugspunktSignals.contains(signal)]
					}

					return pzbEle.PZBZuordnungSignal.map[IDSignal?.value].
						filterNull.exists [ signal |
							bezugspunktSignals.contains(signal)
						]
				].filterNull.map [ pzbEle |
					pzbEle -> getPointsDistance(it, pzbEle).min
				].filter[value.doubleValue !== 0].map [ pair |
					val distance = AgateRounding.roundDown(pair.value,
						distanceScale).toTableDecimal(distanceScale)
					if (PZBArt?.wert === ENUMPZBArt.ENUMPZB_ART_500_HZ) {
						return distance
					}
					val signal = pair.key.PZBElementBezugspunkt.filter(Signal).
						firstOrNull
					return '''«distance» «IF signal !== null»(«signal.bezeichnung?.bezeichnungTabelle?.wert»)«ENDIF»'''
				]
			],
			MIXED_STRING_COMPARATOR
		)

		if (pzb.PZBElementZuordnungBP !== null &&
			pzb.PZBElementZuordnungBP.exists [
				PZBElementZuordnungINA !== null
			] && isPZB2000) {
			val inaGefahrstelles = pzb.PZBElementZuordnungBP.map [
				INAGefahrstelle
			].flatten

			val isGefahrstelle = inaGefahrstelles.exists [
				prioritaetGefahrstelle?.wert.intValue === 1
			] && !inaGefahrstelles.map[IDMarkanterPunkt].empty
			// K: Sskp.Ina.Gef_Stelle
			fillIterableWithConditional(
				instance,
				cols.getColumn(Gef_Stelle),
				pzb,
				[isGefahrstelle],
				[
					inaGefahrstelles.map [
						IDMarkanterPunkt?.value?.bezeichnung?.
							bezeichnungMarkanterPunkt?.wert
					]
				],
				MIXED_STRING_COMPARATOR,
				ITERABLE_FILLING_SEPARATOR
			)

			// L: Sskp.Ina.Gef_Stelle_abstand
			fillConditional(
				instance,
				cols.getColumn(Gef_Stelle_Abstand),
				pzb,
				[isGefahrstelle],
				[
					val markanteStelle = inaGefahrstelles.map [
						IDMarkanterPunkt?.value?.IDMarkanteStelle?.value
					].filter(Punkt_Objekt)
					return AgateRounding.roundDown(
						getDistanceOfPoints(markanteStelle, it)).toString
				]
			)

			val bahnSteigKantes = pzb?.PZBElementZuordnungBP?.map [
				PZBElementZuordnungINA
			]?.map[IDBahnsteigKante?.value].toList

			val bahnsteigDistance = SskpBahnsteigUtils.
				getBahnsteigDistances(bahnSteigKantes, pzb)
			// M: Sskp.Ina.Abstand_GM_2000_Bahnsteig.Abstand_GM_2000_Bahnsteig_Anfang
			fillConditional(
				instance,
				cols.getColumn(Abstand_GM_2000_Bahnsteig_Anfang),
				pzb,
				[bahnsteigDistance.distanceStart.present],
				[
					bahnsteigDistance.distanceStart.getAsDouble.toTableInteger
				]
			)

			// N: Sskp.Ina.Abstand_GM_2000_Bahnsteig.Abstand_GM_2000_Bahnsteig_Ende
			fillConditional(
				instance,
				cols.getColumn(Abstand_GM_2000_Bahnsteig_Ende),
				pzb,
				[bahnsteigDistance.distanceEnd.present],
				[
					bahnsteigDistance.distanceEnd.getAsDouble.toTableInteger
				]
			)

			// O: Sskp.Ina.H-Tafel_Abstand
			fillIterableWithConditional(
				instance,
				cols.getColumn(H_Tafel_Abstand),
				pzb,
				[
					PZBZuordnungSignal?.map[IDSignal?.value?.signalRahmen].
						flatten.map [
							signalbegriffe
						].flatten.exists [
							hasSignalbegriffID(Ne5)
						]
				],
				[
					PZBZuordnungSignal?.map[IDSignal?.value].map [ signal |
						getPointsDistance(pzb, signal).min
					].filter[it.doubleValue !== 0.0].map [
						AgateRounding.roundDown(it).toString
					]
				],
				NUMERIC_COMPARATOR,
				ITERABLE_FILLING_SEPARATOR
			)

			// P: Sskp.Ina.Abstand_VorsignalWdh_GM_2000
			val vorSignalWiederholer = pzb.PZBZuordnungSignal?.map [
				IDSignal?.value
			].filter [
				signalReal?.signalRealAktivSchirm?.signalArt?.wert ===
					ENUMSignalArt.ENUM_SIGNAL_ART_VORSIGNALWIEDERHOLER
			]
			fillIterableWithConditional(
				instance,
				cols.getColumn(Abstand_vorsignalWdh_GM_2000),
				pzb,
				[
					!vorSignalWiederholer.isNullOrEmpty
				],
				[
					vorSignalWiederholer.map [ signal |
						signal -> getPointsDistance(pzb, signal).min
					].filter[value.doubleValue !== 0.0].map [

						'''«AgateRounding.roundDown(value).toString» «
						»(«key.bezeichnung?.bezeichnungTabelle?.wert»)'''
					]
				],
				MIXED_STRING_COMPARATOR,
				ITERABLE_FILLING_SEPARATOR
			)

			// Q: Sskp.Ina.Prüfdatum_Wirkbereichsbogen
			// Fill Attribute exixts first on Model 1.11
			fill(
				instance,
				cols.getColumn(Pruefdatum_Wirkbereichsbogen),
				pzb,
				[""]
			)

		} else {
			for (var i = 10; i < 17; i++) {
				fillBlank(instance, i)
			}
		}

		val pzbGUEs = (pzb.container.PZBElement.map[PZBElementGUE].filterNull.
			filter[IDPZBElementMitnutzung?.value === pzb] +
			#[pzb.PZBElementGUE]).filterNull

		if (!pzbGUEs.empty) {
			// R: Sskp.Gue.Pruefgeschwindigkeit
			fillIterable(
				instance,
				cols.getColumn(Pruefgeschwindigkeit),
				pzb,
				[pzbGUEs],
				null,
				[pruefgeschwindigkeit?.wert.intValue.toString]
			)

			// S: Sskp.Gue.Pruefzeit
			fillIterable(
				instance,
				cols.getColumn(Pruefzeit),
				pzb,
				[pzbGUEs],
				null,
				[pruefzeit?.wert.toTableDecimal(2)]
			)

			// T: Sskp.Gue.Messfehler
			fillIterable(
				instance,
				cols.getColumn(Messfehler),
				pzb,
				[pzbGUEs],
				null,
				[messfehler?.translate]
			)

			// U: Sskp.Gue.Messstrecke
			fillIterable(
				instance,
				cols.getColumn(Messstrecke),
				pzb,
				[pzbGUEs],
				null,
				[
					val value = GUEMessstrecke?.wert?.setScale(2,
						RoundingMode.FLOOR)
					if (value === null) {
						return ""
					}

					if (!PZBValidator.INSTANCE.
						validateGUE_Messstrecke_Type(value, null, null)) {
						val GUEMessstreckePattern = PZBValidator.
							GUE_MESSSTRECKE_TYPE__PATTERN__VALUES.flatMap [ pattern |
								pattern.map[t|t.toString]
							].firstOrNull

						throw new IllegalArgumentException('''The value: «value.toString»  isn't match the pattern: «GUEMessstreckePattern»''')
					}
					return value.toString
				]
			)

			// V: Sskp.Gue.GUE_Anordnung
			fillIterable(
				instance,
				cols.getColumn(GUE_Anordnung),
				pzb,
				[pzbGUEs],
				null,
				[GUEAnordnung?.translate]
			)

			// W: Sskp.Gue.GUE_Bauart
			fillIterable(
				instance,
				cols.getColumn(GUE_Bauart),
				pzb,
				[pzbGUEs],
				null,
				[GUEBauart?.translate]
			)

			// X: SSkp.Gue.Montageort_Schaltkastens
			fill(
				instance,
				cols.getColumn(Montageort_Schaltkastens),
				pzb,
				[
					IDUnterbringung?.value?.unterbringungAllg?.
						unterbringungBefestigung?.translate ?: ""
				]
			)

			// Y: Sskp.Gue.Energieversorgung
			fillIterable(
				instance,
				cols.getColumn(Energieversorgung),
				pzb,
				[pzbGUEs],
				null,
				[GUEEnergieversorgung?.translate]
			)
		} else {
			for (var i = 17; i < 25; i++) {
				fillBlank(instance, i)
			}
		}

		// Z: Sskp.Bemerkung
		fill(
			instance,
			cols.getColumn(Bemerkung),
			pzb,
			[]
		)

		fillFootnotes(instance, pzb)

	}

	static dispatch def String fillBezugsElement(Basis_Objekt object) {
		throw new IllegalArgumentException(object.class.simpleName)
	}

	static dispatch def String fillBezugsElement(W_Kr_Gsp_Element object) {
		return object?.bezeichnung?.bezeichnungTabelle?.wert
	}

	static dispatch def String fillBezugsElement(Signal object) {
		return object.signalReal.signalFunktion.wert ===
			ENUMSignalFunktion.ENUM_SIGNAL_FUNKTION_BUE_UEBERWACHUNGSSIGNAL
			? '''BÜ-K «object?.bezeichnung?.bezeichnungTabelle?.wert»'''
			: object?.bezeichnung?.bezeichnungTabelle?.wert
	}

	private dispatch def String getDistanceSignalTrackSwitch(TopGraph topGraph,
		PZB_Element pzb, Basis_Objekt object) {
		throw new IllegalArgumentException(object.class.simpleName)
	}

	private dispatch def String getDistanceSignalTrackSwitch(TopGraph topGraph,
		PZB_Element pzb, Signal signal) {
		val scaleValue = pzb.distanceScale
		if (signal?.signalReal?.signalFunktion?.wert !==
			ENUMSignalFunktion.ENUM_SIGNAL_FUNKTION_BUE_UEBERWACHUNGSSIGNAL) {
			val distance = AgateRounding.roundDown(
				getPointsDistance(pzb, signal).min, scaleValue)
			val directionSign = topGraph.
					isInWirkrichtungOfSignal(signal, pzb) ? "+" : "-"
			return distance == 0.0
				? distance.toTableDecimal(scaleValue)
				: '''«directionSign»«distance.toTableDecimal(scaleValue)»'''
		}

		val bueSpezifischesSignal = signal.container.BUESpezifischesSignal.
			filter [
				IDSignal?.value === signal
			]

		if (bueSpezifischesSignal.empty) {
			return ""
		}

		val bueKantens = signal.container.BUEKante.filter [ kante |
			bueSpezifischesSignal.map[IDBUEAnlage].exists [
				it === kante.IDBUEAnlage
			]
		]

		if (bueKantens.empty) {
			return ""
		}
		return AgateRounding.roundDown(getDistanceOfPoints(bueKantens, pzb),
			scaleValue).toTableDecimal(scaleValue)

	}

	private dispatch def String getDistanceSignalTrackSwitch(TopGraph topGraph,
		PZB_Element pzb, W_Kr_Gsp_Element gspElement) {
		val gspKomponent = gspElement.WKrGspKomponenten.filter [
			zungenpaar !== null
		]
		if (gspKomponent.empty) {
			throw new IllegalArgumentException('''«gspElement?.bezeichnung.bezeichnungTabelle?.wert» hast no Zungenpaar''')
		}
		return AgateRounding.roundDown(getDistanceOfPoints(gspKomponent, pzb)).
			toString
	}

	private def double getDistanceOfPoints(Iterable<? extends Punkt_Objekt> p1s,
		Punkt_Objekt p2) {
		val distance = p1s?.fold(
			Double.MAX_VALUE,
			[ Double current, Punkt_Objekt p1 |
				Math.min(current, getPointsDistance(p1, p2).min)
			]
		)
		return distance.doubleValue
	}

	private def Iterable<Double> getPointsDistance(Punkt_Objekt p1,
		Punkt_Objekt p2) {
		val points1 = p1.singlePoints.map[new TopPoint(it)]
		val points2 = p2.singlePoints.map[new TopPoint(it)]

		return points1.flatMap [ pa |
			points2.map [ pb |
				topGraphService.findShortestDistance(pa, pb)
			]
		].filter[present].map[get.doubleValue].toList
	}

	protected def int getDistanceScale(PZB_Element pzb) {
		return 0;
	}
}
