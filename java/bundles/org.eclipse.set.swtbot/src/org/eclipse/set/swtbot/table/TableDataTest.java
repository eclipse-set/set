/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot.table;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Compare table data with csv reference
 * 
 * @author truong
 *
 */
public class TableDataTest extends AbstractTableTest {
	PtTable tableToTest;

	@Override
	public String getTestTableName() {
		if (tableToTest != null) {
			return tableToTest.shortcut();
		}
		return null;
	}

	private void givenFixedColumnCount(final PtTable table) {
		fixedColumnCount = table.fixedColumns().size();
	}

	protected void givenReferenceCSV(final PtTable table) throws IOException {
		referenceData = loadReferenceFile(table.shortcut());
	}

	/**
	 * Compare table data with reference file
	 * 
	 * @throws Exception
	 */
	@ParameterizedTest
	@MethodSource("providesPtTable")
	protected void testTableData(final PtTable table) throws Exception {
		this.tableToTest = table;
		givenNattableBot(table.tableName());
		givenReferenceCSV(table);
		givenFixedColumnCount(table);
		whenExistReferenceCSV();
		thenRowAndColumnCountEqualReferenceCSV();
		thenPtTableColumnHeaderEqualReferenceCSV();
		thenTableDataEqualReferenceCSV();
	}

	protected void thenPtTableColumnHeaderEqualReferenceCSV() {
		final int rowCount = layers.gridLayer().getColumnHeaderLayer()
				.getRowCount();
		assertDoesNotThrow(() -> compareValue(
				layers.gridLayer().getColumnHeaderLayer(), 0, rowCount));

	}

	protected void whenExistReferenceCSV() {
		assertNotNull(referenceData);
		assertFalse(referenceData.isEmpty());
	}
}
