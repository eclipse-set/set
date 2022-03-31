/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis.emfforms;

/**
 * Context of a renderer.
 * 
 * @author Schaefer
 */
public interface RendererContext {

	/**
	 * @param <T>
	 *            the wanted type
	 * @param clazz
	 *            the wanted class
	 * 
	 * @return the object of the wanted type from the context
	 */
	public <T> T get(final Class<T> clazz);
}
