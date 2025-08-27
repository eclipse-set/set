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
package org.eclipse.set.application.contribution;

import org.eclipse.set.basis.files.ToolboxFileRole;

/**
 * 
 */
public class OpenCompareProjectMenuCnntribution
		extends OpenRecentMenuContribution {
	private static final String OPEN_COMPARE_CONTRIBUTION_CLASS = "bundleclass://org.eclipse.set.application/org.eclipse.set.application.handler.OpenCompareProjectHandler";//$NON-NLS-1$

	@Override
	protected String getItemContributionClass() {
		return OPEN_COMPARE_CONTRIBUTION_CLASS;
	}

	@Override
	protected ToolboxFileRole getRole() {
		return ToolboxFileRole.COMPARE_PLANNING;
	}
}
