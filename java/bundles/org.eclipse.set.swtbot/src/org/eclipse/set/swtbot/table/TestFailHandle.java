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
import org.eclipse.set.swtbot.utils.TestFile;
import org.eclipse.swt.SWT;
import org.eclipse.swtbot.nebula.nattable.finder.widgets.SWTBotNatTable;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

/**
 * 
 */
@Disabled
public class TestFailHandle implements TestWatcher {
	private static final String CURRENT_CSV_EXTENSIONS = "_current.csv";
	private static final String DIFF_DIR = "diff";
	private static final String REFERENCE_CSV_EXTENSIONS = "_reference.csv";

	@Override
	public void testFailed(final ExtensionContext context,
			final Throwable cause) {
		final Optional<Object> testInstance = context.getTestInstance();
		if (testInstance.isPresent() && testInstance
				.get() instanceof final AbstractTableTest tableTest) {
			exportCurrentCSV(tableTest.getTestFile(),
					tableTest.getTestTableName(), tableTest.getClass());
			exportReferenceCSV(tableTest.getTestFile(),
					tableTest.getTestTableName(), tableTest.getReferenceDir(),
					tableTest.getTestResourceClass().getClassLoader(),
					tableTest.getClass());
		}
		TestWatcher.super.testFailed(context, cause);
	}

	protected void exportCurrentCSV(final TestFile testFile,
			final String tableName, final Class<?> testClass) {
		final MockDialogService mockDialogService = MockDialogServiceContextFunction.mockService;
		final File file = getExportFile(testFile,
				tableName + CURRENT_CSV_EXTENSIONS, testClass);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		mockDialogService.exportFileDialogHandler = filter -> {
			return Optional.of(file.toPath());
		};
		final SWTBotNatTable nattableBot = SWTBotUtils
				.waitForNattable(AbstractSWTBotTest.bot, 30000);
		nattableBot.click(4, 5);
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
		}, 5l * 60 * 1000);
	}

	protected void exportReferenceCSV(final TestFile testFile,
			final String tableName, final String parentDir,
			final ClassLoader resourceClassLoader, final Class<?> testClass) {
		final File outFile = getExportFile(testFile,
				tableName + REFERENCE_CSV_EXTENSIONS, testClass);
		if (!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdirs();
		}
		try (final InputStream resourceStream = resourceClassLoader
				.getResourceAsStream(
						parentDir + tableName + REFERENCE_CSV_EXTENSIONS);
				final FileOutputStream outputStream = new FileOutputStream(
						outFile);) {
			outputStream.write(resourceStream.readAllBytes());
		} catch (final IOException e) {
			throw new RuntimeException(String.format("Cannot find file: %s",
					tableName + REFERENCE_CSV_EXTENSIONS));
		}
	}

	protected File getExportFile(final TestFile testFile, final String csvFile,
			final Class<?> testClass) {
		try {
			final URL parent = testClass.getProtectionDomain().getCodeSource()
					.getLocation();
			final File parentDir = Path.of(parent.toURI()).toFile();
			return new File(parentDir, Path
					.of(DIFF_DIR, testFile.getShortName(), csvFile).toString());
		} catch (final URISyntaxException e) {
			throw new RuntimeException(e);
		}

	}
}