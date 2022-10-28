/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.application.cacheservice;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.cache.CacheService;
import org.eclipse.set.core.services.cache.NoCacheService;
import org.osgi.service.component.annotations.Component;

/**
 * Create and publish the {@link CacheService}.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(service = IContextFunction.class, property = "service.context.key:String=org.eclipse.set.core.services.cache.CacheService")
public class CacheServiceContextFunction extends ContextFunction {
	@Override
	public Object compute(final IEclipseContext context,
			final String contextKey) {
		final CacheServiceImpl cacheService = ContextInjectionFactory
				.make(CacheServiceImpl.class, context);
		final NoCacheService noCacheService = ContextInjectionFactory
				.make(NoCacheService.class, context);
		final MApplication application = context.get(MApplication.class);
		final IEclipseContext applicationContext = application.getContext();
		applicationContext.set(CacheService.class, cacheService);
		Services.setCacheService(cacheService);
		Services.setNoCacheService(noCacheService);
		// IMPROVE Injection into the CacheService does not work for an unknown
		// reason, see also PLANPRO-3270
		final IEventBroker broker = applicationContext.get(IEventBroker.class);
		cacheService.setEventBroker(broker);
		return cacheService;
	}
}
