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

/**
 * Make the old minor version compatible
 * 
 * @author truong
 */
public class PlanProXMLHelper extends XMLHelperImpl {

	private static final String PLANPRO_URI_PREFIX = "http://www.plan-pro.org/modell/"; //$NON-NLS-1$
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
		if (!uri.startsWith(PLANPRO_URI_PREFIX)
				|| parseVersion(uri).equals(versionService.getCurrentVersion())
				|| !parseVersion(uri)
						.startsWith(versionService.getSupportedVersionFormat()
								.getMajorPatchVersion())) {
			super.addPrefix(prefix, uri);
			return;
		}

		// When the version isn't equal current version, but this version is
		// supported then replace the version part in uri to current version
		final String version = parseVersion(uri);
		final String newUri = uri.replace(version,
				versionService.getCurrentVersion());
		super.addPrefix(prefix, newUri);

	}
}
