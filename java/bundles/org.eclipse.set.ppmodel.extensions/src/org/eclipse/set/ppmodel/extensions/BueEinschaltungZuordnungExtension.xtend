/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.Bahnuebergang.BUE_Einschaltung
import de.scheidtbachmann.planpro.model.model1902.Bahnuebergang.BUE_Einschaltung_Zuordnung
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

/**
 * Extensions for {@link BUE_Einschaltung_Zuordnung}.
 * 
 * @author Schaefer
 */
class BueEinschaltungZuordnungExtension extends BasisObjektExtensions {

	/**
	 * @param zuordnung this BUE Einschaltung Zuordnung
	 * 
	 * @return the BUE Einschaltung
	 */
	static def BUE_Einschaltung getEinschaltung(
		BUE_Einschaltung_Zuordnung zuordnung) {
		return zuordnung.IDBUEEinschaltung.resolve(BUE_Einschaltung)
	}
}
