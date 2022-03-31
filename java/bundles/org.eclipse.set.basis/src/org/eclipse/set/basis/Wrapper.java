/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

/**
 * Wraps a value.
 * 
 * @param <T>
 *            the type of the value
 * 
 * @author Schaefer
 */
public class Wrapper<T> {
	private T value;

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(final T value) {
		this.value = value;
	}
}
