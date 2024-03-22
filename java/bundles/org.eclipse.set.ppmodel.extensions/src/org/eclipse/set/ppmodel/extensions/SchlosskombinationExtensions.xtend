/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.ppmodel.extensions.BasisObjektExtensions
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schlosskombination
import org.eclipse.set.model.planpro.Ansteuerung_Element.Unterbringung

/**
 * Extensions for {@link Schlosskombination}.
 */
class SchlosskombinationExtensions extends BasisObjektExtensions {

	/**
	 * @param schlosskombination this schlosskombination
	 * 
	 * @returns the Unterbringung
	 */
	def static Unterbringung getUnterbringung(
		Schlosskombination schlosskombination) {
		return schlosskombination.IDUnterbringung?.value
	}

}
