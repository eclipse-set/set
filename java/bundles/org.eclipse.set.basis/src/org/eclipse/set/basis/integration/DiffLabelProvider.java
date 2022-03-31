/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.integration;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * Describe the UI representation and compare value for an element.
 * 
 * @author Schaefer
 */
public interface DiffLabelProvider {

	/**
	 * @param element
	 *            the element
	 * @param path
	 *            the attribute path
	 * 
	 * @return the text to display to the user
	 */
	String getAttributeLabel(EObject element, List<String> path);

	/**
	 * @param element
	 *            the element
	 * @param path
	 *            the attribute path
	 * 
	 * @return the value to compare
	 */
	Object getAttributeValue(EObject element, List<String> path);

	/**
	 * @param element
	 *            the element
	 * 
	 * @return the text to display to the user
	 */
	String getElementLabel(EObject element);

	/**
	 * @param element
	 *            the element
	 * @param path
	 *            the attribute path
	 * 
	 * @return path text to display to the user
	 */
	String getPathLabel(EObject element, List<String> path);
}
