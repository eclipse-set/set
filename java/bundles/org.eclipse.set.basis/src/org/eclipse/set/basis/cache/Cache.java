/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.cache;

import java.util.Collection;

import org.eclipse.set.basis.MissingSupplier;

/**
 * How to retrieve items from a cache.
 * 
 * @author Schaefer
 */
public interface Cache {

	/**
	 * @param <T>
	 *            The type to retrieve from the cache
	 * @param key
	 *            the key
	 * @param valueLoader
	 *            the value loader
	 * 
	 * @return the (possibly cached) value
	 */
	public <T> T get(final String key, final MissingSupplier<T> valueLoader);

	/**
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void set(String key, Object value);

	/**
	 * @return all values present in the cache
	 */
	public Iterable<Object> values();

	/**
	 * @return all keys present in the cache
	 */
	public Collection<String> getKeys();

	/**
	 * @return a string representation of cache statistics (if recorded)
	 */
	public String stats();

	/**
	 * Returns the value associated with key in this cache, or null if there is
	 * no cached value for key.
	 * 
	 * @param key
	 *            the key
	 * 
	 * @return the value or null
	 */
	public Object getIfPresent(String key);

	/**
	 * Invalidates the cache
	 */
	public void invalidate();
}
