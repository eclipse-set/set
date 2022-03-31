/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

/**
 * Additional information for (and around) the free field of the pdf export.
 * 
 * @author Schaefer
 */
public class FreeFieldInfo {

	private String significantInformation;

	/**
	 * @return the significant information
	 */
	public String getSignificantInformation() {
		return significantInformation;
	}

	/**
	 * @param significantInformation
	 *            the significant information to set
	 */
	public void setSignificantInformation(final String significantInformation) {
		this.significantInformation = significantInformation;
	}
}
