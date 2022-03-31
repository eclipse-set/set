/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.export;

/**
 * Model element for a checkbox.
 *
 * @author Rumpf/Schaefer
 */
public class CheckboxModelElement {

	private final String id;

	private final String name;

	private boolean selected = true;

	/**
	 * @param id
	 *            the unique id for this element
	 * @param name
	 *            the name of this element
	 */
	public CheckboxModelElement(final String id, final String name) {
		this.id = id;
		this.name = name;
	}

	/**
	 * Deselect this element.
	 */
	public void deselect() {
		selected = false;
	}

	/**
	 * @return the unique id of this element
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the name of this element
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return whether this element is checked
	 */
	public boolean isChecked() {
		return selected;
	}

	/**
	 * Select this element.
	 */
	public void select() {
		selected = true;
	}
}
