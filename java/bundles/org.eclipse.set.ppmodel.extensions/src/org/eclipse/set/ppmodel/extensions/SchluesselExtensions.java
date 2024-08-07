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

import static org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.getContainer;

import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schloss;
import org.eclipse.set.model.planpro.Schluesselabhaengigkeiten.Schluessel;

/**
 * 
 */
public class SchluesselExtensions {

	/**
	 * @param schluessel
	 *            the {@link Schluessel}
	 * @return all {@link Schloss} of this schlussel
	 */
	public static List<Schloss> getSchloesser(final Schluessel schluessel) {
		if (schluessel == null) {
			return Collections.emptyList();
		}
		final Spliterator<Schloss> schlossIterator = getContainer(schluessel)
				.getSchloss().spliterator();
		return StreamSupport.stream(schlossIterator, false)
				.filter(schloss -> schloss.getIDSchluessel() != null && schloss
						.getIDSchluessel().getValue().equals(schluessel))
				.toList();
	}
}
