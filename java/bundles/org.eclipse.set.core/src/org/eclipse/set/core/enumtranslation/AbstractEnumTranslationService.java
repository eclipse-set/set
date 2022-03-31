/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.enumtranslation;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.set.basis.Translateable;
import org.eclipse.set.basis.exceptions.NoEnumTranslationFound;
import org.eclipse.set.core.Messages;
import org.eclipse.set.core.services.enumtranslation.EnumTranslation;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.utils.enums.EnumTranslationUtils;

/**
 * Implements an enumerator translation service.
 * 
 * @author Schaefer
 */
public abstract class AbstractEnumTranslationService
		implements EnumTranslationService {

	protected static Field[] getSetDeclaredFields() {
		return Enumerators.class.getDeclaredFields();
	}

	@Inject
	@Translation
	protected Enumerators enumerators;

	@Inject
	@Translation
	Messages messages;

	/**
	 * @return enum translation message
	 */
	public abstract Object getEnumerators();

	@Override
	public EnumTranslation translate(final boolean value)
			throws NoEnumTranslationFound {
		return translate(
				EnumTranslationUtils.getKeyBasis(Boolean.valueOf(value)));
	}

	@Override
	public EnumTranslation translate(final Enumerator obj) {
		final Enumerator enumerator = obj;
		final String enumeratorName = EnumTranslationUtils
				.getKeyBasis(enumerator);

		return translate(enumeratorName);
	}

	@Override
	public <C extends Enumerator> List<EnumTranslation> translate(
			final List<C> enums) {
		return enums.stream().map(e -> translate(e))
				.collect(Collectors.toList());
	}

	@Override
	public EnumTranslation translate(final Translateable translateable) {
		return translate(translateable.getKey());
	}

	private EnumTranslation translate(final String keyBasis) {
		return new EnumTranslation() {

			@Override
			public String getAlternative() {
				return translateSingle(
						EnumTranslationUtils.getKeyAlternative(keyBasis));
			}

			@Override
			public String getKeyBasis() {
				return keyBasis;
			}

			@Override
			public String getPresentation() {
				return translateSingle(
						EnumTranslationUtils.getKeyPresentation(keyBasis));
			}

			@Override
			public String getSorting() {
				return translateSingle(
						EnumTranslationUtils.getKeySorting(keyBasis));
			}
		};
	}

	private String translateApplicationEnum(final String key) {
		try {
			final Field[] declaredFields = Messages.class.getDeclaredFields();
			for (final Field field : declaredFields) {
				final String fieldName = field.getName();
				if (fieldName.equals(key)) {
					return ((String) field.get(messages)).trim();
				}
			}
			throw new NoEnumTranslationFound(key);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return enum translation message field
	 */
	protected abstract Field[] getDeclaredFields();

	protected Enumerators getSetEnumerators() {
		return enumerators;
	}

	String translateSingle(final String key) {
		try {
			final Field[] declaredFields = getDeclaredFields();
			for (final Field field : declaredFields) {
				final String fieldName = field.getName();
				if (fieldName.equals(key)) {
					return ((String) field.get(getEnumerators())).trim();
				}
			}
			return translateApplicationEnum(key);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
