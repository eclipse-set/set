/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.util.Observable;

import org.eclipse.e4.ui.model.application.ui.MDirtyable;

/**
 * An observable dirty state.
 * 
 * @author Schaefer
 */
@SuppressWarnings("deprecation") // IMPROVE Observable should be replaced
public class DirtyState extends Observable {

	private final MDirtyable dirtyable;

	/**
	 * @param dirtyable
	 *            the dirtyable
	 */
	public DirtyState(final MDirtyable dirtyable) {
		this.dirtyable = dirtyable;
	}

	/**
	 * @return whether this state is dirty
	 */
	public boolean isDirty() {
		return dirtyable.isDirty();
	}

	/**
	 * @param value
	 *            the new value
	 */
	public void setDirty(final boolean value) {
		dirtyable.setDirty(value);
		setChanged();
		notifyObservers();
	}
}
