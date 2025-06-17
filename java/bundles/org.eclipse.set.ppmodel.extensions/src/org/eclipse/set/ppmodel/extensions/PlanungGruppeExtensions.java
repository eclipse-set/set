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

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.eclipse.set.model.planpro.PlanPro.ENUMUntergewerkArt;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.PlanPro.Planung_G_Allg_AttributeGroup;
import org.eclipse.set.model.planpro.PlanPro.Planung_Gruppe;
import org.eclipse.set.model.planpro.PlanPro.Untergewerk_Art_TypeClass;
import org.eclipse.set.ppmodel.extensions.utils.IterableExtensions;

/**
 * 
 */
public class PlanungGruppeExtensions {

	/**
	 * 
	 */
	private PlanungGruppeExtensions() {
	}

	/**
	 * Get first relevant {@link Planung_Gruppe}
	 * 
	 * @param schnittStelle
	 *            the model
	 * @return the plang group
	 */
	public static Optional<Planung_Gruppe> getPlanungGruppe(
			final PlanPro_Schnittstelle schnittStelle) {
		return getPlanungGruppe(schnittStelle, ""); //$NON-NLS-1$
	}

	/**
	 * Find first relevant {@link Planung_Gruppe} through
	 * {@link Untergewerk_Art_TypeClass}
	 * 
	 * @param schnittStelle
	 *            the model
	 * @param subworkType
	 *            the type of subwork
	 * @return the planing group
	 */
	public static Optional<Planung_Gruppe> getPlanungGruppe(
			final PlanPro_Schnittstelle schnittStelle,
			final ENUMUntergewerkArt subworkType) {
		if (subworkType == null || subworkType.getLiteral() == null) {
			return Optional.empty();
		}
		return getPlanungGruppe(schnittStelle, subworkType.getLiteral(), ""); //$NON-NLS-1$
	}

	/**
	 * Find first relevant {@link Planung_Gruppe} through
	 * {@link Untergewerk_Art_TypeClass}
	 * 
	 * @param schnittStelle
	 *            the model
	 * @param subworkType
	 *            the type of subwork
	 * @return the planing group
	 */
	public static Optional<Planung_Gruppe> getPlanungGruppe(
			final PlanPro_Schnittstelle schnittStelle,
			final String subworkType) {
		return getPlanungGruppe(schnittStelle, subworkType, ""); //$NON-NLS-1$
	}

	/**
	 * Find {@link Planung_Gruppe} through {@link Untergewerk_Art_TypeClass} and
	 * GUID
	 * 
	 * @param schnittStelle
	 *            the model
	 * @param subworkType
	 *            the type of subwork
	 * @param guid
	 *            the group guid
	 * @return the planing group
	 */
	public static Optional<Planung_Gruppe> getPlanungGruppe(
			final PlanPro_Schnittstelle schnittStelle,
			final ENUMUntergewerkArt subworkType, final String guid) {
		return getPlanungGruppe(schnittStelle, subworkType.getLiteral(), guid);
	}

	/**
	 * Find {@link Planung_Gruppe} through {@link Untergewerk_Art_TypeClass} and
	 * GUID
	 * 
	 * @param schnittStelle
	 *            the model
	 * @param subworkType
	 *            the type of subwork
	 * @param guid
	 *            the group guid
	 * @return the planing group
	 */
	public static Optional<Planung_Gruppe> getPlanungGruppe(
			final PlanPro_Schnittstelle schnittStelle, final String subworkType,
			final String guid) {
		final Optional<Iterable<Planung_Gruppe>> lstPlanungGruppes = PlanProSchnittstelleExtensions
				.getLSTPlanungGruppe(schnittStelle);
		if (lstPlanungGruppes.isEmpty()) {
			return Optional.empty();
		}

		if (subworkType == null) {
			return guid.isBlank() || guid.isEmpty()
					? Optional.ofNullable(
							lstPlanungGruppes.get().iterator().next())
					: StreamSupport
							.stream(lstPlanungGruppes.get().spliterator(),
									false)
							.filter(group -> group.getIdentitaet()
									.getWert()
									.equals(guid))
							.findFirst();
		}
		final List<Planung_Gruppe> relevantGroups = StreamSupport
				.stream(lstPlanungGruppes.get().spliterator(), false)
				.filter(group -> group.getPlanungGAllg()
						.getUntergewerkArt()
						.getWert()
						.getLiteral()
						.equals(subworkType))
				.toList();
		return guid.isBlank() || guid.isEmpty()
				? Optional.ofNullable(
						IterableExtensions.getFirstOrNull(relevantGroups))
				: relevantGroups.stream()
						.filter(group -> group.getIdentitaet()
								.getWert()
								.equals(guid))
						.findFirst();
	}

	/**
	 * @param group
	 *            the planing group
	 * @return the subwork type of this group
	 */
	public static ENUMUntergewerkArt getUntergewerkArt(
			final Planung_Gruppe group) {
		final Planung_G_Allg_AttributeGroup planungGAllg = group
				.getPlanungGAllg();
		if (planungGAllg == null) {
			return null;
		}
		final Untergewerk_Art_TypeClass untergewerkArt = planungGAllg
				.getUntergewerkArt();
		if (untergewerkArt == null) {
			return null;
		}
		return untergewerkArt.getWert();
	}
}
