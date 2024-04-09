/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.exception

import org.eclipse.set.model.planpro.Ortung.FMA_Komponente

/**
 * No unique FMA-Anlage found for given boundaries. 
 * 
 * @author Schaefer
 */
class MissingFmaBoundary extends RuntimeException {
	
	/**
	 * @param border1 first border
	 * @param border2 second border
	 */
	new(FMA_Komponente border1, FMA_Komponente border2) {
		super(
			'''border1=«border1.name» border2=«border2.name»'''
		)
	}
	
	private static def getName(FMA_Komponente komponente) {
		return '''«komponente?.bezeichnung?.bezeichnungTabelle?.wert» («komponente?.identitaet?.wert»)'''
	}
}
