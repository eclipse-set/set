/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.ppmodel.extensions.BasisObjektExtensions
import de.scheidtbachmann.planpro.model.model1902.Schluesselabhaengigkeiten.Schluesselsperre
import de.scheidtbachmann.planpro.model.model1902.Ansteuerung_Element.Unterbringung
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

/**
 * Extensions for {@link Schluesselsperre}.
 */
class SchluesselsperreExtensions extends BasisObjektExtensions {

	/**
	 * @param ssp this Schluesselsperre
	 * 
	 * @returns the Unterbringung
	 */
	def static Unterbringung getUnterbringung(Schluesselsperre ssp) {
		return ssp.IDUnterbringung.resolve(Unterbringung)
	}
}
