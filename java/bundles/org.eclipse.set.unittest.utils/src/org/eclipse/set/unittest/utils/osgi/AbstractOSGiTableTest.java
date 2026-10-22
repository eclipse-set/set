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
package org.eclipse.set.unittest.utils.osgi;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;

/**
 * 
 */
public abstract class AbstractOSGiTableTest extends AbstractOSGiServiceTest {
	/**
	 * the csv delimiter
	 */
	public static final String CSV_DELIMITER = ";";

	/**
	 * the index column designation
	 */
	public static final String ROW_INDEX_COL = "Lfd. Nr.";

	protected boolean isTableEmpty;

	/**
	 * @return
	 */
	public abstract Table getTestTable();

	/**
	 * @return
	 */
	public abstract String getReferenceDir();

	/**
	 * @return the table reference file name
	 */
	public abstract String getTestTableReferenceName();

	/**
	 * @return
	 */
	public abstract Class<? extends AbstractOSGiTableTest> getTestResourceClass();

	/**
	 * @param testTable
	 * @return
	 */
	public static String getTableCSVHeader(final Table testTable) {
		final List<ColumnDescriptor> columns = TableExtensions
				.getColumns(testTable);
		final String header = columns.stream()
				.map(ColumnDescriptor::getColumnPosition)
				.collect(Collectors.joining(CSV_DELIMITER));
		return ROW_INDEX_COL + CSV_DELIMITER + header + System.lineSeparator();
	}

	/**
	 * @return whether table is empty
	 */
	public boolean isTableEmpty() {
		return isTableEmpty;
	}

}
