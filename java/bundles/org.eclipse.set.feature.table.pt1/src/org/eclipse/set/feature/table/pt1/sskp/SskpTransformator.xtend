/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sskp

import java.util.Set
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.ppmodel.extensions.utils.TopGraph
import org.eclipse.set.toolboxmodel.Bahnsteig.Bahnsteig_Kante
import org.eclipse.set.toolboxmodel.Basisobjekte.Basis_Objekt
import org.eclipse.set.toolboxmodel.Basisobjekte.Punkt_Objekt
import org.eclipse.set.toolboxmodel.Fahrstrasse.Fstr_DWeg
import org.eclipse.set.toolboxmodel.PZB.ENUMPZBArt
import org.eclipse.set.toolboxmodel.PZB.ENUMWirksamkeitFstr
import org.eclipse.set.toolboxmodel.PZB.PZB_Element
import org.eclipse.set.toolboxmodel.Signalbegriffe_Ril_301.Ne5
import org.eclipse.set.toolboxmodel.Signale.ENUMSignalArt
import org.eclipse.set.toolboxmodel.Signale.ENUMSignalFunktion
import org.eclipse.set.toolboxmodel.Signale.Signal
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.utils.math.AgateRounding
import org.eclipse.set.utils.table.TMFactory

import static org.eclipse.set.basis.constants.ToolboxConstants.NUMERIC_COMPARATOR
import static org.eclipse.set.feature.table.pt1.sskp.SskpColumns.*

import static extension org.eclipse.set.basis.graph.Digraphs.*
import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PZBElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalRahmenExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalbegriffExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.TopKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import static extension org.eclipse.set.utils.math.BigDecimalExtensions.*
import static extension org.eclipse.set.utils.math.BigIntegerExtensions.*
import static extension org.eclipse.set.utils.math.DoubleExtensions.*

/**
 * Table transformation for a PZB-Tabelle (Sskp)
 * 
 * @author Truong
 */
