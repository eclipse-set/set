/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.cacheservice;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.cache.NoCacheService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

/**
 * Implementation of {@link CacheService}.
 * 
 * @author Schaefer
 */
@Component(property = EventConstants.EVENT_TOPIC + "="
		+ Events.MODEL_CHANGED, service = { EventHandler.class,
				CacheService.class })
public class CacheServiceImpl implements CacheService, EventHandler {

	/**
	 * Constructor
	 */
	public CacheServiceImpl() {
		Services.setCacheService(this);
		Services.setNoCacheService(new NoCacheService());
	}

	private final Map<String, Cache> caches = new HashMap<>();

	@Override
	public Cache getCache(final String cacheId) {
		caches.computeIfAbsent(cacheId, CacheImpl::new);
		return caches.get(cacheId);
	}

	@Override
	public Cache getCache(final String cacheId, final String containerCacheId)
			throws IllegalArgumentException {
		final String cacheKey = cacheId + "/" + containerCacheId; //$NON-NLS-1$
		caches.computeIfAbsent(cacheKey, CacheImpl::new);
		return caches.get(cacheKey);
	}

	private void invalidate() {
		caches.values().forEach(Cache::invalidate);
	}

	@Override
	public void handleEvent(final Event event) {
		// Invalidate all caches if the model is changed
		this.invalidate();
	}

	@Override
	public Boolean existCache(final String cacheID) {
		return Boolean.valueOf(caches.keySet().contains(cacheID));
	}
}
