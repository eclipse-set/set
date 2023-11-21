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
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVRecord;
import org.eclipse.set.swtbot.utils.AbstractPPHNTest;
import org.eclipse.set.swtbot.utils.SWTBotUtils;
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
	protected static List<CSVRecord> loadReferenceFile(final String tableName) throws IOException {
		return AbstractPPHNTest.loadReferenceFile("table_reference/" + tableName);
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
		final ExpandItem expandItem = bot.widget(allOf(widgetOfType(ExpandItem.class), withText("Tabellen")));
		final SWTBotExpandItem swtBotExpandItem = new SWTBotExpandItem(expandItem);
		assertNotNull(swtBotExpandItem);
		swtBotExpandItem.expand();
	}

	protected void givenNattableBot(final PtTable table) {
		bot.button(table.tableName()).click();
		nattableBot = SWTBotUtils.waitForNattable(bot, 30000);
	}
}
