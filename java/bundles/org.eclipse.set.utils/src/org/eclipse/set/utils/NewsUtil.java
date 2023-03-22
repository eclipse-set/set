/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVFormat.Builder;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

/**
 * Configuration properties for toolbox.
 * 
 * @author Schaefer
 */
public class NewsUtil {

	private static final String FILENAME = "showNewsConfig.csv"; //$NON-NLS-1$
	private static final String CSV_HEADER_VERSION = "Version"; //$NON-NLS-1$
	private static final Logger LOGGER = LoggerFactory
			.getLogger(NewsUtil.class);
	private final List<CSVRecord> csvRecodes = new ArrayList<>();
	private boolean isFileExist = true;
	private final Builder csvBuilder;

	private static Path getFilePath() {
		return Paths.get(ToolboxConstants.TMP_BASE_DIR, FILENAME);
	}

	/**
	 * check, if release log should show or not
	 * 
	 * @return true, if release log should show
	 */
	public static boolean isShowNews() {
		final String currentVersion = ToolboxConfiguration.getToolboxVersion()
				.getShortVersion();
		final String[] splitVersion = currentVersion.split("\\."); //$NON-NLS-1$
		LOGGER.debug(String.format("Current Version: %s", currentVersion));
		// Not show news by development version
		if (splitVersion.length > 3) {
			return false;
		}

		final NewsUtil newsUtil = new NewsUtil();
		final String releaseVersion = String.format("%s.%s", splitVersion[0], //$NON-NLS-1$
				splitVersion[1]);
		LOGGER.debug(String.format("Release Version: %s", releaseVersion));
		if (newsUtil.isContainVersion(releaseVersion)) {
			newsUtil.store(currentVersion);
			return true;
		}

		return false;
	}

	/**
	 * Read the properties.
	 */
	public NewsUtil() {
		csvBuilder = CSVFormat.Builder.create(CSVFormat.DEFAULT);
		csvBuilder.setHeader(CSV_HEADER_VERSION);
		if (getFilePath().toFile().exists()) {
			isFileExist = true;
			load();
		} else {
			isFileExist = false;
			create();
		}
	}

	/**
	 * Create the properties
	 */
	public void create() {
		final File config = getFilePath().toFile();
		try {
			if (!config.getParentFile().exists()) {
				config.getParentFile().mkdirs();
			}
			if (!config.createNewFile()) {
				LOGGER.error("Cannot create File: ", FILENAME); //$NON-NLS-1$
				return;
			}
			store(null);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Load the properties
	 */
	public void load() {
		try (final Reader reader = Files.newBufferedReader(getFilePath());
				final CSVParser csvParser = new CSVParser(reader,
						csvBuilder.build())) {
			csvRecodes.addAll(csvParser.getRecords());
		} catch (final IOException e) {
			final Path workingDir = Paths.get(""); //$NON-NLS-1$
			LOGGER.error("Current Relative Path"); //$NON-NLS-1$
			LOGGER.error(workingDir.toAbsolutePath().toString());
			LOGGER.error("config.properties exists: " //$NON-NLS-1$
					+ Files.exists(Paths.get(FILENAME)));
			throw new RuntimeException(e);
		}
	}

	/**
	 * Store the properties.
	 * 
	 * @param newVersionNumber
	 *            new version
	 */
	public void store(final String newVersionNumber) {
		try (BufferedWriter writer = Files.newBufferedWriter(getFilePath());
				final CSVPrinter csvPrinter = new CSVPrinter(writer,
						csvBuilder.build())) {
			final List<String> storeVersions = getStoreVersions();
			if (Strings.isNullOrEmpty(newVersionNumber)) {
				storeVersions.add(newVersionNumber);
			}
			storeVersions.forEach(version -> {
				try {
					csvPrinter.printRecord(version);
				} catch (final IOException ex) {
					LOGGER.error(String.format("Cant store Versionnumber: %s", //$NON-NLS-1$
							version));
					throw new RuntimeException(ex);
				}
			});
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Check, if version number already storage
	 * 
	 * @param versionNumber
	 *            version number
	 * @return true, if version number already storage
	 */
	public boolean isContainVersion(final String versionNumber) {
		final List<String> storeVersions = getStoreVersions();
		if (storeVersions.isEmpty()) {
			return false;
		}
		for (final String storeVersion : storeVersions) {
			if (storeVersion.equals(versionNumber)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return true, if property already create
	 */
	public boolean isPropertyExist() {
		return isFileExist;
	}

	private List<String> getStoreVersions() {
		return csvRecodes.stream().map(record -> record.get(CSV_HEADER_VERSION))
				.toList();
	}

}
