/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.cache;

import org.eclipse.set.basis.cache.Cache;

/**
 * Provides different caches.
 * 
 * @author Schaefer
 */
public interface CacheService {

	/**
	 * @param cacheId
	 *            the id for the cache
	 * 
	 * @return the cache
	 * 
	 * @throws IllegalArgumentException
	 *             if no cache for the given id is found
	 */
	public Cache getCache(String cacheId) throws IllegalArgumentException;

	/**
	 * @param cacheId
	 *            the id for the cache
	 * @param containerCacheId
	 *            the id for the container to consider
	 * @return the container cache
	 * 
	 * @throws IllegalArgumentException
	 *             if no cache for the given id is found
	 */
	public Cache getCache(String cacheId, String containerCacheId)
			throws IllegalArgumentException;
}
