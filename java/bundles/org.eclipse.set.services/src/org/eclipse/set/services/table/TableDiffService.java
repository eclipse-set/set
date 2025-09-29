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

/**
 * Interface for table difference presentation.
 * 
 * @author Schaefer
 */
public interface TableDiffService {
	/**
	 * 
	 */
	enum TableCompareType {
		/**
		 * 
		 */
		STATE("state"), //$NON-NLS-1$
		/**
		 * 
		 */
		PROJECT("project"); //$NON-NLS-1$

		String value;

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
	 * @param firstPlanTable
	 *            the initial or final table of first plan
	 * @param secondPlanDiffTable
	 *            the diff table of second plan
	 * @return the compare table
	 */
	Table createCompareTable(Table firstPlanTable, Table secondPlanDiffTable);
}
