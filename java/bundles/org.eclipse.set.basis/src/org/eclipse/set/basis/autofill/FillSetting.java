/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.autofill;

import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Describes, how to get and set a value of an object.
 * 
 * @author Schaefer
 */
public interface FillSetting {

	/**
	 * @return the value
	 */
	Object getValue();

	/**
	 * @param editingDomain
	 *            the editing domain
	 * @param value
	 *            the value
	 */
	void setValue(EditingDomain editingDomain, Object value);
}
