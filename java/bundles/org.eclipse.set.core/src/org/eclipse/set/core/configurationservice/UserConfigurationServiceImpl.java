/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.configurationservice;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.core.services.Services;
import org.eclipse.set.core.services.configurationservice.UserConfigurationService;
import org.eclipse.set.utils.ToolboxConfiguration;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

/**
 * Implementation for {@link UserConfigurationService}
 */
@Component
public class UserConfigurationServiceImpl implements UserConfigurationService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserConfigurationServiceImpl.class);
	private static final String FILENAME = "configuration.json"; //$NON-NLS-1$

	protected static File getFile() {
		return Paths.get(ToolboxConstants.TMP_BASE_DIR, FILENAME)
				.toAbsolutePath().toFile();
	}

	// IMPROVE: This should be a record, however due to
	// https://github.com/FasterXML/jackson-databind/issues/3439
	// we cannot apply JsonAnyGetter to records yet
	protected static class UserConfiguration {
		public Set<String> versions = new HashSet<>();

		public Path lastFileOpenPath;

		public Path lastFileExportPath;

		/**
		 * Any unknown properties must be stored and preserverd, as new versions
		 * may add new properties, which should not be removed
		 */
		@JsonAnyGetter
		@JsonAnySetter
		private final Map<String, Object> extraValues = new LinkedHashMap<>();
	}

	protected final ObjectMapper mapper = new ObjectMapper();
	protected UserConfiguration configuration;

	protected UserConfiguration getConfiguration() {
		return configuration;
	}

	protected void setConfiguration(final UserConfiguration configuration) {
		this.configuration = configuration;
	}

	/**
	 * Constructor
	 */
	public UserConfigurationServiceImpl() {
		loadConfiguration();
		Services.setUserConfigurationService(this);
	}

	protected void loadConfiguration() {
		// Do not reload the configuration
		if (configuration != null) {
			return;
		}

		final File configurationFile = getFile();
		// Create configuration if none exists
		if (!configurationFile.exists()) {
			configuration = new UserConfiguration();
			return;
		}
		try (final Reader reader = Files
				.newBufferedReader(configurationFile.toPath())) {
			final ObjectReader objectReader = mapper
					.readerFor(new TypeReference<UserConfiguration>() {
						/* */});
			configuration = (UserConfiguration) objectReader
					.readValue(configurationFile);
		} catch (final IOException e) {
			// If the configuration isn't valid, create a new one
			configuration = new UserConfiguration();
		}
	}

	protected void saveConfiguration() {
		final File configurationFile = getFile();
		// Create configuration file, when not exsits
		if (!configurationFile.exists()) {
			if (!configurationFile.getParentFile().exists()) {
				configurationFile.getParentFile().mkdirs();
			}

			try {
				if (!configurationFile.createNewFile()) {
					LOGGER.error("Cannot write to file {}", FILENAME); //$NON-NLS-1$
					return;
				}
			} catch (final IOException e) {
				LOGGER.error("Cannot write to file {}", FILENAME); //$NON-NLS-1$
				return;
			}
		}

		try {
			mapper.writerWithDefaultPrettyPrinter()
					.writeValue(configurationFile, configuration);
		} catch (final IOException e) {
			LOGGER.error("Cannot save user configuration"); //$NON-NLS-1$
		}
	}

	@Override
	public Set<String> getKnownVersions() throws IOException {
		if (configuration.versions == null) {
			configuration.versions = new HashSet<>();
		}
		return configuration.versions;
	}

	@Override
	public void addKnownVersions(final String version) throws IOException {
		configuration.versions.add(version);
		saveConfiguration();
	}

	@Override
	public void setLastFileOpenPath(final Path path) {
		configuration.lastFileOpenPath = path;
		saveConfiguration();
	}

	@Override
	public void setLastExportPath(final Path path) {
		configuration.lastFileExportPath = path;
		saveConfiguration();
	}

	@Override
	public Optional<Path> getLastFileOpenPath() {
		return Optional.ofNullable(configuration.lastFileOpenPath);
	}

	@Override
	public Path getLastExportPath() {
		if (configuration.lastFileExportPath == null) {
			return ToolboxConfiguration.getDefaultPath();
		}
		return configuration.lastFileExportPath;
	}

}
