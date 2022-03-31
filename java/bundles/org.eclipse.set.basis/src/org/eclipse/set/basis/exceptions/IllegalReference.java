/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.exceptions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

/**
 * An illegal reference was used.
 * 
 * @author Schaefer
 */
public class IllegalReference extends RuntimeException {

	private final EObject object;
	private final EReference reference;

	/**
	 * @param object
	 *            the object with the reference
	 * @param reference
	 *            the reference
	 */
	public IllegalReference(final EObject object, final EReference reference) {
		this.object = object;
		this.reference = reference;
	}

	/**
	 * @return the object with the reference
	 */
	public EObject getObject() {
		return object;
	}

	/**
	 * @return the reference
	 */
	public EReference getReference() {
		return reference;
	}
}
