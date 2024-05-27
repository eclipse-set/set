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
	 * @return the default value index
	 */
	public int getDefaultIndex();

	/**
	 * @return the default value
	 */
	public String getDefaultValue();

	/**
	 * @param value
	 *            the value
	 * 
	 * @return the index
	 */
	public int getIndex(T value);

	/**
	 * 
	 * @param stringValue
	 *            the value string value
	 * @return the index
	 */
	public int getIndex(String stringValue);

	/**
	 * @return the values in string
	 */
	public String[] getComboValues();

	/**
	 * @return the values string withoud default value
	 */
	public String[] getValuesWithoutDefault();

	/**
	 * @param selectionIndex
	 *            the selection index of the combo box
	 * 
	 * @return the value
	 */
	public T getValue(int selectionIndex);

	/**
	 * @param valueString
	 *            the value in string
	 * @return the value
	 */
	public T getValue(String valueString);

	/**
	 * @return the values size
	 */
	public int size();
}
