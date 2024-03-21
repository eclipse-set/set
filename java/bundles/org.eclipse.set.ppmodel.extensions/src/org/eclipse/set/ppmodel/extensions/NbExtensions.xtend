/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Nahbedienung.NB
import java.util.List

/**
 * Extensions for {@link NB}.
 * 
 * @author Schaefer
 */
class NbExtensions {

	enum NBFunktionalitaetNBR {
		AWU,
		F_ST_Z,
		FA_FAE,
		SBUE,
		SLE_SLS,
		WHU,
		WUS
	}

	/**
	 * @param nb this Nahbedienbereich
	 * 
	 * @return the associated Nahbedienbereich
	 */
	static def List<NBFunktionalitaetNBR> getNBFunktionalitaetNBREnums(NB nb) {
		val result = newLinkedList()

		val nbFunktionalitaetNBR = nb.NBFunktionalitaetNBR

		if (nbFunktionalitaetNBR !== null) {
			if (Boolean.TRUE.equals(nbFunktionalitaetNBR.AWU?.wert)) {
				result.add(NBFunktionalitaetNBR.AWU)
			}
			if (Boolean.TRUE.equals(nbFunktionalitaetNBR.FSTZ?.wert)) {
				result.add(NBFunktionalitaetNBR.F_ST_Z)
			}
			if (Boolean.TRUE.equals(nbFunktionalitaetNBR.FAFAE?.wert)) {
				result.add(NBFunktionalitaetNBR.FA_FAE)
			}
			if (Boolean.TRUE.equals(nbFunktionalitaetNBR.SBUE?.wert)) {
				result.add(NBFunktionalitaetNBR.SBUE)
			}
			if (Boolean.TRUE.equals(nbFunktionalitaetNBR.SLESLS?.wert)) {
				result.add(NBFunktionalitaetNBR.SLE_SLS)
			}
			if (Boolean.TRUE.equals(nbFunktionalitaetNBR.WHU?.wert)) {
				result.add(NBFunktionalitaetNBR.WHU)
			}
			if (Boolean.TRUE.equals(nbFunktionalitaetNBR.WUS?.wert)) {
				result.add(NBFunktionalitaetNBR.WUS)
			}
		}

		return result
	}
}
