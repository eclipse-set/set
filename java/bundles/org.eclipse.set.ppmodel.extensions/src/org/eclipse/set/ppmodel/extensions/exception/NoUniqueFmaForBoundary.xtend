/**
 * Copyright (c) 2016 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions.exception

import de.scheidtbachmann.planpro.model.model1902.Ortung.FMA_Anlage
import de.scheidtbachmann.planpro.model.model1902.Ortung.FMA_Komponente
import java.util.Set

import static extension org.eclipse.set.ppmodel.extensions.FmaAnlageExtensions.*

/**
 * No unique FMA-Anlage found for given boundaries. 
 * 
 * @author Schaefer
 */
class NoUniqueFmaForBoundary extends RuntimeException {
	
	/**
	 * @param border1 first border
	 * @param border2 second border
	 * @param anlagen found FMA Anlagen
	 */
	new(FMA_Komponente border1, FMA_Komponente border2, Set<FMA_Anlage> anlagen) {
		super(
			'''border1=«border1.name» border2=«border2.name» anlagen={«
				FOR anlage: anlagen SEPARATOR ", "»«anlage.name»«ENDFOR»}'''
		)
	}
	
	private static def getName(FMA_Komponente komponente) {
		return '''«komponente?.bezeichnung?.bezeichnungTabelle?.wert» («komponente?.identitaet?.wert»)'''
	}
	
	private static def getName(FMA_Anlage anlage) {
		return '''«anlage?.tableName» («anlage?.identitaet?.wert»)'''
	}
}
