/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.table;

import java.util.Optional;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.nls.Translation;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;
import org.eclipse.set.core.services.part.PartDescriptionService;
import org.eclipse.set.feature.table.messages.Messages;
import org.eclipse.set.utils.viewgroups.SetViewGroups;

/**
 * Common table description functions.
 * 
 * @author Schaefer
 */
public abstract class AbstractTableDescription
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
				getOptionalTableId().orElse(
						"org.eclipse.set.feature.table." + getTableId()), //$NON-NLS-1$
				// contributionURI
				"bundleclass://org.eclipse.set.feature.table/org.eclipse.set.feature.table.ToolboxTableView", //$NON-NLS-1$
				// toolboxViewName
				getToolboxViewName(injectionHelper.messages),
				// toolboxViewToolTip
				getToolboxViewTooltip(injectionHelper.messages),
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
				false);
	}

	@Override
	public ToolboxViewGroup getToolboxViewGroup() {
		return SetViewGroups.getTable();
	}

	private String getTableId() {
		// IMPROVE: use table service?
		return this.getClass().getSimpleName().toLowerCase().substring(0, 4);
	}

	@SuppressWarnings("static-method")
	protected Optional<String> getOptionalTableId() {
		return Optional.empty();
	}

	protected abstract String getToolboxViewName(final Messages messages);

	protected abstract String getToolboxViewTooltip(Messages messages);
}
