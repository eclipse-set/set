/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.table.pt1.ssli;

import org.eclipse.set.core.services.part.PartDescriptionService;
import org.eclipse.set.feature.table.AbstractESTWTableDescription;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Part description for SSLI table view.
 * 
 * @author Schaefer
 */
@Component(service = PartDescriptionService.class)
public class SsliDescriptionService extends AbstractESTWTableDescription {
	@Reference
	Messages messages;

	@Override
	protected String getToolboxViewName() {
		return messages.SsliDescriptionService_ViewName;
	}

	@Override
	protected String getToolboxViewTooltip() {
		return messages.SsliDescriptionService_ViewTooltip;
	}

	@Override
	protected String getTableShortcut() {
		return messages.ToolboxTableNameSsliShort;
	}
}
