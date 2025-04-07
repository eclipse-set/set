/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.enumtranslation;

import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.set.basis.Translateable;
import org.eclipse.set.basis.exceptions.NoEnumTranslationFound;
import org.eclipse.set.model.planpro.Basisobjekte.Basis_Objekt;

/**
 * This service can translate enumerators.
 * <p>
 * 
 * bundle.properties files
 * 
 * @author Schaefer
 */
public interface EnumTranslationService {

	/**
	 * @param value
	 *            the value
	 * @return the translation
	 * 
	 * @throws NoEnumTranslationFound
	 *             if no translation was found for the given value
	 */
	EnumTranslation translate(boolean value) throws NoEnumTranslationFound;

	/**
	 * @param enumerator
	 *            the enumerator
	 * @return the translation
	 * 
	 * @throws NoEnumTranslationFound
	 *             if no translation was found for the given enumerator
	 */
	EnumTranslation translate(Enumerator enumerator)
			throws NoEnumTranslationFound;

	/**
	 * @param <T>
	 *            the enumerator type
	 * @param enums
	 *            the list of enumerators
	 * 
	 * @return the list of translations
	 */
	<T extends Enumerator> List<EnumTranslation> translate(List<T> enums);

	/**
	 * @param translateable
	 *            the translateable
	 * 
	 * @return the translation
	 */
	EnumTranslation translate(Translateable translateable);

	/**
	 * @param owner
	 *            the {@link Basis_Objekt}
	 * @param obj
	 *            the enum
	 * @return the translation
	 */
	EnumTranslation translate(Basis_Objekt owner, Enumerator obj);
}
