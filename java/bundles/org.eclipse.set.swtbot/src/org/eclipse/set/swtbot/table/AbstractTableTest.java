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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.set.swtbot.utils.AbstractPPHNTest;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swtbot.nebula.nattable.finder.SWTNatTableBot;
import org.eclipse.swtbot.nebula.nattable.finder.widgets.SWTBotNatTable;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
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
	protected static final String REFERENCE_DIR = "test_res/table_reference/";

	protected static List<CSVRecord> loadReferenceFile(final String tableName)
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

	protected static Stream<Arguments> providesPtTable() {
		return PtTable.tablesToTest.stream().map(table -> Arguments.of(table));
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
	}

	void givenNattableBot(final PtTable table) {
		bot.button(table.tableName()).click();
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
}