/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform;

import java.util.Comparator;
import java.util.Set;

import org.eclipse.set.model.planpro.Signale.Signal;
import org.eclipse.set.model.planpro.Signale.Signal_Befestigung;

/**
 * Represents the full information for a given visualized siteplan signal
 * 
 * A siteplan signal consists of - At least one signal - Any number of signal
 * mounts either directly or indirectly (via another mount) connected to a
 * signal
 */
class SignalInfo {
	/**
	 * all signals which are represented by the drawn signal on the Lageplan.
	 */
	public Set<Signal> signals;

	/**
	 * contains all mounts from connected, directly or indirectly, to any of the
	 * signals.
	 */
	public Set<Signal_Befestigung> mounts;

	/**
	 * the first mount with no parent, coming from the mount with a frame
	 * "Schirm" attached to it. If there are multiple Schirme, behaviour is
	 * unspecified (See SignalTransformator)
	 * 
	 * rootMount is not updated, when signals or mounts are changed
	 */
	public Signal_Befestigung baseMount;

	protected String getSignalGuid() {
		final Signal_Befestigung base = this.baseMount;
		if (base != null) {
			return base.getIdentitaet().getWert();
		}
		return this.getFirstSignal().getIdentitaet().getWert();
	}

	// This order defines, what "First Signal" is
	private static final Comparator<Signal> signalComparator = Comparator
			.comparing(sig -> sig.getIdentitaet().getWert());

	protected Signal getFirstSignal() {
		return signals.stream().min(signalComparator).orElse(null);
	}

}
