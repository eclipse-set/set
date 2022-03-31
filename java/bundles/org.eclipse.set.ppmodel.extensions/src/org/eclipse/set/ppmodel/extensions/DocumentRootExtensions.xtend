/**
 * Copyright (c) 2017 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.PlanPro.DocumentRoot
import de.scheidtbachmann.planpro.model.model1902.PlanPro.PlanProPackage
import de.scheidtbachmann.planpro.model.model1902.PlanPro.PlanPro_Schnittstelle
import org.eclipse.set.basis.PlanProSchemaDir

import static extension org.eclipse.set.basis.extensions.PathExtensions.*

/**
 * Extensions for {@link DocumentRoot}.
 * 
 * @author Schaefer
 */
class DocumentRootExtensions {

	/**
	 * The PlanPro schema dir 
	 */
	public static val PlanProSchemaDir<PlanPro_Schnittstelle> SCHEMA_DIR = new PlanProSchemaDir(
		typeof(PlanPro_Schnittstelle));

	static val String DEFAULT_LOCATION = "model location not found"

	/**
	 * Perform some corrections for the given document root
	 * 
	 * @param documentRoot this document root
	 */
	static def void fix(DocumentRoot documentRoot) {
		documentRoot.XMLNSPrefixMap.put("xsi",
			"http://www.w3.org/2001/XMLSchema-instance")
		documentRoot.XSISchemaLocation.clear
	}

	static def String getModelLocation() {
		return SCHEMA_DIR.planProSchemaPath.map [
			subpath(1, it.nameCount).toString("/")
		].orElse(DEFAULT_LOCATION)
	}

	static def String getNsUri() {
		return PlanProPackage.eNS_URI
	}
}
