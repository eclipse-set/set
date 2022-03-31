/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.exceptions;

import org.eclipse.emf.common.util.Enumerator;

/**
 * No translation found for given enumerator.
 * 
 * @author Schaefer
 */
public class NoEnumTranslationFound extends RuntimeException {

	/**
	 * @param enumerator
	 *            the enumerator
	 */
	public NoEnumTranslationFound(final Enumerator enumerator) {
		super(String.format("No translation found for enumerator %s", //$NON-NLS-1$
				enumerator.getName()));
	}

	/**
	 * @param obj
	 *            the object
	 */
	public NoEnumTranslationFound(final Object obj) {
		super(String.format("No translation found for enumerator %s", //$NON-NLS-1$
				obj.toString()));
	}
}
