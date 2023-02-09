/** 
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.pt1;

import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.extensions.ColumnDescriptorExtensions;
import org.eclipse.set.utils.table.ColumnDescriptorModelBuilder;

/**
 * Common base for tables in this bundle
 * 
 * @param <C>
 *            Columns type
 */
public abstract class AbstractPlanPro2TableTransformationService<C extends AbstractTableColumns>
		extends PlanPro2TableTransformationService {
	@Override
	protected ColumnDescriptor buildHeading(final Table table) {
		// Add missing heading units
		final ColumnDescriptor root = super.buildHeading(table);
		ColumnDescriptorExtensions.addMissingHeadingUnits(root);
		return root;
	}

	protected C columns;

	@Override
	public ColumnDescriptor fillHeaderDescriptions(
			final ColumnDescriptorModelBuilder builder) {
		return columns.fillHeaderDescriptions(builder);
	}
}
