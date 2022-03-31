/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.cacheservice;

import org.eclipse.set.basis.MissingSupplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for CacheImpl
 * 
 * @author Stuecker
 */
@SuppressWarnings("nls")
public class CacheImplTest {
	private static final String CACHE_ID = "Cache ID";
	private static final String CACHE_KEY = "Dummy cache key";
	private static final String CACHE_VALUE = "Dummy cache value";
	private CacheImpl testee;
	private MissingSupplier<Object> missingSupplier;
	private int missingSupplierCallCounter;
	private Object retrievedValue;

	/**
	 * Test whether the cache caches values rather than calling the missing
	 * supplier twice
	 */
	@Test
	void cacheCachesValues() {
		givenCache();
		givenMissingSupplier();

		whenRetrievingValue(CACHE_KEY);
		whenRetrievingValue(CACHE_KEY);

		thenMissingSupplierIsCalledOnce();
		thenValueMatchesSupplied();
	}

	/**
	 * Test whether the cache caches missing values
	 */
	@Test
	void cacheDoesNotCacheMissingValues() {
		givenCache();
		givenNullMissingSupplier();

		whenRetrievingValue(CACHE_KEY);
		whenRetrievingValue(CACHE_KEY);

		thenMissingSupplierIsCalledTwice();
		thenCacheIsEmpty();
	}

	/**
	 * Test whether Cache::set correctly sets values in the cache
	 */
	@Test
	void setCacheWorks() {
		givenCache();
		givenNullMissingSupplier();

		whenSettingCacheValue();
		whenRetrievingValue(CACHE_KEY);

		thenMissingSupplierIsNotCalled();
		thenValueMatchesSupplied();
		thenCacheContainsValue();
	}

	/**
	 * Test whether cache invalidation works
	 */
	@Test
	void invalidatedCacheIsEmpty() {
		givenCache();
		givenMissingSupplier();

		whenSettingCacheValue();
		whenInvalidatingCache();

		thenCacheIsEmpty();
	}

	private void givenCache() {
		testee = new CacheImpl(CACHE_ID);
	}

	private void givenMissingSupplier() {
		missingSupplierCallCounter = 0;
		missingSupplier = () -> {
			missingSupplierCallCounter++;
			return CACHE_VALUE;
		};
	}

	private void givenNullMissingSupplier() {
		missingSupplierCallCounter = 0;
		missingSupplier = () -> {
			missingSupplierCallCounter++;
			return MissingSupplier.MISSING_VALUE;
		};
	}

	private void whenRetrievingValue(final String cacheKey) {
		retrievedValue = testee.get(cacheKey, missingSupplier);
	}

	private void whenSettingCacheValue() {
		testee.set(CACHE_KEY, CACHE_VALUE);
	}

	private void whenInvalidatingCache() {
		testee.invalidate();
	}

	private void thenMissingSupplierIsCalledOnce() {
		Assertions.assertEquals(missingSupplierCallCounter, 1);
	}

	private void thenMissingSupplierIsCalledTwice() {
		Assertions.assertEquals(missingSupplierCallCounter, 2);
	}

	private void thenMissingSupplierIsNotCalled() {
		Assertions.assertEquals(missingSupplierCallCounter, 0);
	}

	private void thenValueMatchesSupplied() {
		Assertions.assertSame(CACHE_VALUE, retrievedValue);
	}

	private void thenCacheIsEmpty() {
		Assertions.assertFalse(testee.values().iterator().hasNext(),
				"Cache is not empty");
	}

	private void thenCacheContainsValue() {
		Assertions.assertSame(CACHE_VALUE, testee.values().iterator().next(),
				"Cache does not contain value");
	}

}
