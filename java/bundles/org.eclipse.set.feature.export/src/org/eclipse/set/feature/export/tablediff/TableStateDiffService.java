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

import org.eclipse.set.core.services.session.SessionService;
import org.eclipse.set.model.tablemodel.CellContent;
import org.eclipse.set.model.tablemodel.CompareCellContent;
import org.eclipse.set.model.tablemodel.MultiColorCellContent;
import org.eclipse.set.model.tablemodel.TableCell;
import org.eclipse.set.model.tablemodel.TablemodelFactory;
import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions;
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

			// Convert to CompareCellContent, when give different between
			// initial
			// and final state
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

	@Override
	public TableCompareType getCompareType() {
		return TableCompareType.STATE;
	}
}
