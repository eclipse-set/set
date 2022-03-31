/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.basis;

import java.lang.reflect.ParameterizedType;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;

/**
 * Create and publish a specific service using an implementation
 * 
 * @param <I>
 *            The interface of the service
 * @param <T>
 *            The implementation of the service
 * 
 * @author Stuecker
 */
public abstract class PlanProContextFunction<I, T extends I>
		extends ContextFunction {

	@Override
	public Object compute(final IEclipseContext context,
			final String contextKey) {
		final T service = ContextInjectionFactory.make(getImplementationClass(),
				context);

		final MApplication application = context.get(MApplication.class);
		final IEclipseContext applicationContext = application.getContext();
		applicationContext.set(getInterfaceClass(), service);
		return service;
	}

	@SuppressWarnings("unchecked")
	private Class<T> getImplementationClass() {
		return (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1];
	}

	@SuppressWarnings("unchecked")
	private Class<I> getInterfaceClass() {
		return (Class<I>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
}