class SskpTransformator extends AbstractPlanPro2TableModelTransformator {
	static final double ADDITION_SCHUTZSTRECKE_SOLL_60 = 450
	static final double ADDITION_SCHUTZSTRECKE_SOLL_40_60 = 350
	static final double ADDITION_SCHUTZSTRECKE_SOLL_40 = 210

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService) {
		super(cols, enumTranslationService)
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {

		val topGraph = new TopGraph(container.TOPKante)
		for (PZB_Element pzb : container.PZBElement.filter [
			PZBElementGUE?.IDPZBElementMitnutzung === null
		]) {
			if (Thread.currentThread.interrupted) {
				return null
			}

			val rg = factory.newRowGroup(pzb)

			val isPZB2000 = pzb.PZBArt?.wert ===
				ENUMPZBArt.ENUMPZB_ART_2000_HZ ||
				pzb.PZBArt?.wert === ENUMPZBArt.ENUMPZB_ART_1000_2000_HZ

			if (!isPZB2000) {
				val instance = rg.newTableRow()
				fillRowGroupContent(instance, pzb, null, topGraph)
			} else {
				pzb.fstrDWegs.forEach [
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
			[PZBElementBezugspunkt.map[fillBezugsElement]],
			MIXED_STRING_COMPARATOR
		)

		// B: Sskp.Bezug.Wirkfrequenz
		fill(
			instance,
			cols.getColumn(Wirkfrequenz),
			pzb,
			[PZBArt?.wert?.translate]
		)

		val isPZB2000 = pzb.PZBArt?.wert === ENUMPZBArt.ENUMPZB_ART_2000_HZ ||
			pzb.PZBArt?.wert === ENUMPZBArt.ENUMPZB_ART_1000_2000_HZ

		// C: Sskp.PZB_Schutzstrecke.PZB_Schutzpunkt
		fillConditional(
			instance,
			cols.getColumn(PZB_Schutzpunkt),
			dweg,
			[isPZB2000],
			[
				IDPZBGefahrpunkt?.bezeichnung?.bezeichnungMarkanterPunkt?.wert
			]
		)

		// D: Sskp.PZB_Schutzstrecke.GeschwindigkeitsKlasse
		fillConditional(
			instance,
			cols.getColumn(GeschwindigkeitsKlasse),
			dweg,
			[isPZB2000 && IDPZBGefahrpunkt !== null],
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
			[isPZB2000 && IDPZBGefahrpunkt !== null],
			[
				val dwegV = fstrDWegSpezifisch?.DWegV?.wert.toInteger
				val inclination = fstrDWegAllg?.massgebendeNeigung?.wert.
					toDouble
				val multipleValue = inclination < 0 ? 0.05 : 0.1
				if (dwegV === 0) {
					return ""
				}

				if (dwegV > 60) {
					return '''«AgateRounding.roundUp(inclination * multipleValue * 200 + ADDITION_SCHUTZSTRECKE_SOLL_60)»'''
				} else if (dwegV <= 60 && dwegV > 40) {
					return '''«AgateRounding.roundUp(inclination * multipleValue * 100 + ADDITION_SCHUTZSTRECKE_SOLL_40_60)»'''
				} else if (dwegV <= 40) {
					return '''«AgateRounding.roundUp(inclination * multipleValue * 50 + ADDITION_SCHUTZSTRECKE_SOLL_40)»'''
				}
				return ""
			]
		)

		// F: Sskp.PZB_Schutzstrecke.PZB_Schutzstr.PZB_Schutzstrecke_Ist
		fillConditional(
			instance,
			cols.getColumn(PZB_Schutzstrecke_Ist),
			dweg,
			[
				isPZB2000 && IDPZBGefahrpunkt !== null
			],
			[
				val markanteStelle = dweg?.IDPZBGefahrpunkt?.IDMarkanteStelle
				if (markanteStelle instanceof Punkt_Objekt)
					return AgateRounding.roundDown(
						getPointsDistance(topGraph, markanteStelle,
							dweg.IDFstrFahrweg?.IDStart).min).toString
				else
					return ""
			]
		)

		// G: Sskp.Gleismagnete.Wirksamkeit
		fillIterable(
			instance,
			cols.getColumn(Wirksamkeit),
			pzb,
			[
				PZBElementZuordnungBP.map [
					switch (wirksamkeit?.wert) {
						case ENUM_WIRKSAMKEIT_SCHALTBAR_VON_SIGNAL,
						case ENUM_WIRKSAMKEIT_SONSTIGE: {
							wirksamkeit?.wert?.translate
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
			].exists[signal|signal === IDSignal]
		]
		fillSwitch(
			instance,
			cols.getColumn(Wirksamkeit_Bedingung),
			pzb,
			new Case<PZB_Element>(
				[
					!PZBElementZuordnungFstr.map[IDFstrZugRangier].empty ||
						(PZBElementGUE !== null &&
							PZBElementZuordnungFstr.exists [
								wirksamkeitFstr?.wert === ENUMWirksamkeitFstr.
									ENUM_WIRKSAMKEIT_FSTR_STAENDIG_WIRKSAM_WENN_FAHRSTRASSE_EINGESTELLT
							])
				],
				[
					PZBElementZuordnungFstr.map [
						val wirksamKeit = wirksamkeitFstr?.wert?.translate
						val fstrZugRangier = IDFstrZugRangier.
							fstrZugRangierBezeichnung
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
					IDPZBElementZuordnung?.PZBElementZuordnungFstr.flatMap [
						wirksamkeitFstr?.IDBearbeitungsvermerk
					].map [
						bearbeitungsvermerkAllg?.kurztext?.wert
					].filterNull
				],
				ITERABLE_FILLING_SEPARATOR,
				MIXED_STRING_COMPARATOR
			),
			new Case<PZB_Element>(
				[!bueSpezifischeSignals.empty],
				[
					bueSpezifischeSignals.map [
						IDBUEAnlage?.bezeichnung?.bezeichnungTabelle?.wert
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
				PZBElementBezugspunkt.map [
					getDistanceSignalTrackSwtich(topGraph, pzb, it)
				]
			],
			null
		)

		// J: Sskp.Gleismagnete.Abstand_GM_2000
		fillSwitch(
			instance,
			cols.getColumn(Abstand_GM_2000),
			pzb,
			new Case<PZB_Element>(
				[PZBArt?.wert === ENUMPZBArt.ENUMPZB_ART_500_HZ],
				[
					""
				]
			),
			new Case<PZB_Element>(
				[PZBArt?.wert === ENUMPZBArt.ENUMPZB_ART_1000_HZ],
				[
					val bezugspunktSignals = PZBElementBezugspunkt.filter(
						Signal)
					val pzbZuordnungSignals = PZBZuordnungSignal.map [
						IDSignal
					]
					val distance = bezugspunktSignals.filter [
						pzbZuordnungSignals.contains(it)
					].map [
						AgateRounding.roundDown(
							topGraph.getPointsDistance(pzb, it).min)
					].filter[it !== 0]
					return distance.map[it.toString]
				],
				ITERABLE_FILLING_SEPARATOR,
				NUMERIC_COMPARATOR
			)
		)

		if (pzb.PZBElementZuordnungBP !== null && pzb.PZBElementZuordnungBP.exists [
			PZBElementZuordnungINA !== null
		] && (pzb.PZBArt?.wert === ENUMPZBArt.ENUMPZB_ART_2000_HZ ||
			pzb.PZBArt?.wert === ENUMPZBArt.ENUMPZB_ART_1000_2000_HZ)) {
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
						IDMarkanterPunkt?.bezeichnung?.
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
						IDMarkanterPunkt?.IDMarkanteStelle
					].filter(Punkt_Objekt)
					return getDistanceOfPoints(topGraph, markanteStelle, it)
				]
			)

			val bahnSteigKantes = pzb?.PZBElementZuordnungBP?.map [
				PZBElementZuordnungINA
			]?.map[IDBahnsteigKante]
			val bahnSteigAbstand = bahnSteigKantes.map [
				pzb.getBahnsteigAbstand(it)
			].flatten
			// M: Sskp.Ina.Abstand_GM_2000_Bahnsteig.Abstand_GM_2000_Bahnsteig_Anfang
			fillConditional(
				instance,
				cols.getColumn(Abstand_GM_2000_Bahnsteig_Anfang),
				pzb,
				[!bahnSteigKantes.empty],
				[
					bahnSteigAbstand.max.toTableInteger
				]
			)

			// N: Sskp.Ina.Abstand_GM_2000_Bahnsteig.Abstand_GM_2000_Bahnsteig_Ende
			fillConditional(
				instance,
				cols.getColumn(Abstand_GM_2000_Bahnsteig_Ende),
				pzb,
				[!bahnSteigKantes.empty],
				[
					bahnSteigAbstand.min.toTableInteger
				]
			)

			// O: Sskp.Ina.H-Tafel_Abstand
			fillIterableWithConditional(
				instance,
				cols.getColumn(H_Tafel_Abstand),
				pzb,
				[
					PZBZuordnungSignal?.map[IDSignal?.signalRahmen].flatten.map [
						signalbegriffe
					].flatten.exists [
						hasSignalbegriffID(Ne5)
					]
				],
				[
					PZBZuordnungSignal?.map[IDSignal].map [ signal |
						getPointsDistance(topGraph, pzb, signal).min
					].filter[it.doubleValue === 0.0].map [
						AgateRounding.roundDown(it).toString
					]
				],
				NUMERIC_COMPARATOR,
				ITERABLE_FILLING_SEPARATOR
			)

			// P: Sskp.Ina.Abstand_VorsignalWdh_GM_2000
			fillIterableWithConditional(
				instance,
				cols.getColumn(Abstand_vorsignalWdh_GM_2000),
				pzb,
				[
					PZBZuordnungSignal?.map[IDSignal].exists [
						signalReal?.signalRealAktivSchirm?.signalArt?.wert ===
							ENUMSignalArt.ENUM_SIGNAL_ART_VORSIGNALWIEDERHOLER
					]
				],
				[
					PZBZuordnungSignal?.map[IDSignal].map [ signal |
						getPointsDistance(topGraph, pzb, signal).min
					].filter[it.doubleValue === 0.0].map [
						AgateRounding.roundDown(it).toString
					]
				],
				NUMERIC_COMPARATOR,
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
			for (var i = 9; i < 15; i++) {
				fillBlank(instance, i)
			}
		}

		val pzbGUEs = (pzb.container.PZBElement.map[PZBElementGUE].filterNull.
			filter[IDPZBElementMitnutzung === pzb] + #[pzb.PZBElementGUE]).
			filterNull

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
				[pruefzeit?.wert.toTableInteger]
			)

			// T: Sskp.Gue.Messfehler
			fillIterable(
				instance,
				cols.getColumn(Messfehler),
				pzb,
				[pzbGUEs],
				null,
				[messfehler?.wert.translate]
			)

			// U: Sskp.Gue.Messstrecke
			fillIterable(
				instance,
				cols.getColumn(Messstrecke),
				pzb,
				[pzbGUEs],
				null,
				[GUEMessstrecke?.wert.intValue.toString]
			)

			// V: Sskp.Gue.GUE_Anordnung
			fillIterable(
				instance,
				cols.getColumn(GUE_Anordnung),
				pzb,
				[pzbGUEs],
				null,
				[GUEAnordnung?.wert.translate]
			)

			// W: Sskp.Gue.GUE_Bauart
			fillIterable(
				instance,
				cols.getColumn(GUE_Bauart),
				pzb,
				[pzbGUEs],
				null,
				[GUEBauart?.wert.translate]
			)

			// X: SSkp.Gue.Montageort_Schaltkastens
			fill(
				instance,
				cols.getColumn(Montageort_Schaltkastens),
				pzb,
				[
					IDUnterbringung?.unterbringungAllg?.
						unterbringungBefestigung?.wert.translate
				]
			)

			// Y: Sskp.Gue.Energieversorgung
			fillIterable(
				instance,
				cols.getColumn(Energieversorgung),
				pzb,
				[pzbGUEs],
				null,
				[GUEEnergieversorgung?.wert.translate]
			)
		} else {
			for (var i = 15; i < 23; i++) {
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

	}

	private dispatch def String fillBezugsElement(Basis_Objekt object) {
		throw new IllegalArgumentException(object.class.simpleName)
	}

	private dispatch def String fillBezugsElement(W_Kr_Gsp_Element object) {
		return object?.bezeichnung?.bezeichnungTabelle?.wert
	}

	private dispatch def String fillBezugsElement(Signal object) {
		return object.signalReal.signalFunktion.wert ===
			ENUMSignalFunktion.ENUM_SIGNAL_FUNKTION_BUE_UEBERWACHUNGSSIGNAL
			? '''BÜ-K «object?.bezeichnung?.bezeichnungTabelle?.wert»''' : object?.
			bezeichnung?.bezeichnungTabelle?.wert
	}

	private dispatch def String getDistanceSignalTrackSwtich(TopGraph topGraph,
		PZB_Element pzb, Basis_Objekt object) {
		throw new IllegalArgumentException(object.class.simpleName)
	}

	private dispatch def String getDistanceSignalTrackSwtich(TopGraph topGraph,
		PZB_Element pzb, Signal signal) {
		if (signal?.signalReal?.signalFunktion?.wert !==
			ENUMSignalFunktion.ENUM_SIGNAL_FUNKTION_BUE_UEBERWACHUNGSSIGNAL) {
			val distance = AgateRounding.roundDown(
				getPointsDistance(topGraph, pzb, signal).min)
			val directionSign = topGraph.
					isInWirkrichtungOfSignal(signal, pzb) ? "+" : "-"
			return distance == 0 ? distance.
				toString : '''«directionSign»«distance.toString»'''
		}

		val bueSpezifischesSignal = signal.container.BUESpezifischesSignal.
			filter [
				IDSignal === signal
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
		return getDistanceOfPoints(topGraph, bueKantens, pzb)

	}

	private dispatch def String getDistanceSignalTrackSwtich(TopGraph topGraph,
		PZB_Element pzb, W_Kr_Gsp_Element gspElement) {
		val gspKomponent = gspElement.WKrGspKomponenten.filter [
			zungenpaar !== null
		]
		if (gspKomponent.empty) {
			throw new IllegalArgumentException('''«gspElement?.bezeichnung.bezeichnungTabelle?.wert» hast no Zungenpaar''')
		}
		return getDistanceOfPoints(topGraph, gspKomponent, pzb)
	}

	private def String getDistanceOfPoints(TopGraph topGraph,
		Iterable<? extends Punkt_Objekt> p1s, Punkt_Objekt p2) {
		val distance = p1s?.fold(
			Double.MAX_VALUE,
			[ Double current, Punkt_Objekt p1 |
				Math.min(current, topGraph.getPointsDistance(p1, p2).min)
			]
		)
		if (distance.doubleValue === 0) {
			return ""
		}
		return AgateRounding.roundDown(distance).toString
	}

	private def Iterable<Double> getPointsDistance(TopGraph topGraph,
		Punkt_Objekt p1, Punkt_Objekt p2) {
		val distances = topGraph.getPaths(p1.singlePoints, p2.singlePoints)
		return distances.isNullOrEmpty ? #[] : distances.map[it.length]
	}

	private def Iterable<Double> getBahnsteigAbstand(PZB_Element pzb,
		Bahnsteig_Kante bahnsteigKanten) {
		return bahnsteigKanten.bereichObjektTeilbereich.flatMap [
			val pair = IDTOPKante.getAbstandBO(pzb as Punkt_Objekt, it)
			return #[pair.key.toDouble, pair.value.toDouble]
		]
	}
}
