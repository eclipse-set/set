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
import java.util.List
import org.eclipse.set.model.planpro.Ortung.FMA_Anlage
import java.util.Set

/**
 * No common FMA found for given Abschnittsgrenzen. 
 * 
 * @author Schaefer
 */
class NoCommonFma extends RuntimeException {
	
	/**
	 * @param abschnittsgrenzen the Abschnittsgrenzen
	 */
	new(List<FMA_Komponente> abschnittsgrenzen, Set<FMA_Anlage> commonFMAs) {
		super('''abschnittsgrenzen=«FOR a: abschnittsgrenzen SEPARATOR ", "
			»«a.bezeichnung.bezeichnungTabelle.wert»«ENDFOR» commonFMA=«FOR fma: commonFMAs SEPARATOR ", "
			»«fma.bezeichnung.bezeichnungKennbuchstabe»«ENDFOR»''')
	}
}
