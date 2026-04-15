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
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.set.basis.Translateable;
import org.eclipse.set.basis.exceptions.NoEnumTranslationFound;
import org.eclipse.set.core.Messages;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.enumtranslation.EnumTranslation;
import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.model.planpro.BasisTypen.BasisAttribut_AttributeGroup;
import org.eclipse.set.utils.enums.EnumTranslationUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements an enumerator translation service.
 * 
 * @author Schaefer
 */
@Component
public class EnumTranslationServiceImpl implements EnumTranslationService {
	Logger logger = LoggerFactory.getLogger(EnumTranslationServiceImpl.class);
	@Reference
	Enumerators enumerators;

	@Reference
	Messages messages;

	@Activate
	private void active() {
		Services.setEnumTranslationServie(this);
	}

	protected static Field[] getDeclaredFields() {
		return Enumerators.class.getDeclaredFields();
	}

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
	public EnumTranslation translate(final BasisAttribut_AttributeGroup owner,
			final Enumerator enumerator) {
		final EnumTranslation translate = translate(enumerator);

		if (!translate.getAlternative().equals("sonstige")) { //$NON-NLS-1$
			return translate;
		}
		final String notes = owner.getIDBearbeitungsvermerk()
				.stream()
				.map(note -> Optional.ofNullable(note.getValue()
						.getBearbeitungsvermerkAllg()
						.getKurztext()
						.getWert()))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.joining(",\r\n")); //$NON-NLS-1$
		if (notes.isBlank() || notes.isEmpty()) {
			return translate;
		}
		return new EnumTranslation() {

			@Override
			public String getSorting() {
				return notes;
			}

			@Override
			public String getPresentation() {
				return notes;
			}

			@Override
			public String getKeyBasis() {
				return "sonstige"; //$NON-NLS-1$
			}

			@Override
			public String getAlternative() {
				return notes;
			}
		};
	}

	@Override
	public <C extends Enumerator> List<EnumTranslation> translate(
			final List<C> enums) {
		return enums.stream().map(this::translate).toList();
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
			// Return ENUM value, when no translate found
			logger.error("Missing translation for ENUM {}", key); //$NON-NLS-1$
			return key;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	String translateSingle(final String key) {
		try {
			final Field[] declaredFields = getDeclaredFields();
			for (final Field field : declaredFields) {
				final String fieldName = field.getName();
				if (fieldName.equals(key)) {
					return ((String) field.get(enumerators)).trim();
				}
			}
			return translateApplicationEnum(key);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
