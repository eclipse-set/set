/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.utils.emfforms;

/**
 * Common interface for toolbox renderer.
 * 
 * @author Schaefer
 */
public interface ToolboxRenderer {

	/**
	 * Check the renderer.
	 * 
	 * @throws IllegalStateException
	 *             if the renderer has an illegal state (like being disposed)
	 */
	void checkToolboxRenderer();
}
