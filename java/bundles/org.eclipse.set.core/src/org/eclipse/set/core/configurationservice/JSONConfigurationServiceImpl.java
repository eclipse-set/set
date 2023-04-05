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
import org.eclipse.set.core.services.configurationservice.TransformJSONService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Implementation for {@link JSONConfigurationService}
 * 
 * @author Truong
 */
@Component(immediate = true)
public class JSONConfigurationServiceImpl implements JSONConfigurationService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(JSONConfigurationServiceImpl.class);
	private static final String FILENAME = "configuration.json"; //$NON-NLS-1$

	private static File getFile() {
		return Paths.get(ToolboxConstants.TMP_BASE_DIR, FILENAME).toFile();
	}

	ObjectMapper mapper = new ObjectMapper();
	private final List<TransformJSONService<?>> transformServices = new LinkedList<>();

	@Override
	public void createJSON() throws IOException {
		final File configuration = getFile();
		if (!configuration.getParentFile().exists()) {
			configuration.getParentFile().mkdirs();
		}
		if (!configuration.createNewFile()) {
			LOGGER.error("Cannot create File: ", FILENAME); //$NON-NLS-1$
			return;
		}
		final ObjectNode root = mapper.createObjectNode();
		for (final TransformJSONService<?> service : transformServices) {
			root.set(service.getPropertyName(), service.getDefaultValue());
		}
		TransformJSONService.setRootNode(root);
		mapper.writerWithDefaultPrettyPrinter().writeValue(configuration, root);
	}

	@Override
	public void loadJSON() throws IOException {
		final File configurationFile = getFile();
		if (!configurationFile.exists()) {
			createJSON();
			return;
		}
		try (final Reader reader = Files
				.newBufferedReader(configurationFile.toPath())) {
			final JsonNode jsonNode = mapper.readTree(reader);
			TransformJSONService.setRootNode(jsonNode);
			for (final TransformJSONService<?> service : transformServices) {
				service.loadProperty();
			}
		}
	}

	/**
	 * Add a json transform service
	 * 
	 * @param service
	 *            the json tranformation service
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addTransformService(final TransformJSONService<?> service) {
		transformServices.add(service);
	}

	/**
	 * Remove a model service
	 * 
	 * @param service
	 *            the json tranformation service
	 */
	public void removeTransformService(final TransformJSONService<?> service) {
		transformServices.remove(service);
	}

	@Override
	public void storeJSON() throws IOException {
		if (!transformServices.isEmpty()
				&& !transformServices.get(0).isChangedConfiguration()) {
			return;
		}
		final File configurationFile = getFile();
		if (!configurationFile.exists()) {
			LOGGER.error(
					"Can't found file: " + configurationFile.getAbsolutePath()); //$NON-NLS-1$
			return;
		}

		final ObjectNode root = mapper.createObjectNode();
		for (final TransformJSONService<?> service : transformServices) {
			root.set(service.getPropertyName(), service.transform());
		}
		mapper.writerWithDefaultPrettyPrinter().writeValue(configurationFile,
				root);
	}

	@Override
	public List<TransformJSONService<?>> getTransformServices(
			final String propertyName) {
		return transformServices.stream().filter(
				service -> service.getPropertyName().equals(propertyName))
				.toList();
	}

}
