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
package org.eclipse.set.feature.table.pt1.sskz;

import org.eclipse.set.core.services.enumtranslation.EnumTranslationService;
import org.eclipse.set.feature.table.PlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.AbstractPlanPro2TableTransformationService;
import org.eclipse.set.feature.table.pt1.messages.Messages;
import org.eclipse.set.ppmodel.extensions.container.MultiContainer_AttributeGroup;
import org.eclipse.set.ppmodel.extensions.utils.TableNameInfo;
import org.eclipse.set.utils.table.TableModelTransformator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Service for creating the Sskz table model. org.eclipse.set.feature.table
 * 
 * @author Truong
 */
@Component(service = {
		PlanPro2TableTransformationService.class }, immediate = true, property = {
				"table.category=supplement-estw", "table.shortcut=sskz" })
public class SskzTransformationService
		extends AbstractPlanPro2TableTransformationService {

	@Reference
	private Messages messages;

	@Reference
	private EnumTranslationService enumTranslationService;

	/**
	 * constructor.
	 */
	public SskzTransformationService() {
		super();
	}

	@Override
	protected String getTableHeading() {
		return messages.SskzTableView_Heading;
	}

	@Override
	public TableNameInfo getTableNameInfo() {
		return new TableNameInfo(messages.ToolboxTableNameSskzLong,
				messages.ToolboxTableNameSskzPlanningNumber,
				messages.ToolboxTableNameSskzShort);
	}

	@Override
	public TableModelTransformator<MultiContainer_AttributeGroup> createTransformator() {
		return new SskzTransformator(cols, enumTranslationService);
	}
}
