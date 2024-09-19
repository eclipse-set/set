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

import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Ansteuerung_Element.Uebertragungsweg;
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt;
import org.eclipse.set.model.planpro.Gleis.Gleis_Bezeichnung;

/**
 * 
 */
public class UebertragungswegExtensions extends BasisObjektExtensions {

	/**
	 * @param route
	 *            the {@link Gleis_Bezeichnung}
	 * @param area
	 *            the {@link Stell_Bereich}
	 * @return true, if the route belong to the area
	 */
	public static boolean isBelongToControlArea(final Uebertragungsweg route,
			final Stell_Bereich area) {
		final Basis_Objekt fromRoute = getNullableObject(route,
				r -> r.getIDUebertragungswegVon().getValue()).orElse(null);
		final Basis_Objekt toRoute = getNullableObject(route,
				r -> r.getIDUebertragungswegNach().getValue()).orElse(null);
		return StellBereichExtensions.isInControlArea(area, fromRoute)
				|| StellBereichExtensions.isInControlArea(area, toRoute);
	}
}
