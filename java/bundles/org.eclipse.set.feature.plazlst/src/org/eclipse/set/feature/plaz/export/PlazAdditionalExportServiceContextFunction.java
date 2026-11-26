/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.plaz.export;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.basis.export.CheckBoxTreeElement;
import org.eclipse.set.core.services.export.AdditionalExportService;
import org.osgi.service.component.annotations.Component;

/**
 * Create and publish {@link PlazAdditionalExportService}.
 * 
 * @author Schaefer
 */
@Component(service = {
		IContextFunction.class }, property = "service.context.key:String=org.eclipse.set.core.services.export.AdditionalExportService")
public class PlazAdditionalExportServiceContextFunction
		extends ContextFunction {

	@Override
	public Object compute(final IEclipseContext context,
			final String contextKey) {
		final AdditionalExportService<CheckBoxTreeElement> service = ContextInjectionFactory
				.make(PlazAdditionalExportService.class, context);
		context.get(MApplication.class)
				.getContext()
				.set(AdditionalExportService.class, service);
		return service;
	}
}
