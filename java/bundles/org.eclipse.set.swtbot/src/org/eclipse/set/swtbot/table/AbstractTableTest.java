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
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.freeze.FreezeLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.set.swtbot.utils.AbstractPPHNTest;
import org.eclipse.set.utils.table.BodyLayerStack;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swtbot.nebula.nattable.finder.SWTNatTableBot;
import org.eclipse.swtbot.nebula.nattable.finder.widgets.SWTBotNatTable;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotExpandItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Abstract class for table test
 * 
 * @author Truong
 *
 */
public abstract class AbstractTableTest extends AbstractPPHNTest {
	protected static final String REFERENCE_DIR = "test_res/table_reference/";

	private static List<CSVRecord> loadReferenceFile(final String tableName)
			throws IOException {

		final String fileName = REFERENCE_DIR + tableName + "_reference.csv";
		final Builder csvBuilder = CSVFormat.Builder.create(CSVFormat.DEFAULT);
		csvBuilder.setDelimiter(";");
		try (InputStream inputStream = AbstractTableTest.class.getClassLoader()
				.getResourceAsStream(fileName);
				final Reader reader = new InputStreamReader(inputStream);
				final CSVParser csvParser = new CSVParser(reader,
						csvBuilder.build())) {
			return csvParser.getRecords();
		}
	}

	protected SWTBotNatTable nattableBot;

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

		bot.button(getTableName()).click();
		bot.waitUntil(new DefaultCondition() {

			@Override
			public String getFailureMessage() {
				return "Faleid to load Table";
			}

			@Override
			public boolean test() throws Exception {
				nattableBot = new SWTNatTableBot().nattable();
				return nattableBot != null;
			}
		}, 30000);
	}

	protected void fixedColsTest(final int... fixedCols) {
		final NatTable natTable = nattableBot.widget;
		final ILayer layer = natTable.getLayer();
		assertInstanceOf(GridLayer.class, layer);
		final GridLayer gridLayer = (GridLayer) layer;

		assertInstanceOf(BodyLayerStack.class, gridLayer.getBodyLayer());
		final BodyLayerStack bodyLayerStack = (BodyLayerStack) gridLayer
				.getBodyLayer();
		final FreezeLayer freezeLayer = bodyLayerStack.getFreezeLayer();
		assertTrue(freezeLayer.isFrozen());

		int lastFixedColumn = 0;
		int firstFixedColumn = 0;
		if (fixedCols.length > 1) {
			final Set<Integer> cols = new HashSet<>();
			for (final int col : fixedCols) {
				cols.add(Integer.valueOf(col));
			}
			lastFixedColumn = Collections.max(cols).intValue();
			firstFixedColumn = Collections.min(cols).intValue();
		}

		assertEquals(firstFixedColumn,
				freezeLayer.getTopLeftPosition().columnPosition);
		assertEquals(-1, freezeLayer.getTopLeftPosition().rowPosition);
		assertEquals(lastFixedColumn,
				freezeLayer.getBottomRightPosition().columnPosition);
		assertEquals(-1, freezeLayer.getBottomRightPosition().rowPosition);
	}

	/**
	 * @return table shortcut name
	 */
	protected abstract String getShortcut();

	/**
	 * @return table name
	 */
	protected abstract String getTableName();

	/**
	 * Compare table data with reference file
	 * 
	 * @throws Exception
	 */
	@Test
	void testTableData() throws Exception {
		final List<CSVRecord> referenceData = loadReferenceFile(getShortcut());
		assertNotNull(referenceData);
		assertFalse(referenceData.isEmpty());
		for (int rowIndex = 0; rowIndex < nattableBot.rowCount(); rowIndex++) {
			for (int columnIndex = 0; columnIndex < nattableBot
					.columnCount(); columnIndex++) {
				// Skip first cell
				if (rowIndex == 0 && columnIndex == 0) {
					continue;
				}
				final String cellValue = nattableBot
						.getCellDataValueByPosition(rowIndex, columnIndex)
						.replaceAll("[\\n\\r]", "");
				final String referenceValue = referenceData.get(rowIndex)
						.get(columnIndex).replaceAll("[\\n\\r]", "");

				assertEquals(referenceValue, cellValue);
			}
		}
	}
}
