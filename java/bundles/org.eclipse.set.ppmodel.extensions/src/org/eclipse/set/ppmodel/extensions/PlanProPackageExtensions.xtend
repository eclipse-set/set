/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.planpro.PlanPro.PlanProPackage

/**
 * This class extends {@link PlanProPackage}.
 */
class PlanProPackageExtensions {

	/**
	 * @return the version of the PlanPro model
	 */
	static def String getModelVersion() {
		val uri = PlanProPackage.eNS_URI
		return uri.substring(uri.lastIndexOf('/') + 1)
	}
}
