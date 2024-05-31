/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.math.BigDecimal
import org.eclipse.emf.ecore.EObject
import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stellelement
import org.eclipse.set.model.planpro.Ansteuerung_Element.Technik_Standort
import org.eclipse.set.model.planpro.Ansteuerung_Element.Uebertragungsweg
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt
import org.eclipse.set.model.planpro.Bedienung.Bedien_Bezirk
import org.eclipse.set.model.planpro.Bedienung.Bedien_Einrichtung_Oertlich
import org.eclipse.set.model.planpro.Bedienung.Bedien_Standort
import org.eclipse.set.model.planpro.Bedienung.Bedien_Zentrale
import org.eclipse.set.model.planpro.Block.Block_Element
import org.eclipse.set.model.planpro.Fahrstrasse.ENUMFstrZugArt
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Aneinander
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Fahrweg
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.model.planpro.Flankenschutz.Fla_Schutz
import org.eclipse.set.model.planpro.Flankenschutz.Fla_Zwieschutz
import org.eclipse.set.model.planpro.Gleis.Gleis_Bezeichnung
import org.eclipse.set.model.planpro.Nahbedienung.NB_Zone
import org.eclipse.set.model.planpro.Nahbedienung.NB_Zone_Grenze
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente
import org.eclipse.set.model.planpro.Ortung.Zugeinwirkung
import org.eclipse.set.model.planpro.PZB.PZB_Element
import org.eclipse.set.model.planpro.PlanPro.LST_Zustand
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle

import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BedienBezirkExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BlockElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ESTW_ZentraleinheitExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FlaSchutzExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FlaZwieschutzExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrAneinanderExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.MarkanterPunktExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.NbZoneGrenzeExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PZBElementExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StellBereichExtensions.*
import org.eclipse.set.core.services.Services

/**
 * Diese Klasse erweitert {@link Ur_Objekt}.
 */
class UrObjectExtensions extends BasisAttributExtensions {

	/**
	 * @param object this object
	 * 
	 * @return the type name of the object
	 */
	def static String getTypeName(EObject object) {
		val interfaces = object.class.interfaces
		return '''«FOR i : interfaces»«i.simpleName»«ENDFOR»'''
	}

	/**
	 * Returns the LST_Zustand this Ur_Objekt is contained within
	 * 
	 * @param object this object
	 * @return the LST_Zustand or null
	 */
	def static LST_Zustand getLSTZustand(Ur_Objekt object) {
		var EObject container = object
		while (container !== null && !(container instanceof LST_Zustand)) {
			container = container.eContainer
		}
		return container as LST_Zustand
	}

	/**
	 * Returns the PlanPro_Schnittstelle this Ur_Objekt is contained within
	 * 
	 * @param object this object
	 * @return the PlanPro_Schnittstelle or null
	 */
	def static PlanPro_Schnittstelle getPlanProSchnittstelle(Ur_Objekt object) {
		var EObject container = object
		while (container !== null &&
			!(container instanceof PlanPro_Schnittstelle)) {
			container = container.eContainer
		}

		return container as PlanPro_Schnittstelle
	}

	def static boolean isPlanningObject(Ur_Objekt object) {
		val modelSession = Services.toolboxViewModelService.session.orElse(null)
		if (modelSession !== null && modelSession.isPlanningAreaIgnored) {
			return true;
		}

		val guid = object?.identitaet?.wert
		val ppschnittstelle = object.planProSchnittstelle
		val planData = ppschnittstelle?.LSTPlanung?.objektmanagement?.
			LSTPlanungProjekt?.flatMap [
				it?.LSTPlanungGruppe?.map [
					it?.LSTPlanungEinzel?.LSTObjektePlanungsbereich
				]?.filterNull?.flatMap[IDLSTObjektPlanungsbereich]
			]?.filterNull ?: #[]

		return planData.exists[wert == guid]
	}

