/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Ortung.FMA_Anlage
import org.eclipse.set.toolboxmodel.Flankenschutz.Fla_Freimelde_Zuordnung
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

/**
 * Extensions for {@link Fla_Freimelde_Zuordnung}.
 */
class FlaFreimeldeZuordnungExtensions extends BasisObjektExtensions {

	/**
	 * @param zuordnung this Freimeldezuordnung
	 * 
	 * @return the freizuprüfende Flankenschutzraum 
	 */
	def static FMA_Anlage getFmaAnlage(Fla_Freimelde_Zuordnung zuordnung) {
		return zuordnung.IDFMAAnlage.resolve(FMA_Anlage)
	}
}
