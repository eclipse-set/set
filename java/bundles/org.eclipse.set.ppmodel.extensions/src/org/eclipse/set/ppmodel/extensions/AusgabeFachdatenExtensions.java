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

import org.eclipse.set.model.planpro.PlanPro.Ausgabe_Fachdaten;
import org.eclipse.set.model.planpro.PlanPro.ENUMUntergewerkArt;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.model.planpro.PlanPro.Planung_Einzel;
import org.eclipse.set.model.planpro.PlanPro.Planung_Gruppe;

/**
 * Extensions for {@link Ausgabe_Fachdaten}
 */
public class AusgabeFachdatenExtensions {

	/**
	 * 
	 */
	private AusgabeFachdatenExtensions() {
	}

	/**
	 * Get first relevant subwork
	 * 
	 * @param schnittstelle
	 *            the model
	 * @return subwork
	 */
	public static Optional<Ausgabe_Fachdaten> getAusgabeFachdaten(
			final PlanPro_Schnittstelle schnittstelle) {
		return getAusgabeFachdaten(schnittstelle, Optional.empty());
	}

	/**
	 * Get {@link Ausgabe_Fachdaten} through {@link Planung_Gruppe}
	 * 
	 * @param schnittstelle
	 *            the {@link PlanPro_Schnittstelle}
	 * @param planungGruppe
	 *            the {@link Planung_Gruppe}
	 * @return the {@link Ausgabe_Fachdaten}
	 */
	public static Optional<Ausgabe_Fachdaten> getAusgabeFachdaten(
			final PlanPro_Schnittstelle schnittstelle,
			final Planung_Gruppe planungGruppe) {
		return getAusgabeFachdaten(schnittstelle, Optional.of(planungGruppe));
	}

	/**
	 * Get {@link Ausgabe_Fachdaten} through {@link Planung_Gruppe}
	 * 
	 * @param schnittstelle
	 *            the {@link PlanPro_Schnittstelle}
	 * @param planungGruppe
	 *            the {@link Planung_Gruppe}
	 * @return the {@link Ausgabe_Fachdaten}
	 */
	public static Optional<Ausgabe_Fachdaten> getAusgabeFachdaten(
			final PlanPro_Schnittstelle schnittstelle,
			final Optional<Planung_Gruppe> planungGruppe) {
		if (!PlanProSchnittstelleExtensions.isPlanning(schnittstelle)) {
			throw new UnsupportedOperationException(
					"Single state plan not exist subwork"); //$NON-NLS-1$
		}
		if (planungGruppe.isEmpty()) {
			return Optional.of(schnittstelle.getLSTPlanung()
					.getFachdaten()
					.getAusgabeFachdaten()
					.getFirst());
		}
		final Optional<Planung_Einzel> lstPlanungEinzel = Optional
				.ofNullable(planungGruppe.get().getLSTPlanungEinzel());
		if (lstPlanungEinzel.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(PlanungEinzelExtensions
				.getAusgabeFachdaten(lstPlanungEinzel.get()));
	}

	/**
	 * Get first relevant {@link Ausgabe_Fachdaten} through subworktype
	 * 
	 * @param schnittstelle
	 *            the {@link PlanPro_Schnittstelle}
	 * @param subworkType
	 *            the {@link ENUMUntergewerkArt}
	 * @return the {@link Ausgabe_Fachdaten}
	 */
	public static Optional<Ausgabe_Fachdaten> getAusgabeFachdaten(
			final PlanPro_Schnittstelle schnittstelle,
			final ENUMUntergewerkArt subworkType) {
		if (!PlanProSchnittstelleExtensions.isPlanning(schnittstelle)) {
			throw new UnsupportedOperationException(
					"Single state plan not exist subwork"); //$NON-NLS-1$
		}
		return schnittstelle.getLSTPlanung()
				.getFachdaten()
				.getAusgabeFachdaten()
				.stream()
				.filter(subwork -> subwork.getUntergewerkArt() != null
						&& subwork.getUntergewerkArt().getWert() != null)
				.filter(subwork -> subwork.getUntergewerkArt()
						.getWert()
						.equals(subworkType))
				.findFirst();
	}

}
