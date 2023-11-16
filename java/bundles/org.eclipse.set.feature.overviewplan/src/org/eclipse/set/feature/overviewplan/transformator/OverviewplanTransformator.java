/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.overviewplan.transformator;

import org.eclipse.set.basis.IModelSession;
import org.eclipse.set.model.siteplan.Siteplan;

/**
 * Transform the PlanPro data model into the overviewplan data
 * 
 * @author Truong
 */
public interface OverviewplanTransformator {
	/**
	 * Transform model to overviewplan data
	 * 
	 * @param modelSession
	 * @return overviewplan data
	 */
	Siteplan transform(IModelSession modelSession);
}
