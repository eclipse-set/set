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
import java.util.Set
import org.eclipse.set.basis.graph.TopPoint
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.core.services.graph.TopologicalGraphService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Anlage
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Kante
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Fahrstrasse.Markanter_Punkt
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente
import org.eclipse.set.model.planpro.Ortung.Zugeinwirkung
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin

import static org.eclipse.set.feature.table.pt1.sskg.SskgColumns.*

import static extension org.eclipse.set.ppmodel.extensions.BueKanteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaKomponenteAchszaehlpunktExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaKomponenteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektStreckeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ZugEinwirkungExtensions.*

/**
 * Table transformation for a Gleisschaltmitteltabelle (SSKG).
 * 
 * @author Rumpf
 */
class SskgTransformator extends AbstractPlanPro2TableModelTransformator {

	val TopologicalGraphService topGraphService;

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService,
		TopologicalGraphService topGraphService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
		this.topGraphService = topGraphService;
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		val instances = new ArrayList<TableRow>

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
				[
					ein?.zugeinwirkungAllg?.zugeinwirkungArt?.translate ?: ""
				]
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
					bezugspunktBezeichnung
				]
			)

			// I: Sskg.Standortmerkmale.Bezugspunkt.Abstand
			fill(
				row,
				cols.getColumn(Bezugspunkt_Abstand),
				ein,
				[
					val punkt = bezugspunkt
					if (punkt === null) {
						return null
					}
					String.format("%.0f", getShortestPathLength(ein, punkt))
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
						schaltmittelFunktion?.translate ?: ""
					]
				],
				null
			)

			// M: Sskg.Bemerkung
			fillFootnotes(row, ein)
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
							bezeichnung?.bezeichnungAEA?.wert ?: ""
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
						FMAKomponenteAchszaehlpunkt?.
							FMAKomponenteSchienenprofil?.translate ?: ""
					]
				)

				// H: Sskg.Standortmerkmale.Bezugspunkt.Bezeichnung
				fill(
					row,
					cols.getColumn(Bezugspunkt_Bezeichnung),
					fma,
					[
						bezugspunktBezeichnung ?: ""
					]
				)

				// I: Sskg.Standortmerkmale.Bezugspunkt.Abstand
				fill(
					row,
					cols.getColumn(Bezugspunkt_Abstand),
					fma,
					[
						val punkt = bezugsPunkt
						if (punkt === null) {
							return null
						}
						String.format("%.0f", getShortestPathLength(fma, punkt))
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
						schaltmittelZuordnung?.schaltmittelFunktion?.
							translate ?: ""
					]
				)

				// M: Sskg.Bemerkung
				fillFootnotes(row, fma)
				instances.add(row);
			}
		}

		return factory.table;
	}

	def double getShortestPathLength(Punkt_Objekt p1, Punkt_Objekt p2) {
		val points1 = p1.singlePoints.map[new TopPoint(it)]
		val points2 = p2.singlePoints.map[new TopPoint(it)]

		return points1.flatMap [ pa |
			points2.map [ pb |
				topGraphService.findShortestDistance(pa, pb)
			]
		].filter[present].map[get.doubleValue].min
	}

	def dispatch String getBezugspunktBezeichnung(FMA_Komponente fma) {
		return fma.IDBezugspunkt?.value?.bezugspunktBezeichnung
	}

	def dispatch String getBezugspunktBezeichnung(Zugeinwirkung ein) {
		return ein.IDBezugspunkt?.value?.bezugspunktBezeichnung
	}

	def dispatch String getBezugspunktBezeichnung(Basis_Objekt fma) {
		return ""
	}

	def dispatch String getBezugspunktBezeichnung(BUE_Anlage bueAnlage) {
		return bueAnlage.bezeichnung?.bezeichnungTabelle?.wert
	}

	def dispatch String getBezugspunktBezeichnung(BUE_Kante bueKante) {
		return bueKante?.bezeichnung ?: ""
	}

	def dispatch String getBezugspunktBezeichnung(Markanter_Punkt markanter) {
		return markanter?.bezeichnung?.bezeichnungMarkanterPunkt?.wert
	}

}
