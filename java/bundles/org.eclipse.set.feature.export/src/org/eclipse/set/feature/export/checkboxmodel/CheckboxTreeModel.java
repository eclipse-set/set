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

import org.eclipse.set.basis.export.CheckBoxTreeElement;

/**
 * The check box tree data model
 * 
 * @author Truong
 */
public class CheckboxTreeModel {

	private final List<CheckBoxTreeElement> elements;

	/**
	 * Constructor
	 * 
	 * @param elements
	 *            the first elements of the tree
	 */
	public CheckboxTreeModel(final List<CheckBoxTreeElement> elements) {
		this.elements = elements;
	}

	/**
	 * Deselect all element
	 */
	public void deselectAll() {
		elements.forEach(ele -> ele.deselect());
	}

	/**
	 * Select all element
	 */
	public void selectAll() {
		elements.forEach(ele -> ele.select());
	}

	/**
	 * @return the parent element in tree model
	 */
	public CheckBoxTreeElement[] getParentElements() {
		return Arrays.stream(getAllElements())
				.filter(ele -> ele.isParent())
				.toArray(CheckBoxTreeElement[]::new);
	}

	/**
	 * @return the element at first level of the tree model
	 */
	public CheckBoxTreeElement[] getElements() {
		return elements.toArray(CheckBoxTreeElement[]::new);
	}

	/**
	 * @return all element in data model
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
	 * @return all checked elements
	 */
	public CheckBoxTreeElement[] getChecked() {
		return Arrays.stream(getAllElements())
				.filter(ele -> ele.isChecked())
				.toArray(CheckBoxTreeElement[]::new);
	}

	/**
	 * Find the element with given element id
	 * 
	 * @param elementId
	 *            the element id
	 * @return the optional of the element
	 */
	public Optional<CheckBoxTreeElement> getElement(final String elementId) {
		return Arrays.stream(getAllElements())
				.filter(ele -> ele.getId().equals(elementId))
				.findFirst();
	}

	/**
	 * Find the element with parent id and element id
	 * 
	 * @param parentId
	 *            the parent id
	 * @param element
	 *            the element id
	 * @return the optional of element
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
	 * Add a element to data model at first level of the tree
	 * 
	 * @param element
	 *            the element
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
	 * Add a element to given parent element, when the parent not exists in
	 * model, then add the parent element first
	 * 
	 * @param parent
	 *            the parent element
	 * @param newElement
	 *            the element
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
		final CheckBoxTreeElement parentElement = getElement(parent.getId())
				.orElse(null);
		if (parentElement == null) {
			elements.add(parent);
		}
		parent.addChild(newElement);
	}
}
