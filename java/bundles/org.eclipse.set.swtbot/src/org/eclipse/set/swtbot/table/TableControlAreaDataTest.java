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

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCTabItem;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCombo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.FieldSource;

/**
 * Test table data each Stell_Bereich
 * 
 * @author truong
 */
@TestInstance(Lifecycle.PER_CLASS)
public class TableControlAreaDataTest extends AbstractTableTest {
	private static String ALL_CONTROL_AREA = "Alle Stellbereiche";
	private static String ALL_DATA_OPTION = "Gesamter Dateiinhalt";
	private static String PLANUNG_BEREICH_OPTION = "Planungsbereich";

	private SWTBotCombo controlAreaCombo;
	List<String> controlAreas;
	boolean isTableDataEmpty = false;
	PtTable tableToTest;
	String testArea;

	@BeforeEach
	@Override
	public void beforeEach() throws Exception {
		// donothing
	}

	@Override
	public String getReferenceDir() {
		return super.getReferenceDir() + testArea + "/";
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

	protected void expectReferenceCSVNotEmpty() {
		assertNotNull(referenceData);
		assertFalse(referenceData.isEmpty());
	}

	protected void givenReferenceCSV(final PtTable table) throws IOException {
		referenceData = loadReferenceFile(table.shortcut());
	}

	@AfterEach
	void afterEach() throws Exception {
		for (PtTable table : PtTable.tablesToTest) {
			if (!table.tableName().endsWith("estw")) {
				continue;
			}
			final SWTBotCTabItem cTabItem = bot.cTabItem(table.tableName());
			UIThreadRunnable.syncExec(() -> {
				cTabItem.activate();
				cTabItem.close();
			});
		}

	}

	@BeforeAll
	void beforeAll() throws Exception {
		// Load test file first time to determine the control areas
		super.beforeEach();
		controlAreaCombo = bot.comboBox("Planungsbereich");
		controlAreas = Arrays.asList(controlAreaCombo.items())
				.stream()
				.filter(item -> !item.equals(ALL_DATA_OPTION)
						&& !item.equals(PLANUNG_BEREICH_OPTION)
						&& !item.equals(ALL_CONTROL_AREA))
				.toList();
		if (controlAreas.isEmpty()) {
			Assumptions.abort("Control area not exist");
		}
	}

	void expectMissingReferenceWhenTableDataEmpty() {
		isTableDataEmpty = layers.selectionLayer().getRowCount() == 0;
		if (isTableDataEmpty) {
			assertThrows(AssertionError.class,
					() -> givenReferenceCSV(tableToTest));
		}
	}

	/**
	 * Compare table data with reference file
	 * 
	 * @throws Exception
	 */
	@ParameterizedTest
	@FieldSource("controlAreas")
	void testTableControlAreaData(final String controlArea) throws Exception {
		controlAreaCombo.setSelection(controlArea);
		testArea = controlArea;
		for (final PtTable table : PtTable.tablesToTest) {
			if (!table.category().endsWith("estw")) {
				continue;
			}
			tableToTest = table;
			givenNattableBot(table.tableName());
			expectMissingReferenceWhenTableDataEmpty();
			if (!isTableDataEmpty) {
				givenReferenceCSV(table);
				expectReferenceCSVNotEmpty();
				givenFixedColumnCount(table);
				thenRowAndColumnCountEqualReferenceCSV();
				thenExpectTableDataEqualReferenceCSV();
			}
		}
	}
}
