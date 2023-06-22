/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot.utils;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.osgi.service.component.annotations.Component;

import org.eclipse.set.core.services.dialog.DialogService;

/**
 * Helper component to override the dialog service of SET
 */
@Component(immediate=true, service = IContextFunction.class, property = {
		"service.ranking:Integer=100","service.context.key:String=org.eclipse.set.core.services.dialog.DialogService"
})
public class MockDialogServiceContextFunction extends ContextFunction {
	public static MockDialogService mockService;

	@Override
	public Object compute(final IEclipseContext context,
			final String contextKey) {
		mockService = ContextInjectionFactory
				.make(MockDialogService.class, context);
		final MApplication application = context.get(MApplication.class);
		application.getContext().set(DialogService.class, mockService);
		return mockService;
	}
}
