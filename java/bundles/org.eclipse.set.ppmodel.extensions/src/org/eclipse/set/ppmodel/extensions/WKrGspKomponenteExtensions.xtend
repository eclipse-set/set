/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Ortung.FMA_Anlage
import org.eclipse.set.toolboxmodel.Regelzeichnung.Regelzeichnung
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Element
import org.eclipse.set.toolboxmodel.Weichen_und_Gleissperren.W_Kr_Gsp_Komponente
import java.util.List
import java.util.Set

import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.GleisAbschnittExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.PunktObjektTopKanteExtensions.*
import org.eclipse.set.toolboxmodel.Geodaten.TOP_Kante

/**
 * This class extends {@link W_Kr_Gsp_Komponente}.
 * 
 * @author Schaefer
 */
class WKrGspKomponenteExtensions extends PunktObjektExtensions {

	/**
	 * @param komponente this Weiche, Kreuzung or Gleissperre Komponente
	 * 
	 * @return the Weiche, Kreuzung or Gleissperre Element
	 */
	def static W_Kr_Gsp_Element getWKrGspElement(
		W_Kr_Gsp_Komponente komponente) {
		return komponente?.IDWKrGspElement
	}

	/**
	 * @param komponente this Weiche, Kreuzung or Gleissperre Komponente
	 * 
	 * @return the Regelzeichnungen
	 */
	def static List<Regelzeichnung> getRegelzeichnungen(
		W_Kr_Gsp_Komponente komponente) {
		return komponente?.IDRegelzeichnung
	}

	/**
	 * @param komponente this Weiche, Kreuzung or Gleissperre Komponente
	 * 
	 * @return the FMA Anlage of this Komponente
	 */
	def static Set<FMA_Anlage> getFmaAnlage(W_Kr_Gsp_Komponente komponente) {
		return komponente.container?.gleisAbschnitt?.filter [
			intersects(komponente)
		].map[fmaAnlagen].flatten.toSet
	}

	/**
	 * @param komponente this Weiche, Kreuzung or Gleissperre Komponente
	 * 
	 * @return whether this Weiche, Kreuzung oder Gleissperre Komponente has a Zungenpaar
	 */
	def static boolean hasZungenpaar(W_Kr_Gsp_Komponente komponente) {
		return komponente.zungenpaar !== null
	}
	
	def static List<TOP_Kante> getTOPKante(W_Kr_Gsp_Komponente komponente) {
		return komponente.punktObjektTOPKante.map[topKante]
	}
}
