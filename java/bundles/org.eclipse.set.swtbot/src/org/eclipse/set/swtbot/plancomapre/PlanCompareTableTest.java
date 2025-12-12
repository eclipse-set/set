/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.swtbot.plancomapre;

import static org.eclipse.swtbot.swt.finder.matchers.WidgetMatcherFactory.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Stream;

import org.eclipse.set.swtbot.table.PtTable;
import org.eclipse.set.swtbot.utils.SWTBotUtils;
import org.eclipse.swt.widgets.ExpandItem;
import org.eclipse.swtbot.nebula.nattable.finder.widgets.SWTBotNatTable;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotExpandItem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test table by compare plan
 * 
 * @author truong
 */
@TestInstance(Lifecycle.PER_CLASS)
public class PlanCompareTableTest extends AbstractPlanCompareTest {
	PtTable tableToTest;
	SWTBotNatTable nattableBot;
	
	protected static Stream<Arguments> providesPtTable() {
		return PtTable.tablesToTest.stream().map(table -> Arguments.of(table));
	}
	
	@BeforeEach
	@Override
	public void beforeEach() throws Exception {
		// do nothing
	}
	
	// The test file should only open one times by this test
	@BeforeAll
	void beforeAll() throws Exception {
		super.beforeEach();
		openSecondPlan();
		@SuppressWarnings("unchecked")
		final List<? extends ExpandItem> expandItems = bot
				.widgets(allOf(widgetOfType(ExpandItem.class), withRegex(
						"^.+ – (Zusatzt|T)abellen( \\(in Entwicklung\\))?$")));
		expandItems.forEach(item -> {
			final SWTBotExpandItem swtBotExpandItem = new SWTBotExpandItem(
					item);
			assertNotNull(swtBotExpandItem);
			swtBotExpandItem.expand();
		});
	}
	
	/**
	 * Test Open Table
	 * @param table
	 */
	@ParameterizedTest
	@MethodSource("providesPtTable")
	protected void testOpenTable(PtTable table) {
		givenNattableBot(table.tableName());
		assertNotNull(nattableBot);
		
	}
	
	protected void givenNattableBot(final String tableName) {
		bot.button(tableName).click();
		nattableBot = SWTBotUtils.waitForNattable(bot, 30000);
	}
}
