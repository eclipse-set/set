/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.constants;

import org.eclipse.set.basis.Translateable;

/**
 * Describes the type of a table.
 * 
 * @author Schaefer
 */
public enum TableType implements Translateable {

	/**
	 * Table describing the difference of the initial container and the final
	 * container.
	 */
	DIFF,

	/**
	 * Table for the contents of the final container.
	 */
	FINAL,

	/**
	 * Table for the contents of the initial container.
	 */
	INITIAL,

	/**
	 * Table for the contents of the single container of a state.
	 */
	SINGLE;

	/**
	 * @return the container type for this table type
	 * 
	 * @throws IllegalArgumentException
	 *             if there is no container for this table type
	 */
	public ContainerType getContainerForTable()
			throws IllegalArgumentException {
		switch (this) {
			case INITIAL:
				return ContainerType.INITIAL;
			case FINAL:
				return ContainerType.FINAL;
			case SINGLE:
				return ContainerType.SINGLE;
			default:
				throw new IllegalArgumentException(this.toString());
		}
	}

	@Override
	public String getKey() {
		return "E_" + getClass().getSimpleName() + "_" + name(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @return whether the table for the table type is represented by a
	 *         container
	 */
	public boolean hasContainer() {
		switch (this) {
			case INITIAL:
				return true;
			case FINAL:
				return true;
			case SINGLE:
				return true;
			default:
				return false;
		}
	}
}
