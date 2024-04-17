/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.ppmodel.extensions;

import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Bezeichnung_Stellwerk_TypeClass;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;

/**
 * 
 */
public class StellBereichExtensions {

	/**
	 * 
	 */
	private StellBereichExtensions() {
	}

	/**
	 *
	 * @param area
	 *            the {@link Stell_Bereich}
	 * @return name of place area
	 */
	public static String getStellBeteichBezeichnung(final Stell_Bereich area) {
		final Bezeichnung_Stellwerk_TypeClass areaNameTypeClass = area
				.getBezeichnungStellwerk();
		if (areaNameTypeClass != null) {
			final String areaName = areaNameTypeClass.getWert();
			if (areaName != null && !areaName.isEmpty()
					&& !areaName.isBlank()) {
				return areaName;
			}
		}
		return null;
	}

	/**
	 * @param area
	 *            the {@link Stell_Bereich}
	 * @return the {@link Aussenelementansteuerung}
	 */
	public static Aussenelementansteuerung getAussenElementAnsteuerung(
			final Stell_Bereich area) {
		return area.getIDAussenelementansteuerung() != null
				? area.getIDAussenelementansteuerung().getValue()
				: null;
	}
}
