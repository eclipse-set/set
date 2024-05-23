/**
 * Copyright (c) 2024 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.core.services.siteplan;

import java.util.Optional;

import org.eclipse.set.model.planpro.Basisobjekte.Ur_Objekt;
import org.eclipse.set.model.siteplan.SiteplanObject;

/**
 * Bridge between the siteplan and the table, which find reference of planPro
 * object in siteplan
 * 
 * @author Truong
 */
public interface SiteplanService {
	/**
	 * Storage avaible object in siteplan
	 * 
	 * @param object
	 *            siteplan object
	 */
	void addSiteplanElement(SiteplanObject object);

	/**
	 * Check, if the siteplan contains this object
	 * 
	 * @param object
	 *            the table object
	 * @return true, if siteplan contains this object
	 */
	boolean isSiteplanElement(Ur_Objekt object);

	/**
	 * @param guid
	 * @return true, if site plan contains object with this guid
	 */
	boolean isSiteplanElement(String guid);

	/**
	 * Find reference object of the Ur_Objekt in siteplan
	 * 
	 * @param object
	 *            the Ur_Objekt
	 * @return the siteplan object
	 */
	Optional<SiteplanObject> getSiteplanElement(Ur_Objekt object);

	/**
	 * @return true, if siteplan still loading
	 */
	boolean isSiteplanLoading();

	/**
	 * @return true, when siteplan is at least one times open
	 */
	boolean isNotFirstTimeOpenSiteplan();
}
