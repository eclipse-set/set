/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.projectdata.utils;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.osgi.service.component.annotations.Component;

/**
 * 
 */
@Component(service = IContextFunction.class, property = "service.context.key:String=org.eclipse.set.feature.projectdata.utils.ServiceProvider")
public class ServiceProviderContextFunction extends ContextFunction {

	@Override
	public Object compute(final IEclipseContext context,
			final String contextKey) {
		final ServiceProvider impl = ContextInjectionFactory
				.make(ServiceProvider.class, context);
		final MApplication application = context.get(MApplication.class);
		application.getContext().set(ServiceProvider.class, impl);
		return impl;
	}
}
