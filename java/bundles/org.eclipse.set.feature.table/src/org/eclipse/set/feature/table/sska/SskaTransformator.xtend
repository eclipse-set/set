/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.sska

import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Aussenelementansteuerung
import org.eclipse.set.toolboxmodel.Ansteuerung_Element.ESTW_Zentraleinheit
import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Unterbringung
import org.eclipse.set.toolboxmodel.Basisobjekte.Basis_Objekt
import java.util.Collections
import java.util.LinkedList
import org.eclipse.emf.common.util.BasicEList
import org.eclipse.emf.common.util.EList
import org.eclipse.set.feature.table.AbstractPlanPro2TableModelTransformator
import org.eclipse.set.feature.table.messages.MessagesWrapper
import org.eclipse.set.model.tablemodel.Table
import org.eclipse.set.model.tablemodel.format.TextAlignment
import org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions
import org.eclipse.set.ppmodel.extensions.ESTW_ZentraleinheitExtensions
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup

import static org.eclipse.set.toolboxmodel.Bedienung.ENUMBedienPlatzArt.*

import static extension org.eclipse.set.model.tablemodel.extensions.TableExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ESTW_ZentraleinheitExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.UnterbringungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienBezirkExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.utils.CollectionExtensions.*
import org.eclipse.set.utils.table.TMFactory

/**
 * Table transformation for a Elementansteuertabelle (Sska).
 * 
 * @author Schneider
 */
class SskaTransformator extends AbstractPlanPro2TableModelTransformator {

	SskaColumns cols

	new(SskaColumns columns, MessagesWrapper messagesWrapper) {
		super(messagesWrapper)
		this.cols = columns;
	}

	override transformTableContent(MultiContainer_AttributeGroup container,
		TMFactory factory) {

		val aussenelementansteuerungList = container.aussenelementansteuerung
		val estwzentraleinheitList = container.ESTWZentraleinheit
		var EList<Basis_Objekt> elementList = new BasicEList<Basis_Objekt>();

		elementList.addAll(estwzentraleinheitList);
		elementList.addAll(aussenelementansteuerungList);

		// Basis_Objekt
		for (element : elementList) {
			if (Thread.currentThread.interrupted) {
				return null
			}
			val instance = factory.newTableRow(element)

			// A: Sska.Grundsatzangaben.Bezeichnung
			fill(
				instance,
				cols.zentraleinheit_bezeichnung,
				element,
				[elementBezeichnung]
			)

			// B: Sska.Grundsatzangaben.Art
			fill(
				instance,
				cols.art,
				element,
				[elementArt]
			)

			// C: Sska.Grundsatzangaben.Bauart
			fill(
				instance,
				cols.bauart,
				element,
				[elementBauart]
			)

			// D: Sska.Grundsatzangaben.Unterbringung.Art
			fill(
				instance,
				cols.unterbringung_art,
				element,
				[unterbringung.art.translate ?: ""]
			)

			// E: Sska.Grundsatzangaben.Unterbringung.Ort
			fill(
				instance,
				cols.ort,
				element,
				[unterbringung.ort ?: ""]
			)

			// F: Sska.Grundsatzangaben.Unterbringung.Strecke
			fillIterable(
				instance,
				cols.strecke,
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
				cols.km,
				element,
				[unterbringung.streckeKm ?: ""]
			)

			// H: Sska.Verknüpfungen.Information.primaer
			fill(
				instance,
				cols.information_primaer,
				element,
				[elementVerknuepfungenInformationPrimaer]
			)

			// I: Sska.Verknüpfungen.Information.sekundaer
			fill(
				instance,
				cols.information_sekundaer,
				element,
				[elementVerknuepfungenInformationSekundaer]
			)

			// J: Sska.Verknüpfungen.Energie.primaer
			fill(
				instance,
				cols.energie_primaer,
				element,
				[elementVerknuepfungenEnergiePrimaer]
			)

			// K: Sska.Verknüpfungen.Energie.sekundaer
			fill(
				instance,
				cols.energie_sekundaer,
				element,
				[elementVerknuepfungenEnergieSekundaer]
			)

			// L: Sska.Verknüpfungen.Bedienung.lokal
			fill(
				instance,
				cols.lokal,
				element,
				[elementVerknuepfungenBedienungLokal]
			)

			// M: Sska.Verknüpfungen.Bedienung.bezirk
			fill(
				instance,
				cols.bezirk,
				element,
				[elementVerknuepfungenBedienungBezirk]
			)

			// N: Sska.Verknüpfungen.Bedienung.zentrale
			fill(
				instance,
				cols.zentrale,
				element,
				[elementVerknuepfungenBedienungZentrale]
			)

			// O: Sska.Verknüpfungen.Bedienung.NotBP
			fill(
				instance,
				cols.notbp,
				element,
				[elementVerknuepfungenBedienungNotBP]
			)

			// P: Sska.IP_Adressangaben.GFK_Kategorie
			fill(
				instance,
				cols.GFK_Kategorie,
				element,
				[ipAdressangabenGFKKategorie]
			)

			// Q: Sska.IP_Adressangaben.Regionalbereich
			fill(
				instance,
				cols.Regionalbereich,
				element,
				[ipAdressangabenRegionalbereich]
			)

			// R: Sska.IP_Adressangaben.Adressblock_Blau.IPv4_Blau
			fill(
				instance,
				cols.IPv4_Blau,
				element,
				[ipAdressangabenIPv4Blau]
			)

			// S: Sska.IP_Adressangaben.Adressblock_Blau.IPv6_Blau
			fill(
				instance,
				cols.IPv6_Blau,
				element,
				[ipAdressangabenIPv6Blau]
			)

			// T: Sska.IP_Adressangaben.Adressblock_Blau.IPv4_Grau
			fill(
				instance,
				cols.IPv4_Grau,
				element,
				[ipAdressangabenIPv4Grau]
			)

			// U: Sska.IP_Adressangaben.Adressblock_Blau.IPv6_Grau
			fill(
				instance,
				cols.IPv6_Grau,
				element,
				[ipAdressangabenIPv6Grau]
			)

			// V: Bemerkung
			fill(
				instance,
				cols.basis_bemerkung,
				element,
				[footnoteTransformation.transform(it, instance)]
			)
		}

		return factory.table
	}

