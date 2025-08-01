/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sslb

import java.math.BigDecimal
import java.util.HashMap
import java.util.Set
import org.eclipse.set.basis.Pair
import org.eclipse.set.basis.graph.TopPoint
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.core.services.graph.TopologicalGraphService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.BasisTypen.ENUMWirkrichtung
import org.eclipse.set.model.planpro.Block.Block_Element
import org.eclipse.set.model.planpro.Block.ENUMBetriebsfuehrung
import org.eclipse.set.model.planpro.Geodaten.ENUMOertlichkeitArt
import org.eclipse.set.model.planpro.Gleis.ENUMGleisart
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin

import static org.eclipse.set.feature.table.pt1.sslb.SslbColumns.*

import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienAnrueckabschnittExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BlockAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BlockElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BlockStreckeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*

/**
 * Table transformation for a Inselgleistabelle (Sslb).
 * 
 * @author Rumpf
 */
class SslbTransformator extends AbstractPlanPro2TableModelTransformator {

	var TMFactory factory = null
	val TopologicalGraphService topGraph
	// Minimum overlap distance between free reporting section and track route in meter
	static final BigDecimal MIN_OVERLAP_DISTANCE = BigDecimal.valueOf(10)

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService,
		TopologicalGraphService topGraphService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
		topGraph = topGraphService
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory, Stell_Bereich controlArea) {
		this.factory = factory
		return container.transform(controlArea)
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup container, Stell_Bereich controlArea) {

		val validObjects = container.blockElement.filter[isPlanningObject].
			filterObjectsInControlArea(controlArea).filterNull
		val fmaLookupCache = getFMALookupCache(container)
		validObjects.flatMap[findRelevantBlockElements].filterNull.forEach [ it |
			if (Thread.currentThread.interrupted) {
				return
			}
			it.transform(fmaLookupCache)
		]
		return
	}

	private def getFMALookupCache(MultiContainer_AttributeGroup container) {
		val lookupTable = newHashMap
		// Find FMA Tracks which majorly overlap a route track
		val routeTrackTypes = container.gleisArt.filter [
			gleisart?.wert === ENUMGleisart.ENUM_GLEISART_STRECKENGLEIS
		].flatMap[bereichObjektTeilbereich]

		container.FMAAnlage.map[it -> IDGleisAbschnitt?.value].filterNull.filter [ fmaTrack |
			val overlappingDistance = routeTrackTypes.fold(
				BigDecimal.ZERO, [ sum, rtt |
					sum.add(getOverlappingLength(fmaTrack.value, rtt))
				])

			overlappingDistance.compareTo(MIN_OVERLAP_DISTANCE) >= 0
		].forEach [
			val fmaObject = it.key
			value.bereichObjektTeilbereich?.filter[IDTOPKante?.value !== null].
				forEach [
					lookupTable.put(
						new TopPoint(IDTOPKante?.value, begrenzungA?.wert),
						fmaObject)
					lookupTable.put(
						new TopPoint(IDTOPKante?.value, begrenzungB?.wert),
						fmaObject)
				]
		]

		return lookupTable
	}

	private def findRelevantBlockElements(Block_Element element) {
		val blockAnlage = element.container.blockAnlage.findFirst [
			IDBlockElementA?.value === element ||
				IDBlockElementB?.value === element
		]
		if (blockAnlage === null)
			return #[element]
		val elementA = blockAnlage.IDBlockElementA?.value
		val elementB = blockAnlage.IDBlockElementB?.value

		switch (element.IDBlockStrecke?.value?.blockStreckeAllg?.betriebsfuehrung?.wert) {
			// For eingl/zweigl gwb, consider both elements
			case ENUM_BETRIEBSFUEHRUNG_EINGL,
			case ENUM_BETRIEBSFUEHRUNG_ZWEIGL_GWB: {
				return #[elementA, elementB]
			}
			// For zweigl only consider element A, discarding element B
			case ENUM_BETRIEBSFUEHRUNG_ZWEIGL: {
				return #[elementA]
			}
			// Otherwise return only element
			default:
				return #[element]
		}
	}

	private def TableRow create factory.newTableRow(blockElement) transform(
		Block_Element blockElement, HashMap<TopPoint, FMA_Anlage> fmaCache) {
		val blockAnlage = blockElement.container.blockAnlage.findFirst [
			IDBlockElementA?.value === blockElement ||
				IDBlockElementB?.value === blockElement
		]

		val isElementA = blockElement === blockAnlage?.IDBlockElementA?.value
		val isElementB = blockElement === blockAnlage?.IDBlockElementB?.value
		val otherBlockElement = isElementA ? blockAnlage?.IDBlockElementB?.
				value : blockAnlage?.IDBlockElementA?.value

		val row = it
		// A: Sslb.Strecke.Nummer
		fill(
			cols.getColumn(Strecke_Nummer),
			blockElement,
			[
				blockElement?.blockStrecke?.strecke?.bezeichnung?.
					bezeichnungStrecke?.wert
			]
		)

		// B: Sslb.Strecke.Gleis
		fill(
			cols.getColumn(Strecke_Gleis),
			blockAnlage,
			[
				IDGleisBezeichnung?.value?.bezeichnung?.
					bezGleisBezeichnung?.wert
			]
		)

		// C: Sslb.Strecke.Regel_Gegengleis
		fillConditional(
			cols.getColumn(Regel_Gegengleis),
			blockElement,
			[
				blockAnlage !== null &&
					IDBlockStrecke?.value?.blockStreckeAllg?.betriebsfuehrung?.
						wert ==
						ENUMBetriebsfuehrung.ENUM_BETRIEBSFUEHRUNG_ZWEIGL_GWB
			],
			[
				if (isElementA)
					return "R"
				if (isElementB)
					return "G"
			]
		)

		// D: Sslb.Strecke.Streckenziel_Start
		fillConditional(
			cols.getColumn(Streckenziel_Start),
			blockElement,
			[isPlanningObject],
			[IDSignal?.value?.bezeichnung?.bezeichnungTabelle?.wert],
			['''(«IDSignal?.value?.bezeichnung?.bezeichnungTabelle?.wert»)''']
		)

		// E: Sslb.Strecke.Betriebsfuehrung
		fill(
			cols.getColumn(Betriebsfuehrung),
			blockElement,
			[
				blockStrecke?.blockStreckeAllg?.betriebsfuehrung?.translate
			]
		)

		// F: Sslb.Grundsatzangaben.von.Betriebsstelle_Start
		fill(
			cols.getColumn(Betriebsstelle_Start),
			otherBlockElement,
			[
				IDBlockStrecke?.value?.IDBetriebsstelleNachbar?.value?.
					bezeichnung?.oertlichkeitAbkuerzung?.wert
			]
		)

		// G: Sslb.Grundsatzangaben.von.Bauform_Start
		fill(
			cols.getColumn(Bauform_Start),
			blockElement,
			[blockElement?.blockElementAllg?.blockBauform?.translate]
		)

		// H: Sslb.Grundsatzangaben.von.Streckenfreimeldung
		fillConditional(
			cols.getColumn(Streckenfreimeldung),
			blockElement,
			[IDSignal?.value !== null],
			[
				val signal = blockElement.IDSignal?.value
				val closestPointOpt = topGraph.findClosestPoint(
					new TopPoint(signal), fmaCache.keySet.toList,
					IDSignal?.value.punktObjektTOPKante?.first?.wirkrichtung?.
						wert === ENUMWirkrichtung.ENUM_WIRKRICHTUNG_IN)
				if (closestPointOpt.empty)
					return ""

				val closestPoint = closestPointOpt.get()

				// Check if the closest point is a duplicate
				val fmas = fmaCache.filter [ point, fma |
					point.equalLocation(closestPoint)
				].values.toSet

				if (fmas.size > 1) {
					return fmas.filter [
						val ga = IDGleisAbschnitt?.value
						return ga !== null && ga.contains(signal)
					].map[bzBezeichner].join(ITERABLE_FILLING_SEPARATOR)
				} else {
					return fmas.get(0)?.bzBezeichner
				}

			]
		)

		// I: Sslb.Grundsatzangaben.nach.Betriebsstelle_Ziel
		fill(
			cols.getColumn(Betriebsstelle_Ziel),
			blockElement,
			[
				IDBlockStrecke?.value?.IDBetriebsstelleNachbar?.value?.
					bezeichnung?.oertlichkeitAbkuerzung?.wert
			]
		)

		// J: Sslb.Grundsatzangaben.nach.Bauform_Ziel
		fill(
			cols.getColumn(Bauform_Ziel),
			otherBlockElement,
			[blockElementAllg?.blockBauform?.translate]
		)

		// K: Sslb.Grundsatzangaben.Blockschaltung
		fill(
			cols.getColumn(Blockschaltung),
			blockAnlage,
			[
				blockAnlageAllg?.schaltung?.translate
			]
		)

		// L: Sslb.Grundsatzangaben.Schutzuebertrager
		fill(
			cols.getColumn(Schutzuebertrager),
			blockAnlage,
			[
				blockAnlageAllg?.schutzuebertrager?.wert?.translate
			]
		)

		// M: Sslb.Erlaubnis.staendig
		fill(
			cols.getColumn(Erlaubnis_staendig),
			blockElement,
			[
				blockElementErlaubnis?.erlaubnisStaendigVorhanden?.wert?.
					translate
			]
		)

		// N: Sslb.Erlaubnis.holen
		fillSwitch(
			cols.getColumn(Erlaubnis_holen),
			blockElement,
			new Case<Block_Element>(
				[
					blockElementErlaubnis?.autoErlaubnisholen?.wert ==
						Boolean.TRUE
				],
				["auto"]
			),
			new Case<Block_Element>(
				[
					blockElementErlaubnis?.erlaubnisholen?.wert == Boolean.TRUE
				],
				["manuell"]
			)
		)

		// O: Sslb.Erlaubnis.Ruecklauf_autom
		fill(
			cols.getColumn(Erlaubnis_Ruecklauf_autom),
			blockElement,
			[
				blockElementErlaubnis?.autoErlaubnisruecklauf?.wert?.translate
			]
		)

		// P: Sslb.Erlaubnis.Abgabespeicherung
		fill(
			cols.getColumn(Erlaubnis_Abgabespeicherung),
			blockElement,
			[
				blockElementErlaubnis?.erlaubnisabgabespeicherung?.wert?.
					translate
			]
		)

		// Q: Sslb.Erlaubnis.Abh_D_Weg_Rf
		fillConditional(
			cols.getColumn(Erlaubnis_Abh_D_Weg_Rf),
			blockElement,
			[
				signal !== null || fstrZugRangier !== null
			],
			["x"]
		)

		val gleisabschnittAnordnung = newLinkedList
		// R: Sslb.Blockmeldung.Anrueckabschnitt.Bezeichnung 
		fillIterable(
			cols.getColumn(Anrueckabschnitt_Bezeichnung),
			blockElement,
			[
				val gleisbezeichnungenStart = blockAnlagenStart.map [
					gleisBezeichnung
				].toList
				val gleisbezeichnungenZiel = blockAnlagenZiel.map [
					gleisBezeichnung
				].toList
				val gleisbezeichnungenList = newLinkedList
				gleisbezeichnungenList.addAll(gleisbezeichnungenStart)
				gleisbezeichnungenList.addAll(gleisbezeichnungenZiel)
				val gleisbezeichnungen = gleisbezeichnungenList.toSet
				val anrueckGleisAbschnittPairs = it.container.
					bedienAnrueckabschnitt.map [
						new Pair(it, gleisAbschnittPosition)
					].filter [
						val anrueckGleisAbschnittPair = it
						gleisbezeichnungen.exists [
							it.intersects(anrueckGleisAbschnittPair.second)
						]
					]
				gleisabschnittAnordnung.addAll(anrueckGleisAbschnittPairs.map [
					second
				])
				anrueckGleisAbschnittPairs.map[first].map [
					var gleisAbschnittDarstellen = gleisAbschnittDarstellen
					if (gleisAbschnittDarstellen !== null) {
						gleisAbschnittDarstellen?.bezeichnung?.
							bezeichnungTabelle?.wert ?: ""
					} else {
						bezeichnung?.bezBedAnrueckabschnitt?.wert ?: ""
					}
				]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// S: Sslb.Blockmeldung.Anrueckabschnitt.Anordnung
		fillIterable(
			cols.getColumn(Anrueckabschnitt_Anordnung),
			blockElement,
			[
				gleisabschnittAnordnung.map [
					bezeichnung?.bezeichnungTabelle?.wert ?: ""
				]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// T: Sslb.Blockmeldung.Zugschluss
		fill(
			cols.getColumn(Blockmeldung_Zugschluss),
			blockElement,
			[
				IDZugschlussmeldung?.value?.IDBedienEinrichtungOertlich?.value?.
					bezeichnung?.bedienEinrichtOertlBez?.wert
			]
		)

		// U: Sslb.Blockmeldung.Raeumungspruefung
		fill(
			cols.getColumn(Blockmeldung_Raeumungspruefung),
			blockElement,
			[raeumungspruefung]
		)

		// V: Sslb.Akustische_Meldung.Vorblock
		fill(
			cols.getColumn(Akustische_Meldung_Vorblock),
			blockElement,
			[blockElementAllg?.vorblockwecker?.wert?.translate]
		)

		// W: Sslb.Akustische_Meldung.Rueckblock
		fill(
			cols.getColumn(Akustische_Meldung_Rueckblock),
			blockElement,
			[blockElementAllg?.rueckblockwecker?.wert?.translate]
		)

		// X: Sslb.Awanst.Bez_Bed
		fillConditional(
			cols.getColumn(Awanst_Bez_Bed),
			blockElement,
			[
				blockStrecke?.oertlichkeit?.bezeichnung?.
					oertlichkeitAbkuerzung !== null &&
					blockElement.blockStrecke?.oertlichkeit?.oertlichkeitAllg?.
						oertlichkeitArt?.wert ===
						ENUMOertlichkeitArt.ENUM_OERTLICHKEIT_ART_AWANST
			],
			[fillBezBed]
		)

		// Y: Sslb.Bemerkung
		fillFootnotes(row, blockElement)

		return
	}

	private static def String fillBezBed(Block_Element blockElement) {
		val oertlichkeit = blockElement.blockStrecke?.oertlichkeit
		val oertlichkeitAbk = oertlichkeit?.bezeichnung?.
			oertlichkeitAbkuerzung?.wert
		val oertlichkeitAwanst = oertlichkeit?.IDOertlichkeitAwanstBedient?.
			value?.bezeichnung?.oertlichkeitAbkuerzung
		return '''«oertlichkeitAbk» («oertlichkeitAwanst»)'''
	}
}
