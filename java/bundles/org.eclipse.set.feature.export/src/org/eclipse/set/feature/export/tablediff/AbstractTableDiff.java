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

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.services.table.TableDiffService;

/**
 * 
 */
public abstract class AbstractTableDiff implements TableDiffService {

	@Override
	public Table createDiffTable(final Table oldTable, final Table newTable) {
		// expand old table by new lines
		final Table expanded = expandNewRowGroups(oldTable, newTable);
		TableExtensions.getTableRows(expanded)
				.forEach(row -> compareRow(row, newTable));
		return expanded;
	}

	/**
	 * Merges two tables by combining their rows and ensuring that all unique
	 * rows from both tables are present in the result.
	 * 
	 * @param oldTable
	 *            the first
	 * @param newTable
	 *            the second
	 * @return the table containing rows of both table
	 */
	protected Table expandNewRowGroups(final Table oldTable,
			final Table newTable) {
		final Table mergedTable = EcoreUtil.copy(oldTable);
		final List<RowGroup> newRowGroups = newTable.getTablecontent()
				.getRowgroups();
		newRowGroups.forEach(group -> addMissingRowGroup(group, mergedTable));
		return mergedTable;
	}

	protected void addMissingRowGroup(final RowGroup newTableRowGroup,
			final Table mergedTable) {
		final String groupGuid = EObjectExtensions
				.getNullableObject(newTableRowGroup,
						g -> g.getLeadingObject().getIdentitaet().getWert())
				.orElse(null);
		final int groupId = newTableRowGroup.getLeadingObjectIndex();
		final RowGroup match = TableExtensions.getGroupById(mergedTable,
				groupGuid, groupId);
		// we add a new matching group to the table
		if (match == null) {
			final RowGroup newRowGroup = TablemodelFactory.eINSTANCE
					.createRowGroup();
			newRowGroup.setLeadingObject(newTableRowGroup.getLeadingObject());
			newRowGroup.setLeadingObjectIndex(
					newTableRowGroup.getLeadingObjectIndex());
			for (final TableRow element : newTableRowGroup.getRows()) {
				newRowGroup.getRows()
						.add(createMissingRow(
								TableExtensions.getColumns(mergedTable)));
			}

			if (mergedTable.getTablecontent() == null) {
				mergedTable.setTablecontent(
						TablemodelFactory.eINSTANCE.createTableContent());
			}
			mergedTable.getTablecontent().getRowgroups().add(newRowGroup);
		}
	}

	protected TableRow createMissingRow(final List<ColumnDescriptor> columns) {
		final TableRow missingRow = TablemodelFactory.eINSTANCE
				.createTableRow();
		columns.forEach(col -> addEmptyValue(missingRow, col));
		return missingRow;
	}

	protected void compareRow(final TableRow targetRow, final Table table) {
		final TableRow matchingRow = TableExtensions.getMatchingRow(table,
				targetRow);
		for (int i = 0; i < targetRow.getCells().size(); i++) {
			compareCell(i, targetRow, matchingRow);
		}
	}

	protected void compareCell(final int cellIndex, final TableRow first,
			final TableRow second) {
		final TableCell oldCell = first.getCells().get(cellIndex);
		TableCell newCell = TablemodelFactory.eINSTANCE.createTableCell();
		newCell.setContent(
				TablemodelFactory.eINSTANCE.createStringCellContent());
		if (second != null) {
			newCell = second.getCells().get(cellIndex);
		}

		final CellContent diffContent = createDiffContent(oldCell, newCell);
		if (diffContent == null) {
			return;
		}
		oldCell.setContent(diffContent);
	}

	abstract CellContent createDiffContent(TableCell first, TableCell second);

	protected static List<Bearbeitungsvermerk> getFootnotes(
			final TableRow row) {
		if (row == null || row.getFootnotes() == null
				|| ((SimpleFootnoteContainer) row.getFootnotes())
						.getFootnotes() == null) {
			return List.of();
		}
		return ((SimpleFootnoteContainer) row.getFootnotes()).getFootnotes();
	}

	/**
	 * Add empty value to table cell
	 * 
	 * @param row
	 *            the table row
	 * @param descriptor
	 *            the column
	 */
	protected static void addEmptyValue(final TableRow row,
			final ColumnDescriptor descriptor) {
		final TableCell cell = TablemodelFactory.eINSTANCE.createTableCell();
		cell.setColumndescriptor(descriptor);
		final StringCellContent content = TablemodelFactory.eINSTANCE
				.createStringCellContent();
		content.getValue().add(""); //$NON-NLS-1$
		cell.setContent(content);
		row.getCells().add(cell);
	}

	abstract SessionService getSessionService();
}
