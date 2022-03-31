/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.text;

import org.eclipse.emf.ecp.view.spi.model.VControl;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.eclipse.set.emfforms.utils.AnnotatedViewModelRendererService;
import org.osgi.service.component.annotations.Component;

/**
 * Check if the {@link TextFieldRenderer} should be uses and provide the rendere
 * class
 * 
 * @author Truong
 *
 */
@Component(immediate = true, service = { EMFFormsDIRendererService.class })
public class TextFieldService
		extends AnnotatedViewModelRendererService<VControl, TextFieldRenderer> {
	private static final double PRIORITY = 22;

	/**
	 * Create the service
	 */
	public TextFieldService() {
		super(TextFieldRenderer.class, PRIORITY);
	}

}
