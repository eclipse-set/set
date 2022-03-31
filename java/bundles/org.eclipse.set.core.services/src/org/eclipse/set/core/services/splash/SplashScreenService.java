/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.splash;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Show a dynamic splash screen.
 * 
 * @author Schaefer
 */
public interface SplashScreenService {

	/**
	 * Show the splash screen.
	 * 
	 * @param descriptor
	 *            the descriptor for the splash screen image
	 */
	void show(ImageDescriptor descriptor);
}
