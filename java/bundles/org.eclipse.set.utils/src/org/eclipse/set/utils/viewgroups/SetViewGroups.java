/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.viewgroups;

import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;
import org.eclipse.set.utils.SetImages;

/**
 * Static access to SET view groups.
 * 
 * @author Schaefer
 */
public class SetViewGroups {

	private static final ToolboxViewGroup DEVELOPMENT;
	private static final ToolboxViewGroup EDIT;
	private static final ToolboxViewGroup EXPORT;
	private static final ToolboxViewGroup INFORMATION;
	private static final ToolboxViewGroup INVISIBLE;
	private static final ToolboxViewGroup SITEPLAN;
	private static final ToolboxViewGroup TABLE;

	static {
		DEVELOPMENT = new ToolboxViewGroup(
				// text
				Messages.SetViewGroups_Development,
				// image
				SetImages.IC_BUILD_BLACK_18DP_1X,
				// isInitiallyExpanded
				false,
				// isDevelopment
				true,
				// isInvisible
				false);
		EDIT = new ToolboxViewGroup(
				// text
				Messages.SetViewGroups_Edit,
				// image
				SetImages.IC_EDIT_BLACK_18DP_1X,
				// isInitiallyExpanded
				false,
				// isDevelopment
				false,
				// isInvisible
				false);
		EXPORT = new ToolboxViewGroup(
				// text
				Messages.SetViewGroups_Export,
				// image
				SetImages.IC_FILE_DOWNLOAD_BLACK_18DP_1X,
				// isInitiallyExpanded
				false,
				// isDevelopment
				false,
				// isInvisible
				false);
		INFORMATION = new ToolboxViewGroup(
				// text
				Messages.SetViewGroups_Information,
				// image
				SetImages.IC_INFO_OUTLINE_BLACK_18DP_1X,
				// isInitiallyExpanded
				true,
				// isDevelopment
				false,
				// isInvisible
				false);
		INVISIBLE = new ToolboxViewGroup(
				// text
				"", //$NON-NLS-1$
				// image
				null,
				// isInitiallyExpanded
				false,
				// isDevelopment
				false,
				// isInvisible
				true);
		SITEPLAN = new ToolboxViewGroup(
				// text
				Messages.SetViewGroups_Siteplan,
				// image
				SetImages.IC_MAP_BLACK_18DP_1X,
				// isInitiallyExpanded
				false,
				// isDevelopment
				false,
				// isInvisible
				false);
		TABLE = new ToolboxViewGroup(
				// text
				Messages.SetViewGroups_Table,
				// image
				SetImages.IC_GRID_ON_BLACK_18DP_1X,
				// isInitiallyExpanded
				false,
				// isDevelopment
				false,
				// isInvisible
				false);
	}

	/**
	 * @return the development view group
	 */
	public static ToolboxViewGroup getDevelopment() {
		return DEVELOPMENT;
	}

	/**
	 * @return the edit view group
	 */
	public static ToolboxViewGroup getEdit() {
		return EDIT;
	}

	/**
	 * @return the export view group
	 */
	public static ToolboxViewGroup getExport() {
		return EXPORT;
	}

	/**
	 * @return the information view group
	 */
	public static ToolboxViewGroup getInformation() {
		return INFORMATION;
	}

	/**
	 * @return the invisible view group
	 */
	public static ToolboxViewGroup getInvisible() {
		return INVISIBLE;
	}

	/**
	 * @return the siteplan view group
	 */
	public static ToolboxViewGroup getSiteplan() {
		return SITEPLAN;
	}

	/**
	 * @return the table view group
	 */
	public static ToolboxViewGroup getTable() {
		return TABLE;
	}
}
