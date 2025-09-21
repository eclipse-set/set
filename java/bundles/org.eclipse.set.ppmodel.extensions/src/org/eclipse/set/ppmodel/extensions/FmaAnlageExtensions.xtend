/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.List
import java.util.Set
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt
import org.eclipse.set.model.planpro.Gleis.Gleis_Schaltgruppe
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente
import org.eclipse.set.model.planpro.Ortung.Schaltmittel_Zuordnung
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.Gleis_Abschluss

import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.FmaKomponenteExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.StellBereichExtensions.*

/**
 * Diese Klasse erweitert {@link FMA_Anlage}.
 */
class FmaAnlageExtensions extends BasisObjektExtensions {

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
		return anlage.IDGleisAbschnitt?.value.filterContained(
			anlage.container.gleisAbschluss).toSet
	}

	/**
	 * @param anlage this FMA Anlage
	 * 
	 * @returns the table name of this FMA Anlage
	 */
	def static String getTableName(FMA_Anlage anlage) {
		val name = anlage.IDGleisAbschnitt?.value.bezeichnung.
			bezeichnungTabelle.wert
		var kaskadeBez = anlage?.FMAAnlageKaskade?.FMAKaskadeBezeichnung?.wert
		if (kaskadeBez === null) {
			kaskadeBez = ""
		}

		return '''«name»«kaskadeBez»'''
	}

	/**
	 * @param anlage this FMA Anlage
	 * 
	 * @returns the BZ-Bezeichner of this FMA Anlage
	 */
	def static String getBzBezeichner(FMA_Anlage anlage) {
		val gleisabschnitt = anlage.IDGleisAbschnitt?.value
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
			IDSchalter?.value?.identitaet?.wert == anlage?.identitaet?.wert
		].toList
	}

	/**
	 * @param anlage this FMA Anlage
	 * 
	 * @return the Gleisschaltgruppen intersecting the Gleisabschnitt of this FMA Anlage
	 */
	def static List<Gleis_Schaltgruppe> getGleisSchaltgruppen(
		FMA_Anlage anlage) {
		val gleisabschnitt = anlage.IDGleisAbschnitt?.value
		return anlage.container.gleisSchaltgruppe.filter [
			intersectsStrictly(gleisabschnitt)
		].toList
	}

	/**
	 * @param analge this FMA Anlage
	 * 
	 * @return the control area, which the most with the track segment of FMA_Anlage overlaps
	 */
	def static Stell_Bereich getRelevantAreaControl(FMA_Anlage anlage) {
		val gleisAbschnitt = anlage?.IDGleisAbschnitt?.value
		return gleisAbschnitt.mostOverlapControlArea
	}

	def static boolean isBelongToControlArea(FMA_Anlage anlage,
		Stell_Bereich area) {
		val anlageAEA = anlage?.IDGleisfreimeldeInnenanlage?.value
		return anlageAEA.isBelongToControlArea(area) ||
			area.isOverlappingControlArea(anlage?.IDGleisAbschnitt?.value, 50)
	}

}
