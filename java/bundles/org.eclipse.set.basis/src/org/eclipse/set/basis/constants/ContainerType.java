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
 * Describes the type of a container.
 * 
 * @author Schaefer
 */
public enum ContainerType implements Translateable {
	/**
	 * The container with the final state of the planning.
	 */
	FINAL,

	/**
	 * The container with the initial state of the planning.
	 */
	INITIAL,

	/**
	 * The single container of a state.
	 */
	SINGLE;

	/**
	 * @return the default table type for this container type
	 */
	public TableType getDefaultTableType() {
		switch (this) {
		case FINAL:
			return TableType.FINAL;
		case INITIAL:
			return TableType.INITIAL;
		case SINGLE:
			return TableType.SINGLE;
		default:
			throw new IllegalArgumentException(this.toString());
		}
	}

	@Override
	public String getKey() {
		return "E_" + getClass().getSimpleName() + "_" + name(); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * @return the table type for table views for this container type
	 */
	public TableType getTableTypeForTables() {
		switch (this) {
		case FINAL:
			return TableType.DIFF;
		case INITIAL:
			return TableType.DIFF;
		case SINGLE:
			return TableType.SINGLE;
		default:
			throw new IllegalArgumentException(this.toString());
		}
	}
}
