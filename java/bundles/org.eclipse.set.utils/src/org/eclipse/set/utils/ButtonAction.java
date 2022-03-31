/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils;

import org.eclipse.set.basis.emfforms.RendererContext;

/**
 * Describe a button action.
 * 
 * @author Schaefer
 */
public interface ButtonAction extends SelectableAction {

	/**
	 * @return the button width
	 */
	int getWidth();

	/**
	 * Called by the renderer to register the button and domain object with the
	 * action.
	 * 
	 * @param context
	 *            the renderer context
	 */
	void register(RendererContext context);
}
