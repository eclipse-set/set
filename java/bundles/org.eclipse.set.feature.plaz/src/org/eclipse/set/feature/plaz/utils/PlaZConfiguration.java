/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.plaz.utils;

/**
 * Interpretation of toolbox properties.
 */
public class PlaZConfiguration {
	/**
	 * This interface provides constants used in configuration/config.ini.
	 */
	public interface PlaZProperties {
		/**
		 * Property key for the max object count in pdf
		 */
		public static final String MAX_OBJECT_COUNT = "toolbox.plaz.lst.max.object.count.pdf";
	}

	/**
	 * Default value of {@link PlaZProperties#MAX_OBJECT_COUNT}
	 */
	public static final String DEFAULT_MAX_OBJECT_COUNT = "50";

	/**
	 * @return value of {@link PlaZProperties#MAX_OBJECT_COUNT}
	 */
	public static String getMaxObjectCount() {
		return System.getProperty(PlaZProperties.MAX_OBJECT_COUNT,
				DEFAULT_MAX_OBJECT_COUNT);
	}
}
