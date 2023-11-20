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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.set.swtbot.utils.SWTBotUtils;
import org.eclipse.set.swtbot.utils.SWTBotUtils.NattableLayers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Compare table data with csv reference
 * 
 * @author truong
 *
 */
public class TableDataTest extends AbstractTableTest {

	private static final String CELL_VALUE_REPLACE_REGEX = "[\\n\\r]";

	private NattableLayers layers;

	private int fixedColumnCount;
	List<CSVRecord> referenceData = new LinkedList<>();

	private void compareValue(final ILayer nattableLayer, final int startRow, final int endRow) {

		for (int rowIndex = 0; rowIndex < endRow; rowIndex++) {
			for (int columnIndex = 0; columnIndex < nattableLayer.getPreferredColumnCount()
					- fixedColumnCount; columnIndex++) {
				final String cellValue = nattableLayer
						.getDataValueByPosition(columnIndex, rowIndex)
						.toString().replaceAll(CELL_VALUE_REPLACE_REGEX, "");
				final String referenceValue = referenceData
						.get(rowIndex + startRow).get(columnIndex + 1)
						.replaceAll(CELL_VALUE_REPLACE_REGEX, "")
						// By Nattable 2.2.0 add to much double quote into
						// richtext
						// value
						.replace("\"\"", "\"");
				assertEquals(referenceValue, cellValue);
			}
		}

	}

	private void givenFixedColumnCount(final PtTable table) {
		fixedColumnCount = table.fixedColumns().size();
	}

	private void givenNattableLayers() {
		layers = SWTBotUtils.getNattableLayers(nattableBot);
	}

	private void thenRowAndColumnCountEqualReferenceCSV() {
		final int nattableColumnCount = layers.gridLayer().getPreferredColumnCount() - fixedColumnCount;
		final int referenceColumnCount = referenceData.get(0).size();
		assertEquals(referenceColumnCount, nattableColumnCount);
		final int nattableRowCount = layers.columnHeaderLayer().getRowCount() + layers.selectionLayer().getRowCount();
		final int referenceRowCount = referenceData.size();
		assertEquals(referenceRowCount, nattableRowCount);
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
		givenNattableBot(table);
		givenReferenceCSV(table);
		givenFixedColumnCount(table);
		givenNattableLayers();
		whenExistReferenceCSV();
		thenRowAndColumnCountEqualReferenceCSV();
		thenPtTableColumnHeaderEqualReferenceCSV();
		thenPtTableDataEqualReferenceCSV();
	}

	protected void thenPtTableColumnHeaderEqualReferenceCSV() {
		final int rowCount = layers.gridLayer().getColumnHeaderLayer().getRowCount();
		assertDoesNotThrow(() -> compareValue(layers.gridLayer().getColumnHeaderLayer(), 0, rowCount));

	}

	protected void thenPtTableDataEqualReferenceCSV() {
		final int startRow = layers.columnHeaderLayer().getRowCount();
		assertDoesNotThrow(
				() -> compareValue(layers.selectionLayer(), startRow, layers.selectionLayer().getRowCount()));
	}

	protected void whenExistReferenceCSV() {
		assertNotNull(referenceData);
		assertFalse(referenceData.isEmpty());
	}
}
