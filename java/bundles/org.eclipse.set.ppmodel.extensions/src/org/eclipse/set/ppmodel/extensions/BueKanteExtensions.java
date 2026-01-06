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
package org.eclipse.set.ppmodel.extensions;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.set.model.planpro.Bahnuebergang.BUE_Kante;
import org.eclipse.set.model.planpro.Gleis.Gleis_Bezeichnung;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;

import com.google.common.collect.Streams;

/**
 * Extensions for {@link BUE_Kante}
 */
public class BueKanteExtensions extends BasisObjektExtensions {

	/**
	 * Get table designation of {@link BUE_Kante}
	 * 
	 * @param bueKante
	 *            the {@link BUE_Kante}
	 * @return the designation
	 */
	@SuppressWarnings("nls")
	public static String getBezeichnung(final BUE_Kante bueKante) {
		final MultiContainer_AttributeGroup container = getContainer(bueKante);
		final List<Gleis_Bezeichnung> relevantGleisBezeichnung = Streams
				.stream(container.getGleisBezeichnung())
				.filter(bo -> BereichObjektExtensions.contains(bo, bueKante))
				.filter(Objects::nonNull)
				.toList();
		final List<String> gleisBezeichnung = relevantGleisBezeichnung.stream()
				.map(ele -> EObjectExtensions
						.getNullableObject(ele,
								e -> e.getBezeichnung()
										.getBezGleisBezeichnung()
										.getWert())
						.orElse(null))
				.filter(Objects::nonNull)
				.toList();

		final String bueAnlageBezeichnung = Optional
				.ofNullable(BueAnlageExtensions
						.getBezeichnung(bueKante.getIDBUEAnlage().getValue()))
				.orElse("");
		final StringBuilder builder = new StringBuilder();
		builder.append("BÜ-K ");
		builder.append(bueAnlageBezeichnung);
		if (!gleisBezeichnung.isEmpty()) {
			builder.append(", Gl. ");
			builder.append(gleisBezeichnung.stream()
					.collect(Collectors.joining(", ")));
		}
		return builder.toString();
	}
}
