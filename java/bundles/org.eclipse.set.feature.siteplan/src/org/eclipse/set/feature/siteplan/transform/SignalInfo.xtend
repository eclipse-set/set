/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform

import java.util.Set
import org.eclipse.set.toolboxmodel.Signale.Signal
import org.eclipse.set.toolboxmodel.Signale.Signal_Befestigung

import static extension org.eclipse.set.ppmodel.extensions.SignalBefestigungExtensions.*

/**
 * Represents the full information for a given visualized siteplan signal
 * 
 * A siteplan signal consists of 
 * - At least one signal
 * - Any number of signal mounts either directly or indirectly (via another mount) 
 *   connected to a signal  
 */
class SignalInfo {
	public Set<Signal> signals
	public Set<Signal_Befestigung> mounts;
	
	def String getSignalGuid() {
		val base = baseMount
		if(base !== null)
			return base.identitaet?.wert
		return firstSignal?.identitaet?.wert
	}
	
	def Signal getFirstSignal() 
	{
		return signals.sortBy[identitaet.wert].head
	}
	
	/**
	 * Returns the base mount. 
	 * The base mount is a mount which is not attached to any other mounts 
	 */
	def Signal_Befestigung getBaseMount() {
		return mounts.filter[signalBefestigung === null].head
	}
	
}
