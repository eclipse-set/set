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
package org.eclipse.set.core.fileservice;

import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMLHelperImpl;
import org.eclipse.set.core.services.version.PlanProVersionService;
import org.eclipse.set.model.validationreport.VersionInfo;

/**
 * Make the old minor version compatible
 * 
 * @author truong
 */
public class PlanProXMLHelper extends XMLHelperImpl {

	private static final String PLANPRO_URI_PREFIX = "http://www.plan-pro.org/modell/PlanPro"; //$NON-NLS-1$
	private static final String SIGNALBEGRIFF_RIL_URI_PREFIX = "http://www.plan-pro.org/modell/Signalbegriffe_Ril_301"; //$NON-NLS-1$
	private final PlanProVersionService versionService;

	private static String parseVersion(final String uri) {
		return uri.substring(uri.lastIndexOf("/") + 1); //$NON-NLS-1$
	}

	/**
	 * @param resource
	 *            the {@link XMLResource}
	 * @param versionSerivce
	 *            the {@link PlanProVersionService}
	 */
	public PlanProXMLHelper(final XMLResource resource,
			final PlanProVersionService versionSerivce) {
		super(resource);
		this.versionService = versionSerivce;
	}

	@Override
	public void addPrefix(final String prefix, final String uri) {
		if (!versionService.isSupportedVersion(uri)) {
			super.addPrefix(prefix, uri);
			return;
		}

		// When the version isn't equal current version, but this version is
		// supported then replace the version part in uri to current version
		final VersionInfo currentVersion = versionService.getCurrentVersion();
		String newUri = uri;
		final String loadedVersion = parseVersion(uri);
		if (uri.startsWith(PLANPRO_URI_PREFIX)) {
			newUri = uri.replace(loadedVersion,
					currentVersion.getPlanProVersions().getFirst());
		} else if (uri.startsWith(SIGNALBEGRIFF_RIL_URI_PREFIX)) {
			newUri = uri.replace(loadedVersion,
					currentVersion.getSignalbegriffeVersions().getFirst());
		}
		super.addPrefix(prefix, newUri);
	}
}
