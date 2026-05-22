/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.model.simplemerge.extensions

import org.eclipse.set.model.simplemerge.Resolution

/**
 * Extensions for {@link Resolution}.
 * 
 * @author Schaefer
 */
class ResolutionExtensions {

	/**
	 * @param resolution this resolution
	 * 
	 * @return whether the resolution was automatic
	 */
	static def boolean isAutomatic(Resolution resolution) {
		switch (resolution) {
			case PRIMARY_UNRESOLVED,
			case PRIMARY_MANUAL,
			case SECONDARY_MANUAL: {
				return false
			}
			case PRIMARY_AUTO,
			case SECONDARY_AUTO: {
				return true
			}
			default: {
				throw new IllegalArgumentException(resolution.toString)
			}
		}
	}

	static def boolean isManualResolved(Resolution resolution) {
		return #{
			Resolution.PRIMARY_MANUAL,
			Resolution.SECONDARY_MANUAL
		}.contains(resolution)
	}

	static def boolean isResolved(Resolution resolution) {
		return resolution !== null &&
			resolution != Resolution.PRIMARY_UNRESOLVED
	}

	static def boolean isPrimary(Resolution resolution) {
		return #{
			Resolution.PRIMARY_UNRESOLVED,
			Resolution.PRIMARY_MANUAL,
			Resolution.PRIMARY_AUTO
		}.contains(resolution)
	}
}
