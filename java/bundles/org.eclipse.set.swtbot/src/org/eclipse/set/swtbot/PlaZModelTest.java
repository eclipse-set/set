/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.set.swtbot.utils.AbstractPPHNTest;
import org.eclipse.set.swtbot.utils.SWTBotUtils;
import org.eclipse.set.swtbot.utils.SWTBotUtils.NattableLayers;
import org.eclipse.swtbot.nebula.nattable.finder.widgets.SWTBotNatTable;
import org.junit.jupiter.api.Test;

/**
 * Test for changes in PlaZ Model
 */
class PlaZModelTest extends AbstractPPHNTest {

	private static final String CELL_VALUE_REPLACE_REGEX = "[\\n\\r]";
	private int fixedColumnCount;
	List<CSVRecord> referenceData = new LinkedList<>();

	private NattableLayers layers;

	private void compareValue(final ILayer nattableLayer, final int startRow, final int endRow) {

		for (int rowIndex = 0; rowIndex < endRow; rowIndex++) {
			for (int columnIndex = 0; columnIndex < nattableLayer.getPreferredColumnCount()
					- fixedColumnCount; columnIndex++) {
				final String cellValue = nattableLayer.getDataValueByPosition(columnIndex, rowIndex).toString()
						.replaceAll(CELL_VALUE_REPLACE_REGEX, "");
				final String referenceValue = referenceData.get(rowIndex + startRow).get(columnIndex + 1)
						.replaceAll(CELL_VALUE_REPLACE_REGEX, "");
				assertEquals(referenceValue, cellValue);
			}
		}
	}

	private void thenRowAndColumnCountEqualReferenceCSV() {
		final int nattableColumnCount = layers.gridLayer().getPreferredColumnCount() - fixedColumnCount;
		final int referenceColumnCount = referenceData.get(0).size();
		assertEquals(referenceColumnCount, nattableColumnCount);
		final int nattableRowCount = layers.columnHeaderLayer().getRowCount() + layers.selectionLayer().getRowCount();
		final int referenceRowCount = referenceData.size();
		assertEquals(referenceRowCount, nattableRowCount);
	}

	protected void givenReferenceCSV() throws IOException {
		referenceData = loadReferenceFile("plaz_model_reference.csv");
	}

	/**
	 * Compare plaz model table data with reference file
	 * 
	 * @throws Exception
	 */
	@Test
	protected void testTableData() throws Exception {
		givenReferenceCSV();
		whenOpeningPlaZModelNatTable();
		thenRowAndColumnCountEqualReferenceCSV();
		thenPtTableDataEqualsReferenceCSV();
	}

	private void thenPtTableDataEqualsReferenceCSV() {
		final int rowCount = layers.columnHeaderLayer().getRowCount();
		assertDoesNotThrow(() -> compareValue(layers.gridLayer().getColumnHeaderLayer(), 0, rowCount));
		assertDoesNotThrow(
				() -> compareValue(layers.selectionLayer(), rowCount, layers.selectionLayer().getRowCount()));
	}

	private void whenOpeningPlaZModelNatTable() {
		bot.button("PlaZ-Modell").click();
		final SWTBotNatTable nattableBot = SWTBotUtils.waitForNattable(bot, 30000);
		layers = SWTBotUtils.getNattableLayers(nattableBot);
	}
}
