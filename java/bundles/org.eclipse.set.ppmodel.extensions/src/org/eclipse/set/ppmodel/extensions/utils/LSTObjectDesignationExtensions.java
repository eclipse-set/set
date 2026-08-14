/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.ppmodel.extensions.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Aussenelementansteuerung;
import org.eclipse.set.model.planpro.Ansteuerung_Element.ESTW_Zentraleinheit;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Technik_Standort;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Uebertragungsweg;
import org.eclipse.set.model.planpro.Bedienung.Bedien_Einrichtung_Oertlich;
import org.eclipse.set.model.planpro.Block.Block_Element;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Aneinander;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_DWeg;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Fahrweg;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Zug_Rangier;
import org.eclipse.set.model.planpro.Fahrstrasse.Markanter_Punkt;
import org.eclipse.set.model.planpro.Flankenschutz.Fla_Zwieschutz;
import org.eclipse.set.model.planpro.Gleis.Gleis_Bezeichnung;
import org.eclipse.set.model.planpro.Nahbedienung.NB_Zone;
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage;
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente;
import org.eclipse.set.model.planpro.Ortung.Zugeinwirkung;
import org.eclipse.set.model.planpro.PZB.ENUMPZBArt;
import org.eclipse.set.model.planpro.PZB.PZB_Element;
import org.eclipse.set.model.planpro.PlanPro.Container_AttributeGroup;
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schloss;
import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.planpro.Signale.Signal_Signalbegriff;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente;
import org.eclipse.set.ppmodel.extensions.AussenelementansteuerungExtensions;
import org.eclipse.set.ppmodel.extensions.DwegExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.ppmodel.extensions.FahrwegExtensions;
import org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions;
import org.eclipse.set.ppmodel.extensions.FstrAneinanderExtensions;
import org.eclipse.set.ppmodel.extensions.FstrZugRangierExtensions;
import org.eclipse.set.ppmodel.extensions.NbZoneExtensions;
import org.eclipse.set.ppmodel.extensions.PZBElementExtensions;
import org.eclipse.set.ppmodel.extensions.SignalbegriffExtensions;

import com.google.common.collect.Streams;

/**
 * Extensions for determine the LST Object designation
 * 
 * @author Truong
 */
