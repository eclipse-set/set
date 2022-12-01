/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import org.eclipse.jface.resource.ImageDescriptor;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * This class provides descriptors for images.
 * 
 * @author Schaefer
 */
@SuppressWarnings("nls") // filenames are not translated
public class SetImages extends Images {

	/**
	 * ic_build_black_18dp_1x
	 */
	public static final ImageDescriptor IC_BUILD_BLACK_18DP_1X;

	/**
	 * ic_edit_black_18dp_1x
	 */
	public static final ImageDescriptor IC_EDIT_BLACK_18DP_1X;

	/**
	 * ic_file_download_black_18dp_1x
	 */
	public static final ImageDescriptor IC_FILE_DOWNLOAD_BLACK_18DP_1X;

	/**
	 * ic_format_list_numbered
	 */
	public static final ImageDescriptor IC_FORMAT_LIST_NUMBERED;

	/**
	 * ic_grid_on_black_18dp_1x
	 */
	public static final ImageDescriptor IC_GRID_ON_BLACK_18DP_1X;

	/**
	 * ic_info_outline_black_18dp_1x
	 */
	public static final ImageDescriptor IC_INFO_OUTLINE_BLACK_18DP_1X;

	/**
	 * ic_map_black_18dp_1x
	 */
	public static final ImageDescriptor IC_MAP_BLACK_18DP_1X;

	/**
	 * ic_playlist_add_check_black_18dp_1x
	 */
	public static final ImageDescriptor IC_PLAYLIST_ADD_CHECK_BLACK_18DP_1X;
	/**
	 * ic_update_black_18dp_1x
	 */
	public static final ImageDescriptor IC_UPDATE_BLACK_18DP_1X;

	/**
	 * IC_WARNING_BLACK_18DP_1X
	 */
	public static final ImageDescriptor IC_WARNING_BLACK_18DP_1X;

	/**
	 * IC_WARNING_BLACK_18DP_2X
	 */
	public static final ImageDescriptor IC_WARNING_BLACK_18DP_2X;

	/**
	 * warning_red
	 */
	public static final ImageDescriptor WARNING_RED;

	/**
	 * warning_red_transparent
	 */
	public static final ImageDescriptor WARNING_RED_TRANSPARENT;

	/**
	 * warning_yellow
	 */
	public static final ImageDescriptor WARNING_YELLOW;

	private static final Bundle BUNDLE = FrameworkUtil
			.getBundle(SetImages.class);

	static {
		IC_GRID_ON_BLACK_18DP_1X = createDescriptor(BUNDLE,
				"/icons/ic_grid_on_black_18dp_1x.png");
		IC_INFO_OUTLINE_BLACK_18DP_1X = createDescriptor(BUNDLE,
				"/icons/ic_info_outline_black_18dp_1x.png");
		IC_MAP_BLACK_18DP_1X = createDescriptor(BUNDLE,
				"/icons/ic_map_black_18dp_1x.png");
		IC_PLAYLIST_ADD_CHECK_BLACK_18DP_1X = createDescriptor(BUNDLE,
				"/icons/ic_playlist_add_check_black_18dp_1x.png");
		IC_UPDATE_BLACK_18DP_1X = createDescriptor(BUNDLE,
				"/icons/ic_update_black_18dp_1x.png");
		IC_BUILD_BLACK_18DP_1X = createDescriptor(BUNDLE,
				"/icons/ic_build_black_18dp_1x.png");
		IC_WARNING_BLACK_18DP_1X = createDescriptor(BUNDLE,
				"/icons/ic_warning_black_18dp_1x.png");
		IC_WARNING_BLACK_18DP_2X = createDescriptor(BUNDLE,
				"/icons/ic_warning_black_18dp_2x.png");
		IC_FILE_DOWNLOAD_BLACK_18DP_1X = createDescriptor(BUNDLE,
				"/icons/ic_file_download_black_18dp_1x.png");
		IC_FORMAT_LIST_NUMBERED = createDescriptor(BUNDLE,
				"/icons/baseline_format_list_numbered_black_18dp.png");
		IC_EDIT_BLACK_18DP_1X = createDescriptor(BUNDLE,
				"/icons/ic_edit_black_18dp_1x.png");
		WARNING_RED = createDescriptor(BUNDLE, "/icons/warning_red.png");
		WARNING_RED_TRANSPARENT = createDescriptor(BUNDLE,
				"/icons/warning_red_transparent.png");
		WARNING_YELLOW = createDescriptor(BUNDLE, "/icons/warning_yellow.png");
	}
}
