/**
 * Copyright (c) 2021 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.core.services.planningaccess;

import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Gruppe;
import org.eclipse.set.toolboxmodel.PlanPro.Planung_Projekt;

/**
 * Provide access to certain Planung Projekt and Planung Gruppe instances. By
 * means of this service, the client can access specified Planung Projekt and
 * Planung Gruppe instances as if they were unique (simulating the PlanPro 1.8
 * API).
 * 
 * @author Schaefer
 */
public interface PlanningAccessService {

	/**
	 * @param planProIterface
	 *            the PlanPro Schnittstelle
	 * 
	 * @return a defined Planung Projekt
	 */
	public Planung_Projekt getLSTPlanungProjekt(
			PlanPro_Schnittstelle planProIterface);

	/**
	 * @param project
	 *            the Planung Projekt
	 * 
	 * @return a defined Planung Gruppe
	 */
	public Planung_Gruppe getPlanungGruppe(Planung_Projekt project);

	/**
	 * Set the Planung Projekt at a defined place.
	 * 
	 * @param planProIterface
	 *            the PlanPro Schnittstelle
	 * @param project
	 *            the Planung Projekt
	 */
	public void setLSTPlanungProjekt(PlanPro_Schnittstelle planProIterface,
			Planung_Projekt project);

	/**
	 * Set the Planung Gruppe at a defined place.
	 * 
	 * @param project
	 *            the Planung Projekt
	 * @param group
	 *            the Planung Gruppe
	 */
	public void setPlanungGruppe(Planung_Projekt project, Planung_Gruppe group);
}
