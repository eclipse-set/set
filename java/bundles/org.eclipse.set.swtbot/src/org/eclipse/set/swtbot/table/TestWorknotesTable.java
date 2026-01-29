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
package org.eclipse.set.swtbot.table;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Test data of table Ssbv
 */
@ExtendWith(TestFailHandle.class)
@TestInstance(Lifecycle.PER_CLASS)
public class TestWorknotesTable extends AbstractTableTest {

	String tableRefName;
	PtTable worknotesTable;

	@Override
	public String getTestTableReferenceName() {
		return tableRefName;
	}

	@Test
	protected void testWorknotesTableOpenAfterAnotherTable() throws Exception {
		givenWorknotesTable();
		// Open Worknotes table after another tables
		whenOpenAnotherTable();
		whenOpenWorkNotesTable();
		givenWorknotesTableReferenceOpenAfter();
		whenExistReferenceCSV();
		thenExpectTableDataEqualReferenceCSV();
	}

	@Test
	protected void testWorknotesTableOpenBeforeAnotherTable() throws Exception {
		givenWorknotesTable();

		// Open Worknotes table before another tables
		whenOpenWorkNotesTable();
		givenWorknotesTableReferenceOpenBefore();
		whenExistReferenceCSV();
		whenOpenAnotherTable();
		thenExpectTableDataEqualReferenceCSV();
	}

	protected void whenExistReferenceCSV() {
		assertNotNull(referenceData);
		assertFalse(referenceData.isEmpty());
	}

	@SuppressWarnings("boxing")
	void givenWorknotesTable() {
		worknotesTable = new PtTable("Ssbv",
				"Ssbv – Bearbeitungsvermerke Tabelle", List.of(0));
	}

	void givenWorknotesTableReferenceOpenAfter() throws Exception {
		tableRefName = worknotesTable.shortcut().toLowerCase() + "_after";
		referenceData = loadReferenceFile(tableRefName);
	}

	void givenWorknotesTableReferenceOpenBefore() throws Exception {
		tableRefName = worknotesTable.shortcut().toLowerCase() + "_before";
		referenceData = loadReferenceFile(tableRefName);
	}

	void whenOpenAnotherTable() {
		for (final PtTable table : PtTable.tablesToTest) {
			givenNattableBot(table.tableName());
		}
	}

	void whenOpenWorkNotesTable() {
		givenNattableBot(worknotesTable.tableName());
	}
}
