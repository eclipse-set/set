/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.tree

import org.eclipse.nebula.widgets.nattable.tree.TreeRowModel
import org.eclipse.set.utils.table.TableRowData

class TableTreeRowModel extends TreeRowModel<TableRowData> {
	
	new(TreeDataProvider treeData) {
		super(treeData)
	}
	
	override collapse(int rowIndex) {
		treeData.collasedGroup(rowIndex)
		return super.collapse(rowIndex)
	}
	
	override expand(int rowIndex) {
		treeData.expandedGroup(rowIndex)
		return super.expand(rowIndex)
	}
	
	
	override TreeDataProvider getTreeData() {
		return super.treeData as TreeDataProvider
	}
	
}