/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.compileservice;

import java.util.HashMap;
import java.util.Map;

import jakarta.inject.Inject;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.feature.table.TableService;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.services.export.TableCompileService;
import org.eclipse.set.services.table.TableDiffService;

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
			final IModelSession modelSession) {
		final Map<TableType, Table> result = new HashMap<>();

		if (PlanProSchnittstelleExtensions
				.isPlanning(modelSession.getPlanProSchnittstelle())) {
			final Table start = tableService.transformToTable(shortcut,
					TableType.INITIAL, modelSession);
			final Table ziel = tableService.transformToTable(shortcut, TableType.FINAL,
					modelSession);
			final Table diff = diffService.createDiffTable(start, ziel);

			result.put(TableType.DIFF, diff);
			result.put(TableType.FINAL, ziel);
		} else {
			final Table single = tableService.transformToTable(shortcut,
					TableType.SINGLE, modelSession);
			result.put(TableType.SINGLE, single);
		}

		return result;
	}
}
