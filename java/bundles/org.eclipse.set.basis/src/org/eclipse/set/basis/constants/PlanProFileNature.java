/**
 * Copyright (c) 2015 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.constants;

/**
 * Enum beschreibt die Art
 * 
 * @author bleidiessel
 */
public enum PlanProFileNature {

	/**
	 * 
	 */
	INFORMATION_STATE(ContainerType.SINGLE),

	/**
	 * Integration model
	 */
	INTEGRATION(ContainerType.FINAL),

	/**
	 * Invalid model
	 */
	INVALID(null),

	/**
	 * 
	 */
	PLANNING(ContainerType.FINAL);

	private final ContainerType defaultContainer;

	private PlanProFileNature(final ContainerType defaultContainer) {
		this.defaultContainer = defaultContainer;
	}

	/**
	 * @return the default container for this nature
	 */
	public ContainerType getDefaultContainer() {
		return defaultContainer;
	}
}
