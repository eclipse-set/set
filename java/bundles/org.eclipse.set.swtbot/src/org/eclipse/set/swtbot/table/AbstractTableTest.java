/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot.table;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.allOf;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.widgetOfType;
import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.withText;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.set.swtbot.utils.AbstractPPHNTest;
import org.eclipse.set.swtbot.utils.SWTBotUtils;
import org.eclipse.set.swtbot.utils.SWTBotUtils.NattableLayers;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swtbot.nebula.nattable.finder.widgets.SWTBotNatTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotExpandItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.provider.Arguments;

/**
 * Abstract class for table test
 * 
 * @author Truong
 *
 */
public abstract class AbstractTableTest extends AbstractPPHNTest {
	protected static final String CELL_VALUE_REPLACE_REGEX = "[\\n\\r]";
	protected static final String ZERO_WIDTH_SPACE = "\\u200B";

	protected static Stream<Arguments> providesPtTable() {
		return PtTable.tablesToTest.stream().map(table -> Arguments.of(table));
	}

	protected int fixedColumnCount = 1;
	protected NattableLayers layers;
	protected List<CSVRecord> referenceData = new LinkedList<>();

	@Override
	@BeforeEach
	public void beforeEach() throws Exception {
		super.beforeEach();
		@SuppressWarnings("unchecked")
		final ExpandItem expandItem = bot.widget(
				allOf(widgetOfType(ExpandItem.class), withText("Tabellen")));
		final SWTBotExpandItem swtBotExpandItem = new SWTBotExpandItem(
				expandItem);
		assertNotNull(swtBotExpandItem);
		swtBotExpandItem.expand();
	}

	@Override
	public String getReferenceDir() {
		return TEST_RESOURCE_DIR + "table_reference/"
				+ getTestFile().getShortName() + "/";
	}

	public abstract String getTestTableName();

	protected void compareValue(final ILayer nattableLayer, final int startRow,
			final int endRow) {
		for (int rowIndex = 0; rowIndex < endRow; rowIndex++) {
			for (int columnIndex = 0; columnIndex < layers.selectionLayer()
					.getPreferredColumnCount(); columnIndex++) {
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

	protected int getNattableHeaderRowCount() {
		return layers.columnHeaderLayer().getRowCount();
	}

	protected void givenNattableBot(final String tableName) {
		bot.button(tableName).click();
		final SWTBotNatTable nattableBot = SWTBotUtils.waitForNattable(bot,
				30000);
		layers = SWTBotUtils.getNattableLayers(nattableBot);
	}

	protected void thenRowAndColumnCountEqualReferenceCSV() {
		final int nattableColumnCount = layers.gridLayer()
				.getPreferredColumnCount() - fixedColumnCount;
		final int referenceColumnCount = referenceData.get(0).size();
		assertEquals(referenceColumnCount, nattableColumnCount);
		final int nattableRowCount = getNattableHeaderRowCount()
				+ +layers.selectionLayer().getRowCount();
		final int referenceRowCount = referenceData.size();
		assertEquals(referenceRowCount, nattableRowCount);
	}

	protected void thenTableDataEqualReferenceCSV() {
		final int startRow = getNattableHeaderRowCount();
		assertDoesNotThrow(() -> compareValue(layers.selectionLayer(), startRow,
				layers.selectionLayer().getRowCount()));
	}
}
