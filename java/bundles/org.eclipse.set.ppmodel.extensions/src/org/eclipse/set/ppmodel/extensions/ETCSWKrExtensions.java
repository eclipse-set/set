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

import org.eclipse.set.model.planpro.Balisentechnik_ETCS.ETCS_W_Kr;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Anlage;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Element;
import org.eclipse.set.model.planpro.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente;

/**
 * Extensions for {@link ETCS_W_Kr}
 */
public class ETCSWKrExtensions extends BasisObjektExtensions {

	/**
	 * @param etcsWKr
	 *            the {@link ETCS_W_Kr}
	 * @return the list of W_Kr_Gsp_Element according to this {@link ETCS_W_Kr}
	 */
	public static List<W_Kr_Gsp_Element> getWKrGspElements(
			final ETCS_W_Kr etcsWKr) {
		final W_Kr_Anlage refWKrAnlage = EObjectExtensions.getNullableObject(
				etcsWKr, wkr -> wkr.getIDWKrAnlage().getValue()).orElse(null);
		if (refWKrAnlage == null) {
			throw new IllegalArgumentException(
					String.format("ETCS_W_KR: %s missing W_Kr_Anlage", //$NON-NLS-1$
							etcsWKr.getIdentitaet().getWert()));
		}
		return WKrAnlageExtensions.getWKrGspElemente(refWKrAnlage);
	}

	/**
	 * @param etcsWKr
	 *            {@link ETCS_W_Kr}
	 * @return the list of {@link W_Kr_Gsp_Komponente} according to this
	 *         {@link ETCS_W_Kr}
	 */
	public static List<W_Kr_Gsp_Komponente> getWKrGspKomponents(
			final ETCS_W_Kr etcsWKr) {
		return getWKrGspElements(etcsWKr).stream()
				.flatMap(gspElement -> WKrGspElementExtensions
						.getWKrGspKomponenten(gspElement).stream())
				.toList();
	}
}
