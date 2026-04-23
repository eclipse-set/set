/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.Comparator
import java.util.regex.Pattern
import org.eclipse.set.basis.extensions.MatcherExtensions
import org.eclipse.set.model.planpro.Basisobjekte.Punkt_Objekt_Strecke_AttributeGroup
import org.eclipse.set.model.planpro.Geodaten.Strecke

/**
 * Extensions for {@link Punkt_Objekt_Strecke_AttributeGroup}.
 * 
 * @author Schaefer
 */
class PunktObjektStreckeExtensions extends BasisObjektExtensions {

	public static final Pattern KILOMETRIERUNG_PATTERN = Pattern.compile(
		"(?<numberN>-?([1-9]\\d{0,2}|0)),((?<numberD1>\\d{3})|(?<numberD2>\\d)(?<numberN2>[\\+\\-][1-9]\\d{0,4}))"); // $NON-NLS-1$
	static final String EXTRA_LENGTH_GROUP_NAME = "numberN2"; // $NON-NLS-1$

	/**
	 * @param p this Punkt Objekt
	 * 
	 * @returns the Strecke of the Punkt Objekt
	 */
	def static Strecke getStrecke(Punkt_Objekt_Strecke_AttributeGroup p) {
		return p.IDStrecke?.value
	}
	
	def static Comparator<String> compareKm() {
		return [first, second|
			if (!KILOMETRIERUNG_PATTERN.matcher(first).matches || !KILOMETRIERUNG_PATTERN.matcher(second).matches) {
				return 0
			}
			val firstKm = first.analyseKmValue
			val secondKm = second.analyseKmValue
			val mainValueComapare = firstKm.key.compareTo(secondKm.key)
			if (mainValueComapare !== 0) {
				return mainValueComapare
			}
			return firstKm.value.compareTo(secondKm.value)
		]
	}
	
	def static Pair<Double, Double> analyseKmValue(String km) {
		val matcher = KILOMETRIERUNG_PATTERN.matcher(km)
		val extraLength = MatcherExtensions.getGroup(matcher, EXTRA_LENGTH_GROUP_NAME)
		if (extraLength.present) {
			val mainKm = km.replace(extraLength.get, "")
			return Double.valueOf(mainKm.replace(",", ".")) -> Double.valueOf(extraLength.get)
		}
		return Double.valueOf(km.replace(",", ".")) -> Double.valueOf(0)
	}
}
