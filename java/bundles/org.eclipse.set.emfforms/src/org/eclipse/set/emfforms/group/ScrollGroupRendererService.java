/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.group;

import org.eclipse.emf.ecp.view.spi.group.model.VGroup;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.osgi.service.component.annotations.Component;

import org.eclipse.set.emfforms.utils.AnnotatedViewModelRendererService;

/**
 * Check if the {@link ScrollGroupRenderer} should be used and provide the
 * renderer class.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(immediate = true, service = { EMFFormsDIRendererService.class })
public class ScrollGroupRendererService
		extends AnnotatedViewModelRendererService<VGroup, ScrollGroupRenderer> {

	private static final double PRIORITY = 50;

	/**
	 * Create the service.
	 */
	public ScrollGroupRendererService() {
		super(ScrollGroupRenderer.class, PRIORITY);
	}
}
