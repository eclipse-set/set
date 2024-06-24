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

import static org.eclipse.set.ppmodel.extensions.EObjectExtensions.getNullableObject;
import static org.eclipse.set.ppmodel.extensions.UrObjectExtensions.filterObjectsInControlArea;
import static org.eclipse.set.ppmodel.extensions.WKrGspElementExtensions.getGleisAbschnitt;

import java.util.stream.StreamSupport;
import java.util.stream.StreamSupport;

import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung;
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Geodaten.Oertlichkeit;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.model.planpro.Gleis.Gleis_Abschnitt;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element;

/**
 * Extensions for {@link Stell_Bereich}
 */
public class StellBereichExtensions {

	private StellBereichExtensions() {

	}

	/**
	 * Find {@link Stell_Bereich} through guid
	 * 
	 * @param container
	 *            the container
	 * @param guid
	 *            the guid
	 * @return the {@link Stell_Bereich}
	 */
	public static Stell_Bereich getStellBereich(
			final MultiContainer_AttributeGroup container, final String guid) {
		return StreamSupport
				.stream(container.getStellBereich().spliterator(), false)
				.filter(e -> e.getIdentitaet().getWert().equals(guid))
				.findFirst().orElse(null);
	}

	/**
	 *
	 * @param area
	 *            the {@link Stell_Bereich}
	 * @return name of control area
	 */
	public static String getStellBeteichBezeichnung(final Stell_Bereich area) {
		if (area == null) {
			return null;
		}
		final String oertlichkeitBezeichnung = EObjectExtensions
				.getNullableObject(area, e -> {
					final Aussenelementansteuerung aussenElementAnsteuerung = getAussenElementAnsteuerung(
							e);
					final Oertlichkeit oertlichkeit = AussenelementansteuerungExtensions
							.getOertlichkeitNamensgebend(
									aussenElementAnsteuerung);
					return oertlichkeit.getBezeichnung()
							.getOertlichkeitKurzname().getWert();
				}).orElse(null);

		if (oertlichkeitBezeichnung != null) {
			return oertlichkeitBezeichnung;
		}
		final String areaBezeichnung = EObjectExtensions
				.getNullableObject(area,
						e -> e.getBezeichnungStellwerk().getWert())
				.orElse(null);

		if (areaBezeichnung != null && !areaBezeichnung.isBlank()
				&& !areaBezeichnung.isEmpty()) {
			return areaBezeichnung;
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
		return getNullableObject(area,
				e -> e.getIDAussenelementansteuerung().getValue()).orElse(null);
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
		final Iterable<Gleis_Abschnitt> abschnitts = filterObjectsInControlArea(
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
