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
package org.eclipse.set.feature.table.diff;

import static org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.*;
import static org.eclipse.set.model.tablemodel.extensions.TableCellExtensions.getIterableStringValue;

import java.util.Collections;
import java.util.Set;

import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.CompareStateCellContent;
import org.eclipse.set.model.tablemodel.MultiColorCellContent;
import org.eclipse.set.model.tablemodel.StringCellContent;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.ppmodel.extensions.EObjectExtensions;
import org.eclipse.set.services.table.TableDiffService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Craete diff table between states Initial and Final
 * 
 * @author Schaefer
 */
@Component(immediate = true, service = TableDiffService.class)
public class TableStateDiffService extends AbstractTableDiff {

	@Reference
	SessionService sessionService;

	@Override
	SessionService getSessionService() {
		return sessionService;
	}

	@Override
	CellContent createDiffContent(final TableCell oldCell,
			final TableCell newCell) {
		if (oldCell.getContent() instanceof MultiColorCellContent
				|| newCell != null && newCell
						.getContent() instanceof MultiColorCellContent) {
			return createMultiColorDiffContent(oldCell.getContent(),
					newCell.getContent());
		}

		if (!(oldCell.getContent() instanceof StringCellContent)
				|| newCell != null && newCell.getContent() != null && !(newCell
						.getContent() instanceof StringCellContent)) {
			throw new IllegalArgumentException(
					"Can not create CompareStateCellContent only from StringCellContent"); //$NON-NLS-1$
		}

		final Set<String> oldValues = getIterableStringValue(oldCell);
		final Set<String> newValues = newCell == null ? Collections.emptySet()
				: getIterableStringValue(newCell);
		if (oldValues.equals(newValues)) {
			return null;
		}
		final CompareStateCellContent compareContent = TablemodelFactory.eINSTANCE
				.createCompareStateCellContent();
		compareContent.setOldValue(createStringCellContent(oldValues));
		compareContent.setNewValue(createStringCellContent(newValues));
		compareContent.setSeparator(EObjectExtensions
				.getNullableObject(oldCell, c -> c.getContent().getSeparator())
				.orElse(null));
		return compareContent;
	}

	@SuppressWarnings("nls")
	private static CellContent createMultiColorDiffContent(
			final CellContent oldContent, final CellContent newContent) {
		if (oldContent == null
				&& newContent instanceof final MultiColorCellContent newMuliColorCellContent) {
			return createCompareStateCellContent(oldContent,
					createStringCellContent(
							getStringValueIterable(newMuliColorCellContent)));
		}

		if (oldContent == null) {
			throw new IllegalArgumentException();
		}

		return switch (oldContent) {
			case final StringCellContent oldStringCellContent -> {
				if (newContent instanceof final MultiColorCellContent newMultiColorCellContent) {
					yield createMultiColorDiffContent(oldStringCellContent,
							newMultiColorCellContent);
				}
				throw new IllegalArgumentException(
						"At least one cell content is multicolor cellcontent");
			}
			case final MultiColorCellContent oldMultiColorCellContent -> {
				if (newContent instanceof final StringCellContent newStringCellContent) {
					yield createMultiColorDiffContent(oldMultiColorCellContent,
							newStringCellContent);
				}

				if (newContent instanceof final MultiColorCellContent newMultiColorCellContent) {
					yield createMultiColorDiffContent(oldMultiColorCellContent,
							newMultiColorCellContent);
				}
				throw new IllegalArgumentException(
						"At least one cell content is multicolor cellcontent");
			}

			default -> throw new IllegalArgumentException(
					"At least one cell content is multicolor cellcontent");
		};
	}

	private static CellContent createMultiColorDiffContent(
			final StringCellContent oldContent,
			final MultiColorCellContent newContent) {
		final String plainStringValue = getPlainStringValue(oldContent);
		if (!plainStringValue.isEmpty()) {
			throw new IllegalArgumentException(
					"The compare cells content must be same type"); //$NON-NLS-1$
		}
		return createCompareStateCellContent(oldContent,
				createStringCellContent(getStringValueIterable(newContent)));
	}

	private static CellContent createMultiColorDiffContent(
			final MultiColorCellContent oldContent,
			final StringCellContent newContent) {
		if (!getPlainStringValue(newContent).isEmpty()) {
			throw new IllegalArgumentException(
					"The compare cells content must be same type"); //$NON-NLS-1$
		}
		return createCompareStateCellContent(
				createStringCellContent(getStringValueIterable(oldContent)),
				newContent);
	}

	private static CellContent createMultiColorDiffContent(
			final MultiColorCellContent oldContent,
			final MultiColorCellContent newContent) {
		if (getPlainStringValue(oldContent)
				.equals(getPlainStringValue(newContent))) {
			oldContent.getValue()
					.forEach(value -> value.setDisableMultiColor(false));
			return oldContent;
		}
		return createCompareStateCellContent(
				createStringCellContent(getStringValueIterable(oldContent)),
				createStringCellContent(getStringValueIterable(newContent)));
	}

	private static CompareStateCellContent createCompareStateCellContent(
			final CellContent oldContent, final CellContent newContent) {
		final CompareStateCellContent compareStateCellContent = TablemodelFactory.eINSTANCE
				.createCompareStateCellContent();
		compareStateCellContent.setOldValue(oldContent);
		compareStateCellContent.setNewValue(newContent);
		return compareStateCellContent;
	}

	@Override
	public TableCompareType getCompareType() {
		return TableCompareType.STATE;
	}
}
