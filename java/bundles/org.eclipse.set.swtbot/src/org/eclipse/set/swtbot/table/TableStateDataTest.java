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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.set.basis.constants.TableType;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test table data by each {@link TableType}. The table data bei change
 * Stell_Bereich should be test too, but in currently test file given't
 * Stell_Bereich. By fail this test will not export new reference file
 * 
 * @author truong
 */
@TestInstance(Lifecycle.PER_CLASS)
public class TableStateDataTest extends AbstractTableTest {
	private static final String DIFF_STATE_TEXT = "Vergleich Start/Ziel";
	private static final String FINAL_STATE_TEXT = "Zielzustand";
	private static final String INITIAL_STATE_TEXT = "Startzustand";

	private static Stream<Arguments> providesPtTableByTableType() {
		return List.of(TableType.INITIAL, TableType.FINAL)
				.stream()
				.map(type -> Arguments.of(type, PtTable.tablesToTest));
	}

	TableType tableState;
	PtTable tableToTest;
	SWTBotCombo tableTypeSelectionCombo;

	@BeforeEach
	@Override
	public void beforeEach() throws Exception {
		super.beforeEach();
		tableTypeSelectionCombo = bot.comboBox(DIFF_STATE_TEXT);
	}

	@Override
	public String getReferenceDir() {
		final String tableStateDir = switch (tableState) {
			case FINAL -> "finalState";
			case INITIAL -> "initialState";
			default -> "diffState";
		};
		return super.getReferenceDir() + tableStateDir + "/";
	}

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

	@Override
	protected String getErrorMessage(final int columnIndex, final int rowIndex,
			final String expectedValue, final String actualValue) {
		final String message = super.getErrorMessage(columnIndex, rowIndex,
				actualValue, actualValue);
		return tableState.name() + " - " + message;
	}

	protected void givenReferenceCSV(final PtTable table) throws IOException {
		referenceData = loadReferenceFile(table.shortcut());
	}

	protected void whenChangeTableType() {
		final List<String> comboItems = Arrays
				.asList(tableTypeSelectionCombo.items());
		switch (tableState) {
			case INITIAL: {
				tableTypeSelectionCombo
						.setSelection(comboItems.indexOf(INITIAL_STATE_TEXT));
				break;
			}
			case FINAL: {
				tableTypeSelectionCombo
						.setSelection(comboItems.indexOf(FINAL_STATE_TEXT));
				break;
			}
			default: {
				return;
			}
		}
	}

	protected void whenExistReferenceCSV() {
		assertNotNull(referenceData);
		assertFalse(referenceData.isEmpty());
	}

	/**
	 * Compare table data with reference file
	 * 
	 * @throws Exception
	 */
	@ParameterizedTest
	@MethodSource("providesPtTableByTableType")
	void testTableStateData(final TableType tableType,
			final List<PtTable> tablesToTest) throws Exception {
		this.tableState = tableType;
		whenChangeTableType();
		for (final PtTable table : tablesToTest) {
			this.tableToTest = table;
			givenNattableBot(table.tableName());
			givenReferenceCSV(table);
			givenFixedColumnCount(table);
			whenExistReferenceCSV();
			thenRowAndColumnCountEqualReferenceCSV();
			thenTableDataEqualReferenceCSV();
		}
	}
}
