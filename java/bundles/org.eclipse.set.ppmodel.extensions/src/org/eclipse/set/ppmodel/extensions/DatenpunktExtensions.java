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
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.set.model.planpro.Balisentechnik_ETCS.Datenpunkt;
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_Kante;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;

import com.google.common.collect.Streams;

/**
 * Extensions for {@link Datenpunkt} object
 */
public class DatenpunktExtensions extends BasisObjektExtensions {

	/**
	 * @param dp
	 *            the {@link Datenpunkt}
	 * @return etcs edges of the data point
	 */
	public static Set<ETCS_Kante> getETCSKanten(final Datenpunkt dp) {
		final List<TOP_Kante> dpTopEdges = PunktObjektExtensions
				.getTopKanten(dp);
		return Streams.stream(getContainer(dp).getETCSKante())
				.filter(etcsEdge -> etcsEdge.getIDTOPKante().stream()
						.filter(Objects::nonNull).map(edge -> edge.getValue())
						.filter(Objects::nonNull)
						.anyMatch(dpTopEdges::contains))
				.collect(Collectors.toSet());
	}
}
