/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Interface to manipulate lists of domain elements.
 * 
 * @param <T>
 *            the type of the domain element
 * @param <I>
 *            the type of the media info
 * 
 * @author Schaefer
 */
public interface DomainElementList<T, I extends MediaInfo<T>> {

	/**
	 * A listener to changes of domain element lists.
	 * 
	 * @param <T>
	 *            the type of the domain element
	 */
	public static interface ChangeListener<T> {
		/**
		 * @param msg
		 *            the notification
		 */
		void listChanged(Notification msg);
	}

	/**
	 * Adds a new element.
	 * 
	 * @param mediaInfo
	 *            the element info
	 */
	void add(I mediaInfo);

	/**
	 * @param changeListener
	 *            the change listener
	 */
	void addChangeListener(ChangeListener<T> changeListener);

	/**
	 * @return the container of the list
	 */
	EObject getContainer();

	/**
	 * @return the containing feature
	 */
	EStructuralFeature getContainingFeature();

	/**
	 * @return the elements of this list
	 */
	List<T> getElements();

	/**
	 * @return the feature
	 */
	EStructuralFeature getFeature();

	/**
	 * Remove an element.
	 * 
	 * @param element
	 *            the element
	 */
	void remove(T element);

	/**
	 * @param changeListener
	 *            the change listener
	 */
	void removeChangeListener(ChangeListener<T> changeListener);
}
