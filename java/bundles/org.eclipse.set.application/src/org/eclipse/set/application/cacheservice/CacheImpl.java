/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.cacheservice;

import java.util.Collection;
import java.util.concurrent.ExecutionException;

import org.eclipse.set.basis.MissingSupplier;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.utils.ToolboxConfiguration;

import com.google.common.cache.CacheBuilder;
import com.google.common.util.concurrent.UncheckedExecutionException;

/**
 * How to retrieve items from a cache.
 * 
 * @author Schaefer
 */
public class CacheImpl implements Cache {
	private final String cacheId;
	private final com.google.common.cache.Cache<String, Object> cache;

	/**
	 * @param cacheId
	 *            the cache
	 */
	public CacheImpl(final String cacheId) {
		this.cacheId = cacheId;
		this.cache = buildCache();
	}

	private static com.google.common.cache.Cache<String, Object> buildCache() {
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
		if (ToolboxConfiguration.isDevelopmentMode()) {
			cacheBuilder = cacheBuilder.recordStats();
		}
		return cacheBuilder.build();
	}

	@Override
	public <T> T get(final String key, final MissingSupplier<T> valueLoader) {
		try {
			// As the cache is filled by the MissingSupplier<T>, it must also
			// contain values of type T. To avoid using an unchecked cast in all
			// users of the Cache, cast only once (here).
			@SuppressWarnings("unchecked")
			final T value = (T) cache.get(key, valueLoader::get);
			if (value == MissingSupplier.MISSING_VALUE) {
				// we do not want to cache a missing value, because there may be
				// successive searches with different value loaders
				cache.invalidate(key);
				return null;
			}
			return value;
		} catch (final ExecutionException | UncheckedExecutionException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void set(final String key, final Object value) {
		cache.put(key, value);
	}

	@Override
	public String stats() {
		return this.cacheId + " " + cache.stats().toString(); //$NON-NLS-1$
	}

	@Override
	public Object getIfPresent(final String key) {
		return cache.getIfPresent(key);
	}

	@Override
	public Iterable<Object> values() {
		return cache.asMap().values();
	}

	@Override
	public void invalidate() {
		cache.invalidateAll();
	}

	@Override
	public Collection<String> getKeys() {
		return cache.asMap().keySet();
	}
}
