/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.attachment;

import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.eclipse.set.toolboxmodel.Basisobjekte.Anhang;
import org.eclipse.set.toolboxmodel.PlanPro.PlanProPackage;
import org.eclipse.set.emfforms.utils.DomainModelTypeRendererService;

/**
 * Check if the {@link AttachmentListRenderer} should be used and provide the
 * renderer class.
 *
 * @author Schaefer
 * 
 * @usage production
 */
@Component(immediate = true, service = { EMFFormsDIRendererService.class })
public class AttachmentListRendererService
		extends DomainModelTypeRendererService<Anhang, AttachmentListRenderer> {

	private static final Logger logger = LoggerFactory
			.getLogger(AttachmentListRendererService.class);

	private static final double PRIORITY = 21;

	/**
	 * Create the service
	 */
	public AttachmentListRendererService() {
		super(PlanProPackage.eINSTANCE, Anhang.class,
				AttachmentListRenderer.class, PRIORITY, Multiplicity.MANY);
	}

	/**
	 * Activate the component.
	 */
	@SuppressWarnings("static-method")
	@Activate
	public void activate() {
		logger.info("Started attachment list renderer service."); //$NON-NLS-1$
	}
}
