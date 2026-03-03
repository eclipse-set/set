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
	private static final ToolboxViewGroup TABLE_ESTW;
	private static final ToolboxViewGroup TABLE_ETCS;
	private static final ToolboxViewGroup TABLE_ESTW_SUPPLEMENT;
	private static final ToolboxViewGroup TABLE_SUPPLEMENT;

	static {
		DEVELOPMENT = new ToolboxViewGroup.Builder()
				.withText(Messages.SetViewGroups_Development)
				.withIcon(SetImages.IC_BUILD_BLACK_18DP_1X)
				.withOrderPriority(100)
				.setDevelopment(true)
				.build();
		EDIT = new ToolboxViewGroup.Builder()
				.withText(Messages.SetViewGroups_Edit)
				.withIcon(SetImages.IC_EDIT_BLACK_18DP_1X)
				.withOrderPriority(500)
				.build();
		EXPORT = new ToolboxViewGroup.Builder()
				.withText(Messages.SetViewGroups_Export)
				.withIcon(SetImages.IC_FILE_DOWNLOAD_BLACK_18DP_1X)
				.withOrderPriority(150)
				.build();

		INFORMATION = new ToolboxViewGroup.Builder()
				.withText(Messages.SetViewGroups_Information)
				.withIcon(SetImages.IC_INFO_OUTLINE_BLACK_18DP_1X)
				.withOrderPriority(600)
				.setInitiallyExpanded(true)
				.build();

		INVISIBLE = new ToolboxViewGroup.Builder()
				.withText(Messages.SetViewGroups_Information)
				.withIcon(SetImages.IC_INFO_OUTLINE_BLACK_18DP_1X)
				.setInvisible(true)
				.build();

		SITEPLAN = new ToolboxViewGroup.Builder()
				.withText(Messages.SetViewGroups_Siteplan)
				.withIcon(SetImages.IC_MAP_BLACK_18DP_1X)
				.withOrderPriority(400)
				.build();
		TABLE_ESTW = new ToolboxViewGroup.Builder()
				.withText(Messages.SetViewGroups_Table_ESTW)
				.withIcon(SetImages.IC_GRID_ON_BLACK_18DP_1X)
				.withOrderPriority(300)
				.build();
		TABLE_ETCS = new ToolboxViewGroup.Builder()
				.withText(Messages.SetViewGroups_Table_ETCS)
				.withIcon(SetImages.IC_GRID_ON_BLACK_18DP_1X)
				.withOrderPriority(250)
				.build();
		TABLE_ESTW_SUPPLEMENT = new ToolboxViewGroup.Builder()
				.withText(Messages.SetViewGroups_Table_ESTW_Supplement)
				.withIcon(SetImages.IC_GRID_ON_BLACK_18DP_1X)
				.withOrderPriority(200)
				.build();
		TABLE_SUPPLEMENT = new ToolboxViewGroup.Builder()
				.withText(Messages.SetViewGroups_Table_Supplement)
				.withIcon(SetImages.IC_GRID_ON_BLACK_18DP_1X)
				.withOrderPriority(170)
				.build();

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
	 * @return the table estw view group
	 */
	public static ToolboxViewGroup getTable_ESTW() {
		return TABLE_ESTW;
	}

	/**
	 * @return the table etcs view group
	 */
	public static ToolboxViewGroup getTable_ETCS() {
		return TABLE_ETCS;
	}

	/**
	 * @return the table estw supplement view group
	 */
	public static ToolboxViewGroup getTable_ESTW_Supplement() {
		return TABLE_ESTW_SUPPLEMENT;
	}

	/**
	 * @return table table supplement view group
	 */
	public static ToolboxViewGroup getTable_Supplement() {
		return TABLE_SUPPLEMENT;
	}
}
