/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Nahbedienung.NB
import org.eclipse.set.toolboxmodel.Nahbedienung.NB_Bedien_Anzeige_Element
import org.eclipse.set.toolboxmodel.Nahbedienung.NB_Zone
import org.eclipse.set.toolboxmodel.Nahbedienung.NB_Zone_Element
import org.eclipse.set.toolboxmodel.Nahbedienung.NB_Zone_Grenze
import java.util.List

/**
 * Extensions for {@link NB_Zone}.
 * 
 * @author Schaefer
 */
class NbZoneExtensions extends BasisObjektExtensions {

	/**
	 * @param nbZone this Nahbedienbereichszone
	 * 
	 * @return the associated Nahbedienbereich
	 */
	def static NB getNb(NB_Zone nbZone) {
		return nbZone.IDNB
	}

	/**
	 * @param nbZone this Nahbedienbereichszone
	 * 
	 * @return the übergeordnete Nahbedienbereichszone
	 */
	def static NB_Zone getNBZone(NB_Zone nbZone) {
		return nbZone.IDNBZone
	}

	/**
	 * @param nbZone this Nahbedienbereichszone
	 * 
	 * @return the NB Zone Elemente of this Nahbedienbereichszone
	 */
	def static List<NB_Zone_Element> getNBZoneElemente(NB_Zone nbZone) {
		return nbZone.container.NBZoneElement.filter [
			IDNBZone.identitaet.wert == nbZone.identitaet.wert
		].toList
	}

	/**
	 * @param nbZone this Nahbedienbereichszone
	 * 
	 * @return the NB Zone Grenze list of this Nahbedienbereichszone
	 */
	def static List<NB_Zone_Grenze> getNBZoneGrenzen(NB_Zone nbZone) {
		return nbZone.container.NBZoneGrenze.filter [
			IDNBZone.identitaet.wert == nbZone.identitaet.wert
		].toList
	}

	/**
	 * @param nbZone this Nahbedienbereichszone
	 * 
	 * @return the NB Zone Grenze list of this Nahbedienbereichszone
	 */
	def static List<NB_Bedien_Anzeige_Element> getNBBedienAnzeigeElemente(
		NB_Zone nbZone) {
		return nbZone.container.NBBedienAnzeigeElement.filter [
			IDNBZone.identitaet.wert == nbZone.identitaet.wert
		].toList
	}

	/**
	 * @param nbZone this Nahbedienbereichszone
	 * 
	 * @return the Bereich/Zone Bezeichner of this Nahbedienbereichszone
	 */
	def static String getBezeichnung(NB_Zone nbZone) {
		val nb = nbZone.nb
		return '''«nb?.bezeichnung?.kennzahl?.wert ?: ""»NB«nb?.bezeichnung.bezeichnungNB.wert ?: ""»«nbZone.bezeichnung?.bezeichnungNBZone?.wert ?: ""»'''
	}
}
