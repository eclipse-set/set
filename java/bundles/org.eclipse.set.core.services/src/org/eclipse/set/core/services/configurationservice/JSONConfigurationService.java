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
import java.util.List;

/**
 * Service for handle JSON configuration file
 * 
 * @author truong
 */
public interface JSONConfigurationService {
	/**
	 * Create JSON configuration file
	 * 
	 * @throws IOException
	 */
	void createJSON() throws IOException;

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
	 * Get transform services of property
	 * 
	 * @param propertyName
	 * @return list of transform service for this property
	 */
	List<TransformJSONService<?>> getTransformServices(
			final String propertyName);
}
