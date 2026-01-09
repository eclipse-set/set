/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.ssko;

import java.util.Set
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schloss
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schluesselsperre
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.ppmodel.extensions.utils.Case
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin

import static org.eclipse.set.feature.table.pt1.ssko.SskoColumns.*

import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FahrwegExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrAbhaengigkeitExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SchlossExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SchlosskombinationExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SchluesselExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SchluesselsperreExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StellBereichExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UnterbringungExtensions.*

/**
 * Table transformation for a Schlosstabelle Entwurf (Ssko).
 * 
 * @author Rumpf
 */
class SskoTransformator extends AbstractPlanPro2TableModelTransformator {

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {
		for (Schloss schloss : container.schloss) {
			if (Thread.currentThread.interrupted) {
				return null
			}
			val instance = factory.newTableRow(schloss)

			// A: Ssko.Grundsatzangaben.Bezeichnung_Schloss
			fill(
				instance,
				cols.getColumn(Bezeichnung),
				schloss,
				[bezeichnung?.bezeichnungSchloss?.wert]
			);

			// B: Ssko.Grundsatzangaben.Schloss_an
			fillSwitch(
				instance,
				cols.getColumn(Schloss_an),
				schloss,
				new Case<Schloss>(
					[schlossBUE !== null],
					["BÜ"]
				),
				new Case<Schloss>(
					[schlossGsp !== null],
					["Gsp"]
				),
				new Case<Schloss>(
					[schlossSonderanlage !== null],
					["Sonder"]
				),
				new Case<Schloss>(
					[schlossSsp !== null],
					["Ssp"]
				),
				new Case<Schloss>(
					[schlossW !== null],
					["W"]
				)
			)

			// C: Ssko.Grundsatzangaben.Grundstellung_eingeschl
			fill(
				instance,
				cols.getColumn(Grundstellung_eingeschl),
				schloss,
				[schluesselInGrdstEingeschl?.wert?.translate]
			)

			// D: Ssko.Schluessel.Bezeichnung
			fill(
				instance,
				cols.getColumn(Schluessel_Bezeichnung),
				schloss,
				[schluesel?.bezeichnung?.bezeichnungSchluessel?.wert]
			)

			// E: Ssko.Schluessel.Bartform
			fill(
				instance,
				cols.getColumn(Schluessel_Bartform),
				schloss,
				[
					schluesel?.schluesselAllg?.schluesselBartform?.translate
				]
			)

			// F: Ssko.Schluessel.Gruppe
			fill(
				instance,
				cols.getColumn(Schluessel_Gruppe),
				schloss,
				[
					schluesel?.schluesselAllg?.schluesselGruppe?.translate
				]
			)

			// G: Ssko.Fahrweg.Bezeichnung.Zug
			fillIterable(
				instance,
				cols.getColumn(Bezeichnung_Zug),
				schloss,
				[
					schluesselsperre?.fstrZugRangier?.filter[fstrZug !== null]?.
						map[fstrName]?.toSet ?: #[]
				],
				null
			)

			// H: Ssko.Fahrweg.Bezeichnung.Rangier
			fillIterable(
				instance,
				cols.getColumn(Bezeichnung_Rangier),
				schloss,
				[
					schluesselsperre?.fstrZugRangier?.filter [
						fstrRangier !== null
					]?.map[fstrName]?.toSet ?: #[]
				],
				null
			)

			// I: Ssko.W_Gsp_Bue.Verschl_Element.Bezeichnung
			fillSwitch(
				instance,
				cols.getColumn(Verschl_Element_Bezeichnung),
				schloss,
				new Case<Schloss>(
					[schlossBUE !== null],
					[bueAnlage?.bezeichnung?.bezeichnungTabelle?.wert]
				),
				new Case<Schloss>(
					[schlossGsp !== null],
					[gspElement?.bezeichnung?.bezeichnungTabelle?.wert]
				),
				new Case<Schloss>(
					[schlossW?.IDWKrElement !== null],
					[getWKrElement?.bezeichnung?.bezeichnungTabelle?.wert]
				)
			)

			// J: Ssko.W_Gsp_Bue.Verschl_Element.Lage
			fillSwitch(
				instance,
				cols.getColumn(Verschl_Element_Lage),
				schloss,
				new Case<Schloss>(
					[schlossBUE !== null],
					[schlossBUE.BUELage?.translate ?: ""]
				),
				new Case<Schloss>(
					[schlossGsp !== null],
					[schlossGsp.gspLage?.translate ?: ""]
				),
				new Case<Schloss>(
					[schlossW !== null],
					[schlossW.WLage?.translate ?: ""]
				)
			)

			// K:Ssko.W_Gsp_Bue.Verschl_Element.Anbaulage
			fill(
				instance,
				cols.getColumn(Verschl_Element_Anbaulage),
				schloss,
				[schlossW?.WAnbaulage?.translate ?: ""]
			)

			// L: Ssko.W_Gsp_Bue.Verschl_Element.Ort
			fill(
				instance,
				cols.getColumn(Verschl_Element_Ort),
				schloss,
				[schlossW?.verschlussOrt?.translate ?: ""]
			)

			// M: Ssko.W_Gsp_Bue.Schlossart
			fill(
				instance,
				cols.getColumn(Schlossart),
				schloss,
				[schlossW?.schlossArt?.translate ?: ""]
			)

			// N: Ssko.Sk_Ssp.Bezeichnung
			fillSwitch(
				instance,
				cols.getColumn(Sk_Ssp_Bezeichnung),
				schloss,
				new Case<Schloss>(
					[schlossSk !== null],
					[schlossKombination?.bezeichnung?.bezeichnungSk?.wert]
				),
				new Case<Schloss>(
					[schlossSsp !== null],
					[schluesselsperre?.bezeichnung?.bezeichnungTabelle?.wert]
				)
			)

			// O: Ssko.Sk_Ssp.Hauptschloss
			fill(
				instance,
				cols.getColumn(Sk_Ssp_Hauptschloss),
				schloss,
				[schloss?.schlossSk?.hauptschloss?.wert?.translate]
			);

			// P: Ssko.Sk_Ssp.Unterbringung.Art
			fillSwitch(
				instance,
				cols.getColumn(Sk_Ssp_Unterbringung_Art),
				schloss,
				new Case<Schloss>(
					[schlossSk !== null],
					[
						schlossKombination?.unterbringung?.unterbringungAllg?.
							unterbringungArt?.translate
					]
				),
				new Case<Schloss>(
					[schlossSsp !== null],
					[
						schluesselsperre?.unterbringung?.unterbringungAllg?.
							unterbringungArt?.translate
					]
				)
			)

			// Q: Ssko.Sk_Ssp.Unterbringung.Ort
			fillSwitch(
				instance,
				cols.getColumn(Sk_Ssp_Unterbringung_Ort),
				schloss,
				new Case<Schloss>(
					[schlossSk !== null],
					[
						schlossKombination?.unterbringung?.
							standortBeschreibung?.wert
					]
				),
				new Case<Schloss>(
					[schlossSsp !== null],
					[
						schluesselsperre?.unterbringung?.
							standortBeschreibung?.wert
					]
				)
			)

			// R: Ssko.Sk_Ssp.Unterbringung.Strecke
			fillSwitch(
				instance,
				cols.getColumn(Sk_Ssp_Unterbringung_Strecke),
				schloss,
				new Case<Schloss>(
					[schlossSk !== null],
					[
						schlossKombination?.unterbringung?.strecken?.map [
							bezeichnung?.bezeichnungStrecke.wert
						]
					],
					ITERABLE_FILLING_SEPARATOR,
					null
				),
				new Case<Schloss>(
					[schlossSsp !== null],
					[
						schluesselsperre?.unterbringung?.strecken?.map [
							bezeichnung?.bezeichnungStrecke?.wert
						]
					],
					ITERABLE_FILLING_SEPARATOR,
					null
				)
			)

			// S: Ssko.Sk_Ssp.Unterbringung.km
			fillSwitch(
				instance,
				cols.getColumn(Sk_Ssp_Unterbringung_km),
				schloss,
				new Case<Schloss>(
					[schlossSk !== null],
					[
						schlossKombination?.unterbringung?.punktObjektStrecke?.
							map[streckeKm?.wert]
					],
					ITERABLE_FILLING_SEPARATOR,
					null
				),
				new Case<Schloss>(
					[schlossSsp !== null],
					[
						schluesselsperre?.unterbringung?.punktObjektStrecke?.map [
							streckeKm?.wert
						]
					],
					ITERABLE_FILLING_SEPARATOR,
					null
				)
			)

			// T: Ssko.Sonderanlage
			fillSwitch(
				instance,
				cols.getColumn(Sonderanlage),
				schloss,
				new Case<Schloss>(
					[schloss?.schlossSonderanlage !== null],
					[
						'''
							«sonderanlage?.bezeichnung?.bezeichnungTabelle?.wert»
							(«schlossSonderanlage?.sonderanlageLage?.translate ?: ""»)
						'''
					]
				),
				new Case<Schloss>(
					[
						schloss?.schlossSonderanlage?.
							beschreibungSonderanlage !== null
					],
					[
						'''
							«schlossSonderanlage?.beschreibungSonderanlage?.wert»
							(«schlossSonderanlage?.sonderanlageLage?.translate ?: ""»)
						'''
					]
				)
			)

			// U: Ssko.Technisch_Berechtigter
			fillConditional(
				instance,
				cols.getColumn(Technisch_Berechtigter),
				schloss,
				[technischBerechtigter?.wert !== null],
				[(technischBerechtigter.wert.translate)]
			)

			// V: Ssko.Bemerkung
			fillFootnotes(instance, schloss)

		}

		return factory.table
	}

