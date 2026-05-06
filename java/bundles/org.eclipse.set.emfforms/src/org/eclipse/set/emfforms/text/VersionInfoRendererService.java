/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.emfforms.text;

import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.eclipse.set.emfforms.utils.AnnotatedViewModelRendererService;
import org.osgi.service.component.annotations.Component;

/**
 * 
 */
@Component(immediate = true, service = { EMFFormsDIRendererService.class })
public class VersionInfoRendererService extends
		AnnotatedViewModelRendererService<VControl, VersionInfoRenderer> {
	private static final double PRIORITY = 30;

	/**
	 * Create the service
	 */
	public VersionInfoRendererService() {
		super(VersionInfoRenderer.class, PRIORITY);
	}

}
