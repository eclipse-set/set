/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.plazmodel.test;

import org.eclipse.set.osgi.test.utils.AbstractOsgiValidationTableTest;
import org.osgi.framework.BundleContext;

/**
 * 
 */
public abstract class AbstractPlaZModelTest
		extends AbstractOsgiValidationTableTest {
	private static final String REF_DIR = "resource/";

	protected abstract BundleContext getBundleContext();

	@Override
	protected String getReferenceDir() {
		return REF_DIR + "table_reference/" + getTestFile().getShortName()
				+ "/";
	}

	protected void givenReferenceData() {
		referenceData = loadReferenceFile(getTestTableReferenceName());
	}
}
