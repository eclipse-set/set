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
import org.eclipse.set.model.siteplan.SiteplanFactory;
import org.osgi.service.component.annotations.Component;

/**
 * Implementation for {@link OverviewplanTransformator}
 * 
 * @author Truong
 */
@Component
public class OverviewplanTransformatorImpl
		implements OverviewplanTransformator {

	@Override
	public Siteplan transform(final IModelSession modelSession) {
		final Siteplan emptyPlan = SiteplanFactory.eINSTANCE.createSiteplan();
		emptyPlan.setChangedFinalState(
				SiteplanFactory.eINSTANCE.createSiteplanState());
		emptyPlan.setChangedInitialState(
				SiteplanFactory.eINSTANCE.createSiteplanState());
		emptyPlan.setCommonState(
				SiteplanFactory.eINSTANCE.createSiteplanState());
		emptyPlan
				.setFinalState(SiteplanFactory.eINSTANCE.createSiteplanState());
		emptyPlan.setInitialState(
				SiteplanFactory.eINSTANCE.createSiteplanState());

		return emptyPlan;
	}
}
