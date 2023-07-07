/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.enumtransformation.impl;

import org.eclipse.set.core.services.enumtranslation.EnumTranslation;

/**
 * Implementation for {@link EnumTranslation}.
 * 
 * @author Schaefer
 */

/**
 * @param keyBasis
 *            the key basis for this translation
 * @param presentation
 *            value used for presentation
 * @param alternative
 *            value used for alternative presentation
 * @param sorting
 *            value used for sorting
 */
public record EnumTranslationImpl(String keyBasis, String presentation,
		String alternative, String sorting) implements EnumTranslation {

	@Override
	public String getAlternative() {
		return alternative;
	}

	@Override
	public String getKeyBasis() {
		return keyBasis;
	}

	@Override
	public String getPresentation() {
		return presentation;
	}

	@Override
	public String getSorting() {
		return sorting;
	}
}
