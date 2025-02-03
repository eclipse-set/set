/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.export.checkboxmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.set.core.services.export.CheckboxModelElement;

/**
 * A set of CheckboxModelElements
 *
 * @author Rumpf/Schaefer
 */
public final class CheckboxModel {

	private final List<CheckboxModelElement> checkboxes;

	/**
	 * @param elements
	 *            the checkbox model elements
	 */
	public CheckboxModel(final CheckboxModelElement[] elements) {
		checkboxes = new ArrayList<>(Arrays.asList(elements));
	}

	/**
	 * Deselect all elements.
	 */
	public void deselectAll() {
		checkboxes.forEach(ele -> ele.deselect());
	}

	/**
	 * @return the checked elements
	 */
	public CheckboxModelElement[] getChecked() {
		final List<CheckboxModelElement> result = checkboxes.stream()
				.filter(ele -> ele.isChecked())
				.collect(Collectors.toList());
		return result.toArray(new CheckboxModelElement[result.size()]);
	}

	/**
	 * @return all elements
	 */
	public CheckboxModelElement[] getElements() {
		return checkboxes.toArray(new CheckboxModelElement[checkboxes.size()]);
	}

	/**
	 * Select all elements.
	 */
	public void selectAll() {
		checkboxes.forEach(ele -> ele.select());
	}
}
