/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.ssit

import com.google.common.collect.Lists
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Basis_Objekt
import de.scheidtbachmann.planpro.model.model1902.Bedienung.Bedien_Einrichtung_Oertlich
import de.scheidtbachmann.planpro.model.model1902.Schluesselabhaengigkeiten.Schluesselsperre
import de.scheidtbachmann.planpro.model.model1902.Signale.Signal
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.feature.table.messages.MessagesWrapper
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.TableRow
import org.eclipse.set.model.tablemodel.format.TextAlignment
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienAnzeigeElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienEinrichtungOertlichExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbBedienAnzeigeElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbZoneElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbZoneExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektStreckeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspKomponenteExtensions.*
import static extension org.eclipse.set.utils.math.BigDecimalExtensions.*
import org.eclipse.set.utils.table.TMFactory

/**
 * Table transformation for a Bedieneinrichtungstabelle ESTW (Ssit).
 * 
 * @author Schaefer
 */
class SsitTransformator extends AbstractPlanPro2TableModelTransformator {

	val SsitColumns columns

	var TMFactory factory

	new(SsitColumns columns, MessagesWrapper messagesWrapper) {
		super(messagesWrapper)
		this.columns = columns
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
		val row = it

		val lBedienAnzeigeElemente = einrichtung.bedienAnzeigeElemente
		val lNbBedienAnzeigeElemente = lBedienAnzeigeElemente.map [
			nbBedienAnzeigeElemente
		].flatten

		fill(
			columns.Bezeichnung,
			einrichtung,
			[bezeichnung?.bedienEinrichtOertlBez?.wert ?: ""]
		)

		fill(
			columns.Zug_AEA,
			einrichtung,
			[aussenelementansteuerung?.bezeichnung?.bezeichnungAEA?.wert ?: ""]
		)

		fill(
			columns.Bauart,
			einrichtung,
			[
				bedienEinrichtOertlichAllg?.bedienEinrichtBauart?.wert?.
					translate ?: ""
			]
		)

		fill(
			columns.Art,
			einrichtung,
			[
				unterbringung?.unterbringungAllg?.unterbringungBefestigung?.
					wert?.translate ?: ""
			]
		)

		fillIterable(
			columns.Strecke,
			einrichtung,
			[
				((unterbringung?.punktObjektStrecke) ?: Lists.newLinkedList).map [
					strecke?.bezeichnung?.bezeichnungStrecke?.wert
				].toSet.filterNull
			],
			null,
			[it]
		)

		fillIterable(
			columns.km,
			einrichtung,
			[
				((unterbringung?.punktObjektStrecke) ?: Lists.newLinkedList).map [
					streckeKm?.wert
				].toSet.filterNull
			],
			null,
			[it]
		)

		val lBedienAnzeigeElementAllg = lBedienAnzeigeElemente.map [
			bedienAnzeigeElementAllg
		].filterNull
		fillIterable(
			columns.Melder,
			einrichtung,
			[
				lBedienAnzeigeElementAllg.map[melder?.wert?.translate].toSet.
					filterNull
			],
			null,
			[it]
		)

		fillIterable(
			columns.Schalter,
			einrichtung,
			[
				lBedienAnzeigeElementAllg.map[schalter?.wert?.translate].toSet.
					filterNull
			],
			null,
			[it]
		)

		fillIterable(
			columns.Taste,
			einrichtung,
			[
				lBedienAnzeigeElementAllg.map[taste?.wert?.translate].toSet.
					filterNull
			],
			null,
			[it]
		)

		fillConditional(
			columns.Anf_NB,
			einrichtung,
			[
				lNbBedienAnzeigeElemente.map [
					NBBedienAnzeigeFunktionen?.tasteANF?.wert
				].filterNull.exists[it]
			],
			["x"]
		)

		fillConditional(
			columns.Fertigmeldung,
			einrichtung,
			[
				lNbBedienAnzeigeElemente.map [
					NBBedienAnzeigeFunktionen?.tasteFGT?.wert
				].filterNull.exists[it]
			],
			["x"]
		)

		fillConditional(
			columns.Weichengruppe,
			einrichtung,
			[
				lNbBedienAnzeigeElemente.map [
					NBBedienAnzeigeFunktionen?.tasteWGT?.wert
				].filterNull.exists[it]
			],
			["x"]
		)

		fillIterable(
			columns.Umst_Weiche,
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

		fillIterable(
			columns.Umst_Gs,
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

		fillIterable(
			columns.Umst_Sig,
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

		fillIterable(
			columns.Freigabe_Ssp,
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

		fill(
			columns.An_Zeit_Hupe,
			einrichtung,
			[bedienEinrichtOertlichAllg?.hupeAnschaltzeit?.wert?.toTableInteger]
		)

		fill(
			columns.basis_bemerkung,
			einrichtung,
			[footnoteTransformation.transform(it, row)]
		)

		return
	}

	override void formatTableContent(Table table) {
		// A: Ssit.Grundsatzangaben.Bezeichnung
		table.setTextAlignment(0, TextAlignment.LEFT);

		// R: Ssit.Bemerkung
		table.setTextAlignment(17, TextAlignment.LEFT);
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
