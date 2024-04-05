/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Bedienung.Bedien_Anzeige_Element
import org.eclipse.set.model.planpro.Nahbedienung.NB_Bedien_Anzeige_Element
import org.eclipse.set.model.planpro.Nahbedienung.NB_Zone

/**
 * Extensions for {@link NB_Bedien_Anzeige_Element}.
 */
class NbBedienAnzeigeElementExtensions extends BasisObjektExtensions {

	/**
	 * @param nbBedienAnzeigeElement this NB Bedien Anzeige Element
	 * 
	 * @return the Bedien Anzeige Element, realizing the assignment to the
	 * physical button placement
	 */
	static def Bedien_Anzeige_Element getBedienAnzeigeElement(
		NB_Bedien_Anzeige_Element nbBedienAnzeigeElement) {
		return nbBedienAnzeigeElement.IDBedienAnzeigeElement?.value
	}

	/**
	 * @param nbBedienAnzeigeElement this NB Bedien Anzeige Element
	 * 
	 * @return the NB Zone of this NB Bedien Anzeige Element
	 */
	static def NB_Zone getNbZone(
		NB_Bedien_Anzeige_Element nbBedienAnzeigeElement) {
		return nbBedienAnzeigeElement.IDNBZone?.value
	}
}
