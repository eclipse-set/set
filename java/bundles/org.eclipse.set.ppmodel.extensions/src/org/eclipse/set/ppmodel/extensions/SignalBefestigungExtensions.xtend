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
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung
import static extension org.eclipse.set.ppmodel.extensions.SignalExtensions.*

/**
 * This class extends {@link Signal_Befestigung}.
 */
class SignalBefestigungExtensions extends BasisObjektExtensions {

	/**
	 * @param befestigung the Signalbefestigung
	 * 
	 * @returns the zugehöriges Objekt Signal Befestigung
	 */
	def static Signal_Befestigung getSignalBefestigung(
		Signal_Befestigung befestigung) {
		return befestigung.IDSignalBefestigung?.value
	}

	/**
	 * @param mount the starting Signal_Befestigung
	 * 
	 * @returns A list containing mount and all Signal_Befestigung mount is attached to (either directly or indirectly)
	 * 			in order from 'mount' to the last element in the Signal_Befestigung tree 
	 * 			includes the starting-mount
	 */
	def static List<Signal_Befestigung> getSignalBefestigungen(
		Signal_Befestigung mount) {
		val mounts = newArrayList
		var current = mount
		while (current !== null && !mounts.contains(current)) {
			mounts.add(current)
			current = current.signalBefestigung
		}
		return mounts
	}

	def static List<Signal> getAttachmentSignal(Signal_Befestigung mount) {
		return mount.container.signal.filter [ s |
			s.signalRahmen.map[IDSignalBefestigung?.value].filterNull.exists [
				it === mount
			]
		].toList
	}
}
