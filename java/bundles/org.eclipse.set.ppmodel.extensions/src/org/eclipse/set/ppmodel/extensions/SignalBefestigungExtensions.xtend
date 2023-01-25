/**
 * Copyright (c) 2016 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.toolboxmodel.Regelzeichnung.Regelzeichnung
import org.eclipse.set.toolboxmodel.Signale.Signal_Befestigung
import java.util.List

/**
 * This class extends {@link Signal_Befestigung}.
 */
class SignalBefestigungExtensions extends BasisObjektExtensions {

	/**
	 * @param befestigung the Signalbefestigung
	 * 
	 * @returns the list of Regelzeichnungen
	 */
	def static List<Regelzeichnung> getRegelzeichnungen(
		Signal_Befestigung befestigung) {
		return befestigung.IDRegelzeichnung
	}


	/**
	 * @param befestigung the Signalbefestigung
	 * 
	 * @returns the zugehöriges Objekt Signal Befestigung
	 */
	def static Signal_Befestigung getSignalBefestigung(
		Signal_Befestigung befestigung) {
		return befestigung.IDSignalBefestigung
	}

	/**
	 * @param mount the Signal_Befestigung
	 * 
	 * @returns A list containing mount and all Signal_Befestigung mount is attached to (either directly or indirectly)
	 * 			in order from 'mount' to the last element in the Signal_Befestigung tree 
	 */
	def static List<Signal_Befestigung> getSignalBefestigungen(
		Signal_Befestigung mount) {
		val mounts = newArrayList
		var current = mount
		while (current !== null) {
			mounts.add(current)
			current = current.signalBefestigung
		}
		return mounts
	}

}
