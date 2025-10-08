/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.table.compile;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.ppmodel.extensions.PlanProSchnittstelleExtensions;
import org.eclipse.set.services.export.TableCompileService;
import org.eclipse.set.services.table.TableService;

import jakarta.inject.Inject;

/**
 * Implementation for {@link TableCompileService}.
 * 
 * @author Schaefer
 */
public class TableCompileServiceImpl implements TableCompileService {

	@Inject
	TableService tableService;

	@Override
	public Map<TableType, Table> compile(final String shortcut,
			final IModelSession modelSession,
			final Set<String> controlAreaIds) {
		final Map<TableType, Table> result = new EnumMap<>(TableType.class);
		if (PlanProSchnittstelleExtensions
				.isPlanning(modelSession.getPlanProSchnittstelle())) {
			Arrays.stream(TableType.values())
					.filter(type -> type != TableType.SINGLE)
					.forEach(type -> {
						final Table table = tableService
								.createDiffTable(shortcut, type,
										controlAreaIds);
						result.put(type, table);
					});
		} else {
			final Table single = tableService.createDiffTable(
					shortcut, TableType.SINGLE, controlAreaIds);
			result.put(TableType.SINGLE, single);
		}

		return result;
	}
}
