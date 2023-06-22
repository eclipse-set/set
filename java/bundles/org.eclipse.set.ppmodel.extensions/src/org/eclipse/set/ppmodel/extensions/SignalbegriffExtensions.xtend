/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Signalbegriffe_Struktur.Signalbegriff_ID_TypeClass
import org.eclipse.set.toolboxmodel.Signale.Signal_Rahmen
import org.eclipse.set.toolboxmodel.Signale.Signal_Signalbegriff
import java.util.Collection

/**
 * This class extends {@link Signal_Signalbegriff}.
 */
class SignalbegriffExtensions extends BasisObjektExtensions {

	/**
	 * @param signalBegriff this Signalbegriff
	 * 
	 * @return the Signalrahmen containing this Signalbegriff
	 */
	def static Signal_Rahmen signalRahmen(Signal_Signalbegriff signalBegriff) {
		return signalBegriff.IDSignalRahmen
	}

	/**
	 * @param signalBegriff this Signalbegriff
	 * @param type the type of the Signalbegriff ID
	 * 
	 * @return whether this Signalbegriff has a Signalbegriff ID of the given
	 * type
	 */
	def static <T extends Signalbegriff_ID_TypeClass> boolean hasSignalbegriffID(
		Signal_Signalbegriff signalBegriff, Class<T> type) {
		return type.isInstance(signalBegriff?.signalbegriffID)
	}

	/**
	 * @param signalBegriffe a collection of Signalbegriffe
	 * @param type the type of the Signalbegriff ID
	 * 
	 * @return whether the collection contains a Signalbegriff ID of the given type
	 */
	def static <T extends Signalbegriff_ID_TypeClass> boolean containsSignalbegriffID(
		Collection<Signal_Signalbegriff> signalBegriffe, Class<T> type) {
		return signalBegriffe.exists[hasSignalbegriffID(type)]
	}
}
