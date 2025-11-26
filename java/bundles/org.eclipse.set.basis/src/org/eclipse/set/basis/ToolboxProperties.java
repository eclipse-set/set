/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

/**
 * This interface provides constants used in configuration/config.ini.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls") // The provided constants are language independent
public interface ToolboxProperties {

	/**
	 * Property key for the toolbox buildNumber.
	 */
	public static final String BUILDNUMBER = "toolbox.buildNumber";

	/**
	 * Property key for curved geometry tolerance.
	 */
	public static final String CURVED_GEOMETRY_TOLERANCE = "toolbox.curved.geometry.tolerance";

	/**
	 * Property key for the toolbox short name.
	 */
	public static final String SHORT_NAME = "toolbox.shortname";

	/**
	 * Property key for the toolbox detail version.
	 */
	public static final String DETAIL_VERSION = "toolbox.detailversion";

	/**
	 * Property key for the toolbox version
	 */
	public static final String VERSION = "toolbox.version";

	/**
	 * Property key for indicating development mode.
	 */
	public static final String DEVELOPMENT_MODE = "toolbox.development.mode";

	/**
	 * Property key for greyed out style background color
	 */
	public static final String GREYED_OUT_STYLE_BACKGROUND = "toolbox.greyedoutstyle.background";

	/**
	 * Property key for greyed out style foreground color
	 */
	public static final String GREYED_OUT_STYLE_FOREGROUND = "toolbox.greyedoutstyle.foreground";

	/**
	 * Property key for log file (empty name deactivates logging)
	 */
	public static final String LOGFILE = "toolbox.logfile";

	/**
	 * Property key for indicating PDF export test filling.
	 */
	public static final String PDF_EXPORT_TEST_FILLING = "toolbox.export.pdf.test.filling";

	/**
	 * Property key for tables scale factor.
	 */
	public static final String TABLES_SCALE_FACTOR = "toolbox.tables.scale";

	/**
	 * Property key for text font description.
	 */
	public static final String TEXT_FONT = "toolbox.text.font";

	/**
	 * Property key for the temporary directory of the toolbox application.
	 */
	public static final String TMP_BASE_DIR = "toolbox.tmpdir";

	/**
	 * Property key for the default directory of the toolbox application.
	 */
	public static final String TOOLBOX_DEFAULT_DIRECTORY = "toolbox.defaultdir";

	/**
	 * Property key for the default NatTable line height.
	 */
	public static final String TOOLBOX_DEFAULT_LINE_HEIGHT = "toolbox.default.line.height";

	/**
	 * Property key for map sources.
	 */
	public static final String MAP_SOURCES = "toolbox.siteplan.mapsources";

	/**
	 * Property key for HERE API key.
	 */
	public static final String HERE_API_KEY = "toolbox.siteplan.mapsources.here_apikey";

	/**
	 * Property key for HERE ClientID key.
	 */
	public static final String HERE_CLIENT_ID = "toolbox.siteplan.mapsources.here_clientid";

	/**
	 * Property key for MAPBOX API key.
	 */
	public static final String MAPBOX_API_KEY = "toolbox.siteplan.mapsources.mapbox_apikey";

	/**
	 * Property key for DOP 20 API key.
	 */
	public static final String DOP20_API_KEY = "toolbox.siteplan.mapsources.dop20_apikey";

	/**
	 * Property key for DOP20 intern URL.
	 */
	public static final String DOP20_INTERN_URL = "toolbox.siteplan.mapsources.dop20_intern_url";

	/**
	 * Property key for the LOD scale.
	 */
	public static final String LOD_SCALE = "toolbox.siteplan.lod_scale";

	/**
	 * Property key for the export DPI.
	 */
	public static final String EXPORT_DPI = "toolbox.siteplan.export_dpi";

	/**
	 * Property key for track width
	 */
	public static final String TRACK_WIDTH = "toolbox.siteplan.track_width";

	/**
	 * Property key for track width intervall
	 */
	public static final String TRACK_WIDTH_INTERVALL = "toolbox.siteplan.track_width_intervall";

	/**
	 * Property key for base zoom level
	 */
	public static final String BASE_ZOOM_LEVEL = "toolbox.siteplan.base_zoom_level";

	/**
	 * Property key for collisions default enablement state
	 */
	public static final String DEFAULT_COLLISIONS_ENABLED = "toolbox.siteplan.default_collisions_enabled";

	/**
	 * Property key for sheetcut coordinate reference system
	 */
	public static final String DEFAULT_SHEETCUT_CRS = "toolbox.siteplan.sheetcut_crs";

	/**
	 * Property key for the minimum of content in table row group.
	 */
	public static final String TREE_MINIMUN = "toolbox.table.tree.minimum";

	/**
	 * Property key for the maximum bank line length offset to the top path
	 */
	public static final String BANK_LINE_TOP_PATH_OFFSET_LIMIT = "toolbox.bank_line_offset_limit";

	/**
	 * Property key for indicating, the table show only planing elment or not
	 */
	public static final String TABLE_ONLY_PLANING_ELEMENT = "toolbox.table.only.planing.element";

	/**
	 * Property key for siteplan scale at export
	 */
	public static final String EXPORT_SITEPLAN_SCALE = "toolbox.siteplan.export.scale";

	/**
	 * Property key for the tolerance of length offsets when searching a
	 * topological path
	 */
	public static final String PATH_FINDING_TOLERANCE = "toolbox.path_finding_tolerance";

	/**
	 * Property key for the arc step by geometry calculation
	 */
	public static final String GEOMETRY_ARC_STEP_LENGTH = "toolbox.geometry_calculation.arc_step_length_in_meter";

	/**
	 * Property key for the clothoid step by geometry calculation
	 */
	public static final String GEOMETRY_CLOTHOID_STEP_LENGTH = "toolbox.geometry_calculation.clothoid_step_length_in_meter";

	/**
	 * Property key for the bloss step by geometry calculation
	 */
	public static final String GEOMETRY_BLOSS_STEP_LENGTH = "toolbox.geometry_calculation.bloss_step_length_in_meter";
}
