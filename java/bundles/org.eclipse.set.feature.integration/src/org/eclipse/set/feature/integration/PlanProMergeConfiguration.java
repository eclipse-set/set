/**
 * Copyright (c) 2018 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */
package org.eclipse.set.feature.integration;

import org.eclipse.set.basis.integration.DiffLabelProvider;
import org.eclipse.set.basis.integration.Matcher;
import org.eclipse.set.core.services.merge.MergeService.Configuration;
import org.eclipse.set.core.services.merge.MergeService.ElementProvider;
import org.eclipse.set.core.services.merge.MergeService.GuidProvider;
import org.eclipse.set.core.services.name.NameService;

/**
 * Merge configuration for PlanPro model.
 * 
 * @author Schaefer
 */
public class PlanProMergeConfiguration implements Configuration {

	private final ElementProvider elementProvider;
	private final GuidProvider guidProvider;
	private final DiffLabelProvider labelProvider;
	private final Matcher matcher;

	/**
	 * Creates a {@link PlanProMergeConfiguration}.
	 * 
	 * @param nameService
	 *            the name service
	 */
	public PlanProMergeConfiguration(final NameService nameService) {
		guidProvider = new PlanProGuidProvider();
		elementProvider = new PlanProElementProvider();
		labelProvider = new PlanProLabelProvider(nameService);
		matcher = new PlanProMatcher(labelProvider);
	}

	@Override
	public ElementProvider getElementProvider() {
		return elementProvider;
	}

	@Override
	public GuidProvider getGuidProvider() {
		return guidProvider;
	}

	@Override
	public DiffLabelProvider getLabelProvider() {
		return labelProvider;
	}

	@Override
	public Matcher getMatcher() {
		return matcher;
	}
}
