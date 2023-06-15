/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.set.core.services.configurationservice.JSONConfigurationService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author truong
 *
 */
public abstract class AbstractVersionsProperty {
	JSONConfigurationService<List<String>> jsonService;

	/**
	 * Version property
	 */
	public static final String VERSION_PROPERTY = "version"; //$NON-NLS-1$
	private static final String VERSION_REGEX = "^([\\d]*\\.[\\d]*)$"; //$NON-NLS-1$

	protected Map<String, List<String>> versionprop = new HashMap<>();

	protected JsonNode versionNode = null;

	protected abstract String getNestedPropertyName();

	protected abstract JsonNode getChildNode();

	/**
	 * @param jsonService
	 *            {@link JSONConfigurationService}
	 */
	public void setJSONService(
			final JSONConfigurationService<List<String>> jsonService) {
		this.jsonService = jsonService;
	}

	/**
	 * @throws IOException
	 *             {@link IOException}
	 */
	public void loadProperty() throws IOException {
		versionNode = getVersionNode();
		if (versionNode == null) {
			versionNode = new ObjectMapper().createObjectNode();
		}
		final JsonNode nestedVersionNode = getChildNode();
		if (nestedVersionNode == null) {
			return;
		}
		final List<String> versions = jsonService
				.readJSONNode(nestedVersionNode.toString());
		versions.removeIf(ele -> !ele.matches(VERSION_REGEX));
		versionprop.put(VERSION_PROPERTY, versions);
	}

	/**
	 * @return list of opened version
	 */
	public List<String> getProperty() {
		return versionprop.get(VERSION_PROPERTY);
	}

	protected JsonNode getVersionNode() {
		if (jsonService == null || jsonService.getRootNode() == null) {
			return null;
		}
		return jsonService.getRootNode().get(VERSION_PROPERTY);
	}

	/**
	 * Check, if this current toolbox version have before opened
	 * 
	 * @param versionNumber
	 *            current version number
	 * @return true, if this toolbox version already once opened
	 */
	public boolean isContainVersion(final String versionNumber) {
		if (!versionNumber.matches(VERSION_REGEX)) {
			return true;
		}
		final List<String> versions = getProperty();
		if (versions == null || versions.isEmpty()) {
			storeNewVersion(versionNumber);
			return false;
		}
		final boolean isContains = versions.contains(versionNumber);
		if (!isContains) {
			storeNewVersion(versionNumber);
			return false;
		}
		return true;
	}

	private void storeNewVersion(final String versionNumber) {
		final List<String> versions = new LinkedList<>();
		if (getProperty() == null) {
			versionprop.put(VERSION_PROPERTY, List.of(versionNumber));
		} else {
			versions.addAll(getProperty());
		}
		versions.add(versionNumber);
		final ArrayNode arrayNode = new ObjectMapper().createArrayNode();
		versions.forEach(arrayNode::add);
		if (versionNode.isObject()) {
			final ObjectNode objectNode = (ObjectNode) versionNode.deepCopy();
			objectNode.set(getNestedPropertyName(), arrayNode);
			jsonService.addJSONNode(VERSION_PROPERTY, objectNode);
		}
	}
}
