/**
 * Copyright (c) 2025 DB InfraGO AG and others
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
import static org.eclipse.set.ppmodel.extensions.TopKanteExtensions.getTOPKnotenA;
import static org.eclipse.set.ppmodel.extensions.TopKanteExtensions.getTOPKnotenB;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_Knoten;
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.Knoten_Auf_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;
import org.eclipse.set.model.planpro.Geodaten.TOP_Knoten;

import com.google.common.collect.Streams;

/**
 * Extensions for {@link ETCS_Knoten}
 */
public class ETCSKnotenExtensions extends BasisObjektExtensions {

	/**
	 * @param etcsNode
	 *            the {@link ETCS_Knoten}
	 * @return the position of ETCS_Knoten on reference TOP_Kantne
	 */
	public static Map<TOP_Kante, BigDecimal> getKnotenPosition(
			final ETCS_Knoten etcsNode) {
		final TOP_Knoten topNode = getNullableObject(etcsNode,
				node -> node.getIDTOPKnoten().getValue()).orElse(null);
		if (topNode != null) {
			return Streams.stream(TopKnotenExtensions.getTopKanten(topNode))
					.map(topEdge -> {
						if (topNode == getTOPKnotenA(topEdge)) {
							return Map.entry(topEdge, BigDecimal.ZERO);
						}
						if (topNode == getTOPKnotenB(topEdge)) {
							return Map.entry(topEdge,
									topEdge.getTOPKanteAllg()
											.getTOPLaenge()
											.getWert());
						}
						throw new IllegalArgumentException(String.format(
								"The TOP_Knoten %s isn't start or end node of TOP_Kante %s", //$NON-NLS-1$
								topNode.getIdentitaet().getWert(),
								topEdge.getIdentitaet().getWert()));
					})
					.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
		}

		final Knoten_Auf_TOP_Kante_AttributeGroup positionOnTopEdge = getNullableObject(
				etcsNode, ETCS_Knoten::getKnotenAufTOPKante).orElse(null);
		if (positionOnTopEdge == null) {
			throw new IllegalArgumentException(String.format(
					"Can't find position of ETCS_Knoten %s on TOP_Kante", //$NON-NLS-1$
					etcsNode.getIdentitaet().getWert()));
		}
		return positionOnTopEdge.getPunktObjektTOPKante()
				.stream()
				.collect(Collectors.toMap(
						potk -> potk.getIDTOPKante().getValue(),
						potk -> potk.getAbstand().getWert()));
	}
}
