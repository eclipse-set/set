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
package org.eclipse.set.osgi.test.utils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;

/**
 * 
 */
public abstract class AbstractOsgiValidationTableTest
		extends AbstractOsgiTableTest {

	@Override
	protected int getExpectedColumnCount() {
		return referenceData.get(0).size();
	}

	@Override
	protected TestFile getTestFile() {
		return OsgiTestTestFile.PPHN_1_10_0_3;
	}

	@Override
	protected List<CSVRecord> loadReferenceFile(final String tableName) {
		final List<CSVRecord> records = super.loadReferenceFile(tableName);
		final Optional<CSVRecord> csvHeader = records.stream()
				.filter(r -> r.get(0).equals(ROW_INDEX_COL))
				.findFirst();
		if (csvHeader.isEmpty()) {
			throw new IllegalArgumentException("Wrong csv reference format");
		}

		// Remove meta data line
		return records.subList(records.indexOf(csvHeader.get()) + 1,
				records.size());
	}

	@Override
	protected boolean skipCompareCountColumn() {
		return false;
	}

	@Override
	protected void thenExpectTableDataEqualReferenceCSV() {
		final List<TableRow> tableRows = TableExtensions.getTableRows(testee);
		tableRows.sort(TreeTableTestFailed.treeRowComparator());
		assertDoesNotThrow(() -> compareValue(0, tableRows));
	}
}
