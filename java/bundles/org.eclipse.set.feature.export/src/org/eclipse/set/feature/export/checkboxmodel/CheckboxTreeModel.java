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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 
 */
public class CheckboxTreeModel {

	private final List<CheckBoxTreeElement> elements;

	public CheckboxTreeModel(final List<CheckBoxTreeElement> elements) {
		this.elements = elements;
	}

	public void deselectAll() {
		elements.forEach(ele -> ele.deselect());
	}

	public void selectAll() {
		elements.forEach(ele -> ele.select());
	}

	public CheckBoxTreeElement[] getParentElements() {
		return Arrays.stream(getAllElements())
				.filter(ele -> ele.isParent())
				.toArray(CheckBoxTreeElement[]::new);
	}

	public CheckBoxTreeElement[] getAllElements() {
		final List<CheckBoxTreeElement> parentElements = elements.stream()
				.toList();
		final List<CheckBoxTreeElement> childElements = elements.stream()
				.flatMap(ele -> ele.getChildElements().stream())
				.toList();
		return List.of(parentElements, childElements)
				.stream()
				.flatMap(Collection::stream)
				.toArray(CheckBoxTreeElement[]::new);
	}

	public CheckBoxTreeElement[] getChecked() {
		return Arrays.stream(getAllElements())
				.filter(ele -> ele.isChecked())
				.toArray(CheckBoxTreeElement[]::new);
	}

	public Optional<CheckBoxTreeElement> getElement(final String elementId) {
		return Arrays.stream(getAllElements())
				.filter(ele -> ele.getId().equals(elementId))
				.findFirst();
	}

	public void addElement(final CheckBoxTreeElement element) {
		final Optional<CheckBoxTreeElement> findElement = getElement(
				element.getId());
		if (findElement.isPresent()) {
			throw new IllegalArgumentException(String.format(
					"Element with i: %s already exists ", element.getId()));
		}
		addElement(null, element);
	}

	public void addElement(final CheckBoxTreeElement parent,
			final CheckBoxTreeElement newElement) {
		if (parent == null) {
			elements.add(newElement);
			return;
		}
		if (parent.getChildElements()
				.stream()
				.anyMatch(ele -> ele.getId().equals(newElement.getId()))) {
			throw new IllegalArgumentException(String.format(
					"Element with i: %s already exists in group: %s",
					newElement.getId(), parent.getId()));
		}
		parent.addChild(newElement);
	}
	//
	// public CheckBoxTreeElement addElement(final TableInfo tableInfo,
	// final String status) {
	// final Pt1TableCategory category = tableInfo.category();
	// CheckBoxTreeElement parentElement = elements.stream()
	// .filter(ele -> ele.getId().equals(category.getId()))
	// .findFirst()
	// .orElse(null);
	// if (parentElement == null) {
	// parentElement = new CheckBoxTreeElement(category.getId(),
	// category.toString());
	// elements.add(parentElement);
	// }
	//
	// final TableNameInfo tableNameInfo = tableService
	// .getTableNameInfo(tableInfo.shortcut());
	// return new CheckBoxTreeElement(parentElement,
	// tableNameInfo.getShortName().toLowerCase(),
	// tableNameInfo.getFullDisplayName(), status);
	// }
	//
	// public CheckBoxTreeElement getElement(final TableInfo tableInfo) {
	// final TableNameInfo tableNameInfo = tableService
	// .getTableNameInfo(tableInfo.shortcut());
	// final CheckBoxTreeElement parent = elements.stream()
	// .filter(ele -> ele.getId().equals(tableInfo.category().getId()))
	// .findFirst()
	// .orElse(null);
	// if (parent != null) {
	// return parent.getChildElements()
	// .stream()
	// .filter(ele -> ele.getId()
	// .equals(tableNameInfo.getShortName().toLowerCase()))
	// .findFirst()
	// .orElse(null);
	// }
	// return elements.stream()
	// .filter(ele -> ele.getId()
	// .equals(tableNameInfo.getShortName().toLowerCase()))
	// .findFirst()
	// .orElse(null);
	// }
}
