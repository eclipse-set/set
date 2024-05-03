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
import org.eclipse.set.basis.cache.NoCache;

/**
 * A cache service without caching for test purposes.
 * 
 * @author Schaefer
 */
public class NoCacheService implements CacheService {

	@Override
	public Cache getCache(final String cacheId)
			throws IllegalArgumentException {
		return new NoCache();
	}

	@Override
	public Cache getCache(final String cacheId, final String containerCacheId)
			throws IllegalArgumentException {
		return new NoCache();
	}

	@Override
	public Boolean existCache(final String cacheID) {
		return Boolean.FALSE;
	}
}
