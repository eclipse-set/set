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
import static org.eclipse.set.ppmodel.extensions.ESTW_ZentraleinheitExtensions.getBedienBezirkVirtuell;
import static org.eclipse.set.ppmodel.extensions.ESTW_ZentraleinheitExtensions.getBedienBezirkZentral;

import java.util.Objects;
import java.util.stream.StreamSupport;

import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich;
import org.eclipse.set.model.planpro.Bedienung.Bedien_Zentrale;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;

/**
 * 
 */
public class BedienZentraleExtensions extends BasisObjektExtensions {
	/**
	 * @param center
	 *            the {@link Bedien_Zentrale}
	 * @param area
	 *            the {@link Stell_Bereich}
	 * @return true, if the control center belong to the area
	 */
	public static boolean isRelevantControlArea(final Bedien_Zentrale center,
			final Stell_Bereich area) {
		final MultiContainer_AttributeGroup container = getContainer(center);
		return StreamSupport
				.stream(container.getESTWZentraleinheit().spliterator(), false)
				.filter(Objects::nonNull)
				.filter(estw -> getNullableObject(estw,
						r -> getBedienBezirkVirtuell(r).getIDBedienZentrale()
								.getValue()).orElse(null) == center
						|| getNullableObject(estw,
								r -> getBedienBezirkZentral(r)
										.getIDBedienZentrale().getValue())
												.orElse(null) == center)
				.anyMatch(estw -> ESTW_ZentraleinheitExtensions
						.isRelevantControlArea(estw, area));

	}

}
