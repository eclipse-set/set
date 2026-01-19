/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.container

import org.eclipse.core.runtime.Assert
import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stellelement
import org.eclipse.set.model.planpro.Ansteuerung_Element.Unterbringung
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Anlage
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Bedien_Anzeige_Element
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Einschaltung
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Einschaltung_Zuordnung
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Gleisbezogener_Gefahrraum
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk
import org.eclipse.set.model.planpro.Bedienung.Bedien_Anrueckabschnitt
import org.eclipse.set.model.planpro.Bedienung.Bedien_Anzeige_Element
import org.eclipse.set.model.planpro.Bedienung.Bedien_Einrichtung_Oertlich
import org.eclipse.set.model.planpro.Bedienung.Bedien_Platz
import org.eclipse.set.model.planpro.Bedienung.Bedien_Standort
import org.eclipse.set.model.planpro.Block.Block_Anlage
import org.eclipse.set.model.planpro.Block.Block_Strecke
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Abhaengigkeit
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Aneinander
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Aneinander_Zuordnung
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg_W_Kr
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Fahrweg
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Nichthaltfall
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Rangier_Fla_Zuordnung
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Signalisierung
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Umfahrpunkt
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier
import org.eclipse.set.model.planpro.Fahrstrasse.Markanter_Punkt
import org.eclipse.set.model.planpro.Flankenschutz.Fla_Freimelde_Zuordnung
import org.eclipse.set.model.planpro.Flankenschutz.Fla_Schutz
import org.eclipse.set.model.planpro.Flankenschutz.Fla_Zwieschutz
import org.eclipse.set.model.planpro.Geodaten.GEO_Kante
import org.eclipse.set.model.planpro.Geodaten.GEO_Knoten
import org.eclipse.set.model.planpro.Geodaten.GEO_Punkt
import org.eclipse.set.model.planpro.Geodaten.Oertlichkeit
import org.eclipse.set.model.planpro.Geodaten.Strecke
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten
import org.eclipse.set.model.planpro.Geodaten.Ueberhoehung
import org.eclipse.set.model.planpro.Gleis.Gleis_Abschnitt
import org.eclipse.set.model.planpro.Gleis.Gleis_Art
import org.eclipse.set.model.planpro.Gleis.Gleis_Bezeichnung
import org.eclipse.set.model.planpro.Gleis.Gleis_Lichtraum
import org.eclipse.set.model.planpro.Gleis.Gleis_Schaltgruppe
import org.eclipse.set.model.planpro.Nahbedienung.NB
import org.eclipse.set.model.planpro.Nahbedienung.NB_Bedien_Anzeige_Element
import org.eclipse.set.model.planpro.Nahbedienung.NB_Zone
import org.eclipse.set.model.planpro.Nahbedienung.NB_Zone_Element
import org.eclipse.set.model.planpro.Nahbedienung.NB_Zone_Grenze
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente
import org.eclipse.set.model.planpro.Ortung.Schaltmittel_Zuordnung
import org.eclipse.set.model.planpro.Ortung.Zugeinwirkung
import org.eclipse.set.model.planpro.PZB.PZB_Element_Zuordnung
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup
import org.eclipse.set.model.planpro.PlanPro.LST_Zustand
import org.eclipse.set.model.planpro.Regelzeichnung.Regelzeichnung
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schlosskombination
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schluessel
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schluesselsperre
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung
import org.eclipse.set.model.planpro.Signale.Signal_Rahmen
import org.eclipse.set.model.planpro.Signale.Signal_Signalbegriff
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.Gleis_Abschluss
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Anlage
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import org.eclipse.set.model.planpro.Basisobjekte.Anhang
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Anlage_Strasse
import org.eclipse.set.model.planpro.Medien_und_Trassen.Kabel
import org.eclipse.set.model.planpro.Medien_und_Trassen.Kabel_Verteilpunkt
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.LEU_Anlage
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.LEU_Modul
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schloss
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.LEU_Schaltkasten
import org.eclipse.set.model.planpro.Basisobjekte.Lieferobjekt
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.Luft_Telegramm
import org.eclipse.set.model.planpro.PZB.PZB_Element
import org.eclipse.set.model.planpro.PZB.PZB_Zuordnung_Signal
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.Prog_Datei_Gruppe
import org.eclipse.set.model.planpro.Basisobjekte.Proxy_Objekt
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.RBC
import org.eclipse.set.model.planpro.Regelzeichnung.Regelzeichnung_Parameter
import org.eclipse.set.model.planpro.Bahnuebergang.Schaltmittel_Fstr_Zuordnung
import org.eclipse.set.model.planpro.Bahnuebergang.Schrankenantrieb
import org.eclipse.set.model.planpro.Signale.Signal_Fank_Zuordnung
import org.eclipse.set.model.planpro.Fahrstrasse.Sonstiger_Punkt
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Geodaten.Strecke_Punkt
import org.eclipse.set.model.planpro.Ansteuerung_Element.Technik_Standort
import org.eclipse.set.model.planpro.Geodaten.Technischer_Bereich
import org.eclipse.set.model.planpro.Geodaten.Technischer_Punkt
import org.eclipse.set.model.planpro.Medien_und_Trassen.Trasse_Kante
import org.eclipse.set.model.planpro.Medien_und_Trassen.Trasse_Knoten
import org.eclipse.set.model.planpro.Geodaten.Ueberhoehungslinie
import org.eclipse.set.model.planpro.Ansteuerung_Element.Uebertragungsweg
import org.eclipse.set.model.planpro.Bahnuebergang.Verkehrszeichen
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.Weichenlaufkette
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.Weichenlaufkette_Zuordnung
import org.eclipse.set.model.planpro.Zuglenkung.ZL
import org.eclipse.set.model.planpro.Zuglenkung.ZL_DLP_Abschnitt
import org.eclipse.set.model.planpro.Zuglenkung.ZL_DLP_Fstr
import org.eclipse.set.model.planpro.Zuglenkung.ZL_Fstr
import org.eclipse.set.model.planpro.Zuglenkung.ZL_Fstr_Anstoss
import org.eclipse.set.model.planpro.Zuglenkung.ZL_Signalgruppe
import org.eclipse.set.model.planpro.Zuglenkung.ZL_Signalgruppe_Zuordnung
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZLV_Bus
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZLV_Bus_US_Zuordnung
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZN
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZN_Akustik
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZN_Fortschalt_Kriterium
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZN_Anzeigefeld
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZN_Telegramm_84_Zuordnung
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZN_Telegramm_85_Zuordnung
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ZUB_Streckeneigenschaft
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ZUB_Bereichsgrenze
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZN_ZBS
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZN_Unterstation
import org.eclipse.set.model.planpro.Geodaten.Hoehenpunkt
import org.eclipse.set.model.planpro.Geodaten.Hoehenlinie
import org.eclipse.set.model.planpro.Gleis.Gleis_Fahrbahn
import org.eclipse.set.model.planpro.Gleis.Gleis_Baubereich
import org.eclipse.set.model.planpro.Geodaten.Geschwindigkeitsprofil
import org.eclipse.set.model.planpro.Bahnuebergang.GFR_Tripelspiegel
import org.eclipse.set.model.planpro.Bahnuebergang.GFR_Element
import org.eclipse.set.model.planpro.Bahnuebergang.GFR_Anlage
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.Fachtelegramm
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.FT_Fahrweg_Teil
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.FT_Anschaltbedingung
import org.eclipse.set.model.planpro.Ortung.FMA_Element
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.EV_Modul
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_W_Kr
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_Signal
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_Knoten
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_Kante
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.Datenpunkt_Link
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.Datenpunkt
import org.eclipse.set.model.planpro.Block.Block_Element
import org.eclipse.set.model.planpro.Bedienung.Bedien_Zentrale
import org.eclipse.set.model.planpro.Bedienung.Bedien_Oertlichkeit
import org.eclipse.set.model.planpro.Bedienung.Bedien_Oberflaeche_Bild
import org.eclipse.set.model.planpro.Bedienung.Bedien_Oberflaeche
import org.eclipse.set.model.planpro.Bedienung.Bedien_GBT
import org.eclipse.set.model.planpro.Bedienung.Bedien_Bezirk
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.Balise
import org.eclipse.set.model.planpro.Bahnsteig.Bahnsteig_Zugang
import org.eclipse.set.model.planpro.Bahnsteig.Bahnsteig_Kante
import org.eclipse.set.model.planpro.Bahnsteig.Bahnsteig_Dach
import org.eclipse.set.model.planpro.Bahnsteig.Bahnsteig_Anlage
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_WS_Fstr_Zuordnung
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Spezifisches_Signal
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Schnittstelle
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Kreuzungsplan
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Kante
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Gefahrraum_Eckpunkt
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Deckendes_Signal_Zuordnung
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Ausschaltung
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Anlage_V
import org.eclipse.emf.ecore.EObject
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.emf.ecore.EClass
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt

