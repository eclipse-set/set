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
 */
public class TableNameInfo {

	private String longName;

	private String planningNumber;
	private String shortName;

	/**
	 * @param longName
	 *            the long name
	 * @param planningNumber
	 *            the planning number
	 * @param shortName
	 *            the short name
	 */
	public TableNameInfo(final String longName, final String planningNumber,
			final String shortName) {
		this.longName = longName;
		this.planningNumber = planningNumber;
		this.shortName = shortName;
	}

	/**
	 * @return the long name
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * @return the planning number
	 */
	public String getPlanningNumber() {
		return planningNumber;
	}

	/**
	 * @return the short name
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @param longName
	 *            the long name to set
	 */
	public void setLongName(final String longName) {
		this.longName = longName;
	}

	/**
	 * @param planningNumber
	 *            the planning number to set
	 */
	public void setPlanningNumber(final String planningNumber) {
		this.planningNumber = planningNumber;
	}

	/**
	 * @param shortName
	 *            the short name to set
	 */
	public void setShortName(final String shortName) {
		this.shortName = shortName;
	}
}
