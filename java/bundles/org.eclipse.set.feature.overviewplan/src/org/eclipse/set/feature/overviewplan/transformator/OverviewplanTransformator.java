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
