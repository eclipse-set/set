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

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Optional;

import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.junit.jupiter.api.Test;

/**
 * Test open and close compare plan
 * 
 * @author truong
 */
public class PlanCompareTest extends AbstractPlanCompareTest {
	@Test
	void testOpenComparePlan() {
		assertDoesNotThrow(this::openSecondPlan);
		expectApplicationTitleAfterOpenComparePlanEquals();
	}

	@Test
	void testOpenCloseComparePlan() {
		assertDoesNotThrow(this::openSecondPlan);
		expectApplicationTitleAfterOpenComparePlanEquals();
		assertDoesNotThrow(this::whenCloseComparePlan);
		expectApplicationTitleAfterCloseComparePlanEquals();
	}

	void whenCloseComparePlan() {
		getDialogService().openFileDialogHandler = filter -> Optional.empty();
		SWTBotMenu dateiMenu = bot.menu("Datei");
		SWTBotMenu comparePlanMenu = dateiMenu.menu("Vergleichsplanung laden");
		comparePlanMenu.menu("Andere Planung laden...").click();
		bot.sleep(500);
	}

	protected void expectApplicationTitleAfterOpenComparePlanEquals() {
		String applicationTitle = bot.activeShell().getText();
		assertTrue(applicationTitle.endsWith(getTestFile().getFullName()
				+ ToolboxConstants.TITLE_FILE_NAME_SEPARATOR
				+ getComparePlan().getFullName()));
	}

	protected void expectApplicationTitleAfterCloseComparePlanEquals() {
		String applicationTitle = bot.activeShell().getText();
		assertTrue(applicationTitle.endsWith(getTestFile().getFullName()));
	}
}
