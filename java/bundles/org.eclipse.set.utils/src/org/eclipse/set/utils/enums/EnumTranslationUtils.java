/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.enums;

import org.eclipse.emf.common.util.Enumerator;

/**
 * Utility for enumeration translations.
 * 
 * @author Schaefer
 */
public class EnumTranslationUtils {

	private static final String ALTERNATIVE = "_Alternative"; //$NON-NLS-1$
	private static final String PRESENTATION = "_Presentation"; //$NON-NLS-1$
	private static final String SORTING = "_Sorting"; //$NON-NLS-1$
	private static final String XSBOOLEAN = "XSBoolean_"; //$NON-NLS-1$

	/**
	 * @param keyBasis
	 *            the key basis
	 * 
	 * @return the key for lookup the alternative value
	 */
	public static String getKeyAlternative(final String keyBasis) {
		return keyBasis + ALTERNATIVE;
	}

	/**
	 * @param value
	 *            the boolean value
	 * 
	 * @return the key basis for lookup the boolean value
	 */
	public static String getKeyBasis(final Boolean value) {
		return XSBOOLEAN + value.toString().toLowerCase();
	}

	/**
	 * @param enumerator
	 *            the enumerator value
	 * 
	 * @return the key basis for lookup the enumerator value
	 */
	public static String getKeyBasis(final Enumerator enumerator) {
		return transformToKey(enumerator.getName());
	}

	/**
	 * @param enumType
	 *            the enum type
	 * @param enumValue
	 *            the enum value
	 * 
	 * @return the key basis
	 */
	public static String getKeyBasis(final String enumType,
			final String enumValue) {
		final String key = enumType + "_" + enumValue; //$NON-NLS-1$
		return transformToKey(key);
	}

	/**
	 * @param keyBasis
	 *            the key basis
	 * 
	 * @return the key for lookup the presentation value
	 */
	public static String getKeyPresentation(final String keyBasis) {
		return keyBasis + PRESENTATION;
	}

	/**
	 * @param keyBasis
	 *            the key basis
	 * 
	 * @return the key for lookup the sorting value
	 */
	public static String getKeySorting(final String keyBasis) {
		return keyBasis + SORTING;
	}

	private static String firstUpper(final String value) {
		return value.substring(0, 1).toUpperCase() + value.substring(1);
	}

	private static String replaceWithCase(final String value, final String from,
			final String to) {
		final String fromLowerCase = from.toLowerCase();
		final String fromUpperCase = firstUpper(from);
		final String toLowerCase = to.toLowerCase();
		final String toUpperCase = firstUpper(to);
		final String replaceLower = value.replace(fromLowerCase, toLowerCase);
		return replaceLower.replace(fromUpperCase, toUpperCase);
	}

	@SuppressWarnings("nls")
	private static String transformToKey(final String key) {
		String result = new String(key);
		result = replaceWithCase(result, "ä", "ae");
		result = replaceWithCase(result, "ö", "oe");
		result = replaceWithCase(result, "ü", "ue");
		result = result.replace("ß", "ss");
		result = result.replace(" ", "_");
		result = result.replace("-", "_");
		result = result.replace("(", "_");
		result = result.replace(")", "_");
		result = result.replace("/", "_");
		result = result.replace(".", "_");
		result = result.replace("+", "P");
		result = result.replace("-", "M");
		return result;
	}
}
