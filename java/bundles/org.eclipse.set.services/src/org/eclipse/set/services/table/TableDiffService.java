/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.services.table;

import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;

/**
 * Interface for table difference presentation.
 * 
 * @author Schaefer
 */
public interface TableDiffService {

	/**
	 * The osgi property name for table compare type
	 */
	public static final String COMPARE_TYPE_OSGi_PROPERTY = "compareType"; //$NON-NLS-1$

	/**
	 * The compare type state
	 */
	public static final String COMPARE_TYPE_STATE = "state"; //$NON-NLS-1$
	/**
	 * The compare type project
	 */
	public static final String COMPARE_TYPE_PROJECT = "project"; //$NON-NLS-1$

	/**
	 * 
	 */
	public enum TableCompareType {
		/**
		 * 
		 */
		STATE(COMPARE_TYPE_STATE),
		/**
		 * 
		 */
		PROJECT(COMPARE_TYPE_PROJECT);

		String value;

		/**
		 * @return string value
		 */
		public String getValue() {
			return value;
		}

		private TableCompareType(final String value) {
			this.value = value;
		}
	}

	/**
	 * @param oldTable
	 *            the old table
	 * @param newTable
	 *            the new table
	 * 
	 * @return the difference table
	 */
	Table createDiffTable(Table oldTable, Table newTable);

	/**
	 * @return {@link TableCompareType}
	 */
	TableCompareType getCompareType();

	boolean isTableRowDifferent(TableRow row);
}
