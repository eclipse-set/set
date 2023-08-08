/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.swtbot.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base test for all swt bot tests
 *
 */
public abstract class AbstractSWTBotTest {
	protected static SWTBot bot;
	protected static MockDialogService dialogService;
	protected static final String TEST_FILE_DIR = "test_res/test_file/";

	/**
	 * Get path of the reference file
	 * 
	 * @param fileName
	 *            reference file name
	 * @return the path of reference file
	 */
	protected static Path getFilePath(final String fileName) {
		final URL projectLocation = AbstractSWTBotTest.class
				.getProtectionDomain().getCodeSource().getLocation();
		try (final InputStream resourceStream = AbstractSWTBotTest.class
				.getClassLoader().getResourceAsStream(TEST_FILE_DIR + fileName);
				final FileOutputStream outputStream = new FileOutputStream(
						new File(projectLocation.getPath() + fileName));) {
			outputStream.write(resourceStream.readAllBytes());
		} catch (final IOException e) {
			throw new RuntimeException(
					String.format("Cannot find file: %s", fileName));
		}
		final File testfile = new File(projectLocation.getPath() + fileName);
		return testfile.toPath();
	}

	@BeforeEach
	public void beforeEach() throws Exception {
		// Default Timeout: 60s
		SWTBotPreferences.TIMEOUT = 1000 * 60;

		bot = new SWTBot();
		dialogService = MockDialogServiceContextFunction.mockService;
	}
}
