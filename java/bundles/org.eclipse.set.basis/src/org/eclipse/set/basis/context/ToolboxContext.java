/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.context;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * A map for context information.
 * 
 * @author Schaefer
 */
public class ToolboxContext {

	private final Map<String, Object> content = Maps.newHashMap();

	/**
	 * @param <T>
	 *            the wanted type
	 * @param key
	 *            the wanted class
	 * 
	 * @return the context object of the wanted type
	 */
	public <T> T get(final Class<T> key) {
		return key.cast(get(key.getName()));
	}

	/**
	 * @param key
	 *            the key
	 * 
	 * @return the context object for the given key
	 */
	public Object get(final String key) {
		return content.get(key);
	}

	/**
	 * Add an context object with the given type to this context.
	 * 
	 * @param <T>
	 *            the wanted type
	 * @param key
	 *            the key
	 * @param object
	 *            the context object
	 */
	public <T> void put(final Class<T> key, final T object) {
		put(key.getName(), object);
	}

	/**
	 * Add an element with the given key to this context.
	 * 
	 * @param key
	 *            the key
	 * @param object
	 *            the context object
	 */
	public void put(final String key, final Object object) {
		content.put(key, object);
	}
}
