/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.e4.core.internal.services.ResourceBundleHelper;
import org.eclipse.osgi.service.localization.BundleLocalization;
import org.osgi.framework.FrameworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class to build an osgi service for translating arbitrary messages.
 * 
 * Keys from the property files will be matched to members of the deriving
 * class.
 */
public abstract class AbstractMessageService {
	static final Logger logger = LoggerFactory
			.getLogger(AbstractMessageService.class);

	private void setupLocalization(final ResourceBundle resourceBundle)
			throws IllegalAccessException {
		for (final Field field : this.getClass().getDeclaredFields()) {
			if (Modifier.isPublic(field.getModifiers())) {
				try {
					field.set(this, resourceBundle.getString(field.getName()));
				} catch (final MissingResourceException e) {
					logger.error("Missing localization for field {}", //$NON-NLS-1$
							field.getName());
					field.set(this, "MISSING LOCALIZATION"); //$NON-NLS-1$
				}
			}
		}
	}

	/**
	 * Sets up the message service to translate standard bundle i18n contents
	 * from OSGI-INF/l10n/bundle.properties
	 * 
	 * @param bundleLocalization
	 *            The resource bundle
	 * @throws IllegalAccessException
	 */
	protected void setupLocalization(
			final BundleLocalization bundleLocalization)
			throws IllegalAccessException {
		final ResourceBundle resourceBundle = bundleLocalization
				.getLocalization(FrameworkUtil.getBundle(getClass()), null);
		setupLocalization(resourceBundle);
	}

	/**
	 * Sets up the message service to translate using a property file
	 * 
	 * @param uri
	 *            The property file uri
	 * @throws IllegalAccessException
	 */
	protected void setupLocalization(final String uri)
			throws IllegalAccessException {
		final ResourceBundle resourceBundle = ResourceBundleHelper
				.getResourceBundleForUri(uri, Locale.getDefault(), null);
		setupLocalization(resourceBundle);
	}
}
