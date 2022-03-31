/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.basis;

/**
 * Provides symbol rotation.
 * 
 * @author Schaefer
 */
public interface SymbolRotationProvider {

	/**
	 * @return the total symbol rotation (counterclockwise in degrees)
	 */
	double getRotation();
}
