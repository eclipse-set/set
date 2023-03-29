/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sskg

import java.util.ArrayList
import java.util.NoSuchElementException
import java.util.Set
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.TopGraph
import org.eclipse.set.toolboxmodel.Ortung.FMA_Komponente
import org.eclipse.set.toolboxmodel.Ortung.Zugeinwirkung
import org.eclipse.set.utils.table.TMFactory

import static org.eclipse.set.feature.table.pt1.sskg.SskgColumns.*

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

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService) {
		super(cols, enumTranslationService)
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
			// A: Sskg.Grunsatzangaben.Bezeichnung
			fill(
				row,
				cols.getColumn(Bezeichnung),
				ein,
				[ein?.bezeichnung?.bezeichnungTabelle?.wert ?: ""]
			)

			// B: Sskg.Grunsatzangaben.Art
			fill(
				row,
				cols.getColumn(Art),
				ein,
				[translate(ein?.zugeinwirkungAllg?.zugeinwirkungArt.wert) ?: ""]
			)

			// C: Sskg.Grundsatzangaben.Typ
			fill(
				row,
				cols.getColumn(Typ),
				ein,
				[ein?.zugeinwirkungAllg?.zugeinwirkungTyp?.wert ?: ""]
			)

			// D: Sskg.Achszaehlpunkte.Anschluss_Info.AEA_I
			fill(
				row,
				cols.getColumn(AEA_I),
				ein,
				[""]
			)

			// E: Sskg.Achszaehlepunkte.Anschluss_Energie.AEA_E
			fill(
				row,
				cols.getColumn(AEA_E),
				ein,
				[""]
			)

			// F: Sskg.Achszaehlpunkte.Anschluss_Energie.AEA_E_separat
			fill(
				row,
				cols.getColumn(AEA_E_separat),
				ein,
				[""]
			)

			// G: Sskg.Achszaehlpunkte.Schienenprofil
			fill(
				row,
				cols.getColumn(Schienenprofil),
				ein,
				[""]
			)

			// H: Sskg.Standortmerkmale.Bezugspunkt.Bezeichnung
			fill(
				row,
				cols.getColumn(Bezugspunkt_Bezeichnung),
				ein,
				[
					ein?.markanterPunkt?.bezeichnung?.
						bezeichnungMarkanterPunkt?.wert ?: ""
				]
			)

			// I: Sskg.Standortmerkmale.Bezugspunkt.Abstand
			fill(
				row,
				cols.getColumn(Bezugspunkt_Abstand),
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

			// J: Sskg.Standortmerkmale.Standort.Strecke
			fillIterable(
				row,
				cols.getColumn(Standort_Strecke),
				ein,
				[
					punktObjektStrecke.map[strecke].map [
						bezeichnung?.bezeichnungStrecke?.wert ?: ""
					]
				],
				null
			)

			// K: Sskg.Standortmerkmale.Standort.km
			fillIterable(
				row,
				cols.getColumn(Standort_km),
				ein,
				[punktObjektStrecke.map[streckeKm?.wert ?: ""]],
				null
			)

			// L: Sskg.Funktion
			fillIterable(
				row,
				cols.getColumn(Funktion),
				ein,
				[
					ein?.schaltMittelZuordnung.map [
						schaltmittelFunktion?.wert?.translate ?: ""
					]
				],
				null
			)

			// M: Sskg.Bemerkung
			fill(
				row,
				cols.getColumn(Bemerkung),
				ein,
				[footnoteTransformation.transform(it, row)]
			)
			instances.add(row);
		}

		for (FMA_Komponente fma : container.FMAKomponente) {
			if (fma.FMAKomponenteAchszaehlpunkt !== null) {
				val TableRow row = factory.newTableRow(fma);
				// A: Sskg.Grundsatzangaben.Bezeichnung
				fill(
					row,
					cols.getColumn(Bezeichnung),
					fma,
					[fma?.bezeichnung?.bezeichnungTabelle?.wert ?: ""]
				)

				// B: Sskg.Grundsatzangaben.Art
				fill(
					row,
					cols.getColumn(Art),
					fma,
					["Achszählpunkt"]
				)

				// C: Sskg.Grundsatzangaben.Typ
				fill(
					row,
					cols.getColumn(Typ),
					fma,
					[
						fma?.FMAKomponenteAchszaehlpunkt?.FMAKomponenteTyp?.
							wert ?: ""
					]
				)

				// D: Sskg.Achszaehlpunkte.Anschluss_Info.AEA_I
				fillIterable(
					row,
					cols.getColumn(AEA_I),
					fma,
					[
						fma?.FMAKomponenteAchszaehlpunkt?.
							aussenelementInformation.map [
								bezeichnung?.bezeichnungAEA?.wert ?: ""
							]
					],
					null
				)

				// E: Sskg.Achszaehlpunkte.Anschluss_Energie.AEA_E
				fill(
					row,
					cols.getColumn(AEA_E),
					fma,
					[
						fma?.FMAKomponenteAchszaehlpunkt?.aussenelementEnergie?.
							bezeichnung?.bezeichnungAEA.wert ?: ""
					]
				)

				// F: Sskg.Achszaehlpunkte.Anschluss_Energie.AEA_E_separat
				fill(
					row,
					cols.getColumn(AEA_E_separat),
					fma,
					[
						fma?.FMAKomponenteAchszaehlpunkt?.
							FMAKomponenteStromversorgung?.wert?.translate ?: ""
					]
				)

				// G: Sskg.Achszaehlpunkte.Schienenprofil
				fill(
					row,
					cols.getColumn(Schienenprofil),
					fma,
					[
						fma?.FMAKomponenteAchszaehlpunkt?.
							FMAKomponenteSchienenprofil?.wert?.translate ?: ""
					]
				)

				// H: Sskg.Standortmerkmale.Bezugspunkt.Bezeichnung
				fill(
					row,
					cols.getColumn(Bezugspunkt_Bezeichnung),
					fma,
					[
						fma?.markanterPunkt?.bezeichnung?.
							bezeichnungMarkanterPunkt?.wert ?: ""
					]
				)

				// I: Sskg.Standortmerkmale.Bezugspunkt.Abstand
				fill(
					row,
					cols.getColumn(Bezugspunkt_Abstand),
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

				// J: Sskg.Standortmerkmale.Standort.Strecke
				fillIterable(
					row,
					cols.getColumn(Standort_Strecke),
					fma,
					[
						punktObjektStrecke.map[strecke].map [
							bezeichnung?.bezeichnungStrecke?.wert ?: ""
						]
					],
					null
				)

				// K: Sskg.Standortmerkmale.Standort.km
				fillIterable(
					row,
					cols.getColumn(Standort_km),
					fma,
					[punktObjektStrecke.map[streckeKm?.wert ?: ""]],
					null
				)

				// L: Sskg.Funktion
				fill(
					row,
					cols.getColumn(Funktion),
					fma,
					[
						fma?.schaltmittelZuordnung?.schaltmittelFunktion?.wert?.
							translate ?: ""
					]
				)

				// M: Sskg.Bemerkung
				fill(
					row,
					cols.getColumn(Bemerkung),
					fma,
					[footnoteTransformation.transform(it, row)]
				)
				instances.add(row);
			}
		}

		return factory.table;
	}
}
