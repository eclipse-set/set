/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sskf

import java.math.BigDecimal
import java.util.List
import java.util.Set
import org.eclipse.set.basis.Wrapper
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Gleis.Gleis_Schaltgruppe
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin

import static org.eclipse.set.feature.table.pt1.sskf.SskfColumns.*
import static org.eclipse.set.model.planpro.Ortung.ENUMFMAArt.*
import static org.eclipse.set.model.planpro.Ortung.ENUMUebertragungFMinfoRichtung.*

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

	// 1 Meter
	static val BigDecimal MIN_GLEIS_SCHALTGRUPPE_OVERLAP_LENGTH = BigDecimal.ONE

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
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
			fill(instance, cols.getColumn(Bezeichnung), fmaAnlage, [
				bzBezeichner
			])

			// B: Grundsatzangaben.Teilabschnitt.TA_Bez
			fill(
				instance,
				cols.getColumn(Teilabschnitt_TA_Bez),
				fmaAnlage,
				[FMAAnlageKaskade?.FMAKaskadeBezeichnung?.wert ?: ""]
			)

			// C: Grundsatzangaben.Teilabschnitt.TA_E_A
			fill(
				instance,
				cols.getColumn(Teilabschnitt_TA_E_A),
				fmaAnlage,
				[
					FMAAnlageKaskade?.FMAKaskadeEinzelauswertung?.wert?.
						translate ?: ""
				]
			)

			// D: Grundsatzangaben.Art
			fill(
				instance,
				cols.getColumn(Unterbringung_Art),
				fmaAnlage,
				[FMAAnlageAllg?.FMAArt?.translate ?: ""]
			)

			// E: Grundsatzangaben.Typ			
			fill(
				instance,
				cols.getColumn(Typ),
				fmaAnlage,
				[FMAAnlageAllg?.FMATyp?.wert]
			)

			// F: Auswertung.AeA
			fill(
				instance,
				cols.getColumn(Auswertung_AeA),
				fmaAnlage,
				[
					IDGleisfreimeldeInnenanlage?.value?.bezeichnung?.
						bezeichnungAEA?.wert ?: ""
				]
			)

			// G: Auswertung.Uebertragung.von
			fillConditional(
				instance,
				cols.getColumn(Uebertragung_von),
				fmaAnlage,
				[
					FMAAnlageUebertragungFMinfo?.uebertragungFMinfoRichtung?.
						wert === ENUM_UEBERTRAGUNG_FMINFO_RICHTUNG_KOMMEND
				],
				[
					FMAAnlageUebertragungFMinfo.IDUebertragungFMinfo?.value?.
						oertlichkeitNamensgebend?.bezeichnung?.
						oertlichkeitAbkuerzung?.wert
				]
			)

			// H: Auswertung.Uebertragung.nach
			fillConditional(
				instance,
				cols.getColumn(Uebertragung_nach),
				fmaAnlage,
				[
					FMAAnlageUebertragungFMinfo?.uebertragungFMinfoRichtung?.
						wert === ENUM_UEBERTRAGUNG_FMINFO_RICHTUNG_GEHEND
				],
				[

					FMAAnlageUebertragungFMinfo.IDUebertragungFMinfo?.value?.
						oertlichkeitNamensgebend?.bezeichnung?.
						oertlichkeitAbkuerzung?.wert
				]
			)

			// I: Auswertung.Uebertragung.Typ
			fill(
				instance,
				cols.getColumn(Uebertragung_Typ),
				fmaAnlage,
				[FMAAnlageUebertragungFMinfo?.uebertragungFMinfoTyp?.wert]
			)

			// J: Ftgs.Laenge.ls
			fillConditional(
				instance,
				cols.getColumn(Ftgs_Laenge_ls),
				fmaAnlage,
				[FMAAnlageAllg.FMAArt.wert === ENUMFMA_ART_FTGS],
				[FMAAnlageElektrMerkmale.FMALaengeS.wert.toString]
			)

			// K: Ftgs.Laenge.l1
			fillConditional(
				instance,
				cols.getColumn(Ftgs_Laenge_l1),
				fmaAnlage,
				[FMAAnlageAllg.FMAArt.wert === ENUMFMA_ART_FTGS],
				[FMAAnlageElektrMerkmale.FMALaengeE1.wert.toString]
			)

			// L: Ftgs.Laenge.l2
			fillConditional(
				instance,
				cols.getColumn(Ftgs_Laenge_l2),
				fmaAnlage,
				[FMAAnlageAllg.FMAArt.wert === ENUMFMA_ART_FTGS],
				[FMAAnlageElektrMerkmale.FMALaengeE2.wert.toString]
			)

			// N: Ftgs.Laenge.l3
			fillConditional(
				instance,
				cols.getColumn(Ftgs_Laenge_l3),
				fmaAnlage,
				[FMAAnlageAllg.FMAArt.wert === ENUMFMA_ART_FTGS],
				[FMAAnlageElektrMerkmale.FMALaengeE3.wert.toString]
			)

			// M: NfTf_GSK.Laenge.elektr
			fillConditional(
				instance,
				cols.getColumn(NfTf_GSK_Laenge_elektr),
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
				cols.getColumn(NfTf_GSK_Laenge_beeinfl),
				fmaAnlage,
				[
					FMAAnlageAllg.FMAArt.wert ===
						ENUMFMA_ART_NF_GLEISSTROMKREIS ||
						FMAAnlageAllg.FMAArt.wert ===
							ENUMFMA_ART_TF_GLEISSTROMKREIS
				],
				[FMAAnlageElektrMerkmale.FMALaengeBeeinflusst.wert.toString]
			)

			// P: Sonstiges.Rbmin
			fillConditional(
				instance,
				cols.getColumn(Sonstiges_Rbmin),
				fmaAnlage,
				[
					FMAAnlageAllg.FMAArt.wert ===
						ENUMFMA_ART_NF_GLEISSTROMKREIS ||
						FMAAnlageAllg.FMAArt.wert === ENUMFMA_ART_FTGS
				],
				[FMAAnlageElektrMerkmale?.bettungswiderstand?.wert?.toString]
			)

			// Q: Sskf.Sonstiges.Weiche
			val Wrapper<Iterable<W_Kr_Gsp_Element>> weichen = new Wrapper
			val Wrapper<Iterable<W_Kr_Gsp_Element>> weichenZK = new Wrapper

			fillIterableWithConditional(
				instance,
				cols.getColumn(Sonstiges_Weiche),
				fmaAnlage,
				[
					weichen.value = IDGleisAbschnitt?.value.filterContained(
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
						toSet
				],
				MIXED_STRING_COMPARATOR,
				ITERABLE_FILLING_SEPARATOR
			)

			val List<Gleis_Schaltgruppe> schaltgruppen = fmaAnlage.
				getGleisSchaltgruppen(MIN_GLEIS_SCHALTGRUPPE_OVERLAP_LENGTH)

			// R: Sonstiges.OlA.Schaltgruppe
			fillIterable(
				instance,
				cols.getColumn(Sonstiges_OlA_Schaltgruppe),
				fmaAnlage,
				[
					schaltgruppen.map [
						bezeichnung?.bezeichnungAussenanlage?.wert ?: ""
					]
				],
				MIXED_STRING_COMPARATOR
			)

			// S: Sonstiges.OlA.Bezeichner
			fillIterable(
				instance,
				cols.getColumn(Sonstiges_OlA_Bezeichner),
				fmaAnlage,
				[schaltgruppen.map[bezeichnung?.bezeichnungTabelle?.wert ?: ""]],
				MIXED_STRING_COMPARATOR
			)

			// T: Sonstiges.zul_v
			fill(
				instance,
				cols.getColumn(Sonstiges_zul_Geschwindigkeit),
				fmaAnlage,
				[IDGleisAbschnitt?.value?.geschwindigkeit?.wert?.toString ?: ""]
			)

			// U: Sonstiges.HFmeldung
			fill(
				instance,
				cols.getColumn(Sonstiges_HFmeldung),
				fmaAnlage,
				[FMAAnlageAllg?.FMAHilffreimeldung?.wert?.translate ?: ""]
			)

			// V: Sonstiges.Funktion
			fillIterable(
				instance,
				cols.getColumn(Sonstiges_Funktion),
				fmaAnlage,
				[
					schaltmittelZuordnungen.map [
						schaltmittelFunktion?.translate
					]
				],
				null
			)

			// W: Bemerkung
			fillFootnotes(instance, fmaAnlage)
		}

		return factory.table
	}
}
