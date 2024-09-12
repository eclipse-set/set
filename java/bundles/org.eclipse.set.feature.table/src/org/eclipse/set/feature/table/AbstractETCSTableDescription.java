/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table;

import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;
import org.eclipse.set.utils.viewgroups.SetViewGroups;

/**
 * 
 */
public abstract class AbstractETCSTableDescription
		extends AbstractTableDescription {

	@Override
	protected String getTableCategory() {
		return ToolboxConstants.ETCS_CATEGORY;
	}

	@Override
	public ToolboxViewGroup getToolboxViewGroup() {
		return SetViewGroups.getTable_ETCS();
	}

}
