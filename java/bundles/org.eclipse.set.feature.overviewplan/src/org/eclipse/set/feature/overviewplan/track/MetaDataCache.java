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

package org.eclipse.set.feature.overviewplan.track;

import static org.eclipse.set.ppmodel.extensions.BasisAttributExtensions.getContainer;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante;
import org.eclipse.set.utils.ToolboxConfiguration;

/**
 * Caching {@link TOPKanteMetaData}
 * 
 * @author Truong
 */
public class MetaDataCache {
	/**
	 * The id of the used cache for a caching map for mapping TOPKanteMetadata
	 */
	static final String METADATA_CACHE_ID = "toolbox.cache.overviewplan.trackservice.metadata"; //$NON-NLS-1$
	static final Map<String, TOPKanteMetaData> topCache = new HashMap<>();

	/**
	 * Default Constructor
	 */
	private MetaDataCache() {
	}

	/**
	 * Get storage edge metadata
	 * 
	 * @param edge
	 *            the edge
	 * @return edge metadata
	 */
	public static TOPKanteMetaData getMetaData(final TOP_Kante edge) {
		if (!ToolboxConfiguration.isDevelopmentMode()) {
			final MultiContainer_AttributeGroup container = getContainer(edge);
			final Cache cache = Services.getCacheService()
					.getCache(getCacheString(container));
			return (TOPKanteMetaData) cache
					.getIfPresent(edge.getIdentitaet().getWert());
		}
		return topCache.getOrDefault(edge.getIdentitaet().getWert(), null);
	}

	/**
	 * Storage edge metadata
	 * 
	 * @param edge
	 *            the edge
	 * @param edgeMetadata
	 *            the metadata
	 */
	public static void setMetaData(final TOP_Kante edge,
			final TOPKanteMetaData edgeMetadata) {
		if (!ToolboxConfiguration.isDevelopmentMode()) {
			final MultiContainer_AttributeGroup container = getContainer(edge);
			final Cache cache = Services.getCacheService()
					.getCache(getCacheString(container));
			cache.set(edge.getIdentitaet().getWert(), edgeMetadata);
			return;
		}
		topCache.put(edge.getIdentitaet().getWert(), edgeMetadata);
	}

	/**
	 * @param container
	 *            the container
	 */
	public static void clearCache(
			final MultiContainer_AttributeGroup container) {
		if (!ToolboxConfiguration.isDevelopmentMode()) {
			final Cache cache = Services.getCacheService()
					.getCache(getCacheString(container));
			cache.invalidate();
			return;
		}
		topCache.clear();
	}

	private static String getCacheString(
			final MultiContainer_AttributeGroup container) {
		return METADATA_CACHE_ID + "/" + container.getCacheString(); //$NON-NLS-1$
	}
}
