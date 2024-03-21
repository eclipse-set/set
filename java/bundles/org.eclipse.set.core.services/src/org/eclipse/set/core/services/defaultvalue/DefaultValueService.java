/**
 * Copyright (c) 2017 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.core.services.defaultvalue;

import org.eclipse.set.model.planpro.PlanPro.PlanPro_Schnittstelle;

/**
 * Describes, how default values for a PlanPro Schnittstelle are set.
 * 
 * @author Schaefer
 */
public interface DefaultValueService {

	/**
	 * Set default values for the given PlanPro Schnittstelle.
	 * 
	 * @param planProSchnittstelle
	 *            the PlanPro Schnittstelle
	 */
	void setDefaultValues(PlanPro_Schnittstelle planProSchnittstelle);
}