	def static <T extends Ur_Objekt> Iterable<T> filterObjectsInControlArea(
		Iterable<T> objects, Stell_Bereich area) {
		if (area === null) {
			return objects
		}

		return objects.filter[isInControlArea(area)]
	}

	private def static dispatch boolean isInControlArea(Ur_Objekt object,
		Stell_Bereich area) {
		throw new IllegalArgumentException()
	}

	private def static dispatch boolean isInControlArea(
		Aussenelementansteuerung aussenElement, Stell_Bereich area) {
		return area.aussenElementAnsteuerung == aussenElement;
	}

	private def static dispatch boolean isInControlArea(
		ESTW_Zentraleinheit object, Stell_Bereich area) {
		val energiePrimar = area?.aussenElementAnsteuerung?.
			aussenelementansteuerungEnergiePrimaer
		return energiePrimar == object;
	}

	private def static dispatch boolean isInControlArea(
		Bedien_Einrichtung_Oertlich bedienEinrichtung, Stell_Bereich area) {
		return area?.aussenElementAnsteuerung ===
			bedienEinrichtung.IDAussenelementansteuerung?.value
	}

	private def static dispatch boolean isInControlArea(FMA_Komponente object,
		Stell_Bereich area) {
		return object?.FMAKomponenteAchszaehlpunkt?.IDInformation?.filterNull.
			exists [
				area.aussenElementAnsteuerung !== null &&
					it === area.aussenElementAnsteuerung
			]
	}

	def static dispatch boolean isInPlaceArea(Zugeinwirkung zugeinwirkung,
		Stell_Bereich area) {
		val schaltmittle = zugeinwirkung.container.schaltmittelZuordnung.filter [
			IDSchalter?.value instanceof Zugeinwirkung
		].filter[it === zugeinwirkung]

		return schaltmittle.map[IDSchalter?.value].exists [
			it instanceof Block_Element || it instanceof Fstr_Fahrweg
		] && zugeinwirkung.punktObjektTOPKante.exists[area.contains(it)]
	}

	def static dispatch boolean isInPlaceArea(PZB_Element object,
		Stell_Bereich placeArea) {
		val potk = object.PZBElementBezugspunkt.filter(Signal).filter [
			signalReal !== null && signalReal.signalRealAktiv === null
		].flatMap [
			punktObjektTOPKante
		]
		return !object.stellelements.filter [
			IDInformation?.value === placeArea.aussenElementAnsteuerung
		].nullOrEmpty && placeArea.bereichObjektTeilbereich.exists [ botb |
			potk.exists[botb.contains(it)]
		]
	}

	static final double tolerantDistance = 1000

	def static dispatch boolean isInPlaceArea(Signal signal,
		Stell_Bereich placeArea) {
		val firstcondition = signal.stellelement.IDInformation ===
			placeArea.IDAussenelementansteuerung ||
			((signal.signalFiktiv !== null || signal.signalReal !== null) &&
				signal.punktObjektTOPKante.exists [ potk |
					placeArea.bereichObjektTeilbereich.exists[it.contains(potk)]
				]
		)
		if (firstcondition) {
			return firstcondition
		}

		val isTargetSignal = signal.container.fstrZugRangier.map [
			IDFstrFahrweg?.value
		].forall[IDStart?.value !== signal]
		if (signal.signalReal !== null &&
			signal.signalReal.signalRealAktiv === null &&
			signal.signalFiktiv !== null && signal.punktObjektTOPKante.exists [
				placeArea.contains(it, tolerantDistance)
			]) {
			val topGraph = new TopGraph(signal.container.TOPKante)
			val areaTopKante = placeArea.bereichObjektTeilbereich.map[topKante].
				filter[!signal.topKanten.contains(it)]
			return isTargetSignal === !areaTopKante.forall [ topKante |
				topGraph.isInWirkrichtungOfSignal(signal, topKante)
			]
		}
		return false
	}

