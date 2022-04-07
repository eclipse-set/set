/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Gleis.Gleis_Abschnitt
import org.eclipse.set.toolboxmodel.Bedienung.Bedien_Anrueckabschnitt

/**
 * Extensions for {@link Bedien_Anrueckabschnitt}.
 */
class BedienAnrueckabschnittExtensions extends BasisObjektExtensions {

	/**
	 * @param bedienAnrueckabschnitt this Bedien-Anrueckabschnitt
	 * 
	 * @returns the Gleisabschnitt Position
	 */
	static def Gleis_Abschnitt getGleisAbschnittPosition(
		Bedien_Anrueckabschnitt bedienAnrueckabschnitt) {
		return bedienAnrueckabschnitt.IDGleisAbschnittPosition
	}

	/**
	 * @param bedienAnrueckabschnitt this Bedien-Anrueckabschnitt
	 * 
	 * @returns the Gleis Abschnitt Darstellen
	 */
	static def Gleis_Abschnitt getGleisAbschnittDarstellen(
		Bedien_Anrueckabschnitt bedienAnrueckabschnitt) {
		return bedienAnrueckabschnitt.IDGleisAbschnittDarstellen
	}
}
