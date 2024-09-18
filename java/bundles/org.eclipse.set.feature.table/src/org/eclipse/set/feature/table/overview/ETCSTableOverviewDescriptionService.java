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
package org.eclipse.set.feature.table.overview;

import static org.eclipse.set.basis.constants.ToolboxConstants.ETCS_TABLE_PART_ID_PREFIX;
import static org.eclipse.set.basis.constants.ToolboxConstants.TABLE_OVERVIEW_ID;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;
import org.eclipse.set.core.services.part.PartDescriptionService;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.utils.viewgroups.SetViewGroups;
import org.osgi.service.component.annotations.Component;

import jakarta.inject.Inject;

/**
 * 
 */
@Component(immediate = true)
public class ETCSTableOverviewDescriptionService
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
				String.format("%s.%s", ETCS_TABLE_PART_ID_PREFIX, //$NON-NLS-1$
						TABLE_OVERVIEW_ID),
				// contributionURI
				"bundleclass://org.eclipse.set.feature.table/org.eclipse.set.feature.table.overview.TableOverviewPart", //$NON-NLS-1$
				// toolboxViewName
				injectionHelper.messages.ETCS_TableOverviewDescriptionService_ViewName,
				// toolboxViewToolTip
				"", //$NON-NLS-1$
				// toolboxViewType
				getToolboxViewGroup(),
				// defaultForNature
				null,
				// toolboxViewNeedsLoadedModel
				true,
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
				false,
				// toolboxViewNeedsCleanSession
				false,
				// toolboxViewExclusiveEditor
				false,
				// Priority: Show first
				100);
	}

	@Override
	public ToolboxViewGroup getToolboxViewGroup() {
		return SetViewGroups.getTable_ETCS();
	}

}
