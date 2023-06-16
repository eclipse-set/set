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
import java.util.Set;

/**
 * Service for handle user configurations
 */
public interface UserConfigurationService {
	/**
	 * @return a set of versions known to the user
	 * @throws IOException
	 */
	Set<String> getKnownVersions() throws IOException;

	/**
	 * @param version
	 *            a new version known to the user
	 * @throws IOException
	 */
	void addKnownVersions(String version) throws IOException;

}
