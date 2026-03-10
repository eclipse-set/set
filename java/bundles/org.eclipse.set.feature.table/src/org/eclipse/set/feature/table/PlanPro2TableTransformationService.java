/** 
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table;

import java.util.Set;

import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.AbstractTableTransformationService;

/**
 * Common base for tables in this bundle
 */
public abstract class PlanPro2TableTransformationService extends
		AbstractTableTransformationService<MultiContainer_AttributeGroup> {

	/**
	 * @return the table name info
	 */
	public abstract TableNameInfo getTableNameInfo();

	/**
	 * @return position of fixed columns
	 */
	public abstract Set<Integer> getFixedColumnsPos();

	/**
	 * @return whether filtering shall be enabled for the table or not
	 */
	@SuppressWarnings("static-method")
	public boolean enableFiltering() {
		// by default we disable filtering
		return false;
	}
}
