/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.services.export;

import java.util.Map;
import java.util.Set;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.basis.constants.TableType;
import org.eclipse.set.model.tablemodel.Table;
import org.eclipse.set.utils.table.TableInfo;

/**
 * Compile tables.
 * 
 * @author Schaefer
 */
public interface TableCompileService {

	/**
	 * @param tableInfo
	 *            the {@link TableInfo}
	 * @param modelSession
	 *            the model session
	 * @param controlAreaIds
	 *            the selected control areas
	 * 
	 * @return a mapping of possible tables
	 */
	Map<TableType, Table> compile(TableInfo tableInfo,
			IModelSession modelSession, Set<String> controlAreaIds);
}
