/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Ortung.FMA_Anlage
import java.util.List
import org.eclipse.set.toolboxmodel.Gleis.Gleis_Abschnitt

/**
 * This class extends {@link Gleis_Abschnitt}.
 * 
 * @author Schaefer
 */
class GleisAbschnittExtensions extends BereichObjektExtensions {
	
	/**
	 * @param abschnitt this Gleisabschnitt
	 * 
	 * @returns the FMA Anlagen monitoring this Gleisabschnitt
	 */
	def static List<FMA_Anlage> getFmaAnlagen(Gleis_Abschnitt abschnitt) {
		return abschnitt.container.FMAAnlage.filter[IDGleisAbschnitt.identitaet.wert == abschnitt.identitaet.wert].toList
	}
}
