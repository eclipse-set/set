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
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.basis.files.ToolboxFileRole;
import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.model.planpro.Basisobjekte.Bearbeitungsvermerk;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.ColumnDescriptor;
import org.eclipse.set.model.tablemodel.CompareCellContent;
import org.eclipse.set.model.tablemodel.MultiColorCellContent;
import org.eclipse.set.model.tablemodel.RowGroup;
import org.eclipse.set.model.tablemodel.SimpleFootnoteContainer;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TableRow;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions;
import org.eclipse.set.services.table.TableDiffService;

import com.google.common.collect.Streams;

/**
 * 
 */
public abstract class AbstractTableDiff implements TableDiffService {

	/**
	 * @param first
	 *            the first cell
	 * @param second
	 *            the second cell
	 * @return true, if the table cell haven same value
	 */
	protected boolean isSameValue(final CellContent first,
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

	/**
	 * @param firstCellContent
	 *            the {@link CompareCellContent}
	 * @param secondCellContent
	 *            the {@link CellContent}
	 * @return true, if the table cell haven same value
	 */
	protected boolean isSameValue(final CompareCellContent firstCellContent,
			final CellContent secondCellContent) {
		// Compare between CompareCellContent with another cell content is only
		// in compare table between two Project available.
		// That mean by table DIFF state will compare FINAL state
		final TableType tableType = getSessionService()
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

	/**
	 * @param firstCellContent
	 *            the {@link MultiColorCellContent}
	 * @param secondCellContent
	 *            the {@link CellContent}
	 * @return true, if the table cell haven same value
	 */
	protected boolean isSameValue(final MultiColorCellContent firstCellContent,
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

	/**
	 * @param firstCellContent
	 *            the {@link StringCellContent}
	 * @param secondCellContent
	 *            the {@link CellContent}
	 * @return true, if the table cell haven same value
	 */
	protected boolean isSameValue(final StringCellContent firstCellContent,
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
				final TableType tableType = getSessionService()
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
	 * Merges two tables by combining their rows and ensuring that all unique
	 * rows from both tables are present in the result.
	 * 
	 * @param first
	 *            the first
	 * @param second
	 *            the second
	 * @return the table containing rows of both table
	 */
	protected Table expandNewRowGroups(final Table first, final Table second) {
		final Table result = EcoreUtil.copy(first);
		final List<RowGroup> newRowGroups = second.getTablecontent()
				.getRowgroups();
		newRowGroups.forEach(group -> addMissingRowGroup(group, result));
		return result;
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

	abstract void addMissingRowGroup(RowGroup group, Table result);

	abstract SessionService getSessionService();
}
