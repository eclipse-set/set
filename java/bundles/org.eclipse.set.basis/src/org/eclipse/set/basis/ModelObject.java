/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Abstract class for data binding support.
 * 
 * @author rumpf
 *
 */
public abstract class ModelObject implements Cloneable {
	private final PropertyChangeSupport changeSupport = new PropertyChangeSupport(
			this);

	/**
	 * adds a PropertyChangeListener
	 * 
	 * @param listener
	 *            the listener
	 */
	public void addPropertyChangeListener(
			final PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * removes a PropertyChangeListener
	 * 
	 * @param listener
	 *            the listener
	 */
	public void removePropertyChangeListener(
			final PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * fires a property change event.
	 */
	protected void firePropertyChange(final String propertyName,
			final Object oldValue, final Object newValue) {
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}
}
