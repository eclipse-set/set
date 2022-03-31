/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.services.version;

import java.util.List;

import org.eclipse.set.basis.ui.VersionInfo;

/**
 * Interface for additional version information.
 * 
 * @author Schaefer
 *
 */
public interface AdditionalVersionService {

	/**
	 * @param versionInfo
	 *            the list for adding additional version info
	 */
	public void addInfo(final List<VersionInfo> versionInfo);
}
