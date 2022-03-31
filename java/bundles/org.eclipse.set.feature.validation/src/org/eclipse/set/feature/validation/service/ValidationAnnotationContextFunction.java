/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.validation.service;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.core.services.validation.ValidationAnnotationService;
import org.osgi.service.component.annotations.Component;

/**
 * Create and publish the {@link ValidationAnnotationService}.
 * 
 * @author Schaefer
 */
@Component(service = IContextFunction.class, property = "service.context.key:String=org.eclipse.set.core.services.validation.ValidationAnnotationService")
public class ValidationAnnotationContextFunction extends ContextFunction {

	private EventBrokerValidationAnnotationService impl;

	@Override
	public Object compute(final IEclipseContext context,
			final String contextKey) {
		impl = ContextInjectionFactory
				.make(EventBrokerValidationAnnotationService.class, context);
		final MApplication application = context.get(MApplication.class);
		application.getContext().set(ValidationAnnotationService.class, impl);
		return impl;
	}
}
