/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.compileservice;

import java.util.EnumMap;
import java.util.Map;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.ContainerType;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.feature.table.TableService;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.services.export.TableCompileService;
import org.eclipse.set.services.table.TableDiffService;

import jakarta.inject.Inject;

/**
 * Implementation for {@link TableCompileService}.
 * 
 * @author Schaefer
 */
public class TableCompileServiceImpl implements TableCompileService {

	@Inject
	private TableDiffService diffService;

	@Inject
	TableService tableService;

	@Override
	public Map<TableType, Table> compile(final String shortcut,
			final IModelSession modelSession,
			final Map<String, ContainerType> controlAreaIds) {
		final Map<TableType, Table> result = new EnumMap<>(TableType.class);
		if (PlanProSchnittstelleExtensions
				.isPlanning(modelSession.getPlanProSchnittstelle())) {
			final Table start = tableService.transformToTable(shortcut,
					TableType.INITIAL, modelSession, controlAreaIds);
			final Table ziel = tableService.transformToTable(shortcut,
					TableType.FINAL, modelSession, controlAreaIds);
			final Table diff = diffService.createDiffTable(start, ziel);

			result.put(TableType.DIFF, diff);
			result.put(TableType.FINAL, ziel);
		} else {
			final Table single = tableService.transformToTable(shortcut,
					TableType.SINGLE, modelSession, controlAreaIds);
			result.put(TableType.SINGLE, single);
		}

		return result;
	}
}
