/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Properties;

import org.eclipse.emf.common.util.ResourceLocator;

/**
 * Resource locator for validation messages.
 * 
 * @author Schaefer
 */
public class ToolboxResourceLocator implements ResourceLocator {

	private static Properties properties;

	private static final String PROPERTY_FILE = ToolboxResourceLocator.class
			.getSimpleName() + ".properties"; //$NON-NLS-1$

	static {
		properties = new Properties();
		try (InputStream input = ToolboxResourceLocator.class
				.getResourceAsStream(PROPERTY_FILE)) {
			properties.load(input);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static String findString(final String key) {
		return properties.getProperty(key);
	}

	private final ResourceLocator superResourceLocator;

	/**
	 * @param resourceLocator
	 *            the super resource locator
	 */
	public ToolboxResourceLocator(final ResourceLocator resourceLocator) {
		superResourceLocator = resourceLocator;
	}

	@Override
	public URL getBaseURL() {
		return superResourceLocator.getBaseURL();
	}

	@Override
	public Object getImage(final String key) {
		return superResourceLocator.getImage(key);
	}

	@Override
	public String getString(final String key) {
		return getString(key, true);
	}

	@Override
	public String getString(final String key, final boolean translate) {
		final String result = findString(key);
		if (result != null) {
			return result;
		}
		return superResourceLocator.getString(key, translate);
	}

	@Override
	public String getString(final String key, final Object[] substitutions) {
		return getString(key, substitutions, true);
	}

	@Override
	public String getString(final String key, final Object[] substitutions,
			final boolean translate) {
		return MessageFormat.format(getString(key, translate), substitutions);
	}
}
