/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssbb

import com.google.common.collect.Lists
import java.util.Set
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stellelement
import org.eclipse.set.model.planpro.Bedienung.Bedien_Einrichtung_Oertlich
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente
import org.eclipse.set.model.planpro.Ortung.Zugeinwirkung
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.utils.table.TMFactory

import static org.eclipse.set.feature.table.pt1.ssbb.SsbbColumns.*

import static extension org.eclipse.set.ppmodel.extensions.BedienAnzeigeElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienEinrichtungOertlichExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BueAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BueBedienAnzeigeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektStreckeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StellelementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import org.osgi.service.event.EventAdmin

class SsbbTransformator extends AbstractPlanPro2TableModelTransformator {

	var TMFactory factory

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService,
		EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory, Stell_Bereich controlArea) {
		this.factory = factory
		return container.transform(controlArea)
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup container, Stell_Bereich controlArea) {
		container.bedienEinrichtungOertlich.filter[isPlanningObject].
			filterObjectsInControlArea(controlArea).filter [
				!bedienAnzeigeElemente.map[bueBedienAnzeigeElemente].flatten.
					filterNull.empty
			].forEach [
				if (Thread.currentThread.interrupted) {
					return
				}
				it.transform
			]
		return
	}

	private def TableRow create factory.newTableRow(einrichtung) transform(
		Bedien_Einrichtung_Oertlich einrichtung) {
		val instance = it
		val lBedienAnzeigeElement = einrichtung.bedienAnzeigeElemente
		val lBUEBedienAnzeigeElement = lBedienAnzeigeElement.map [
			bueBedienAnzeigeElemente
		].flatten.filterNull

		// A: Ssbb.Grundsatzangaben.Bezeichnung
		fill(
			instance,
			cols.getColumn(Bezeichnung),
			einrichtung,
			[bezeichnung?.bedienEinrichtOertlBez?.wert ?: ""]
		)

		// B: Ssbb.Grundsatzangaben.Zug_AEA
		val verknuepftesElements = lBedienAnzeigeElement.map [
			verknuepftesElement
		].filterNull.filter [ verknuepftesElement |
			(verknuepftesElement instanceof Signal) ||
				(verknuepftesElement instanceof W_Kr_Gsp_Element)
		].toSet
		fillSwitch(
			instance,
			cols.getColumn(Zug_AEA),
			einrichtung,
			new Case<Bedien_Einrichtung_Oertlich>(
				[
					verknuepftesElements !== null && !verknuepftesElements.empty
				],
				[
					verknuepftesElements.map [
						if (it instanceof Signal) {
							return realAktivStellelement?.bezeichungAEA
						} else {
							return (it as W_Kr_Gsp_Element).stellelement?.
								bezeichungAEA
						}
					].toSet.filterNull
				],
				ITERABLE_FILLING_SEPARATOR,
				null
			),
			new Case<Bedien_Einrichtung_Oertlich>(
				[!lBUEBedienAnzeigeElement.empty],
				[
					lBUEBedienAnzeigeElement.map[bueAnlage].flatten.map [
						stellelement.bezeichungAEA
					].toSet.filterNull
				],
				ITERABLE_FILLING_SEPARATOR,
				null
			)
		)

		// C: Ssbb.Grundsatzangaben.Bauart
		fill(
			instance,
			cols.getColumn(Bauart),
			einrichtung,
			[
				bedienEinrichtOertlichAllg?.bedienEinrichtBauart.
					translate ?: ""
			]
		)

		// D: Ssbb.Grundsatzangaben.Befestigung.Art
		fill(
			instance,
			cols.getColumn(Befestigung_Art),
			einrichtung,
			[
				unterbringung?.unterbringungAllg?.unterbringungBefestigung.
					translate ?: ""
			]
		)

		// E: Ssbb.Grundsatzangaben.Befestigung.Strecke
		fillIterable(
			instance,
			cols.getColumn(Befestigung_Strecke),
			einrichtung,
			[
				((unterbringung?.punktObjektStrecke) ?: Lists.newLinkedList).map [
					strecke?.bezeichnung?.bezeichnungStrecke?.wert
				].toSet.filterNull
			],
			null
		)

		// F: Ssbb.Grundsatzangaben.Befestigung.km
		fillIterable(
			instance,
			cols.getColumn(Befestigung_km),
			einrichtung,
			[
				((unterbringung?.punktObjektStrecke) ?: Lists.newLinkedList).map [
					streckeKm?.wert
				].toSet.filterNull
			],
			null
		)

		// G: Ssbb.Wirkung.Bue_Anl
		fillIterable(
			instance,
			cols.getColumn(Bue_Anlage),
			einrichtung,
			[
				lBUEBedienAnzeigeElement?.map[bueAnlage]?.flatten.map [
					bezeichnung?.bezeichnungTabelle?.wert
				].toSet.filterNull
			],
			null
		)

		val bue_Eins = lBUEBedienAnzeigeElement.map[bueEin].flatten.filterNull
		val bue_Eins_Schalmittle = bue_Eins.map[schaltmittel_Zuordnung].flatten.
			filterNull

		// H: Ssbb.Wirkung.Bue_Ein
		fillIterable(
			instance,
			cols.getColumn(Bue_Ein),
			einrichtung,
			[
				bue_Eins_Schalmittle.map [
					val schalter = IDSchalter?.value
					if (schalter instanceof Zugeinwirkung) {
						return schalter?.bezeichnung?.bezeichnungTabelle?.wert
					} else if (schalter instanceof FMA_Komponente) {
						return schalter?.bezeichnung?.bezeichnungTabelle?.wert
					}
					return null
				].toSet.filterNull
			],
			null
		)

		val bue_Aus = lBUEBedienAnzeigeElement.map[bueAus].flatten.filterNull
		val bue_Aus_Schalmittles = bue_Aus.map[schaltmittel_Zuordnung].flatten.
			filterNull

		// I: Ssbb.Wirkung.Bue_Aus
		fillIterable(
			instance,
			cols.getColumn(Bue_Aus),
			einrichtung,
			[
				bue_Aus_Schalmittles.map [
					val schalter = IDSchalter?.value
					if (schalter instanceof Zugeinwirkung) {
						return schalter?.bezeichnung?.bezeichnungTabelle?.wert
					}
					return null
				].toSet.filterNull
			],
			null
		)

		// J: Ssbb.Bemerkung
		fillFootnotes(instance, einrichtung)

		return
	}

	private def String getBezeichungAEA(Stellelement element) {
		return element?.information?.bezeichnung?.bezeichnungAEA?.wert ?: ""
	}
}