	def static dispatch boolean isInPlaceArea(Technik_Standort standort,
		Stell_Bereich placeArea) {
		return placeArea.technikStandorts.exists[it === standort]
	}

	def static dispatch boolean isInPlaceArea(Bedien_Standort standort,
		Stell_Bereich placeArea) {
		return placeArea.technikStandorts.flatMap[IDBedienStandort.map[value]].
			filterNull.exists[it === standort]
	}

	def static dispatch boolean isInPlaceArea(W_Kr_Gsp_Element gspElement,
		Stell_Bereich placeArea) {
		return gspElement.IDStellelement?.value.IDInformation?.value ===
			placeArea.aussenElementAnsteuerung
	}

	def static dispatch boolean isInPlaceArea(Fstr_Aneinander fstrAneinander,
		Stell_Bereich placeArea) {
		val areaStellelements = placeArea.aussenElementAnsteuerung.stellelements
		val fstrFarhwegs = fstrAneinander.container.fstrFahrweg.filter [ fstr |
			areaStellelements.exists[fstr?.IDStart?.value?.stellelement === it]
		]
		return fstrAneinander.zuordnungen.map[fstrZugRangier].map[fstrFahrweg].
			exists [ fstr |
				fstrFarhwegs.forall[IDZiel?.wert !== fstr?.IDStart?.wert]
			]
	}

	def static dispatch boolean isInPlaceArea(Fstr_DWeg fstrDWeg,
		Stell_Bereich placeArea) {
		return fstrDWeg.IDFstrFahrweg?.value?.IDStart?.value?.stellelement.
			isInPlaceArea(placeArea)
	}

	def static dispatch boolean isInPlaceArea(Fstr_Zug_Rangier fstrZugRangier,
		Stell_Bereich placeArea) {

		if (fstrZugRangier.isR) {
			return fstrZugRangier.isRangierStrInPlaceArea(placeArea)
		}

		if (isZ(fstrZugRangier.fstrZug?.fstrZugArt)) {
			return fstrZugRangier.isZugStrInPlaceArea(placeArea)
		}

		if (fstrZugRangier.fstrZug?.fstrZugArt.wert ===
			ENUMFstrZugArt.ENUM_FSTR_ZUG_ART_B) {
			// TODO
		}
		return true
	}

	private def static boolean isRangierStrInPlaceArea(
		Fstr_Zug_Rangier fstrZugRangier, Stell_Bereich placeArea) {
		if (fstrZugRangier.fstrRangier === null) {
			return false
		}
		val startSignal = fstrZugRangier.IDFstrFahrweg?.value?.IDStart?.value
		if (startSignal.signalReal !== null) {
			return startSignal.stellelement.isInPlaceArea(placeArea)
		}

		if (startSignal.signalFiktiv !== null) {
			return startSignal.punktObjektTOPKante.exists [ potk |
				placeArea.bereichObjektTeilbereich.exists[it.contains(potk)]
			]
		}
		return true
	}

	private def static boolean isZugStrInPlaceArea(
		Fstr_Zug_Rangier fstrZugRangier, Stell_Bereich placeArea) {
		if (fstrZugRangier.fstrZug === null &&
			fstrZugRangier.fstrMittel === null) {
			return false
		}
		val startSignal = fstrZugRangier.IDFstrFahrweg?.value?.IDStart?.value
		if (startSignal.signalReal !== null) {
			return startSignal.stellelement.isInPlaceArea(placeArea)
		}

		if (startSignal.signalFiktiv !== null) {
			return startSignal.punktObjektTOPKante.exists [ potk |
				placeArea.bereichObjektTeilbereich.exists[it.contains(potk)]
			]
		}
		return true
	}

	static def dispatch boolean isInPlaceArea(Fla_Schutz fla,
		Stell_Bereich placeArea) {
		val anforderer = fla.anforderer
		if (anforderer instanceof W_Kr_Gsp_Element) {
			return anforderer.IDStellelement?.value.isInPlaceArea(placeArea)
		}

		if (anforderer instanceof NB_Zone_Grenze) {
			return anforderer.isNBZoneGrenzeInPlaceArea(placeArea)
		}

		throw new IllegalArgumentException()
	}

