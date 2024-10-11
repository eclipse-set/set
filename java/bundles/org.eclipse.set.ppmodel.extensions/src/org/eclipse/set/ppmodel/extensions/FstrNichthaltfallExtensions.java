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

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Fahrweg;
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Nichthaltfall;
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage;
import org.eclipse.set.model.planpro.Ortung.FMA_Komponente;

/**
 * Extensions for {@link Fstr_Nichthaltfall}
 * 
 * @author truong
 */
public class FstrNichthaltfallExtensions extends BasisObjektExtensions {

	/**
	 * 
	 * @param fstrNichthaltfall
	 *            the {@link Fstr_Nichthaltfall}
	 * @return the reference {@link FMA_Anlage}
	 */
	public static FMA_Anlage getFmaAnlage(
			final Fstr_Nichthaltfall fstrNichthaltfall) {
		return getNullableObject(fstrNichthaltfall,
				fstr -> fstr.getIDFMAAnlage().getValue()).orElse(null);
	}

	/**
	 * @param fstrNichthaltfall
	 *            the {@link Fstr_Nichthaltfall}
	 * @return the list of {@link FMA_Komponente}, which lie on the
	 *         {@link Fstr_Fahrweg} of this {@link Fstr_Nichthaltfall}
	 */
	public static Set<FMA_Komponente> getFMAKomponentOnFstr(
			final Fstr_Nichthaltfall fstrNichthaltfall) {
		final FMA_Anlage fmaAnlage = getFmaAnlage(fstrNichthaltfall);
		final Fstr_Fahrweg fstrFahrWeg = getNullableObject(fstrNichthaltfall,
				fstr -> fstr.getIDFstrFahrweg().getValue()).orElse(null);
		if (fmaAnlage == null || fstrFahrWeg == null) {
			return Collections.emptySet();
		}

		final Set<FMA_Komponente> fmaKomponenten = FmaAnlageExtensions
				.getFmaKomponenten(fmaAnlage);
		return fmaKomponenten.stream()
				.filter(komponente -> BereichObjektExtensions
						.contains(fstrFahrWeg, komponente))
				.collect(Collectors.toSet());
	}
}
