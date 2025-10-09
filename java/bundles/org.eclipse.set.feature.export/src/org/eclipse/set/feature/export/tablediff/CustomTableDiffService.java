/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.tablediff;

import static org.eclipse.set.model.tablemodel.extensions.TableCellExtensions.getIterableStringValue;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;
import org.eclipse.set.model.planpro.Basisobjekte.Identitaet_TypeClass;
import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.CompareCellContent;
import org.eclipse.set.model.tablemodel.CompareFootnoteContainer;
import org.eclipse.set.model.tablemodel.CompareTableCellContent;
import org.eclipse.set.model.tablemodel.MultiColorCellContent;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions;
import org.eclipse.set.model.tablemodel.extensions.TableExtensions;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.services.table.TableDiffService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.google.common.collect.Streams;

/**
 * Custom implementation of {@link TableDiffService}.
 * 
 * @author Schaefer
 * 
 * @usage production
 */
@Component(immediate = true)
public class CustomTableDiffService implements TableDiffService {
	@Reference
	SessionService sessionService;

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

			// we add the new group to the table
			table.getTablecontent().getRowgroups().add(newRowGroup);
		}
	}

	private static void createDiffContent(final int i, final TableRow row,
			final TableRow match,
			final BiFunction<TableCell, TableCell, CellContent> createCompareContent) {
		final TableCell oldCell = row.getCells().get(i);
		TableCell newCell = TablemodelFactory.eINSTANCE.createTableCell();
		newCell.setContent(
				TablemodelFactory.eINSTANCE.createStringCellContent());
		if (match != null) {
			newCell = match.getCells().get(i);
		}
		if (oldCell.getContent() instanceof MultiColorCellContent) {
			createMultiColorDiffCotent(oldCell, newCell);
			return;
		}
		final CellContent compareCellContent = createCompareContent
				.apply(oldCell, newCell);
		if (compareCellContent == null) {
			return;
		}
		oldCell.setContent(compareCellContent);
	}

	// IMPROVE: this function isn't completely.
	private static void createMultiColorDiffCotent(final TableCell oldCell,
			final TableCell newCell) {
		if (oldCell
				.getContent() instanceof final MultiColorCellContent oldCellContent
				&& newCell != null && newCell
						.getContent() instanceof final MultiColorCellContent newCellContent) {
			if (CellContentExtensions.getPlainStringValue(oldCellContent)
					.equals(CellContentExtensions
							.getPlainStringValue(newCellContent))) {
				oldCellContent.getValue()
						.forEach(e -> e.setDisableMultiColor(false));
				return;
			}

			final CompareCellContent compareCellContent = TablemodelFactory.eINSTANCE
					.createCompareCellContent();
			oldCellContent.getValue().forEach(colorContent -> {
				compareCellContent.getOldValue()
						.add(String.format(colorContent.getStringFormat(),
								colorContent.getMultiColorValue()));
			});

			newCellContent.getValue().forEach(colorContent -> {
				compareCellContent.getNewValue()
						.add(String.format(colorContent.getStringFormat(),
								colorContent.getMultiColorValue()));
			});
			oldCell.setContent(compareCellContent);
		}
	}

	private static CompareCellContent createCompareCellContent(
			final TableCell oldCell, final TableCell newCell) {
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

	private CompareTableCellContent createTableCompareCellContent(
			final TableCell mainTableCell, final TableCell compareTableCell) {

		if (isSameValue(
				EObjectExtensions
						.getNullableObject(mainTableCell, TableCell::getContent)
						.orElse(null),
				EObjectExtensions
						.getNullableObject(compareTableCell,
								TableCell::getContent)
						.orElse(null))) {
			return null;
		}

		final CompareTableCellContent compareTableCellContent = TablemodelFactory.eINSTANCE
				.createCompareTableCellContent();
		compareTableCellContent
				.setMainPlanCellContent(mainTableCell.getContent());
		compareTableCellContent
				.setComparePlanCellContent(compareTableCell == null
						|| compareTableCell.getContent() == null ? null
								: compareTableCell.getContent());
		return compareTableCellContent;

	}

	/**
	 * @param sessionService
	 *            the {@link SessionService}
	 * @param first
	 *            the first cell
	 * @param second
	 *            the second cell
	 * @return true, if the table cell haven same value
	 */
	private boolean isSameValue(final CellContent first,
			final CellContent second) {
		if (first == null || second == null) {
			return first == second;
		}
		return switch (first) {
			case final StringCellContent stringContent -> isSameValue(
					stringContent, second);
			case final CompareCellContent compareContent -> isSameValue(
					compareContent, second);
			case final MultiColorCellContent multiColorContent -> isSameValue(
					multiColorContent, second);
			default -> true;
		};
	}

	private boolean isSameValue(final CompareCellContent firstCellContent,
			final CellContent secondCellContent) {
		// Compare between CompareCellContent with another cell content is only
		// in compare table between two Project available.
		// That mean by table DIFF state will compare FINAL state
		final TableType tableType = sessionService
				.getLoadedSession(ToolboxFileRole.SESSION)
				.getTableType();
		final StringCellContent compareCellStringContent = TablemodelFactory.eINSTANCE
				.createStringCellContent();
		if (tableType == TableType.INITIAL) {
			compareCellStringContent.getValue()
					.addAll(firstCellContent.getOldValue());
		} else {
			compareCellStringContent.getValue()
					.addAll(firstCellContent.getNewValue());
		}
		return isSameValue(compareCellStringContent, secondCellContent);
	}

	private boolean isSameValue(final MultiColorCellContent firstCellContent,
			final CellContent secondCellContent) {
		return firstCellContent.getValue().stream().allMatch(value -> {
			final StringCellContent stringCellContent = TablemodelFactory.eINSTANCE
					.createStringCellContent();
			if (value.getMultiColorValue() == null
					|| value.getMultiColorValue().isEmpty()) {
				stringCellContent.getValue().add(value.getStringFormat());

			} else {
				stringCellContent.getValue()
						.add(String.format(value.getStringFormat(),
								value.getMultiColorValue()));
			}

			return isSameValue(stringCellContent, secondCellContent);
		});
	}

	private boolean isSameValue(final StringCellContent firstCellContent,
			final CellContent secondCellContent) {
		final Set<String> firstValues = Streams
				.stream(CellContentExtensions
						.getStringValueIterable(firstCellContent))
				.filter(value -> value != null && !value.trim().isEmpty())
				.collect(Collectors.toSet());
		if (firstValues.contains(CellContentExtensions.HOURGLASS_ICON)) {
			return false;
		}
		return switch (secondCellContent) {
			case final StringCellContent stringContent -> {
				final Set<String> secondValues = Streams
						.stream(CellContentExtensions
								.getStringValueIterable(stringContent))
						.filter(value -> value != null
								&& !value.trim().isEmpty())
						.collect(Collectors.toSet());
				yield firstValues.equals(secondValues);
			}
			case final CompareCellContent compareContent -> {
				final TableType tableType = sessionService
						.getLoadedSession(ToolboxFileRole.SESSION)
						.getTableType();
				final Set<String> values = switch (tableType) {
					case TableType.INITIAL -> compareContent.getOldValue()
							.stream()
							.filter(value -> value != null
									&& !value.trim().isEmpty())
							.collect(Collectors.toSet());
					default -> compareContent.getNewValue()
							.stream()
							.filter(value -> value != null
									&& !value.trim().isEmpty())
							.collect(Collectors.toSet());

				};
				yield firstValues.equals(values);
			}
			case final MultiColorCellContent multiColorContent -> {
				final Set<String> values = multiColorContent.getValue()
						.stream()
						.map(content -> {
							if (content.getMultiColorValue() == null
									|| content.getMultiColorValue().isEmpty()) {
								return content.getStringFormat();
							}
							return String.format(content.getStringFormat(),
									content.getMultiColorValue());
						})
						.collect(Collectors.toSet());
				yield firstValues.equals(values);
			}
			default -> false;
		};
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
			createDiffContent(i, row, match,
					(a, b) -> createCompareCellContent(a, b));
		}

		// Create diff footnotes
		final CompareFootnoteContainer diffFootnotes = TablemodelFactory.eINSTANCE
				.createCompareFootnoteContainer();

		final List<Bearbeitungsvermerk> oldFootnotes = getFootnotes(row);
		final List<Bearbeitungsvermerk> newFootnotes = getFootnotes(match);

		for (final Bearbeitungsvermerk footnote : oldFootnotes) {
			if (newFootnotes.stream()
					.anyMatch(c -> c.getIdentitaet()
							.getWert()
							.equals(footnote.getIdentitaet().getWert()))) {
				diffFootnotes.getUnchangedFootnotes().add(footnote);
			} else {
				diffFootnotes.getOldFootnotes().add(footnote);
			}
		}

		for (final Bearbeitungsvermerk footnote : newFootnotes) {
			if (oldFootnotes.stream()
					.anyMatch(c -> c.getIdentitaet()
							.getWert()
							.equals(footnote.getIdentitaet().getWert()))) {
				// do nothing (already added by for loop above)
			} else {
				diffFootnotes.getNewFootnotes().add(footnote);
			}
		}

		row.setFootnotes(diffFootnotes);
	}

	private static List<Bearbeitungsvermerk> getFootnotes(final TableRow row) {
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

	@Override
	public Table createCompareTable(final Table mainPlanTable,
			final Table comparePlanTable) {
		final Table copy = EcoreUtil.copy(mainPlanTable);
		final Table expandedTable = expandNewRowGroups(comparePlanTable, copy);
		final List<TableRow> rows = TableExtensions.getTableRows(copy);
		rows.forEach(row -> {
			final TableRow match = TableExtensions.getMatchingRow(expandedTable,
					row);
			// Create diff content
			for (int i = 0; i < row.getCells().size(); i++) {
				createDiffContent(i, row, match,
						this::createTableCompareCellContent);
			}
		});

		return copy;
	}
}
