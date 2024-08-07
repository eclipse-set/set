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
import org.eclipse.set.core.services.Services
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt
import org.eclipse.set.model.planpro.Ortung.Zugeinwirkung
import org.eclipse.set.model.planpro.PZB.PZB_Element
import org.eclipse.set.model.planpro.PlanPro.LST_Zustand
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle
import org.eclipse.set.model.planpro.Signale.ENUMFiktivesSignalFunktion
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZN_ZBS
import org.eclipse.set.ppmodel.extensions.utils.TopGraph

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
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StellBereichExtensions.*

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

	def static <T extends Basis_Objekt> Iterable<T> filterObjectsInControlArea(
		Iterable<T> objects, Stell_Bereich area) {
		if (area === null) {
			return objects
		}

		return objects.filter[area.isInControlArea(it)]
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
