/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.cacheservice;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.basis.constants.Events;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.cache.NoCacheService;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

/**
 * Implementation of {@link CacheService}.
 * 
 * @author Schaefer
 */
@Component(property = { EventConstants.EVENT_TOPIC + "=" + Events.MODEL_CHANGED,
		EventConstants.EVENT_TOPIC + "=" + Events.CLOSE_SESSION }, service = {
				EventHandler.class, CacheService.class })
public class CacheServiceImpl implements CacheService, EventHandler {
	@Reference
	SessionService sessionService;

	/**
	 * Constructor
	 */
	public CacheServiceImpl() {
		Services.setCacheService(this);
		Services.setNoCacheService(new NoCacheService());
	}

	private final Map<ToolboxFileRole, Map<String, Cache>> caches = new ConcurrentHashMap<>();

	@Override
	public Cache getCache(final PlanPro_Schnittstelle schnittstelle,
			final String cacheId) {
		return getCache(getSessionRole(schnittstelle), cacheId);
	}

	@Override
	public Cache getCache(final PlanPro_Schnittstelle schnittstelle,
			final String... cacheId) throws IllegalArgumentException {
		return getCache(getSessionRole(schnittstelle), cacheId);
	}

	private void invalidate(final ToolboxFileRole role) {
		if (role == ToolboxFileRole.SESSION) {
			caches.values()
					.stream()
					.flatMap(ele -> ele.values().stream())
					.forEach(Cache::invalidate);
		} else {
			caches.computeIfPresent(role, (k, v) -> {
				v.values().forEach(Cache::invalidate);
				return v;
			});
		}

	}

	@Override
	public void handleEvent(final Event event) {
		if (event.getTopic().equals(Events.CLOSE_SESSION)) {
			final ToolboxFileRole role = (ToolboxFileRole) event
					.getProperty(IEventBroker.DATA);
			invalidate(role);
			return;
		}

		final IModelSession modelSession = (IModelSession) event
				.getProperty(IEventBroker.DATA);
		// Invalidate all caches if the model is changed
		this.invalidate(modelSession.getToolboxFile().getRole());
	}

	@Override
	public Boolean existCache(final PlanPro_Schnittstelle schnittstelle,
			final String cacheID) {
		return existCache(getSessionRole(schnittstelle), cacheID);
	}

	private ToolboxFileRole getSessionRole(
			final PlanPro_Schnittstelle schnittStelle) {
		final Map<ToolboxFileRole, IModelSession> loadedSessions = sessionService
				.getLoadedSessions();
		final Entry<ToolboxFileRole, IModelSession> targetSession = loadedSessions
				.entrySet()
				.stream()
				.filter(entry -> entry.getValue()
						.getPlanProSchnittstelle() == schnittStelle)
				.findFirst()
				.orElse(null);
		if (targetSession != null) {
			return targetSession.getKey();
		}
		return ToolboxFileRole.SESSION;
	}

	@Override
	public Cache getCache(final ToolboxFileRole role, final String cacheId)
			throws IllegalArgumentException {
		final Map<String, Cache> sessionCaches = caches.computeIfAbsent(role,
				k -> new ConcurrentHashMap<>());
		return sessionCaches.computeIfAbsent(cacheId, CacheImpl::new);
	}

	@Override
	public Cache getCache(final ToolboxFileRole role, final String... cacheId)
			throws IllegalArgumentException {
		final String cacheKey = Arrays.stream(cacheId)
				.collect(Collectors.joining("/")); //$NON-NLS-1$
		return getCache(role, cacheKey);
	}

	@Override
	public Boolean existCache(final ToolboxFileRole role,
			final String cacheID) {
		return Boolean.valueOf(caches.get(role).keySet().contains(cacheID));
	}
}
