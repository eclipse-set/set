/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.part;

/**
 * Describes the visibility of a view/part for the user.
 * 
 * @author Schaefer
 */
public enum ViewVisibility {

	/**
	 * The part is concealed from the user and does not need immediate updates.
	 */
	CONCEALED,

	/**
	 * The part is exposed to the user and does need immediate updates.
	 */
	EXPOSED
}
