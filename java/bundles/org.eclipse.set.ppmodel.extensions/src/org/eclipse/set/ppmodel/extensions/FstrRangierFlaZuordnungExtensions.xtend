/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Rangier_Fla_Zuordnung
import org.eclipse.set.model.planpro.Flankenschutz.Fla_Schutz

/**
 * This class extends {@link Fstr_Rangier_Fla_Zuordnung}.
 */
class FstrRangierFlaZuordnungExtensions extends BasisObjektExtensions {

	/**
	 * @param fstrRangierFlaZuordnung this Fstr_Rangier_Fla_Zuordnung
	 * 
	 * @returns the Fla_Schutz
	 */
	def static Fla_Schutz getFlaSchutz(
		Fstr_Rangier_Fla_Zuordnung fstrRangierFlaZuordnung) {
		return fstrRangierFlaZuordnung.IDFlaSchutz?.value
	}
}
