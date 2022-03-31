/**
 * Copyright (c) 2019 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.ppmodel.extensions

import de.scheidtbachmann.planpro.model.model1902.PlanPro.Planung_Projekt
import de.scheidtbachmann.planpro.model.model1902.PlanPro.Planung_Gruppe
import org.eclipse.set.core.services.Services

/** 
 * Extensions for {@link Planung_Projekt}.
 * 
 * @author Schaefer
 */
class PlanungProjektExtensions {
	
	/**
	 * @param project the Planung Projekt
	 * 
	 * @return a defined Planung Gruppe of the Planung Projekt or
	 * {@code null}, if there are no Planung Gruppe for the Planung Projekt
	 */
	static def Planung_Gruppe getPlanungGruppe(Planung_Projekt project) {
		// "1.9 update" toolbox currently supports only a single group
		return Services.planningAccessService.getPlanungGruppe(project)
	}
	
	/**
	 * Replace a defined Planung Gruppe for the given Planung Projekt with the
	 * given Planung Gruppe.
	 *  
	 * @param project the Planung Projekt
	 * @param group the Planung Gruppe
	 */
	static def void setLSTPlanungGruppe(Planung_Projekt project, Planung_Gruppe group) {
		// "1.9 update" toolbox currently supports only a single group
		Services.planningAccessService.setPlanungGruppe(project, group)
	}
}
