/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.services.table;

/**
 * The status of the Pt1 table by each table state/control area
 * 
 * @author truong
 */
public class TableStatus {
	private boolean containsErrors;
	private boolean containsStateChanged;
	private boolean containsPlanChanged;
	private boolean nonTransformable;
	private boolean isEmpty;

	/**
	 * 
	 */
	public TableStatus() {
		containsErrors = false;
		containsPlanChanged = false;
		containsStateChanged = false;
		nonTransformable = false;
		isEmpty = false;
	}

	/**
	 * @return true, when table contain errors
	 */
	public boolean isContainsErrors() {
		return containsErrors;
	}

	/**
	 * @param containsErrors
	 *            true, when table contain errors
	 */
	public void setContainsErrors(final boolean containsErrors) {
		this.containsErrors = containsErrors;
	}

	/**
	 * @return true, when table contains state changed
	 */
	public boolean isContainsStateChanged() {
		return containsStateChanged;
	}

	/**
	 * @param containsStateChanged
	 *            true, when table contains state changed
	 */
	public void setContainsStateChanged(final boolean containsStateChanged) {
		this.containsStateChanged = containsStateChanged;
	}

	/**
	 * @return true, when table contains plan changed
	 */
	public boolean isContainsPlanChanged() {
		return containsPlanChanged;
	}

	/**
	 * @param containsPlanChanged
	 *            true, when table contains plan changed
	 */
	public void setContainsPlanChanged(final boolean containsPlanChanged) {
		this.containsPlanChanged = containsPlanChanged;
	}

	/**
	 * @return true, if table non transformable
	 */
	public boolean isNonTransformable() {
		return nonTransformable;
	}

	/**
	 * @param nonTransformable
	 *            true, if table non transformable
	 */
	public void setNonTransformable(final boolean nonTransformable) {
		this.nonTransformable = nonTransformable;
	}

	/**
	 * @return true, if table is empty
	 */
	public boolean isEmpty() {
		return isEmpty;
	}

	/**
	 * @param value
	 *            true, if table is empty
	 */
	public void setEmpty(final boolean value) {
		isEmpty = value;
	}
}
