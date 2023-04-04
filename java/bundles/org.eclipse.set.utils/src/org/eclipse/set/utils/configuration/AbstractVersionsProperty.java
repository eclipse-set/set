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
import java.util.List;

import org.eclipse.set.core.services.configurationservice.TransformJSONService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author truong
 *
 */
public abstract class AbstractVersionsProperty
		extends TransformJSONService<List<String>> {
	/**
	 * Version property
	 */
	public static final String VERSION_PROPERTY = "version"; //$NON-NLS-1$
	private static final String VERSION_REGEX = "^([\\d]*.[\\d]*)$"; //$NON-NLS-1$

	protected JsonNode versionNode = null;

	protected abstract String getNestedPropertyName();

	protected abstract JsonNode getChildNode();

	@Override
	public void loadProperty() throws IOException {
		versionNode = this.getVersionNode();
		if (versionNode == null) {
			return;
		}
		final JsonNode nestedVersionNode = getChildNode();
		if (nestedVersionNode == null) {
			return;
		}
		final List<String> versions = mapper
				.readValue(nestedVersionNode.toString(), new TypeReference<>() {
					// Empty
				});
		versions.removeIf(ele -> !ele.matches(VERSION_REGEX));
		properties.put(VERSION_PROPERTY, versions);
	}

	@Override
	public JsonNode getDefaultValue() {
		final ObjectNode node = mapper.createObjectNode();
		node.set(getNestedPropertyName(), mapper.createArrayNode());
		return node;
	}

	@Override
	public String getPropertyName() {
		return VERSION_PROPERTY;
	}

	@Override
	public List<String> getProperty() {
		return getProperty(VERSION_PROPERTY);
	}

	@Override
	public JsonNode transform() {
		final ArrayNode arrayNode = mapper.createArrayNode();
		this.getProperty().forEach(version -> arrayNode.add(version));
		if (versionNode.isObject()) {
			final ObjectNode objectNode = (ObjectNode) versionNode.deepCopy();
			objectNode.set(getNestedPropertyName(), arrayNode);
			return objectNode;
		}
		return arrayNode;
	}

	protected JsonNode getVersionNode() {
		if (rootNode == null) {
			return null;
		}
		return rootNode.get(VERSION_PROPERTY);
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
			return false;
		}
		final List<String> versions = getProperty(VERSION_PROPERTY);
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
		if (getProperty() == null) {
			properties.put(VERSION_PROPERTY, List.of(versionNumber));
		} else {
			final List<String> versions = getProperty();
			versions.add(versionNumber);
		}

		this.isChanged = true;
	}
}
