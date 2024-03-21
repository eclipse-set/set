/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.Signalbegriffe_Ril_301.Signalbegriffe_Ril_301Package

/**
 * This class extends {@link Signalbegriffe_Ril_301Package}.
 */
class SignalbegriffeRil301PackageExtensions {

	/**
	 * @return the version of the Signale model
	 */
	static def String getModelVersion() {
		val uri = Signalbegriffe_Ril_301Package.eNS_URI
		return uri.substring(uri.lastIndexOf('/') + 1)
	}
}
