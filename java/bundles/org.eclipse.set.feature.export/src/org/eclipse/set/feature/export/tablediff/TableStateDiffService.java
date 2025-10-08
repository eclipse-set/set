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

import static org.eclipse.set.model.tablemodel.extensions.TableCellExtensions.getIterableStringValue;

import java.util.Collections;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.CompareCellContent;
import org.eclipse.set.model.tablemodel.MultiColorCellContent;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.services.table.TableDiffService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * 
 */
@Component(immediate = true, service = TableDiffService.class)
public class TableStateDiffService extends AbstractTableDiff {

	@Reference
	SessionService sessionService;

	// @SuppressWarnings("unused")
	// @Override
	// protected void addMissingRowGroup(final RowGroup group, final Table
	// table) {
	// final String groupGuid = EObjectExtensions
	// .getNullableObject(group,
	// g -> g.getLeadingObject().getIdentitaet().getWert())
	// .orElse(null);
	// final int groupId = group.getLeadingObjectIndex();
	// final RowGroup match = TableExtensions.getGroupById(table, groupGuid,
	// groupId);
	// // we add a new matching group to the table
	// if (match == null) {
	// final RowGroup newRowGroup = TablemodelFactory.eINSTANCE
	// .createRowGroup();
	// newRowGroup.setLeadingObject(group.getLeadingObject());
	// newRowGroup.setLeadingObjectIndex(group.getLeadingObjectIndex());
	// for (final TableRow element : group.getRows()) {
	// final TableRow newRow = TablemodelFactory.eINSTANCE
	// .createTableRow();
	// newRowGroup.getRows().add(newRow);
	// final List<ColumnDescriptor> columns = TableExtensions
	// .getColumns(table);
	// columns.forEach(column -> addEmptyValue(newRow, column));
	// }
	//
	// if (table.getTablecontent() == null) {
	// table.setTablecontent(
	// TablemodelFactory.eINSTANCE.createTableContent());
	// }
	// table.getTablecontent().getRowgroups().add(newRowGroup);
	// }
	// }

	@Override
	SessionService getSessionService() {
		return sessionService;
	}

	@Override
	CellContent createDiffContent(final TableCell oldCell,
			final TableCell newCell) {
		if (oldCell.getContent() instanceof MultiColorCellContent) {
			createMultiColorDiffCotent(oldCell, newCell);
			return oldCell.getContent();
		}

		final Set<String> oldValues = getIterableStringValue(oldCell);
		final Set<String> newValues = newCell == null ? Collections.emptySet()
				: getIterableStringValue(newCell);
		if (oldValues.equals(newValues)) {
			return null;
		}
		final CompareCellContent compareContent = TablemodelFactory.eINSTANCE
				.createCompareCellContent();
		compareContent.getOldValue().addAll(oldValues);
		compareContent.getNewValue().addAll(newValues);
		compareContent.setSeparator(EObjectExtensions
				.getNullableObject(oldCell, c -> c.getContent().getSeparator())
				.orElse(null));

		return compareContent;
	}

	// IMPROVE: currently missing the compare between two MultiColorCellContent.
	// This function do only color in cell active
	private static void createMultiColorDiffCotent(final TableCell oldCell,
			final TableCell newCell) {
		if (oldCell
				.getContent() instanceof final MultiColorCellContent oldMultiColorCellContent) {
			if (newCell != null && newCell
					.getContent() instanceof final MultiColorCellContent newCellContent) {
				final MultiColorCellContent clone = EcoreUtil
						.copy(newCellContent);

				oldCell.setContent(clone);
			}
			oldMultiColorCellContent.getValue()
					.forEach(e -> e.setDisableMultiColor(false));
		}
	}

	@Override
	public TableCompareType getCompareType() {
		return TableCompareType.STATE;
	}
}
