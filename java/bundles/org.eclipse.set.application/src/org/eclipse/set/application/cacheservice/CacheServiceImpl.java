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

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.core.services.cache.CacheService;
import org.osgi.service.event.EventHandler;

/**
 * Implementation of {@link CacheService}.
 * 
 * @author Schaefer
 */
public class CacheServiceImpl implements CacheService {
	private IEventBroker broker;

	private final EventHandler modelChangedHandler = event -> {
		// Invalidate all caches if the model is changed
		this.invalidate();
	};

	/**
	 * Sets the event broker and registers the cache service to toolbox events
	 * 
	 * Workaround for PLANPRO-3270
	 * 
	 * @param broker
	 *            the event broker
	 */
	public void setEventBroker(final IEventBroker broker) {
		if (this.broker != null) {
			this.broker.unsubscribe(modelChangedHandler);
		}
		this.broker = broker;
		if (this.broker != null) {
			this.broker.subscribe(Events.MODEL_CHANGED, modelChangedHandler);
		}
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
}
