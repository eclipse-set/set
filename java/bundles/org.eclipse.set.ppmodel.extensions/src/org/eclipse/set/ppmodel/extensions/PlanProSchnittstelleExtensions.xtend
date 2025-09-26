/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.nio.file.Path
import java.nio.file.Paths
import java.time.format.DateTimeFormatter
import java.util.Collections
import java.util.GregorianCalendar
import java.util.List
import java.util.Optional
import java.util.Set
import javax.xml.datatype.DatatypeConfigurationException
import javax.xml.datatype.DatatypeFactory
import javax.xml.datatype.XMLGregorianCalendar
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecore.xmi.XMLResource
import org.eclipse.emf.edit.command.SetCommand
import org.eclipse.emf.edit.domain.EditingDomain
import org.eclipse.set.basis.constants.ContainerType
import org.eclipse.set.basis.constants.ExportType
import org.eclipse.set.basis.guid.Guid
import org.eclipse.set.core.services.Services
import org.eclipse.set.model.planpro.Basisobjekte.Anhang
import org.eclipse.set.model.planpro.Basisobjekte.BasisobjekteFactory
import org.eclipse.set.model.planpro.Basisobjekte.Identitaet_TypeClass
import org.eclipse.set.model.planpro.PlanPro.Akteur_Allg_AttributeGroup
import org.eclipse.set.model.planpro.PlanPro.Akteur_Zuordnung
import org.eclipse.set.model.planpro.PlanPro.DocumentRoot
import org.eclipse.set.model.planpro.PlanPro.ENUMPlanungEArt
import org.eclipse.set.model.planpro.PlanPro.ENUMPlanungPhase
import org.eclipse.set.model.planpro.PlanPro.Organisation
import org.eclipse.set.model.planpro.PlanPro.PlanProFactory
import org.eclipse.set.model.planpro.PlanPro.PlanProPackage
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle
import org.eclipse.set.model.planpro.PlanPro.Planung_E_Allg_AttributeGroup
import org.eclipse.set.model.planpro.PlanPro.Planung_Gruppe
import org.eclipse.set.model.planpro.PlanPro.Planung_Projekt
import org.eclipse.set.model.planpro.PlanPro.util.PlanProResourceImpl
import org.eclipse.set.model.planpro.Verweise.VerweiseFactory
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup
import org.eclipse.set.utils.ToolboxConfiguration

import static extension org.eclipse.set.ppmodel.extensions.EObjectExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanungEinzelExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PlanungProjektExtensions.*

/**
 * Extensions for {@link PlanPro_Schnittstelle}.
 * 
 * @author Schaefer
 */
class PlanProSchnittstelleExtensions {
	/**
	 * @param schnittstelle this PlanPro Schnittstelle
	 * 
	 * @return whether this Schnittstelle is a planning
	 */
	static def boolean isPlanning(PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle !== null &&
			schnittstelle.LSTPlanungProjekt !== null
	}

	/**
	 * Perform some corrections for the given PlanPro Schnittstelle.
	 * 
	 * @param schnittstelle this PlanPro Schnittstelle
	 * @return whether fixes have been applied
	 */
	static def void fix(PlanPro_Schnittstelle schnittstelle) {
		if (schnittstelle.planning) {
			schnittstelle.fixGuids
		}
	}

	/**
	 * Fills default values for the given PlanPro Schnittstelle's Objektmanagement if required.
	 * 
	 * @param schnittstelle this PlanPro Schnittstelle
	 * @return whether fixes have been applied
	 */
	static def boolean containsUnfilledManagementValues(
		PlanPro_Schnittstelle schnittstelle, XMLResource resource) {
		val objman = schnittstelle?.LSTPlanung?.objektmanagement
		return !objman.unfilledValues.empty
	}

	static def void fixManagementDefaults(PlanPro_Schnittstelle schnittstlle) {
		schnittstlle?.LSTPlanung?.objektmanagement.unfilledValues.forEach [
			value.fillDefaults
		]
	}

