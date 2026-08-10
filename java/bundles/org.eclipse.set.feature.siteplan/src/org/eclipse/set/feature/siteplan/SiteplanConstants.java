/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.siteplan;

import java.util.HashMap;
import java.util.Map;

/**
 * Constant for WebSiteplan
 * 
 * @author Truong
 */
@SuppressWarnings("nls")
public class SiteplanConstants {
	private SiteplanConstants() {

	}

	/**
	 * Path of front end siteplan
	 */
	public static final String SITEPLAN_DIRECTORY = "./web/siteplan";

	/**
	 * The id of the siteplan available
	 */
	public static final String SITEPLAN_CACHE_AVAILABLE_ID = "siteplan-available";

	/**
	 * The id of the siteplan transformation
	 */
	public static final String SITEPLAN_TRANSFORMATION_CACHE_ID = "transfomartion";

	/**
	 * 
	 */
	public static final String WINDOW_PLANPRO_NEW_TABLE_TYPE = "window.planproNewTableType";

	/**
	 * The id of signal table
	 */
	public static final String SIGNAL_TABLE = "org.eclipse.set.feature.table.ssks";

	/**
	 * Color of TOP_Kante
	 */
	public static final Map<String, String> TOP_KANTEN_COLOR = new HashMap<>();
}
