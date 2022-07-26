/**
 * Copyright (c) 2020 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.ppmodel.extensions

import org.eclipse.set.model.model1902.PlanPro.util.PlanProResourceImpl
import org.eclipse.set.basis.files.PlanProXmlSave

/**
 * Extensions for {@link PlanProResourceImpl}.
 */
class PlanProResourceImplExtensions {

	static val STANDALONE = "standalone"

	/**
	 * Set the stand alone value of the XML declaration.
	 */
	static def void setStandalone(PlanProResourceImpl resource, String value) {
		// we replace the XMLSave implementation to manipulate the XML declaration
		resource.xmlSaveCreator = [ helper |
			new PlanProXmlSave(helper, #{STANDALONE -> value})
		]
	}

	/**
	 * Get the stand alone value of the XML declaration.
	 */
	static def String getStandalone(PlanProResourceImpl resource,
		String value) {
		return (resource?.xmlSaveCreator as PlanProXmlSave).
			additionalDeclarations?.get(STANDALONE);
	}
}
