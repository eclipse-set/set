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
import static org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.getContainer;
import static org.eclipse.set.ppmodel.extensions.ESTW_ZentraleinheitExtensions.getTechnikStandort;
import static org.eclipse.set.ppmodel.extensions.UrObjectExtensions.filterObjectsInPlaceArea;
import static org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.getGleisAbschnitt;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Bezeichnung_Stellwerk_TypeClass;
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Technik_Standort;
import org.eclipse.set.model.planpro.Gleis.Gleis_Abschnitt;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element;

/**
 * 
 */
public class StellBereichExtensions {

	private static Object object;

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

	/**
	 * @param area
	 *            the {@link Stell_Bereich}
	 * @return the {@link W_Kr_Gsp_Element} in this area
	 */
	public static List<W_Kr_Gsp_Element> getWkrGspElement(
			final Stell_Bereich area) {
		final Iterable<Gleis_Abschnitt> abschnitts = filterObjectsInPlaceArea(
				getContainer(area).getGleisAbschnitt(), area);
		return StreamSupport.stream(getContainer(area).getWKrGspElement()
				.spliterator(), false).filter(
						gspElement -> StreamSupport
								.stream(abschnitts.spliterator(), false)
								.anyMatch(abschnitt -> getGleisAbschnitt(
										gspElement).contains(abschnitt)))
				.toList();

	}
}
