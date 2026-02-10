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
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;

/**
 * Provides different caches.
 * 
 * @author Schaefer
 */
public interface CacheService {

	/**
	 * @param schnittstelle
	 *            the {@link PlanPro_Schnittstelle}
	 * @param cacheId
	 *            the id for the cache
	 * 
	 * @return the cache
	 * 
	 * @throws IllegalArgumentException
	 *             if no cache for the given id is found
	 */
	public Cache getCache(PlanPro_Schnittstelle schnittstelle, String cacheId)
			throws IllegalArgumentException;

	/**
	 * @param schnittstelle
	 *            the {@link PlanPro_Schnittstelle}
	 * @param cacheId
	 *            the id for the cache
	 * @return the container cache
	 * 
	 * @throws IllegalArgumentException
	 *             if no cache for the given id is found
	 */
	public Cache getCache(PlanPro_Schnittstelle schnittstelle,
			String... cacheId) throws IllegalArgumentException;

	/**
	 * check if already storage cache
	 * 
	 * @param schnittstelle
	 *            the {@link PlanPro_Schnittstelle}
	 * 
	 * @param cacheID
	 *            the id for the cache
	 * @return true, if the cache already storage
	 */
	public Boolean existCache(PlanPro_Schnittstelle schnittstelle,
			String cacheID);

	/**
	 * @param role
	 *            the {@link ToolboxFileRole}
	 * @param cacheId
	 *            the id for the cache
	 * 
	 * @return the cache
	 * 
	 * @throws IllegalArgumentException
	 *             if no cache for the given id is found
	 */
	public Cache getCache(ToolboxFileRole role, String cacheId)
			throws IllegalArgumentException;

	/**
	 * @param role
	 *            {@link ToolboxFileRole}
	 * @param cacheId
	 *            the id for the cache
	 * @return the container cache
	 * 
	 * @throws IllegalArgumentException
	 *             if no cache for the given id is found
	 */
	public Cache getCache(ToolboxFileRole role, String... cacheId)
			throws IllegalArgumentException;

	/**
	 * check if already storage cache
	 * 
	 * @param role
	 *            the {@link ToolboxFileRole}
	 * @param cacheID
	 *            the id for the cache
	 * @return true, if the cache already storage
	 */
	public Boolean existCache(ToolboxFileRole role, String cacheID);

	/**
	 * @param args
	 *            the arguments
	 * @return the cache key contains arguments with / as separator.
	 */
	public default String cacheKeyBuilder(final String... args) {
		return String.join("/", args); //$NON-NLS-1$
	}
}
