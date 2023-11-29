/**
 * Copyright (c) 2023 DB Netz AG and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.overviewplan.transformator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.set.feature.siteplan.transform.AbstractSiteplanTransformator;
import org.eclipse.set.feature.siteplan.transform.Transformator;
import org.eclipse.set.model.siteplan.Position;
import org.eclipse.set.model.siteplan.Siteplan;
import org.eclipse.set.model.siteplan.SiteplanFactory;
import org.eclipse.set.model.siteplan.SiteplanState;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.toolboxmodel.PlanPro.PlanPro_Schnittstelle;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * Implementation for {@link OverviewplanTransformator}
 * 
 * @author Truong
 */
@Component(service = OverviewplanTransformator.class)
public class OverviewplanTransformatorImpl extends AbstractSiteplanTransformator
		implements OverviewplanTransformator {
	/**
	 * List of transformator
	 */
	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY, target = "(component.name=org.eclipse.set.feature.overviewplan.transformator.*)")
	public final List<Transformator> transformators = new ArrayList<>();

	@Override
	public SiteplanState transformState(
			final MultiContainer_AttributeGroup container) {
		final SiteplanState siteplanState = SiteplanFactory.eINSTANCE
				.createSiteplanState();
		transformators.forEach(transform -> transform
				.transformContainer(siteplanState, container));
		return siteplanState;
	}

	@Override
	public Position getLeadingPosition(
			final PlanPro_Schnittstelle schnittstelle,
			final MultiContainer_AttributeGroup container) {
		return null;
	}

	// This need for UnitTest
	@Override
	public Siteplan transform(final PlanPro_Schnittstelle schnittstelle) {
		return super.transform(schnittstelle);
	}
}
