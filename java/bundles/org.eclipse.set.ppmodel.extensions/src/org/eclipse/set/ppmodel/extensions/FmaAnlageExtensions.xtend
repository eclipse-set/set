/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Ansteuerung_Element.Aussenelementansteuerung
import de.scheidtbachmann.planpro.model.model1902.BasisTypen.Zeiger_TypeClass
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Punkt_Objekt
import de.scheidtbachmann.planpro.model.model1902.Gleis.Gleis_Abschnitt
import de.scheidtbachmann.planpro.model.model1902.Gleis.Gleis_Schaltgruppe
import de.scheidtbachmann.planpro.model.model1902.Ortung.FMA_Anlage
import de.scheidtbachmann.planpro.model.model1902.Ortung.FMA_Komponente
import de.scheidtbachmann.planpro.model.model1902.Ortung.Schaltmittel_Zuordnung
import de.scheidtbachmann.planpro.model.model1902.Weichen_und_Gleissperren.Gleis_Abschluss
import java.util.List
import java.util.Set

import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaKomponenteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

/**
 * Diese Klasse erweitert {@link FMA_Anlage}.
 */
class FmaAnlageExtensions extends BasisObjektExtensions {

	/**
	 * @param anlage this FMA Anlage
	 * 
	 * @returns the observed Gleisabschnitt of this FMA Anlage
	 */
	def static Gleis_Abschnitt getGleisabschnitt(FMA_Anlage anlage) {
		return anlage.IDGleisAbschnitt.resolve(Gleis_Abschnitt)
	}

	/** 
	 * @param anlage the fma anlage
	 * 
	 * @return the fma components
	 */
	def static Set<FMA_Komponente> getFmaKomponenten(FMA_Anlage anlage) {
		return anlage.container.FMAKomponente.filter [
			it.FMAKomponenteAchszaehlpunkt !== null
			it.belongsTo(anlage)
		].toSet
	}

	/**
	 * @param anlage this FMA Anlage
	 * 
	 * @returns the FMA Komponenten and Gleisabschlüsse of this FMA Anlage
	 */
	def static Set<Punkt_Objekt> getFmaGrenzen(FMA_Anlage anlage) {
		return (anlage.fmaKomponenten + anlage.gleisAbschluesse).toSet
	}

	/**
	 * @param anlage this FMA Anlage
	 * 
	 * @returns the Gleisabschlüsse of this FMA Anlage
	 */
	def static Set<Gleis_Abschluss> getGleisAbschluesse(FMA_Anlage anlage) {
		return anlage.gleisabschnitt.filterContained(
			anlage.container.gleisAbschluss).toSet
	}

	/**
	 * @param anlage this FMA Anlage
	 * 
	 * @returns the table name of this FMA Anlage
	 */
	def static String getTableName(FMA_Anlage anlage) {
		val name = anlage.gleisabschnitt.bezeichnung.bezeichnungTabelle.wert
		var kaskadeBez = anlage?.FMAAnlageKaskade?.FMAKaskadeBezeichnung?.wert
		if (kaskadeBez === null) {
			kaskadeBez = ""
		}

		return '''«name»«kaskadeBez»'''
	}

	/**
	 * @param anlage this FMA Anlage
	 * @param id of one associated Aussenelementansteuerung 
	 * 
	 * @returns the Aussenelementansteuerung of the given id
	 */
	def static Aussenelementansteuerung getAussenelementById(FMA_Anlage anlage,
		Zeiger_TypeClass id) {
		return id?.resolve(Aussenelementansteuerung)
	}

	/**
	 * @param anlage this FMA Anlage
	 * 
	 * @returns the BZ-Bezeichner of this FMA Anlage
	 */
	def static String getBzBezeichner(FMA_Anlage anlage) {
		val gleisabschnitt = anlage.gleisabschnitt
		val kennzahl = gleisabschnitt?.bezeichnung?.kennzahl?.wert
		val kennbuchstabe = anlage?.bezeichnung?.bezeichnungKennbuchstabe?.
			wert?.toString
		val elementbezeichner = gleisabschnitt?.bezeichnung?.
			oertlicherElementname?.wert

		return '''«kennzahl»«kennbuchstabe»«elementbezeichner»'''
	}

	/**
	 * @param anlage this FMA Anlage
	 * 
	 * @return the Schaltmittel Zuordnungen for this FMA Anlage
	 */
	def static List<Schaltmittel_Zuordnung> getSchaltmittelZuordnungen(
		FMA_Anlage anlage) {
		return anlage.container.schaltmittelZuordnung.filter [
			IDSchalter.wert == anlage.identitaet.wert
		].toList
	}

	/**
	 * @param anlage this FMA Anlage
	 * 
	 * @return the Gleisschaltgruppen intersecting the Gleisabschnitt of this FMA Anlage
	 */
	def static List<Gleis_Schaltgruppe> getGleisSchaltgruppen(
		FMA_Anlage anlage) {
		val gleisabschnitt = anlage.gleisabschnitt
		return anlage.container.gleisSchaltgruppe.filter [
			intersects(gleisabschnitt)
		].toList
	}
}
