/**
 * Copyright (c) 2021 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Bedienung.Bedien_Bezirk
import org.eclipse.set.toolboxmodel.Bedienung.Bedien_Zentrale

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
		return bedienBezirk.IDBedienZentrale
	}
}
