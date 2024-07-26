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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.util.Optional;

import org.eclipse.set.swtbot.utils.AbstractSWTBotTest;
import org.eclipse.set.swtbot.utils.MockDialogService;
import org.eclipse.set.swtbot.utils.MockDialogServiceContextFunction;
import org.eclipse.set.swtbot.utils.SWTBotUtils;
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
	private static final String DIFF_DIR = "diff/";
	private static final String REFERENCE_CSV_EXTENSIONS = "_reference.csv";

	protected static void exportCurrentCSV(final String tableName) {
		final MockDialogService mockDialogService = MockDialogServiceContextFunction.mockService;
		final URL projectLocation = TestFailHandle.class.getProtectionDomain()
				.getCodeSource().getLocation();
		final File file = new File(projectLocation.getPath() + DIFF_DIR
				+ tableName + CURRENT_CSV_EXTENSIONS);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdir();
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

	protected static void exportReferenceCSV(final String tableName,
			final String parentDir) {
		final URL outLocation = TestFailHandle.class.getProtectionDomain()
				.getCodeSource().getLocation();
		final File outFile = new File(outLocation.getPath() + DIFF_DIR
				+ tableName + REFERENCE_CSV_EXTENSIONS);
		if (!outFile.getParentFile().exists()) {
			outFile.getParentFile().mkdir();
		}
		try (final InputStream resourceStream = AbstractSWTBotTest.class
				.getClassLoader().getResourceAsStream(
						parentDir + tableName + REFERENCE_CSV_EXTENSIONS);
				final FileOutputStream outputStream = new FileOutputStream(
						outFile);) {
			outputStream.write(resourceStream.readAllBytes());
		} catch (final IOException e) {
			throw new RuntimeException(String.format("Cannot find file: %s",
					tableName + REFERENCE_CSV_EXTENSIONS));
		}
	}

	@Override
	public void testFailed(final ExtensionContext context,
			final Throwable cause) {
		final Optional<Object> testInstance = context.getTestInstance();
		if (testInstance.isPresent() && testInstance
				.get() instanceof final AbstractTableTest tableTest) {
			exportCurrentCSV(tableTest.getTestTableName());
			exportReferenceCSV(tableTest.getTestTableName(),
					tableTest.getReferenceDir());
		}
		TestWatcher.super.testFailed(context, cause);
	}
}