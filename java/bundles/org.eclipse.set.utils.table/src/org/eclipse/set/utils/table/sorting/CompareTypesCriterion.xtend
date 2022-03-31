/**
 * Copyright (c) 2019 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.sorting

import java.util.Comparator
import java.util.List
import org.eclipse.set.model.tablemodel.TableRow

import static extension org.eclipse.set.model.tablemodel.extensions.TableRowExtensions.*

/**
 * Comparator for Table Rows according to the used type
 */
package class CompareTypesCriterion implements Comparator<TableRow> {

	final List<String> types

	new(List<Class<?>> types) {
		this.types = types.map[simpleName]
	}

	override int compare(TableRow row1, TableRow row2) {
		return Integer.compare(row1.index, row2.index)
	}

	private def int getIndex(TableRow row) {
		return types.indexOf(row.group.leadingObject?.class?.simpleName) +
			row.group.leadingObjectIndex * types.size
	}
}
