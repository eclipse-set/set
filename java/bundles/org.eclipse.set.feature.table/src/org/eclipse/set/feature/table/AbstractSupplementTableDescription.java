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
package org.eclipse.set.feature.table;

import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;
import org.eclipse.set.utils.viewgroups.SetViewGroups;

/**
 * Part description for supplement tables
 * 
 * @author truong
 */
public abstract class AbstractSupplementTableDescription
		extends AbstractTableDescription {

	@Override
	public ToolboxViewGroup getToolboxViewGroup() {
		return SetViewGroups.getTable_Supplement();
	}

	@Override
	protected String getTableIdPrefix() {
		return ToolboxConstants.SUPPLEMENT_TABLE_PART_ID_PREFIX;
	}
}
