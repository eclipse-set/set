/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.List
import org.eclipse.set.model.planpro.Ansteuerung_Element.Stell_Bereich
import org.eclipse.set.model.planpro.Fahrstrasse.Markanter_Punkt
import org.eclipse.set.model.planpro.Flankenschutz.Fla_Schutz
import org.eclipse.set.model.planpro.Nahbedienung.NB_Zone
import org.eclipse.set.model.planpro.Nahbedienung.NB_Zone_Grenze

import static extension org.eclipse.set.ppmodel.extensions.BereichObjektExtensions.*
import static extension org.eclipse.set.ppmodel.extensions.MarkanterPunktExtensions.*

/**
 * Extensions for {@link NB_Zone}.
 */
class NbZoneGrenzeExtensions extends BasisObjektExtensions {

	/**
	 * @param nbZoneGrenze this Bereichsgrenze
	 * 
	 * @return the Nahbedienbereichszone this Bereichsgrenze is defined for
	 */
	def static NB_Zone getNbZone(NB_Zone_Grenze nbZoneGrenze) {
		return nbZoneGrenze.IDNBZone?.value
	}

	/**
	 * @param nbZoneGrenze this Bereichsgrenze
	 * 
	 * @return the Markanter Punkt of this Bereichsgrenze
	 */
	def static Markanter_Punkt getMarkanterPunkt(NB_Zone_Grenze nbZoneGrenze) {
		return nbZoneGrenze.IDMarkanterPunkt?.value
	}

	/**
	 * @param nbZoneGrenze this Bereichsgrenze
	 * 
	 * @return the Flankenschutz this Bereichsgrenze is Anforderer of
	 */
	def static List<Fla_Schutz> getFlaSchutz(NB_Zone_Grenze nbZoneGrenze) {
		return nbZoneGrenze.container.flaSchutz.filter [
			flaSchutzAnforderer.IDAnfordererElement?.wert ==
				nbZoneGrenze.identitaet.wert
		].toList
	}

	def static boolean isRelevantControlArea(NB_Zone_Grenze grenze,
		Stell_Bereich controlArea) {
		return grenze.markanterPunkt.markanteStelle.punktObjektTOPKante.exists [ potk |
			controlArea.bereichObjektTeilbereich.exists[it.contains(potk)]
		]
	}

}
