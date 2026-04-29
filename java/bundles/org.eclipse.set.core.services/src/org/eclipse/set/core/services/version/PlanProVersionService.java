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
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

		private static final String VERSION_FORMAT = "(?<major>[1-9]+\\.\\d+)\\.(?<patch>\\d+)(\\.(?<minor>\\d))*"; //$NON-NLS-1$

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

		/**
		 * @param another
		 *            the another version format
		 * @return true, if same
		 */
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

		/**
		 * @return the planpro version comparator
		 */
		public static Comparator<String> compareVersion() {
			return (first, second) -> {
				final PlanProVersionFormat firstVersion = parseVersionFormat(
						first);
				final PlanProVersionFormat secondVersion = parseVersionFormat(
						second);
				return firstVersion.compare(secondVersion);
			};
		}

		/**
		 * Parse planpro version from string
		 * 
		 * @param version
		 *            the version
		 * @return the {@link PlanProVersionFormat}
		 */
		public static PlanProVersionFormat parseVersionFormat(
				final String version) {
			final Pattern compile = Pattern.compile(VERSION_FORMAT);
			final Matcher matcher = compile.matcher(version);
			if (!matcher.matches()) {
				throw new IllegalArgumentException("Illegal Version Foramt"); //$NON-NLS-1$
			}

			return new PlanProVersionFormat(matcher.group("major"), //$NON-NLS-1$
					matcher.group("patch"), matcher.group("minor")); //$NON-NLS-1$ //$NON-NLS-2$

		}
	}

	/**
	 * @return the supported PlanPro version
	 */
	public VersionInfo getSupportedVersions();

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
	 * @return the version info with actual PlanPro and Signalbegriff_Ril301
	 *         version
	 */
	public VersionInfo getCurrentVersion();

	/**
	 * @param uri
	 *            the used version in loaded file
	 * @return true, if this version was supported
	 */
	public boolean isSupportedVersion(String uri);
}
