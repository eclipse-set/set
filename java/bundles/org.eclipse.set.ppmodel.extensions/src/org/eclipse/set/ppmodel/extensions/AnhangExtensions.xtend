/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions;

import org.eclipse.set.toolboxmodel.Basisobjekte.Anhang

/**
 * Extensions for {@link Anhang}.
 * 
 * @author Schaefer
 */
class AnhangExtensions {

	/**
	 * @param this Anhang
	 * 
	 * @return the filename of this Anhang
	 */
	static def String getFilename(Anhang anhang) {
		return '''«anhang?.anhangAllg?.dateiname?.wert».«anhang?.anhangAllg?.dateityp?.wert?.literal»'''
	}
	

}
