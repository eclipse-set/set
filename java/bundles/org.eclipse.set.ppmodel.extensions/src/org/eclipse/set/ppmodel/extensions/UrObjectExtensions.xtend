/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

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
		val estwZentrals = area?.aussenElementAnsteuerung?.ESTWZentraleinheits
		return estwZentrals.exists[it === object];
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

	private def static dispatch boolean isInControlArea(Zugeinwirkung zugeinwirkung,
		Stell_Bereich area) {
		val schaltmittle = zugeinwirkung.container.schaltmittelZuordnung.filter [
			IDSchalter?.value instanceof Zugeinwirkung
		].filter[it === zugeinwirkung]

		return schaltmittle.map[IDSchalter?.value].exists [
			it instanceof Block_Element || it instanceof Fstr_Fahrweg
		] && zugeinwirkung.punktObjektTOPKante.exists[area.contains(it)]
	}

	private def static dispatch boolean isInControlArea(PZB_Element object,
		Stell_Bereich controlArea) {
		val potk = object.PZBElementBezugspunkt.filter(Signal).filter [
			signalReal !== null && signalReal.signalRealAktiv === null
		].flatMap [
			punktObjektTOPKante
		]
		return !object.stellelements.filter [
			IDInformation?.value === controlArea.aussenElementAnsteuerung
		].nullOrEmpty && controlArea.bereichObjektTeilbereich.exists [ botb |
			potk.exists[botb.contains(it)]
		]
	}

	static final double tolerantDistance = 1000

	private def static dispatch boolean isInControlArea(Signal signal,
		Stell_Bereich controlArea) {
		val firstcondition = signal.stellelement.IDInformation ===
			controlArea.IDAussenelementansteuerung ||
			((signal.signalFiktiv !== null || signal.signalReal !== null) &&
				signal.punktObjektTOPKante.exists [ potk |
					controlArea.bereichObjektTeilbereich.exists[it.contains(potk)]
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
				controlArea.contains(it, tolerantDistance)
			]) {
			val topGraph = new TopGraph(signal.container.TOPKante)
			val areaTopKante = controlArea.bereichObjektTeilbereich.map[topKante].
				filter[!signal.topKanten.contains(it)]
			return isTargetSignal === !areaTopKante.forall [ topKante |
				topGraph.isInWirkrichtungOfSignal(signal, topKante)
			]
		}
		return false
	}

	private def static dispatch boolean isInControlArea(Technik_Standort standort,
		Stell_Bereich controlArea) {
		return controlArea.technikStandorts.exists[it === standort]
	}

	private def static dispatch boolean isInControlArea(Bedien_Standort standort,
		Stell_Bereich controlArea) {
		return controlArea.technikStandorts.flatMap[IDBedienStandort.map[value]].
			filterNull.exists[it === standort]
	}

	private def static dispatch boolean isInControlArea(W_Kr_Gsp_Element gspElement,
		Stell_Bereich controlArea) {
		return gspElement.IDStellelement?.value.IDInformation?.value ===
			controlArea.aussenElementAnsteuerung
	}

	private def static dispatch boolean isInControlArea(Fstr_Aneinander fstrAneinander,
		Stell_Bereich controlArea) {
		val areaStellelements = controlArea.aussenElementAnsteuerung.stellelements
		val fstrFarhwegs = fstrAneinander.container.fstrFahrweg.filter [ fstr |
			areaStellelements.exists[fstr?.IDStart?.value?.stellelement === it]
		]
		return fstrAneinander.zuordnungen.map[fstrZugRangier].map[fstrFahrweg].
			exists [ fstr |
				fstrFarhwegs.forall[IDZiel?.wert !== fstr?.IDStart?.wert]
			]
	}

	private def static dispatch boolean isInControlArea(Fstr_DWeg fstrDWeg,
		Stell_Bereich controlArea) {
		return fstrDWeg.IDFstrFahrweg?.value?.IDStart?.value?.stellelement.
			isInControlArea(controlArea)
	}

	private def static dispatch boolean isInControlArea(Fstr_Zug_Rangier fstrZugRangier,
		Stell_Bereich controlArea) {

		if (fstrZugRangier.isR) {
			return fstrZugRangier.isRangierStrInPlaceArea(controlArea)
		}

		if (isZ(fstrZugRangier.fstrZug?.fstrZugArt)) {
			return fstrZugRangier.isZugStrInPlaceArea(controlArea)
		}

		if (fstrZugRangier.fstrZug?.fstrZugArt.wert ===
			ENUMFstrZugArt.ENUM_FSTR_ZUG_ART_B) {
			// TODO
		}
		return true
	}

	private def static boolean isRangierStrInPlaceArea(
		Fstr_Zug_Rangier fstrZugRangier, Stell_Bereich controlArea) {
		if (fstrZugRangier.fstrRangier === null) {
			return false
		}
		val startSignal = fstrZugRangier.IDFstrFahrweg?.value?.IDStart?.value
		if (startSignal.signalReal !== null) {
			return startSignal.stellelement.isInControlArea(controlArea)
		}

		if (startSignal.signalFiktiv !== null) {
			return startSignal.punktObjektTOPKante.exists [ potk |
				controlArea.bereichObjektTeilbereich.exists[it.contains(potk)]
			]
		}
		return true
	}

	private def static boolean isZugStrInPlaceArea(
		Fstr_Zug_Rangier fstrZugRangier, Stell_Bereich controlArea) {
		if (fstrZugRangier.fstrZug === null &&
			fstrZugRangier.fstrMittel === null) {
			return false
		}
		val startSignal = fstrZugRangier.IDFstrFahrweg?.value?.IDStart?.value
		if (startSignal.signalReal !== null) {
			return startSignal.stellelement.isInControlArea(controlArea)
		}

		if (startSignal.signalFiktiv !== null) {
			return startSignal.punktObjektTOPKante.exists [ potk |
				controlArea.bereichObjektTeilbereich.exists[it.contains(potk)]
			]
		}
		return true
	}

	private static def dispatch boolean isInControlArea(Fla_Schutz fla,
		Stell_Bereich controlArea) {
		val anforderer = fla.anforderer
		if (anforderer instanceof W_Kr_Gsp_Element) {
			return anforderer.IDStellelement?.value.isInControlArea(controlArea)
		}

		if (anforderer instanceof NB_Zone_Grenze) {
			return anforderer.isNBZoneGrenzeInPlaceArea(controlArea)
		}

		throw new IllegalArgumentException()
	}

	static def boolean isNBZoneGrenzeInPlaceArea(NB_Zone_Grenze nbZoneGrenze,
		Stell_Bereich controlArea) {
		return nbZoneGrenze.markanterPunkt.markanteStelle.punktObjektTOPKante.
			exists [ potk |
				controlArea.bereichObjektTeilbereich.exists[it.contains(potk)]
			]
	}

	private static def dispatch boolean isInControlArea(Fla_Zwieschutz fla,
		Stell_Bereich controlArea) {
		return fla.zwieschutzweiche?.IDStellelement?.value.
			isInControlArea(controlArea)
	}

	private static def dispatch boolean isInControlArea(
		Gleis_Bezeichnung gleisBezeichnung, Stell_Bereich controlArea) {
		return controlArea.bereichObjektTeilbereich.exists [ botb |
			gleisBezeichnung.bereichObjektTeilbereich.exists[intersects(botb)]
		]
	}

	private static def dispatch boolean isInControlArea(NB_Zone nbZone,
		Stell_Bereich controlArea) {
		return nbZone.container.NBZoneGrenze.filterNull.filter [
			IDNBZone.value === nbZone
		].exists[isNBZoneGrenzeInPlaceArea(controlArea)]
	}

	private static def dispatch boolean isInControlArea(Uebertragungsweg uebertragungsweg,
		Stell_Bereich controlArea) {
		return uebertragungsweg.IDUebertragungswegVon?.value.
			isInControlArea(controlArea) || uebertragungsweg.IDAnhangUeWegNach?.map [
			value
		].filterNull.exists[isInControlArea(controlArea)]

	}

	private static def dispatch boolean isInControlArea(Bedien_Bezirk bedienBezirk,
		Stell_Bereich controlArea) {
		return bedienBezirk.container.ESTWZentraleinheit.filterNull.filter [
			bedienBezirkVirtuell === bedienBezirk ||
				bedienBezirkZentral === bedienBezirk
		].exists[isInControlArea(controlArea)]
	}

	private static def dispatch boolean isInControlArea(Bedien_Zentrale bedienZentral,
		Stell_Bereich controlArea) {
		return bedienZentral.container.ESTWZentraleinheit.filterNull.filter [
			bedienBezirkVirtuell?.bedienZentrale === bedienZentral ||
				bedienBezirkZentral?.bedienZentrale === bedienZentral
		].exists[isInControlArea(controlArea)]
	}

	private static def dispatch boolean isInControlArea(ZN_ZBS znZBS,
		Stell_Bereich controlArea) {
		return znZBS.IDESTWZentraleinheit?.value.isInControlArea(controlArea)
	}

	private static def dispatch boolean isInControlArea(Stellelement stellElement,
		Stell_Bereich controlArea) {
		return stellElement.IDInformation?.value ===
			controlArea.aussenElementAnsteuerung
	}

	private static def dispatch boolean isInControlArea(Block_Element blockElement,
		Stell_Bereich controlArea) {
		val blockAs = blockElement.blockAnlagenStart.map[IDBlockElementB.value]
		val blockBs = blockElement.blockAnlagenZiel.map[IDBlockElementA.value]
		return #[blockAs, blockBs].flatten.map[IDSignal?.value].filterNull.
			filter [
				signalFiktiv?.fiktivesSignalFunktion.exists [ funktion |
					funktion.wert === ENUMFiktivesSignalFunktion.
						ENUM_FIKTIVES_SIGNAL_FUNKTION_ZUG_ZIEL_STRECKE
				]
			].flatMap[punktObjektTOPKante].exists [ potk |
				controlArea.bereichObjektTeilbereich.exists[contains(potk)]
			]
	}
}
