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
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Bedien_Anzeige_Element
import org.eclipse.set.model.planpro.BasisTypen.BasisAttribut_AttributeGroup
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Bedienung.Bedien_Anzeige_Element
import org.eclipse.set.model.planpro.Bedienung.Bedien_Einrichtung_Oertlich
import org.eclipse.set.model.planpro.Nahbedienung.NB_Bedien_Anzeige_Element

/**
 * Extensions for {@link Bedien_Anzeige_Element}.
 * 
 * @author Rumpf/Schaefer
 */
class BedienAnzeigeElementExtensions extends BasisObjektExtensions {

	/**
	 * @param bedienAnzeigeElement this Bedienanzeigeelement
	 * 
	 * @returns the örtliche Bedieneinrichtung
	 */
	def static Bedien_Einrichtung_Oertlich getBedienEinrichtungOertlich(
		Bedien_Anzeige_Element bedienAnzeigeElement) {
		return bedienAnzeigeElement.IDBedienEinrichtungOertlich?.value
	}

	/**
	 * @param bedienAnzeigeElement this Bedienanzeigeelement
	 * 
	 * @returns the NB Bedienanzeigeelemente of this Bedienanzeigeelement
	 */
	def static List<NB_Bedien_Anzeige_Element> getNbBedienAnzeigeElemente(
		Bedien_Anzeige_Element bedienAnzeigeElement) {
		return bedienAnzeigeElement.container.NBBedienAnzeigeElement.filter [
			IDBedienAnzeigeElement?.value?.identitaet?.wert ==
				bedienAnzeigeElement.identitaet.wert
		].toList
	}

	/**
	 * @param bedienAnzeigeElement this Bedienanzeigeelement
	 * 
	 * @returns the verknüpfte Element of this Bedienanzeigeelement
	 */
	def static Basis_Objekt getVerknuepftesElement(
		Bedien_Anzeige_Element bedienAnzeigeElement) {
		return bedienAnzeigeElement.IDVerknuepftesElement?.value
	}

	/**
	 * @param bedienAnzeigeElement this Bedienanzeigeelement
	 * 
	 * @returns the linked BUE Bedien Anzeige Elemente of this Bedienanzeigeelement
	 */
	def static List<BUE_Bedien_Anzeige_Element> getBueBedienAnzeigeElemente(
		Bedien_Anzeige_Element bedienAnzeigeElement
	) {
		return bedienAnzeigeElement.container.BUEBedienAnzeigeElement.filter [
			BUEBedienAnzElementAllg?.IDBedienAnzeigeElement?.value?.identitaet?.wert ==
				bedienAnzeigeElement.identitaet.wert
		].toList
	}

	/**
	 * @param bedienAnzeigeElement this Bedienanzeigeelement
	 * 
	 * @return the comment
	 */
	def static String comment(Bedien_Anzeige_Element bedAnzeigeElement,
		(BasisAttribut_AttributeGroup)=>String translation) {
		val taste = translation.apply(
			bedAnzeigeElement?.bedienAnzeigeElementAllg?.taste)
		val schalter = translation.apply(
			bedAnzeigeElement?.bedienAnzeigeElementAllg?.schalter)
		if (taste !== null || schalter !== null) {
			val tasteSchalter = '''«taste ?: ""»«schalter ?: ""»'''
			val bezeichner = bedAnzeigeElement?.bezeichnung?.
				bezBedAnzeigeElement?.wert ?: ""
			val oertlBez = bedAnzeigeElement?.bedienEinrichtungOertlich?.
				bezeichnung?.bedienEinrichtOertlBez?.wert ?: ""
			return '''«tasteSchalter»«bezeichner» («oertlBez»)'''
		} else {
			return null
		}
	}
}
