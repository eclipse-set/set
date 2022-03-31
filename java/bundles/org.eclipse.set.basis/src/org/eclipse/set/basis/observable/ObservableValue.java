/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.observable;

import java.util.Observable;
import java.util.Optional;

/**
 * A value, that can be observed.
 * 
 * @param <T>
 *            the type of the value
 * 
 * @author Schaefer
 */
@SuppressWarnings("deprecation") // IMPROVE Observable should be replaced
public class ObservableValue<T> extends Observable {

	private Optional<T> value = Optional.empty();

	/**
	 * @return the (optional) value
	 */
	public Optional<T> getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the new value
	 */
	public void setValue(final T value) {
		this.value = Optional.ofNullable(value);
		setChanged();
		notifyObservers();
	}
}
