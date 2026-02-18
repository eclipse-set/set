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

import org.eclipse.set.basis.Pair;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.swtbot.table.TestFailHandle.ReopenTableBeforeFailHandle;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCTabItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test table data by each {@link TableType}.
 * 
 * @author truong
 */
@TestInstance(Lifecycle.PER_CLASS)
public class TableStateDataTest extends AbstractTableTest {
	private static String ALL_DATA_OPTION = "Gesamter Dateiinhalt";
	private static String PLANUNG_BEREICH_OPTION = "Planungsbereich";
	
	private static final String DIFF_STATE_TEXT = "Vergleich Start/Ziel";
	private static final String FINAL_STATE_TEXT = "Zielzustand";
	private static final String INITIAL_STATE_TEXT = "Startzustand";

	private static Stream<Arguments> providesTableTypeAndTable() {
		// Diff state is already tested in TableTestData
		return Arrays.asList(TableType.INITIAL, TableType.FINAL)
				.stream()
				.flatMap(state -> PtTable.tablesToTest.stream()
						.map(table -> new Pair<TableType, PtTable>(state,
								table)))
				.map(pair -> Arguments.argumentSet(
						pair.getFirst() + " - " + pair.getSecond().shortcut(),
						pair.getFirst(), pair.getSecond()));
	}

	TableType tableState;
	SWTBotCombo tableTypeSelectionCombo;
	SWTBotCombo controlAreaCombo;

	@BeforeAll
	void beforeAll() throws Exception {
		super.beforeEach();
		tableTypeSelectionCombo = bot.comboBox(DIFF_STATE_TEXT);
		controlAreaCombo = bot.comboBox(PLANUNG_BEREICH_OPTION);
	}

	@BeforeEach
	@Override
	public void beforeEach() throws Exception {
		// do nothing
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
	public String getTestTableReferenceName() {
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
		// because changing the table type resets the control area we need to switch back to all data
		// TODO: as soon as the control area stays stable we should move this into the @BeforeAll hook 
		controlAreaCombo.setSelection(ALL_DATA_OPTION);
	}

	protected void whenExistReferenceCSV() {
		assertNotNull(referenceData);
		assertFalse(referenceData.isEmpty());
	}

	@AfterEach
	void afterEach() throws Exception {
		final SWTBotCTabItem cTabItem = bot.cTabItem(tableToTest.tableName());
		UIThreadRunnable.syncExec(() -> {
			cTabItem.activate();
			cTabItem.close();
		});
	}

	/**
	 * Compare table data with reference file
	 * 
	 * @throws Exception
	 */
	@ParameterizedTest(name = "{argumentSetName}")
	@MethodSource("providesTableTypeAndTable")
	@ExtendWith(ReopenTableBeforeFailHandle.class)
	void testTableStateData(final TableType tableType, final PtTable table)
			throws Exception {
		this.tableState = tableType;
		whenChangeTableType();
		this.tableToTest = table;
		givenNattableBot(table.tableName());
		givenReferenceCSV(table);
		givenFixedColumnCount(table);
		whenExistReferenceCSV();
		thenRowAndColumnCountEqualReferenceCSV();
		thenExpectTableDataEqualReferenceCSV();
	}
}
