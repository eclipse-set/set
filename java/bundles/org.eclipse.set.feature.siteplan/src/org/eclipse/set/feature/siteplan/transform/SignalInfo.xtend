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
import org.eclipse.set.model.planpro.Signale.Signal
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung


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
	
	/**
	 * the first mount with no parent, coming from the mount with a frame
	 * "Schirm" attached to it. 
	 * If there are multiple Schirme, behaviour is unspecified (See SignalTransformator)
	 * 
	 * rootMount is not updated, when signals or mounts are changed
	 */
	public Signal_Befestigung rootMount;
	
	def String getSignalGuid() {
		val base = rootMount
		if(base !== null)
			return base.identitaet?.wert
		return firstSignal?.identitaet?.wert
	}
	
	def Signal getFirstSignal() 
	{
		return signals.sortBy[identitaet.wert].head
	}
}
