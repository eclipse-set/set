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

//TODO import static extension org.eclipse.set.ppmodel.extensions.SignalBefestigungExtensions.*

/**
 * Represents the full information for a given visualized siteplan signal
 * 
 * A siteplan signal consists of - At least one signal - Any number of signal
 * mounts either directly or indirectly (via another mount) connected to a
 * signal
 */
class SignalInfo {
	public Set<Signal> signals;
	public Set<Signal_Befestigung> mounts;

	/**
	 * Returns the base mount. The base mount is a mount which is not attached
	 * to any other mounts
	 */
	protected Signal_Befestigung getBaseMount() {
		return mounts.stream()
				.filter(sb -> sb.getIDSignalBefestigung() == null)
				.findFirst()
				.orElse(null);
	}

	protected String getSignalGuid() {
		final Signal_Befestigung base = this.getBaseMount();
		if (base != null) {
			return base.getIdentitaet().getWert();
		}
		return this.getFirstSignal().getIdentitaet().getWert();
	}

	// This order defines, what "First Signal" is
	private static final Comparator<Signal> signalComparator = Comparator
			.comparing((final Signal sig) -> sig.getIdentitaet().getWert());

	protected Signal getFirstSignal() {
		return signals.stream().min(signalComparator).orElse(null);
	}

}
