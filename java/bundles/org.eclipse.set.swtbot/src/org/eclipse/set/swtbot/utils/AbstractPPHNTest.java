/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot.utils;

import static org.eclipse.set.swtbot.utils.SWTBotTestFile.PPHN_1_10_0_3;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base test for test which require a loaded PPHN file
 */
public abstract class AbstractPPHNTest extends AbstractSWTBotTest {

	protected static final String TEST_RESOURCE_DIR = "test_res/";

	private final Logger LOGGER = LoggerFactory
			.getLogger(AbstractPPHNTest.class);

	@Override
	@BeforeEach
	public void beforeEach() throws Exception {
		super.beforeEach();
		LOGGER.info(getFilePath(getTestFile().getFullName()).toString());
		bot.waitUntil(SWTBotUtils.botWaitUntil(bot, () -> {
			final var shell = bot.activeShell();
			final String toolboxName = System.getProperty("toolbox.name",
					DEFAULT_TOOLBOX_NAME);
			return Boolean.valueOf(shell.getText().startsWith(toolboxName));
		}));

		bot.menu("Datei").menu("Öffnen...").click();
		bot.shell("Statusinformationen");
		bot.waitUntil(SWTBotUtils.botWaitUntil(bot, () -> {
			final var shell = List.of(bot.shells());
			return Boolean.valueOf(shell.stream()
					.filter(c -> "Statusinformationen".equals(c.getText()))
					.findAny()
					.isEmpty());
		}), 5l * 60 * 1000);
		bot.sleep(500);
	}

	/**
	 * @return location of reference file
	 */
	public abstract String getReferenceDir();

	@Override
	public TestFile getTestFile() {
		return PPHN_1_10_0_3;
	}

	protected List<CSVRecord> loadReferenceFile(final String tableName)
			throws IOException {
		final String fileName = getReferenceDir() + tableName
				+ "_reference.csv";
		final Builder csvBuilder = CSVFormat.Builder.create(CSVFormat.DEFAULT);
		csvBuilder.setDelimiter(";");
		final InputStream referenceResource = getTestResourceClass()
				.getClassLoader()
				.getResourceAsStream(fileName);
		if (referenceResource == null) {
			fail(String.format("Cannot find file: %s", fileName));
		}
		try (InputStream inputStream = referenceResource;
				final Reader reader = new InputStreamReader(inputStream);
				final CSVParser csvParser = new CSVParser(reader,
						csvBuilder.build())) {
			return csvParser.getRecords();
		}
	}
}
