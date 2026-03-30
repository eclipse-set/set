/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.utils;

/**
 * Collect table name information.
 * 
 * @author Schaefer
 * @param longName
 *            the long name
 * @param planningNumber
 *            the planning number
 * @param shortName
 *            the short name
 * @param rilNumber
 *            the ril number
 */
public record TableNameInfo(String longName, String planningNumber,
		String shortName, String rilNumber) {

	/**
	 * @return the full display name
	 */
	public String getFullDisplayName() {
		return shortName() + " – " + longName(); //$NON-NLS-1$
	}
}
