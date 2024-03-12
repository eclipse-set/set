/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.siteplan;

import org.eclipse.set.feature.siteplan.transform.LayoutTransformator;
import org.eclipse.set.utils.ToolboxConfiguration;

/**
 * Helper record for creating the JSON configuration
 *
 * This class must match the ToolboxConfiguration class in the frontend
 * application
 * 
 * @param developmentMode
 *            is in development mode
 * @param mapSources
 *            the map sources
 * @param hereApiKey
 *            the HERE Map Api Key
 * @param mapboxApiKey
 *            the MapBox Api Key
 * @param dop20ApiKey
 *            the DOP20 Api Key
 * @param dop20InternUrl
 *            the DOP20 url
 * @param lodScale
 *            default LOD scale
 * @param exportDPI
 *            default export dpi
 * @param trackWidth
 *            default track width
 * @param trackWidthInterval
 *            the min, max of track with
 * @param baseZoomLevel
 *            default zoom level
 * @param defaultCollisionsEnabled
 *            should colision handle enable
 * @param defaultSheetCutCRS
 *            default sheecut cut crs
 * @param planproModelType
 *            the type of planpro model
 */
public record SiteplanConfiguration(boolean developmentMode, String mapSources,
		String hereApiKey, String mapboxApiKey, String dop20ApiKey,
		String dop20InternUrl, int lodScale, int exportDPI, String trackWidth,
		String trackWidthInterval, int baseZoomLevel,
		boolean defaultCollisionsEnabled, String defaultSheetCutCRS,
		String planproModelType) {
	/**
	 * @param planproModelType
	 *            the type of planpro model
	 */
	public SiteplanConfiguration(final String planproModelType) {
		this(ToolboxConfiguration.isDevelopmentMode(),
				ToolboxConfiguration.getMapSources(),
				ToolboxConfiguration.getHereApiKey(),
				ToolboxConfiguration.getMapboxApiKey(),
				ToolboxConfiguration.getDop20ApiKey(),
				ToolboxConfiguration.getDop20InternUrl(),
				ToolboxConfiguration.getLodScale(),
				ToolboxConfiguration.getExportDPI(),
				ToolboxConfiguration.getTrackWidth(),
				ToolboxConfiguration.getTrackWidthIntervall(),
				ToolboxConfiguration.getBaseZoomLevel(),
				ToolboxConfiguration.getDefaultCollisionsEnabled(),
				LayoutTransformator.selectedCRS == null
						? ToolboxConfiguration.getDefaultSheetCutCRS()
						: LayoutTransformator.selectedCRS.getLiteral(),
				planproModelType);
	}
}
