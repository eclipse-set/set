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

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.core.services.part.PartDescriptionService;

/**
 * Common table description functions.
 * 
 * @author Schaefer
 */
public abstract class AbstractTableDescription
		implements PartDescriptionService {
	@Override
	public PartDescription getDescription(final IEclipseContext context) {
		return new PartDescription(
				// ID
				getOptionalTableId().orElse(String.format("%s.%s", //$NON-NLS-1$
						getTableIdPrefix(), getTableShortcut().toLowerCase())),
				// contributionURI
				"bundleclass://org.eclipse.set.feature.table/org.eclipse.set.feature.table.ToolboxTableView", //$NON-NLS-1$
				// toolboxViewName
				getToolboxViewName(),
				// toolboxViewToolTip
				getToolboxViewTooltip(),
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

	@SuppressWarnings("static-method")
	protected Optional<String> getOptionalTableId() {
		return Optional.empty();
	}

	protected abstract String getToolboxViewName();

	protected abstract String getToolboxViewTooltip();

	protected abstract String getTableIdPrefix();

	protected abstract String getTableShortcut();
}
