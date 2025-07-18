/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1.sska

import java.util.Collections
import java.util.LinkedList
import java.util.Set
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Ansteuerung_Element.Technik_Standort
import org.eclipse.set.model.planpro.Ansteuerung_Element.Unterbringung
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.tablemodel.ColumnDescriptor
import org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions
import org.eclipse.set.ppmodel.extensions.ESTW_ZentraleinheitExtensions
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.utils.table.TMFactory
import org.osgi.service.event.EventAdmin

import static org.eclipse.set.feature.table.pt1.sska.SskaColumns.*
import static org.eclipse.set.model.planpro.Bedienung.ENUMBedienPlatzArt.*

import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienBezirkExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ESTW_ZentraleinheitExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UnterbringungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UrObjectExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.CollectionExtensions.*

/**
 * Table transformation for a Elementansteuertabelle (Sska).
 * 
 * @author Schneider
 */
class SskaTransformator extends AbstractPlanPro2TableModelTransformator {

	new(Set<ColumnDescriptor> cols,
		EnumTranslationService enumTranslationService, EventAdmin eventAdmin) {
		super(cols, enumTranslationService, eventAdmin)
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory, Stell_Bereich controlArea) {

		val aussenelementansteuerungList = container.aussenelementansteuerung
		val estwzentraleinheitList = container.ESTWZentraleinheit
		var EList<Basis_Objekt> elementList = new BasicEList<Basis_Objekt>();

		elementList.addAll(estwzentraleinheitList);
		elementList.addAll(aussenelementansteuerungList);
		// Basis_Objekt
		for (element : elementList.filter[isPlanningObject].
			filterObjectsInControlArea(controlArea)) {
			if (Thread.currentThread.interrupted) {
				return null
			}
			val instance = factory.newTableRow(element)

			// A: Sska.Grundsatzangaben.Bezeichnung
			fill(
				instance,
				cols.getColumn(Bezeichnung),
				element,
				[elementBezeichnung]
			)

			// B: Sska.Grundsatzangaben.Art
			fill(
				instance,
				cols.getColumn(Art),
				element,
				[elementArt]
			)

			// C: Sska.Grundsatzangaben.Bauart
			fill(
				instance,
				cols.getColumn(Bauart),
				element,
				[elementBauart]
			)

			// D: Sska.Grundsatzangaben.Unterbringung.Art
			fill(
				instance,
				cols.getColumn(Unterbringung_Art),
				element,
				[
					unterbringung?.unterbringungAllg?.unterbringungArt?.
						translate ?: ""
				]
			)

			// E: Sska.Grundsatzangaben.Unterbringung.Ort
			fill(
				instance,
				cols.getColumn(Unterbringung_Ort),
				element,
				[unterbringung.ort ?: ""]
			)

			// F: Sska.Grundsatzangaben.Unterbringung.Strecke
			fillIterable(
				instance,
				cols.getColumn(Unterbringung_Strecke),
				element,
				[
					unterbringung.strecken?.map [
						bezeichnung?.bezeichnungStrecke?.wert ?: ""
					] ?: Collections.emptyList
				],
				null
			)

			// G: Sska.Grundsatzangaben.Unterbringung.km
			fill(
				instance,
				cols.getColumn(Unterbringung_km),
				element,
				[unterbringung.streckeKm ?: ""]
			)

			// H: Sska.Grundsatzangaben.Unterbringung.Tueranschlag
			fill(
				instance,
				cols.getColumn(Unterbringung_Tueranschlag),
				element,
				[
					unterbringung?.unterbringungAllg?.tueranschlag?.
						translate ?: ""
				]
			)

			// I: Sska.Verknüpfungen.Information.primaer
			fillIterable(
				instance,
				cols.getColumn(Information_primaer),
				element,
				[elementVerknuepfungenInformationPrimaer],
				MIXED_STRING_COMPARATOR
			)

			// J: Sska.Verknüpfungen.Information.sekundaer
			fillIterable(
				instance,
				cols.getColumn(Information_sekundaer),
				element,
				[elementVerknuepfungenInformationSekundaer],
				MIXED_STRING_COMPARATOR
			)

			// K: Sska.Verknüpfungen.Energie.primaer
			fill(
				instance,
				cols.getColumn(Energie_primaer),
				element,
				[elementVerknuepfungenEnergiePrimaer]
			)

			// L: Sska.Verknüpfungen.Energie.sekundaer
			fill(
				instance,
				cols.getColumn(Energie_sekundaer),
				element,
				[elementVerknuepfungenEnergieSekundaer]
			)

			// M: Sska.Verknüpfungen.Bedienung.lokal
			fill(
				instance,
				cols.getColumn(Bedienung_lokal),
				element,
				[elementVerknuepfungenBedienungLokal]
			)

			// N: Sska.Verknüpfungen.Bedienung.bezirk
			fill(
				instance,
				cols.getColumn(Bedienung_bezirk),
				element,
				[elementVerknuepfungenBedienungBezirk]
			)

			// O: Sska.Verknüpfungen.Bedienung.zentrale
			fill(
				instance,
				cols.getColumn(Bedienung_zentrale),
				element,
				[elementVerknuepfungenBedienungZentrale]
			)

			// P: Sska.Verknüpfungen.Bedienung.NotBP
			fill(
				instance,
				cols.getColumn(Bedienung_NotBP),
				element,
				[elementVerknuepfungenBedienungNotBP]
			)

			// Q: Sska.IP_Adressangaben.GFK_Kategorie
			fill(
				instance,
				cols.getColumn(IP_GFK_Kategorie),
				element,
				[ipAdressangabenGFKKategorie]
			)

			// R: Sska.IP_Adressangaben.Regionalbereich
			fill(
				instance,
				cols.getColumn(IP_Regionalbereich),
				element,
				[ipAdressangabenRegionalbereich]
			)

			// S: Sska.IP_Adressangaben.Adressblock_Blau.IPv4_Blau
			fill(
				instance,
				cols.getColumn(IPv4_Blau),
				element,
				[ipAdressangabenIPv4Blau]
			)

			// T: Sska.IP_Adressangaben.Adressblock_Blau.IPv6_Blau
			fill(
				instance,
				cols.getColumn(IPv6_Blau),
				element,
				[ipAdressangabenIPv6Blau]
			)

			// U: Sska.IP_Adressangaben.Adressblock_Grau.IPv4_Grau
			fill(
				instance,
				cols.getColumn(IPv4_Grau),
				element,
				[ipAdressangabenIPv4Grau]
			)

			// V: Sska.IP_Adressangaben.Adressblock_Grau.IPv6_Grau
			fill(
				instance,
				cols.getColumn(IPv6_Grau),
				element,
				[ipAdressangabenIPv6Grau]
			)

			// W: Bemerkung
			fillFootnotes(instance, element)
		}

		return factory.table
	}

