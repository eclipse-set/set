/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.messages.internal;

import java.util.Hashtable;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.feature.table.messages.MessagesWrapper;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * Create and publish {@link MessagesWrapper}.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(service = IContextFunction.class, immediate = true, property = "service.context.key:String=org.eclipse.set.feature.table.messages.MessagesWrapper")
public class MessagesContextFunction extends ContextFunction {

	private BundleContext bundleContext;
	private ServiceRegistration<MessagesWrapper> serviceRegistration;

	@Override
	public Object compute(final IEclipseContext context,
			final String contextKey) {
		// create wrapper instance via cdi context to retrieve
		// the messages
		final MessagesWrapper wrapper = ContextInjectionFactory
				.make(MessagesWrapper.class, context);

		final MApplication application = context.get(MApplication.class);
		final IEclipseContext applicationContext = application.getContext();

		applicationContext.set(MessagesWrapper.class, wrapper);
		// publish as an osgi service:
		serviceRegistration = bundleContext.registerService(
				MessagesWrapper.class, wrapper, new Hashtable<>());

		return wrapper;
	}

	/**
	 * activator of the context function. Used to retrieve the osgi bundle
	 * context.
	 * 
	 * @param bundleContext
	 *            context
	 */
	@Activate
	public void setup(final BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	/**
	 * unregisters the wrapper instance from the osgi service registry.
	 */
	@Deactivate
	public void tearDown() {
		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}
}