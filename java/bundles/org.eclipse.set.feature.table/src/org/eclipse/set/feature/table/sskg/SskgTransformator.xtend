/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sskg

import org.eclipse.set.toolboxmodel.Ortung.FMA_Komponente
import org.eclipse.set.toolboxmodel.Ortung.Zugeinwirkung
import java.util.ArrayList
import java.util.NoSuchElementException
import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.utils.table.TMFactory
import org.eclipse.set.feature.table.messages.MessagesWrapper
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.format.TextAlignment
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.TopGraph

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaKomponenteAchszaehlpunktExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaKomponenteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.MarkanterPunktExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektStreckeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ZugEinwirkungExtensions.*
import static extension org.eclipse.set.utils.graph.DigraphExtensions.*

/**
 * Table transformation for a Gleisschaltmitteltabelle (SSKG).
 * 
 * @author Rumpf
 */
class SskgTransformator extends AbstractPlanPro2TableModelTransformator {

	SskgColumns cols;

	new(SskgColumns columns, MessagesWrapper messagesWrapper) {
		super(messagesWrapper)
		this.cols = columns;
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		val instances = new ArrayList<TableRow>
		val topGraph = new TopGraph(container.TOPKante)

		for (Zugeinwirkung ein : container.zugeinwirkung) {
			if (Thread.currentThread.interrupted) {
				return null
			}
			val TableRow row = factory.newTableRow(ein);
			// A: Bezeichnung
			fill(
				row,
				cols.bezeichnung_gleisschaltmittel,
				ein,
				[ein?.bezeichnung?.bezeichnungTabelle?.wert ?: ""]
			)

			// B: Art
			fill(
				row,
				cols.art,
				ein,
				[translate(ein?.zugeinwirkungAllg?.zugeinwirkungArt.wert) ?: ""]
			)

			// C: Typ
			fill(
				row,
				cols.typ,
				ein,
				[ein?.zugeinwirkungAllg?.zugeinwirkungTyp?.wert ?: ""]
			)

			// D
			fill(
				row,
				cols.aea_i,
				ein,
				[""]
			)

			// E
			fill(
				row,
				cols.aea_e,
				ein,
				[""]
			)

			// F
			fill(
				row,
				cols.aea_e_separat,
				ein,
				[""]
			)

			// G
			fill(
				row,
				cols.schienenprofil,
				ein,
				[""]
			)

			// H
			fill(
				row,
				cols.bezugspunkt_bezeichnung,
				ein,
				[
					ein?.markanterPunkt?.bezeichnung?.
						bezeichnungMarkanterPunkt?.wert ?: ""
				]
			)

			// I: Standortmerkmale.Bezugspunkt.Abstand
			fill(
				row,
				cols.abstand,
				ein,
				[
					val mp = markanterPunkt?.markanteStelle
					if (mp === null) {
						return null
					}
					val paths = topGraph.getPaths(singlePoints, mp.singlePoints)
					if (paths.empty) {
						throw new NoSuchElementException("no path")
					} else {
						String.format("%.0f", paths.map[length].min)
					}
				]
			)

			// J
			fillIterable(
				row,
				cols.strecke,
				ein,
				[
					punktObjektStrecke.map[strecke].map [
						bezeichnung?.bezeichnungStrecke?.wert ?: ""
					]
				],
				null
			)

			// K
			fillIterable(
				row,
				cols.km,
				ein,
				[punktObjektStrecke.map[streckeKm?.wert ?: ""]],
				null
			)

			// L
			fillIterable(
				row,
				cols.funktion,
				ein,
				[
					ein?.schaltMittelZuordnung.map [
						schaltmittelFunktion?.wert?.translate ?: ""
					]
				],
				null
			)

			// M: Bemerkung
			fill(
				row,
				cols.basis_bemerkung,
				ein,
				[footnoteTransformation.transform(it, row)]
			)
			instances.add(row);
		}

		for (FMA_Komponente fma : container.FMAKomponente) {
			if (fma.FMAKomponenteAchszaehlpunkt !== null) {
				val TableRow row = factory.newTableRow(fma);
				// A: Bezeichnung
				fill(
					row,
					cols.bezeichnung_gleisschaltmittel,
					fma,
					[fma?.bezeichnung?.bezeichnungTabelle?.wert ?: ""]
				)

				// B: ART
				fill(
					row,
					cols.art,
					fma,
					["Achszählpunkt"]
				)

				// C: Typ
				fill(
					row,
					cols.typ,
					fma,
					[
						fma?.FMAKomponenteAchszaehlpunkt?.FMAKomponenteTyp?.
							wert ?: ""
					]
				)

				// D: AEA
				fill(
					row,
					cols.aea_i,
					fma,
					[
						fma?.FMAKomponenteAchszaehlpunkt?.
							aussenelementInformation?.oertlichkeitNamensgebend?.
							bezeichnung?.oertlichkeitAbkuerzung?.wert ?: ""
					]
				)

				// E: AEA
				fill(
					row,
					cols.aea_e,
					fma,
					[
						fma?.FMAKomponenteAchszaehlpunkt?.aussenelementEnergie?.
							oertlichkeitNamensgebend?.bezeichnung?.
							oertlichkeitAbkuerzung?.wert ?: ""
					]
				)

				// F: separate Adern
				fill(
					row,
					cols.aea_e_separat,
					fma,
					[
						fma?.FMAKomponenteAchszaehlpunkt?.
							FMAKomponenteStromversorgung?.wert?.translate ?: ""
					]
				)

				// G: Schienenprofil
				fill(
					row,
					cols.schienenprofil,
					fma,
					[
						fma?.FMAKomponenteAchszaehlpunkt?.
							FMAKomponenteSchienenprofil?.wert?.translate ?: ""
					]
				)

				// H: Bezugspunkt Bezeichnung
				fill(
					row,
					cols.bezugspunkt_bezeichnung,
					fma,
					[
						fma?.markanterPunkt?.bezeichnung?.
							bezeichnungMarkanterPunkt?.wert ?: ""
					]
				)

				// I: Bezugspunkt Abstand
				fill(
					row,
					cols.abstand,
					fma,
					[
						val mp = markanterPunkt?.markanteStelle
						if (mp === null) {
							return null
						}
						val paths = topGraph.getPaths(singlePoints,
							mp.singlePoints)
						if (paths.empty) {
							throw new NoSuchElementException("no path")
						} else {
							String.format("%.0f", paths.map[length].min)
						}
					]
				)

				// J: Bezeichnung
				fillIterable(
					row,
					cols.strecke,
					fma,
					[
						punktObjektStrecke.map[strecke].map [
							bezeichnung?.bezeichnungStrecke?.wert ?: ""
						]
					],
					null
				)

				// K: km
				fillIterable(
					row,
					cols.km,
					fma,
					[punktObjektStrecke.map[streckeKm?.wert ?: ""]],
					null
				)

				// L: Funktion
				fill(
					row,
					cols.funktion,
					fma,
					[
						fma?.schaltmittelZuordnung?.schaltmittelFunktion?.wert?.
							translate ?: ""
					]
				)

				// M: Bemerkung
				fill(
					row,
					cols.basis_bemerkung,
					fma,
					[footnoteTransformation.transform(it, row)]
				)
				instances.add(row);
			}
		}

		return factory.table;
	}

	override void formatTableContent(Table table) {
		// A: Grundsatzangaben.Bezeichnung
		table.setTextAlignment(0, TextAlignment.LEFT);

		// L: Bemerkung
		table.setTextAlignment(11, TextAlignment.LEFT);
	}
}
