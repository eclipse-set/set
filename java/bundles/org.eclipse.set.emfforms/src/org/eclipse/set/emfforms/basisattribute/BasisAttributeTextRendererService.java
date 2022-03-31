/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.basisattribute;

import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.osgi.service.component.annotations.Component;

import de.scheidtbachmann.planpro.model.model1902.BasisTypen.BasisAttribut_AttributeGroup;
import de.scheidtbachmann.planpro.model.model1902.PlanPro.PlanProPackage;
import org.eclipse.set.emfforms.utils.DomainModelTypeRendererService;

/**
 * Check if the {@link BasisAttributeTextRenderer} should be used and provide
 * the renderer class.
 *
 * @author Schaefer
 * 
 * @usage production
 */
@Component(immediate = true, service = { EMFFormsDIRendererService.class })
public class BasisAttributeTextRendererService extends
		DomainModelTypeRendererService<BasisAttribut_AttributeGroup, BasisAttributeTextRenderer> {

	private static final double PRIORITY = 22;

	/**
	 * Create the service.
	 */
	public BasisAttributeTextRendererService() {
		super(PlanProPackage.eINSTANCE, BasisAttribut_AttributeGroup.class,
				BasisAttributeTextRenderer.class, PRIORITY,
				Multiplicity.ZERO_OR_ONE,
				BasisAttributeTextRenderer::isApplicable);
	}
}
