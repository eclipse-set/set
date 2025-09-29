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

import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.services.table.TableDiffService;
import org.osgi.service.component.annotations.Component;

/**
 * 
 */
@Component(immediate = true, service = TableDiffService.class, property = "compareType=project")
public class TableProjectDiffService implements TableDiffService {

	@Override
	public Table createDiffTable(final Table oldTable, final Table newTable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table createCompareTable(final Table firstPlanTable,
			final Table secondPlanDiffTable) {
		// TODO Auto-generated method stub
		return null;
	}

}
