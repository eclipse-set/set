/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sskf

import com.google.common.collect.Lists
import de.scheidtbachmann.planpro.model.model1902.Gleis.Gleis_Schaltgruppe
import de.scheidtbachmann.planpro.model.model1902.Ortung.FMA_Anlage
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import java.util.List
import org.eclipse.set.basis.Wrapper
import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.utils.table.TMFactory
import org.eclipse.set.feature.table.messages.MessagesWrapper
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.format.TextAlignment
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup

import static de.scheidtbachmann.planpro.model.model1902.Ortung.ENUMFMAArt.*
import static de.scheidtbachmann.planpro.model.model1902.Ortung.ENUMUebertragungFMinfoRichtung.*

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.WKrGspKomponenteExtensions.*

/**
 * Table transformation for a Zugstraßentabelle (SSLZ).
 * 
 * @author Rumpf
 */
class SskfTransformator extends AbstractPlanPro2TableModelTransformator {

	SskfColumns cols;

	new(SskfColumns columns, MessagesWrapper messagesWrapper) {
		super(messagesWrapper)
		this.cols = columns;
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		// Maßgebendes Objekt: FMA_Anlage
		val Iterable<FMA_Anlage> fmaAnlageList = container.FMAAnlage

		for (fmaAnlage : fmaAnlageList) {
			if (Thread.currentThread.interrupted) {
				return null
			}
			val instance = factory.newTableRow(fmaAnlage)

			// A: Grundsatzangaben.Bezeichnung
			fill(instance, cols.bezeichnung_freimeldeabschnitt, fmaAnlage, [
				bzBezeichner
			])

			// B: Grundsatzangaben.Teilabschnitt.TA_Bez
			fill(
				instance,
				cols.ta_bez,
				fmaAnlage,
				[FMAAnlageKaskade?.FMAKaskadeBezeichnung?.wert ?: ""]
			)

			// C: Grundsatzangaben.Teilabschnitt.TA_E_A
			fill(
				instance,
				cols.ta_e_a,
				fmaAnlage,
				[
					FMAAnlageKaskade?.FMAKaskadeEinzelauswertung?.wert?.
						translate ?: ""
				]
			)

			// D: Grundsatzangaben.Art
			fill(
				instance,
				cols.art,
				fmaAnlage,
				[FMAAnlageAllg?.FMAArt?.wert?.translate ?: ""]
			)

			// E: Grundsatzangaben.Typ			
			fill(instance, cols.typ, fmaAnlage, [FMAAnlageAllg?.FMATyp?.wert])

			// F: Auswertung.AeA
			fill(instance, cols.aea, fmaAnlage, [
				getAussenelementById(IDGleisfreimeldeInnenanlage)?.bezeichnung?.
					bezeichnungAEA?.wert ?: ""
			])

			// G: Auswertung.Uebertragung.von
			fillConditional(
				instance,
				cols.uebertragung_von,
				fmaAnlage,
				[
					FMAAnlageUebertragungFMinfo?.uebertragungFMinfoRichtung?.
						wert === ENUM_UEBERTRAGUNG_FMINFO_RICHTUNG_KOMMEND
				],
				[
					getAussenelementById(
						FMAAnlageUebertragungFMinfo.IDUebertragungFMinfo).
						oertlichkeitNamensgebend?.bezeichnung?.
						oertlichkeitAbkuerzung?.wert
				]
			)

			// H: Auswertung.Uebertragung.nach
			fillConditional(
				instance,
				cols.uebertragung_nach,
				fmaAnlage,
				[
					FMAAnlageUebertragungFMinfo?.uebertragungFMinfoRichtung?.
						wert === ENUM_UEBERTRAGUNG_FMINFO_RICHTUNG_GEHEND
				],
				[
					getAussenelementById(
						FMAAnlageUebertragungFMinfo.IDUebertragungFMinfo).
						oertlichkeitNamensgebend?.bezeichnung?.
						oertlichkeitAbkuerzung?.wert
				]
			)

			// I: Auswertung.Uebertragung.Typ
			fill(instance, cols.uebertragung_typ, fmaAnlage, [
				FMAAnlageUebertragungFMinfo?.uebertragungFMinfoTyp?.wert
			])

			// J: Ftgs.Laenge.ls
			fillConditional(
				instance,
				cols.ls,
				fmaAnlage,
				[FMAAnlageAllg.FMAArt.wert === ENUMFMA_ART_FTGS],
				[FMAAnlageElektrMerkmale.FMALaengeS.wert.toString]
			)

			// K: Ftgs.Laenge.l1
			fillConditional(
				instance,
				cols.l1,
				fmaAnlage,
				[FMAAnlageAllg.FMAArt.wert === ENUMFMA_ART_FTGS],
				[FMAAnlageElektrMerkmale.FMALaengeE1.wert.toString]
			)

			// L: Ftgs.Laenge.l2
			fillConditional(
				instance,
				cols.l2,
				fmaAnlage,
				[FMAAnlageAllg.FMAArt.wert === ENUMFMA_ART_FTGS],
				[FMAAnlageElektrMerkmale.FMALaengeE2.wert.toString]
			)

			// N: Ftgs.Laenge.l3
			fillConditional(
				instance,
				cols.l3,
				fmaAnlage,
				[FMAAnlageAllg.FMAArt.wert === ENUMFMA_ART_FTGS],
				[FMAAnlageElektrMerkmale.FMALaengeE3.wert.toString]
			)

			// M: NfTf_GSK.Laenge.elektr
			fillConditional(
				instance,
				cols.elektr,
				fmaAnlage,
				[
					FMAAnlageAllg.FMAArt.wert ===
						ENUMFMA_ART_NF_GLEISSTROMKREIS ||
						FMAAnlageAllg.FMAArt.wert ===
							ENUMFMA_ART_TF_GLEISSTROMKREIS
				],
				[FMAAnlageElektrMerkmale.FMALaenge.wert.toString]
			)

			// O: NfTf_GSK.Laenge.beeinfl
			fillConditional(
				instance,
				cols.beeinfl,
				fmaAnlage,
				[
					FMAAnlageAllg.FMAArt.wert ===
						ENUMFMA_ART_NF_GLEISSTROMKREIS ||
						FMAAnlageAllg.FMAArt.wert ===
							ENUMFMA_ART_TF_GLEISSTROMKREIS
				],
				[FMAAnlageElektrMerkmale.FMALaengeBeeinflusst.wert.toString]
			)

			// P: Sskf.Sonstiges.Weiche
			val Wrapper<Iterable<W_Kr_Gsp_Element>> weichen = new Wrapper
			val Wrapper<Iterable<W_Kr_Gsp_Element>> weichenZK = new Wrapper

			fillConditional(
				instance,
				cols.Weiche,
				fmaAnlage,
				[
					weichen.value = gleisabschnitt.filterContained(
						container.WKrGspKomponente
					).map[WKrGspElement]

					weichenZK.value = weichen.value.filter [
						WKrGspKomponenten.exists[zungenpaar !== null] ||
							WKrGspKomponenten.exists[kreuzung !== null] ||
							WKrGspKomponenten.exists[entgleisungsschuh !== null]
					]

					!weichenZK.value.empty
				],
				[
					weichenZK.value.map[bezeichnung.bezeichnungTabelle.wert].
						toSet.getIterableFilling(MIXED_STRING_COMPARATOR)
				]
			)

			val List<Gleis_Schaltgruppe> schaltgruppen = Lists.newLinkedList

			// Q: Sonstiges.OlA.Schaltgruppe
			fillIterable(
				instance,
				cols.schaltgruppe,
				fmaAnlage,
				[
					schaltgruppen.addAll(gleisSchaltgruppen)
					return schaltgruppen.map [
						bezeichnung?.bezeichnungAussenanlage?.wert ?: ""
					]
				],
				MIXED_STRING_COMPARATOR
			)

			// R: Sonstiges.OlA.Bezeichner
			fillIterable(
				instance,
				cols.Bezeichner,
				fmaAnlage,
				[schaltgruppen.map[bezeichnung?.bezeichnungTabelle?.wert ?: ""]],
				MIXED_STRING_COMPARATOR
			)

			// S: Sonstiges.Rbmin
			fillConditional(
				instance,
				cols.rbmin_mit,
				fmaAnlage,
				[
					FMAAnlageAllg.FMAArt.wert ===
						ENUMFMA_ART_NF_GLEISSTROMKREIS ||
						FMAAnlageAllg.FMAArt.wert === ENUMFMA_ART_FTGS
				],
				[FMAAnlageElektrMerkmale?.bettungswiderstand?.wert?.toString]
			)

			// T: Sonstiges.HFmeldung
			fill(instance, cols.hfmeldung, fmaAnlage, [
				FMAAnlageAllg?.FMAHilffreimeldung?.wert?.translate ?: ""
			])

			// U: Sonstiges.Funktion
			fillIterable(
				instance,
				cols.funktion,
				fmaAnlage,
				[
					schaltmittelZuordnungen.map [
						schaltmittelFunktion.wert.translate
					]
				],
				null
			)

			// V: Bemerkung
			fill(
				instance,
				cols.basis_bemerkung,
				fmaAnlage,
				[footnoteTransformation.transform(it, instance)]
			)

		}

		return factory.table
	}

	override void formatTableContent(Table table) {
		// A: Grundsatzangaben.Bezeichnung
		table.setTextAlignment(0, TextAlignment.LEFT);

		// V: Bemerkung
		table.setTextAlignment(21, TextAlignment.LEFT);
	}
}
