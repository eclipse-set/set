/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.pdf.utils.rendererservice;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.set.core.services.pdf.PdfRendererService;
import org.osgi.service.component.annotations.Component;

/**
 * Create and publish {@link PdfRendererService}.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(service = IContextFunction.class, property = "service.context.key:String=org.eclipse.set.core.services.pdf.PdfRendererService")
public class PdfRendererServiceContextFunction extends ContextFunction {

	@Override
	public Object compute(final IEclipseContext context,
			final String contextKey) {
		final PdfRendererService impl = ContextInjectionFactory
				.make(BrowserPdfRendererService.class, context);
		final MApplication application = context.get(MApplication.class);
		application.getContext().set(PdfRendererService.class, impl);
		return impl;
	}
}
