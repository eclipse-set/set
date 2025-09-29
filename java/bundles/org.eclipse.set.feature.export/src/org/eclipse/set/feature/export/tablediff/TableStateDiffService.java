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
package org.eclipse.set.feature.export.tablediff;

import java.util.List;

import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.services.table.TableDiffService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * 
 */
@Component(immediate = true, service = TableDiffService.class, property = "compareType=state")
public class TableStateDiffService extends AbstractTableDiff {

	@Reference
	SessionService sessionService;

	@Override
	public Table createDiffTable(final Table oldTable, final Table newTable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table createCompareTable(final Table firstPlanTable,
			final Table secondPlanDiffTable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	void addMissingRowGroup(final RowGroup group, final Table table) {
		final String groupGuid = EObjectExtensions
				.getNullableObject(group,
						g -> g.getLeadingObject().getIdentitaet().getWert())
				.orElse(null);
		final int groupId = group.getLeadingObjectIndex();
		final RowGroup match = TableExtensions.getGroupById(table, groupGuid,
				groupId);
		// we add a new matching group to the table
		if (match == null) {
			final RowGroup newRowGroup = TablemodelFactory.eINSTANCE
					.createRowGroup();
			newRowGroup.setLeadingObject(group.getLeadingObject());
			newRowGroup.setLeadingObjectIndex(group.getLeadingObjectIndex());
			for (final TableRow element : group.getRows()) {
				final TableRow newRow = TablemodelFactory.eINSTANCE
						.createTableRow();
				newRowGroup.getRows().add(newRow);
				final List<ColumnDescriptor> columns = TableExtensions
						.getColumns(table);
				columns.forEach(column -> addEmptyValue(newRow, column));
			}

			if (table.getTablecontent() == null) {
				table.setTablecontent(
						TablemodelFactory.eINSTANCE.createTableContent());
			}
			table.getTablecontent().getRowgroups().add(newRowGroup);
		}
	}

	@Override
	SessionService getSessionService() {
		return sessionService;
	}

}
