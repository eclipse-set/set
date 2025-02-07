/**
 * Copyright (c) 2015-2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.utils;

import org.eclipse.emf.ecp.view.spi.context.ViewModelContext;
import org.eclipse.emf.ecp.view.spi.model.VElement;
import org.eclipse.emfforms.spi.swt.core.AbstractSWTRenderer;
import org.eclipse.emfforms.spi.swt.core.di.EMFFormsDIRendererService;
import org.eclipse.set.utils.emfforms.Annotations;

/**
 * This abstract service tests for an annotation of a view model element and
 * provides an appropriate renderer.
 * <p>
 * The specified renderer will be used if the view model element contains an
 * annotation with the simple name of the type <b>T</b> as the key and the value
 * <b>true</b>.
 * <p>
 * Example: Key = PolygonTextRenderer, Value = true
 * 
 * @param <T>
 *            the type of the specified renderer
 * @param <VELEMENT>
 *            the type of the rendered control
 * 
 * @author Schaefer
 */
public abstract class AnnotatedViewModelRendererService<VELEMENT extends VElement, T extends AbstractSWTRenderer<VELEMENT>>
		implements EMFFormsDIRendererService<VELEMENT> {

	private final double priority;
	private final Class<T> type;

	/**
	 * @param type
	 *            the type of the specified renderer
	 * @param priority
	 *            the priority of the specified renderer
	 */
	public AnnotatedViewModelRendererService(final Class<T> type,
			final double priority) {
		this.type = type;
		this.priority = priority;
	}

	@Override
	public Class<? extends AbstractSWTRenderer<VELEMENT>> getRendererClass() {
		return type;
	}

	@Override
	public double isApplicable(final VElement vElement,
			final ViewModelContext viewModelContext) {
		if (Boolean.TRUE.toString()
				.equalsIgnoreCase(Annotations.getViewModelValue(vElement,
						type.getSimpleName()))) {
			return priority;
		}
		return NOT_APPLICABLE;
	}
}
