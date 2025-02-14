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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.set.core.services.export.CheckboxModelElement;

/**
 * Check box element for tree structure
 * 
 * @author Truong
 */
public class CheckBoxTreeElement extends CheckboxModelElement {

	CheckBoxTreeElement parent;
	List<CheckBoxTreeElement> childs;

	/**
	 * @param parent
	 *            the parent element
	 * @param id
	 *            the unique id for this element
	 * @param name
	 *            the name of this element
	 */
	public CheckBoxTreeElement(final CheckBoxTreeElement parent,
			final String id, final String name) {
		this(parent, id, name, null);
	}

	/**
	 * @param parent
	 *            the parent element
	 * @param id
	 *            the unique id for this element
	 * @param name
	 *            the name of this element
	 * @param status
	 *            the supplement information of this element
	 */
	public CheckBoxTreeElement(final CheckBoxTreeElement parent,
			final String id, final String name, final String status) {
		super(id, name, status);
		this.parent = parent;
		this.childs = new ArrayList<>();
		if (parent != null) {
			parent.addChild(this);
		}
	}

	/**
	 * @return the parent element
	 */
	public CheckBoxTreeElement getParent() {
		return parent;
	}

	/**
	 * @return true, when this element have child
	 */
	public boolean isParent() {
		return !childs.isEmpty();
	}

	/**
	 * @return the childs element
	 */
	public List<CheckBoxTreeElement> getChildElements() {
		return childs;
	}

	/**
	 * Add child element for this element
	 * 
	 * @param child
	 *            the child element
	 */
	private void addChild(final CheckBoxTreeElement child) {
		this.childs.add(child);
	}

	@Override
	public void select() {
		super.select();
		if (isParent()) {
			childs.forEach(ele -> ele.select());
		} else if (parent != null && parent.isAllChildSameState(true)) {
			parent.selected = true;
		}
	}

	@Override
	public void deselect() {
		super.deselect();
		if (isParent()) {
			childs.forEach(ele -> ele.deselect());
		} else if (parent != null && parent.isAllChildSameState(false)) {
			parent.selected = false;
		}
	}

	@Override
	public boolean isChecked() {
		if (childs.isEmpty()) {
			return super.isChecked();
		}

		return childs.stream().allMatch(ele -> ele.isChecked());
	}

	private boolean isAllChildSameState(final boolean checked) {
		if (!isParent()) {
			return false;
		}
		return childs.stream().allMatch(ele -> ele.isChecked() == checked);
	}
}
