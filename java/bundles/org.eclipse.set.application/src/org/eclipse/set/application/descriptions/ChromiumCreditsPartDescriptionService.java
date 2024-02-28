/**
 * Copyright (c) 2023 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.descriptions;

import jakarta.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.application.Messages;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;
import org.eclipse.set.core.services.part.PartDescriptionService;
import org.eclipse.set.utils.viewgroups.SetViewGroups;
import org.osgi.service.component.annotations.Component;

/**
 * Part description for chromium credits part.
 *
 */
@Component(service = PartDescriptionService.class)
public class ChromiumCreditsPartDescriptionService
		implements PartDescriptionService {
	private static class InjectionHelper {
		@Inject
		@Translation
		Messages messages;

		@SuppressWarnings("unused")
		public InjectionHelper() {
		}
	}

	@Override
	public PartDescription getDescription(final IEclipseContext context) {
		final InjectionHelper injectionHelper = ContextInjectionFactory
				.make(InjectionHelper.class, context);
		return new PartDescription(
				// ID
				ToolboxConstants.CHROMIUM_CREDITS_PART_ID,
				// contributionURI
				"bundleclass://org.eclipse.set.application/org.eclipse.set.application.about.ChromiumCreditsPart", //$NON-NLS-1$
				// toolboxViewName
				injectionHelper.messages.ChromiumCreditsPartDescriptionService_ViewName,
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
				false,
				// toolboxViewProcessPlanning
				false,
				// toolboxViewProcessIntegration
				false,
				// toolboxViewProcessInvalid
				true,
				// toolboxViewNeedsCleanSession
				false,
				// toolboxViewExclusiveEditor
				false);
	}

	@Override
	public ToolboxViewGroup getToolboxViewGroup() {
		return SetViewGroups.getInvisible();
	}
}