	override void formatTableContent(Table table) {
		// A: Sska.Grundsatzangaben.Bezeichnung
		table.setTextAlignment(0, TextAlignment.LEFT);

		// G: Sska.Grundsatzangaben.Unterbringung.km
		table.setTextAlignment(6, TextAlignment.RIGHT);

		// O: Sska.Bemerkung
		table.setTextAlignment(20, TextAlignment.LEFT);
	}

	private def dispatch String getElementBezeichnung(Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch String getElementBezeichnung(
		Aussenelementansteuerung element) {
		return element?.bezeichnung?.bezeichnungAEA?.wert ?: "";
	}

	private def dispatch String getElementBezeichnung(
		ESTW_Zentraleinheit element) {
		return element?.bezeichnung?.bezeichnungESTWZE?.wert ?: "";
	}

	private def dispatch String getElementArt(Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch String getElementArt(
		Aussenelementansteuerung element) {
		return element?.AEAAllg?.aussenelementansteuerungArt?.wert?.translate ?:
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

	private def dispatch String getElementVerknuepfungenInformationPrimaer(
		Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch String getElementVerknuepfungenInformationPrimaer(
		Aussenelementansteuerung element) {
		var elementList = new LinkedList<Basis_Objekt>

		elementList.addAll(element.container.aussenelementansteuerung)
		elementList.addAll(element.container.ESTWZentraleinheit)

		return elementList.filter [
			element.IDInformationPrimaer.map[identitaet?.wert].contains(identitaet.wert)
		].map[bezeichner].toList.getIterableFilling(MIXED_STRING_COMPARATOR)
	}

	private def dispatch String getElementVerknuepfungenInformationPrimaer(
		ESTW_Zentraleinheit element) {
		return "";
	}

	private def dispatch String getElementVerknuepfungenInformationSekundaer(
		Basis_Objekt element) {
		throw new IllegalArgumentException(element.class.simpleName)
	}

	private def dispatch String getElementVerknuepfungenInformationSekundaer(
		Aussenelementansteuerung element) {
		return element.aussenelementansteuerungInformationSekundaer.map [
			bezeichner
		].getIterableFilling(MIXED_STRING_COMPARATOR)
	}

	private def dispatch String getElementVerknuepfungenInformationSekundaer(
		ESTW_Zentraleinheit element) {
		return "";
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
			return element?.AEAEnergieversorgung?.energieversorgungArt?.wert?.
				translate;
		}
	}

	private def dispatch String getElementVerknuepfungenEnergiePrimaer(
		ESTW_Zentraleinheit element) {
		return element.ESTWZentraleinheitAllg.energieversorgungArt.wert.
			translate;
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
		return element?.AEAEnergieversorgung?.energieversorgungArtErsatz?.wert?.
			translate ?: "";
	}

	private def dispatch String getElementVerknuepfungenEnergieSekundaer(
		ESTW_Zentraleinheit element) {
		return element?.ESTWZentraleinheitAllg?.energieversorgungArtErsatz?.
			wert?.translate;
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
			steuerbezirksnummer
		val steuerbezirksName = bedienBezirk?.bedienBezirkAllg?.
			steuerbezirksname

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

	private def Basis_Objekt getElementEnergiePrimaer(
		Aussenelementansteuerung element) {

		var elementList = new LinkedList<Basis_Objekt>

		elementList.addAll(element.container.aussenelementansteuerung)
		elementList.addAll(element.container.ESTWZentraleinheit)

		for (el : elementList) {
			if (el.identitaet.wert ==
				element.AEAEnergieversorgung.IDEnergiePrimaer.identitaet?.wert) {
				return el;
			}
		}
		return null;
	}

	private def Basis_Objekt getElementEnergieSekundaer(
		Aussenelementansteuerung element) {

		var elementList = new LinkedList<Basis_Objekt>

		elementList.addAll(element.container.aussenelementansteuerung)
		elementList.addAll(element.container.ESTWZentraleinheit)

		for (el : elementList) {
			if (el.identitaet.wert ==
				element.AEAEnergieversorgung.IDEnergieSekundaer.identitaet?.wert) {
				return el;
			}
		}
		return null;
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

	private dispatch def String ipAdressangabenGFKKategorie(
		Basis_Objekt objekt
	) {
		throw new IllegalArgumentException(objekt.class.simpleName)
	}

	private dispatch def String ipAdressangabenGFKKategorie(
		Aussenelementansteuerung ansteuerung
	) {
		return ansteuerung?.AEAGFKIPAdressblock?.GFKKategorie?.wert?.translate
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
		return ansteuerung?.AEAGFKIPAdressblock?.regionalbereich?.wert?.
			translate
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
