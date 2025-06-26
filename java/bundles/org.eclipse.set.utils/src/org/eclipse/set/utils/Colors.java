/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.resource.ColorDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * Provides access to predefined colors and functions to interpret color
 * descriptions from system properties.
 * 
 * @author Schaefer
 */
public class Colors {

	/**
	 * the default color for greyed out columns
	 */
	public static final ColorDescriptor DEFAULT_GREYEDOUT_COLOR;

	private static final String BLUE = "blue"; //$NON-NLS-1$
	private static final String DESCRIPTION_FORMAT = "%d,%d,%d"; //$NON-NLS-1$
	private static final String DESCRIPTION_PATTERN = "(?<red>[0-9]+),(?<green>[0-9]+),(?<blue>[0-9]+)"; //$NON-NLS-1$
	private static final String GREEN = "green"; //$NON-NLS-1$
	private static final String RED = "red"; //$NON-NLS-1$

	static {
		final RGB defaultGreyedoutRgb = new RGB(242, 242, 242);
		DEFAULT_GREYEDOUT_COLOR = ColorDescriptor
				.createFrom(defaultGreyedoutRgb);
	}

	/**
	 * Creates a color descriptor for the given color property key.
	 * 
	 * @param key
	 *            the color property key
	 * 
	 * @return the color descriptor
	 */
	public static ColorDescriptor keyToDescriptor(final String key) {
		final RGB rgb = keyToRGB(key);
		return ColorDescriptor.createFrom(rgb);
	}

	/**
	 * Creates a color descriptor for the given color property key.
	 * 
	 * @param key
	 *            the color property key
	 * @param defaultColor
	 *            default color if value for key is missing
	 * 
	 * @return the color descriptor
	 */
	public static ColorDescriptor keyToDescriptor(final String key,
			final Color defaultColor) {
		final RGB rgb = keyToRGB(key, defaultColor);
		return ColorDescriptor.createFrom(rgb);
	}

	/**
	 * Creates RGB instance for the given color property.
	 * 
	 * @param key
	 *            the color property key
	 * 
	 * @return the RGB instance
	 */
	public static RGB keyToRGB(final String key) {
		final String value = System.getProperty(key, toDescription(
				Display.getDefault().getSystemColor(SWT.COLOR_BLACK)));
		return parseRGB(value);
	}

	/**
	 * Creates RGB instance for the given color property.
	 * 
	 * @param key
	 *            the color property key
	 * @param defaultColor
	 *            default color if value for key is missing
	 * 
	 * @return the RGB instance
	 */
	public static RGB keyToRGB(final String key, final Color defaultColor) {
		final String value = System.getProperty(key,
				toDescription(defaultColor));
		return parseRGB(value);
	}

	/**
	 * Creates RGB instance for the given value.
	 * 
	 * @param value
	 *            the value describing the RGB value
	 * 
	 * @return the RGB instance
	 * 
	 * @throws IllegalArgumentException
	 *             if the value does not match the description pattern
	 */
	public static RGB parseRGB(final String value) {
		final Pattern pattern = Pattern.compile(DESCRIPTION_PATTERN);
		final Matcher matcher = pattern.matcher(value);
		if (matcher.matches()) {
			final int red = Integer.parseInt(matcher.group(RED));
			final int green = Integer.parseInt(matcher.group(GREEN));
			final int blue = Integer.parseInt(matcher.group(BLUE));

			return new RGB(red, green, blue);
		}
		throw new IllegalArgumentException(value + " does not match pattern \"" //$NON-NLS-1$
				+ DESCRIPTION_PATTERN + "\""); //$NON-NLS-1$
	}

	/**
	 * Returns the description string for the given color.
	 * 
	 * @param color
	 *            the color
	 * 
	 * @return the description string
	 */
	public static String toDescription(final Color color) {
		final RGB rgb = color.getRGB();
		return String.format(DESCRIPTION_FORMAT, Integer.valueOf(rgb.red),
				Integer.valueOf(rgb.green), Integer.valueOf(rgb.blue));
	}

	/**
	 * Parse RGB color from hex color
	 * 
	 * @param value
	 *            the hex color
	 * @return the RGB color
	 */
	public static RGB parseHexCode(final String value) {
		if (!value.startsWith("#") || value.substring(1).length() != 6) { //$NON-NLS-1$
			throw new IllegalArgumentException("Invalid hex color code"); //$NON-NLS-1$
		}
		final String hex = value.substring(1);
		final int r = Integer.parseInt(hex.substring(0, 2), 16);
		final int g = Integer.parseInt(hex.substring(2, 4), 16);
		final int b = Integer.parseInt(hex.substring(4, 6), 16);
		return new RGB(r, g, b);
	}
}