	/**
	 * Returns whether the given PlanPro Schnittstelle contains required but not set values.
	 * 
	 * @param schnittstelle this PlanPro Schnittstelle
	 * @return whether fixes have been applied
	 */
	static def boolean containsUnfilledValues(
		PlanPro_Schnittstelle schnittstelle, XMLResource resource) {
		val unfilledValues = schnittstelle.unfilledValues.toList

		if (resource instanceof PlanProResourceImpl) {
			return !unfilledValues.empty
		}
		/* TODO(1.10.0.1): Readd when temporary integartion is readded 
		 * if (resource instanceof TemporaryintegrationResourceImpl) {
		 * 	// Do not fill for integrations
		 * 	return false
		 * }
		 */
		return !unfilledValues.empty
	}

	/**
	 * @param schnittstelle this PlanPro Schnittstelle
	 * @param container the wanted container
	 * 
	 * @return the specified container
	 */
	static def MultiContainer_AttributeGroup getContainer(
		PlanPro_Schnittstelle schnittstelle,
		ContainerType container) throws IllegalArgumentException {
		switch (container) {
			case INITIAL: {
				val projects = schnittstelle.LSTPlanungGruppe
				if (projects.isPresent) {
					val containers = projects.get.map [
						LSTPlanungEinzel?.LSTZustandStart?.container
					]
					return new MultiContainer_AttributeGroup(containers)
				}
				return null
			}
			case FINAL: {
				val projects = schnittstelle.LSTPlanungGruppe
				if (projects.isPresent) {
					val containers = projects.get.map [
						LSTPlanungEinzel?.LSTZustandZiel?.container
					]
					return new MultiContainer_AttributeGroup(containers)
				}
				return null
			}
			case SINGLE: {
				val singleContainer = schnittstelle?.LSTZustand?.container
				if (singleContainer !== null)
					return new MultiContainer_AttributeGroup(
						schnittstelle?.LSTZustand?.container);
				return null
			}
		}
	}

	/**
	 * @param schnittstelle this PlanPro Schnittstelle
	 * 
	 * @return the document root or <code>null</code> if this Schnittstelle is
	 * not contained within a document root
	 */
	static def DocumentRoot getDocumentRoot(
		PlanPro_Schnittstelle schnittstelle) {
		val container = schnittstelle.eContainer
		if (container instanceof DocumentRoot) {
			return container
		}
		return null
	}

	/**
	 * @param schnittstelle this PlanPro Schnittstelle
	 * 
	 * @return die Planung allgemein
	 */
	static def Planung_E_Allg_AttributeGroup getPlanungAllgemein(
		PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
			LSTPlanungEinzel?.planungEAllg
	}