	static def boolean isNBZoneGrenzeInPlaceArea(NB_Zone_Grenze nbZoneGrenze,
		Stell_Bereich placeArea) {
		return nbZoneGrenze.markanterPunkt.markanteStelle.punktObjektTOPKante.
			exists [ potk |
				placeArea.bereichObjektTeilbereich.exists[it.contains(potk)]
			]
	}

	static def dispatch boolean isInPlaceArea(Fla_Zwieschutz fla,
		Stell_Bereich placeArea) {
		return fla.zwieschutzweiche?.IDStellelement?.value.
			isInPlaceArea(placeArea)
	}

	static def dispatch boolean isInPlaceArea(
		Gleis_Bezeichnung gleisBezeichnung, Stell_Bereich placeArea) {
		return placeArea.bereichObjektTeilbereich.exists [ botb |
			gleisBezeichnung.bereichObjektTeilbereich.exists[intersects(botb)]
		]
	}

	static def dispatch boolean isInPlaceArea(NB_Zone nbZone,
		Stell_Bereich placeArea) {
		return nbZone.container.NBZoneGrenze.filterNull.filter [
			IDNBZone.value === nbZone
		].exists[isNBZoneGrenzeInPlaceArea(placeArea)]
	}

	static def dispatch boolean isInPlaceArea(Uebertragungsweg uebertragungsweg,
		Stell_Bereich placeArea) {
		return uebertragungsweg.IDUebertragungswegVon?.value.
			isInPlaceArea(placeArea) || uebertragungsweg.IDAnhangUeWegNach?.map [
			value
		].filterNull.exists[isInPlaceArea(placeArea)]

	}

	static def dispatch boolean isInPlaceArea(Bedien_Bezirk bedienBezirk,
		Stell_Bereich placeArea) {
		return bedienBezirk.container.ESTWZentraleinheit.filterNull.filter [
			bedienBezirkVirtuell === bedienBezirk ||
				bedienBezirkZentral === bedienBezirk
		].exists[isInPlaceArea(placeArea)]
	}

	static def dispatch boolean isInPlaceArea(Bedien_Zentrale bedienZentral,
		Stell_Bereich placeArea) {
		return bedienZentral.container.ESTWZentraleinheit.filterNull.filter [
			bedienBezirkVirtuell?.bedienZentrale === bedienZentral ||
				bedienBezirkZentral?.bedienZentrale === bedienZentral
		].exists[isInPlaceArea(placeArea)]
	}

	static def dispatch boolean isInPlaceArea(ZN_ZBS znZBS,
		Stell_Bereich placeArea) {
		return znZBS.IDESTWZentraleinheit?.value.isInPlaceArea(placeArea)
	}

	static def dispatch boolean isInPlaceArea(Stellelement stellElement,
		Stell_Bereich placeArea) {
		return stellElement.IDInformation?.value ===
			placeArea.aussenElementAnsteuerung
	}

	static def dispatch boolean isInPlaceArea(Block_Element blockElement,
		Stell_Bereich placeArea) {
		val blockAs = blockElement.blockAnlagenStart.map[IDBlockElementB.value]
		val blockBs = blockElement.blockAnlagenZiel.map[IDBlockElementA.value]
		return #[blockAs, blockBs].flatten.map[IDSignal?.value].filterNull.
			filter [
				signalFiktiv?.fiktivesSignalFunktion.exists [ funktion |
					funktion.wert === ENUMFiktivesSignalFunktion.
						ENUM_FIKTIVES_SIGNAL_FUNKTION_ZUG_ZIEL_STRECKE
				]
			].flatMap[punktObjektTOPKante].exists [ potk |
				placeArea.bereichObjektTeilbereich.exists[contains(potk)]
			]
	}
}
