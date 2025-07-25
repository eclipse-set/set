/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions;

import java.util.List
import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Ansteuerung_Element.Unterbringung
import org.eclipse.set.model.planpro.Bedienung.Bedien_Anzeige_Element
import org.eclipse.set.model.planpro.Bedienung.Bedien_Einrichtung_Oertlich

import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*

/**
 * Extensions for {@link Bedien_Einrichtung_Oertlich}.
 * 
 * @author Schaefer
 */
class BedienEinrichtungOertlichExtensions extends BasisObjektExtensions {

	/**
	 * @param einrichtung this örtliche Bedieneinrichtung
	 * 
	 * @return the Bedienanzeigeelemente of this örtliche Bedieneinrichtung
	 */
	static def List<Bedien_Anzeige_Element> getBedienAnzeigeElemente(
		Bedien_Einrichtung_Oertlich einrichtung) {
		return einrichtung.container.bedienAnzeigeElement.filter [
			IDBedienEinrichtungOertlich?.value?.identitaet?.wert ==
				einrichtung.identitaet.wert
		].toList
	}

	/**
	 * @param einrichtung this örtliche Bedieneinrichtung
	 * 
	 * @return the Unterbringung of this örtliche Bedieneinrichtung
	 */
	static def Unterbringung getUnterbringung(
		Bedien_Einrichtung_Oertlich einrichtung) {
		return einrichtung?.IDUnterbringung?.value
	}

	static def Aussenelementansteuerung getAussenelementansteuerung(
		Bedien_Einrichtung_Oertlich einrichtung
	) {
		return einrichtung?.IDAussenelementansteuerung?.value
	}

	static def boolean isBelongToControlArea(
		Bedien_Einrichtung_Oertlich bedienEinrichtung, Stell_Bereich area) {
		return bedienEinrichtung.IDAussenelementansteuerung?.value.
			isBelongToControlArea(area)

	}
}
