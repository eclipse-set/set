/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.table.tree

import org.eclipse.nebula.widgets.nattable.sort.SortHeaderLayer
import org.eclipse.nebula.widgets.nattable.sort.command.SortColumnCommand
import org.eclipse.nebula.widgets.nattable.sort.command.SortCommandHandler
import org.eclipse.nebula.widgets.nattable.tree.TreeLayer
import org.eclipse.nebula.widgets.nattable.tree.TreeRowModel
import org.eclipse.set.utils.table.BodyLayerStack
import org.eclipse.set.utils.table.sorting.TableSortModel

class TreeSortCommandHandler extends SortCommandHandler<BodyLayerStack> {
	TreeLayer treeLayer
	TableSortModel sortModel

	new(TableSortModel sortModel,
		SortHeaderLayer<BodyLayerStack> sortHeaderLayer, TreeLayer treeLayer) {
		super(sortModel, sortHeaderLayer)
		this.treeLayer = treeLayer
		this.sortModel = sortModel
	}

	override doCommand(SortColumnCommand command) {
		if (!treeLayer.hasHiddenRows) {
			return super.doCommand(command)
		}

		val hiddenRows = treeLayer.hiddenRowIndexes.map [
			sortModel.tableData.getRowData(it)
		].filterNull.toList
		treeLayer.expandAll
		super.doCommand(command)
		val treeData = treeDataProvider
		if (treeData === null) {
			return true
		}
		
		val parents = hiddenRows.map [
			treeData.getParent(it)
		].filterNull.toSet
		parents.forEach [
			treeLayer.collapseTreeRow(
				sortModel.tableData.getCurrentRowIndex(it))

		]
		return true
	}

	def TreeDataProvider getTreeDataProvider() {
		if (treeLayer.model instanceof TreeRowModel<?>) {
			val treeRowModel = (treeLayer.model as TreeRowModel<?>)
			if (treeRowModel.treeData instanceof TreeDataProvider) {
				return treeRowModel.treeData as TreeDataProvider
			}
		}	
		return null
	}
}
