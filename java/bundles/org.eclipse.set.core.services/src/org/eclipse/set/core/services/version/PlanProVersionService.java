/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.version;

import java.nio.file.Path;

import org.eclipse.set.model.validationreport.VersionInfo;

/**
 * Creation of PlanPro version information.
 * 
 * @author Schaefer
 */
public interface PlanProVersionService {

	/**
	 * @return the supported PlanPro version
	 */
	public VersionInfo createSupportedVersion();

	/**
	 * @param location
	 *            location to the PlanPro model
	 * 
	 * @return the used version
	 */
	public VersionInfo createUsedVersion(Path location);
}
