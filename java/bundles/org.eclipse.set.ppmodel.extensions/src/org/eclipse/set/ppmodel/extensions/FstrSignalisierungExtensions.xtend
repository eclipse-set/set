/**
 * Copyright (c) 2015 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Fahrstrasse.Fstr_Signalisierung
import org.eclipse.set.toolboxmodel.Signale.Signal_Signalbegriff
import static extension org.eclipse.set.ppmodel.extensions.ZeigerExtensions.*

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
		return sig.IDSignalSignalbegriff.resolve(Signal_Signalbegriff)
	}

	/**
	 * @param sig this Signalisierung
	 * 
	 * @return the Signal Signalbegriff Ziel (or <code>null</code> if there is no Signal Signalbegriff Ziel)
	 */
	def static Signal_Signalbegriff getSignalSignalbegriffZiel(
		Fstr_Signalisierung sig) {
		return sig.IDSignalSignalbegriffZiel.resolve(Signal_Signalbegriff)
	}
}
