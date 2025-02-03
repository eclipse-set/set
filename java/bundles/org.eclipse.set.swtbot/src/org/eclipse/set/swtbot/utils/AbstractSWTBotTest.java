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
import java.util.Optional;

import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.utils.SWTBotPreferences;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base test for all swt bot tests
 *
 */
public abstract class AbstractSWTBotTest {
	public static SWTBot bot;
	protected static final String DEFAULT_TOOLBOX_NAME = "Eclipse Signalling Engineering Toolbox";
	protected static MockDialogService dialogService;
	protected static final String TEST_FILE_DIR = "test_res/test_file/";

	@BeforeEach
	public void beforeEach() throws Exception {
		// Default Timeout: 60s
		SWTBotPreferences.TIMEOUT = 1000 * 60;

		bot = new SWTBot();
		dialogService = getDialogService();
		dialogService.openFileDialogHandler = filters -> Optional
				.of(getFilePath(getTestFile().getFullName()));
	}

	/**
	 * Call execute test class to get test resource location
	 * 
	 * @return execute test class
	 */
	public Class<? extends AbstractSWTBotTest> getTestResourceClass() {
		// Class for get the default test resource
		if (getTestFile().equals(SWTBotTestFile.PPHN)) {
			return AbstractSWTBotTest.class;
		}
		return getClass();
	}

	public abstract TestFile getTestFile();

	protected MockDialogService getDialogService() {
		return MockDialogServiceContextFunction.mockService;
	}

	/**
	 * Get path of the reference file
	 * 
	 * @param fileName
	 *            reference file name
	 * @return the path of reference file
	 */
	protected Path getFilePath(final String fileName) {
		final File testFile = getTestFileLocation(fileName);
		if (testFile.exists()) {
			return testFile.toPath();
		}

		try (final InputStream resourceStream = getTestResourceClass().getClassLoader()
				.getResourceAsStream(TEST_FILE_DIR + fileName);
				final FileOutputStream outputStream = new FileOutputStream(
						testFile);) {
			outputStream.write(resourceStream.readAllBytes());
		} catch (final IOException e) {
			throw new RuntimeException(
					String.format("Cannot find file: %s", fileName));
		}
		return testFile.toPath();
	}

	protected File getTestFileLocation(final String fileName) {
		final URL projectLocation = getTestResourceClass().getProtectionDomain()
				.getCodeSource().getLocation();
		return new File(projectLocation.getPath() + fileName);
	}
}
