/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Ansteuerung_Element.Unterbringung
import org.eclipse.set.model.planpro.Bedienung.Bedien_Standort

/** 
 * Extensions for {@link Bedien_Standort}.
 * 
 * @author Schaefer
 */
class BedienStandortExtensions extends BasisObjektExtensions {

	/**
	 * @param standort this Technikstandort
	 * 
	 * @return the Unterbringung this Technikstandort is installed in
	 */
	static def Unterbringung getUnterbringung(Bedien_Standort standort) {
		return standort?.IDUnterbringung?.value
	}
}
