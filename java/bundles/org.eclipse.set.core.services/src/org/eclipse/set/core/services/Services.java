/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services;

import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.cache.NoCacheService;
import org.eclipse.set.core.services.planningaccess.PlanningAccessService;
import org.eclipse.set.core.services.viewmodel.ToolboxViewModelService;

/**
 * Static access to certain services.
 * 
 * @author Schaefer
 */
public class Services {
	private static CacheService cacheService;
	private static PlanningAccessService planningAccessService;
	private static ToolboxViewModelService toolboxViewModelService;
	private static NoCacheService noCacheService;

	/**
	 * @return the cache service
	 */
	public static CacheService getCacheService() {
		return cacheService;
	}

	/**
	 * @return the no cache service
	 */
	public static CacheService getNoCacheService() {
		return noCacheService;
	}

	/**
	 * @return the planning access service
	 */
	public static PlanningAccessService getPlanningAccessService() {
		return planningAccessService;
	}

	/**
	 * @return the toolbox view model service
	 */
	public static ToolboxViewModelService getToolboxViewModelService() {
		return toolboxViewModelService;
	}

	/**
	 * @param cacheService
	 *            the cache service
	 */
	public static void setCacheService(final CacheService cacheService) {
		Services.cacheService = cacheService;
	}

	/**
	 * @param noCacheService
	 *            the no cache service
	 */
	public static void setNoCacheService(final NoCacheService noCacheService) {
		Services.noCacheService = noCacheService;
	}

	/**
	 * @param planningAccessService
	 *            the planning access service
	 */
	public static void setPlanningAccessService(
			final PlanningAccessService planningAccessService) {
		Services.planningAccessService = planningAccessService;
	}

	/**
	 * @param toolboxViewModelService
	 *            the toolbox view model service
	 */
	public static void setToolboxViewModelService(
			final ToolboxViewModelService toolboxViewModelService) {
		Services.toolboxViewModelService = toolboxViewModelService;
	}

	private Services() {
		// hide constructor
	}
}
