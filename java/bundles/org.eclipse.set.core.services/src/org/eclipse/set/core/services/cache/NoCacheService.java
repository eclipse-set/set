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
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;

/**
 * A cache service without caching for test purposes.
 * 
 * @author Schaefer
 */
public class NoCacheService implements CacheService {

	@Override
	public Cache getCache(final PlanPro_Schnittstelle schnittStellle,
			final String cacheId) throws IllegalArgumentException {
		if (cacheId.equals(ToolboxConstants.CacheId.TABLE_ERRORS)
				|| cacheId.equals(ToolboxConstants.CacheId.TABLE_ERRORS_INITIAL)
				|| cacheId
						.equals(ToolboxConstants.CacheId.TABLE_ERRORS_FINAL)) {
			return Services.getCacheService().getCache(schnittStellle, cacheId);
		}
		return new NoCache();
	}

	@Override
	public Cache getCache(final PlanPro_Schnittstelle schnittStellle,
			final String... cacheId) throws IllegalArgumentException {
		return new NoCache();
	}

	@Override
	public Boolean existCache(final PlanPro_Schnittstelle schnittStellle,
			final String cacheID) {
		return Boolean.FALSE;
	}

	@Override
	public Cache getCache(final ToolboxFileRole role, final String cacheId)
			throws IllegalArgumentException {
		return new NoCache();
	}

	@Override
	public Cache getCache(final ToolboxFileRole role, final String... cacheId)
			throws IllegalArgumentException {
		return new NoCache();
	}

	@Override
	public Boolean existCache(final ToolboxFileRole role,
			final String cacheID) {
		return Boolean.FALSE;
	}
}
