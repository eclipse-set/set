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
package org.eclipse.set.feature.export.checkboxmodel;

import java.util.Collection;
import java.util.List;

import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.services.table.TableService;
import org.eclipse.set.utils.table.TableInfo;
import org.eclipse.set.utils.table.TableInfo.Pt1TableCategory;

/**
 * 
 */
public class CheckboxTreeModel {

	private final List<CheckBoxTreeElement> elements;
	private final TableService tableService;

	public CheckboxTreeModel(final List<CheckBoxTreeElement> elements,
			final TableService tableService) {
		this.elements = elements;
		this.tableService = tableService;
	}

	public void deselectAll() {
		elements.forEach(ele -> ele.deselect());
	}

	public void selectAll() {
		elements.forEach(ele -> ele.select());
	}

	public CheckBoxTreeElement[] getChildElements() {
		return elements.stream()
				.filter(ele -> ele.isParent())
				.flatMap(ele -> ele.getChildElements().stream())
				.toArray(CheckBoxTreeElement[]::new);
	}

	public CheckBoxTreeElement[] getParentElements() {
		return elements.stream()
				.filter(ele -> ele.isParent())
				.toArray(CheckBoxTreeElement[]::new);
	}

	public CheckBoxTreeElement[] getAllElements() {
		return elements.toArray(new CheckBoxTreeElement[elements.size()]);
	}

	public CheckBoxTreeElement[] getChecked() {
		final List<CheckBoxTreeElement> checkedParents = elements.stream()
				.filter(ele -> ele.isChecked())
				.toList();
		final List<CheckBoxTreeElement> checkedChilds = elements.stream()
				.flatMap(ele -> ele.getChildElements().stream())
				.filter(ele -> ele.isChecked())
				.toList();
		return List.of(checkedParents, checkedChilds)
				.stream()
				.flatMap(Collection::stream)
				.filter(ele -> ele.isChecked())
				.toArray(CheckBoxTreeElement[]::new);
	}

	public void addElement(final CheckBoxTreeElement element) {
		elements.add(element);
	}

	public void addElement(final TableInfo tableInfo, final String status) {
		final Pt1TableCategory category = tableInfo.category();
		CheckBoxTreeElement parentElement = elements.stream()
				.filter(ele -> ele.getId().equals(category.getId()))
				.findFirst()
				.orElse(null);
		if (parentElement == null) {
			parentElement = new CheckBoxTreeElement(null, category.getId(),
					category.toString());
			elements.add(parentElement);
		}
		final TableNameInfo tableNameInfo = tableService
				.getTableNameInfo(tableInfo.shortcut());
		elements.add(new CheckBoxTreeElement(parentElement,
				tableNameInfo.getShortName().toLowerCase(),
				tableNameInfo.getFullDisplayName()));
	}

	public CheckBoxTreeElement getElement(final TableInfo tableInfo) {
		final TableNameInfo tableNameInfo = tableService
				.getTableNameInfo(tableInfo.shortcut());
		final CheckBoxTreeElement parent = elements.stream()
				.filter(ele -> ele.getId().equals(tableInfo.category().getId()))
				.findFirst()
				.orElse(null);
		if (parent != null) {
			return parent.getChildElements()
					.stream()
					.filter(ele -> ele.getId()
							.equals(tableNameInfo.getShortName().toLowerCase()))
					.findFirst()
					.orElse(null);
		}
		return elements.stream()
				.filter(ele -> ele.getId()
						.equals(tableNameInfo.getShortName().toLowerCase()))
				.findFirst()
				.orElse(null);
	}
}
