/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.group;

import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.eclipse.set.emfforms.utils.AnnotatedViewModelRendererService;
import org.osgi.service.component.annotations.Component;

/**
 * Checks if the {@link EmbeddedGroupRendererService} should be used and
 * provides the renderer class
 * 
 * @author Stuecker
 *
 */
@Component(immediate = true, service = { EMFFormsDIRendererService.class })
public class EmbeddedGroupRendererService extends
		AnnotatedViewModelRendererService<VControl, EmbeddedGroupRenderer> {
	private static final double PRIORITY = 25;

	/**
	 * Create the service
	 */
	public EmbeddedGroupRendererService() {
		super(EmbeddedGroupRenderer.class, PRIORITY);
	}

}
