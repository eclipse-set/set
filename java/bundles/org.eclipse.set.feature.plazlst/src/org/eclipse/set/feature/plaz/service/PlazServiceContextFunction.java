/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.plaz.service;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.osgi.service.component.annotations.Component;

/**
 * ContextFunction, um den PlaZService dem EclipseContext bekannt zu machen.
 * IMPROVE: Wir wollen solchen Boilerplate-Code mit e4-Abhängigkeit nicht
 * 
 * @author rumpf
 *
 * @usage production
 */
@Component(service = {
		IContextFunction.class }, property = "service.context.key:String=org.eclipse.set.feature.plaz.service.PlazService")
public class PlazServiceContextFunction extends ContextFunction {

	/**
	 * constructor.
	 */
	public PlazServiceContextFunction() {
		super();
	}

	@Override
	public Object compute(final IEclipseContext context,
			final String contextKey) {
		final PlaZServiceImpl translationService = ContextInjectionFactory
				.make(PlaZServiceImpl.class, context);

		final MApplication application = context.get(MApplication.class);
		final IEclipseContext applicationContext = application.getContext();
		applicationContext.set(PlazService.class, translationService);

		return translationService;
	}
}