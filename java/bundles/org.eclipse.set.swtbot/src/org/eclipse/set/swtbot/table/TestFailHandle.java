/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.swtbot.table;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Optional;

import org.eclipse.set.swtbot.utils.AbstractSWTBotTest;
import org.eclipse.set.swtbot.utils.MockDialogService;
import org.eclipse.set.swtbot.utils.MockDialogServiceContextFunction;
import org.eclipse.set.swtbot.utils.SWTBotUtils;
import org.eclipse.swt.SWT;
import org.eclipse.swtbot.nebula.nattable.finder.widgets.SWTBotNatTable;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotCTabItem;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Export current state and reference table, when test fail
 * 
 * @author truong
 */
@Disabled
public class TestFailHandle implements TestWatcher {
	private static final String CURRENT_CSV_EXTENSIONS = "_current.csv";
	private static final String DIFF_DIR = "diff";
	private static final String REFERENCE_CSV_EXTENSIONS = "_reference.csv";
	private final Logger LOGGER = LoggerFactory.getLogger(TestFailHandle.class);

	@Override
	public void testFailed(final ExtensionContext context,
			final Throwable cause) {
		final Optional<Object> testInstance = context.getTestInstance();
		if (testInstance.isPresent() && testInstance
				.get() instanceof final AbstractTableTest tableTest) {
			exportCurrentCSV(tableTest);
			exportReferenceCSV(tableTest);
		}
		TestWatcher.super.testFailed(context, cause);
	}

	protected void exportCurrentCSV(final AbstractTableTest tableTest) {
		final MockDialogService mockDialogService = MockDialogServiceContextFunction.mockService;
		final File file = getExportFile(tableTest, CURRENT_CSV_EXTENSIONS);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		mockDialogService.exportFileDialogHandler = filter -> {
			return Optional.of(file.toPath());
		};

		final SWTBotNatTable nattableBot = SWTBotUtils
				.waitForNattable(AbstractSWTBotTest.bot, 30000);
		// Select a table cell to select Nattable. Don't take (0,1) because, it
		// can resort the table
		nattableBot.setFocus();
		// nattableBot.click(2, 2);
		nattableBot.pressShortcut(SWT.MOD1, 'r');
		AbstractSWTBotTest.bot.waitUntil(new DefaultCondition() {

			@Override
			public String getFailureMessage() {
				return "Failed to wait for Application";
			}

			@Override
			public boolean test() throws Exception {
				return Files.exists(file.toPath(), LinkOption.NOFOLLOW_LINKS);
			}
		}, 2 * 60 * 1000);
	}

	protected void exportReferenceCSV(final AbstractTableTest tableTest) {
		final File outFile = getExportFile(tableTest, REFERENCE_CSV_EXTENSIONS);
		if (!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdirs();
		}
		final String tableName = tableTest.getTestTableReferenceName();
		final InputStream referenceResource = tableTest.getTestResourceClass()
				.getClassLoader()
				.getResourceAsStream(tableTest.getReferenceDir() + tableName
						+ REFERENCE_CSV_EXTENSIONS);
		// New table given't reference datei
		if (referenceResource == null) {
			LOGGER.debug(String.format("Cannot find file: %s",
					tableName + REFERENCE_CSV_EXTENSIONS));
			return;
		}
		try (final InputStream resourceStream = referenceResource;
				final FileOutputStream outputStream = new FileOutputStream(
						outFile);) {
			outputStream.write(resourceStream.readAllBytes());
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	protected File getExportFile(final AbstractTableTest tableTest,
			final String extension) {
		try {
			final URL parent = tableTest.getClass()
					.getProtectionDomain()
					.getCodeSource()
					.getLocation();
			final File parentDir = Path.of(parent.toURI()).toFile();
			return new File(parentDir,
					Path.of(tableTest.getReferenceDir()
							.replaceFirst(".*table_reference/", DIFF_DIR + "/"),
							tableTest.getTestTableReferenceName() + extension)
							.toString());
		} catch (final URISyntaxException e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Special fail handle that reopens the table so that the current CSV can be
	 * exported
	 */
	public static class ReopenTableBeforeFailHandle extends TestFailHandle {
		@Override
		public void testFailed(final ExtensionContext context,
				final Throwable cause) {
			final Optional<Object> testInstance = context.getTestInstance();
			if (testInstance.isPresent() && testInstance
					.get() instanceof final AbstractTableTest tableTest) {
				tableTest.givenNattableBot(tableTest.tableToTest.tableName());
				super.testFailed(context, cause);
				final SWTBotCTabItem cTabItem = tableTest.bot
						.cTabItem(tableTest.tableToTest.tableName());
				UIThreadRunnable.syncExec(() -> {
					cTabItem.activate();
					cTabItem.close();
				});
			}
		}
	}
}
