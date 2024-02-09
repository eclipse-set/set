/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.set.swtbot.table.AbstractTableTest;
import org.eclipse.set.swtbot.utils.AbstractPPHNTest;
import org.eclipse.set.swtbot.utils.SWTBotUtils;
import org.eclipse.swtbot.nebula.nattable.finder.widgets.SWTBotNatTable;
import org.junit.jupiter.api.Test;

/**
 * Test for changes in PlaZ Model
 */
class PlaZModelTest extends AbstractTableTest {

	private static final String RICHTEXT_REPLACE_REGEX = "<[^>]+>";

	private void whenOpeningPlaZModelNatTable() {
		bot.button("PlaZ-Modell").click();
		final SWTBotNatTable nattableBot = SWTBotUtils.waitForNattable(bot,
				30000);
		layers = SWTBotUtils.getNattableLayers(nattableBot);
		bot.button("Alle ausklappen").click();
	}

	@Override
	protected void compareValue(final ILayer nattableLayer, final int startRow,
			final int endRow) {

		for (int rowIndex = 0; rowIndex < endRow; rowIndex++) {
			for (int columnIndex = 0; columnIndex < nattableLayer
					.getPreferredColumnCount()
					- fixedColumnCount; columnIndex++) {
				final String cellValue = nattableLayer
						.getDataValueByPosition(columnIndex, rowIndex)
						.toString().trim()
						.replaceAll(CELL_VALUE_REPLACE_REGEX, "")
						.replaceAll(RICHTEXT_REPLACE_REGEX, "")
						.replaceAll(ZERO_WIDTH_SPACE, "");

				final String referenceValue = referenceData
						.get(rowIndex + startRow).get(columnIndex)
						.replaceAll(CELL_VALUE_REPLACE_REGEX, "")
						.replaceAll(ZERO_WIDTH_SPACE, "");
				assertEquals(referenceValue, cellValue);
			}
		}
	}

	@Override
	protected int getNattableHeaderRowCount() {
		// Filter Row
		return super.getNattableHeaderRowCount() - 1;
	}

	protected void givenReferenceCSV() throws IOException {
		referenceData = AbstractPPHNTest.loadReferenceFile("plaz_model");
		// Remove CSV header info
		referenceData = referenceData.subList(4, referenceData.size());
	}

	/**
	 * Compare plaz model table data with reference file
	 * 
	 * @throws Exception
	 */
	@Test
	void testTableData() throws Exception {
		givenReferenceCSV();
		whenOpeningPlaZModelNatTable();
		thenRowAndColumnCountEqualReferenceCSV();
		thenTableDataEqualReferenceCSV();
	}
}
