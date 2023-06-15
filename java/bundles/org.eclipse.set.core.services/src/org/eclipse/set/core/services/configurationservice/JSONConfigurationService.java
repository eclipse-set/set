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

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Service for handle JSON configuration file
 * 
 * @author truong
 * @param <T>
 *            type of node value
 */
public interface JSONConfigurationService<T> {

	/**
	 * Load JSON configuration file
	 * 
	 * @throws IOException
	 */
	void loadJSON() throws IOException;

	/**
	 * Storage value to json file
	 * 
	 * @throws IOException
	 */
	void storeJSON() throws IOException;

	/**
	 * @return json root node
	 */
	JsonNode getRootNode();

	/**
	 * Read value of given node
	 * 
	 * @param nodeName
	 *            the name of node
	 * @return node value
	 * @throws IOException
	 */
	T readJSONNode(String nodeName) throws IOException;

	/**
	 * Add node to root node
	 * 
	 * @param propertyName
	 *            name of given node
	 * @param node
	 *            {@link JsonNode}
	 */
	void addJSONNode(String propertyName, JsonNode node);
}