/**
 * Read-only metaobject which provides a similar API to {@link Container_AttributeGroup},
 * while using multiple different {@link Container_AttributeGroup} objects as a data source. 
 * 
 * @author Stuecker
 */
class MultiContainer_AttributeGroup {
	Iterable<Container_AttributeGroup> containers;
	val String cacheString

	/**
	 * Wraps multiple Container_AttributeGroup instances 
	 * 
	 * @param containers an iterable of containers to use as data source
	 */
	new(Iterable<Container_AttributeGroup> containers) {
		Assert.isTrue(!containers.empty)
		this.containers = containers
		cacheString = "multi/" + containers.map [
			'''«(eContainer as LST_Zustand).identitaet.wert»/«eContainer.hashCode» '''
			
		].join("/")
	}

	/**
	 * Wraps a single Container_AttributeGroup
	 * 
	 * @param container a single container
	 */
	new(Container_AttributeGroup container) {
		this(#[container])
	}

	/**
	 * Returns a String that can be used to cache results 
	 * 
	 * @returns a unique caching string 
	 */
	def String getCacheString() {
		return cacheString
	}

	/**
	 * Returns the LST_Zustand of the first container
	 * 
	 * @returns the first LST_Zustand
	 */
	def LST_Zustand getFirstLSTZustand() {
		return containers.get(0).eContainer as LST_Zustand;
	}

	/**
	 * Returns the EClass of the wrapped containers
	 * 
	 * @return the eClass of the first container
	 */
	def EClass getContainerEClass() {
		return containers.get(0).eClass
	}

	/**
	 * Returns all contents, similar to EObject.eContents
	 * 
	 * @return all contained objects
	 */
	def Iterable<EObject> getContents() {
		return containers.flatMap[eContents]
	}

	/**
	 * Returns the contained containers
	 * 
	 * @returns an iterable of containers
	 */
	def Iterable<Container_AttributeGroup> getContainers() {
		return containers;
	}

	/**
	 * Returns all Ur_Objekt entities
	 * 
	 * @return all Ur_Objekt instances
	 */
	def Iterable<Ur_Objekt> getUrObjekt() {
		return containers.flatMap[eAllContents.filter(Ur_Objekt).toList]
	}

	/**
	 * Returns all entities
	 * 
	 * @return all EObject instances
	 */
	def Iterable<EObject> getAllContents() {
		return containers.flatMap[eAllContents.toList]
	}

	/**
	 * Returns all Basis_Objekt entities
	 * 
	 * @return all Basis_Objekt instances
	 */
	def Iterable<Basis_Objekt> getBasisObjekt() {
		return containers.flatMap[eContents.filter(Basis_Objekt)]
	}

	/**
	 * Returns all Bereich_Objekt entities
	 * 
	 * @return all Bereich_Objekt instances
	 */
	def Iterable<Bereich_Objekt> getBereichObjekt() {
		return containers.flatMap[eContents.filter(Bereich_Objekt)]
	}

	/**
	 * @return all Punkt_Objekt instances 
	 */
	def Iterable<Punkt_Objekt> getPunktObjekts() {
		return containers.flatMap[eAllContents.filter(Punkt_Objekt).toList]
	}

	/**
	 * Returns the size of the combined containers
	 * 
	 * @return the number of LST_Objekt in all subcontainers
	 */
	def int getSize() {
		return containers.map[eContents.size].reduce[sizeA, sizeB|sizeA + sizeB]
	}

	/**
	 * Returns all entities of a given class type 
	 * 
	 * @return all entities of a given class type
	 */
	def <T> Iterable<T> get(Class<T> clazz) {
		return containers.flatMap[eContents.filter(clazz)]
	}

	/* Generated methods follow. Each method simply forwards the call to all contained LST_Zustand objects
	 * and constructs an Iterable from the results. This should reflect the API of Container_AttributeGroup
	 * excluding the EObject functions
	 */
	def Iterable<GEO_Knoten> getGEOKnoten() {
		return containers.flatMap[GEOKnoten]
	}

	def Iterable<TOP_Kante> getTOPKante() {
		return containers.flatMap[TOPKante]
	}

	def Iterable<Ueberhoehungslinie> getUeberhoehungslinie() {
		return containers.flatMap[ueberhoehungslinie]
	}

	def Iterable<W_Kr_Gsp_Komponente> getWKrGspKomponente() {
		return containers.flatMap[WKrGspKomponente]
	}

	def Iterable<Bearbeitungsvermerk> getBearbeitungsvermerk() {
		return containers.flatMap[bearbeitungsvermerk]
	}

	def Iterable<Signal> getSignal() {
		return containers.flatMap[signal]
	}

	def Iterable<BUE_Anlage> getBUEAnlage() {
		return containers.flatMap[BUEAnlage]
	}

	def Iterable<Fstr_Umfahrpunkt> getFstrUmfahrpunkt() {
		return containers.flatMap[fstrUmfahrpunkt]
	}

	def Iterable<Aussenelementansteuerung> getAussenelementansteuerung() {
		return containers.flatMap[aussenelementansteuerung]
	}

	def Iterable<ESTW_Zentraleinheit> getESTWZentraleinheit() {
		return containers.flatMap[ESTWZentraleinheit]
	}

	def Iterable<Bedien_Anrueckabschnitt> getBedienAnrueckabschnitt() {
		return containers.flatMap[bedienAnrueckabschnitt]
	}

	def Iterable<Fla_Schutz> getFlaSchutz() {
		return containers.flatMap[flaSchutz]
	}

	def Iterable<Fla_Freimelde_Zuordnung> getFlaFreimeldeZuordnung() {
		return containers.flatMap[flaFreimeldeZuordnung]
	}

	def Iterable<W_Kr_Gsp_Element> getWKrGspElement() {
		return containers.flatMap[WKrGspElement]
	}

	def Iterable<NB_Zone_Grenze> getNBZoneGrenze() {
		return containers.flatMap[NBZoneGrenze]
	}

	def Iterable<Bedien_Anzeige_Element> getBedienAnzeigeElement() {
		return containers.flatMap[bedienAnzeigeElement]
	}

	def Iterable<Bedien_Einrichtung_Oertlich> getBedienEinrichtungOertlich() {
		return containers.flatMap[bedienEinrichtungOertlich]
	}

	def Iterable<NB_Bedien_Anzeige_Element> getNBBedienAnzeigeElement() {
		return containers.flatMap[NBBedienAnzeigeElement]
	}

	def Iterable<FMA_Anlage> getFMAAnlage() {
		return containers.flatMap[FMAAnlage]
	}

	def Iterable<Markanter_Punkt> getMarkanterPunkt() {
		return containers.flatMap[markanterPunkt]
	}

	def Iterable<Schaltmittel_Zuordnung> getSchaltmittelZuordnung() {
		return containers.flatMap[schaltmittelZuordnung]
	}

	def Iterable<Fstr_Zug_Rangier> getFstrZugRangier() {
		return containers.flatMap[fstrZugRangier]
	}

	def Iterable<Fstr_Aneinander> getFstrAneinander() {
		return containers.flatMap[fstrAneinander]
	}

	def Iterable<Gleis_Abschluss> getGleisAbschluss() {
		return containers.flatMap[gleisAbschluss]
	}

	def Iterable<Gleis_Abschnitt> getGleisAbschnitt() {
		return containers.flatMap[gleisAbschnitt]
	}

	def Iterable<Gleis_Art> getGleisArt() {
		return containers.flatMap[gleisArt]
	}

	def Iterable<Gleis_Lichtraum> getGleisLichtraum() {
		return containers.flatMap[gleisLichtraum]
	}

	def Iterable<Gleis_Schaltgruppe> getGleisSchaltgruppe() {
		return containers.flatMap[gleisSchaltgruppe]
	}

	def Iterable<Bedien_Platz> getBedienPlatz() {
		return containers.flatMap[bedienPlatz]
	}

	def Iterable<Bedien_Standort> getBedienStandort() {
		return containers.flatMap[bedienStandort]
	}

	def Iterable<Block_Anlage> getBlockAnlage() {
		return containers.flatMap[blockAnlage]
	}

	def Iterable<Block_Strecke> getBlockStrecke() {
		return containers.flatMap[blockStrecke]
	}

	def Iterable<BUE_Bedien_Anzeige_Element> getBUEBedienAnzeigeElement() {
		return containers.flatMap[BUEBedienAnzeigeElement]
	}

	def Iterable<BUE_Einschaltung> getBUEEinschaltung() {
		return containers.flatMap[BUEEinschaltung]
	}

	def Iterable<BUE_Einschaltung_Zuordnung> getBUEEinschaltungZuordnung() {
		return containers.flatMap[BUEEinschaltungZuordnung]
	}

	def Iterable<FMA_Komponente> getFMAKomponente() {
		return containers.flatMap[FMAKomponente]
	}

	def Iterable<Fstr_Abhaengigkeit> getFstrAbhaengigkeit() {
		return containers.flatMap[fstrAbhaengigkeit]
	}

	def Iterable<Fstr_Aneinander_Zuordnung> getFstrAneinanderZuordnung() {
		return containers.flatMap[fstrAneinanderZuordnung]
	}

	def Iterable<Fstr_DWeg> getFstrDWeg() {
		return containers.flatMap[fstrDWeg]
	}

	def Iterable<Fstr_DWeg_W_Kr> getFstrDWegWKr() {
		return containers.flatMap[fstrDWegWKr]
	}

	def Iterable<Fstr_Nichthaltfall> getFstrNichthaltfall() {
		return containers.flatMap[fstrNichthaltfall]
	}

	def Iterable<GEO_Kante> getGEOKante() {
		return containers.flatMap[GEOKante]
	}

	def Iterable<NB> getNB() {
		return containers.flatMap[NB]
	}

	def Iterable<Oertlichkeit> getOertlichkeit() {
		return containers.flatMap[oertlichkeit]
	}

	def Iterable<Regelzeichnung> getRegelzeichnung() {
		return containers.flatMap[regelzeichnung]
	}

	def Iterable<NB_Zone> getNBZone() {
		return containers.flatMap[NBZone]
	}

	def Iterable<NB_Zone_Element> getNBZoneElement() {
		return containers.flatMap[NBZoneElement]
	}

	def Iterable<Gleis_Bezeichnung> getGleisBezeichnung() {
		return containers.flatMap[gleisBezeichnung]
	}

	def Iterable<GEO_Punkt> getGEOPunkt() {
		return containers.flatMap[GEOPunkt]
	}

	def Iterable<BUE_Gleisbezogener_Gefahrraum> getBUEGleisbezogenerGefahrraum() {
		return containers.flatMap[BUEGleisbezogenerGefahrraum]
	}

	def Iterable<Fla_Zwieschutz> getFlaZwieschutz() {
		return containers.flatMap[flaZwieschutz]
	}

	def Iterable<Fstr_Fahrweg> getFstrFahrweg() {
		return containers.flatMap[fstrFahrweg]
	}

	def Iterable<Fstr_Rangier_Fla_Zuordnung> getFstrRangierFlaZuordnung() {
		return containers.flatMap[fstrRangierFlaZuordnung]
	}

	def Iterable<Fstr_Signalisierung> getFstrSignalisierung() {
		return containers.flatMap[fstrSignalisierung]
	}

	def Iterable<PZB_Element_Zuordnung> getPZBElementZuordnung() {
		return containers.flatMap[PZBElementZuordnung]
	}

	def Iterable<Schlosskombination> getSchlosskombination() {
		return containers.flatMap[schlosskombination]
	}

	def Iterable<Schluessel> getSchluessel() {
		return containers.flatMap[schluessel]
	}

	def Iterable<Schluesselsperre> getSchluesselsperre() {
		return containers.flatMap[schluesselsperre]
	}

	def Iterable<Signal_Befestigung> getSignalBefestigung() {
		return containers.flatMap[signalBefestigung]
	}

	def Iterable<Signal_Rahmen> getSignalRahmen() {
		return containers.flatMap[signalRahmen]
	}

	def Iterable<Signal_Signalbegriff> getSignalSignalbegriff() {
		return containers.flatMap[signalSignalbegriff]
	}

	def Iterable<Stellelement> getStellelement() {
		return containers.flatMap[stellelement]
	}

	def Iterable<Strecke> getStrecke() {
		return containers.flatMap[strecke]
	}

	def Iterable<Ueberhoehung> getUeberhoehung() {
		return containers.flatMap[ueberhoehung]
	}

	def Iterable<TOP_Knoten> getTOPKnoten() {
		return containers.flatMap[TOPKnoten]
	}

	def Iterable<Unterbringung> getUnterbringung() {
		return containers.flatMap[unterbringung]
	}

	def Iterable<W_Kr_Anlage> getWKrAnlage() {
		return containers.flatMap[WKrAnlage]
	}

	def Iterable<Zugeinwirkung> getZugeinwirkung() {
		return containers.flatMap[zugeinwirkung]
	}

	def Iterable<Anhang> getAnhang() {
		return containers.flatMap[getAnhang]
	}

	def Iterable<BUE_Anlage_Strasse> getBUEAnlageStrasse() {
		return containers.flatMap[getBUEAnlageStrasse]
	}

	def Iterable<BUE_Anlage_V> getBUEAnlageV() {
		return containers.flatMap[getBUEAnlageV]
	}

	def Iterable<BUE_Ausschaltung> getBUEAusschaltung() {
		return containers.flatMap[getBUEAusschaltung]
	}

	def Iterable<BUE_Deckendes_Signal_Zuordnung> getBUEDeckendesSignalZuordnung() {
		return containers.flatMap[getBUEDeckendesSignalZuordnung]
	}

	def Iterable<BUE_Gefahrraum_Eckpunkt> getBUEGefahrraumEckpunkt() {
		return containers.flatMap[getBUEGefahrraumEckpunkt]
	}

	def Iterable<BUE_Kante> getBUEKante() {
		return containers.flatMap[getBUEKante]
	}

	def Iterable<BUE_Kreuzungsplan> getBUEKreuzungsplan() {
		return containers.flatMap[getBUEKreuzungsplan]
	}

	def Iterable<BUE_Schnittstelle> getBUESchnittstelle() {
		return containers.flatMap[getBUESchnittstelle]
	}

	def Iterable<BUE_Spezifisches_Signal> getBUESpezifischesSignal() {
		return containers.flatMap[getBUESpezifischesSignal]
	}

	def Iterable<BUE_WS_Fstr_Zuordnung> getBUEWSFstrZuordnung() {
		return containers.flatMap[getBUEWSFstrZuordnung]
	}

	def Iterable<Bahnsteig_Anlage> getBahnsteigAnlage() {
		return containers.flatMap[getBahnsteigAnlage]
	}

	def Iterable<Bahnsteig_Dach> getBahnsteigDach() {
		return containers.flatMap[getBahnsteigDach]
	}

	def Iterable<Bahnsteig_Kante> getBahnsteigKante() {
		return containers.flatMap[getBahnsteigKante]
	}

	def Iterable<Bahnsteig_Zugang> getBahnsteigZugang() {
		return containers.flatMap[getBahnsteigZugang]
	}

	def Iterable<Balise> getBalise() {
		return containers.flatMap[getBalise]
	}

	def Iterable<Bedien_Bezirk> getBedienBezirk() {
		return containers.flatMap[getBedienBezirk]
	}

	def Iterable<Bedien_GBT> getBedienGBT() {
		return containers.flatMap[getBedienGBT]
	}

	def Iterable<Bedien_Oberflaeche> getBedienOberflaeche() {
		return containers.flatMap[getBedienOberflaeche]
	}

	def Iterable<Bedien_Oberflaeche_Bild> getBedienOberflaecheBild() {
		return containers.flatMap[getBedienOberflaecheBild]
	}

	def Iterable<Bedien_Oertlichkeit> getBedienOertlichkeit() {
		return containers.flatMap[getBedienOertlichkeit]
	}

	def Iterable<Bedien_Zentrale> getBedienZentrale() {
		return containers.flatMap[getBedienZentrale]
	}

	def Iterable<Block_Element> getBlockElement() {
		return containers.flatMap[getBlockElement]
	}

	def Iterable<Datenpunkt> getDatenpunkt() {
		return containers.flatMap[getDatenpunkt]
	}

	def Iterable<Datenpunkt_Link> getDatenpunktLink() {
		return containers.flatMap[getDatenpunktLink]
	}

	def Iterable<ETCS_Kante> getETCSKante() {
		return containers.flatMap[getETCSKante]
	}

	def Iterable<ETCS_Knoten> getETCSKnoten() {
		return containers.flatMap[getETCSKnoten]
	}

	def Iterable<ETCS_Signal> getETCSSignal() {
		return containers.flatMap[getETCSSignal]
	}

	def Iterable<ETCS_W_Kr> getETCSWKr() {
		return containers.flatMap[getETCSWKr]
	}

	def Iterable<EV_Modul> getEVModul() {
		return containers.flatMap[getEVModul]
	}

	def Iterable<FMA_Element> getFMAElement() {
		return containers.flatMap[getFMAElement]
	}

	def Iterable<FT_Anschaltbedingung> getFTAnschaltbedingung() {
		return containers.flatMap[getFTAnschaltbedingung]
	}

	def Iterable<FT_Fahrweg_Teil> getFTFahrwegTeil() {
		return containers.flatMap[getFTFahrwegTeil]
	}

	def Iterable<Fachtelegramm> getFachtelegramm() {
		return containers.flatMap[getFachtelegramm]
	}

	def Iterable<GFR_Anlage> getGFRAnlage() {
		return containers.flatMap[getGFRAnlage]
	}

	def Iterable<GFR_Element> getGFRElement() {
		return containers.flatMap[getGFRElement]
	}

	def Iterable<GFR_Tripelspiegel> getGFRTripelspiegel() {
		return containers.flatMap[getGFRTripelspiegel]
	}

	def Iterable<Geschwindigkeitsprofil> getGeschwindigkeitsprofil() {
		return containers.flatMap[getGeschwindigkeitsprofil]
	}

	def Iterable<Gleis_Baubereich> getGleisBaubereich() {
		return containers.flatMap[getGleisBaubereich]
	}

	def Iterable<Gleis_Fahrbahn> getGleisFahrbahn() {
		return containers.flatMap[getGleisFahrbahn]
	}

	def Iterable<Hoehenlinie> getHoehenlinie() {
		return containers.flatMap[getHoehenlinie]
	}

	def Iterable<Hoehenpunkt> getHoehenpunkt() {
		return containers.flatMap[getHoehenpunkt]
	}

	def Iterable<Kabel> getKabel() {
		return containers.flatMap[getKabel]
	}

	def Iterable<Kabel_Verteilpunkt> getKabelVerteilpunkt() {
		return containers.flatMap[getKabelVerteilpunkt]
	}

	def Iterable<LEU_Anlage> getLEUAnlage() {
		return containers.flatMap[getLEUAnlage]
	}

	def Iterable<LEU_Modul> getLEUModul() {
		return containers.flatMap[getLEUModul]
	}

	def Iterable<LEU_Schaltkasten> getLEUSchaltkasten() {
		return containers.flatMap[getLEUSchaltkasten]
	}

	def Iterable<Lieferobjekt> getLieferobjekt() {
		return containers.flatMap[getLieferobjekt]
	}

	def Iterable<Luft_Telegramm> getLuftTelegramm() {
		return containers.flatMap[getLuftTelegramm]
	}

	def Iterable<PZB_Element> getPZBElement() {
		return containers.flatMap[getPZBElement]
	}

	def Iterable<PZB_Zuordnung_Signal> getPZBZuordnungSignal() {
		return containers.flatMap[getPZBZuordnungSignal]
	}

	def Iterable<Prog_Datei_Gruppe> getProgDateiGruppe() {
		return containers.flatMap[getProgDateiGruppe]
	}

	def Iterable<Proxy_Objekt> getProxyObjekt() {
		return containers.flatMap[getProxyObjekt]
	}

	def Iterable<RBC> getRBC() {
		return containers.flatMap[getRBC]
	}

	def Iterable<Regelzeichnung_Parameter> getRegelzeichnungParameter() {
		return containers.flatMap[getRegelzeichnungParameter]
	}

	def Iterable<Schaltmittel_Fstr_Zuordnung> getSchaltmittelFstrZuordnung() {
		return containers.flatMap[getSchaltmittelFstrZuordnung]
	}

	def Iterable<Schloss> getSchloss() {
		return containers.flatMap[getSchloss]
	}

	def Iterable<Schrankenantrieb> getSchrankenantrieb() {
		return containers.flatMap[getSchrankenantrieb]
	}

	def Iterable<Signal_Fank_Zuordnung> getSignalFankZuordnung() {
		return containers.flatMap[getSignalFankZuordnung]
	}

	def Iterable<Sonstiger_Punkt> getSonstigerPunkt() {
		return containers.flatMap[getSonstigerPunkt]
	}

	def Iterable<Stell_Bereich> getStellBereich() {
		return containers.flatMap[getStellBereich]
	}

	def Iterable<Strecke_Punkt> getStreckePunkt() {
		return containers.flatMap[getStreckePunkt]
	}

	def Iterable<Technik_Standort> getTechnikStandort() {
		return containers.flatMap[getTechnikStandort]
	}

	def Iterable<Technischer_Bereich> getTechnischerBereich() {
		return containers.flatMap[getTechnischerBereich]
	}

	def Iterable<Technischer_Punkt> getTechnischerPunkt() {
		return containers.flatMap[getTechnischerPunkt]
	}

	def Iterable<Trasse_Kante> getTrasseKante() {
		return containers.flatMap[getTrasseKante]
	}

	def Iterable<Trasse_Knoten> getTrasseKnoten() {
		return containers.flatMap[getTrasseKnoten]
	}

	def Iterable<Uebertragungsweg> getUebertragungsweg() {
		return containers.flatMap[getUebertragungsweg]
	}

	def Iterable<Verkehrszeichen> getVerkehrszeichen() {
		return containers.flatMap[getVerkehrszeichen]
	}

	def Iterable<Weichenlaufkette> getWeichenlaufkette() {
		return containers.flatMap[getWeichenlaufkette]
	}

	def Iterable<Weichenlaufkette_Zuordnung> getWeichenlaufketteZuordnung() {
		return containers.flatMap[getWeichenlaufketteZuordnung]
	}

	def Iterable<ZL> getZL() {
		return containers.flatMap[getZL]
	}

	def Iterable<ZL_DLP_Abschnitt> getZLDLPAbschnitt() {
		return containers.flatMap[getZLDLPAbschnitt]
	}

	def Iterable<ZL_DLP_Fstr> getZLDLPFstr() {
		return containers.flatMap[getZLDLPFstr]
	}

	def Iterable<ZL_Fstr> getZLFstr() {
		return containers.flatMap[getZLFstr]
	}

	def Iterable<ZL_Fstr_Anstoss> getZLFstrAnstoss() {
		return containers.flatMap[getZLFstrAnstoss]
	}

	def Iterable<ZL_Signalgruppe> getZLSignalgruppe() {
		return containers.flatMap[getZLSignalgruppe]
	}

	def Iterable<ZL_Signalgruppe_Zuordnung> getZLSignalgruppeZuordnung() {
		return containers.flatMap[getZLSignalgruppeZuordnung]
	}

	def Iterable<ZLV_Bus> getZLVBus() {
		return containers.flatMap[getZLVBus]
	}

	def Iterable<ZLV_Bus_US_Zuordnung> getZLVBusUSZuordnung() {
		return containers.flatMap[getZLVBusUSZuordnung]
	}

	def Iterable<ZN> getZN() {
		return containers.flatMap[getZN]
	}

	def Iterable<ZN_Akustik> getZNAkustik() {
		return containers.flatMap[getZNAkustik]
	}

	def Iterable<ZN_Anzeigefeld> getZNAnzeigefeld() {
		return containers.flatMap[getZNAnzeigefeld]
	}

	def Iterable<ZN_Fortschalt_Kriterium> getZNFortschaltKriterium() {
		return containers.flatMap[getZNFortschaltKriterium]
	}

	def Iterable<ZN_Telegramm_84_Zuordnung> getZNTelegramm84Zuordnung() {
		return containers.flatMap[getZNTelegramm84Zuordnung]
	}

	def Iterable<ZN_Telegramm_85_Zuordnung> getZNTelegramm85Zuordnung() {
		return containers.flatMap[getZNTelegramm85Zuordnung]
	}

	def Iterable<ZN_Unterstation> getZNUnterstation() {
		return containers.flatMap[getZNUnterstation]
	}

	def Iterable<ZN_ZBS> getZNZBS() {
		return containers.flatMap[getZNZBS]
	}

	def Iterable<ZUB_Bereichsgrenze> getZUBBereichsgrenze() {
		return containers.flatMap[getZUBBereichsgrenze]
	}

	def Iterable<ZUB_Streckeneigenschaft> getZUBStreckeneigenschaft() {
		return containers.flatMap[getZUBStreckeneigenschaft]
	}
}
