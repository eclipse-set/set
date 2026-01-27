/**
 * Copyright (c) 2025 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.export;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.set.basis.export.CheckBoxTreeElement;
import org.eclipse.set.feature.export.checkboxmodel.CheckboxTreeModel;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.services.table.TableService;
import org.eclipse.set.utils.table.TableInfo;
import org.eclipse.set.utils.table.TableInfo.Pt1TableCategory;

/**
 * Tree data model for the table export
 * 
 * @author Truong
 */
public class TableCheckboxTreeModel extends CheckboxTreeModel {
	TableService tableService;

	Map<TableInfo, CheckBoxTreeElement> tableInfoTreeElementMap;

	/**
	 * Constructor
	 * 
	 * @param elements
	 *            the first level element
	 * @param tableService
	 *            the {@link TableService}
	 */
	public TableCheckboxTreeModel(final List<CheckBoxTreeElement> elements,
			final TableService tableService) {
		super(elements);
		this.tableService = tableService;
		this.tableInfoTreeElementMap = new HashMap<>();
	}

	/**
	 * Add table element to data model
	 * 
	 * @param info
	 *            the {@link TableInfo}
	 * @return the added element
	 */
	public CheckBoxTreeElement addElement(final TableInfo info) {
		final Pt1TableCategory category = info.category();
		CheckBoxTreeElement parent = Arrays.stream(getParentElements())
				.filter(ele -> ele.getId().equals(category.getId()))
				.findFirst()
				.orElse(null);
		if (parent == null) {
			parent = new CheckBoxTreeElement(category.getId(),
					category.toString());
			addElement(parent);
		}
		final TableNameInfo nameInfo = tableService.getTableNameInfo(info);
		final CheckBoxTreeElement newElement = new CheckBoxTreeElement(
				nameInfo.getShortName().toLowerCase(),
				nameInfo.getFullDisplayName());
		tableInfoTreeElementMap.put(info, newElement);
		addElement(parent, newElement);
		return newElement;
	}

	/**
	 * Find table element with {@link TableInfo}
	 * 
	 * @param info
	 *            the {@link TableInfo}
	 * @return the optional of the table element
	 */
	public Optional<CheckBoxTreeElement> getElement(final TableInfo info) {
		final Optional<CheckBoxTreeElement> ele = getElement(
				info.category().getId(), info.shortcut());
		if (ele.isPresent()) {
			tableInfoTreeElementMap.putIfAbsent(info, ele.get());
		}
		return ele;
	}

	/**
	 * @param treeElement
	 *            the tree element
	 * @return the {@link TableInfo} of this tree element
	 */
	public Optional<TableInfo> getTableInfo(
			final CheckBoxTreeElement treeElement) {
		return tableInfoTreeElementMap.entrySet()
				.stream()
				.filter(ele -> ele.getValue().equals(treeElement))
				.map(Map.Entry::getKey)
				.findFirst();
	}
}
