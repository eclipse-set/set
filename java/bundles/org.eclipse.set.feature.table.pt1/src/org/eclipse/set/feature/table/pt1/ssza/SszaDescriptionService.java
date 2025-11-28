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

package org.eclipse.set.feature.table.pt1.ssza;

import org.eclipse.set.core.services.part.PartDescriptionService;
import org.eclipse.set.feature.table.AbstractETCSTableDescription;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Part description for ssza table view.
 */
@Component(service = PartDescriptionService.class)
public class SszaDescriptionService extends AbstractETCSTableDescription {
	@Reference
	Messages messages;

	@Override
	protected String getToolboxViewName() {
		return messages.SszaDescriptionService_ViewName;
	}

	@Override
	protected String getToolboxViewTooltip() {
		return messages.SszaDescriptionService_ViewTooltip;
	}

	@Override
	protected String getTableShortcut() {
		return messages.ToolboxTableNameSszaShort;
	}
}
