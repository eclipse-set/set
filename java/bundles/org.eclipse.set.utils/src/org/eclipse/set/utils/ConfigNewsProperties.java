/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.eclipse.set.basis.ToolboxProperties;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration properties for toolbox.
 * 
 * @author Schaefer
 */
public class ConfigNewsProperties extends Properties {

	private static final String FILENAME = "showNewsConfig.properties"; //$NON-NLS-1$
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfigNewsProperties.class);

	private static String getFilePath() {
		return Paths.get(ToolboxConstants.TMP_BASE_DIR, FILENAME).toString();
	}

	/**
	 * Read the properties.
	 */
	public ConfigNewsProperties() {
		if (new File(getFilePath()).exists()) {
			load();
		} else {
			create();
		}
	}

	/**
	 * Create the properties
	 */
	public void create() {
		final File config = new File(getFilePath());
		try {
			if (!config.getParentFile().exists()) {
				config.getParentFile().mkdirs();
			}
			if (!config.createNewFile()) {
				LOGGER.error("Cannot create File: ", FILENAME); //$NON-NLS-1$
				return;
			}
			try (FileInputStream input = new FileInputStream(getFilePath())) {
				this.load(input);
			}
			this.setProperty(ToolboxProperties.VERSION,
					ToolboxConfiguration.getToolboxVersion().getShortVersion());
			store();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Store the properties.
	 */
	public void store() {
		try (final FileOutputStream output = new FileOutputStream(
				getFilePath())) {
			store(output, null);

		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Load the properties
	 */
	public void load() {
		try (final FileInputStream input = new FileInputStream(getFilePath())) {
			load(input);
		} catch (final IOException e) {
			final Path workingDir = Paths.get(""); //$NON-NLS-1$
			LOGGER.error("Current Relative Path"); //$NON-NLS-1$
			LOGGER.error(workingDir.toAbsolutePath().toString());
			LOGGER.error("config.properties exists: " //$NON-NLS-1$
					+ Files.exists(Paths.get(FILENAME)));
			throw new RuntimeException(e);
		}
	}

}
