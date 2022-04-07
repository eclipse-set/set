/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Fahrstrasse.Markanter_Punkt
import org.eclipse.set.toolboxmodel.Flankenschutz.Fla_Schutz
import org.eclipse.set.toolboxmodel.Nahbedienbereich.NB_Zone
import org.eclipse.set.toolboxmodel.Nahbedienbereich.NB_Zone_Grenze
import java.util.List

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
		return nbZoneGrenze.IDNBZone
	}

	/**
	 * @param nbZoneGrenze this Bereichsgrenze
	 * 
	 * @return the Markanter Punkt of this Bereichsgrenze
	 */
	def static Markanter_Punkt getMarkanterPunkt(NB_Zone_Grenze nbZoneGrenze) {
		return nbZoneGrenze.IDMarkanterPunkt
	}

	/**
	 * @param nbZoneGrenze this Bereichsgrenze
	 * 
	 * @return the Flankenschutz this Bereichsgrenze is Anforderer of
	 */
	def static List<Fla_Schutz> getFlaSchutz(NB_Zone_Grenze nbZoneGrenze) {
		return nbZoneGrenze.container.flaSchutz.filter [
			flaSchutzAnforderer.IDAnfordererElement.identitaet.wert ==
				nbZoneGrenze.identitaet.wert
		].toList
	}
}
