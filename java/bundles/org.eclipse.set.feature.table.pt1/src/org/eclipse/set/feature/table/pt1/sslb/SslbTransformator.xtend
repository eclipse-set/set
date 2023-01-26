/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sslb

import org.eclipse.set.basis.Pair
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.format.TextAlignment
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.toolboxmodel.Block.Block_Element
import org.eclipse.set.toolboxmodel.Geodaten.ENUMOertlichkeitArt
import org.eclipse.set.utils.table.TMFactory

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienAnrueckabschnittExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienAnzeigeElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BlockAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BlockElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BlockStreckeExtensions.*

/**
 * Table transformation for a Inselgleistabelle (Sslb).
 * 
 * @author Rumpf
 */
class SslbTransformator extends AbstractPlanPro2TableModelTransformator {

	val SslbColumns columns
	var TMFactory factory = null

	new(SslbColumns columns, EnumTranslationService enumTranslationService) {
		super(enumTranslationService)
		this.columns = columns;
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		this.factory = factory
		return container.transform
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup container) {
		container.blockElement.forEach [ it |
			if (Thread.currentThread.interrupted) {
				return
			}
			it.transform
		]
		return
	}

	private def TableRow create factory.newTableRow(blockElement) transform(
		Block_Element blockElement) {

		val row = it
		fill(columns.nummer, blockElement, [
			blockElement?.blockStrecke?.strecke?.bezeichnung?.
				bezeichnungStrecke?.wert
		])

		fillIterable(
			columns.gleis,
			blockElement,
			[
				blockElement.blockAnlagenStart.map [
					gleisBezeichnung?.bezeichnung?.bezGleisBezeichnung?.wert
				]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		fill(columns.betriebsfuehrung, blockElement, [
			blockElement?.blockStrecke?.blockStreckeAllg?.betriebsfuehrung?.
				wert?.translate
		])

		fillConditional(columns.betriebsst_Start, blockElement, [
			!blockElement.blockAnlagenStart.isEmpty
		], [
			blockElement?.blockStrecke?.oertlichkeit?.bezeichnung?.
				oertlichkeitAbkuerzung?.wert
		])

		fillConditional(columns.bauform_Start, blockElement, [
			!blockElement.blockAnlagenStart.isEmpty
		], [blockElement?.blockElementAllg?.blockBauform?.wert?.translate])

		fillConditional(columns.betriebsst_Ziel, blockElement, [
			!blockElement.blockAnlagenZiel.isEmpty
		], [
			blockElement?.blockStrecke?.oertlichkeit?.bezeichnung?.
				oertlichkeitAbkuerzung?.wert
		])

		fillConditional(columns.bauform_Ziel, blockElement, [
			!blockElement.blockAnlagenZiel.isEmpty
		], [blockElement?.blockElementAllg?.blockBauform?.wert?.translate])

		fillIterable(
			columns.blockschaltung,
			blockElement,
			[
				blockElement.blockAnlagenStart.map [
					blockAnlageAllg?.schaltung?.wert?.translate
				]
			],
			null,
			[it]
		)

		fillIterable(
			columns.schutzuebertrager,
			blockElement,
			[
				blockElement.blockAnlagenStart.map [
					blockAnlageAllg?.schutzuebertrager?.wert?.translate
				]
			],
			null,
			[it]
		)

		fill(columns.staendig, blockElement, [
			blockElement.blockElementErlaubnis?.erlaubnisStaendigVorhanden?.
				wert?.translate
		])

		fillSwitch(columns.holen, blockElement, new Case<Block_Element>(
			[
				blockElement?.blockElementErlaubnis?.autoErlaubnisholen?.wert ==
					Boolean.TRUE
			],
			["auto"]
		), new Case<Block_Element>(
			[
				blockElement?.blockElementErlaubnis?.erlaubnisholen?.wert ==
					Boolean.TRUE
			],
			["manuell"]
		))

		val gleisabschnittAnordnung = newLinkedList

		fillIterable(
			columns.anrueckabschnitt_bezeichnung,
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

		fillIterable(
			columns.anrueckabschnitt_anordnung,
			blockElement,
			[
				gleisabschnittAnordnung.map [
					bezeichnung?.bezeichnungTabelle?.wert ?: ""
				]
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		fill(columns.ruecklauf_autom, blockElement, [
			blockElement?.blockElementErlaubnis?.autoErlaubnisruecklauf?.wert?.
				translate
		])

		fill(columns.abgabespeicherung, blockElement, [
			blockElement?.blockElementErlaubnis?.erlaubnisabgabespeicherung?.
				wert?.translate
		])

		fill(columns.raeumungspruefung, blockElement, [
			blockElement?.raeumungspruefung
		])
		fill(columns.vorblock, blockElement, [
			blockElement?.blockElementAllg?.vorblockwecker?.wert?.translate
		])
		fill(columns.rueckblock, blockElement, [
			blockElement?.blockElementAllg?.rueckblockwecker?.wert?.translate
		])

		fillConditional(columns.abh_d_weg_rf, blockElement, [
			blockElement.signal !== null || blockElement.fstrZugRangier !== null
		], ["x"])
		fill(columns.zugschluss, blockElement, [
			blockElement?.bedienanzeigeElement?.bedienEinrichtungOertlich?.
				bezeichnung?.bedienEinrichtOertlBez?.wert
		])
		fill(
			columns.basis_bemerkung,
			blockElement,
			[footnoteTransformation.transform(it, row)]
		)
		fillConditional(columns.awanst_bez_bed, blockElement, [
			blockElement.blockStrecke?.oertlichkeit?.bezeichnung?.
				oertlichkeitAbkuerzung !== null &&
				blockElement.blockStrecke?.oertlichkeit?.oertlichkeitAllg?.
					oertlichkeitArt?.wert ===
					ENUMOertlichkeitArt.ENUM_OERTLICHKEIT_ART_AWANST
		], [
			blockElement.blockStrecke?.oertlichkeit?.bezeichnung?.
				oertlichkeitAbkuerzung?.wert
		])

		return
	}

	override void formatTableContent(Table table) {
		// D: Sslb.Grundsatzangaben.von.Betriebsst_Start
		table.setTextAlignment(3, TextAlignment.LEFT);

		// F: Sslb.Grundsatzangaben.nach.Betriebsst_Ziel
		table.setTextAlignment(5, TextAlignment.LEFT);

		// V: Sslb.Bemerkung
		table.setTextAlignment(21, TextAlignment.LEFT);
	}
}
