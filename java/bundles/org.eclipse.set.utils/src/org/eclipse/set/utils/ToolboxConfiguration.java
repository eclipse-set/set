/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.set.basis.ToolboxProperties;
import org.eclipse.set.basis.constants.TextType;
import org.eclipse.swt.widgets.Composite;

/**
 * Interpretation of toolbox properties.
 * 
 * @author Schaefer
 */
public class ToolboxConfiguration {

	private static Path defaultDirectory;

	/**
	 * @return the default NatTable line height
	 */
	public static float getDefaultHeight() {
		return Float.parseFloat(System.getProperty(
				ToolboxProperties.TOOLBOX_DEFAULT_LINE_HEIGHT,
				Float.toString(0.6f)));
	}

	/**
	 * @return the default path
	 */
	public static Path getDefaultPath() {
		if (defaultDirectory != null) {
			return defaultDirectory;
		}

		// read default dir from system properties
		final String dir = System.getProperty(
				ToolboxProperties.TOOLBOX_DEFAULT_DIRECTORY,
				"$USERPROFILE$\\planpro"); //$NON-NLS-1$
		defaultDirectory = Paths
				.get(StringExtensions.expandFromEnvironment(dir));

		// ensure the directory exists
		final File file = defaultDirectory.toFile();
		if (file.exists() || file.mkdirs()) {
			return defaultDirectory;
		}
		throw new RuntimeException("Creation of default directory " //$NON-NLS-1$
				+ defaultDirectory + " failed."); //$NON-NLS-1$
	}

	/**
	 * @return the tables scale factor
	 */
	public static float getTablesScaleFactor() {
		return Float.parseFloat(System.getProperty(
				ToolboxProperties.TABLES_SCALE_FACTOR, Float.toString(50.0f)));
	}

	/**
	 * @param type
	 *            the text type
	 * @param defaultDescription
	 *            the default description
	 * @param parent
	 *            parent composite for freeing resources
	 * 
	 * @return the text attribute
	 */
	public static String getTextAttribute(final TextType type,
			final String defaultDescription, final Composite parent) {
		return System.getProperty(type.getProperty(), defaultDescription);
	}

	/**
	 * @param defaultDescription
	 *            the default description
	 * 
	 * @return the description for the text font
	 */
	public static String getTextFont(final String defaultDescription) {
		return System.getProperty(ToolboxProperties.TEXT_FONT,
				defaultDescription);
	}

	/**
	 * @return the toolbox log file
	 */
	public static String getToolboxLogfile() {
		return System.getProperty(ToolboxProperties.LOGFILE);
	}

	/**
	 * @return the toolbox version
	 */
	public static ToolboxVersion getToolboxVersion() {
		return new ToolboxVersion(
				System.getProperty(ToolboxProperties.DETAIL_VERSION));
	}

	/**
	 * @return the toolbox short name
	 */
	public static String getShortName() {
		return System.getProperty(ToolboxProperties.SHORT_NAME);
	}

	/**
	 * @return whether the toolbox is in development mode
	 */
	public static boolean isDevelopmentMode() {
		return Boolean.parseBoolean(System.getProperty(
				ToolboxProperties.DEVELOPMENT_MODE, Boolean.TRUE.toString()));
	}

	/**
	 * @return whether a test filling should be generated for the pdf export
	 */
	public static boolean isPdfExportTestFilling() {
		return Boolean.parseBoolean(
				System.getProperty(ToolboxProperties.PDF_EXPORT_TEST_FILLING,
						Boolean.FALSE.toString()));
	}

	/**
	 * @return whether the toolbox is in debug mode
	 */
	public static boolean isDebugMode() {
		// Check if, Java Debug Wire Protocol(jdwp) is running
		return ManagementFactory.getRuntimeMXBean().getInputArguments()
				.toString().indexOf("jdwp") > -1; //$NON-NLS-1$
	}
}
