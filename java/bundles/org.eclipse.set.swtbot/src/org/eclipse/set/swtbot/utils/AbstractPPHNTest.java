/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat.Builder;
import org.eclipse.set.swtbot.table.AbstractTableTest;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base test for test which require a loaded PPHN file
 */
public abstract class AbstractPPHNTest extends AbstractSWTBotTest {

	protected static final String PPHN_FILE_NAME = "PPHN_1.10.0.1_01-02_Ibn-Z._-_2._AeM_2022-05-17_13-44_tg2.planpro";
	private final Logger LOGGER = LoggerFactory.getLogger(AbstractPPHNTest.class);

	protected static final String REFERENCE_DIR = "test_res/";

	protected static List<CSVRecord> loadReferenceFile(final String tableName) throws IOException {

		final String fileName = REFERENCE_DIR + tableName + "_reference.csv";
		final Builder csvBuilder = CSVFormat.Builder.create(CSVFormat.DEFAULT);
		csvBuilder.setDelimiter(";");
		try (InputStream inputStream = AbstractTableTest.class.getClassLoader().getResourceAsStream(fileName);
				final Reader reader = new InputStreamReader(inputStream);
				final CSVParser csvParser = new CSVParser(reader, csvBuilder.build())) {
			return csvParser.getRecords();
		}
	}

	@Override
	@BeforeEach
	public void beforeEach() throws Exception {
		super.beforeEach();
		LOGGER.info(getFilePath(PPHN_FILE_NAME).toString());
		dialogService.openFileDialogHandler = filters -> Optional.of(getFilePath(PPHN_FILE_NAME));
		bot.waitUntil(new DefaultCondition() {
			@Override
			public String getFailureMessage() {
				return "Failed to wait for Application";
			}

			@Override
			public boolean test() throws Exception {
				final var shell = bot.activeShell();
				String toolboxName = System.getProperty("toolbox.name", DEFAULT_TOOLBOX_NAME);
				return shell.getText().startsWith(toolboxName);
			}

		});

		bot.menu("Datei").menu("Öffnen...").click();
		bot.shell("Statusinformationen");
		bot.waitUntil(new DefaultCondition() {
			@Override
			public String getFailureMessage() {
				return "Failed to wait for Application";
			}

			@Override
			public boolean test() throws Exception {
				final var shell = List.of(bot.shells());
				return shell.stream().filter(c -> "Statusinformationen".equals(c.getText())).findAny().isEmpty();
			}

		}, 5l * 60 * 1000);
		bot.sleep(500);
	}
}
