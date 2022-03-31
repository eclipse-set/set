/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table;

import org.eclipse.set.feature.table.messages.MessagesWrapper;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.AbstractTableTransformationService;

/**
 * Common base for tables in this bundle
 * 
 * @author Stuecker
 */
public abstract class AbstractPlanPro2TableTransformationService extends
		AbstractTableTransformationService<MultiContainer_AttributeGroup> {
	protected MessagesWrapper messagesWrapper;

	@Override
	protected ColumnDescriptor buildHeading(final Table table) {
		// Add missing heading units
		final ColumnDescriptor root = super.buildHeading(table);
		ColumnDescriptorExtensions.addMissingHeadingUnits(root);
		return root;
	}

	/**
	 * @return the table name info
	 */
	public abstract TableNameInfo getTableNameInfo();
}
