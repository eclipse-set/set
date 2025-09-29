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
	 * The PlanPro Version format <major>.<patch>.<minor>
	 * 
	 * @param major
	 *            the major version
	 * @param patch
	 *            the patch version
	 * @param minor
	 *            the minor version
	 */
	public record PlanProVersionFormat(String major, String patch,
			String minor) {
		/**
		 * @return <major>.<patch>
		 */
		public String getMajorPatchVersion() {
			return major + "." + patch; //$NON-NLS-1$
		}

		/**
		 * @return <major>.<patch>.<version>
		 */
		public String getFullVersion() {
			return String.format("%s.%s.%s", major, patch, minor); //$NON-NLS-1$
		}

		public int compare(final PlanProVersionFormat another) {
			if (!major.equals(another.major)) {
				return major.compareToIgnoreCase(another.major);
			}

			if (!patch.equals(another.patch)) {
				return patch.compareToIgnoreCase(another.patch);
			}

			if (!minor.equals(another.minor)) {
				return minor.compareToIgnoreCase(another.minor);
			}
			return 0;

		}
	}

	/**
	 * @return the supported PlanPro version
	 */
	public VersionInfo createSupportedVersion();

	/**
	 * @return the supported PlanPro version format
	 */
	public PlanProVersionFormat getSupportedVersionFormat();

	/**
	 * @param location
	 *            location to the PlanPro model
	 * 
	 * @return the used version
	 */
	public VersionInfo createUsedVersion(Path location);

	/**
	 * @return the current PlanPro version
	 */
	public String getCurrentVersion();
}
