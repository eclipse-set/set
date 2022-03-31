/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.graph;

import java.util.Objects;

/**
 * An element with an direction.
 * 
 * @param <T>
 *            type of the directed element
 * 
 * @author Schaefer
 */
public class DirectedElementImpl<T> implements DirectedElement<T> {

	/**
	 * @param element
	 *            the element
	 * @param <S>
	 *            type of the directed element
	 * 
	 * @return the element with the direction "backwards"
	 */
	public static <S> DirectedElementImpl<S> backwards(final S element) {
		return new DirectedElementImpl<>(element, false);
	}

	/**
	 * @param element
	 *            the element
	 * @param <S>
	 *            type of the directed element
	 * 
	 * @return the element with the direction "forwards"
	 */
	public static <S> DirectedElementImpl<S> forwards(final S element) {
		return new DirectedElementImpl<>(element, true);
	}

	private T element;

	private boolean isForwards;

	/**
	 * @param element
	 *            the element
	 * @param isForwards
	 *            whether the direction is forwards
	 */
	public DirectedElementImpl(final T element, final boolean isForwards) {
		this.element = element;
		this.isForwards = isForwards;
	}

	@Override
	public T getElement() {
		return element;
	}

	@Override
	public boolean isForwards() {
		return isForwards;
	}

	@Override
	public void setElement(final T element) {
		this.element = element;
	}

	@Override
	public void setForwards(final boolean isForwards) {
		this.isForwards = isForwards;
	}

	@Override
	public String toString() {
		return String.format("%s{element=%s isForwards=%s}", super.toString(), //$NON-NLS-1$
				element, Boolean.valueOf(isForwards));
	}

	@Override
	public int hashCode() {
		return Objects.hash(element, Boolean.valueOf(isForwards));
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DirectedElementImpl<?> other = (DirectedElementImpl<?>) obj;
		return Objects.equals(element, other.element)
				&& isForwards == other.isForwards;
	}
}
