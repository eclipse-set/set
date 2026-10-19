/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.plaz.viewgroups;

import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;
import org.eclipse.set.utils.SetImages;

/**
 * Static access to PlaZ view groups.
 * 
 * @author Stuecker
 */
public class PlazViewGroups {
	private PlazViewGroups() {
	}

	private static final ToolboxViewGroup PLAZ;

	static {
		PLAZ = new ToolboxViewGroup.Builder()
				.withText(Messages.PlazViewGroups_Plaz)
				.withIcon(SetImages.IC_PLAYLIST_ADD_CHECK_BLACK_18DP_1X)
				.withOrderPriority(150).build();
	}

	/**
	 * @return the PlaZ view group
	 */
	public static ToolboxViewGroup getPlaz() {
		return PLAZ;
	}
}