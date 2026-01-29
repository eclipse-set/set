/**
 * Copyright (c) 2026 DB InfraGO AG and others
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 */
package org.eclipse.set.feature.table.pt1.ssbv;

import java.util.Collections;
import java.util.List;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.TableModelTransformator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventAdmin;

/**
 * 
 */
@Component(service = {
		PlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.category=supplement", "table.shortcut=ssbv" })
public class SsbvTransformationService
		extends AbstractPlanPro2TableTransformationService {
	@Reference
	Messages messages;

	@Reference
	EventAdmin eventAdmin;

	@Reference
	EnumTranslationService enumTranslationService;

	@Override
	protected String getTableHeading() {
		return messages.SsbvTableView_Heading;
	}

	@Override
	protected String getShortcut() {
		return messages.ToolboxTableNameSsbvShort;
	}

	@Override
	protected List<String> getTopologicalColumnPosition() {
		return Collections.emptyList();
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSsbvLong,
				messages.ToolboxTableNameSsbvPlanningNumber,
				messages.ToolboxTableNameSsbvShort);
	}

	@Override
	public TableModelTransformator<MultiContainer_AttributeGroup> createTransformator() {
		return new SsbvTransformator(cols, enumTranslationService, eventAdmin);
	}

}
