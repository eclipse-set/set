/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stellelement

import static extension org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.*

/**
 * This class extends {@link Stellelement}.
 */
class StellelementExtensions extends BasisObjektExtensions {

	/**
	 * @param stellelement this Stellelement
	 * 
	 * @returns the Außenelementansteuerung which is the power supply of this
	 * Stellelement 
	 */
	def static Aussenelementansteuerung getEnergie(Stellelement stellelement) {
		return stellelement?.IDEnergie?.value
	}

	/**
	 * @param stellelement this Stellelement
	 * 
	 * @returns the Außenelementansteuerung which is the data supply of this
	 * Stellelement 
	 */
	def static Aussenelementansteuerung getInformation(
		Stellelement stellelement) {
		return stellelement?.IDInformation?.value
	}

	def static boolean isBelongToControlArea(Stellelement stellElement,
		Stell_Bereich area) {
		return stellElement?.IDInformation?.value.isBelongToControlArea(area)
	}
}