	/**
	 * Copy meta data from this Schnittstelle to the destination Schnittstelle.
	 * 
	 * @param schnittstelle this PlanPro Schnittstelle
	 * @param destination the destination PlanPro Schnittstelle
	 */
	static def void copyMetaData(
		PlanPro_Schnittstelle schnittstelle,
		PlanPro_Schnittstelle destination
	) {
		val copy = EcoreUtil.copy(schnittstelle)
		destination.LSTPlanungProjekt.leadingPlanungGruppe.LSTPlanungEinzel.
			anhangErlaeuterungsbericht.addAll(
				copy.LSTPlanungProjekt.leadingPlanungGruppe.LSTPlanungEinzel.
					anhangErlaeuterungsbericht
			)
		destination.LSTPlanungProjekt.leadingPlanungGruppe.LSTPlanungEinzel.
			anhangMaterialBesonders.addAll(
				copy.LSTPlanungProjekt.leadingPlanungGruppe.LSTPlanungEinzel.
					anhangMaterialBesonders
			)
		destination.LSTPlanungProjekt.leadingPlanungGruppe.LSTPlanungEinzel.anhangVzG.
			addAll(
				copy.LSTPlanungProjekt.leadingPlanungGruppe.LSTPlanungEinzel.anhangVzG
			)
		destination.LSTPlanungProjekt.leadingPlanungGruppe.
			LSTPlanungEinzel.planungEAllg = copy.LSTPlanungProjekt.
			leadingPlanungGruppe.LSTPlanungEinzel.planungEAllg
		destination.LSTPlanungProjekt.leadingPlanungGruppe.
			LSTPlanungEinzel.planungEHandlung = copy.LSTPlanungProjekt.
			leadingPlanungGruppe.LSTPlanungEinzel.planungEHandlung
		destination.LSTPlanungProjekt.leadingPlanungGruppe.planungGAllg = copy.
			LSTPlanungProjekt.leadingPlanungGruppe.planungGAllg
		destination.LSTPlanungProjekt.
			leadingPlanungGruppe.planungGFuehrendeStrecke = copy.LSTPlanungProjekt.
			leadingPlanungGruppe.planungGFuehrendeStrecke
		destination.LSTPlanungProjekt.leadingPlanungGruppe.planungGSchriftfeld = copy.
			LSTPlanungProjekt.leadingPlanungGruppe.planungGSchriftfeld
		destination.LSTPlanungProjekt.planungPAllg = copy.LSTPlanungProjekt.
			planungPAllg

		// adapt meta data of destination to enforce a different file name
		val bauzustandKurzbezeichnung = destination?.LSTPlanungProjekt?.
			leadingPlanungGruppe?.LSTPlanungEinzel?.planungEAllg?.
			bauzustandKurzbezeichnung?.wert
		if (bauzustandKurzbezeichnung !== null) {
			destination.LSTPlanungProjekt.leadingPlanungGruppe.LSTPlanungEinzel.
				planungEAllg.
				bauzustandKurzbezeichnung.wert = '''«bauzustandKurzbezeichnung»_Gesamt'''
		}
	}

	/** 
	 * erzeugt eine leere PlanPro-Model-Instanz.
	 */
	static def PlanPro_Schnittstelle createEmptyModel() {
		val factory = PlanProFactory.eINSTANCE;
		val planPro_Schnittstelle = factory.createPlanPro_Schnittstelle();
		val allgemeineAttribute = factory.
			createPlanPro_Schnittstelle_Allg_AttributeGroup();
		val toolboxName = factory.createWerkzeug_Name_TypeClass();
		val toolboxVersion = factory.createWerkzeug_Version_TypeClass();
		val timestamp = factory.createErzeugung_Zeitstempel_TypeClass();
		allgemeineAttribute.setErzeugungZeitstempel(timestamp);
		allgemeineAttribute.setWerkzeugName(toolboxName);
		allgemeineAttribute.setWerkzeugVersion(toolboxVersion);

		planPro_Schnittstelle.setPlanProSchnittstelleAllg(allgemeineAttribute);

		val planungProject = factory.createPlanung_Projekt();
		planPro_Schnittstelle.setLSTPlanungProjekt(planungProject);

		val planungGruppe = factory.createPlanung_Gruppe();
		planungProject.setLSTPlanungGruppe(planungGruppe);
		planungGruppe.fixGuids
		val planungEinzel = factory.createPlanung_Einzel();
		planungGruppe.setLSTPlanungEinzel(planungEinzel);

		val schriftfeld = factory.createPlanung_G_Schriftfeld_AttributeGroup
		schriftfeld.planungsbuero = factory.createOrganisation
		planungGruppe.planungGSchriftfeld = schriftfeld

		val planungPAllg = factory.createPlanung_P_Allg_AttributeGroup
		val projektleiter = factory.createAkteur
		projektleiter.kontaktdaten = factory.createOrganisation
		projektleiter.akteurAllg = factory.createAkteur_Allg_AttributeGroup

		planungPAllg.projektleiter = projektleiter

		planungProject.planungPAllg = planungPAllg

		val fachdaten = factory.createFachdaten_AttributeGroup
		val ausgabeFachdaten = factory.createAusgabe_Fachdaten
		ausgabeFachdaten.fixGuids
		planungEinzel.IDAusgabeFachdaten = VerweiseFactory.eINSTANCE.
			createID_Ausgabe_Fachdaten_ohne_Proxy_TypeClass
		planungEinzel.IDAusgabeFachdaten.value = ausgabeFachdaten
		planungEinzel.IDAusgabeFachdaten.wert = ausgabeFachdaten.identitaet.wert

		fachdaten.ausgabeFachdaten.add(ausgabeFachdaten)
		planungEinzel.LSTPlanung.fachdaten = fachdaten
		planungEinzel.planungEHandlung = factory.
			createPlanung_E_Handlung_AttributeGroup

		val zustandStart = factory.createLST_Zustand
		planungEinzel.ausgabeFachdaten.LSTZustandStart = zustandStart

		val planungAllgemein = factory.createPlanung_E_Allg_AttributeGroup();

		val bauzustand = factory.createBauzustand_Kurzbezeichnung_TypeClass();
		planungAllgemein.setBauzustandKurzbezeichnung(bauzustand);
		val lfdNummer = factory.createLaufende_Nummer_Ausgabe_TypeClass();

		planungAllgemein.setLaufendeNummerAusgabe(lfdNummer);
		val index = factory.createIndex_Ausgabe_TypeClass();
		planungAllgemein.setIndexAusgabe(index);
		planungEinzel.setPlanungEAllg(planungAllgemein);

		val containerStart = factory.createContainer_AttributeGroup();
		zustandStart.setContainer(containerStart);
		zustandStart.fixGuids

		val zustandZiel = factory.createLST_Zustand
		planungEinzel.ausgabeFachdaten.LSTZustandZiel = zustandZiel

		val containerZiel = factory.createContainer_AttributeGroup();
		zustandZiel.setContainer(containerZiel);
		zustandZiel.fixGuids
		return planPro_Schnittstelle
	}

