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
	 * Property key for show toolbox news at start up.
	 */
	public static final String SHOW_NEWS = "showNews";

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
}
