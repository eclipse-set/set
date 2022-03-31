/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Begrenzung_A_TypeClass
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Begrenzung_B_TypeClass
import de.scheidtbachmann.planpro.model.model1902.Basisobjekte.Abstand_TypeClass

/**
 * Extensions for {@link Begrenzung_A_TypeClass} and {@link Begrenzung_B_TypeClass}.
 */
class BegrenzungExtensions {

	static double TOLERANCE = 0.01

	/**
	 * @param begrenzungA this Begrenzung A
	 * @param abstand1 the first Abstand
	 * @param abstand2 the second Abstand
	 * 
	 * @returns whether this Begrenzung is between the given Abstand values with respect to a global tolerance
	 */	
	def static boolean isBetween(
		Begrenzung_A_TypeClass begrenzungA,
		Abstand_TypeClass abstand1,
		Abstand_TypeClass abstand2
	) {
		return isBetween(begrenzungA.wert.doubleValue, abstand1.wert.doubleValue, abstand2.wert.doubleValue)
	}
	
	/**
	 * @param begrenzungA this Begrenzung B
	 * @param abstand1 the first Abstand
	 * @param abstand2 the second Abstand
	 * 
	 * @returns whether this Begrenzung is between the given Abstand values with respect to a global tolerance
	 */	
	def static boolean isBetween(
		Begrenzung_B_TypeClass begrenzungB,
		Abstand_TypeClass abstand1,
		Abstand_TypeClass abstand2
	) {
		return isBetween(begrenzungB.wert.doubleValue, abstand1.wert.doubleValue, abstand2.wert.doubleValue)
	}
	
	private def static boolean isBetween(double begrenzung, double abstand1, double abstand2) {
		return
				abstand1 - TOLERANCE <= begrenzung && begrenzung <= abstand2 + TOLERANCE
			||	abstand2 - TOLERANCE <= begrenzung && begrenzung <= abstand1 + TOLERANCE
	}	
}
