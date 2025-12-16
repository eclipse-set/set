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
@SuppressWarnings("nls")
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
				"$USERPROFILE$\\planpro");
		defaultDirectory = Paths
				.get(StringExtensions.expandFromEnvironment(dir));

		// ensure the directory exists
		final File file = defaultDirectory.toFile();
		if (file.exists() || file.mkdirs()) {
			return defaultDirectory;
		}
		throw new RuntimeException("Creation of default directory "
				+ defaultDirectory + " failed.");
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
	 * @return the minimum content of row group
	 */
	public static int getTableTreeMinimum() {
		final String property = System
				.getProperty(ToolboxProperties.TREE_MINIMUN);
		try {
			return Integer.parseInt(property);
		} catch (final NumberFormatException e) {
			return 5;
		}
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
				ToolboxProperties.DEVELOPMENT_MODE, Boolean.FALSE.toString()));
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
		return ManagementFactory.getRuntimeMXBean()
				.getInputArguments()
				.toString()
				.indexOf("jdwp") > -1; //$NON-NLS-1$
	}

	private static final String MAP_SOURCES_DEFAULT = "BKG_TOPPLUS|BKG_SENT";
	private static final String HERE_API_KEY_DEFAULT = "";
	private static final String HERE_CLIENT_ID_DEFAULT = "";
	private static final String MAPBOX_API_KEY_DEFAULT = "";
	private static final String DOP20_API_KEY_DEFAULT = "";
	private static final String DOP20_INTERN_URL_DEFAULT = "https://sg.geodatenzentrum.de/wms_dop";
	private static final String LOD_SCALE_DEFAULT = "10000";
	private static final String EXPORT_DPI_DEFAULT = "300";
	private static final String TRACK_WIDTH = "3|1.5|0.75|1";
	private static final String TRACK_WIDTH_INTERVALL = "1,10|0.5,8|0.25,3|0.5,3";
	private static final String BASE_ZOOM_LEVEL = "20";
	private static final String DEFAULT_COLLISIONS_ENABLED = "true";
	private static final String DEFAULT_SHEETCUT_CRS = "DR0";
	private static final String BANK_LINE_TOP_PATH_OFFSET_LIMIT = "0.2";
	private static final String EXPORT_SITEPLAN_SCALE_DEFAULT = "1000";
	private static final String PATH_FINDING_TOLERANCE = "0.001";
	private static final String GEOMETRY_ARC_STEP_LENGTH = "0.001"; // meter
	private static final String GEOMETRY_CLOTHOID_STEP_LENGTH = "0.5";
	private static final String GEOMETRY_BLOSS_STEP_LENGTH = "0.5";

	/**
	 * @return a string describing the map sources
	 */
	public static String getMapSources() {
		return System.getProperty(ToolboxProperties.MAP_SOURCES,
				MAP_SOURCES_DEFAULT);
	}

	/**
	 * @return the HERE API key
	 */
	public static String getHereClientID() {
		return System.getProperty(ToolboxProperties.HERE_CLIENT_ID,
				HERE_CLIENT_ID_DEFAULT);
	}

	/**
	 * @return the HERE API key
	 */
	public static String getHereApiKey() {
		return System.getProperty(ToolboxProperties.HERE_API_KEY,
				HERE_API_KEY_DEFAULT);
	}

	/**
	 * @return the HERE API key
	 */
	public static String getMapboxApiKey() {
		return System.getProperty(ToolboxProperties.MAPBOX_API_KEY,
				MAPBOX_API_KEY_DEFAULT);
	}

	/**
	 * @return the DOP 20 API key
	 */
	public static String getDop20ApiKey() {
		return System.getProperty(ToolboxProperties.DOP20_API_KEY,
				DOP20_API_KEY_DEFAULT);
	}

	/**
	 * @return the DOP 20 intern URL
	 */
	public static String getDop20InternUrl() {
		return System.getProperty(ToolboxProperties.DOP20_INTERN_URL,
				DOP20_INTERN_URL_DEFAULT);
	}

	/**
	 * @return the LOD scale
	 */
	public static int getLodScale() {
		return Integer.parseInt(System.getProperty(ToolboxProperties.LOD_SCALE,
				LOD_SCALE_DEFAULT));
	}

	/**
	 * @return the PDF export DPI
	 */
	public static int getExportDPI() {
		return Integer.parseInt(System.getProperty(ToolboxProperties.EXPORT_DPI,
				EXPORT_DPI_DEFAULT));
	}

	/**
	 * @return the siteplan export scale value
	 */
	public static int getSiteplanExportScale() {
		return Integer.parseInt(
				System.getProperty(ToolboxProperties.EXPORT_SITEPLAN_SCALE,
						EXPORT_SITEPLAN_SCALE_DEFAULT));
	}

	/**
	 * @return the default track width
	 */
	public static String getTrackWidth() {
		return System.getProperty(ToolboxProperties.TRACK_WIDTH, TRACK_WIDTH);
	}

	/**
	 * @return the track width intervall
	 */
	public static String getTrackWidthIntervall() {
		return System.getProperty(ToolboxProperties.TRACK_WIDTH_INTERVALL,
				TRACK_WIDTH_INTERVALL);
	}

	/**
	 * @return the base zoom level
	 */
	public static int getBaseZoomLevel() {
		return Integer.parseInt(System.getProperty(
				ToolboxProperties.BASE_ZOOM_LEVEL, BASE_ZOOM_LEVEL));
	}

	/**
	 * @return whether collisions should be enabled by default
	 */
	public static boolean getDefaultCollisionsEnabled() {
		return System
				.getProperty(ToolboxProperties.DEFAULT_COLLISIONS_ENABLED,
						DEFAULT_COLLISIONS_ENABLED)
				.equals("true"); //$NON-NLS-1$
	}

	/**
	 * @return default sheetcut coordinate reference system
	 */
	public static String getDefaultSheetCutCRS() {
		return System.getProperty(ToolboxProperties.DEFAULT_SHEETCUT_CRS,
				DEFAULT_SHEETCUT_CRS);
	}

	/**
	 * @return maximum offset between a banking line length to the topological
	 *         path length
	 */
	public static double getBankLineTopOffsetLimit() {
		return Double.parseDouble(System.getProperty(
				ToolboxProperties.BANK_LINE_TOP_PATH_OFFSET_LIMIT,
				BANK_LINE_TOP_PATH_OFFSET_LIMIT));
	}

	/**
	 * @return tolerance for length offsets when searching a topological path
	 */
	public static double getPathFindingTolerance() {
		return Double.parseDouble(
				System.getProperty(ToolboxProperties.PATH_FINDING_TOLERANCE,
						PATH_FINDING_TOLERANCE));
	}

	/**
	 * @return the step length of the arc geometry
	 */
	public static double getGeometryArcStepLength() {
		return Double.parseDouble(
				System.getProperty(ToolboxProperties.GEOMETRY_ARC_STEP_LENGTH,
						GEOMETRY_ARC_STEP_LENGTH));
	}

	/**
	 * @return the step length of the clothoid geometry
	 */
	public static double getGeometryClothoidStepLength() {
		return Double.parseDouble(System.getProperty(
				ToolboxProperties.GEOMETRY_CLOTHOID_STEP_LENGTH,
				GEOMETRY_CLOTHOID_STEP_LENGTH));
	}

	/**
	 * @return the step length of the bloss geometry
	 */
	public static double getGeometryBlossStepLength() {
		return Double.parseDouble(
				System.getProperty(ToolboxProperties.GEOMETRY_BLOSS_STEP_LENGTH,
						GEOMETRY_BLOSS_STEP_LENGTH));
	}
}
