/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.emfforms.basisattribute;

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Control;

/**
 * Describes how binding and setting communicate with the basis attribute
 * renderer.
 * 
 * @param <T>
 *            the type of the wert-value
 * 
 * @author Schaefer
 */
public interface BasisAttributeRenderer<T> {

	/**
	 * Annotation for text consumers.
	 */
	public static final String TEXT_CONSUMER = "textConsumer"; //$NON-NLS-1$

	/**
	 * @return the basis attribute setting
	 */
	BasisAttributeSetting<T> getBasisAttributeSetting();

	/**
	 * @return the control of the renderer
	 */
	Control getControl();

	/**
	 * @return the parent of the basis attribute
	 */
	EObject getParent();

	/**
	 * @return whether the renderer is disposed
	 */
	boolean isDisposed();

	/**
	 * Update the control with the model value.
	 */
	void updateControl();

	/**
	 * Update the model
	 */
	void updateModel();

	/**
	 * @param representation
	 *            the string representation of the value
	 * 
	 * @return the optional value
	 */
	Optional<T> valueOf(String representation);
}