	private def dispatch String getElementArt(Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch String getElementArt(
		Aussenelementansteuerung element) {
		return element?.AEAAllg?.aussenelementansteuerungArt?.translate ?:
			"";
	}

	private def dispatch String getElementArt(ESTW_Zentraleinheit element) {
		return "ESTW-ZE";
	}

	private def dispatch String getElementBauart(Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch String getElementBauart(
		Aussenelementansteuerung element) {
		return element?.AEAAllg?.bauart?.wert ?: "";
	}

	private def dispatch String getElementBauart(ESTW_Zentraleinheit element) {
		return element.ESTWZentraleinheitAllg.bauart?.wert ?: "";
	}

	private def dispatch Unterbringung getUnterbringung(Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch Unterbringung getUnterbringung(
		Aussenelementansteuerung element) {
		return AussenelementansteuerungExtensions.getUnterbringung(element)
	}

	private def dispatch Unterbringung getUnterbringung(
		ESTW_Zentraleinheit element) {
		return ESTW_ZentraleinheitExtensions.getUnterbringung(element)
	}

	private def dispatch Iterable<String> getElementVerknuepfungenInformationPrimaer(
		Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch Iterable<String> getElementVerknuepfungenInformationPrimaer(
		Aussenelementansteuerung element) {
		var elementList = new LinkedList<Basis_Objekt>

		elementList.addAll(element.container.aussenelementansteuerung)
		elementList.addAll(element.container.ESTWZentraleinheit)

		return elementList.filter [
			element.IDInformationPrimaer.map[value?.identitaet?.wert].contains(
				identitaet.wert)
		].map[bezeichner]
	}

	private def dispatch Iterable<String> getElementVerknuepfungenInformationPrimaer(
		ESTW_Zentraleinheit element) {
		val technikStandorts = element.technikStandort
		return technikStandorts.map[bezeichner]
	}

	private def dispatch Iterable<String> getElementVerknuepfungenInformationSekundaer(
		Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch Iterable<String> getElementVerknuepfungenInformationSekundaer(
		Aussenelementansteuerung element) {
		return element.informationSekundaer.map [
			bezeichner
		]
	}

	private def dispatch Iterable<String> getElementVerknuepfungenInformationSekundaer(
		ESTW_Zentraleinheit element) {
		return #[];
	}

	private def dispatch String getElementVerknuepfungenEnergiePrimaer(
		Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch String getElementVerknuepfungenEnergiePrimaer(
		Aussenelementansteuerung element) {

		if (element?.AEAEnergieversorgung?.IDEnergiePrimaer !== null) {
			val energiePrimaer = element.elementEnergiePrimaer
			return energiePrimaer.bezeichner
		} else {
			return element?.AEAEnergieversorgung?.energieversorgungArt.
				translate ?: "";
		}
	}

	private def dispatch String getElementVerknuepfungenEnergiePrimaer(
		ESTW_Zentraleinheit element) {
		if (element.ESTWZEEnergieversorgung?.IDEnergiePrimaer !== null) {
			return element.elementEnergiePrimaer.bezeichner
		}
		return element?.ESTWZEEnergieversorgung?.energieversorgungArt.
			translate ?: ""
	}

	private def dispatch String getElementVerknuepfungenEnergieSekundaer(
		Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch String getElementVerknuepfungenEnergieSekundaer(
		Aussenelementansteuerung element) {
		if (element?.AEAEnergieversorgung?.IDEnergieSekundaer !== null) {
			val energieSekundaer = element.elementEnergieSekundaer
			return energieSekundaer.bezeichner
		}
		return element?.AEAEnergieversorgung?.energieversorgungArtErsatz.
			translate ?: "";
	}

	private def dispatch String getElementVerknuepfungenEnergieSekundaer(
		ESTW_Zentraleinheit element) {
		if (element?.ESTWZEEnergieversorgung?.IDEnergieSekundaer !== null) {
			return element.elementEnergieSekundaer.bezeichner
		}
		return element?.ESTWZEEnergieversorgung?.energieversorgungArtErsatz.
			translate ?: ""
	}

	private def dispatch String getElementVerknuepfungenBedienungLokal(
		Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch String getElementVerknuepfungenBedienungLokal(
		Aussenelementansteuerung element) {
		return "";
	}

	private def dispatch String getElementVerknuepfungenBedienungLokal(
		ESTW_Zentraleinheit element) {
		return if (!element.bedienPlaetze.empty &&
			(element.bedienPlaetze.unique.bedienPlatzAllg.bedienPlatzArt.wert ==
				ENUM_BEDIEN_PLATZ_ART_STANDARD_BPS ||
				element.bedienPlaetze.unique.bedienPlatzAllg.bedienPlatzArt.
					wert == ENUM_BEDIEN_PLATZ_ART_STANDARD_BPS_ABGESETZT)) {
			"x"
		} else {
			""
		}
	}

	private def dispatch String getElementVerknuepfungenBedienungBezirk(
		Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch String getElementVerknuepfungenBedienungBezirk(
		Aussenelementansteuerung element) {
		return "";
	}

	private def dispatch String getElementVerknuepfungenBedienungBezirk(
		ESTW_Zentraleinheit element) {

		var bedienBezirk = element.bedienBezirkZentral
		if (bedienBezirk === null)
			bedienBezirk = element.bedienBezirkVirtuell

		val steuerbezirksNummer = bedienBezirk?.bedienBezirkAllg?.
			steuerbezirksnummer?.wert
		val steuerbezirksName = bedienBezirk?.bedienBezirkAllg?.
			steuerbezirksname?.wert

		if (steuerbezirksNummer !== null && steuerbezirksName !== null) {
			return '''«steuerbezirksNummer» («steuerbezirksName»)'''
		} else if (steuerbezirksNummer !== null) {
			return '''«steuerbezirksNummer»'''
		} else if (steuerbezirksName !== null) {
			return '''«steuerbezirksName»'''
		}
		return ""
	}

	private def dispatch String getElementVerknuepfungenBedienungZentrale(
		Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch String getElementVerknuepfungenBedienungZentrale(
		Aussenelementansteuerung element) {
		return "";
	}

	private def dispatch String getElementVerknuepfungenBedienungZentrale(
		ESTW_Zentraleinheit element) {

		var bedienBezirk = element.bedienBezirkZentral
		if (bedienBezirk === null)
			bedienBezirk = element.bedienBezirkVirtuell

		return bedienBezirk?.bedienZentrale?.bezeichnung?.bezBedZentrale?.
			wert ?: ""
	}

	private def dispatch String getElementVerknuepfungenBedienungNotBP(
		Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch String getElementVerknuepfungenBedienungNotBP(
		Aussenelementansteuerung element) {
		return "";
	}

	private def dispatch String getElementVerknuepfungenBedienungNotBP(
		ESTW_Zentraleinheit element) {
		return if (!element.bedienPlaetze.empty &&
			(element.bedienPlaetze.unique.bedienPlatzAllg.bedienPlatzArt.wert ==
				ENUM_BEDIEN_PLATZ_ART_NOT_BPS ||
				element.bedienPlaetze.unique.bedienPlatzAllg.bedienPlatzArt.
					wert == ENUM_BEDIEN_PLATZ_ART_NOT_BPS_ABGESETZT)) {
			"x"
		} else {
			""
		}
	}

	private dispatch def Basis_Objekt getElementEnergiePrimaer(
		Aussenelementansteuerung element) {
		return element.container.getElementEnergie(
			element.AEAEnergieversorgung.IDEnergiePrimaer.value)
	}

	private dispatch def Basis_Objekt getElementEnergiePrimaer(
		ESTW_Zentraleinheit element) {
		return element.container.getElementEnergie(
			element.ESTWZEEnergieversorgung.IDEnergiePrimaer.value)
	}

	private dispatch def Basis_Objekt getElementEnergieSekundaer(
		Aussenelementansteuerung element) {
		return element.container.getElementEnergie(
			element.AEAEnergieversorgung.IDEnergieSekundaer.value)
	}

	private dispatch def Basis_Objekt getElementEnergieSekundaer(
		ESTW_Zentraleinheit element) {
		return element.container.getElementEnergie(
			element.ESTWZEEnergieversorgung.IDEnergieSekundaer.value)
	}

	private def Basis_Objekt getElementEnergie(
		MultiContainer_AttributeGroup container, Basis_Objekt idEnergie) {
		var elementList = new LinkedList<Basis_Objekt>

		elementList.addAll(container.aussenelementansteuerung)
		elementList.addAll(container.ESTWZentraleinheit)

		for (el : elementList) {
			if (el.identitaet.wert == idEnergie.identitaet?.wert) {
				return el
			}
		}
		return null
	}

	private dispatch def String getBezeichner(Basis_Objekt objekt) {
		throw new IllegalArgumentException(objekt.class.simpleName)
	}

	private dispatch def String getBezeichner(
		Aussenelementansteuerung ansteuerung) {
		return '''«ansteuerung.bezeichnung.bezeichnungAEA.wert»'''
	}

	private dispatch def String getBezeichner(ESTW_Zentraleinheit ze) {
		return '''«ze.bezeichnung.bezeichnungESTWZE.wert»'''
	}

	private dispatch def String getBezeichner(
		Technik_Standort technikStandort) {
		return '''«technikStandort.bezeichnung.bezeichnungTSO.wert»'''
	}

	private dispatch def String ipAdressangabenGFKKategorie(
		Basis_Objekt objekt
	) {
		throw new IllegalArgumentException(objekt.class.simpleName)
	}

	private dispatch def String ipAdressangabenGFKKategorie(
		Aussenelementansteuerung ansteuerung
	) {
		return ansteuerung?.AEAGFKIPAdressblock?.GFKKategorie?.translate
	}

	private dispatch def String ipAdressangabenGFKKategorie(
		ESTW_Zentraleinheit ze
	) {
		return ""
	}

	private dispatch def String ipAdressangabenRegionalbereich(
		Basis_Objekt objekt
	) {
		throw new IllegalArgumentException(objekt.class.simpleName)
	}

	private dispatch def String ipAdressangabenRegionalbereich(
		Aussenelementansteuerung ansteuerung
	) {
		return ansteuerung?.AEAGFKIPAdressblock?.regionalbereich?.translate
	}

	private dispatch def String ipAdressangabenRegionalbereich(
		ESTW_Zentraleinheit ze
	) {
		return ""
	}

	private dispatch def String ipAdressangabenIPv4Blau(
		Basis_Objekt objekt
	) {
		throw new IllegalArgumentException(objekt.class.simpleName)
	}

	private dispatch def String ipAdressangabenIPv4Blau(
		Aussenelementansteuerung ansteuerung
	) {
		return ansteuerung?.AEAGFKIPAdressblock?.IPAdressblockBlauV4?.wert
	}

	private dispatch def String ipAdressangabenIPv4Blau(
		ESTW_Zentraleinheit ze
	) {
		return ""
	}

	private dispatch def String ipAdressangabenIPv6Blau(
		Basis_Objekt objekt
	) {
		throw new IllegalArgumentException(objekt.class.simpleName)
	}

	private dispatch def String ipAdressangabenIPv6Blau(
		Aussenelementansteuerung ansteuerung
	) {
		return ansteuerung?.AEAGFKIPAdressblock?.IPAdressblockBlauV6?.wert
	}

	private dispatch def String ipAdressangabenIPv6Blau(
		ESTW_Zentraleinheit ze
	) {
		return ""
	}

	private dispatch def String ipAdressangabenIPv4Grau(
		Basis_Objekt objekt
	) {
		throw new IllegalArgumentException(objekt.class.simpleName)
	}

	private dispatch def String ipAdressangabenIPv4Grau(
		Aussenelementansteuerung ansteuerung
	) {
		return ansteuerung?.AEAGFKIPAdressblock?.IPAdressblockGrauV4?.wert
	}

	private dispatch def String ipAdressangabenIPv4Grau(
		ESTW_Zentraleinheit ze
	) {
		return ""
	}

	private dispatch def String ipAdressangabenIPv6Grau(
		Basis_Objekt objekt
	) {
		throw new IllegalArgumentException(objekt.class.simpleName)
	}

	private dispatch def String ipAdressangabenIPv6Grau(
		Aussenelementansteuerung ansteuerung
	) {
		return ansteuerung?.AEAGFKIPAdressblock?.IPAdressblockGrauV6?.wert
	}

	private dispatch def String ipAdressangabenIPv6Grau(
		ESTW_Zentraleinheit ze
	) {
		return ""
	}
}
