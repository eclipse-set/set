/**
 * Copyright (c) 2022 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.utils.descriptions;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.core.services.part.PartDescriptionService;

/**
 * Abstract description for source text view in different view groups.
 * 
 * @author Stuecker
 */
public abstract class AbstractWebSourceTextViewDescriptionService
		implements PartDescriptionService {

	@Override
	public PartDescription getDescription(final IEclipseContext context) {
		return new PartDescription(
				// ID
				this.getClass().getName(),
				// contributionURI
				"bundleclass://org.eclipse.set.application/org.eclipse.set.application.parts.SourceTextViewPart", //$NON-NLS-1$
				// toolboxViewName
				getViewName(context),
				// toolboxViewToolTip
				"", //$NON-NLS-1$
				// toolboxViewType
				getToolboxViewGroup(),
				// defaultForNature
				null,
				// toolboxViewNeedsLoadedModel
				false,
				// toolboxViewNeedsXsdValidation
				false,
				// toolboxViewNeedsEmfValidation
				false,
				// toolboxViewProcessState
				true,
				// toolboxViewProcessPlanning
				true,
				// toolboxViewProcessIntegration
				true,
				// toolboxViewProcessInvalid
				true,
				// toolboxViewNeedsCleanSession
				false,
				// toolboxViewExclusiveEditor
				false);
	}

	protected abstract String getViewName(IEclipseContext context);
}
