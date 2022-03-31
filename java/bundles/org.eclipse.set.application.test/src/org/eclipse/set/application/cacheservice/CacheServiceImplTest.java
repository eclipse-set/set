/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.cacheservice;

import org.eclipse.set.basis.cache.Cache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for CacheServiceImpl
 * 
 * IMPROVE: Mock & Test invalidation via IEventBroker
 * 
 * @author Stuecker
 */
@SuppressWarnings("nls")
public class CacheServiceImplTest {
	private static String CACHE_ID_1 = "cacheID 1";
	private static String CACHE_ID_2 = "cacheID 2";
	private static String CONTAINER_ID_1 = "containerID 1";
	private static String CONTAINER_ID_2 = "containerID 2";

	CacheServiceImpl testee;
	Cache givenCache;
	Cache requestedCache;

	/**
	 * Tests whether getCache serves the same cache for the same cache id
	 */
	@Test
	void cacheIdIdentical() {
		givenCacheService();
		givenCache1();
		whenRequestingCache(CACHE_ID_1);
		thenIdenticalCachesArePresent();
	}

	/**
	 * Tests whether getCache serves different caches for different cache ids
	 */
	@Test
	void cacheIdDifferent() {
		givenCacheService();
		givenCache1();
		whenRequestingCache(CACHE_ID_2);
		thenDifferentCachesArePresent();
	}

	/**
	 * Tests whether getContainerCache serves the same cache for the same cache
	 * id and container id
	 */
	@Test
	void containerCacheIdentical() {
		givenCacheService();
		givenContainerCache1();
		whenRequestingContainerCache(CACHE_ID_1, CONTAINER_ID_1);
		thenIdenticalCachesArePresent();
	}

	/**
	 * Tests whether getContainerCache serves a different cache for the same
	 * cache id but different container id
	 */
	@Test
	void containerCacheDifferentContainerId() {
		givenCacheService();
		givenContainerCache1();
		whenRequestingContainerCache(CACHE_ID_1, CONTAINER_ID_2);
		thenDifferentCachesArePresent();
	}

	/**
	 * Tests whether getContainerCache serves the same cache for a different
	 * cache id and an identical container id
	 */
	@Test
	void containerCacheDifferentCacheId() {
		givenCacheService();
		givenContainerCache1();
		whenRequestingContainerCache(CACHE_ID_2, CONTAINER_ID_1);
		thenDifferentCachesArePresent();
	}

	private void givenCacheService() {
		testee = new CacheServiceImpl();
	}

	private void givenCache1() {
		givenCache = testee.getCache(CACHE_ID_1);
	}

	private void givenContainerCache1() {
		givenCache = testee.getCache(CACHE_ID_1, CONTAINER_ID_1);
	}

	private void whenRequestingCache(final String cacheId) {
		requestedCache = testee.getCache(cacheId);
	}

	private void whenRequestingContainerCache(final String cacheId,
			final String containerId) {
		requestedCache = testee.getCache(cacheId, containerId);
	}

	private void thenIdenticalCachesArePresent() {
		Assertions.assertSame(givenCache, requestedCache);
	}

	private void thenDifferentCachesArePresent() {
		Assertions.assertNotSame(givenCache, requestedCache);
	}
}
