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

import java.util.List;
import java.util.Objects;

import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_Kante;
import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_Knoten;
import org.eclipse.set.model.planpro.Geodaten.TOP_Kante;

/**
 * Extensions for {@link ETCS_Kante}
 * 
 * @author truong
 */
public class ECTSKanteExtensions extends BasisObjektExtensions {
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
}
