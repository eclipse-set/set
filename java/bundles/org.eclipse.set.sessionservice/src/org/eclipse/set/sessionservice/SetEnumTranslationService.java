/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.sessionservice;

import java.lang.reflect.Field;

import org.eclipse.set.core.enumtranslation.AbstractEnumTranslationService;
import org.eclipse.set.core.enumtranslation.Enumerators;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;

/**
 * Implementation of {@link EnumTranslationService} for SET product
 * 
 * @author Truong
 *
 */
public class SetEnumTranslationService extends AbstractEnumTranslationService {

	@Override
	public Field[] getDeclaredFields() {
		return getSetDeclaredFields();
	}

	@Override
	public Object getEnumerators() {
		return getSetEnumerators();
	}

	void setEnumerators(final Enumerators enumerators) {
		this.enumerators = enumerators;
	}
}
