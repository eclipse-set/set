/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Bedienung.Bedien_Bezirk
import de.scheidtbachmann.planpro.model.model1902.Bedienung.Bedien_Zentrale

import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

/**
 * Extensions for {@link Bedien_Bezirk}.
 * 
 * @author Stuecker
 */
class BedienBezirkExtensions extends BasisObjektExtensions {
	/**
	 * @param bedienBezirk the Bedien_Bezirk
	 * 
	 * @return the Bedien_Zentrale
	 */
	def static Bedien_Zentrale getBedienZentrale(Bedien_Bezirk bedienBezirk) {
		return bedienBezirk.IDBedienZentrale.resolve(Bedien_Zentrale)
	}
}