public class LSTObjectDesignationExtensions {
	/**
	 * @param obj
	 *            the LST Object
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(final EObject obj) {
		final EObject lstObjectFromChild = getLSTObjectFromChild(obj);
		if (lstObjectFromChild == null) {
			return ""; //$NON-NLS-1$
		}
		return switch (lstObjectFromChild) {
			case final Aussenelementansteuerung aea -> getLSTObjectDesignation(
					aea);
			case final Bedien_Einrichtung_Oertlich beo -> getLSTObjectDesignation(
					beo);
			case final Block_Element blockElement -> getLSTObjectDesignation(
					blockElement);
			case final ESTW_Zentraleinheit estw -> getLSTObjectDesignation(
					estw);
			case final FMA_Anlage anlage -> FmaAnlageExtensions
					.getBzBezeichner(anlage);
			case final FMA_Komponente fmaKomponent -> getLSTObjectDesignation(
					fmaKomponent);
			case final Fla_Zwieschutz fla -> getLSTObjectDesignation(fla);
			case final Fstr_Aneinander fstrAneiander -> getLSTObjectDesignation(
					fstrAneiander);
			case final Fstr_DWeg dweg -> getLSTObjectDesignation(dweg);
			case final Fstr_Zug_Rangier fstrZR -> getLSTObjectDesignation(
					fstrZR);
			case final Gleis_Bezeichnung gleis -> getLSTObjectDesignation(
					gleis);
			case final Markanter_Punkt markanter -> getLSTObjectDesignation(
					markanter);
			case final NB_Zone nbZone -> getLSTObjectDesignation(nbZone);
			case final Schloss schloss -> getLSTObjectDesignation(schloss);
			case final Signal signal -> getLSTObjectDesignation(signal);
			case final Signal_Signalbegriff signalbegriff -> getLSTObjectDesignation(
					signalbegriff);
			case final Technik_Standort ts -> getLSTObjectDesignation(ts);
			case final Uebertragungsweg uebertragungsweg -> getLSTObjectDesignation(
					uebertragungsweg);
			case final PZB_Element pzb -> getLSTObjectDesignation(pzb);
			case final W_Kr_Gsp_Element gspElement -> getLSTObjectDesignation(
					gspElement);
			case final W_Kr_Gsp_Komponente gspKomponent -> getLSTObjectDesignation(
					gspKomponent);
			case final Zugeinwirkung ein -> getLSTObjectDesignation(ein);
			default -> ""; //$NON-NLS-1$
		};
	}

	/**
	 * @param aea
	 *            the {@link Aussenelementansteuerung}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(
			final Aussenelementansteuerung aea) {
		return AussenelementansteuerungExtensions.getElementBezeichnung(aea);
	}

	/**
	 * @param beo
	 *            the {@link Bedien_Einrichtung_Oertlich}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(
			final Bedien_Einrichtung_Oertlich beo) {
		return getEmptyStringWhenNull(beo,
				b -> b.getBezeichnung().getBedienEinrichtOertlBez().getWert());
	}

	/**
	 * @param blockElement
	 *            the {@link Block_Element}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(
			final Block_Element blockElement) {
		// TODO
		return ""; //$NON-NLS-1$
	}

	/**
	 * @param estw
	 *            the {@link ESTW_Zentraleinheit}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(
			final ESTW_Zentraleinheit estw) {
		return AussenelementansteuerungExtensions.getElementBezeichnung(estw);
	}

	/**
	 * @param fmaKomponent
	 *            the {@link FMA_Komponente}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(
			final FMA_Komponente fmaKomponent) {
		return getEmptyStringWhenNull(fmaKomponent,
				f -> f.getBezeichnung().getBezeichnungTabelle().getWert());
	}

	/**
	 * @param fla
	 *            the {@link Fla_Zwieschutz}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(final Fla_Zwieschutz fla) {
		// TODO
		return ""; //$NON-NLS-1$
	}

	/**
	 * @param fstrAneiander
	 *            the {@link Fstr_Aneinander}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(
			final Fstr_Aneinander fstrAneiander) {
		return FstrAneinanderExtensions.getTableDescription(fstrAneiander);
	}

	/**
	 * @param dweg
	 *            the {@link Fstr_DWeg}
	 * @return the start and end designation of the DWeg
	 */
	public static String getLSTObjectDesignation(final Fstr_DWeg dweg) {
		final Fstr_Fahrweg fstrFahrweg = DwegExtensions.getFstrFahrweg(dweg);
		final String startBezeichnung = getLSTObjectDesignation(
				FahrwegExtensions.getStart(fstrFahrweg));
		final String dwegDesignation = EObjectExtensions.getNullableObject(dweg,
				f -> f.getBezeichnung().getBezeichnungFstrDWeg().getWert())
				.orElse(""); //$NON-NLS-1$

		if (!startBezeichnung.isEmpty()) {
			return startBezeichnung + " " + dwegDesignation; //$NON-NLS-1$
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * @param fstrZR
	 *            the {@link Fstr_Zug_Rangier}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(
			final Fstr_Zug_Rangier fstrZR) {
		return getEmptyStringWhenNull(fstrZR, f -> FstrZugRangierExtensions
				.getFstrZugRangierBezeichnung(fstrZR));
	}

	/**
	 * @param gleis
	 *            the {@link Gleis_Bezeichnung}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(
			final Gleis_Bezeichnung gleis) {
		// TODO
		return ""; //$NON-NLS-1$
	}

	/**
	 * @param markanter
	 *            the {@link Markanter_Punkt}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(
			final Markanter_Punkt markanter) {
		return getEmptyStringWhenNull(markanter,
				m -> m.getBezeichnung()
						.getBezeichnungMarkanterPunkt()
						.getWert());
	}

	/**
	 * @param nbZone
	 *            the {@link NB_Zone}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(final NB_Zone nbZone) {
		return NbZoneExtensions.getBezeichnung(nbZone);
	}

	/**
	 * @param schloss
	 *            the {@link Schloss}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(final Schloss schloss) {
		return getEmptyStringWhenNull(schloss,
				s -> s.getBezeichnung().getBezeichnungSchloss().getWert());
	}

	/**
	 * @param signal
	 *            the {@link Signal}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(final Signal signal) {
		return getEmptyStringWhenNull(signal,
				s -> s.getBezeichnung().getBezeichnungTabelle().getWert());
	}

	/**
	 * @param signalbegriff
	 *            the {@link Signal_Signalbegriff}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(
			final Signal_Signalbegriff signalbegriff) {
		return getEmptyStringWhenNull(signalbegriff,
				s -> SignalbegriffExtensions
						.getSignalBegriffIDName(s.getSignalbegriffID()));
	}

	/**
	 * @param ts
	 *            the {@link Technik_Standort}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(final Technik_Standort ts) {
		return getEmptyStringWhenNull(ts,
				t -> t.getBezeichnung().getBezeichnungTSO().getWert());
	}

	/**
	 * @param uebertragungsweg
	 *            the {@link Uebertragungsweg}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(
			final Uebertragungsweg uebertragungsweg) {
		// TODO
		return ""; //$NON-NLS-1$
	}

	/**
	 * @param pzb
	 *            the {@link PZB_Element}
	 * @return the designation of the PZB Bezugspunkte and the pzb frequenz
	 */
	public static String getLSTObjectDesignation(final PZB_Element pzb) {
		return Streams
				.stream(PZBElementExtensions.getBezugsElementBezeichnungen(pzb))
				.filter(Objects::nonNull)
				.map(designation -> {
					final Optional<ENUMPZBArt> pzbArt = EObjectExtensions
							.getNullableObject(pzb,
									p -> p.getPZBArt().getWert());
					if (pzbArt.isEmpty()) {
						return designation;
					}
					return String.format("%s %s", designation, //$NON-NLS-1$
							Services.getEnumTranslationService()
									.translate(pzbArt.get()));
				})
				.collect(Collectors.joining(System.lineSeparator()));
	}

	/**
	 * @param gspElement
	 *            the {@link W_Kr_Gsp_Element}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(
			final W_Kr_Gsp_Element gspElement) {
		return getEmptyStringWhenNull(gspElement,
				gsp -> gsp.getBezeichnung().getBezeichnungTabelle().getWert());
	}

	/**
	 * @param gspKomponent
	 *            the {@link W_Kr_Gsp_Komponente}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(
			final W_Kr_Gsp_Komponente gspKomponent) {
		// TODO
		return ""; //$NON-NLS-1$
	}

	/**
	 * @param ein
	 *            the {@link Zugeinwirkung}
	 * @return the object designation
	 */
	public static String getLSTObjectDesignation(final Zugeinwirkung ein) {
		return getEmptyStringWhenNull(ein,
				e -> e.getBezeichnung().getBezeichnungTabelle().getWert());
	}

	private static EObject getLSTObjectFromChild(final EObject child) {
		if (child == null) {
			return null;
		}
		if (child.eContainer() instanceof Container_AttributeGroup) {
			return child;
		}
		return getLSTObjectFromChild(child.eContainer());
	}

	private static <T> String getEmptyStringWhenNull(final T obj,
			final Function<T, String> getDesignationFunc) {
		return EObjectExtensions
				.getNullableObject(obj, getDesignationFunc::apply)
				.orElse(""); //$NON-NLS-1$
	}
}
