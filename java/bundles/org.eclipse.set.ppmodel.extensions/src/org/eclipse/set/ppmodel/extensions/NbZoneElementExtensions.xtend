/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt
import org.eclipse.set.model.planpro.Nahbedienung.NB_Zone_Element

/**
 * Extensions for {@link NB_Zone_Element}.
 * 
 * @author Schaefer
 */
class NbZoneElementExtensions extends BasisObjektExtensions {

	/**
	 * @param nbZoneElement this NB Zone Element
	 * 
	 * @return the NB Element
	 */
	def static Basis_Objekt getNbElement(NB_Zone_Element nbZoneElement) {
		return nbZoneElement.IDNBElement?.value
	}
}
