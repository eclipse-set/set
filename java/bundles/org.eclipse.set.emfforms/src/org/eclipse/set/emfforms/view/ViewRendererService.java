/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.view;

import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.eclipse.set.emfforms.utils.AnnotatedViewModelRendererService;
import org.osgi.service.component.annotations.Component;

/**
 * Check if the {@link ViewRenderer} should be used and provide the renderer
 * class
 * 
 * @author Peters
 *
 */
@Component(immediate = true, service = { EMFFormsDIRendererService.class })
public class ViewRendererService
		extends AnnotatedViewModelRendererService<VControl, ViewRenderer> {
	private static final double PRIORITY = 10;

	/**
	 * Create the service
	 */
	public ViewRendererService() {
		super(ViewRenderer.class, PRIORITY);
	}

}
