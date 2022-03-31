/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.graph;

/**
 * A directed element.
 * 
 * @param <T>
 *            type of the directed element
 * 
 * @author Schaefer
 */
public interface DirectedElement<T> {

	/**
	 * @return the element
	 */
	T getElement();

	/**
	 * @return whether the direction is forwards
	 */
	boolean isForwards();

	/**
	 * @param element
	 *            the element
	 */
	void setElement(T element);

	/**
	 * @param isForwards
	 *            whether the direction is forwards
	 */
	void setForwards(boolean isForwards);
}