	static def PlanPro_Schnittstelle readFrom(Resource resource) {
		val contents = resource.contents
		if (contents.empty) {
			return null
		}
		val root = contents.head
		if (root instanceof DocumentRoot) {
			return root.planProSchnittstelle
		}
		throw new IllegalArgumentException(
			"Ressource contains no PlanPro model with the requested version."
		);
	}

	static def Optional<Akteur_Allg_AttributeGroup> getProjektleiter(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.planungPAllg?.projektleiter?.
				akteurAllg
		)
	}

	static def Optional<Organisation> getFachplaner(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
				planungGSchriftfeld?.planungsbuero
		)
	}

	static def Optional<String> getBauphase(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.LSTPlanungEinzel?.
				planungEAllg?.bauphase?.wert
		)
	}

	static def Optional<String> getBauzustandKurzbezeichnung(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.LSTPlanungEinzel?.
				planungEAllg?.bauzustandKurzbezeichnung?.wert
		)
	}

	static def Optional<XMLGregorianCalendar> getDatumAbschlussEinzel(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.LSTPlanungEinzel?.
				planungEAllg?.datumAbschlussEinzel?.wert
		)
	}

	static def Optional<String> getIndexAusgabe(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.LSTPlanungEinzel?.
				planungEAllg?.indexAusgabe?.wert
		)
	}

	static def Optional<Boolean> getInformativ(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.LSTPlanungEinzel?.
				planungEAllg?.informativ?.wert
		)
	}

	static def Optional<String> getLaufendeNummerAusgabe(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.LSTPlanungEinzel?.
				planungEAllg?.laufendeNummerAusgabe?.wert
		)
	}

	static def Optional<ENUMPlanungEArt> getPlanungArt(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.LSTPlanungEinzel?.
				planungEAllg?.planungEArt?.wert
		)
	}

	static def Optional<ENUMPlanungPhase> getPlanungPhase(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.LSTPlanungEinzel?.
				planungEAllg?.planungPhase?.wert
		)
	}

	static def Optional<XMLGregorianCalendar> getDatumAbschlussGruppe(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.planungGAllg?.
				datumAbschlussGruppe?.wert
		)
	}

	static def Optional<String> getPlanProXSDVersion(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.planungGAllg?.
				planProXSDVersion?.wert
		)
	}

	static def Optional<String> getVerantwortlicheStelleDB(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.planungGAllg?.
				verantwortlicheStelleDB?.wert
		)
	}

	static def Optional<String> getStreckeAbschnitt(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
				planungGFuehrendeStrecke?.streckeAbschnitt?.wert
		)
	}

	static def Optional<String> getStreckeKm(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
				planungGFuehrendeStrecke?.streckeKm?.wert
		)
	}

	static def Optional<String> getBauabschnitt(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
				planungGSchriftfeld?.bauabschnitt?.wert
		)
	}

	static def Optional<String> getBezeichnungAnlage(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
				planungGSchriftfeld?.bezeichnungAnlage?.wert
		)
	}

	static def Optional<String> getBezeichnungPlanungGruppe(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
				planungGSchriftfeld?.bezeichnungPlanungGruppe?.wert
		)
	}

	static def Optional<String> getBezeichnungUnteranlage(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
				planungGSchriftfeld?.bezeichnungUnteranlage?.wert
		)
	}

	static def Optional<String> getWerkzeugName(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.planProSchnittstelleAllg?.werkzeugName?.wert)
	}

	static def Optional<XMLGregorianCalendar> getErzeugungZeitstempel(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.planProSchnittstelleAllg?.erzeugungZeitstempel?.wert)
	}

	static def Optional<String> getWerkzeugVersion(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.planProSchnittstelleAllg?.werkzeugVersion?.wert)
	}

	static def Optional<String> getBezeichnungPlanungProjekt(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.planungPAllg?.
				bezeichnungPlanungProjekt?.wert)
	}

	static def Optional<XMLGregorianCalendar> getDatumAbschlussProjekt(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.planungPAllg?.
				datumAbschlussProjekt?.wert)
	}

	static def Optional<String> getProjektNummer(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.planungPAllg?.projektNummer?.wert)
	}

	static def Optional<String> getIdentitaet(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(schnittstelle?.identitaet?.wert)
	}

	static def Optional<String> getLSTPlanungEinzelIdentitaet(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.LSTPlanungEinzel?.
				identitaet?.wert)
	}

	static def Optional<String> getLSTPlanungGruppeIdentitaet(
		PlanPro_Schnittstelle schnittstelle) {
		val wert = schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.identitaet?.
			wert
		return Optional.ofNullable(wert)
	}

	static def Optional<Iterable<Planung_Gruppe>> getLSTPlanungGruppe(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanung?.objektmanagement?.LSTPlanungProjekt?.map [
				LSTPlanungGruppe
			]?.flatten)
	}

	static def Optional<String> getLSTPlanungProjektIdentitaet(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.identitaet?.wert)
	}

	static def Optional<String> getLSTZustandInformationIdentitaet(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(schnittstelle?.LSTZustand?.identitaet?.wert)
	}

	static def List<Akteur_Zuordnung> getPlanungAbnahme(
		PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
			LSTPlanungEinzel?.planungEHandlung?.planungEAbnahme ?:
			Collections.emptyList
	}

	static def List<Akteur_Zuordnung> getPlanungErstellung(
		PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
			LSTPlanungEinzel?.planungEHandlung?.planungEErstellung ?:
			Collections.emptyList
	}

	static def List<Akteur_Zuordnung> getPlanungFreigabe(
		PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
			LSTPlanungEinzel?.planungEHandlung?.planungEFreigabe ?:
			Collections.emptyList
	}

	static def List<Akteur_Zuordnung> getPlanungGenehmigung(
		PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
			LSTPlanungEinzel?.planungEHandlung?.planungEGenehmigung ?:
			Collections.emptyList
	}

	static def List<Akteur_Zuordnung> getPlanungGleichstellung(
		PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
			LSTPlanungEinzel?.planungEHandlung?.planungEGleichstellung ?:
			Collections.emptyList
	}

	static def List<Akteur_Zuordnung> getPlanungPruefung(
		PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
			LSTPlanungEinzel?.planungEHandlung?.planungEPruefung ?:
			Collections.emptyList
	}

	static def List<Akteur_Zuordnung> getPlanungQualitaetspruefung(
		PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
			LSTPlanungEinzel?.planungEHandlung?.planungEQualitaetspruefung ?:
			Collections.emptyList
	}

	static def List<Akteur_Zuordnung> getPlanungSonstige(
		PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
			LSTPlanungEinzel?.planungEHandlung?.planungESonstige ?:
			Collections.emptyList
	}

	static def List<Akteur_Zuordnung> getPlanungUebernahme(
		PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
			LSTPlanungEinzel?.planungEHandlung?.planungEUebernahme ?:
			Collections.emptyList
	}

	static def Optional<String> getFuehrendeOertlichkeit(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.LSTPlanungProjekt?.leadingPlanungGruppe?.
				fuehrendeOertlichkeit?.wert)
	}

	static def Optional<String> getBemerkung(
		PlanPro_Schnittstelle schnittstelle) {
		return Optional.ofNullable(
			schnittstelle?.planProSchnittstelleAllg?.bemerkung?.wert)
	}

	static def boolean hasHandlung(PlanPro_Schnittstelle schnittstelle) {
		return !schnittstelle.planungAbnahme.empty ||
			!schnittstelle.planungErstellung.empty ||
			!schnittstelle.planungFreigabe.empty ||
			!schnittstelle.planungGenehmigung.empty ||
			!schnittstelle.planungGleichstellung.empty ||
			!schnittstelle.planungPruefung.empty ||
			!schnittstelle.planungQualitaetspruefung.empty ||
			!schnittstelle.planungUebernahme.empty ||
			!schnittstelle.planungSonstige.empty
	}

	/**
	 * Update tool name, version and time stamp.
	 * 
	 * @param schnittstelle the PlanPro Schnittstelle
	 * @param applicationName the application name
	 */
	static def void updateErzeugung(
		PlanPro_Schnittstelle schnittstelle,
		String applicationName,
		EditingDomain domain
	) {
		try {
			var cmd = SetCommand.create(
				domain,
				schnittstelle.planProSchnittstelleAllg.werkzeugName,
				PlanProPackage.eINSTANCE.werkzeug_Name_TypeClass_Wert,
				applicationName
			)
			domain.commandStack.execute(cmd)

			cmd = SetCommand.create(
				domain,
				schnittstelle.planProSchnittstelleAllg.werkzeugVersion,
				PlanProPackage.eINSTANCE.werkzeug_Version_TypeClass_Wert,
				ToolboxConfiguration.toolboxVersion.shortVersion
			)
			domain.commandStack.execute(cmd)

			cmd = SetCommand.create(
				domain,
				schnittstelle.planProSchnittstelleAllg.erzeugungZeitstempel,
				PlanProPackage.eINSTANCE.erzeugung_Zeitstempel_TypeClass_Wert,
				DatatypeFactory.newInstance.
					newXMLGregorianCalendar(new GregorianCalendar)
			)
			domain.commandStack.execute(cmd)
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e)
		}
	}

	/**
	 * @param schnittstelle the PlanPro Schnittstelle
	 * @param directory the directory
	 * 
	 * @return the path derived from the model and the given directory
	 */
	static def Path getDerivedPath(
		PlanPro_Schnittstelle schnittstelle,
		String directory,
		String fileExtension,
		ExportType exportType
	) {
		// derive raw filename
		val oertlichkeit = schnittstelle.fuehrendeOertlichkeit.orElse(
			"(oertlichkeit)")
		val index = schnittstelle.indexAusgabe.orElse("(index)")
		val lfdNummer = schnittstelle.laufendeNummerAusgabe.orElse(
			"(lfdNummer)")
		val bauzustand = schnittstelle.bauzustandKurzbezeichnung.orElse(
			"(bauzustand)"
		)

		val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm")

		val timestamp = schnittstelle.erzeugungZeitstempel.map [
			"_" + it.toGregorianCalendar.toZonedDateTime.format(formatter)
		].orElse("")

		var String filename
		if (exportType === ExportType.INVENTORY_RECORDS) {
			filename = '''«oertlichkeit»_«index»_«lfdNummer»_B_«bauzustand»«timestamp».«fileExtension»'''
		} else {
			filename = '''«oertlichkeit»_«index»_«lfdNummer»_«bauzustand»«timestamp».«fileExtension»'''
		}

		// revise filename		
		filename = filename.replaceAll("\\*", "");
		filename = filename.replaceAll("\\?", "");
		filename = filename.replaceAll(" ", "_");
		filename = filename.replaceAll("/", "-");
		filename = filename.replaceAll("\\\\", "-");
		filename = filename.replaceAll("\\|", "-");
		filename = filename.replaceAll(":", "-");
		filename = filename.replaceAll("<", "(");
		filename = filename.replaceAll(">", ")");

		return Paths.get(directory, filename);
	}

	static def Set<String> getGuids(PlanPro_Schnittstelle schnittstelle,
		ContainerType containerType) {
		return schnittstelle.getContainer(containerType).urObjekt.map [
			identitaet.wert
		].toSet
	}

	/**
	 * @param schnittstelle the PlanPro Schnittstelle
	 * 
	 * @return the (via PlanningAccessService) defined LST Planung Projekt of the Schnittstelle
	 */
	static def Planung_Projekt LSTPlanungProjekt(
		PlanPro_Schnittstelle schnittstelle
	) {
		// "1.9 update" toolbox currently supports only a single project
		return Services.planningAccessService.
			getLSTPlanungProjekt(schnittstelle)
	}

	/**
	 * Replace the first Planung Projekt for the given PlanPro Schnittstelle
	 * with the given Planung Projekt.
	 *  
	 * @param schnittstelle the PlanPro Schnittstelle
	 * @param planungProject the Planung Projekt
	 */
	static def void setLSTPlanungProjekt(
		PlanPro_Schnittstelle schnittstelle,
		Planung_Projekt planungProject
	) {
		Services.planningAccessService.setLSTPlanungProjekt(schnittstelle,
			planungProject);
	}

	private static def void fixGuids(PlanPro_Schnittstelle schnittstelle) {
		schnittstelle.LSTPlanung.objektmanagement.eAllContents.forEach [
			fixGuids
		]
	}

	private static def void fixGuids(EObject element) {
		// find containment reference of type Identitaet_TypeClass
		val idContainments = element.eClass.EAllContainments.filter [
			EType.instanceClass == typeof(Identitaet_TypeClass)
		]
		if (idContainments.empty) {
			return
		}
		if (idContainments.size > 1) {
			throw new RuntimeException('''size=«idContainments.size»''')
		}
		val idRef = idContainments.get(0)

		// get the value of the reference
		var Identitaet_TypeClass value = element.eGet(
			idRef) as Identitaet_TypeClass

		// set the value if it is null
		if (value === null) {
			value = BasisobjekteFactory.eINSTANCE.createIdentitaet_TypeClass
			element.eSet(idRef, value)
		}

		// set the wert if it is null
		if (value.wert === null) {
			value.wert = Guid.create.toString
		}
	}

	/**
	 * @param this schnittstelle
	 * @return  list of attachements within the schnittstelle 
	 */
	static def List<Anhang> getAttachments(
		PlanPro_Schnittstelle schnittstelle) {
		return schnittstelle.eAllContents.filter(Anhang).toList
	}

	static def PlanPro_Schnittstelle transformSingleState(
		PlanPro_Schnittstelle source) {
		if (source.planning) {
			return source;
		}

		val singleStateContainer = source.LSTZustand
		val copyContainer = EcoreUtil.copy(singleStateContainer)
		val newSchnittstelle = createEmptyModel
		val guid = BasisobjekteFactory.eINSTANCE.createIdentitaet_TypeClass;
		guid.setWert = source.identitaet.wert
		newSchnittstelle.identitaet = guid
		val newAusgabeFachdaten = newSchnittstelle.LSTPlanungProjekt.
			leadingPlanungGruppe.LSTPlanungEinzel.ausgabeFachdaten
		newAusgabeFachdaten.LSTZustandStart = copyContainer
		return newSchnittstelle
	}
}
