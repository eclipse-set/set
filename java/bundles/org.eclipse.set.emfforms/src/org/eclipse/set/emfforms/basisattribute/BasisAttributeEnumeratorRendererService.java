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

import org.eclipse.set.toolboxmodel.BasisTypen.BasisAttribut_AttributeGroup;
import org.eclipse.set.toolboxmodel.PlanPro.PlanProPackage;
import org.eclipse.set.emfforms.utils.DomainModelTypeRendererService;

/**
 * Check if the {@link BasisAttributeEnumeratorRenderer} should be used and
 * provide the renderer class.
 *
 * @author Schaefer
 * 
 * @usage production
 */
@Component(immediate = true, service = { EMFFormsDIRendererService.class })
public class BasisAttributeEnumeratorRendererService extends
		DomainModelTypeRendererService<BasisAttribut_AttributeGroup, BasisAttributeEnumeratorRenderer> {

	private static final double PRIORITY = 22;

	/**
	 * Create the service.
	 */
	public BasisAttributeEnumeratorRendererService() {
		super(PlanProPackage.eINSTANCE, BasisAttribut_AttributeGroup.class,
				BasisAttributeEnumeratorRenderer.class, PRIORITY,
				Multiplicity.ZERO_OR_ONE,
				BasisAttributeEnumeratorRenderer::isApplicable);
	}
}
