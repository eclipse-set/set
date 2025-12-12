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

import java.util.List;
import java.util.Optional;

import org.eclipse.set.swtbot.utils.AbstractPPHNTest;
import org.eclipse.set.swtbot.utils.SWTBotTestFile;
import org.eclipse.set.swtbot.utils.SWTBotUtils;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class for test compare plan
 * 
 * @author truong
 */
public abstract class AbstractPlanCompareTest extends AbstractPPHNTest {
	private final Logger LOGGER = LoggerFactory
			.getLogger(AbstractPlanCompareTest.class);
	
	
	protected void openSecondPlan() throws Exception {
		LOGGER.info(getFilePath(getComparePlan().getFullName()).toString());
		getDialogService().openFileDialogHandler = filters -> Optional
				.of(getFilePath(getComparePlan().getFullName()));

		bot.waitUntil(SWTBotUtils.botWaitUntil(bot, () -> {
			final var shell = bot.activeShell();
			return Boolean.valueOf(shell.getText()
					.endsWith(SWTBotTestFile.PPHN_1_10_0_3.getFullName()));
		}));

		SWTBotMenu dateiMenu = bot.menu("Datei");
		SWTBotMenu comparePlanMenu = dateiMenu.menu("Vergleichsplanung laden");
		comparePlanMenu.menu("Andere Planung laden...").click();
		bot.waitUntil(SWTBotUtils.botWaitUntil(bot, () -> {
			final var shell = List.of(bot.shells());
			return Boolean.valueOf(shell.stream()
					.filter(c -> "Statusinformationen".equals(c.getText()))
					.findAny()
					.isEmpty());
		}), 5l * 60 * 1000);
		bot.sleep(500);
	}
	
	@Override
	public String getReferenceDir() {
		return TEST_RESOURCE_DIR + "table_reference/"
				+ getTestFile().getShortName() + "/";
	}

	protected SWTBotTestFile getComparePlan() {
		return SWTBotTestFile.PPHN_1_10_0_1;
	}
}
