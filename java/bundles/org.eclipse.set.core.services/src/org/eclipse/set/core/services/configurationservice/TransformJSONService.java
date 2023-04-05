/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.services.configurationservice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Transformation property value to json
 * 
 * @author Truong
 * @param <T>
 *            Type of property
 *
 */
public abstract class TransformJSONService<T> {
	protected ObjectMapper mapper = new ObjectMapper();
	protected Map<String, T> properties = new HashMap<>();
	protected static JsonNode rootNode = null;

	protected boolean isChanged = false;

	/**
	 * Load property from root node
	 * 
	 * @throws IOException
	 *             {@link IOException}
	 */
	public abstract void loadProperty() throws IOException;

	/**
	 * Get default value of a property
	 * 
	 * @return default value
	 */
	public abstract JsonNode getDefaultValue();

	/**
	 * Get property value
	 * 
	 * @return property value
	 */
	public abstract T getProperty();

	/**
	 * Get property name
	 * 
	 * @return property name
	 */
	public abstract String getPropertyName();

	/**
	 * Transform java object to json node
	 * 
	 * @return json node of property
	 * 
	 */
	public abstract JsonNode transform();

	/**
	 * Get property value
	 * 
	 * @param propertyName
	 *            name of property
	 * @return property value
	 */
	public T getProperty(final String propertyName) {
		return properties.get(propertyName);
	}

	/**
	 * Get root node of configuration file
	 * 
	 * @return root of json file
	 */
	public static JsonNode getRootNode() {
		return rootNode;
	}

	/**
	 * Set root node
	 * 
	 * @param node
	 *            json node
	 */
	public static void setRootNode(final JsonNode node) {
		rootNode = node;
	}

	/**
	 * @return true, if exist change in properties
	 */
	public boolean isChangedConfiguration() {
		return isChanged;
	}
}
