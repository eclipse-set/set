/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.helpmessage;

import org.eclipse.set.basis.emfforms.RendererContext;

/**
 * Provide help messages for EMF Forms views.
 * 
 * @author Schaefer
 */
public interface HelpMessageService {

	/**
	 * @param rendererContext
	 *            the renderer context
	 * 
	 * @return the help message
	 */
	String getMessage(RendererContext rendererContext);
}
