/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.cache;

import java.util.ArrayList;

import org.eclipse.set.basis.MissingSupplier;

/**
 * A cache without caching for test purposes.
 * 
 * @author Schaefer
 */
public class NoCache implements Cache {

	@Override
	public <T> T get(final String key, final MissingSupplier<T> valueLoader) {
		return valueLoader.get();
	}

	@Override
	public void set(final String key, final Object value) {
		// do nothing
	}

	@Override
	public String stats() {
		return "this is no cache"; //$NON-NLS-1$
	}

	@Override
	public Object getIfPresent(final String key) {
		return null;
	}

	@Override
	public Iterable<Object> values() {
		return new ArrayList<>();
	}

	@Override
	public void invalidate() {
		// do nothing
	}
}
