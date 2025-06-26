/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.table.internal;

import java.util.Collection;
import java.util.List;

import org.eclipse.set.basis.MissingSupplier;
import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_TOP_Kante_AttributeGroup;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;

/**
 * A Proxy of the directed edge to single points cache dealing with abstract
 * directed edges using arbitrary point types.
 * 
 * @author Schaefer
 */
public class EdgeToPointsCacheProxy implements Cache {

	private final Cache directedEdgeToSinglepointsCache;

	/**
	 * @param schnittstelle
	 *            the {@link PlanPro_Schnittstelle}
	 * @param cacheService
	 *            the cache service
	 */
	public EdgeToPointsCacheProxy(final PlanPro_Schnittstelle schnittstelle,
			final CacheService cacheService) {
		directedEdgeToSinglepointsCache = cacheService.getCache(schnittstelle,
				ToolboxConstants.CacheId.DIRECTED_EDGE_TO_SINGLEPOINTS);
	}

	@Override
	public <T> T get(final String key, final MissingSupplier<T> valueLoader) {
		Object result = getIfPresent(key);

		// We need to call the value loader manually, because cache.get detect
		// recursive calls.
		if (result == null) {
			result = valueLoader.get();
			if (result == MissingSupplier.MISSING_VALUE) {
				return null;
			}
		}
		typeCheck(result);

		// After the above type check we already know, that the value loader
		// returned a List<Punkt_Objekt_TOP_Kante_AttributeGroup> (or at least
		// an empty list of some arbitrary type).
		@SuppressWarnings("unchecked")
		final T t = (T) result;

		// this may be redundant, if the value loader already cached the value
		set(key, t);

		return t;
	}

	private static void typeCheck(final Object result) {
		if (!(result instanceof List)) {
			throw new IllegalArgumentException(
					result == null ? "null" : result.toString()); //$NON-NLS-1$
		}
		final List<?> list = (List<?>) result;
		if (list.isEmpty()) {
			// we cannot test an empty list
			return;
		}
		final Object item = list.get(0);
		if (!(item instanceof Punkt_Objekt_TOP_Kante_AttributeGroup)) {
			throw new IllegalArgumentException(
					item == null ? "null" : item.toString()); //$NON-NLS-1$
		}
	}

	@Override
	public void set(final String key, final Object value) {
		typeCheck(value);
		directedEdgeToSinglepointsCache.set(key, value);
	}

	@Override
	public String stats() {
		return directedEdgeToSinglepointsCache.stats();
	}

	@Override
	public Object getIfPresent(final String key) {
		return directedEdgeToSinglepointsCache.getIfPresent(key);
	}

	@Override
	public Iterable<Object> values() {
		return directedEdgeToSinglepointsCache.values();
	}

	@Override
	public void invalidate() {
		directedEdgeToSinglepointsCache.invalidate();
	}

	@Override
	public Collection<String> getKeys() {
		return directedEdgeToSinglepointsCache.getKeys();
	}
}
