/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot.table;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.set.utils.table.BodyLayerStack;
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
	private int fixedColumnCount;

	private GridLayer gridLayer;
	List<CSVRecord> referenceData = new LinkedList<>();

	private void compareValue(final ILayer nattableLayer, final int startRow,
			final int endRow) {

		for (int rowIndex = 0; rowIndex < endRow; rowIndex++) {
			for (int columnIndex = 0; columnIndex < nattableLayer
					.getPreferredColumnCount()
					- fixedColumnCount; columnIndex++) {
				try {
					final String cellValue = nattableLayer
							.getDataValueByPosition(columnIndex, rowIndex)
							.toString()
							.replaceAll(CELL_VALUE_REPLACE_REGEX, "");
					final String referenceValue = referenceData
							.get(rowIndex + startRow).get(columnIndex + 1)
							.replaceAll(CELL_VALUE_REPLACE_REGEX, "");
					assertEquals(referenceValue, cellValue);
				} catch (final Exception e) {
					System.out.println(e);
				}

			}
		}
	}

	private void givenFixedColumnCount(final PtTable table) {
		fixedColumnCount = table.fixedColumns().size();
	}

	private void givenNattableGridLayer() {
		final NatTable natTable = nattableBot.widget;
		final ILayer layer = natTable.getLayer();
		assertInstanceOf(GridLayer.class, layer);
		gridLayer = (GridLayer) layer;
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
		givenNattableGridLayer();
		whenExistReferenceCSV();
		thenPtTableColumnHeaderEqualReferenceCSV();
		thenPtTableDataEqualReferenceCSV();
	}

	protected void thenPtTableColumnHeaderEqualReferenceCSV() {
		final int rowCount = gridLayer.getColumnHeaderLayer().getRowCount();
		compareValue(gridLayer.getColumnHeaderLayer(), 0, rowCount);

	}

	protected void thenPtTableDataEqualReferenceCSV() {

		assertInstanceOf(BodyLayerStack.class, gridLayer.getBodyLayer());
		final BodyLayerStack bodyLayerStack = (BodyLayerStack) gridLayer
				.getBodyLayer();
		final int startRow = gridLayer.getColumnHeaderLayer().getRowCount();
		compareValue(bodyLayerStack.getSelectionLayer(), startRow,
				bodyLayerStack.getSelectionLayer().getPreferredRowCount());
	}

	protected void whenExistReferenceCSV() {
		assertNotNull(referenceData);
		assertFalse(referenceData.isEmpty());
	}
}
