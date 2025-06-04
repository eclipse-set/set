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
import static org.eclipse.set.ppmodel.extensions.ETCSKnotenExtensions.getKnotenPosition;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.set.basis.graph.TopPath;
import org.eclipse.set.basis.graph.TopPoint;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_Kante;
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_Knoten;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;

/**
 * Extensions for {@link ETCS_Kante}
 * 
 * @author truong
 */
public class ETCSKanteExtensions extends BasisObjektExtensions {
	/**
	 * @param etcsEdge
	 *            the {@link ETCS_Kante}
	 * @return the start {@link ETCS_Knoten}
	 */
	public static ETCS_Knoten getETCSKnotenA(final ETCS_Kante etcsEdge) {
		return getNullableObject(etcsEdge,
				edge -> edge.getIDETCSKnotenA().getValue()).orElse(null);
	}

	/**
	 * @param etcsEdge
	 *            the {@link ETCS_Kante}
	 * @return the end {@link ETCS_Knoten}
	 */
	public static ETCS_Knoten getETCSKnotenB(final ETCS_Kante etcsEdge) {
		return getNullableObject(etcsEdge,
				edge -> edge.getIDETCSKnotenB().getValue()).orElse(null);
	}

	/**
	 * @param etcsEdge
	 *            the {@link ETCS_Kante}
	 * @return the list of {@link TOP_Kante}
	 */
	public static List<TOP_Kante> getTopKante(final ETCS_Kante etcsEdge) {
		return etcsEdge.getIDTOPKante()
				.stream()
				.map(ref -> getNullableObject(ref, e -> e.getValue())
						.orElse(null))
				.filter(Objects::nonNull)
				.toList();
	}

	/**
	 * @param etcsEdge
	 *            the {@link ETCS_Kante}
	 * @return the path between the both {@link ETCS_Knoten} of this ETCS_Kante
	 */
	public static TopPath getETCSKantePath(final ETCS_Kante etcsEdge) {
		final ETCS_Knoten etcsKnotenA = getETCSKnotenA(etcsEdge);
		final ETCS_Knoten etcsKnotenB = getETCSKnotenB(etcsEdge);
		if (etcsKnotenA == null || etcsKnotenB == null) {
			throw new IllegalArgumentException(
					String.format("ETCS_Kante %s is missing ETCS_Knoten", //$NON-NLS-1$
							etcsEdge.getIdentitaet().getWert()));
		}

		final Map<TOP_Kante, BigDecimal> posA = getKnotenPosition(etcsKnotenA);
		final Map<TOP_Kante, BigDecimal> posB = getKnotenPosition(etcsKnotenB);
		return posA.entrySet()
				.stream()
				.map(a -> new TopPoint(a.getKey(), a.getValue()))
				.flatMap(pointA -> posB.entrySet()
						.stream()
						.map(b -> new TopPoint(b.getKey(), b.getValue()))
						.map(pointB -> Services.getTopGraphService()
								.findShortestPath(pointA, pointB)))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.min((p1, p2) -> p1.length().compareTo(p2.length()))
				.orElse(null);
	}

}
