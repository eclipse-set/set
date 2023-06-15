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
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.core.services.configurationservice.JSONConfigurationService;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Implementation for {@link JSONConfigurationService}
 * 
 * @author Truong
 * @param <T>
 *            type of node value
 */
@Component(immediate = true)
public class JSONConfigurationServiceImpl<T>
		implements JSONConfigurationService<T> {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(JSONConfigurationServiceImpl.class);
	private static final String FILENAME = "configuration.json"; //$NON-NLS-1$

	private static File getFile() {
		return Paths.get(ToolboxConstants.TMP_BASE_DIR, FILENAME).toFile();
	}

	protected List<String> propertyList = new LinkedList<>();
	private JsonNode rootNode;
	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public void loadJSON() throws IOException {
		final File configurationFile = getFile();
		// Create configuration file, when not exsits
		if (!configurationFile.exists()) {
			if (!configurationFile.getParentFile().exists()) {
				configurationFile.getParentFile().mkdirs();
			}
			if (!configurationFile.createNewFile()) {
				LOGGER.error("Cannot create File: ", FILENAME); //$NON-NLS-1$
				return;
			}
			// create empty json node
			rootNode = mapper.createObjectNode();
			storeJSON();
		}
		try (final Reader reader = Files
				.newBufferedReader(configurationFile.toPath())) {
			rootNode = mapper.readTree(reader);
		}
	}

	@Override
	public void storeJSON() throws IOException {
		final File configurationFile = getFile();
		if (!configurationFile.exists()) {
			LOGGER.error(
					"Can't found file: " + configurationFile.getAbsolutePath()); //$NON-NLS-1$
			return;
		}
		mapper.writerWithDefaultPrettyPrinter().writeValue(configurationFile,
				rootNode);
	}

	@Override
	public JsonNode getRootNode() {
		return rootNode;
	}

	@Override
	public void addJSONNode(final String propertyName, final JsonNode node) {
		if (rootNode instanceof final ObjectNode objectNode) {
			objectNode.set(propertyName, node);
		}
	}

	@Override
	public T readJSONNode(final String nodeName) throws IOException {
		return mapper.readValue(nodeName, new TypeReference<T>() {
			// Empty
		});
	}

}
