/**
 * Copyright (c) 2018 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions;

import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Aussenelementansteuerung
import org.eclipse.set.toolboxmodel.Ansteuerung_Element.Unterbringung
import org.eclipse.set.toolboxmodel.Bedienung.Bedien_Anzeige_Element
import org.eclipse.set.toolboxmodel.Bedienung.Bedien_Einrichtung_Oertlich
import java.util.List

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
			IDBedienEinrichtungOertlich?.identitaet?.wert ==
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
		return einrichtung?.IDUnterbringung
	}

	static def Aussenelementansteuerung getAussenelementansteuerung(
		Bedien_Einrichtung_Oertlich einrichtung
	) {
		return einrichtung?.IDAussenelementansteuerung
	}
}
