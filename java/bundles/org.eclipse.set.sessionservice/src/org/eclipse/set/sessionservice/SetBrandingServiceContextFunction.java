/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.sessionservice;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.set.core.services.branding.BrandingService;
import org.osgi.service.component.annotations.Component;

/**
 * Create and publish {@link BrandingService}.
 * 
 * @author Schaefer
 */
@Component(service = IContextFunction.class, property = {
		"service.context.key:String=org.eclipse.set.core.services.branding.BrandingService",
		"service.ranking:Integer=100" })
public class SetBrandingServiceContextFunction extends ContextFunction {

	@Override
	public Object compute(final IEclipseContext context,
			final String contextKey) {
		final BrandingService impl = ContextInjectionFactory
				.make(SetBrandingService.class, context);
		context.set(BrandingService.class, impl);
		return impl;
	}
}
