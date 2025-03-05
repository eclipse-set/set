/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import java.util.List
import org.eclipse.set.model.planpro.Fahrstrasse.Fstr_Signalisierung
import org.eclipse.set.model.planpro.Signalbegriffe_Struktur.Signalbegriff_ID_TypeClass
import org.eclipse.set.model.planpro.Signale.Signal_Signalbegriff

import static extension org.eclipse.set.ppmodel.extensions.SignalbegriffExtensions.*

/**
 * This class extends {@link Fstr_Signalisierung}.
 * 
 * @author Schaefer
 */
class FstrSignalisierungExtensions extends BasisObjektExtensions {

	/**
	 * @param sig this Signalisierung
	 * 
	 * @return the Signal Signalbegriff
	 */
	def static Signal_Signalbegriff getSignalSignalbegriff(
		Fstr_Signalisierung sig) {
		if (sig.IDSignalSignalbegriff === null) {
			throw new IllegalArgumentException(
				'''Fstr_Signalisierung «sig.identitaet.wert» has not the required IDSignalSignalbegriff value'''
			)
		}
		return sig.IDSignalSignalbegriff?.value
	}

	/**
	 * @param sig this Signalisierung
	 * 
	 * @return the Signal Signalbegriff Ziel (or <code>null</code> if there is no Signal Signalbegriff Ziel)
	 */
	def static Signal_Signalbegriff getSignalSignalbegriffZiel(
		Fstr_Signalisierung sig) {
		return sig.IDSignalSignalbegriffZiel?.value
	}

	def static <T extends Signalbegriff_ID_TypeClass> Signal_Signalbegriff getSignalbegriffWithType(
		Fstr_Signalisierung sig, Class<T> type) {
		val signalBegriff = sig.signalSignalbegriff
		if (signalBegriff.hasSignalbegriffID(type)) {
			return signalBegriff
		}
		return null
	}

	def static <T extends Signalbegriff_ID_TypeClass> List<Signal_Signalbegriff> getSignalberiffsWithType(
		List<Fstr_Signalisierung> sigs, Class<T> type) {
		return sigs.map[getSignalbegriffWithType(type)].filterNull.toList
	}
}
