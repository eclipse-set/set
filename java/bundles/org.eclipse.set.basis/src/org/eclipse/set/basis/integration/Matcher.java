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
 * Finds matching elements.
 * 
 * @author Schaefer
 */
public interface Matcher {

	/**
	 * @param first
	 *            the first object
	 * @param second
	 *            the second object
	 * 
	 * @return the attribute paths of the object
	 */
	List<List<String>> getNonEmptyAttributes(EObject first, EObject second);

	/**
	 * @param first
	 *            the first object
	 * @param second
	 *            the second object
	 * 
	 * @return the attribute paths of different attributes of the objects
	 */
	List<List<String>> getDifferences(EObject first, EObject second);

	/**
	 * @param first
	 *            the first object
	 * @param second
	 *            the second object
	 * 
	 * @return whether the objects are different
	 */
	boolean isDifferent(EObject first, EObject second);

	/**
	 * @param first
	 *            the first object
	 * @param second
	 *            the second object
	 * 
	 * @return whether the objects match
	 */
	boolean match(EObject first, EObject second);
}
