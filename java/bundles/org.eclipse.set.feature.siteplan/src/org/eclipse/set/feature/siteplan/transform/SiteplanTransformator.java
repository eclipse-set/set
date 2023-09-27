/**
 * Copyright (c) 2022 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.siteplan.transform;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.model.siteplan.Siteplan;

/**
 * @author Stuecker
 *
 */
public interface SiteplanTransformator {
	/**
	 * Transforms the data in the model session into a siteplan model
	 * 
	 * @param modelSession
	 * @return a siteplan model
	 */
	Siteplan transform(final IModelSession modelSession);

	/**
	 * Cancel transformator
	 */
	void stopTransform();
}
