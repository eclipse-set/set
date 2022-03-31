/**
 * Copyright (c) 2020 DB Netz AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 */

package org.eclipse.set.feature.table.descriptions;

import org.eclipse.set.core.services.part.PartDescriptionService;
import org.eclipse.set.feature.table.AbstractTableDescription;
import org.eclipse.set.feature.table.messages.Messages;
import org.osgi.service.component.annotations.Component;

/**
 * Part description for SSLI table view.
 * 
 * @author Schaefer
 */
@Component(service = PartDescriptionService.class)
public class SsliDescriptionService extends AbstractTableDescription {

	@Override
	protected String getToolboxViewName(final Messages messages) {
		return messages.SsliDescriptionService_ViewName;
	}

	@Override
	protected String getToolboxViewTooltip(final Messages messages) {
		return messages.SsliDescriptionService_ViewTooltip;
	}
}
