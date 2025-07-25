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
import static org.eclipse.set.ppmodel.extensions.EObjectExtensions.getNullableObject;
import static org.eclipse.set.ppmodel.extensions.ESTW_ZentraleinheitExtensions.getTechnikStandort;
import static org.eclipse.set.ppmodel.extensions.UrObjectExtensions.filterObjectsInControlArea;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;

import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung;
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stellelement;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Technik_Standort;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Uebertragungsweg;
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Anlage;
import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Kante;
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt;
import org.eclipse.set.model.planpro.Basisobjekte.Bereich_Objekt;
import org.eclipse.set.model.planpro.Bedienung.Bedien_Bezirk;
import org.eclipse.set.model.planpro.Bedienung.Bedien_Einrichtung_Oertlich;
import org.eclipse.set.model.planpro.Bedienung.Bedien_Standort;
import org.eclipse.set.model.planpro.Bedienung.Bedien_Zentrale;
import org.eclipse.set.model.planpro.Block.Block_Element;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Aneinander;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier;
import org.eclipse.set.model.planpro.Flankenschutz.Fla_Schutz;
import org.eclipse.set.model.planpro.Flankenschutz.Fla_Zwieschutz;
import org.eclipse.set.model.planpro.Geodaten.Oertlichkeit;
import org.eclipse.set.model.planpro.Gleis.Gleis_Abschnitt;
import org.eclipse.set.model.planpro.Gleis.Gleis_Bezeichnung;
import org.eclipse.set.model.planpro.Nahbedienung.NB_Zone;
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage;
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente;
import org.eclipse.set.model.planpro.Ortung.Zugeinwirkung;
import org.eclipse.set.model.planpro.PZB.PZB_Element;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente;
import org.eclipse.set.model.planpro.Zugnummernmeldeanlage.ZN_ZBS;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;

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
				.findFirst()
				.orElse(null);
	}

	/**
	 *
	 * @param area
	 *            the {@link Stell_Bereich}
	 * @return name of control area
	 */
	public static String getStellBeteichBezeichnung(final Stell_Bereich area) {
		return getNullableObject(area, e -> {
			final Aussenelementansteuerung aussenElementAnsteuerung = getAussenElementAnsteuerung(
					e);
			return aussenElementAnsteuerung.getBezeichnung()
					.getBezeichnungAEA()
					.getWert();
		}).orElse(null);
	}

	/**
	 * @param area
	 *            the {@link Stell_Bereich}
	 * @return the short designation of control area location
	 */
	public static String getOertlichkeitBezeichnung(final Stell_Bereich area) {
		if (area == null) {
			return null;
		}
		return getNullableObject(area, e -> {
			final Aussenelementansteuerung aussenElementAnsteuerung = getAussenElementAnsteuerung(
					e);
			final Oertlichkeit oertlichkeit = AussenelementansteuerungExtensions
					.getOertlichkeitNamensgebend(aussenElementAnsteuerung);
			return oertlichkeit.getBezeichnung()
					.getOertlichkeitAbkuerzung()
					.getWert();
		}).orElse(null);
	}

	/**
	 * @param area
	 *            the {@link Stell_Bereich}
	 * @return the AEA designation of the {@link Aussenelementansteuerung},
	 *         which according to this area
	 */
	public static String getAussenelementansteuerungBezeichnungAEA(
			final Stell_Bereich area) {
		return getNullableObject(area,
				e -> getAussenElementAnsteuerung(e).getBezeichnung()
						.getBezeichnungAEA()
						.getWert()).orElse(null);
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
		if (aussenElementAnsteuerung == null) {
			return Collections.emptyList();
		}
		final List<ESTW_Zentraleinheit> estwZentraleinheits = getESTWZentraleinheits(
				aussenElementAnsteuerung);

		estwZentraleinheits
				.addAll(aussenElementAnsteuerung.getIDInformationPrimaer()
						.stream()
						.map(id -> getNullableObject(id, e -> e.getValue())
								.orElse(null))
						.filter(Objects::nonNull)
						.filter(Aussenelementansteuerung.class::isInstance)
						.map(Aussenelementansteuerung.class::cast)
						.flatMap(e -> getESTWZentraleinheits(e).stream())
						.toList());
		return estwZentraleinheits.stream()
				.flatMap(estw -> getTechnikStandort(estw).stream())
				.filter(Objects::nonNull)
				.toList();
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
		return StreamSupport
				.stream(getContainer(area).getWKrGspElement().spliterator(),
						false)
				.filter(gspElement -> StreamSupport
						.stream(abschnitts.spliterator(), false)
						.anyMatch(abschnitt -> WKrGspElementExtensions
								.getGleisAbschnitt(gspElement)
								.contains(abschnitt)))
				.toList();

	}

	/**
	 * @param area
	 *            the {@link Stell_Bereich}
	 * @param object
	 *            the element
	 * @return true, if the element belong to the area
	 */
	public static boolean isInControlArea(final Stell_Bereich area,
			final Basis_Objekt object) {
		if (object == null) {
			return false;
		}
		return switch (object) {
			case final Aussenelementansteuerung aussenElement -> AussenelementansteuerungExtensions
					.isBelongToControlArea(aussenElement, area);
			case final ESTW_Zentraleinheit estwZentral -> ESTW_ZentraleinheitExtensions
					.isBelongToControlArea(estwZentral, area);
			case final Bedien_Einrichtung_Oertlich oertlich -> BedienEinrichtungOertlichExtensions
					.isBelongToControlArea(oertlich, area);
			case final FMA_Anlage fmaAnlage -> FmaAnlageExtensions
					.isBelongToControlArea(fmaAnlage, area);
			case final FMA_Komponente fmaKomponente -> FmaKomponenteExtensions
					.isBelongToControlArea(fmaKomponente, area);
			case final Zugeinwirkung zugeinwirkung -> ZugEinwirkungExtensions
					.isBelongToControlArea(zugeinwirkung, area);
			case final PZB_Element pzb -> PZBElementExtensions
					.isBelongToControlArea(pzb, area);
			case final Signal signal -> SignalExtensions
					.isBelongToControlArea(signal, area);
			case final Technik_Standort standort -> TechnikStandortExtensions
					.isBelongToControlArea(standort, area);
			case final Bedien_Standort standort -> BedienStandortExtensions
					.isBelongToControlArea(standort, area);
			case final W_Kr_Gsp_Element gspElement -> WKrGspElementExtensions
					.isBelongToControlArea(gspElement, area);
			case final W_Kr_Gsp_Komponente gspKomponent -> isInControlArea(area,
					getNullableObject(gspKomponent,
							gsp -> gsp.getIDWKrGspElement().getValue())
									.orElse(null));
			case final Fstr_Aneinander fstr -> FstrAneinanderExtensions
					.isBelongToControlArea(fstr, area);
			case final Fstr_DWeg fstr -> FstrDWegWKrExtensions
					.isBelongToControlArea(fstr, area);
			case final Fstr_Zug_Rangier fstr -> FstrZugRangierExtensions
					.isBelongToControlArea(fstr, area);
			case final Fla_Schutz fla -> FlaSchutzExtensions
					.isBelongToControlArea(fla, area);
			case final Fla_Zwieschutz fla -> FlaZwieschutzExtensions
					.isBelongToControlArea(fla, area);
			case final Gleis_Abschnitt segment -> BereichObjektExtensions
					.intersects(area, segment);
			case final Gleis_Bezeichnung description -> isOverlappingControlArea(
					area, description, 50);
			case final NB_Zone nbZone -> NbZoneExtensions
					.isBelongToControlArea(nbZone, area);
			case final Uebertragungsweg uebertrangsweg -> UebertragungswegExtensions
					.isBelongToControlArea(uebertrangsweg, area);
			case final Bedien_Bezirk bedienBezirk -> BedienBezirkExtensions
					.isBelongToControlArea(bedienBezirk, area);
			case final Bedien_Zentrale controlCenter -> BedienZentraleExtensions
					.isBelongToControlArea(controlCenter, area);
			case final ZN_ZBS znZBS -> isInControlArea(area,
					getNullableObject(znZBS,
							ele -> ele.getIDESTWZentraleinheit().getValue())
									.orElse(null));
			case final Stellelement stellelement -> StellelementExtensions
					.isBelongToControlArea(stellelement, area);
			case final Block_Element blockElement -> BlockElementExtensions
					.isBelongToControlArea(blockElement, area);
			case final BUE_Kante bueKante -> BereichObjektExtensions
					.intersects(area, bueKante);
			case final BUE_Anlage bueAnlage -> BereichObjektExtensions
					.intersects(area, bueAnlage);
			default -> throw new IllegalArgumentException();
		};
	}

	/**
	 * @param area
	 *            the {@link Stell_Bereich}
	 * @param bo
	 *            {@link Bereich_Objekt}
	 * @param coverageDegree
	 *            the degree of coverage in percent
	 * @return true, if the control area coverage the {@link Bereich_Objekt}
	 *         with a given degree
	 */
	public static boolean isOverlappingControlArea(final Stell_Bereich area,
			final Bereich_Objekt bo, final double coverageDegree) {
		if (bo == null || area == null) {
			return false;
		}
		if (coverageDegree <= 0) {
			throw new IllegalArgumentException(
					"Coverage Dgreee must be greater than 0"); //$NON-NLS-1$
		}
		final BigDecimal overlappingLength = BereichObjektExtensions
				.getOverlappingLength(area, bo);

		return BereichObjektExtensions.getLength(bo)
				.multiply(BigDecimal.valueOf(coverageDegree / 100))
				.compareTo(overlappingLength) < 0;
	}
}
