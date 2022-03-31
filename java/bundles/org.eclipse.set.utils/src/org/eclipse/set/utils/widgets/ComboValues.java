/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.widgets;

/**
 * Describes values for a combo box.
 * 
 * @param <T>
 *            the type for the values
 * 
 * @author Schaefer
 */
public interface ComboValues<T> {

	/**
	 * @return the default value
	 */
	public int getDefault();

	/**
	 * @param value
	 *            the value
	 * 
	 * @return the index
	 */
	public int getIndex(T value);

	/**
	 * @return the items
	 */
	public String[] getItems();

	/**
	 * @param selectionIndex
	 *            the selection index of the combo box
	 * 
	 * @return the value
	 */
	public T getValue(int selectionIndex);
}
