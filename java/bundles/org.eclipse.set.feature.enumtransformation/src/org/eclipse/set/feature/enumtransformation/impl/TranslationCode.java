/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.enumtransformation.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.eclipse.set.core.services.enumtranslation.EnumTranslation;
import org.eclipse.set.utils.enums.EnumTranslationUtils;

/**
 * The source code for the translations.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls")
public class TranslationCode {

	private static final String COPYRIGHT_DATE_PATTERN = "uuuu";
	private static final String MESSAGE_ENTRY_PATTERN = "	/**%n" //
			+ "	 * %s%n" //
			+ "	 */%n" //
			+ "	public String %s;%n%n";

	private static final String MESSAGE_FILE = "Enumerators.java";

	private static final String MESSAGES_EPILOGUE_PATTERN = """
				/**
				 * Fehler
				 */
				public String ERROR_Alternative;

				/**
				 * Warnung
				 */
				public String WARNING_Alternative;

				/**
				 * Erfolg
				 */
				public String SUCCESS_Alternative;
			}""";

	private static final String MESSAGES_INTRODUCTION_PATTERN = """
			  /**
			   * Copyright (c) %s DB Netz AG and others.
			   *
			   * All rights reserved. This program and the accompanying materials
			   * are made available under the terms of the Eclipse Public License v2.0
			   * which accompanies this distribution, and is available at
			   * http://www.eclipse.org/legal/epl-v20.html
			   */
			  package org.eclipse.set.core.enumtranslation;

			  import org.eclipse.set.core.AbstractMessageService;
			  import org.osgi.service.component.annotations.Activate;
			  import org.osgi.service.component.annotations.Component;

			  /**
			   * Translations for enumerators.
			   *
			   * @generated
			   */
			  @Component(service = Enumerators.class)
			  public class Enumerators extends AbstractMessageService {
			  		@Activate
			  		private void setupLocalization()
			  				throws IllegalArgumentException, IllegalAccessException {
			  			super.setupLocalization(
			  					"platform:/plugin/org.eclipse.set.core/translation/Enumerators");
			  		}
			""";

	private static final String PROPERTIES_DATE_PATTERN = "uuuu-MM-dd";

	private static final String PROPERTIES_FILE = "Enumerators.properties";

	private static final String PROPERTIES_INTRODUCTION_PATTERN = "# generated %s%n%n";

	private static final String PROPERTIES_EPILOGUE_PATTERN = """
			ERROR_Alternative=Fehler
			WARNING_Alternative=Warnung
			SUCCESS_Alternative=Erfolg
			""";

	private static final String PROPERTY_ENTRY_PATTERN = "%s=%s%n";

	private static String getMessagesEpilogue() {
		return String.format(MESSAGES_EPILOGUE_PATTERN);
	}

	private static String getMessagesIntroduction() {
		final LocalDate now = LocalDate.now();
		return String.format(MESSAGES_INTRODUCTION_PATTERN, now
				.format(DateTimeFormatter.ofPattern(COPYRIGHT_DATE_PATTERN)));
	}

	private static String getPropertiesEpilogue() {
		return PROPERTIES_EPILOGUE_PATTERN;
	}

	private static String getPropertiesIntroduction() {
		final LocalDate now = LocalDate.now();
		return String.format(PROPERTIES_INTRODUCTION_PATTERN, now
				.format(DateTimeFormatter.ofPattern(PROPERTIES_DATE_PATTERN)));
	}

	private static String nullToEmpty(final String value) {
		if (value == null) {
			return "";
		}
		return value;
	}

	private boolean closed = false;

	private final StringBuilder messages = new StringBuilder();

	private final StringBuilder properties = new StringBuilder();

	/**
	 * Create translation code for no enumerations.
	 */
	public TranslationCode() {
		messages.append(getMessagesIntroduction());
		properties.append(getPropertiesIntroduction());
	}

	/**
	 * Add the translation to the code.
	 * 
	 * @param translation
	 *            the enumeration translation
	 */
	public void add(final EnumTranslation translation) {
		if (!closed) {
			appendToMessages(translation);
			appendToProperties(translation);
		} else {
			throw new IllegalStateException("translation code closed");
		}
	}

	/**
	 * @return the message file name
	 */
	@SuppressWarnings("static-method")
	public String getMessageFile() {
		return MESSAGE_FILE;
	}

	/**
	 * @return the code for the messages class
	 */
	public String getMessages() {
		close();
		return messages.toString();
	}

	/**
	 * @return the code for the properties class
	 */
	public String getProperties() {
		close();
		return properties.toString();
	}

	/**
	 * @return the properties file name
	 */
	@SuppressWarnings("static-method")
	public String getPropertiesFile() {
		return PROPERTIES_FILE;
	}

	private void appendToMessages(final EnumTranslation translation) {
		// presentation
		final String presentation = String.format(MESSAGE_ENTRY_PATTERN,
				translation.getPresentation(), EnumTranslationUtils
						.getKeyPresentation(translation.getKeyBasis()));
		messages.append(presentation);

		// sorting
		final String sorting = String.format(MESSAGE_ENTRY_PATTERN,
				translation.getSorting(),
				EnumTranslationUtils.getKeySorting(translation.getKeyBasis()));
		messages.append(sorting);

		// alternative
		final String alternative = String.format(MESSAGE_ENTRY_PATTERN,
				translation.getAlternative(), EnumTranslationUtils
						.getKeyAlternative(translation.getKeyBasis()));
		messages.append(alternative);
	}

	private void appendToProperties(final EnumTranslation translation) {
		// presentation
		final String presentation = String.format(PROPERTY_ENTRY_PATTERN,
				EnumTranslationUtils
						.getKeyPresentation(translation.getKeyBasis()),
				nullToEmpty(translation.getPresentation()));
		properties.append(presentation);

		// sorting
		final String sorting = String.format(PROPERTY_ENTRY_PATTERN,
				EnumTranslationUtils.getKeySorting(translation.getKeyBasis()),
				nullToEmpty(translation.getSorting()));
		properties.append(sorting);

		// alternative
		final String alternative = String.format(PROPERTY_ENTRY_PATTERN,
				EnumTranslationUtils
						.getKeyAlternative(translation.getKeyBasis()),
				nullToEmpty(translation.getAlternative()));
		properties.append(alternative);
	}

	private void close() {
		if (!closed) {
			closed = true;
			messages.append(getMessagesEpilogue());
			properties.append(getPropertiesEpilogue());
		}
	}
}
