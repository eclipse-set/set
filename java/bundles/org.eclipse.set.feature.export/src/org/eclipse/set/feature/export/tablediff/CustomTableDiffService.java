/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.tablediff;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.set.model.planpro.Basisobjekte.Identitaet_TypeClass;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.CompareCellContent;
import org.eclipse.set.model.tablemodel.CompareFootnoteContainer;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.extensions.TableCellExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.services.table.TableDiffService;
import org.osgi.service.component.annotations.Component;

/**
 * Custom implementation of {@link TableDiffService}.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(immediate = true)
public class CustomTableDiffService implements TableDiffService {
	private static void addEmptyValue(final TableRow row,
			final ColumnDescriptor descriptor) {
		final TableCell cell = TablemodelFactory.eINSTANCE.createTableCell();
		cell.setColumndescriptor(descriptor);
		final StringCellContent content = TablemodelFactory.eINSTANCE
				.createStringCellContent();
		content.getValue().add(""); //$NON-NLS-1$
		cell.setContent(content);
		row.getCells().add(cell);
	}

	private static void addMissingRowGroup(final RowGroup group,
			final Table table) {
		String groupGuid = null;
		final Ur_Objekt leadingObject = group.getLeadingObject();
		if (leadingObject != null) {
			final Identitaet_TypeClass identitaet = leadingObject
					.getIdentitaet();
			if (identitaet != null) {
				groupGuid = identitaet.getWert();
			}
		}

		final int groupId = group.getLeadingObjectIndex();
		final RowGroup match = TableExtensions.getGroupById(table, groupGuid,
				groupId);
		if (match == null) {
			// we add a new matching group to the table
			final RowGroup newRowGroup = TablemodelFactory.eINSTANCE
					.createRowGroup();
			newRowGroup.setLeadingObject(group.getLeadingObject());
			newRowGroup.setLeadingObjectIndex(group.getLeadingObjectIndex());

			// we add the same number of empty rows to the new group
			for (int i = 0; i < group.getRows().size(); i++) {
				final TableRow newRow = TablemodelFactory.eINSTANCE
						.createTableRow();
				newRowGroup.getRows().add(newRow);
				final List<ColumnDescriptor> columns = TableExtensions
						.getColumns(table);
				columns.forEach(column -> addEmptyValue(newRow, column));
			}

			// we add the new group to the table
			table.getTablecontent().getRowgroups().add(newRowGroup);
		}
	}

	private static void createDiffContent(final int i, final TableRow row,
			final TableRow match) {
		final TableCell oldCell = row.getCells().get(i);
		final Set<String> oldValue = TableCellExtensions
				.getIterableStringValue(oldCell);
		final Set<String> newValue = new LinkedHashSet<>();
		if (match != null) {
			newValue.addAll(TableCellExtensions
					.getIterableStringValue(match.getCells().get(i)));
		}

		if (!oldValue.equals(newValue)) {
			final CompareCellContent compareContent = TablemodelFactory.eINSTANCE
					.createCompareCellContent();
			compareContent.getOldValue().addAll(oldValue);
			compareContent.getNewValue().addAll(newValue);
			compareContent.setSeparator(oldCell.getContent().getSeparator());
			oldCell.setContent(compareContent);
		}
	}

	private static Table expandNewRowGroups(final Table oldTable,
			final Table newTable) {
		final Table result = EcoreUtil.copy(oldTable);
		final List<RowGroup> newRowGroups = newTable.getTablecontent()
				.getRowgroups();
		newRowGroups.forEach(group -> addMissingRowGroup(group, result));
		return result;
	}

	private static void matchRow(final TableRow row, final Table newTable) {
		final TableRow match = TableExtensions.getMatchingRow(newTable, row);
		// Create diff content
		for (int i = 0; i < row.getCells().size(); i++) {
			createDiffContent(i, row, match);
		}

		// Create diff footnotes
		final CompareFootnoteContainer diffFootnotes = TablemodelFactory.eINSTANCE
				.createCompareFootnoteContainer();

		final List<String> oldFootnotes = getFootnotes(row);
		final List<String> newFootnotes = getFootnotes(match);

		for (final String footnote : oldFootnotes) {
			if (newFootnotes.stream().anyMatch(c -> c.equals(footnote))) {
				diffFootnotes.getUnchangedFootnotes().add(footnote);
			} else {
				diffFootnotes.getOldFootnotes().add(footnote);
			}
		}

		for (final String footnote : newFootnotes) {
			if (oldFootnotes.stream().anyMatch(c -> c.equals(footnote))) {
				// do nothing (already added by for loop above)
			} else {
				diffFootnotes.getNewFootnotes().add(footnote);
			}
		}

		row.setFootnotes(diffFootnotes);
	}

	private static List<String> getFootnotes(final TableRow row) {
		if (row == null || row.getFootnotes() == null
				|| ((SimpleFootnoteContainer) row.getFootnotes())
						.getFootnotes() == null) {
			return List.of();
		}
		return ((SimpleFootnoteContainer) row.getFootnotes()).getFootnotes();
	}

	private static Table matchRows(final Table expanded, final Table newTable) {
		final Table result = EcoreUtil.copy(expanded);
		final List<TableRow> rows = TableExtensions.getTableRows(result);
		rows.forEach(row -> matchRow(row, newTable));
		return result;
	}

	@Override
	public Table createDiffTable(final Table oldTable, final Table newTable) {
		// expand old table by new lines
		final Table expanded = expandNewRowGroups(oldTable, newTable);

		// create diff table by matching each row of the expanded table
		return matchRows(expanded, newTable);
	}
}