	private def Iterable<Fstr_Zug_Rangier> getFstrZugRangier(
		Schluesselsperre schluesselsperre) {
		val c = schluesselsperre.container
		return c.fstrAbhaengigkeit.filter [
			fstrAbhaengigkeitSsp?.IDSchluesselsperre?.value === schluesselsperre
		].flatMap [
			val fahrweg = it.fstrFahrweg
			c.fstrZugRangier.filter[IDFstrFahrweg?.value === fahrweg]
		].toSet
	}

	private def String fstrName(
		Fstr_Zug_Rangier fstrZugRangier
	) {
		val fstrFahrweg = fstrZugRangier?.fstrFahrweg
		return '''«fstrFahrweg?.start?.bezeichnung?.bezeichnungTabelle?.wert»/«fstrFahrweg?.zielSignal?.bezeichnung?.bezeichnungTabelle?.wert»'''
	}

	private def Iterable<Schloss> getObjectInControlArea(
		MultiContainer_AttributeGroup container, Stell_Bereich controlArea) {
		if (controlArea === null) {
			return container.schloss
		}
		val result = newHashSet
		// 1. Condition
		// IMPROVE: Not completely, because the requirements for this case aren't clear
		val stellelements = container.stellelement.map[IDInformation?.value].
			filterNull.filter[isBelongToControlArea(controlArea)]
		val ssp = container.schluesselsperre.filter [ ssp |
			stellelements.exists[it === ssp.IDStellelement.value]
		]
		val schluessels = container.schloss.filter [ schloss |
			ssp.exists[it === schloss.schlossSsp.IDSchluesselsperre.value]
		].map[schluesel].filterNull
		result.addAll(schluessels.flatMap[schloesser])

		// 2.Condition
		result.filter[schlossSk?.hauptschloss.wert].flatMap [ schloss |
			schloss.schlossSk.IDSchlosskombination?.value.schloesser.filter [
				it.schlossSk !== null && !it.schlossSk.hauptschloss.wert
			]
		].filterNull.map[schluesel].flatMap[schloesser].forEach[result.add(it)]

		// 3. Condition
		container.schloss.filter [ schloss |
			controlArea.wkrGspElement.exists [ gspElement |
				schloss.schlossW?.IDWKrElement?.value === gspElement ||
					schloss.schlossGsp?.IDGspElement?.value === gspElement ||
					schloss.schlossSonderanlage?.IDSonderanlage?.value ==
						gspElement
			]
		].map[schluesel].flatMap[schloesser].toSet.filter [
			technischBerechtigter?.wert
		].forEach[result.add(it)]
		return result
	}
}
