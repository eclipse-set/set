/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Bahnuebergang.BUE_Anlage
import de.scheidtbachmann.planpro.model.model1902.Bahnuebergang.BUE_Gleisbezogener_Gefahrraum
import java.util.List

/**
 * Extensions for {@link BUE_Anlage}.
 * 
 * @author Schaefer
 */
class BueAnlageExtensions extends PunktObjektExtensions {

	/**
	 * @param anlage this BUE Anlage
	 * 
	 * @return the gleisbezogene Gefahrräume of this BUE Anlage
	 */
	static def List<BUE_Gleisbezogener_Gefahrraum> getGleisbezogeneGefahrraeume(
		BUE_Anlage anlage) {
		return anlage.container.BUEGleisbezogenerGefahrraum.filter [
			IDBUEAnlage.wert == anlage.identitaet.wert
		].toList
	}
}
