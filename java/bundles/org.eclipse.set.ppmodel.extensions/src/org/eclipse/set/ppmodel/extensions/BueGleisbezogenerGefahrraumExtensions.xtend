/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Bahnuebergang.BUE_Einschaltung_Zuordnung
import org.eclipse.set.toolboxmodel.Bahnuebergang.BUE_Gleisbezogener_Gefahrraum
import java.util.List

/**
 * Extensions for {@link BUE_Gleisbezogener_Gefahrraum}.
 * 
 * @author Schaefer
 */
class BueGleisbezogenerGefahrraumExtensions extends BereichObjektExtensions {

	/**
	 * @param raum this BUE gleisbezogener Gefahrraum
	 * 
	 * @return the BUE Einschaltung Zuordnungen this Gefahrraum is assigned to
	 */
	static def List<BUE_Einschaltung_Zuordnung> getEinschaltungZuordnungen(
		BUE_Gleisbezogener_Gefahrraum raum) {
		return raum.container.BUEEinschaltungZuordnung.filter [
			it.IDBUEGleisbezGefahrraum.identitaet?.wert == raum.identitaet.wert
		].toList
	}
}
