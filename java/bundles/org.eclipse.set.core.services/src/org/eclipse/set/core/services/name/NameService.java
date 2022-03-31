/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.name;

import org.eclipse.emf.ecore.EObject;

/**
 * Can provide names for model objects.
 * 
 * @author schaefer
 */
public interface NameService {

	/**
	 * @param object
	 *            the model object
	 * 
	 * @return the name
	 */
	String getName(EObject object);

	/**
	 * @param object
	 *            the model object
	 * 
	 * @return the type name
	 */
	String getTypeName(EObject object);

	/**
	 * @param object
	 *            the model object
	 * 
	 * @return the type name
	 */
	String getValue(EObject object);
}
