/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.browserfunctions;

import org.eclipse.set.basis.cache.Cache;
import org.eclipse.set.browser.swt.BrowserFunction;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.feature.siteplan.SiteplanConstants;
import org.eclipse.set.feature.siteplan.transform.LayoutTransformator;
import org.eclipse.set.utils.WebBrowser;

/**
 * Browser function to change layout crs
 * 
 * @author Truong
 */
public class LayoutChangeCRSBrowserFunction extends BrowserFunction {

	private final CacheService cacheService;

	/**
	 * Constructor.
	 * 
	 * @param webBrowser
	 *            browser to add this function to
	 * @param name
	 *            name of this function for Javascript
	 * @param cacheService
	 *            the cache service
	 */
	public LayoutChangeCRSBrowserFunction(final WebBrowser webBrowser,
			final String name, final CacheService cacheService) {
		super(webBrowser.getBrowser(), name);
		this.cacheService = cacheService;
	}

	@Override
	public final Object function(final Object[] arguments) {
		execute((String) arguments[0]);
		return null;
	}

	private final void execute(final String crs) {
		LayoutTransformator.setCRS(crs);
		final Cache cache = cacheService
				.getCache(SiteplanConstants.SITEPLAN_CACHE_ID);
		cache.invalidate();
	}

}
