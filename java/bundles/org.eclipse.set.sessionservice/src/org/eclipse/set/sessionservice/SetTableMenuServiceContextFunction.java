/**
 * Copyright (c) 2022 DB Netz AG and others.
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
import org.eclipse.set.utils.table.menu.TableMenuService;
import org.osgi.service.component.annotations.Component;

/**
 * Create and publish {@link TableMenuService}.
 * 
 * @author Truong
 *
 */
@Component(service = IContextFunction.class, property = "service.context.key:String=org.eclipse.set.utils.table.menu.TableMenuService")
public class SetTableMenuServiceContextFunction extends ContextFunction {
	@Override
	public Object compute(final IEclipseContext context,
			final String contextKey) {
		final TableMenuService impl = ContextInjectionFactory
				.make(SetTableMenuService.class, context);
		context.set(TableMenuService.class, impl);
		return impl;
	}
}
