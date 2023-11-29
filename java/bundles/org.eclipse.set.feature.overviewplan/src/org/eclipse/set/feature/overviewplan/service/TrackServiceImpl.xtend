/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.overviewplan.service

import org.eclipse.set.feature.overviewplan.service.TrackService
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante
import org.eclipse.set.basis.cache.Cache
import org.eclipse.set.core.services.Services
import org.eclipse.set.feature.overviewplan.transformator.TOPKanteMetaData
import org.osgi.service.component.annotations.Component

@Component
class TrackServiceImpl implements TrackService {
	
		/**
	 * The id of the used cache for a caching map for mapping TOPKanteMetadata
	 */
	static final String METADATA_CACHE_ID = "toolbox.cache.overviewplan.trackservice";

	private def Cache getCache() {
		// Cache objects are of type List<GEOKanteMetadata>>
		return Services.cacheService.getCache(METADATA_CACHE_ID)
	}
	
	
	override getTOPKanteMetaData(TOP_Kante topKante) {
		val key = topKante.identitaet.wert
		val value = cache.getIfPresent(key)
		if (value !== null) {
			return value as TOPKanteMetaData
		}
		val metadata = new TOPKanteMetaData(topKante)
		cache.set(key, metadata)
		return metadata
	}
	
}