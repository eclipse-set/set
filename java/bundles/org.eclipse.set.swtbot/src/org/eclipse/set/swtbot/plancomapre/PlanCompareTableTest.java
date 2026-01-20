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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.stream.Stream;

import org.eclipse.set.swtbot.table.PtTable;
import org.eclipse.set.swtbot.utils.SWTBotUtils;
import org.eclipse.swtbot.nebula.nattable.finder.widgets.SWTBotNatTable;
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
	protected static Stream<Arguments> providesPtTable() {
		return PtTable.tablesToTest.stream().map(Arguments::of);
	}

	SWTBotNatTable nattableBot;

	@BeforeEach
	@Override
	public void beforeEach() throws Exception {
		// do nothing
	}

	protected void givenNattableBot(final String tableName) {
		bot.button(tableName).click();
		nattableBot = SWTBotUtils.waitForNattable(bot, 30000);
	}

	/**
	 * Test Open Table
	 * 
	 * @param table
	 */
	@ParameterizedTest
	@MethodSource("providesPtTable")
	protected void testOpenTable(final PtTable table) {
		givenNattableBot(table.tableName());
		assertNotNull(nattableBot);

	}

	// The test file should only open one times by this test
	@BeforeAll
	void beforeAll() throws Exception {
		super.beforeEach();
		openSecondPlan();
		SWTBotUtils.expandTableMenu(bot);
	}
}
