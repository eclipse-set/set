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

import static org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.intersects;

import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Gleis.Gleis_Bezeichnung;

/**
 * 
 */
public class GleisBezeichnungExtensions extends BasisObjektExtensions {

	/**
	 * @param description
	 *            the track description
	 * @param area
	 *            the control area
	 * @return true, if the {@link Gleis_Bezeichnung} belong to the
	 *         {@link Stell_Bereich}
	 */
	public static boolean isRelevantControlArea(
			final Gleis_Bezeichnung description, final Stell_Bereich area) {
		return intersects(area, description);
	}
}
