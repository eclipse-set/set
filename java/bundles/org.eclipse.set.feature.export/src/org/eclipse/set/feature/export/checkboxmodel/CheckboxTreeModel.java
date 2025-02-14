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

	/**
	 * 
	 * @param elements
	 */
	public CheckboxTreeModel(final List<CheckBoxTreeElement> elements) {
		this.elements = elements;
	}

	/**
	 * 
	 */
	public void deselectAll() {
		elements.forEach(ele -> ele.deselect());
	}

	/**
	 * 
	 */
	public void selectAll() {
		elements.forEach(ele -> ele.select());
	}

	/**
	 * 
	 * @return
	 */
	public CheckBoxTreeElement[] getParentElements() {
		return Arrays.stream(getAllElements())
				.filter(ele -> ele.isParent())
				.toArray(CheckBoxTreeElement[]::new);
	}

	/**
	 * 
	 * @return
	 */
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

	/**
	 * 
	 * @return
	 */
	public CheckBoxTreeElement[] getChecked() {
		return Arrays.stream(getAllElements())
				.filter(ele -> ele.isChecked())
				.toArray(CheckBoxTreeElement[]::new);
	}

	/**
	 * 
	 * @param elementId
	 * @return
	 */
	public Optional<CheckBoxTreeElement> getElement(final String elementId) {
		return Arrays.stream(getAllElements())
				.filter(ele -> ele.getId().equals(elementId))
				.findFirst();
	}

	/**
	 * 
	 * @param parentId
	 * @param element
	 * @return
	 */
	public Optional<CheckBoxTreeElement> getElement(final String parentId,
			final String element) {
		return Arrays.stream(getParentElements())
				.filter(parent -> parent.getId().equals(parentId))
				.flatMap(ele -> ele.getChildElements().stream())
				.filter(ele -> ele.getId().equals(element))
				.findFirst();
	}

	/**
	 * 
	 * @param element
	 */
	public void addElement(final CheckBoxTreeElement element) {
		final Optional<CheckBoxTreeElement> findElement = getElement(
				element.getId());
		if (findElement.isPresent()) {
			throw new IllegalArgumentException(String.format(
					"Element with i: %s already exists ", element.getId())); //$NON-NLS-1$
		}
		addElement(null, element);
	}

	/**
	 * 
	 * @param parent
	 * @param newElement
	 */
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
					"Element with i: %s already exists in group: %s", //$NON-NLS-1$
					newElement.getId(), parent.getId()));
		}
		parent.addChild(newElement);
	}
}
