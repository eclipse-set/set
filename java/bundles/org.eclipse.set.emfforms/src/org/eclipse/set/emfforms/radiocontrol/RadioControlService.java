/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.radiocontrol;

import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.osgi.service.component.annotations.Component;

import org.eclipse.set.emfforms.utils.AnnotatedViewModelRendererService;

/**
 * Check if the {@link RadioControlRenderer} should be uses and provide the
 * rendere class.
 * 
 * @author Truong
 *
 */
@Component(immediate = true, service = { EMFFormsDIRendererService.class })
public class RadioControlService extends
		AnnotatedViewModelRendererService<VControl, RadioControlRenderer> {
	private static final double PRIORITY = 10;

	/**
	 * Create the service
	 */
	public RadioControlService() {
		super(RadioControlRenderer.class, PRIORITY);
	}

}
