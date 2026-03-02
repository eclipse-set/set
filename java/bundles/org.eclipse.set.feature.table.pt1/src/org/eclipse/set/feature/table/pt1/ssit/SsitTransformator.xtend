/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssit

import com.google.common.collect.Lists
import java.util.Set
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Bedienung.Bedien_Einrichtung_Oertlich
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schluesselsperre
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin

import static org.eclipse.set.feature.table.pt1.ssit.SsitColumns.*

import static extension org.eclipse.set.ppmodel.extensions.BedienAnzeigeElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienEinrichtungOertlichExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbBedienAnzeigeElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbZoneElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbZoneExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektStreckeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspKomponenteExtensions.*
import static extension org.eclipse.set.utils.math.BigDecimalExtensions.*

/**
 * Table transformation for a Bedieneinrichtungstabelle ESTW (Ssit).
 * 
 * @author Schaefer 
 */
class SsitTransformator extends AbstractPlanPro2TableModelTransformator {

	var TMFactory factory

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		this.factory = factory
		return container.transform
	}

	private def Table create factory.table transform(
		MultiContainer_AttributeGroup container) {
		container.bedienEinrichtungOertlich.filter [
			bedienAnzeigeElemente.forall[bueBedienAnzeigeElemente.empty]
		].forEach [ it |
			if (Thread.currentThread.interrupted) {
				return
			}
			it.transform
		]
		return
	}

	private def TableRow create factory.newTableRow(einrichtung) transform(
		Bedien_Einrichtung_Oertlich einrichtung) {

		val lBedienAnzeigeElemente = einrichtung.bedienAnzeigeElemente
		val lNbBedienAnzeigeElemente = lBedienAnzeigeElemente.map [
			nbBedienAnzeigeElemente
		].flatten

		// A: Ssit.Grundsatzangaben.Bezeichnung
		fill(
			cols.getColumn(Bezeichnung),
			einrichtung,
			[bezeichnung?.bedienEinrichtOertlBez?.wert ?: ""]
		)

		// B: Ssit.Grundsatzangaben.Zug_AEA
		fill(
			cols.getColumn(Zug_AEA),
			einrichtung,
			[aussenelementansteuerung?.bezeichnung?.bezeichnungAEA?.wert ?: ""]
		)

		// C: Ssit.Grundsatzangaben.Bauart
		fill(
			cols.getColumn(Bauart),
			einrichtung,
			[
				bedienEinrichtOertlichAllg?.bedienEinrichtBauart?.
					translate ?: ""
			]
		)

		// D: Ssit.Grundsatzangaben.Befestigung.Art
		fill(
			cols.getColumn(Befestigung_Art),
			einrichtung,
			[
				unterbringung?.unterbringungAllg?.unterbringungBefestigung?.
					translate ?: ""
			]
		)

		// E: Ssit.Grundsatzangaben.Befestigung.Strecke
		fillIterable(
			cols.getColumn(Befestigung_Strecke),
			einrichtung,
			[
				((unterbringung?.punktObjektStrecke) ?: Lists.newLinkedList).map [
					strecke?.bezeichnung?.bezeichnungStrecke?.wert
				].toSet.filterNull
			],
			null,
			[it]
		)

		// F: Ssit.Grundsatzangaben.Befestigung.km
		fillIterable(
			cols.getColumn(Befestigung_km),
			einrichtung,
			[
				((unterbringung?.punktObjektStrecke) ?: Lists.newLinkedList).map [
					streckeKm?.wert
				].toSet.filterNull
			],
			null,
			[it]
		)

		// G: Ssit.Bedien_Anz_Elemente.Melder
		val lBedienAnzeigeElementAllg = lBedienAnzeigeElemente.map [
			bedienAnzeigeElementAllg
		].filterNull
		fillIterable(
			cols.getColumn(Melder),
			einrichtung,
			[
				lBedienAnzeigeElementAllg.map [ bedienAnzeigeAllg |
					bedienAnzeigeAllg.melder?.translate
				].toSet.filterNull
			],
			null,
			[it]
		)

		// H: Ssit.Bedien_Anz_Elemente.Schalter
		fillIterable(
			cols.getColumn(Schalter),
			einrichtung,
			[
				lBedienAnzeigeElementAllg.map [ bedienAnzeigeAllg |
					bedienAnzeigeAllg.schalter?.translate
				].toSet.filterNull
			],
			null,
			[it]
		)

		// I: Ssit.Bedien_Anz_Elemente.Taste
		fillIterable(
			cols.getColumn(Taste),
			einrichtung,
			[
				lBedienAnzeigeElementAllg.map [ bedienAnzeigeAllg |
					bedienAnzeigeAllg.taste?.translate
				].toSet.filterNull
			],
			null,
			[it]
		)

		// J: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Anf_NB
		fillConditional(
			cols.getColumn(Anf_NB),
			einrichtung,
			[
				lNbBedienAnzeigeElemente.map [
					NBBedienAnzeigeFunktionen?.tasteANF?.wert
				].filterNull.exists[it]
			],
			["x"]
		)

		// K: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Fertigmeldung
		fillConditional(
			cols.getColumn(Fertigmeldung),
			einrichtung,
			[
				lNbBedienAnzeigeElemente.map [
					NBBedienAnzeigeFunktionen?.tasteFGT?.wert
				].filterNull.exists[it]
			],
			["x"]
		)

		// L: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Weichengruppe
		fillConditional(
			cols.getColumn(Weichengruppe),
			einrichtung,
			[
				lNbBedienAnzeigeElemente.map [
					NBBedienAnzeigeFunktionen?.tasteWGT?.wert
				].filterNull.exists[it]
			],
			["x"]
		)

		// M: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Umst_Weiche
		fillIterable(
			cols.getColumn(Umst_Weiche),
			einrichtung,
			[
				lNbBedienAnzeigeElemente.map [
					nbZone.NBZoneElemente
				].flatten.filter [
					nbElement.WKrGspKomponenteWithZungenpaar &&
						NBZoneElementAllg?.freieStellbarkeit?.wert
				].map [
					(nbElement as W_Kr_Gsp_Komponente).WKrGspElement?.
						bezeichnung?.bezeichnungTabelle?.wert
				].toSet.filterNull
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// N: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Umst_Gs
		fillIterable(
			cols.getColumn(Umst_Gs),
			einrichtung,
			[
				lNbBedienAnzeigeElemente.map [
					nbZone.NBZoneElemente
				].flatten.filter [
					nbElement.WKrGspKomponenteWithEntgleisungsschuh &&
						NBZoneElementAllg?.freieStellbarkeit?.wert
				].map [
					(nbElement as W_Kr_Gsp_Komponente).WKrGspElement?.
						bezeichnung?.bezeichnungTabelle?.wert
				].toSet.filterNull
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// O: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Umst_Sig
		fillIterable(
			cols.getColumn(Umst_Sig),
			einrichtung,
			[
				lNbBedienAnzeigeElemente.map [
					nbZone.NBZoneElemente
				].flatten.filter [
					nbElement instanceof Signal &&
						NBZoneElementAllg?.freieStellbarkeit?.wert
				].map [
					(nbElement as Signal).bezeichnung?.bezeichnungTabelle?.wert
				].toSet.filterNull
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// P: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.Freigabe_Ssp
		fillIterable(
			cols.getColumn(Freigabe_Ssp),
			einrichtung,
			[
				lNbBedienAnzeigeElemente.map [
					nbZone.NBZoneElemente
				].flatten.filter [
					nbElement instanceof Schluesselsperre &&
						NBZoneElementAllg?.freieStellbarkeit?.wert
				].map [
					(nbElement as Schluesselsperre).bezeichnung?.
						bezeichnungTabelle?.wert
				].toSet.filterNull
			],
			MIXED_STRING_COMPARATOR,
			[it]
		)

		// Q: Ssit.Bedien_Anz_Elemente.Nahbedienbereich.An_Zeit_Hupe
		fill(
			cols.getColumn(An_Zeit_Hupe),
			einrichtung,
			[
				val zeit = bedienEinrichtOertlichAllg?.hupeAnschaltzeit?.wert?.
					toTableInteger
				return zeit == "0" ? Boolean.FALSE.translate : zeit
			]
		)

		// R: Bemerkung
		fillFootnotes(einrichtung)

		return
	}

	private static def boolean isWKrGspKomponenteWithZungenpaar(
		Basis_Objekt objekt) {
		if (objekt instanceof W_Kr_Gsp_Komponente) {
			if (objekt.zungenpaar !== null) {
				return true
			}
		}
		return false
	}

	private static def boolean isWKrGspKomponenteWithEntgleisungsschuh(
		Basis_Objekt objekt) {
		if (objekt instanceof W_Kr_Gsp_Komponente) {
			if (objekt.entgleisungsschuh !== null) {
				return true
			}
		}
		return false
	}
}
