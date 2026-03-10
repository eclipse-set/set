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

import static org.eclipse.set.model.tablemodel.extensions.CellContentExtensions.createStringCellContent;
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
import org.eclipse.set.model.tablemodel.extensions.CellContentExtensions;
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
			createMultiColorDiffCotent(oldCell, newCell);
			return oldCell.getContent();
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
			final CompareStateCellContent compareCellContent = TablemodelFactory.eINSTANCE
					.createCompareStateCellContent();
			oldCellContent.getValue()
					.forEach(colorContent -> compareCellContent
							.setOldValue(createStringCellContent(String.format(
									colorContent.getStringFormat(),
									colorContent.getMultiColorValue()))));

			newCellContent.getValue()
					.forEach(colorContent -> compareCellContent
							.setNewValue(createStringCellContent(String.format(
									colorContent.getStringFormat(),
									colorContent.getMultiColorValue()))));
			oldCell.setContent(compareCellContent);
		}
	}

	@Override
	public TableCompareType getCompareType() {
		return TableCompareType.STATE;
	}
}
