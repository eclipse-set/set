/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.application.descriptions;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.set.basis.constants.ToolboxConstants;
import org.eclipse.set.basis.part.PartDescription;
import org.eclipse.set.basis.viewgroups.ToolboxViewGroup;
import org.eclipse.set.core.services.part.PartDescriptionService;
import org.eclipse.set.utils.viewgroups.SetViewGroups;
import org.osgi.service.component.annotations.Component;

/**
 * Part description for pdf viewer.
 * 
 * @author Schaefer
 */
@Component
public class AttachmentViewertPartDescriptionService
		implements PartDescriptionService {

	@Override
	public PartDescription getDescription(final IEclipseContext context) {
		return new PartDescription(
				// ID
				ToolboxConstants.ATTACHMENT_VIEWER_PART_ID,
				// contributionURI
				"bundleclass://org.eclipse.set.application/org.eclipse.set.application.parts.AttachmentViewerPart", //$NON-NLS-1$
				// toolboxViewName
				"", //$NON-NLS-1$
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

	@Override
	public ToolboxViewGroup getToolboxViewGroup() {
		return SetViewGroups.getInvisible();
	}
}
