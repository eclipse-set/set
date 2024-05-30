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

import static org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions.getESTWZentraleinheits;
import static org.eclipse.set.ppmodel.extensions.ESTW_ZentraleinheitExtensions.getTechnikStandort;

import java.util.List;
import java.util.Objects;

import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Bezeichnung_Stellwerk_TypeClass;
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Technik_Standort;

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
	 * @return name of control area
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

	/**
	 * The list of technik standort in this area
	 * 
	 * @param area
	 *            the {@link Stell_Bereich}
	 * @return list of {@link Technik_Standort}
	 */
	public static List<Technik_Standort> getTechnikStandorts(
			final Stell_Bereich area) {
		final Aussenelementansteuerung aussenElementAnsteuerung = getAussenElementAnsteuerung(
				area);
		final List<ESTW_Zentraleinheit> estwZentraleinheits = getESTWZentraleinheits(
				aussenElementAnsteuerung);
		return estwZentraleinheits.stream()
				.flatMap(estw -> getTechnikStandort(estw).stream())
				.filter(Objects::nonNull).toList();
	}
}
