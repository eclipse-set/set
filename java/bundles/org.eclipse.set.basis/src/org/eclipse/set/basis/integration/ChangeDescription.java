/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.integration;

/**
 * Describes the change of a PlanPro planning.
 * 
 * @param <T>
 *            the type of the implementation specific change model
 * 
 * @author Schaefer
 */
public interface ChangeDescription<T> {

	/**
	 * @return implementation specific change model
	 */
	public T getModel();
}
