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

import java.util.Optional;
import java.util.stream.StreamSupport;

import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Gruppe;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Projekt;
import org.eclipse.set.toolboxmodel.PlanPro.Untergewerk_Art_TypeClass;

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
	 * Get serial number of LSTPlanungEinzel
	 * 
	 * @param planungGruppe
	 *            teh plan group
	 * @return serial number
	 */
	public static Optional<String> getLaufendeNummerAusgabe(
			final Planung_Gruppe planungGruppe) {
		return Optional.ofNullable(planungGruppe.getLSTPlanungEinzel()
				.getPlanungEAllg().getLaufendeNummerAusgabe().getWert());
	}

	/**
	 * Find {@link Planung_Gruppe} through {@link Untergewerk_Art_TypeClass}
	 * 
	 * @param schnittStelle
	 *            the model
	 * @return the plang group
	 */
	public static Optional<Planung_Gruppe> getPlanungGruppe(
			final PlanPro_Schnittstelle schnittStelle) {
		return getPlanungGruppe(schnittStelle, null);
	}

	/**
	 * Find {@link Planung_Gruppe} through {@link Untergewerk_Art_TypeClass}
	 * 
	 * @param schnittStelle
	 *            the model
	 * @param subworkType
	 *            the type of subwork
	 * @return the plang group
	 */
	public static Optional<Planung_Gruppe> getPlanungGruppe(
			final PlanPro_Schnittstelle schnittStelle,
			final String subworkType) {
		if (subworkType == null || subworkType.isBlank()) {
			final Planung_Projekt planungProjekt = PlanProSchnittstelleExtensions
					.LSTPlanungProjekt(schnittStelle);
			final Planung_Gruppe planungGruppe = PlanungProjektExtensions
					.getPlanungGruppe(planungProjekt);
			return Optional.of(planungGruppe);
		}
		final Optional<Iterable<Planung_Gruppe>> lstPlanungGruppe = PlanProSchnittstelleExtensions
				.getLSTPlanungGruppe(schnittStelle);
		if (lstPlanungGruppe.isEmpty()) {
			return Optional.empty();
		}
		return StreamSupport.stream(lstPlanungGruppe.get().spliterator(), false)
				.filter(group -> group.getPlanungGAllg().getUntergewerkArt()
						.getWert().getLiteral().equals(subworkType))
				.findFirst();
	}

}
